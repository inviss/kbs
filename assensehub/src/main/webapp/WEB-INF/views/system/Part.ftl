<#include "/WEB-INF/views/includes/commonTags.ftl">
<#include "/WEB-INF/views/includes/macro_fomat.ftl">
<script type="text/javascript">
function check(id,no){
	

	
	if(id=="a2"){
		if(no =="0"){
			document.part.a3[0].checked = true;
			document.part.a4[0].checked = true;
			document.part.a24[0].checked = true;
			document.part.a25[0].checked = true;
			document.part.a26[0].checked = true;
			document.part.a27[0].checked = true;
			document.part.a28[0].checked = true;
			document.part.a29[0].checked = true;
			document.part.a31[0].checked = true;
			
			document.part.a3[0].disabled = true;
			document.part.a3[1].disabled = true;
			document.part.a3[2].disabled = true;
			document.part.a3[3].disabled = true; 
			document.part.a4[0].disabled = true;
			document.part.a4[1].disabled = true;
			document.part.a4[2].disabled = true;
			document.part.a4[3].disabled = true;
			document.part.a24[0].disabled = true;
			document.part.a24[1].disabled = true;
			document.part.a24[2].disabled = true;
			document.part.a24[3].disabled = true; 
			document.part.a25[0].disabled = true;
			document.part.a25[1].disabled = true;
			document.part.a25[2].disabled = true;
			document.part.a25[3].disabled = true;
			document.part.a26[0].disabled = true;
			document.part.a26[1].disabled = true;
			document.part.a26[2].disabled = true;
			document.part.a26[3].disabled = true; 
			document.part.a27[0].disabled = true;
			document.part.a27[1].disabled = true;
			document.part.a27[2].disabled = true;
			document.part.a27[3].disabled = true;
			document.part.a28[0].disabled = true;
			document.part.a28[1].disabled = true;
			document.part.a28[2].disabled = true;
			document.part.a28[3].disabled = true; 
			document.part.a29[0].disabled = true;
			document.part.a29[1].disabled = true;
			document.part.a29[2].disabled = true;
			document.part.a29[3].disabled = true;
			document.part.a31[0].disabled = true;
			document.part.a31[1].disabled = true;
			document.part.a31[2].disabled = true;
			document.part.a31[3].disabled = true;
		
						 
		}else if(no =="1"){
			document.part.a3[1].checked = true;
			document.part.a4[1].checked = true;
			document.part.a24[1].checked = true;
			document.part.a25[1].checked = true;
			document.part.a26[1].checked = true;
			document.part.a27[1].checked = true;
			document.part.a28[1].checked = true;
			document.part.a29[1].checked = true;
			document.part.a31[1].checked = true;
			document.part.a3[0].disabled = false;
			document.part.a3[1].disabled = false;
			document.part.a3[2].disabled = false;
			document.part.a3[3].disabled = false;
			document.part.a4[0].disabled = false;
			document.part.a4[1].disabled = false;
			document.part.a4[2].disabled = false;
			document.part.a4[3].disabled = false;
			document.part.a24[0].disabled = false;
			document.part.a24[1].disabled = false;
			document.part.a24[2].disabled = false;
			document.part.a24[3].disabled = false; 
			document.part.a25[0].disabled = false;
			document.part.a25[1].disabled = false;
			document.part.a25[2].disabled = false;
			document.part.a25[3].disabled = false;
			document.part.a26[0].disabled = false;
			document.part.a26[1].disabled = false;
			document.part.a26[2].disabled = false;
			document.part.a26[3].disabled = false; 
			document.part.a27[0].disabled = false;
			document.part.a27[1].disabled = false;
			document.part.a27[2].disabled = false;
			document.part.a27[3].disabled = false;
			document.part.a28[0].disabled = false;
			document.part.a28[1].disabled = false;
			document.part.a28[2].disabled = false;
			document.part.a28[3].disabled = false; 
			document.part.a29[0].disabled = false;
			document.part.a29[1].disabled = false;
			document.part.a29[2].disabled = false;
			document.part.a29[3].disabled = false;
			document.part.a31[0].disabled = false;
			document.part.a31[1].disabled = false;
			document.part.a31[2].disabled = false;
			document.part.a31[3].disabled = false;
		}else if(no =="2"){
			document.part.a3[2].checked = true;
			document.part.a4[2].checked = true;
			document.part.a24[2].checked = true;
			document.part.a25[2].checked = true;
			document.part.a26[2].checked = true;
			document.part.a27[2].checked = true;
			document.part.a28[2].checked = true;
			document.part.a29[2].checked = true;
			document.part.a31[2].checked = true;
			document.part.a3[1].disabled = false;
			document.part.a3[2].disabled = false;
			document.part.a3[3].disabled = false;
			document.part.a4[0].disabled = false;
			document.part.a4[1].disabled = false;
			document.part.a4[2].disabled = false;
			document.part.a4[3].disabled = false;
			document.part.a24[0].disabled = false;
			document.part.a24[1].disabled = false;
			document.part.a24[2].disabled = false;
			document.part.a24[3].disabled = false; 
			document.part.a25[0].disabled = false;
			document.part.a25[1].disabled = false;
			document.part.a25[2].disabled = false;
			document.part.a25[3].disabled = false;
			document.part.a26[0].disabled = false;
			document.part.a26[1].disabled = false;
			document.part.a26[2].disabled = false;
			document.part.a26[3].disabled = false; 
			document.part.a27[0].disabled = false;
			document.part.a27[1].disabled = false;
			document.part.a27[2].disabled = false;
			document.part.a27[3].disabled = false;
			document.part.a28[0].disabled = false;
			document.part.a28[1].disabled = false;
			document.part.a28[2].disabled = false;
			document.part.a28[3].disabled = false; 
			document.part.a29[0].disabled = false;
			document.part.a29[1].disabled = false;
			document.part.a29[2].disabled = false;
			document.part.a29[3].disabled = false;
			document.part.a31[0].disabled = false;
			document.part.a31[1].disabled = false;
			document.part.a31[2].disabled = false;
			document.part.a31[3].disabled = false;
			
		}else{
			document.part.a3[3].checked = true;
			document.part.a4[3].checked = true;
			document.part.a24[3].checked = true;
			document.part.a25[3].checked = true;
			document.part.a26[3].checked = true;
			document.part.a27[3].checked = true;
			document.part.a28[3].checked = true;
			document.part.a29[3].checked = true;
			document.part.a31[3].checked = true;
			document.part.a3[1].disabled = false;
			document.part.a3[2].disabled = false;
			document.part.a3[3].disabled = false;
			document.part.a4[0].disabled = false;
			document.part.a4[1].disabled = false;
			document.part.a4[2].disabled = false;
			document.part.a4[3].disabled = false;
			document.part.a24[0].disabled = false;
			document.part.a24[1].disabled = false;
			document.part.a24[2].disabled = false;
			document.part.a24[3].disabled = false; 
			document.part.a25[0].disabled = false;
			document.part.a25[1].disabled = false;
			document.part.a25[2].disabled = false;
			document.part.a25[3].disabled = false;
			document.part.a26[0].disabled = false;
			document.part.a26[1].disabled = false;
			document.part.a26[2].disabled = false;
			document.part.a26[3].disabled = false; 
			document.part.a27[0].disabled = false;
			document.part.a27[1].disabled = false;
			document.part.a27[2].disabled = false;
			document.part.a27[3].disabled = false;
			document.part.a28[0].disabled = false;
			document.part.a28[1].disabled = false;
			document.part.a28[2].disabled = false;
			document.part.a28[3].disabled = false; 
			document.part.a29[0].disabled = false;
			document.part.a29[1].disabled = false;
			document.part.a29[2].disabled = false;
			document.part.a29[3].disabled = false;
			document.part.a31[0].disabled = false;
			document.part.a31[1].disabled = false;
			document.part.a31[2].disabled = false;
			document.part.a31[3].disabled = false;
		}
		
	}   
	
	if(id=="a5"){
		if(no =="0"){
			document.part.a6[0].checked = true;
			document.part.a7[0].checked = true;
			document.part.a6[0].disabled = true;
			document.part.a6[1].disabled = true;
			document.part.a6[2].disabled = true;
			document.part.a6[3].disabled = true; 
			document.part.a7[0].disabled = true;
			document.part.a7[1].disabled = true;
			document.part.a7[2].disabled = true;
			document.part.a7[3].disabled = true;
		}else if(no =="1"){
			document.part.a6[1].checked = true;
			document.part.a7[1].checked = true;
			document.part.a6[0].disabled = false;  
			document.part.a6[1].disabled = false;  
			document.part.a6[2].disabled = false;  
			document.part.a6[3].disabled = false;  
			document.part.a7[0].disabled = false;  
			document.part.a7[1].disabled = false;  
			document.part.a7[2].disabled = false;  
			document.part.a7[3].disabled = false;  
			
			
		}else if(no =="2"){
			document.part.a6[2].checked = true;
			document.part.a7[2].checked = true;
			document.part.a6[0].disabled = false;  
			document.part.a6[1].disabled = false;  
			document.part.a6[2].disabled = false;  
			document.part.a6[3].disabled = false;  
			document.part.a7[0].disabled = false;  
			document.part.a7[1].disabled = false;  
			document.part.a7[2].disabled = false;  
			document.part.a7[3].disabled = false;
		}else{
			document.part.a6[3].checked = true;
			document.part.a7[3].checked = true;
			document.part.a6[0].disabled = false;  
			document.part.a6[1].disabled = false;  
			document.part.a6[2].disabled = false;  
			document.part.a6[3].disabled = false;  
			document.part.a7[0].disabled = false;  
			document.part.a7[1].disabled = false;  
			document.part.a7[2].disabled = false;  
			document.part.a7[3].disabled = false;
		}
		
	}
	
	if(id=="a8"){
		if(no =="0"){
			document.part.a9[0].checked = true;
			document.part.a10[0].checked = true;
			document.part.a11[0].checked = true;
			document.part.a9[0].disabled = true;
			document.part.a9[1].disabled = true;
			document.part.a9[2].disabled = true;
			document.part.a9[3].disabled = true; 
			document.part.a10[0].disabled = true;
			document.part.a10[1].disabled = true;
			document.part.a10[2].disabled = true;
			document.part.a10[3].disabled = true;
			document.part.a11[0].disabled = true;
			document.part.a11[1].disabled = true;
			document.part.a11[2].disabled = true;
			document.part.a11[3].disabled = true;
		}else if(no =="1"){
			document.part.a9[1].checked = true;
			document.part.a10[1].checked = true;
			document.part.a11[1].checked = true;
			document.part.a9[0].disabled = false; 
			document.part.a9[1].disabled = false; 
			document.part.a9[2].disabled = false; 
			document.part.a9[3].disabled = false; 
			document.part.a10[0].disabled = false;
			document.part.a10[1].disabled = false;
			document.part.a10[2].disabled = false;
			document.part.a10[3].disabled = false;
			document.part.a11[0].disabled = false;
			document.part.a11[1].disabled = false;
			document.part.a11[2].disabled = false;
			document.part.a11[3].disabled = false;
			
		}else if(no =="2"){
			document.part.a9[2].checked = true;
			document.part.a10[2].checked = true;
			document.part.a11[2].checked = true;
			document.part.a9[0].disabled = false; 
			document.part.a9[1].disabled = false; 
			document.part.a9[2].disabled = false; 
			document.part.a9[3].disabled = false; 
			document.part.a10[0].disabled = false;
			document.part.a10[1].disabled = false;
			document.part.a10[2].disabled = false;
			document.part.a10[3].disabled = false;
			document.part.a11[0].disabled = false;
			document.part.a11[1].disabled = false;
			document.part.a11[2].disabled = false;
			document.part.a11[3].disabled = false;
		}else{
			document.part.a9[3].checked = true;
			document.part.a10[3].checked = true;
			document.part.a11[3].checked = true;
			document.part.a9[0].disabled = false; 
			document.part.a9[1].disabled = false; 
			document.part.a9[2].disabled = false; 
			document.part.a9[3].disabled = false; 
			document.part.a10[0].disabled = false;
			document.part.a10[1].disabled = false;
			document.part.a10[2].disabled = false;
			document.part.a10[3].disabled = false;
			document.part.a11[0].disabled = false;
			document.part.a11[1].disabled = false;
			document.part.a11[2].disabled = false;
			document.part.a11[3].disabled = false;
		}
		
	}
	
	if(id=="a12"){
		if(no =="0"){
			document.part.a13[0].checked = true;
			document.part.a14[0].checked = true;
			document.part.a15[0].checked = true;
			document.part.a13[0].disabled = true;
			document.part.a13[1].disabled = true;
			document.part.a13[2].disabled = true;
			document.part.a13[3].disabled = true; 
			document.part.a14[0].disabled = true;
			document.part.a14[1].disabled = true;
			document.part.a14[2].disabled = true;
			document.part.a14[3].disabled = true;
			document.part.a15[0].disabled = true;
			document.part.a15[1].disabled = true;
			document.part.a15[2].disabled = true;
			document.part.a15[3].disabled = true;
		}else if(no =="1"){
			document.part.a13[1].checked = true;
			document.part.a14[1].checked = true;
			document.part.a15[1].checked = true;
			document.part.a13[0].disabled = false;    
			document.part.a13[1].disabled = false;    
			document.part.a13[2].disabled = false;    
			document.part.a13[3].disabled = false;    
			document.part.a14[0].disabled = false;    
			document.part.a14[1].disabled = false;    
			document.part.a14[2].disabled = false;    
			document.part.a14[3].disabled = false;    
			document.part.a15[0].disabled = false;    
			document.part.a15[1].disabled = false;    
			document.part.a15[2].disabled = false;    
			document.part.a15[3].disabled = false;    
			
		}else if(no =="2"){
			document.part.a13[2].checked = true;
			document.part.a14[2].checked = true;
			document.part.a15[2].checked = true;
			document.part.a13[0].disabled = false;    
			document.part.a13[1].disabled = false;    
			document.part.a13[2].disabled = false;    
			document.part.a13[3].disabled = false;    
			document.part.a14[0].disabled = false;    
			document.part.a14[1].disabled = false;    
			document.part.a14[2].disabled = false;    
			document.part.a14[3].disabled = false;    
			document.part.a15[0].disabled = false;    
			document.part.a15[1].disabled = false;    
			document.part.a15[2].disabled = false;    
			document.part.a15[3].disabled = false;
		}else{
			document.part.a13[3].checked = true;
			document.part.a14[3].checked = true;
			document.part.a15[3].checked = true;
			document.part.a13[0].disabled = false;    
			document.part.a13[1].disabled = false;    
			document.part.a13[2].disabled = false;    
			document.part.a13[3].disabled = false;    
			document.part.a14[0].disabled = false;    
			document.part.a14[1].disabled = false;    
			document.part.a14[2].disabled = false;    
			document.part.a14[3].disabled = false;    
			document.part.a15[0].disabled = false;    
			document.part.a15[1].disabled = false;    
			document.part.a15[2].disabled = false;    
			document.part.a15[3].disabled = false;
		}
		
	}
	
	if(id=="a16"){
		if(no =="0"){
			document.part.a17[0].checked = true;
			document.part.a18[0].checked = true;
			document.part.a19[0].checked = true;
			document.part.a17[0].disabled = true;
			document.part.a17[1].disabled = true;
			document.part.a17[2].disabled = true;
			document.part.a17[3].disabled = true; 
			document.part.a18[0].disabled = true;
			document.part.a18[1].disabled = true;
			document.part.a18[2].disabled = true;
			document.part.a18[3].disabled = true;
			document.part.a19[0].disabled = true;
			document.part.a19[1].disabled = true;
			document.part.a19[2].disabled = true;
			document.part.a19[3].disabled = true;
		}else if(no =="1"){
			document.part.a17[1].checked = true;
			document.part.a18[1].checked = true;
			document.part.a19[1].checked = true;
			document.part.a17[0].disabled = false;
			document.part.a17[1].disabled = false;
			document.part.a17[2].disabled = false;
			document.part.a17[3].disabled = false; 
			document.part.a18[0].disabled = false;
			document.part.a18[1].disabled = false;
			document.part.a18[2].disabled = false;
			document.part.a18[3].disabled = false;
			document.part.a19[0].disabled = false;
			document.part.a19[1].disabled = false;
			document.part.a19[2].disabled = false;
			document.part.a19[3].disabled = false;
		}else if(no =="2"){
			document.part.a17[2].checked = true;
			document.part.a18[2].checked = true;
			document.part.a19[2].checked = true;
			document.part.a17[0].disabled = false;
			document.part.a17[1].disabled = false;
			document.part.a17[2].disabled = false;
			document.part.a17[3].disabled = false; 
			document.part.a18[0].disabled = false;
			document.part.a18[1].disabled = false;
			document.part.a18[2].disabled = false;
			document.part.a18[3].disabled = false;
			document.part.a19[0].disabled = false;
			document.part.a19[1].disabled = false;
			document.part.a19[2].disabled = false;
			document.part.a19[3].disabled = false;
		}else{
			document.part.a17[3].checked = true;
			document.part.a18[3].checked = true;
			document.part.a19[3].checked = true;
			document.part.a17[0].disabled = false;
			document.part.a17[1].disabled = false;
			document.part.a17[2].disabled = false;
			document.part.a17[3].disabled = false; 
			document.part.a18[0].disabled = false;
			document.part.a18[1].disabled = false;
			document.part.a18[2].disabled = false;
			document.part.a18[3].disabled = false;
			document.part.a19[0].disabled = false;
			document.part.a19[1].disabled = false;
			document.part.a19[2].disabled = false;
			document.part.a19[3].disabled = false;
		}
		
	}
	
	if(id=="a20"){
		if(no =="0"){
			document.part.a21[0].checked = true;
			document.part.a22[0].checked = true;
			document.part.a23[0].checked = true;
			document.part.a30[0].checked = true;
			document.part.a21[0].disabled = true;
			document.part.a21[1].disabled = true;
			document.part.a21[2].disabled = true;
			document.part.a21[3].disabled = true; 
			document.part.a22[0].disabled = true;
			document.part.a22[1].disabled = true;
			document.part.a22[2].disabled = true;
			document.part.a22[3].disabled = true;
			document.part.a23[0].disabled = true;
			document.part.a23[1].disabled = true;
			document.part.a23[2].disabled = true;
			document.part.a23[3].disabled = true;
			document.part.a30[0].disabled = true;
			document.part.a30[1].disabled = true;
			document.part.a30[2].disabled = true;
			document.part.a30[3].disabled = true;
		}else if(no =="1"){
			document.part.a21[1].checked = true;
			document.part.a22[1].checked = true;
			document.part.a23[1].checked = true;
			document.part.a30[1].checked = true;
			document.part.a21[0].disabled = false;
			document.part.a21[1].disabled = false;
			document.part.a21[2].disabled = false;
			document.part.a21[3].disabled = false;
			document.part.a22[0].disabled = false;
			document.part.a22[1].disabled = false;
			document.part.a22[2].disabled = false;
			document.part.a22[3].disabled = false;
			document.part.a23[0].disabled = false;
			document.part.a23[1].disabled = false;
			document.part.a23[2].disabled = false;
			document.part.a23[3].disabled = false;
			document.part.a30[0].disabled = false;
			document.part.a30[1].disabled = false;
			document.part.a30[2].disabled = false;
			document.part.a30[3].disabled = false;
			
		}else if(no =="2"){
			document.part.a21[2].checked = true;
			document.part.a22[2].checked = true;
			document.part.a23[2].checked = true;
			document.part.a30[2].checked = true;
			document.part.a21[0].disabled = false;
			document.part.a21[1].disabled = false;
			document.part.a21[2].disabled = false;
			document.part.a21[3].disabled = false;
			document.part.a22[0].disabled = false;
			document.part.a22[1].disabled = false;
			document.part.a22[2].disabled = false;
			document.part.a22[3].disabled = false;
			document.part.a23[0].disabled = false;
			document.part.a23[1].disabled = false;
			document.part.a23[2].disabled = false;
			document.part.a23[3].disabled = false;
			document.part.a30[0].disabled = false;
			document.part.a30[1].disabled = false;
			document.part.a30[2].disabled = false;
			document.part.a30[3].disabled = false;
		}else{
			document.part.a21[3].checked = true;
			document.part.a22[3].checked = true;
			document.part.a23[3].checked = true;
			document.part.a30[3].checked = true;
			document.part.a21[0].disabled = false;
			document.part.a21[1].disabled = false;
			document.part.a21[2].disabled = false;
			document.part.a21[3].disabled = false;
			document.part.a22[0].disabled = false;
			document.part.a22[1].disabled = false;
			document.part.a22[2].disabled = false;
			document.part.a22[3].disabled = false;
			document.part.a23[0].disabled = false;
			document.part.a23[1].disabled = false;
			document.part.a23[2].disabled = false;
			document.part.a23[3].disabled = false;
			document.part.a30[0].disabled = false;
			document.part.a30[1].disabled = false;
			document.part.a30[2].disabled = false;
			document.part.a30[3].disabled = false;
		}
		
	}
}

