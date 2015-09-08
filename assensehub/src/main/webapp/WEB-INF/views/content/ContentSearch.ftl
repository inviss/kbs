<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">
function goPage(pageNo) {
		//document.ContentsSearch.pgmId.value="";
		document.ContentsSearch.action="<@spring.url '/content/ContentSearch.ssc'/>";
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
		
    //document.ContentsSearch.pgmId.value="";
	document.ContentsSearch.action="<@spring.url '/content/ContentSearch.ssc'/>";
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
				document.ContentsSearch.action="<@spring.url '/content/ContentSearch.ssc'/>";
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
	window.open("<@spring.url '/popup/Schedule.ssc'/>","goSchedule","width=970,height=700,scrollbars=yes");
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
			alert("파일/프로파일이 선택되지 않았습니다!.");			
		}else{
			alert("다운로드 요청을 하겠습니다!.");
			document.ContentsSearch.downFile.value = filePath;
			
			document.ContentsSearch.action="<@spring.url '/content/filedownload.ssc'/>";
			document.ContentsSearch.submit();
		}
}


function deleteContentsInst(tmp){
	//alert(ctiId);
	document.ContentsSearch.filePath.value="";
	//document.ContentsSearch.pgmId.value="";
	var ctiId = document.getElementById('ctiId').value;
	//alert(ctiId);
		if(ctiId === undefined || ctiId == null || ctiId <= 0){
			alert("파일/프로파일이 선택되지 않았습니다!.");			
		}else{
			
			document.ContentsSearch.ctiId.value = ctiId;
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
					
					$jq('#viewContent').empty();
					  var table="";
					  table +='<tr><th>프로그램ID</th>'
					  table +='<td><input type="text" name="pgmId" id="pgmId" class="ip_text" value=\''+isEmpty(data.contentsTbl.pgmId)+'\' readonly="readonly" />'
					  <#-- 
					  table +='<span class="btns"><a href="javascript:saveContentInfo();" class="save" title="저장">저장</a></span>'
					  -->
					  table +='<span class="btns">'
					  table +=<@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'saveContentInfo();' 'save' /> 
					  table +='</span>'
					  table +='<span class="btns"><a href="javascript:goSchedule();" class="srch" title="검색">검색</a></span>'
		  			  table +='</td></tr>'
					  table +='<tr><th>제목</th><td id="pgmNm">'+isEmpty(data.contentsTbl.pgmNm)+'</td></tr>'
					  table +='<tr><th>콘텐츠명</th><td>'+isEmpty(data.contentsTbl.ctNm)+'</td></tr>'
					$jq('#viewContent').append(table);
							
					$jq('#listProfile').empty();
					  var table="";
					  table +='<tr><th>파일명 / 프로파일명</th><th>영상</th><th>전송</th></tr>'
					  for(var i=0;i<data.contentsInstTbls.length;i++){
					 	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
					  }
					$jq('#listProfile').append(table);
					document.ContentsSearch.ctiId.value = "";
				}
			});
			
		}
}

