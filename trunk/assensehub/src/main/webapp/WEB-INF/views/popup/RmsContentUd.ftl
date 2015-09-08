<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
<script Language="JavaScript">

function nullCheck(){
	if(document.ContentsSearch.pgmId.value==""){
	  alert("프로그램 ID를 입력하세요.");
	  document.ContentsSearch.pgmId.focus();
	  return false;
	}
	if(document.ContentsSearch.ctCla.value==""){
	  alert("콘텐츠 구분을 선택하세요");
	  document.ContentsSearch.ctCla.focus();
	  return false;
	}
	if(document.ContentsSearch.ctTyp.value==""){
	  alert("콘텐츠 유형을 선택하세요");
	  document.ContentsSearch.ctTyp.focus();
	  return false;
	}
	if(document.ContentsSearch.segmentId.value==""){
	  alert("세그먼트ID을 선택하세요");
	  document.ContentsSearch.segmentId.focus();
	  return false;
	}
	return true;
}
function goPage(pageNo) {
		document.ContentsSearch.pgmId.value="";
		document.ContentsSearch.action="<@spring.url '/popup/RmsContentUd.ssc'/>";
		document.ContentsSearch.pageNo.value = pageNo;
		document.ContentsSearch.submit();
	}
	
	// 검색 
function checkSearch(){ 

	var stDt=document.ContentsSearch.startDt.value;
	var edDt=document.ContentsSearch.endDt.value;
	
	if(edDt < stDt){
		alert("날짜 검색에 종료일이 시작일 보다 앞선 날짜 입니다.");
		return false;
	}
		
    document.ContentsSearch.pgmId.value="";
	document.ContentsSearch.action="<@spring.url '/content/RMSContent.ssc'/>";
	document.ContentsSearch.pageNo.value="1";
	document.ContentsSearch.submit();
}

function dispTime(f){
 
}



function deleteContents(){

	//alert("!");
	var obj = document.getElementById("ContentsSearch");
	var chk = document.getElementsByName("ctIds");
	
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
		
			
	var obj = ($jq('#ContentsSearch').get(0));
	$jq.ajax({
		url: '<@spring.url "/popup/ajaxRmsDeleteContent.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			alert('정상적으로 콘텐츠 삭제 진행 하였습니다.!');
			 	//document.ContentsSearch.pgmId.value = ""; 
				document.ContentsSearch.pgmCd.value = ""; 
				document.ContentsSearch.action="<@spring.url '/popup/RmsContentUd.ssc'/>";
				document.ContentsSearch.submit();
		}
	});
}

function keyEnter() {   
	if(event.keyCode ==13) {   //엔터키가 눌려지면,
		checkSearch(ContentsSearch);    //검색 함수 호출
	}
}

function searchEmpty(){
	document.ContentsSearch.startDt.value="";
	document.ContentsSearch.endDt.value="";
	document.ContentsSearch.channelCode.value="0";
	document.ContentsSearch.keyword.value="";
}


