<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">
function saveCheck(){
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
	
	return true;
}

function traCheck(){
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
		//document.ContentsSearch.pgmId.value="";
		document.ContentsSearch.action="<@spring.url '/content/StandbyContent.ssc'/>";
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
	document.ContentsSearch.action="<@spring.url '/content/StandbyContent.ssc'/>";
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
		},
		success: function(data){
			alert('정상적으로 콘텐츠 삭제 진행 하였습니다.!');
				document.ContentsSearch.action="<@spring.url '/content/StandbyContent.ssc'/>";
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
	  	return (value === undefined || value == null || value.length <= 0 || value <= 0) ? 0 : value;
	  }else{
	  	return (value === undefined || value == null || value.length <= 0) ? "" : value;
	  } 
 }
 
function isProgress(value){
	  	return (value === undefined || value == null || value.length <= 0 || value == '-1') ? 0 : value;
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
	document.ContentsSearch.filePath.value="";
	document.ContentsSearch.pgmId.value="";
	var ctiId = document.getElementById('ctiId').value
	
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
					 	 table +='<tr><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / <br>'+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></td><td class="vam"><span class="btns"><a href="#" class="request" title="재요청">요청</a></span></td><td class="vam"><span class="btns"><a href="javascript:transRequest('+data.contentsInstTbls[i].ctiId+')" class="request" title="재전송">요청</a></span></td></tr>'
					  }
					$jq('#listProfile').append(table);
					document.ContentsSearch.ctiId.value = "";
				}
			});
			
		}
}

function contentMetaInitialize() {
	$jq('#viewContent').empty();
	var table = "";
		table +='<tr><th>프로그램코드</th><td></td></tr>'
		table +='<tr><th>그룹코드</th><td></td></tr>'
		table +='<tr><th>프로그램ID</th><td></td></tr>'
		table +='<tr><th>제목</th><td id="pgmNm"></td></tr>'
		table +='<tr><th>콘텐츠(코너)명</th><td></td></tr>'
		table +='<tr><th>채널</th><td></td></tr>'
		table +='<tr><th>콘텐츠구분</th><td></td></tr>'
		table +='<tr><th>콘텐츠유형</th><td></td></tr>'
		table +='<tr><th>등록일</th><td></td></tr>'
		table +='<tr><th>방영일자</th><td></td></tr>'
		table +='<tr><th>출처</th><td></td></tr>'
		table +='<tr><th>수정순번</th><td></td></tr>'
		table +='<tr id="sid"><th>세그먼트ID</th><td></td></tr>'
		$jq('#viewContent').append(table);
}

