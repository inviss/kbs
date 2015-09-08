<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl">
<script type="text/javascript">
	
	// 검색 
function checkSearch(){ 
	document.manualContents.action="<@spring.url '/content/ManualReg.ssc'/>";
	document.manualContents.submit();
}

function localCheckSearch(localCode){ 
	document.manualContents.localCode.value=localCode;
	document.manualContents.fTyp.value="";
	document.manualContents.action="<@spring.url '/content/ManualReg.ssc'/>";
	document.manualContents.submit();
}

function keyEnter() {   
	if(event.keyCode ==13) {   //엔터키가 눌려지면,
		checkSearch(manualContents);    //검색 함수 호출
	}
}
	
function goSchedule(){
	
	var vaGubun = document.manualContents.vaGubun.value;
	var ctTyp=document.manualContents.ctTyp.value;
	var localCode=document.manualContents.localCode.value;
	var fTyp=document.manualContents.fTyp.value;
	
	var ctx = '<@spring.url ''/>';
	
	if(vaGubun == "doad"){
		if(localCode == "00"){
			alert("지역총국을 선택하세요");
			return;
		}		
	}else{
		localCode="00";
	}
	
	if(ctTyp == ""){
		alert("콘텐츠 타입을 선택하세요");
		return;
	}else{
		if(ctTyp == "00"){
			window.open(ctx+"/popup/Schedule.ssc?gubun="+vaGubun+"&ctTyp="+ctTyp+"&localCode="+localCode+"&fTyp="+fTyp,"goSchedule","width=970,height=700,scrollbars=yes");
		}else{
			alert("본방 일때만 편성표 조회가 가능합니다.");
			return;
		}	
	}
	
}
function isEmpty(value){
	if(typeof(value)=='number'){    
	  	return (value === undefined || value == null || value.length <= 0) ? 0 : value;
	  }else{
	  	return (value === undefined || value == null || value.length <= 0) ? "" : value;
	  } 
 }