function goSchedule(){
	window.open("/popup/Schedule.ssc","goSchedule","width=850,height=700,scrollbars=yes");
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
function callDownload(tmp){

var filePath = document.getElementById('filePath').value
		alert(filePath);
		if(filePath === undefined || filePath == null || filePath.length <= 0){
			alert("프로그램이 선택되지 않았습니다!.");			
		}else{
			alert("다운로드 요청을 하겠습니다!.");
			document.ContentsSearch.downFile.value = filePath;
			
			document.ContentsSearch.action="<@spring.url '/popup/filedownload.ssc'/>";
			document.ContentsSearch.submit();
		}
}

function deleteContentsInst(tmp){
	//alert(ctId);
	document.ContentsSearch.filePath.value="";
	document.ContentsSearch.pgmId.value="";
	var ctId = document.getElementById('ctId').value
	//alert(ctId);
		if(ctId === undefined || ctId == null || ctId <= 0){
			alert("프로그램이 선택되지 않았습니다!.");			
		}else{
			
			document.ContentsSearch.ctId.value = ctId;
			$jq('#Player').attr("URL","");
			
			var obj = ($jq('#ContentsSearch').get(0));
			$jq.ajax({
				url: '<@spring.url "/content/ajaxDeleteContent.ssc" />',
				type: 'POST',
				dataType: 'json',
				data: $jq('#ContentsSearch').serialize(),
				error: function(){
					//alert('Return Error.');
				},
				success: function(data){
					alert('정상적으로 삭제 진행 하였습니다.!');
					document.ContentsSearch.pgmId.value = ""; 
					document.ContentsSearch.action="<@spring.url '/content/RMSContent.ssc'/>";
					document.ContentsSearch.submit();
				}
			});
			
		}
}
function programSearch(value){
	//alert(live.popupGB.value);
	var pcode= value;
	
	var ctTyp=document.ContentsSearch.ctTyp.value;
	var ctx = '<@spring.url ''/>';
	
	if(ctTyp == ""){
		alert("콘텐츠 유형을 선택하세요.");
		return;
	}
	window.open(ctx+"/popup/MRmsProgramSearch.ssc?keyword="+pcode+"&ctTyp="+ctTyp,"programSearch","width=970,height=820,scrollbars=yes");
}

function getContentInfo(ctId){
	
	document.ContentsSearch.ctId.value=ctId;
	$jq('#Player').attr("URL","");
	var obj = ($jq('#ContentsSearch').get(0));
	$jq.ajax({
		url: '<@spring.url "/popup/ajaxFindContentInfoRms.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			//-- ajax 콘텐츠 상세정보 get
			$jq('#layer3').empty();
				 var table="";
				 table +='<article class="sideinfo"><dl class="btncover_meta dark"><dt>영상보기</dt><dd class="mgl7">'
			if(data.rcontentsInstTbls[0].flExt == 'mp4' || data.rcontentsInstTbls[0].flExt == 'mov' ){
				//table +='<object id="moviePlayerOther"  type="video/quicktime" data="http://210.115.198.102'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'" width="284px" height="212px">'
				table +='<object id="moviePlayerOther"  type="video/quicktime" data="<@spring.url "'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'"/>" width="284px" height="212px">'
				
						  table +='<param name="autoplay" value="true" />'
						  table +='<param name="controller" value="true" />' 
						  table +='<param name="scale" value="tofit" />'
						  //table +='<param name="src" value="" />' 
						  table +='<param name="pluginurl" value="http://www.apple.com/quicktime/download/" />'
						 
						  <#--
						  	table +='<object id="moviePlayerOther"  type="video/quicktime" data="" width="284px" height="212px">' 
								  table +='<param name="autoplay" value="true" />'
								  table +='<param name="controller" value="true" />'
								  table +='<param name="scale" value="tofit" />'
								  table +='<param name="pluginurl" value="http://www.apple.com/quicktime/download/" />'
							  table +='</object>' 
						  -->	
					table +='</object>'
					
			}else{
				
				<#--
				table +='<object name="Player" ID="Player"  CLASSID="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" width="284px" height="212px">'
                table +='<PARAM  name="URL"  value="http://210.115.198.102'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'">'
                table +='</object>'
                
                table +='<object ID="Player" name="Player" classid="CLSID:22D6F312-B0F6-11D0-94AB-0080C74C7E95" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701" standby="Loading Microsoft Windows Media Player components..." type="application/x-oleobject" align="center" border="0" width="284px" height="212px">'
                
                -->
                table +='<object ID="Player" name="Player" classid="clsid:6BF52A52-394A-11D3-B153-00C04F79FAA6" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701" standby="Loading Microsoft Windows Media Player components..." type="application/x-ms-wmp" align="center" border="0" width="284px" height="212px">'
                //table +='<param name="Filename" value="http://210.115.198.102'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'">'
				table +='<param name="Filename" value="<@spring.url "'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'"/>">'
				
				table +='<param name="AutoStart" value="1">'
				table +='<param name="AutoSize" value="1">'	
				table +='<param name="AnimationAtStart" value="0">'	
				table +='<param name="AudioStream" value="-1">'	
				table +='<param name="AllowScan" value="-1">'	
				table +='<param name="CurrentPosition" value="-1">'	
				table +='<param name="CurrentMarker" value="0">'	
				table +='<param name="BufferingTime" value="4">'	
				table +='<param name="ShowControls" value="1">'	
				table +='<param name="ShowPositionControls" value="1">'	
				table +='<param name="ShowDisplay" value="0">'	
				table +='<param name="ShowTracker" value="1">'	
				table +='<param name="ClickToPlay" value="1">'	
				table +='<param name="TransparentAtStart" value="1">'	
				table +='<param name="EnableFullScreenControls" value="0">'	
				table +='<param name="SendKeyboardEvents" value="0">'	
				table +='<param name="SendMouseClickEvents" value="0">'	
				table +='<param name="SendMouseMoveEvents" value="0">'	
				table +='<param name="SendKeyboardEvents" value="0">'	
				table +='<param name="SendMouseClickEvents" value="0">'	
				table +='<param name="Volume" value="700">'
				table +='<param name="SendMouseMoveEvents" value="-1">'
				//table +='<embed id="wPlayer" name="wPlayer" type="application/x-ms-wmp" pluginspage="http://www.microsoft.com/Windows/Downloads/Contents/Products/MediaPlayer/" src="http://210.115.198.102'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'" width="284px" height="212px">'
				table +='<embed id="wPlayer" name="wPlayer" type="application/x-ms-wmp" pluginspage="http://www.microsoft.com/Windows/Downloads/Contents/Products/MediaPlayer/" src="<@spring.url "'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'"/>" width="284px" height="212px">'
				
				<#--
				table +='<embed id="wPlayer" name="wPlayer" type="application/x-mplayer2" pluginspage="http://www.microsoft.com/Windows/Downloads/Contents/Products/MediaPlayer/" src="http://210.115.198.102'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'" width="284px" height="212px">'
				-->
				table +='</embed>'
				
				table +='</object>'	
				
                <#--
                table +='<object id="Player" name="Player" CLASSID="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701" type="application/x-oleobject" width="284px" height="212px">'
	                       table +='<param name="ShowStatusBar" value="true" />'
	                       table +='<param name="fileName" value="" />'
	                       table +='<param name="autoStart" value="true" />'
	                      
		                       table +='<embed id="wPlayer" name="wPlayer" type="application/x-mplayer2"  width="284px" height="212px" src="http://210.115.198.102'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'">' 
		                      table +='<param name="stretchToFit" value="0">'
		                       		table +='<param name="pluginspage" value="http://www.microsoft.com/Windows/MediaPlayer/" />' 
		                      		 table +='<param name="autoStart" value="true" />'
		                       		
								
								table +='</embed>'
						table +='</object>'
				-->		 
			}
			 table +='</dd><br><br>'
	                table +='<dd class="btncover2 btn" id="selectContentsInst">'
	                	table +='<span class="btn_pack gry">'
	                	
	                	table +='<a href="#" onclick="javascript:callDownload(\'\');">다운로드</a>'
	                	
	                	table +='</span>' 
	                	
	                table +='</dd>'
	            table +='</dl>'
	        table +='</article>'	 
				 
				 
			$jq('#layer3').append(table);
			
			$jq('#viewContent').empty();
			  var table="";
			  table +='<tr><th id="programCode" name="programCode">프로그램코드</th>'
			  table +='<td>'+isEmpty(data.contentsTbl.pgmCd)+'</td>'
			  table +='</tr>'
			  table +='<tr><th>콘텐츠구분</th>'
			  table +='<td><select name="ctCla">'
			  table +='<option value="" >&#160; 선택 &#160;</option>'
			  for(var i =0 ; i< data.clas.length ; i++){
			  	if(data.clas[i].clfNm == isEmpty(data.codeTbl2.clfNm)){
			  		table += '<option value="'+data.clas[i].sclCd+'" selected>'+data.clas[i].clfNm+'</option>'
			  	}else{
			  		table += '<option value="'+data.clas[i].sclCd+'">'+data.clas[i].clfNm+'</option>'
			  	}
			  }
			  
			  table +='</select></td></tr>'
			  
			  table +='<tr><th>콘텐츠유형</th>'
			  table +='<td><select name="ctTyp">'
			  table +='<option value="" >&#160; 선택 &#160;</option>'
			  for(var i =0 ; i< data.typs.length ; i++){
			  	if(data.typs[i].sclCd =="00" || data.typs[i].sclCd == "01"){
			  	}else{
				  	if(data.typs[i].clfNm == isEmpty(data.codeTbl3.clfNm)){
				  		table += '<option value="'+data.typs[i].sclCd+'" selected>'+data.typs[i].clfNm+'</option>'
				  	}else{
				  		table += '<option value="'+data.typs[i].sclCd+'">'+data.typs[i].clfNm+'</option>'
				  	}
			  	}
			  }
			  table +='</select></td></tr>'
			  table +='<tr><th>프로그램ID</th>'
			  table +='<td><input type="text" name="pgmId" id="pgmId" class="ip_text5" value=\''+isEmpty(data.contentsTbl.pgmId)+'\' readonly="readonly" />'
			  <#-- 
			  table +='<span class="btns"><a href="javascript:saveContentInfo();" class="save" title="저장">저장</a></span>'
			  
			  table +='<span class="btns">'
			 
			  table +=<@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'saveContentInfo();' 'save' /> 
			  table +='</span>'
			 -->
			  
			  table +='<span class="btns"><a href="javascript:programSearch(\''+isEmpty(data.contentsTbl.pgmCd)+'\');" class="pg" title="프로그램정보조회">프로그램정보조회</a></span>'
			  //table +='<span class="btns"><a href="javascript:goSchedule();" class="srch" title="검색">검색</a></span>'
  			   
  			  table +='</td></tr>'
  			  
			  table +='<tr><th>프로그램명</th><td><input type="text" class="ip_text5" id="pgmNm" name="pgmNm" value=\''+isEmpty(data.contentsTbl.pgmNm)+'\' readonly="readonly"/></td></tr>'
  			   	
  			  if(data.contentsTbl.segmentId ==null){
  			  		table += '<tr><th>세그먼트ID</th><td id="sid">'
		 			table += '<select name="segmentId">'
		 			table +='<option value="" >&#160; 선택 &#160;</option>'
		 			table += '</select>'
		 			table += '</td></tr>'
  			  }else{
  			  		table += '<tr><th>세그먼트ID</th><td id="sid">'
		 			table += '<select name="segmentId">'
		 			table += '<option value=\'S000\'>&#160;'+isEmpty(data.contentsTbl.pgmNm)+'&#160(S000)</option>'
		 			
		 			for(var iq=0;iq<data.scodes.length;iq++){	
		 				if(data.scodes[iq].segmentId == isEmpty(data.contentsTbl.segmentId)){
		 					table += '<option value="'+data.scodes[iq].segmentId+'" selected>&#160;'+data.scodes[iq].segmentNm+'&#160('+data.scodes[iq].segmentId+')</option>'
		 				}else{
		 					table += '<option value="'+data.scodes[iq].segmentId+'" >&#160;'+data.scodes[iq].segmentNm+'&#160('+data.scodes[iq].segmentId+')</option>'
		 				}
		 			}
		 			table += '</select>'
		 			
		 			table += '</td></tr>'
			  }
			  table +='<tr><th>회차</th><td><input type="text" class="ip_text5" name="part" value=\''+isEmpty(data.contentsTbl.part)+'\' /></td></tr>'
			  
			  
			  <#--
			  table +='<tr><th>프로그램ID</th><td>'+isEmpty(data.contentsTbl.pgmId)+'</td></tr>'
			  
			  table +='<tr><th>프로그램명</th><td>'+isEmpty(data.contentsTbl.pgmNm)+'</td></tr>'
  			   	
  			  table += '<tr id="sid"><th>세그먼트ID</th><td>'+isEmpty(data.contentsTbl.pgmNm)+'('+isEmpty(data.contentsTbl.segmentId)+')</td></tr>'
			  table +='<tr><th>회차</th><td>'+isEmpty(data.contentsTbl.part)+'</td></tr>'
			  table +='<tr><th>콘텐츠구분</th><td>'+isEmpty(data.codeTbl2.clfNm)+'</td></tr>'
			  table +='<tr><th>콘텐츠유형</th><td>'+isEmpty(data.codeTbl3.clfNm)+'</td></tr>'
			  -->
			  table +='<tr><th>파일사이즈</th><td>'+isEmpty(data.rcontentsInstTbls[0].flSz)+' byte</td></tr>' 
			  table +='<tr><th>파일포맷</th><td>'+isEmpty(data.rcontentsInstTbls[0].flExt)+'</td></tr>'
			  table +='<tr><th>등록일</th><td>'+isEmpty(data.regDate)+'</td></tr>'
			  
			  table +='<tr><th>인물정보</th><td><input type="text" class="ip_text5" name="personInfo" value=\''+isEmpty(data.contentsTbl.personInfo)+'\' readonly="readonly"/></td></tr>'
			  $jq('#viewContent').append(table);
			
			
			document.ContentsSearch.downFileNm.value=''+isEmpty(data.rcontentsInstTbls[0].usrFileNm)+'';
			
			//alert(''+isEmpty(data.rcontentsInstTbls[0].ctiId+''));
			document.ContentsSearch.flExt.value=''+isEmpty(data.rcontentsInstTbls[0].flExt)+'';
			document.ContentsSearch.sSeq.value=''+data.contentsTbl.sSeq+'';
			document.ContentsSearch.pgmCd.value=''+isEmpty(data.contentsTbl.pgmCd)+'';
			document.ContentsSearch.ctiId.value=''+isEmpty(data.rcontentsInstTbls[0].ctiId)+'';
			document.ContentsSearch.trimmSte.value=''+isEmpty(data.contentsTbl.trimmSte)+'';
			document.ContentsSearch.gubun.value=''+isEmpty(data.rcontentsInstTbls[0].avGubun)+'';
			
			$jq('#pgmNm').focus();
			viewMediaPlayer(''+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'',''+isEmpty(data.rcontentsInstTbls[0].ctId)+'',''+isEmpty(data.rcontentsInstTbls[0].ctiId)+'');
			<#--
			$jq('#listProfile').empty();
			  var table="";
			  table +='<tr><th>파일명 / 프로파일명</th><th>영상</th><th>전송</th></tr>'
			  for(var i=0;i<data.contentsInstTbls.length;i++){
			 	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
			  }
			$jq('#listProfile').append(table);
			-->
			
			
		}
	});
	
	
}
function transRequest(value){
	//alert("1");
	//alert(value);
	document.ContentsSearch.ctiId.value=value;
	
	$jq('#Player').attr("URL","");
			
	var obj = ($jq('#ContentsSearch').get(0));
	$jq.ajax({
		url: '<@spring.url "/content/ajaxTransRequest.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			alert('프로파일 재전송 요청 하였습니다.!');
			
			<#--				
			$jq('#listProfile').empty();
			  var table="";
			  table +='<tr><th>파일명 / 프로파일명</th><th>영상</th><th>전송</th></tr>'
			  for(var i=0;i<data.contentsInstTbls.length;i++){
			 	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
			  }
			$jq('#listProfile').append(table);
			
			-->
			
		}
	});
	
	
}

