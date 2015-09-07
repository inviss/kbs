using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Forms;
using System.Linq;
using System.Text;

using System.Xml.Serialization;
using System.IO;
using System.Xml;
using System.ServiceModel;

using System.Threading;
using Transcoder_KBS.SoapObj;
using LoggerWitter;
using FileMoveManager;

namespace Transcoder_KBS
{
    
    public class WorkAgent
    {
#region Variables
        public delegate void GotNewJob(workflow wf);
        public event GotNewJob OnGotNewJob;

        public delegate void LocalCopyPercent(int percent);
        public event LocalCopyPercent OnLocalCopyPercent;
        /************************************************************************/
        private ServiceNavigatorService TC_Interface = null;
        private BindingList<Job> _bindJobList = null;
        private List<Job> _listJobs = null;
        public BindingSource BndSrcJob = null;

        private DataGridView _dtGrdJobQ= null;
        //private Label _lbJobCnt = null;
        /************************************************************************/
        public string SourcePath = "Not Set";
        public string SourceFile = "Not Set";
        public string TargetPath = "Not Set";
        public string CTI_ID = "";
        public string Start_TC = "00:00:00";
        public string End_TC = "00:00:00";
        public string Default_Opt = string.Empty;
        public string EqID = "";
        /************************************************************************/
        public DateTime LastJobReqeustTime = DateTime.Now.AddDays(-1);
        private Logger _Logger = null;
        private List<Job> _RecoveryList = null;
        private ConfigInfo _Config = null;
        private BackgroundWorker _BackWorker = null;
        /************************************************************************/
        private bool _bCheckError = false;
        private string _strLastCopyedFileName = string.Empty;
             
        /************************************************************************/
#endregion
#region Constuctor
        public WorkAgent(DataGridView dtQ, ServiceNavigatorService tc_interface, ConfigInfo Config)
        {
            TC_Interface = tc_interface;
            _listJobs = new List<Job>();
            _bindJobList = new BindingList<Job>(_listJobs);
            BndSrcJob = new BindingSource(_bindJobList, null);
            _dtGrdJobQ = dtQ;
            _Config = Config;
            _listJobs.Clear();

            _Logger = new Logger(string.Format(@"{0}\Log", Application.StartupPath), string.Format("WorkAgent"));
            _Logger.WriteLog("****************Initialized*******************");

            _RecoveryList = new List<Job>();

            _BackWorker = new BackgroundWorker();
            _BackWorker.WorkerReportsProgress = true;
            _BackWorker.WorkerSupportsCancellation = true;
            _BackWorker.DoWork += new DoWorkEventHandler(_BackWorker_DoWork);
            _BackWorker.ProgressChanged += new ProgressChangedEventHandler(_BackWorker_ProgressChanged);
            _BackWorker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(_BackWorker_RunWorkerCompleted);
        }
        ~WorkAgent()
        {
            _listJobs.Clear();
            if (TC_Interface != null)
            {
                TC_Interface.Abort();
                TC_Interface = null;
            }
        }
#endregion
#region Method-For-Transcoder
        public Job GetJob(string eqid)
        {
            Job job = null;
            try
            {
                Monitor.Enter(this);
                if (_listJobs.Count < 1)
                    return null;

                if(_Config.CopyMode)
                    if (!_listJobs[0].IsLocalCopied)
                        return null;
                
                job = _listJobs[0];
                _listJobs.RemoveAt(0);

                UpdateJobQ(false);
            }
            catch (Exception ex)
            {
                _Logger.WriteLog(ex);
            }
            finally
            {
                Monitor.Exit(this);
            }
            return job;
        }