function getContentInfo(ctId){
	
	document.ContentsSearch.ctId.value=ctId;
	$jq('#Player').attr("URL","");
	var obj = ($jq('#ContentsSearch').get(0));
	$jq.ajax({
		url: '<@spring.url "/content/ajaxFindContentInfo.ssc" />',
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
			  table +='<tr><th>프로그램ID</th>'
			  table +='<td id="pgmId">'+isEmpty(data.contentsTbl.pgmId)+'</td></tr>'
			  table +='<tr><th>제목</th><td id="pgmNm">'+isEmpty(data.contentsTbl.pgmNm)+'</td></tr>'
			  table +='<tr><th>콘텐츠명</th><td>'+isEmpty(data.contentsTbl.ctNm)+'</td></tr>'
			$jq('#viewContent').append(table);
			
			
			if(data.contentsTbl.channelCode == "11" || data.contentsTbl.channelCode == "12"){
				document.ContentsSearch.avGubun.value='V';
			}else{
				document.ContentsSearch.avGubun.value='A';
			} 
			
			document.ContentsSearch.pgmCd.value=''+isEmpty(data.contentsTbl.pgmCd)+'';
			document.ContentsSearch.ctTyp.value=''+isEmpty(data.contentsTbl.ctTyp)+'';
			
			
			$jq('#listProfile').empty();
			var table="";
			
			table +='<tr><th>파일명 / 프로파일명</th><th>상태</th></tr>'
			for(var i=0;i<data.contentsInstTbls.length;i++){
				table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span id="imgarrange" class="btns"><a href="#layer" class="dbon" title="DB연결됨" onclick="divHide();showDisHide(\'layer_'+i+'\',\'0\');viewMediaPlayer2(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');return false;">DB연결됨</a><div id="layer_'+i+'" class="ly_pop19"  style="display:none;"><ul><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');callDownload();" class="clse">다운로드</a></li><li><a href="#layer_1" onclick="showDisHide(\'layer_'+i+'\',\'1\');reCreate();" class="clse">ContentML 생성</a></li><li><a href="#layer_1" onclick="showDisHide(\'layer_'+i+'\',\'1\');contentsCopy();" class="clse">복사</a></li><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');proflRequest('+data.contentsInstTbls[i].ctiId+');" class="clse">트랜스코더 재요청</a></li><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');transRequest('+data.contentsInstTbls[i].ctiId+');" class="clse">트랜스퍼 재요청</a></li></ul></div></span></td></tr>'
			}
			for(var i=0;i<data.proFlTbls.length;i++){
				table +='<tr><td><span class="text">'+isEmpty(data.proFlTbls[i].proFlnm)+'</span></td><td class="vam"><span id="imgarrange" class="btns"><a href="#layer" class="dboff" title="DB연결안됨" onclick="divHide();showDisHide(\'layerNew_'+i+'\',\'0\');">DB연결안됨</a><div id="layerNew_'+i+'" class="ly_pop19"  style="display:none;"><ul><li><a href="#layerNew_1" onclick="showDisHide(\'layerNew_'+i+'\',\'1\');newMlCreate(\''+isEmpty(data.proFlTbls[i].proFlnm)+'\',\''+isEmpty(data.proFlTbls[i].proFlid)+'\',\''+isEmpty(data.proFlTbls[i].flNameRule)+'\',\''+isEmpty(data.proFlTbls[i].ext)+'\');" class="clse">ContentML 생성</a></li><li><a href="#layerNew" onclick="showDisHide(\'layerNew_'+i+'\',\'1\');newProflRequest(\''+data.proFlTbls[i].proFlid+'\');" class="clse">트랜스코더 재요청</a></li></ul></div></span></td></tr>'
			}
			$jq('#listProfile').append(table);
			
			
			document.nProfl.ctId.value=''+isEmpty(data.contentsTbl.ctId)+'';
			document.nProfl.pgmId.value=''+isEmpty(data.contentsTbl.pgmId)+'';
			document.nProfl.downFlNm.value=''+isEmpty(data.contentsInstTbls[0].wrkFileNm)+'';
			document.nProfl.fPath.value=''+isEmpty(data.contentsInstTbls[0].flPath)+'';
			document.nProfl.orgNm.value=''+isEmpty(data.contentsInstTbls[0].orgFileNm)+'';
			
			
			document.ContentsSearch.newInstLeng.value=''+isEmpty(data.proFlTbls.length)+'';
			document.ContentsSearch.instLeng.value=''+isEmpty(data.contentsInstTbls.length)+'';
			document.ContentsSearch.a_ctiId.value=''+isEmpty(data.contentsInst.ctiId)+'';
			
		}
	});
}

function newMlCreate(proFlnm,proFlid,flNameRule,ext){
	document.nProfl.proFlid.value=proFlid;
	document.nProfl.flNameRule.value=flNameRule;
	document.nProfl.proFlnm.value=proFlnm;
	document.nProfl.ext.value=ext;
	
	
	document.nProfl.action="<@spring.url '/content/newContentML.ssc'/>";
	document.nProfl.submit();
}

function divHide(){
	var leng = document.ContentsSearch.instLeng.value;
	var newLeng = document.ContentsSearch.newInstLeng.value;
	
	
	for(var i=0;i<leng;i++){
		showDisHide('layer_'+i+'','1');
	}
	for(var i=0;i<newLeng;i++){
		showDisHide('layerNew_'+i+'','1');
	}
}

