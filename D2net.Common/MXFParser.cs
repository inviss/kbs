using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using System.IO;

namespace D2net.Common.Xml
{
    public class MXFParser
    {
        private XmlDocument XDoc = null;
        private XmlNode Root = null;
        private string _FileName = "";

        private string _Duration = "";
        private string _VideoCodec = "";
        private string _FPS = "";
        private string _VidResX = "";
        private string _VidResY = "";
        private string _AudioCodec = "";
        private string _AudioChnNum = "";
        private string _SttPos = "";
        private string _EndPos = "";
        private string _AsperctRatio = "";
        private string _FrmPerSec = "";

        private static readonly Encoding _KSC5601 = Encoding.GetEncoding(949 /* korean */);

        public MXFParser(FileInfo FileName)
        {
            _FileName = FileName.FullName;
            StreamReader sr = new StreamReader(FileName.FullName, _KSC5601);
            try
            {
                _Duration = "";
                _VideoCodec = "";
                _FPS = "";
                _VidResX = "";
                _VidResY = "";
                _AudioCodec = "";
                _AudioChnNum = "";
                _SttPos = "";
                _EndPos = "";
                _AsperctRatio = "";
                _FrmPerSec = "";

                XDoc = new XmlDocument();
                XDoc.Load(sr);
                sr.Close();

                Root = XDoc.FirstChild;
                XmlNodeList list = Root.ChildNodes;

                XMLAnalyzer(XDoc.ChildNodes);


            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                sr.Close();
            }
        }

        public MXFParser(string Input)
        {
            //_FileName = FileName;
            //StreamReader sr = new StreamReader(FileName, _KSC5601);
            try
            {
                _Duration = "";
                _VideoCodec = "";
                _FPS = "";
                _VidResX = "";
                _VidResY = "";
                _AudioCodec = "";
                _AudioChnNum = "";
                _SttPos = "";
                _EndPos = "";
                _AsperctRatio = "";
                _FrmPerSec = "";

                XDoc = new XmlDocument();
                XDoc.LoadXml(Input);
                //sr.Close();

                Root = XDoc.FirstChild;
                XmlNodeList list = Root.ChildNodes;

                XMLAnalyzer(XDoc.ChildNodes);


            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                //sr.Close();
            }
        }

        public string Duration { get { return _Duration; } }
        public string VideoCodec { get { return _VideoCodec; } }
        public string FPS { get { return _FPS; } }
        public string XResolution { get { return _VidResX; } }
        public string YResolution { get { return _VidResY; } }
        public string AudioCodec { get { return _AudioCodec; } }
        public string AudioChnNum { get { return _AudioChnNum; } }
        public string StartPosition { get { return _SttPos; } }
        public string EndPosition { get { return _EndPos; } }
        public string AsperctRatio { get { return _AsperctRatio; } }
        public string FrmPerSec { get { return _FrmPerSec; } }