function checkEmpty(value){
	if(typeof(value)=='number'){    
	  	return (value === undefined || value == null || value.length <= 0) ? false : true;
	  }else{
	  	return (value === undefined || value == null || value.length <= 0) ? false : true;
	  } 
 }
 
 function removeFileList(){
 	
 	var obj = document.forms["manualContents"];
	var chk = document.getElementsByName("ctNms");
	
	var chked=0;
	
	for(var i=0; i<chk.length; i++){
		if(chk[i].checked){
			chked++;
		}
	
	}
	
	if(chked == 0){
		alert("삭제 항목을 선택하세요.");
		return;
	}
 	
 	document.manualContents.action="<@spring.url '/content/deleteFiles.ssc'/>";
	document.manualContents.submit();
 }
 
 function timeCheck(value,name,id){
 	
 	if(value.length <6){
 		return false;
 	}
 	if(isNaN(value)){
		alert(name+" 입력값에 숫자가 아닌 값이 있습니다. 확인해 주세요.");
		$jq('#'+id).focus();
		return false;
	}
	if(value.length > 6  ){
		alert(name+" 입력값은 6자리 타임코드이여야 합니다.");
		$jq('#'+id).val(value.substring( 0,6));
		$jq('#'+id).focus();
		return false;
	}
	
	
	var result1 =value.substring( 0,2) ;
	var result2 =value.substring( 2,4) ;
	var result3 =value.substring( 4,6) ;
	
	var result= result1 +":"+result2+":"+result3;
	
	if(result2 > 59){
		alert(name+" 입력값의 분단위 값을 확인하세요.");
		$jq('#'+id).focus();

		return false;
	}
	if(result3 > 59){
		alert(name+" 입력값의 초단위 값을 확인하세요.");
		$jq('#'+id).focus();
		return false;
	}
	
	$jq('#'+id).val(result);
	
 }
 
 
 function trim(s) {
 	s += ''; // 숫자라도 문자열로 변환
 	return s.replace(/^\s*|\s*$/g, '');
 }

 function checksave(){

	if(document.manualContents.ctNm.value==""){
	  alert("콘텐츠 선택이 필요합니다");
	  //document.manualContents.ctNm.focus();
	  return false;
	}
	if(document.manualContents.ctCla.value==""){
	  alert("콘텐츠 구분을 선택하세요");
	  document.manualContents.ctCla.focus();
	  return false;
	}
	if(document.manualContents.ctTyp.value==""){
	  alert("콘텐츠 타입을 선택하세요");
	  document.manualContents.ctTyp.focus();
	  return false;
	}
	if(document.manualContents.segmentId.value==""){
	  alert("세그먼트ID을 선택하세요");
	  document.manualContents.segmentId.focus();
	  return false;
	}
	
	var pgmId = document.manualContents.pgmId.value;
 	if(pgmId.indexOf("00-0000000000-00") > -1) {
 		alert("기본 프로그램ID(00-0000000000-00)는 서비스를\n할 수 없습니다. 고유 프로그램ID가 필요합니다.");
 		return false
 	}
 	document.manualContents.pgmId.value = trim(pgmId);
 	
 	var segmentNm = document.manualContents.segmentId.options[document.manualContents.segmentId.selectedIndex].text;
 	segmentNm = segmentNm.substring(0, segmentNm.indexOf('S0')-2);
	document.manualContents.segmentNm.value = segmentNm;
	//alert(document.manualContents.segmentNm.value);
	return true;
}
 function saveContentInfo(){
 	
 	if(checksave()){
		$jq.ajax({
		url: "<@spring.url '/content/manualSave.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#manualContents').serialize(),
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		error: function(){
			alert("에센스 수동등록 처리에 실패하였습니다.");
			document.manualContents.action="<@spring.url '/content/ManualReg.ssc'/>";
			document.manualContents.submit();
		},
		success: function(data){
			if(data.result == 'Y') {
				alert("에센스 수동등록 처리에 성공하였습니다.");
			} else {
				alert("에센스 수동등록 처리에 실패하였습니다.");
			}
			
			document.manualContents.action="<@spring.url '/content/ManualReg.ssc'/>";
			document.manualContents.submit();
		}
			
		});
	}
	
	
 }
 
 function selectFile(name, filePath){
 	var str = name; 
 	
 	var blank_pattern=/[\s]/g;
 	var special_pattern=/[~!@\#$%^&*\()\=+']/gi;

 	if(blank_pattern.test(str)||special_pattern.test(str)){
 		alert("파일명에 특수문자나 공백이 들어갈 수 없습니다.");
 	}else{
		document.getElementById('ctDesc').innerHTML=name;
		document.getElementById('ctNm').value=name;
		
		viewMediaPlayer(filePath);
 	}
 }
 
 function access_click(value){
 	if(value != "doad"){
 		document.manualContents.localCode.value="";
 	}
 	document.manualContents.gubun.value=value;
 	
 	document.manualContents.action="<@spring.url '/content/ManualReg.ssc'/>";
	document.manualContents.submit();
 }
function programSearch(){
	//alert(document.manualContents.ctTyp.value);
	var ctTyp=document.manualContents.ctTyp.value;
	var ctx = '<@spring.url ''/>';
	
	if(ctTyp == ""){
		alert("콘텐츠 타입을 선택하세요.");
		return;
	}
	//alert("1");
	window.open(ctx+"/popup/ProgramSearch.ssc?ctTyp="+ctTyp,"programSearch","width=970,height=820,scrollbars=yes");
}

function getScode(){
	//alert(document.manualContents.brdDd.value);
	var nm=document.manualContents.pgmNm2.value;

	
	$jq.ajax({
		url: "<@spring.url '/popup/getScode.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#manualContents').serialize(),
		error: function(){
			alert('세그먼트 조회 오류');
		},
		success: function(data){
			var table ="";
		 		$jq('#sid').empty();
		 			table += '<th>세그먼트ID</th><td>'
		 			table += '<select name="segmentId">'
		 			table += '<option value=\'\'>&#160; 선택&#160</option>'
		 			table += '<option value=\'S000\'>&#160;'+nm+'&#160(S000)</option>'
		 			for(var iq=0;iq<data.scodes.length;iq++){	
		 				table += '<option value="'+data.scodes[iq].segmentId+'" >&#160;'+data.scodes[iq].segmentNm+'&#160('+data.scodes[iq].segmentId+')</option>'
		 			}
		 			table += '</select>'
		 			
		 			table += '</td>'
		 		$jq('#sid').append(table);
		}
			
	});
	showDisHide('sid','0'); 
	
}

function callDownload(){

	var filename = document.manualContents.ctNm.value;
	if(document.manualContents.ctNm.value==""){
	  alert("콘텐츠 선택이 필요합니다");
	  //document.manualContents.ctNm.focus();
	  return;
	}
	
	
	var path = "/mp2/manual/audio/main/"+filename;
		
	alert("다운로드 요청을 하겠습니다!.");
	
	document.manualContents.downFile.value = path;
	
	document.manualContents.action="<@spring.url '/content/audioFiledownload.ssc'/>";
	document.manualContents.submit();
		
}

function typChange(value){
	//alert(value);
	document.manualContents.fTyp.value = value;
}

function viewMediaPlayer(filePath){
	$jq('#Player').attr("URL", "<@spring.url '"+filePath+"'/>");
}
</script>
<section id="container">
<form name="manualContents" id="manualContents" method="post">
	<@spring.bind "search" />
	<input type="hidden" name="gubun" value="${search.gubun}">
    <section id="content">
    	<h3 class="blind">수동 등록</h3>
		<article class="title"><img src="<@spring.url '/images/title_manualreg.gif'/>" title="수동 등록"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 콘텐츠 관리 &gt; <span class="now">수동 등록</span></nav>
        <article class="tabmenu">
            <ul class="tab1">
            <li class="off"><a href="<@spring.url '/content/ContentSearch.ssc'/>"><span>서비스콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/StandbyContent.ssc'/>"><span>대기콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/RMSContent.ssc'/>"><span>보관콘텐츠</span></a></li>
            <li class="on"><a href="#"><span>수동등록</span></a></li>
			<!--<li class="off"><a href="/content/WebReg.ssc"><span>웹등록</span></a></li>-->
            </ul>
        </article>
<!-- Popup -->
		<article class="ly_pop15">
        <article class="sideinfo">
        	<#if search.gubun == 'doad'>
        	<dl class="btncover3">
	            	<dt>영상보기
	            	<table align="right">
						<tr>	
							<td class="vam">
								<#--
				            	<span class="btn_pack gry" >
				                	<@button '${search.menuId}' '${user.userId}' 'common.download' 'callDownload();' '' />
			                	</span> 
			                	-->
                			</td>
                		</tr>
                	</table>		
                	</dt>
	                <dd class="mgl7" style="padding-top: 2px;">
	                   <object name="Player" ID="Player"  CLASSID="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" width="284px" height="212px" >
                               <PARAM  name="URL"  value="">
                       </object>
	                </dd>
	            </dl>
	           </#if>
            <dl>
                <dt>콘텐츠 정보</dt>
                <dd>
                <table summary="" id="boardview4">
                <colgroup><col width="100px"></col><col width="207px"></col></colgroup>
                <tbody>
                <tr><th>콘텐츠/파일명</th><td id="ctDesc"></td></tr>
                <tr><th>콘텐츠 구분</th><td>
                <select name="ctCla">
	                    	<option value="" >&#160; 선택 &#160;</option>
	                    	<#list clas as cla>
	                    	<option value="${cla.sclCd}" >&#160; ${cla.clfNm} &#160;</option>
	                    	</#list>
	                    </select>
                </td></tr>
                <tr><th>콘텐츠 타입</th><td>
                <select name="ctTyp">
	                    	<option value="" >&#160; 선택 &#160;</option>
	                    	<#list typs as typ>
	                    	<option value="${typ.sclCd}" >&#160; ${typ.clfNm} &#160;</option>
	                    	</#list>
	                    </select>
                </td></tr>
                <#if search.gubun == 'doad'>
               <tr>
               		<th>파일유형</th>
               		<td>
               			<input type="radio" name="source_gb" value="V" checked="checked" onclick="javascript:typChange(this.value);">비디오&nbsp;&nbsp;
               			<input type="radio" name="source_gb" value="A" onclick="javascript:typChange(this.value);">오디오
               		</td>
               </tr>
                </#if>
                
                <tr><th>프로그램ID</th><td>
                	<input type="text" id="pgmId" name="pgmId" value="" class="ip_text2" readonly="readonly"></input>
                	<input type="hidden" id="pgmGrpCd" name="pgmGrpCd" value="">
                	<input type="hidden" name="pgmCd" id="pgmCd" value="">
	                <input type="hidden" name="edtrid" id="edtrid" value="${user.userId}">
	                <input type="hidden" name="pgmNm" value="">
	                <input type="hidden" name="ctNm" id="ctNm" value="">
	                <input type="hidden" name="menuId" id="menuId" value="${search.menuId}">
	                <input type="hidden" id="channelCode" name="channelCode" value="">
					<input type="hidden" id="brdDd" name="brdDd" value="">
	                <input type="hidden" id="pid" name="pid" value="">
	                <input type="hidden" id="pgmNm2" name="pgmNm2" value="">
	                <input type="hidden" id="vaGubun" name="vaGubun" value="${search.gubun}">
	                <input type="hidden" id="spGubun" name="spGubun" value="">
	                <input type="hidden" name="downFile" value="" />
	                <input type="hidden" name="localCode" value="${search.localCode}" />
	                <input type="hidden" name="fTyp" value="V" />
	                <input type="hidden" name="segmentNm" value="" />
            		<span class="btns">
            		<#-- 
            		<a href="javascript:saveContentInfo();" class="save" title="저장">저장</a>
            		-->
            		
            		</span>
            		<span class="btns"><a href="javascript:programSearch();" class="pg" title="프로그램정보조회">프로그램정보조회</a></span>
					<span class="btns"><a href="javascript:goSchedule();" class="srch" title="검색">검색</a></span>
                </td></tr>
                <tr><th>프로그램코드</th><td id="pgmCd2"></td></tr>
                <tr><th>프로그램명</th><td id="pgmNm"></td></tr>
                <tr><th>트리밍패스여부</th><td><input type="radio" name="trimmSte" value="P" checked="checked">트리밍과정 필요없음.
                <#if search.gubun != 'doad'>
                <br><input type="radio" name="trimmSte" value="N">트리밍과정 필요.</td></tr>
                </#if>
                <tr id="sid"><th>세그먼트ID</th><td><select name="segmentId"><option value="">&#160; 선택 &#160;</option></select></td></tr>
                <#if search.gubun == 'doad'>
                
                <tr><th>시작시간</th>
                	<td>
                		<input type="text" id="startTime" name="startTime" value="" class="ip_text2" style="width:120px;" onFocusOut="javascript:timeCheck(this.value,'시작시간','startTime');"></input>
                		ex) 001230
                	</td>
                </tr>
                <tr><th>종료시간</th>
                	<td>
                		<input type="text" id="endTime" name="endTime" value="" class="ip_text2" style="width:120px;" onFocusOut="javascript:timeCheck(this.value,'종료시간','endTime');"></input>
                		ex) 001230
                	</td>
                </tr>
                </#if>
                </tbody>
                </table>
                </dd>
            </dl>
            <#assign a= search.gubun>
            <#assign b= search.dirGubun>
            <#if a == "audio">
	            <#if b=="main">
		            <article class="btncover4">
		            <span class="btn_pack gry">
		        	<#-- 
		        	<a href="#" onclick="javascript:callDownload('');">다운로드</a>
		        	-->
		        	<@button '${search.menuId}' '${user.userId}' 'common.download' 'callDownload();' '' />
		        	</span> 
		        	</article>
	        	<#else>
	        	</#if>
        	<#else>
        	</#if>
        </article>
        </article>
