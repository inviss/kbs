<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">
<!--
	// 검색 
	function checkSearch(){ 
	
		var stDt=document.ArchiveSearch.startDt.value;
		var edDt=document.ArchiveSearch.endDt.value;
		
		if(edDt < stDt){
			alert("날짜 검색에 종료일이 시작일 보다 앞선 날짜 입니다.");
			return false;
		}
		document.ArchiveSearch.action="<@spring.url '/content/ArchiveSearch.ssc'/>";
		document.ArchiveSearch.pageNo.value="1";
		document.ArchiveSearch.submit();
	}
	
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
			checkSearch(ArchiveSearch);    //검색 함수 호출
		}
	}
	
	function archiveOpenView(url) {
		if(confirm('아카이브 상세보기는 VPN 연결이 우선되어야 합니다. 연결되셨습니까?')) {
			window.open(url, '_blank');
		}
	}
	
	function switchTab(name) {
		var tab = new Array('pro', 'news');
		for(var i=0; i<tab.length; i++) {
			if(tab[i] == name) {
				$jq('#'+tab[i]+'_tab').removeClass('off');
				$jq('#'+tab[i]+'_tab').addClass('on');
				
				$jq('#'+tab[i]+'_result').show();
				$jq('#'+tab[i]+'_paging').show();
				document.ArchiveSearch.name.value = name;
				
			} else {
				$jq('#'+tab[i]+'_tab').removeClass('on');
				$jq('#'+tab[i]+'_tab').addClass('off');
				
				$jq('#'+tab[i]+'_result').hide();
				$jq('#'+tab[i]+'_paging').hide();
			}
		}
	}
	
	function goPage(pageNo) {
		document.ArchiveSearch.action="<@spring.url '/content/ArchiveSearch.ssc'/>";
		document.ArchiveSearch.pageNo.value = pageNo;
		document.ArchiveSearch.submit();
	}
	
	
//-->
</script>

<section id="container">
    <section id="content">
    	<form name="ArchiveSearch" method="post">
    	<@spring.bind "search" />
    	<input type="hidden" name="pgmId" />
    	<input type="hidden" name="name" value="${name}"/>
    	<input type="hidden" name="pageNo" value="${search.pageNo!0}" />
    	<h3 class="blind">아카이브 조회</h3>
		<article class="title5"><img src="<@spring.url '/images/title_viewarch.gif' />" title="아카이브 조회"/></article>
        <nav class="snavi"><span class="home">${name}//HOME</span> &gt; 콘텐츠 관리 &gt; <span class="now">아카이브 조회</span></nav>
        <table summary="" id="boardview3">
        <thead>
            <tr><th>
		        <article class="box2">
		            <dl>
		                <dt>방송일</dt>
		                <dd>
                        <input type="text" style="cursor:pointer;"  id="calInput1" name="startDt"  value="<#if search.startDt?exists>${search.startDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>&#126;
                        <input type="text" style="cursor:pointer;" id="calInput2"  name="endDt"  value="<#if search.endDt?exists>${search.endDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>
                        </dd>
		                <dt>콘텐츠명</dt>
		                <dd><input type="text" class="text" name="keyword" value="${search.keyword!""}" onKeyDown="keyEnter();"></input></dd>
		            </dl>
		            <article class="btncover5">
		            	<span class="btn_pack gry"><a href="javascript:void(0)" onClick="checkSearch()">조회</a></span></span>
		            </article>
		        </article>
		</th></tr>
        </thead>
        </table>
        <article id="archiveTab" class="tabmenu">
            <ul class="tab1">
	            <li id="pro_tab" class="on"><a href="javascript:void(0)" onClick="switchTab('pro')"><span>프로그램(${proCount})</span></a></li>
	            <li id="news_tab" class="off"><a href="javascript:void(0)" onClick="switchTab('news')"><span>뉴스(${newsCount})</span></a></li>
            </ul>
        </article>
        	
		<!-- Table -->
        <article id="pro_result" class="bbsview3 bd2" style="display:none">
        	<table summary="" id="boardview3">
        		<colgroup><col width="200px"></col><col width="450px"></col><col width="100px"></col></colgroup>
        		<thead>
        			<tr><th>link Id</th><th>프로그램명</th><th>영상길이</th></tr>
        		</thead>
        		<tbody>
        			<#list proResult.items as content>
        			<tr class="gry">
        				<td>${content.programIdlinkId!""}</td>
    					<td align="left">
    						<a href="javascript:void(0)" onClick="archiveOpenView('${content.pageURL}')">&nbsp;${content.programTitle!""}</a>
   				 		</td>
			            <td>${content.length!""}</td>
        			</tr>
        			</#list>
        		</tbody>
    		</table>     
    	</article>
    	
    	<article id="news_result" class="bbsview3 bd2" style="display:none">
    	
        	<table summary="" id="boardview3" >
        		<colgroup><col width="200px"></col><col width="450px"></col><col width="100px"></col></colgroup>
        		<thead>
        			<tr><th>link Id</th><th>프로그램명</th><th>영상길이</th></tr>
        		</thead>
        		<tbody>
        			<#list newsResult.items as content>
        			<tr class="gry">
        				<td>${content.programIdlinkId!""}</td>
    					<td align="left">
    						<a href="javascript:void(0)" onClick="archiveOpenView('${content.pageURL}')">&nbsp;${content.programTitle!""}</a>
   				 		</td> 
			            <td>${content.length!""}</td>
        			</tr>
        			</#list>
        		</tbody>
    		</table> 
    		    
    	</article>
		<!-- //Table -->
		
		<!-- paginate -->
    	<article id="pro_paging" class="paginate">
	        <@paging proResult search.pageNo '' />
    	</article>
    	<article id="news_paging" class="paginate">
	        <@paging newsResult search.pageNo '' />
    	</article>
		<!-- //paginate -->
		</form>
	</section>
<script Language="JavaScript">

	window.onload = function() {
		
	}

</script>	
<script type="text/javascript">
window.onload=function(){
	cal1 = new dhtmlxCalendarObject('calInput1', false, {isYearEditable: true,isMonthEditable: true});
	cal1.setYearsRange(1973, 2020);
	cal1.draw();
	
	cal2 = new dhtmlxCalendarObject('calInput2', false, {isYearEditable: true,isMonthEditable: true});
	cal2.setYearsRange(1973, 2020);
	cal2.draw();
	
	
	
		var tabvalue = document.ArchiveSearch.name.value;
		if(tabvalue == "pro" || tabvalue == "0"){
			
			switchTab('pro');
		}else{
			
			switchTab('news');
		}
}
</script>