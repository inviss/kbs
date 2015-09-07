using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Xml;
using System.Collections;
using System.Web;

namespace D2net.Common.Xml
{
    public class ProduceXMLCtrlClass
    {
        /*
         * 
         <produce>
          <pgg_req_yn></pgg_req_yn>						<!-- program add y/n 			-->
          <sub_pgg_req_yn></sub_pgg_req_yn>		<!-- sub program add y/n 	-->
          <ct_req_yn>Y</ct_req_yn>						<!-- content add y/n 			-->
          <metadata><![CDATA[
  	        <tb_ct>
			        
  	        </tb_ct>
	        ]]></metadata>
        </produce>
         */
        private XmlDocument XDoc = null;
        private XmlNode nproduce = null;
        //private XmlNode npgg_req_yn = null;
        //private XmlNode nsub_pgg_req_yn = null;
        private XmlNode nct_req_yn = null;
        private XmlNode nmetadata = null;
        private XmlNode DataSec = null;
        private ContentsXml _Data = null;
        //XmlNode tb_ct = null;

        private static readonly Encoding _KSC5601 = Encoding.GetEncoding(949 /* korean */);

        public ProduceXMLCtrlClass()
        {
            XDoc = new XmlDocument();

            nproduce = XDoc.CreateElement("produce");
            nproduce.InnerText = "";
            XDoc.AppendChild(nproduce);

            /*npgg_req_yn = XDoc.CreateElement("pgg_req_yn");
            npgg_req_yn.InnerText = "";
            nproduce.AppendChild(npgg_req_yn);

            nsub_pgg_req_yn = XDoc.CreateElement("sub_pgg_req_yn");
            nsub_pgg_req_yn.InnerText = "";
            nproduce.AppendChild(nsub_pgg_req_yn);
            */
            nct_req_yn = XDoc.CreateElement("ct_req_yn");
            nct_req_yn.InnerText = "";
            nproduce.AppendChild(nct_req_yn);

            nmetadata = XDoc.CreateElement("metadata");
            nmetadata.InnerText = "";
            nproduce.AppendChild(nmetadata);

            DataSec = XDoc.CreateCDataSection("");
            DataSec.InnerText = "";
            nmetadata.AppendChild(DataSec);

            _Data = new ContentsXml();
        }

        public ProduceXMLCtrlClass(string FileNmae)
        {
            XDoc = new XmlDocument();
            StreamReader sr = new StreamReader(FileNmae, _KSC5601);
            XDoc.Load(sr);
            sr.Close();
            nproduce = XDoc.FirstChild;
            //npgg_req_yn = nproduce.SelectSingleNode("pgg_req_yn");
            //nsub_pgg_req_yn = nproduce.SelectSingleNode("sub_pgg_req_yn");
            nct_req_yn = nproduce.SelectSingleNode("ct_req_yn");
            nmetadata = nproduce.SelectSingleNode("metadata");
        }

        /*public bool pgg_req_yn
        {
            
            get {
                if (npgg_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (npgg_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (npgg_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }
                
            }
            set {
                if (npgg_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { npgg_req_yn.InnerText = "Y"; }
                else
                { npgg_req_yn.InnerText = "N"; }
            }
        }*/

        /*public bool sub_pgg_req_yn
        {

            get
            {
                if (nsub_pgg_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (nsub_pgg_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (nsub_pgg_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (nsub_pgg_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { nsub_pgg_req_yn.InnerText = "Y"; }
                else
                { nsub_pgg_req_yn.InnerText = "N"; }
            }
        }*/

