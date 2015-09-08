	<#include "/WEB-INF/views/includes/commonTags.ftl">
	<div id="header">
		<h1><a href="#">부산경마장</a></h1>
	    <h2 class="login"><a href="#">로그아웃</a></h2>
		<div class="gnb">
	    	<h2 class="blind">로컬네비게이션</h2>
			<ul class="tmenu" id="topmenu">
				<li id="top-menu1" class="none"><a href="<@spring.url '/race/detailList.ssc'/>" id="top-menu-head1">경기정보</a></li>
				<li class="bar" id="top-menu2">
					<a href="#" id="top-menu-head2">컨텐츠관리</a>
					<ul class="smenu" id="submenu2">
						<li class="smenu_st"><a href="<@spring.url '/media/contentList.ssc'/>">영상목록</a></li>
						<li><a href="<@spring.url '/media/transferList.ssc'/>">전송목록</a></li>
	                    <li><a href="<@spring.url '/media/spectialList.ssc'/>">경기외영상</a></li>
					</ul>
            	</li>
				<li class="bar" id="top-menu3">
					<a href="#" id="top-menu-head3">시스템관리</a>
					<ul class="smenu" id="submenu3">
						<li><a href="<@spring.url '/manager/pgmList.ssc'/>">PGM관리</a></li>
						<li><a href="<@spring.url '/manager/safetyDay.ssc'/>">보관일관리</a></li>
					</ul>
            	</li>
        	</ul>
		</div>
		<script type="text/javascript">
			initNavigation(0);
		</script>
	</div>