<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">

<!--


	
	
	// 검색 
	function checkSearch(){ 
   		
		document.OtherSystem.action="<@spring.url '/system/OtherSystem.ssc'/>";
		document.OtherSystem.pageNo.value="1";
		document.OtherSystem.submit();
	} 
	
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
			checkSearch(OtherSystem);    //검색 함수 호출
		}
	}

	
	//추가버튼
	function insertOthersystem(){
		//alert("1");
		$jq.ajax({
			url: "<@spring.url '/system/insertOthersystem.ssc'/>",
			type: 'GET',
			dataType: 'json',
			
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
			 	
					
					 $jq('#layer2').empty();
					 	table += '<article class="sideinfo"><dl class="btncover7"><dt>타 시스템 정보</dt><dd>'
			            table += '<table summary="" id="boardview4"><colgroup><col width="80px"></col><col width="207px"></col></colgroup><tbody>'
			            table += '<tr><th>장비선택</th><td><select name="device" class="ip_text wt180">'
	                	for(var i=0; i< data.codetbls.length; i++){
	                		table += '<option value="'+data.codetbls[i].sclCd+'">'+data.codetbls[i].clfNm+'</option>'
	                	}
	                	
                		table += '</select></td></tr>'
			            table += '<tr><th colspan="2">'
			            table += '<input type="radio" name="savaSelect" value="newDevice" onclick="access_click(this.value);" />장비 추가 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
			            table += '<input type="radio" name="savaSelect" value="newEq" onclick="access_click(this.value);" />인스턴스 추가</th></tr>'
			            table += '</tbody></table></article><article id="layer007" class="sideinfo"></article>'
			          
		                	 
					$jq('#layer2').append(table);
				
			}
				
		});
		showDisHide('layer2','0');   
	}
	
	function reset(){
		OtherSystem.keyword.value="";		
				
	}

	// 입력 사항 확인
	function newOtherSystem(){
		
		if(document.OtherSystem.deviceNm.value==""){
		  alert("장비명을 입력하세요");
		  document.OtherSystem.deviceNm.focus();
		  return false;
		}
		
		if(document.OtherSystem.deviceIp.value==""){
		  alert("IP을 입력하세요");
		  document.OtherSystem.deviceIp.focus();
		  return false;
		}
		
		
		
		return true;
	}

	//디바이스추가
	function saveOthersystem(){
		if(newOtherSystem()){
			document.OtherSystem.action="<@spring.url '/system/saveOthersystem.ssc'/>";
			document.OtherSystem.submit();
		}
	}
	
	//인스턴스추가
	function saveinstance(){
		
			document.OtherSystem.action="<@spring.url '/system/saveinstance.ssc'/>";
			document.OtherSystem.submit();
		
	}
	
	
	//빈값 체크하는 함수
	function isEmpty(value){
		if(typeof(value)=='number'){    
		  	return (value === undefined || value == null || value.length <= 0) ? 0 : value;
		  }else{
		  	return (value === undefined || value == null || value.length <= 0) ? "" : value;
		  } 
	 }
	
	
	
	
	
	//타 시스템 상세보기
	function othersystemView(id){
		//alert(id);
		
		OtherSystem.id.value=id;
		
		$jq.ajax({
			url: "<@spring.url '/system/selectOther.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#OtherSystem').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
			 	
					
					 $jq('#layer2').empty();
					 	table += '<article class="sideinfo"><dl class="btncover7"><dt>타 시스템 정보</dt><dd>'
			            table += '<table summary="" id="boardview4"><colgroup><col width="80px"></col><col width="207px"></col></colgroup><tbody>'
			            table += '<tr><th>장비명</th><td><input type="text" name="deviceNm" class="ip_text wt180" value="'+data.eq.deviceNm+'"></input></td></tr>'
			            table += '<tr><th>장비ID</th><td><input type="text" name="deviceid" class="ip_text wt180" value="'+data.eq.deviceid+'" readonly></input></td></tr>'
			            table += '<tr><th>장비코드</th><td><input type="text" name="eqPsCd" class="ip_text wt180" value="'+data.eq.eqPsCd+'" readonly></input></td></tr>'
			            table += '<tr><th>IP</th><td><input name="deviceIp" type="text" class="ip_text2 wt180" value="'+data.eq.deviceIp+'"></input></td></tr>'
			       		table += '<tr><th>Port</th><td><input name="devicePort" type="text" class="ip_text2 wt180" value="'+isEmpty(data.eq.devicePort)+'"></input></td></tr>'
			            table += '</tbody></table></dd><dd class="btncover6">'
			            table += '<span class="btn_pack gry"><a href="javascript:" onclick="updateOthersystem(this)">저장</a></span> <span class="btn_pack gry"><a href="#layer2" onclick="showDisHide(\'layer2\',\'1\');return false;">닫기</a></span>'
			            table += '</dd></dl></article>'
		                	 
					$jq('#layer2').append(table);
				
			}
				
		});
		showDisHide('layer2','0');  
		
	}
	
	//수정
	function updateOthersystem(){
		if(newOtherSystem()){
			//document.OtherSystem.action="<@spring.url '/system/updateOsi.ssc'/>";
			//document.OtherSystem.submit();
			
			$jq.ajax({
			url: "<@spring.url '/system/updateOsi.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#OtherSystem').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	document.OtherSystem.action="<@spring.url '/system/OtherSystem.ssc'/>";
				document.OtherSystem.submit();
			
			}
			
				
		});
		}	
	}
	
	// 삭제
	function deleteOsi(){
	
		var obj = document.forms["OtherSystem"];
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
		
		//document.OtherSystem.action="<@spring.url '/system/deleteOsi.ssc'/>";
		//document.OtherSystem.submit();
	
		$jq.ajax({
			url: "<@spring.url '/system/deleteOsi.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#OtherSystem').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	document.OtherSystem.action="<@spring.url '/system/OtherSystem.ssc'/>";
				document.OtherSystem.submit();
			
			}
			
				
		});		
	}
	
	function goPage(pageNo) {
		document.OtherSystem.action="<@spring.url '/system/OtherSystem.ssc'/>";
		document.OtherSystem.pageNo.value = pageNo;
		document.OtherSystem.submit();
	}
	
	function access_click(value){
		//alert(value);
		
		var device =OtherSystem.device.value;
		
		
		
		$jq.ajax({
			url: "<@spring.url '/system/selectDeviceEqps.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#OtherSystem').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
			 	
					
					 $jq('#layer007').empty();
					 
					 if(value == "newDevice"){
					 	table += '<dl class="btncover7">'
			            table += '<table summary="" id="boardview4"><colgroup><col width="80px"></col><col width="207px"></col></colgroup><tbody>'
			            table += '<tr><th>장비명</th><td><input type="text" name="deviceNm" class="ip_text wt180" value=""></input></td></tr>'
			            table += '<tr><th>IP</th><td><input name="deviceIp" type="text" class="ip_text2 wt180" value=""></input></td></tr>'
			            table += '<tr><th>Port</th><td><input name="devicePort" type="text" class="ip_text2 wt180" value=""></input></td></tr>'
			            table += '</tbody></table><dd class="btncover6">'
			            table += '<span class="btn_pack gry"><a href="javascript:" onclick="saveOthersystem(this)">저장</a></span> <span class="btn_pack gry"><a href="#layer2" onclick="showDisHide(\'layer2\',\'1\');return false;">닫기</a></span>'
			            table += '</dd></dl>'
			         }else{
			         	
			         	if(device =="TC" || device=="AC" || device=="TF"){
				         	table += '<dl class="btncover7">'
				            table += '<table summary="" id="boardview4"><colgroup><col width="80px"></col><col width="207px"></col></colgroup><tbody>'
				            table += '<tr><th>인스턴스를</br>추가할장비</th><td>'
				            
				            
				            table += '<select name="deviceNm" class="ip_text wt180">'
		             		
		             		var j = data.code.rmk2;
		             		for(var i=0; i<j; i++){
			                	table += '<option value="'+data.code.sclCd+','+(i+1)+'">'+data.code.clfNm+' #'+(i+1)+'</option>'
			                }	
			                
			                table += '</select>'
				            
				           
				            table += '</td></tr>'
				            table += '</tbody></table><dd class="btncover6">'
				            table += '<span class="btn_pack gry"><a href="javascript:" onclick="saveinstance(this)">저장</a></span> <span class="btn_pack gry"><a href="#layer2" onclick="showDisHide(\'layer2\',\'1\');return false;">닫기</a></span>'
				            table += '</dd></dl>'
			            }else{
			            	alert("트랜스코더, 트랜스퍼 서버만 인스턴스 추가가 가능합니다.");
			            }
			         }   
		                	 
					$jq('#layer007').append(table);
				
			}
				
		});
		showDisHide('layer007','0');  
	}
