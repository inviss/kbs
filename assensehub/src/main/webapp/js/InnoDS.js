var InnoInstall = "../install.html";	// 사용자 설치안내 페이지
var InnoUrlBack = "<@spring.url '/content/WebReg.ssc'/>";	// 사용자 설치 완료후 이동할 페이지
var InnoPackage = "/package";		// 사용자 OS별 설치파일 경로

// -----------------------------------

var Enc = new String();
var Prod = new String();
var Dir = new String();

var CharSet = "UTF-8";
var UploadURL = new String();
var SubDir = new String();
var InputName = new String("file");
var InputType = "fixed";

var DialogListHeight = 0;
var ListStyle = new String("");
var BkImgURL = new String("");
var ViewType = 0;
var ShowFullPath = "false";
var ShowStatus = "true";

var InnoDS = null;
var Innorix_Version = "2,1,1,7";

var ExistFileAdd = "false";
var ShowFileOpen = "true";

var UseLog = "false";
var UseLogFolderOpen = "false";
var UseDragDrop = "true";

var LimitExt = new String();
var ExtPermission = "false";
var UseContextMenu = "true"; 

function GetDSObject( objName )
{
	var obj = document.getElementById(objName);
	if( obj == null )
	{
		obj = document.getElementById("_"+objName);
	}
	return obj;
}

function OnIELoadComplete( objName )
{
	try 
	{
		eval( OnLoadComplete( objName ));
	}
	catch (e)
	{
		// alert(e);
	}
}

function OnNPLoadComplete( objName )
{
	try 
	{	
		if( browserDetect.browser == "Firefox" )
		{
			setTimeout( 'OnLoadComplete( "+objName+" )', 0 );		
		}
		else
		{
			eval( OnLoadComplete( GetDSObject(objName) ) );
		}
	}
	catch (e)
	{
		// alert(e);
	}
	return true;
}

function InnoDSInitMulti( MaxTotalSize, MaxFileSize, MaxFileCount, nWidth, nHeight , strID )
{
	if (innomp_pkg_update()) innomp_install_guide();

	if( nWidth < 0  )
	{
		nWidth = 0;
	}

	if( nHeight < 0 )
	{
		nHeight = 0;
	}

	var nWidth1 = nWidth;
	var nHeight1 = nHeight;

	if( browserOS == "MacIntel" || browserOS == "MacPPC" )
	{
		ListStyle = "report";
		nWidth1 = 1;
		nHeight1 = 1;
	}

	if (Prod == "")
	{
		var licStrIE = 	"<param name=\"ENC\" value=\""+Enc+"\">";
		var licStrNP = 	"ENC=\""+Enc+"\"";
	}
	else
	{
		var licStrIE = 	"<param name=\"Prod\" value=\""+Prod+"\">" +
						"<param name=\"Dir\" value=\""+Dir+"\">";
		var licStrNP = 	"Prod=\""+Prod+"\"" +
						"Dir=\""+Dir+"\"";
	}
	
	var tStrID = ("_"+strID);

	if (browserName == "Explorer")	
	{
		var tStr = "<object codebase=\"#version="+Innorix_Version+"\" onerror=\"innomp_install_guide()\" id=\""+tStrID+"\" classid=\"CLSID:8818A9CD-6A04-46E0-8F81-01CA85B47BC3\" width=\""+ nWidth1 +"\" height=\""+ nHeight1 +"\">" +
		licStrIE +
		"<param name=\"UploadURL\" value=\""+UploadURL+"\">" +
		"<param name=\"ShowStatusBar\" value=\""+ShowStatus+"\">" +
		"<param name=\"ListStyle\" value=\""+ListStyle+"\">" +
		"<param name=\"UploadMode\" value=\"ds\">" +
		"<param name=\"MaxTotalSize\" value=\""+MaxTotalSize+"\">" +
		"<param name=\"MaxFileSize\" value=\""+MaxFileSize+"\">" +
		"<param name=\"MaxFileCount\" value=\""+MaxFileCount+"\">" +
		"<param name=\"CharSet\" value=\""+CharSet+"\">" +
		"<param name=\"InputName\" value=\""+InputName+"\">" +
		"<param name=\"InputType\" value=\""+InputType+"\">" +
		"<param name=\"ShowFullPath\" value=\""+ShowFullPath+"\">" +
		"<param name=\"DialogListHeight\" value=\""+DialogListHeight+"\">" +
		"<param name=\"ExistFileAdd\" value=\""+ExistFileAdd+"\">" +
		"<param name=\"ShowFileOpen\" value=\""+ShowFileOpen+"\">" +
		"<param name=\"ViewType\" value=\""+ViewType+"\">" +
		"<param name=\"ObjectName\" value=\""+strID+"\">" +
		"<param name=\"UseLog\" value=\""+UseLog+"\">" +
		"<param name=\"UseLogFolderOpen\" value=\""+UseLogFolderOpen+"\">" +
		"<param name=\"LimitExt\" value=\""+LimitExt+"\">" +
		"<param name=\"ExtPermission\" value=\""+ExtPermission+"\">" +
		"<param name=\"BkImgURL\" value=\""+BkImgURL+"\">" +
		"<param name=\"UseContextMenu\" value=\""+UseContextMenu+"\">" +
		"</object><br>";	
	}
	else
	{
		var tStr = "<embed name="+strID+" id="+strID+" type=\"application/innorix-multi-platform\" allowscriptaccess=\always\" width=\""+ nWidth1  +"\" height=\""+ nHeight1  +"\"" +
		licStrNP +
		"UploadURL=\""+UploadURL+"\"" +
		"ShowStatusBar=\""+ShowStatus+"\"" +
		"ListStyle=\""+ListStyle+"\"" +
		"UploadMode=\"ds\"" +
		"MaxTotalSize=\""+MaxTotalSize+"\"" +
		"MaxFileSize=\""+MaxFileSize+"\"" +
		"MaxFileCount=\""+MaxFileCount+"\"" +
		"CharSet=\""+CharSet+"\"" +
		"InputName=\""+InputName+"\"" +
		"InputType=\""+InputType+"\"" +
		"ShowFullPath=\""+ShowFullPath+"\"" +
		"DialogListHeight=\""+DialogListHeight+"\"" +
		"ExistFileAdd=\""+ExistFileAdd+"\"" +
		"ShowFileOpen=\""+ShowFileOpen+"\"" +
		"ViewType=\""+ViewType+"\"" +
		"ObjectName=\""+strID+"\"" +
		"UseLog=\""+UseLog+"\"" +
		"UseLogFolderOpen=\""+UseLogFolderOpen+"\"" +
		"UseDragDrop=\""+UseDragDrop+"\"" +
		"LimitExt=\""+LimitExt+"\"" +
		"ExtPermission=\""+ExtPermission+"\"" +
		"BkImgURL=\""+BkImgURL+"\"" +
		"UseContextMenu=\""+UseContextMenu+"\"" +
		"></embed>";
	}
	
	document.writeln(tStr);
	document.close();

	InnoDS = document.getElementById(strID);
	if( InnoDS == null )
	{
		InnoDS = document.getElementById(tStrID);
	}

	if( browserOS == "MacIntel" || browserOS == "MacPPC" )
	{
		InnoDSInit_HTML( MaxTotalSize, MaxFileSize, MaxFileCount, nWidth, nHeight, strID );
	}
}

