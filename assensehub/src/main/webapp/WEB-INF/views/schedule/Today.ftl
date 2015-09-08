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

function defaultVal(oldValue) {
	if(oldValue == null || oldValue == '' || oldValue == ' ' || oldValue == 'null' || oldValue == 'undefined') return "Stereo";
	else return oldValue;
}

function ajaxData(){
	$jq.ajax({
		url: "<@spring.url '/schedule/ajaxToday.ssc' />",
		cache: false,
		type: 'POST',
		dataType: 'json',
		data: $jq('#listData').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		
		 var table ="";
		 var daytable ="";
		 
		 //-- ajax 실시간 데이타 GET & Write (편성표 ) by dekim
		 	for(var iq=0;iq<data.contents.length;iq++){
			 $jq('#'+parseInt(data.contents[iq].programPlannedStartTime.substring(0,2),10)+'week').empty();
			 var table ="";
				for(var i=0;i<data.contents.length;i++){
				 	if(data.contents[iq].programPlannedStartTime.substring(0,2)==data.contents[i].programPlannedStartTime.substring(0,2)){
						 table += '<p><b>'+data.contents[i].programPlannedStartTime.substring(2,4)+' '+data.contents[i].programTitle+'<b><br/>'
						 if(defaultVal(data.contents[i].audioModeMain) == 'Comment'){
						 	table += '<img src="<@spring.url '/images/icon_hae.gif'/>" title="화면해설"/>&nbsp;'
						 }
						 if(data.contents[i].productionTypeCode=='01'){
						 	table += '<img src="<@spring.url '/images/icon_onair.gif'/>" title="생방"/>&nbsp;'
						 }else if(data.contents[i].productionTypeCode=='02'){
						  	table += '<img src="<@spring.url '/images/icon_rec.gif'/>" title="녹화"/>&nbsp;'
						 }
						 if(data.contents[i].productionVideoQuality=='HD'){
						 	table += '<img src="<@spring.url '/images/icon_h.gif'/>" title="HD"/>&nbsp;'
						 }else if(data.contents[i].productionVideoQuality=='SD'){
						  	table += '<img src="<@spring.url '/images/icon_s.gif'/>" title="SD"/>&nbsp;'
						 }
						 if(data.contents[i].rerunClassificationCode=='02'){
						 	table += '<img src="<@spring.url '/images/icon_rerun.gif'/>" title="재방"/>&nbsp;'
						 }
						 
					 	table += '<a href="#" onclick="javascript:showDisHide(\'layer000\',\'1\');getLiveInfo(\''+data.contents[i].programCode+'\',\''+data.contents[i].rerunClassificationCode+'\',\''+defaultVal(data.contents[i].audioModeMain)+'\');showDisHide(\'layer000\',\'0\');return false;">'
					 	if(data.contents[i].recyn=='Y'){
					 		table += '<img id=\''+data.contents[i].programCode+'_'+data.contents[i].rerunClassificationCode+'\' src="<@spring.url '/images/icon_np.gif'/>" title="p"/></a><p/>'
					 	}else if(data.contents[i].recyn=='N'){
					 		table += '<img id=\''+data.contents[i].programCode+'_'+data.contents[i].rerunClassificationCode+'\' src="<@spring.url '/images/icon_x.gif'/>" title="p"/></a><p/>'
					 	}else{
					 		table += '<img id=\''+data.contents[i].programCode+'_'+data.contents[i].rerunClassificationCode+'\' src="<@spring.url '/images/icon_question.gif'/>" title="p"/></a><p/>'
					 	}
					 	table += '<p/>'
				 	}
			 	}
			 	
			 $jq('#'+parseInt(data.contents[iq].programPlannedStartTime.substring(0,2),10)+'week').append(table);
			}
			
			//-- ajax 실시간 데이타 GET & Write (운행표 ) by dekim
			if(data.dairyOrderLists.length != 0){
				for(var jq=0;jq<data.dairyOrderLists.length;jq++){
				$jq('#'+parseInt(data.dairyOrderLists[jq].runningStartTime.substring(0,2),10)+'day').empty();
					 var daytable ="";
					for(var j=0;j<data.dairyOrderLists.length;j++){
						 if(data.dairyOrderLists[jq].runningStartTime.substring(0,2)==data.dairyOrderLists[j].runningStartTime.substring(0,2)){
							 daytable += '<p><b>'+data.dairyOrderLists[j].runningStartTime.substring(2,4)+':'+data.dairyOrderLists[j].runningStartTime.substring(4,6)+' '+data.dairyOrderLists[j].programTitle+'<b><br/>'
					 		
					 		
					 		if(defaultVal(data.dairyOrderLists[i].audioModeMain) == 'Comment'){
						 		table += '<img src="<@spring.url '/images/icon_hae.gif'/>" title="화면해설"/>&nbsp;'
						 	}
						 
					 	 	if(data.dairyOrderLists[j].productionTypeCode=='01'){
							 	daytable += '<img src="<@spring.url '/images/icon_onair.gif'/>" title="생방"/>&nbsp;'
							 }else if(data.dairyOrderLists[j].productionTypeCode=='02'){
							  	daytable += '<img src="<@spring.url '/images/icon_rec.gif'/>" title="녹화"/>&nbsp;'
							 }
							
							 if(data.dairyOrderLists[j].productionVideoQuality=='HD'){
							 	daytable += '<img src="<@spring.url '/images/icon_h.gif'/>" title="HD"/>&nbsp;'
							 }else if(data.dairyOrderLists[j].productionVideoQuality=='SD'){
							  	daytable += '<img src="<@spring.url '/images/icon_s.gif'/>" title="SD"/>&nbsp;'
							 }
							 if(data.dairyOrderLists[j].rerunClassification=='재방'){
							 	
							 	daytable += '<img src="<@spring.url '/images/icon_rerun.gif'/>" title="재방"/>&nbsp;'
							 }
							var rerunCode=''+data.dairyOrderLists[j].rerunClassification+'';
							
							//alert(rerunCode);
							if(rerunCode == "본방"){
								var rCode = '01';
							}else{
							    var rCode = '02';
							}
							
							//alert(rCode); 
					 		daytable += '<a href="#" onclick="javascript:showDisHide(\'layer000\',\'1\');getLiveInfo(\''+data.dairyOrderLists[j].programCode+'\',\''+rCode+'\',\''+defaultVal(data.dairyOrderLists[j].audioModeMain)+'\');showDisHide(\'layer000\',\'0\');return false;">'
					 		if(data.dairyOrderLists[j].recyn=='Y'){
						 		daytable += '<img id=\''+data.dairyOrderLists[j].programCode+'_'+rCode+'\' src="<@spring.url '/images/icon_np.gif'/>" title="p"/></a><p/>'
					 		}else if(data.dairyOrderLists[j].recyn=='N'){
					 			daytable += '<img id=\''+data.dairyOrderLists[j].programCode+'_'+rCode+'\' src="<@spring.url '/images/icon_x.gif'/>" title="p"/></a><p/>'
					 		}else{
					 			daytable += '<img id=\''+data.dairyOrderLists[j].programCode+'_'+rCode+'\' src="<@spring.url '/images/icon_question.gif'/>" title="p"/></a><p/>'
					 		}
					 	
						 	daytable += '<p/>'
					 	}
					}
				 	
				 $jq('#'+parseInt(data.dairyOrderLists[jq].runningStartTime.substring(0,2),10)+'day').append(daytable);
				}
			}else{
					//-- 데이타 초기화
				for(var iTmp=6;iTmp<28;iTmp++){
					$jq('#'+iTmp+'day').empty();
				}
			}
            
		},
		complete:function(){
		
		}
			
	});
	refreshTime();
}