function transRequest(value){
	//alert("1");
	//alert(value);
	
	var check = confirm("전송 재전송 하시겠습니까?");
	
	if(check == true){
		
		
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
				alert('전송 재전송 요청 하였습니다.!');
				
				<#--				
				$jq('#listProfile').empty();
				  var table="";
				  table +='<tr><th>파일명 / 프로파일명</th><th>영상</th><th>전송</th></tr>'
				  for(var i=0;i<data.contentsInstTbls.length;i++){
				 	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
				  }
				$jq('#listProfile').append(table);
				-->
			}
		});
		
	}else{
		return;
	}
}

function newProflRequest(value){
	//alert(value);
	
	document.nProfl.proFlid.value=value;
	document.nProfl.gubun.value=document.ContentsSearch.avGubun.value;
	document.nProfl.nctiId.value=document.ContentsSearch.a_ctiId.value;

	document.nProfl.nctTyp.value=document.ContentsSearch.ctTyp.value;
	document.nProfl.npgmCd.value=document.ContentsSearch.pgmCd.value;
	
	
	var check = confirm("프로파일 재요청 하시겠습니까?");
	
	if(check == true){
		
		$jq('#Player').attr("URL","");
				
		var obj = ($jq('#nProfl').get(0));
		$jq.ajax({
			url: '<@spring.url "/content/ajaxNewProflRequest.ssc" />',
			type: 'POST',
			dataType: 'json',
			data: $jq('#nProfl').serialize(),
			error: function(){
				alert('소스 파일이 존재하지 않습니다.');
			},
			success: function(data){
				alert('프로파일 재요청 하였습니다.!');
								
				$jq('#listProfile').empty();
				  var table="";
				  //table +='<tr><th>파일명 / 프로파일명</th><th>영상</th><th>전송</th></tr>'
				  //for(var i=0;i<data.contentsInstTbls.length;i++){
				  //	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
				  //}
				  
				  
				  table +='<tr><th>파일명 / 프로파일명</th><th>상태</th></tr>'
				  for(var i=0;i<data.contentsInstTbls.length;i++){
				 	 //table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
				  	
				  	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span id="imgarrange" class="btns"><a href="#layer" class="dbon" title="DB연결됨" onclick="divHide();showDisHide(\'layer_'+i+'\',\'0\');viewMediaPlayer2(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');return false;">DB연결됨</a><div id="layer_'+i+'" class="ly_pop19"  style="display:none;"><ul><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');callDownload();" class="clse">다운로드</a></li><li><a href="#layer_1" onclick="showDisHide(\'layer_'+i+'\',\'1\');reCreate();" class="clse">ContentML 생성</a></li><li><a href="#layer_1" onclick="showDisHide(\'layer_'+i+'\',\'1\');contentsCopy();" class="clse">복사</a></li><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');proflRequest('+data.contentsInstTbls[i].ctiId+');" class="clse">트랜스코더 재요청</a></li><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');transRequest('+data.contentsInstTbls[i].ctiId+');" class="clse">트랜스퍼 재요청</a></li></ul></div></span></td></tr>'
				  	 
				  	 
				  }
			      for(var i=0;i<data.proFlTbls.length;i++){
				 	 
				  	 table +='<tr><td><span class="text">'+isEmpty(data.proFlTbls[i].proFlnm)+'</span></td><td class="vam"><span id="imgarrange" class="btns"><a href="#layer" class="dboff" title="DB연결안됨" onclick="divHide();showDisHide(\'layerNew_'+i+'\',\'0\');">DB연결안됨</a><div id="layerNew_'+i+'" class="ly_pop19"  style="display:none;"><ul><li><a href="#layerNew_1" onclick="showDisHide(\'layerNew_'+i+'\',\'1\');newMlCreate(\''+isEmpty(data.proFlTbls[i].proFlnm)+'\',\''+isEmpty(data.proFlTbls[i].proFlid)+'\',\''+isEmpty(data.proFlTbls[i].flNameRule)+'\',\''+isEmpty(data.proFlTbls[i].ext)+'\');" class="clse">ContentML 생성</a></li><li><a href="#layerNew" onclick="showDisHide(\'layerNew_'+i+'\',\'1\');newProflRequest(\''+data.proFlTbls[i].proFlid+'\');" class="clse">트랜스코더 재요청</a></li></ul></div></span></td></tr>'
				  	 
				  }
				  
				document.ContentsSearch.newInstLeng.value=''+isEmpty(data.proFlTbls.length)+'';
				document.ContentsSearch.instLeng.value=''+isEmpty(data.contentsInstTbls.length)+'';  
				$jq('#listProfile').append(table);
			}
		});
	}else{
		return;
	}	
}