function savePart(){

	
		
	document.part.action="<@spring.url '/system/updatePart.ssc'/>";
	document.part.submit();
}

function select(id){
	part.authid.value=id;

	$jq.ajax({
		url: "<@spring.url '/system/selectPart.ssc'/>",
		type: 'POST',
		dataType: 'json',
		data: $jq('#part').serialize(),
		error: function(){
		//alert('Return Error.');
		},
		success: function(data){
			var table ="";
			 			
			$jq('#partName').empty();
				table += '<dl class="btncover7">'
				if(data.roleauth[0].authId=="1"){
					table += '<dt>최고관리자</dt>'
				}else if(data.roleauth[0].authId=="3"){
					table += '<dt>VOD관리자</dt>'
				}else if(data.roleauth[0].authId=="5"){
					table += '<dt>일반관리자</dt>'
				}else{
					table += '<dt>제한관리자</dt>'
				}
				table += '</dl>'
				
			$jq('#partName').append(table);
			
			for(var i=2 ; i<=31 ; i++){
				$jq(".a"+i+"").attr("checked",false);
				$jq('.a'+i+'').filter('.a'+i+'[value=N]').attr("checked","checked");
			}
			
			for(var iq=0;iq<data.roleauth.length;iq++){
				
				$jq('.a'+data.roleauth[iq].menuId+'').filter('.a'+data.roleauth[iq].menuId+'[value='+data.roleauth[iq].controlGubun+']').attr("checked", "checked");
				
			}	
			
			$jq('.btncover4').attr("style","block");
		}
});
showDisHide('view','0');  
}

