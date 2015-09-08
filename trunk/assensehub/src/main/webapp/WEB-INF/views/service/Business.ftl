<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">
<!--
	//사업자 입력 사항 확인
	function newBusiness(){
	 
        var size=document.BusinessSearch.elements['contentML'].length;	
	 	var size2=document.BusinessSearch.elements['vodSmil'].length;	
	 	
	 	for(var i = 0; i < size; i++) {
          if(document.BusinessSearch.elements["contentML"][i].checked) {
               var contentML=document.BusinessSearch.elements['contentML'][i].value;
               break;
          }
        }
	 	for(var i = 0; i < size2; i++) {
          if(document.BusinessSearch.elements["vodSmil"][i].checked) {
               var vodSmil=document.BusinessSearch.elements['vodSmil'][i].value;
               break;
          }
        }
		
		if(document.BusinessSearch.company.value==""){
		  alert("업체명을 입력하세요");
		  document.BusinessSearch.company.focus();
		  return false;
		}
		
		if(document.BusinessSearch.ip.value==""){
		  alert("아이피을 입력하세요");
		  document.BusinessSearch.ip.focus();
		  return false;
		}
		if(document.BusinessSearch.port.value==""){
		  alert("포트을 입력하세요");
		  document.BusinessSearch.company.focus();
		  return false;
		}
		if(document.BusinessSearch.ftpid.value==""){
		  alert("아이디을 입력하세요");
		  document.BusinessSearch.ftpid.focus();
		  return false;
		}
		if(document.BusinessSearch.password.value==""){
		  alert("패스워드을 입력하세요");
		  document.BusinessSearch.password.focus();
		  return false;
		}
		if(document.BusinessSearch.remoteDir.value==""){
		  alert("사용자 경로을 입력하세요");
		  document.BusinessSearch.remoteDir.focus();
		  return false;
		}
		 
		if(contentML==vodSmil){
		  
		  alert("ContentML과 Smil 선택 값이 같을 수 없습니다.");
		  //document.BusinessSearch.remoteDir.focus();
		  return false;
		}
		
		return true;
	}
	
	//사업자 삭제
	function deleteBusiness(){
		
		var obj = document.forms["BusinessSearch"];
		var chk = document.getElementsByName("check");
		
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
		
		
		//document.BusinessSearch.action="<@spring.url '/service/deleteBusiness.ssc'/>";
		//document.BusinessSearch.submit();
			
		$jq.ajax({
			url: "<@spring.url '/service/deleteBusiness.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#BusinessSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	document.BusinessSearch.action="<@spring.url '/service/Business.ssc'/>";
				document.BusinessSearch.submit();
			
			}
			
				
		});	
	}
	
	//사업자 수정하기
	function updateBusiness(){
		
		if(newBusiness()){
			//document.BusinessSearch.action="<@spring.url '/service/updateBusiness.ssc'/>";
			//document.BusinessSearch.submit();
			
			$jq.ajax({
			url: "<@spring.url '/service/updateBusiness.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#BusinessSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	document.BusinessSearch.action="<@spring.url '/service/Business.ssc'/>";
				document.BusinessSearch.submit();
			
			}
			
				
		});	
		}
	}
	
	// 검색 
	function checkSearch(){ 
   
		document.BusinessSearch.action="<@spring.url '/service/Business.ssc'/>";
		document.BusinessSearch.pageNo.value="1";
		document.BusinessSearch.submit();
	}
	
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
			checkSearch(BusinessSearch);    //검색 함수 호출
		}
	}
	
	//사업자 저장
	function saveBusiness(){
		if(newBusiness()){
			document.BusinessSearch.action="<@spring.url '/service/saveBusiness.ssc'/>";
			document.BusinessSearch.submit();
		}
	}
	
	//사업자 상세보기
	function selectbusipartner(id){
		BusinessSearch.busiPartnerid.value=id;
		$jq.ajax({
			url: "<@spring.url '/service/selectbusipartner.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#BusinessSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
			 	
					
					 $jq('#layer2').empty();
					 	table += '<article class="sideinfo" ><dl class="btncover3"><dt>서비스 기본 정보</dt><dd><table summary="" class="boardview6"><colgroup><col width="110px"></col><col width="177px"></col></colgroup><tbody>'
				 		table += '<tr><th>업체명</th><td><input type="text" name="company" class="ip_text2" value="'+(data.busipartnerTbl.company==null?'':data.busipartnerTbl.company)+'"></input></td></tr>'  
						                
                		
                		table += '<tr><th>서비스 여부</th>'
                		if(data.busipartnerTbl.servyn == "Y"){
                			table += '<td><ul class="radio"><li class="blank"><input name="servyn" type="radio" value="Y" checked="checked">사용함</li>'
		                	table += '<li class="blank"><input name="servyn" type="radio" value="N" >사용안함</li></ul></td></tr>'
                		}else{
                			table += '<td><ul class="radio"><li class="blank"><input name="servyn" type="radio" value="Y">사용함</li>'
		                	table += '<li class="blank"><input name="servyn" type="radio" value="N" checked="checked">사용안함</li></ul></td></tr>'
                		}
                		table += '<tr><th>FTP 서비스 여부</th>'
                		if(data.busipartnerTbl.ftpServYn == "Y"){
                			table += '<td><ul class="radio"><li class="blank"><input name="ftpServYn" type="radio" value="Y" checked="checked">사용함</li>'
		                	table += '<li class="blank"><input name="ftpServYn" type="radio" value="N" >사용안함</li></ul></td></tr>'
		                }else{
		                	table += '<td><ul class="radio"><li class="blank"><input name="ftpServYn" type="radio" value="Y">사용함</li>'
		                	table += '<li class="blank"><input name="ftpServYn" type="radio" value="N" checked="checked">사용안함</li></ul></td></tr>'
		                }
		                table += '<tr><th>프로그램 Alias정보</th>'
		                if(data.busipartnerTbl.proEngYn == "N"){
		                		table += '<td><ul class="radio">'
				                table += '<li class="blank"><input name="proEngYn" type="radio" value="N" checked="checked">코드</li>'
				                table += '<li class="blank"><input name="proEngYn" type="radio" value="E" >영문</li>'
				                table += '<li class="blank"><input name="proEngYn" type="radio" value="K" >한글</li>'
				                table += '</ul></td></tr>'
		                }else if(data.busipartnerTbl.proEngYn == "K"){
		                		table += '<td><ul class="radio">'
				                table += '<li class="blank"><input name="proEngYn" type="radio" value="N" >코드</li>'
				                table += '<li class="blank"><input name="proEngYn" type="radio" value="E" >영문</li>'
				                table += '<li class="blank"><input name="proEngYn" type="radio" value="K" checked="checked">한글</li>'
				                table += '</ul></td></tr>'
		                }else{
		                		table += '<td><ul class="radio">'
		                		table += '<li class="blank"><input name="proEngYn" type="radio" value="N" >코드</li>'
				                table += '<li class="blank"><input name="proEngYn" type="radio" value="E" checked="checked" >영문</li>'
				                table += '<li class="blank"><input name="proEngYn" type="radio" value="K"  >한글</li>'
				                table += '</ul></td></tr>'
		                }
		                table += '<tr><th>서비스URL 전달</th>'
		                if(data.busipartnerTbl.srvUrl == "Y"){
		                	table += '<td><ul class="radio"><li class="blank"><input name="srvUrl" type="radio" value="Y" checked="checked">사용함</li>'
		                	table += '<li class="blank"><input name="srvUrl" type="radio" value="N" >사용안함</li></ul></td></tr>'
		                }else{
		                	table += '<td><ul class="radio"><li class="blank"><input name="srvUrl" type="radio" value="Y">사용함</li>'
		                	table += '<li class="blank"><input name="srvUrl" type="radio" value="N" checked="checked">사용안함</li></ul></td></tr>'
		                }
		                table += '<tr><th>ContentML 전달</th>'
		                if(data.busipartnerTbl.contentML == "Y"){
		                	table += '<td><ul class="radio"><li class="blank"><input name="contentML" type="radio" value="Y" checked="checked">사용함</li>'
		                	table += '<li class="blank"><input name="contentML" type="radio" value="N" >사용안함</li></ul></td></tr>'
		                }else{
		                	table += '<td><ul class="radio"><li class="blank"><input name="contentML" type="radio" value="Y">사용함</li>'
		                	table += '<li class="blank"><input name="contentML" type="radio" value="N" checked="checked">사용안함</li></ul></td></tr>'
		                }
		                table += '<tr><th>Smil 전달</th>' 
		                if(data.busipartnerTbl.vodSmil == "Y"){
		                	table += '<td><ul class="radio"><li class="blank"><input name="vodSmil" type="radio" value="Y" checked="checked">사용함</li>'
		                	table += '<li class="blank"><input name="vodSmil" type="radio" value="N" >사용안함</li></ul></td></tr>'
		                }else{
		                	table += '<td><ul class="radio"><li class="blank"><input name="vodSmil" type="radio" value="Y">사용함</li>'
		                	table += '<li class="blank"><input name="vodSmil" type="radio" value="N" checked="checked">사용안함</li></ul></td></tr>'
		                }
		                
		                table += '<tr><th>사업자별 영문 alias</th><td><input type="text" name="alias" class="ip_text2" value="'+(data.busipartnerTbl.alias==null?'':data.busipartnerTbl.alias)+'"></input></td></tr>'  
                		table += '</tbody></table></dd></dl>'
		                table += '<dl class="btncover3"><dt>FTP 정보</dt><dd><table summary="" class="boardview6">'
		                table += '<colgroup><col width="55px"></col><col width="88px"></col><col width="55px"></col><col width="89px"></col></colgroup><tbody>'
		                table += '<tr><th>아이피</th><td><input name="ip" type="text" class="ip_short" value="'+(data.busipartnerTbl.ip==null?'':data.busipartnerTbl.ip)+'"></input></td><th>포트</th><td><input name="port" type="text" class="ip_short" value="'+(data.busipartnerTbl.port==null?'':data.busipartnerTbl.port)+'"></input></td></tr>'
		                
		                
		                table += '<tr><th>아이디</th><td><input name="ftpid" type="text" class="ip_short" value="'+(data.busipartnerTbl.ftpid==null?'':data.busipartnerTbl.ftpid)+'"></td><th>패스워드</th><td><input name="password" type="text" class="ip_short" value="'+(data.busipartnerTbl.password==null?'':data.busipartnerTbl.password)+'"></input></tr>'
		                table += '<tr><th>전송방식</th><td colspan="3">'
		                if(data.busipartnerTbl.transMethod == "Passive Mode"){
		                	table += '<ul class="radio"><li class="blank"><input name="transMethod" type="radio" value="Passive Mode" checked="checked">Passive Mode</li>'
		                	table += '<li class="blank"><input name="transMethod" type="radio" value="Active Mode" >Active Mode</li></ul></td></tr>'
		                }else{
		                	table += '<ul class="radio"><li class="blank"><input name="transMethod" type="radio" value="Passive Mode">Passive Mode</li>'
		                	table += '<li class="blank"><input name="transMethod" type="radio" value="Active Mode" checked="checked">Active Mode</li></ul></td></tr>'		            	
		                }
		                table += '<tr><th>사용자<br/>경로</th><td colspan="3"><input name="remoteDir" type="text" class="ip_text3" value="'+(data.busipartnerTbl.remoteDir==null?'':data.busipartnerTbl.remoteDir)+'"></input></td></tr></tbody></table></dd></dl>'
		                
		                table += '<dl class="btncover3"><dt>프로파일 정보</dt><dd><ul id="change_check" class="box6">'
		                for(var iq=0;iq<data.profls.length;iq++){	
							var j=0;
		                	
		                	for(var i=0;i<data.probuis.length;i++){
		                		if(data.profls[iq].proFlid == data.probuis[i].proFlid){               			
		                			j++;
		                		}else{
		 
		                		}
		                	}
		                	if(j !=0 ){
		                		table += '<li><input name="profl" type="checkbox" value="'+data.profls[iq].proFlid+'" checked><span>'+data.profls[iq].proFlnm+'</span></li>'
		                	}else{
		                		table += '<li><input name="profl" type="checkbox" value="'+data.profls[iq].proFlid+'"><span>'+data.profls[iq].proFlnm+'</span></li>'
		                	}
		                	
		                	
		                }
						table += '</ul></dd><dd class="btncover11">'
                	    table += '<span class="btn_pack gry">'
                	    <#--
                	    table += '<a href="javascript:" onclick="updateBusiness(this)">저장</a>'
                	    <@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'updateOpt(this);' '' />
                	    -->
                	    table +=  <@buttonSingle '${search.menuId}' '${user.userId}' 'common.update' 'updateBusiness(this);' ''/>
                	    table += '</span> <span class="btn_pack gry"><a href="#layer2" onclick="showDisHide(\'layer2\',\'1\');return false;">닫기</a></span></dd></dl></article>'
                		
		                	 
					$jq('#layer2').append(table);
				
			}
				
		});
		showDisHide('layer2','0');   
	}

	//추가버튼
	function insertBusipartner(){
		
		$jq.ajax({
			url: "<@spring.url '/service/insertbusipartner.ssc'/>",
			type: 'GET',
			dataType: 'json',
			
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
					 $jq('#layer2').empty();
					 	table += '<article class="sideinfo" ><dl class="btncover3"><dt>서비스 기본 정보</dt><dd><table summary="" class="boardview6"><colgroup><col width="110px"></col><col width="177px"></col></colgroup><tbody>'
				 		table += '<tr><th>업체명</th><td><input type="text" name="company" class="ip_text2" value=""></input></td></tr>' 
                		table += '<tr><th>서비스 여부</th>'
                		table += '<td><ul class="radio"><li class="blank"><input name="servyn" type="radio" value="Y" checked="checked">사용함</li>'
		                table += '<li class="blank"><input name="servyn" type="radio" value="N" >사용안함</li></ul></td></tr>'
                		table += '<tr><th>FTP 서비스 여부</th>'
                		table += '<td><ul class="radio"><li class="blank"><input name="ftpServYn" type="radio" value="Y" checked="checked">사용함</li>'
		                table += '<li class="blank"><input name="ftpServYn" type="radio" value="N" >사용안함</li></ul></td></tr>'
		                table += '<tr><th>프로그램 Alias정보</th>'
                		table += '<td><ul class="radio">'
		                table += '<li class="blank"><input name="proEngYn" type="radio" value="N" checked="checked">코드</li>'
		                table += '<li class="blank"><input name="proEngYn" type="radio" value="E" >영문</li>'
		                table += '<li class="blank"><input name="proEngYn" type="radio" value="K" >한글</li>'
		                table += '</ul></td></tr>'
		                table += '<tr><th>서비스URL 전달</th>'
                		table += '<td><ul class="radio"><li class="blank"><input name="srvUrl" type="radio" value="Y">사용함</li>'
		                table += '<li class="blank"><input name="srvUrl" type="radio" value="N" checked="checked">사용안함</li></ul></td></tr>'
		                 table += '<tr><th>ContentML 전달</th>'
                		table += '<td><ul class="radio"><li class="blank"><input name="contentML" type="radio" value="Y">사용함</li>'
		                table += '<li class="blank"><input name="contentML" type="radio" value="N" checked="checked">사용안함</li></ul></td></tr>'
		                 table += '<tr><th>Smil 전달</th>'
                		table += '<td><ul class="radio"><li class="blank"><input name="vodSmil" type="radio" value="Y">사용함</li>'
		                table += '<li class="blank"><input name="vodSmil" type="radio" value="N" checked="checked">사용안함</li></ul></td></tr>'
		                table += '<tr><th>사업자별 영문 alias</th><td><input type="text" name="alias" class="ip_text2" value=""></input></td></tr>'
		                table += '</tbody></table></dd></dl>'
		                table += '<dl class="btncover3"><dt>FTP 정보</dt><dd><table summary="" class="boardview6">'
		                table += '<colgroup><col width="55px"></col><col width="88px"></col><col width="55px"></col><col width="89px"></col></colgroup><tbody>'
		                table += '<tr><th>아이피</th><td><input name="ip" type="text" class="ip_short"></input></td><th>포트</th><td><input name="port" type="text" class="ip_short"></input></td></tr>'
		                
		                
		                table += '<tr><th>아이디</th><td><input name="ftpid" type="text" class="ip_short"></td><th>패스워드</th><td><input name="password" type="text" class="ip_short"></input></tr>'
		                table += '<tr><th>전송방식</th><td colspan="3">'
		                table += '<ul class="radio"><li class="blank"><input name="transMethod" type="radio" value="Passive Mode" checked="checked">Passive Mode</li>'
		                table += '<li class="blank"><input name="transMethod" type="radio" value="Active Mode" >Active Mode</li></ul></td></tr>'
		                table += '<tr><th>사용자<br/>경로</th><td colspan="3"><input name="remoteDir" type="text" class="ip_text3"></input></td></tr></tbody></table></dd></dl>'
		                
		                table += '<dl class="btncover3"><dt>프로파일 정보</dt><dd><ul id="change_check" class="box6">'
		                for(var iq=0;iq<data.profls.length;iq++){	
			
		                	table += '<li><input name="profl" type="checkbox" value="'+data.profls[iq].proFlid+'"  id="checkin"><label for="checkin"><span>'+data.profls[iq].proFlnm+'</span></label></li>'
		                	
		                }
						table += '</ul></dd><dd class="btncover11">'
                	    table += '<span class="btn_pack gry">'
                	    <#--
                	    table += '<a href="javascript:" onclick="saveBusiness(this)">저장</a>'
                	    -->
                	    table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'saveBusiness(this)' '' />
                	    table += '</span> <span class="btn_pack gry"><a href="#layer2" onclick="showDisHide(\'layer2\',\'1\');return false;">닫기</a></span></dd></dl></article>'
                		
		                	 
					$jq('#layer2').append(table);
				
			}
				
		});
		showDisHide('layer2','0');   
	}
	
	function reset(){
		BusinessSearch.keyword.value="";		
			
	}
	
	function goPage(pageNo) {
		document.BusinessSearch.action="<@spring.url '/service/Business.ssc'/>";
		document.BusinessSearch.pageNo.value = pageNo;
		document.BusinessSearch.submit();
	}