function InnoDSInit( MaxTotalSize, MaxFileSize, MaxFileCount, nWidth, nHeight  )
{
	InnoDSInitMulti( MaxTotalSize, MaxFileSize, MaxFileCount, nWidth, nHeight, "InnoDS_innorix" );
}

function InnoDSSubmitMulti(_FormObject, _DSObject)
{
	var itemCount = _DSObject.UploadFileCount;
	var z = '';
	for( var i = 0 ; i < itemCount ; i++ )
	{
		if (InputType == 'array') 
		{
			z = '[]';
		} 
		else if (InputType == 'ordernum') 
		{
			z = new String(i+1);
		} 
		else 
		{
			z = '';
		}
		var index = ""+i;
		var FileName = _DSObject.GetUploadFileName(index);
		var FileSize = _DSObject.GetUploadFileSize(index);
		var Folder = _DSObject.GetUploadFolder(index);
		
		if( Folder == " " )
		{
			Folder = "";
		}
		var SendBytes = _DSObject.GetUploadBytes(index);
		var Status = _DSObject.GetUploadStatus(index);
		var _ds_filename = '_innods_filename' + z;
		var _ds_filesize = '_innods_filesize' + z;
		var _ds_filefolder = '_innods_folder' + z;
		var _ds_sendbytes = '_innods_sendbytes' + z;
		var _ds_status = '_innods_status' + z;

		var oInput1 = document.createElement( 'input' );
		var oInput2 = document.createElement( 'input' );
		var oInput3 = document.createElement( 'input' );
		var oInput4 = document.createElement( 'input' );
		var oInput5 = document.createElement( 'input' );
		
		oInput1.type = 'hidden';
		oInput2.type = 'hidden';
		oInput3.type = 'hidden';
		oInput4.type = 'hidden';
		oInput5.type = 'hidden';

		oInput1.name = _ds_filename ;
		oInput2.name = _ds_filesize ;
		oInput3.name = _ds_filefolder ;
		oInput4.name = _ds_sendbytes ;
		oInput5.name = _ds_status ;

		oInput1.value = FileName;
		oInput2.value = FileSize;
		oInput3.value = Folder;
		oInput4.value = SendBytes;
		oInput5.value = Status;
		
		_FormObject.insertBefore( oInput1, _FormObject.firstChild );
		_FormObject.insertBefore( oInput2, _FormObject.firstChild );
		_FormObject.insertBefore( oInput3, _FormObject.firstChild );
		_FormObject.insertBefore( oInput4, _FormObject.firstChild );
		_FormObject.insertBefore( oInput5, _FormObject.firstChild );

	}

	var oInput  = document.createElement('input');
	oInput.type = 'hidden';
	oInput.name = '_SUB_DIR';
	oInput.value = SubDir;

	_FormObject.insertBefore(oInput, _FormObject.firstChild);

	_FormObject.submit();
}