function proflRequest(value){
	var check = confirm("프로파일 재요청 하시겠습니까?");
	
	if(check == true){
		document.ContentsSearch.ctiId.value=value;
		
		$jq('#Player').attr("URL","");
				
		var obj = ($jq('#ContentsSearch').get(0));
		$jq.ajax({
			url: '<@spring.url "/content/ajaxProflRequest.ssc" />',
			type: 'POST',
			dataType: 'json',
			data: $jq('#ContentsSearch').serialize(),
			error: function(){
				alert('소스 파일이 존재하지 않습니다.');
			},
			success: function(data){
				alert('프로파일 재요청 하였습니다.!');
								
				$jq('#listProfile').empty();
				  var table="";
				  //table +='<tr><th>파일명 / 프로파일명</th><th>영상</th><th>전송</th></tr>'
				  //for(var i=0;i<data.contentsInstTbls.length;i++){
				  //	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
				  //}
				  
				  table +='<tr><th>파일명 / 프로파일명</th><th>상태</th></tr>'
				  for(var i=0;i<data.contentsInstTbls.length;i++){
				 	 //table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
				  	
				  	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span id="imgarrange" class="btns"><a href="#layer" class="dbon" title="DB연결됨" onclick="divHide();showDisHide(\'layer_'+i+'\',\'0\');viewMediaPlayer2(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');return false;">DB연결됨</a><div id="layer_'+i+'" class="ly_pop19"  style="display:none;"><ul><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');callDownload();" class="clse">다운로드</a></li><li><a href="#layer_1" onclick="showDisHide(\'layer_'+i+'\',\'1\');reCreate();" class="clse">ContentML 생성</a></li><li><a href="#layer_1" onclick="showDisHide(\'layer_'+i+'\',\'1\');contentsCopy();" class="clse">복사</a></li><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');proflRequest('+data.contentsInstTbls[i].ctiId+');" class="clse">트랜스코더 재요청</a></li><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');transRequest('+data.contentsInstTbls[i].ctiId+');" class="clse">트랜스퍼 재요청</a></li></ul></div></span></td></tr>'
				  	 
				  	 
				  }
			      for(var i=0;i<data.proFlTbls.length;i++){
				 	 
				  	 table +='<tr><td><span class="text">'+isEmpty(data.proFlTbls[i].proFlnm)+'</span></td><td class="vam"><span id="imgarrange" class="btns"><a href="#layer" class="dboff" title="DB연결안됨" onclick="divHide();showDisHide(\'layerNew_'+i+'\',\'0\');">DB연결안됨</a><div id="layerNew_'+i+'" class="ly_pop19"  style="display:none;"><ul><li><a href="#layerNew_1" onclick="showDisHide(\'layerNew_'+i+'\',\'1\');newMlCreate(\''+isEmpty(data.proFlTbls[i].proFlnm)+'\',\''+isEmpty(data.proFlTbls[i].proFlid)+'\',\''+isEmpty(data.proFlTbls[i].flNameRule)+'\',\''+isEmpty(data.proFlTbls[i].ext)+'\');" class="clse">ContentML 생성</a></li><li><a href="#layerNew" onclick="showDisHide(\'layerNew_'+i+'\',\'1\');newProflRequest(\''+data.proFlTbls[i].proFlid+'\');" class="clse">트랜스코더 재요청</a></li></ul></div></span></td></tr>'
				  	 
				  }
				  
				document.ContentsSearch.newInstLeng.value=''+isEmpty(data.proFlTbls.length)+'';
				document.ContentsSearch.instLeng.value=''+isEmpty(data.contentsInstTbls.length)+'';
				$jq('#listProfile').append(table);
			}
		});
	}else{
		return;
	}	
}

