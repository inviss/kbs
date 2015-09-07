using System;
using System.IO;
using System.Collections;

namespace D2net.Common
{
    /// <summary>
    /// Logging을 위한 로그 스트림 클래스
    /// </summary>
    public sealed class LogManager
    {
        /// <summary>
        /// 로그를 출력할 Stream를 리스트로 관리할 객체
        /// </summary>
        private ArrayList _WriterList;
        /// <summary>
        /// 기본 생성자
        /// </summary>
        public LogManager()
        {
            // System.Diagnostics.Debug.WriteLine("LogManager(" + this.GetHashCode().ToString() + ") : ");
            _WriterList = new ArrayList();
        }

        /// <summary>
        /// 로그 메너저가 메모리에서 삭제될때 호출된다.
        /// </summary>
        ~LogManager()
        {
            try
            {
                for (int i = 0; i < _WriterList.Count; i++)
                {
                    if (_WriterList[i] != null)
                    {
                        ((StreamWriter)_WriterList[i]).Close();
                    }
                }
                _WriterList.Clear();
            }
            catch (Exception ex)
            {
                
            }
        }

        /// <summary>
        /// 로그를 출력할 출력 스트림을 추가한다.
        /// </summary>
        /// <param name="sw">로그를 출력할 출력 스트림.</param>
        public void AddWriter(System.IO.StreamWriter sw)
        {
            _WriterList.Add((object)sw);
        }

        /// <summary>
        /// 현재 시스템의 날짜와 시간을 로그에 기록한다. 
        /// </summary>
        public void WriteDateTime()
        {
            WriteDateTime(DateTime.Now);
        }

        /// <summary>
        /// 입력된 날짜와 시간을 로그에 기록한다.
        /// </summary>
        /// <param name="dt">로그에 출력할 날짜와 시간.</param>
        public void WriteDateTime(DateTime dt)
        {
            try
            {
                int i = 0;
                int len = _WriterList.Count;
                StringWriter sw = new StringWriter();
                string msg;

                sw.Write(@"[{0}:{1:D3}]", dt.ToString(), dt.Millisecond);
                msg = sw.ToString();
                // System.Diagnostics.Debug.Write(msg);

                for (i = 0; i < len; i++)
                {
                    ((StreamWriter)_WriterList[i]).WriteLine(msg);
                    ((StreamWriter)_WriterList[i]).Flush();
                }
            }
            catch (Exception ex)
            {
            }
        }

        /// <summary>
        /// 입력된 문자열을 로그에 기록한다.
        /// </summary>
        /// <param name="txt">로그에 출력할 문자열.</param>
        public void Write(string txt)
        {
            try
            {
                int i = 0;
                int len = _WriterList.Count;

                // System.Diagnostics.Debug.Write(txt);

                for (i = 0; i < len; i++)
                {
                    ((StreamWriter)_WriterList[i]).WriteLine(txt);
                    ((StreamWriter)_WriterList[i]).Flush();
                }
            }
            catch (Exception ex)
            {
            }
        }

        /// <summary>
        /// 입력된 문자열을 로그에 기록하고 한줄 밑으로 내린다.
        /// </summary>
        /// <param name="txt">로그에 출력할 문자열.</param>
        public void WriteLine(string txt)
        {
            // System.Diagnostics.Debug.WriteLine("WriteLine(" + this.GetHashCode().ToString() + ") : " + txt);
            WriteLine(DateTime.Now, txt);
        }

        /// <summary>
        /// 입력된 날찌와 시간, 문자열을 로그에 기록하고 한줄 밑으로 내린다.
        /// </summary>
        /// <param name="dt">로그에 출력할 날짜와 시간.</param>
        /// <param name="txt">로그에 출력할 문자열.</param>
        public void WriteLine(DateTime dt, string txt)
        {
            try
            {
                int i = 0;
                int len = _WriterList.Count;
                StringWriter sw = new StringWriter();
                string msg;

                sw.Write(@"[{0}:{1:D3}]    {2}", dt.ToString(), dt.Millisecond, txt);
                msg = sw.ToString();
                sw.Close();
                // System.Diagnostics.Debug.WriteLine(msg);

                for (i = 0; i < len; i++)
                {
                    ((StreamWriter)_WriterList[i]).WriteLine(msg);
                    ((StreamWriter)_WriterList[i]).Flush();
                }
            }
            catch (Exception ex)
            {
            }
        }

        /// <summary>
        /// 입력된 문자열을 로그에 기록하고 한줄 밑으로 내린다.
        /// </summary>
        /// <param name="ex">로그에 출력할 예외.</param>
        public void WriteException(Exception ex)
        {
            WriteException(DateTime.Now, ex);
        }

        /// <summary>
        /// 입력된 날찌와 시간, 문자열을 로그에 기록하고 한줄 밑으로 내린다.
        /// </summary>
        /// <param name="dt">로그에 출력할 날짜와 시간.</param>
        /// <param name="ex">로그에 출력할 예외.</param>
        public void WriteException(DateTime dt, Exception ex)
        {
            try
            {
                int i = 0;
                int len = _WriterList.Count;
                StringWriter sw = new StringWriter();
                string msg;
                Exception iex = null;

                sw.WriteLine("[{0}:{1:D3}]    {2} -> {3}", dt.ToString(), dt.Millisecond, ex.GetType().Name, ex.Message);
                sw.WriteLine("{0}", ex.StackTrace);
                msg = sw.ToString();
                iex = ex.InnerException;
                for (i = 0; (iex != null); i++)
                {
                    sw.WriteLine(" <inner:{0}> {1}", i, ex.Message);
                    sw.WriteLine(" <inner:{0}> {1}", i, ex.StackTrace);
                    iex = iex.InnerException;
                }
                sw.Close();
                // System.Diagnostics.Debug.WriteLine(msg);

                for (i = 0; i < len; i++)
                {
                    ((StreamWriter)_WriterList[i]).Write(msg);
                    ((StreamWriter)_WriterList[i]).Flush();
                }
            }
            catch (Exception ex1)
            {
            }
        }
    }
}
