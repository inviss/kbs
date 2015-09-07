using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Transcoder_KBS
{
    public class ConfigInfo
    {
        public string Equip_ID;
        public int Thread_Count;
        public bool CopyMode;
        public string LocalFolder;
        public int MinimumFileSize;
        public int MinimumPercent;
        public int DeleteThreadOccurrenceCycle;
        public int DeleteCycleForErroredFiles;

        public string WebServiceURL;
        public int JobRequestCycle; //sec
        
    }
}
