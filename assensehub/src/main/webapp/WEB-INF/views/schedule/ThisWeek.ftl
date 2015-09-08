<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl">
<script type="text/javascript">
function ajaxGET() {
	var obj = ($jq('#live').get(0));
	alert(obj);
}
function ajaxPOST() {
	var obj = ($jq('#live').get(0));
	alert(obj);
}

$jq(document).ready(function(){

   $jq("img").click(function(e){
   	
	  var p=$jq('#position_init');

   	   var posX =25;
   	   var posY =80;
   	   
      $jq('#layer001').attr("style","display:none;position:absolute;left:"+(e.pageX-p.offset().left+posX)+"px;top:"+(e.pageY-p.offset().top+posY)+"px ");
   }); 
})

function getLiveInfo(programCode,rerunCode,audioModeMain){

	live.programCode.value=programCode;
	live.rerunCode.value=rerunCode;
	
	if(audioModeMain == 'Mono') {
		live.audioModeMain.value = '01';
	} else if (audioModeMain == 'Stereo') {
		live.audioModeMain.value = '02';
	} else if (audioModeMain == 'Comment') {
		live.audioModeMain.value = '04';
	} else {
		live.audioModeMain.value = '03';
	}
	
	var obj = ($jq('#live').get(0));
	
	$jq.ajax({
		url: '<@spring.url '/schedule/findLive.ssc' />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#live').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			if(data.liveTbl!=null){
			
				if(data.liveTbl.recyn==null||data.liveTbl.recyn=="Y"){
					obj.recyn[0].checked=true;
				}else{
					obj.recyn[1].checked=true;
				}
			}else{
				obj.recyn[0].checked=true;
			}	
			
			$jq(".busiPartnerid").attr("checked",false);
			if(data.busiPartnerPgm!=null){
				for(var i=0;i<data.busiPartnerPgm.length;i++){
					$jq('#'+data.busiPartnerPgm[i].busiPartnerid+'').attr("checked",true);
					//$jq('input[name='+data.busiPartnerPgm[i].busiPartnerid+']').attr("checked",true);
				}
			}
			
			document.getElementById('programCode').innerHTML=programCode+'('+audioModeMain+')';
		}
	});
}

function updateLiveInfo(){
	var obj = ($jq('#live').get(0));
	
	alert(obj.audioModeMain.value);
	
	$jq.ajax({
		url: '<@spring.url '/schedule/updateLiveInfo.ssc' />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#live').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		//alert("data.result :"+data.result+" data.recyn :"+data.recyn);
			if(data.result=="Y"){
			   if(data.recyn=="N"){
			   		$jq('.'+data.programCode+'_'+data.rerunCode+'').attr("src", "<@spring.url '/images/icon_x.gif'/>");
			   }else if(data.recyn=="Y"){
				   	$jq('.'+data.programCode+'_'+data.rerunCode+'').attr("src", "<@spring.url '/images/icon_np.gif'/>");
			   }else{
			   		$jq('.'+data.programCode+'_'+data.rerunCode+'').attr("src", "<@spring.url '/images/icon_question.gif'/>");
			   }
				alert("저장되었습니다!.");
				showDisHide('layer001','1');
			}
			
		}
	});
}

window.onload = function(){
	//updateLivInfo();
    //ajaxJSON();
    
}

function goWeekInfo(value){
document.live.action="<@spring.url '/schedule/ThisWeek.ssc'/>";
	document.live.tabGubun.value=value;
	document.live.submit();
}
function goDayInfo(value){
	document.live.action="<@spring.url '/schedule/Today.ssc'/>";
	document.live.tabGubun.value=value;
	document.live.submit();
}

function interface(){
	alert('편성표를 갱신하겠습니다.');
	//document.live.tabGubun.value=1;
	
	var obj = ($jq('#live').get(0));
	$jq.ajax({
		url: '<@spring.url '/schedule/ThisWeekInterlock.ssc'/>',
		type: 'POST',
		dataType: 'json',
		data: $jq('#live').serialize(),
		error: function(){
			alert('Return Error.');
		},
		success: function(data){
				
				alert('연동완료!!');
				location.reload(); 
				//document.ContentsSearch.action="<<@spring.url '/schedule/ThisWeek.ssc'/>";
				//document.ContentsSearch.submit();
		}
	});
	
}
function programSearch(){
	//alert(live.popupGB.value);
	
	window.open("/popup/scd_ProgramSearch.ssc?ctTyp=00","programSearch","width=970,height=820,scrollbars=yes");
}


