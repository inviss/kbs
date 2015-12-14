<#include "/WEB-INF/views/includes/commonTags.ftl">
<script type="text/javascript">
// getCookie
function getCookie(name){
        var arg = name + "=";
        var alen = arg.length;
        var clen=document.cookie.length;
        var i=0;
        
        while(i< clen){
                var j = i+alen;
                if(document.cookie.substring(i,j)==arg){
                        var end = document.cookie.indexOf(";",j);
                        if(end== -1)
                                end = document.cookie.length;
                        return unescape(document.cookie.substring(j,end));
                }
        i=document.cookie.indexOf(" ",i)+1;
        if (i==0) break;
        }
        return null;
}
// setCookie
function setCookie(name,value,expires){
        document.cookie=name + "=" + escape(value)+
        ((expires == null)? "" : (" ; expires=" + expires.toGMTString()));
}
function setcookieVariables(name){
        var today = new Date();
        var expires=new Date();
        expires.setTime(today.getTime()+ 1000*60*60*24*365);
        setCookie("MyCoolCookiePage",name,expires)
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
function ajaxRightData(){
	
	var obj = ($jq('#rightData').get(0));
	var yourname = getCookie("MyCoolCookiePage")
	$jq('#avGubun').attr("value",yourname);
	$jq.ajax({
		url: "<@spring.url '/ajax/workflow/ajaxWorkflowAv.ssc' />",
		type: 'POST',
		dataType: 'json',
		data: $jq('#rightData').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		
		 $jq('#avGubun').attr("value",data.avGubun);
		 var ingestTable ="";
		 
		 //-- ajax 디바이스 정보 취압 (인제스트) by dekim
			 $jq('#listTable').empty();
		 		ingestTable +='<colgroup><col width="140px"></col><col></col></colgroup>' 
				for(var i=0;i<data.contents.length;i++){

					if(data.avGubun=="Video"){
						if(data.contents[i].deviceid.substring(0,2)=="TC" || data.contents[i].deviceid.substring(0,2)=="TF"){
							ingestTable +='<tr><th>'+isEmpty(data.contents[i].deviceNm)+'</th>'
						 		if(data.contents[i].workStatcd=='001'){
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_wait.gif' />" title="wait"/><td>'
						 		}else if(data.contents[i].workStatcd=='002'){
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_busy.gif' />" title="busy"/><td>'
						 		}else if(data.contents[i].workStatcd=='003'){
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_hyphen.gif' />" title="-"/><td>'
						 		}else{
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_hyphen.gif' />" title="-"/><td>'
						 		}
					 		ingestTable += '</tr>'
						}
					}else if(data.avGubun=="Audio"){
						if(data.contents[i].deviceid.substring(0,2)=="AC" || data.contents[i].deviceid.substring(0,2)=="TF"){
							ingestTable +='<tr><th>'+isEmpty(data.contents[i].deviceNm)+'</th>'
						 		if(data.contents[i].workStatcd=='001'){
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_wait.gif' />" title="wait"/><td>'
						 		}else if(data.contents[i].workStatcd=='002'){
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_busy.gif' />" title="busy"/><td>'
						 		}else if(data.contents[i].workStatcd=='003'){
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_hyphen.gif' />" title="-"/><td>'
						 		}else{
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_hyphen.gif' />" title="-"/><td>'
						 		}
					 		ingestTable += '</tr>'
						}
					}else{
							ingestTable +='<tr><th>'+isEmpty(data.contents[i].deviceNm)+'</th>'
						 		if(data.contents[i].workStatcd=='001'){
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_wait.gif' />" title="wait"/><td>'
						 		}else if(data.contents[i].workStatcd=='002'){
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_busy.gif' />" title="busy"/><td>'
						 		}else if(data.contents[i].workStatcd=='003'){
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_hyphen.gif' />" title="-"/><td>'
						 		}else{
						 			ingestTable += '<td><img src="<@spring.url '/images/icon_hyphen.gif' />" title="-"/><td>'
						 		}
					 		ingestTable += '</tr>'
					
					}

					<#-- 
						if(data.contents[i].deviceid.substring(0,2) == "VT" || data.contents[i].deviceid.substring(0,2) == "AC"){ 
						
						}else{
						
					 		ingestTable +='<tr><th>'+isEmpty(data.contents[i].deviceNm)+'</th>'
					 		if(data.contents[i].workStatcd=='001'){
					 			ingestTable += '<td><img src="<@spring.url '/images/icon_wait.gif' />" title="wait"/><td>'
					 		}else if(data.contents[i].workStatcd=='002'){
					 			ingestTable += '<td><img src="<@spring.url '/images/icon_busy.gif' />" title="busy"/><td>'
					 		}else if(data.contents[i].workStatcd=='003'){
					 			ingestTable += '<td><img src="<@spring.url '/images/icon_hyphen.gif' />" title="-"/><td>'
					 		}else{
					 			ingestTable += '<td><img src="<@spring.url '/images/icon_hyphen.gif' />" title="-"/><td>'
					 		}
					 		ingestTable += '</tr>'
					 		
					 	}	
				 	-->				 	
			 	}
			 $jq('#listTable').append(ingestTable);
		}
	});
	
	
	$jq.ajax({
		url: "<@spring.url '/workflow/ajaxStorageAvailable.ssc' />",
		type: 'POST',
		dataType: 'json',
		data: $jq('#listData').serialize(),
		error: function(){
		//	alert('Return Error.');
		},
		success: function(data){
			var stTable ="";
		 	var stTable2 ="";	
			
			$jq('#pro').empty();
			if ( data.contents2 != null ) {
				stTable +=data.contents2[5];
		 	    stTable +='<p class="dsc_loading_no"><span class="progress"><em>'+parseInt(Number(data.contents2[2])/1024/1024)+' /'+parseInt(Number(data.contents2[1])/1024/1024)+'GB('+data.contents2[4]+')</em></span></p>';
		 		stTable +='<article class="loading_bar"><span style="width:'+data.contents2[4]+';"></span></article><br>';
				$jq('#pro').append(stTable);
			}
		 	
		 	$jq('#pro2').empty();    
			if ( data.contents != null ) {
				stTable2 +=data.contents[5];
		 	    stTable2 +='<p class="dsc_loading_no"><span class="progress"><em>'+parseInt(Number(data.contents[2])/1024/1024)+' / '+parseInt(Number(data.contents[1])/1024/1024)+'GB ('+data.contents[4]+')</em></span></p>';
		 		stTable2 +='<article class="loading_bar"><span style="width:'+data.contents[4]+';"></span></article>';
				$jq('#pro2').append(stTable2);
			}
				
		}
	});
	
	rightRefreshTime();
}
function setGubunInfo(gubun){
	 $jq('#avGubun').attr("value",gubun);
	 setcookieVariables(gubun);
}

