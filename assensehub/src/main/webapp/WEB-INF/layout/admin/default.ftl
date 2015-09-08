<#include "/WEB-INF/views/includes/commonTags.ftl">
<?xml version="1.0" encoding="utf-8"?> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko"> 
<head>
    <title><@tiles.getAsString name="title"/></title>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	
	<link rel="stylesheet" type="text/css" href="<@spring.url '/css/layout.css'/>" />
	<link rel="stylesheet" type="text/css" href="<@spring.url '/css/master.css'/>" />
	<script type="text/javascript" src="<@spring.url '/js/jquery.js'/>"></script>
	<script type="text/javascript" src="<@spring.url '/js/script.js'/>"></script>
	<script type="text/javascript" src="<@spring.url '/js/calendar.js'/>"></script>
	
	<script type="text/javascript" src="<@spring.url '/jquery.datalink.js'/>"></script>
	<script type="text/javascript" src="<@spring.url '/jquery.tmpl.js'/>"></script>
	
	<script Language="JavaScript">
		<!--
		function mouseDown() {
			if(event.button == '2'){
				alert('오른 마우스 클릭을 금지합니다');
			}
			return;
		}
		//-->
	</script>
</head>
<body>
<div id="wrap">
	<ul class="blind">
        <li><a href="#content">본문바로가기</a></li>
    </ul>
	<@tiles.insertAttribute name="header"/>
	<hr />
	<div id="container">
		<@tiles.insertAttribute name="left"/>
		<hr />
		<@tiles.insertAttribute name="body"/>
	</div>
</table>
</body>
</html>