//-->
</script>
<section id="container">
	<form id="OtherSystem" name="OtherSystem" method="post">
		<@spring.bind "search" />
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="id" value="" />
		<input type="hidden" name="eqcd" value="" />
    <section id="content">
    	<h3 class="blind">타 시스템 관리</h3>
		<article class="title5"><img src="<@spring.url '/images/title_othersystem.gif' />" title="타 시스템 관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 시스템 관리 &gt; <span class="now">타 시스템 관리</span></nav>
<!-- Popup -->
	<article id="layer2" class="ly_pop11">
	    <article class="sideinfo">
	        <dl class="btncover7">
	        	<dt>타 시스템 정보</dt>
	            <dd>
	            <table summary="" id="boardview4">
	            <colgroup><col width="80px"></col><col width="207px"></col></colgroup>
	            <tbody>
	            <tr><th>장비선택</th><td>
	            	<select name="device" class="ip_text wt180">
	             		<#list codetbls as codetbl> 
	             			<option value="${codetbl.sclCd!""}">${codetbl.clfNm!""}</option>
	             		</#list>
	             		
	                	
	                </select>
	            
	            </td></tr>
	            <tr><th colspan="2">
	                <input type="radio" name="savaSelect" value="newDevice" onclick="access_click(this.value);" />장비 추가 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                <input type="radio" name="savaSelect" value="newEq" onclick="access_click(this.value);" />인스턴스 추가
	            	
	            </th></tr>
	            
	            </tbody>
	            </table>
	            
				</dd>
				
	            
	        </dl>
	    </article>
	    <article id="layer007" class="sideinfo">
		</article>
    </article>
    