</script>
<section id="container">
	<form id="part" name="part" method="post">
	<input type="hidden" name="authid" value="" />
    <section id="content">
    	<h3 class="blind">역할 관리</h3>
		<article class="title"><img src="<@spring.url '/images/title_part.gif' />" title="역할 관리"/></article>
        <nav class="snavi"><span class="home">HOME</span> &gt; 시스템 관리 &gt; <span class="now">역할 관리</span></nav>
        <article class="user">
        	<ol>
        	<#list authTbls as auth>
            <li><a href="javascript:" onclick="select('${auth.authId!""}')">${auth.authNm!""}</a></li><br>
           
            </#list>
            
            </ol>
        </article>
        <article class="nav_tree" id="view">
        	<article class="sideinfo" id="partName">
	        
	        </article>	
	        
            <ul>
            <li class="nav_tree_first nav_tree_on">
            <button type="button">+</button>
            <label class="nav_tree_label">편성표</label>  
            	<dl class="option">
                	<dt>없음</dt>
                    <dd><input id="a2" name="a2" class="a2" type="radio" value="N" onclick="check('a2','0')"></dd>
                	<dt>쓰기</dt>
                    <dd><input id="a2" name="a2" class="a2" type="radio" value="RW" onclick="check('a2','1')"></dd>
                	<dt>다운로드</dt>
                    <dd><input id="a2" name="a2" class="a2" type="radio" value="RD" onclick="check('a2','2')"></dd>
                	<dt>조회</dt>
                    <dd><input id="a2" name="a2" class="a2" type="radio" value="R" onclick="check('a2','3')"></dd>
                </dl>
                <ul>
                <li class="nav_tree_off">
                <label class="nav_tree_label">1TV</label> 
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a3" class="a3" type="radio" value="N"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a3" class="a3" type="radio" value="RW"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a3" class="a3" type="radio" value="RD"></dd>
                	<dt>조회</dt>
                    <dd><input name="a3" class="a3" type="radio" value="R"></dd>
                </dl>
                </li>
				<li class="nav_tree_on">
                <label class="nav_tree_label">2TV</label>
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a4" class="a4" type="radio" value="N"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a4" class="a4" type="radio" value="RW"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a4" class="a4" type="radio" value="RD"></dd>
                	<dt>조회</dt>
                    <dd><input name="a4" class="a4" type="radio" value="R"></dd>
                </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">1라디오</label>
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a24" class="a24" type="radio" value="N"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a24" class="a24" type="radio" value="RW"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a24" class="a24" type="radio" value="RD"></dd>
                	<dt>조회</dt>
                    <dd><input name="a24" class="a24" type="radio" value="R"></dd>
                </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">2라디오</label>
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a25" class="a25" type="radio" value="N"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a25" class="a25" type="radio" value="RW"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a25" class="a25" type="radio" value="RD"></dd>
                	<dt>조회</dt>
                    <dd><input name="a25" class="a25" type="radio" value="R"></dd>
                </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">3라디오</label>
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a26" class="a26" type="radio" value="N"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a26" class="a26" type="radio" value="RW"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a26" class="a26" type="radio" value="RD"></dd>
                	<dt>조회</dt>
                    <dd><input name="a26" class="a26" type="radio" value="R"></dd>
                </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">1FM</label>
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a27" class="a27" type="radio" value="N"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a27" class="a27" type="radio" value="RW"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a27" class="a27" type="radio" value="RD"></dd>
                	<dt>조회</dt>
                    <dd><input name="a27" class="a27" type="radio" value="R"></dd>
                </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">2FM</label>
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a28" class="a28" type="radio" value="N"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a28" class="a28" type="radio" value="RW"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a28" class="a28" type="radio" value="RD"></dd>
                	<dt>조회</dt>
                    <dd><input name="a28" class="a28" type="radio" value="R"></dd>
                </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">한민족1</label>
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a29" class="a29" type="radio" value="N"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a29" class="a29" type="radio" value="RW"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a29" class="a29" type="radio" value="RD"></dd>
                	<dt>조회</dt>
                    <dd><input name="a29" class="a29" type="radio" value="R"></dd>
                </dl>
                </li>
                
                <li class="nav_tree_last nav_tree_on">
                <label class="nav_tree_label">DMB</label>
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a31" class="a31" type="radio" value="N"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a31" class="a31" type="radio" value="RW"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a31" class="a31" type="radio" value="RD"></dd>
                	<dt>조회</dt>
                    <dd><input name="a31" class="a31" type="radio" value="R"></dd>
                </dl>
                </li>
                
                </ul>
            </li>
            <li class="nav_tree_on">
            <button type="button">-</button>
            <label class="nav_tree_label">콘텐츠관리</label>  
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a5" class="a5" type="radio" value="N" onclick="check('a5','0')"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a5" class="a5" type="radio" value="RW" onclick="check('a5','1')"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a5" class="a5" type="radio" value="RD" onclick="check('a5','2')"></dd>
                	<dt>조회</dt>
                    <dd><input name="a5" class="a5" type="radio" value="R" onclick="check('a5','3')"></dd>
                </dl>
                <ul>
                <li class="nav_tree_on">
                <label class="nav_tree_label">아카이브관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a6" class="a6" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a6" class="a6" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a6" class="a6" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a6" class="a6" type="radio" value="R"></dd>
                    </dl>
                </li>
                <li class="nav_tree_last nav_tree_on">
                <label class="nav_tree_label">콘텐츠</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a7" class="a7" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a7" class="a7" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a7" class="a7" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a7" class="a7" type="radio" value="R"></dd>
                    </dl>
                </li>                
                </ul> 
            </li>
            <li class="nav_tree_on">
            <button type="button">-</button>
            <label class="nav_tree_label">워크플로우</label>  
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a8" class="a8" type="radio" value="N" onclick="check('a8','0')"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a8" class="a8" type="radio" value="RW" onclick="check('a8','1')"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a8" class="a8" type="radio" value="RD" onclick="check('a8','2')"></dd>
                	<dt>조회</dt>
                    <dd><input name="a8" class="a8" type="radio" value="R" onclick="check('a8','3')"></dd>
                </dl>
                <ul>
                <li class="nav_tree_on">
                <label class="nav_tree_label">워크플로우관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a9" class="a9" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a9" class="a9" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a9" class="a9" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a9" class="a9" type="radio" value="R"></dd>
                    </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">전송관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a10" class="a10" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a10" class="a10" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a10" class="a10" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a10" class="a10" type="radio" value="R"></dd>
                    </dl>
                </li>
                <li class="nav_tree_last nav_tree_on">
                <label class="nav_tree_label">작업관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a11" class="a11" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a11" class="a11" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a11" class="a11" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a11" class="a11" type="radio" value="R"></dd>
                    </dl>
                </li>                  
                </ul> 
            </li> 
            <li class="nav_tree_on">
            <button type="button">-</button>
            <label class="nav_tree_label">서비스</label>  
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a12" class="a12" type="radio" value="N" onclick="check('a12','0')"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a12" class="a12" type="radio" value="RW" onclick="check('a12','1')"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a12" class="a12" type="radio" value="RD" onclick="check('a12','2')"></dd>
                	<dt>조회</dt>
                    <dd><input name="a12" class="a12" type="radio" value="R" onclick="check('a12','3')"></dd>
                </dl>
                <ul>
                <li class="nav_tree_on">
                <label class="nav_tree_label">프로파일관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a13" class="a13" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a13" class="a13" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a13" class="a13" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a13" class="a13" type="radio" value="R"></dd>
                    </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">프로파일옵션</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a14" class="a14" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a14" class="a14" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a14" class="a14" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a14" class="a14" type="radio" value="R"></dd>
                    </dl>
                </li>
                <li class="nav_tree_last nav_tree_on">
                <label class="nav_tree_label">사업자관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a15" class="a15" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a15" class="a15" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a15" class="a15" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a15" class="a15" type="radio" value="R"></dd>
                    </dl>
                </li>                
                </ul> 
            </li>
            <li class="nav_tree_on">
            <button type="button">-</button>
            <label class="nav_tree_label">폐기관리</label>  
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a16" class="a16" type="radio" value="N" onclick="check('a16','0')"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a16" class="a16" type="radio" value="RW" onclick="check('a16','1')"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a16" class="a16" type="radio" value="RD" onclick="check('a16','2')"></dd>
                	<dt>조회</dt>
                    <dd><input name="a16" class="a16" type="radio" value="R" onclick="check('a16','3')"></dd>
                </dl>
                <ul>
                <li class="nav_tree_on">
                <label class="nav_tree_label">폐기검색</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a17" class="a17" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a17" class="a17" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a17" class="a17" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a17" class="a17" type="radio" value="R"></dd>
                    </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">폐기현황</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a18" class="a18" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a18" class="a18" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a18" class="a18" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a18" class="a18" type="radio" value="R"></dd>
                    </dl>
                </li>
                <li class="nav_tree_last nav_tree_on">
                <label class="nav_tree_label">폐기연장</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a19" class="a19" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a19" class="a19" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a19" class="a19" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a19" class="a19" type="radio" value="R"></dd>
                    </dl>
                </li>                
                </ul>
            </li>
            <li class="nav_tree_last nav_tree_on">
            <button type="button">-</button>
            <label class="nav_tree_label">시스템관리</label>  
				<dl class="option">
                	<dt>없음</dt>
                    <dd><input name="a20" class="a20" type="radio" value="N" onclick="check('a20','0')"></dd>
                	<dt>쓰기</dt>
                    <dd><input name="a20" class="a20" type="radio" value="RW" onclick="check('a20','1')"></dd>
                	<dt>다운로드</dt>
                    <dd><input name="a20" class="a20" type="radio" value="RD" onclick="check('a20','2')"></dd>
                	<dt>조회</dt>
                    <dd><input name="a20" class="a20" type="radio" value="R" onclick="check('a20','3')"></dd>
                </dl>
                <ul>
                <li class="nav_tree_on">
                <label class="nav_tree_label">작업자관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a21" class="a21" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a21" class="a21" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a21" class="a21" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a21" class="a21" type="radio" value="R"></dd>
                    </dl>
                </li>
                <li class="nav_tree_on">
                <label class="nav_tree_label">역할관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a22" class="a22" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a22" class="a22" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a22" class="a22" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a22" class="a22" type="radio" value="R"></dd>
                    </dl>
                </li>             
                <li class="nav_tree_on">
                <input type="hidden" name="" value=""/>
                <label class="nav_tree_label">타시스템관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a23" class="a23" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a23" class="a23" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a23" class="a23" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a23" class="a23" type="radio" value="R"></dd>
                    </dl>
                </li> 
                <li class="nav_tree_last nav_tree_on">
                <label class="nav_tree_label">코드관리</label>  
                    <dl class="option">
                        <dt>없음</dt>
                        <dd><input name="a30" class="a30" type="radio" value="N"></dd>
                        <dt>쓰기</dt>
                        <dd><input name="a30" class="a30" type="radio" value="RW"></dd>
                        <dt>다운로드</dt>
                        <dd><input name="a30" class="a30" type="radio" value="RD"></dd>
                        <dt>조회</dt>
                        <dd><input name="a30" class="a30" type="radio" value="R"></dd>
                    </dl>
                </li>                 
                </ul>
            </li>
            </ul><br>
            <article class="btncover4" style="display:none">
				<span class="btn_pack gry"><a href="javascript:" onclick="savePart()">저장</a></span>
			
			</article>
        </article>
	</section>
	</from>