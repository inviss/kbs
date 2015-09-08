<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl">
<script type="text/javascript">
function newWorker(id){
	//alert(id);
	if(document.worker.userId.value==""){
	  alert("아이디를 입력하세요");
	  document.worker.userId.focus();
	  return false;
	}
	
	var ids=id.split(",");
	
	for(var i =0; i<ids.length ;i++){
		
		if(ids[i] == document.worker.userId.value){
			alert("아이디가 중복됩니다");
			return false;
		}
		
	}
	
	
	if(document.worker.userPass.value==""){
	  alert("비밀번호를 입력하세요");
	  document.Code.userPass.focus();
	  return false;
	}	
	
	if(document.worker.userNm.value==""){
		alert("사용자명을 입력하세요");
		document.worker.userNm.focus();
	  	return false;
	}
	
	var obj = document.forms["worker"];
	var authid = document.getElementsByName("authId");
	var count=0;
	
	for(var i=0; i<authid.length;i++){
		if(authid[i].checked){
			count+=1;
		}
	}
	if(count == 0){
		alert("작업자 구분을 선택해주세요");
		return false;
	}
	
	return true;
}

function addUser(id) {
	//if(newWorker(id)){
		var obj = ($jq('#worker').get(0));
		obj.action="<@spring.url '/system/addWorker.ssc' />";
		obj.method="post";
		obj.submit();
	//}
}

function updateUser() {
	var obj = ($jq('#worker').get(0));
	obj.action="<@spring.url '/system/updateWorker.ssc' />";
	obj.method="post";
	obj.submit();
}

function deleteUser() {
	var obj = ($jq('#worker').get(0));
	obj.action="<@spring.url '/system/deleteWorker.ssc' />";
	obj.method="post";
	obj.submit();
}


function workerView(userId) {
	//var obj = ($jq('#worker').get(0));
	worker.userId.value = userId;
	
	$jq.ajax({
		url: '<@spring.url '/system/workerDetail.ssc' />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#worker').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){ 
			var table = '';
			$jq('#layer2').empty();
			table +='<article class="sideinfo">                                                                                             '
			table +='	<dl class="btncover7">                                                                                                '
			table +='		<dt>작업자</dt>                                                                                                     '
			table +='  		<dd>                                                                                                              '
			table +='        <table summary="" id="boardview4">                                                                             '
			table +='          <colgroup><col width="80px"></col><col width="207px"></col></colgroup>                                       '
			table +='          <tbody>                                                                                                      '
			table +='            <tr><th>아이디</th><td><input type="text" id="userId" name="userId" class="ip_text wt180" value="'+data.userTbl.userId+'"></input></td></tr>                         '
			table +='            <tr><th>비밀번호</th><td><input type="password" id="userPass" name="userPass" class="ip_text wt180" value=""></input></td></tr>                   '
			table +='            <tr><th>사용자명</th><td><input type="text" id="userNm" name="userNm" class="ip_text wt180" value="'+data.userTbl.userNm+'"></input></td></tr>                       '
			table +='            <tr><th>구분</th>                                                                                          '
			table +='            	<td>                                                                                                      '
			table +='                <ul>                                                                                                   '
			for(var i=0;i<data.authTbls.length;i++){	
				if(data.authTbls[i].authId == data.userTbl.authId){
					table +='                	<li class="blank"><input name="authId" type="radio" value="'+data.authTbls[i].authId+'" checked>'+data.authTbls[i].authNm+'</li> '
				}else{
					table +='                	<li class="blank"><input name="authId" type="radio" value="'+data.authTbls[i].authId+'" >'+data.authTbls[i].authNm+'</li> '
				}
			}
			table +='              	</ul>                                                                                                   '
			table +='							</td>                                                                                                     '
			table +='						</tr>                                                                                                       '
			table +='          </tbody>                                                                                                     '
			table +='        </table>                                                                                                       '
			table +='			</dd>                                                                                                             '
			table +='  		<dd class="btncover11">                                                                                            '
			table +='  			<span class="btn_pack gry"><a href="javascript:void(0)" onClick="updateUser()">저장</a></span>   '
			table +='  		</dd>                                                                                                             '
			table +='		</dl>                                                                                                               '
			table +='	</article>																																																						'
			$jq('#layer2').append(table);
		}
	});
	showDisHide('layer2','0');
}

function workerInit() {
	//var obj = ($jq('#worker').get(0));
	
	$jq.ajax({
		url: '<@spring.url '/system/WorkerCheck.ssc' />',
		type: 'POST',
		dataType: 'json',
		data: $jq('#worker').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){ 
			var table = '';
			$jq('#layer2').empty();
			table +='<article class="sideinfo">                                                                                             '
			table +='	<dl class="btncover7">                                                                                                '
			table +='		<dt>작업자</dt>                                                                                                     '
			table +='  		<dd>                                                                                                              '
			table +='        <table summary="" id="boardview4">                                                                             '
			table +='          <colgroup><col width="80px"></col><col width="207px"></col></colgroup>                                       '
			table +='          <tbody>                                                                                                      '
			table +='            <tr><th>아이디</th><td><input type="text" id="userId" name="userId" class="ip_text wt180"></input></td></tr>                         '
			table +='            <tr><th>비밀번호</th><td><input type="password" id="userPass" name="userPass" class="ip_text wt180"></input></td></tr>                   '
			table +='            <tr><th>사용자명</th><td><input type="text" id="userNm" name="userNm" class="ip_text wt180"></input></td></tr>                       '
			table +='            <tr><th>구분</th>                                                                                          '
			table +='            	<td>                                                                                                      '
			table +='                <ul>                                                                                                   '
			<#list authTbls as auth>
			table +='                	<li class="blank"><input name="authId" type="radio" value="${auth.authId!""}">${auth.authNm!""}</li>               '
			</#list>
			table +='              	</ul>                                                                                                   '
			table +='							</td>                                                                                                     '
			table +='						</tr>                                                                                                       '
			table +='          </tbody>                                                                                                     '
			table +='        </table>                                                                                                       '
			table +='			</dd>                                                                                                             '
			table +='  		<dd class="btncover11">                                                                                            '
			table +='  			<span class="btn_pack gry"><a href="javascript:void(0)" onClick="addUser()">저장</a></span>   '
			table +='  		</dd>                                                                                                             '
			table +='		</dl>                                                                                                               '
			table +='	</article>																																																						'
			$jq('#layer2').append(table);
		}
	});
	showDisHide('layer2','0');  
}