function getContentInfo(ctId){
	document.ContentsSearch.ctId.value=ctId;
	
	contentMetaInitialize();
	
	var obj = ($jq('#ContentsSearch').get(0));
	
	$jq.ajax({
		url: '<@spring.url "/content/ajaxFindContentInfoSB.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
		},
		success: function(data){
			$jq('#viewContent').empty();
			  var table="";
			  table +='<tr width="75px"><th>프로그램코드</th><td width="145px">'+isEmpty(data.contentsTbl.pgmCd)+'</td></tr>'
			  table +='<tr><th>그룹코드</th><td>'+isEmpty(data.contentsTbl.pgmGrpCd)+'</td></tr>'
			  table +='<tr><th>프로그램ID</th><td>'+isEmpty(data.contentsTbl.pgmId)+'</td></tr>'
			  <#-- 
			  table +='<td><input type="text" name="pgmId" id="pgmId" class="ip_text" value=\''+isEmpty(data.contentsTbl.pgmId)+'\' readonly="readonly" />'
			  table +='<span class="btns"><a href="javascript:saveContentInfo();" class="save" title="저장">저장</a></span>'
			  table +='<span class="btns">'
			  table +=<@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'saveContentInfo();' 'save' /> 
			  table +='</span>'
			  table +='<span class="btns"><a href="javascript:goSchedule();" class="srch" title="검색">검색</a></span>'
  			  table +='</td></tr>'
			  -->
			  table +='<tr><th>제목</th><td id="pgmNm">'+isEmpty(data.contentsTbl.pgmNm)+'</td></tr>'
			  table +='<tr><th>콘텐츠(코너)명</th><td>'+isEmpty(data.contentsTbl.ctNm)+'</td></tr>'
			  table +='<tr><th>채널</th><td>'+isEmpty(data.codeTbl.clfNm)+'</td></tr>' 
	          table +='<tr><th>콘텐츠구분</th>'
			  table +='<td><select name="ctCla" style="width:130px;">'
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
			  table +='<td><select name="ctTyp" style="width:130px;">'
			  table +='<option value="" >&#160; 선택 &#160;</option>'
			  for(var i =0 ; i< data.typs.length ; i++){
			  	if(data.typs[i].sclCd == isEmpty(data.codeTbl3.sclCd)){
			  		table += '<option value="'+data.typs[i].sclCd+'" selected>'+data.typs[i].clfNm+'</option>'
			  	}else{
			  		table += '<option value="'+data.typs[i].sclCd+'">'+data.typs[i].clfNm+'</option>'
			  	}
			  }
			  table +='</select></td></tr>'
	          table +='<tr><th>등록일</th><td>'+isEmpty(data.regDate)+'</td></tr>' 
	          table +='<tr><th>방영일자</th><td>'+isEmpty(data.brdDate)+'</td></tr>' 
	          table +='<tr><th>출처</th><td>'+isEmpty(data.contentsTbl.regrid)+'</td></tr>'
	          table +='<tr><th>수정순번</th><td>'+isEmpty(data.contentsTbl.pgmNum)+'</td></tr>' 
	          table += '<tr><th>세그먼트ID</th><td>'
			  table += '<select name="segmentId" id="segmentId" style="width:130px;">'  
			  table += '<option value=\'\'>&#160; 선택&#160</option>'
			  
				if(data.scodes.length == 0){
					table += '<option value=\'S000\'>&#160;'+isEmpty(data.contentsTbl.pgmNm)+'&#160(S000)</option>'
				}else{
					table += '<option value=\'S000\'>&#160;'+isEmpty(data.contentsTbl.pgmNm)+'&#160(S000)</option>'
					for(var iq=0;iq<data.scodes.length;iq++){	
						if(data.scodes[iq].segmentId == isEmpty(data.contentsTbl.segmentId)){
						  	table += '<option value="'+data.scodes[iq].segmentId+'" selected>&#160;'+data.scodes[iq].segmentNm+'&#160('+data.scodes[iq].segmentId+')</option>'
						  }else{
						  	table += '<option value="'+data.scodes[iq].segmentId+'" >&#160;'+data.scodes[iq].segmentNm+'&#160('+data.scodes[iq].segmentId+')</option>'
						  }
					}
				}	
			  
	 		  table += '</select>'
	 			
	 	      table += '</td>'
	          table += '</tr>'  
			$jq('#viewContent').append(table);
			
			document.ContentsSearch.trimmSte.value=''+isEmpty(data.contentsTbl.trimmSte)+'';
			document.ContentsSearch.pgmCd.value=''+isEmpty(data.contentsTbl.pgmCd)+'';
			document.ContentsSearch.ctiId.value=''+isEmpty(data.contentsInstTbls.ctiId)+'';
		}
	});
}

