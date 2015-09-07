using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Xml;
using System.Xml.Serialization;

namespace Transcoder_KBS
{
    /*
     * <job>
            <job_id>147012</job_id>
            <job_kind>video</job_kind>
            <pro_flid>391</pro_flid>
            <serv_bit>350k</serv_bit>
            <ext>mp4</ext>
            <vdo_codec>h264</vdo_codec>
            <vdo_bit_rate>280</vdo_bit_rate>
            <vdo_hori>480</vdo_hori>
            <vdo_vert>272</vdo_vert>
            <vdo_f_s>29.97</vdo_f_s>
            <vdo_sync>0</vdo_sync>
            <aud_codec>aac</aud_codec>
            <aud_bit_rate>64</aud_bit_rate>
            <aud_chan>2</aud_chan>
            <aud_s_rate>48</aud_s_rate>
            <target_file>20121218141722_350k_HD.mp4</target_file>
            <qc_yn>Y</qc_yn>
          </job>
     */
//     public enum JOb_KIND
//     {
//         JOB_VIDEO = 0,
//         JOB_AUDIO
//     }
    public class Job
    {
        //[XmlElement]
        //public string job_id { get; set; }
        
        //[XmlElement]
        //public string job_kind { get; set; }

        //[XmlElement]
        //public int pro_flid { get; set; }

        //[XmlElement]
        //public string serv_bit { get; set; }

        //[XmlElement]
        //public string ext { get; set; }

        //[XmlElement]
        //public string vdo_codec { get; set; }

        //[XmlElement]
        //public string vdo_bit_rate { get; set; }

        //[XmlElement]
        //public int vdo_hori { get; set; }

        //[XmlElement]
        //public int vdo_vert { get; set; }

        //[XmlElement]
        //public string vdo_f_s { get; set; }

        //[XmlElement]
        //public int vdo_sync { get; set; }

        //[XmlElement]
        //public string aud_codec { get; set; }

        //[XmlElement]
        //public string aud_bit_rate { get; set; }

        //[XmlElement]
        //public int aud_chan { get; set; }

        //[XmlElement]
        //public string audio_mode { get; set; }  //Workflow에 있는 값을 Job에 넣기 위해 쓰임. 정말 사용되는 변수

        //[XmlElement]
        //public string aud_s_rate { get; set; }

        //[XmlElement]
        //public string target_file { get; set; }
                
        //[XmlElement]
        //public string qc_yn { get; set; }
        /************************************************************************/
        public string job_id;
        public string job_kind;
        public int pro_flid;
        public string serv_bit;
        public string ext;
        public string vdo_codec;
        public string vdo_bit_rate;
        public int vdo_hori;
        public int vdo_vert;
        public string vdo_f_s;
        public int vdo_sync;
        public string aud_codec;
        public string aud_bit_rate;
        public int aud_chan;
        public string audio_mode;  //Workflow에 있는 값을 Job에 넣기 위해 쓰임. 정말 사용되는 변수
        public string aud_s_rate;
        public string target_file;
        public string qc_yn;
        /************************************************************************/
        public bool UpdateResult;           //CMS 3회 등록 실패 여부 판단하기 위한 변수
        public bool IsLocalCopied = false;  //로컬카피 모드시 로컬 카피 완료 상태여부
        /************************************************************************/
        public string JOB_Id { get; set; }
        public string JOB_Ext { get; set; }
        public string JOB_Res { get; set; }
        public string JOB_V_Codec { get; set; }
        public string JOB_V_Bitrate { get; set; }
        public string JOB_A_Codec { get; set; }
        public string JOB_A_Bitrate { get; set; }
        public string JOB_A_Sampling { get; set; }
        /************************************************************************/
        public void SetPropertyData()
        {
            JOB_Id = this.job_id;
            JOB_Ext = this.ext.ToUpper();
            JOB_Res = string.Format("{0} x {1}", this.vdo_hori, this.vdo_vert);
            JOB_V_Codec = this.vdo_codec;
            JOB_V_Bitrate = string.Format("{0} kb",this.vdo_bit_rate);
            JOB_A_Codec = this.aud_codec;
            JOB_A_Bitrate = string.Format("{0} kb", this.aud_bit_rate);
            JOB_A_Sampling = string.Format("{0} kb", this.aud_s_rate);
        }
    }
}