function proflRequest(value){
	
	document.ContentsSearch.ctiId.value=value;
	
	$jq('#Player').attr("URL","");
			
	var obj = ($jq('#ContentsSearch').get(0));
	$jq.ajax({
		url: '<@spring.url "/content/ajaxProflRequest.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			alert('프로파일 재요청 하였습니다.!');
							
			$jq('#listProfile').empty();
			  var table="";
			  table +='<tr><th>파일명 / 프로파일명</th><th>영상</th><th>전송</th></tr>'
			  for(var i=0;i<data.contentsInstTbls.length;i++){
			 	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
			  }
			$jq('#listProfile').append(table);
		}
	});
}
function saveContentInfo(){
	var tmp = document.getElementById('pgmNm').innerHTML;
	document.ContentsSearch.pgmNm.value = tmp;
	
	if(checkEmpty(document.ContentsSearch.ctId.value)&&checkEmpty(document.ContentsSearch.pgmId.value)){;
		var obj = ($jq('#ContentsSearch').get(0));
		$jq.ajax({
			url: '<@spring.url "/content/ajaxUpdateContentInfo.ssc" />',
			type: 'POST',
			dataType: 'json',
			data: $jq('#ContentsSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
				alert("저장되었습니다!.");
				//-- ajax 콘텐츠 상세정보 get
			}
		});
	}else{
		alert("콘텐츠 정보 및 프로그램 정보가 올바르지 않습니다.!");
	}
}


