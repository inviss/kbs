<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
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
		document.ContentsSearch.action="<@spring.url '/content/RMSContent.ssc'/>";
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
				
				document.ContentsSearch.pgmCd.value = ""; 
				document.ContentsSearch.action="<@spring.url '/content/RMSContent.ssc'/>";
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
//alert(filePath);
		if(filePath === undefined || filePath == null || filePath.length <= 0){
			alert("프로그램이 선택되지 않았습니다!.");			
		}else{
			alert("다운로드 요청을 하겠습니다!.");
			document.ContentsSearch.downFile.value = filePath;
			
			document.ContentsSearch.action="<@spring.url '/content/filedownload.ssc'/>";
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
	
	window.open(ctx+"/popup/RmsProgramSearch.ssc?keyword="+pcode+"&ctTyp="+ctTyp,"programSearch","width=970,height=820,scrollbars=yes");
}
function getContentInfo(ctId){
	//alert(ctId);
	//alert(document.ContentsSearch.ctiId.value);
	document.ContentsSearch.ctId.value=ctId;
	$jq('#Player').attr("URL","");
	var obj = ($jq('#ContentsSearch').get(0));
	$jq.ajax({
		url: '<@spring.url "/content/ajaxFindContentInfoRms.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			//-- ajax 콘텐츠 상세정보 get
			
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
			  table +='<span class="btns"><a href="javascript:programSearch(\''+isEmpty(data.contentsTbl.pgmCd)+'\');" class="pg" title="프로그램정보조회">프로그램정보조회</a></span>'

			  //table +='<span class="btns"><a href="javascript:goSchedule();" class="srch" title="검색">검색</a></span>'

			

  			  table +='</td></tr>'
			  table +='<tr><th>프로그램명</th><td><input type="text" class="ip_text5" id="pgmNm" name="pgmNm" value=\''+isEmpty(data.contentsTbl.pgmNm)+'\' readonly="readonly"/></td></tr>'
  			   	
  			  if(data.contentsTbl.segmentId ==null){
  			  		table += '<tr id="sid"><th>세그먼트ID</th><td>'
		 			table += '<select name="segmentId">'
		 			table +='<option value="" >선택</option>'
		 			table += '</select>'
		 			table += '</td></tr>'
  			  }else{
  			  		table += '<tr id="sid"><th>세그먼트ID</th><td>'
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
			document.ContentsSearch.pcode.value=''+isEmpty(data.contentsTbl.pgmCd)+'';
		    document.ContentsSearch.trimmSte.value=''+isEmpty(data.contentsTbl.trimmSte)+'';
		    document.ContentsSearch.gubun.value=''+isEmpty(data.rcontentsInstTbls[0].avGubun)+'';
			viewMediaPlayer(''+isEmpty(data.rcontentsInstTbls[0].flPath)+isEmpty(data.rcontentsInstTbls[0].orgFileNm)+'.'+isEmpty(data.rcontentsInstTbls[0].flExt)+'',''+isEmpty(data.rcontentsInstTbls[0].ctId)+'',''+isEmpty(data.rcontentsInstTbls[0].ctiId)+'');
		}
	});
	
	
}
function transRequest(value){
	document.ContentsSearch.ctiId.value=value;
	
	$jq('#Player').attr("URL","");
			
	var obj = ($jq('#ContentsSearch').get(0));
	$jq.ajax({
		url: '<@spring.url "/content/ajaxTransRequest.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
			alert('Return Error.');
		},
		success: function(data){
			alert('프로파일 재전송 요청 하였습니다.!');
			
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
	//alert("filePath="+filePath);
	//alert("ctId="+ctId);
	//alert("ctiId="+ctiId);
	
	ContentsSearch.filePath.value=filePath;
	ContentsSearch.ctId.value=ctId;
	ContentsSearch.ctiId.value=ctiId;
		
	$jq('#filePath').attr("filePath",filePath);
	$jq('#ctId').attr("ctId",ctId);
	$jq('#ctiId').attr("ctiId",ctiId);	
	$jq('#selectContentsInst').empty();
	var table ="";
	<#-- 
	table += '<span class="btn_pack gry"><a href="#" onclick="javascript:callDownload(\''+filePath+'\');">다운로드</a></span> <span class="btn_pack gry">'
	table += '<a href="#" onclick="javascript:deleteContentsInst(\''+ctId+'\');">삭제</a></span>'
	-->
	table += '<span class="btn_pack gry">'
	table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.download' 'callDownload();' '' />
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
	$jq('#Player').attr("URL", "<@spring.url '"+filePath+"'/>");
	
}

function webRegPopup(){
	//alert("1111");
	window.open("/popup/WebRegPopup.ssc","WebRegPopup","width=830,height=570,scrollbars=yes");
}

function callTra(){
	alert("!");
	var flExt = document.ContentsSearch.flExt.value;
	var trimmSte = document.ContentsSearch.trimmSte.value;
	
	var pgmCd = document.getElementById('pgmCd').value;
	var brdDd = document.getElementById('brdDd').value;
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
					url: "<@spring.url '/content/saveContentInfo.ssc'/>",
					type: 'POST',
					dataType: 'json',
					data: $jq('#ContentsSearch').serialize(),
					error: function(){
						//alert('Return Error.1');
					},
					success: function(data){
						$jq.ajax({
							url: "<@spring.url '/popup/reTrancoder.ssc'/>",
							type: 'POST',
							dataType: 'json',
							data: $jq('#ContentsSearch').serialize(),
							error: function(){
								//alert('Return Error.2');
							},
							success: function(data){
								//alert("1");
								document.ContentsSearch.pgmId.value = ""; 
								document.ContentsSearch.pgmNm.value = ""; 
								document.ContentsSearch.pgmCd.value = "";
							 	document.ContentsSearch.action="<@spring.url '/content/RMSContent.ssc'/>";
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
				url: "<@spring.url '/content/saveContentInfo.ssc'/>",
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
		  			  		table += '<tr id="sid"><th>세그먼트ID</th><td>'
				 			table += '<select name="segmentId">'
				 			table +='<option value="" >선택</option>'
				 			table += '</select>'
				 			table += '</td></tr>'
		  			  }else{
		  			  		table += '<tr id="sid"><th>세그먼트ID</th><td>'
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
					 
					  
					  table +='<tr><th>파일사이즈</th><td>'+isEmpty(data.rcontentsInstTbls[0].flSz)+'</td></tr>' 
			  		  table +='<tr><th>파일포맷</th><td>'+isEmpty(data.rcontentsInstTbls[0].flExt)+'</td></tr>'
					  table +='<tr><th>등록일</th><td>'+isEmpty(data.regDate)+'</td></tr>' 
		
					  table +='<tr><th>인물정보</th><td><input type="text" class="ip_text5" name="personInfo"  value=\''+isEmpty(data.contentsTbl.personInfo)+'\' readonly="readonly"/></td></tr>'
		
		
					  
					$jq('#viewContent').append(table);
					document.ContentsSearch.flExt.value=''+isEmpty(data.rcontentsInstTbls[0].flExt)+'';
					document.ContentsSearch.sSeq.value=''+data.contentsTbl.sSeq+'';
					document.ContentsSearch.pgmCd.value=''+isEmpty(data.contentsTbl.pgmCd)+'';
					document.ContentsSearch.pcode.value=''+isEmpty(data.contentsTbl.pgmCd)+'';
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
					
					
					document.ContentsSearch.action="<@spring.url '/content/RMSContent.ssc'/>";
					document.ContentsSearch.submit();
					
				}
				
					
			});
			//document.ContentsSearch.action="<@spring.url '/content/saveContentInfo.ssc'/>";
			//document.ContentsSearch.submit();
		
	}
}
function getScode(){
	var nm=document.ContentsSearch.pgmNm2.value;
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
	
	var pvalue=document.ContentsSearch.sSeq.value;
	var pcode=document.ContentsSearch.pgmCd.value;
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
	               	if(data.contentsTbl.length == 0){
	               		table +='<tr><th>-</th><td>-</td><td>-</td><td>-</td></tr>'
	               	}else{
	               		for(var i=0; i< data.contentsTbl.length ; i++){
	               			var z=0;
	               			for(var j=0; j< pInfo.length; j++){
	               				if(pInfo[j] == data.contentsTbl[i].sSeq){
	               					z++;
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
}

function personInfoSave(){
		$jq.ajax({
			url: "<@spring.url '/content/saveContentPersoninfo.ssc'/>",
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
function goMRMS(){
	window.open("/popup/MRmsContent.ssc","programSearch","width=1000,height=820,scrollbars=yes");
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
               		table +='<tr><th>-</th><td>-</td><td>-</td></tr>'
               	}else{
               		for(var i=0; i< data.contentsTbl.length ; i++){
  
               			table +='<tr><a href="javascript:void(0)" onclick="selectPeople(\''+data.contentsTbl[i].sSeq+'\',\''+data.contentsTbl[i].nameKorea+'\');showDisHide(\'searchPeople\',\'1\');return false;"><th><img src="'+data.contentsTbl[i].imageUrl+'" title="" width="50px" height="50px" class="thumb"/></th>'
               			table +='<td>'+data.contentsTbl[i].nameKorea+'</td>'
               			table +='<td>'+data.contentsTbl[i].castingName+'</td></th></a></tr>'
               			
               			<#--
               			table +='<tr><th><img src="'+data.contentsTbl[i].imageUrl+'" title="" width="50px" height="50px" class="thumb"/></th>'
               			table +='<td><a href="javascript:void(0)" onclick="selectPeople(\''+data.contentsTbl[i].sSeq+'\',\''+data.contentsTbl[i].nameKorea+'\');showDisHide(\'searchPeople\',\'1\');return false;">'+data.contentsTbl[i].nameKorea+'</a></td>'
               			table +='<td>'+data.contentsTbl[i].castingName+'</td></th></tr>';
               			-->
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
	alert("!");
	showDisHide('searchPeople','1');
}


</script>
<section id="container">
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
		<input type="hidden" name="searchPeople" value="">
		
		<input type="hidden" name="gubun" value="">
		<input type="hidden" name="flExt" value="">
    <section id="content">
    	<h3 class="blind">콘텐츠조회</h3>    
		<article class="title"><img src="<@spring.url '/images/title_contents.gif'/>" title="콘텐츠"/></article>

        <nav class="snavi">
        <!--
        <a href="javascript:void(0)" onClick="goUpload();">업로드</a>
        <a href="javascript:void(0)" onClick="goMRMS();">M보관콘텐츠</a>
        <a href="javascript:void(0)" onClick="goRMS();">RMS</a>
        -->
        <span class="home">HOME</span> &gt; 콘텐츠 관리 &gt; <span class="now">보관콘텐츠</span></nav>
        <article class="icon_info">
			<span><img src='<@spring.url "/images/icon_KDAS.gif"/>'/>&nbsp;KDAS</span>
			<span><img src='<@spring.url "/images/icon_DNPS.gif"/>'/>&nbsp;NPS</span>
			<span><img src='<@spring.url "/images/icon_AACH.gif"/>'/>&nbsp;AACH</span>
			<span><img src='<@spring.url "/images/icon_DMCR.gif"/>'/>&nbsp;DMCR</span>
			<span><img src='<@spring.url "/images/icon_NAS.gif"/>'/>&nbsp;NAS</span>
			<span><img src='<@spring.url "/images/icon_TS.gif"/>'/>&nbsp;DOAD</span>
			<span><img src='<@spring.url "/images/icon_LIVE.gif"/>'/>&nbsp;Live</span>
			<span><img src='<@spring.url "/images/icon_VCR.gif"/>'/>&nbsp;VCR</span>
			<span><img src='<@spring.url "/images/icon_VTRIM.gif"/>'/>&nbsp;V-Trim</span>
			<span><img src='<@spring.url "/images/icon_ATRIM.gif"/>'/>&nbsp;A-Trim</span>
			<span><img src='<@spring.url "/images/icon_RMS.gif"/>'/>&nbsp;RMS</span>
		</article>
        <article class="tabmenu">
            <ul class="tab1">
            <li class="off"><a href="<@spring.url '/content/ContentSearch.ssc'/>"><span>서비스콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/StandbyContent.ssc'/>"><span>대기콘텐츠</span></a></li>
            <li class="on"><a href="#"><span>보관콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/ManualReg.ssc'/>"><span>수동등록</span></a></li>
            <!--<li class="off"><a href="/content/WebReg.ssc"><span>웹등록</span></a></li>-->
            </ul>
        </article>
         
<!-- Popup -->
		<article id="layer4" class="ly_pop12">
			<article class="sideinfo">
	        	<dl class="btncover3">
	            	<dt>영상보기</dt>
	                <dd class="mgl7">
	                   <object name="Player" ID="Player"  CLASSID="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" width="284px" height="212px" >
                               <PARAM  name="URL"  value="">
                       </object>
                 <#--
	                -->
	                </dd><br><br>
	                <dd class="btncover2 btn" id="selectContentsInst">
	                	<span class="btn_pack gry">
	                	<#-- 
	                	<a href="#" onclick="javascript:callDownload('');">다운로드</a>
	                	-->
	                	<@button '${search.menuId}' '${user.userId}' 'common.download' 'callDownload();' '' />
	                	</span> 
	                	
	                </dd>
	            </dl>
	        </article>
	    </article>    
		<div class="ly_pop12_2">
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
	                	<#-- 
	                	<a href="#">삭제</a>
	                	-->
	                	<@button '${search.menuId}' '${user.userId}' 'common.save' 'save(this)' '' />
	                	</span>
	                	<span class="btn_pack gry">
	                	<a href="#" onclick="showDisHide('searchPeople','1');personInfoSelect();return false;">인물 편집</a>
	                	</span>
	                	&nbsp;&nbsp;
	                </dd>
			</dl>
			
			</article>
		</div>
<!-- //Popup -->   
<!-- 인물정보(프로그램코드로 인물검색) Popup -->
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
        <article class="bbsview3">  
        <table summary="" id="boardview3">

         <colgroup><col width="20px"></col><col width="35px"></col><col width=""></col><col width="35px"></col><col width="70px"></col><col width="60px"></col><col width="60px"></col><col width="115px"></col><col width="325px"></col></colgroup>

            <thead>
            <tr><th colspan="9">
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
	                        	<input type="text" class="ip_text2"  name="personInfo2" value="<#if search.personInfo2?exists>${search.personInfo2!""}</#if>" onkeydown="if(event.keyCode == 13) document.getElementById('searchPeoples').click()" />
	                        	<span class="btn_pack gry"><a href="#" id="searchPeoples" onclick="showDisHide('people','1');searchPeople(this)">찾기</a></span>
	                        </dd>
	                        <dt>파일명</dt>
	                        <dd><input type="text" name="keyword" value="<#if search.keyword?exists>${search.keyword}</#if>" onKeyDown="keyEnter();" class="ip_text2" /></dd>
	                        <dt></dt>
	                    </dl>
	                    <article class="btncover2">
	                    	<span class="btn_pack gry"><a href="#"  onclick="checkSearch(this)">검색</a></span> <span class="btn_pack gry"><a href="javascript:searchEmpty();">초기화</a></span>
	                    </article>
            		
                </article>               
            </th></tr>
            </thead>
<!-- list -->
            <tbody>

            <tr><th></th><th>채널</th><th>파일명</th><th>회차</th><th>유형</th><th>콘텐츠구분</th><th>방송일</th><th>인물정보</th><th></th></tr>

			<#assign x = 20> 
			<#assign y = x>     
	            <#list contents.items as content>    
	            	<#if y%2==0>
	            		<tr class="gry">
		            <#else>
		            	<tr>
		            </#if>        
			            <td><input name="ctIds" type="checkbox" value="${content.ctId!""}"></td>          
			            
			            <td>${ tpl.getCodeDesc("CHAN", content.channelCode!"")}</td>
			            
						<#assign size=content.usrFileNm?length>
						<#assign regrid=content.regrid!"">
	
			         	<#if size <= 11>
			         	<td align="left" title="${content.ctId!""}">

			            	&nbsp;<div class="icon"><img src='<@spring.url "/images/icon_"+regrid+".gif"/>'/>&nbsp;<a href="javascript:void(0)" onclick="getContentInfo('${content.ctId!""}');Player.controls.stop();showDisHide('people','1');showDisHide('searchPeople','1');return false;">${content.usrFileNm!""}</a></div>

			            </td>
			            <#else>
			            <td align="left" title="${content.ctId!""}">

			            	&nbsp;<div class="icon"><img src='<@spring.url "/images/icon_"+regrid+".gif"/>'/>&nbsp;<a href="javascript:void(0)" onclick="getContentInfo('${content.ctId!""}');Player.controls.stop();showDisHide('people','1');showDisHide('searchPeople','1');return false;">${content.usrFileNm?substring(0,11)+" ..."}</a></div>

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
			            	<#if psize <= 9>
			            		<td>${content.personInfo!""}</td>
							<#else>
								<td>${content.personInfo?substring(0,8)+"..."}</td>
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
		-->
		 <@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteContents();' '' />
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