function getLiveInfo(programCode, rerunCode, audioModeMain){

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
				if(data.liveTbl.recyn==null||data.liveTbl.recyn=="N"){
					obj.recyn[1].checked=true;
				}else{
					obj.recyn[0].checked=true;
				}
			}
			$jq(".busiPartnerid").attr("checked",false);
			if(data.busiPartnerPgm != null){
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
	
	$jq.ajax({
		url: '<@spring.url '/schedule/updateLiveInfo.ssc' />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#live').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			
			if(data.result=="Y"){
			   if(data.recyn=="N"){
			   		$jq('.'+data.programCode+'_'+data.rerunCode+'').attr("src", "<@spring.url '/images/icon_x.gif'/>");
			   }else if(data.recyn=="Y"){
				   	$jq('.'+data.programCode+'_'+data.rerunCode+'').attr("src", "<@spring.url '/images/icon_np.gif'/>");
			   }else{
			   		$jq('.'+data.programCode+'_'+data.rerunCode+'').attr("src", "<@spring.url '/images/icon_question.gif'/>");
			   }
				alert("저장되었습니다!.");
				showDisHide('layer000','1');
			}
			
		}
	});
}

function refreshTime(){
		  window.setTimeout("ajaxData()", 1000 * 10 );
	}

<#--
window.onload = function(){
    ajaxData();
}
-->

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
	var obj = ($jq('#live').get(0));
	$jq.ajax({
		url: '<@spring.url '/schedule/TodayInterlock.ssc'/>',
		type: 'POST',
		dataType: 'json',
		data: $jq('#live').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			//alert('연동완료입니다.!');
				document.ContentsSearch.action="<@spring.url '/schedule/Today.ssc'/>";
				document.ContentsSearch.submit();
		}
	});
}