function viewMediaPlayer(filePath,ctId,ctiId){
	document.ContentsSearch.filePath.value=filePath;
	document.ContentsSearch.ctId.value=ctId;
	document.ContentsSearch.ctiId.value=ctiId;
		
	//$jq('#filePath').attr("filePath",filePath);
	//$jq('#ctId').attr("ctId",ctId);
	//$jq('#ctiId').attr("ctiId",ctiId);	
	//$jq('#selectContentsInst').empty();
	$jq('#selectContentsInst').empty();
	var table ="";
	<#-- 
	table += '<span class="btn_pack gry"><a href="#" onclick="javascript:callDownload(\''+filePath+'\');">다운로드</a></span> <span class="btn_pack gry">'
	table += '<a href="#" onclick="javascript:deleteContentsInst(\''+ctId+'\');">삭제</a></span>'
	-->
	table += '<span class="btn_pack gry">'
	table += '<a href="#" onclick="javascript:callDownload(\'\');">다운로드</a>'
	table += '</span>&nbsp;'
	
	$jq('#selectContentsInst').append(table);
	
	//window.clipboardData.setData("Text","http://210.115.198.102"+filePath);
	
	
	//prompt("Media URL :","http://210.115.198.102"+filePath);
	//$jq('#Player').attr("URL","http://210.115.198.102"+filePath);
	//$jq('#moviePlayerSrc').attr("src","http://210.115.198.102"+filePath);
	//$jq('#moviePlayer').attr("data","http://210.115.198.102"+filePath);
	//$jq('#moviePlayer').attr("href","http://210.115.198.102"+filePath);
	//$jq('#moviePlayerOther').attr("data","http://210.115.198.102"+filePath);
	
	$jq('#moviePlayerOther').attr("URL", "<@spring.url '"+filePath+"'/>");
	$jq('#moviePlayer').attr("URL", "<@spring.url '"+filePath+"'/>");
	$jq('#moviePlayerSrc').attr("URL", "<@spring.url '"+filePath+"'/>");
}

function playStop(){
	$jq('#moviePlayerOther').attr("data","");
}


function webRegPopup(){
	//alert("1111");
	window.open("/popup/WebRegPopup.ssc","WebRegPopup","width=830,height=570,scrollbars=yes");
}

function callTra(){
        var flExt = document.ContentsSearch.flExt.value;
        
        
		var trimmSte = document.ContentsSearch.trimmSte.value;
	
		var pgmCd = document.getElementById('pgmCd').value;
		
		//alert(pgmCd);
		if(pgmCd === undefined || pgmCd == null || pgmCd.length <= 0){
				alert("프로그램이 선택되지 않았습니다!.");			
		}else{
			if(trimmSte == "Y"){
				alert("이미 서비스콘텐츠를 만들었습니다.");
			}else{
				if(flExt == "asf" || flExt == "ogg" || flExt == "zip" || flExt == "m2t" || flExt =="mts"){
		        	alert("트랜스코딩 요청을 할 수 없는 파일포맷 입니다.");
		        	return;
		        }
				if(nullCheck()){
					alert("트랜스 코딩 요청을 하겠습니다!.");
						$jq.ajax({
							url: "<@spring.url '/popup/saveContentInfo.ssc'/>",
							type: 'POST',
							dataType: 'json',
							data: $jq('#ContentsSearch').serialize(),
							error: function(){
								//alert('Return Error.');
							},
							success: function(data){
							
								$jq.ajax({
									url: "<@spring.url '/popup/reTrancoder.ssc'/>",
									type: 'POST',
									dataType: 'json',
									data: $jq('#ContentsSearch').serialize(),
									error: function(){
										//alert('Return Error.');
									},
									success: function(data){
										
										document.ContentsSearch.pgmId.value = ""; 
										document.ContentsSearch.pgmNm.value = ""; 
										document.ContentsSearch.pgmCd.value = "";
									 	document.ContentsSearch.action="<@spring.url '/popup/RmsContentUd.ssc'/>";
										document.ContentsSearch.submit();
										
										
									}
									
										
								});
								
								
								
							}
							
								
						});
				}
			}
		}
	
	
}


