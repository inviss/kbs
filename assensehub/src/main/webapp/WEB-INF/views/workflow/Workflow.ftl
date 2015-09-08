<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl">
<script type="text/javascript">
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
function ajaxData(){
	$jq.ajax({
		url: "<@spring.url '/workflow/ajaxWorkflow.ssc' />",
		type: 'POST',
		dataType: 'json',
		data: $jq('#listData').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		
		 var ingestTable ="";
		 var trimmingTable ="";
		 var transcorderTable="";
		 var transferTable="";
		 
		 //-- ajax 실시간 데이타  (인제스트) by dekim
			 $jq('#ingest').empty();
		 		ingestTable +='<tr><th>No</th><th>인제스트</th><th>인제스트 IP</th><th>컨텐츠 명</th><th>상태</th></tr>' 
				var j= data.contents.length;
				for(var i=0;i<data.contents.length;i++){
				 	if(data.contents[i].deviceid.substring(0,2)=="LI"){
				 		if(j%2==0){
				 			ingestTable += '<tr class="gry">'
				 		}else{
				 			ingestTable += '<tr>'
				 		}
				 	
				 		ingestTable += '<td>'+data.contents[i].deviceid+'</td>'
				 		ingestTable += '<td align="left">&nbsp;'+data.contents[i].deviceNm+'</td>'
				 		ingestTable += '<td>'+data.contents[i].deviceIp+'</td>'
				 		ingestTable += '<td align="left">&nbsp;'+isEmpty(data.contents[i].ctNm)+'</td>'
			 			if(data.contents[i].workStatcd=='001'){
				 			ingestTable += '<td>대기중</td>'
				 		}else if(data.contents[i].workStatcd=='002'){
				 			ingestTable += '<td>진행중</td>'
				 		}else if(data.contents[i].workStatcd=='003'){
				 			ingestTable += '<td>완료</td>'
				 		}else{
				 			ingestTable += '<td></td>'
				 		}
				 		//ingestTable += '<td><article class="ly_loading"><p class="dsc_loading_no"><span class="progress">'
						//ingestTable += '<em>'+data.contents[i].prgrs+'</em>%</span></p><article class="loading_bar">'
					    //ingestTable += '<span style="width:'+data.contents[i].prgrs+'%;"></span></article></article></td></tr>'
					 						 	
				 	}
				 	j=j-1;
			 	}
			 $jq('#ingest').append(ingestTable);
            
		 //-- ajax 실시간 데이타  (트리밍) by dekim
             $jq('#trimming').empty();
		 		trimmingTable +='<tr><th>No</th><th>트리밍</th><th>컨텐츠 명</th><th>상태</th></tr>' 
				for(var i=0;i<data.contents.length;i++){
				 	if(data.contents[i].deviceclfCd=='0002'){
				 		if(parseInt(data.contents[i].deviceid.substring(2,4),10)%2==0){
				 			trimmingTable += '<tr class="gry">'
				 		}else{
				 			trimmingTable += '<tr>'
				 		}
				 	
				 		trimmingTable += '<td>'+data.contents[i].deviceid+'</td>'
				 		trimmingTable += '<td>'+isEmpty(data.contents[i].deviceNm)+'</td>'
				 		trimmingTable += '<td>'+isEmpty(data.contents[i].ctNm)+'</td>'
				 		
				 		
				 		if(data.contents[i].workStatcd=='001'){
				 			trimmingTable += '<td>대기중</td>'
				 		}else if(data.contents[i].workStatcd=='002'){
				 			trimmingTable += '<td>진행중</td>'
				 		}else if(data.contents[i].workStatcd=='003'){
				 			trimmingTable += '<td>완료</td>'
				 		}else{
				 			trimmingTable += '<td></td>'
				 		}
				 		//ingestTable += '<td><article class="ly_loading"><p class="dsc_loading_no"><span class="progress">'
						//ingestTable += '<em>'+data.contents[i].prgrs+'</em>%</span></p><article class="loading_bar">'
					    //ingestTable += '<span style="width:'+data.contents[i].prgrs+'%;"></span></article></article></td></tr>'
					 						 	
				 	}
			 	}
			 	
			 $jq('#trimming').append(trimmingTable);
			 
			      
		 //-- ajax 실시간 데이타  (트랜스코더) by dekim
             $jq('#transcorder').empty();
		 		transcorderTable +='<tr><th>No</th><th>트랜스코더 명</th><th>트랜스코더 IP</th><th>컨텐츠 명</th><th>상태</th></tr>' 
				var j=data.contents.length;
				for(var i=0;i<data.contents.length;i++){
				 	
				 	
				 	
				 	if(data.contents[i].deviceid.substring(0,2)=="TC"){
				 		if(j%2==0){
				 			transcorderTable += '<tr class="gry">'
				 		}else{
				 			transcorderTable += '<tr>'
				 		}
				 	
				 		transcorderTable += '<td>'+data.contents[i].deviceid+'</td>'
				 		transcorderTable += '<td align="left">&nbsp;'+data.contents[i].deviceNm+'</td>'
				 		transcorderTable += '<td>'+data.contents[i].deviceIp+'</td>'
				 		transcorderTable += '<td align="left">&nbsp;'+isEmpty(data.contents[i].ctNm)+'</td>'
				 		transcorderTable += '<td><article class="ly_loading"><p class="dsc_loading_no"><span class="progress">'
						transcorderTable += '<em>'+data.contents[i].prgrs+'</em>%</span></p><article class="loading_bar">'
					    transcorderTable += '<span style="width:'+data.contents[i].prgrs+'%;"></span></article></article></td></tr>'
				 	}
				 	j=j-1;
			 	}
			 	
			 $jq('#transcorder').append(transcorderTable);
			 
			  //-- ajax 실시간 데이타  (트랜스퍼) by dekim
             $jq('#transfer').empty();
		 		transferTable +='<tr><th>No</th><th>트랜스퍼 명</th><th>트랜스퍼 IP</th><th>컨텐츠 명</th><th>상태</th></tr>' 
				var j=data.contents.length;
				for(var i=0;i<data.contents.length;i++){
				 	if(data.contents[i].deviceid.substring(0,2)=="TF"){
				 		if(j%2==0){
				 			transferTable += '<tr class="gry">'
				 		}else{
				 			transferTable += '<tr>'
				 		}
				 		transferTable += '<td>'+data.contents[i].deviceid+'</td>'
				 		transferTable += '<td align="left">&nbsp;'+data.contents[i].deviceNm+'</td>'
				 		transferTable += '<td>'+data.contents[i].deviceIp+'</td>'
				 		transferTable += '<td align="left">&nbsp;'+isEmpty(data.contents[i].ctNm)+'</td>'
				 		transferTable += '<td><article class="ly_loading"><p class="dsc_loading_no"><span class="progress">'
						transferTable += '<em>'+data.contents[i].prgrs+'</em>%</span></p><article class="loading_bar">'
					    transferTable += '<span style="width:'+data.contents[i].prgrs+'%;"></span></article></article></td></tr>'
				 	}
				 	j=j-1;
			 	}
			 	
			 $jq('#transfer').append(transferTable);
		}
			
	});
	
	refreshTime();
}
function refreshTime(){
		  window.setTimeout("ajaxData()", 1000 * 2 );
	}
	