function InnoDSSubmit(_FormObject)
{
    InnoDSSubmitMulti(_FormObject, InnoDS  );
}

////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// HTML List Ctrl ( DO NOT MODIFY )
//
////////////////////////////////////////////////////////////////////////////////////////////////////////

var bUseHtmlCtrl = false;
var g_nWidth 	= 0;
var g_nHeight	= 0;

var g_view0_0 = 0.72;
var g_view0_1 = 0.236;
var g_view0_2 = 0.044;

var g_view1_0 = 0.52;
var g_view1_1 = 0.236;
var g_view1_2 = 0.20;
var g_view1_3 = 0.044;

var g_view2_0 = 0.50;
var g_view2_1 = 0.152;
var g_view2_2 = 0.152;
var g_view2_3 = 0.152;
var g_view2_4 = 0.044;


function OnUpdateListCtrl( objName , arg )
{
	if( bUseHtmlCtrl == false )
	{
		return;
	} 
	

	var result = JSON.parse( arg, reviver( arg ) );
	HeaderSetting( objName, result.data[0].viewtype, result.header[0] );
	var item_cnt = result.items.length;
	var viewtype = result.data[0].viewtype;

	var CtrlName = objName + "Ctrl";
	var FileListID = objName + "file_list";
	
	var frm = document.getElementById(CtrlName).contentWindow;
	var tbl = frm.document.getElementById(FileListID);
    var tbl_index = tbl.rows.length;


	for( var i = tbl_index-1; i >= 0 ; i-- )
	{
		tbl.deleteRow( i );
	}

	for( var i = 0 ; i < item_cnt ; i++ )
	{
		if( viewtype == 0 )
		{
			var filename = result.items[i].filename;
			var filesize = result.items[i].filesize;
			if( filesize == "" )
			{
				filesize = "";
			}
			additem( objName,  0, filename, filesize , null, null , i);

		}
		else if( viewtype == 1 )
		{
			var filename = result.items[i].filename;
			var filesize = result.items[i].filesize;
			var folder = result.items[i].folder;
			if( folder == "" )
			{
				folder = "&nbsp;";
			}
			if( filesize == "" )
			{
				filesize = "&nbsp;";
			}

			additem( objName, 1, filename, filesize , folder,  null , i);
		}
		else if( viewtype == 2 )
		{
			var filename = result.items[i].filename;
			var filesize = result.items[i].filesize;
			var filecnt = result.items[i].filecnt;
			var folder = result.items[i].folder;
			if( folder == "" )
			{
				folder = "&nbsp;";
			}
			if( filesize == "" )
			{
				filesize = "&nbsp;";
			}
			if( filecnt == "" )
			{
				filecnt = "&nbsp";
			}

			additem( objName, 2, filename, filesize , filecnt,  folder , i);
		}
	}
	SetStatusBar( objName, result.status[0] );
}

function OnUpdateStatusBar( objName,  arg )
{
	var result = JSON.parse( arg, reviver( arg ) );
	SetStatusBar( objName,  result );
}

