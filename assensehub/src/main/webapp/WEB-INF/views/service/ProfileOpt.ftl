<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">
<!--
	//프로파일 옵션 수정하기
	function updateOpt(){
		//alert("111");
		//document.ProfileOptSearch.action="<@spring.url '/service/updateOpt.ssc'/>";
		//document.ProfileOptSearch.submit();
		
		$jq.ajax({
			url: "<@spring.url '/service/updateOpt.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ProfileOptSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	document.ProfileOptSearch.action="<@spring.url '/service/ProfileOpt.ssc'/>";
				document.ProfileOptSearch.submit();
			}
		});
	}
	
	//프로파일 옵션 입력 사항 확인
	function newProfileOpt(){
	
		if(document.ProfileOptSearch.optInfo.value==""){
		  alert("제목을 입력하세요");
		  document.ProfileOptSearch.optInfo.focus();
		  return false;
		}
		if(document.ProfileOptSearch.optDesc.value==""){
		  alert("내용을 입력하세요");
		  document.ProfileOptSearch.optDesc.focus();
		  return false;
		}
		return true;
	}
	
	function defaultOpt(){
		var obj = document.forms["ProfileOptSearch"];
		var chk = document.getElementsByName("check");
		
		var chked=0;
		
		for(var i=0; i<chk.length; i++){
			if(chk[i].checked){
				chked++;
			}
		
		}
		
		if(chked != 1){
			alert("기본옵션은 한개만 선택할 수 있습니다.");
			return;
		}
		
		document.ProfileOptSearch.action="<@spring.url '/service/defaultOpt.ssc'/>";
		document.ProfileOptSearch.submit();
	}
	//프로파일 옵션 삭제
	function deleteProfileOpt(){
	
		var obj = document.forms["ProfileOptSearch"];
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
		
		
		//document.ProfileOptSearch.action="<@spring.url '/service/deleteProfileOpt.ssc'/>";
		//document.ProfileOptSearch.submit();
		
		$jq.ajax({
			url: "<@spring.url '/service/deleteProfileOpt.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ProfileOptSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	document.ProfileOptSearch.action="<@spring.url '/service/ProfileOpt.ssc'/>";
				document.ProfileOptSearch.submit();
			
			}
		});
	}
	
	
	//프로파일 옵션 저장
	function saveProfileOpt(){
		if(newProfileOpt()){
			document.ProfileOptSearch.action="<@spring.url '/service/saveProfileOpt.ssc'/>";
			document.ProfileOptSearch.submit();
		}
	}
	
	//프로파일 옵션 상세보기
	function selectOpt(id){
		//alert(id);
		ProfileOptSearch.optId.value=id;
		$jq.ajax({
			url: "<@spring.url '/service/selectOpt.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#ProfileOptSearch').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
					 $jq('#layer2').empty();
		                
		                table += '<dl class="btncover3"><dt>프로파일 옵션</dt><dd class="bbsview5">'
				 		table += '<table summary="" class="boardview5"><colgroup><col width="70px"></col><col width=""></col></colgroup>' 
						
                		table += '<tbody><tr><th>제목</th><td><input name="optInfo" type="text" value="'+(data.opt.optInfo==null?'':data.opt.optInfo)+'"></input></td></tr>'
                		table += '<tr><th>내용</th><td><article class="box2"><textarea class="ip_contents" name="optDesc" cols="50" rows="20">'+(data.opt.optDesc==null?'':data.opt.optDesc)+'</textarea>'
		                <#-- 
		                table += '<article class="btncover9 mgb7"><span class="btn_pack gry"><a href="#" onclick="saveProfileOpt(this)">저장</a></span>'
		                -->
		                table += '<article class="btncover9 mgb7"><span class="btn_pack gry">'
		                table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'updateOpt(this);' '' />
		                table += '</span>&nbsp;'
                		table += '<span class="btn_pack gry"><a href="#layer2" onclick="showDisHide(\'layer2\',\'1\');return false;">닫기</a></span></article>'
                		table += '</article></td></tr></tbody></table></dd></dl>'	 
					$jq('#layer2').append(table);
			}
		});
		showDisHide('layer2','0');  
	}
	
	//추가버튼
	function insertOpt(){
		
		$jq.ajax({
			url: "<@spring.url '/service/insertprofileopt.ssc'/>",
			type: 'GET',
			dataType: 'json',
			
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
					 $jq('#layer2').empty();
					 	table += '<dl class="btncover3"><dt>프로파일 옵션</dt><dd class="bbsview5">'
				 		table += '<table summary="" class="boardview5"><colgroup><col width="70px"></col><col width=""></col></colgroup>' 
						
                		
                		table += '<tbody><tr><th>제목</th><td><input name="optInfo" type="text"></input></td></tr>'
                		table += '<tr><th>내용</th><td><article class="box2"><textarea class="ip_contents" name="optDesc" cols="50" rows="20"></textarea>'
		                <#-- 
		                table += '<article class="btncover9 mgb7"><span class="btn_pack gry"><a href="#" onclick="saveProfileOpt(this)">저장</a></span>'
		                -->
		                table += '<article class="btncover9 mgb7"><span class="btn_pack gry">'
		                table +=  <@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'saveProfileOpt(this);' '' />
		                table += '</span>&nbsp;'
                		table += '<span class="btn_pack gry"><a href="#layer2" onclick="showDisHide(\'layer2\',\'1\');return false;">닫기</a></span></article>'
                		table += '</article></td></tr></tbody></table></dd></dl>'
		                
		                	 
					$jq('#layer2').append(table);
				
			}
				
		});
		showDisHide('layer2','0');   
	}
	
	function goPage(pageNo) {
		document.ProfileOptSearch.action="<@spring.url '/service/ProfileOpt.ssc'/>";
		document.ProfileOptSearch.pageNo.value = pageNo;
		document.ProfileOptSearch.submit();
	}