function goLocalWeekInfo(value){
	//alert(value);
		   
	document.live.action="<@spring.url '/schedule/ThisWeek.ssc'/>";
	document.live.localCode.value=value;
	document.live.submit();
}
</script>

 <script language="JavaScript" type="text/JavaScript">
  
</script>

<section id="container">
    <section id="content">
    	<h3 class="blind">TV편성표  </h3>
		<@spring.bind "search" />
			<article class="title"><img src="<@spring.url '/images/${search.localCode}.gif'/>" title="TV편성표"/></article>
	        <nav class="snavi">
	        	<#if search.channelCode=="11">
		        	<select name="localCode" onchange="javascript:goLocalWeekInfo(this.value);">
		        		
		        		<#if doads?has_content>
		                <#list doads as doad>
		                	<option value="${doad.sclCd}" <#if search.localCode=="${doad.sclCd}"> selected</#if>>&#160; ${doad.clfNm}&#160;</option>
		                </#list>
		                
		                </#if>
		        	</select>
	        	</#if>
	        	<#if search.channelCode=="12">
		        	<select name="localCode" onchange="javascript:goLocalWeekInfo(this.value);">
		        		
		        		<#if doads?has_content>
		                <#list doads as doad>
		                	<option value="${doad.sclCd}" <#if search.localCode=="${doad.sclCd}"> selected</#if>>&#160; ${doad.clfNm}&#160;</option>
		                </#list>
		                
		                </#if>
		        	</select>
	        	</#if>
	        	<#if search.channelCode=="21">
		        	<select name="localCode" onchange="javascript:goLocalWeekInfo(this.value);">
		        		
		        		<#if doads?has_content>
		                <#list doads as doad>
		                	<option value="${doad.sclCd}" <#if search.localCode=="${doad.sclCd}"> selected</#if>>&#160; ${doad.clfNm}&#160;</option>
		                </#list>
		                
		                </#if>
		        	</select>
	        	</#if>
	        	<span class="home"> HOME</span> &gt; 편성표 &gt;<span class="now">
	        <#if search.channelCode=="11">1TV
	        <#elseif search.channelCode=="12">2TV
	        <#elseif search.channelCode=="21">1라디오
	        <#elseif search.channelCode=="22">2라디오
	        <#elseif search.channelCode=="23">3라디오
	        <#elseif search.channelCode=="24">1FM
	        <#elseif search.channelCode=="25">2FM
	        <#elseif search.channelCode=="26">한민족1
	        <#elseif search.channelCode=="27">한민족2
	        <#elseif search.channelCode=="61">DMB
	        <#else></#if></span></nav>
	        
	       
			
	        <article class="tabmenu">
	            <ul class="tab1">
	            <#if search.tabGubun=="1"><li class="on"><#else><li class="off"></#if><a href="#" onclick="javascript:goWeekInfo('1');"><span>이번주</span></a></li>
	            <#if search.tabGubun=="2"><li class="on"><#else><li class="off"></#if><a href="#" onclick="javascript:goWeekInfo('2');"><span>다음주</span></a></li>
	            
	            <#if search.channelCode=="11" || search.channelCode=="12">
	            	<#if search.localCode=="00">
	            		<#if search.tabGubun=="3"><li class="on"><#else><li class="off"></#if><a href="#" onclick="javascript:goDayInfo('3');"><span>오늘</span></a></li>
	            		<#if search.tabGubun=="4"><li class="on"><#else><li class="off"></#if><a href="#" onclick="javascript:goDayInfo('4');"><span>내일</span></a></li>
	            	</#if>
	            <#else>
	            </#if>
	            </ul>
	            
	            <article class="btncover15">
	            	<p>변경시간: ${tpl.getChangeTime(search.channelCode,search.localCode,'W')}</p>
	            </article>
	            <article class="btncover14">       
	            	<p><span class="btn_pack medium"><span class="link"></span><a href="#" onclick="javascript:interface();">갱신</a><#--<@button '${search.menuId}' '${user.userId}' 'common.interface' 'interface();' '' />--></span></p>
	            	<p><span class="btn_pack medium"><a href="javascript:programSearch();">PG</a></span></p>
	            </article>
	        </article>
		<h2 id="position_init"></h2>

		<!--popup div start -->
		<form id="live" name="live" method="get">
		
		<@spring.bind "search" />
		<input type="hidden" name=audioModeMain value="">
		<input type="hidden" name=rerunCode value="">
		<input type="hidden" name=programCode value="">
		<input type="hidden" name=bgnTime value="">
		<input type="hidden" name=endTime value="">
		<input type="hidden" name=localCode value="${search.localCode}">
		<input type="hidden" name=tabGubun value="${search.tabGubun}">
		<input type="hidden" name=channelCode value="${search.channelCode}">
		<input type="hidden" name=menuId value="${search.menuId}">
		<input type="hidden" name=lCode value="${search.localCode}">
		
		<article id="layer001"  class="ly_pop8" style="display:none;position:absolute;left:0px;top:0px">
            <ul id="recinfo">
            	<#if search.channelCode=="11">
            		<li class="blank"><input name="recyn" type="radio" value="Y" checked>녹화</li>
                	<li class="blank"><input name="recyn" type="radio" value="N">녹화안함</li>
		        <#elseif search.channelCode=="12">
		        	<li class="blank"><input name="recyn" type="radio" value="Y" checked>녹화</li>
                	<li class="blank"><input name="recyn" type="radio" value="N">녹화안함</li>
		        <#elseif search.channelCode=="21">
		        	<li class="blank"><input name="recyn" type="radio" value="Y" checked>확인</li>
                	<li class="blank"><input name="recyn" type="radio" value="N">미확인</li>
		        <#elseif search.channelCode=="22">
		       	 	<li class="blank"><input name="recyn" type="radio" value="Y" checked>확인</li>
                	<li class="blank"><input name="recyn" type="radio" value="N">미확인</li>
		        <#elseif search.channelCode=="23">
		        	<li class="blank"><input name="recyn" type="radio" value="Y" checked>확인</li>
                	<li class="blank"><input name="recyn" type="radio" value="N">미확인</li>
		        <#elseif search.channelCode=="24">
		        	<li class="blank"><input name="recyn" type="radio" value="Y" checked>확인</li>
                	<li class="blank"><input name="recyn" type="radio" value="N">미확인</li>
		        <#elseif search.channelCode=="25">
		        	<li class="blank"><input name="recyn" type="radio" value="Y" checked>확인</li>
                	<li class="blank"><input name="recyn" type="radio" value="N">미확인</li>
		        <#elseif search.channelCode=="26">
		        	<li class="blank"><input name="recyn" type="radio" value="Y" checked>확인</li>
                	<li class="blank"><input name="recyn" type="radio" value="N">미확인</li>
		        <#elseif search.channelCode=="61">
		        	<li class="blank"><input name="recyn" type="radio" value="Y" checked>확인</li>
                	<li class="blank"><input name="recyn" type="radio" value="N">미확인</li>
		        <#else></#if>
                
                <li class="right" id="programCode"></li>
            </ul>
            <ul class="bggray1">
                <li class="left">사업자</li>
                <li class="right">프로파일</li>
            </ul>
            <table summary="" class="tb_pop">
            <colgroup><col width="180px"></col><col></col></colgroup>
                <tbody id="liveinfo">
                	<#list busiPartnerTbls as busiPartnerTbl>
                	<tr>
		                <th>&nbsp;
		                <input type="checkbox" id="${busiPartnerTbl.busiPartnerid?default("")}" name="busiPartnerid" class="busiPartnerid" value="${busiPartnerTbl.busiPartnerid?default("")}">
		                ${busiPartnerTbl.company?default("")}
		                </th>
		                <td>
			                <#if proBusiTbls?has_content>
			                <#list proBusiTbls as proBusiTbl>
				                <#if busiPartnerTbl.busiPartnerid==proBusiTbl.busiPartnerid?default("")>
				                	<#list proFlTbls as proFlTbl>
					                	<#if proFlTbl.proFlid==proBusiTbl.proFlid?default("")>
					                		 <span>${proFlTbl.proFlnm?default("")}</span>
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
             <article class="btncover11 pdr5">
	             <span class="btn_pack gry">
	             <#--
	             <#macro button menuId userId btId url css>
	              <a href="#" onclick="updateLiveInfo();">저장</a>
	             -->
	             <@button '${search.menuId}' '${user.userId}' 'common.save' 'updateLiveInfo();' '' />
	             </span>
	             <span class="btn_pack gry">
	            	 <a href="#" onclick="showDisHide('layer001','1');return false;">닫기</a>
	             </span>
             </article>
            <a href="#" onclick="showDisHide('layer001','1');return false;" class="clse">
            	<img src="<@spring.url '/images/button_clse.gif'/>" title="닫기" >
            </a>
            <div class="save">
            <#--
	               <a href="#" onclick="updateLiveInfo();">
	            	<img src="<@spring.url '/images/button_save.gif'/>" title="저장">
	            </a>
            -->
            	<@imgbutton '${search.menuId}' '${user.userId}' 'common.save' 'updateLiveInfo();' '' '/images/button_save.gif' />
            </div>
            <#--
            <article class="btncover11 pdr5"><span class="btn_pack gry"><a href="#">저장</a></span> <span class="btn_pack gry"><a href="#" onclick="showHide('layer0');return false;">닫기</a></span></article>
            <a href="#layer0" onclick="showHide('layer0');return false;" class="clse"><img src="images/button_clse.gif" title="닫기"></a>
            <div class="save"><a href="#"><img src="images/button_save.gif" title="저장"></a></div>
            -->
    	</article>
		</form>