        public void UpdateJobState(Job j, int pro ,string eqid)
        {
            try
            {
                Job[] jobs = new Job[1];
                jobs[0] = j;
                status st = new status
                {
                    eq_id = eqid,
                    eq_state = "B",
                    progress = pro,

                    job_id = j.job_id
                };
                if (pro == 100)
                {
                    st.job_state = "C";
                }
                else
                    st.job_state = "i";

                workflow wf = new workflow
                {
                    //불필요한 항목(Server쪽)이라 주석함 [12/23/2014 sehui]
                    //source_path = SourcePath,
                    //source_file = SourceFile,
                    //target_path = TargetPath,
                    //default_opt = Default_Opt,
                    //cti_Id = CTI_ID,
                    status = st,
                };

                QueryUpdateJobState(wf);
            }
            catch (System.Exception ex)
            {
                _Logger.WriteLog(ex);
            }
        }
        public void RetryUpdateJobErrorSuccessed(Job j, StatusErrorCode isError, string eqid)
        {
            int retryCount = 0;
            while (true)
            {
                retryCount++;
                try
                {
                    if (isError != StatusErrorCode.NO_ERROR)
                    {
                        _bCheckError = _bCheckError | true;
                        _Logger.WriteLog(string.Format("{0}({1}) Occured Error : {2} - CheckEror Value : {3}", eqid, j.job_id, isError, _bCheckError));
                    }
                    if (!UpdateJobErrorSuccessed(j, isError, eqid))
                        throw new Exception("UpdateJobErrorSuccesed - Failed");
                    return;
                }
                catch (System.Exception ex)
                {
                    if (retryCount >= 5)
                    {
                        _Logger.WriteLog(string.Format("[Move to Job to the RecoveryList-({0})]", j.job_id));
                        _RecoveryList.Add(j);
                        _RecoveryList[_RecoveryList.Count - 1].UpdateResult = (isError!= StatusErrorCode.NO_ERROR)? true : false;
                        return;
                    }
                    else
                    {
                        _Logger.WriteLog(string.Format("[It was Failed To Update to Server-({0})] Count : {1}\r\n{2}\r\n{3}",j.job_id, retryCount, ex.Message, ex.StackTrace));
                        Thread.Sleep(5 * 1000);
                    }
                }
            }
        }
        public bool UpdateJobErrorSuccessed(Job j, StatusErrorCode isError,string eqid)
        {
            try
            {
                Job[] jobs = new Job[1];
                jobs[0] = j;
                status st = new status
                {
                    eq_id = eqid,
                    eq_state = "B",
                    job_id = j.job_id
                };

                if (isError != StatusErrorCode.NO_ERROR)
                {
                    st.progress = 0;
                    st.job_state = "E";
                    st.error_cd = ((int)isError).ToString();
                }
                else
                {
                    st.progress = 100;
                    st.job_state = "C";
                }

                workflow wf = new workflow
                {
                    //불필요한 항목(Server쪽)이라 주석함 [12/23/2014 sehui]
                    //source_path = SourcePath,
                    //source_file = SourceFile,
                    //target_path = TargetPath,
                    //default_opt = Default_Opt,
                    //cti_Id = CTI_ID,
                    status = st,
                };
                QueryUpdateJobState(wf);
                if (isError == StatusErrorCode.NO_ERROR)
                {
                    Content Ctt = new Content
                    {
                        job_id = j.job_id,
                        job_gb = (j.job_kind.ToUpper() == "VIDEO")? "V":"A",
                        job_state = "C",
                        file_size = "",
                        frm_per_sec = "",
                        job_err_code = "",
                    };
                    QulityCheck Qc = new QulityCheck
                    {
                        content = Ctt,
                    };
                    QueryQulityCheckResult(Qc);
                }
                return true;
            }
            catch (System.Exception ex)
            {
                _Logger.WriteLog(ex);
                return false;
            }
        }