<!-- //Popup -->

<!-- PopList -->
		<div class="ly_pop18">
        <table summary="" id="boardview3">
        <colgroup><col width="30px"></col><col width=""></col><col width="80px"></col><col width="80px"></col></colgroup>
			<tbody>
			        <#assign x=20>
			            <#assign y = x>
				           <#list fileList as file>
			            	<#if y%2 ==0>
					        	<tr class="gry">
				        	<#else>
				        		<tr>
				        	</#if>
				             <td><input name="ctNms" type="checkbox" value="${file.getName()}"></td>
				            <td align="left">&nbsp;<a href="javascript:selectFile('${file.getName()}');">${file.getName()?substring(0,file.getName()?last_index_of("."))}</a></td>
				            <td>&nbsp;${file.getName()?substring(file.getName()?last_index_of(".")+1 ,file.getName()?length )}</td>
				            <td class="tar pdr5">${(file.length()/1024)?string(",##0")}</td>
			             	
				            	
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
						           
						        </tr>
						        <#assign y = y -1>		
								</#list>
							</#if>
            </tbody>
		</table>
		</div>
<!-- //PopList -->

<!-- Table -->
        <article class="bbsview3 bd2">
        <table summary="" id="boardview3">
        <colgroup><col width="30px"></col><col width=""></col><col width="80px"></col><col width="99px"></col><col width="333"></col></colgroup>
            <thead>
            <tr><th colspan="6">
            	<article class="box3">
                    <dl>
                    	<dt>구분</dt>
                    	<dd>
                    	<ul class="box4">
                    		<#assign i= search.gubun>
                    		<#if i == "audio">
                    			<li class="blank"><input name="va" type="radio" value="video" onclick="access_click(this.value);">비디오</li>
                       			<li class="blank"><input name="va" type="radio" value="audio" checked="checked" onclick="access_click(this.value);">오디오</li>
                       			<li class="blank"><input name="va" type="radio" value="doad" onclick="access_click(this.value);">지역국</li>
                       		<#elseif i == "doad">
                       			<li class="blank"><input name="va" type="radio" value="video" onclick="access_click(this.value);">비디오</li>
                       			<li class="blank"><input name="va" type="radio" value="audio" onclick="access_click(this.value);">오디오</li>
                       			<li class="blank"><input name="va" type="radio" value="doad" checked="checked" onclick="access_click(this.value);">지역국</li>
                    		<#else>
                    			<li class="blank"><input name="va" type="radio" value="video" checked="checked" onclick="access_click(this.value);">비디오</li>
                       			<li class="blank"><input name="va" type="radio" value="audio" onclick="access_click(this.value);">오디오</li>
                       			<li class="blank"><input name="va" type="radio" value="doad" onclick="access_click(this.value);">지역국</li>
                    		</#if>
                    		
                       	</ul>
                       	</dd>
                       	<#if i == "audio">
                       	<dd>
                       		<select name="dirGubun" onchange="javascript:checkSearch();"  >
								<option value="main" <#if search.dirGubun=="main">selected</#if>>&#160; 메인 &#160;</option>
								<option value="backup" <#if search.dirGubun=="backup">selected</#if>>&#160; 백업 &#160;</option>
							</select>
                       		<select name="type" onchange="javascript:checkSearch();" >
                       			<option value="" <#if search.type=="">selected</#if>>&#160; 선택 &#160;</option>
								<option value="1fm" <#if search.type=="1fm">selected</#if>>&#160; 1FM &#160;</option>
								<option value="2fm" <#if search.type=="2fm">selected</#if>>&#160; 2FM &#160;</option>
								<option value="1r" <#if search.type=="1r">selected</#if>>&#160; 1라디오 &#160;</option>
								<option value="2r" <#if search.type=="2r">selected</#if>>&#160; 2라디오 &#160;</option>
								<option value="3r" <#if search.type=="3r">selected</#if>>&#160; 3라디오 &#160;</option>
								<option value="dmb" <#if search.type=="dmb">selected</#if>>&#160; DMB &#160;</option>
								<option value="han" <#if search.type=="han">selected</#if>>&#160; 한민족 &#160;</option>
							</select>
                       	</dd>
                       	<#elseif i == "doad">
                       	<dd>
                       		<select name="local" onchange="javascript:localCheckSearch(this.value);" >
                       			<option value="">&#160; 선택 &#160;</option>
				        		<#if doads?has_content>
				                <#list doads as doad>
				                	<#if doad.sclCd != '00'>
				                	<option value="${doad.sclCd}" <#if search.localCode=="${doad.sclCd}"> selected</#if>>&#160; ${doad.clfNm}&#160;</option>
				                	</#if>
				                </#list>
				                </#if>
							</select>
						</dd>
                       	</#if>
                        <dt>파일명</dt>
                        <dd><input type="text" name="keyword" value="${search.keyword}" onKeyDown="keyEnter();"></input></dd>
                    </dl>
                    <article class="btncover5">
                     <span class="btn_pack gry"><a href="#" onclick="javascript:checkSearch();">수동 영상조회</a></span>
                    </article>
                </article>
            </th></tr>
            </thead>
<!-- list -->
            <tbody>
            <tr><th></th><th>파일명</th><th>확장자</th><th>사이즈(KB)</th><th></th></tr>
         		<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td rowspan="20"></td>
		        </tr>
		        
        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		        
	        	<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
	        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		        	<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
	        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		        	<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
	        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		        	<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
	        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		        	<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
	        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		        	<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
	        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		        	<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
	        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		        	<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
	        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		        	<tr class="gry">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
	        		<tr>
		            <td></td>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
            </tbody>
<!-- //list -->
    </table>     
    </article>
<!-- //Table -->
	<article class="btncover4">
	<span class="btn_pack gry">
	<#-- 
	<a href="javascript:removeFileList();">콘텐츠 삭제</a>
	-->
	<@button '${search.menuId}' '${user.userId}' 'common.save' 'saveContentInfo();' '' />
	</span>
	<span class="btn_pack gry">
	<@button '${search.menuId}' '${user.userId}' 'common.delete' 'removeFileList();' ''/>
	</span>
	</article>
<!-- paginate -->

<!-- //paginate -->
	</section>
</form>




