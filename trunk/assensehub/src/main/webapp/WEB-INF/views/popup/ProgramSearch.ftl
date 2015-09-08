<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
<script Language="JavaScript">

function search(){
	if(document.ProgramSearch.keyword.value==""){
	  alert("프로그램명을 입력하세요");
	  document.ProgramSearch.keyword.focus();
	  return false;
	}
	if(document.ProgramSearch.startDt.value==""){
	  alert("날짜 검색 시작일을 입력하세요");
	  //document.ProgramSearch.startDt.focus();
	  return false;
	}
	if(document.ProgramSearch.endDt.value==""){
	  alert("날짜 검색 종료일을 입력하세요");
	  //document.ProgramSearch.endDt.focus();
	  return false;
	}
	return true;
}
function checkSearch(){
	
		
		var stDt=document.ProgramSearch.startDt.value;
		var edDt=document.ProgramSearch.endDt.value;
		
		if(edDt < stDt){
			alert("날짜 검색에 종료일이 시작일 보다 앞선 날짜 입니다.");
			return false;
		}
		
		$jq.blockUI();      
	  	$jq('.blockOverlay').attr('title','Click to unblock').click($jq.unblockUI); 
	  	$jq.ajax({
			url: '<@spring.url '/popup/TimeProgramSearch.ssc' />',
			type: 'POST',
			dataType: 'json',
			data: $jq('#ProgramSearch').serialize(),
			timeout: 30000,
			error: function(xhr, ajaxOptions, thrownError){
				alert('검색에 실패했습니다.');
				$jq.unblockUI();  
			},
			success: function(data){
				document.ProgramSearch.method="post";
				document.ProgramSearch.action="<@spring.url '/popup/ProgramSearch.ssc'/>";
				document.ProgramSearch.submit();
			}
		});
	  	<#--
		$jq.ajax({
			url: '<@spring.url '/popup/TimeProgramSearch.ssc' />',
			type: 'POST',
			dataType: 'json',
			data: $jq('#ProgramSearch').serialize(),
			timeout: 30000,
			error: function(){
				alert('검색시간이 너무 오래걸립니다.');
				$jq.unblockUI();  
			},
			success: function(data){
				document.ProgramSearch.method="post";
				document.ProgramSearch.action="<@spring.url '/popup/ProgramSearch.ssc'/>";
				document.ProgramSearch.submit();
			}
		});
		-->
}
		
function paging(cur){
	//alert(cur);
	ProgramSearch.pagecurrent.value=cur;
	document.ProgramSearch.action="<@spring.url '/popup/ProgramSearch.ssc'/>";
	document.ProgramSearch.submit()
}

function keyEnter() {  
	//alert("1"); 
	//alert(event.keyCode);
	//alert("2");
	if(event.keyCode ==13) {   //엔터키가 눌려지면,
		checkSearch(ProgramSearch);    //검색 함수 호출
	}
}
	
function getLiveInfo(brdDd,channelCode,programCode,pgmId,pgmGrpCd,pgmNm,audioModeMain){
	//alert(audioModeMain);
	if(pgmId == "" || pgmId == null){
		alert("프로그램ID가 없습니다.");
		return false;
	}
	//alert(audioModeMain);
	if(audioModeMain == 'Mono') {
		ProgramSearch.audioModeMain.value = '01';
	} else if (audioModeMain == 'Stereo') {
		ProgramSearch.audioModeMain.value = '02';
	} else if (audioModeMain == 'Comment') {
		ProgramSearch.audioModeMain.value = '04';
	} else {
		ProgramSearch.audioModeMain.value = '03';
	}
	
	ProgramSearch.programCode.value=programCode;
	ProgramSearch.pgmId.value=pgmId;
	ProgramSearch.pgmGrpCd.value=pgmGrpCd;
	ProgramSearch.pgmNm.value=pgmNm;
	ProgramSearch.brdDd.value=brdDd;
	ProgramSearch.channelCode.value=channelCode;
	//alert(pgmGrpCd);
	var obj = ($jq('#ProgramSearch').get(0));

	$jq.ajax({
		url: '<@spring.url '/popup/findLive.ssc' />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ProgramSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		
			
			$jq(".busiPartnerid").attr("checked",false);
			if(data.busiPartnerPgm!=null){
				for(var i=0;i<data.busiPartnerPgm.length;i++){
					$jq('#'+data.busiPartnerPgm[i].busiPartnerid+'').attr("checked",true);
					//$jq('input[name='+data.busiPartnerPgm[i].busiPartnerid+']').attr("checked",true);
				}
			}
			
		}
	});
}