        private void QueryUpdateJobState(workflow wf)
        {
            Stream msIn = null;
            BufferedStream bfsIn = null;
            XmlTextWriter xtw = null;
            StreamReader sr = null;
            XmlSerializer xs = null;
            try
            {
                xs = new XmlSerializer(typeof(workflow));
                string strXml = null;
                string strOutXml = "";

                //네임 스페이스 제거
                XmlSerializerNamespaces xmlnsEmpty = new XmlSerializerNamespaces();
                xmlnsEmpty.Add("", "");
                
                // 버퍼를 쓰면 빠르지 않을까?? [1/10/2013 LJY]
                msIn = new MemoryStream();
                bfsIn = new BufferedStream(msIn);
                xtw = new XmlTextWriter(bfsIn, new UTF8Encoding(false));
               
                xs.Serialize(xtw, wf, xmlnsEmpty);
                bfsIn.Seek(0, SeekOrigin.Begin);
                sr = new StreamReader(bfsIn);
                
                strXml = sr.ReadToEnd();
                Console.WriteLine("UPDATE JOB STAT");
                Console.WriteLine("CLT XML");
                Console.WriteLine(strXml);
                Console.WriteLine("\r\n");
                strOutXml = TC_Interface.saveStatus(strXml);
                Console.WriteLine("CMS JOB XML");
                Console.WriteLine(strOutXml);
                Console.WriteLine("\r\n");

                if (xtw != null) xtw.Close();
                xtw = null;
                if (sr != null) sr.Close();
                sr = null;
                if (bfsIn != null) bfsIn.Close();
                bfsIn = null;
                if (msIn != null) msIn.Close();
                msIn = null;

                xs = null;
                //_Logger.WriteLog(string.Format("[UpdateStatus - Cleared Memory]"));
            }
            catch (Exception ex)
            {
                _Logger.WriteLog(ex);
                if (xtw != null) xtw.Close();
                xtw = null;
                if (sr != null) sr.Close();
                sr = null;
                if (bfsIn != null) bfsIn.Close();
                bfsIn = null;
                if (msIn != null) msIn.Close();
                msIn = null;

                xs = null;
                _Logger.WriteLog(string.Format("[UpdateStatus-Exception - Cleared Memory]"));
                throw ex;
            }
        }
        private void QueryQulityCheckResult(QulityCheck Qc)
        {
            Stream msIn = null;
            BufferedStream bfsIn = null;
            XmlTextWriter xtw = null;
            StreamReader sr = null;
            XmlSerializer xs = null;
            try
            {
                xs = new XmlSerializer(typeof(QulityCheck));
                string strXml = null;
                string strOutXml = "";

                //네임 스페이스 제거
                XmlSerializerNamespaces xmlnsEmpty = new XmlSerializerNamespaces();
                xmlnsEmpty.Add("", "");

                // 버퍼를 쓰면 빠르지 않을까?? [1/10/2013 LJY]
                msIn = new MemoryStream();
                bfsIn = new BufferedStream(msIn);
                xtw = new XmlTextWriter(bfsIn, new UTF8Encoding(false));

                xs.Serialize(xtw, Qc, xmlnsEmpty);
                bfsIn.Seek(0, SeekOrigin.Begin);
                sr = new StreamReader(bfsIn);
                
                strXml = sr.ReadToEnd();
                strXml = strXml.Replace("QulityCheck", "workflow");
                Console.WriteLine("UPDATE JOB STAT");
                Console.WriteLine("CLT XML");
                Console.WriteLine(strXml);
                Console.WriteLine("\r\n");
                _Logger.WriteLog(string.Format("[SaveRePorts] {0}", strXml));
                strOutXml = TC_Interface.saveReports(strXml);
                _Logger.WriteLog(string.Format("[SaveRePorts - Result({0})]  {1}", Qc.content.job_id, strOutXml));
                Console.WriteLine("CMS JOB XML");
                Console.WriteLine(strOutXml);
                Console.WriteLine("\r\n");

                if (xtw != null) xtw.Close();
                xtw = null;
                if (sr != null) sr.Close();
                sr = null;
                if (bfsIn != null) bfsIn.Close();
                bfsIn = null;
                if (msIn != null) msIn.Close();
                msIn = null;

                xs = null;
                //_Logger.WriteLog(string.Format("[SaveRePorts - Cleared Memory]"));
#region 
                //using (Stream msIn = new MemoryStream(), bfsIn = new BufferedStream(msIn))
                //{
                //    XmlTextWriter xtw = new XmlTextWriter(bfsIn, new UTF8Encoding(false));
                //    xs.Serialize(xtw, Qc, xmlnsEmpty);
                //    bfsIn.Seek(0, SeekOrigin.Begin);
                //    using(StreamReader sr = new StreamReader(bfsIn))
                //    {
                //        strXml = sr.ReadToEnd();
                //        strXml = strXml.Replace("QulityCheck", "workflow");
                //        Console.WriteLine("UPDATE JOB STAT");
                //        Console.WriteLine("CLT XML");
                //        Console.WriteLine(strXml);
                //        Console.WriteLine("\r\n");
                //        _Logger.WriteLog(string.Format("[SaveRePorts] {0}", strXml));
                //        strOutXml = TC_Interface.saveReports(strXml);
                //        _Logger.WriteLog(string.Format("[SaveRePorts - Result] {0}", strOutXml));
                //        Console.WriteLine("CMS JOB XML");
                //        Console.WriteLine(strOutXml);
                //        Console.WriteLine("\r\n");
                //    }
                //    xtw = null;
                //    bfsIn.Close();
                //}
#endregion
            }
            catch (Exception ex)
            {
                if (xtw != null) xtw.Close();
                xtw = null;
                if (sr != null) sr.Close();
                sr = null;
                if (bfsIn != null) bfsIn.Close();
                bfsIn = null;
                if (msIn != null) msIn.Close();
                msIn = null;

                xs = null;
                _Logger.WriteLog(string.Format("[SaveRePorts-Exception - Cleared Memory]"));
                throw ex;
            }
        }
        private void UpdateJobQ(bool metadataChanged)
        {
            try
            {
                _dtGrdJobQ.Invoke((MethodInvoker)(() =>
                {
                    try 
                    { 
                        BndSrcJob.ResetBindings(false);
                        _dtGrdJobQ.Refresh();
                    }
                    catch (Exception ex)
                    {
                        _Logger.WriteLog(ex);
                    }
                }));
            }
            catch (Exception ex)
            {
                _Logger.WriteLog(ex);
            }
            finally 
            {
                Thread.Sleep(100); 
            }
        }
#endregion
#region Methoed-For-MainForm
        public void WriteLog(string str)
        {
            if (_Logger != null)
                _Logger.WriteLog(string.Format("[By MainForm] {0}",str));
        }
        public void UpdateConfig(ConfigInfo config)
        {
            _Config = config;
        }

