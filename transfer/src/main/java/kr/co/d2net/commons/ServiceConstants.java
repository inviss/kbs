package kr.co.d2net.commons;

public class ServiceConstants {
	
	public static final String WORKFLOW_START = "WF_S";
	public static final String TRANSFER_START = "TF_S";
	public static final String EQSTATE_START = "ES_S";
	
	public static final String WORKFLOW_SAVE = "WF_S"; //CT,HCTI,LCTI
	public static final String CT_HCTI_SAVE = "CH_S"; //CT,HCTI
	public static final String CT_LCTI_SAVE = "CL_S"; //CT,LCTI
	public static final String CT_LCTI_CATALOG_SAVE = "CLC_S"; //CT,LCTI
	
	public static final String CT_ONLY_SAVE = "CO_S"; //CT
	public static final String CT_ONLY_UPDATE = "CL_U";
	public static final String CT_ONLY_DELETE = "CO_D";
	
	public static final String HCTI_LCTI_SAVE = "HL_S"; //HCTI,LCTI
	public static final String HCTI_LCTI_UPDATE = "HL_U";
	public static final String HCTI_LCTI_DELETE = "HL_D";
	
	public static final String HCTI_LCTI_CATALOG_SAVE = "HLC_S"; //HCTI,LCTI,CATALOG
	public static final String HCTI_LCTI_CATALOG_UPDATE = "HLC_U";
	public static final String HCTI_LCTI_CATALOG_DELETE = "HLC_D";
	
	public static final String HCTI_ONLY_SAVE = "HO_S"; //HCTI
	public static final String HCTI_ONLY_UPDATE = "HO_U";
	public static final String HCTI_ONLY_DELETE = "HO_D";
	
	public static final String HCTI_ONLY_LOC_SAVE = "HO_LS";
	public static final String HCTI_ONLY_LOC_UPDATE = "HO_LU";
	public static final String HCTI_ONLY_LOC_DELETE = "HO_LD";
	
	public static final String LCTI_CATALOG_SAVE = "LC_S"; //LCTI & CATALOG
	public static final String LCTI_CATALOG_UPDATE = "LC_U";
	public static final String LCTI_CATALOG_DELETE = "LC_D";
	
	public static final String LCTI_ONLY_SAVE = "LO_S"; //LCTI
	public static final String LCTI_ONLY_UPDATE = "LO_U";
	public static final String LCTI_ONLY_DELETE = "LO_D";
	
	public static final String LCTI_ONLY_LOC_SAVE = "LO_LS";
	public static final String LCTI_ONLY_LOC_UPDATE = "LO_LU";
	public static final String LCTI_ONLY_LOC_DELETE = "LO_LD";
	
	public static final String KFRM_ONLY_SAVE = "KT_S"; //CATALOG
	public static final String KFRM_ONLY_UPDATE = "KT_U";
	public static final String KFRM_ONLY_DELETE = "KT_D";
	
	public static final String HCTI_TRSF_REQUEST = "HT_Q"; //HCTI Transfer
	public static final String HCTI_TRSF_RESUME = "HT_R";
	public static final String HCTI_TRSF_STOP = "HT_T";
	
	public static final String HCTI_TRSF_SAVE = "HT_S";
	public static final String HCTI_TRSF_UPDATE = "HT_U";
	public static final String HCTI_TRSF_DELETE = "HT_D";
	
	public static final String LCTI_TRSF_REQUEST = "LT_Q"; //LCTI Transfer
	public static final String LCTI_TRSF_RESUME = "LT_R";
	public static final String LCTI_TRSF_STOP = "LT_T";
	
	public static final String LCTI_TRSF_SAVE = "LT_S";
	public static final String LCTI_TRSF_UPDATE = "LT_U";
	public static final String LCTI_TRSF_DELETE = "LT_D";
	
	public static final String HCTI_TRCD_SAVE = "HR_S";
	public static final String HCTI_TRCD_UPDATE = "HR_U";
	public static final String HCTI_TRCD_DELETE = "HR_D";
	
	public static final String HCTI_TRCD_REQUEST = "HR_Q"; //HCTI Transcode
	public static final String HCTI_TRCD_RESUME = "HR_R";
	public static final String HCTI_TRCD_STOP = "HR_T";
	
	public static final String LCTI_TRCD_SAVE = "LR_S";
	public static final String LCTI_TRCD_UPDATE = "LR_U";
	public static final String LCTI_TRCD_DELETE = "LR_D";
	
