<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">

// 검색 
function checkSearch(){ 
   
	document.Code.action="<@spring.url '/system/Code.ssc'/>";
	document.Code.pageNo.value="1";
	document.Code.submit();
}

//코드 입력 사항 확인
function newCode(){

	if(document.Code.clfNm.value==""){
	  alert("코드명을 입력하세요");
	  document.Code.clfNm.focus();
	  return false;
	}
	
	if(document.Code.clfCd.value==""){
	  alert("구분코드을 선택하세요");
	  document.Code.clfCd.focus();
	  return false;
	}
	
	
	
		
	if(document.Code.sclCd.value==""){
  	  alert("구분상세코드을 입력하세요");
	  document.Code.sclCd.focus();
	  return false;
	}
	
	var str2 = document.Code.sclCd.value;
		
	if(str2.length > 5){
		alert("구분상세코드는 5자리를 넘길수 없습니다.");
		document.Code.sclCd.focus();
	  	return false;
	}
	for(var i=0; i<str2.length; i++) {
		if(str2.charCodeAt(i)>=65 && str2.charCodeAt(i)<=90)  {
	    	// ascii
	    	//alert("1");
	  	}else if(str2.charCodeAt(i)>=97 && str2.charCodeAt(i)<=122){
	  		//alert("2");
	  	}else if(str2.charCodeAt(i)>=48 && str2.charCodeAt(i)<=57){
	  	} else {
	    	// not ascii
	    	cnt++;
	  	}
	  	if(cnt!=0){
	   		alert("구분상세코드는 영문과 숫자로만 입력이 가능합니다."); 
	   		document.Code.sclCd.focus();
	  		return false;
		} 	
	} 
	return true;
}
	
//코드 입력 사항 확인
function newCode2(){

	if(document.Code.clfNm.value==""){
	  alert("코드명을 입력하세요");
	  document.Code.clfNm.focus();
	  return false;
	}
	
	
	return true;
}
	
function keyEnter() {   
	if(event.keyCode ==13) {   //엔터키가 눌려지면,
		checkSearch(Code);    //검색 함수 호출
	}
}
function reset(){
	Code.keyword.value="";		
	document.Code.channelCode.value="0";	
}
function goPage(pageNo) {
	document.Code.action="<@spring.url '/system/Code.ssc'/>";
	document.Code.pageNo.value = pageNo;
	document.Code.submit();
}

//추가버튼
function insertCode(){
	
	$jq.ajax({
		url: "<@spring.url '/system/insertCode.ssc'/>",
		type: 'GET',
		dataType: 'json',
		
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		 	var table ="";

				 //alert("!");
				 $jq('#layer2').empty();
				 	table += '<article class="sideinfo" ><dl class="btncover7"><dt>코드 정보</dt><dd><table summary="" id="boardview4"><colgroup><col width="80px"></col><col width="207px"></col></colgroup><tbody>'
			 		
            		
            		table += '<tr><th>구분코드</th>'
            		table += '<td><select name="clfCd" class="ip_text ip_text wt180"><option value="">&#160; 선택 &#160;</option>'
			            	for(var i=0; i<data.sv.length; i++){
			            		table += '<option value="'+data.sv[i]+'">&#160; '+data.sv[i]+' &#160;</option>'
			            	}
			                 
		            table +='</select></td></tr>'
		            
            		
            		table += '<tr><th>구분상세코드</th>'
            		table += '<td><input type="text" name="sclCd" class="ip_text wt180"></input></td><span class="btns"></span></tr>'
            		table += '<tr><th>코드명</th><td><input type="text" name="clfNm" class="ip_text wt180"></input><span class="btns"></span></td></tr>' 
					
            		table += '<tr><th>비고1</th>'
            		table += '<td><input type="text" name="rmk1" class="ip_text wt180"></input></td><span class="btns"></span></tr>'
            		table += '<tr><th>비고2</th>'
            		table += '<td><input type="text" name="rmk2" class="ip_text wt180"></input></td><span class="btns"></span></tr>'
	                table += '<tr><th>사용유무</th>'
	                table += '<td><input type="radio" name="useYn" value="Y" checked="checked"/>사용함 &nbsp;<input type="radio" name="useYn" value="N" />사용안함</td></tr>'
	                table += '</tbody></table></dd>'
	                table += '<dd class="btncover11"><span class="btn_pack gry">'	
	                table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'saveCode(this)' '' />
	                table += '</span>'
	                table += '&nbsp;'
	                table +='<span class="btn_pack gry"><a href="#layer2" onclick="showDisHide(\'layer2\',\'1\');return false;">닫기</a></span></dd>'
	                table += '</dl></article>'
	             $jq('#layer2').append(table);  
		}
			
	});
	showDisHide('layer2','0');   
}