        public int GetJobListCount()
        {
            return _listJobs.Count;
        }
        public int QueryGetJobs(string eqid)
        {
            XmlSerializer xs = null;
            try
            {
                if ((DateTime.Now - LastJobReqeustTime).TotalSeconds < 10)
                    return _listJobs.Count;

                workflow wf = new workflow
                {
                    status = new status
                    {
                        eq_id = string.Format("{0}_01",eqid),
                        eq_state = "W"
                    }
                };
                /************************************************************************/             
                Stream msIn = null;
                BufferedStream bfsIn = null;
                XmlTextWriter xtw = null;
                StreamReader sr = null;
                xs = new XmlSerializer(typeof(workflow));
                string strXml = string.Empty;
                string strOutXml = string.Empty;

                //네임 스페이스 제거
                /************************************************************************/             
                XmlSerializerNamespaces xmlnsEmpty = new XmlSerializerNamespaces();
                xmlnsEmpty.Add("", "");
                try
                {
                    // 버퍼를 쓰면 빠르지 않을까?? [1/10/2013 LJY]
                    msIn = new MemoryStream();
                    bfsIn = new BufferedStream(msIn);
                    xtw = new XmlTextWriter(bfsIn, new UTF8Encoding(false));
                    XmlTextWriter.Create(bfsIn);
                        
                    xs.Serialize(xtw, wf, xmlnsEmpty);
                    bfsIn.Seek(0, SeekOrigin.Begin);
                    sr = new StreamReader(bfsIn);
                    
                    strXml = sr.ReadToEnd();
                    Console.WriteLine("Request Job to CMS");
                    Console.WriteLine("CLT XML");
                    Console.WriteLine(strXml);
                    Console.WriteLine("\r\n");
                    strOutXml = TC_Interface.saveStatus(strXml);
                    LastJobReqeustTime = DateTime.Now;
                    Console.WriteLine("CMS JOB XML");
                    Console.WriteLine(strOutXml);
                    Console.WriteLine("\r\n");

                    if (xtw != null) xtw.Close();
                    xtw = null;
                    if (sr != null) sr.Close();
                    sr = null;
                    if (bfsIn != null) bfsIn.Close();
                    bfsIn = null;
                    if (msIn != null) msIn.Close();
                    msIn = null;
                    //_Logger.WriteLog(string.Format("[GetJob - Cleared Memory]"));
                }
                catch (Exception ex)
                {
                    _Logger.WriteLog(ex);
                    Console.WriteLine(ex.Message + "\r\n" + ex.StackTrace);
                    if (xtw != null) xtw.Close();
                    xtw = null;
                    if (sr != null) sr.Close();
                    sr = null;
                    if (bfsIn != null) bfsIn.Close();
                    bfsIn = null;
                    if (msIn != null) msIn.Close();
                    msIn = null;

                    _Logger.WriteLog(string.Format("[GetJob-Exception - Cleared Memory]"));
                    Thread.Sleep(1000 * _Config.Thread_Count);
                }
                /************************************************************************/                
                wf = null;
                if (strOutXml.Length > 0 && strOutXml != "false" && strOutXml != "FALSE")
                {
                    using (Stream msOut = new MemoryStream(System.Text.Encoding.Default.GetBytes(strOutXml)), bfOut = new BufferedStream(msOut))
                    {
                        using (XmlTextReader xtr = new XmlTextReader(bfOut))
                        {
                            wf = xs.Deserialize(xtr) as workflow;
                        }
                        bfOut.Close();

                        if(msOut != null) msOut.Close();
                    }
                }
                /************************************************************************/
                if (wf != null && wf.cti_Id != null)
                {
                    _Logger.WriteLog(string.Format("--->Got a Job\r\n{0}", strOutXml));
                    SourcePath = wf.source_path;
                    SourceFile = wf.source_file;
                    TargetPath = wf.target_path;
                    CTI_ID = wf.cti_Id;
                    Start_TC = wf.start_time;
                    End_TC = wf.end_time;
                    Default_Opt = wf.default_opt;
                    EqID = eqid;
                    
                    foreach (Job job in wf.job)
                    {
                        job.audio_mode = wf.audio_mode;
                        job.SetPropertyData();
                    }

                    wf.job[0].IsLocalCopied = false;
                    foreach (Job job in wf.job)
                    {
                        job.IsLocalCopied = false;
                        _Logger.WriteLog(string.Format("Set CopyState : {0} , {1} ",job.JOB_Id,job.IsLocalCopied));
                    }
                    _listJobs.AddRange(wf.job);

                    if (OnGotNewJob != null)
                        OnGotNewJob(wf);
                    UpdateJobQ(false);
                    ThreadPool.QueueUserWorkItem(LocalDelProc, wf.source_file);
                    /************************************************************************/
                    // local hard full 현상으로 보관 하지 않기로함. [8/11/2015 sehui]
                    //if (_Config.CopyMode)
                    //{
                    //    if (_bCheckError)
                    //    {
                    //        if (_strLastCopyedFileName !=null && _strLastCopyedFileName != string.Empty)
                    //        {
                    //            string ErrorFolder = string.Format(@"{0}\Error",_Config.LocalFolder);
                    //            if(!Directory.Exists(ErrorFolder))
                    //                Directory.CreateDirectory(ErrorFolder);
                    //            if (File.Exists(string.Format(@"{0}\{1}", ErrorFolder, Path.GetFileName(_strLastCopyedFileName))))
                    //            {
                    //                _Logger.WriteLog(string.Format("-->Already Existed Error File.It will be Deleted : {0}", string.Format(@"{0}\{1}", ErrorFolder, Path.GetFileName(_strLastCopyedFileName))));
                    //                File.Delete(string.Format(@"{0}\{1}", ErrorFolder, Path.GetFileName(_strLastCopyedFileName)));
                    //            }
                    //            File.Move(_strLastCopyedFileName, string.Format(@"{0}\{1}", ErrorFolder, Path.GetFileName(_strLastCopyedFileName)));
                    //            _Logger.WriteLog(string.Format("-->Errored File Moved In Error Folder : {0}", string.Format(@"{0}\{1}", ErrorFolder, Path.GetFileName(_strLastCopyedFileName))));
                    //        }
                    //    }
                    //    _bCheckError = false;
                    //    ThreadPool.QueueUserWorkItem(LocalDelProc, wf.source_file);
                    //}
                    //else
                    //    _strLastCopyedFileName = string.Empty;
                    /************************************************************************/
                }
            }
            catch (System.Exception ex)
            {
                _Logger.WriteLog(ex);
                throw ex;
            }
            finally
            {
                if (xs != null)
                    xs = null;
                Monitor.Exit(this);
                UpdateJobQ(false);
            }

            return _listJobs.Count;
        }