function HeaderSetting( objName, viewtype, header )
{
	if( bUseHtmlCtrl == false )
	{
		return;
	} 

	var CtrlName = objName+"Ctrl";
	var ListHeaderName = objName+"ListHeader";

	var frm = document.getElementById(CtrlName).contentWindow;
	var tbl = frm.document.getElementById(ListHeaderName);

	if( tbl.rows.length > 0 )
	{
		tbl.deleteRow( 0 );
	}
	
	if( viewtype == 0 )
	{
		var row = tbl.insertRow( 0 );
		row.bgColor="#DEDEDE";

		var cel0 = row.insertCell( 0 );
		var cel1 = row.insertCell( 1 );
		var cel2 = row.insertCell( 2 );

		cel1.width = (g_nWidth*g_view0_1);
		cel2.width = 15;
		cel0.width = (g_nWidth - cel1.width - cel2.width);

		cel0.align="center";
		cel0.style.borderRight = '1px #CDCDCD solid';

		cel1.align="center";
		cel1.style.borderRight = '1px #CDCDCD solid';

		cel2.align="center";

		cel0.innerHTML = "<font style=\"font-size:9pt;\">"+header.filename+"</font>";
		cel1.innerHTML = "<font style=\"font-size:9pt;\">"+header.filesize+"</font>";
		cel2.innerHTML = "<font style=\"font-size:9pt;\">"+" "+"</font>";

	}
	else if( viewtype == 1 )
	{
		var row = tbl.insertRow( 0 );
		row.bgColor="#DEDEDE";
		var cel0 = row.insertCell( 0 );
		var cel1 = row.insertCell( 1 );
		var cel2 = row.insertCell( 2 );
		var cel3 = row.insertCell( 3 );


		cel1.width = (g_nWidth*g_view1_1);
		cel2.width = (g_nWidth*g_view1_2);
		cel3.width = 15;
		cel0.width = (g_nWidth - cel1.width - cel2.width - cel3.width);


		cel0.align="center";
		cel0.style.borderRight = '1px #CDCDCD solid';

		cel1.align="center";
		cel1.style.borderRight = '1px #CDCDCD solid';

		cel2.align="center";
		cel2.style.borderRight = '1px #CDCDCD solid';

		cel3.align="center";

		cel0.innerHTML = "<font style=\"font-size:9pt;\">"+header.filename+"</font>";
		cel1.innerHTML = "<font style=\"font-size:9pt;\">"+header.filesize+"</font>";
		cel2.innerHTML = "<font style=\"font-size:9pt;\">"+header.folder+"</font>";
		cel3.innerHTML = "<font style=\"font-size:9pt;\">"+" "+"</font>";

	}
	else if( viewtype == 2 )
	{
		var row = tbl.insertRow( 0 );
		row.bgColor="#DEDEDE";
		var cel0 = row.insertCell( 0 );
		var cel1 = row.insertCell( 1 );
		var cel2 = row.insertCell( 2 );
		var cel3 = row.insertCell( 3 );
		var cel4 = row.insertCell( 4 );


		cel1.width = (g_nWidth*g_view2_1);
		cel2.width = (g_nWidth*g_view2_2);
		cel3.width = (g_nWidth*g_view2_3);
		cel4.width = 15;
		cel0.width = (g_nWidth - cel1.width - cel2.width - cel3.width - cel4.width);


		cel0.align="center";
		cel0.style.borderRight = '1px #CDCDCD solid';

		cel1.align="center";
		cel1.style.borderRight = '1px #CDCDCD solid';

		cel2.align="center";
		cel2.style.borderRight = '1px #CDCDCD solid';

		cel3.align="center";
		cel3.style.borderRight = '1px #CDCDCD solid';

		cel4.align="center";

		cel0.innerHTML = "<font style=\"font-size:9pt;\">"+header.filename+"</font>";
		cel1.innerHTML = "<font style=\"font-size:9pt;\">"+header.filesize+"</font>";
		cel2.innerHTML = "<font style=\"font-size:9pt;\">"+header.filecnt+"</font>";
		cel3.innerHTML = "<font style=\"font-size:9pt;\">"+header.folder+"</font>";
		cel4.innerHTML = "<font style=\"font-size:9pt;\">"+" "+"</font>";

	}
}

function SetStatusBar( objName, status )
{
	if( bUseHtmlCtrl == false )
	{
		return;
	} 

	var CtrlName = objName+"Ctrl";
	var ListStatusBarName = objName + "ListStatusBar";

	var frm = document.getElementById(CtrlName).contentWindow;
	var tbl = frm.document.getElementById(ListStatusBarName);
	var tbl_index = tbl.rows.length;

	for( var i = tbl_index-1; i >= 0 ; i-- )
	{
		tbl.deleteRow( i );
	}

	var bUseStatus = status.use;
	if( bUseStatus == "true" )
	{
		if( status.type == 0 )
		{
			var row = tbl.insertRow( 0 );
			row.bgColor="#DEDEDE";
			row.height=21;
			var cel0 = row.insertCell( 0 );
			var cel1 = row.insertCell( 1 );

			cel0.width=(g_nWidth*0.5);
			cel1.width=(g_nWidth*0.5);


			cel0.style.borderRight='1px #CDCDCD solid';

			cel0.innerHTML = "<font style=\"font-size:9pt;\">&nbsp;"+ status.filecnt +"</font>";
			cel1.innerHTML = "<font style=\"font-size:9pt;\">&nbsp;"+ status.filesize +"</font>";


		}
		else if( status.type == 1 )
		{
			var row = tbl.insertRow( 0 );
			row.bgColor="#DEDEDE";
			row.height=20;
			var cel0 = row.insertCell( 0 );
			var cel1 = row.insertCell( 1 );
			var cel2 = row.insertCell( 2 );

			cel0.width = (g_nWidth*0.333);	
			cel1.width = (g_nWidth*0.333);	
			cel2.width = (g_nWidth*0.333);	

			cel0.style.borderRight='1px #CDCDCD solid';
			cel1.style.borderRight='1px #CDCDCD solid';


			cel0.innerHTML = "<font style=\"font-size:9pt;\">"+ status.foldercnt +"</font>";
			cel1.innerHTML = "<font style=\"font-size:9pt;\">"+ status.filecnt +"</font>";
			cel2.innerHTML = "<font style=\"font-size:9pt;\">"+ status.filesize +"</font>";

		}
	}
	else
	{

	}
}

