/* 
  function getRealOffsetTop(el) {
     return el ? el.offsetTop + getRealOffsetTop(el.offsetParent) : 0;
   }
 
  function getRealOffsetLeft(el) {
     return el ? el.offsetLeft + getRealOffsetLeft(el.offsetParent) : 0;
   }
 
  var obj;
   function roadMenu(el, pos) {
 
    var top = getRealOffsetTop(el) + el.offsetHeight;
     var left = getRealOffsetLeft(el);
     var sub = document.getElementById("sub");
     sub.style.left = parseInt(el.offsetWidth / 2)+left+"px";
     sub.style.top = top-50+"px";
     sub.style.display = "block";
     if(obj) obj.style.display = "none";
     obj = document.getElementById("lay_"+pos);
     obj.style.display = "block";
     obj.style.top=top-38+"px";
     obj.style.left=100+"px";
   }
 
//- 롤오버서브메뉴 
*/
function MM_preloadImages() {
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { 
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { 
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() {
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function flashCreateObject0( id, name, width, height ,srcValue) {
	document.write('<object classid="clsid:'+id+'" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="'+width+'" height="'+height+'">');
	document.write('<param name="'+name+'" value="'+srcValue+'">');
	document.write('<param name="quality" value="high">');
	document.write('<param name="wmode" value="transparent">');
	document.write('<embed src="'+srcValue+'" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="'+width+'" height="'+height+'" wmode="transparent"></embed>');
	document.write('</object>');
}

function winopen_pop_read() {		
	window.open("/movie/pop.html","","width=732,height=538");
}	

function openWinCenter(url, wname, wopt) {
    var newopt = "", wHeight = 0, wWidth = 0;
    if (wopt != undefined) {
        var woptlist = wopt.replace(/ /g, "").split(",");
        for (var i in woptlist) {
            if (woptlist[i].match(/^height=/i)) {
                wHeight = parseInt(woptlist[i].substr(7),10);
                if (!isNaN(wHeight)) newopt += "top=" + Math.floor((screen.availHeight - wHeight) / 2) + ",";
            }
            if (woptlist[i].match(/^width=/i)) {
                wWidth = parseInt(woptlist[i].substr(6),10);
                if (!isNaN(wWidth)) newopt += "left=" + Math.floor((screen.availWidth - wWidth) / 2) + ",";
            }
        }
    }
    return window.open(url, wname, newopt + wopt);
} 

function closewin()
{ 
openWinCenter.close();
}

//* topmenu *//

function JS_mainTab(menuId){
	var nav = document.getElementById("main-tab"+menuId);
	for(i = 1; i <= 4; i++) {
		document.getElementById("main-tab"+i).style.display = "none";
	}
	nav.style.display = "block";
}

function JS_tabClick(imgId) {
	var nav = document.getElementById("img"+imgId);
	//alert(nav.src);
	if(nav.src.substring(nav.src.length-7) != "_on.gif"){
		nav.src = nav.src.replace("_off.gif", "_on.gif");
		return;
	}

	if(nav.src.substring(nav.src.length-8) != "_off.gif"){
		nav.src = nav.src.replace("_on.gif", "_off.gif");
		return;
	}
}

function JS_tabOver(imgId) {
	var nav = document.getElementById("img"+imgId);
	//alert(nav.src);
	if(nav.src.substring(nav.src.length-7) != "_on.gif")
		nav.src = nav.src.replace("_off.gif", "_on.gif");
}

function JS_tabOut(imgId) {
	var nav = document.getElementById("img"+imgId);
	nav.src = nav.src.replace("_on.gif", "_off.gif");
}

function JS_contentSaveOver(code) {
	var nav = document.getElementById(code);
	nav.className = "part contentOverColor";
}
function JS_contentSaveOut(code) {
	var nav = document.getElementById(code);
	nav.className = "part";
}


/* IMG 메뉴 스크립트 */
function initNavigation(seq) {
	nav = document.getElementById("topmenu");
	nav.menu = new Array();
	nav.current = null;
	nav.menuseq = 0;
	navLen = nav.childNodes.length;

	allA = nav.getElementsByTagName("LI");
	for(k = 0; k < allA.length; k++) {
		allA.item(k).onmouseover = allA.item(k).onfocus = function () {
			nav.isOver = true;
		}
		allA.item(k).onmouseout = allA.item(k).onblur = function () {
			nav.isOver = false;
			setTimeout(function () {
				if (nav.isOver == false) {
					if(nav.menu[seq])
						nav.menu[seq].onmouseover();
					else if(nav.current){
						if(nav.current.submenu)
							nav.current.submenu.style.visibility = "hidden";
						nav.current = null;
					}
				}
			}, 3000);
		}
	}

	// 1depth
	for (i = 0; i < navLen; i++) {
		navItem = nav.childNodes.item(i);
		if (navItem.tagName != "LI")
			continue;

		navAnchor = navItem.getElementsByTagName("a").item(0);
		navAnchor.submenu = navItem.getElementsByTagName("ul").item(0);

		navAnchor.onmouseover = navAnchor.onfocus = function () {
			if (nav.current) {
				if (nav.current.submenu)
					nav.current.submenu.style.visibility = "hidden";
				nav.current = null;
			}
			if (nav.current != this) {
				if (this.submenu) {
					this.submenu.style.visibility = "visible";
				}
				nav.current = this;
			}
			nav.isOver = true;
		}
		nav.menuseq++;
		nav.menu[nav.menuseq] = navAnchor;
	}
	if (nav.menu[seq])
		nav.menu[seq].onmouseover();
}

//Adijust Layout
window.onload = function() {
	window.setInterval(function() {
		bodyEI = document.getElementById("body");
		subEI = document.getElementById("sub");
		if(!bodyEI || !subEI) {
			return;
			if(bodyEI.offsetHeight < subEI.offsetHeight + 25) {
				bodyEI.style.height = subEI.offsetHeight + "px";
			}
		}
	}, 200);
}

function initBoardList() {
	boardPager = document.getElementById("board-pager");
	if(boardPager)
	boardPager.getElementByTagName("li").item(2).style.borderStyle="none";
}

function initBoardViewPhoto() {
	imageContainer = document.getElementById("attach-photo");
	if(imageContainer) {
		imgEI = imageContainer.getElementsByTagName("img");
		for(i = 0; i<imgEI.length; i++) {
			if(imagesContainer.offsetWidth < imgEI.item(i).offsetWidth) {
				imgEI.item(i).style.width = imageContainer.offsetWidth;
			}
		}
	}
}

//popup layer
var cc=0
function showHide(id){
	if(cc==0){
		cc=1
		//2013-05-02 
		//ie 외에 화면 깨짐 수정
		//document.getElementById(id).style.display="block";
		document.getElementById(id).style.display="";
	}else{
		cc=0
		document.getElementById(id).style.display="none";
	}
}


function showDisHide(id, pp){

	if(pp == '0') {
		//2013-05-02 
		//ie 외에 화면 깨짐 수정
		//document.getElementById(id).style.display="block";
		document.getElementById(id).style.display="";
	} else {
		
		document.getElementById(id).style.display="none";
	}
}