function rightRefreshTime(){
		  window.setTimeout("ajaxRightData()", 1000 * 5 );
}
	
function window::onload(){
  ajaxRightData();
}

//window.onload=function() 주석을 하면 에센스 허브 onload function 이 중단된다.

</script>
	<section class="aside">
	    <h3 class="blind">고정 정보</h3>
	    <article class="bgblack radius1 title">
	    <span>
	    <a href="javascript:void(0)" onclick="setGubunInfo('Video');">Video</a>
	    / <a href="javascript:void(0)" onclick="setGubunInfo('Audio');">Audio</a> 트랜스코더</span>
	    
	    </article>
	    <article class="bbsview2">
	        <table class="boardview" id="listTable">
	        	<colgroup><col width="140px"></col><col></col></colgroup>
	        	<#--
	        	
	        	
	        	-->
	        </table>
	    </article>
	    <article class="bgblack radius1 title"><span>스토리지 정보</span></article>
	    <article class="box1">
			   <article class="ly_loading" id="pro">
			   		<p class="dsc_loading_no">
		            <span class="progress">
		            <em></em></span>
		            </p>
		            <article class="loading_bar">
		            	<span style="width:30%;"></span>
		            </article>
            	</article>
            	<article class="ly_loading" id="pro2">
			   		<p class="dsc_loading_no">
		            <span class="progress">
		            <em></em></span>
		            </p>
		            <article class="loading_bar">
		            	<span style="width:30%;"></span>
		            </article>
            	</article>
	    </article>
	</section>
	<form name="rightData" id="rightData" method="post">
		<input type="hidden" name="avGubun" id="avGubun" value="" />
	</form>
	<form name="listData" id="listData" method="post">
		
	</form>
</section>