function OnCheck( objName , msg )
{
	
	var result = JSON.decode(msg);
	var result = JSON.parse( msg, reviver( msg ) );
	var index = parseInt( result.idx );
	var select = result.select;
	
	var CtrlName = objName+"Ctrl";
	var FileListID = objName+"file_list";

	var frm = document.getElementById(CtrlName).contentWindow;
	var tbl = frm.document.getElementById(FileListID);
	var tbl_row = tbl.rows.length;


	for( var i = 0 ; i < tbl_row ; i++ )
	{
		if( i == index )
		{
			if( tbl.rows[i].selected )
			{
				if( select == "false"  )
				{
					tbl.rows[i].selected = false;
					tbl.rows[i].bgColor = "#ffffff";
				}
			}
			else
			{
				if( select == "true" )
				{
					tbl.rows[i].selected = true;
					tbl.rows[i].bgColor = "#FDECBA";
				}
			}
			return;
		}	
	}
}

function  additem(  objName , type, col1, col2, col3, col4, idx ) 
{
	var CtrlName = objName + "Ctrl";
	var FileListID = objName + "file_list";

	var frm = document.getElementById(CtrlName).contentWindow;
	var tbl = frm.document.getElementById(FileListID);
	var objRow = tbl.insertRow(idx);
	objRow.id = "row_"+idx;
	objRow.height = 20;
	objRow.bgColor = "#ffffff";
	objRow.selected = false;


	objRow.onclick = function()
	{
		if( this.selected )
		{
			this.selected = false;
			this.bgColor = "#ffffff";		
		}
		else
		{
			this.selected = true;
			this.bgColor = "#FDECBA";		
		}
		GetDSObject(ObjName).ItemClick( this.id.split("_")[1] );
	}

	if(type == 0 )
	{
		var objCell1 = objRow.insertCell(0);
		var objCell2 = objRow.insertCell(1);

		objCell1.width = (g_nWidth*g_view0_0);
		objCell1.align = "left";
		objCell1.innerHTML = "&nbsp;&nbsp;<font style=\"font-size:9pt;\">"+col1+"</font>";

		objCell2.width = (g_nWidth*g_view0_1);
		objCell2.align = "center";
		objCell2.innerHTML = "<font style=\"font-size:9pt;\">"+col2+"</font>";
	}

	else if(type == 1)
	{
		var objCell1 = objRow.insertCell(0);
		var objCell2 = objRow.insertCell(1);
		var objCell3 = objRow.insertCell(2);
		objCell1.width = (g_nWidth*0.52);
		objCell1.align = "left";
		objCell1.innerHTML = "&nbsp;&nbsp;<font style=\"font-size:9pt;\">"+col1+"</font>";
		
		objCell2.width = (g_nWidth*0.236);
		objCell2.align = "center";
		objCell2.innerHTML = "<font style=\"font-size:9pt;\">"+col2+"</font>";
		
		objCell3.width = (g_nWidth*0.20);
		objCell3.align = "center";
		objCell3.innerHTML = "<font style=\"font-size:9pt;\">"+col3+"</font>";
	}

	else if(type == "2")
	{
		var objCell1 = objRow.insertCell(0);
		var objCell2 = objRow.insertCell(1);
		var objCell3 = objRow.insertCell(2);
		var objCell4 = objRow.insertCell(3);

		objCell1.width = (g_nWidth*g_view2_0);
		objCell1.align = "left";
		objCell1.innerHTML = "&nbsp;&nbsp;<font style=\"font-size:9pt;\">"+col1+"</font>";
		
		objCell2.width = (g_nWidth*g_view2_1);
		objCell2.align = "center";
		objCell2.innerHTML = "<font style=\"font-size:9pt;\">"+col2+"</font>";

		objCell3.width = (g_nWidth*g_view2_2);
		objCell3.align = "center";
		objCell3.innerHTML = "<font style=\"font-size:9pt;\">"+col3+"</font>";
		
		objCell4.width = (g_nWidth*g_view2_3);
		objCell4.align = "center";
		objCell4.innerHTML = "<font style=\"font-size:9pt;\">"+col4+"</font>";
	}
}