function save(){
	
	var ctId = document.getElementById('ctId').value;
	var brdDd = document.getElementById('brdDd').value;

	//alert(brdDd);

	//var part= document.ContentsSearch.part.value;
	//var ctCla=document.ContentsSearch.ctCla.value;
	//var ctTyp=document.ContentsSearch.ctTyp.value;
	

	if(ctId === undefined || ctId == null || ctId <= 0){
			alert("프로그램이 선택되지 않았습니다!.");			
	}else{
		
			//alert("저장 되었습니다!.");
			//document.ContentsSearch.downFile.value = ctId;
			$jq.ajax({
				url: "<@spring.url '/popup/saveContentInfo.ssc'/>",
				type: 'POST',
				dataType: 'json',
				data: $jq('#ContentsSearch').serialize(),
				error: function(){
					//alert('Return Error.');
				},
				success: function(data){
					//-- ajax 콘텐츠 상세정보 get
					alert("저장 되었습니다!.");
					
					document.ContentsSearch.action="<@spring.url '/popup/RmsContentUd.ssc'/>";
					document.ContentsSearch.submit();
				}
				
					
			});
			//document.ContentsSearch.action="<@spring.url '/content/saveContentInfo.ssc'/>";
			//document.ContentsSearch.submit();
			
			
	}
}
function getScode(){
	//alert("code");
	
	var nm=document.ContentsSearch.pgmNm2.value;
	
	//alert(document.manualContents.pgmNm.value);
	$jq.ajax({
		url: "<@spring.url '/popup/getScode.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			var table ="";
		 		$jq('#sid').empty();
		 			
		 			table += '<select name="segmentId">'
		 			table += '<option value=\'\'>&#160; 선택&#160</option>'
		 			table += '<option value=\'S000\'>&#160;'+nm+'&#160(S000)</option>'
		 			for(var iq=0;iq<data.scodes.length;iq++){	
		 				table += '<option value="'+data.scodes[iq].segmentId+'" >&#160;'+data.scodes[iq].segmentNm+'&#160('+data.scodes[iq].segmentId+')</option>'
		 			}
		 			table += '</select>'
		 			
		 		
		 		$jq('#sid').append(table);
		}
		
			
	});
	showDisHide('sid','0'); 
	
}

function goRMS(){
	window.open("/popup/MRmsContentList.ssc","programSearch","width=970,height=820,scrollbars=yes");
}

function update(value){
	var part2 = document.ContentsSearch.part2.value;
	var brdDd2 = document.ContentsSearch.brdDd2.value;
	var ctTyp2 = document.ContentsSearch.ctTyp2.value;
	var ctCla2 = document.ContentsSearch.ctCla2.value;
	var personInfo2 = document.ContentsSearch.personInfo2.value;
	
	document.ContentsSearch.gubun.value=value;
	
	//alert(value);
	//alert(document.ContentsSearch.gubun.value);
	
	var obj = document.forms["ContentsSearch"];
	var chk = document.getElementsByName("ctIds");
	
	var chked=0;
	
	for(var i=0; i<chk.length; i++){
		if(chk[i].checked){
			chked++;
		}
	
	}
	
	if(chked == 0){
		alert("적용할 파일을 선택하세요.");
		return;
	}
	
	
	if(value == "part"){
		if(part2 == ""){
			alert("적용할 데이터가 비어 있습니다.");
			return;
		}
	}else if(value == "brdDd"){
		if(brdDd2 == ""){
			alert("적용할 데이터가 비어 있습니다.");
			return;
		}
	}else if(value == "ctTyp"){
		if(ctTyp2 == ""){
			alert("적용할 데이터가 비어 있습니다.");
			return;
		}
	}else if(value == "ctCla"){
		if(ctCla2 == ""){
			alert("적용할 데이터가 비어 있습니다.");
			return;
		}
	}else{
		if(personInfo2 == ""){
			alert("적용할 데이터가 비어 있습니다.");
			return;
		}
	}
	
	$jq.ajax({
		url: "<@spring.url '/popup/updateContentRms.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			
		 	//document.ContentsSearch.part2.value="";
		 	//document.ContentsSearch.personInfo2.value="";
		 	document.ContentsSearch.action="<@spring.url '/popup/RmsContentUd.ssc'/>";
			document.ContentsSearch.submit();
		
		}
		
			
	});
}

function peopleSearch(){
	var pcode=document.ContentsSearch.pcode.value;
	
	//alert(pcode);
	
	//$jq('#people').empty();
	
	$jq.ajax({
		url: "<@spring.url '/popup/getMappingPeople2.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			alert('Return Error.');
		},
		success: function(data){
		 	
		 	//alert("1");
		 	$jq('#viewpeople').empty();
		 	
		 		var table="";
		 		
               	
               	if(data.contentsTbl.length != 0){
               		//alert("1");
               		for(var i=0; i< data.contentsTbl.length ; i++){
               			table +='<tr><th><input name="pcheck" type="checkbox" value="'+data.contentsTbl[i].sSeq+','+data.contentsTbl[i].nameKorea+'"></th><th><img src="'+data.contentsTbl[i].imageUrl+'" title="" width="50px" height="50px" class="thumb"/></th><td>'+data.contentsTbl[i].nameKorea+'</td><td>'+data.contentsTbl[i].castingName+'</td></th></tr>'
               			
               		}
               	}else{
               		//alert("2");
               		table +='<tr><th colspan="4">인물정보가 없습니다.</th></tr>'
               	}
               	
                document.ContentsSearch.pGubun.value="PI";
               	
               	<#--
               	if(data.contentsTbl.length == 0){
               		table +='<tr><th colspan="4">인물정보가 없습니다.</th></tr>'
               	}
               
               	else{
	               	for(var i=0;data.contentsTbl.length; i++){
	                	table +='<tr><th><input name="" type="checkbox" value=""></th><th><img src="images/test_img.jpg" title="이덕화" width="50px" height="50px" class="thumb"/></th><td>이덕화</td><td>이덕화</td></tr>'
	                }
                }
                
                table +='<tr><th colspan="4">인물정보가 없습니다.</th></tr>'
                
                table +='<tr><th><input name="" type="checkbox" value=""></th><th><img src="images/test_img.jpg" title="이덕화" width="50px" height="50px" class="thumb"/></th><td>'+isEmpty(data.contentsTbls[1].nameKorea)+'</td><td>'+isEmpty(data.contentsTbls[1].castingName)+'</td></tr>'
                
                table +='</tbody></table></dd>'
                table +='<dd class="btncover18">'
                table +='<span class="btn_pack gry"><a href="javascript:void(0)" onclick="peopleSave();showDisHide(\'people\',\'1\');return false;">저장</a></span> <span class="btn_pack gry"><a href="javascript:void(0)" onclick="showDisHide(\'people\',\'1\');return false;">취소</a></span>'
                table +='</dd>'
            	table +='</dl></article>'
            	-->
            $jq('#viewpeople').append(table);	
		}
		
			
	});
}