function save(){
		
		opener.document.getElementById('pgmId').value=ProgramSearch.pgmId.value;
		opener.document.getElementById('pgmGrpCd').value=ProgramSearch.pgmGrpCd.value;
	
		opener.document.getElementById('pgmCd').value=ProgramSearch.programCode.value;
		opener.document.getElementById('pgmCd2').innerHTML=ProgramSearch.programCode.value;	
		
		opener.document.getElementById('pgmNm').innerHTML=ProgramSearch.pgmNm.value;
		opener.document.getElementsByName('pgmNm')[0].value=ProgramSearch.pgmNm.value;
	
		opener.document.getElementById('brdDd').value=ProgramSearch.brdDd.value;
		opener.document.getElementById('channelCode').value=ProgramSearch.channelCode.value;
		opener.document.getElementById('pid').value=ProgramSearch.pgmId.value;
		
		opener.document.getElementById('pgmNm').value=ProgramSearch.pgmNm.value;
		opener.document.getElementById('pgmNm2').value=ProgramSearch.pgmNm.value;
		opener.document.getElementById('spGubun').value="p";
		
		opener.getScode();
	
	$jq.ajax({
		url: '<@spring.url '/popup/ProgramInfoSave.ssc'/>',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ProgramSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			alert("저장되었습니다.");
			window.open('about:blank', '_self').close();
			
		}
	});
		
}

