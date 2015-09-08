<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script type="text/javascript">

function checkLogout(){
	document.header.action="<@spring.url '/member/loginout.ssc'/>";
	document.header.submit();
}
</script>
<header id="header">
	<#assign ctx>
  		<@spring.url ''/>
	</#assign>
	<form name="header" method = "post">
	
	</form>
	<h3 class="blind">TOPMENU</h3>
	<div class="header_area">
		<article class="logo"><a href="<@spring.url '/schedule/ThisWeek.ssc?channelCode=11&menuId=3'/>"><img src="<@spring.url '/images/logo.gif' />" alt=""></a></article>
		<ul class="quick">
			<li><a href="<@spring.url '/schedule/ThisWeek.ssc?channelCode=11&menuId=3'/>">HOME</a></li>
			<li class="bar"><a href="javascript:void(0)"  onclick="checkLogout();">LOGOUT</a></li>
		</ul>
	</div>
	<nav class="gnb">
		<!-- Topmenu -->
		<ul class="mMenu" id="topmenu">
			<@menu tpl.findUserMenus('${Session.user.userId}') '${ctx}' />
		</ul>
		<article class="bg_sMenu"></article>
	</nav>
	<script type="text/javascript">initNavigation(0);</script>
<!-- //Topmenu -->
</header>