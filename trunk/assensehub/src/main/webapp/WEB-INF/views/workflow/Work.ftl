<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script type="text/javascript">
function goPage(pageNo) {
		
		document.search.action="<@spring.url '/workflow/Work.ssc'/>";
		document.search.pageNo.value = pageNo;
		document.search.submit();
	}

function qcr(){
	var x;
	var y;
	
	x=155;
	//x=window.event.clientX + document.body.scrollLeft;
	
	y=window.event.clientY + document.search.scrollTop-113;
	
	//alert("x="+x);
	//alert("y="+y); 
	
	$jq('#layer001').attr("style","display:none;position:absolute;left:"+x+"px;top:"+y+"px ");
}

function ajaxQcCount(qcCo, seq) {
	var data = {seq: seq};
	$jq.ajax({
			url: "<@spring.url '/workflow/ajaxQcReport.ssc' />",
			type: 'GET',
			dataType: 'json',
			data: data,
			error: function(){
				qcCount = 0;
			},
			success: function(data){
				qcCo = data.qcCount;
			}
	});
}

function ajaxData(){
	$jq.ajax({
		url: "<@spring.url '/ajax/workflow/ajaxWork.ssc' />",
		type: 'POST',
		dataType: 'json',
		data: $jq('#search').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
		 var z=19;
		 var table ="";
		 var qcCo = 1;
		 var traSeq="";
		 //-- ajax 실시간 데이타  (트랜스코더) by dekim
			 $jq('#viewData').empty();
		 		
		 		table +='   <tr><th>위치</th><th>프로그램명</th><th>프로파일명</th><th>등록일시</th><th>등록시각</th><th>완료시각</th><th>진행율</th><th>상태</th><th>재요청</th></tr>' 
				for(var i=0;i<data.contents.length;i++){
					    	if(i%2==0){
					 			table += '<tr class="gry">'
					 		}else{
					 			table += '<tr>'
					 		}
				    	var segmentNM = ( isEmpty(data.contents[i].segmentId) == '' || isEmpty(data.contents[i].segmentId) == 'S000' ) ? '' : ' ('+data.contents[i].segmentNm+')';
				    	var num=data.search.pageNo*20;
				 		var c_num= num-z;

				 		table += '<td title="'+isEmpty(data.contents[i].ctiId)+'['+data.contents[i].deviceid+'_'+data.contents[i].eqPsCd+']">'+isEmpty(data.contents[i].local)+'</td>'
				    	table += '<td  align="left" id="qc'+isEmpty(data.contents[i].seq)+'" title="'+isEmpty(data.contents[i].seq)+'">&nbsp;<a href="javascript:" class="btn_qcr" onclick="qcr();$jq(\'#layer001\').empty();qcView('+data.contents[i].seq+', '+data.contents[i].ctiId+', \''+isEmpty(data.contents[i].pgmNm)+'\')">('+isEmpty(data.contents[i].channel)+')&nbsp;'+isEmpty(data.contents[i].pgmNm)+segmentNM+'</a></td>'
				 		table += '<td  align="left">&nbsp;'+isEmpty(data.contents[i].proFlnm)+'</td>'
				 		table += '<td >&nbsp;'+isEmpty(data.contents[i].regDate)+'</td>'
				 		table += '<td>'+isEmpty(data.contents[i].strRegDt)+'</td>'
			 			table += '<td>'+isEmpty(data.contents[i].strModDt)+'</td>'
				 		table += '<td id="'+isEmpty(data.contents[i].seq)+'"><article class="ly_loading2"><p class="dsc_loading_no"><span class="progress">'
						table += '<em>'+isEmpty(data.contents[i].prgrs)+'</em>%</span></p><article class="loading_bar">'
					    table += '<span style="width:'+isProgress(data.contents[i].prgrs)+'%;">'
					    table += '</span></article></article></td>'
					    table += '<td>'+isEmpty(data.contents[i].clfNm)+'</td>'
					    
					    if(data.contents[i].workStatcd != "000" && data.contents[i].workStatcd != "001" && data.contents[i].workStatcd != "002" && data.contents[i].workStatcd != "200"){
					    	table += '<td><input type="button" value="재요청" class="btn_qcr" onclick="javascript:request('+isEmpty(data.contents[i].seq)+');"  ></td>'

					    }else{
					    
					    	table +='<td></td>'
					    }
					    
					 	table += '</tr>'
					 	
					 	z--;
					 	
					 	if( data.contents[i].prgrs != 100){
					 		if(traSeq == ""){
					 			traSeq= data.contents[i].seq;
					 		}else{
					 		
					 			traSeq= traSeq +"," + data.contents[i].seq;
					 		}
					 	}
			 	}
			 $jq('#viewData').append(table);
			 
			 document.search.traSeq.value=traSeq;
		}
			
	});
	refreshTime();
}

