using System;
using System.Collections.Generic;
using System.Threading;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;
using System.Collections;
using LoggerWitter;
using D2net.Common;

namespace Transcoder_KBS
{
    public enum StatusErrorCode
    {
        NO_ERROR = 0,
        INFO_ERROR = 300,
        CONECT_ERROR = 400,
        TRANSFER_ERROR = 500,
        READ_ERROR = 600,
        REGIST_ERROR = 700,
        ETC_ERROR = 900,
    }
    class Transcoder
    {
#region Varialbes
        /************************************************************************/
        // for databinding
        public string Source_path;
        public string Source_file;

        public string Target_path;
        public string Target_file;
        public string Start_time;
        public string End_time;

        public string TC_ID { get; set; }
        public string Job_Id { get; set; }
        public string Profile_Info { get; set; }
        public string Work_Status { get; set; }
        public string Current_Progress{get;set;}
        private string _default_opt = string.Empty;
        private string _AudioMode = string.Empty;
        /************************************************************************/
        private WorkAgent _workAgent = null;
        private Thread _threadProc = null;
        public UInt16 Is_Job_Proc = 1;

        private DataGridView _dtGrdTC = null;
        private Job _job = null;
        public bool _IsRunning = false;

        private string _tcID = "null";
        string strSource = string.Empty;
        string strTarget = string.Empty;
        /************************************************************************/
        private string _FFMpegFolder = string.Format(@"{0}\ffmpeg", Application.StartupPath);
        private Process _prs = null;
        private ConfigInfo _Config = null;
        Logger _Logger = null;
        /************************************************************************/
        private string _Duration = string.Empty;
        private string _CurDuraion = string.Empty;
        private int _AudioCount = -1;
        private string _VisualImpaired = string.Empty;
        /************************************************************************/
        int _DurationTypeSec = 0;
        int _CurTimeTypeSec = 0;
        private int _oldPercent = -1;
        private string _TcId = string.Empty;
        /************************************************************************/
#endregion
#region Constructor
        public Transcoder(WorkAgent w, DataGridView dtGrdTC,ConfigInfo config, string tc_ID)
        {
            _workAgent = w;
            _dtGrdTC = dtGrdTC;
            TC_ID = tc_ID;
            
            _Config = config;
            _threadProc = new Thread(new ThreadStart(Proc));
            _threadProc.Start();
            _Logger = new Logger(string.Format(@"{0}\Log", Application.StartupPath), string.Format("Transcorder_{0}", tc_ID));
            _TcId = string.Format("{0}_{1}", _Config.Equip_ID, tc_ID);
            _Logger.WriteLog("****************Initialized*******************");
        }
        public string GetTcEquipId()
        { return _tcID;}
#endregion
#region Method
        public void UpdateConfig(ConfigInfo config)
        {
            _Config = config;
        }
        public void ReleaseTC()
        {
            try
            {
                if (_prs != null && !_prs.HasExited)
                {
                    _prs.Close();
                }
                _prs = null;
                
                if (_job != null)
                    _workAgent.UpdateJobErrorSuccessed(_job, StatusErrorCode.ETC_ERROR,_TcId);

                Is_Job_Proc = 0;
                if (_threadProc.IsAlive)
                    _threadProc.Abort();
                _threadProc = null;
                _Logger.WriteLog("|---> ReleaseTC");
            }
            catch (Exception ex) 
            {
                _Logger.WriteLog(ex);
            }
        }
        private void Proc()
        {
            while (Is_Job_Proc > 0)
            {
                try
                {
                    Thread.Sleep(2000);
                    _job = _workAgent.GetJob(_TcId);

                    if (_job != null)
                    {
                        _IsRunning = true;
                        _Logger.WriteLog(string.Format("--->Got a Job {0}", _job.job_id));
                        Source_path = _workAgent.SourcePath.EndsWith("\\") ? _workAgent.SourcePath : _workAgent.SourcePath + "\\";
                        Source_file = _workAgent.SourceFile;
                        Target_path = _workAgent.TargetPath.EndsWith("\\") ? _workAgent.TargetPath : _workAgent.TargetPath + "\\";
                        Target_file = _job.target_file;
                        Start_time = _workAgent.Start_TC;
                        End_time = _workAgent.End_TC;
                        _default_opt = _workAgent.Default_Opt;

                        Job_Id = _job.job_id;
                        Profile_Info = _job.target_file;
                        if (_Config.CopyMode)
                        {
                            strSource = string.Format(@"{0}\{1}", _Config.LocalFolder , Source_file);
                            strTarget = string.Format(@"{0}\{1}", _Config.LocalFolder, Target_file);
                        }
                        else
                        {
                            strSource = Source_path + Source_file;
                            strTarget = Target_path + Target_file;
                        }
                        
                        UpdateTC(false);
                        StartTranscording();
                    }
                }
                catch (ThreadAbortException ex)
                {
                    throw ex;
                }
                catch (Exception ex)
                {
                    _Logger.WriteLog(ex);
                }
            }
        }
        private void UpdateTC(bool metadataChanged)
        {
            try
            {
                _dtGrdTC.Invoke((MethodInvoker)(() =>
                {
                     ((BindingSource)_dtGrdTC.DataSource).ResetBindings(false);
                    _dtGrdTC.Refresh();
                    
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

        private void StartTranscording()
        {
            try
            {
                _Logger.WriteLog(string.Format("******************Start Transcording JobID :[{0}]******************",_job.job_id));
                /************************************************************************/
                if (!File.Exists(strSource))
                {
                    _Logger.WriteLog(string.Format("작업 대상 파일이 존재하지 않습니다. [Src:{0}]->[Trg:{1}]", strSource, strTarget));
                    Work_Status = "READ_ERROR";
                    _workAgent.RetryUpdateJobErrorSuccessed(_job, StatusErrorCode.READ_ERROR, _TcId);
                    return;
                }
                /************************************************************************/
                Work_Status = "Extract MediaInfo";
                FFprobeInfo probeInfo = new FFprobeInfo(_FFMpegFolder, strSource, ref _Logger);
                probeInfo.ExtractMetadata(out _Duration, out _AudioCount, out _VisualImpaired);
                /************************************************************************/
                StartFFmpeg();
                /************************************************************************/
                Work_Status = "Completed";
            }
            catch (Exception ex)
            {
                _Logger.WriteLog(ex);
                Work_Status = "ETC_ERROR";
                _workAgent.RetryUpdateJobErrorSuccessed(_job, StatusErrorCode.ETC_ERROR, _TcId);
            }
            finally
            {
                _IsRunning = false;
                _Logger.WriteLog("******************Completed Transcording******************");
                UpdateTC(true);
            }
        }
        private bool IsTransferSuccess(string beCampareFile ,string beComparedDuration,int Percent)
        {
            bool retVal = false;
            try
            {
                string duration = string.Empty;
                int AudioCount = -1;

                FileInfo Fi = new FileInfo(beCampareFile);
                if (!Fi.Exists || Fi.Length < _Config.MinimumFileSize * 1024 * 1024 || Percent<_Config.MinimumPercent)
                {
                    _Logger.WriteLog(string.Format("[Validation Check] {0} ,Exist={1},Size={2}/{3}(최소사이즈[{4}MB]),Percent={5}", beCampareFile, Fi.Exists, Fi.Length, _Config.MinimumFileSize * 1024 * 1024, _Config.MinimumFileSize, Percent));
                    _Logger.WriteLog(string.Format("[Validation Check-Fail]"));
                    return false;
                }
                else
                {
                    FFprobeInfo probeinfo = new FFprobeInfo(_FFMpegFolder, beCampareFile, ref _Logger);
                    probeinfo.ExtractMetadata(out _Duration, out AudioCount, out _VisualImpaired);

                    int DesToSec = 0;
                    FFTimeToDateTime(_CurDuraion, out DesToSec);
                    _Logger.WriteLog(string.Format("[Validation Check] {0} ,Exist={1},Size={2}/{3}(최소사이즈[{4}MB]),Percent={5}", beCampareFile, Fi.Exists, Fi.Length, _Config.MinimumFileSize * 1024 * 1024, _Config.MinimumFileSize, Percent));
                    _Logger.WriteLog(string.Format("[Validation Check-OK] Src Duration : {0} Des Duration : {1}", beComparedDuration, _CurDuraion));
                    return true;
                }
            }
            catch (Exception ex)
            {
                _Logger.WriteLog(ex);
                retVal = false;
            }
            return retVal;
        }

        private void StartFFmpeg()
        {
            try
            {
                _Logger.WriteLog("---------------------FFMPEG---------------------");
                if (_VisualImpaired != string.Empty)
                {
                    _Logger.WriteLog(string.Format("-----Visual Impaired Media {0}" , _VisualImpaired));
                }
                Work_Status = "Transcoding";
                if (Start_time != null && Start_time.Trim() != string.Empty && End_time != null && End_time.Trim() != string.Empty)
                {
                    /************************************************************************/
                    if (End_time != null && End_time.Trim() != string.Empty)
                    {
                        int tempDuration = Convert.ToInt32(End_time) - Convert.ToInt32(Start_time);
                        int Hour = 0;
                        int Min = 0;
                        int Sec = 0;
                        if (tempDuration > 60 * 60)
                        {
                            Hour = Convert.ToInt32(tempDuration / (60 * 60));
                            tempDuration = tempDuration % (60 * 60);
                        }
                        if (tempDuration > 60)
                        {
                            Min = Convert.ToInt32(tempDuration / (60));
                            tempDuration = tempDuration % (60);
                        }
                        Sec = Convert.ToInt32(tempDuration);

                        _Duration = string.Format("{0:D2}:{1:D2}:{2:D2}.00", Hour, Min, Sec);
                        _Logger.WriteLog("<TrimMode> Duration Force Changed " + _Duration);
                    }
                    /************************************************************************/
                }
                FFTimeToDateTime(_Duration, out _DurationTypeSec);
                /************************************************************************/
                _prs = new Process();
                _prs.StartInfo.FileName = string.Format("\"{0}\\ffmpeg.exe\"", _FFMpegFolder);
                _prs.StartInfo.Arguments = MakeFFMpegArg(_job);
                _prs.StartInfo.UseShellExecute = false;
                _prs.StartInfo.RedirectStandardError = true;
                _prs.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                _prs.StartInfo.CreateNoWindow = true;
                _prs.EnableRaisingEvents = true;               
                /************************************************************************/
                if (!Directory.Exists(Path.GetDirectoryName(strTarget)))
                    Directory.CreateDirectory(Path.GetDirectoryName(strTarget));
                if (File.Exists(strTarget))
                    File.Delete(strTarget);
                /************************************************************************/
                try
                {
                    _prs.Start();
                    _prs.BeginErrorReadLine();
                    _prs.ErrorDataReceived += new DataReceivedEventHandler(_prs_FFmpegErrorDataReceived);
                    _prs.WaitForExit();
                    _prs = null;
                }
                catch (Exception ex)
                {
                    _Logger.WriteLog(string.Format("[FFMpeg Prs Excepted] {0}\r\n{1}", ex.Message, ex.StackTrace));
                    Current_Progress = "-1";
                    Work_Status = "ETC_ERROR";
                    _workAgent.RetryUpdateJobErrorSuccessed(_job, StatusErrorCode.ETC_ERROR, _TcId);
                    UpdateTC(false);
                    return;
                }
                /************************************************************************/
                _workAgent.UpdateJobState(_job, Convert.ToInt32(Current_Progress), _TcId);
                if (_Config.CopyMode)
                {
                    string TmpMP4 = string.Format(@"{0}\{1}", _Config.LocalFolder, Target_file);
                    string TargetMP4 = string.Format(@"{0}\{1}", Target_path, Target_file);

                    if (IsTransferSuccess(TmpMP4, _Duration,Convert.ToInt32(Current_Progress)))
                    {
                        Current_Progress = "100";
                        if (_job.vdo_codec != null && _job.vdo_codec.ToUpper() == "H264")
                            SetMP4BOX(TmpMP4);
                        /************************************************************************/
                        bool test = true;
                        _Logger.WriteLog(string.Format("FileMove : {0} -> {1}", TmpMP4, TargetMP4));
                        if (File.Exists(TmpMP4))
                        {
                            if (!Directory.Exists(Path.GetDirectoryName(TargetMP4)))
                                Directory.CreateDirectory(Path.GetDirectoryName(TargetMP4));
                            if (File.Exists(TargetMP4))
                                File.Delete(TargetMP4);
                            int CopyCount = 0;
                            while (true)
                            {
                                // CopyError 관련 Retry 로직 추가함. [8/12/2015 sehui]
                                try
                                {
                                    CopyCount++;
                                    Work_Status = "Copying To Storage";
                                    File.Move(TmpMP4, TargetMP4);
                                    break;
                                }
                                catch (System.Exception ex)
                                {
                                    Thread.Sleep(3000);
                                    if (CopyCount <= 5)
                                    {
                                        _Logger.WriteLog(string.Format("[Profile Move Excepted-RetryCount : {0}] {1}\r\n{2}",CopyCount, ex.Message, ex.StackTrace));
                                    }
                                    else
                                    {
                                        _Logger.WriteLog(string.Format("[Profile Move Excepted] {0}\r\n{1}", ex.Message, ex.StackTrace));
                                        Current_Progress = "-1";
                                        Work_Status = "ETC_ERROR";
                                        _workAgent.RetryUpdateJobErrorSuccessed(_job, StatusErrorCode.ETC_ERROR, _TcId);
                                        UpdateTC(false);
                                        return;
                                    }
                                }
                            }
                        }
                        _workAgent.RetryUpdateJobErrorSuccessed(_job, StatusErrorCode.NO_ERROR, _TcId);
                    }
                    else
                    {
                        Current_Progress = "-1";
                        Work_Status = "ETC_ERROR";
                        _workAgent.RetryUpdateJobErrorSuccessed(_job, StatusErrorCode.ETC_ERROR, _TcId);
                    }
                }
                else
                {
                    if (IsTransferSuccess(strTarget, _Duration,Convert.ToInt32(Current_Progress)))
                    {
                        Current_Progress = "100";
                        if (_job.vdo_codec != null && _job.vdo_codec.ToUpper() == "H264")
                            SetMP4BOX(strTarget);
                        /************************************************************************/
                        _workAgent.RetryUpdateJobErrorSuccessed(_job, StatusErrorCode.NO_ERROR, _TcId);
                    }
                    else
                    {
                        Current_Progress = "-1";
                        Work_Status = "ETC_ERROR";
                        _workAgent.RetryUpdateJobErrorSuccessed(_job, StatusErrorCode.ETC_ERROR, _TcId);
                    }
                }
                UpdateTC(false);
            }
            catch (System.Exception ex)
            {
                _Logger.WriteLog(ex);
                throw ex;
            }
        }
        private string MakeFFMpegArg(Job job)
        {
            string sJobArgs = string.Empty;
            string strAudioMap = string.Empty;
           
            //-threads 12 -i "X:\KBS_sample\T2009-0165_유희열스케치북\AudioMuxTestFile.mxf" -map 0:v:0 -c:v libx264 -pix_fmt yuv420p 
            //-b 64k -minrate 64k -maxrate 64k -bufsize 32k -async -1 -sws_flags lanczos -vf "yadif=0:1-1, scale=640:360" -r 29.97 -g 15 
            //-filter_complex "[0:1][0:2][0:3][0:4][0:5][0:6][0:7][0:8]amerge=inputs=8[aout]" -map "[aout]" -codec:a aac 
            //-strict experimental -ar 48000 -ac 2 "W:\Test.mp4"

            sJobArgs += string.Format("-threads {0} -i \"{1}\" ", _Config.Thread_Count, strSource);
            if (Start_time != null &&Start_time.Trim() != string.Empty && End_time!=null && End_time.Trim() != string.Empty)
            {
                sJobArgs += string.Format("-ss {0} -t {1} ", Start_time,(int.Parse(End_time) - int.Parse(Start_time)));
            }
            if (job.job_kind == "video")
            {

                string strVCodec = string.Empty;
                if (job.vdo_codec.ToUpper() == "H264")
                    strVCodec = "libx264";
                else if (job.vdo_codec.ToUpper() == "MPEG2")
                    strVCodec = "mpeg2video";
                /************************************************************************/
                if (_AudioCount > 1)
                {
                    sJobArgs += string.Format("-map 0:v:0 -c:v {0} ", strVCodec);
                }
                sJobArgs += string.Format("-pix_fmt yuv420p -b:v {0}k ", job.vdo_bit_rate, int.Parse(job.vdo_bit_rate) / 2);
                sJobArgs += string.Format("-sws_flags lanczos -vf \"yadif=0:1-1, scale={0}:{1}\" -r {2} -vsync {3} -g 15 ", job.vdo_hori, job.vdo_vert, job.vdo_f_s, job.vdo_sync);
            }
            /*********************************Audio Codec Set***************************************/
            string strACodec = string.Empty;
            strACodec = job.aud_codec;
            if (strACodec.ToUpper() == "MP3")
                strACodec = "libmp3lame";
            /*********************************Audio SampleRate Set***************************************/
            int nASamplerate = int.Parse(job.aud_s_rate);
            if (nASamplerate == 44)
                nASamplerate = 441 * 100;
            else
                nASamplerate = nASamplerate * 1000;
            /*********************************Audio Muxing Stream Set***************************************/
            int nAudioMurgeCount = 0;
            for (int idx = 1; idx <= _AudioCount; idx++)
            {
                if (_job.audio_mode.Trim() == "02")
                    if (!(idx == 1 || idx == 2)) 
                        continue;
                if (_job.audio_mode.Trim() == "04")
                    if (!(idx == 3 || idx == 4)) 
                        continue;
                if (_VisualImpaired.Contains(idx.ToString()))
                    continue;
                strAudioMap += string.Format("[0:{0}]", idx);
                nAudioMurgeCount++;
                //if (_job.audio_mode.Trim() == "02")
                //{
                //    strAudioMap += string.Format("[0:1][0:2]");
                //    break;
                //}
                //else if (_job.audio_mode.Trim() == "04")
                //{
                //    strAudioMap += string.Format("[0:3][0:4]");
                //    break;
                //}
                //else
                //{
                //    if(!_VisualImpaired.Contains(idx.ToString()))
                //        strAudioMap += string.Format("[0:{0}]", idx);
                //}
            }
            if (nAudioMurgeCount <=1)
            {
                // Visual Impaired로 인해서 머지할 오디오가 1개인경우에는 Murge필터가 적용되지 않아서 이미 추가된 머지값을 한번더 추가한다.
                strAudioMap += strAudioMap;
                nAudioMurgeCount++;
            }
            /*********************************Audio Muxing Filter Set***************************************/
            if (_AudioCount > 1)
            {
                sJobArgs += string.Format("-filter_complex \"{0}amerge=inputs={1}[aout]\" -map \"[aout]\" ", strAudioMap, nAudioMurgeCount);
                //if (_job.audio_mode.Trim() == "02")
                //{
                //    sJobArgs += string.Format("-filter_complex \"{0}amerge=inputs={1}[aout]\" -map \"[aout]\" ", strAudioMap, 2);
                //}
                //else if (_job.audio_mode.Trim() == "04")
                //{
                //    sJobArgs += string.Format("-filter_complex \"{0}amerge=inputs={1}[aout]\" -map \"[aout]\" ", strAudioMap, 2);
                //}
                //else
                //{
                //    sJobArgs += string.Format("-filter_complex \"{0}amerge=inputs={1}[aout]\" -map \"[aout]\" ", strAudioMap, _AudioCount);
                //}
            }
            sJobArgs += string.Format("-c:a {0} -ac {1} -ar {2} -strict experimental ", strACodec, job.aud_chan, nASamplerate);            
            sJobArgs += string.Format("\"{0}\"", strTarget);
            /************************************************************************/
            _Logger.WriteLog(string.Format("[Make Arg] -> {0} , [AudioMode] ->{1}",strSource,_job.audio_mode));
            _Logger.WriteLog(string.Format("Arg: {0}", sJobArgs));
            return sJobArgs;
        }
        private void parsingFFmPeggMsg(string Msg)
        {
            try
            {
                if (Msg == null || Msg.Trim() == string.Empty || Msg.StartsWith("[mxf") || Msg.IndexOf(":") < 0)
                {
                    return;
                }
                if (Msg.StartsWith("frame") || Msg.StartsWith("size"))
                {
                    string frame = Msg.Remove(Msg.IndexOf("bitrate"));
                    frame = frame.Remove(0, frame.IndexOf("time=") + 5).Trim();
                    FFTimeToDateTime(frame, out _CurTimeTypeSec);
                    _CurDuraion = frame;
                    Current_Progress = _CurTimeTypeSec * 100 / _DurationTypeSec + "";
                    if (_oldPercent != Convert.ToInt32(Current_Progress))
                    {
                        _oldPercent = Convert.ToInt32(Current_Progress);
                        if (_oldPercent % 2 == 0)
                            _workAgent.UpdateJobState(_job, Convert.ToInt32(Current_Progress), _TcId);
                        if (Convert.ToInt32(Current_Progress) > 100)
                            _Logger.WriteLog(string.Format("[Percent overed] {0}%  Cur:{1}, Dur:{2} MSG:{3}", Current_Progress,_CurTimeTypeSec,_DurationTypeSec, Msg));
                    }
                    UpdateTC(false);
                }
                else
                {
                    _Logger.WriteLog(Msg);
                }
            }
            catch (Exception ex)
            {
                _Logger.WriteLog(ex);
            }
        }
        private DateTime FFTimeToDateTime(string fftime, out int ToSec)
        {

            string[] sliceTime = fftime.Split(":"[0]);
            DateTime Dt = new DateTime(DateTime.Today.Year, DateTime.Today.Month, DateTime.Today.Day, 
                                        Convert.ToInt32(sliceTime[0]),
                                        Convert.ToInt32(sliceTime[1]),
                                        (sliceTime[2].Contains("."))? Convert.ToInt32(sliceTime[2].Remove(2)):Convert.ToInt32(sliceTime[2]));
            ToSec = Dt.Hour * 3600 + Dt.Minute * 60 + Dt.Second;
            return Dt;
        }
        void _prs_FFmpegErrorDataReceived(object sender, DataReceivedEventArgs e)
        {
            parsingFFmPeggMsg(e.Data);
        }

        private void SetMP4BOX(string File)
        {
            try
            {
                Work_Status = "MP4BOX Running";
                Process MP4BOX = new Process();
                ProcessStartInfo psi = new ProcessStartInfo();
                psi.FileName = string.Format(@"{0}\MP4Box.exe", _FFMpegFolder);
                psi.Arguments = string.Format("-hint {0}", File);
                psi.WindowStyle = ProcessWindowStyle.Hidden;
                psi.CreateNoWindow = true;
                MP4BOX.StartInfo = psi;
                MP4BOX.Start();
                MP4BOX.WaitForExit();
             
                if (MP4BOX.HasExited == false)
                {
                    _Logger.WriteLog("MP4 BOX - Process Force Killed");
                    MP4BOX.Kill();
                    MP4BOX = null;
                }
            }
            catch (System.Exception ex)
            {
                _Logger.WriteLog(ex);
                Console.WriteLine(ex.ToString());
            }
        }
#endregion
    }
    class FFprobeInfo
    {
#region Variables
        private List<string> _FFProbeMetaLabel = null;
        private Hashtable _FFProbeMeta = null;
        private string _FFMpegFolder = string.Empty;
        private string _srcFile = string.Empty;
        private Logger _Logger = null;

        //private MediaInfo _Minfo = null;
        //private bool _UsePorbe = false;
#endregion
#region Property
        public string GetDuration 
        {
            get
            {
                try
                {
                    if (_FFProbeMeta != null && _FFProbeMeta["Duration"] != null)
                    {
                        if (_FFProbeMeta["company_name"] != null && ((string)_FFProbeMeta["company_name"]).ToUpper().Contains("OMNEON"))
                        {
                            //return _FFProbeMeta["precharge"].ToString();
                            TimeCode TC = TimeCode.CreateTimeCode(TCFormat.NTSC_Drop, TCMode.TimeCode, Convert.ToInt32(_FFProbeMeta["precharge"]));
                            if (TC.Frame < 1)
                                return _FFProbeMeta["Duration"].ToString();
                            return TC.ToString();
                        }
                        else
                        {
                            return _FFProbeMeta["Duration"].ToString();
                        }
                    }
                }
                catch (Exception ex)
                {
                    _Logger.WriteLog(ex);
                }
                
                return "00:00:00:00";
            }
        }
        public int GetAudioStreamCount
        {
            get
            {
                if (_FFProbeMeta != null && _FFProbeMeta["AudioStreamCount"] != null)
                    return Convert.ToInt32(_FFProbeMeta["AudioStreamCount"]);
                else
                    return -1;
            }
        }
        public string GetVisualImppaired
        {
            get
            {
                if (_FFProbeMeta != null && _FFProbeMeta["visual impaired"] != null)
                    return Convert.ToString(_FFProbeMeta["visual impaired"]);
                else
                    return "";
            }
        }
#endregion
#region Constructor
        public FFprobeInfo(string ffprobePath, string SourceFile,ref Logger logger)
        {
            _FFMpegFolder = ffprobePath;
            _srcFile = SourceFile;

            _FFProbeMetaLabel = new List<string>();
            _FFProbeMetaLabel.Add("uid");
            _FFProbeMetaLabel.Add("generation_uid");
            _FFProbeMetaLabel.Add("company_name");
            _FFProbeMetaLabel.Add("product_name");
            _FFProbeMetaLabel.Add("product_version");
            _FFProbeMetaLabel.Add("product_uid");
            _FFProbeMetaLabel.Add("modification_date");
            _FFProbeMetaLabel.Add("timecode");
            _FFProbeMetaLabel.Add("duration");
            _FFProbeMetaLabel.Add("stream");

            _FFProbeMeta = new Hashtable();
            _Logger = logger;
        }
#endregion
#region Method
        public void ExtractMetadata(out string Duraion, out int AudioStreamCount, out string VisualImpaired)
        {
            StartFFprobe(_srcFile);
            Duraion = GetDuration;
            AudioStreamCount = GetAudioStreamCount;
            VisualImpaired = GetVisualImppaired;
            _Logger.WriteLog(string.Format("Extract MediaInfo Duration:{0}/ AudiocStreamCount:{1}\r\nFile :{2}", Duraion, AudioStreamCount, _srcFile));
        }
        private void StartFFprobe(string srcFile)
        {
            int Result = -1;
            try
            {
                _Logger.WriteLog("--------------------FFPROBE------------------------");
                _FFProbeMeta.Clear();
                /************************************************************************/
                Process prs = new Process();
                prs.StartInfo.FileName = string.Format(@"{0}\ffprobe.exe", _FFMpegFolder);
                prs.StartInfo.Arguments = string.Format(@"{0}", srcFile);
                prs.StartInfo.UseShellExecute = false;
                prs.StartInfo.RedirectStandardError = true;
                prs.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                prs.StartInfo.CreateNoWindow = true;
                prs.EnableRaisingEvents = true;
                /************************************************************************/
                prs.Start();
                prs.BeginErrorReadLine();
                prs.ErrorDataReceived += new DataReceivedEventHandler(prs_FFProbeErrorDataReceived);
                prs.WaitForExit();

                string result = string.Empty;
                IDictionaryEnumerator IDC = _FFProbeMeta.GetEnumerator();
                while (IDC.MoveNext())
                {
                    result += string.Format("{0} : {1}\r\n", IDC.Key, IDC.Value);
                }
            }
            catch (System.Exception ex)
            {
                _Logger.WriteLog(ex);
                throw ex;
            }
        }
        private void parsingFFprobeMsg(string Msg)
        {
            #region FFProbe Output Sample
            /************************************************************************/
            /* 
                [mxf @ 0072f5e0] inconsistent FooterPartition value: 32139984384 != 79372800
                Input #0, mxf, from 'X:\KBS_sample\T2009-0165_?좏씗?댁뒪耳移섎턿\20141011014525
                .mxf':
                  Metadata:
                    uid             : 70431900-4542-05c1-f017-080046020125
                    generation_uid  : 70431900-4542-05c2-f017-080046020125
                    company_name    : Matrox Electronic
                    product_name    : DSX
                    product_version : 3.0
                    product_uid     : 060e2b34-0401-0103-0e06-012002030300
                    modification_date: 2014-10-11 02:06:35
                    timecode        : 00:00:00;00
                  Duration: 01:11:10.77, start: 0.000000, bitrate: 60209 kb/s
                    Stream #0:0: Video: mpeg2video (4:2:2), yuv422p(tv, bt709), 1920x1080 [SAR 1
                :1 DAR 16:9], 50000 kb/s, 29.97 fps, 29.97 tbr, 29.97 tbn, 59.94 tbc
                    Stream #0:1: Audio: pcm_s24le, 48000 Hz, 1 channels, s32 (24 bit), 1152 kb/s
                    Stream #0:2: Audio: pcm_s24le, 48000 Hz, 1 channels, s32 (24 bit), 1152 kb/s
                    Stream #0:3: Audio: pcm_s24le, 48000 Hz, 1 channels, s32 (24 bit), 1152 kb/s
                    Stream #0:4: Audio: pcm_s24le, 48000 Hz, 1 channels, s32 (24 bit), 1152 kb/s
                    Stream #0:5: Audio: pcm_s24le, 48000 Hz, 1 channels, s32 (24 bit), 1152 kb/s
                    Stream #0:6: Audio: pcm_s24le, 48000 Hz, 1 channels, s32 (24 bit), 1152 kb/s
                    Stream #0:7: Audio: pcm_s24le, 48000 Hz, 1 channels, s32 (24 bit), 1152 kb/s
                    Stream #0:8: Audio: pcm_s24le, 48000 Hz, 1 channels, s32 (24 bit), 1152 kb/s                                                                     */
            /************************************************************************/
            #endregion
            if (Msg == null || Msg.Trim() == string.Empty)
                return;
            else if (Msg.StartsWith("[mxf"))
            {
                if (Msg.Contains("TemporalOffset"))
                {
                    string PreCharge = string.Empty;
                    PreCharge = Msg.Remove(0,Msg.LastIndexOf("=")+2);
                    PreCharge = PreCharge.Remove(PreCharge.LastIndexOf(","));
                    _FFProbeMeta.Add("precharge", PreCharge.Trim());
                }
                return;
            }
            else if (Msg.IndexOf(":") < 0)
            {
                return;
            }
            
            string MetaLabel = Msg.Remove(Msg.IndexOf(":"));
            string MetaData = Msg.Remove(0, Msg.IndexOf(":") + 1);
            foreach (string strCmp in _FFProbeMetaLabel)
            {
                if (MetaLabel.Trim().ToLower().StartsWith(strCmp))
                {
                    if (strCmp == "duration")
                    {
                        string[] info = Msg.Split(","[0]);
                        foreach (string data in info)
                        {
                            string dataLabel = data.Remove(data.IndexOf(":"));
                            string dataData = data.Remove(0, data.IndexOf(":") + 1);
                            _FFProbeMeta.Add(dataLabel.Trim(), dataData.Trim());
                        }
                    }
                    else if (strCmp == "stream")
                    {
                        string[] Stream = Msg.Split(":"[0]);
                        if (Stream[2].Trim().ToLower() == "video")
                        {
                            //string[] info = Stream[3].Split(","[0]);
                            //{

                            //}
                            _FFProbeMeta.Add("VideoStreamCount", 1);
                        }
                        else if (Stream[2].Trim().ToLower() == "audio")
                        {
                            if (_FFProbeMeta.ContainsKey("AudioStreamCount"))
                                _FFProbeMeta["AudioStreamCount"] = (int)_FFProbeMeta["AudioStreamCount"] + 1;
                            else
                                _FFProbeMeta.Add("AudioStreamCount", 1);

                            if (Msg.Contains("visual impaired"))
                            {
                                if (_FFProbeMeta.Contains("visual impaired"))
                                    _FFProbeMeta["visual impaired"] = string.Format("{0}{1}",_FFProbeMeta["visual impaired"],Stream[1].Remove(1));
                                else
                                    _FFProbeMeta.Add("visual impaired" ,Stream[1].Remove(1));
                            }
                        }
                    }
                    else
                    {
                        _FFProbeMeta.Add(strCmp, MetaData.Trim());
                        return;
                    }
                }
            }
        }
        private void prs_FFProbeErrorDataReceived(object sender, DataReceivedEventArgs e)
        {
            _Logger.WriteLog(e.Data);
            parsingFFprobeMsg(e.Data);
        }
#endregion
    }
}
