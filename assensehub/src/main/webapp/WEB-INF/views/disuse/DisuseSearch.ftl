<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">

<!--
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
			checkSearch(Disuse);    //검색 함수 호출
		}
	}
	
	function reset(){
		
		Disuse.keyword.value="";		
		Disuse.startDt.value="";		
		Disuse.endDt.value="";		
	}
	
	//페기 검색
	function checkSearch(){
		//alert("1");
		
		var stDt=document.Disuse.startDt.value;
		var edDt=document.Disuse.endDt.value;
		
		if(edDt < stDt){
			alert("날짜 검색에 종료일이 시작일 보다 앞선 날짜 입니다.");
			return false;
		}
		
		document.Disuse.action="<@spring.url '/disuse/DisuseSearch.ssc'/>";
		document.Disuse.pageNo.value="1";
		document.Disuse.submit();
		
	}
	
	function goPage(pageNo) {
		document.Disuse.action="<@spring.url '/disuse/DisuseSearch.ssc'/>";
		document.Disuse.pageNo.value = pageNo;
		document.Disuse.submit();
	}
	
	//상세보기
	function selectContent(id){
	
		
		Disuse.id.value = id;
		$jq('#Player').attr("URL","");
		$jq.ajax({
			url: "<@spring.url '/disuse/selectContent.ssc'/>",
			type: 'POST',
			dataType: 'json',
			data: $jq('#Disuse').serialize(),
			error: function(){
				//alert('Return Error.');
			},
			success: function(data){
			 	//var table ="";
					 
					 $jq('#viewContent').empty();
					 	var table ="";
	                    table += '<tr><th>NO</th><td align="left" colspan="3">&nbsp;'+(data.contentTbl.ctId==null?'':data.contentTbl.ctId)+'</td></tr>'
	                    table += '<tr><th>콘텐츠명</th><td align="left" colspan="3">&nbsp;'+(data.contentTbl.ctNm==null?'':data.contentTbl.ctNm)+'</td></tr>'
	                    table += '<tr><th>프로그램명</th><td align="left" colspan="3">&nbsp;'+(data.contentTbl.pgmNm==null?'':data.contentTbl.pgmNm)+'</td></tr>'
	                    
	                   	table += '<tr><th>채널</th><td align="left" colspan="3">&nbsp;'+(data.contentTbl.channelCode==null?'':data.contentTbl.channelCode)+'</td></tr>' 
						
	                    
	                    table += '<tr><th>삭제예정일</th><td align="left" colspan="3">&nbsp;'+(data.delDate==null?'':data.delDate)+'</td></tr>'
	                    
	                    table += '<tr><th>영상구분</th><td align="left" colspan="3">&nbsp;'+(data.contentTbl.ctCla==null?'':data.contentTbl.ctCla)+'</td></tr>'
	                    
	                    
	                    table += '<tr><th>등록일</th><td align="left">&nbsp;'+(data.regDate==null?'':data.regDate)+'</td></tr>'
	                    table += '<tr><th>방영일자</th><td align="left">&nbsp;'+(data.brdDate==null?'':data.brdDate)+'</td></tr>'
	                    //table += '<input type="hidden" name="ctid" value="'+data.contentTbl.ctId+'" />'
                		 
                	 $jq('#viewContent').append(table);	
                	 
                	 document.Disuse.ctid.value= data.contentTbl.ctId;
                	 
                	 $jq('#listProfile').empty();	
                	 	var table ="";
                		//table += '<dl><dt>프로파일</dt><dd><table summary="" id="boardview4">'
                        //table += '<colgroup><col width="20px"></col><col width="267px"></col></colgroup>'
                        //table += '<tbody id="listProfile">'
	                    table += '<tr><th></th><th>파일명 / 프로파일명</th></tr>'
	                    
	                    for(var i=0;i<data.contentsInstTbls.length;i++){
						 	 table +='<tr><td>'+(i+1)+'</td><td><span class="text"><a href="#"  onclick="javascript:viewMediaPlayer(\''+isEmpty(data.contentsInstTbls[i].flPath)+isEmpty(data.contentsInstTbls[i].orgFileNm)+'.'+isEmpty(data.contentsInstTbls[i].flExt)+'\',\''+isEmpty(data.contentsInstTbls[i].ctId)+'\',\''+isEmpty(data.contentsInstTbls[i].ctiId)+'\');" >'+isEmpty(data.contentsInstTbls[i].wrkFileNm)+' / '+isEmpty(data.contentsInstTbls[i].proFlnm)+'</a></span></tr>'
						}
	                    
                        //table += '</tbody></table></dd></dl>'
                        
                        
                		
                		//table += '</article>'
                		
                		 
					$jq('#listProfile').append(table);
			}
			
				
		});
		showDisHide('layer2','0');  
	}
	
	//연장하기
	function disuseDelay(id){
		//alert(id);
		
		var ctId=document.Disuse.ctid.value;
		
		//alert(ctId);
		
		if(ctId === undefined || ctId == null || ctId.length <= 0){
			alert("프로그램이 선택되지 않았습니다!.");			
		}else{
		
			var ans=confirm('정말 연장하시겠습니까?');
			
			
			//document.Disuse.action="<@spring.url '/disuse/disuseDelay.ssc'/>";
			//document.Disuse.submit();
			
			if(ans == true){
				$jq('#Player').attr("URL","");
				$jq.ajax({
					url: "<@spring.url '/disuse/disuseDelay.ssc'/>",
					type: 'POST',
					dataType: 'json',
					data: $jq('#Disuse').serialize(),
					error: function(){
						//alert('Return Error.');
					},
					success: function(data){
						//alert("1");
					 	document.Disuse.action="<@spring.url '/disuse/DisuseSearch.ssc'/>";
						document.Disuse.submit();
					
					}
					
						
				});
			}else{
				return;
			}
		}	
	}
	
	//한개 삭제
	function contentdelete(id){
	
		var ctId=document.Disuse.ctid.value;
		
		//alert(ctId);
		
		if(ctId === undefined || ctId == null || ctId.length <= 0){
			alert("프로그램이 선택되지 않았습니다!.");			
		}else{	
	
			var ans=confirm('정말 삭제하시겠습니까?');
			
			//document.Disuse.action="<@spring.url '/disuse/contentdelete.ssc'/>";
			//document.Disuse.submit();
			
			if(ans == true){
				$jq('#Player').attr("URL","");
				$jq.ajax({
					url: "<@spring.url '/disuse/contentdelete.ssc'/>",
					type: 'POST',
					dataType: 'json',
					data: $jq('#Disuse').serialize(),
					error: function(){
						//alert('Return Error.');
					},
					success: function(data){
					 	document.Disuse.action="<@spring.url '/disuse/DisuseSearch.ssc'/>";
						document.Disuse.submit();
					
					}
					
						
				});
			}else{
				return;
			}	
		}
	}
	
	//체크박스를 통한 여러개 삭제
	function contentsdelete(){
		var obj = document.forms["Disuse"];
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
		
		//document.Disuse.action="<@spring.url '/disuse/contentsdelete.ssc'/>";
		//document.Disuse.submit();
		var ans=confirm('정말 삭제하시겠습니까?');
		if(ans == true){
			$jq('#Player').attr("URL","");
			$jq.ajax({
				url: "<@spring.url '/disuse/contentsdelete.ssc'/>",
				type: 'POST',
				dataType: 'json',
				data: $jq('#Disuse').serialize(),
				error: function(){
					//alert('Return Error.');
				},
				success: function(data){
				 	document.Disuse.action="<@spring.url '/disuse/DisuseSearch.ssc'/>";
					document.Disuse.submit();
				
				}
				
					
			});
		}else{
			return;
		}		
	}
	
	window.onload = function(){
		showDisHide('layer2','0');  
		cal1 = new dhtmlxCalendarObject('calInput1', false, {isYearEditable: true,isMonthEditable: true});
		cal1.setYearsRange(1973, 2020);
		cal1.draw();
		
		cal2 = new dhtmlxCalendarObject('calInput2', false, {isYearEditable: true,isMonthEditable: true});
		cal2.setYearsRange(1973, 2020);
		cal2.draw();
	}
	
	function viewMediaPlayer(filePath,ctId,ctiId){
	
		//$jq('#selectContentsInst').empty();
		//var table ="";
		//table += '<span class="btn_pack gry"><a href="#" onclick="javascript:callDownload(\''+filePath+'\');">다운로드</a></span> <span class="btn_pack gry"><a href="#" onclick="javascript:deleteContentsInst(\''+ctId+'\',\''+ctiId+'\');">삭제</a></span>'
		//$jq('#selectContentsInst').append(table);
		//confirm("http://10.30.13.34/mp4/"+filePath);
		//$jq('#URL').attr("value","http://10.30.13.34/mp4/"+filePath)
		//confirm("Start!"+filePath);
	
		$jq('#Player').attr("URL","http://210.115.198.102"+filePath);
		
		//confirm("Complete!");
	}
	