//-->
</script>
<section id="container">
	<form id="BusinessSearch" name="BusinessSearch" method="post">
		<@spring.bind "search" />
		<@spring.bind "business" />
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="busiPartnerid" value="" />
		<input type="hidden" name="menuId" value="${search.menuId}" />
    <section id="content">
    	<h3 class="blind">사업자 관리</h3>
		<article class="title5"><img src="<@spring.url '/images/title_business.gif' />" title="사업자 관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 서비스 관리  &gt; <span class="now">사업자 관리</span></nav>
<!-- Popup -->
		<article id="layer2" class="ly_pop5">
        <article class="sideinfo">
            <dl class="btncover3">
                <dt>서비스 기본 정보</dt>
                <dd>
	                <table summary="" class="boardview6">
	                <colgroup><col width="110px"></col><col width="177px"></col></colgroup>
	                <tbody>
		                <tr><th>업체명</th><td><input name="company" type="text" class="ip_text2"></input></td></tr>
		                
		                <tr><th>서비스 여부</th>
		                <td>
		                <ul class="radio">
		                <li class="blank"><input name="servyn" type="radio" value="Y" checked="checked">사용함</li>
		                <li class="blank"><input name="servyn" type="radio" value="N" >사용안함</li>
		                </ul>
						</td></tr>
		                <tr><th>FTP 서비스 여부</th>
		                <td>
		                <ul class="radio">
		                <li class="blank"><input name="ftpServYn" type="radio" value="Y" checked="checked">사용함</li>
		                <li class="blank"><input name="ftpServYn" type="radio" value="N" >사용안함</li>
		                </ul>
						</td></tr>
		                <tr><th>프로그램 Alias정보</th>
		                <td>
		                <ul class="radio">
		                <li class="blank"><input name="proEngYn" type="radio" value="N" checked="checked">코드</li>
		                <li class="blank"><input name="proEngYn" type="radio" value="E" >영문</li>
		                <li class="blank"><input name="proEngYn" type="radio" value="K" >한글</li>
		                </ul>
						</td></tr>
						<tr><th>서비스URL 전달</th>
		                <td>
		                <ul class="radio">
		                <li class="blank"><input name="srvUrl" type="radio" value="Y">사용함</li>
		                <li class="blank"><input name="srvUrl" type="radio" value="N" checked="checked" >사용안함</li>
		                </ul>
						</td></tr>
						<tr><th>ContentML 전달</th>
		                <td>
		                <ul class="radio">
		                <li class="blank"><input name="contentML" type="radio" value="Y">사용함</li>
		                <li class="blank"><input name="contentML" type="radio" value="N" checked="checked" >사용안함</li>
		                </ul>
						</td></tr>
						<tr><th>Smil 전달</th>
		                <td>
		                <ul class="radio">
		                <li class="blank"><input name="vodSmil" type="radio" value="Y">사용함</li>
		                <li class="blank"><input name="vodSmil" type="radio" value="N" checked="checked" >사용안함</li>
		                </ul>
						</td></tr>
						<tr><th>사업자별 영문 alias</th><td><input name="alias" type="text" class="ip_text2"></input></td></tr>
	                </tbody>
	                </table>
                </dd>
            </dl>
            <dl class="btncover3">
                <dt>FTP 정보</dt>
                <dd>
	                <table summary="" class="boardview6">
	                <colgroup><col width="55px"></col><col width="88px"></col><col width="55px"></col><col width="89px"></col></colgroup>
	                <tbody>
		                <tr><th>아이피</th><td><input name="ip" type="text" class="ip_short"></input></td><th>포트</th><td><input name="port" type="text" class="ip_short"></input></td></tr>
		                <tr><th>아이디</th><td><input name="ftpid" type="text" class="ip_short"></td><th>패스워드</th><td><input name="password" type="text" class="ip_short"></input></tr>
		                <tr><th>전송방식</th>
		                <td colspan="3">
		                <ul class="radio">
		                <li class="blank"><input name="transMethod" type="radio" value="Passive Mode" checked="checked">Passive Mode</li>
		                <li class="blank"><input name="transMethod" type="radio" value="Active Mode" >Active Mode</li>
		                </ul>
		                </td></tr>
		                <tr><th>사용자<br/>경로</th>
		                <td colspan="3">
		                	<input name="remoteDir" type="text" class="ip_text3"></input>
		                </td>
		                </tr>
	                </tbody>
	                </table>
                </dd>
            </dl>
            <dl class="btncover3">
                <dt>프로파일 정보</dt>
                <dd>
				<ul id="change_check" class="box6">
					<#list profls as profl>
						<li><input name="profl" type="checkbox" value="${profl.proFlid!""}"><span>${profl.proFlnm!""}</span></li>
					</#list> 
                </ul>
                </dd>
                <dd class="btncover11">
                	<span class="btn_pack gry">
                	<#--
                	<a href="javascript:" onclick="saveBusiness(this)">저장</a>
                	-->
                	<@button '${search.menuId}' '${user.userId}' 'common.save' 'saveBusiness(this)' '' />
                	</span> <span class="btn_pack gry"><a href="#layer2" onclick="showDisHide('layer2','1');return false;">닫기</a></span>
                </dd>
            </dl>
        </article>
        
    </article>
