using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

using System.Threading;

namespace Transcoder_KBS
{
    /// <summary>
    /// 파일을 복사를 컨트롤하기 위한 클래스 
    /// </summary>
    public class FileCpyThread
    {
#region Variable
        /// <summary>
        /// Source File name
        /// </summary>
        private string _SrcFile = "";
        /// <summary>
        /// Destination File name
        /// </summary>
        private string[] _DestFile = null;
        /// <summary>
        /// Copy state
        /// </summary>
        private bool _Copying = false;
        /// <summary>
        /// Progress Percent
        /// </summary>
        private int _Percent = 0;
        /// <summary>
        /// Thread Process
        /// </summary>
        protected System.Threading.Thread ProcessThread = null;
        /// <summary>
        /// Source File FileStream
        /// </summary>
        protected FileStream srcs = null;
        /// <summary>
        /// Destination File FileStream
        /// </summary>
        protected FileStream[] dests = null;
        /// <summary>
        /// Process Run state
        /// </summary>
        private bool IsProcessRun = false;

        #region CreateEvents
        public delegate void FireEvent(object sender, EventArgs e);
        public event FireEvent CopyProgress;
        #endregion

#endregion

#region Constructor
        /// <summary>
        /// 생성자
        /// </summary>
        /// <param name="log">로그</param>
        public FileCpyThread(/*LogManager log*/)
        {
            /*_Logger = log;*/
            _SrcFile = "";
            _DestFile = null; ;
            _Copying = false;
            _Percent = 0;
        }
        /// <summary>
        /// 생성자
        /// </summary>
        /// <param name="src">원본파일명</param>
        /// <param name="dest">사본파일명</param>
        /// <param name="log">로그</param>
        public FileCpyThread(string src, string[] dest/*, LogManager log*/)
        {
            /*_Logger = log;*/
            _SrcFile = src;
            _DestFile = dest;
            _Copying = false;
            _Percent = 0;
        }
#endregion

#region Method
        public string SrcFile
        {
            get { return _SrcFile; }
            set { _SrcFile = value; }
        }

        public string[] DestFile
        {
            get { return _DestFile; }
            set { _DestFile = value; }
        }

