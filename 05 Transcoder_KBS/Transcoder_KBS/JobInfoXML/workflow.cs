using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Xml;
using System.Xml.Serialization;
using System.Web;

namespace Transcoder_KBS
{
    /*
     * <workflow>
          <source_path>X:\\mp2\T2005-0905\PS-2012233362-01-000\</source_path>
          <source_file>20121218141722.mxf</source_file>
          <target_path>Z:\\mp4\T2005-0905\PS-2012233362-01-000\</target_path>
          <default_opt>-qscale 5</default_opt>
          <cti_Id>268325</cti_Id>
          <job>
            <job_id>147010</job_id>
            <job_kind>audio</job_kind>
            <pro_flid>371</pro_flid>
            <serv_bit>64k</serv_bit>
            <ext>mp4</ext>
            <aud_codec>aac</aud_codec>
            <aud_bit_rate>64</aud_bit_rate>
            <aud_chan>2</aud_chan>
            <aud_s_rate>44</aud_s_rate>
            <target_file>20121218141722_64k.mp4</target_file>
            <qc_yn>N</qc_yn>
          </job>
          <job>
              .
              .
           </job>
        </workflow>
     */
    public class workflow
    {
        [XmlElement]
        public status status{ get; set; }

        [XmlElement]
        public string source_path { get; set; }

        [XmlElement]
        public string source_file { get; set;}

        [XmlElement]
        public string target_path { get; set; }

        [XmlElement]
        public string default_opt { get; set; }

        [XmlElement]
        public string cti_Id { get; set; }

        [XmlElement]
        public string audio_mode { get; set; }   //Job Class에서 사용되기 위한 변수이며, Deserialize를 통해 값을 보관하기 위해 쓰임. (값을 임시 보관)

        [XmlElement]
        public string start_time { get; set; }

        [XmlElement]
        public string end_time { get; set; }
        
        [XmlElement]
        public Job [] job { get; set; }

        //[XmlElement]
        //public ulong duration { get; set; }
    }
}
