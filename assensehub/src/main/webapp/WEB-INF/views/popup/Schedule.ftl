<!DOCTYPE html>
<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
<script Language="JavaScript">
	// 검색 
	function checkSearch(){ 
   
		document.ContentsSearch.action="<@spring.url '/pupup/Schedule.ssc'/>";
		document.ContentsSearch.submit();
	}
	
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
			checkSearch(ContentsSearch);    //검색 함수 호출
		}
	}
	
function goSchedule(){
	window.open("/popup/schedule.ssc","gobonwrite","width=800,height=700,scrollbars=yes");
}
function isEmpty(value){
	if(typeof(value)=='number'){    
	  	return (value === undefined || value == null || value.length <= 0) ? 0 : value;
	  }else{
	  	return (value === undefined || value == null || value.length <= 0) ? "" : value;
	  } 
}
function forwardPgmInfo(channelCode,pgmId,pgmNm,pgmCd,pgmGrpCd){
	
	if(pgmId == "" || pgmId == null){
		alert("프로그램ID가 없습니다.");
		return false;
	}
	
	if(pgmGrpCd == "" || pgmGrpCd == null){
		
		opener.document.getElementById('pgmGrpCd').value=pgmCd;
	}else{
		
		opener.document.getElementById('pgmGrpCd').value=pgmGrpCd;
	}
	opener.document.getElementById('pgmId').value=pgmId;
	opener.document.getElementById('pgmNm').value=pgmNm;
	opener.document.getElementById('pgmNm').innerHTML=pgmNm;
	opener.document.getElementsByName('pgmNm')[0].value=pgmNm;
	opener.document.getElementById('pgmCd').value=pgmCd;
	opener.document.getElementById('channelCode').value=channelCode;
	opener.document.getElementById('pgmCd2').innerHTML=pgmCd;
	opener.document.getElementById('pgmNm2').value=pgmNm;
	opener.document.getElementById('pid').value=pgmId;
	opener.document.getElementById('spGubun').value="s";
	//opener.location.href ='<@spring.url '/popup/GetScode.ssc'/>'
	//opener.parent.getScode();
	opener.getScode();
	
	window.open('about:blank', '_self').close();
}

function schedule(value,fTyp){
	
	var tabGubun = document.Schedule.sGubun.value;
	document.Schedule.fTyp.value=fTyp;
	
	if(value == '3'){
		tabGubun = -1;
		document.Schedule.tabGubun.value = '1';
	}else if(value =='1'){
		tabGubun = -2;   //이전주
		document.Schedule.tabGubun.value = '11';
	}else{
		tabGubun = 0;   // 다음주
		document.Schedule.tabGubun.value = '11';
	}
	
	//alert(tabGubun);
	document.Schedule.sGubun.value = tabGubun;
	//alert(document.Schedule.sGubun.value);
	document.Schedule.action="<@spring.url '/popup/Schedule.ssc'/>";
	document.Schedule.submit();
	
}
</script>
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

<script type="text/javascript" src="<@spring.url '/js/calendar.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/script.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/jquery.js' />"></script>
<script type=text/javascript>	 
	var $jq = jQuery.noConflict();
	var baseUrl = "<@spring.url '/'/>";
</script>
<script type="text/javascript" src="<@spring.url '/js/jquery.form.js' />"></script>
</head>
<body>
<section id="wrap2">
<!-- Container -->
<section id="container2">
	<form name="Schedule" id="Schedule" method="post">
		<input type="hidden" name="pid" value="">
		<input type="hidden" name="ctTyp" value="${ctTyp}">
		<input type="hidden" name="tabGubun" value="${tabGubun}">
		<input type="hidden" name="gubun" value="${gubun}">
		<input type="hidden" name="sGubun" value="${sGubun}">
		<input type="hidden" name="channelCode" value="${channelCode}">
		<input type="hidden" name="localCode" value="${search.localCode}">
		<input type="hidden" name="fTyp" value="">
	</form>
    <section id="content">
    	<h3 class="blind">편성표</h3>
		<@spring.bind "search" />
			<article class="title4"><img src="<@spring.url '/images/${search.localCode}.gif'/>" title="TV편성표"/></article>
	        <!--
	        <nav class="snavi"><span class="home">HOME</span> &gt; <span class="now">
	        <#if channelCode=="11">1TV
	        <#elseif channelCode=="12">2TV
	        <#elseif channelCode=="12">2TV
	        <#elseif channelCode=="21">1라디오
	        <#elseif channelCode=="22">2라디오
	        <#elseif channelCode=="23">3라디오
	        <#elseif channelCode=="24">1FM
	        <#elseif channelCode=="25">2FM
	        <#elseif channelCode=="26">사회교육1
	        <#elseif channelCode=="27">사회교육2
	        <#else>
	        </#if></span></nav>
	        -->
	        <article class="tabmenu">
	            <ul class="tab1">
	            <#if gubun=="video">
		            <#if channelCode=="11"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=11&gubun=video&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>1TV</span></a></li>
		            <#if channelCode=="12"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=12&gubun=video&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>2TV</span></a></li>
		        <#elseif gubun="doad">
		        	<#if fTyp=="V">
		        		<#if channelCode=="11"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=11&gubun=doad&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>1TV</span></a></li>
		            	<#if channelCode=="12"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=12&gubun=doad&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>2TV</span></a></li>
		        	<#else>
		        		<#if channelCode=="21"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=21&gubun=doad&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>1라디오</span></a></li>
		        	</#if>
		        <#else>    
		            <#if channelCode=="21"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=21&gubun=audio&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>1라디오</span></a></li>
		            <#if channelCode=="22"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=22&gubun=audio&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>2라디오</span></a></li>
		            <#if channelCode=="23"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=23&gubun=audio&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>3라디오</span></a></li>
		            <#if channelCode=="24"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=24&gubun=audio&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>1FM</span></a></li>
		            <#if channelCode=="25"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=25&gubun=audio&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>2FM</span></a></li>
		            <#if channelCode=="26"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=26&gubun=audio&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>한민족1</span></a></li>
		            <#if channelCode=="61"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=61&gubun=audio&ctTyp=${ctTyp}&localCode=${search.localCode}&fTyp=${fTyp}'/>"><span>DMB</span></a></li>
		            
		            <#--<#if channelCode=="27"><li class="on"><#else><li class="off"></#if><a href="<@spring.url '/popup/Schedule.ssc?channelCode=27&gubun=audio'/>"><span>사회교육2</span></a></li>-->
	            </#if>
	            </ul>
	            
	            <#assign week = sGubun?number>
	            <#if week == -1>
	            <article class="btncover">
	            	<span class="btn_pack gry"><a href="javascript:"  onclick="schedule('1','${fTyp}')"><<</a></span>
	            	<span class="btn_pack gry"><a href="javascript:"  onclick="schedule('2','${fTyp}')">>></a></span>
	            </article>
	            <#elseif week ==0>
	            <article class="btncover">
	            	<span class="btn_pack gry"><a href="javascript:"  onclick="schedule('1','${fTyp}')"><<</a></span>
	            	<span class="btn_pack gry"><a href="javascript:"  onclick="schedule('3','${fTyp}')">금주</a></span>
	            </article>
	            <#else>
	            <article class="btncover">
	            	<span class="btn_pack gry"><a href="javascript:"  onclick="schedule('3','${fTyp}')">금주</a></span>
	            	<span class="btn_pack gry"><a href="javascript:"  onclick="schedule('2','${fTyp}')">>></a></span>
	            </article>
	            </#if>
	        </article>
	        