function reset(){
		ProgramSearch.keyword.value="";		
		ProgramSearch.startDt.value="";		
		ProgramSearch.endDt.value="";	
		ProgramSearch.channelCode2.value="0";			
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
<link rel="stylesheet" type="text/css" href="<@spring.url '/css/dhtmlxcalendar.css' />" />
<link rel="stylesheet" type="text/css" href="<@spring.url '/css/dhtmlxcalendar_skins.css' />" />

<script type='text/javascript' src='http://code.jquery.com/jquery-1.7.1.js'></script>

<script type="text/javascript" src="<@spring.url '/js/calendar.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/script.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/jquery.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/jquery.blockUI.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/date.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/dhtmlxcommon.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/dhtmlxcalendar.js' />"></script>

<script type=text/javascript>	 
	var $jq = jQuery.noConflict();
	var baseUrl = "<@spring.url '/'/>";
	
    window.dhx_globalImgPath="<@spring.url '../imgs'/>";
	var cal1, cal2, mCal, mDCal, newStyleSheet;
</script>
<script type="text/javascript" src="<@spring.url '/js/jquery.form.js' />"></script>
<script type="text/javascript">
window.onload=function(){
	cal1 = new dhtmlxCalendarObject('calInput1', false, {isYearEditable: true,isMonthEditable: true});
	cal1.setYearsRange(1973, 2020);
	cal1.draw();
	
	cal2 = new dhtmlxCalendarObject('calInput2', false, {isYearEditable: true,isMonthEditable: true});
	cal2.setYearsRange(1973, 2020);
	cal2.draw();
}
</script>
</head>
<body>
<section id="wrap2">
<!-- Container -->
<section id="container2">
	<form name="ProgramSearch" id="ProgramSearch" method="post">
		<@spring.bind "search" />
  		<input type="hidden" name="audioModeMain" value="">
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="pagestart" value="" />
		<input type="hidden" name="pagecurrent" value="" />
		<input type="hidden" name="programCode" value="">
		<input type="hidden" name="pgmId" value="">
		<input type="hidden" name="pgmGrpCd" value="">
		<input type="hidden" name="pgmNm" value="">
		<input type="hidden" name="channelCode" value="">
		<input type="hidden" name="brdDd" value="">
		<input type="hidden" name="ctTyp" value="${ctTyp}">
    <section id="content">
    	<h3 class="blind">프로그램 정보 조회</h3>    
    	
		<article class="title4"><img src="<@spring.url '/images/title_srchpginfo.gif' />" title="프로그램 정보 조회"/></article>
        <!--
        <nav class="snavi2"><span class="home2">HOME</span> &gt;<span class="now2">프로그램 정보 조회</span></nav>
        -->
        
        <article class="tabmenu">
            <ul class="tab1">
            <li class="on"><a href="#"><span>프로그램 정보 조회</span></a></li>
            </ul>
        </article>
        
<!-- Popup -->		
		<article class="ly_pop17" id="ly_pop13">
            <article class="sideinfo2" >
            <dl class="btncover13">
            
            <table summary="" class="boardview6">
            <colgroup><col width="20px"></col><col width=""></col><col width="150px"></col></colgroup>
                <tbody id="liveinfo">
                	<tr>
                		<th class="bggray1"></th><th class="bggray1">사업자</th><th class="bggray1">프로파일</th>
                	</tr>
                	<#list busiPartnerTbls as busiPartnerTbl>
                	<tr>
                		<th>
                		<input type="checkbox" id="${busiPartnerTbl.busiPartnerid?default("")}" name="busiPartnerid" class="busiPartnerid" value="${busiPartnerTbl.busiPartnerid?default("")}">
                		</th>
		                <th>
		                ${busiPartnerTbl.company?default("")}
		                </th>
		                <td>
			                <#if proBusiTbls?has_content>
			                <#list proBusiTbls as proBusiTbl>
				                <#if busiPartnerTbl.busiPartnerid==proBusiTbl.busiPartnerid?default("")>
				                	<#list proFlTbls as proFlTbl>
					                	<#if proFlTbl.proFlid==proBusiTbl.proFlid?default("")>
					                		 <span>${proFlTbl.proFlnm?default("")}</span><br>
					                	</#if>
				                	</#list>
				                	
				                </#if>
			                </#list>
			                <#else>
			                	<span></span>
			                </#if>
		                </td>
                	</tr>
					</#list>
                </tbody>
            </table>
            <#--
            <td>
	            <a href="javascript:updateLiveInfo();"><input type="button" value="저  장"></a>
	            <a href="#" onclick="showDisHide('layer','1');return false;"><input type="button" value="닫  기"></a>
            </td>
            </tr>
            <a href="#" onclick="showDisHide('layer','1');return false;" class="clse">
            	<img src="<@spring.url '/images/button_clse.gif'/>" title="닫기" >
            </a>
            
             <article class="btncover11 pdr5">
	             <span class="btn_pack gry">
	             	 <@button '${search.menuId}' '${user.userId}' 'common.save' 'updateLiveInfo();' '' />
	             </span>
	             <span class="btn_pack gry">
	            	 <a href="#" onclick="showDisHide('layer','1');return false;">닫기</a>
	             </span>
             </article>
            <a href="#" onclick="showDisHide('layer','1');return false;" class="clse">
            	<img src="<@spring.url '/images/button_clse.gif'/>" title="닫기" >
            </a>
            <div class="save">
	            <@imgbutton '${search.menuId}' '${user.userId}' 'common.save' 'updateLiveInfo();' '' '/images/button_save.gif' />
            </div>-->
            </dl>
            </article>	
    	</article> 
<!-- //Popup -->   
   
<!-- Table -->                             
        <article class="bbsview3">  
        <table summary="" id="boardview3">
         <colgroup><col width="60px"></col><col width="80px"></col><col width="50px"></col><col width=""></col><col width="80px"></col><col width="50px"></col><col width="352px"></col></colgroup>
            <thead>
            <tr><th colspan="7">
            	<article class="box2">
                    
	                    <dl>
	                        <dt>채널</dt>
	                        <dd>
	                        	<select name="channelCode2">
		                       
		                        	<option value="0"  <#if search.channelCode2?default(0)?number==0>selected</#if>>&#160; 전체 </option>
		                        	<option value="11" <#if search.channelCode2?default(0)?number==11>selected</#if>>&#160; 1TV </option>
		                        	<option value="12" <#if search.channelCode2?default(0)?number==12>selected</#if>>&#160; 2TV </option>
		                        	<option value="21" <#if search.channelCode2?default(0)?number==21>selected</#if>>&#160; 1라디오 </option>
		                        	<option value="22" <#if search.channelCode2?default(0)?number==22>selected</#if>>&#160; 2라디오 </option>
		                        	<option value="23" <#if search.channelCode2?default(0)?number==23>selected</#if>>&#160; 3라디오 </option>
		                        	<option value="24" <#if search.channelCode2?default(0)?number==24>selected</#if>>&#160; 1FM </option>
		                        	<option value="25" <#if search.channelCode2?default(0)?number==25>selected</#if>>&#160; 2FM </option>
		                        	<option value="26" <#if search.channelCode2?default(0)?number==26>selected</#if>>&#160; 한민족1 </option>
		                        	<option value="61" <#if search.channelCode2?default(0)?number==27>selected</#if>>&#160; DMB </option>
		                       
		                        </select>
	                        </dd>
	                        <dt>방송일자</dt>
	                        <dd>
	                        <input type="text" style="cursor:pointer;"  id="calInput1" name="startDt"  value="<#if search.startDt?exists>${search.startDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>&#126;
	                        <input type="text" style="cursor:pointer;" id="calInput2"  name="endDt"  value="<#if search.endDt?exists>${search.endDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>
	                        </dd>
	                        <dt>프로그램명</dt>
	                        <dd><input type="text" name="keyword" value="<#if search.keyword?exists>${search.keyword}</#if>" onKeyDown="keyEnter();"></input></dd>
	                        <dt></dt>
	                    </dl>
	                    <article class="btncover2">
	                    	<span class="btn_pack gry"><a href="#"  onclick="checkSearch(this)">검색</a></span> <span class="btn_pack gry"><a href="javascript:"  onclick="reset()">초기화</a></span>
	                    </article>
            		
                </article>               
            </th></tr>
            </thead>
<!-- list -->
            <tbody>
            <tr><th>채널</th><th>프로그램코드</th><th>방송구분</th><th>프로그램명</th><th>방송일</th><th>방송시각</th><th>프로파일 설정</th></tr>
				<#assign x = 20>  
            	<#assign y = x>
				<#list contentsTbl as content>
					
					<#if y%2==0>
		            <tr class="gry">
		            <#else>
		            <tr>
		            </#if>
		            	<#assign a = content.brdtime!"">
		            	<#assign b = content.time!"">
		            	<#assign channel=content.channelCode!"">
		            	
		            	<#assign desYn = content.descriptiveVideoServiceYn!"?">
		            	<#assign desNm = "">
		            	<#assign audioMode = content.audioModeMain?default("Stereo")>
		            	<#if desYn == "Y">
		            		<#assign desNm = "[해]">
		            		<#assign audioMode = "Comment">
		     			</#if>
		     			
			            <td>${ tpl.getCodeDesc("CHAN", content.channelCode!"")}</td>
			            <td>${content.pgmCd!""}</td>
			            <td>${content.rerunClassification!""}</td>
			            <#assign size=content.pgmNm?length>
			         	<#if size <= 20>
			         	<td align="left">
			            	<a href="#" onclick="javascript:showDisHide('ly_pop13','1');getLiveInfo('${a?replace('/', '-')}','${content.channelCode!""}','${content.pgmCd!""}','${content.pgmId!""}','${content.pgmGrpCd!""}','${content.pgmNm?replace("'", "")?replace("\"", "")}','${audioMode}');showDisHide('ly_pop13','0');return false;">${desNm}${content.pgmNm!""}(${audioMode})</a>
			            </td>
			            <#else>
			            <td align="left">
			            	&nbsp;<a href="#" onclick="javascript:showDisHide('ly_pop13','1');getLiveInfo('${a?replace('/', '-')}','${content.channelCode!""}','${content.pgmCd!""}','${content.pgmId!""}','${content.pgmGrpCd!""}','${content.pgmNm?replace("'", "")?replace("\"", "")}','${audioMode}');showDisHide('ly_pop13','0');return false;">${desNm}${content.pgmNm?substring(0,20)+" ..."}(${audioMode})</a>
			            </td>
			            </#if>
			         	
			            
			            <#if a == "">
			            	<td></td>
			            <#else>
			            	<td>${a?substring(0,10)}</td>
			            </#if>
			            
			            <#if b == ""> 
			            	<td></td>
			            <#else>
			            	<td>${b?substring(0,2)+" : "+b?substring(2,4)}</td>
			            </#if>	
			            <#if y == 20> 
				            <td rowspan="20">
							</td> 
						</#if>
					</tr>	
					<#assign y = y -1>		
				</#list> 
				<#if y==0>
			        <#else>
				        <#list  1..y  as i > 
					        <#if y%2 ==0>
				        		<tr class="gry">
				        	<#else>
				        		<tr>
				        	</#if>
					            <td></td>
					          	<td></td>
					            <td></td>
					            <td></td>
					            <td></td>
					            <#if y==20>      
					            <td rowspan="20">

		            			</td>
	            			</#if>	
			            </tr>
			            <#assign y = y -1>
				        </#list>
				        
				</#if>       
            </tbody>
<!-- //list -->
	    </table>     
		<script type="text/javascript" ></script>   
	    </article>
	<!-- //Table -->
		<#--
		<#assign page= s_page>
		<#assign cur=current>
		<#if page == "0">
			<table border="1" align="center">
				<tr height="10px"><td></td></tr>
				<tr>
					<#list start..size as i>
						<#if cur== i>
							<td>&nbsp;&nbsp;<font size="3">[${i}]</font>&nbsp;&nbsp;</td>
						<#else>
							<td>&nbsp;&nbsp;<a href="javascript:void(0)" onClick="paging('${i}')">${i}</a>&nbsp;&nbsp;</td>
						</#if>
						<#assign i=i+1>
					</#list>
				</tr>
			</table>
			
		<#else>
		</#if>
		-->
		<article class="btncover4">
		<span class="btn_pack gry">
		<a href="#" onclick="save();">저장</a>
		<#-- 
		<a href="#" onclick="deleteContents('PS-1234567890');">삭제</a>
	
		 <@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteContents();' '' />-->
		</span>
		</article>
		
	
	
	</section>
	
</form>
</section>
<!-- //Container -->


</section>
</body>
<script Language="JavaScript">
showDisHide('ly_pop13','1');
</script>
</html>
