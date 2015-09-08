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
String url="";
String id="";
String pass="";
if(SystemUtils.IS_OS_WINDOWS){
 url = "jdbc:oracle:thin:@14.36.147.34:1521:orclesh";
 id = "esh";
 pass = "esh123";
}else{
 url = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = 100.100.120.11)(PORT = 7522))(ADDRESS = (PROTOCOL = TCP)(HOST = 100.100.120.12)(PORT = 7522))(LOAD_BALANCE = ON)(FAIL_OVER =ON )(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = CMSDB)(FAILOVER_MODE = (TYPE = SESSION) (METHOD = BASIC)  (RETRIES = 5) (DELAY = 1))))";
 id = "esh";
 pass = "esh";
}
Statement stmt; 
ResultSet rs; 

Connection conn = DriverManager.getConnection(url, id, pass); 

stmt = conn.createStatement();


String sql= "UPDATE USER_TBL SET user_nm ='KBSAOD' WHERE user_id = 'A001'"; 
stmt.executeUpdate(sql); 


if(conn != null) {
	out.println("Connection Success");
}
conn.close(); 

} catch(SQLException e) { 
out.println("Connection Fail");
} 
	*/
%>