//-->
</script>	
<section id="container">
	<form id="Disuse" name="Disuse" method="post">
		<@spring.bind "search" />
		<input type="hidden" name="pageNo" value="${search.pageNo}" />
		<input type="hidden" id="id" name="id" value="" />
		<input type="hidden" name="menuId" value="${search.menuId}" />
		<input type="hidden" name="ctid" value="" />
    <section id="content">
    	<h3 class="blind">폐기 검색</h3>
		<article class="title5"><img src="<@spring.url '/images/title_discardsrch.gif' />" title="폐기 검색"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 폐기 관리  &gt; <span class="now">폐기 검색</span></nav>
        <article class="icon_info">
			<span><img src='<@spring.url "/images/icon_KDAS.gif"/>'/>&nbsp;KDAS</span>
			<span><img src='<@spring.url "/images/icon_DNPS.gif"/>'/>&nbsp;NPS</span>
			<span><img src='<@spring.url "/images/icon_AACH.gif"/>'/>&nbsp;AACH</span>
			<span><img src='<@spring.url "/images/icon_DMCR.gif"/>'/>&nbsp;DMCR</span>
			<span><img src='<@spring.url "/images/icon_NAS.gif"/>'/>&nbsp;NAS</span>
			<span><img src='<@spring.url "/images/icon_TS.gif"/>'/>&nbsp;DOAD</span>
			<span><img src='<@spring.url "/images/icon_LIVE.gif"/>'/>&nbsp;Live</span>
			<span><img src='<@spring.url "/images/icon_VCR.gif"/>'/>&nbsp;VCR</span>
			<span><img src='<@spring.url "/images/icon_VTRIM.gif"/>'/>&nbsp;V-Trim</span>
			<span><img src='<@spring.url "/images/icon_ATRIM.gif"/>'/>&nbsp;A-Trim</span>
			<span><img src='<@spring.url "/images/icon_RMS.gif"/>'/>&nbsp;RMS</span>
		</article>