<!-- //Popup -->

<!-- Table -->
        <article class="bbsview3">
	        <table summary="" id="boardview3">
	        <colgroup><col width="30px"></col><col width="160px"></col><col width=""></col><col width="70px"></col><col width="325px"></col></colgroup>
	            <thead>
		            <tr><th colspan="5">
		            	<article class="box2">
		                    <dl>
		                        <dt>사업자명</dt>
		                        <dd><input type="text" name="keyword" value="<#if search.keyword?exists>${search.keyword}</#if>" onKeyDown="keyEnter();"></input></dd>
		                        <dt></dt>
		                    </dl>
		                    <article class="btncover2">
		                    <span class="btn_pack gry"><a href="javascript:"  onclick="checkSearch(this)">검색</a></span> <span class="btn_pack gry"><a href="javascript:"  onclick="reset()">초기화</a></span>
		                    </article>
		                </article>
		            </th></tr>
	            </thead>
	<!-- list -->
	            <tbody>
	            <tr><th></th><th>IP</th><th>업체명</th><th>서비스 여부</th><th></th></tr>
	            <#assign x = 20>  
	            <#assign y = x>
	          	<#list contents.items as content>    
	          		<#if y%2==0>
		            <tr class="gry">
		            <#else>
		            <tr>
		            </#if>
		            	<td><input id="check" name="check" type="checkbox" value="${content.busiPartnerid!""}"></td>
			            <td>
			            	${content.ip!""}
			            </td>
			            <td align="left">&nbsp;<a href="javascript:" onclick="selectbusipartner('${content.busiPartnerid!""}')">${content.company!""}</td>
			           	<td>${content.servyn!""}</td>
			            <#if y == 20> 
			            	<td rowspan="20">
							<!-- side information -->
				
							<!-- //side information -->
			    	        </td>
			    	    </#if>
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
					            <td>
					            	
					            </td>
					          	<td></td>
					            <td></td>
					            <#if y==20>      
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
    </article>
<!-- //Table -->
	<article class="btncover4">
		<span class="btn_pack gry">
		<#-- 
		<a href="javascript:" onclick="insertBusipartner()">추가</a>
		-->
		<@button '${search.menuId}' '${user.userId}' 'common.insert' 'insertBusipartner();' '' />
		</span>
		<span class="btn_pack gry">
		<#--
		<a href="javascript:" onclick="deleteBusiness(this)">삭제</a>
		-->
		<@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteBusiness(this);' '' />
		</span>
	</article>
	<!-- paginate -->
	    <@paging contents search.pageNo '' />
	<!-- //paginate -->	
	</section>
	</form>