function allProflRe(){

		var ctId= document.ContentsSearch.ctId.value;
		var a_ctiId= document.ContentsSearch.a_ctiId.value;
		
		if(ctId === undefined || ctId == null || ctId <= 0){
			alert("프로그램이 선택되지 않았습니다!");			
		}else{
			$jq.ajax({
				url: '<@spring.url "/content/ajaxAllProflRequest.ssc" />',
				type: 'POST',
				dataType: 'json',
				data: $jq('#ContentsSearch').serialize(),
				error: function(){
					alert('Return Error.');
				},
				success: function(data){
					alert('프로파일 전체 재요청 하였습니다.!');
									
					$jq('#listProfile').empty();
					  var table="";
					  //table +='<tr><th>파일명 / 프로파일명</th><th>영상</th><th>전송</th></tr>'
					  //for(var i=0;i<data.contentsInstTbls.length;i++){
					  //	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
					  //}
					  
					  table +='<tr><th>파일명 / 프로파일명</th><th>상태</th></tr>'
					  for(var i=0;i<data.contentsInstTbls.length;i++){
					 	 //table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="javascript:proflRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
					  	
					  	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span id="imgarrange" class="btns"><a href="#layer" class="dbon" title="DB연결됨" onclick="divHide();showDisHide(\'layer_'+i+'\',\'0\');viewMediaPlayer2(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\',\''+isEmpty(data.contentsInstTbls[i].wrkFileNm)+'\');return false;">DB연결됨</a><div id="layer_'+i+'" class="ly_pop19"  style="display:none;"><ul><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');callDownload();" class="clse">다운로드</a></li><li><a href="#layer_1" onclick="showDisHide(\'layer_'+i+'\',\'1\');reCreate();" class="clse">ContentML 생성</a></li><li><a href="#layer_1" onclick="showDisHide(\'layer_'+i+'\',\'1\');contentsCopy();" class="clse">복사</a></li><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');proflRequest('+data.contentsInstTbls[i].ctiId+');" class="clse">트랜스코더 재요청</a></li><li><a href="#layer" onclick="showDisHide(\'layer_'+i+'\',\'1\');transRequest('+data.contentsInstTbls[i].ctiId+');" class="clse">트랜스퍼 재요청</a></li></ul></div></span></td></tr>'
					  	 
					  	 
					  }
				      for(var i=0;i<data.proFlTbls.length;i++){
					 	 
					  	 table +='<tr><td><span class="text">'+isEmpty(data.proFlTbls[i].proFlnm)+'</span></td><td class="vam"><span id="imgarrange" class="btns"><a href="#layer" class="dboff" title="DB연결안됨" onclick="divHide();showDisHide(\'layerNew_'+i+'\',\'0\');">DB연결안됨</a><div id="layerNew_'+i+'" class="ly_pop19"  style="display:none;"><ul><li><a href="#layerNew_1" onclick="showDisHide(\'layerNew_'+i+'\',\'1\');newMlCreate(\''+isEmpty(data.proFlTbls[i].proFlnm)+'\',\''+isEmpty(data.proFlTbls[i].proFlid)+'\',\''+isEmpty(data.proFlTbls[i].flNameRule)+'\',\''+isEmpty(data.proFlTbls[i].ext)+'\');" class="clse">ContentML 생성</a></li><li><a href="#layerNew" onclick="showDisHide(\'layerNew_'+i+'\',\'1\');newProflRequest(\''+data.proFlTbls[i].proFlid+'\');" class="clse">트랜스코더 재요청</a></li></ul></div></span></td></tr>'
					  	 
					  }
					document.ContentsSearch.newInstLeng.value=''+isEmpty(data.proFlTbls.length)+'';
					document.ContentsSearch.instLeng.value=''+isEmpty(data.contentsInstTbls.length)+'';
					$jq('#listProfile').append(table);
				}
			});
			
		}
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