function CreateHTMLCtrl( objName , nWidth, nHeight )
{
	var CtrlName = objName+"Ctrl";
	var ListHeaderID = objName+"ListHeader";
	var FileListID = objName+"file_list";
	var ListStatusBarID = objName+"ListStatusBar";

	document.writeln('<iframe id = \"'+CtrlName+'\" width = ' + nWidth + ' height = ' + nHeight + ' frameborder=0 cellpadding=0 cellspacing=0 marginwidth=0 marginheight=0 scrolling=\"no\"></iframe>')
	var tStr1 = "" +
	"<table border=0 cellpadding=0 cellspacing=0 style=\"width:" + nWidth + "; border-top:1px solid #CDCDCD; border-left:1px solid #CDCDCD; border-right:1px solid #CDCDCD; border-bottm:1px solid #CDCDCD; CURSOR:default;\"><tr><td>" +
	"<table  id=\""+ ListHeaderID +"\" border=0 height=25 cellpadding=0 cellspacing=0 bgcolor=#cccccc style=\"border-top:0px solid #CDCDCD; border-bottom:0px solid #CDCDCD; border-right:0px solid #CDCDCD; border-left:0px solid #CDCDCD;\">" +
	"</table>" +
	"<div style=\"height: " + (nHeight - 46) + "px; overflow-x: hidden; overflow-y: scroll; border-left: 0px solid #040204\">" +
	"<table border=0  cellpadding=0 cellspacing=0 bgcolor=#white id=\""+ FileListID +"\" style=\"border-top:0px solid #white; border-bottom:0px solid #CDCDCD; border-right:0px solid #CDCDCD; border-left:0px solid #CDCDCD;\">" +
	"</table>" +
	"</div>" +
	"<table id=\""+ListStatusBarID+"\" height = 21 border=0 cellpadding=0 cellspacing=0 bgcolor=#cccccc style=\"border-top:0px solid #CDCDCD; border-bottom:0px solid #CDCDCD; border-right:1px solid #CDCDCD; border-left:1px solid #CDCDCD;\">" +
	"</table>" +
	"</table>";
	
	document.close();
	document.getElementById(CtrlName).contentWindow.document.writeln( tStr1 );
	document.getElementById(CtrlName).contentWindow.document.close();
}

function InnoDSInit_HTML( MaxTotalSize, MaxFileSize, MaxFileCount , nWidth, nHeight , objName )
{
	//InnoAPInitMulti( MaxTotalSize, MaxFileSize, MaxFileCount, 1 ,1, objName );
	g_nWidth = nWidth;
	g_nHeight = nHeight;
	bUseHtmlCtrl = true;
	CreateHTMLCtrl( objName , nWidth, nHeight  );	
}

////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//  JSON ( DO NOT MODIFY )
//
////////////////////////////////////////////////////////////////////////////////////////////////////////


function reviver( value )
{
	var a;
	if (typeof value === 'string') 
	{
		a = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}(?:\.\d*)?)Z$/.exec(value);
		if (a) 
		{
                
			return new Date(Date.UTC(+a[1], +a[2] - 1, +a[3], +a[4], +a[5], +a[6]));
		}
	}
	return value;
}


if (!this.JSON) {
    this.JSON = {};
}

(function () {

    function f(n) {
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function (key) {

            return isFinite(this.valueOf()) ?
                   this.getUTCFullYear()   + '-' +
                 f(this.getUTCMonth() + 1) + '-' +
                 f(this.getUTCDate())      + 'T' +
                 f(this.getUTCHours())     + ':' +
                 f(this.getUTCMinutes())   + ':' +
                 f(this.getUTCSeconds())   + 'Z' : null;
        };

        String.prototype.toJSON =
        Number.prototype.toJSON =
        Boolean.prototype.toJSON = function (key) {
            return this.valueOf();
        };
    }

    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        gap,
        indent,
        meta = {  
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        },
        rep;


    function quote(string) {


        escapable.lastIndex = 0;
        return escapable.test(string) ?
            '"' + string.replace(escapable, function (a) {
                var c = meta[a];
                return typeof c === 'string' ? c :
                    '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            }) + '"' :
            '"' + string + '"';
    }


    function str(key, holder) {


        var i,          
            k,          
            v,          
            length,
            mind = gap,
            partial,
            value = holder[key];


        if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }


        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }


        switch (typeof value) {
        case 'string':
            return quote(value);

        case 'number':


            return isFinite(value) ? String(value) : 'null';

        case 'boolean':
        case 'null':


            return String(value);


        case 'object':


            if (!value) {
                return 'null';
            }


            gap += indent;
            partial = [];


            if (Object.prototype.toString.apply(value) === '[object Array]') {


                length = value.length;
                for (i = 0; i < length; i += 1) {
                    partial[i] = str(i, value) || 'null';
                }


                v = partial.length === 0 ? '[]' :
                    gap ? '[\n' + gap +
                            partial.join(',\n' + gap) + '\n' +
                                mind + ']' :
                          '[' + partial.join(',') + ']';
                gap = mind;
                return v;
            }


            if (rep && typeof rep === 'object') {
                length = rep.length;
                for (i = 0; i < length; i += 1) {
                    k = rep[i];
                    if (typeof k === 'string') {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            } else {


                for (k in value) {
                    if (Object.hasOwnProperty.call(value, k)) {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            }


            v = partial.length === 0 ? '{}' :
                gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' +
                        mind + '}' : '{' + partial.join(',') + '}';
            gap = mind;
            return v;
        }
    }


    if (typeof JSON.stringify !== 'function') {
        JSON.stringify = function (value, replacer, space) {


            var i;
            gap = '';
            indent = '';


            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }

            } else if (typeof space === 'string') {
                indent = space;
            }


            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                     typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }


            return str('', {'': value});
        };
    }



    if (typeof JSON.parse !== 'function') {
        JSON.parse = function (text, reviver) {


            var j;

            function walk(holder, key) {


                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }



            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }



            if (/^[\],:{}\s]*$/.
test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').
replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').
replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {


                j = eval('(' + text + ')');


                return typeof reviver === 'function' ?
                    walk({'': j}, '') : j;
            }


            throw new SyntaxError('JSON.parse');
        };
    }
}());