        public bool ct_req_yn
        {

            get
            {
                if (nct_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (nct_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (nct_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (nct_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { nct_req_yn.InnerText = "Y"; }
                else
                { nct_req_yn.InnerText = "N"; }
            }
        }

        public ContentsXml metadata
        {
            get
            {
                if (DataSec == null)
                { throw new Exception("노드가 비어있습니다."); }
                return _Data;
            }
            set
            {
                _Data = value;
                DataSec.InnerText = ((ContentsXml)value).WholeText;
            }
        }

        public string WholeText
        {
            get
            {
                if (XDoc == null)
                {
                    throw new Exception("내부 값이 없습니다");
                }
                return XDoc.OuterXml;
                //return writer.ToString();
            }
            set
            {
                XDoc.RemoveAll();
                XDoc = null;

                XDoc = new XmlDocument();
                XDoc.LoadXml(value);
                nproduce = XDoc.FirstChild;
                //npgg_req_yn = nproduce.SelectSingleNode("pgg_req_yn");
                //nsub_pgg_req_yn = nproduce.SelectSingleNode("sub_pgg_req_yn");
                nct_req_yn = nproduce.SelectSingleNode("ct_req_yn");
                nmetadata = nproduce.SelectSingleNode("metadata");

            }
        }

        public void Save(string FileNmae)
        {
            try
            {
                if (XDoc == null)
                {
                    throw new Exception("XML이 NULL입니다.");
                }

                XDoc.Save(FileNmae);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

    }

    public class Returnkey
    {
        private XmlDocument XDoc = null;
        private XmlNode nreturnkey = null;
        private XmlNode nct_id = null;
        private XmlNode ncti_id = null;

        public Returnkey(string strReturnkey)
        {
            XDoc = new XmlDocument();
            XDoc.LoadXml(strReturnkey);

            nreturnkey = XDoc.FirstChild;
            switch (nreturnkey.FirstChild.Name)
            {
                case "ct_id":
                    nct_id = nreturnkey.FirstChild;
                    break;
                case "cti_id":
                    ncti_id = nreturnkey.FirstChild;
                    break;
            }
        }

        public string ct_id { get { return nct_id.InnerText; } }
        public string cti_id { get { return ncti_id.InnerText; } }
    }

    public class RequestClass
    {
        private XmlDocument XDoc = null;
        private XmlNode nrequestKey = null;
        private XmlNode nmda_dvc_id = null;
        private XmlNode nct_id = null;

        public RequestClass()
        {
            XDoc = new XmlDocument();

            nrequestKey = XDoc.CreateElement("requestKey");
            nrequestKey.InnerText = "";
            XDoc.AppendChild(nrequestKey);

            nct_id = XDoc.CreateElement("ct_id");
            nct_id.InnerText = "";
            nrequestKey.AppendChild(nct_id);

            nmda_dvc_id = XDoc.CreateElement("mda_dvc_id");
            nmda_dvc_id.InnerText = "";
            nrequestKey.AppendChild(nmda_dvc_id);
        }

        public string ct_id { set { nct_id.InnerText = value; } get { return nct_id.InnerText; } }
        public string mda_dvc_id { set { nmda_dvc_id.InnerText = value; } get { return nmda_dvc_id.InnerText; } }
        public string WholeText { get { return XDoc.InnerXml; } }

    }

    public class ReturnkeyXMLCtrlClass
    {
        /*
         <returnkey>
	          <pgg_req_yn></pgg_req_yn>						<!-- program add y/n 			-->
              <sub_pgg_req_yn></sub_pgg_req_yn>		<!-- sub program add y/n  -->
              <ct_req_yn>Y</ct_req_yn>						<!-- content add y/n 			-->
              <key>
                <ct_id></ct_id>
                <cti_id></cti_id>
              </key>
        </returnkey>
         */
        private XmlDocument XDoc = null;
        private XmlNode nreturnkey = null;
        private XmlNode npgg_req_yn = null;
        private XmlNode nsub_pgg_req_yn = null;
        private XmlNode nct_req_yn = null;
        private XmlNode nkey = null;
        private XmlNode nct_id = null;
        private XmlNode ncti_id = null;


        private static readonly Encoding _KSC5601 = Encoding.GetEncoding(949 /* korean */);

        public ReturnkeyXMLCtrlClass()
        {
            XDoc = new XmlDocument();

            nreturnkey = XDoc.CreateElement("returnkey");
            nreturnkey.InnerText = "";
            XDoc.AppendChild(nreturnkey);

            npgg_req_yn = XDoc.CreateElement("pgg_req_yn");
            npgg_req_yn.InnerText = "";
            nreturnkey.AppendChild(npgg_req_yn);

            nsub_pgg_req_yn = XDoc.CreateElement("sub_pgg_req_yn");
            nsub_pgg_req_yn.InnerText = "";
            nreturnkey.AppendChild(nsub_pgg_req_yn);

            nct_req_yn = XDoc.CreateElement("ct_req_yn");
            nct_req_yn.InnerText = "";
            nreturnkey.AppendChild(nct_req_yn);

            nkey = XDoc.CreateElement("key");
            nkey.InnerText = "";
            nreturnkey.AppendChild(nkey);

            nct_id = XDoc.CreateElement("ct_id");
            nct_id.InnerText = "";
            nkey.AppendChild(nct_id);

            ncti_id = XDoc.CreateElement("cti_id");
            ncti_id.InnerText = "";
            nkey.AppendChild(ncti_id);
        }

        public ReturnkeyXMLCtrlClass(string FileNmae)
        {
            XDoc = new XmlDocument();
            StreamReader sr = new StreamReader(FileNmae, _KSC5601);
            XDoc.Load(sr);
            sr.Close();
            nreturnkey = XDoc.FirstChild;
            npgg_req_yn = nreturnkey.SelectSingleNode("pgg_req_yn");
            nsub_pgg_req_yn = nreturnkey.SelectSingleNode("sub_pgg_req_yn");
            nct_req_yn = nreturnkey.SelectSingleNode("ct_req_yn");
            nkey = nreturnkey.SelectSingleNode("key");
            nct_id = nkey.SelectSingleNode("ct_id");
            ncti_id = nkey.SelectSingleNode("cti_id");
        }

        public bool pgg_req_yn
        {

            get
            {
                if (npgg_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (npgg_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (npgg_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (npgg_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { npgg_req_yn.InnerText = "Y"; }
                else
                { npgg_req_yn.InnerText = "N"; }
            }
        }

        public bool sub_pgg_req_yn
        {

            get
            {
                if (nsub_pgg_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (nsub_pgg_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (nsub_pgg_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (nsub_pgg_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { nsub_pgg_req_yn.InnerText = "Y"; }
                else
                { nsub_pgg_req_yn.InnerText = "N"; }
            }
        }

        public bool ct_req_yn
        {

            get
            {
                if (nct_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (nct_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (nct_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (nct_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { nct_req_yn.InnerText = "Y"; }
                else
                { nct_req_yn.InnerText = "N"; }
            }
        }

        public string ct_id
        {
            get
            {
                if (nct_id == null)
                { throw new Exception("노드가 비어있습니다."); }
                string str = nct_id.InnerText;
                return str;
            }
            set
            {
                nct_id.InnerText = value;
            }
        }

        public string cti_id
        {
            get
            {
                if (ncti_id == null)
                { throw new Exception("노드가 비어있습니다."); }
                string str = ncti_id.InnerText;
                return str;
            }
            set
            {
                ncti_id.InnerText = value;
            }
        }

        public void Save(string FileNmae)
        {
            try
            {
                if (XDoc == null)
                {
                    throw new Exception("XML이 NULL입니다.");
                }

                XDoc.Save(FileNmae);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

    }

    public class WorkflowXMLCtrlClass
    {
        /*
         <workflow>
          <ing_req_yn></ing_req_yn>			<!-- ingest y/n 			-->
          <arch_req_yn></arch_req_yn>		<!-- archive y/n 			-->
          <rsto_req_yn></rsto_req_yn>		<!-- restore y/n 			-->
          <trcd_req_yn>Y</trcd_req_yn>	<!-- transcoding y/n 	-->
          <kfrm_req_yn></kfrm_req_yn>		<!-- keyframe y/n 		-->
          <metadata><![CDATA[
	        ]]></metadata>
        </workflow>
         */
        private XmlDocument XDoc = null;
        private XmlNode nworkflow = null;
        private XmlNode ntrsf_req_yn = null; //munsam
        private XmlNode ning_req_yn = null;
        private XmlNode narch_req_yn = null;
        private XmlNode nrsto_req_yn = null;
        private XmlNode ntrcd_req_yn = null;
        private XmlNode nkfrm_req_yn = null;
        private XmlNode nwmv_req_yn = null;
        private XmlNode nmetadata = null;
        private XmlNode DataSec = null;



        tb_cti_Class ctb_cti = null;

        private static readonly Encoding _KSC5601 = Encoding.GetEncoding(949 /* korean */);

        public WorkflowXMLCtrlClass()
        {
            XDoc = new XmlDocument();

            nworkflow = XDoc.CreateElement("workflow");
            nworkflow.InnerText = "";
            XDoc.AppendChild(nworkflow);

            //munsam
            ntrsf_req_yn = XDoc.CreateElement("trsf_req_yn");
            ntrsf_req_yn.InnerText = "";
            nworkflow.AppendChild(ntrsf_req_yn);

            ning_req_yn = XDoc.CreateElement("ing_req_yn");
            ning_req_yn.InnerText = "";
            nworkflow.AppendChild(ning_req_yn);

            narch_req_yn = XDoc.CreateElement("arch_req_yn");
            narch_req_yn.InnerText = "";
            nworkflow.AppendChild(narch_req_yn);

            nrsto_req_yn = XDoc.CreateElement("rsto_req_yn");
            nrsto_req_yn.InnerText = "";
            nworkflow.AppendChild(nrsto_req_yn);

            ntrcd_req_yn = XDoc.CreateElement("trcd_req_yn");
            ntrcd_req_yn.InnerText = "";
            nworkflow.AppendChild(ntrcd_req_yn);

            nkfrm_req_yn = XDoc.CreateElement("kfrm_req_yn");
            nkfrm_req_yn.InnerText = "";
            nworkflow.AppendChild(nkfrm_req_yn);

            nwmv_req_yn = XDoc.CreateElement("wmv_req_yn");
            nwmv_req_yn.InnerText = "";
            nworkflow.AppendChild(nwmv_req_yn);

            nmetadata = XDoc.CreateElement("metadata");
            nmetadata.InnerText = "";
            nworkflow.AppendChild(nmetadata);

            DataSec = XDoc.CreateCDataSection("");
            ctb_cti = new tb_cti_Class();
            DataSec.InnerText = ctb_cti.WholeText;
            nmetadata.AppendChild(DataSec);


        }

        public WorkflowXMLCtrlClass(string FileName)
        {
            XDoc = new XmlDocument();
            StreamReader sr = new StreamReader(FileName, _KSC5601);
            XDoc.Load(sr);
            sr.Close();
            nworkflow = XDoc.SelectSingleNode("workflow");
            ntrsf_req_yn = nworkflow.SelectSingleNode("trsf_req_yn"); //munsam
            ning_req_yn = nworkflow.SelectSingleNode("ing_req_yn");
            narch_req_yn = nworkflow.SelectSingleNode("arch_req_yn");
            nrsto_req_yn = nworkflow.SelectSingleNode("rsto_req_yn");
            ntrcd_req_yn = nworkflow.SelectSingleNode("trcd_req_yn");
            nkfrm_req_yn = nworkflow.SelectSingleNode("kfrm_req_yn");
            nwmv_req_yn = nworkflow.SelectSingleNode("wmv_req_yn");
            nmetadata = nworkflow.SelectSingleNode("metadata");
            ctb_cti = new tb_cti_Class(nmetadata.InnerText);
        }

        //munsam
        public bool trsf_req_yn
        {

            get
            {
                if (ntrsf_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (ntrsf_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (ntrsf_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (ntrsf_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { ntrsf_req_yn.InnerText = "Y"; }
                else
                { ntrsf_req_yn.InnerText = "N"; }
            }
        }

        public bool ing_req_yn
        {

            get
            {
                if (ning_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (ning_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (ning_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (ning_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { ning_req_yn.InnerText = "Y"; }
                else
                { ning_req_yn.InnerText = "N"; }
            }
        }

        public bool arch_req_yn
        {

            get
            {
                if (narch_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (narch_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (narch_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (narch_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { narch_req_yn.InnerText = "Y"; }
                else
                { narch_req_yn.InnerText = "N"; }
            }
        }

        public bool rsto_req_yn
        {

            get
            {
                if (nrsto_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (nrsto_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (nrsto_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (nrsto_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { nrsto_req_yn.InnerText = "Y"; }
                else
                { nrsto_req_yn.InnerText = "N"; }
            }
        }

        public bool trcd_req_yn
        {

            get
            {
                if (ntrcd_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (ntrcd_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (ntrcd_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (ntrcd_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { ntrcd_req_yn.InnerText = "Y"; }
                else
                { ntrcd_req_yn.InnerText = "N"; }
            }
        }

        public bool kfrm_req_yn
        {

            get
            {
                if (nkfrm_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (nkfrm_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (nkfrm_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (nkfrm_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { nkfrm_req_yn.InnerText = "Y"; }
                else
                { nkfrm_req_yn.InnerText = "N"; }
            }
        }

        public bool wmv_req_yn
        {

            get
            {
                if (nwmv_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (nwmv_req_yn.InnerText.ToUpper() == "Y")
                {
                    return true;
                }
                else if (nwmv_req_yn.InnerText.ToUpper() == "N")
                {
                    return false;
                }
                else { throw new Exception("노드값이 정상이 아닙니다."); }

            }
            set
            {
                if (nwmv_req_yn == null)
                {
                    throw new Exception("노드가 비어있습니다.");
                }
                if (value)
                { nwmv_req_yn.InnerText = "Y"; }
                else
                { nwmv_req_yn.InnerText = "N"; }
            }
        }

        public tb_cti_Class metadata
        {
            get
            {
                tb_cti_Class ret = new tb_cti_Class(DataSec.InnerText);
                return ret;
            }

            set
            {
                string TempText = value.WholeText;
                this.DataSec.InnerText = TempText;
            }
        }

        public RequestClass Reqmetadata
        {
            set
            {
                string TempText = value.WholeText;
                this.DataSec.InnerText = TempText;
            }

        }

        public string WholeString
        {
            get
            {
                return XDoc.InnerXml;
            }
        }

        public void Save(string FileName)
        {
            if (XDoc == null)
            {
                throw new Exception("내부값이 없습니다.");
            }
            XDoc.Save(FileName);
        }
    }


    public class tb_cti_Class
    {
        /*<tb_cti>
             <ct_id>M0601102</ct_id>	<!-- content id 			-->
             <cti_id>000190C8</cti_id>	<!-- content instance id -->
             <cti_fmt>0067108864</cti_fmt>	<!-- source file format -->
             <mda_dvc_id>TRSSVR01</mda_dvc_id>	<!-- create job device id -->
             <fl_nm>000190C7_800.wmv</fl_nm>
             <fl_typ></fl_typ>
             <fl_sz>19678576</fl_sz>
             <fl_loc>\\10.30.40.00\\news\\1</fl_loc>
             <cti_dur></cti_dur> <!-- content duration -->
             <vd_cdc>WMV V9</vd_cdc>	<!-- video codec -->
             <bit_rt>800</bit_rt> <!-- video bit_rate -->
             <frm_per_sec>30</frm_per_sec>	<!-- frame 1per second -->
             <vd_resol></vd_resol>	<!-- video resolution -->
             <aud_cdc>WMA 9.1</aud_cdc>	<!-- audio codec -->
             <aud_sou_min_unit>48</aud_sou_min_unit> <!-- audio sound min unit -->
             <aud_bit_rt>128</aud_bit_rt>	<!-- audio bit_rate -->
             <aud_sp_yn></aud_sp_yn>	<!-- audio splite y/n -->
             <aud_chl_sec></aud_chl_sec>	<!-- audio chnnel count -->
             <som></som>	<!-- content start position -->
             <eom></eom>	<!-- content end position -->
             <curr_ste>20</curr_ste>	<!-- job state -->
             <reg_dtm>2006-09-29 14:34:31</reg_dtm>
             <reg_usrid></reg_usrid>	<!-- job user id -->
       </tb_cti>*/
        private XmlDocument XDoc = null;
        private XmlNode ndest_from = null; //munsam 14
        private XmlNode ndest_to = null;  //munsam 14

        private XmlNode ntb_cti = null;
        private XmlNode nct_cla = null; //munsam
        private XmlNode nct_id = null;//컨텐츠ID
        private XmlNode ncti_id = null;//컨텐츠 인스턴스 ID
        private XmlNode ncti_fmt = null;//컨텐츠 포맷 코드

        private XmlNode nfl_nm = null;//
        private XmlNode nfl_typ = null;//
        private XmlNode nfl_sz = null;
        private XmlNode nfl_loc = null;
        private XmlNode ncti_dur = null;
        private XmlNode nvd_cdc = null;
        private XmlNode nbit_rt = null;
        private XmlNode nfrm_per_sec = null;
        private XmlNode nvd_vresol = null;
        private XmlNode nvd_hresol = null;
        private XmlNode naud_cdc = null;
        private XmlNode naud_sou_min_unit = null;
        private XmlNode naud_bit_rt = null;
        private XmlNode naud_sp_yn = null;
        private XmlNode naud_chl = null;
        private XmlNode nsom = null;
        private XmlNode neom = null;
        private XmlNode ncurr_ste = null;
        private XmlNode nreg_dtm = null;
        private XmlNode nreg_usrid = null;
        private XmlNode nasp_rto = null;
        private XmlNode ntot_frm_nums = null;
        private XmlNode nmda_dvc_id = null;

        private XmlNode ndelibgrd_cd = null; //munsam 심의등급
        private XmlNode nct_src = null;
        private XmlNode nstart_dtm = null;
        private XmlNode nend_dtm = null;
        private XmlNode nbrct_dt = null;
        private XmlNode nsou_typ = null;
        private XmlNode nsou_cla = null;


        /// <summary>
        /// 컬러구분
        /// </summary>
        private XmlNode ncol_cla = null;
        private XmlNode norg_fl_nm = null;
        /**/
        public tb_cti_Class()
        {
            XDoc = new XmlDocument();
            ntb_cti = XDoc.CreateElement("tb_cti");
            ntb_cti.InnerText = "";
            XDoc.AppendChild(ntb_cti);

            //munsam
            nct_cla = XDoc.CreateElement("ct_cla");
            nct_cla.InnerText = "";
            ntb_cti.AppendChild(nct_cla);

            ndest_from = XDoc.CreateElement("dest_from");
            ndest_from.InnerText = "IT01";
            ntb_cti.AppendChild(ndest_from);

            ndest_to = XDoc.CreateElement("dest_to");
            ndest_to.InnerText = "";
            ntb_cti.AppendChild(ndest_to);

            nct_id = XDoc.CreateElement("ct_id");
            nct_id.InnerText = "";
            ntb_cti.AppendChild(nct_id);

            ncti_id = XDoc.CreateElement("cti_id");
            ncti_id.InnerText = "";
            ntb_cti.AppendChild(ncti_id);

            ncti_fmt = XDoc.CreateElement("cti_fmt");
            ncti_fmt.InnerText = "";
            ntb_cti.AppendChild(ncti_fmt);

            nmda_dvc_id = XDoc.CreateElement("mda_dvc_id");
            nmda_dvc_id.InnerText = "";
            ntb_cti.AppendChild(nmda_dvc_id);

            nfl_nm = XDoc.CreateElement("fl_nm");
            nfl_nm.InnerText = "";
            ntb_cti.AppendChild(nfl_nm);

            nfl_typ = XDoc.CreateElement("fl_typ");
            nfl_typ.InnerText = "";
            ntb_cti.AppendChild(nfl_typ);

            nfl_sz = XDoc.CreateElement("fl_sz");
            nfl_sz.InnerText = "";
            ntb_cti.AppendChild(nfl_sz);

            nfl_loc = XDoc.CreateElement("fl_loc");
            nfl_loc.InnerText = "";
            ntb_cti.AppendChild(nfl_loc);

            ncti_dur = XDoc.CreateElement("cti_dur");
            ncti_dur.InnerText = "";
            ntb_cti.AppendChild(ncti_dur);

            nvd_cdc = XDoc.CreateElement("vd_cdc");
            nvd_cdc.InnerText = "";
            ntb_cti.AppendChild(nvd_cdc);

            nbit_rt = XDoc.CreateElement("bit_rt");
            nbit_rt.InnerText = "";
            ntb_cti.AppendChild(nbit_rt);

            nfrm_per_sec = XDoc.CreateElement("frm_per_sec");
            nfrm_per_sec.InnerText = "";
            ntb_cti.AppendChild(nfrm_per_sec);

            nvd_vresol = XDoc.CreateElement("vd_vresol");
            nvd_vresol.InnerText = "";
            ntb_cti.AppendChild(nvd_vresol);

            nvd_hresol = XDoc.CreateElement("vd_hresol");
            nvd_hresol.InnerText = "";
            ntb_cti.AppendChild(nvd_hresol);

            naud_cdc = XDoc.CreateElement("aud_cdc");
            naud_cdc.InnerText = "";
            ntb_cti.AppendChild(naud_cdc);

            naud_sou_min_unit = XDoc.CreateElement("aud_sou_min_unit");
            naud_sou_min_unit.InnerText = "";
            ntb_cti.AppendChild(naud_sou_min_unit);

            naud_bit_rt = XDoc.CreateElement("aud_bit_rt");
            naud_bit_rt.InnerText = "";
            ntb_cti.AppendChild(naud_bit_rt);

            naud_sp_yn = XDoc.CreateElement("aud_sp_yn");
            naud_sp_yn.InnerText = "";
            ntb_cti.AppendChild(naud_sp_yn);

            naud_chl = XDoc.CreateElement("aud_chl");
            naud_chl.InnerText = "";
            ntb_cti.AppendChild(naud_chl);

            nsom = XDoc.CreateElement("som");
            nsom.InnerText = "";
            ntb_cti.AppendChild(nsom);

            neom = XDoc.CreateElement("eom");
            neom.InnerText = "";
            ntb_cti.AppendChild(neom);

            ncurr_ste = XDoc.CreateElement("curr_ste");
            ncurr_ste.InnerText = "";
            ntb_cti.AppendChild(ncurr_ste);

            nreg_dtm = XDoc.CreateElement("reg_dtm");
            nreg_dtm.InnerText = "";
            ntb_cti.AppendChild(nreg_dtm);

            nreg_usrid = XDoc.CreateElement("reg_usrid");
            nreg_usrid.InnerText = "";
            ntb_cti.AppendChild(nreg_usrid);

            nasp_rto = XDoc.CreateElement("asp_rto");
            nasp_rto.InnerText = "";
            ntb_cti.AppendChild(nasp_rto);

            ntot_frm_nums = XDoc.CreateElement("tot_frm_nums");
            ntot_frm_nums.InnerText = "";
            ntb_cti.AppendChild(ntot_frm_nums);

            ndelibgrd_cd = XDoc.CreateElement("delibgrd_cd");
            ndelibgrd_cd.InnerText = "";
            ntb_cti.AppendChild(ndelibgrd_cd);

            nct_src = XDoc.CreateElement("ct_src");
            nct_src.InnerText = "";
            ntb_cti.AppendChild(nct_src);

            ncol_cla = XDoc.CreateElement("col_cla");
            ncol_cla.InnerText = "";
            ntb_cti.AppendChild(ncol_cla);

            norg_fl_nm = XDoc.CreateElement("org_fl_nm");
            norg_fl_nm.InnerText = "";
            ntb_cti.AppendChild(norg_fl_nm);

            nstart_dtm = XDoc.CreateElement("start_dtm");
            nstart_dtm.InnerText = "";
            ntb_cti.AppendChild(nstart_dtm);

            nend_dtm = XDoc.CreateElement("end_dtm");
            nend_dtm.InnerText = "";
            ntb_cti.AppendChild(nend_dtm);

            nbrct_dt = XDoc.CreateElement("brct_dt");
            nbrct_dt.InnerText = "";
            ntb_cti.AppendChild(nbrct_dt);

            //nsou_typ = null;
            //nsou_cla = null;


            nsou_typ = XDoc.CreateElement("sou_typ");
            nsou_typ.InnerText = "";
            ntb_cti.AppendChild(nsou_typ);

            nsou_cla = XDoc.CreateElement("sou_cla");
            nsou_cla.InnerText = "";
            ntb_cti.AppendChild(nsou_cla);

            string inner = XDoc.InnerXml;
        }

        //munsam
#if true
        public string dest_from
        {
            get
            {
                if (ndest_from == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(ndest_from.InnerText);
            }
            set
            {
                if (ndest_from == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                ndest_from.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string dest_to
        {
            get
            {
                if (ndest_to == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(ndest_to.InnerText);
            }
            set
            {
                if (ndest_to == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                ndest_to.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }


        public string delibgrd_cd
        {
            get
            {
                if (ndelibgrd_cd == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(ndelibgrd_cd.InnerText);
            }
            set
            {
                if (ndelibgrd_cd == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                ndelibgrd_cd.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }


        public string ct_src
        {
            get
            {
                if (nct_src == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nct_src.InnerText);
            }
            set
            {
                if (nct_src == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nct_src.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }


        public string ct_cla
        {
            get
            {
                if (nct_cla == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nct_cla.InnerText);
            }
            set
            {
                if (nct_cla == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nct_cla.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }
#endif


        public string ct_id
        {
            get
            {
                if (nct_id == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nct_id.InnerText);
            }
            set
            {
                if (nct_id == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nct_id.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string cti_id
        {
            get
            {
                if (ncti_id == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(ncti_id.InnerText);
            }
            set
            {
                if (ncti_id == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                ncti_id.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string cti_fmt
        {
            get
            {
                if (ncti_fmt == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(ncti_fmt.InnerText);
            }
            set
            {
                if (ncti_fmt == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                ncti_fmt.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string mda_dvc_id
        {
            get
            {
                if (nmda_dvc_id == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nmda_dvc_id.InnerText);
            }
            set
            {
                if (nmda_dvc_id == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nmda_dvc_id.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string fl_nm
        {
            get
            {
                if (nfl_nm == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nfl_nm.InnerText);
            }
            set
            {
                if (nfl_nm == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nfl_nm.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string fl_typ
        {
            get
            {
                if (nfl_typ == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nfl_typ.InnerText);
            }
            set
            {
                if (nfl_typ == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nfl_typ.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string fl_sz
        {
            get
            {
                if (nfl_sz == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nfl_sz.InnerText);
            }
            set
            {
                if (nfl_sz == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nfl_sz.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string fl_loc
        {
            get
            {
                if (nfl_loc == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nfl_loc.InnerText);
            }
            set
            {
                if (nfl_loc == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nfl_loc.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string cti_dur
        {
            get
            {
                if (ncti_dur == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(ncti_dur.InnerText);
            }
            set
            {
                if (ncti_dur == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                ncti_dur.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string vd_cdc
        {
            get
            {
                if (nvd_cdc == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nvd_cdc.InnerText);
            }
            set
            {
                if (nvd_cdc == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nvd_cdc.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string bit_rt
        {
            get
            {
                if (nbit_rt == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nbit_rt.InnerText);
            }
            set
            {
                if (nbit_rt == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nbit_rt.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string frm_per_sec
        {
            get
            {
                if (nfrm_per_sec == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nfrm_per_sec.InnerText);
            }
            set
            {
                if (nfrm_per_sec == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nfrm_per_sec.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string vd_vresol
        {
            get
            {
                if (nvd_vresol == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nvd_vresol.InnerText);
            }
            set
            {
                if (nvd_vresol == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nvd_vresol.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string vd_hresol
        {
            get
            {
                if (nvd_hresol == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nvd_hresol.InnerText);
            }
            set
            {
                if (nvd_hresol == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nvd_hresol.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string aud_cdc
        {
            get
            {
                if (naud_cdc == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(naud_cdc.InnerText);
            }
            set
            {
                if (naud_cdc == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                naud_cdc.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string aud_sou_min_unit
        {
            get
            {
                if (naud_sou_min_unit == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(naud_sou_min_unit.InnerText);
            }
            set
            {
                if (naud_sou_min_unit == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                naud_sou_min_unit.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string aud_bit_rt
        {
            get
            {
                if (naud_bit_rt == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(naud_bit_rt.InnerText);
            }
            set
            {
                if (naud_bit_rt == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                naud_bit_rt.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string aud_sp_yn
        {
            get
            {
                if (naud_sp_yn == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(naud_sp_yn.InnerText);
            }
            set
            {
                if (naud_sp_yn == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                naud_sp_yn.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string aud_chl
        {
            get
            {
                if (naud_chl == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(naud_chl.InnerText);
            }
            set
            {
                if (naud_chl == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                naud_chl.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string som
        {
            get
            {
                if (nsom == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nsom.InnerText);
            }
            set
            {
                if (nsom == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nsom.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string eom
        {
            get
            {
                if (neom == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(neom.InnerText);
            }
            set
            {
                if (neom == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                neom.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string curr_ste
        {
            get
            {
                if (ncurr_ste == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(ncurr_ste.InnerText);
            }
            set
            {
                if (ncurr_ste == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                ncurr_ste.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string reg_dtm
        {
            get
            {
                if (nreg_dtm == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return nreg_dtm.InnerText;
            }
            set
            {
                if (nreg_dtm == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                DateTime dt = DateTime.Now;
                //nreg_dtm.InnerText = value;
                string time = dt.ToString("yyyy-MM-dd HH:mm:ss");
                nreg_dtm.InnerText = time;
            }
        }

        public string asp_rto
        {
            get
            {
                if (nasp_rto == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nasp_rto.InnerText);
            }
            set
            {
                if (nasp_rto == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nasp_rto.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string tot_frm_nums
        {
            get
            {
                if (ntot_frm_nums == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(ntot_frm_nums.InnerText);
            }
            set
            {
                if (ntot_frm_nums == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                ntot_frm_nums.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }



        public string reg_usrid
        {
            get
            {
                if (nreg_usrid == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(nreg_usrid.InnerText);
            }
            set
            {
                if (nreg_usrid == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nreg_usrid.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string col_cla
        {
            get
            {
                if (ncol_cla == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                return System.Web.HttpUtility.UrlDecode(ncol_cla.InnerText);
            }
            set
            {
                if (ncol_cla == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                ncol_cla.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string org_fl_nm
        {
            get
            {
                if (norg_fl_nm == null)
                {
                    norg_fl_nm = XDoc.CreateElement("org_fl_nm");
                    norg_fl_nm.InnerText = "";
                    ntb_cti.AppendChild(norg_fl_nm);
                }
                return System.Web.HttpUtility.UrlDecode(norg_fl_nm.InnerText);
            }
            set
            {
                if (norg_fl_nm == null)
                {
                    norg_fl_nm = XDoc.CreateElement("org_fl_nm");
                    norg_fl_nm.InnerText = "";
                    ntb_cti.AppendChild(norg_fl_nm);
                }
                norg_fl_nm.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string start_dtm
        {
            get
            {
                if (nstart_dtm == null)
                {
                    nstart_dtm = XDoc.CreateElement("start_dtm");
                    nstart_dtm.InnerText = "";
                    ntb_cti.AppendChild(nstart_dtm);
                }
                return System.Web.HttpUtility.UrlDecode(nstart_dtm.InnerText);
            }
            set
            {
                if (nstart_dtm == null)
                {
                    nstart_dtm = XDoc.CreateElement("start_dtm");
                    nstart_dtm.InnerText = "";
                    ntb_cti.AppendChild(nstart_dtm);
                }
                nstart_dtm.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string end_dtm
        {
            get
            {
                if (nend_dtm == null)
                {
                    nend_dtm = XDoc.CreateElement("end_dtm");
                    nend_dtm.InnerText = "";
                    ntb_cti.AppendChild(nend_dtm);
                }
                return System.Web.HttpUtility.UrlDecode(nend_dtm.InnerText);
            }
            set
            {
                if (nend_dtm == null)
                {
                    nend_dtm = XDoc.CreateElement("end_dtm");
                    nend_dtm.InnerText = "";
                    ntb_cti.AppendChild(nend_dtm);
                }
                nend_dtm.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        /// <summary>
        /// 재방일
        /// </summary>
        public string brct_dt
        {
            get
            {
                if (nbrct_dt == null)
                {
                    nbrct_dt = XDoc.CreateElement("brct_dt");
                    nbrct_dt.InnerText = "";
                    ntb_cti.AppendChild(nbrct_dt);
                }
                return System.Web.HttpUtility.UrlDecode(nbrct_dt.InnerText);
            }
            set
            {
                if (nbrct_dt == null)
                {
                    nbrct_dt = XDoc.CreateElement("brct_dt");
                    nbrct_dt.InnerText = "";
                    ntb_cti.AppendChild(nbrct_dt);
                }
                nbrct_dt.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

//        nsou_typ = XDoc.CreateElement("sou_typ");

//        nsou_cla = XDoc.CreateElement("sou_cla");
        /// <summary>
        /// 음색구분
        /// </summary>
        public string sou_typ
        {
            get
            {
                if (nsou_typ == null)
                {
                    nsou_typ = XDoc.CreateElement("sou_typ");
                    nsou_typ.InnerText = "";
                    ntb_cti.AppendChild(nsou_typ);
                }
                return System.Web.HttpUtility.UrlDecode(nsou_typ.InnerText);
            }
            set
            {
                if (nsou_typ == null)
                {
                    nsou_typ = XDoc.CreateElement("sou_typ");
                    nsou_typ.InnerText = "";
                    ntb_cti.AppendChild(nsou_typ);
                }
                nsou_typ.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        /// <summary>
        /// 음향구분
        /// </summary>
        public string sou_cla
        {
            get
            {
                if (nsou_cla == null)
                {
                    nsou_cla = XDoc.CreateElement("sou_cla");
                    nsou_cla.InnerText = "";
                    ntb_cti.AppendChild(nsou_cla);
                }
                return System.Web.HttpUtility.UrlDecode(nsou_cla.InnerText);
            }
            set
            {
                if (nsou_cla == null)
                {
                    nsou_cla = XDoc.CreateElement("sou_cla");
                    nsou_cla.InnerText = "";
                    ntb_cti.AppendChild(nsou_cla);
                }
                nsou_cla.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string WholeText
        {
            get
            {
                if (XDoc == null)
                {
                    throw new Exception("내부 값이 없습니다");
                }
                //XDoc.Save(xw)
                //XmlWriterSettings set = new XmlWriterSettings();
                //set.OmitXmlDeclaration = true;
                //XmlWriter xw = XmlWriter.Create(sb, set);
                //string str = sb.ToString();
                //StringBuilder sb = new StringBuilder();
                //StringWriter writer = new StringWriter();              
                //XDoc.Save(writer);
                //return writer.ToString();
                return XDoc.OuterXml;
            }
            set
            {
                XDoc.RemoveAll();
                XDoc = null;

                XDoc = new XmlDocument();
                XDoc.LoadXml(value);
                ntb_cti = XDoc.FirstChild;
                ndest_from = ntb_cti.SelectSingleNode("dest_from"); //munsam
                ndest_to = ntb_cti.SelectSingleNode("dest_to "); //munsam
                nct_cla = ntb_cti.SelectSingleNode("ct_cla"); //munsam
                nct_id = ntb_cti.SelectSingleNode("ct_id");
                ncti_id = ntb_cti.SelectSingleNode("cti_id");
                ncti_fmt = ntb_cti.SelectSingleNode("cti_fmt");
                nmda_dvc_id = ntb_cti.SelectSingleNode("mda_dvc_id");
                nfl_nm = ntb_cti.SelectSingleNode("fl_nm");
                nfl_typ = ntb_cti.SelectSingleNode("fl_typ");
                nfl_sz = ntb_cti.SelectSingleNode("fl_sz");
                nfl_loc = ntb_cti.SelectSingleNode("fl_loc");
                ncti_dur = ntb_cti.SelectSingleNode("cti_dur");
                nvd_cdc = ntb_cti.SelectSingleNode("vd_cdc");
                nbit_rt = ntb_cti.SelectSingleNode("bit_rt");
                nfrm_per_sec = ntb_cti.SelectSingleNode("frm_per_sec");
                nvd_vresol = ntb_cti.SelectSingleNode("vd_resol");
                nvd_hresol = ntb_cti.SelectSingleNode("vd_hesol");
                naud_cdc = ntb_cti.SelectSingleNode("aud_cdc");
                naud_sou_min_unit = ntb_cti.SelectSingleNode("aud_sou_min_unit");
                naud_bit_rt = ntb_cti.SelectSingleNode("aud_bit_rt");
                naud_sp_yn = ntb_cti.SelectSingleNode("aud_sp_yn");
                naud_chl = ntb_cti.SelectSingleNode("aud_chl");
                nsom = ntb_cti.SelectSingleNode("som");
                neom = ntb_cti.SelectSingleNode("eom");
                ncurr_ste = ntb_cti.SelectSingleNode("curr_ste");
                nreg_dtm = ntb_cti.SelectSingleNode("reg_dtm");
                nasp_rto = ntb_cti.SelectSingleNode("asp_rto");
                nreg_usrid = ntb_cti.SelectSingleNode("reg_usrid");
                ntot_frm_nums = ntb_cti.SelectSingleNode("tot_frm_nums");
                ndelibgrd_cd = ntb_cti.SelectSingleNode("delibgrd_cd");
                nct_src = ntb_cti.SelectSingleNode("ct_src");
                ncol_cla = ntb_cti.SelectSingleNode("col_cla");
                norg_fl_nm = ntb_cti.SelectSingleNode("org_fl_nm");
                nstart_dtm = ntb_cti.SelectSingleNode("start_dtm");
                nend_dtm = ntb_cti.SelectSingleNode("end_dtm");
                nbrct_dt = ntb_cti.SelectSingleNode("brct_dt");
                nsou_cla = ntb_cti.SelectSingleNode("sou_cla");
                nsou_typ = ntb_cti.SelectSingleNode("sou_typ");

                if (nct_src == null)
                {
                    nct_src = XDoc.CreateElement("ct_src");
                    nct_src.InnerText = "";
                    ntb_cti.AppendChild(nct_src);
                }
                if (naud_chl == null)
                {
                    naud_chl = XDoc.CreateElement("aud_chl");
                    naud_chl.InnerText = "";
                    ntb_cti.AppendChild(naud_chl);
                }

                if (ncol_cla == null)
                {
                    ncol_cla = XDoc.CreateElement("col_cla");
                    ncol_cla.InnerText = "";
                    ntb_cti.AppendChild(ncol_cla);
                }

               
            }
        }

        public tb_cti_Class(string input)
        {
            XDoc = new XmlDocument();
            XDoc.LoadXml(input);
            ntb_cti = XDoc.FirstChild;

            ndest_from = ntb_cti.SelectSingleNode("dest_from"); //munsam
            ndest_to = ntb_cti.SelectSingleNode("dest_to "); //munsam
            nct_cla = ntb_cti.SelectSingleNode("ct_cla"); //munsam
            nct_id = ntb_cti.SelectSingleNode("ct_id");
            ncti_id = ntb_cti.SelectSingleNode("cti_id");
            ncti_fmt = ntb_cti.SelectSingleNode("cti_fmt");
            nmda_dvc_id = ntb_cti.SelectSingleNode("mda_dvc_id");
            nfl_nm = ntb_cti.SelectSingleNode("fl_nm");
            nfl_typ = ntb_cti.SelectSingleNode("fl_typ");
            nfl_sz = ntb_cti.SelectSingleNode("fl_sz");
            nfl_loc = ntb_cti.SelectSingleNode("fl_loc");
            ncti_dur = ntb_cti.SelectSingleNode("cti_dur");
            nvd_cdc = ntb_cti.SelectSingleNode("vd_cdc");
            nbit_rt = ntb_cti.SelectSingleNode("bit_rt");
            nfrm_per_sec = ntb_cti.SelectSingleNode("frm_per_sec");
            nvd_vresol = ntb_cti.SelectSingleNode("vd_vresol");
            nvd_hresol = ntb_cti.SelectSingleNode("vd_hresol");
            naud_cdc = ntb_cti.SelectSingleNode("aud_cdc");
            naud_sou_min_unit = ntb_cti.SelectSingleNode("aud_sou_min_unit");
            naud_bit_rt = ntb_cti.SelectSingleNode("aud_bit_rt");
            naud_sp_yn = ntb_cti.SelectSingleNode("aud_sp_yn");
            naud_chl = ntb_cti.SelectSingleNode("aud_chl");
            nsom = ntb_cti.SelectSingleNode("som");
            neom = ntb_cti.SelectSingleNode("eom");
            ncurr_ste = ntb_cti.SelectSingleNode("curr_ste");
            nreg_dtm = ntb_cti.SelectSingleNode("reg_dtm");
            nreg_usrid = ntb_cti.SelectSingleNode("reg_usrid");
            nasp_rto = ntb_cti.SelectSingleNode("asp_rto");
            ntot_frm_nums = ntb_cti.SelectSingleNode("tot_frm_nums");

            ndelibgrd_cd = ntb_cti.SelectSingleNode("delibgrd_cd");
            nct_src = ntb_cti.SelectSingleNode("ct_src");
            ncol_cla = ntb_cti.SelectSingleNode("col_cla");
            norg_fl_nm = ntb_cti.SelectSingleNode("org_fl_nm");
            nstart_dtm = ntb_cti.SelectSingleNode("start_dtm");
            nend_dtm = ntb_cti.SelectSingleNode("end_dtm");
            nbrct_dt = ntb_cti.SelectSingleNode("brct_dt");
            nsou_cla = ntb_cti.SelectSingleNode("sou_cla");
            nsou_typ = ntb_cti.SelectSingleNode("sou_typ");

            if (nct_src == null)
            {
                nct_src = XDoc.CreateElement("ct_src");
                nct_src.InnerText = "";
                ntb_cti.AppendChild(nct_src);
            }
            if (naud_chl == null)
            {
                naud_chl = XDoc.CreateElement("aud_chl");
                naud_chl.InnerText = "";
                ntb_cti.AppendChild(naud_chl);
            }

            if (ncol_cla == null)
            {
                ncol_cla = XDoc.CreateElement("col_cla");
                ncol_cla.InnerText = "";
                ntb_cti.AppendChild(ncol_cla);
            }
        }

        public tb_cti_Class(FileInfo input)
        {
            XDoc = new XmlDocument();
            XDoc.Load(input.FullName);

            ntb_cti = XDoc.FirstChild;
            ndest_from = ntb_cti.SelectSingleNode("dest_from"); //munsam
            ndest_to = ntb_cti.SelectSingleNode("dest_to "); //munsam
            nct_cla = ntb_cti.SelectSingleNode("ct_cla"); //munsam
            nct_id = ntb_cti.SelectSingleNode("ct_id");
            ncti_id = ntb_cti.SelectSingleNode("cti_id");
            ncti_fmt = ntb_cti.SelectSingleNode("cti_fmt");
            nmda_dvc_id = ntb_cti.SelectSingleNode("mda_dvc_id");
            nfl_nm = ntb_cti.SelectSingleNode("fl_nm");
            nfl_typ = ntb_cti.SelectSingleNode("fl_typ");
            nfl_sz = ntb_cti.SelectSingleNode("fl_sz");
            nfl_loc = ntb_cti.SelectSingleNode("fl_loc");
            ncti_dur = ntb_cti.SelectSingleNode("cti_dur");
            nvd_cdc = ntb_cti.SelectSingleNode("vd_cdc");
            nbit_rt = ntb_cti.SelectSingleNode("bit_rt");
            nfrm_per_sec = ntb_cti.SelectSingleNode("frm_per_sec");
            nvd_vresol = ntb_cti.SelectSingleNode("vd_vresol");
            nvd_hresol = ntb_cti.SelectSingleNode("vd_hresol");
            naud_cdc = ntb_cti.SelectSingleNode("aud_cdc");
            naud_sou_min_unit = ntb_cti.SelectSingleNode("aud_sou_min_unit");
            naud_bit_rt = ntb_cti.SelectSingleNode("aud_bit_rt");
            naud_sp_yn = ntb_cti.SelectSingleNode("aud_sp_yn");
            naud_chl = ntb_cti.SelectSingleNode("aud_chl");
            nsom = ntb_cti.SelectSingleNode("som");
            neom = ntb_cti.SelectSingleNode("eom");
            ncurr_ste = ntb_cti.SelectSingleNode("curr_ste");
            nreg_dtm = ntb_cti.SelectSingleNode("reg_dtm");
            nreg_usrid = ntb_cti.SelectSingleNode("reg_usrid");
            nasp_rto = ntb_cti.SelectSingleNode("asp_rto");
            ntot_frm_nums = ntb_cti.SelectSingleNode("tot_frm_nums");

            ndelibgrd_cd = ntb_cti.SelectSingleNode("delibgrd_cd");//munsam
            nct_src = ntb_cti.SelectSingleNode("ct_src");//munsam
            ncol_cla = ntb_cti.SelectSingleNode("col_cla");
            norg_fl_nm = ntb_cti.SelectSingleNode("org_fl_nm");
            nstart_dtm = ntb_cti.SelectSingleNode("start_dtm");
            nend_dtm = ntb_cti.SelectSingleNode("end_dtm");
            nbrct_dt = ntb_cti.SelectSingleNode("brct_dt");
            nsou_cla = ntb_cti.SelectSingleNode("sou_cla");
            nsou_typ = ntb_cti.SelectSingleNode("sou_typ");

            if (nct_src == null)
            {
                nct_src = XDoc.CreateElement("ct_src");
                nct_src.InnerText = "";
                ntb_cti.AppendChild(nct_src);
            }
            if (naud_chl == null)
            {
                naud_chl = XDoc.CreateElement("aud_chl");
                naud_chl.InnerText = "";
                ntb_cti.AppendChild(naud_chl);
            }

            if (ncol_cla == null)
            {
                ncol_cla = XDoc.CreateElement("col_cla");
                ncol_cla.InnerText = "";
                ntb_cti.AppendChild(ncol_cla);
            }
        }

        public void SetValue(MXFParser psr)
        {
            vd_cdc = psr.VideoCodec;
            decimal fps = Convert.ToDecimal(psr.FrmPerSec.Replace("i", "").Replace("p", ""));
            if (fps > 30)
            {
                fps = fps / 2;
            }
            //frm_per_sec = psr.FPS;
            frm_per_sec = fps.ToString();
            vd_vresol = psr.XResolution;
            vd_hresol = psr.YResolution;
            if (Convert.ToInt32(vd_vresol) > 1000)
            {
                ct_src = "HD";
            }
            else
            {
                ct_src = "SD";
            }
            aud_cdc = psr.AudioCodec;
            aud_chl = psr.AudioChnNum;
            som = psr.StartPosition;
            eom = psr.EndPosition;
            asp_rto = psr.AsperctRatio;
            reg_dtm = System.Web.HttpUtility.UrlEncode(DateTime.Now.ToString());
            tot_frm_nums = psr.Duration;
            TimeCode tc = null;
            string dur = "";
            if (psr.FrmPerSec == "59.94i")
            {
                tc = TimeCode.CreateTimeCode(TCFormat.NTSC_Drop, TCMode.TimeCode, Convert.ToInt32(psr.Duration));
                dur = tc.TC.Replace(":", ";");
            }
            else
            {
                tc = TimeCode.CreateTimeCode(TCFormat.NTSC_Nondrop, TCMode.TimeCode, Convert.ToInt32(psr.Duration));
                dur = tc.TC.Replace(";", ":");
            }
            //string dur = tc.TC;
            //dur = dur.Replace(":", "");
            cti_dur = dur;
        }

        public void SetWMVValue(string ctid, string ctiid, string filetyp, TimeCode tc, string VideoCodec, string FPS, string Xres, string Yres, string AudCodec,
            string AudChnNum, string StartPosition)
        {
            ct_id = ctid;
            cti_id = ctiid;
            fl_typ = filetyp;

            //TimeCode tc = TimeCode.CreateTimeCode(TCFormat.NTSC_Drop, TCMode.TimeCode, Convert.ToInt16(Duration));
            //string dur = tc.TC;
            //dur = dur.Replace(":", "");
            cti_dur = (tc.TC).Replace(":", "");
            this.tot_frm_nums = tc.Frame.ToString();

            //cti_dur = Duration;
            vd_cdc = VideoCodec;
            frm_per_sec = FPS;
            vd_vresol = Xres;
            vd_hresol = Yres;
            aud_cdc = AudCodec;
            aud_chl = "2";
            som = StartPosition;
            eom = tc.TC;

            reg_dtm = System.Web.HttpUtility.UrlEncode(DateTime.Now.ToString());
        }

        public void Save(string path)
        {
            if (XDoc != null)
            {
                XDoc.Save(path);
            }
        }


    }

    public class ContentsXml
    {
        private XmlDocument XDoc = null;
        private XmlNode ntb_ct = null;
        private XmlNode nct_id = null;
        private XmlNode npgm_id = null;
        private XmlNode nsub_pgm_id = null;
        private XmlNode nacc_auth = null;
        private XmlNode nct_gb = null;
        private XmlNode nct_typ = null;
        private XmlNode nct_nm = null;
        private XmlNode nrem = null;
        private XmlNode ndesc = null;
        private XmlNode nfm_dt = null;
        private XmlNode nfm_pers = null;
        private XmlNode nedtr = null;
        private XmlNode nsrch_kwd = null;
        private XmlNode ncoll_pln_doc_id = null;
        private XmlNode nctts_code = null;
        private XmlNode ncnrnum = null;
        private XmlNode next_news_id = null;
        private XmlNode nadveid = null;
        private XmlNode nreg_dtm = null;
        private XmlNode nupdt_dtm = null;
        private XmlNode nreg_usrid = null;
        private XmlNode nupdt_usrid = null;
        private XmlNode nmda_dvc_id = null;//미디어장비번호
        private XmlNode nstart_dtm = null;
        private XmlNode nct_cla = null;//munsam
        private XmlNode ndelibgrd_cd = null;//munsam
        private XmlNode nct_scr = null;//munsam

        public ContentsXml()
        {
            XDoc = new XmlDocument();

            ntb_ct = XDoc.CreateElement("tb_ct");
            ntb_ct.InnerText = "";
            XDoc.AppendChild(ntb_ct);

            //munsam
            nct_cla = XDoc.CreateElement("ct_cla");
            nct_cla.InnerText = "";
            ntb_ct.AppendChild(nct_cla);

            nct_id = XDoc.CreateElement("ct_id");
            nct_id.InnerText = "";
            ntb_ct.AppendChild(nct_id);

            nct_gb = XDoc.CreateElement("ct_gb");
            nct_gb.InnerText = "";
            ntb_ct.AppendChild(nct_gb);

            nct_typ = XDoc.CreateElement("ct_typ");
            nct_typ.InnerText = "";
            ntb_ct.AppendChild(nct_typ);

            //nct_cla = XDoc.CreateElement("ct_cla");
            //nct_cla.InnerText = "";
            //ntb_ct.AppendChild(nct_cla);

            nct_nm = XDoc.CreateElement("ct_nm");
            nct_nm.InnerText = "";
            ntb_ct.AppendChild(nct_nm);

            nrem = XDoc.CreateElement("rem");
            nrem.InnerText = "";
            ntb_ct.AppendChild(nrem);

            ndesc = XDoc.CreateElement("desc");
            ndesc.InnerText = "";
            ntb_ct.AppendChild(ndesc);

            nfm_dt = XDoc.CreateElement("fm_dt");
            nfm_dt.InnerText = "";
            ntb_ct.AppendChild(nfm_dt);

            nfm_pers = XDoc.CreateElement("fm_pers");
            nfm_pers.InnerText = "";
            ntb_ct.AppendChild(nfm_pers);

            nedtr = XDoc.CreateElement("edtr");
            nedtr.InnerText = "";
            ntb_ct.AppendChild(nedtr);

            nsrch_kwd = XDoc.CreateElement("srch_kwd");
            nsrch_kwd.InnerText = "";
            ntb_ct.AppendChild(nsrch_kwd);

            ncoll_pln_doc_id = XDoc.CreateElement("coll_pln_doc_id");
            ncoll_pln_doc_id.InnerText = "";
            ntb_ct.AppendChild(ncoll_pln_doc_id);

            nctts_code = XDoc.CreateElement("ctts_code");
            nctts_code.InnerText = "";
            ntb_ct.AppendChild(nctts_code);

            npgm_id = XDoc.CreateElement("pgm_id");
            npgm_id.InnerText = "";
            ntb_ct.AppendChild(npgm_id);

            nsub_pgm_id = XDoc.CreateElement("sub_pgm_id");
            nsub_pgm_id.InnerText = "";
            ntb_ct.AppendChild(nsub_pgm_id);



            ncnrnum = XDoc.CreateElement("cnrnum");
            ncnrnum.InnerText = "";
            ntb_ct.AppendChild(ncnrnum);


            next_news_id = XDoc.CreateElement("ext_news_id");
            next_news_id.InnerText = "";
            ntb_ct.AppendChild(next_news_id);

            nadveid = XDoc.CreateElement("nadveid");
            nadveid.InnerText = "";
            ntb_ct.AppendChild(nadveid);

            nreg_dtm = XDoc.CreateElement("reg_dtm");
            nreg_dtm.InnerText = "";
            ntb_ct.AppendChild(nreg_dtm);

            nupdt_dtm = XDoc.CreateElement("updt_dtm");
            nupdt_dtm.InnerText = "";
            ntb_ct.AppendChild(nupdt_dtm);

            nreg_usrid = XDoc.CreateElement("reg_usrid");
            nreg_usrid.InnerText = "";
            ntb_ct.AppendChild(nreg_usrid);

            nupdt_usrid = XDoc.CreateElement("updt_usrid");
            nupdt_usrid.InnerText = "";
            ntb_ct.AppendChild(nupdt_usrid);

            nmda_dvc_id = XDoc.CreateElement("mda_dvc_id");
            nmda_dvc_id.InnerText = "";
            ntb_ct.AppendChild(nmda_dvc_id);

            nstart_dtm = XDoc.CreateElement("start_dtm");
            nstart_dtm.InnerText = "";
            ntb_ct.AppendChild(nstart_dtm);

            ndelibgrd_cd = XDoc.CreateElement("delibgrd_cd");
            ndelibgrd_cd.InnerText = "";
            ntb_ct.AppendChild(ndelibgrd_cd);

            nct_scr = XDoc.CreateElement("ct_scr");
            nct_scr.InnerText = "";
            ntb_ct.AppendChild(nct_scr);
        }

        public ContentsXml(string input)
        {
            XDoc = new XmlDocument();
            XDoc.LoadXml(input);

            ntb_ct = XDoc.FirstChild;
            nct_cla = ntb_ct.SelectSingleNode("ct_cla"); //munsam
            nct_id = ntb_ct.SelectSingleNode("ct_id");
            nct_gb = ntb_ct.SelectSingleNode("ct_gb");
            nct_typ = ntb_ct.SelectSingleNode("ct_typ");
            nct_nm = ntb_ct.SelectSingleNode("ct_nm");
            nrem = ntb_ct.SelectSingleNode("rem");
            ndesc = ntb_ct.SelectSingleNode("desc");
            nfm_dt = ntb_ct.SelectSingleNode("fm_dt");
            nfm_pers = ntb_ct.SelectSingleNode("fm_pers");
            nedtr = ntb_ct.SelectSingleNode("edtr");
            nsrch_kwd = ntb_ct.SelectSingleNode("srch_kwd");
            ncoll_pln_doc_id = ntb_ct.SelectSingleNode("coll_pln_doc_id");
            nctts_code = ntb_ct.SelectSingleNode("ctts_code");
            npgm_id = ntb_ct.SelectSingleNode("pgm_id");
            ncnrnum = ntb_ct.SelectSingleNode("cnrnum");
            nsub_pgm_id = ntb_ct.SelectSingleNode("sub_pgm_id");
            next_news_id = ntb_ct.SelectSingleNode("ext_news_id");
            nadveid = ntb_ct.SelectSingleNode("adveid");
            nreg_dtm = ntb_ct.SelectSingleNode("reg_dtm");
            nupdt_dtm = ntb_ct.SelectSingleNode("updt_dtm");
            nreg_usrid = ntb_ct.SelectSingleNode("reg_usrid");
            nupdt_usrid = ntb_ct.SelectSingleNode("updt_usrid");
            nmda_dvc_id = ntb_ct.SelectSingleNode("mda_dvc_id");
            nstart_dtm = ntb_ct.SelectSingleNode("start_dtm");
            ndelibgrd_cd = ntb_ct.SelectSingleNode("delibgrd_cd");
            nct_scr = ntb_ct.SelectSingleNode("ct_scr");

            if (ntb_ct == null)
            {
                ntb_ct = XDoc.CreateElement("tb_ct");
                ntb_ct.InnerText = "";
                XDoc.AppendChild(ntb_ct);
            }

            if (nct_cla == null)
            {
                nct_cla = XDoc.CreateElement("ct_cla");
                nct_cla.InnerText = "";
                ntb_ct.AppendChild(nct_cla);
            }

            if (nct_id == null)
            {
                nct_id = XDoc.CreateElement("ct_id");
                nct_id.InnerText = "";
                ntb_ct.AppendChild(nct_id);
            }

            if (nct_gb == null)
            {
                nct_gb = XDoc.CreateElement("ct_gb");
                nct_gb.InnerText = "";
                ntb_ct.AppendChild(nct_gb);
            }

            if (nct_typ == null)
            {
                nct_typ = XDoc.CreateElement("ct_typ");
                nct_typ.InnerText = "";
                ntb_ct.AppendChild(nct_typ);
            }

            if (nct_nm == null)
            {
                nct_nm = XDoc.CreateElement("ct_nm");
                nct_nm.InnerText = "";
                ntb_ct.AppendChild(nct_nm);
            }

            if (nrem == null)
            {
                nrem = XDoc.CreateElement("rem");
                nrem.InnerText = "";
                ntb_ct.AppendChild(nrem);
            }

            if (ndesc == null)
            {
                ndesc = XDoc.CreateElement("desc");
                ndesc.InnerText = "";
                ntb_ct.AppendChild(ndesc);
            }

            if (nfm_dt == null)
            {
                nfm_dt = XDoc.CreateElement("fm_dt");
                nfm_dt.InnerText = "";
                ntb_ct.AppendChild(nfm_dt);
            }

            if (nfm_pers == null)
            {
                nfm_pers = XDoc.CreateElement("fm_pers");
                nfm_pers.InnerText = "";
                ntb_ct.AppendChild(nfm_pers);
            }

            if (nedtr == null)
            {
                nedtr = XDoc.CreateElement("edtr");
                nedtr.InnerText = "";
                ntb_ct.AppendChild(nedtr);
            }

            if (nsrch_kwd == null)
            {
                nsrch_kwd = XDoc.CreateElement("srch_kwd");
                nsrch_kwd.InnerText = "";
                ntb_ct.AppendChild(nsrch_kwd);
            }

            if (ncoll_pln_doc_id == null)
            {
                ncoll_pln_doc_id = XDoc.CreateElement("coll_pln_doc_id");
                ncoll_pln_doc_id.InnerText = "";
                ntb_ct.AppendChild(ncoll_pln_doc_id);
            }

            if (nctts_code == null)
            {
                nctts_code = XDoc.CreateElement("ctts_code");
                nctts_code.InnerText = "";
                ntb_ct.AppendChild(nctts_code);
            }

            if (npgm_id == null)
            {
                npgm_id = XDoc.CreateElement("pgm_id");
                npgm_id.InnerText = "";
                ntb_ct.AppendChild(npgm_id);
            }

            if (nsub_pgm_id == null)
            {
                nsub_pgm_id = XDoc.CreateElement("sub_pgm_id");
                nsub_pgm_id.InnerText = "";
                ntb_ct.AppendChild(nsub_pgm_id);
            }


            if (ncnrnum == null)
            {
                ncnrnum = XDoc.CreateElement("cnrnum");
                ncnrnum.InnerText = "";
                ntb_ct.AppendChild(ncnrnum);
            }

            if (next_news_id == null)
            {
                next_news_id = XDoc.CreateElement("ext_news_id");
                next_news_id.InnerText = "";
                ntb_ct.AppendChild(next_news_id);
            }

            if (nadveid == null)
            {
                nadveid = XDoc.CreateElement("nadveid");
                nadveid.InnerText = "";
                ntb_ct.AppendChild(nadveid);
            }

            if (nreg_dtm == null)
            {
                nreg_dtm = XDoc.CreateElement("reg_dtm");
                nreg_dtm.InnerText = "";
                ntb_ct.AppendChild(nreg_dtm);
            }

            if (nupdt_dtm == null)
            {
                nupdt_dtm = XDoc.CreateElement("updt_dtm");
                nupdt_dtm.InnerText = "";
                ntb_ct.AppendChild(nupdt_dtm);
            }

            if (nreg_usrid == null)
            {
                nreg_usrid = XDoc.CreateElement("reg_usrid");
                nreg_usrid.InnerText = "";
                ntb_ct.AppendChild(nreg_usrid);
            }

            if (nupdt_usrid == null)
            {
                nupdt_usrid = XDoc.CreateElement("updt_usrid");
                nupdt_usrid.InnerText = "";
                ntb_ct.AppendChild(nupdt_usrid);
            }

            if (nmda_dvc_id == null)
            {
                nmda_dvc_id = XDoc.CreateElement("mda_dvc_id");
                nmda_dvc_id.InnerText = "";
                ntb_ct.AppendChild(nmda_dvc_id);
            }

            if (nstart_dtm == null)
            {
                nstart_dtm = XDoc.CreateElement("start_dtm");
                nstart_dtm.InnerText = "";
                ntb_ct.AppendChild(nstart_dtm);
            }

            if (ndelibgrd_cd == null)
            {
                ndelibgrd_cd = XDoc.CreateElement("delibgrd_cd");
                ndelibgrd_cd.InnerText = "";
                ntb_ct.AppendChild(ndelibgrd_cd);
            }

            if (nct_scr == null)
            {
                nct_scr = XDoc.CreateElement("ct_scr");
                nct_scr.InnerText = "";
                ntb_ct.AppendChild(nct_scr);
            }
        }

        public ContentsXml(FileInfo input)
        {
            XDoc = new XmlDocument();
            XDoc.Load(input.FullName);

            ntb_ct = XDoc.FirstChild;
            nct_cla = ntb_ct.SelectSingleNode("ct_cla"); // munsam
            nct_id = ntb_ct.SelectSingleNode("ct_id");
            nct_gb = ntb_ct.SelectSingleNode("ct_gb");
            nct_typ = ntb_ct.SelectSingleNode("ct_typ");
            nct_nm = ntb_ct.SelectSingleNode("ct_nm");
            nrem = ntb_ct.SelectSingleNode("rem");
            ndesc = ntb_ct.SelectSingleNode("desc");
            nfm_dt = ntb_ct.SelectSingleNode("fm_dt");
            nfm_pers = ntb_ct.SelectSingleNode("fm_pers");
            nedtr = ntb_ct.SelectSingleNode("edtr");
            nsrch_kwd = ntb_ct.SelectSingleNode("srch_kwd");
            ncoll_pln_doc_id = ntb_ct.SelectSingleNode("coll_pln_doc_id");
            nctts_code = ntb_ct.SelectSingleNode("ctts_code");
            npgm_id = ntb_ct.SelectSingleNode("pgm_id");
            nsub_pgm_id = ntb_ct.SelectSingleNode("sub_pgm_id");
            ncnrnum = ntb_ct.SelectSingleNode("cnrnum");
            next_news_id = ntb_ct.SelectSingleNode("ext_news_id");
            nadveid = ntb_ct.SelectSingleNode("adveid");
            nreg_dtm = ntb_ct.SelectSingleNode("reg_dtm");
            nupdt_dtm = ntb_ct.SelectSingleNode("updt_dtm");
            nreg_usrid = ntb_ct.SelectSingleNode("reg_usrid");
            nupdt_usrid = ntb_ct.SelectSingleNode("updt_usrid");
            nmda_dvc_id = ntb_ct.SelectSingleNode("mda_dvc_id");
            nstart_dtm = ntb_ct.SelectSingleNode("start_dtm");
            ndelibgrd_cd = ntb_ct.SelectSingleNode("delibgrd_cd");
            nct_scr = ntb_ct.SelectSingleNode("ct_scr");

            if (ntb_ct == null)
            {
                ntb_ct = XDoc.CreateElement("tb_ct");
                ntb_ct.InnerText = "";
                XDoc.AppendChild(ntb_ct);
            }

            if (nct_cla == null)
            {
                nct_cla = XDoc.CreateElement("ct_cla");
                nct_cla.InnerText = "";
                ntb_ct.AppendChild(nct_cla);
            }

            if (nct_id == null)
            {
                nct_id = XDoc.CreateElement("ct_id");
                nct_id.InnerText = "";
                ntb_ct.AppendChild(nct_id);
            }

            if (nct_gb == null)
            {
                nct_gb = XDoc.CreateElement("ct_gb");
                nct_gb.InnerText = "";
                ntb_ct.AppendChild(nct_gb);
            }

            if (nct_typ == null)
            {
                nct_typ = XDoc.CreateElement("ct_typ");
                nct_typ.InnerText = "";
                ntb_ct.AppendChild(nct_typ);
            }

            if (nct_nm == null)
            {
                nct_nm = XDoc.CreateElement("ct_nm");
                nct_nm.InnerText = "";
                ntb_ct.AppendChild(nct_nm);
            }

            if (nrem == null)
            {
                nrem = XDoc.CreateElement("rem");
                nrem.InnerText = "";
                ntb_ct.AppendChild(nrem);
            }

            if (ndesc == null)
            {
                ndesc = XDoc.CreateElement("desc");
                ndesc.InnerText = "";
                ntb_ct.AppendChild(ndesc);
            }

            if (nfm_dt == null)
            {
                nfm_dt = XDoc.CreateElement("fm_dt");
                nfm_dt.InnerText = "";
                ntb_ct.AppendChild(nfm_dt);
            }

            if (nfm_pers == null)
            {
                nfm_pers = XDoc.CreateElement("fm_pers");
                nfm_pers.InnerText = "";
                ntb_ct.AppendChild(nfm_pers);
            }

            if (nedtr == null)
            {
                nedtr = XDoc.CreateElement("edtr");
                nedtr.InnerText = "";
                ntb_ct.AppendChild(nedtr);
            }

            if (nsrch_kwd == null)
            {
                nsrch_kwd = XDoc.CreateElement("srch_kwd");
                nsrch_kwd.InnerText = "";
                ntb_ct.AppendChild(nsrch_kwd);
            }

            if (ncoll_pln_doc_id == null)
            {
                ncoll_pln_doc_id = XDoc.CreateElement("coll_pln_doc_id");
                ncoll_pln_doc_id.InnerText = "";
                ntb_ct.AppendChild(ncoll_pln_doc_id);
            }

            if (nctts_code == null)
            {
                nctts_code = XDoc.CreateElement("ctts_code");
                nctts_code.InnerText = "";
                ntb_ct.AppendChild(nctts_code);
            }

            if (npgm_id == null)
            {
                npgm_id = XDoc.CreateElement("pgm_id");
                npgm_id.InnerText = "";
                ntb_ct.AppendChild(npgm_id);
            }

            if (nsub_pgm_id == null)
            {
                nsub_pgm_id = XDoc.CreateElement("sub_pgm_id");
                nsub_pgm_id.InnerText = "";
                ntb_ct.AppendChild(nsub_pgm_id);
            }


            if (ncnrnum == null)
            {
                ncnrnum = XDoc.CreateElement("cnrnum");
                ncnrnum.InnerText = "";
                ntb_ct.AppendChild(ncnrnum);
            }

            if (next_news_id == null)
            {
                next_news_id = XDoc.CreateElement("ext_news_id");
                next_news_id.InnerText = "";
                ntb_ct.AppendChild(next_news_id);
            }

            if (nadveid == null)
            {
                nadveid = XDoc.CreateElement("nadveid");
                nadveid.InnerText = "";
                ntb_ct.AppendChild(nadveid);
            }

            if (nreg_dtm == null)
            {
                nreg_dtm = XDoc.CreateElement("reg_dtm");
                nreg_dtm.InnerText = "";
                ntb_ct.AppendChild(nreg_dtm);
            }

            if (nupdt_dtm == null)
            {
                nupdt_dtm = XDoc.CreateElement("updt_dtm");
                nupdt_dtm.InnerText = "";
                ntb_ct.AppendChild(nupdt_dtm);
            }

            if (nreg_usrid == null)
            {
                nreg_usrid = XDoc.CreateElement("reg_usrid");
                nreg_usrid.InnerText = "";
                ntb_ct.AppendChild(nreg_usrid);
            }

            if (nupdt_usrid == null)
            {
                nupdt_usrid = XDoc.CreateElement("updt_usrid");
                nupdt_usrid.InnerText = "";
                ntb_ct.AppendChild(nupdt_usrid);
            }

            if (nmda_dvc_id == null)
            {
                nmda_dvc_id = XDoc.CreateElement("mda_dvc_id");
                nmda_dvc_id.InnerText = "";
                ntb_ct.AppendChild(nmda_dvc_id);
            }

            if (nstart_dtm == null)
            {
                nstart_dtm = XDoc.CreateElement("start_dtm");
                nstart_dtm.InnerText = "";
                ntb_ct.AppendChild(nstart_dtm);
            }

            if (ndelibgrd_cd == null)
            {
                ndelibgrd_cd = XDoc.CreateElement("delibgrd_cd");
                ndelibgrd_cd.InnerText = "";
                ntb_ct.AppendChild(ndelibgrd_cd);
            }

            if (nct_scr == null)
            {
                nct_scr = XDoc.CreateElement("ct_scr");
                nct_scr.InnerText = "";
                ntb_ct.AppendChild(nct_scr);
            }
        }

        //munsam
#if true
        public string ct_scr
        {
            get { return System.Web.HttpUtility.UrlDecode(nct_scr.InnerText); }
            set { nct_scr.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }

        public string delibgrd_cd
        {
            get { return System.Web.HttpUtility.UrlDecode(ndelibgrd_cd.InnerText); }
            set { ndelibgrd_cd.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }

        public string ct_cla
        {
            get { return System.Web.HttpUtility.UrlDecode(nct_cla.InnerText); }
            set { nct_cla.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
#endif

        public string ct_id
        {
            get { return System.Web.HttpUtility.UrlDecode(nct_id.InnerText); }
            set { nct_id.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string ct_gb
        {
            get { return System.Web.HttpUtility.UrlDecode(nct_gb.InnerText); }
            set { nct_gb.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string ct_typ
        {
            get { return System.Web.HttpUtility.UrlDecode(nct_typ.InnerText); }
            set { nct_typ.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string ct_nm
        {
            get { return System.Web.HttpUtility.UrlDecode(nct_nm.InnerText); }
            set { nct_nm.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string rem
        {
            get { return System.Web.HttpUtility.UrlDecode(nrem.InnerText); }
            set { nrem.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string desc
        {
            get { return System.Web.HttpUtility.UrlDecode(ndesc.InnerText); }
            set { ndesc.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string fm_dt
        {
            get { return System.Web.HttpUtility.UrlDecode(nfm_dt.InnerText); }
            set { nfm_dt.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string fm_pers
        {
            get { return System.Web.HttpUtility.UrlDecode(nfm_pers.InnerText); }
            set { nfm_pers.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string edtr
        {
            get { return System.Web.HttpUtility.UrlDecode(nedtr.InnerText); }
            set { nedtr.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string srch_kwd
        {
            get { return System.Web.HttpUtility.UrlDecode(nsrch_kwd.InnerText); }
            set { nsrch_kwd.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string coll_pln_doc_id
        {
            get { return System.Web.HttpUtility.UrlDecode(ncoll_pln_doc_id.InnerText); }
            set { ncoll_pln_doc_id.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string ctts_code
        {
            get { return System.Web.HttpUtility.UrlDecode(nctts_code.InnerText); }
            set { nctts_code.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string pgm_id
        {
            get { return System.Web.HttpUtility.UrlDecode(npgm_id.InnerText); }
            set { npgm_id.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string sub_pgm_id
        {
            get { return System.Web.HttpUtility.UrlDecode(nsub_pgm_id.InnerText); }
            set { nsub_pgm_id.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        //ncnrnum = ntb_ct.SelectSingleNode("cnrnum");
        public string cnrnum
        {
            get { return System.Web.HttpUtility.UrlDecode(ncnrnum.InnerText); }
            set { ncnrnum.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string ext_news_id
        {
            get { return System.Web.HttpUtility.UrlDecode(next_news_id.InnerText); }
            set { next_news_id.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string adve_id
        {
            get { return System.Web.HttpUtility.UrlDecode(nadveid.InnerText); }
            set { nadveid.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string reg_dtm
        {
            get { return System.Web.HttpUtility.UrlDecode(nreg_dtm.InnerText); }
            set { nreg_dtm.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string updt_dtm
        {
            get { return System.Web.HttpUtility.UrlDecode(nupdt_dtm.InnerText); }
            set { nupdt_dtm.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string reg_usrid
        {
            get { return System.Web.HttpUtility.UrlDecode(nreg_usrid.InnerText); }
            set { nreg_usrid.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }
        public string updt_usrid
        {
            get { return System.Web.HttpUtility.UrlDecode(nupdt_usrid.InnerText); }
            set { nupdt_usrid.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }

        public string mda_dvc_id
        {
            get { return System.Web.HttpUtility.UrlDecode(nmda_dvc_id.InnerText); }
            set { nmda_dvc_id.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }

        public string start_dtm
        {
            get { return System.Web.HttpUtility.UrlDecode(nstart_dtm.InnerText); }
            set { nstart_dtm.InnerText = System.Web.HttpUtility.UrlEncode(value); }
        }

        public string WholeText
        {
            get { return ntb_ct.OuterXml; }
        }
    }

    public class ListItemClass
    {
        private XmlDocument XDoc = null;
        private XmlNode ntb_ct = null;
        private XmlNode nct_id = null;
        private XmlNode nmxfcti_id = null;
        private XmlNode nwmvcti_id = null;
        private XmlNode npgm_id = null;
        private XmlNode npgm_nm = null;
        private XmlNode nsub_pgm_id = null;
        private XmlNode nsub_pgm_nm = null;
        private XmlNode ncnr_num = null;
        private XmlNode ncnr_nm = null;
        private XmlNode nprodrreq_id = null;
        private XmlNode nprodrreq_nm = null;
        private XmlNode nfmdrreq_id = null;
        private XmlNode nfmdrreq_nm = null;
        private XmlNode nnews_head = null;
        private XmlNode nct_ts = null;
        private XmlNode nnews_pick_id = null;
        private XmlNode nnews_pick_nm = null;
        private XmlNode nct_gb = null;

        /// <summary>
        /// 
        /// </summary>
        public string tb_ct
        {
            get { return ntb_ct.InnerText; }
            set { ntb_ct.InnerText = value; }
        }
        public string ct_id
        {
            get { return nct_id.InnerText; }
            set { nct_id.InnerText = value; }
        }
        public string mxfcti_id
        {
            get { return nmxfcti_id.InnerText; }
            set { nmxfcti_id.InnerText = value; }
        }
        public string wmvcti_id
        {
            get { return nwmvcti_id.InnerText; }
            set { nwmvcti_id.InnerText = value; }
        }
        public string pgm_id
        {
            get { return npgm_id.InnerText; }
            set { npgm_id.InnerText = value; }
        }
        public string pgm_nm
        {
            get { return npgm_nm.InnerText; }
            set { npgm_nm.InnerText = value; }
        }
        public string sub_pgm_id
        {
            get { return nsub_pgm_id.InnerText; }
            set { nsub_pgm_id.InnerText = value; }
        }
        public string sub_pgm_nm
        {
            get { return nsub_pgm_nm.InnerText; }
            set { nsub_pgm_nm.InnerText = value; }
        }
        public string cnr_num
        {
            get { return ncnr_num.InnerText; }
            set { ncnr_num.InnerText = value; }
        }
        public string cnr_nm
        {
            get { return ncnr_nm.InnerText; }
            set { ncnr_nm.InnerText = value; }
        }
        public string prodrreq_id
        {
            get { return nprodrreq_id.InnerText; }
            set { nprodrreq_id.InnerText = value; }
        }
        public string prodrreq_nm
        {
            get { return nprodrreq_nm.InnerText; }
            set { nprodrreq_nm.InnerText = value; }
        }
        public string fmdrreq_id
        {
            get { return nfmdrreq_id.InnerText; }
            set { nfmdrreq_id.InnerText = value; }
        }
        public string fmdrreq_nm
        {
            get { return nfmdrreq_nm.InnerText; }
            set { nfmdrreq_nm.InnerText = value; }
        }
        public string news_head
        {
            get { return nnews_head.InnerText; }
            set { nnews_head.InnerText = value; }
        }
        public string ct_ts
        {
            get { return nct_ts.InnerText; }
            set { nct_ts.InnerText = value; }
        }
        public string news_pick_id
        {
            get { return nnews_pick_id.InnerText; }
            set { nnews_pick_id.InnerText = value; }
        }
        public string news_pick_nm
        {
            get { return nnews_pick_nm.InnerText; }
            set { nnews_pick_nm.InnerText = value; }
        }
        public string ct_gb
        {
            get { return nct_gb.InnerText; }
            set { nct_gb.InnerText = value; }
        }

        public string WholeText
        {
            get { return XDoc.OuterXml; }
        }

        public ListItemClass()
        {
            XDoc = new XmlDocument();
            ntb_ct = XDoc.CreateElement("tb_ct");
            ntb_ct.InnerText = "";
            XDoc.AppendChild(ntb_ct);
            nct_id = XDoc.CreateElement("ct_id");
            nct_id.InnerText = "";
            ntb_ct.AppendChild(nct_id);
            nmxfcti_id = XDoc.CreateElement("mxfcti_id");
            nmxfcti_id.InnerText = "";
            ntb_ct.AppendChild(nmxfcti_id);
            nwmvcti_id = XDoc.CreateElement("wmvcti_id");
            nwmvcti_id.InnerText = "";
            ntb_ct.AppendChild(nwmvcti_id);
            npgm_id = XDoc.CreateElement("pgm_id");
            npgm_id.InnerText = "";
            ntb_ct.AppendChild(npgm_id);
            npgm_nm = XDoc.CreateElement("pgm_nm");
            npgm_nm.InnerText = "";
            ntb_ct.AppendChild(npgm_nm);
            nsub_pgm_id = XDoc.CreateElement("sub_pgm_id");
            nsub_pgm_id.InnerText = "";
            ntb_ct.AppendChild(nsub_pgm_id);
            nsub_pgm_nm = XDoc.CreateElement("sub_pgm_nm");
            nsub_pgm_nm.InnerText = "";
            ntb_ct.AppendChild(nsub_pgm_nm);
            ncnr_num = XDoc.CreateElement("cnr_num");
            ncnr_num.InnerText = "";
            ntb_ct.AppendChild(ncnr_num);
            ncnr_nm = XDoc.CreateElement("cnr_nm");
            ncnr_nm.InnerText = "";
            ntb_ct.AppendChild(ncnr_nm);
            nprodrreq_id = XDoc.CreateElement("prodrreq_id");
            nprodrreq_id.InnerText = "";
            ntb_ct.AppendChild(nprodrreq_id);
            nprodrreq_nm = XDoc.CreateElement("prodrreq_nm");
            nprodrreq_nm.InnerText = "";
            ntb_ct.AppendChild(nprodrreq_nm);
            nfmdrreq_id = XDoc.CreateElement("fmdrreq_id");
            nfmdrreq_id.InnerText = "";
            ntb_ct.AppendChild(nfmdrreq_id);
            nfmdrreq_nm = XDoc.CreateElement("fmdrreq_nm");
            nfmdrreq_nm.InnerText = "";
            ntb_ct.AppendChild(nfmdrreq_nm);
            nnews_head = XDoc.CreateElement("news_head");
            nnews_head.InnerText = "";
            ntb_ct.AppendChild(nnews_head);
            nct_ts = XDoc.CreateElement("ct_ts");
            nct_ts.InnerText = "";
            ntb_ct.AppendChild(nct_ts);
            nnews_pick_id = XDoc.CreateElement("news_pick_id");
            nnews_pick_id.InnerText = "";
            ntb_ct.AppendChild(nnews_pick_id);
            nnews_pick_nm = XDoc.CreateElement("news_pick_nm");
            nnews_pick_nm.InnerText = "";
            ntb_ct.AppendChild(nnews_pick_nm);
            nct_gb = XDoc.CreateElement("ct_gb");
            nct_gb.InnerText = "";
            ntb_ct.AppendChild(nct_gb);

        }

        public ListItemClass(FileInfo Input)
        {
            string FileName = Input.FullName;
            XDoc = new XmlDocument();
            XDoc.Load(FileName);
            ntb_ct = XDoc.SelectSingleNode("ntb_ct");
            nmxfcti_id = ntb_ct.SelectSingleNode("mxfcti_id");
            nwmvcti_id = ntb_ct.SelectSingleNode("wmvcti_id");
            npgm_id = ntb_ct.SelectSingleNode("pgm_id");
            npgm_nm = ntb_ct.SelectSingleNode("pgm_nm");
            nsub_pgm_id = ntb_ct.SelectSingleNode("sub_pgm_id");
            nsub_pgm_nm = ntb_ct.SelectSingleNode("sub_pgm_nm");
            ncnr_num = ntb_ct.SelectSingleNode("cnr_num");
            ncnr_nm = ntb_ct.SelectSingleNode("cnr_nm");
            nprodrreq_id = ntb_ct.SelectSingleNode("prodrreq_id");
            nprodrreq_nm = ntb_ct.SelectSingleNode("prodrreq_nm");
            nfmdrreq_id = ntb_ct.SelectSingleNode("fmdrreq_id");
            nfmdrreq_nm = ntb_ct.SelectSingleNode("fmdrreq_nm");
            nnews_head = ntb_ct.SelectSingleNode("news_head");
            nct_ts = ntb_ct.SelectSingleNode("ct_ts");
            nnews_pick_id = ntb_ct.SelectSingleNode("news_pick_id");
            nnews_pick_nm = ntb_ct.SelectSingleNode("news_pick_nm");
            nct_gb = ntb_ct.SelectSingleNode("ct_gb");
        }

        public ListItemClass(string Input)
        {
            XDoc = new XmlDocument();
            XDoc.LoadXml(Input);
            ntb_ct = XDoc.SelectSingleNode("ntb_ct");
            nmxfcti_id = ntb_ct.SelectSingleNode("mxfcti_id");
            nwmvcti_id = ntb_ct.SelectSingleNode("wmvcti_id");
            npgm_id = ntb_ct.SelectSingleNode("pgm_id");
            npgm_nm = ntb_ct.SelectSingleNode("pgm_nm");
            nsub_pgm_id = ntb_ct.SelectSingleNode("sub_pgm_id");
            nsub_pgm_nm = ntb_ct.SelectSingleNode("sub_pgm_nm");
            ncnr_num = ntb_ct.SelectSingleNode("cnr_num");
            ncnr_nm = ntb_ct.SelectSingleNode("cnr_nm");
            nprodrreq_id = ntb_ct.SelectSingleNode("prodrreq_id");
            nprodrreq_nm = ntb_ct.SelectSingleNode("prodrreq_nm");
            nfmdrreq_id = ntb_ct.SelectSingleNode("fmdrreq_id");
            nfmdrreq_nm = ntb_ct.SelectSingleNode("fmdrreq_nm");
            nnews_head = ntb_ct.SelectSingleNode("news_head");
            nct_ts = ntb_ct.SelectSingleNode("ct_ts");
            nnews_pick_id = ntb_ct.SelectSingleNode("news_pick_id");
            nnews_pick_nm = ntb_ct.SelectSingleNode("news_pick_nm");
            nct_gb = ntb_ct.SelectSingleNode("ct_gb");
        }


    }

    public class XmlGenerateClass
    {
        private string ExtString = "";

        public XmlGenerateClass()
        {
            ExtString = "";
        }

        public void GenerateMethod_FullName(string FileName)
        {
            StreamReader sr = new StreamReader(FileName);
            StreamWriter sw = new StreamWriter(FileName + ".cs");
            while (!sr.EndOfStream)
            {
                string str = sr.ReadLine();
                System.Diagnostics.Debug.WriteLine(str);
                string output = "";
                string strNew = str.Remove(0, 1);
                output = "public string " + strNew + "\n" +
                    "{\n" +
                    "   get { return " + str + ".InnerText; }\n" +
                    "   set { " + str + ".InnerText = value; }\n" +
                    "}\n";
                sw.Write(output);
            }
            sr.Close();
            sw.Close();


        }

        public void GenerateMethod_OnlyName(string FileName)
        {
            StreamReader sr = new StreamReader(FileName);
            StreamWriter sw = new StreamWriter(FileName + ".cs");
            while (!sr.EndOfStream)
            {
                string str = sr.ReadLine();
                System.Diagnostics.Debug.WriteLine(str);
                string output = "";
                output = "public string " + str + "\n" +
                    "{\n" +
                    "   get { return n" + str + ".InnerText; }\n" +
                    "   set { n" + str + ".InnerText = value; }\n" +
                    "}\n";
                sw.Write(output);
            }
            sr.Close();
            sw.Close();

        }

        public void GenerateWholeNewXml_OnlyName(string FileName)
        {
            StreamReader sr = new StreamReader(FileName);
            StreamWriter sw = new StreamWriter(FileName + ".cs");
            string FirstString = "";
            int i = 0;
            while (!sr.EndOfStream)
            {
                string str = sr.ReadLine();
                System.Diagnostics.Debug.WriteLine(str);
                string Output = "";

                if (i == 0)
                {
                    FirstString = str;
                    Output =
                        "n" + str + " = XDoc.CreateElement(\"" + str + "\");\n" +
                        "n" + str + ".InnerText = \"\";\n" +
                        "XDoc.AppendChild(n" + str + ");\n";
                    sw.Write(Output);
                    i++;
                    continue;
                }

                Output =
                    "n" + str + " = n" + FirstString + ".CreateElement(\"" + str + "\");\n" +
                    "n" + str + ".InnerText = \"\";\n" +
                    "n" + FirstString + ".AppendChild(n" + str + ");\n";
                sw.Write(Output);
                i++;
            }
            sr.Close();
            sw.Close();
        }

        public void GenerateWholeNewXml_FullName(string FileName)
        {
            StreamReader sr = new StreamReader(FileName);
            StreamWriter sw = new StreamWriter(FileName + ".cs");
            string FirstString = "";
            int i = 0;
            while (!sr.EndOfStream)
            {
                string str = sr.ReadLine();
                System.Diagnostics.Debug.WriteLine(str);
                string Output = "";
                string TempString = "";

                if (i == 0)
                {
                    FirstString = str;
                    TempString = str.Remove(0, 1);
                    Output =
                        "n" + TempString + " = XDoc.CreateElement(\"" + TempString + "\");\n" +
                        "n" + TempString + ".InnerText = \"\";\n" +
                        "XDoc.AppendChild(n" + TempString + ");\n";
                    sw.Write(Output);
                    i++;
                    continue;
                }
                TempString = str.Remove(0, 1);
                Output =
                    "n" + TempString + " = XDoc.CreateElement(\"" + TempString + "\");\n" +
                    "n" + TempString + ".InnerText = \"\";\n" +
                    FirstString + ".AppendChild(n" + TempString + ");\n";
                sw.Write(Output);
                i++;
            }
            sr.Close();
            sw.Close();
        }

        public void GenerateNewXmlByFileOrText_OnlyName(string FileName)
        {
            StreamReader sr = new StreamReader(FileName);
            StreamWriter sw = new StreamWriter(FileName + ".cs");
            string FirstString = "";
            int i = 0;
            while (!sr.EndOfStream)
            {
                string str = sr.ReadLine();
                System.Diagnostics.Debug.WriteLine(str);
                string Output = "";
                if (i == 0)
                {
                    FirstString = str;
                    Output =
                        "n" + str + " = XDoc.FirstChild\n";
                    sw.Write(Output);
                    i++;
                    continue;
                }

                Output =
                    "n" + str + " = n" + FirstString + ".SelectSingleNode(\"" + str + "\");\n";
                sw.Write(Output);
                i++;
            }
            sr.Close();
            sw.Close();
        }

        public void GenerateNewXmlByFileOrText_FullName(string FileName)
        {
            StreamReader sr = new StreamReader(FileName);
            StreamWriter sw = new StreamWriter(FileName + ".cs");
            string FirstString = "";
            int i = 0;
            while (!sr.EndOfStream)
            {
                string str = sr.ReadLine();
                System.Diagnostics.Debug.WriteLine(str);
                string Output = "";
                if (i == 0)
                {
                    FirstString = str;
                    str = str.Remove(0, 1);
                    Output =
                        "n" + str + " = XDoc.FirstChild\n";
                    i++;
                    continue;
                }
                str = str.Remove(0, 1);
                Output =
                    "n" + str + " = " + FirstString + ".SelectSingleNode(\"" + str + "\");\n";
                sw.Write(Output);
                i++;
            }
            sr.Close();
            sw.Close();
        }
    }

    public class AdditionalEXTClass
    {
        public string EXT = "";
        public string VideoCodec = "";
        public string AudioCodec = "";
        public string SourceFileType = "";

        public AdditionalEXTClass()
        {
            EXT = "";
            VideoCodec = "";
            AudioCodec = "";
            SourceFileType = "";
        }

    }

    public class WorkOrderXML
    {
        private XmlDocument XDoc = null;
        private XmlNode Root = null;
        private XmlNode nctid = null;
        private XmlNode nFilePath = null;
        private XmlNode nDestFilePath = null;
        private XmlNode nProfilePath = null;
        private XmlNode nWMVWorkflowUrn = null;
        private XmlNode nParamName = null;
        private XmlNode nAsyncMsc = null;
        private XmlNode nAsyncUrn = null;
        private XmlNode nUrl = null;
        private XmlNode nCTIID_Urn = null;
        private XmlNode nmda_dvc_id = null;
        private XmlNode nwmv_Target_dir = null;
        private XmlNode nMetaXmlPath = null;
        private XmlNode DataSec = null;
        private tb_cti_Class _Data = null;

        public WorkOrderXML()
        {
            XDoc = new XmlDocument();


            Root = XDoc.CreateElement("Root");
            Root.InnerText = "";
            XDoc.AppendChild(Root);

            nctid = XDoc.CreateElement("ctid");
            nctid.InnerText = "";
            Root.AppendChild(nctid);

            nFilePath = XDoc.CreateElement("FilePath");
            nFilePath.InnerText = "";
            Root.AppendChild(nFilePath);

            nDestFilePath = XDoc.CreateElement("DestFilePath");
            nDestFilePath.InnerText = "";
            Root.AppendChild(nDestFilePath);

            nProfilePath = XDoc.CreateElement("ProfilePath");
            nProfilePath.InnerText = "";
            Root.AppendChild(nProfilePath);

            nWMVWorkflowUrn = XDoc.CreateElement("WMVWorkflowUrn");
            nWMVWorkflowUrn.InnerText = "";
            Root.AppendChild(nWMVWorkflowUrn);

            nParamName = XDoc.CreateElement("ParamName");
            nParamName.InnerText = "";
            Root.AppendChild(nParamName);

            nAsyncMsc = XDoc.CreateElement("AsyncMsc");
            nAsyncMsc.InnerText = "";
            Root.AppendChild(nAsyncMsc);

            nAsyncUrn = XDoc.CreateElement("AsyncUrn");
            nAsyncUrn.InnerText = "";
            Root.AppendChild(nAsyncUrn);

            nUrl = XDoc.CreateElement("Url");
            nUrl.InnerText = "";
            Root.AppendChild(nUrl);

            nCTIID_Urn = XDoc.CreateElement("CTIID_Urn");
            nCTIID_Urn.InnerText = "";
            Root.AppendChild(nCTIID_Urn);

            nmda_dvc_id = XDoc.CreateElement("mda_dvc_id");
            nmda_dvc_id.InnerText = "";
            Root.AppendChild(nmda_dvc_id);

            nwmv_Target_dir = XDoc.CreateElement("wmv_Target_dir");
            nwmv_Target_dir.InnerText = "";
            Root.AppendChild(nwmv_Target_dir);

            nMetaXmlPath = XDoc.CreateElement("MetaXmlPath");
            nMetaXmlPath.InnerText = "";
            Root.AppendChild(nMetaXmlPath);



            DataSec = XDoc.CreateCDataSection("DataSec");
            DataSec.InnerText = "";
            Root.AppendChild(DataSec);

            _Data = new tb_cti_Class();
        }

        public WorkOrderXML(string str)
        {
            XDoc = new XmlDocument();
            XDoc.LoadXml(str);

            Root = XDoc.FirstChild;
            nctid = Root.SelectSingleNode("ctid");
            nFilePath = Root.SelectSingleNode("FilePath");
            nDestFilePath = Root.SelectSingleNode("DestFilePath");
            nProfilePath = Root.SelectSingleNode("ProfilePath");
            nWMVWorkflowUrn = Root.SelectSingleNode("WMVWorkflowUrn");
            nParamName = Root.SelectSingleNode("ParamName");
            nAsyncMsc = Root.SelectSingleNode("AsyncMsc");
            nAsyncUrn = Root.SelectSingleNode("AsyncUrn");
            nUrl = Root.SelectSingleNode("Url");
            nCTIID_Urn = Root.SelectSingleNode("CTIID_Urn");
            nmda_dvc_id = Root.SelectSingleNode("mda_dvc_id");
            nwmv_Target_dir = Root.SelectSingleNode("wmv_Target_dir");
            nMetaXmlPath = Root.SelectSingleNode("MetaXmlPath");
            if (Root.LastChild.GetType().ToString() == "System.Xml.XmlCDataSection")
            {
                DataSec = Root.LastChild;
                _Data = new tb_cti_Class(DataSec.InnerText);
            }
            else
            {
                DataSec = XDoc.CreateCDataSection("DataSec");
                DataSec.InnerText = "";
                Root.AppendChild(DataSec);

                _Data = new tb_cti_Class();
            }
        }

        public WorkOrderXML(FileInfo fi)
        {
            XDoc = new XmlDocument();
            XDoc.Load(fi.FullName);

            Root = XDoc.FirstChild;
            nctid = Root.SelectSingleNode("ctid");
            nFilePath = Root.SelectSingleNode("FilePath");
            nDestFilePath = Root.SelectSingleNode("DestFilePath");
            nProfilePath = Root.SelectSingleNode("ProfilePath");
            nWMVWorkflowUrn = Root.SelectSingleNode("WMVWorkflowUrn");
            nParamName = Root.SelectSingleNode("ParamName");
            nAsyncMsc = Root.SelectSingleNode("AsyncMsc");
            nAsyncUrn = Root.SelectSingleNode("AsyncUrn");
            nUrl = Root.SelectSingleNode("Url");
            nCTIID_Urn = Root.SelectSingleNode("CTIID_Urn");
            nmda_dvc_id = Root.SelectSingleNode("mda_dvc_id");
            nwmv_Target_dir = Root.SelectSingleNode("wmv_Target_dir");
            nMetaXmlPath = Root.SelectSingleNode("MetaXmlPath");
            if (Root.LastChild.GetType().ToString() == "System.Xml.XmlCDataSection")
            {
                DataSec = Root.LastChild;
                _Data = new tb_cti_Class(DataSec.InnerText);
            }
            else
            {
                DataSec = XDoc.CreateCDataSection("DataSec");
                DataSec.InnerText = "";
                Root.AppendChild(DataSec);

                _Data = new tb_cti_Class();
            }
        }

        public string ctid
        {
            get { return nctid.InnerText; }
            set { nctid.InnerText = value; }
        }

        public string mda_dvc_id
        {
            get { return nmda_dvc_id.InnerText; }
            set { nmda_dvc_id.InnerText = value; }
        }

        public string CTIID_Urn
        {
            get { return nCTIID_Urn.InnerText; }
            set { nCTIID_Urn.InnerText = value; }
        }

        public string FilePath
        {
            get { return nFilePath.InnerText; }
            set { nFilePath.InnerText = value; }
        }
        public string DestFilePath
        {
            get { return nDestFilePath.InnerText; }
            set { nDestFilePath.InnerText = value; }
        }

        public string ProfilePath
        {
            get { return nProfilePath.InnerText; }
            set { nProfilePath.InnerText = value; }
        }

        public string WMVWorkflowUrn
        {
            get { return nWMVWorkflowUrn.InnerText; }
            set { nWMVWorkflowUrn.InnerText = value; }
        }
        public string ParamName
        {
            get { return nParamName.InnerText; }
            set { nParamName.InnerText = value; }
        }
        public string AsyncMsc
        {
            get { return nAsyncMsc.InnerText; }
            set { nAsyncMsc.InnerText = value; }
        }
        public string AsyncUrn
        {
            get { return nAsyncUrn.InnerText; }
            set { nAsyncUrn.InnerText = value; }
        }
        public string Url
        {
            get { return nUrl.InnerText; }
            set { nUrl.InnerText = value; }
        }

        public string wmv_Target_dir
        {
            get { if (nwmv_Target_dir != null)return nwmv_Target_dir.InnerText; else return ""; }
            set { nwmv_Target_dir.InnerText = value; }
        }

        public string MetaXmlPath
        {
            get { if (nMetaXmlPath != null) return nMetaXmlPath.InnerText; else return ""; }
            set { if (nMetaXmlPath != null)nMetaXmlPath.InnerText = value; }
        }

        public void Save(string path)
        {
            if (XDoc != null)
            {
                XDoc.Save(path);
            }
        }

        public tb_cti_Class metadata
        {
            get
            {
                return _Data;
            }
            set
            {
                _Data = value;
                if (DataSec != null)
                {
                    DataSec.InnerText = ((tb_cti_Class)value).WholeText;
                }
            }
        }
    }

    public class NoAssinedWorkflowClass
    {
        /*<workflow>
        <req_gb></req_gb> <!-- 1(insert), 2(update), 3(delete)
        <news_head></news_head> <!-- 뉴스 제목 -->
        <news_content></news_content> <!-- 뉴스 내용 -->
        <news_usrw></news_usrw>    <!-- 작성자 -->
        <news_dtmw></news_dtmw>    <!-- 작성시간 -->
        <news_kind></news_kind>    <!-- 중분류 --> 
        <news_divd></news_divd>    <!-- 소분류 -->
        </workflow>
        */
        private XmlDocument XDoc = null;
        private XmlNode nworkflow = null;
        private XmlNode nreq_gb = null;
        private XmlNode nnews_head = null;
        private XmlNode nnews_content = null;
        private XmlNode nnews_usrw = null;
        private XmlNode nnews_dtmw = null;
        //private XmlNode nnews_kind = null;
        //private XmlNode nnews_divd = null;

        public NoAssinedWorkflowClass()
        {
            XDoc = new XmlDocument();

            nworkflow = XDoc.CreateElement("workflow");
            nworkflow.InnerText = "";
            XDoc.AppendChild(nworkflow);

            nreq_gb = XDoc.CreateElement("req_gb");
            nreq_gb.InnerText = "";
            nworkflow.AppendChild(nreq_gb);

            nnews_head = XDoc.CreateElement("news_head");
            nnews_head.InnerText = "";
            nworkflow.AppendChild(nnews_head);

            nnews_content = XDoc.CreateElement("news_content");
            nnews_content.InnerText = "";
            nworkflow.AppendChild(nnews_content);

            nnews_usrw = XDoc.CreateElement("news_usrw");
            nnews_usrw.InnerText = "";
            nworkflow.AppendChild(nnews_usrw);

            nnews_dtmw = XDoc.CreateElement("news_dtmw");
            nnews_dtmw.InnerText = "";
            nworkflow.AppendChild(nnews_dtmw);

            /*nnews_kind = XDoc.CreateElement("news_kind");
            nnews_kind.InnerText = "";
            nworkflow.AppendChild(nnews_kind);

            nnews_divd = XDoc.CreateElement("news_divd");
            nnews_divd.InnerText = "";
            nworkflow.AppendChild(nnews_divd);*/
        }

        public string req_gb
        {
            get
            {
                if (nreq_gb == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nreq_gb.InnerText);
            }
            set
            {
                if (nreq_gb == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nreq_gb.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string news_head
        {
            get
            {
                if (nnews_head == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nnews_head.InnerText);
            }
            set
            {
                if (nnews_head == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nnews_head.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string news_content
        {
            get
            {
                if (nnews_content == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nnews_content.InnerText);
            }
            set
            {
                if (nnews_content == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nnews_content.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string news_usrw
        {
            get
            {
                if (nnews_usrw == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nnews_usrw.InnerText);
            }
            set
            {
                if (nnews_usrw == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nnews_usrw.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string news_dtmw
        {
            get
            {
                if (nnews_dtmw == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nnews_dtmw.InnerText);
            }
            set
            {
                if (nnews_dtmw == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nnews_dtmw.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        /*public string news_kind
        {
            get
            {
                if (nnews_kind == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nnews_kind.InnerText);
            }
            set
            {
                if (nnews_kind == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nnews_kind.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }

        public string news_divd
        {
            get
            {
                if (nnews_divd == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }

                return System.Web.HttpUtility.UrlDecode(nnews_divd.InnerText);
            }
            set
            {
                if (nnews_divd == null)
                {
                    throw new Exception("Node 값이 없습니다");
                }
                nnews_divd.InnerText = System.Web.HttpUtility.UrlEncode(value);
            }
        }*/

        public string WholeString
        {
            get { return XDoc.InnerXml; }
        }
    }
}