<!-- Popup -->
		<article id="layer2" class="ly_pop3">
        <article class="sideinfo">
            <dl class="btncover3">
                <dt>콘텐츠 정보</dt>
                <dd>
                	<#--
	                <object id="mediaPlayer" width="284px" height="212px" classid="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701" standby="Loading Microsoft Windows Media Player components..." type="application/x-oleobject">
		                <param name='fileName' value="wmv/testmovie.wmv">
		                <param name="animationatStart" value="true">
		                <param name="autoStart" value="false">
		                <param name="showControls" value="true">
		                <param name="loop" value="true">
		                <embed autostart="0" loop="0"  type="application/x-mplayer2" pluginspage="http://microsoft.com/windows/mediaplayer/en/download/" id="mediaPlayer" name="mediaPlayer" showcontrols="true" width="284px" height="212px" src="wmv/testmovie.wmv" designtimesp="5311"></embed>
	                </object>
	                -->
	                <object  ID="Player"  CLASSID="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" width="284px" height="212px" >
                           <PARAM  name="URL"  value="">
                   </object>
                </dd>
             
			    <dd class="btncover2 btn">   
			        <select name="delayDay" ><option value="30">1개월</option><option value="90">3개월</option><option value="180">6개월</option><option value="1">무제한</option></select>
					
					
					
					<span class="btn_pack gry">
						<@button '${search.menuId}' '${user.userId}' 'common.delay' 'disuseDelay();' 'delay' />
					</span>
					
					<span class="btn_pack gry">
						<#-- 
						<a href="javascript:" onclick="contentdelete()">삭제</a>
						-->
						<@button '${search.menuId}' '${user.userId}' 'common.delete' 'contentdelete();' 'delete' />
					</span>
		        </dd> 
         		</dl>
	        </article>
	    </article>      
        <div class="ly_pop3_2">
			<article class="sideinfo"> 
			<dl><dt>콘텐츠 정보</dt>       
                <dd class="pdt10">
	                <ul class="btncover3">
	                    <li>
		                    <table id="boardview4" summary="" >
			                    <colgroup><col width="80px"></col><col width="207px"></col></colgroup>
			                    <tbody id="viewContent">
				                    <tr><th>NO</th><td colspan="3"></td></tr>
				                    <tr><th>콘텐츠명</th><td colspan="3"></td></tr>
				                    <tr><th>프로그램명</th><td colspan="3"></td></tr>
				                    <tr><th>채널</th><td colspan="3"></td></tr>
				                    <tr><th>삭제예정일</th><td colspan="3"></td></tr>
				                    <tr><th>영상구분</th><td colspan="3"></td></tr>
				                    <tr><th>등록일</th><td></td></tr>
				                    <tr><th>방영일자</th><td></td></tr>
			                    </tbody>
		                    </table>
	                    </li>
	                </ul>
                </dd>
                <!--
                <dd class="btncover2 btn4">
                	<select name="delayDay" ><option value="30">1개월</option><option value="90">3개월</option><option value="180">6개월</option><option value="99999">무제한</option></select> 
                	<span class="btn_pack gry"><a href="javascript:" onclick="disuseDelay()">폐기연장</a></span>
                	<br><br>
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<span class="btn_pack gry"><a href="#">삭제</a></span>
                </dd>
                -->
            </dl>
            <dl>
            	<dt>프로파일</dt>
                <dd>
					<table summary="" id="boardview4">
                        <colgroup><col width="20px"></col><col width="267px"></col></colgroup>
                        <tbody id="listProfile">
	                        <tr><th></th><th>파일명 / 프로파일명</th></tr>
                        </tbody>
                    </table>
                </dd>
            </dl>
        </article>
    </div>
