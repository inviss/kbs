<%@page import="org.apache.commons.lang.SystemUtils"%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.PreparedStatement"%>

<%
/*
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "";
		String id = "";
		String pass = "";
		if (SystemUtils.IS_OS_WINDOWS) {
			url = "jdbc:oracle:thin:@14.36.147.34:1521:orclesh";
			id = "esh";
			pass = "esh123";
		} else {
			url = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = 100.100.120.11)(PORT = 7522))(ADDRESS = (PROTOCOL = TCP)(HOST = 100.100.120.12)(PORT = 7522))(LOAD_BALANCE = ON)(FAIL_OVER =ON )(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = CMSDB)(FAILOVER_MODE = (TYPE = SESSION) (METHOD = BASIC)  (RETRIES = 5) (DELAY = 1))))";
			id = "esh";
			pass = "esh";
		}
		Statement stmt;
		ResultSet rs;
		String seq = "";
		String[] arrColumnNames;
		Connection conn = DriverManager.getConnection(url, id, pass);

		stmt = conn.createStatement();

		//String sql = "select * from tra_his_tbl where  seq in ( 12825 ) ";
		String sql = "select * from contents_inst_Tbl where  cti_id in ( 22324 ) ";
		//String sql = " SELECT *  FROM CONTENTS_inst_TBL        WHERE ct_id = '11447'  ";
		//String sql = " SELECT  count(*)    as pgmNum		FROM CONTENTS_TBL        WHERE pgm_id = 'PS-2012055151-01-000' AND ct_typ='00' AND ct_cla='00'        GROUP BY CT_CLA,CT_TYP,PGM_ID ";

		String sql = "  SELECT * FROM CONTENTS_inst_TBL cit																																																	"+
			"	      	INNER JOIN CONTENTS_TBL ct on ct.CT_ID = cit.CT_ID                                                                            "+
			"   			WHERE (cit.USE_YN ='Y' AND (cit.cti_fmt LIKE '1%' OR cit.cti_fmt LIKE '3%') AND ct.reg_dt <SYSDATE -4 AND ct.regrid='LIVE')   "+
			"  UNION ALL                                                                                                                            "+
			"    SELECT * FROM CONTENTS_inst_TBL cit                                                                                                "+
			"	      	INNER JOIN CONTENTS_TBL ct on ct.CT_ID = cit.CT_ID                                                                            "+
			"   			WHERE (cit.USE_YN ='Y' AND (cit.cti_fmt LIKE '1%' OR cit.cti_fmt LIKE '3%') AND ct.reg_dt <SYSDATE -3 AND ct.regrid='NPS')    "+
			"  UNION ALL                                                                                                                            "+
			"    SELECT * FROM CONTENTS_inst_TBL cit                                                                                                "+
			"	      	INNER JOIN CONTENTS_TBL ct on ct.CT_ID = cit.CT_ID                                                                            "+
			"   			WHERE (cit.USE_YN ='Y' AND (cit.cti_fmt LIKE '1%' OR cit.cti_fmt LIKE '3%') AND ct.reg_dt <SYSDATE -2 AND ct.regrid='VTRIM')  "+
			"  UNION ALL                                                                                                                            "+
			"    SELECT * FROM CONTENTS_inst_TBL cit                                                                                                "+
			"	      	INNER JOIN CONTENTS_TBL ct on ct.CT_ID = cit.CT_ID                                                                            "+
			"   			WHERE (cit.USE_YN ='Y' AND (cit.cti_fmt LIKE '1%' OR cit.cti_fmt LIKE '3%') AND ct.reg_dt <SYSDATE -3 AND ct.regrid='KDAS')   "+
			"  UNION ALL                                                                                                                            "+
			"    SELECT * FROM CONTENTS_inst_TBL cit                                                                                                "+
			"	      	INNER JOIN CONTENTS_TBL ct on ct.CT_ID = cit.CT_ID                                                                            "+
			"   			WHERE (cit.USE_YN ='Y' AND (cit.cti_fmt LIKE '1%' OR cit.cti_fmt LIKE '3%') AND ct.reg_dt <SYSDATE -2 AND ct.regrid='ATRIM')  "+
			"  UNION ALL                                                                                                                            "+
			"    SELECT * FROM CONTENTS_inst_TBL cit                                                                                                "+
			"	      	INNER JOIN CONTENTS_TBL ct on ct.CT_ID = cit.CT_ID                                                                            "+
			"   			WHERE (cit.USE_YN ='Y' AND (cit.cti_fmt LIKE '1%' OR cit.cti_fmt LIKE '3%') AND ct.reg_dt <SYSDATE -3 AND ct.regrid='NAS')    "+
			"  UNION ALL                                                                                                                            "+
			"    SELECT * FROM CONTENTS_inst_TBL cit                                                                                                "+
			"	      	INNER JOIN CONTENTS_TBL ct on ct.CT_ID = cit.CT_ID                                                                            "+
			"   			WHERE (cit.USE_YN ='Y' AND (cit.cti_fmt LIKE '2%' OR cit.cti_fmt LIKE '4%') AND ct.reg_dt <SYSDATE -30)                       "+
			"";
	

		rs = stmt.executeQuery(sql);

		ResultSetMetaData oRsmd = rs.getMetaData();
		int count = oRsmd.getColumnCount();

		arrColumnNames = new String[count];

		for (int j = 1; j <= count; j++){
			arrColumnNames[j - 1] = oRsmd.getColumnName(j);
			//out.print(arrColumnNames[j - 1]+"||");
		}
		while (rs.next()) {
			out.println("=======================<BR> ");
			for (int j = 1; j <= count; j++){
			out.print(arrColumnNames[j - 1]+" :");
			out.print(rs.getString(arrColumnNames[j - 1])+"");
			out.println("<BR> ");
			}
			out.println("=======================<BR> ");
		}
		out.println("<BR> " + seq);
		if (conn != null) {
			out.println("Connection Success");
		}
		conn.close();

	} catch (SQLException e) {
		out.println("Connection Fail");
	}
*/
%>