// --------------------------------------------------------------------------------------------------
// Detect OS & Install( DO NOT MODIFY )
// --------------------------------------------------------------------------------------------------

function innomp_pkg_update()
{
	if (browserName == "Explorer")
	{
		return false;
	}
	else
	{
		var innomp_new_version = innomp_get_ver("new");
		var innomp_old_version = innomp_get_ver("old");

		if (!innomp_old_version || innomp_new_version > innomp_old_version)
		{
			return true;
		}
		else if (innomp_new_version <= innomp_old_version)
		{
			return false;
		}
		else
		{
			return false;		
		}
	}
}

function innomp_get_ver(flag)
{
	if (flag == "old")
	{
		var old_ver_result = innomp_get_ver().split(".");

		var old_ver1 = parseInt(old_ver_result[0] + "00000000");
		var old_ver2 = parseInt(old_ver_result[1] + "000000");
		var old_ver3 = parseInt(old_ver_result[2] + "0000");
		var old_ver4 = parseInt(old_ver_result[3]);

		var innomp_old_version = old_ver1 + old_ver2 + old_ver3 + old_ver4;
		return innomp_old_version;
	}
	else if (flag == "new")
	{
		var new_ver_result = Innorix_Version.split(",");
		
		var new_ver1 = parseInt(new_ver_result[0] + "00000000");
		var new_ver2 = parseInt(new_ver_result[1] + "000000");
		var new_ver3 = parseInt(new_ver_result[2] + "0000");
		var new_ver4 = parseInt(new_ver_result[3]);
		
		var innomp_new_version = new_ver1 + new_ver2 + new_ver3 + new_ver4;
		return innomp_new_version;
	}
	else
	{
		navigator.plugins.refresh(false);
		var numPlugins = navigator.plugins.length;
	
		for (var i = 0; i < numPlugins; i++)
		{
			var plugin = navigator.plugins[i];
			var numTypes = plugin.length;
			var mimetype;
			
			for (var j = 0; j < numTypes; j++)
			{		
				mimetype = plugin[j];
				if (mimetype)
				{
					var innomp_mime = mimetype.type.split(";");
					if (innomp_mime[0] == "application/innorix-multi-platform")
					{
						if (innomp_mime[1])
						{
							var innomp_ver = innomp_mime[1];
							var innomp_ver = innomp_ver.split("=");
							return innomp_ver[1];
						}
					}
				}
			}
		}
		return "0.0.0.0";
	}
}

function innomp_install_guide()
{
	document.location.href = InnoInstall;
}

var update_timer;
function innomp_update_status()
{
	if (browserName == "Explorer")
	{
		clearTimeout(update_timer);
		update_timer = setTimeout('innomp_install_check()', 3000);
	}
	else
	{
		innomp_update_interval = setInterval("innomp_install_check()", 3000);	
	}
}

function innomp_install_check()
{
	if (browserName == "Explorer")
	{
		var objName = "innomp";
		var innomp_ready_state = 0;
		var innomp_obj_str = '<object id="'+objName+'" codebase="#version='+Innorix_Version+'" width="1" height="1" classid="clsid:8818A9CD-6A04-46E0-8F81-01CA85B47BC3" onerror="innomp_update_status();"></object>';
			document.getElementById('innomp_check_obj').innerHTML = innomp_obj_str;

		try
		{
			innomp_get_obj(objName).GetVersion();
			innomp_ready_state = 4;
		}
		catch(err)
		{
			innomp_ready_state = 0;
		}

		if (innomp_ready_state == 4)
		{
			document.location.href = InnoUrlBack;
		}
	}
	else
	{
		var innomp_new_version = innomp_get_ver("new");
		var innomp_old_version = innomp_get_ver("old");

		if (innomp_new_version <= innomp_old_version)
		{
			clearInterval(innomp_update_interval);
			document.location.href = InnoUrlBack;
		}
	}
}

