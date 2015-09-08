<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript" type="text/javascript">

<!--
	//프로파일 입력 사항 확인
	function newprofile(){
	
		if(document.ProfileSearch.proFlnm.value==""){
		  alert("프로파일 이름을 입력하세요");
		  document.ProfileSearch.proFlnm.focus();
		  return false;
		}
		if(document.ProfileSearch.flNameRule.value==""){
		  alert("파일네임규칙을 입력하세요");
		  document.ProfileSearch.flNameRule.focus();
		  return false;
		}
		
		var str = document.ProfileSearch.flNameRule.value;
		
		if(str.length< 8 || str.length > 8){
			alert("파일네임규칙을 8자리를 맞쳐주세요");
			document.ProfileSearch.flNameRule.focus();
		  	return false;
		}
		
	 	var special_pattern=/[~!@\#$%^&*\()\=+']/gi;
	
	 	if(special_pattern.test(str)){
	 		alert("파일네임규칙에 특수문자가 들어갈 수 없습니다.");
	 		document.ProfileSearch.flNameRule.focus();
		  	return false;
	 	}
		
		if(document.ProfileSearch.ext.value==""){
		  alert("확장자을 입력하세요");
		  document.ProfileSearch.ext.focus();
		  return false;
		}
		if(document.ProfileSearch.servBit.value==""){
		  alert("서비스Bit을 입력하세요");
		  document.ProfileSearch.servBit.focus();
		  return false;
		}
		
		
		return true;
	}

	//프로파일 삭제
	function deleteProfile(){
	
		var obj = document.forms["ProfileSearch"];
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
		
		//document.ProfileSearch.action="<@spring.url '/service/deleteProfile.ssc'/>";
		//document.ProfileSearch.submit();
		
		$jq.ajax({
			url: "<@spring.url '/service/deleteProfile.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ProfileSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	document.ProfileSearch.action="<@spring.url '/service/Profile.ssc'/>";
				document.ProfileSearch.submit();
			
			}
			
				
		});
	
	}
	
	// 검색 
	function checkSearch(){ 
   		
   		var stDt=document.ProfileSearch.startDt.value;
		var edDt=document.ProfileSearch.endDt.value;
		
		if(edDt < stDt){
			alert("날짜 검색에 종료일이 시작일 보다 앞선 날짜 입니다.");
			return false;
		}
   		
		document.ProfileSearch.action="<@spring.url '/service/Profile.ssc'/>";
		document.ProfileSearch.pageNo.value="1";
		document.ProfileSearch.submit();
	} 
	
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
			checkSearch(ProfileSearch);    //검색 함수 호출
		}
	}
	
	//프로파일 저장
	function saveProfile(){
		if(newprofile()){
			document.ProfileSearch.action="<@spring.url '/service/saveProfile.ssc'/>";
			document.ProfileSearch.submit();
		}
	}
	
	//프로파일 수정
	function updateProfile(){
		if(newprofile()){
			
			//document.ProfileSearch.action="<@spring.url '/service/updateProfile.ssc'/>";
			//document.ProfileSearch.submit();
			
			$jq.ajax({
			url: "<@spring.url '/service/updateProfile.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ProfileSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	document.ProfileSearch.action="<@spring.url '/service/Profile.ssc'/>";
				document.ProfileSearch.submit();
			
			}
			
				
		});
		}
		
	}
	
	//프로파일 상세보기
	function selectProfile(id){
		ProfileSearch.proflid.value=id;
		$jq.ajax({
			url: "<@spring.url '/service/selectProfile.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ProfileSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
					 
					 $jq('#layer3').empty();
					 	table += '<article class="sideinfo" ><dl class="btncover3"><dt>프로파일 등록</dt><dd><table summary="" class="boardview6"><colgroup><col width="35px"></col><col width="95px"></col><col width="157px"></col></colgroup><tbody>'
					 
				 		table +='<tr><th colspan="2">*이름</th><td><input type="text" name="proFlnm" class="ip_text" value="'+data.proflTbl.proFlnm+'"></input><span class="btns"></span></td></tr>' 
						table +='<tr><th colspan="2">*파일네임규칙</th><td><input type="text" name="flNameRule" class="ip_text" value="'+(data.proflTbl.flNameRule==null?'':data.proflTbl.flNameRule)+'"></input><span class="btns"></span></td></tr>' 
						
				 		table +='<tr><th rowspan="7" class="bggray1">비<br/>디<br/>오</th>'
                		table += '<tr><th>코덱</th><td> <input type="text" name="vdoCodec" value="'+(data.proflTbl.vdoCodec==null?'':data.proflTbl.vdoCodec)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>비트레이트</th><td><input type="text" name="vdoBitRate" value="'+(data.proflTbl.vdoBitRate==null?'':data.proflTbl.vdoBitRate)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>가로</th><td><input type="text" name="vdoHori" value="'+(data.proflTbl.vdoHori==null?'':data.proflTbl.vdoHori)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>세로</th><td><input type="text" name="vdoVert" value="'+(data.proflTbl.vdoVert==null?'':data.proflTbl.vdoVert)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>F/S</th><td><input type="text" name="vdoFS" value="'+(data.proflTbl.vdoFS==null?'':data.proflTbl.vdoFS)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>종횡맞춤</th><td><input type="text" name="vdoSync" value="'+(data.proflTbl.vdoSync==null?'':data.proflTbl.vdoSync)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
					 	table += '<tr><th rowspan="4" class="bggray1">오<br/>디<br/>오</th>'
					 	table += '<th>코덱</th><td><input type="text" name="audCodec" value="'+(data.proflTbl.audCodec==null?'':data.proflTbl.audCodec)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th>비트레이트</th><td><input type="text" name="audBitRate" value="'+(data.proflTbl.audBitRate==null?'':data.proflTbl.audBitRate)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th>채널</th><td><input type="text" name="audChan" value="'+(data.proflTbl.audChan==null?'':data.proflTbl.audChan)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th>샘플링레이트</th><td><input type="text" name="audSRate" value="'+(data.proflTbl.audSRate==null?'':data.proflTbl.audSRate)+'" class="ip_text"></input><span class="btns"></span> </td></tr>'
		                table += '<tr><th colspan="2">*확장자</th><td><input type="text" name="ext" value="'+(data.proflTbl.ext==null?'':data.proflTbl.ext)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th colspan="2">*서비스Bit</th><td><input type="text" name="servBit" value="'+(data.proflTbl.servBit==null?'':data.proflTbl.servBit)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th colspan="2">규격</th><td><input type="text" name="picKind" value="'+(data.proflTbl.picKind==null?'':data.proflTbl.picKind)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th colspan="2">KEY프레임</th><td><input type="text" name="keyFrame" value="'+(data.proflTbl.keyFrame==null?'':data.proflTbl.keyFrame)+'" class="ip_text"></input><span class="btns"></span></td></tr>'
		                //table += '<tr><th colspan="2">*순위</th><td><input type="text" name="priority" value="'+(data.proflTbl.priority==null?'':data.proflTbl.priority)+'" class="ip_text"></input><span class="btns"></span></td></tr></tbody></table></dd></dl>'
		                table += '<tr><th colspan="2">*우선순위</th><td>'
						table += '<select name="priority"><option value="" >&#160; 작업 우선순위 선택 &#160;</option>'
						var priority = data.proflTbl.priority==null ? 0 : data.proflTbl.priority;
		                for(var i=1;i<=10;i++){
		                	if(i==priority)
		                		table += '<option value="'+i+'" selected>&#160; '+i+' &#160;</option>'
		                	else
		                		table += '<option value="'+i+'" >&#160; '+i+' &#160;</option>'
		                }
		                table += '</select></td></tr></tbody></table></dd></dl>'
						
						table += '<dl class="btncover3"><dt>프로파일 옵션'
						for(var iq=0;iq<data.opts.length;iq++){
							if(data.opts[iq].defaultYn == "N"){
								
		                	}else{
		                		table += '&nbsp;&nbsp;&nbsp;&nbsp;(기본옵션&nbsp;:&nbsp;'+(data.opts[iq].optInfo==null?'':data.opts[iq].optInfo)+')'		                		
		                	}
		                }
		                table += '</dt>'
		                table += '<dd><ul id="change_check" class="box6">'
		                
		                
		               
		                for(var iq=0;iq<data.opts.length;iq++){	
		                	var j=0;
		                	
		                	for(var i=0;i<data.proopt.length;i++){ 
		                		
		                		if(data.proopt[i].optId == data.opts[iq].optId){               			
		                			j++;
		                		}else{
		 
		                		}
		                		
		                	}
		                	
		                	if(data.opts[iq].defaultYn == "N"){
			                	if(j != 0){
			                		table += '<li><input name="opt" type="checkbox" value="'+data.opts[iq].optId+'" checked><span>'+data.opts[iq].optInfo+'</span></li>'
			                	}else{
			                		table += '<li><input name="opt" type="checkbox" value="'+data.opts[iq].optId+'"><span>'+data.opts[iq].optInfo+'</span></li>'
			                	}
			                }else{
			                }	
		                }
		                table += '</dd><dd class="btncover11">'
		                table += '<span class="btn_pack gry"><input type="hidden" name="proFlid" value="'+data.proflTbl.proFlid+'"></input>'
		                <#-- 
		                table += '<a href="javascript:" onclick="updateProfile(this)">저장</a></span>'
		                -->
		                table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'updateProfile(this)' '' />
		                table += '</span>&nbsp;'
		                table += '<span class="btn_pack gry">'
		                table += '<a href="#layer3" onclick="showDisHide(\'layer3\',\'1\');return false;">닫기</a>'
		                table += '</span></dd></dl></article>'
		                
					$jq('#layer3').append(table);
			}
			
				
		});
		showDisHide('layer3','0');   
	}
	
	//추가버튼
	function insertProfile(){
		
		$jq.ajax({
			url: "<@spring.url '/service/insertProfile.ssc'/>",
			type: 'GET',
			dataType: 'json',
			
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
			 	
					
					 $jq('#layer3').empty();
					 	table += '<article class="sideinfo" ><dl class="btncover3"><dt>프로파일 등록</dt><dd><table summary="" class="boardview6"><colgroup><col width="35px"></col><col width="95px"></col><col width="157px"></col></colgroup><tbody>'
				 		table += '<tr><th colspan="2">*이름</th><td><input type="text" name="proFlnm" class="ip_text" value=""></input><span class="btns"></span></td></tr>' 
						table += '<tr><th colspan="2">*파일네임규칙</th><td><input type="text" name="flNameRule" class="ip_text" value=""></input><span class="btns"></span></td></tr>' 
						
				 		table += '<tr><th rowspan="7" class="bggray1">비<br/>디<br/>오</th>'
                		table += '<tr><th>코덱</th><td> <input type="text" name="vdoCodec" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>비트레이트</th><td><input type="text" name="vdoBitRate" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>가로</th><td><input type="text" name="vdoHori" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>세로</th><td><input type="text" name="vdoVert" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>F/S</th><td><input type="text" name="vdoFS" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
                		table += '<tr><th>종횡맞춤</th><td><input type="text" name="vdoSync" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
					 	table += '<tr><th rowspan="4" class="bggray1">오<br/>디<br/>오</th>'
					 	table += '<th>코덱</th><td><input type="text" name="audCodec" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th>비트레이트</th><td><input type="text" name="audBitRate" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th>채널</th><td><input type="text" name="audChan" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th>샘플링레이트</th><td><input type="text" name="audSRate" value="" class="ip_text"></input><span class="btns"></span> </td></tr>'
		                table += '<tr><th colspan="2">*확장자</th><td><input type="text" name="ext" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th colspan="2">*서비스Bit</th><td><input type="text" name="servBit" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th colspan="2">규격</th><td><input type="text" name="picKind" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th colspan="2">Key프레임</th><td><input type="text" name="keyFrame" value="" class="ip_text"></input><span class="btns"></span></td></tr>'
		                table += '<tr><th colspan="2">*우선순위</th><td>'
		                table += '<select name="priority"><option value="" >&#160; 작업 우선순위 선택 &#160;</option>'
		                for(var i=1;i<=10;i++){
		                	table += '<option value="'+i+'" >&#160; '+i+' &#160;</option>'
		                }
		                table += '</select></td></tr></tbody></table></dd></dl>'
						
						table += '<dl class="btncover3"><dt>프로파일 옵션'
						for(var iq=0;iq<data.opts.length;iq++){
							
							if(data.opts[iq].defaultYn == "N"){
								
		                	}else{
		                		table += '&nbsp;&nbsp;&nbsp;&nbsp;(기본옵션&nbsp;:&nbsp;'+(data.opts[iq].optInfo==null?'':data.opts[iq].optInfo)+')'
		                	}
		                }
		                table += '</dt>'
		               	
						table += '<dd><ul id="change_check" class="box6">'
              
		                for(var iq=0;iq<data.opts.length;iq++){	
							if(data.opts[iq].defaultYn == "N"){
		                		table += '<li><input name="opt" type="checkbox" value="'+data.opts[iq].optId+'" id="checkin"><label for="checkin"><span>'+data.opts[iq].optInfo+'</span></label></li>'
		                	}else{
		                	}
		                }
		                table += '</dd><dd class="btncover11">'
		                table += '<span class="btn_pack gry">'
		                
		                <#--
		                table += '<a href="javascript:" onclick="saveProfile(this)">저장</a></span>'
		                 -->
		                 table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'saveProfile(this)' '' />
		                 table += '</span>&nbsp;'
		                table += '<span class="btn_pack gry">'
		                table += '<a href="#layer3" onclick="showDisHide(\'layer3\',\'1\');return false;">닫기</a>'
		                table += '</span></dd></dl></article>'
		                	 
					$jq('#layer3').append(table);
				
			}
				
		});
		showDisHide('layer3','0');   
	}
	
	function reset(){
		ProfileSearch.keyword.value="";		
		ProfileSearch.startDt.value="";		
		ProfileSearch.endDt.value="";		
	}

	function goPage(pageNo) {
		document.ProfileSearch.action="<@spring.url '/service/Profile.ssc'/>";
		document.ProfileSearch.pageNo.value = pageNo;
		document.ProfileSearch.submit();
	}
	
	window.onload=function(){
	var obj=document.ProfileSearch.msg.value;
	<#--
	if(obj=="duple"){
		alert("기존 프로파일중 파일네임규칙이 동일한 프로파일이 존재합니다!.");
	}
	-->
	cal1 = new dhtmlxCalendarObject('calInput1', false, {isYearEditable: true,isMonthEditable: true});
	cal1.setYearsRange(1973, 2020);
	cal1.draw();
	
	cal2 = new dhtmlxCalendarObject('calInput2', false, {isYearEditable: true,isMonthEditable: true});
	cal2.setYearsRange(1973, 2020);
	cal2.draw();
	
}
//-->
</script>
<section id="container">
	<form id="ProfileSearch" name="ProfileSearch" method="post">
		<@spring.bind "search" />
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="msg" value="${search.msg!""}" />
		<input type="hidden" name="proflid" value="" />
		<input type="hidden" name="menuId" value="${search.menuId}" />
    <section id="content">
    	<h3 class="blind">프로파일 관리</h3>
		<article class="title5"><img src="<@spring.url '/images/title_profile.gif' />" title="프로파일 관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 서비스 관리  &gt; <span class="now">프로파일 관리</span></nav>