function peopleSave(){
	//alert("1");
	var obj = document.forms["ContentsSearch"];
	var chk = document.getElementsByName("pcheck");
	
	var chkV="";
	var chkN="";
	var check=[];
	
	for(var i=0; i< chk.length; i++){
		
		if(chk[i].checked){
			if(chkV =="" && chkV.length ==0 ){
				check = chk[i].value.split(",");
				
				chkV = check[0];
				chkN = check[1];
			}else{
				check = chk[i].value.split(",");
			
				chkV += ","+check[0];
				chkN += ","+check[1];
			}
		}
	}
	var pGubun = document.ContentsSearch.pGubun.value;
	
	if(pGubun == "PI"){
		document.ContentsSearch.personInfo2.value=chkN;
		document.ContentsSearch.sSeq.value=chkV;
		
	}else{
		//alert("1");
		document.ContentsSearch.personInfo.value=chkN;
		document.ContentsSearch.sSeq.value=chkV;
		
	}
	//document.getElementsByName('personInfo2').innerHTML[0].11;
}


function personInfoSave(){
		//alert("2");
		
		$jq.ajax({
			url: "<@spring.url '/popup/saveContentPersoninfo.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ContentsSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
				document.ContentsSearch.pgmId.value = ""; 
				//document.ContentsSearch.pgmNm.value = ""; 
			 	document.ContentsSearch.action="<@spring.url '/popup/RmsContentUd.ssc'/>";
				document.ContentsSearch.submit();
				
			}
			
				
		});
		//document.ContentsSearch.action="<@spring.url '/content/saveContentInfo.ssc'/>";
		//document.ContentsSearch.submit();
	
}
function personInfoSelect(){
	var pvalue=document.ContentsSearch.sSeq.value;
	var pcode=document.ContentsSearch.pcode.value;
	
	//$jq('#people').empty();
	
	var pInfo=[];
	pInfo=pvalue.split(",");
	var ctId = document.getElementById('ctId').value;
	var brdDd = document.getElementById('brdDd').value;

	if(ctId === undefined || ctId == null || ctId <= 0){
			//showDisHide('people','1');
			alert("프로그램이 선택되지 않았습니다!.");	
					
	}else{
		$jq.ajax({
			url: "<@spring.url '/popup/getMappingPeople.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ContentsSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	
			 	
			 	$jq('#viewpeople').empty();
			 	
			 		var table="";
			 		
	               	if(data.contentsTbl.length == 0){
	               		
	               		table +='<tr><th>-</th><td>-</td><td>-</td><td>-</td></tr>'
	               	}else{
	               
	               		
	               		for(var i=0; i< data.contentsTbl.length ; i++){
	               			var z=0;
	               			for(var j=0; j< pInfo.length; j++){
	               				
	               				//alert(data.contentsTbl[i].sSeq +"="+ pInfo[j]);
	               				
	               				if(pInfo[j] == data.contentsTbl[i].sSeq){
	               					z++;
	               					
	               				}else{
	               					
	               				}
	               				
	               			}
	               			if(z != 0){
	               				table +='<tr><th><input name="pcheck" type="checkbox" value="'+data.contentsTbl[i].sSeq+','+data.contentsTbl[i].nameKorea+'" checked></th>'
	               			}else{
	               				table +='<tr><th><input name="pcheck" type="checkbox" value="'+data.contentsTbl[i].sSeq+','+data.contentsTbl[i].nameKorea+'"></th>'
	               			}
	               			table +='<th><img src="'+data.contentsTbl[i].imageUrl+'" title="" width="50px" height="50px" class="thumb"/></th><td>'+data.contentsTbl[i].nameKorea+'</td><td>'+data.contentsTbl[i].castingName+'</td></th></tr>'
	               			
	               		}
	               	}
	               	
	               
	               	document.ContentsSearch.pGubun.value="PU";
	               	
	            $jq('#viewpeople').append(table);	
			}
			
			//showDisHide('people','0');	
		});
		
		showDisHide('people','0');
	}
	
}

function reset(){
	document.ContentsSearch.part2.value="";
	document.ContentsSearch.brdDd2.value="";
	document.ContentsSearch.ctTyp2.value="";
	document.ContentsSearch.ctCla2.value="";
	document.ContentsSearch.personInfo2.value="";
}