<!-- //Popup -->

<!-- Table -->
        <article class="bbsview3">
	        <table summary="" id="boardview3">
	        <colgroup><col width="50px"></col><col width=""></col><col width="150px"></col><col width="70px"></col><col width="70px"></col><col width="325px"></col></colgroup>
	            <thead>
		            <tr><th colspan="6">
		            	<article class="box2">
		                    <dl>
		                        <dt>삭제예정일</dt>
		                        <dd>
		                        <input type="text" style="cursor:pointer;"  id="calInput1" name="startDt"  value="<#if search.startDt?exists>${search.startDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>&#126;
		                        <input type="text" style="cursor:pointer;" id="calInput2"  name="endDt"  value="<#if search.endDt?exists>${search.endDt?string("yyyy-MM-dd")}</#if>" class="ip_date" readonly></input>
		                        </dd>
		                        <dt>프로그램</dt>
		                        <dd><input name="keyword" type="text" value="<#if search.keyword?exists>${search.keyword}</#if>" onKeyDown="keyEnter();"></input></dd>
		                        <dt></dt>
		                    </dl>
		                    <article class="btncover2">
		                    	<span class="btn_pack gry"><a href="javascript:"  onclick="checkSearch(this)">검색</a></span>
		                    	<span class="btn_pack gry"><a href="javascript:"  onclick="reset()">초기화</a></span>
		                    </article>
		                </article>
		            </th></tr>
	            </thead>
	<!-- list -->
	            <tbody>
		            <tr><th></th><th>프로그램명</th><th>콘텐츠명</th><th>영상구분</th><th>삭제예정일</th><th></th></tr>
		            <#assign x = 22>            
            		<#assign y = x>
            		<#list contents.items as content>
			            <#if y%2==0>
			            <tr class="gry">
			            <#else>
			            <tr>
			            </#if>
				            <td><input name="check" type="checkbox" value="${content.ctId!""}"></td>
				            <#assign regrid=content.regrid!"">
				            <#assign pgmnm=content.pgmNm!"">
				            <#if pgmnm =="">
				            	<td></td>
				            <#else>	
					            <#assign pgmNmsize=content.pgmNm?length>
									<#if pgmNmsize <= 18>
		  	
							         	<td align="left">
							            	&nbsp;
							            	<img src='<@spring.url "/images/icon_"+regrid+".gif"/>'/>
							            	&nbsp;${content.pgmNm!""}
							            </td>
							        <#else>
							            <td align="left">
							            	&nbsp;
							            	<img src='<@spring.url "/images/icon_"+regrid+".gif"/>'/>
							            	&nbsp;${content.pgmNm?substring(0,18)+" ..."}
							            </td>
						            </#if>   
						     </#if> 
							<#assign ctnm = content.ctNm!"">
							<#if ctnm == "">
								<td></td>
							<#else>	
								<#assign ctNmsize=content.ctNm?length>
								<#if ctNmsize <= 14>
								<td align="left">
					            	&nbsp;<a href="javascript:" onclick="selectContent('${content.ctId!""}')">${content.ctNm!""}</a>
					            </td>
					            <#else>
					            <td align="left">
					            	&nbsp;<a href="javascript:" onclick="selectContent('${content.ctId!""}')">${content.ctNm?substring(0,14)+" ..."}</a>
					            </td>
					            </#if>
				            </#if>
		                    <td align="left">&nbsp;${ tpl.getCodeDesc("CCLA", content.ctCla!"")}</td>	
		                    
				            <td>${content.regDt?string("yyyy-MM-dd")}</td>
				            <#if y == 22> 
				            	<td rowspan="22">
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
						            <td>
						            	
						            </td>
						            <td></td>
						            <td></td>
						      		<#if y==22>      
							            <td rowspan="22">
	
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
	<span class="btn_pack gry"><a href="javascript:" onclick="contentsdelete(this)">삭제</a></span>
	</article>

	<!-- paginate -->
	    <@paging contents search.pageNo '' />
	<!-- //paginate -->
	</section>
	</form>