using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Xml;
using System.Xml.Serialization;

namespace Transcoder_KBS
{
    public class status
    {
        /*
          <workflow>
               <status>
                  <eq_id>   </eq_id>
                  <eq_status>W/B</eq_status>  
                  <job_state>C/E</job_state>
                  <progress>1~100   </progress>
                  <error_cd>300   (정보오류)</error_cd>     // 정보가 부족하다. XML 이상
                  <error_cd>400   (연결오류)</error_cd>     // 스토리지 이상 ??
                  <error_cd>500   (전송오류)</error_cd>     // 로컬 To 스토리지(프로파일 복사)
                  <error_cd>600   (읽기오류)</error_cd>     // 스토리지 To Local(원본 복사) 오류
                  <error_cd>700   (등록오류)</error_cd>     // 트랜스 코딩 오류
                  <error_cd>900   (기타오류)</error_cd>     // 트랜스 코딩 오류
               </status>
            </workflow>
         */
        [XmlElement]
        public string eq_id { get; set; }
        
        // I[idle], B[Busy]
        [XmlElement]
        public string eq_state { get; set; }
        
        [XmlElement]
        public string error_cd { get; set; }

        [XmlElement]
        public string job_state { get; set; }

        [XmlElement]
        public int progress { get; set; }

        [XmlElement]
        public string job_id { get; set; }
    }

    public class QulityCheck
    {
        /*
         * <workflow>
             * <content>
                 * <job_id>795268</job_id>
                 * <job_gb>A</job_gb>
                 * <job_state>C</job_state>
                 * <file_size>43361767</file_size>
                 * <frm_per_sec>29.97</frm_per_sec>
                 * <job_err_code></job_err_code>
             * </content>
             * <report>         //리포트는 사용안해도됨 //2014.12.12
                 * <qc_success>Y</qc_success>
                 * <qc_rate>-1.#IO</qc_rate>
                 * <qc_video_err>N</qc_video_err>
                 * <qc_audio_err>N</qc_audio_err>
                 * <qc_path>Z:\\mp4\T2000-0110\PS-2014241613-01-000\f3dde70a-f737-4cba-83cc-446eadae8e5d_96k.mp3_qc.log</qc_path>
             * </report>
         * </workflow>
         */
        [XmlElement]
        public Content content { get; set; }
    }
    public class Content
    {
        [XmlElement]
        public string job_id { get; set; }
        [XmlElement]
        public string job_gb { get; set; }
        [XmlElement]
        public string job_state { get; set; }
        [XmlElement]
        public string file_size { get; set; }
        [XmlElement]
        public string frm_per_sec { get; set; }
        [XmlElement]
        public string job_err_code { get; set; }
    }
}
