<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
<script type="text/javascript">

	
	function upload()
	{
		
		
			//var tmpPgmId = document.getElementById('pgmId').value
			var pgmCd = document.getElementById('pcode').value
			
			var SubDir = pgmCd;
			//var SubDir1= tmpPgmId;
			
			//alert(SubDir);
			// 첨부 파일이 있으면 컴포넌트로 파일전송
			if (InnoDS.GetFileCount() > 0)
			{
				// 서버에 저장될 서브 디렉토리 지정
				InnoDS.AddPostData("_SUB_DIR", SubDir);
				//InnoDS.AddPostData("_SUB_DIR1", SubDir1);
				//alert("start upload");
				// 업로드 시작
				InnoDS.StartUpload();
			}
			else // 첨부 파일이 없으면 폼만 전송
			{
				//InnoDSSubmit(document.fileUpload);
				alert("첨부파일을 넣어주세요.");
			}
		
	}
	
	
	
	// OnUploadComplete는 파일 업로드 완료 후 발생하는 이벤트 입니다.
	// 파일 업로드가 완료되면 fileUpload 폼 데이타와 업로드된 파일 정보를 함께 전송 합니다
	function OnUploadComplete(objName)
	{
		 var oForm = document.fileUpload;
		 var origon_fname;
		 
		 for(var i=0; i < InnoDS.GetFileCount(); i++) {
		 	if(!InnoDS.IsTempFile(i)) {
		 		var strFileName = InnoDS.GetFileName(i);
		 		
		 		var arr = strFileName.replace(/\\/gi, "/").split("/");

		 		if(i==0) origon_fname = arr[arr.length-1];
		 		else origon_fname += ","+arr[arr.length-1];
		 	}
		 }
     	
     	document.fileUpload._origon_fname.value = origon_fname;

       InnoDSSubmit(document.fileUpload);
		
		goUpload();
	}

	//서버오류, 회선 불안정 등으로 업로드를 실패 했을 때 발생되는 이벤트 입니다.
	//function OnUploadError(objName)
	//{
	//	OnUploadResume(objName);
	//}

	// 업로드 중 사용자가 [x]를 클릭하여 전송을 중지했을 때 발생되는 이벤트 입니다.
	//function OnUploadCancel(objName)
	//{
	//	OnUploadResume(objName);	
	//}
	
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
			tStr += '<a href="javascript:void(0)" onClick="InnoDS.StartUpload();">이어올리기</a></span></article>';
			
			tStr += '</div>';
		
		if (UploadErrorResumeExists)
		{
			InnoDS.SetResume = true;
			var zObj = document.getElementById("UploadErrorDiv");
			zObj.innerHTML = tStr;
			zObj.style.display = 'block';

			var zObj2 = document.getElementById("UploadComponent");
			zObj2.style.display= 'none';
			
			var zObj3 = document.getElementById("UploadBtn");
			zObj3.style.display= 'none';
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
	//	document.fileUpload.action="<@spring.url '/popup/mfileUpload.ssc'/>";
	//	document.fileUpload.submit();
	//	windows.close();
		
		$jq.ajax({
		url: "<@spring.url '/popup/mfileUpload.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#fileUpload').serialize(),
		error: function(){
			alert('Return Error.');
		},
		success: function(data){
		 		window.close();
	 			//windows.self.close();
			}
			
		});
	
		
	}
	
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
		//	goUpload(fileUpload);    //upload 함수 호출
		}
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


<script type="text/javascript" src="<@spring.url '/js/calendar.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/script.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/jquery.js' />"></script>
<script type="text/javascript" src="<@spring.url '/js/date.js' />"></script>
<script type=text/javascript>	 
	var $jq = jQuery.noConflict();
	var baseUrl = "<@spring.url '/'/>";
</script>
<script type="text/javascript" src="<@spring.url '/js/jquery.form.js' />"></script>
</head>
<body>

<!-- Container -->

    	
    	<@spring.bind "search" />
		
       