function ajaxData2(){
	//alert(document.search.traSeq.value);
	if(document.search.traSeq.value != ""){
		
		$jq.ajax({
			url: "<@spring.url '/workflow/ajaxWorkPrgrs.ssc' />",
			type: 'POST',
			dataType: 'json',
			data: $jq('#search').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 		
				 for(var i=0;i<data.contents.length;i++){
				 	
				 	$jq('#'+data.contents[i].seq).empty();
				 		var table='';
				 		
				 		table += '<article class="ly_loading2"><p class="dsc_loading_no"><span class="progress">'
						table += '<em>'+isEmpty(data.contents[i].prgrs)+'</em>%</span></p><article class="loading_bar">'
					    table += '<span style="width:'+isProgress(data.contents[i].prgrs)+'%;">'
					    table += '</span></article></article>'
				 	$jq('#'+data.contents[i].seq).append(table);
				 	
				 }
				 
			}
				
		});
	}
	
	refreshTime2();
}

function request(value){
	//alert(value);
	var check = confirm("재요청 하시겠습니까?");
	
	if(check == true){
		document.search.seq.value = value;
		$jq.ajax({
			url: "<@spring.url '/workflow/proflRequest.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#search').serialize(),
			error: function(){
				alert('소스 파일이 존재하지 않습니다.');
			},
			success: function(data){
				
			 	document.search.action="<@spring.url '/workflow/Work.ssc'/>";
			 	document.search.pageNo.value = pageNo;
				document.search.submit();
			
			}
			
				
		}); 
	}
}

function skip(skipseq){
	
	document.search.skipseq.value = skipseq;
	
	$jq.ajax({
		url: '<@spring.url '/workflow/skip.ssc'/>',
		type: 'POST',
		dataType: 'json',
		data: $jq('#search').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			
			ajaxData();
		}
	});
}
function refreshTime(){
		  window.setTimeout("ajaxData()", 1000 * 5 );
}

function refreshTime2(){
		  window.setTimeout("ajaxData2()", 1000 * 1 );
}


function isEmpty(value){
	if(typeof(value)=='number'){    
	  	return (value === undefined || value == null || value.length <= 0) ? 0 : value;
	  }else{
	  	return (value === undefined || value == null || value.length <= 0) ? "" : value;
	  } 
 } 
 
 function isProgress(value){
	  	return (value === undefined || value == null || value.length <= 0) ? 0 : value;
 } 

 function pgmAllRequest(ctiId) {
 	document.search.ctiId.value = ctiId;
	$jq.ajax({
		url: "<@spring.url '/workflow/ajaxProfileAllInitialize.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#search').serialize(),
		error: function(){
			//alert('Return Error.');
		},
		success: function(data){
			if(data.msg == 'Y') {
				alert('요청되었습니다');
			} else {
				alert('요청이 실패하였습니다');
			}
		}
	});
	showDisHide('layer001','1');  
 }

 