	public static final String LCTI_TRCD_REQUEST = "LR_Q"; //LCTI Transcode
	public static final String LCTI_TRCD_RESUME = "LR_R";
	public static final String LCTI_TRCD_STOP = "LR_T";
	
	public static final String HCTI_ARCH_REQUEST = "HA_Q"; //HCTI Archive
	public static final String HCTI_ARCH_RESUME = "HA_R";
	public static final String HCTI_ARCH_STOP = "HA_T";
	
	public static final String PROJ_ONLY_SAVE = "PJ_S"; //Project File
	public static final String PROJ_ONLY_UPDATE = "PJ_U";
	public static final String PROJ_ONLY_DELETE= "PJ_D";
	public static final String PROJ_ONLY_LOC= "PJ_L";
	
	public static final String PROJ_TRSF_REQUEST = "PT_Q"; //Project File Transfer
	public static final String PROJ_TRSF_RESUME = "PT_R";
	
	public static final String TRSF_QUEUE_ADD = "TQ_A";
	public static final String TRSF_QUEUE_GET = "TQ_G";
	
	public static final String TRSF_ONLY_SAVE = "TO_S";
	public static final String TRSF_ONLY_UPDATE = "TO_U";
	public static final String TRSF_ONLY_DELETE = "TO_D";
	
	public static final String TRCD_QUEUE_ADD = "TC_A";
	public static final String TRCD_QUEUE_GET = "TC_G";
	
	public static final String TRCD_ONLY_SAVE = "TC_S";
	public static final String TRCD_ONLY_UPDATE = "TC_U";
	public static final String TRCD_ONLY_DELETE = "TC_D";
	
	public static final String XML_MARSHAL = "XML_MA"; //XML Encoding
	public static final String XML_UNMARSHAL = "XML_UM"; //XML Decoding
	
	public static final String XML_WRITE = "XML_W"; //XML Write
	public static final String XML_READ = "XML_R"; //XML Read
	
	public static final int SERVICE_START = 1;
	public static final int SERVICE_END = 2;
	public static final int SERVICE_CANCEL = 3; // Request Cancel
	public static final int SERVICE_ERROR = -1;
	
	// FTP POOL SIZE
	public static final int FTP_POOL_SIZE = 4;
	public static final int CRON_POOL_SIZE = 1;
	public static final long FTP_THREAD_TIME = 3000L;
	public static final long CRON_THREAD_TIME = 5000L;
	
	// FTP 설정
	public static final String FTP_REMOTE_IP = "14.36.147.23";
	public static final String FTP_REMOTE_USER = "kra";
	public static final String FTP_REMOTE_PASS = "kra";
	public static final String FTP_REMOTE_LOC = "/home/kra";
	
	public static final String FTP_LOCAL_LOC = "X:\\";
	
	// MXF File Cron 생성
	public static final String CRON_DIR = "Y:\\mp2\\";
	
	// TM Drive
	public static final String TM_DIR = "X:\\tc\\in";
	
	// 사용자 에러
	public static final int XML_PARSING_ERR = 201;
	public static final int NON_PRIMARY_ID = 202;
	public static final int REQUIRE_FIELD_BLANK = 203;
	public static final int NON_FILE_LOCATION = 204;
	public static final int VALUE_CONFIGURE_ERR = 205;
	public static final int STATE_EQSIZE_ZERO = 206;
	
	// 서비스 에러
	public static final int DB_SAVE_ROLLBACK = 301;
	public static final int LOCK_TIME_DELAY = 302;
	public static final int SERVICE_RE_COUNT = 303;
	public static final int QUEUE_SPACE_FULL = 304;
	public static final int CONTENT_ALREADY_USED = 305;
	public static final int PROPERTY_NOT_FOUND = 306;
	public static final int SERVICE_NOT_SUPPORT = 307;
	
	// 서버 에러
	public static final int FILE_NOT_FOUND = 401;
	public static final int DIR_NOT_FOUND = 402;
	public static final int SERVICE_NOT_INITIALIZE = 403;
	public static final int SERVER_NOT_RUNNING = 404;
	
	// I/O 에러
	public static final int DB_CONN_ERR = 501;
	public static final int MQ_CONN_ERR = 502;
	public static final int SOCKET_CONN_ERR = 503;
	public static final int SERVICE_IO_ERR=504;
	public static final int FILE_IO_ERR=505;
	
	// 트랜스퍼 관련
	public static final int USER_CANCEL_ONLY = 601;
	public static final int USER_CANCEL_RESUME = 602;
	
	// 기타 에러
	public static final int ETC_SERVICE_ERR = 901;
	
}
