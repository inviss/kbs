<#include "/WEB-INF/views/includes/commonTags.ftl">
<script Language="JavaScript">
<!--
	function Lgfocus() {
		document.Login.userId.focus();
	}
	// 로그인 입력 확인 
	function checkLogin(){   
		document.Login.action="<@spring.url '/member/login.ssc'/>";
		document.Login.submit();
	}
	function Move_tab(frm) {
		key_code = event.keyCode; 
		if (key_code == 9 || key_code == 13) {  // 9는 Tab키, 13은 Enter키 
			frm.passwd.focus();
			event.returnValue= false; 
		} 
	}
	function keyEnter() {   
 		if(event.keyCode ==13) {   //엔터키가 눌려지면,
			checkLogin(login);    //로그인 함수 호출
		}
	}
	function input_clearbg(id) {
		document.getElementById(id).style.background="#ffffff"; 
		topInputFocus = false;	
	}
	window.onload = function() {
		Lgfocus();
	}
//-->
</script>
${Request.errMsg!""}
<section id="wrap">
	<!-- Container --><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<section id="login" class="g_login">
		<img src="<@spring.url '/images/logo_login.gif' />" title="Assense HUB LOGIN" />
		    <form class="g_login" name="Login" method="post">
			    <fieldset>
			    <legend>login</legend>
			    <div class="item">
			        <input class="i_text uid" name="userId" id="textfield" value="" type="text" style="color:#767676;ime-mode:inactive;" onFocus="change(this)" onBlur="change(this)">
			    </div>
			    <div class="item">
			        <input class="i_text upw" name="userPass" id="textfield" value="" type="password" style="color:#767676" onFocus="change2(this)" onBlur="change2(this)" onKeyDown="keyEnter();">
			    </div>
			    <p class="keeping">
			    <input class="i_check" value="" type="checkbox">아이디 저장하기
			    <article class="btncover10">
			    	<span class="btn_pack xlarge"><a href="javascript:"  onclick="checkLogin(this)">로그인</a></span>
			    </article>
			    </fieldset>
		    </form>
	</section>
</section>