<!-- //Popup -->    
<!-- Table -->
        <article class="bbsview3 bd2">
        <table summary="" id="boardview3">
        <colgroup><col width="30px"></col><col width="130px"></col><col width="130px"></col><col width="80px"></col><col width=""></col><col width="313"></col></colgroup>
            <thead>
	            <tr><th colspan="6">
	            	<article class="box2">
	                    <dl>
	                        <dt>장비명</dt>
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
	            <tr><th></th><th>장비분류</th><th>장비명</th><th>장비인스턴스</th><th>IP</th><th></th></tr>
	            <#assign x = 12>  
	            <#assign y = x>
		        <#list contents.items as content> 
		        	<#if y%2==0>
		            <tr class="gry">
		            <#else>
		            <tr>
		            </#if>
			            <td><input name="check" type="checkbox" value="${content.deviceid!""}/${content.eqPsCd!""}"></td>
			            
			            <td align="left">&nbsp;	${ tpl.getCodeDesc("DECD",content.deviceid?substring(0,2))}</td>
			            	
			            <#--<td>${content.deviceid!""}//${content.deviceid?substring(0,2)}</td>-->
			            <td align="left">&nbsp;<a href="javascript:" onclick="othersystemView('${content.deviceid!""}/${content.eqPsCd!""}')">${content.deviceNm!""}</a></td>
			            <td>${content.eqPsCd!""}</td>			            
			            <td>${content.deviceIp!""}</td>
			            <#if y == 12> 
		            	<td rowspan="12">
						<!-- side information -->
		
						<!-- //side information -->
		    	        </td>
		    	    	</#if>
		            </tr>
		            <#assign y= y -1>	     
			        </#list>
			        <#if y==0>
			        <#else>
				        <#list 1..y  as i >
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
				            <#if y == 12> 
				            	<td rowspan="12">
								<!-- side information -->
				
								<!-- //side information -->
				    	        </td>
			    	    	</#if>
			            </tr>
			            <#assign y= y -1>
			            </#list>
					</#if>
            </tbody>
<!-- //list -->
    </table>     
    </article>
<!-- //Table -->
	<article class="btncover4">
		<span class="btn_pack gry">
		<@button '${search.menuId}' '${user.userId}' 'common.insert' 'insertOthersystem();' '' />
		</span>
	<span class="btn_pack gry">
	<@button '${search.menuId}' '${user.userId}' 'common.delete' 'deleteOsi();' '' />
	</article>
<!-- paginate -->
	    <@paging contents search.pageNo '' />
	<!-- //paginate -->
	</section>
</form>