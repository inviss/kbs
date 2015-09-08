<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.PreparedStatement" %>
 
<%
try {
Class.forName("oracle.jdbc.driver.OracleDriver");

//String url = "jdbc:oracle:thin:@14.36.147.34:1521:orclesh";
//String id = "esh";
//String pass = "esh123";

// String url = "jdbc:oracle:thin:@100.100.120.12:7522:CMSDB";

String url = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = 211.233.93.10)(PORT = 7521))(ADDRESS = (PROTOCOL = TCP)(HOST = 211.233.93.11)(PORT = 7521))(LOAD_BALANCE = ON)(FAIL_OVER =ON )(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = CMSDB)(FAILOVER_MODE = (TYPE = SESSION) (METHOD = BASIC)  (RETRIES = 5) (DELAY = 1))))";


String id = "esh";
String pass = "esh";
 
Connection conn = DriverManager.getConnection(url, id, pass); 

PreparedStatement ps = null;
if(conn != null) {
	StringBuffer query = new StringBuffer()
	/*
	.append(" SELECT                                                                                                  ")
	.append(" 	day.PROGRAM_TITLE, week.PROGRAMMING_LOCAL_STATION_CODE, day.RUNNING_DATE||' '||day.RUNNING_END_TIME BRD_DD                                        ")
	.append(" FROM TRS_HIS_TBL tra                                                                                    ")
	.append(" 	inner JOIN CONTENTS_INST_TBL inst ON tra.CTI_ID = inst.CTI_ID                                         ")
	.append("     inner JOIN CONTENTS_TBL ct ON inst.CT_ID = ct.CT_ID                                                 ")
	.append("     left outer JOIN DAIRY_ORDER_TBL day ON ct.PGM_CD = day.PROGRAM_CODE and ct.PGM_ID = day.PROGRAM_ID  ")
	.append("     left outer JOIN WEEK_SCH_TBL week ON ct.PGM_CD = week.PROGRAM_CODE and ct.PGM_ID = week.PROGRAM_ID  ")
	.append(" WHERE tra.SEQ = 273611 																						");
	*/
	//.append("select ct.pgm_cd, ct.pgm_grp_cd, week.program_code, week.group_code from contents_tbl ct inner join week_sch_tbl week on ct.pgm_id = week.program_id and ct.pgm_cd = week.program_code where ct.pgm_id = 'PS-2013033366-01-000'")
	/*
	.append(" SELECT                                                                                                                                                                        ")
	.append(" 	DOT.PROGRAM_TITLE                                                                                                                                                                ")
	.append("   FROM DAIRY_ORDER_TBL DOT                                                                                                                                                    ")
	.append(" 	LEFT OUTER JOIN LIVE_TBL LIVE ON DOT.PROGRAM_CODE = LIVE.PROGRAM_CODE AND (CASE DOT.RERUN_CLASSIFICATION WHEN '占쏙옙占쏙옙' THEN '01' WHEN '占쏙옙占� THEN '02' END) = LIVE.RERUN_CODE ")
	.append(" WHERE DOT.RUNNING_DATE = '20130715' ");
	*/
	//ps = conn.prepareStatement("update TRA_HIS_TBL set WORK_STATCD = '000' where SEQ in( 210313, 210311, 210308)");
    
	//ps = conn.prepareStatement("select PROGRAM_CODE, RERUN_CLASSIFICATION, CHANNEL_CODE,RUNNING_DATE,RUNNING_START_TIME, DAILY_RUNNING_KIND from DAIRY_ORDER_TBL where program_code = 'T2001-0374'");
	//ps = conn.prepareStatement("select ct_id, pgm_cd, ct_seq, pgm_nm, to_char(BRD_DD,'YYYY-MM-DD') brd_dd from CONTENTS_TBL where PGM_ID = 'PS-2012241458-01-000'");
	//ps = conn.prepareStatement("select PROGRAM_PLANNED_DATE from WEEK_SCH_TBL where PROGRAM_ID = 'PS-2012241458-01-000'");
	//ps = conn.prepareStatement("select SCL_CD, CLF_NM from CODE_TBL where CLF_CD = 'DOAD' ");
	//ps = conn.prepareStatement("SELECT      trs.WORK_STATCD,  ct.CT_ID, cti.CTI_ID, trs.SEQ      FROM TRS_HIS_TBL trs    inner JOIN CONTENTS_INST_TBL cti ON trs.CTI_ID = cti.CTI_ID       inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID       inner JOIN BUSI_PARTNER_TBL bp ON trs.BUSI_PARTNER_ID = bp.BUSI_PARTNERID       inner JOIN PRO_FL_TBL pf ON trs.PRO_FLID = pf.PRO_FLID   WHERE trs.work_statcd IN ('001') and trs.REG_DT > (SYSDATE -1)   AND    (pf.VDO_BIT_RATE is null OR pf.VDO_BIT_RATE <> '35000') AND trs.PRGRS <> '100'         ORDER BY trs.priority ASC, trs.reg_dt asc");
	//ps = conn.prepareStatement("SELECT      trs.WORK_STATCD,  ct.CT_ID, cti.CTI_ID, trs.SEQ      FROM TRS_HIS_TBL trs    inner JOIN CONTENTS_INST_TBL cti ON trs.CTI_ID = cti.CTI_ID       inner JOIN CONTENTS_TBL ct ON cti.CT_ID = ct.CT_ID       inner JOIN BUSI_PARTNER_TBL bp ON trs.BUSI_PARTNER_ID = bp.BUSI_PARTNERID       inner JOIN PRO_FL_TBL pf ON trs.PRO_FLID = pf.PRO_FLID   WHERE ROWNUM <= 10 and trs.work_statcd IN ('000')   AND    (pf.VDO_BIT_RATE is null OR pf.VDO_BIT_RATE <> '35000') ORDER BY trs.priority ASC, trs.reg_dt asc");
	ps = conn.prepareStatement(query.toString());
	//ps.executeUpdate();
	
	//while(rs.next()) {
		//out.println("===>"+rs.getString("menuId")+", ");
		//out.println("===>"+rs.getString("menuNm")+", ");
		//out.println("===>"+rs.getString("url")+", ");
		//out.println("===>"+rs.getString("lft")+"</br>");
		//out.println("===>"+rs.getString("CLF_NM")+"</br>");
		//out.println(rs.getString("PROGRAM_TITLE")+", ");
		//out.println(rs.getString("PROGRAMMING_LOCAL_STATION_CODE")+", ");
		//out.println(rs.getString("BRD_DD")+"");
		//out.println(rs.getString("pgm_cd")+", ");
		//out.println(rs.getString("pgm_grp_cd")+", ");
		//out.println(rs.getString("program_code")+", ");
		//out.println(rs.getString("group_code")+"</br>");
		//out.println(rs.getString("PROGRAM_PLANNED_DATE")+"</br>");
		
		//out.println("===>"+rs.getString("PRO_FLiD")+", "+rs.getString("SERV_BIT")+", "+rs.getString("EXT")+", "+rs.getString("FL_NAME_RULE")+"</br>");
		//out.println("===>"+rs.getString("brdDd")+"</br>");
		
		
		
		//out.println("===>"+rs.getString("company")+"</br>");
		//out.println("===>"+rs.getString("busi_partnerid")+"</br>");
		
		//out.println("======================================</br>");
	//}
	
	conn.commit();
	out.println("Connection Success");
}
conn.close(); 

} catch(SQLException e) { 
out.println(e);
} 
%>
