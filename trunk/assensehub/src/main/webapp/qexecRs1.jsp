<%@page import="org.apache.commons.lang.SystemUtils"%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.PreparedStatement"%>

<%
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "";
		String id = "";
		String pass = "";
		if (SystemUtils.IS_OS_WINDOWS) {
		//	url = "jdbc:oracle:thin:@14.36.147.34:1521:orclesh";
		//	id = "esh";
		//	pass = "esh123";
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

		//String sql = "select * from tra_his_tbl where  seq in ( 14336 ) ";
		//String sql = " SELECT *  FROM CONTENTS_inst_TBL        WHERE ct_id = '11447'  ";
		//String sql = " SELECT  count(*)    as pgmNum		FROM CONTENTS_TBL        WHERE pgm_id = 'PS-2012055151-01-000' AND ct_typ='00' AND ct_cla='00'        GROUP BY CT_CLA,CT_TYP,PGM_ID ";
	    //String sql = "  SELECT * FROM CONTENTS_INST_TBL WHERE fl_path LIKE '%PS-2012053928-01-000%'	";
		String sql = " SELECT      *  FROM contents_inst_Tbl  WHERE         ct_id = 78408 ";
			
	
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
%>