<!-- Popup -->
		<article id="layer3" class="ly_pop2">
        <article class="sideinfo" >
            <dl class="btncover3">
                <dt>프로파일 등록</dt>
                <dd>
                <table summary="" class="boardview6">
                <colgroup><col width="35px"></col><col width="95px"></col><col width="157px"></col></colgroup>
                <tbody>
                <tr>
                	
                	<th colspan="2">*이름</th>
                	<td>
                		<input type="text" name="proFlnm" class="ip_text"></input><span class="btns"></span>
                	</td>
                </tr>
                 <tr>
                	
                	<th colspan="2">*파일네임규칙</th>
                	<td>
                		<input type="text" name="flNameRule" class="ip_text"></input><span class="btns"></span>
                	</td>
                </tr>
                
                <tr><th rowspan="7" class="bggray1">비<br/>디<br/>오</th>
                <tr><th>코덱</th><td>
                <input type="text" name="vdoCodec" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>비트레이트</th><td>
                <input type="text" name="vdoBitRate" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>가로</th><td>
                <input type="text" name="vdoHori" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>세로</th><td>
                <input type="text" name="vdoVert" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>F/S</th><td>
                <input type="text" name="vdoFS" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>종횡맞춤</th><td>
                <input type="text" name="vdoSync" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th rowspan="4" class="bggray1">오<br/>디<br/>오</th><th>코덱</th><td>
                <input type="text" name="audCodec" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>비트레이트</th><td>
                <input type="text" name="audBitRate" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>채널</th><td>
                <input type="text" name="audChan" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>샘플링레이트</th><td>
                <input type="text" name="audSRate" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr>
                	<th colspan="2">*확장자</th>
                	<td>
                		<input type="text" name="ext" value="" class="ip_text"></input><span class="btns"></span>
                	</td>
                </tr>
                <tr>
                	<th colspan="2">*서비스Bit</th>
                	<td>
                		<input type="text" name="servBit" value="" class="ip_text"></input><span class="btns"></span>
                	</td>
                </tr>
                <tr>
                	<th colspan="2">규격</th>
                	<td>
                		<input type="text" name="picKind" value="" class="ip_text"></input><span class="btns"></span>
                	</td>
                </tr>
                <tr>
                	<th colspan="2">Key프레임</th>
                	<td>
                		<input type="text" name="keyFrame" value="" class="ip_text"></input><span class="btns"></span>
                	</td>
                </tr>
                <tr>
                	<th colspan="2">*우선순위</th>
                	<td>
                		<select name="priority">
	                    	<option value="" >&#160; 작업 우선순위 선택 &#160;</option>
	                    	<#list 1..10 as i>
	                    		<option value="${i}" >&#160; ${i} &#160;</option>
	                    	</#list>
	                    </select>
                	</td>
                </tr>
                <#--
                <tr>
                	<th colspan="2">*순위</th>
                	<td>
                		<input type="text" name="priority" value="" class="ip_text"></input><span class="btns"></span>
                	</td>
                </tr>
                -->
                </tbody>
                </table>
                </dd>
            </dl>
            <dl class="btncover3">
                <dt>프로파일 옵션
                
                
                <#list opts as opt>
                	<#if opt.defaultYn =="Y">
                		&nbsp;&nbsp;&nbsp;&nbsp;<b>(기본옵션&nbsp;:&nbsp;${opt.optInfo!""})</b>
                	<#else>
                	</#if>
                </#list>
                </dt>
                <dd>
					<ul id="change_check" class="box6">
						<#list opts as opt> 
							<#if opt.defaultYn =="N">
	                			<li><input name="opt" type="checkbox" value="${opt.optId!""}" id="checkin"><label for="checkin"><span>${opt.optInfo!""}</span></label></li>
	                    	<#else>
	                    	</#if>
	                    </#list>
	                </ul>
                </dd>
                <dd class="btncover11">
                	<span class="btn_pack gry">
                	<#-- 
                		<a href="javascript:" onclick="saveProfile(this)">저장</a>
                		-->
                		<@button '${search.menuId}' '${user.userId}' 'common.save' 'saveProfile(this)' '' />
                	</span>
                	<span class="btn_pack gry">
                		<a href="#layer3" onclick="showDisHide('layer3','1');return false;">닫기</a>
                	</span>
                </dd>
            </dl>
        </article>
    </article>
    	
    
    