</script>
<section id="container">
	<form id="worker" name="worker" method="get">
	<@spring.bind "userTbl" />
	<input type="hidden" name="useId" value="" />
	<input type="hidden" name="menuId" value="${search.menuId}" />
    <section id="content">
    	<h3 class="blind">작업자 관리</h3>
		<article class="title5"><img src="<@spring.url '/images/title_worker.gif' />" title="작업자 관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 시스템 관리 &gt; <span class="now">작업자 관리</span></nav>
<!-- Popup -->
		<article id="layer2" class="ly_pop14">
			<article class="sideinfo">                                                                                             
				<dl class="btncover7">                                                                                                
					<dt>작업자</dt>                                                                                                    
			  		<dd>                                                                                                             
			        	<table summary="" id="boardview4">                                                                             
			          		<colgroup><col width="80px"></col><col width="207px"></col></colgroup>                                       
			         		<tbody>                                                                                                      
			     	 			<tr><th>아이디</th><td><input type="text" id="userId" name="userId" class="ip_text wt180"></input></td></tr>                       
			            		<tr><th>비밀번호</th><td><input type="password" id="userPass" name="userPass" class="ip_text wt180"></input></td></tr>                
			          			<tr><th>사용자명</th><td><input type="text" id="userNm" name="userNm" class="ip_text wt180"></input></td></tr>                       
			            		<tr><th>구분</th>                                                                                         
					            	<td>                                                                                                     
						            	<ul> 
						            		<#assign id="">
						            		<#assign j=1>
						            		<#list userList as content>
						            			<#if j=1>
								            		<#assign id=id +content.userId>	
								            	<#else>
								            		<#assign id=id +","+content.userId>
							            		</#if>
							            	<#assign j=j+1>	
						            		</#list>                                                                                                  
									    	<#list authTbls as auth>
							               		<li class="blank"><input name="authId" type="radio" value="${auth.authId!""}">${auth.authNm!""}</li>               
											</#list>
				             			</ul>                                                                                                  
									</td>                                                                                                    
								</tr>                                                                                                     
			         		</tbody>                                                                                                     
			        	</table>                                                                                                       
					</dd>                                                                                                             
			  		<dd class="btncover11">                                                                                            
						<span class="btn_pack gry"><a href="javascript:void(0)" onClick="addUser('${id}')">저장</a></span>   
			 		</dd>                                                                                                            
				</dl>                                                                                                              
			</article>		
    </article>
<!-- //Popup -->
        
<!-- Table -->
        <article class="bbsview3 bd2">
	        <table summary="" id="boardview3">
	       		<colgroup><col width="30px"></col><col width="130px"></col><col width="160px"></col><col width=""></col><col width="313"></col></colgroup>
	<!-- list -->
	            <tbody>
	            	<tr><th></th><th>아이디</th><th>사용자명</th><th>구분</th><th></th></tr>
	            	
	            	<#assign x = 15>  
            		<#assign y = x>
            
	            	<#list userList as content>
	            	<#if y%2==0>
		            <tr>
		            <#else>
		            <tr class="gry">
		            </#if>
	            		<td><input name="userIds" type="checkbox" value="${content.userId}"></td>
			            <td align="left">&nbsp;<a href="javascript:void(0)" onClick="workerView('${content.userId}')">${content.userId}</a></td>
			            <td align="left">&nbsp;${content.userNm}</td>
			            <td align="left">&nbsp;${content.authNm}</td>
			            <#if x == y> 
		            	<td rowspan="${x}">
		    	        </td>
		    	    </#if>
	           		</tr>
	           		<#assign y = y -1>
	           		</#list>
	           		<#list 1..y  as i>
	            	<#if y%2==0>
		            <tr>
		            <#else>
		            <tr class="gry">
		            </#if>
	            		<td></td>
			            <td></td>
			            <td></td>
			            <td></td>
	           		</tr>
	           		<#assign y = y -1>
	           		</#list>
	            	
	        	</tbody>
<!-- //list -->
    		</table>   
    	
   		</article>
<!-- //Table -->
		<article class="btncover4">
			<span class="btn_pack gry">
			<#-- 
			<a href="javascript:void(0)" onClick="workerInit()">추가</a>
			-->
			 <@button '${search.menuId}' '${user.userId}' 'common.insert' 'workerInit();' '' />
			</span>
			<span class="btn_pack gry">
			<#--
			<a href="javascript:void(0)" onClick="deleteUser()">삭제</a>
			-->
			 <@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteUser();' '' />
			</span>
		</article>
	</section>
	</form>  