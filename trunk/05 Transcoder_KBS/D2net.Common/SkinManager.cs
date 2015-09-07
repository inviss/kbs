using System;
using System.IO;
using System.Xml;
using System.Text;
using System.Drawing;
using System.Collections;

namespace D2net.Common.UI
{
	/// <summary>
	/// SkinManager에 대한 요약 설명입니다.
	/// </summary>
	public sealed class SkinManager
	{
        private static Encoding _KSC5601 = Encoding.GetEncoding(949 /* korean */);

        private Hashtable _Map = new Hashtable();

        public SkinManager(string path)
		{
            StreamReader sw = null;
            XmlNodeList Items = null;
            string name = "";
            string type = "";
            string val = "";

            try
            {
                XmlDocument _XDoc = new XmlDocument();
                sw = new StreamReader(path, _KSC5601);
                _XDoc.Load(sw);

                Items = _XDoc.SelectNodes("/CMEDIT_SKIN/ITEM");
                foreach (XmlNode n in Items)
                {
                    name = n.Attributes["name"].Value.ToLower();
                    val = n.Attributes["value"].Value;
                    type = n.Attributes["type"].Value;
                    _Map.Add(name, CreateValue(name, val, type));
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                if (sw != null)
                    sw.Close();
            }
		}

        private object CreateValue(string name, string val, string type)
        {
            object retval = null;

            try
            {
                if (type == "string")
                    retval = val;
                else if (type == "color")
                {
                    string[] rgb = val.Split('/');
                    if (rgb.Length < 3)
                        throw new Exception("색정보를 읽지 못했습니다.");
                    retval = Color.FromArgb(Convert.ToInt32(rgb[0], 10), 
                        Convert.ToInt32(rgb[1], 10),
                        Convert.ToInt32(rgb[2], 10));
                }
                else
                    throw new Exception("지원하지 않는 타입");

                return retval;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public object this [string name]
        {
            get
            {
                return _Map[name.ToLower()];
            }
        }
	}
}