<!-- //Popup -->

<!-- Table -->
        <article class="bbsview3">
        <table summary="" id="boardview3">
        <colgroup><col width="30px"></col><col width=""></col><col width="55px"></col><col width="55px"></col><col width="55px"></col><col width="55px"></col><col width="325"></col></colgroup>
            <thead>
            <tr><th colspan="7">
            	<article class="box2">
                    <dl>
                        <dt>등록일</dt>
                        <dd>
                        <input type="text" style="cursor:pointer;"  id="calInput1" name="startDt"  value="<#if search.startDt?exists>${search.startDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>&#126;
                        <input type="text" style="cursor:pointer;" id="calInput2"  name="endDt"  value="<#if search.endDt?exists>${search.endDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>
                        </dd>
                        <dt>프로파일명</dt>
                        <dd><input type="text" name="keyword" value="<#if search.keyword?exists>${search.keyword}</#if>" onKeyDown="keyEnter();"></input></dd>
                        <dt></dt>
                    </dl>
                    <article class="btncover2">
                    <span class="btn_pack gry"><a href="javascript:"  onclick="checkSearch(this)">검색</a></span><span class="btn_pack gry"><a href="javascript:"  onclick="reset()">초기화</a></span>
                    </article>
                </article>
            </th></tr>
            </thead>