<!-- Table -->
        <article class="bbsview">
        <table summary="" class="boardview">
        <colgroup><col width="40px"></col><col width="107px"></col><col width="107px"></col><col width="107px"></col><col width="107px"></col><col width="107px"></col><col width="107px"></col><col width="107px"></col></colgroup>
            <thead>
            	<tr>
	            	<th></th>
	            	<#list 2..8 as i>
	            	<th>${tpl.commonUtils().getDateOfWeek(week, i)}(${tpl.commonUtils().getDayOfWeekSun()[i-2]})</th>
	            	</#list>
	            </tr>
            </thead>
            <tbody>
            <#if channelCode=="26">
            <#assign time=0>
	        <#elseif channelCode=="27">
	        <#assign time=0>
	        <#else>
	        <#assign time=5>
	        </#if>
	        
            <#list time..29 as i> <!-- 시간대 루프 초기화 -->
	            <tr><!-- 시간 대-->
	            	<th class="pd">${i}</th>
		            	<#list 1..7 as j>
				            <td class="pdl5">
				             <#list contents as content>
	        					<#if content.programPlannedStartTime?substring(0,2)?number==i> 
				           			<#if content.onairDayCode?number==j >
				           					<#if channelCode=="61">
				           					<p><b>${content.programPlannedStartTime?substring(2,4)} ${content.programTitle}<b><br/>
								            
				           					<#else>
							            	<p><b><a href="#" onclick="forwardPgmInfo('${content.channelCode!""}','${content.programId!""}','${content.programTitle?replace("'", "")?replace("\"", "")}','${content.programCode!""}','${content.groupCode!""}');">${content.programPlannedStartTime?substring(2,4)} ${content.programTitle}</a><b><br/>
								            </#if>
								            	<#if content.productionTypeCode?default("")=="01">
								            		<img src="<@spring.url '/images/icon_onair.gif'/>" title="생방"/>&nbsp;
								            	<#elseif content.productionTypeCode?default("")=="02" >
								            		<img src="<@spring.url '/images/icon_rec.gif'/>" title="녹화"/>&nbsp;
								            	<#else>
								            	</#if>
								            	<#if content.productionVideoQuality?default("")=="HD">
								            		<img src="<@spring.url '/images/icon_h.gif'/>" title="HD"/>&nbsp;
								            	<#elseif content.productionVideoQuality?default("")=="SD" >
								            		<img src="<@spring.url '/images/icon_s.gif'/>" title="SD"/>&nbsp;
								            	<#else>
								            	</#if>
								            	<#if content.rerunClassificationCode?default("")=="02">
								            		<img src="<@spring.url '/images/icon_rerun.gif'/>" title="재방"/>&nbsp;
								            	</#if>
								            	
								            	<#--아이콘 오면 수정 요망 시작 -->
								            	
								            	<#if content.recyn?default("")=="Y">
								            		<img id="${content.programCode?default("")}"  class="${content.programCode?default("")}" src="<@spring.url '/images/icon_np.gif'/>" title="p"/>
								            	<#elseif content.recyn?default("")="N">
								            		<img id="${content.programCode?default("")}"  class="${content.programCode?default("")}" src="<@spring.url '/images/icon_x.gif'/>" title="p"/>
								            	<#else>
								            		<img id="${content.programCode?default("")}"  class="${content.programCode?default("")}" src="<@spring.url '/images/icon_question.gif'/>" title="p"/>
								            	</#if>
								            	<p/>
								            	<!--아이콘 오면 수정 요망 끝 -->
								        	<p/>
							 		</#if>
				          		</#if>
				          	</#list>
				            </td>
			            </#list>
		            </tr>
			     <tr>
           </#list>
            <!-- //프로그램 반복 -->
            </tbody>
        </table>        
        </article>
<!-- //Table -->
	</section>
</section>
<!-- //Container -->
</section>
</body>
</html>