<!--popup div end -->


<!-- Table -->
        <article class="bbsview">
        <table summary="" class="boardview">
        <colgroup><col width="40px"></col><col width="120px"></col><col width="120px"></col><col width="120px"></col><col width="120px"></col><col width="120px"></col><col width="120px"></col><col width=""></col></colgroup>
            <thead>
            	<tr>
            	<th></th>
            	<#list 2..8 as i>
            	<th>${tpl.commonUtils().getDateOfWeek((search.tabGubun?number -2), i)}(${tpl.commonUtils().getDayOfWeekSun()[i-2]})</th>
            	</#list>
            	</tr>
            </thead>
            <tbody>
            
            <#if search.channelCode=="26">
            <#assign s_hour=0>
	        <#elseif search.channelCode=="27">
	        <#assign s_hour=0>
	        <#elseif search.channelCode=="12">
	        <#assign s_hour=3>
	        <#else>
	        <#assign s_hour=5>
	        </#if>
	        
	        <#if search.channelCode=="12">
	        <#assign e_hour=25>
	        <#else>
	        <#assign e_hour=28>
	        </#if>
	        
	        <#assign title="KBS 뉴스특보 태풍 '다나스' 한반도 북상">
            <#list s_hour..e_hour as i> <!-- 시간대 루프 초기화 -->
	            <tr><!-- 시간 대-->
	            	<th class="pd">${i}</th>
		            	<#list 1..7 as j>
				            <td class="pdl5">
				             <#list contents as content>
	        					<#if content.programPlannedStartTime?substring(0,2)?number==i> 
				           			<#if content.onairDayCode?number==j >
							            	<p><b>${content.programPlannedStartTime?substring(2,4)} ${content.programTitle!""}<b><br/>
							            		<#if content.audioModeMain?default("Stereo")=="Comment">
								            		<img src="<@spring.url '/images/icon_hae.gif'/>" title="화면해설"/>&nbsp;							
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
								            	<a href="#" onclick="showDisHide('layer001','1');getLiveInfo('${content.programCode?default("")}','${content.rerunClassificationCode?default("")}','${content.audioModeMain?default("Stereo")}');showDisHide('layer001','0');return false;">
								            	<#if content.recyn?default("")=="Y">
								            		<img id="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}"  class="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}" src="<@spring.url '/images/icon_np.gif'/>" title="p"/>
								            	<#elseif content.recyn?default("")="N">
								            		<img id="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}"  class="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}" src="<@spring.url '/images/icon_x.gif'/>" title="p"/>
								            	<#else>
								            		<img id="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}"  class="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}" src="<@spring.url '/images/icon_question.gif'/>" title="p"/>
								            	</#if>
								            	</a><p/>
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
	