        public Job GetSelJobInfo(string JobId)
        {
            foreach (Job job in _listJobs)
            {
                if (job.job_id.Trim() == JobId.Trim())
                    return job;
            }
            return null;
        }
#endregion
#region LocalMode - Copy & Del
        void _BackWorker_DoWork(object sender, DoWorkEventArgs e)
        {
            try
            {
                _Logger.WriteLog(string.Format("[Start FileCopy]"));
                if (_Config.LocalFolder.Trim() == string.Empty)
                {
                    _Logger.WriteLog(string.Format("로컬 임시 폴더가 올바르지 않습니다. : {0}",_Config.LocalFolder.Trim()));
                    MessageBox.Show(_dtGrdJobQ, string.Format("로컬 임시 폴더가 올바르지 않습니다.\r\n환경설정을 확인해 주세요.\r\n경로 : {0}", _Config.LocalFolder));
                    return;
                }
                if (!Directory.Exists(_Config.LocalFolder))
                    Directory.CreateDirectory(_Config.LocalFolder);

                string SrcPath = SourcePath.EndsWith("\\") ? SourcePath : SourcePath + "\\";
                string TrgPath = _Config.LocalFolder.EndsWith("\\") ? _Config.LocalFolder : _Config.LocalFolder + "\\";
                SrcPath += SourceFile;
                TrgPath += SourceFile;
             
                for (int idx = 0; idx <= 5; idx++)
                {
                    _Logger.WriteLog(string.Format("[CTI_ID]{0} --> FileCopyStart {1}->{2}", CTI_ID, SrcPath, TrgPath));
                    if (File.Exists(SrcPath))
                    {
                        XCopy.Copy(SrcPath, TrgPath, true, true, (ob, pce) =>
                        {
                            _BackWorker.ReportProgress(pce.ProgressPercentage);
                        });
                        _Logger.WriteLog(string.Format("     [XCOPY_END] {0} -> Exist? : {1}", SrcPath, File.Exists(SrcPath)));
                        _Logger.WriteLog(string.Format("     [XCOPY_END] {0} -> Exist? : {1}", TrgPath, File.Exists(TrgPath)));
                        e.Result = TrgPath;
                        break;
                    }
                    else
                    {
                        _Logger.WriteLog(string.Format("     [XCOPY_FAIL] RetryCont : {0} {1} 파일이 존재하지 않습니다. (10초대기)",idx,SrcPath));
                        Thread.Sleep(1000 * 10);
                    }
                }
            }
            catch (Exception ex)
            {
                _Logger.WriteLog(ex);
            }
        }
        void _BackWorker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            if (OnLocalCopyPercent != null)
                OnLocalCopyPercent(e.ProgressPercentage);
        }
        void _BackWorker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            try
            {
                if (e.Error != null)
                {
                    _Logger.WriteLog(e.Error.Message + "\r\n" + e.Error.StackTrace);
                }
                _Logger.WriteLog(string.Format("[CopyComplete]{0} --> FileCopyExist? : {1}",e.Result, File.Exists((string)e.Result)));
                _strLastCopyedFileName = (string)e.Result;
                for (int idx = _listJobs.Count-1; idx >= 0; idx--)
                {
                    _listJobs[idx].IsLocalCopied = true;
                    _Logger.WriteLog(string.Format("Set CopyState : {0} , {1} ", _listJobs[idx].JOB_Id, _listJobs[idx].IsLocalCopied));
                }
                
                if (OnLocalCopyPercent != null)
                    OnLocalCopyPercent(101);
            }
            catch (System.Exception ex)
            {
                _Logger.WriteLog(ex);
            }
            finally
            {
                UpdateJobQ(false);
                _Logger.WriteLog(string.Format("[End FileCopy]"));
            }
        }

        private void LocalDelProc(object obj)
        {
            _Logger.WriteLog(string.Format("--->[Start FileDelProc]"));
            try
            {
                if (Directory.Exists(_Config.LocalFolder))
                {
                    string[] Files = Directory.GetFiles(_Config.LocalFolder);
                    foreach (string file in Files)
                    {
                        if (file.ToLower() != ((string)obj).ToLower())
                        {
                            _Logger.WriteLog(string.Format("[FileDelProc] {0}", file));
                            File.Delete(file);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                _Logger.WriteLog(ex);
            }
            finally
            {
                _Logger.WriteLog(string.Format("--->[End FileDelProc]"));
                _BackWorker.RunWorkerAsync();
            }
        }
#endregion
    }
}
