<!DOCTYPE html>
<#include "/WEB-INF/views/includes/commonTags.ftl">
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
<html>
<head>
	<title>KBS CMS</title>
	<!--[if lt IE 9]>
	  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	  <![endif]-->
	
	  <!--[if lt IE 9]>
	  <script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js"></script>
	  <![endif]-->
	<link rel="stylesheet" type="text/css" href="<@spring.url '/css/master.css' />" />
	<link rel="stylesheet" type="text/css" href="<@spring.url '/css/layout.css' />" />
	<link rel="stylesheet" type="text/css" href="<@spring.url '/css/dhtmlxcalendar.css' />" />
	<link rel="stylesheet" type="text/css" href="<@spring.url '/css/dhtmlxcalendar_skins.css' />" />
	
	<script type='text/javascript' src='http://code.jquery.com/jquery-1.7.1.js'></script>
	
	<script type="text/javascript" src="<@spring.url '/js/calendar.js' />"></script>
	<script type="text/javascript" src="<@spring.url '/js/script.js' />"></script>
	<script type="text/javascript" src="<@spring.url '/js/jquery.js' />"></script>
	<script type="text/javascript" src="<@spring.url '/js/date.js' />"></script>
	<script type="text/javascript" src="<@spring.url '/js/dhtmlxcommon.js' />"></script>
	<script type="text/javascript" src="<@spring.url '/js/dhtmlxcalendar.js' />"></script>
	<script type="text/javascript" src="<@spring.url '/js/ac_quicktime.js' />"></script>
	<script type="text/javascript" src="<@spring.url '/js/jquery.blockUI.js' />"></script>
	<script type=text/javascript>	 
		var $jq = jQuery.noConflict();
		var baseUrl = "<@spring.url '/'/>";
		
	    window.dhx_globalImgPath="<@spring.url '/img'/>";
		var cal1, cal2, mCal, mDCal, newStyleSheet;
	</script>
	<script type="text/javascript" src="<@spring.url '/js/jquery.form.js' />"></script>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body>
<div id="wrap_full">
<section id="wrap">
	<!-- header -->
	<@tiles.insertAttribute name="header"/>
	<!-- //header -->
	<hr />
	<!-- Container -->
	<@tiles.insertAttribute name="body"/>
	<hr />
	<@tiles.insertAttribute name="right"/>    
	<!-- //Container -->
	<hr />
	<@tiles.insertAttribute name="footer"/>
</section>
</div>
</body>
</html>