function programSearch(){
	//alert(live.popupGB.value);
	
	window.open("/popup/scd_ProgramSearch.ssc?ctTyp=00","programSearch","width=970,height=820,scrollbars=yes");
}
</script>

<section id="container">
    <section id="content">
    	<h3 class="blind">TV편성표</h3>
    	<@spring.bind "search" />
		<article class="title"><img src="<@spring.url '/images/title_pglog.gif'/>" title="TV편성표"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 편성표 &gt; <span class="now"><#if search.channelCode=="11">1<#elseif search.channelCode="12">2</#if>TV</span></nav>
        <#if search.tabGubun=="3">
        	<article class="date">${tpl.commonUtils().getTimestamp("yyyy-MM-dd")}(${tpl.commonUtils().getDayOfWeek(tpl.commonUtils().getTimestamp())})</article>
        <#else>
            <article class="date">${tpl.commonUtils().getDate(1)}(${tpl.commonUtils().getDayOfWeek(tpl.commonUtils().addDate(1))})</article>
        </#if>
        <article class="tabmenu">
            <ul class="tab1">
            	    <#if search.tabGubun=="1"><li class="on"><#else><li class="off"></#if><a href="#" onclick="javascript:goWeekInfo('1');"><span>이번주</span></a></li>
	            <#if search.tabGubun=="2"><li class="on"><#else><li class="off"></#if><a href="#" onclick="javascript:goWeekInfo('2');"><span>다음주</span></a></li>
	            <#if search.tabGubun=="3"><li class="on"><#else><li class="off"></#if><a href="#" onclick="javascript:goDayInfo('3');"><span>오늘</span></a></li>
	            <#if search.tabGubun=="4"><li class="on"><#else><li class="off"></#if><a href="#" onclick="javascript:goDayInfo('4');"><span>내일</span></a></li>
            </ul>
            
            <article class="btncover16">
	            <p>변경시간: ${tpl.getChangeTime(search.channelCode,'00','D')}</p>
	        </article>
            <article class="btncover12">
            <p><span class="btn_pack medium"><span class="refresh"></span><a href="#" onclick="javascript:interface();">즉시갱신</a></span></p>
            <p><span class="btn_pack medium"><a href="javascript:programSearch();">PG</a></span></p>
            </article>
        </article>

