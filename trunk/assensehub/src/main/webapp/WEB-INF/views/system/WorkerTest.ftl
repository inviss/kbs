<<<<<<< .mine
<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl" >
<script Language="JavaScript">
<!--
	
	// 검색 
	function checkSearch(){ 
   		
		document.ProfileSearch.action="<@spring.url '/service/Profile.ssc'/>";
		document.ProfileSearch.submit();
	}
	
	// 저장 
	function proflSave(){ 
   		 
		document.ProfileSearch.action="<@spring.url '/service/Profile.ssc'/>";
		document.ProfileSearch.submit();
	}
	
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
			checkSearch(ProfileSearch);    //검색 함수 호출
		}
	}
	
	
}
//-->
</script>
<section id="container">

    <section id="content">
    	<h3 class="blind">프로파일 관리</h3>
		<article class="title"><img src="<@spring.url '/images/title_profile.gif' />" title="프로파일 관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 서비스 관리  &gt; <span class="now">프로파일 관리</span></nav>
<!-- Popup -->
		<article id="layer3" class="ly_pop2">
        <article class="sideinfo">
            <dl class="btncover3">
                <dt>프로파일 등록</dt>
                <dd>
                <table summary="" class="boardview6">
                <colgroup><col width="35px"></col><col width="95px"></col><col width="157px"></col></colgroup>
                <tbody>
                <tr><th rowspan="7" class="bggray1">비<br/>디<br/>오</th><th>이름</th><td>
                <input type="text" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>코덱</th><td>
                <input type="text" name="vdoCodec" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>비트레이트</th><td>
                <input type="text" name="vdoBitRate" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>가로</th><td>
                <input type="text" name="vdoHori" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>세로</th><td>
                <input type="text" name="vdoVert" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>F/S</th><td>
                <input type="text" name="vdoFS" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>종횡맞춤</th><td>
                <input type="text" name="vdoSync" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th rowspan="7" class="bggray1">오<br/>디<br/>오</th><th>코덱</th><td>
                <input type="text" name="audCodec" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>비트레이트</th><td>
                <input type="text" name="audBitRate" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>채널</th><td>
                <input type="text" name="audChan" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>샘플링레이트</th><td>
                <input type="text" name="audSRate" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>확장자</th><td>
                <input type="text" name="ext" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>화질</th><td>
                <input type="text" name="vdoCodec" value="" name="vdoCodec" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                <tr><th>순위</th><td>
                <input type="text" name="priority" value="" class="ip_text"></input><span class="btns"></span>
                </td></tr>
                </tbody>
                </table>
                </dd>
            </dl>
            <dl class="btncover3">
                <dt>프로파일 정보</dt>
                <dd>
				<ul class="box6"> 
                	<li><input name="goms500" type="checkbox" value="HD 곰 TV_S_500k"><span>HD 곰 TV_S_500k</span></li>
                    <li><input name="gomd500" type="checkbox" value="HD 곰 TV_D_500k"><span>HD 곰 TV_D_500k</span></li>
                    <li><input name="kbshome300" type="checkbox" value="3D_KBS_HOME_300k"><span>3D_KBS_HOME_300k</span></li>
                    <li><input name="kbshome56" type="checkbox" value="3D_KBS_HOME_56k"><span>3D_KBS_HOME_56k</span></li>
                </ul>
                </dd>
                <dd class="btncover2 btn3">
                <span class="btn_pack gry"><a href="javascript:"  onclick="checkSave(this)">저장</a></span> <span class="btn_pack gry"><a href="#layer2" onclick="showHide('layer2');return false;">닫기</a></span>
                </dd>
            </dl>
        </article>
    </article>
<!-- //Popup -->

<!-- Table -->
        <article class="bbsview3">
        <table summary="" id="boardview3">
        <colgroup><col width=30px""></col><col width="100px"></col><col width="55px"></col><col width="70px"></col><col width="50px"></col><col width=""></col><col width="70px"></col><col width="50px"></col><col width="333px"></col></colgroup>
            <thead>
            <tr><th colspan="9">
            	<article class="box2">
                    <dl>
                        <dt>등록일</dt>
                        <dd><input type="text" id="startDt" name="startDt" value="<#if search.startDt?exists>${search.startDt?string("yyyy.MM.dd")}</#if>" class="ip_date" readonly></input><a href="javascript:void(0)" onClick="callCalPop('document.ProfileSearch.startDt', 'yyyy.mm.dd');"><img src="<@spring.url '/images/icon_calendar.gif' />" title="달력"/></a> &#126; <input type="text" id="endDt" name="endDt" value="<#if search.endDt?exists>${search.endDt?string("yyyy.MM.dd")}</#if>" class="ip_date" readonly></input><a href="javascript:void(0)" onClick="callCalPop('document.ProfileSearch.endDt', 'yyyy.mm.dd');"><img src="<@spring.url '/images/icon_calendar.gif' />" title="달력"/></a></dd>
                        <dt>프로파일명</dt>
                        <dd><input type="text" name="keyword" value="" onKeyDown="keyEnter();"></input></dd>
                        <dt></dt>
                    </dl>
                    <article class="btncover2">
                    <span class="btn_pack gry"><a href="javascript:"  onclick="checkSearch(this)">검색</a></span>
                    </article>
                </article>
            </th></tr>
            </thead>
<!-- list -->
            <tbody>
            <tr class="bgdeep"><th colspan="2"></th><th colspan="3">비디오</th><th colspan="3">오디오</th><th></th></tr>
            <tr><th>선택</th><th>이름</th><th>코덱</th><th>비트레이트</th><th>F/S</th><th>코덱</th><th>비트레이트</th><th>채널</th><th></th></tr>
           
	            <tr class="gry">
		            <td><input name="" type="checkbox" value=""></td>
		            <td>
		            <a href="#layer3" onclick="showHide('layer3');return false;">00</a>
		            </td>
		            <td>
		           1
		            </td>
		            <td>2</td>
		            <td>3</td>
		            <td>4</td>
		            <td>5</td>
		            <td>6</td>
		            
		            	<td rowspan="20">
						<!-- side information -->
							
						<!-- //side information -->
		    	        </td>
		    	    
	            </tr>
           
            </tbody>
<!-- //list --> 
    </table> 
        
    </article>
<!-- //Table -->
	<article class="btncover4">
	<span class="btn_pack gry"><a href="#">추가</a></span> <span class="btn_pack gry"><a href="#">삭제</a></span>
	</article>
	
	</section>
</form>=======
dsfsdfsdf
qasdasd
asdasd
asdasdas>>>>>>> .r342
