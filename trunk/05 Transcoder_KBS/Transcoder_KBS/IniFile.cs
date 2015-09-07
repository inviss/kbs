using System;
using System.Collections.Generic;
using System.Text;
using System.Runtime.InteropServices;

namespace Transcoder_KBS
{
    /// <summary>
    /// Create a New INI file to store or load data
    /// </summary>
    public class IniFile
    {
        public string path;

        [DllImport("kernel32")]
        private static extern long WritePrivateProfileString(string section,
            string key, string val, string filePath);
        [DllImport("kernel32")]
        private static extern int GetPrivateProfileString(string section,
                 string key, string def, StringBuilder retVal,
            int size, string filePath);
        [DllImport("kernel32")]
        private static extern int GetPrivateProfileSectionNames(byte[] returnValue, int size, String filePath);

        /// <summary>
        /// INIFile Constructor.
        /// </summary>
        /// <PARAM name="INIPath"></PARAM>
        public IniFile(string INIPath)
        {
            path = INIPath;
        }
        /// <summary>
        /// Write Data to the INI File
        /// </summary>
        /// <PARAM name="Section"></PARAM>
        /// Section name
        /// <PARAM name="Key"></PARAM>
        /// Key Name
        /// <PARAM name="Value"></PARAM>
        /// Value Name
        public void IniWriteValue(string Section, string Key, string Value)
        {
            WritePrivateProfileString(Section, Key, Value, this.path);
        }

        /// <summary>
        /// Read Data Value From the Ini File
        /// </summary>
        /// <PARAM name="Section"></PARAM>
        /// <PARAM name="Key"></PARAM>
        /// <PARAM name="Path"></PARAM>
        /// <returns></returns>
        public string IniReadValue(string Section, string Key)
        {
            StringBuilder temp = new StringBuilder(255);
            int i = GetPrivateProfileString(Section, Key, "", temp,
                                            255, this.path);
            return temp.ToString();

        }
        public int IniReadValueToInt(string Section, string Key)
        {
            StringBuilder temp = new StringBuilder(255);
            int i = GetPrivateProfileString(Section, Key, "", temp,
                                            255, this.path);
            if (temp.ToString().Trim() == string.Empty)
                return 0;
            return Convert.ToInt32(temp.ToString());

        }

        public string[] IniReadSectionNames()
        {
            string TempSectionNames = "";
            byte[] returnValue = new byte[32768 * 2];
            int retInt = GetPrivateProfileSectionNames(returnValue, 32768 * 2, this.path);
            TempSectionNames = System.Text.Encoding.Default.GetString(returnValue);
            TempSectionNames = TempSectionNames.Remove(TempSectionNames.IndexOf("ENDPROFILE") - 1);
            TempSectionNames = TempSectionNames.Replace("\0", ";");
            TempSectionNames = TempSectionNames.Remove(0, TempSectionNames.IndexOf(";") + 1);

            string[] SectionNaems = TempSectionNames.Split(';');
            return SectionNaems;
        }
    }

}
