<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
<script Language="JavaScript">
function goPage(pageNo) {
	//alert("!");
		document.ContentsSearch.pgmId.value="";
		document.ContentsSearch.action="<@spring.url '/popup/RmsContentList.ssc'/>";
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
	document.ContentsSearch.action="<@spring.url '/popup/RmsContentList.ssc'/>";
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
				document.ContentsSearch.action="<@spring.url '/popup/RmsContentList.ssc'/>";
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
//alert(document.ContentsSearch.downFileNm.value)
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
function programSearch(){
	//alert(live.popupGB.value);
	
	window.open("/popup/RmsProgramSearch.ssc","programSearch","width=970,height=820,scrollbars=yes");
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
				//table +='<object id="moviePlayerOther"  type="video/quicktime" data="http://210.115.198.103:8000'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'" width="284px" height="212px">'
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
                //table +='<param name="Filename" value="http://210.115.198.103:8000'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'">'
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
				//table +='<embed id="wPlayer" name="wPlayer" type="application/x-ms-wmp" pluginspage="http://www.microsoft.com/Windows/Downloads/Contents/Products/MediaPlayer/" src="http://210.115.198.103:8000'+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'" width="284px" height="212px">'
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
			  <#--
			  table +='<tr><th>프로그램ID</th>'
			  table +='<td><input type="text" name="pgmId" id="pgmId" class="ip_text" value=\''+isEmpty(data.contentsTbl.pgmId)+'\' readonly="readonly" />'
			   
			  table +='<span class="btns"><a href="javascript:saveContentInfo();" class="save" title="저장">저장</a></span>'
			  
			  table +='<span class="btns">'
			 
			  table +=<@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'saveContentInfo();' 'save' /> 
			  table +='</span>'
			 
			  
			  table +='<span class="btns"><a href="javascript:programSearch();" class="pg" title="프로그램정보조회">프로그램정보조회</a></span>'
			  table +='<span class="btns"><a href="javascript:goSchedule();" class="srch" title="검색">검색</a></span>'
  			   
  			  table +='</td></tr>'
  			  
			  table +='<tr><th>프로그램명</th><td><input type="text" class="ip_text" id="pgmNm" name="pgmNm" value=\''+isEmpty(data.contentsTbl.pgmNm)+'\' readonly="readonly"/></td></tr>'
  			   	
  			  if(data.contentsTbl.segmentId ==null){
  			  		table += '<tr id="sid"><th>세그먼트ID</th><td>'
		 			table += '<select name="segmentId">'
		 			table +='<option value="" >&#160; 선택 &#160;</option>'
		 			table += '</select>'
		 			table += '</td></tr>'
  			  }else{
  			  		table += '<tr id="sid"><th>세그먼트ID</th><td>'
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
			  table +='<tr><th>회차</th><td><input type="text" class="ip_text" name="part" value=\''+isEmpty(data.contentsTbl.part)+'\' /></td></tr>'
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
			  	if(data.typs[i].clfNm == isEmpty(data.codeTbl3.clfNm)){
			  		table += '<option value="'+data.typs[i].sclCd+'" selected>'+data.typs[i].clfNm+'</option>'
			  	}else{
			  		table += '<option value="'+data.typs[i].sclCd+'">'+data.typs[i].clfNm+'</option>'
			  	}
			  }
			  table +='</select></td></tr>'
			  -->
			  table +='<tr><th>프로그램코드</th>'
			  table +='<td>'+isEmpty(data.contentsTbl.pgmCd)+'</td></tr>'
			   table +='<tr><th>콘텐츠구분</th><td>'+isEmpty(data.codeTbl2.clfNm)+'</td></tr>'
			  table +='<tr><th>콘텐츠유형</th><td>'+isEmpty(data.codeTbl3.clfNm)+'</td></tr>'
			  table +='<tr><th>프로그램ID</th><td>'+isEmpty(data.contentsTbl.pgmId)+'</td></tr>'
			  
			  table +='<tr><th>프로그램명</th><td>'+isEmpty(data.contentsTbl.pgmNm)+'</td></tr>'
  			  if(data.contentsTbl.segmentId == null){
  			  	table += '<tr id="sid"><th>세그먼트ID</th><td></td></tr>'
  			  }else{ 	
  			  	table += '<tr id="sid"><th>세그먼트ID</th><td>'+isEmpty(data.contentsTbl.pgmNm)+'('+isEmpty(data.contentsTbl.segmentId)+')</td></tr>'
			  }
			  table +='<tr><th>회차</th><td>'+isEmpty(data.contentsTbl.part)+'</td></tr>'
			 
			  table +='<tr><th>파일사이즈</th><td>'+isEmpty(data.rcontentsInstTbls[0].flSz)+' byte</td></tr>' 
			  table +='<tr><th>파일포맷</th><td>'+isEmpty(data.rcontentsInstTbls[0].flExt)+'</td></tr>'
			  table +='<tr><th>등록일</th><td>'+isEmpty(data.regDate)+'</td></tr>'
			  table +='<tr><th>인물정보</th><td>'+isEmpty(data.contentsTbl.personInfo)+'</td></tr>'
			$jq('#viewContent').append(table);
			
			document.ContentsSearch.pgmCd.value=''+isEmpty(data.contentsTbl.pgmCd)+'';
			
			document.ContentsSearch.downFileNm.value=''+isEmpty(data.rcontentsInstTbls[0].usrFileNm)+'';
			
			document.ContentsSearch.filePath.value=''+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+''
			
			//alert(document.ContentsSearch.filePath.value);
			
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
		url: '<@spring.url "/popup/ajaxTransRequest.ssc" />',
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


function viewMediaPlayer(filePath,ctId,ctiId,v){
	//ContentsSearch.filePath.value=filePath;
	//ContentsSearch.ctId.value=ctId;
	//ContentsSearch.ctiId.value=ctiId;
		
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
	table += '</span>&nbsp;'
	
	$jq('#selectContentsInst').append(table);
	
	//window.clipboardData.setData("Text","http://210.115.198.102"+filePath);
	//prompt("Media URL :","http://210.115.198.102"+filePath);
	if(v == 'Q'){
		//alert("Q");
		//prompt("Media URL :","http://210.115.198.102"+filePath);
		//$jq('#Player').attr("URL","http://210.115.198.102"+filePath);
		//$jq('#moviePlayerSrc').attr("src","http://210.115.198.102"+filePath);
		//$jq('#moviePlayer').attr("data","http://210.115.198.102"+filePath);
		//$jq('#moviePlayer').attr("href","http://210.115.198.102"+filePath);
		//$jq('#moviePlayerOther').attr("data","http://210.115.198.102"+filePath);
	}else{
		//alert("W");
		
		
		//$jq('#wPlayer').attr("src","http://210.115.198.102"+filePath);
		//$jq('#Player').attr("Filename","http://210.115.198.102"+filePath);
		
		
		//$jq('#Player').attr("URL","http://210.115.198.102"+filePath);
		//$jq('#moviePlayerSrc').attr("src","http://210.115.198.102"+filePath);
		//$jq('#moviePlayer').attr("data","http://210.115.198.102"+filePath);
		//$jq('#moviePlayer').attr("href","http://210.115.198.102"+filePath);
		//$jq('#moviePlayerOther').attr("data","http://210.115.198.102"+filePath);
	}
}

function playStop(){
	$jq('#moviePlayerOther').attr("data","");
}

function webRegPopup(){
	//alert("1111");
	window.open("/popup/WebRegPopup.ssc","WebRegPopup","width=830,height=570,scrollbars=yes");
}

function callTra(){
	var pgmCd = document.getElementById('pgmCd').value;
	
	
	if(pgmCd === undefined || pgmCd == null || pgmCd.length <= 0){
			alert("프로그램이 선택되지 않았습니다!.");			
	}else{
		alert("트랜스 코딩 요청을 하겠습니다!.");
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
			 	document.ContentsSearch.action="<@spring.url '/content/RMSContent.ssc'/>";
				document.ContentsSearch.submit();
				
			}
			
				
		});
	}
	
	
}