<!-- list -->
            <tbody>
           <tr class="bgdeep" style="line-height:10px"><th rowspan="2"></th><th rowspan="2">이름</th><th colspan="2">비디오</th><th colspan="2">오디오</th><th rowspan="2"></td></tr>
		   <tr style="line-height:10px;"><th>코덱</th><th>Bitrate</th><th>코덱</th><th>Bitrate</th></tr>
            <#assign x = 20>  
            <#assign y = x>
	        <#list contents.items as content>
	        	<#if y%2==0>
	            <tr class="gry">
	            <#else>
	            <tr>
	            </#if>
		            <td><input name="check" type="checkbox" value="${content.proFlid!""}"></td>
		            <td align="left">
		            &nbsp;<a href="javascript:" onclick="selectProfile('${content.proFlid!""}')">${content.proFlnm!""}</a>
		            </td>
		            <td align="left">
		            &nbsp;${content.vdoCodec!""}
		            </td>
		            <td>${content.vdoBitRate!""}</td>
		            
		            <td align="left">&nbsp;${content.audCodec!""}</td>
		            <td>${content.audBitRate!""}</td>
		           
		            <#if x == y> 
		            	<td rowspan="${x}">
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
	<a href="javascript:" onclick="insertProfile()">추가</a>
	-->
	<@button '${search.menuId}' '${user.userId}' 'common.insert' 'insertProfile();' '' />
	</span> <span class="btn_pack gry">
	<#-->
	<a href="javascript:" onclick="deleteProfile(this)">삭제</a>
	-->
	<@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteProfile(this);' '' />
	</span>
	</article>
	<!-- paginate -->
	    <@paging contents search.pageNo '' />
	<!-- //paginate -->
	</section>
</form>