function qcView(seq, ctiId, pgmNm){
	var table ="";
	$jq('#layer001').empty();
			 	
	table += '<a href="#" onclick="showDisHide(\'layer001\',\'1\');return false;" class="clse"><img src="<@spring.url '/images/button_clse.gif' />" title="닫기" ></a>'					 	
 	table += '<table summary="" class="tb_pop">'
	table += '<colgroup><col width="30px"></col><col></col></colgroup>' 
	table += '<thead class="bggray1 b"><tr><th>'+seq+'</th><th>'+pgmNm+'</th></tr></thead>'
	table += '<tbody>'
	table += '<tr><th>'+ctiId+'</th><th><a href="#" onClick="pgmAllRequest(\''+ctiId+'\');return false;">프로그램 전체 재요청</a></th></tr>'
	table += '</tbody></table>'
					
	$jq('#layer001').append(table);
	
	$jq('#qc'+seq).click(function(e){
		var p=$jq('#position_init');
		var posX =25;
		var posY =60;
		$jq('#layer001').attr("style","position:absolute;left:"+(e.pageX-p.offset().left+posX)+"px;top:"+(e.pageY-p.offset().top+posY)+"px ");
	}); 
   
	showDisHide('layer001','0');
 }
 
 <#--
 function qcView(id){
 	//alert(id);
 	 
 	search.seq.value=id;
 	
 	//var obj = ($jq('#layer001').get(0));
 	$jq.ajax({
			url: "<@spring.url '/workflow/qcView.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#search').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	var table ="";
					 
					 $jq('#layer001').empty();
					 	
							table += '<a href="#" onclick="showDisHide(\'layer001\',\'1\');return false;" class="clse"><img src="<@spring.url '/images/button_clse.gif' />" title="닫기" ></a>'					 	
						 	table += '<table summary="" class="tb_pop">'
					 		table += '<colgroup><col width="30px"></col><col width="55px"></col><col></col></colgroup>' 
							table += '<thead class="bggray1 b"><tr><th>'+data.seq+'</th><th>오류위치</th><th>내용</th></tr></thead>'
							table += '<tbody>'
							if(data.qc.length == 0){
								table += '<tr><th colspan="3">데이터 없음</th></tr>'
							}else{
								for(var iq=0;iq<data.qc.length;iq++){
									table += '<tr><th>'+(data.qc[iq].reportId==null?'':data.qc[iq].reportId)+'</th><th class="fc1">'+(data.qc[iq].rtTcd==null?'':data.qc[iq].rtTcd)+'</th><td>'+(data.qc[iq].reInfo==null?'':data.qc[iq].reInfo)+'</td></tr>'
								}	
							}
							table += '</tbody></table>'
							
									 
					$jq('#layer001').append(table);
			}
				
		});
		showDisHide('layer001','0');  
 }
-->
            
function checkSearch(){
	document.search.searchDayName.value=document.search.searchDayName2.value;
	document.search.channelCode.value=document.search.channelCode2.value;
	document.search.workStat.value=document.search.workStat2.value;
	document.search.startDt.value=document.search.startDt2.value;
	document.search.endDt.value=document.search.endDt2.value;
	document.search.keyword.value=document.search.keyword2.value;
	
	document.search.action="<@spring.url '/workflow/Work.ssc'/>";
	document.search.pageNo.value="1";
	document.search.submit();
}
function reset(){
	document.search.searchDayName.value="tra.MOD_DT";
	document.search.startDt.value="";
	document.search.endDt.value="";
	document.search.workStat.value="111";
	document.search.channelCode.value="0";
	document.search.keyword.value="";
	document.search.startDt2.value="";
	document.search.endDt2.value="";
	document.search.workStat2.value="111";
	document.search.channelCode2.value="0";
	document.search.keyword2.value="";
	document.search.action="<@spring.url '/workflow/Work.ssc'/>";
	document.search.pageNo.value="1";
	document.search.submit();
}

window.onload=function(){

	cal1 = new dhtmlxCalendarObject('calInput1', false, {isYearEditable: true,isMonthEditable: true});
	cal1.setYearsRange(1973, 2020);
	cal1.draw();
	cal2 = new dhtmlxCalendarObject('calInput2', false, {isYearEditable: true,isMonthEditable: true});
	cal2.setYearsRange(1973, 2020);
	cal2.draw()

	ajaxData();

	ajaxData2(); 
}

