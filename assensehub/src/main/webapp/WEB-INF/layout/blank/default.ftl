<#include "/WEB-INF/views/includes/commonTags.ftl">
<?xml version="1.0" encoding="utf-8"?> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko"> 
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

<script type="text/javascript" src="<@spring.url '/js/calendar.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/script.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/jquery.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/date.js' />"></script>
<script type=text/javascript>	 
	var $jq = jQuery.noConflict();
	var baseUrl = "<@spring.url '/'/>";
</script>
<script type="text/javascript" src="<@spring.url '/js/jquery.form.js' />"></script>
</head>
<body>
<div id="loginvalign">
	<@tiles.insertAttribute name="body"/>
</div>
</body>
</html>