//상세보기
function selectCode(id){
	//alert(id);
	
	Code.id.value=id;
	
	$jq.ajax({
		url: "<@spring.url '/system/selectCode.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#Code').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		 	var table ="";

				 //alert("!");
				 $jq('#layer2').empty();
				 	table += '<article class="sideinfo" ><dl class="btncover7"><dt>코드 정보</dt><dd><table summary="" id="boardview4"><colgroup><col width="80px"></col><col width="207px"></col></colgroup><tbody>'
			 				
            		table += '<tr><th>구분코드</th>'
            		table += '<td><select name="clfCd" class="ip_text ip_text wt180"><option value="">&#160; 선택 &#160;</option>'
			            	for(var i=0; i<data.sv.length; i++){
			            		if(data.code.clfCd == data.sv[i]){
			            			table += '<option value="'+data.sv[i]+'" selected>&#160; '+data.sv[i]+' &#160;</option>'
			            		}else{
			            			table += '<option value="'+data.sv[i]+'">&#160; '+data.sv[i]+' &#160;</option>'
			            		}
			            	}
			                 
		            table +='</select></td></tr>'
            		
            		table += '<tr><th>구분상세코드</th>'
            		table += '<td><input type="text" name="sclCd" class="ip_text ip_text wt180" value="'+data.code.sclCd+'" readonly></input></td><span class="btns"></span></tr>'
            		table += '<tr><th>코드명</th><td><input type="text" name="clfNm" class="ip_text ip_text wt180" value="'+data.code.clfNm+'"></input><span class="btns"></span></td></tr>' 
					
            		table += '<tr><th>비고1</th>'
            		table += '<td><input type="text" name="rmk1" class="ip_text ip_text wt180" value="'+(data.code.rmk1==null?'':data.code.rmk1)+'"></input></td><span class="btns"></span></tr>'
            		table += '<tr><th>비고2</th>'
            		table += '<td><input type="text" name="rmk2" class="ip_text ip_text wt180" value="'+(data.code.rmk2==null?'':data.code.rmk2)+'"></input></td><span class="btns"></span></tr>'
	                table += '<tr><th>사용유무</th>'
	                if(data.code.useYn == "Y"){
	                	table += '<td><input type="radio" name="useYn" value="Y" checked="checked"/>사용함 &nbsp;'
	                	table += '<input type="radio" name="useYn" value="N" />사용안함</td></tr>'
	                }else{
	                	table += '<td><input type="radio" name="useYn" value="Y" />사용함 &nbsp;'
	                	table += '<input type="radio" name="useYn" value="N" checked="checked"/>사용안함</td></tr>'
	                }
	                table += '</tbody></table></dd>'
	                table += '<dd class="btncover11"><span class="btn_pack gry">'	
	                table += <@buttonSingle '${search.menuId}' '${user.userId}' 'common.save' 'updateCode(this)' '' />
	                table += '</span>'
	                table += '&nbsp;'
	                table += '<span class="btn_pack gry"><a href="#layer2" onclick="showDisHide(\'layer2\',\'1\');return false;">닫기</a></span></dd>'
	                table += '</dl></article>'
	             $jq('#layer2').append(table);  
		}
			
	});
	showDisHide('layer2','0'); 
}

//코드 저장
function saveCode(){
	if(newCode()){
		document.Code.action="<@spring.url '/system/saveCode.ssc'/>";
		document.Code.submit();
	}
}

//수정
function updateCode(){
	if(newCode2()){
		//document.Code.action="<@spring.url '/system/updateCode.ssc'/>";
		//document.Code.submit();
		
		$jq.ajax({
			url: "<@spring.url '/system/updateCode.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#Code').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	document.Code.action="<@spring.url '/system/Code.ssc'/>";
				document.Code.submit();
			
			}
			
				
		});
	}
}
</script>
<section id="container">
	<form id="Code" name="Code" method="post">
		<@spring.bind "search" />
		
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="menuId" value="${search.menuId}" />
		<input type="hidden" name="id" value="" />
		
    <section id="content">
    	<h3 class="blind">코드 관리</h3>
		<article class="title5"><img src="<@spring.url '/images/title_code.gif' />" title="코드 관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 시스템 관리 &gt; <span class="now">코드 관리</span></nav>