function viewMediaPlayer(filePath,ctId,ctiId,wrkFileNm,proFlid){
	ContentsSearch.filePath.value=filePath;
	ContentsSearch.ctId.value=ctId;
	ContentsSearch.ctiId.value=ctiId;
	ContentsSearch.downFileNm.value=wrkFileNm;
	
	$jq('#re_downFileNm').val(wrkFileNm);
	$jq('#re_ctiId').val(ctiId);
	//document.reContentML.re_downFileNm.value=wrkFileNm;
	//document.reContentML.re_ctiId.value=ctiId;
		
	$jq('#filePath').attr("filePath",filePath);
	$jq('#ctId').attr("ctId",ctId);
	$jq('#ctiId').attr("ctiId",ctiId);	
	<#-- 
	$jq('#selectContentsInst').empty();
	var table ="";
	
	table += '<span class="btn_pack gry"><a href="#" onclick="javascript:callDownload(\''+filePath+'\');">다운로드</a></span> <span class="btn_pack gry">'
	table += '<a href="#" onclick="javascript:deleteContentsInst(\''+ctId+'\');">삭제</a></span>'
	
	table += '<span class="btn_pack gry">'
	table += <@buttonSingle '${search.menuId}' '${user.userId}' 'contentML.recreate' 'reCreate();' '' />
	table += '</span>&nbsp;'
	
	table += '<span class="btn_pack gry">'
	table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.contentsCopy' 'contentsCopy();' '' />
	//table += '<a href="#" onclick="javascript:contentsCopy();">복사</a></span>'
	table += '</span>&nbsp;'
	table += '<span class="btn_pack gry">'
	table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.delete' 'deleteContentsInst();' '' />
	table += '</span>'
	
	$jq('#selectContentsInst').append(table);
	-->
	//prompt("Media URL :", "<@spring.url '"+filePath+"'/>");
	$jq('#Player').attr("URL", "<@spring.url '"+filePath+"'/>");
	
}

function viewMediaPlayer2(filePath,ctId,ctiId,wrkFileNm,proFlid){
	ContentsSearch.filePath.value=filePath;
	ContentsSearch.ctId.value=ctId;
	ContentsSearch.ctiId.value=ctiId;
	ContentsSearch.downFileNm.value=wrkFileNm;
	
	$jq('#re_downFileNm').val(wrkFileNm);
	$jq('#re_ctiId').val(ctiId);
		
	$jq('#filePath').attr("filePath",filePath);
	$jq('#ctId').attr("ctId",ctId);
	$jq('#ctiId').attr("ctiId",ctiId);	
	
}

function webRegPopup(){
	window.open("<@spring.url '/popup/WebRegPopup.ssc'/>", "WebRegPopup","width=830,height=570,scrollbars=yes");
}


window.onload=function() {
	cal1 = new dhtmlxCalendarObject('calInput1', false, {isYearEditable: true,isMonthEditable: true});
	cal1.setYearsRange(1973, 2020);
	cal1.draw();

	cal2 = new dhtmlxCalendarObject('calInput2', false, {isYearEditable: true,isMonthEditable: true});
	cal2.setYearsRange(1973, 2020);
	cal2.draw();
}

function reCreate(){
	var ctiId = $jq('#re_ctiId').val();
	//alert(ctiId);
	if(ctiId== ""){
		alert("프로그램 및 프로파일을 선택하세요.");
		return;
	}
	

	var obj = ($jq('#reContentML').get(0));
	obj.action="<@spring.url '/content/reContentML.ssc'/>";
	obj.method="post";
	obj.submit();
}

function contentsCopy(){
	var ctiId = $jq('#re_ctiId').val();
	
	if(ctiId== ""){
		alert("프로그램 및 프로파일을 선택하세요.");
		return;
	}
	
	$jq.ajax({
		url: '<@spring.url "/content/contentsCopy.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#reContentML').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			alert("복사되었습니다!.");
		}
	});
}


</script>
<section id="container">
	
	<form name="ContentsSearch" id="ContentsSearch" method="post">
		<@spring.bind "search" />
      	<input type="hidden" name="menuId" value="${search.menuId}">
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="ctiId" id="ctiId" value="" />
		<input type="hidden" name="a_ctiId" id="a_ctiId" value="" />
		<input type="hidden" name="downFile" value="" />
		<input type="hidden" name="downFileNm" id="downFileNm" value="" />
		<input type="hidden" name="pgmNm"  value="" />
		<input type="hidden" name="pgmCd" id="pgmCd" value=""  />
		<input type="hidden" name="pgmGrpCd"  value="" />
		<input type="hidden" name="ctTyp" id="ctTyp" value=""  />
		<input type="hidden" name="avGubun" id="avGubun" value=""  />
		<input type="hidden" name="ctId" id="ctId" value="" />
		<input type="hidden" name="filePath" id="filePath" value="" />
		<input type="hidden" name="instLeng" id="instLeng" value="" />
		<input type="hidden" name="newInstLeng" id="newInstLeng" value="" />
		
    <section id="content">
    	<h3 class="blind">콘텐츠조회</h3>    
		<article class="title"><img src="<@spring.url '/images/title_contents.gif'/>" title="콘텐츠"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 콘텐츠 관리 &gt; <span class="now">서비스콘텐츠</span></nav>
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
            <li class="on"><a href="#"><span>서비스콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/StandbyContent.ssc'/>"><span>대기콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/RMSContent.ssc'/>"><span>보관콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/ManualReg.ssc'/>"><span>수동등록</span></a></li>
            <!--<li class="off"><a href="/content/WebReg.ssc"><span>웹등록</span></a></li>-->
            </ul>
        </article>
        
