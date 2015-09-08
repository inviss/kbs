<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">

function tab(value){
	if(value == "1"){
		document.Meta.action="<@spring.url '/workflow/Transmission.ssc'/>";	
		document.Meta.pageNo.value="1";
		document.Meta.submit();
	}else{
		document.Meta.action="<@spring.url '/workflow/MetaURL.ssc'/>";	
		document.Meta.pageNo.value="1";
		document.Meta.submit();
	}
}

function ajaxData(){
	$jq.ajax({
		url: "<@spring.url '/workflow/ajaxMetaURL.ssc' />",
		type: 'POST',
		dataType: 'json',
		data: $jq('#Meta').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		
		var table ="";
		
		 $jq('#viewData').empty();
	 		table +='<tr><th>No</th><th>프로그램명</th><th>프로그램ID</th><th>방송일</th><th>프로파일명</th><th>사업자</th><th>상태</th><th>재요청</th></tr>'
	 		for(var i=0;i<data.contents.length;i++){
 			    if(i%2==0){
		 			table += '<tr class="gry">'
		 		}else{
		 			table += '<tr>'
		 		}
	 			table += '<td>'+isEmpty(data.contents[i].seq)+'</td>'
		 		table += '<td align="left">&nbsp;'+isEmpty(data.contents[i].pgmNm)+'</td>'
		 		table += '<td>'+isEmpty(data.contents[i].pgmId)+'-'+isEmpty(data.contents[i].segmentId)+'</td>'
		 		table += '<td>'+isEmpty(data.contents[i].brd)+'</td>'
		 		table += '<td align="left">&nbsp;'+isEmpty(data.contents[i].proFlnm)+'</td>'
		 		table += '<td align="left">&nbsp;'+isEmpty(data.contents[i].company)+'</td>'
	 			
	 			if(data.contents[i].workStatcd == "000"){
			    	table += '<td>대기</td>'
			    }else if(data.contents[i].workStatcd == "001"){
			    	table += '<td>요청</td>'
			    }else if(data.contents[i].workStatcd == "002"){
			    	table += '<td>진행</td>'
			    }else if(data.contents[i].workStatcd == "200"){
			    	table += '<td>완료</td>'
			    }else if(data.contents[i].workStatcd == "300"){
			    	table += '<td>진행오류</td>'
			    }else if(data.contents[i].workStatcd == "400"){
			    	table += '<td>서버오류</td>'
			    }else if(data.contents[i].workStatcd == "500"){
			    	table += '<td>읽기/쓰기 오류</td>'
			    }else{
			    	table += '<td>기타오류</td>'
			    }
	 			
	 			if(isEmpty(data.contents[i].metaIns) == "E"){
	 				table += '<td><input type="button" value="재요청" class="btn_qcr" onclick="javascript:request('+isEmpty(data.contents[i].seq)+');"  ></td>'
	 			}else{
	 				table += '<td></td>'
	 			}
	 			table += '</tr>'
	 		}
		 		
			 $jq('#viewData').append(table);
		}
			
	});
	refreshTime();
}
 
function request(value){
	//alert(value);
	var check = confirm("재요청 하시겠습니까?");
	
	if(check == true){
		Meta.seq.value = value;
		$jq.ajax({
			url: "<@spring.url '/workflow/metaRequest.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#Meta').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	//alert("Y");
			 	document.Meta.action="<@spring.url '/workflow/MetaURL.ssc'/>";
				document.Meta.submit();
			
			}
			
				
		}); 
	}
}
 
function refreshTime(){
		  window.setTimeout("ajaxData()", 1000 * 5 );
}
	
function goPage(pageNo) {

		document.Meta.action="<@spring.url '/workflow/MetaURL.ssc'/>";
		document.Meta.pageNo.value = pageNo;
		document.Meta.submit();
}

function isEmpty(value){
	if(typeof(value)=='number'){    
	  	return (value === undefined || value == null || value.length <= 0) ? 0 : value;
	  }else{
	  	return (value === undefined || value == null || value.length <= 0) ? "" : value;
	  } 
 }
		
window.onload = function(){
	ajaxData(); 
}
</script>
<section id="container">
	<form id="Meta" name="Meta" method="post">
		<@spring.bind "search" />
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="menuId" value="${search.menuId}">
		<input type="hidden" name="seq" value="">
		
		<input type="hidden" name="channelCode" value="${search.channelCode!""}">
    	<input type="hidden" name="workStat" value="${search.workStat!""}">
    	<#if search.startDt?exists>
    	<input type="hidden" name="startDt" value="${search.startDt?string("yyyy-MM-dd")}">
    	<#else>
    	<input type="hidden" name="startDt" value="">
    	</#if>
    	<#if search.endDt?exists>
    	<input type="hidden" name="endDt" value="${search.endDt?string("yyyy-MM-dd")}">
    	<#else>
    	<input type="hidden" name="endDt" value="">
    	</#if>
    	<input type="hidden" name="searchDayName" value="${search.searchDayName!""}">
    	<input type="hidden" name="keyword" value="${search.keyword!""}">
    	
    <section id="content">
    	<h3 class="blind">전송관리(트랜스퍼)</h3>
		<article class="title"><img src="<@spring.url '/images/title_transmanage.gif' />" title="전송관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 워크플로우 관리 &gt; <span class="now">전송관리</span></nav>
        <article class="tabmenu">
            <ul class="tab1">
             	<li class="off"><a href="#" onclick="javascript:tab('1');"><span>전송관리</span></a></li>
            	<li class="on"><a href="#" onclick="javascript:tab('2');"><span>Meta URL</span></a></li>
            
            </ul>
            
        </article>

<!-- Table -->
        <article class="bbsview3 bd2">
        <table summary="" id="boardview3">
        <colgroup><col width="60px"></col><col width=""></col><col width="170px"></col><col width="80px"></col><col width="160px"></col><col width="140px"></col><col width="80px"></col><col width="70px"></col></colgroup>

<!-- list -->
            <tbody id="viewData">
	            <tr><th>No</th><th>프로그램명</th><th>프로그램ID</th><th>방송일</th><th>프로파일명</th><th>사업자</th><th>상태</th><th>재요청</th></tr>
	            <#list contents.items as content>
	               <#if content.seq%2==0>
		            <tr class="gry">
		            <#else>
		            <tr>
		            </#if>
		            	<td>${content.seq!""}</td>
		            	<td align="left">&nbsp;${content.pgmNm!""}</td>
		            	<td>${content.pgmId!""}-${content.segmentId!""}</td>
		            	<td>${content.brd!""}</td>
			            <td align="left">&nbsp;${content.proFlnm!""}</td>
		            	<td align="left">&nbsp;${content.company!""}</td>
		            	<td>${ tpl.getCodeDesc("WORK", content.workStatcd!"")}</td>
		            	<td></td>
		            </tr>
	            </#list>	
	            </tr>	
            </tbody>
<!-- //list -->
    </table>     
    </article>
<!-- //Table -->
	
	<@paging contents search.pageNo '' />
	</section>
</form> 