<!--popup div start -->
		<form id="live" name="live" method="get">
		<@spring.bind "search" />
		<input type="hidden" name="tabGubun" value="${search.tabGubun}">
		<input type="hidden" name="channelCode" value="${search.channelCode}">
		<input type="hidden" name="menuId" value="${search.menuId}">
		<input type="hidden" name="programCode" value="">
		<input type="hidden" name="rerunCode" value="">
		<input type="hidden" name="audioModeMain" value="">
		
		<article id="layer000"  class="ly_pop16" style="display:none">
            <ul id="recinfo">
                <li class="blank"><input name="recyn" type="radio" value="Y" >녹화</li>
                <li class="blank"><input name="recyn" type="radio" value="N" checked >녹화안함</li>
                <li class="right" id="programCode"></li>
            </ul>
            <ul class="bggray1">
                <li class="left">사업자</li>
                <li class="right">프로파일</li>
            </ul>
            <table summary="" class="tb_pop">
            <colgroup><col width="150px"></col><col></col></colgroup>
                <tbody id="liveinfo">
                	<#list busiPartnerTbls as busiPartnerTbl>
                	<tr>
		                <th>
		                <input type="checkbox" id="${busiPartnerTbl.busiPartnerid?default("")}" name="busiPartnerid" class="busiPartnerid" value="${busiPartnerTbl.busiPartnerid?default("")}">${busiPartnerTbl.company?default("")}
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
            <#--
            <td>
	            <a href="javascript:updateLiveInfo();"><input type="button" value="저  장"></a>
	            <a href="#" onclick="showDisHide('layer000','1');return false;"><input type="button" value="닫  기"></a>
            </td>
            </tr>
            <a href="#" onclick="showDisHide('layer000','1');return false;" class="clse">
            	<img src="<@spring.url '/images/button_clse.gif'/>" title="닫기" >
            </a>
            -->
             <article class="btncover11 pdr5">
	             <span class="btn_pack gry">
	             	 <@button '${search.menuId}' '${user.userId}' 'common.save' 'updateLiveInfo();' '' />
	             </span>
	             <span class="btn_pack gry">
	            	 <a href="#" onclick="showDisHide('layer000','1');return false;">닫기</a>
	             </span>
             </article>
            <a href="#" onclick="showDisHide('layer000','1');return false;" class="clse">
            	<img src="<@spring.url '/images/button_clse.gif'/>" title="닫기" >
            </a>
            <div class="save">
	            <@imgbutton '${search.menuId}' '${user.userId}' 'common.save' 'updateLiveInfo();' '' '/images/button_save.gif' />
            </div>
    	</article>
		</form>
<!--popup div end -->


