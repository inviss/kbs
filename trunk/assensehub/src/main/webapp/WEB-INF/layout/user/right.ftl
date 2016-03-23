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
 
function ajaxRightData1(){
	var obj = ($jq('#rightData').get(0));
	$jq.ajax({
		url: "<@spring.url '/ajax/workflow/ajaxDmcrContentList.ssc' />",
		type: 'POST',
		cache: false,
		dataType: 'json',
		data: $jq('#rightData').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		 	var ingestTable ="";
		 	var route = "";
		 	var typNm = "";
			$jq('#listTable2').empty();
	 		ingestTable +='<colgroup><col width="140px"></col><col></col></colgroup>' 
	 		// 에센스타입2(ctTyp) : 00-본방, 01-예고편, 02-타이틀, 03-인터뷰, 04-현장스케치, 05-보이는라디오, 06-뮤직비디오, 07-기타
			for(var i=0; i<data.contents.length; i++){
				if(data.contents[i].ctTyp == '00') typNm = "본";
				else if(data.contents[i].ctTyp == '01') typNm = "예";
				else if(data.contents[i].ctTyp == '02') typNm = "타";
				else if(data.contents[i].ctTyp == '03') typNm = "인";
				else if(data.contents[i].ctTyp == '04') typNm = "현";
				else if(data.contents[i].ctTyp == '05') typNm = "보";
				else if(data.contents[i].ctTyp == '06') typNm = "뮤";
				else typNm = "기";
				
				ingestTable += '<tr><th title="['+data.contents[i].brdKd+']['+data.contents[i].pgmGrpCd+']'+data.contents[i].pgmId+'">['+typNm+'] <a href="javascript:void(0)" onclick="">'+isEmpty(data.contents[i].pgmNm)+'</a></th>'
				ingestTable += '<td class="right">'
				if(data.contents[i].regrid == 'DMCR') {
					ingestTable += '<img src=<@spring.url "/images/icon_DMCR.gif"/>>'
				} else if(data.contents[i].regrid == 'NPS') {
					ingestTable += '<img src=<@spring.url "/images/icon_NPS.gif"/>>'
				} else if(data.contents[i].regrid == 'DNPS') {
					ingestTable += '<img src=<@spring.url "/images/icon_KNPS.gif"/>>'
				} else if(data.contents[i].regrid == 'KDAS') {
					ingestTable += '<img src=<@spring.url "/images/icon_KDAS.gif"/>>'
				}
			 	ingestTable += '<td>'
		 		ingestTable += '</tr>'
			}
			$jq('#listTable2').append(ingestTable);
		}
	});
	
	rightRefreshTime1();
}

function ajaxRightData2(){
	
	var obj = ($jq('#rightData').get(0));
	var yourname = getCookie("MyCoolCookiePage")
	$jq('#avGubun').attr("value", yourname);
	$jq.ajax({
		url: "<@spring.url '/ajax/workflow/ajaxWorkflowAv.ssc' />",
		type: 'POST',
		dataType: 'json',
		data: $jq('#rightData').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		
		 $jq('#avGubun').attr("value", data.avGubun);
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
		 	}
			$jq('#listTable').append(ingestTable);
		}
	});
	
	rightRefreshTime2();
}

function ajaxRightData3(){
	var obj = ($jq('#rightData').get(0));
	$jq.ajax({
		url: "<@spring.url '/workflow/ajaxStorageAvailable.ssc' />",
		type: 'POST',
		cache: false,
		dataType: 'json',
		data: $jq('#listData').serialize(),
		error: function(){
			//	alert('Return Error.');
		},
		success: function(data){
			var stTable ="";
			var stTable2 ="";	
			$jq('#pro').empty();
		 	    stTable +=data.contents2[5]
		 	    stTable +='<p class="dsc_loading_no"><span class="progress"><em>'+parseInt(Number(data.contents2[2])/1024)+'/'+parseInt(Number(data.contents2[1])/1024)+'('+data.contents2[4]+')</em></span></p>'
		 		stTable +='<article class="loading_bar"><span style="width:'+data.contents2[4]+';"></span></article><br>'
			$jq('#pro').append(stTable);
			
			$jq('#pro2').empty();
				stTable2 +=data.contents[5];
		 	    stTable2 +='<p class="dsc_loading_no"><span class="progress"><em>'+parseInt(Number(data.contents[2])/1024)+' / '+parseInt(Number(data.contents[1])/1024)+'('+data.contents[4]+')</em></span></p>'
		 		stTable2 +='<article class="loading_bar"><span style="width:'+data.contents[4]+';"></span></article>'
			$jq('#pro2').append(stTable2);
		}
	});
	
	rightRefreshTime3();
}

function setGubunInfo(gubun){
	 $jq('#avGubun').attr("value",gubun);
	 setcookieVariables(gubun);
}

function rightRefreshTime1(){
	window.setTimeout("ajaxRightData1()", 1000 * 10 );
}
function rightRefreshTime2(){
	window.setTimeout("ajaxRightData2()", 1000 * 5 );
}
function rightRefreshTime3(){
	window.setTimeout("ajaxRightData3()", 1000 * 20 );
}

$(document).ready(function() { 
	rightRefreshTime1(); 
});
$(document).ready(function() { 
	rightRefreshTime2(); 
});
$(document).ready(function() { 
	rightRefreshTime3(); 
});

</script>
	<section class="aside">
	    <h3 class="blind">고정 정보</h3>
	    <article class="bgblack radius1 title">
		    <span>
		    	대기콘텐츠(비디오)
		    </span>
	    </article>
	    <article class="bbsview6">
	        <table class="boardview" id="listTable2">
	        	<colgroup><col width="140px"></col><col></col></colgroup>
	        	<#-- -->
	        </table>
	    </article>
	    
	    <article class="bgblack radius1 title">
		    <span>
		    	<a href="javascript:void(0)" onclick="setGubunInfo('Video');">Video</a>
		  		/ 
		  		<a href="javascript:void(0)" onclick="setGubunInfo('Audio');">Audio</a> 트랜스코더
		    </span>
	    </article>
	    <article class="bbsview2">
	        <table class="boardview" id="listTable">
	        	<colgroup><col width="140px"></col><col></col></colgroup>
	        	<#-- -->
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
		<input type="hidden" name="targetValue" id="targetValue" value="" />
		<input type="hidden" name="limit" id="limit" value="" />
	</form>
	<form name="listData" id="listData" method="post">
		
	</form>
</section>