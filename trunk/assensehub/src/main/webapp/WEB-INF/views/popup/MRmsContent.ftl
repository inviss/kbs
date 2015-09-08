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
	  alert("콘텐츠 타입을 선택하세요");
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
		document.ContentsSearch.action="<@spring.url '/popup/MRmsContent.ssc'/>";
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
	document.ContentsSearch.action="<@spring.url '/popup/MRmsContent.ssc'/>";
	document.ContentsSearch.pageNo.value="1";
	document.ContentsSearch.submit();
}

function dispTime(f){
 
}



function deleteContents(){

	var obj = document.forms["ContentsSearch"];
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
		url: '<@spring.url "/content/ajaxDeleteContent.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			alert('정상적으로 콘텐츠 삭제 진행 하였습니다.!');
			document.ContentsSearch.pgmId.value = ""; 
				document.ContentsSearch.action="<@spring.url '/content/RMSContent.ssc'/>";
				document.ContentsSearch.submit();
		}
	});
}

function keyEnter(e) {  
	
	if(e.which ==13) {   //엔터키가 눌려지면,
		//alert("1");
		document.getElementById('checkSearch').click();    //검색 함수 호출
	}
	
}

function EnterPerson(e) {  
	
	if(e.which ==13) {   //엔터키가 눌려지면,
		   document.getElementById('searchPeoples').click(); //검색 함수 호출
	}
	
}