        public bool Copying
        {
            get { if (_Copying) return true; else return false; }
        }
        /// <summary>
        /// 복사률을 반환
        /// </summary>
        public int CopyPercentage
        {
            get
            {
                return _Percent;
            }
        }
        /// <summary>
        /// 파일 정보를 얻는다.
        /// </summary>
        /// <param name="FileName">정보를 얻을 파일명</param>
        /// <returns>FileInfo</returns>
        private FileInfo GetFileInfo(string FileName)
        {
            FileInfo f = new FileInfo(FileName);
            return f;
        }
        /// <summary>
        /// 원본 파일 정보를 얻는다.
        /// </summary>
        /// <returns></returns>
        public FileInfo SourceFileInfo()
        {
            return GetFileInfo(_SrcFile);
        }
        /// <summary>
        /// 사본 파일들의 정보를 얻는다.
        /// </summary>
        /// <param name="index">원하는 사본파일의 인덱스</param>
        /// <returns>FileInfo</returns>
        public FileInfo TargetFileInfo(int index)
        {
            return GetFileInfo(_DestFile[index]);
        }
        /// <summary>
        /// 복사 프로시저
        /// </summary>
        private void CopyProc()
        {
            // 메가 바이트 -> 키로 바이트 [5/24/2012 LJY]
            int nBlock = /*CommInfo.stSetupInfo.nBlock**/  1048576;
            byte[] datas = new byte[nBlock];
            long totlen = 0, totlen2 = 0, readlen = 0;
            int Progress = 0;
            DateTime old = DateTime.Now;
    //        float nKBs = (float)nBlock / /*CommInfo.stSetupInfo.nSpeed*/nBlock;
            try
            {
                srcs = new FileStream(_SrcFile, FileMode.Open, FileAccess.Read);
                dests = new FileStream[_DestFile.Length];
                int ind = 0;
                foreach (string dst in _DestFile)
                {
                    dests[ind] = new FileStream(dst, FileMode.Create);
                    ind++;
                }
            }
            catch (System.Exception ex)
            {
                //CD2N_Log.D2NExceptionLog(System.Reflection.MethodBase.GetCurrentMethod().Name, ex);
                //System.Windows.Forms.MessageBox.Show("등록 할 수 없습니다. 스토리지 경로를 확인해 주세요.", "알림");
               
                if (dests != null)
                {
                    //dests.Close();
                    foreach (FileStream fs in dests)
                    {
                        if (fs != null)
                            fs.Close();
                    }
                }
                dests = null;

                if (srcs != null)
                    srcs.Close();
                srcs = null;
                _Copying = false;
                IsProcessRun = false;
                throw ex;
                //return;
            }

            try
            {
                long nSrt, nEnd;/*, nSleepTm;*/
                totlen = totlen2 = srcs.Length;

                while (totlen2 > 0)// && _StartCopy && _Continue)
                {
                    IsProcessRun = true;
                    if (_Copying == false)
                    {
                        IsProcessRun = false;
                        return;
                    }
                    readlen = srcs.Read(datas, 0, datas.Length);
                    if (readlen <= 0)
                        continue;

                    nSrt = DateTime.Now.Ticks;
                    foreach (FileStream fs in dests)
                    {
                        fs.Write(datas, 0, (int)readlen);
                    }
                    nEnd = DateTime.Now.Ticks;

                    //nSleepTm = (long)(nKBs * 1000) - (((nEnd - nSrt) / 2) / 10000);
                    //// 환경설정 시간동안 대기한다. [4/4/2012 LJY]
                    //if(nSleepTm > 0)    {
                    //    Thread.Sleep((int)nSleepTm);
                    //}
                    
                    totlen2 -= readlen;
                    EventArgs ea = new EventArgs();
                    if (Progress != (int)((totlen - totlen2) / (totlen / 100)))
                    {
                        Progress = (int)((totlen - totlen2) / (totlen / 100));
                    }
                    if ((DateTime.Now.Ticks - old.Ticks) > 50)
                    {
                        old = DateTime.Now;
                        _Percent = Progress;
                        if (CopyProgress != null)
                        {
                            CopyProgress((object)Progress, ea);
                        }
                    }
                }
                //                 EventArgs eaa = new EventArgs();
                //                 _Percent = 100;
                //                 if (CopyProgress != null)
                //                 {
                //                     CopyProgress((object)_Percent, eaa);
                //                 }
            }
            catch (Exception ex)
            {
                //CD2N_Log.D2NExceptionLog(System.Reflection.MethodBase.GetCurrentMethod().Name, ex);
                //System.Windows.Forms.MessageBox.Show("파일을 복사중에 오류가 발생하였습니다.\r\n로그를 확인 해 주세요.", "알림");
                throw ex;
            }
            finally
            {
                if (dests != null)
                {
                    //dests.Close();
                    foreach (FileStream fs in dests)
                    {
                        if(fs != null)
                            fs.Close();
                    }
                }
                dests = null;

                if (srcs != null)
                    srcs.Close();
                srcs = null;
                _Copying = false;
                IsProcessRun = false;
            }
        }
        /// <summary>
        /// 카피 프로시저를 스레드에 넣고 돌린다.
        /// </summary>
        /// <returns></returns>
        public bool Copy()
        {
            _Copying = true;
            if (ProcessThread == null)
            {
                ProcessThread = new System.Threading.Thread(new System.Threading.ThreadStart(CopyProc));
                ProcessThread.Start();
            }
            return true;
        }

        /// <summary>
        /// 스레드를 중단시킨다.
        /// </summary>
        public void Stop()
        {
            try
            {
                _Copying = false;

                if (ProcessThread != null)
                {
                    ProcessThread.Join(1000);
                }
                if (ProcessThread != null)
                {
                    ProcessThread.Abort();
                    ProcessThread = null;
                }

                if (dests != null)
                {
                    //dests.Close();
                    foreach (FileStream fs in dests)
                    {
                        fs.Close();
                    }
                }
                dests = null;

                if (srcs != null)
                    srcs.Close();
                srcs = null;
            }
            catch (Exception ex)
            {
                //CD2N_Log.D2NExceptionLog(System.Reflection.MethodBase.GetCurrentMethod().Name, ex);
                throw ex;
            }
        }

        public void Join(int ms)
        {
            try
            {
                if (ProcessThread != null)
                    ProcessThread.Join(ms);
            }
            catch (System.Exception ex)
            {
                //CD2N_Log.D2NExceptionLog(System.Reflection.MethodBase.GetCurrentMethod().Name, ex);
                throw ex;
            }
        }

        public void Join()
        {
            try
            {
                if (ProcessThread != null)
                    ProcessThread.Join();
            }
            catch (System.Exception ex)
            {
                //CD2N_Log.D2NExceptionLog(System.Reflection.MethodBase.GetCurrentMethod().Name, ex);
                throw ex;
            }
        }
#endregion

#region Event
#endregion
    }
}