function innomp_get_obj(obj) {
	if(navigator.appName.indexOf("Microsoft") != -1)
	{
		return window[obj]
	}
	else
	{
		return document[obj]
	}
}

// ------------------------------------------------------

var browserDetect = {
	init: function () {
		this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
		this.version = this.searchVersion(navigator.userAgent)
			|| this.searchVersion(navigator.appVersion)
			|| "an unknown version";
		this.OS = this.searchString(this.dataOS) || "an unknown OS";
		this.OSver = this.searchString(this.dataOSver) || "an unknown OS version";
	},
	searchString: function (data) {
		for (var i=0;i<data.length;i++)	{
			var dataString = data[i].string;
			var dataProp = data[i].prop;
			this.versionSearchString = data[i].versionSearch || data[i].identity;
			if (dataString) {
				if (dataString.indexOf(data[i].subString) != -1)
					return data[i].identity;
			}
			else if (dataProp)
				return data[i].identity;
		}
	},
	searchVersion: function (dataString) {
		var index = dataString.indexOf(this.versionSearchString);
		if (index == -1) return;
		return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
	},
	dataBrowser: [
		{
			string: navigator.userAgent,
			subString: "Chrome",
			identity: "Chrome"
		},
		{ 	string: navigator.userAgent,
			subString: "OmniWeb",
			versionSearch: "OmniWeb/",
			identity: "OmniWeb"
		},
		{
			string: navigator.vendor,
			subString: "Apple",
			identity: "Safari",
			versionSearch: "Version"
		},
		{
			prop: window.opera,
			identity: "Opera"
		},
		{
			string: navigator.vendor,
			subString: "iCab",
			identity: "iCab"
		},
		{
			string: navigator.vendor,
			subString: "KDE",
			identity: "Konqueror"
		},
		{
			string: navigator.userAgent,
			subString: "Firefox",
			identity: "Firefox"
		},
		{
			string: navigator.vendor,
			subString: "Camino",
			identity: "Camino"
		},
		{		// for newer Netscapes (6+)
			string: navigator.userAgent,
			subString: "Netscape",
			identity: "Netscape"
		},
		{
			string: navigator.userAgent,
			subString: "MSIE",
			identity: "Explorer",
			versionSearch: "MSIE"
		},
		{
			string: navigator.userAgent,
			subString: "Gecko",
			identity: "Mozilla",
			versionSearch: "rv"
		},
		{ 		// for older Netscapes (4-)
			string: navigator.userAgent,
			subString: "Mozilla",
			identity: "Netscape",
			versionSearch: "Mozilla"
		}
	],
	dataOS : [
		{
			string: navigator.platform,
			subString: "Win32",
			identity: "Win32"
		},
		{
			string: navigator.platform,
			subString: "MacIntel",
			identity: "MacIntel"
		},		
		{
			string: navigator.platform,
			subString: "MacPPC",
			identity: "MacPPC"
		},
		{
			   string: navigator.userAgent,
			   subString: "iPhone",
			   identity: "iPhone/iPod"
	    },
		{
			string: navigator.platform,
			subString: "Linux i686",
			identity: "Linux i686"
		}
	],
	dataOSver : [
		{
			string: navigator.userAgent,
			subString: "NT 5.0;",
			identity: "Win2000"
		},		
		{
			string: navigator.userAgent,
			subString: "NT 5.1;",
			identity: "WinXP"
		},
		{
			string: navigator.userAgent,
			subString: "NT 5.2;",
			identity: "Win2003"
		},				
		{
			string: navigator.userAgent,
			subString: "NT 6.0;",
			identity: "Win2008/Vista"
		},						
		{
			string: navigator.userAgent,
			subString: "NT 6.1;",
			identity: "Win7"
		},
		{
			string: navigator.userAgent,
			subString: "OS X 10.4",
			identity: "Mac Tiger"
		},		
		{
			string: navigator.userAgent,
			subString: "OS X 10_4",
			identity: "Mac Tiger"
		},
		{
			string: navigator.userAgent,
			subString: "OS X 10.5",
			identity: "Mac Leopard"
		},		
		{
			string: navigator.userAgent,
			subString: "OS X 10_5",
			identity: "Mac Leopard"
		},
		{
			string: navigator.userAgent,
			subString: "OS X 10.6",
			identity: "Mac Snow Leopard"
		},		
		{
			string: navigator.userAgent,
			subString: "OS X 10_6",
			identity: "Mac Snow Leopard"
		}
	]
 
};

browserDetect.init();

var browserName = browserDetect.browser;
var browserVer = browserDetect.version;
var browserOS = browserDetect.OS;
var browserOSver = browserDetect.OSver;