function CheckAll(){    //체크박스 전체선택 or 해제            
	var chk = document.getElementsByName("ctIds");                 //체크박스의 name값
	var bool = document.getElementById("checkNum").checked;

	for(i = 0; i < chk.length ; i++){                                                                    
  		
		if(chk[i].disabled == true ){
			continue;
		}
		
		chk[i].checked = bool;                                                                //체크되어 있을경우 설정변경
 	}
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
<link rel="stylesheet" type="text/css" href="<@spring.url '/css/master2.css' />" />
<link rel="stylesheet" type="text/css" href="<@spring.url '/css/layout2.css' />" />
<link rel="stylesheet" type="text/css" href="<@spring.url '/css/dhtmlxcalendar.css' />" />
<link rel="stylesheet" type="text/css" href="<@spring.url '/css/dhtmlxcalendar_skins.css' />" />

<script type='text/javascript' src='http://code.jquery.com/jquery-1.7.1.js'></script>

<script type="text/javascript" src="<@spring.url '/js/calendar.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/script.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/jquery.js' />"></script>

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

	<form name="ContentsSearch" id="ContentsSearch" method="post">
		<@spring.bind "search" />
      	<input type="hidden" name="menuId" value="${search.menuId}">
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="ctiId" id="ctiId" value="" />
		<input type="hidden" name="downFile" value="" />
		
		<input type="hidden" name="pgmCd" id="pgmCd" value=""  />
		<input type="hidden" id="pgmGrpCd" name="pgmGrpCd" value="">
		
		<input type="hidden" name="ctId" id="ctId" value="" />
		<input type="hidden" name="filePath" id="filePath" value="" />
		<input type="hidden" name="downFileNm" id="downFileNm" value="" />
		
		<input type="hidden" id="channelCode" name="channelCode" value="">
		<input type="hidden" id="brdDd" name="brdDd" value="">
		<input type="hidden" id="pid" name="pid" value="">
		<input type="hidden" id="pgmNm2" name="pgmNm2" value="">
		
		
		<input type="hidden" name="sSeq" value="">
		<input type="hidden" name="pcode" value="${pcode}">
		<input type="hidden" name="pGubun" value="">
		<input type="hidden" name="personInfo3" value="">
		<input type="hidden" name="nid" value="${nid}">
		<#--
		<input type="hidden" name="avGubun" value="${avGubun}">
		-->
		<input type="hidden" name="checkvalue" value="">
		<input type="hidden" name="trimmSte" value="">
		
		<input type="hidden" name="gubun" value="">
		<input type="hidden" name="flExt" value="">
    <section id="content2">
    	
        <article class="tabmenu_meta">
            <ul class="tab1">
            <li class="off"><a href="/popup/RmsContentList.ssc?nid=${nid}&pcode=${pcode}"><span>콘텐츠정보조회</span></a></li>
            <li class="on"><a href="#"><span>콘텐츠정보편집</span></a></li>
            
            </ul>
        </article>
        <article class="box8">
                    
	                    <dl>
	                        <dt>회차</dt>
	                        <dd>
	                        	<input type="text" class="a2" name="part2" value="<#if search.part2?exists>${search.part2!""}</#if>"/>
	                        	<span class="btn_pack gry"><a href="#"  onclick="update('part')">적용</a></span> 
			                    
	                        </dd>
	                        <dt>방송일자</dt>
	                        <dd>
	                        	<input type="text" style="cursor:pointer;"  id="calInput1" name="brdDd2"  value="<#if search.brdDd2?exists>${search.brdDd2?string("yyyy-MM-dd")}</#if>" class="a2" readonly></input>
	                        	<span class="btn_pack gry"><a href="#"  onclick="update('brdDd')">적용</a></span> 
	                        </dd>
	           
	                    	
	                        <dt>콘텐츠유형</dt>
	                        <dd>
	                        	<select name="ctTyp2" class="a2">
	                        	<option value="">선택</option>
	                        	
	                        	<#assign cttyp2 = search.ctTyp2!"">
	                        	<#list typs as typs> 
	                        		<#if cttyp2 == typs.sclCd>
	                        			<option value="${typs.sclCd}" selected>${typs.clfNm}</option>
	                        		<#else>	
	                        			<option value="${typs.sclCd}">${typs.clfNm}</option>
	                        		</#if>
	                        	</#list>
	                        	</select>
	                        	<span class="btn_pack gry"><a href="#"  onclick="update('ctTyp')">적용</a></span> 
	                        </dd>
	                        <dt class="clearB">콘텐츠구분</dt>
	                        <dd>
	                        	
	                        	<select name="ctCla2" class="a2">
	                        	<option value="">선택</option>
	                        	
	                        	<#assign ctcla2 = search.ctCla2!"">
	                        	
	                        	
	                        	<#list clas as clas> 
	                        		<#if ctcla2 == clas.sclCd>
	                        			<option value="${clas.sclCd}" selected>${clas.clfNm}</option>	
	                        		<#else>
	                        			<option value="${clas.sclCd}">${clas.clfNm}</option>
	                        		</#if>
	                        	</#list>
	                        	
                        		</select>
	                        	<span class="btn_pack gry"><a href="#"  onclick="update('ctCla')">적용</a></span> 
	                        </dd>
	                        <dt>인물정보</dt>
	                        <dd>
	                        	<input type="text" class="a3" name="personInfo2" id="personInfo2" value="<#if search.personInfo2?exists>${search.personInfo2!""}</#if>" readonly/>
	                        </dd>
	                        <dd>	
	                        	<article class="btncover16">
	                        	<span class="btn_pack gry"><a href="#"  onclick="showDisHide('people','1');peopleSearch(this);showDisHide('people','0');return false;">검색</a></span> 
	                        	</article>
	                        	<article class="btncover9">
	                        	<span class="btn_pack gry"><a href="#"  onclick="update('personInfo')">적용</a></span> 
		                        </article>
		                        <article class="btncover17">
				                	<span class="btn_pack gry"><a href="javascript:"  onclick="reset()">초기화</a></span>
				                </article>
			                </dd>
	                    </dl>
	                   
            		
                </article> 
<!-- Popup -->
		<article id="layer3" class="ly_pop12_5">
			<article class="sideinfo">
	        	<dl class="btncover_meta dark">
	            	<dt>영상보기</dt>
	                <dd class="mgl7">
	                    <object id="moviePlayer" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B"
						  codebase="http://www.apple.com/qtactivex/qtplugin.cab" width="284px" height="212px">
						  <param name="src" value="" /> 
						  <param name="controller" value="true" /> 
						  <param name="autoplay" value="true" />
						  <param name="scale" value="tofit" />
						  <param name="pluginspage" value="http://www.apple.com/quicktime/download/" />
						  <!--[if !IE]>--> 
						  	<object id="moviePlayerOther"  type="video/quicktime" 
								 data="" width="284px" height="212px"> 
								  <param name="autoplay" value="true" /> 
								  <param name="controller" value="true" />
								  <param name="scale" value="tofit" />
								  <param name="pluginurl" value="http://www.apple.com/quicktime/download/" />
							  </object> 
							<!--<![endif]--> 
					</object>	
	             <#--
	                   <object name="Player" ID="Player"  CLASSID="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" width="284px" height="212px" >
                               <PARAM  name="URL"  value="">
                       </object>
                 
	                -->
                 
	                </dd><br><br>
	                <dd class="btncover2 btn" id="selectContentsInst">
	                	<span class="btn_pack gry">
	                	<#--
	                	<a href="#" onclick="javascript:callDownload('');">다운로드</a>
	                	-->
	                	</span> 
	                	
	                </dd>
	            </dl>
	        </article>
	    </article>    
		<div class="ly_pop12_6">
			<article class="sideinfo">
			<dl class="btncover_meta dark">
				<dt>콘텐츠 정보</dt>
				<dd>
					<table summary="" id="boardview4">
						<colgroup><col width="80px"></col><col width="207px"></col></colgroup>
						<tbody id="viewContent">
							<tr><th>프로그램코드</th><td></td></tr>
							<tr><th>콘텐츠구분</th><td></td></tr>
		                        <tr><th>콘텐츠유형</th><td></td></tr>
							<tr><th>프로그램ID</th>
								<td>
								
								</td>
							</tr>
								 <tr><th>프로그램명</th><td></td></tr>
								 <tr id="sid"><th>세그먼트ID</th><td></td></tr>
								 <tr><th>회차</th><td></td></tr>
		                        
		                        <tr><th>파일사이즈</th><td></td></tr>
		                        <tr><th>파일포맷</th><td></td></tr>
		                        <tr><th>등록일</th><td></td></tr>
		                        <tr><th>인물정보</th><td></td></tr>
		                 </tbody>   
		                
					</table>
				</dd>
				
				<dd class="btncover11" >
	                	<span class="btn_pack gry">
	                	<#-- 
	                	<a href="#" onclick="javascript:callDownload('');">다운로드</a>
	                	-->
	                	<a href="#" onclick="javascript:callTra('');">트랜스코딩 요청</a>
	                	</span> 
	                	<span class="btn_pack gry">
	                	 
	                	<a href="javascript:save(this);">저장</a>
	                	
	                	</span>
	                	<span class="btn_pack gry"><a href="#" onclick="personInfoSelect();">인물 편집</a></span>
	            </dd><br>
	        </dl>   
	        <#--
	        <dl class="btncover_meta dark" id="viewPeople">   
	            <dt>인물정보 편집</dt>   
	            
			</dl>
			-->
			</article>
		</div>
<!-- //Popup -->   
<!-- 인물정보 Popup -->
		<article class="ly_pop12_maninfocover" id="people" style="display:none;">
        <article class="sideinfo">
            <dl class="btncover_meta none">
            <dt>인물정보</dt>
                <dd>
	                <table summary="" id="boardview4">
		                <colgroup><col width="20px"></col><col width="71px"></col><col width="88px"></col><col width="88px"></col></colgroup>
						<thead>
		                <tr>
		                <th></th><th>이미지</th><th>배우명</th><th>배역명</th>
		                </tr>
		                </thead>
	                </table>
	                <div class="ly_pop12_maninfo">
		                <table summary="" id="boardview4">
		                <colgroup><col width="20px"></col><col width="71px"></col><col width="88px"></col><col width="88px"></col></colgroup>
		                <tbody id="viewpeople">
		                
		                </tbody>
		                </table>
	                </div>
	               
                </dd>
                <dd class="btncover19">
                
                <span class="btn_pack gry"><a href="javascript:void(0)" onclick="peopleSave();showDisHide('people','1');return false;">저장</a></span> <span class="btn_pack gry"><a href="javascript:void(0)" onclick="showDisHide('people','1');return false;">취소</a></span>
                
                </dd>
            </dl>
        </article>
        </article>
<!-- // 인물정보 Popup -->  
<!-- Table -->                             
        <article class="bbsview6">
        <table summary="" id="boardview9">
         <colgroup><col width="20px"></col><col width="60px"></col><col width="70px"></col><col width=""></col><col width="30px"></col><col width="140px"></col><col width="70px"></col><col width="325px"></col></colgroup>
           
<!-- list -->
            <tbody>
            <tr><th><input type="checkbox" id="checkNum" onclick="javascript:CheckAll()"></th><th>구분</th><th>유형</th><th>파일명</th><th>회차</th><th>인물정보</th><th>방송일</th><th></th></tr>
			<#assign x = 16> 
			<#assign y = x>     
	            <#list contents.items as content>    
	            	<#if y%2==0>
	            		<tr class="gry" id="${content.ctId!""}">
		            <#else>
		            	<tr id="${content.ctId!""}">
		            </#if>        
		            	
			            <td><input name="ctIds" type="checkbox" value="${content.ctId!""}"></td>    
			            <td>${ tpl.getCodeDesc("CCLA", content.ctCla!"")}</td>
			            <td>${ tpl.getCodeDesc("CTYP", content.ctTyp!"")}</td>
			            
						<#assign size=content.usrFileNm?length>
						<#assign regrid=content.regrid!"">
			         	<#if size <= 19>
			         	<td align="left" title="${content.ctId!""}">
			            	&nbsp;<img src='/images/icon_rms.gif'/>&nbsp;<a href="javascript:void(0)" onclick="playStop();getContentInfo('${content.ctId!""}');showDisHide('people','1');return false;">${content.usrFileNm!""}</a>
			            </td>
			            <#else>
			            <td align="left" title="${content.ctId!""}">
			            	&nbsp;<img src='/images/icon_rms.gif'/>&nbsp;<a href="javascript:void(0)" onclick="playStop();getContentInfo('${content.ctId!""}');showDisHide('people','1');return false;">${content.usrFileNm?substring(0,19)+" ..."}</a>
			            </td>
						</#if>
						<td align="right">${content.part!""}</td>   
						<#assign pinfo=content.personInfo!"">
			            
			            <#if pinfo =="">
			            	<td></td>
			            <#else>	
			            	<#assign psize=content.personInfo?length>
			            	<#if psize <= 10>
			            		<td>${content.personInfo!""}</td>
							<#else>
								<td>${content.personInfo?substring(0,10)+"..."}</td>
							</#if>
						</#if>
			            <td><#if content.brdDd?exists>${content.brdDd?string("yy/MM/dd")}</#if></td> 
			            
			            
			            <#if y == 16> 
			            	<td rowspan="16">

		            		</td>
			            </#if>
				<#assign y = y -1>	        
			        </tr>
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
				            <td></td>
				            <td></td>
				           
				            <#if y == 16> 
				            	<td rowspan="16">
	
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
		<article class="btncover4">
		<span class="btn_pack gry">
	 	
		<a href="#" onclick="deleteContents();">삭제</a>
		
		</span>
		</article>
		
	<!-- paginate -->
	    <@paging contents search.pageNo '' />
	<!-- //paginate -->
	
	</section>
</form>
<script>
	
	var check=${ctId!""};
	if(check != 0){
		getContentInfo(check);
	}
</script>