function save(){
	
	var ctId = document.getElementById('ctId').value;
	var brdDd = document.getElementById('brdDd').value;

	if(ctId === undefined || ctId == null || ctId <= 0){
			alert("프로그램이 선택되지 않았습니다!.");			
	}else{
		//alert("저장 되었습니다!.");
		//document.ContentsSearch.downFile.value = ctId;
		$jq.ajax({
			url: "<@spring.url '/content/saveContentInfo.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ContentsSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
				document.ContentsSearch.pgmId.value = ""; 
				//document.ContentsSearch.pgmNm.value = ""; 
			 	document.ContentsSearch.action="<@spring.url '/content/RMSContent.ssc'/>";
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

function goRMS(){
	window.open("/popup/RmsContentList.ssc","programSearch","width=970,height=820,scrollbars=yes");
}
window.onload=function(){

}

function upload(nid,pcode,cttyp,avGubun,groupPcode){

	
	window.open("http://210.115.198.102/popup/WebRegPopup.ssc?avGubun="+avGubun+"&nid="+nid+"&pcode="+pcode+"&groupPcode="+groupPcode+"&cttyp="+cttyp,"WebRegPopup","width=600,height=300,scrollbars=yes");
	 
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

<script type="text/javascript" src="<@spring.url '/js/calendar.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/script.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/jquery.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/jquery.blockUI.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/date.js' />"></script>
<script type=text/javascript>	 
	var $jq = jQuery.noConflict();
	var baseUrl = "<@spring.url '/'/>";
</script>
<script type="text/javascript" src="<@spring.url '/js/jquery.form.js' />"></script>
</head>
<body>

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
		<input type="hidden" name="nid" value="${nid}">
    <section id="content2">
    	
        <article class="tabmenu_meta">
            <ul class="tab1">
            <li class="on"><a href="#"><span>콘텐츠정보조회</span></a></li>
            <li class="off"><a href="/popup/RmsContentUd.ssc?nid=${nid}&pcode=${pcode}"><span>콘텐츠정보편집</span></a></li>
            
            </ul>
           <#--
            <dd class="btncover2 btn" style="padding-left: 875px;padding-top: 10px;">
	    		<span class="btn_pack gry">
	    	
	    			<a href="#" onclick="javascript:upload('${nid}','${pcode}','${cttyp}','${avGubun}','${groupPcode}');">에센스업로드</a>
	    	
	    		</span> 
	    	
	    	</dd>
	    	-->
        </article>
       
<!-- Popup -->
		<article id="layer3" class="ly_pop12_1">
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
		<div class="ly_pop12_4">
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
				<#-- 
				<dd class="btncover11" >
	                	<span class="btn_pack gry">
	                	
	                	<a href="#" onclick="javascript:callDownload('');">다운로드</a>
	                	
	                	<a href="#" onclick="javascript:callTra('');">트랜스코딩 요청</a>
	                	</span> 
	                	
	                	
	                </dd>
	                -->
			</dl>
			
			</article>
		</div>
<!-- //Popup -->   
   
<!-- Table -->                             
        <article class="bbsview6">
        <table summary="" id="boardview9">
         <colgroup><col width="60px"></col><col width="70px"></col><col width=""></col><col width="30px"></col><col width="140px"></col><col width="70px"></col><col width="325px"></col></colgroup>
            
<!-- list -->
            <tbody>
            <tr><th>구분</th><th>유형</th><th>파일명</th><th>회차</th><th>인물정보</th><th>방송일</th><th></th></tr>
			<#assign x = 20> 
			<#assign y = x>     
	            <#list contents.items as content>    
	            	<#if y%2==0>
	            		<tr class="gry">
		            <#else>
		            	<tr>
		            </#if>        
			                   
			            <td>${ tpl.getCodeDesc("CCLA", content.ctCla!"")}</td>
			            <td>${ tpl.getCodeDesc("CTYP", content.ctTyp!"")}</td>
			            
						<#assign size=content.usrFileNm?length>
						<#assign regrid=content.regrid!"">
			         	
			         	<#if size <= 20>
			         	<td align="left" title="${content.ctId!""}">
			            	&nbsp;<img src='/images/icon_rms.gif'/>&nbsp;<a href="javascript:void(0)" onclick="playStop();getContentInfo('${content.ctId!""}');">${content.usrFileNm!""}</a>
			            </td>
			            <#else>
			            <td align="left" title="${content.ctId!""}">
			            	&nbsp;<img src='/images/icon_rms.gif'/>&nbsp;<a href="javascript:void(0)" onclick="playStop();getContentInfo('${content.ctId!""}');">${content.usrFileNm?substring(0,20)+" ..."}</a>
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
		<article class="btncover4">
		<span class="btn_pack gry">
		<#-- 
		<a href="#" onclick="deleteContents();">삭제</a>
		
		 <@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteContents();' '' />
		-->
		</span>
		</article>
		
	<!-- paginate -->
	    <@paging contents search.pageNo '' />
	<!-- //paginate -->
	
	
</form>
