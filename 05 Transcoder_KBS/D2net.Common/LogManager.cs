using System;
using System.IO;
using System.Collections;

namespace D2net.Common
{
    /// <summary>
    /// Logging�� ���� �α� ��Ʈ�� Ŭ����
    /// </summary>
    public sealed class LogManager
    {
        /// <summary>
        /// �α׸� ����� Stream�� ����Ʈ�� ������ ��ü
        /// </summary>
        private ArrayList _WriterList;
        /// <summary>
        /// �⺻ ������
        /// </summary>
        public LogManager()
        {
            // System.Diagnostics.Debug.WriteLine("LogManager(" + this.GetHashCode().ToString() + ") : ");
            _WriterList = new ArrayList();
        }

        /// <summary>
        /// �α� �޳����� �޸𸮿��� �����ɶ� ȣ��ȴ�.
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
        /// �α׸� ����� ��� ��Ʈ���� �߰��Ѵ�.
        /// </summary>
        /// <param name="sw">�α׸� ����� ��� ��Ʈ��.</param>
        public void AddWriter(System.IO.StreamWriter sw)
        {
            _WriterList.Add((object)sw);
        }

        /// <summary>
        /// ���� �ý����� ��¥�� �ð��� �α׿� ����Ѵ�. 
        /// </summary>
        public void WriteDateTime()
        {
            WriteDateTime(DateTime.Now);
        }

        /// <summary>
        /// �Էµ� ��¥�� �ð��� �α׿� ����Ѵ�.
        /// </summary>
        /// <param name="dt">�α׿� ����� ��¥�� �ð�.</param>
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
        /// �Էµ� ���ڿ��� �α׿� ����Ѵ�.
        /// </summary>
        /// <param name="txt">�α׿� ����� ���ڿ�.</param>
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
        /// �Էµ� ���ڿ��� �α׿� ����ϰ� ���� ������ ������.
        /// </summary>
        /// <param name="txt">�α׿� ����� ���ڿ�.</param>
        public void WriteLine(string txt)
        {
            // System.Diagnostics.Debug.WriteLine("WriteLine(" + this.GetHashCode().ToString() + ") : " + txt);
            WriteLine(DateTime.Now, txt);
        }

        /// <summary>
        /// �Էµ� ����� �ð�, ���ڿ��� �α׿� ����ϰ� ���� ������ ������.
        /// </summary>
        /// <param name="dt">�α׿� ����� ��¥�� �ð�.</param>
        /// <param name="txt">�α׿� ����� ���ڿ�.</param>
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
        /// �Էµ� ���ڿ��� �α׿� ����ϰ� ���� ������ ������.
        /// </summary>
        /// <param name="ex">�α׿� ����� ����.</param>
        public void WriteException(Exception ex)
        {
            WriteException(DateTime.Now, ex);
        }

        /// <summary>
        /// �Էµ� ����� �ð�, ���ڿ��� �α׿� ����ϰ� ���� ������ ������.
        /// </summary>
        /// <param name="dt">�α׿� ����� ��¥�� �ð�.</param>
        /// <param name="ex">�α׿� ����� ����.</param>
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