function keyEnter() {   
	if(event.keyCode ==13) {   //엔터키가 눌려지면,
		checkSearch(search);    //검색 함수 호출
	}
}
</script>
<section id="container">
	<form name="search" id="search" method="post" >
		<@spring.bind "search" />
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" name="seq" value="" />
		<input type="hidden" name="ctiId" value="" />
		<input type="hidden" name="traSeq" id="traSeq" value="" />
		<input type="hidden" name="skipseq" value="" />
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
    	<h3 class="blind">작업관리(트랜스코더)</h3>
		<article class="title5"><img src="<@spring.url '/images/title_workmanage.gif' />" title="작업관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 워크플로우 관리 &gt; <span class="now">작업관리</span></nav>

<h2 id="position_init">
</h2>        
<!-- Popup1 -->
		<article id="layer001" class="ly_pop4" style="display:none;position:absolute;left:0px;top:0px">
		
        </article>
<!-- //Popup1 -->    

    
<!-- Table -->
        <article class="bbsview3">
        <table summary="" id="boardview7">
        <colgroup><col width="40px"></col><col width=""></col><col width="115px"></col><col width="75px"></col><col width="70px"></col><col width="70px"></col><col width="140px"></col><col width="80px"></col><col width="80px"></col></colgroup>
            <thead>
            <tr><th colspan="9">
            	<article class="box2">
                    <dl>
                        
                        <dt>채널</dt>
	                        <dd>
	                        	<select name="channelCode2">
		                        	<option value="0"  <#if search.channelCode?default(0)?number==0>selected</#if>>&#160; 전체 </option>
		                        	<option value="11" <#if search.channelCode?default(0)?number==11>selected</#if>>&#160; 1TV </option>
		                        	<option value="12" <#if search.channelCode?default(0)?number==12>selected</#if>>&#160; 2TV </option>
		                        	<option value="21" <#if search.channelCode?default(0)?number==21>selected</#if>>&#160; 1라디오 </option>
		                        	<option value="22" <#if search.channelCode?default(0)?number==22>selected</#if>>&#160; 2라디오 </option>
		                        	<option value="23" <#if search.channelCode?default(0)?number==23>selected</#if>>&#160; 3라디오 </option>
		                        	<option value="24" <#if search.channelCode?default(0)?number==24>selected</#if>>&#160; 1FM </option>
		                        	<option value="25" <#if search.channelCode?default(0)?number==25>selected</#if>>&#160; 2FM </option>
		                        	<option value="26" <#if search.channelCode?default(0)?number==26>selected</#if>>&#160; 한민족1 </option>
		                        	<option value="61" <#if search.channelCode?default(0)?number==61>selected</#if>>&#160; DMB </option>
		                        </select>
	                        </dd>
                        <dt>상태</dt>
                        	<dd>
	                        	<select name="workStat2">
	                        		<option value="111"  <#if search.workStat?default(0)?number==111>selected</#if>>&#160; 전체 </option>
		                        	<option value="000"  <#if search.workStat?default(0)?number==000>selected</#if>>&#160; 대기 </option>
		                        	<option value="001" <#if search.workStat?default(0)?number==001>selected</#if>>&#160; 요청 </option>
		                        	<option value="002" <#if search.workStat?default(0)?number==002>selected</#if>>&#160; 진행 </option>
		                        	<option value="200" <#if search.workStat?default(0)?number==200>selected</#if>>&#160; 완료 </option>
		                        	<option value="999" <#if search.workStat?default(0)?number==999>selected</#if>>&#160; 오류 </option>
		                        	
		                        </select>
	                        </dd>
                        <dt>
                        	<select name="searchDayName2">
                        		<option value="tra.REG_DT"  <#if search.searchDayName=='tra.REG_DT'>selected</#if>>&#160; 등록일자 </option>
                        		<option value="ct.BRD_DD"  <#if search.searchDayName=='ct.BRD_DD'>selected</#if>>&#160; 방송일자 </option>
                        	</select>
                        </dt>
                        <dd>
                        <input type="text" style="cursor:pointer;"  id="calInput1" name="startDt2"  value="<#if search.startDt?exists>${search.startDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>&#126;
                        <input type="text" style="cursor:pointer;"  id="calInput2" name="endDt2"  value="<#if search.endDt?exists>${search.endDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>
                        </dd>
                        <#--
                        <dt>프로파일명</dt>
                        <dd>
                        	<select name="channelCode2">
	                        	<option value="0"  <#if search.channelCode?default(0)?number==0>selected</#if>>&#160; 전체 </option>
	                        	<option value="11" <#if search.channelCode?default(0)?number==11>selected</#if>>&#160; 1TV </option>
	                        	<option value="12" <#if search.channelCode?default(0)?number==12>selected</#if>>&#160; 2TV </option>
	                        	<option value="21" <#if search.channelCode?default(0)?number==21>selected</#if>>&#160; 1라디오 </option>
	                        	<option value="22" <#if search.channelCode?default(0)?number==22>selected</#if>>&#160; 2라디오 </option>
	                        	<option value="23" <#if search.channelCode?default(0)?number==23>selected</#if>>&#160; 3라디오 </option>
	                        	<option value="24" <#if search.channelCode?default(0)?number==24>selected</#if>>&#160; 1FM </option>
	                        	<option value="25" <#if search.channelCode?default(0)?number==25>selected</#if>>&#160; 2FM </option>
	                        	<option value="26" <#if search.channelCode?default(0)?number==26>selected</#if>>&#160; 한민족1 </option>
	                        	<option value="61" <#if search.channelCode?default(0)?number==61>selected</#if>>&#160; DMB </option>
	                        </select>
                        </dd>
                        -->
                        <dt>프로그램명</dt>
                        <dd><input type="text" name="keyword2" value="<#if search.keyword?exists>${search.keyword}</#if>" onKeyDown="keyEnter();"></input></dd>
                        <dt></dt>
                    </dl>
                     
                    <article class="btncover2">
                    <span class="btn_pack gry"><a href="javascript:"  onclick="checkSearch(this)">검색</a></span><span class="btn_pack gry"><a href="javascript:"  onclick="reset()">초기화</a></span>
                    </article>
                </article>
            </th></tr>
            </thead>
            <tbody id="viewData">
            <tr><th>위치</th><th>프로그램명</th><th>프로파일명</th><th>등록일시</th><th>등록시각</th><th>완료시각</th><th>진행율</th><th>상태</th><th>재요청</th></tr>
          	  <#assign z= 19>
          	  <#list contents.items as content>
          	  		<#assign segmentId = content.segmentId?default("S000")>
          	  	    <#assign num = search.pageNo*20>
            	    <#assign c_num= num-z>
		            <#if c_num%2==0>
			            <tr>
			            <#else>
			            <tr class="gry">
		           		 </#if>
			            <td title="${content.ctiId}">${content.local!""}</td>
			            <td  align="left" id="qc${content.seq}">&nbsp;<a href="javascript:" class="btn_qcr" onclick="qcr();$jq('#layer001').empty();qcView('${content.seq}')">(${content.channel!""})${content.pgmNm!""}<#if segmentId != "S000"> (${content.segmentNm!""})</#if></a></td>
			            <td  align="left">&nbsp;${content.proFlnm!""}</td>
			            <td>${content.regDate!""}</td>
			            <td>${content.strRegDt!""}</td>
		            	<td>${content.strModDt!""}</td>
			            <td id="${content.seq}">
			            <article class="ly_loading2">
				            <p class="dsc_loading_no">
				            <span class="progress">
				            <em>${content.prgrs!0}</em>%</span>
				            </p>
				            <article class="loading_bar">
				            	<span style="width:${content.prgrs!0}%;"></span>
				            </article>  
				        </article>
			            </td>
			            <td>${ tpl.getCodeDesc("WORK", content.workStatcd!"")}</td>
			            <td></td>
			            <#-- 
			            <td><a href="javascript:" class="btn_qcr" onclick="$jq('#layer001').empty();qcView('${content.seq}')"><input type="button" value="QCR" ></a></td>
			            -->
	            	</tr>
	            	<#assign z = z - 1>
	            </#list>
            </tbody>
    	</table>     
    </article>
<!-- //Table -->

<!-- paginate -->
    <!-- <article class="paginate"> -->
      	<!-- paginate -->
	   	 <@paging contents search.pageNo '' />
		<!-- //paginate -->
    <!-- </article>-->
<!-- //paginate -->


	</section>
		</form>