function searchEmpty(){
	document.ContentsSearch.startDt.value="";
	document.ContentsSearch.endDt.value="";
	document.ContentsSearch.personInfo2.value="";
	document.ContentsSearch.keyword.value="";
	document.ContentsSearch.part2.value="";
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

	document.ContentsSearch.filePath.value="";
	document.ContentsSearch.pgmId.value="";
	var ctId = document.getElementById('ctId').value

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
	
	var pcode= value;
	
	var ctTyp=document.ContentsSearch.ctTyp.value;
	var ctx = '<@spring.url ''/>';
	if(ctTyp == ""){
		alert("콘텐츠 유형을 선택하세요.");
		return;
	}
	window.open(ctx="/popup/MRmsProgramSearch.ssc?keyword="+pcode+"&ctTyp="+ctTyp,"programSearch","width=970,height=820,scrollbars=yes");
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
			$jq('#layer4').empty();
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
				 
				 
			$jq('#layer4').append(table);
			
			
			$jq('#viewContent').empty();
			  var table="";
			  table +='<tr><th>프로그램코드</th>'
				  table +='<td>'+isEmpty(data.contentsTbl.pgmCd)+'</td></tr>'
			  table +='<tr><th>콘텐츠구분</th>'
			  table +='<td><select name="ctCla">'
			  table +='<option value="" >&#160; 선택 &#160;</option>'
			  for(var i =0 ; i< data.clas.length ; i++){
			  	if(data.clas[i].sclCd == isEmpty(data.codeTbl2.sclCd)){
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
				  	if(data.typs[i].sclCd == isEmpty(data.codeTbl3.sclCd)){
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
		 			table +='<option value="" >선택</option>'
		 			table += '</select>'
		 			table += '</td></tr>'
  			  }else{
  			  		table += '<tr><th>세그먼트ID</th><td  id="sid">'
		 			table += '<select name="segmentId">'
		 			table += '<option value=\'S000\'>'+isEmpty(data.contentsTbl.pgmNm)+'(S000)</option>'
		 			
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
			  
			  
			  table +='<tr><th>파일사이즈</th><td>'+isEmpty(data.rcontentsInstTbls[0].flSz)+' byte</td></tr>' 
			  table +='<tr><th>파일포맷</th><td>'+isEmpty(data.rcontentsInstTbls[0].flExt)+'</td></tr>'
			  table +='<tr><th>등록일</th><td>'+isEmpty(data.regDate)+'</td></tr>' 
			  table +='<tr><th>인물정보</th><td><input type="text" class="ip_text5" name="personInfo" value=\''+isEmpty(data.contentsTbl.personInfo)+'\' readonly="readonly"/></td></tr>'
			  
			$jq('#viewContent').append(table);
			
			document.ContentsSearch.downFileNm.value=''+isEmpty(data.rcontentsInstTbls[0].usrFileNm)+'';
			
			document.ContentsSearch.flExt.value=''+isEmpty(data.rcontentsInstTbls[0].flExt)+'';
			document.ContentsSearch.sSeq.value=''+data.contentsTbl.sSeq+'';
			document.ContentsSearch.pgmCd.value=''+isEmpty(data.contentsTbl.pgmCd)+'';
			document.ContentsSearch.ctiId.value=''+isEmpty(data.rcontentsInstTbls[0].ctiId)+'';
			document.ContentsSearch.trimmSte.value=''+isEmpty(data.contentsTbl.trimmSte)+'';
			document.ContentsSearch.gubun.value=''+isEmpty(data.rcontentsInstTbls[0].avGubun)+'';
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
	$jq('#selectContentsInst').empty();
	var table ="";
	<#-- 
	table += '<span class="btn_pack gry"><a href="#" onclick="javascript:callDownload(\''+filePath+'\');">다운로드</a></span> <span class="btn_pack gry">'
	table += '<a href="#" onclick="javascript:deleteContentsInst(\''+ctId+'\');">삭제</a></span>'
	-->
	table += '<span class="btn_pack gry">'
	table += '<a href="#" onclick="javascript:callDownload(\'\');">다운로드</a>'
	table += '</span>'
	<#--
	table += '<span class="btn_pack gry">'
	table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.delete' 'deleteContentsInst();' '' />
	table += '</span>'
	-->
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

function webRegPopup(){
	//alert("1111");
	window.open("/popup/WebRegPopup.ssc","WebRegPopup","width=830,height=570,scrollbars=yes");
}

function playStop(){
	$jq('#moviePlayerOther').attr("data","");
}

function callTra(){
	var flExt = document.ContentsSearch.flExt.value;
	var trimmSte = document.ContentsSearch.trimmSte.value;
	//alert(document.ContentsSearch.pgmCd.value);	
	
		var pgmCd = document.getElementById('pgmCd').value;
		
		
		if(pgmCd === undefined || pgmCd == null || pgmCd.length <= 0){
				alert("프로그램이 선택되지 않았습니다!.");			
		}else{
			if(flExt == "asf" || flExt == "ogg" || flExt == "zip" || flExt == "m2t" || flExt =="mts"){
	        	alert("트랜스코딩 요청을 할 수 없는 파일포맷 입니다.");
	        	return;
	        }
			if(trimmSte == "Y"){
				alert("이미 서비스콘텐츠를 만들었습니다.");
			}else{
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
									 	document.ContentsSearch.action="<@spring.url '/popup/MRmsContent.ssc'/>";
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
	
	//alert("1");
	var ctId = document.getElementById('ctId').value;
	var brdDd = document.getElementById('brdDd').value;

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
					alert('Return Error.');
				},
				success: function(data){
					 alert("저장 되었습니다!.");
					 
					 <#--
						$jq('#viewContent').empty();
					  var table="";
					  table +='<tr><th>프로그램코드</th>'
					  table +='<td>'+isEmpty(data.contentsTbl.pgmCd)+'</td>'
					  table +='</tr>'
					  table +='<tr><th>콘텐츠구분</th>'
					  table +='<td><select name="ctCla">'
					  table +='<option value="" >&#160; 선택 &#160;</option>'
					  for(var i =0 ; i< data.clas.length ; i++){
					  	if(data.clas[i].sclCd == isEmpty(data.codeTbl2.sclCd)){
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
					  	if(data.typs[i].sclCd == isEmpty(data.codeTbl3.sclCd)){
					  		table += '<option value="'+data.typs[i].sclCd+'" selected>'+data.typs[i].clfNm+'</option>'
					  	}else{
					  		table += '<option value="'+data.typs[i].sclCd+'">'+data.typs[i].clfNm+'</option>'
					  	}
					  }
					  table +='</select></td></tr>'
					  table +='<tr><th>프로그램ID</th>'
					  table +='<td><input type="text" name="pgmId" id="pgmId" class="ip_text5" value=\''+isEmpty(data.contentsTbl.pgmId)+'\' readonly="readonly" />'
					  
					  //table +='<span class="btns"><a href="javascript:saveContentInfo();" class="save" title="저장">저장</a></span>'
					  
					  //table +='<span class="btns">'
					 
					  //table +=<@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'saveContentInfo();' 'save' /> 
					  //table +='</span>'
					  
					  
					  table +='<span class="btns"><a href="javascript:programSearch(\''+isEmpty(data.contentsTbl.pgmCd)+'\');" class="pg" title="프로그램정보조회">프로그램정보조회</a></span>'
		
					  //table +='<span class="btns"><a href="javascript:goSchedule();" class="srch" title="검색">검색</a></span>'
		
					
		
		  			  table +='</td></tr>'
		  			  
					  table +='<tr><th>프로그램명</th><td><input type="text" class="ip_text5" id="pgmNm" name="pgmNm" value=\''+isEmpty(data.contentsTbl.pgmNm)+'\' readonly="readonly"/></td></tr>'
		  			   	
		  			  if(data.contentsTbl.segmentId ==null){
		  			  		table += '<tr><th>세그먼트ID</th><td  id="sid">'
				 			table += '<select name="segmentId">'
				 			table +='<option value="" >선택</option>'
				 			table += '</select>'
				 			table += '</td></tr>'
		  			  }else{
		  			  		table += '<tr><th>세그먼트ID</th><td  id="sid">'
				 			table += '<select name="segmentId">'
				 			table += '<option value=\'S000\'>'+isEmpty(data.contentsTbl.pgmNm)+'(S000)</option>'
				 			
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
					 
					  
					  table +='<tr><th>파일사이즈</th><td>'+isEmpty(data.rcontentsInstTbls[0].flSz)+' byte</td></tr>' 
			  		  table +='<tr><th>파일포맷</th><td>'+isEmpty(data.rcontentsInstTbls[0].flExt)+'</td></tr>'
					  table +='<tr><th>등록일</th><td>'+isEmpty(data.regDate)+'</td></tr>' 
		
					  table +='<tr><th>인물정보</th><td><input type="text" class="ip_text5" name="personInfo" value=\''+isEmpty(data.contentsTbl.personInfo)+'\' readonly="readonly"/></td></tr>'
		
		
					  
					$jq('#viewContent').append(table);
					document.ContentsSearch.flExt.value=''+isEmpty(data.rcontentsInstTbls[0].flExt)+'';
					document.ContentsSearch.sSeq.value=''+data.contentsTbl.sSeq+'';
					document.ContentsSearch.pgmCd.value=''+isEmpty(data.contentsTbl.pgmCd)+'';
					document.ContentsSearch.ctiId.value=''+isEmpty(data.rcontentsInstTbls[0].ctiId)+'';
				    document.ContentsSearch.trimmSte.value=''+isEmpty(data.contentsTbl.trimmSte)+'';
				    document.ContentsSearch.gubun.value=''+isEmpty(data.rcontentsInstTbls[0].avGubun)+'';
					//alert(''+isEmpty(data.contentsInstTbls[0].flPath)+isEmpty(data.contentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[0].flExt)+'',''+isEmpty(data.contentsInstTbls[0].ctId)+'',''+isEmpty(data.contentsInstTbls[0].ctiId)+'');
					viewMediaPlayer(''+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'',''+isEmpty(data.rcontentsInstTbls[0].ctId)+'',''+isEmpty(data.rcontentsInstTbls[0].ctiId)+'');
					
					//$jq('#listProfile').empty();
					//  var table="";
					//  table +='<tr><th>파일명 / 프로파일명</th><th>영상</th><th>전송</th></tr>'
					//  for(var i=0;i<data.contentsInstTbls.length;i++){
					// 	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
					//  }
					//$jq('#listProfile').append(table);
					-->
					document.ContentsSearch.action="<@spring.url '/popup/MRmsContent.ssc'/>";
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
	
	//alert(document.ContentsSearch.gubun.value);
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
	window.open("/popup/RmsContentList.ssc","programSearch","width=1000,height=820,scrollbars=yes");
}
window.onload=function(){
	cal1 = new dhtmlxCalendarObject('calInput1', false, {isYearEditable: true,isMonthEditable: true});
	cal1.setYearsRange(1973, 2020);
	cal1.draw();
	
	cal2 = new dhtmlxCalendarObject('calInput2', false, {isYearEditable: true,isMonthEditable: true});
	cal2.setYearsRange(1973, 2020);
	cal2.draw();
}

function personInfoSelect(){
	//alert("1");
	
	var pvalue=document.ContentsSearch.sSeq.value;
	var pcode=document.ContentsSearch.pgmCd.value;
	//alert(pvalue);
	
	
	//$jq('#people').empty();
	
	var pInfo=[];
	pInfo=pvalue.split(",");
	var ctId = document.getElementById('ctId').value;
	var brdDd = document.getElementById('brdDd').value;

	if(ctId === undefined || ctId == null || ctId <= 0){
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
			 		
	               	//alert(pcode);
	               	if(pcode == null || pcode == ""){
	               		//alert("X");
	               		table +='<tr><th>-</th><td>-</td><td>-</td><td>-</td></tr>'
	               	}else{
	               
	               		//alert("O");
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
			
				
		});
		
		showDisHide('people','0');
	}
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
	
	
	
	document.ContentsSearch.personInfo.value=chkN;
	
	document.ContentsSearch.sSeq.value=chkV;
	//personInfoSave();
	
	//document.getElementsByName('personInfo2').innerHTML[0].11;
}

function personInfoSave(){
		
		
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
			 	document.ContentsSearch.action="<@spring.url '/popup/MRmsContent.ssc'/>";
				document.ContentsSearch.submit();
				
			}
			
				
		});
		//document.ContentsSearch.action="<@spring.url '/content/saveContentInfo.ssc'/>";
		//document.ContentsSearch.submit();
	
}

function searchPeople(){
	var personInfo2 = document.ContentsSearch.personInfo2.value;
	
	if(personInfo2 == undefined || personInfo2 == null  || personInfo2 <= 0){
		alert("찾을 인물을 넣어주세요.");
	}else{
		$jq.ajax({
			url: "<@spring.url '/popup/getSearchPeople.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ContentsSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	
			 	
			 	$jq('#viewpeople2').empty();
			 	
			 		var table="";
			 		
	                if(data.contentsTbl.length == 0){
	               		//alert("X");
	               		table +='<tr><td>-</td><td>-</td><td>-</td></tr>'
	               	}else{
	               		for(var i=0; i< data.contentsTbl.length ; i++){
      
	               			table +='<tr><th><img src="'+data.contentsTbl[i].imageUrl+'" title="" width="50px" height="50px" class="thumb"/></th><td><a href="javascript:void(0)" onclick="selectPeople(\''+data.contentsTbl[i].sSeq+'\',\''+data.contentsTbl[i].nameKorea+'\');showDisHide(\'searchPeople\',\'1\');return false;">'+data.contentsTbl[i].nameKorea+'</a></td><td>'+data.contentsTbl[i].castingName+'</td></th></tr>'
	               			
	               		}
	               	
	               	
	                }
	               	
	               	
	            $jq('#viewpeople2').append(table);	
	            
	            
	            
			}
			
				
		});
		
		showDisHide('searchPeople','0');
	
	}
}

function selectPeople(sSeq,name){
	var seq = sSeq;
	
	
	document.ContentsSearch.personInfo3.value = seq;
	//alert("!");
	showDisHide('searchPeople','1');
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
<section id="wrap_meta">
<!-- Container -->
<section id="container_meta">
	<form name="ContentsSearch" id="ContentsSearch" method="post">
		<@spring.bind "search" />
      	<input type="hidden" name="menuId" value="${search.menuId}">
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="ctiId" id="ctiId" value="" />
		<input type="hidden" name="downFile" value="" />
		<input type="hidden" name="downFileNm" id="downFileNm" value="" />
		
		<input type="hidden" name="pgmCd" id="pgmCd" value=""  />
		<input type="hidden" id="pgmGrpCd" name="pgmGrpCd" value="">
		
		<input type="hidden" name="ctId" id="ctId" value="" />
		<input type="hidden" name="filePath" id="filePath" value="" />
		
		<input type="hidden" id="channelCode" name="channelCode" value="">
		<input type="hidden" id="brdDd" name="brdDd" value="">
		<input type="hidden" id="pid" name="pid" value="">
		<input type="hidden" id="pgmNm2" name="pgmNm2" value="">
		
		<input type="hidden" id="sSeq" name="sSeq" value="">
		<input type="hidden" id="pcode" name="pcode" value="">
		<input type="hidden" id="pGubun" name="pGubun" value="">
		<input type="hidden" name="personInfo3" value="">
		<input type="hidden" name="trimmSte" value="">
		
		<input type="hidden" name="gubun" value="">
		<input type="hidden" name="flExt" value="">
    <section id="content2">
    	
        <article class="tabmenu_meta">
            <ul class="tab1">
            
            <li class="on"><a href="#"><span>보관콘텐츠</span></a></li>
            
            </ul>
        </article>
         
<!-- Popup -->
		<article id="layer4" class="ly_pop12_8">
			<article class="sideinfo">
	        	<dl class="btncover3">
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
		<div class="ly_pop12_9">
			<article class="sideinfo">
			<dl>
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
								<input type="text" id="pgmId" value="" class="ip_text5" readonly="readonly"></input>
								<#-- 
								<span class="btns">
								
								<a href="javascript:saveContentInfo();" class="save" title="저장">저장</a>
								
								 <@button '${search.menuId}' '${user.userId}' 'common.save' 'saveContentInfo();' 'save' />
								</span>
								<span class="btns"><a href="javascript:goSchedule();" class="srch" title="검색">검색</a></span>
								-->
								</td>
							</tr>
								 <tr><th>프로그램명</th><td></td></tr>
								 <tr id="sid"><th>세그먼트ID</th><td></td></tr>
								 <tr><th>회차</th><td></td></tr>
		                        
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
	                	
	                	<a href="#" onclick="javascript:save(this);">저장</a>
	                	
	                	
	                	</span>
	                	<span class="btn_pack gry">
	                	<a href="#" onclick="showDisHide('searchPeople','1');personInfoSelect();">인물 편집</a>
	                	</span>
	                	&nbsp;&nbsp;
	                </dd>
			</dl>
			
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
<!-- 인물정보(인물명 검색) Popup -->
		<article class="ly_pop12_maninfocover1" id="searchPeople" style="display:none;">
        <article class="sideinfo">
            <dl class="btncover_meta none">
            <dt>인물정보</dt>
                <dd>
	                <table summary="" id="boardview4">
		                <colgroup><col width="71px"></col><col width="88px"></col><col width="88px"></col></colgroup>
						<thead>
		                <tr>
		                <th>이미지</th><th>배우명</th><th>직업</th>
		                </tr>
		                </thead>
	                </table>
	                <div class="ly_pop12_maninfo">
		                <table summary="" id="boardview4">
		                <colgroup><col width="71px"></col><col width="88px"></col><col width="88px"></col></colgroup>
		                <tbody id="viewpeople2">
		                
		                </tbody>
		                </table>
	                </div>
	                
                </dd>
                <dd class="btncover19">
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span class="btn_pack gry"><a href="javascript:void(0)" onclick="showDisHide('searchPeople','1');return false;">취소</a></span>
                
                </dd>
            </dl>
        </article>
        </article>
<!-- // 인물정보 Popup -->   
<!-- Table -->                             
       <article class="bbsview6">
        <table summary="" id="boardview9">
         <colgroup><col width="40px"></col><col width=""></col><col width="35px"></col><col width="70px"></col><col width="60px"></col><col width="60px"></col><col width="115px"></col><col width="325px"></col></colgroup>
            <thead>
            <tr><th colspan="10">
            	<article class="box2">
                    
	                    <dl>
	                        <dt>회차</dt>
	                        <dd>
	                        	<input type="text" class="ip_text" name="part2" value="<#if search.part2?exists>${search.part2!""}</#if>"/>
	                        </dd>
	                        <dt>방송일자</dt>
	                        <dd>
	                        <input type="text" style="cursor:pointer;"  id="calInput1" name="startDt"  value="<#if search.startDt?exists>${search.startDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>&#126;
	                        <input type="text" style="cursor:pointer;" id="calInput2"  name="endDt"  value="<#if search.endDt?exists>${search.endDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>
	                        </dd>
	                        <dt>인물정보</dt>
	                        <dd>
	                        	<input type="text" class="ip_text2" name="personInfo2" value="<#if search.personInfo2?exists>${search.personInfo2!""}</#if>" onkeydown="EnterPerson(event);" />
	                        	<span class="btn_pack gry"><a href="#" id="searchPeoples" onclick="showDisHide('people','1');searchPeople(this)">찾기</a></span>
	                        </dd>
	                        <dt>파일명</dt>
	                        <dd><input type="text" name="keyword" value="<#if search.keyword?exists>${search.keyword}</#if>" onKeyDown="keyEnter(event);" class="ip_text2" /></dd>
	                        <dt></dt>
	                    </dl>
	                    <article class="btncover2">
	                    	<span class="btn_pack gry"><a href="#"  id="checkSearch" onclick="checkSearch(this)">검색</a></span> <span class="btn_pack gry"><a href="javascript:searchEmpty();">초기화</a></span>
	                    </article>
            		
                </article>               
            </th></tr>
            </thead>
<!-- list -->
            <tbody>
            <tr><th>채널</th><th>파일명</th><th>회차</th><th>유형</th><th>콘텐츠구분</th><th>방송일</th><th>인물정보</th><th></th></tr>
			<#assign x = 20> 
			<#assign y = x>     
	            <#list contents.items as content>    
	            	<#if y%2==0>
	            		<tr class="gry">
		            <#else>
		            	<tr>
		            </#if>        
			             
			            
			            <td>${ tpl.getCodeDesc("CHAN", content.channelCode!"")}</td>
			            
						<#assign size=content.usrFileNm?length>
						<#assign regrid=content.regrid!"">
			         	<#if size <= 21>
			         	<td align="left" title="${content.ctId!""}">
			            	&nbsp;<a href="javascript:void(0)" onclick="playStop();getContentInfo('${content.ctId!""}');showDisHide('searchPeople','1');showDisHide('people','1');return false;">${content.usrFileNm!""}</a></div>
			            </td>
			            <#else>
			            <td align="left" title="${content.ctId!""}">
			            	&nbsp;<a href="javascript:void(0)" onclick="playStop();getContentInfo('${content.ctId!""}');showDisHide('searchPeople','1');showDisHide('people','1');return false;">${content.usrFileNm?substring(0,21)+" ..."}</a>
			            </td>
						</#if>
						<td align="right">${content.part!""}</td>
			            <td>${ tpl.getCodeDesc("CTYP", content.ctTyp!"")}</td>
			            <td>${ tpl.getCodeDesc("CCLA", content.ctCla!"")}</td>
			            
			            <td><#if content.brdDd?exists>${content.brdDd?string("yy/MM/dd")}</#if></td>  
			            
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
			            <#if y == 20> 
			            	<td rowspan="20">

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
				            <#if y == 20> 
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
</section>
<!-- //Container -->


</section>
</body>

</html>