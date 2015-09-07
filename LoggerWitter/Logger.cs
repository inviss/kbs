using System;
using System.Collections.Generic;
using System.Text;
using D2net.Common;
using System.IO;
using System.Diagnostics;

namespace LoggerWitter
{
    public class Logger
    {
        private const string _dbgOutWinName = "DebugOutput      ?EJOR, 1998";
        private const string _dbgOutClassName  = "EJOR DebugOut Window";

        private D2net.Common.LogManager _Logger = null;
        private System.IO.StreamWriter _LogWriter = null;

        private string _LogPath = "Logger";
        private string _LogName = "Sample_Log.log";
        private bool _bDbgOutMode = false;
        private string _bDbgOutLogTitle = "";
        private IntPtr _ptrDbgOutHandle = IntPtr.Zero;
        private DateTime _LogFolderDay = DateTime.Today.AddDays(-1);
        private bool _UseConsolOutput = false;

        public string LogFolder
        {
            get { return _LogPath; }
            set { _LogPath = value; }
        }
        public string LogName
        {
            get { return _LogName; }
            set 
            {
                _LogName = value;
                _bDbgOutLogTitle = value;
            }
        }
        public bool UseConsolOutput
        {
            get { return _UseConsolOutput; }
            set { _UseConsolOutput = value; }
        }

        public Logger()
        {

        }
        public Logger(string LogFolder ,string LogName)
        {
           _LogPath = LogFolder;
           _LogName = LogName;
           _bDbgOutLogTitle = LogName;
        }
        public bool DbgOutMode
        {
            get { return _bDbgOutMode; }
            set { _bDbgOutMode = value; }
        }

        private void WriteConsole(string msg)
        {
            if (_UseConsolOutput)
                Console.WriteLine(msg);
        }
        private void WirteLog_Defalut(string Msg, bool Old)
        {
            string path = _LogPath + "\\" + DateTime.Now.ToString("yyyyMMdd");
            if (!Directory.Exists(path))
            {
                Directory.CreateDirectory(path);

                if (_LogWriter != null)
                    _LogWriter.Close();

                _Logger = null;
                _LogWriter = null;

                _LogWriter = new System.IO.StreamWriter(path + "\\" + LogName + ".log", true);
                _LogWriter.AutoFlush = true;

                _Logger = new D2net.Common.LogManager();
                _Logger.AddWriter(_LogWriter);
            }
            else
            {
                if (_Logger == null || _LogWriter == null)
                {
                    _Logger = null;
                    _LogWriter = null;

                    _LogWriter = new System.IO.StreamWriter(path + "\\" + LogName + ".log", true);
                    _LogWriter.AutoFlush = true;

                    _Logger = new D2net.Common.LogManager();
                    _Logger.AddWriter(_LogWriter);
                }
            }
            _Logger.WriteLine(Msg);
        }
        private void WirteLog_Defalut(string Msg)
        {
            string path = string.Format(@"{0}\{1}", _LogPath, DateTime.Today.ToString("yyyyMMdd"));
            if (!Directory.Exists(path)) Directory.CreateDirectory(path);
            if (DateTime.Today != _LogFolderDay)
            {
                if (_LogWriter != null)
                    _LogWriter.Close();

                _LogFolderDay = DateTime.Today;
                _Logger = null;
                _LogWriter = null;

                _LogWriter = new System.IO.StreamWriter(path + "\\" + LogName + ".log", true);
                _LogWriter.AutoFlush = true;

                _Logger = new D2net.Common.LogManager();
                _Logger.AddWriter(_LogWriter);
            }
            else
            {
                if (_Logger == null || _LogWriter == null)
                {
                    _Logger = null;
                    _LogWriter = null;

                    _LogWriter = new System.IO.StreamWriter(path + "\\" + LogName + ".log", true);
                    _LogWriter.AutoFlush = true;

                    _Logger = new D2net.Common.LogManager();
                    _Logger.AddWriter(_LogWriter);
                }
            }
            _Logger.WriteLine(Msg);
        }
        private void DbgOut(string msg)
        {
            if (_bDbgOutMode)
            {
                _ptrDbgOutHandle = (_ptrDbgOutHandle == IntPtr.Zero) ?
                                  Win32API.FindWindow(_dbgOutClassName, _dbgOutWinName) :
                                  _ptrDbgOutHandle;
            }
            else
            {
                _ptrDbgOutHandle = IntPtr.Zero;
            }

            DateTime CurDate = DateTime.Now;
            Win32API.COPYDATASTRUCT CS = new Win32API.COPYDATASTRUCT();
            CS.cbData = 1024;
            CS.dwData = new IntPtr();
            CS.lpData = string.Format("[{0}:{1:D3}]<{2}>    {3}\r\n", CurDate.ToString(), CurDate.Millisecond,_bDbgOutLogTitle, msg);
            Win32API.SendMessage(_ptrDbgOutHandle, Win32API.WM_COPYDATA,CS.dwData,ref CS);
        }

        public void WriteLog(string Msg)
        {
            WirteLog_Defalut(Msg);
            WriteConsole(Msg);
            if (_bDbgOutMode)
                DbgOut(Msg);
        }
        public void WriteLog(Exception ex)
        {
            string strMsg = string.Format("{0}\r\n{1}", ex.Message, ex.StackTrace);
            WirteLog_Defalut(strMsg);
            WriteConsole(strMsg);
            if (_bDbgOutMode)
                DbgOut(strMsg);
        }
        public void WriteLog(Exception ex, string Msg)
        {
            string strMsg = string.Format("{0}\r\n{1}\r\n{1}", Msg, ex.Message, ex.StackTrace);
            WirteLog_Defalut(strMsg);
            WriteConsole(strMsg);
            if (_bDbgOutMode)
                DbgOut(strMsg);
        }
        public void Close()
        {
            if (_LogWriter != null)
                _LogWriter.Close();
            _LogWriter = null;
            _Logger = null;
        }
    }
}
