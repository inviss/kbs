<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script type="text/javascript">

	function checkupload(){
		if(document.fileUpload.pgmId.value==""){
		  alert("콘텐츠 선택이 필요합니다");
		  //document.fileUpload.pgmId.focus();
		  return false;
		}
		if(document.fileUpload.ctNm.value==""){
		  alert("콘텐츠명을 입력하세요");
		  document.fileUpload.ctNm.focus();
		  return false;
		}
		if(document.fileUpload.ctCla.value==""){
		  alert("콘텐츠 구분을 선택하세요");
		  document.fileUpload.ctCla.focus();
		  return false;
		}
		if(document.fileUpload.ctTyp.value==""){
		  alert("콘텐츠 타입을 선택하세요");
		  document.fileUpload.ctTyp.focus();
		  return false;
		}
		if(document.fileUpload.segmentId.value==""){
		  alert("세그먼트ID을 선택하세요");
		  document.fileUpload.segmentId.focus();
		  return false;
		}
		return true;
	}
	
	function upload()
	{
		if(checkupload()){
		
			var tmpPgmId = document.getElementById('pgmId').value
			var pgmCd = document.getElementById('pgmCd').value
			
			var SubDir = pgmCd;
			var SubDir1= tmpPgmId;
			
			// 첨부 파일이 있으면 컴포넌트로 파일전송
			if (InnoDS.GetFileCount() > 0)
			{
				// 서버에 저장될 서브 디렉토리 지정
				InnoDS.AddPostData("_SUB_DIR", SubDir);
				InnoDS.AddPostData("_SUB_DIR1", SubDir1);
				
				// 업로드 시작
				InnoDS.StartUpload();
			}
			else // 첨부 파일이 없으면 폼만 전송
			{
				//InnoDSSubmit(document.fileUpload);
				alert("첨부파일을 넣어주세요.");
			}
		}
	}
	
	// OnUploadComplete는 파일 업로드 완료 후 발생하는 이벤트 입니다.
	// 파일 업로드가 완료되면 fileUpload 폼 데이타와 업로드된 파일 정보를 함께 전송 합니다.
	function OnUploadComplete(objName)
	{
		InnoDSSubmit(document.fileUpload);
		goUpload();
	}

	//서버오류, 회선 불안정 등으로 업로드를 실패 했을 때 발생되는 이벤트 입니다.
	function OnUploadError(objName)
	{
		OnUploadResume(objName);
	}

	// 업로드 중 사용자가 [x]를 클릭하여 전송을 중지했을 때 발생되는 이벤트 입니다.
	function OnUploadCancel(objName)
	{
		OnUploadResume(objName);	
	}
	
	function OnUploadResume(objName)
	{
		var UploadErrorResumeExists = false;
		var tStr = '';
			tStr += '<div style="background-color:#e0e4e8; width:490px; font-size:9pt">';
			tStr += '<center><font color="#224985">전송이 완료되지 않았습니다. <font color="#333333">이어올리기가 가능합니다.</font></center><br />';
			tStr += '<article class="bbsview3"><table summary="" id="boardview3">';
			tStr += '<colgroup><col width="200px"></col><col width="120px"></col><col width="120px"><col width="80px"></col></colgroup>';
			tStr += '<tbody id="viewData">';
			tStr += '<tr><th>파일명</th><th>원본용량</th><th>업로드용량</th><th>상태</th></tr>';
			

		var zStatus = '';
		var zSentBytes = '';
		
		var nUploadFileCount = InnoDS.UploadFileCount;
		for(var i = 0 ; i < nUploadFileCount ; i++)
		{
			var FullPath = InnoDS.GetUploadFullPath(i);
			var Folder = InnoDS.GetUploadFolder(i);
			var FileName = InnoDS.GetUploadFileName(i);
			var FileSize = InnoDS.GetUploadFileSize(i);
			var SentBytes = InnoDS.GetUploadBytes(i);
			var Status = InnoDS.GetUploadBytes(i);
			var IsTemp = InnoDS.IsTempFile(i);
			
			if (IsTemp)
			{
				continue;
			}		

			if (SentBytes == 0)
			{
				zStatus = '대기';
			} 
			else if (SentBytes >= parseInt(FileSize)) 
			{
				zStatus = '<font color=blue>완료</font>';
			} 
			else if (SentBytes > 0 && SentBytes < parseInt(FileSize)) 
			{
				UploadErrorResumeExists = true;
				zStatus = '<font color=red>전송중</font>';
			} 
			else 
			{
				zStatus = '-';
			}
			
			if(Folder != " ")
			{
				Folder += "\\";
			}

			tStr += '<tr>';
			tStr += '	<td align="left">' + Folder + FileName + '</td>';
			tStr += '	<td align="right">' + fileSizeExp((FileSize/1024/1024), 2) + 'MB</td>';
			tStr += '	<td align="right">' + fileSizeExp((SentBytes/1024/1024), 2) + 'MB</td>';
			tStr += '	<td align="center">' + zStatus +'</td>';
			tStr += '</tr>';
		}
			tStr += '</tbody></table></article>';
			tStr += '<article class="btncover4"><span class="btn_pack gry">';
			tStr += '<a href="javascript:" onClick="InnoDS.StartUpload();">이어올리기</a></span></article>';
			
			tStr += '</div>';
		
		if (UploadErrorResumeExists)
		{
			InnoDS.SetResume = true;
			var zObj = document.getElementById("UploadErrorDiv");
			zObj.innerHTML = tStr;
			zObj.style.display = 'block';

			var zObj2 = document.getElementById("UploadComponent");
			zObj2.style.display= 'none';
		}
	}
	
	// 파일 사이즈 표현 함수
	function fileSizeExp(val, precision)
	{
		var p = Math.pow(10, precision);
		return Math.round(val * p) / p;
	}