<!-- Table -->
        <article class="">
        <form id="fileUpload" name="fileUpload" method="post" enctype="multipart/form-data">
      	<@spring.bind "search" />
      	<input type="hidden" name="menuId" value="${search.menuId}">
      	<input type="hidden" name="_origon_fname" value="">
	        <table summary="" class="" id="">
	        <colgroup><col width=""></col></colgroup>
	<!-- list -->
            <tbody>
	            
		            <ul>
		                <input type="hidden" name="edtrid" id="edtrid" value="metahub">
		                <input type="hidden" id="pgmGrpCd" name="pgmGrpCd" value="">
	                	<input type="hidden" name="pgmCd" id="pgmCd" value="">
		                <input type="hidden" name="pgmNm" value="">
		                <!--<input type="hidden" name="ctNm" id="ctNm" value="">-->
		                <input type="hidden" name="menuId" id="menuId" value="${search.menuId}">
		                <input type="hidden" id="channelCode" name="channelCode" value="">
						<input type="hidden" id="brdDd" name="brdDd" value="">
						<input type="hidden" id="pid" name="pid" value="">
						<input type="hidden" id="pgmNm2" name="pgmNm2" value="">
						<input type="hidden" id="popup" name="popup" value="Y">
						<input type="hidden" id="_innods_filename" name="_innods_filename" value="">
						<input type="hidden" id="_innods_filesize" name="_innods_filesize" value="">
						<input type="hidden" id="_innods_sendbytes" name="_innods_sendbytes" value="">
						<input type="hidden" id="_innods_status" name="_innods_status" value="">
						<input type="hidden" name="pcode" id="pcode" value="${pcode}">
						<input type="hidden" name="nid" id="nid" value="${nid}">
						<input type="hidden" name="avGubun" id="avGubun" value="${avGubun}">
						<input type="hidden" name="cttyp" id="cttyp" value="${cttyp!""}">
		            </ul>

		            <td align="left">
		            <div id="UploadComponent" style="background-color:#e0e4e8; margin-left:15px; display:block">
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
							//var Enc = "e10DNIRjPpBKaeDfc30NqxvifCmyF6Zo0J+zR72ri5sRWTQUTj5M1lqTR2oMHOu8Aw8tT3XbNdVzlgxjStSxuX69dtkYJAz+v8US85jshXJAOKMVDJwgqO7AP5M=";
								
							var InputType = "fixed";
							var UploadURL = "<@spring.url '/jsp/action_ds.jsp'/>";

							
							<#assign gb= avGubun>
							<#if gb = "V">
								var LimitExt = "mpg;mpeg;avi;mov;mp4;wmv;asf;mts;m2t";
								var ExtPermission = "true"; 
								InnoDSInit( '30GB' , '-1' , 100 , 500 , 250 );
								function OnLoadComplete() {
									InnoDS.AppendFilter("영상파일", "*.mpg; *.mpeg; *.avi; *.mov; *.mp4; *.wmv; *.asf; *.mts; *.m2t;");
								}
	
							<#else>
								var LimitExt = "wma;wav;mp3;ogg;zip";
								var ExtPermission = "true";
								InnoDSInit( '30GB' , '-1' , 100 , 500 , 250 );
								function OnLoadComplete() {
									InnoDS.AppendFilter("오디오파일", "*.wma; *.wav; *.mp3; *.ogg; *.zip;");
								}
							</#if>	
							

						</script>
						</div>
					</div>
					<div id="UploadErrorDiv" style="background-color:#e0e4e8 ;argin-left:15px; display:none"></div>
		            </td>
		            <!-- </tr> -->
	            </td></tr>
	           
	            <tr>
	            	<td align="right">
	            		<div id="UploadBtn" style="display:block">
	            			
	            			<span class="btn_pack gry">
								<a href="javascript:void(0)" onclick="InnoDS.OpenFileDialog();">파일찾기</a>
							</span>
		            		<span class="btn_pack gry">
								<a href="javascript:void(0)" onclick="upload();">콘텐츠 등록</a>
								
							</span>
							
	            		</div>
	            	</td>
	            </tr>
	            
            </tbody>
	<!-- //list -->
		</form>
	    </table>
	         
    </article><br>
<!-- //Table -->
	
	
				
	

<!-- //Container -->



</body>

</html>	