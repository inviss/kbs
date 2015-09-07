using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Threading;

namespace D2net.Common.File
{

    public class FileCtrl
    {
        private string _SrcFile = "";
        private string[] _DestFile = null;
        private bool _Copying = false;
        private int _Percent = 0;
        protected System.Threading.Thread ProcessThread = null;
        protected FileStream srcs = null;
        protected FileStream[] dests = null;
        private LogManager _Logger = null;
        private bool IsProcessRun = false;

        #region CreateEvents
        public delegate void FireEvent(object sender, EventArgs e);
        public event FireEvent CopyProgress;
        #endregion

        public FileCtrl(LogManager log)
        {
            _Logger = log;
            _SrcFile = "";
            _DestFile = null; ;
            _Copying = false;
            _Percent = 0;


        }

        public FileCtrl(string src, string[] dest, LogManager log)
        {
            _Logger = log;
            _SrcFile = src;
            _DestFile = dest;
            _Copying = false;
            _Percent = 0;

        }

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

        public int CopyPercentage
        {
            get
            {
                return _Percent;
            }
        }

        private FileInfo GetFileInfo(string FileName)
        {
            FileInfo f = new FileInfo(FileName);
            return f;
        }

        public FileInfo SourceFileInfo()
        {
            return GetFileInfo(_SrcFile);
        }

        public FileInfo TargetFileInfo(int index)
        {
            return GetFileInfo(_DestFile[index]);
        }

        private void CopyProc()
        {
            byte[] datas = new byte[1048576];
            long totlen = 0, totlen2 = 0, readlen = 0;
            int Progress = 0;
            DateTime old = DateTime.Now;

            try
            {
                srcs = new FileStream(_SrcFile, FileMode.Open, FileAccess.Read);
                //dests = new FileStream(_DestFile, FileMode.Create);
                //dests = new FileStream[_DestFile.Length];
                dests = new FileStream[_DestFile.Length];
                int ind = 0;
                foreach (string dst in _DestFile)
                {
                    dests[ind] = new FileStream(dst, FileMode.Create);
                    ind++;
                }

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
                    foreach (FileStream fs in dests)
                    {
                        fs.Write(datas, 0, (int)readlen);
                    }
                    //dests.Write(datas, 0, (int)readlen);
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
                EventArgs eaa = new EventArgs();
                _Percent = 100;
                if (CopyProgress != null)
                {
                    CopyProgress((object)_Percent, eaa);
                }
            }
            catch (Exception ex)
            {
                if (_Logger != null)
                {
                    _Logger.WriteLine(DateTime.Now, ex.Message + "\n" + ex.StackTrace);
                }
            }
            finally
            {
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
                _Copying = false;
                IsProcessRun = false;
            }
        }

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
                if (_Logger != null)
                {
                    _Logger.WriteLine(DateTime.Now, ex.Message + "\n" + ex.StackTrace);
                }
            }
        }
    }
}