</script>
<script Language="JavaScript">
	// 검색 
	function goUpload(){ 
		alert("2");
		document.fileUpload.action="<@spring.url '/content/fileUpload.ssc'/>";
		document.fileUpload.submit();
	}
	
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
		//	goUpload(fileUpload);    //upload 함수 호출
		}
	}
		
function goSchedule(){
	window.open("/popup/Schedule.ssc","goSchedule","width=970,height=700,scrollbars=yes");
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
function programSearch(){
	//alert(live.popupGB.value);
	
	window.open("/popup/ProgramSearch.ssc","programSearch","width=970,height=820,scrollbars=yes");
}
function getScode(){
	//alert(document.fileUpload.pgmNm2.value);

	var nm=document.fileUpload.pgmNm2.value;
	$jq.ajax({
		url: "<@spring.url '/popup/getScode.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#fileUpload').serialize(),
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
</script>
<section id="container">
    <section id="content">
    	<h3 class="blind">웹등록</h3>
    	<@spring.bind "search" />
		<article class="title"><img src="<@spring.url '/images/title_webreg.gif'/>" title="웹등록"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 콘텐츠 관리 &gt; <span class="now">웹등록</span></nav>
        <article class="tabmenu">
            <ul class="tab1">
            <li class="off"><a href="<@spring.url '/content/ContentSearch.ssc'/>"><span>서비스콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/StandbyContent.ssc'/>"><span>대기콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/RMSContent.ssc'/>"><span>보관콘텐츠</span></a></li>
            <li class="off"><a href="<@spring.url '/content/ManualReg.ssc'/>"><span>수동등록</span></a></li>
            <li class="on"><a href="#"><span>웹등록</span></a></li>
            </ul>
        </article>
<!-- Table -->
        <article class="bbsview3">
        <form id="fileUpload" name="fileUpload" method="post" enctype="multipart/form-data">
      	<@spring.bind "search" />
      	<input type="hidden" name="menuId" value="${search.menuId}">
	        <table summary="" class="boardview5">
	        <colgroup><col width="150px"></col><col width=""></col></colgroup>
	<!-- list -->
            <tbody>
				<tr><th>콘텐츠 명</th><td><input type="text" name="ctNm" value="" ></input></td></tr>
				
	            <tr><th>프로그램ID</th><td>
	            <article class="box2">
		            <ul>
		                <li>
		                <input type="text" name="pgmId"  id="pgmId" value="" readonly="readonly"></input></li>
		               
		                <input type="hidden" name="edtrid" id="edtrid" value="${user.userId}">
		             
		                
		                <input type="hidden" id="pgmGrpCd" name="pgmGrpCd" value="">
	                	<input type="hidden" name="pgmCd" id="pgmCd" value="">
		                <input type="hidden" name="pgmNm" value="">
		                <!--<input type="hidden" name="ctNm" id="ctNm" value="">-->
		                <input type="hidden" name="menuId" id="menuId" value="${search.menuId}">
		                <input type="hidden" id="channelCode" name="channelCode" value="">
						<input type="hidden" id="brdDd" name="brdDd" value="">
						<input type="hidden" id="pid" name="pid" value="">
						<input type="hidden" id="pgmNm2" name="pgmNm2" value="">
						<input type="hidden" id="popup" name="popup" value="">
						<input type="hidden" id="_innods_filename" name="_innods_filename" value="">
						<input type="hidden" id="_innods_filesize" name="_innods_filesize" value="">
						<input type="hidden" id="_innods_sendbytes" name="_innods_sendbytes" value="">
						<input type="hidden" id="_innods_status" name="_innods_status" value="">
						
		            </ul>
		            <article class="btncover9">
		            	<span class="btns"><a href="javascript:programSearch();" class="pg" title="프로그램정보조회">프로그램정보조회</a></span>
            			<span class="btns"><a href="javascript:goSchedule();" class="srch" title="검색">검색</a></span>
		            </article>
		            </article>
		            </td></tr>
		            <tr><th>프로그램명</th><td><input type="text" id="pgmNm" value="" readonly/></td></tr>
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
		            <tr><th>트리밍 스킵여부</th>
		            <td><input type="radio" name="trimmSte" value="P" class="radio" checked="checked">트리밍과정 필요없음.<input type="radio" name="trimmSte" value="N" class="radio">트리밍과정 필요.</td></tr>
		            <tr id="sid"><th>세그먼트ID</th><td><select name="segmentId"><option value="99">&#160; 선택 &#160;<option></select></td></tr>
		            <tr>
		            <th>파일업로드</th>
		            <td>
		            <div id="UploadComponent" style="background-color:#e0e4e8; margin-left:15px; display:block">
						
						<br />
						<div id="Upload" style="border: 1px solid #c0c0c0; width:500px">
						<script type="text/javascript" src="<@spring.url '/js/InnoDS.js'/>"></script>
						<script type="text/javascript">
							
						// 라이선스 유형 : InnoDS 29일 체험 (만료 2012-03-01)
						// http://211.233.93.8
						// http://210.115.198.101
						// http://210.115.198.102
						// http://210.115.198.103
						// http://10.30.13.146
						// http://10.30.13.150
						// http://10.30.13.130
						
						
						var Enc = "jOeBEg+TOgVdPwtMqyD+fxqvVNLGvxDxlIeUpZi4fg9Ivn/QiWx+33durmH7YhYFLLp/hjuuLxTIAUTN0elNtftdKoZCiPJNC4x35d2HTUBl6ciHtQLABr3nkc6xQssDLAeNkXe0mHe747jHJMx4nVveDiaubLRrNWOm2EZE1bDUnHUq9hA5iBUTe8pNjne+NDSyj2AI0nFIaHvKTY53vjQ0r2eLN5LWJxn+CR4tgXAlEAXhELkPqu76Fn8m8hg8vtiJeQ==";

						//로컬용 라이센스키 http://localhost/				
						//var Enc = "np+WnTjOHi2snqg6IFEe6VPdO4Vv5bB0u2bh7QH5Ccyyle5QaMFmCD6GDx/XXkhvGz/6z6sS2LXv5BnwuKKnbgvhmOSFPzzdODbYEhNac2wNWWd/";
							
							var InputType = "fixed";
							var UploadURL = "<@spring.url '/jsp/action_ds.jsp'/>";
						
							var LimitExt = "mxf;mov";
							var ExtPermission = "true"; 

							InnoDSInit( -1 , -1 , 1 , 500 , 150 );
						</script>
						</div>
						<br />
					</div>
					<div id="UploadErrorDiv" style="background-color:#e0e4e8 ;argin-left:15px; display:none"></div>
		            </td>
		            </tr>
	            </td></tr>
            </tbody>
	<!-- //list -->
		</form>
	    </table>     
    </article>
<!-- //Table -->
	<article class="btncover4">
		<span class="btn_pack gry">
		<#-- 
		<a href="javascript:void(0)" onclick="goUpload();">콘텐츠 등록</a>
		-->
		<@button '${search.menuId}' '${user.userId}' 'common.resister' 'upload();' '' />
		<#--
		<@button '${search.menuId}' '${user.userId}' 'common.resister' 'goUpload();' '' />
		 -->
		</span>
	</article>
	</section>
	