        private void TestFunc(XmlNodeList list, StreamWriter sWriter)
        {
            foreach (XmlNode node in list)
            {
                string Temp = "";
                Temp += "NodeName : " + node.Name;
                if (sWriter != null)
                {
                    sWriter.WriteLine(Temp);
                }
                int i = 0;
                XmlAttributeCollection Attrs = node.Attributes;
                if (Attrs != null)
                {
                    foreach (XmlAttribute attr in Attrs)
                    {
                        Temp = attr.Name + " = ";
                        Temp += attr.Value;
                        if (sWriter != null)
                        {
                            sWriter.WriteLine(Temp);
                        }
                        if (attr.Name == "value")
                        {
                            string temp = "";


                            switch (node.Name)
                            {
                                case "Duration":
                                    _Duration = attr.Value;
                                    break;
                                case "LtcChange":
                                    if (_SttPos == "")
                                    {
                                        temp = attr.Value;
                                        /*i = temp.Length;
                                        ff = temp[0].ToString() + temp[1].ToString();
                                        ss = temp[2].ToString() + temp[3].ToString();
                                        mm = temp[4].ToString() + temp[5].ToString();
                                        hh = temp[6].ToString() + temp[7].ToString();
                                        int tmp = Convert.ToInt32(ff);
                                        int frm = tmp - 40;
                                        _SttPos = hh + ":" + mm + ":" + ss + ";" + frm.ToString("00");*/
                                        _SttPos = temp;
                                        break;
                                    }
                                    else
                                    {
                                        temp = attr.Value;
                                        /*i = temp.Length;
                                        ff = temp[0].ToString() + temp[1].ToString();
                                        ss = temp[2].ToString() + temp[3].ToString();
                                        mm = temp[4].ToString() + temp[5].ToString();
                                        hh = temp[6].ToString() + temp[7].ToString();
                                        int tmp = Convert.ToInt32(ff);
                                        int frm = tmp - 40;
                                        _EndPos = hh + ":" + mm + ":" + ss + ";" + frm.ToString("00");*/
                                        _EndPos = temp;
                                        break;
                                    }
                                default:
                                    break;
                            }
                        }
                        else
                        {
                            switch (attr.Name)
                            {
                                case "tcFps":
                                    _FPS = attr.Value;
                                    break;
                                case "pixel":
                                    _VidResX = attr.Value;
                                    break;
                                case "numOfVerticalLine":
                                    _VidResY = attr.Value;
                                    break;
                                case "audioCodec":
                                    _AudioCodec = attr.Value;
                                    break;
                                case "numOfChannel":
                                    _AudioChnNum = attr.Value;
                                    break;
                                case "videoCodec":
                                    _VideoCodec = attr.Value;
                                    break;
                                case "aspectRatio":
                                    _AsperctRatio = attr.Value;
                                    break;
                                case "formatFps":
                                    _FrmPerSec = attr.Value;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }


                }
                TestFunc(node.ChildNodes, sWriter);
                if (sWriter != null)
                {
                    sWriter.WriteLine("--------------------------------------------------------------------------------");
                }
            }
            //
            if (_FrmPerSec == "")
            { return; }
            if (_FrmPerSec == "59.94i" || _FrmPerSec == "60i")
            {

                _SttPos = GetTimeCode(_SttPos, true);
                _EndPos = GetTimeCode(_EndPos, true);
                if (_FrmPerSec == "59.94i")
                {
                    _SttPos = _SttPos.Replace(":", ";");
                    _EndPos = _EndPos.Replace(":", ";");
                }
                else
                {
                    _SttPos = _SttPos.Replace(";", ":");
                    _EndPos = _EndPos.Replace(";", ":");
                }
            }
            else
            {
                _SttPos = GetTimeCode(_SttPos, false);
                _EndPos = GetTimeCode(_EndPos, false);
                //_SttPos = _SttPos.Replace(";", ":");
                //_EndPos = _EndPos.Replace(";", ":");
            }

        }

        private string GetTimeCode(string temp, bool Sub40)
        {
            if (temp.Length > 8)
            { return temp; }
            string hh = "";
            string mm = "";
            string ss = "";
            string ff = "";
            string retval = "";
            int i = 0;
            i = temp.Length;
            if (i > 11)
            {
                return temp;
            }
            ff = temp[0].ToString() + temp[1].ToString();
            ss = temp[2].ToString() + temp[3].ToString();
            mm = temp[4].ToString() + temp[5].ToString();
            hh = temp[6].ToString() + temp[7].ToString();
            int tmp = Convert.ToInt32(ff);
            int frm = tmp - 40;
            if (Sub40)
            {
                retval = hh + ":" + mm + ":" + ss + ";" + frm.ToString("00");
            }
            else
            {
                retval = hh + ":" + mm + ":" + ss + ";" + ff;
            }
            return retval;
        }

        private void XMLAnalyzer(XmlNodeList list)
        {
            DateTime dt = DateTime.Now;
            string FN = _FileName + ".Txt";
            StreamWriter sw = null;
            //sw = new StreamWriter(FN);
            TestFunc(XDoc.ChildNodes, sw);
            if (sw != null)
            {
                sw.Close();
                sw = null;
            }
        }
    }
}