<!-- Table -->
	<form id="listData" name="listData" method="get">
		<@spring.bind "search" />
		<input type="hidden" name="channelCode" value="${search.channelCode}">
		<input type="hidden" name="tabGubun" value="${search.tabGubun}">
		<input type="hidden" name="menuId" value="${search.menuId}">
        <article class="bbsview">
        <table summary="" class="boardview">
        <colgroup><col width="40px"></col><col width="210px"></col><col width=""></col><col width="382px"></col></colgroup>
            <thead>
            <tr>
            <th></th><th>편성표</th><th>주조운행표</th><th>프로파일 설정</th></tr>
            </thead>
            
            <#if search.channelCode=="12">
            <#assign s_hour=3>
	        <#assign e_hour=25>
	        <#else>
	        <#assign s_hour=5>
	        <#assign e_hour=28>
	        </#if>
	        
            <tbody id="ajaxData">
            <#list s_hour..e_hour as i>
	            <tr>
	            	<th class="pd">${i}</th>
		            <td id="${i}week">
		             <#list contents as content>
        					<#if content.programPlannedStartTime?substring(0,2)?number==i> 
		         				  <p><b>${content.programPlannedStartTime?substring(2,4)} ${content.programTitle}<b><br/>
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
					            	<a href="#" onclick="javascript:showDisHide('layer000','1');getLiveInfo('${content.programCode?default("")}','${content.rerunClassificationCode?default("")}','${content.audioModeMain?default("Stereo")}');showDisHide('layer000','0');return false;">
					            	<#if content.recyn?default("")=="Y">
					            		<img id="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}"  class="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}" src="<@spring.url '/images/icon_np.gif'/>" title="p"/>
					            	<#elseif content.recyn?default("")=="N">
					            		<img id="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}"  class="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}" src="<@spring.url '/images/icon_x.gif'/>" title="p"/>
					            	<#else>
					            		<img id="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}"  class="${content.programCode?default("")}_${content.rerunClassificationCode?default("")}" src="<@spring.url '/images/icon_question.gif'/>" title="p"/>
					            	</#if>
					            	</a><p/>
					            	<#--아이콘 오면 수정 요망 끝 -->
					        	<p/>
		          		</#if>
		          	</#list>  	
	            	</td>
		            <td id="${i}day">
		            <#list dairyOrderLists as dairyOrderList>
		            	<#if dairyOrderList.runningStartTime?substring(0,2)?number==i> 
		            		 <p><b>${dairyOrderList.runningStartTime?substring(2,4)} ${dairyOrderList.programTitle}<b><br/>
		            		 		<#if dairyOrderList.audioModeMain?default("Stereo")=="Comment">
								    	<img src="<@spring.url '/images/icon_hae.gif'/>" title="화면해설"/>&nbsp;							
								    </#if>
		            		 		<#if dairyOrderList.productionTypeCode?default("")=="01">
					            		<img src="<@spring.url '/images/icon_onair.gif'/>" title="생방"/>&nbsp;
					            	<#elseif dairyOrderList.productionTypeCode?default("")=="02" >
					            		<img src="<@spring.url '/images/icon_rec.gif'/>" title="녹화"/>&nbsp;
					            	<#else>
					            	</#if>
					            	<#if dairyOrderList.productionVideoQuality?default("")=="HD">
					            		<img src="<@spring.url '/images/icon_h.gif'/>" title="HD"/>&nbsp;
					            	<#elseif dairyOrderList.productionVideoQuality?default("")=="SD" >
					            		<img src="<@spring.url '/images/icon_s.gif'/>" title="SD"/>&nbsp;
					            	<#else>
					            	</#if>
					            	<#if dairyOrderList.rerunClassification?default("")=="재방">
					            		<img src="<@spring.url '/images/icon_rerun.gif'/>" title="재방"/>&nbsp;
					            	</#if>
					            	<!--아이콘 오면 수정 요망 시작 -->
					            	<#assign rerunCode = dairyOrderList.rerunClassification!"">
					            	<#if rerunCode == "본방">
					            		<#assign rCode = "01">
					            	<#else>
					            		<#assign rCode = "02">
					            	</#if>
					            	<a href="#" onclick="javascript:showDisHide('layer000','1');getLiveInfo('${dairyOrderList.programCode?default("")}','${rCode}','${dairyOrderList.audioModeMain?default("Stereo")}');showDisHide('layer000','0');return false;">
					            	
					            	<#if dairyOrderList.recyn?default("")=="Y">
					            		<img id="${dairyOrderList.programCode?default("")}_${rCode}"  class="${dairyOrderList.programCode?default("")}_${rCode}" src="<@spring.url '/images/icon_np.gif'/>" title="p"/>
					            	<#elseif dairyOrderList.recyn?default("")=="N">
					            		<img id="${dairyOrderList.programCode?default("")}_${rCode}"  class="${dairyOrderList.programCode?default("")}_${rCode}" src="<@spring.url '/images/icon_x.gif'/>" title="p"/>
					            	<#else>
					            		<img id="${dairyOrderList.programCode?default("")}_${rCode}"  class="${dairyOrderList.programCode?default("")}_${rCode}" src="<@spring.url '/images/icon_question.gif'/>" title="p"/>
					            	</#if>
					            	</a>
					            	<p/>
					            	<!--아이콘 오면 수정 요망 끝 -->
					        	<p/>
		            	</#if>
		            </#list>
		            </td>
		            <#if i?number==6>
	            	<td rowspan="24"></td>
	            	</#if>
	            </tr>
            <!-- //프로그램 반복 -->
            </#list>
            
            </tbody>
        </table>
        </article>
	</form>
<!-- //Table -->


	</section>