<!-- Popup -->
	<article id="layer2" class="ly_pop11">
	    <article class="sideinfo">
	        <dl class="btncover7">
	        	<dt>코드 정보</dt>
	            <dd>
	            <table summary="" id="boardview4">
	            <colgroup><col width="80px"></col><col width="207px"></col></colgroup>
	            <tbody>
	            
	            <tr><th>구분코드</th>
		            <td>
		            	<select name="clfCd" class="ip_text ip_text wt180">
			            	<option value="">&#160; 선택 &#160;</option>
			            	<#list sv as sv>
			                        	
			            		<option value="${sv}" <#if search.channelCode == sv>selected</#if>>&#160; ${sv} &#160;</option>
			            	</#list>
		            	</select>
		            	
		            </td>
	            </tr>
	            <tr><th>구분상세코드</th>
		            <td>
		            	<input type="text" name="sclCd" value="" class="ip_text wt180"></input><span class="btns"></span>
		            </td>
	            </tr>
	            <tr><th>코드명</th>
		            <td>
		            	<input type="text" name="clfNm" value="" class="ip_text wt180"></input><span class="btns"></span>
		            </td>
	            </tr>
	            <tr><th>비고1</th>
		            <td>
		            	<input type="text" name="rmk1" value="" class="ip_text wt180"></input><span class="btns"></span>
		            </td>
	            </tr>
	            <tr><th>비고2</th>
		            <td>
		            	<input type="text" name="rmk2" value="" class="ip_text wt180"></input><span class="btns"></span>
		            </td>
	            </tr>
	            <tr><th>사용유무</th>
	        		<td>
	                	<input type="radio" name="useYn" value="Y" checked="checked"/>사용함 &nbsp;
	                	<input type="radio" name="useYn" value="N" />사용안함
	            	</td>
	            </tr>
	            
	            </tbody>
	            </table>
	            
				</dd>
				<dd class="btncover11">
                	<span class="btn_pack gry">
                	<#-- 
                		<a href="javascript:" onclick="saveProfile(this)">저장</a>
                		-->
                		<@button '${search.menuId}' '${user.userId}' 'common.save' 'saveCode(this)' '' />
                	</span>
                	<span class="btn_pack gry">
                		<a href="#layer3" onclick="showDisHide('layer2','1');return false;">닫기</a>
                	</span>
                </dd>
	            
	        </dl>
	    </article>
	    
    </article>
    
<!-- //Popup -->      
<!-- Table -->
        <article class="bbsview3 bd2">
        <table summary="" id="boardview3">
        <colgroup><col width="90px"></col><col width="80px"></col><col width=""></col><col width="100px"></col><col width="70px"></col><col width="70px"></col><col width="313xp"></col></colgroup>
            <thead>
	            <tr><th colspan="7">
	            	<article class="box2">
	                    <dl>
	                    	<dt>구분코드</dt>
	                    	<dd>
	                        	<select name="channelCode">
		                        	<option value="0">&#160; 전체 &#160;</option>
		                        	
		                        	<#list sv as sv>
			                        	
						            		<option value="${sv}" <#if search.channelCode == sv>selected</#if>>&#160; ${sv} &#160;</option>
					            		
					            	</#list>
		                        </select>
	                        </dd>
	                        <dt>코드명</dt>
	                        <dd><input type="text" class="wt120" name="keyword" value="<#if search.keyword?exists>${search.keyword}</#if>" onKeyDown="keyEnter();"></input></dd>
	                    </dl>
	                    <article class="btncover8">
	                    <span class="btn_pack gry"><a href="javascript:"  onclick="checkSearch(this)">검색</a></span>
	                    <span class="btn_pack gry"><a href="javascript:"  onclick="reset()">초기화</a></span>
	                    </article>
	                </article>
	            </th></tr>
            </thead>
<!-- list -->
            <tbody>
	            <tr><th>구분</th><th>구분코드</th><th>구분상세코드</th><th>코드명</th><th>비고1</th><th>비고2</th><th></th></tr>
	            <#assign x = 12>  
	            <#assign y = x>
		        <#list codes.items as code>
		        	<#if y%2==0>
		            <tr class="gry">
		            <#else>
		            <tr>
		            </#if>
	        	        
						<td>${tpl.getCodeDesc("CDNM",code.clfCd!"")}</td>
			            <td>${code.clfCd!""}</td>
			            <td>${code.sclCd!""}</td>
			            <td align="left">
			            	&nbsp;<a href="javascript:" onclick="selectCode('${code.clfCd!""}/${code.sclCd!""}')">${code.clfNm!""}</a>
			            </td>
			            <td>${code.rmk1!""}</td>
			            <td>${code.rmk2!""}</td>
			            <#if y == 12>  
			            	<td rowspan="12">
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
						            <td></td>
						            <td></td>
						            <td></td>
						            <td></td>
						            <td></td>
						            <#if y==12>      
							            <td rowspan="12">
		
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
		 <@button '${search.menuId}' '${user.userId}' 'common.insert' 'insertCode();' '' />
		</span>
	</article>
<!-- paginate -->
	    <@paging codes search.pageNo '' />
	<!-- //paginate -->	
	</section>
</form>