<#--
function transRequest(ctId){
	document.ContentsSearch.ctId.value=ctId;
	
	$jq('#Player').attr("URL","");
			
	var obj = ($jq('#ContentsSearch').get(0));
	$jq.ajax({
		url: '<@spring.url "/content/ajaxTransRequest.ssc" />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#ContentsSearch').serialize(),
		error: function(){
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
-->

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

<#--
function viewMediaPlayer(filePath,ctId,ctiId){
	ContentsSearch.filePath.value=filePath;
	ContentsSearch.ctId.value=ctId;
	ContentsSearch.ctiId.value=ctiId;
		
	$jq('#filePath').attr("filePath",filePath);
	$jq('#ctId').attr("ctId",ctId);
	$jq('#ctiId').attr("ctiId",ctiId);	
	$jq('#selectContentsInst').empty();
	var table ="";

	table += '<span class="btn_pack gry">'
	table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.download' 'callDownload();' '' />
	table += '</span>&nbsp;'
	table += '<span class="btn_pack gry">'
	table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.delete' 'deleteContentsInst();' '' />
	table += '</span>'
	
	$jq('#selectContentsInst').append(table);
	
	$jq('#Player').attr("URL",<@spring.url '/'/>+filePath);
	
}
-->

function webRegPopup(){
	window.open("<@spring.url '/popup/WebRegPopup.ssc'/>","WebRegPopup","width=830,height=570,scrollbars=yes");
}

function save(){
	var ctId = document.getElementById('ctId').value;
	//var brdDd = document.getElementById('brdDd').value;

	if(ctId === undefined || ctId == null || ctId <= 0){
			alert("프로그램이 선택되지 않았습니다!");			
	}else{
		if(saveCheck()){
			$jq.ajax({
				url: "<@spring.url '/content/updateSbContent.ssc'/>",
				type: 'POST',
				dataType: 'json',
				data: $jq('#ContentsSearch').serialize(),
				error: function(){
					alert('Return Error.');
				},
				success: function(data){
				 	document.ContentsSearch.action="<@spring.url '/content/StandbyContent.ssc'/>";
					document.ContentsSearch.submit();
				}
			});
		}
	}
}

function insertTransReq(){
	var ctId = document.getElementById('ctId').value;
	document.ContentsSearch.trimmSte.value = "P";

	if(ctId === undefined || ctId == null || ctId <= 0){
			alert("프로그램이 선택되지 않았습니다!.");			
	}else{
		if(traCheck()){
			$jq.ajax({
				url: "<@spring.url '/content/updateSbContent.ssc'/>",
				type: 'POST',
				dataType: 'json',
				data: $jq('#ContentsSearch').serialize(),
				error: function(){
					alert('세그먼트 저장 오류');
				},
				success: function(data){
				 	$jq.ajax({
						url: "<@spring.url '/content/insertTrancoder.ssc'/>",
						type: 'POST',
						dataType: 'json',
						data: $jq('#ContentsSearch').serialize(),
						error: function(){
							alert('트랜스코딩 요청 오류');
						},
						success: function(data){
							alert("트랜스코딩이 요청되었습니다.");
						 	document.ContentsSearch.action="<@spring.url '/content/StandbyContent.ssc'/>";
							document.ContentsSearch.submit();
						}
					});
				}
			});
		}
	}
}


window.onload=function(){
	cal1 = new dhtmlxCalendarObject('calInput1', false, {isYearEditable: true,isMonthEditable: true});
	cal1.setYearsRange(1973, 2020);
	cal1.draw();
	
	cal2 = new dhtmlxCalendarObject('calInput2', false, {isYearEditable: true,isMonthEditable: true});
	cal2.setYearsRange(1973, 2020);
	cal2.draw();

	ajaxData(); 
}

function ajaxData(){
	if(document.ContentsSearch.ctIdPrgrs.value != ""){
		$jq.ajax({
			url: "<@spring.url '/content/ajaxStandbyContent.ssc' />",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ContentsSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	for(var i=0;i<data.contents.length;i++){
			 		$jq('#'+data.contents[i].ctId+'_prgs').empty();
				 		var table ='';
				 		table +='<article class="ly_loading2"><p class="dsc_loading_no"><span class="progress">'
				 		table +='<em>'+isEmpty(data.contents[i].prgrs)+'</em>%</span></p>'
				 		table +='<article class="loading_bar">'
				 		table +='<span style="width:'+isProgress(data.contents[i].prgrs)+'%;"></span>'
				 		table +='</article>'
			 		$jq('#'+data.contents[i].ctId+'_prgs').append(table);
			 	}
			}
		});	
	}
	refreshTime();
}

function refreshTime(){
	window.setTimeout("ajaxData()", 1000 * 5 );
}
</script>
<section id="container">
	<form name="ContentsSearch" id="ContentsSearch" method="post">
		<@spring.bind "search" />
      	<input type="hidden" name="menuId" value="${search.menuId}">
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="ctiId" id="ctiId" value="" />
		<input type="hidden" name="downFile" value="" />
		<input type="hidden" name="pgmNm"  value="" />
		<input type="hidden" name="pgmCd" id="pgmCd" value=""  />
		<input type="hidden" name="pgmGrpCd"  value="" />
		<input type="hidden" name="trimmSte" value="">
		<input type="hidden" name="ctId" id="ctId" value="" />
		<input type="hidden" name="filePath" id="filePath" value="" />
		<input type="hidden" name="ctIdPrgrs" id="ctIdPrgrs" value="" />
		
    <section id="content">
    	<h3 class="blind">콘텐츠조회</h3>    
		<article class="title"><img src="<@spring.url '/images/title_contents.gif'/>" title="콘텐츠"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 콘텐츠 관리 &gt; <span class="now">대기콘텐츠</span></nav>
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
            <li class="on"><a href="#"><span>대기콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/RMSContent.ssc'/>"><span>보관콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/ManualReg.ssc'/>"><span>수동등록</span></a></li>
            <!--<li class="off"><a href="/content/WebReg.ssc"><span>웹등록</span></a></li>-->
            </ul>
        </article>
        
<!-- Popup -->
		<article id="layer4" class="ly_pop12" style="padding-left: 80px;">
		
			<article class="sideinfo">
			<dl>
				<dt>콘텐츠 정보</dt>
				<dd>
					<table summary="" id="boardview4">
						<colgroup><col width="75px"></col><col width="150px"></col></colgroup>
						<tbody id="viewContent">
							<tr><th>프로그램코드</th><td></td></tr>
							<tr><th>그룹코드</th><td></td></tr>
							<tr><th>프로그램ID</th><td></td></tr>
							<tr><th>제목</th><td id="pgmNm"></td></tr>
							<tr><th>콘텐츠(코너)명</th><td></td></tr>
							<tr><th>채널</th><td></td></tr>
							<tr><th>콘텐츠구분</th><td></td></tr>
							<tr><th>콘텐츠유형</th><td></td></tr>
							<tr><th>등록일</th><td></td></tr>
							<tr><th>방영일자</th><td></td></tr>
							<tr><th>출처</th><td></td></tr>
							<tr><th>수정순번</th><td></td></tr>
							<tr id="sid"><th>세그먼트ID</th><td></td></tr>
						</tbody>
					</table>
				</dd>
				<dd class="btncover11" style="padding-right: 70px;">
	                	<span class="btn_pack gry">
	                	<@button '${search.menuId}' '${user.userId}' 'transcode.request' 'insertTransReq()' '' />
	                	</span>
	                	&nbsp;&nbsp;
	                	<span class="btn_pack gry">
	                	<@button '${search.menuId}' '${user.userId}' 'common.save' 'save(this)' '' />
	                	</span>
	                	&nbsp;&nbsp;
	                </dd>
			</dl>
			
			</article>
		</article> 
<!-- //Popup -->   
   
<!-- Table -->                             
        <article class="bbsview3">  
        <table summary="" id="boardview3">
         <colgroup><col width="20px"></col><col width="60px"></col><col width="160px"></col><col width="70px"></col><col width="60px"></col><col width="60px"></col><col width="60px"></col><col width="80px"></col><col width=""></col></colgroup>
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
		                        	<option value="26" <#if search.channelCode?default(0)?number==26>selected</#if>>&#160; 한민족1 </option>
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
            <tr><th></th><th>채널</th><th>프로그램명</th><th>유형</th><th>방송일</th><th>등록일</th><th>등록시각</th><th>상태</th><th></th></tr>
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
			            
						<#if content.pgmNm?exists>
			            	<#assign size=content.pgmNm?length>
			            <#else>
			            	<#assign size=0>
			            </#if>
			            
						<#assign regrid=content.regrid!"">
					
			         	<#if size <= 12>
				        	<td align="left" title="${content.ctId!""}">
				        	&nbsp;<div class="icon">
				        	<img src='<@spring.url "/images/icon_"+regrid+".gif"/>'/>	
				           &nbsp;<a href="javascript:void(0)" onclick="getContentInfo('${content.ctId!""}');">${content.pgmNm!content.jobId}</a>
		            		</div>
				            </td>
			            <#else>
				            <td align="left" title="${content.ctId!""}">
				            	&nbsp;<div class="icon">
				            	<img src='<@spring.url "/images/icon_"+regrid+".gif"/>'/>
			            &nbsp;<a href="javascript:void(0)" onclick="getContentInfo('${content.ctId!""}');">${content.pgmNm?substring(0,11)+" ..."}</a>
				            </td>
						</#if>
			            <td>${ tpl.getCodeDesc("CTYP", content.ctTyp!"")}</td>
			            <td><#if content.brdDd?exists>${content.brdDd?string("yy/MM/dd")}</#if></td> 
			            <td><#if content.regDt?exists>${content.regDt?string("yy/MM/dd")}</#if></td>
						<td><#if content.regDt?exists>${content.regDt?string("HH:mm:ss")}</#if></td>
			            <td id="${content.ctId!""}_prgs">
			            	<article class="ly_loading2">
				            <p class="dsc_loading_no">
				            <span class="progress">
				            <em>${content.prgrs!"0"}</em>%</span>
				            </p>
				            
				            <#assign prgrs = content.prgrs!"0">
				            
				            <#if prgrs == "-1">
				            	<#assign prgrs="0">
				            </#if>
				            <article class="loading_bar">
				            	<span style="width:${prgrs}%;"></span>
				            </article>
				            
				        </article>
			            </td>
			            <#assign dataStatCd = content.dataStatCd!"0">
			            
			      
			           <!-- ADAS일 경우 상태Prgrs를 update하지 않는다 -->
				        <#if regrid != 'AACH'>
			            <#if dataStatCd?number < 200>			          
							<script>
								
			            		var ctIds= document.ContentsSearch.ctIdPrgrs.value;
			            		if(ctIds == ""){
			            			ctIds = ${content.ctId};
			            		}else{
			            			
			            			ctIds = ctIds +"," + ${content.ctId};
			            		}
			            		document.ContentsSearch.ctIdPrgrs.value=ctIds;
			            	</script>			            	
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