<!-- Popup -->
		<article id="layer4" class="ly_pop12">
			<article class="sideinfo">
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
                 <#--
	                -->
	                </dd>
	                <dd class="btncover2 btn" id="selectContentsInst">
	                	<#--
	                	<span class="btn_pack gry">
	                	<@button '${search.menuId}' '${user.userId}' 'contentML.recreate' 'reCreate();' '' />
	                	</span>
	                	<span class="btn_pack gry">
	                	
	                	<a href="#" onclick="javascript:contentsCopy();">복사</a></span>
	                	
	                	
	                	<@button '${search.menuId}' '${user.userId}' 'common.contentsCopy' 'contentsCopy();' '' />
	                	
	                	</span> 
	                	<span class="btn_pack gry">
	                	<@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteContentsInst();' '' />
	                	</span>
	                	-->
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
							<tr><th>프로그램ID</th>
								<td id="pgmId">
								
								</td>
							</tr>
								<tr><th>제목</th><td id="pgmNm"></td></tr>
								<tr><th>콘텐츠명</th><td></td></tr>
						</tbody>
					</table>
				</dd>
			</dl>
			
			
			<dl>
				<dt>
					프로파일
					
					<table align="right">
						<tr>	
							<td class="vam"><span class="btn_pack gry"><a href="#" onclick="allProflRe();">프로파일 전체 재요청</a></span>&nbsp;&nbsp;&nbsp;</td>
						</tr>
						
					</table>
					
				</dt>
				<dd>
					<table summary="">
						
						<tbody>
							<tr height="5px"></tr>
						</tbody>
					</table>
				</dd>
				<dd>
					<table summary="" id="boardview4">
						<colgroup><col width="247px"></col><col width="40px"></col></colgroup>
						<tbody id="listProfile">
							<tr><th>파일명 / 프로파일명</th><th>상태</th></tr>
						</tbody>
					</table>
				</dd>
			</dl>
			</article>
		</div>
<!-- //Popup -->   
   