window.onload = function(){
    ajaxData();
}
</script>
<section id="container">
	<form name="search" method="post">
    <section id="content">
    	<h3 class="blind"></h3>
		<article class="title5"><img src="<@spring.url '/images/title_workflow.gif' />" title="워크플로우 관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 워크플로우 관리 &gt; <span class="now">워크플로우 관리</span></nav>
<!-- Table -->
		<#--
		<article class="title2">인제스트</article>
        <article class="bbsview3">
        <table summary="" id="boardview3">
        <colgroup><col width="50px"></col><col width="200px"></col><col width="100px"></col><col width=""></col><col width="50px"></col></colgroup>
            <tbody id="ingest">
	            <tr><th>No</th><th>인제스트</th><th>인제스트 IP</th><th>컨텐츠 명</th><th>상태</th></tr>
	             <#list contents as content>
 	            	 <#if content.deviceid?substring(0,2) == "LI">
						<#if content.deviceid?substring(2,4)?number%2==0>
			            <tr class="gry">
			            <#else>
			            <tr>
		           		 </#if>
			            <td>${content.deviceid!""}</td>
			            <td>${content.deviceNm!""}</td>
			            <td>${content.ctNm!""}</td>
			            <td>${content.workStatcd!""}</td>
			            </tr>
		            </#if>
	            </#list>
            </tbody>
        </table>     
        </article>
        
		<article class="box5">
				<article class="title2">트리밍</article>
	        <article class="bbsview4 bd2">
	        <table summary="" id="boardview3">
	        <colgroup><col width="50px"></col><col width="100px"></col><col width=""></col><col width="50px"></col></colgroup>
	            <tbody id="trimming">
	            <tr><th>No</th><th>트리밍</th><th>컨텐츠 명</th><th>상태</th></tr>
	            <#list contents as content>
	            	 <#if content.deviceclfCd=="0002">
		              	<#if content.deviceid?substring(2,4)?number%2==0>
			            <tr class="gry">
			            <#else>
			            <tr>
		           		 </#if>
			            <td>${content.deviceid!""}</td>
			            <td>${content.deviceNm!""}</td>
			            <td>${content.ctNm!""}</td>
			            <td>${content.workStatcd!""}</td>
			            </tr>
		            </#if>
	            </#list>
	            </tbody>
	        </table>     
	        </article>
        </article>
    	-->
        <article class="bbsview3">
			<article class="title2">트랜스코더</article>
	        <table summary="" id="boardview3">
	        	<colgroup><col width="50px"></col><col width="200px"></col><col width="130px"></col><col width="200px"></col><col width=""></col></colgroup>
	            <tbody id="transcorder">
		            <tr><th>No</th><th>트랜스코더 명</th><th>트랜스코더 IP</th><th>컨텐츠 명</th><th>상태</th></tr>
		              <#list contents as content>
	            	 <#if content.deviceid?substring(0,2) == "TC">
		              	<#if content.deviceid?substring(2,4)?number%2==0>
			            <tr class="gry">
			            <#else>
			            <tr>
		           		 </#if>
			            <td>${content.deviceid!""}</td>
			            <td>${content.deviceNm!""}</td>
			            <td>${content.deviceIp!""}</td>
			            <td>${content.ctNm!""}</td>
			            <td> 
				            <article class="ly_loading">
					            <p class="dsc_loading_no">
					            <span class="progress">
					            <em>${content.prgrs!"0"}</em>%</span>
					            </p>
					            <article class="loading_bar">
					            	<span style="width:${content.prgrs!"0"}%;"></span>
					            </article>
				            </article>
			            </td>
		                 </tr>
		            </#if>
	            </#list>
	            </tbody>
	    </table>     
    </article>
    <article class="bbsview3">
			<article class="title2">트랜스퍼</article>
	        <table summary="" id="boardview3">
	        	<colgroup><col width="50px"></col><col width="200px"></col><col width="130px"></col><col width="200px"></col><col width=""></col></colgroup>
	            <tbody id="transfer">
		            <tr><th>No</th><th>트랜스퍼 명</th><th>트랜스퍼 IP</th><th>컨텐츠 명</th><th>상태</th></tr>
		              <#list contents as content>
	            	 <#if content.deviceid?substring(0,2) == "FC">
	              		<#if content.deviceid?substring(2,4)?number%2==0>
			            <tr class="gry">
			            <#else>
			            <tr>
		           		 </#if>
			            <td>${content.deviceid!""}</td>
			            <td>${content.deviceNm!""}</td>
			            <td>${content.deviceIp!""}</td>
			            <td>${content.ctNm!""}</td>
			            <td> 
				            <article class="ly_loading">
					            <p class="dsc_loading_no">
					            <span class="progress">
					            <em>${content.prgrs!"0"}</em>%</span>
					            </p>
					            <article class="loading_bar">
					            	<span style="width:${content.prgrs!"0"}%;"></span>
					            </article>
				            </article>
			            </td>
		                 </tr>
		            </#if>
	            </#list>
	            </tbody>
	    </table>     
    </article>
<!-- //Table -->
</section>
</form>