//-->
</script>
<section id="container">
	<form id="ProfileOptSearch" name="ProfileOptSearch" method="post">
		<@spring.bind "search" />
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="optId" value="" />
    <section id="content">
    	<h3 class="blind">프로파일 옵션</h3>
		<article class="title5"><img src="<@spring.url '/images/title_profileoption.gif' />" title="프로파일 옵션"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 서비스 관리 &gt; <span class="now">프로파일 옵션</span></nav>
<!-- Popup -->
		<article id="layer2" class="ly_pop7">
            <dl class="btncover3">
                <dt>프로파일 옵션</dt>
            <dd class="bbsview5">
            <table summary="" class="boardview5">
            <colgroup><col width="70px"></col><col width=""></col></colgroup>
                <tbody>
                <tr><th>제목</th><td><input name="optInfo" type="text"></input></td></tr>
                <tr><th>내용</th><td>
                <article class="box2">
                <textarea class="ip_contents" name="optDesc" cols="50" rows="20"></textarea>
                <article class="btncover9 mgb7"><span class="btn_pack gry">
                <#-- 
                <a href="#" onclick="saveProfileOpt(this)">저장</a>
                -->
                 <@button '${search.menuId}' '${user.userId}' 'common.save' 'saveProfileOpt(this);' '' />
                </span> 
                	<span class="btn_pack gry"><a href="#layer2" onclick="showDisHide('layer2','1');return false;">닫기</a></span></article>
                </article>
                </td></tr>
                </tbody>
            </table>   
			</dd>
            </dl>
        </article>
<!-- //Popup -->
<!-- Table -->
        <table summary="" class="boardview8">
        <colgroup><col width="25px"></col><col width="50px"></col><col width="270px"></col><col width="435px"></col></colgroup>
        <tbody>
        <#assign x = 7>
        <#assign y = x>
        <#list contents.items as content>
        <tr><th rowspan="2" class="bgc"><input name="check" type="checkbox" value="${content.optId!""}"></th>
        <th>제목</th>
        <#assign default=content.defaultYn!"">
        <#if default= "Y">
        <td><a href="javascript:" onclick="selectOpt('${content.optId}')">${content.optInfo!""}</a><font color="red">(기본옵션)</font></td>
        <#else>
        <td><a href="javascript:" onclick="selectOpt('${content.optId}')">${content.optInfo!""}</a></td>
        </#if>
        <#if y == 7> 
        <td rowspan="${x*2}" class="gry"></td>
        </#if>
        </tr>
        <tr><th>내용</th><td>${content.optDesc!""}</td></tr>
        <#assign y = y -1>
        </#list>
        <#if y==0>
		<#else>
	        <#list  1..y  as i >
	        <tr>
	        
	        
	        <th rowspan="2" class="bgc"></th><th>제목</th><td><a href="#"></a></td>
	        <#if y == x>
	        <td rowspan="${x*2}" class="gry"></td>
	        </#if> 
	        </tr>
	        <tr><th>내용</th><td><a href="#"></a></td></tr>
	        <#assign y = y -1>
	        </#list>
        </#if>
        
        </tbody>
    </table>     
<!-- //Table -->
	<article class="btncover4">
		<span class="btn_pack gry">
		<#-- 
		-->
		<a href="javascript:" onclick="defaultOpt()">기본옵션선택</a>
		</span>
		<span class="btn_pack gry">
		<#-- 
		<a href="javascript:" onclick="insertOpt()">추가</a>
		-->
		 <@button '${search.menuId}' '${user.userId}' 'common.insert' 'insertOpt();' '' />
		</span> 
		<span class="btn_pack gry">
		<#-- 
		<a href="javascript:" onclick="deleteProfileOpt(this)">삭제</a>
		-->
		<@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteProfileOpt(this);' '' />
		</span>
		
	</article>
	<!-- paginate -->
	    <@paging contents search.pageNo '' />
	<!-- //paginate -->
	</section>

	</form>