<!-- Table -->                             
        <article class="bbsview3">  
        <table summary="" id="boardview3">
         <colgroup><col width="20px"></col><col width="60px"></col><col width="160px"></col><col width="70px"></col><col width="60px"></col><col width="60px"></col><col width="60px"></col><col width="60px"></col><col width="325px"></col></colgroup>
            <thead>
            <tr><th colspan="9">
            	<article class="box2">
                    
	                    <dl>
	                        <dt>채널</dt>
	                        <dd>
	                        	<select name="channelCode">
		                        	<option value="0"  <#if search.channelCode?default(0)?number==0>selected</#if>>&#160; 전체 </option>
		                        	<option value="11" <#if search.channelCode?default(0)?number==11>selected</#if>>&#160; 1TV </option>
		                        	<option value="12" <#if search.channelCode?default(0)?number==12>selected</#if>>&#160; 2TV </option>
		                        	<option value="21" <#if search.channelCode?default(0)?number==21>selected</#if>>&#160; 1라디오 </option>
		                        	<option value="22" <#if search.channelCode?default(0)?number==22>selected</#if>>&#160; 2라디오 </option>
		                        	<option value="23" <#if search.channelCode?default(0)?number==23>selected</#if>>&#160; 3라디오 </option>
		                        	<option value="24" <#if search.channelCode?default(0)?number==24>selected</#if>>&#160; 1FM </option>
		                        	<option value="25" <#if search.channelCode?default(0)?number==25>selected</#if>>&#160; 2FM </option>
		                        	<option value="26" <#if search.channelCode?default(0)?number==26>selected</#if>>&#160; 한민족 </option>
		                        	<option value="61" <#if search.channelCode?default(0)?number==61>selected</#if>>&#160; DMB </option>
		                        </select>
	                        </dd>
	                        <dt>방송일자</dt>
	                        <dd>
	                        <input type="text" style="cursor:pointer;"  id="calInput1" name="startDt"  value="<#if search.startDt?exists>${search.startDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>&#126;
	                        <input type="text" style="cursor:pointer;" id="calInput2"  name="endDt"  value="<#if search.endDt?exists>${search.endDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>
	                        </dd>
	                        <dt>프로그램명</dt>
	                        <dd><input type="text" name="keyword" value="<#if search.keyword?exists>${search.keyword}</#if>" onKeyDown="keyEnter();"/></dd>
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
            <tr><th></th><th>채널</th><th>프로그램명</th><th>유형</th><th>콘텐츠구분</th><th>방송일</th><th>등록일</th><th>등록시각</th><th></th></tr>
			<#assign x = 20> 
			<#assign y = x>     
	            <#list contents.items as content>    
	            	<#if y%2==0>
	            		<tr class="gry">
		            <#else>
		            	<tr>
		            </#if>        
			            <td><input name="ctIds" type="checkbox" value="${content.ctId!""}"></td>          
			            <td>${ tpl.getCodeDesc("CHAN", content.channelCode!"")}[${ tpl.getCodeDesc("DOAD", content.localCode!"")}]</td>
			            
						<#assign size=content.pgmNm?length>
						<#assign regrid=content.regrid!"">
						
			         	<#if size <= 12>
			         	<td align="left" title="${content.ctId!""}">
			            	&nbsp;<div class="icon">
			             	<img src='<@spring.url "/images/icon_"+regrid+".gif"/>'/>		
			            	&nbsp;<a href="javascript:void(0)" onclick="getContentInfo('${content.ctId!""}');Player.controls.stop();">${content.pgmNm!""}</a></div>
			            </td>
			            <#else>
			            <td align="left" title="${content.ctId!""}">
			            	&nbsp;<div class="icon">
			          	<img src='<@spring.url "/images/icon_"+regrid+".gif"/>'/>		            
			            	<a href="javascript:void(0)" onclick="getContentInfo('${content.ctId!""}');Player.controls.stop();">${content.pgmNm?substring(0,12)+" ..."}</a></div>
			            </td>
						</#if>
			            <td>${ tpl.getCodeDesc("CTYP", content.ctTyp!"")}</td>
			            <td>${ tpl.getCodeDesc("CCLA", content.ctCla!"")}</td>
						<#-- 
			            -->
			            <td><#if content.brdDd?exists>${content.brdDd?string("yy/MM/dd")}</#if></td> 
			            <td><#if content.regDt?exists>${content.regDt?string("yy/MM/dd")}</#if></td>
						<td><#if content.regDt?exists>${content.regDt?string("HH:mm:ss")}</#if></td>
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
	<#-- 
		<article class="btncover4">
		<span class="btn_pack gry">
		<#-- 
		<a href="#" onclick="deleteContents();">삭제</a>
		
		 <@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteContents();' '' />
		</span>
		</article>
	-->	
	<!-- paginate -->
	    <@paging contents search.pageNo '' />
	<!-- //paginate -->

	</section>
</form>	
<form name="nProfl" id="nProfl" method="post">
	<input type="hidden" name="pgmId" id="pgmId" value="" />
	<input type="hidden" name="downFlNm" id="downFlNm" value="" />
	<input type="hidden" name="proFlid" id="proFlid" value="" />
	<input type="hidden" name="gubun" id="gubun" value="" />
	<input type="hidden" name="ctId" id="ctId" value="" />
	<input type="hidden" name="nctiId" id="nctiId" value="" />
	<input type="hidden" name="fPath" id="fPath" value="" />
	<input type="hidden" name="orgNm" id="orgNm" value="" />
	<input type="hidden" name="flNameRule" id="flNameRule" value="" />
	<input type="hidden" name="proFlnm" id="proFlnm" value="" />
	<input type="hidden" name="ext" id="ext" value="" />
	<input type="hidden" name="nctTyp" id="nctTyp" value="" />
	<input type="hidden" name="npgmCd" id="npgmCd" value="" />
	
</form>


<form name="reContentML" id="reContentML" method="post">
	<input type="hidden" name="re_ctiId" id="re_ctiId" value="" />
	<input type="hidden" name="re_downFileNm" id="re_downFileNm" value="" />
</form>


