<#-- 날짜관련 공통 포맷 -->
<#macro dateFormt date><#if (date?length == 8)>${date[0..3]}/${date[4..5]}/${date[6..7]}<#else>Date length Error</#if></#macro>

<#macro dateTimeFormt date><#if (date?length == 14)>${date[0..3]}/${date[4..5]}/${date[6..7]} ${date[8..9]}:${date[10..11]}:${date[12..13]}<#else>Date length Error</#if></#macro>

<#-- CommonData를 보여주는 ComboBox -->
<#macro commCombo commonList selValue>
	<#list commonList as common>
	<option value='${common.devVal}' <#if common.devVal?number == selValue?number>selected</#if>>${common.cmmCdNm}</option>
	</#list>
</#macro>

<#-- 카테고리를 보여주는 ComboBox -->
<#macro category srvTypList selValue>
	<#list srvTypList as srvTyp>
	<option value='${srvTyp.typId}' <#if srvTyp.typId?number == selValue?number>selected</#if>>${srvTyp.typNm}</option>
	</#list>
</#macro>

<#-- 분류 보여주는 ComboBox -->
<#macro itemCla chkClas selValue>
	<#list chkClas as chkCla>
	<option value='${chkCla.claId}' <#if chkCla.claId?number == selValue?number>selected</#if>>${chkCla.claNm}</option>
	</#list>
</#macro>

<#-- 사용자권한명 보여주는 ComboBox -->
<#macro usrPerm usrPerms selValue>
	<#list usrPerms as usrprm>
	  <#if (usrprm.permId?number==9) != true>
		<option value='${usrprm.permId}' <#if usrprm.permId?number == selValue?number>selected</#if>>${usrprm.permNm}</option>
	  </#if>
	</#list>
</#macro>

<#-- ArrayString를 보여주는 ComboBox -->
<#macro arrayCombo arrayIdx arrayVal selValue>
	<#list arrayIdx as idx>
	<option value='${idx}' <#if idx?string == selValue?string>selected</#if>>${arrayVal[idx_index]}</option>
	</#list>
</#macro>

<#-- CommonData를 보여주는 RadioBox -->
<#macro commRadio optName commonList selValue>
	<#list commonList as common>
	<input type="radio" id="${optName}" name="${optName}" value="${common.devVal}" <#if common.devVal?number == selValue?number>checked</#if> />${common.cmmCdNm}
	</#list>
</#macro>

<#-- ArrayString를 보여주는 CheckBox -->
<#macro arrayRadio optName arrayIdx arrayVal selValue>
	<#list arrayIdx as idx>
	<input type="radio" id="${optName}" name="${optName}" value="${idx}" <#if idx?string == selValue?string>checked</#if> />${arrayVal[idx_index]}
	</#list>
</#macro>

<#-- CommonData를 보여주는 CheckBox -->
<#macro commCheck optName commonList selValue>
	<#list commonList as common>
		<input type="checkbox" id="${optName}_${common_index}" name="${optName}_${common_index}" value="${common.devVal}" <#if common.devVal?number == selValue?number>checked</#if> />${common.cmmCdNm}
		<#if common_has_next></br></#if>
	</#list>
</#macro>

<#-- ArrayString를 보여주는 CheckBox -->
<#macro arrayCheck optName arrayIdx arrayVal selValue>
	<#list arrayIdx as idx>
		<input type="checkbox" id="${optName}_${idx_index}" name="${optName}_${idx_index}" value="${idx}" <#if idx?string == selValue?string>checked</#if> />${arrayVal[idx_index]}
		<#if idx_has_next></br></#if>
	</#list>
</#macro>

<#-- CommonData를 보여주는 String ul-li -->
<#macro commUl commonList>
	<ul>
	<#list commonList as common>
		<li>${common.cmmCdNm}</li>
	</#list>
	</ul>
</#macro>

<#-- Pagination -->
<#macro paging2 pageList currPage prefix>
	<article class="paginate">
	<#if (currPage > pageList.pageIndexCount)><a href="javascript:void(0)" onClick="goPage('1')">처음</a></#if>
	<#if (pageList.previousIndex > 1)><a href="javascript:void(0)" onClick="goPage('${pageList.previousIndex}');">이전</a></#if>
	<#list pageList.indexs as index>
		<#if (currPage == index)><span>${index}</span><#elseif index?int==0>...<#else><a href="javascript:void(0)" onClick="goPage('${index}')">${index}</a></#if>
	</#list>
	<#if (pageList.nextIndex < pageList.lastIndex?number)><a href="javascript:void(0)" onClick="goPage('${pageList.nextIndex}')">다음</a></#if>
	<#if (currPage < pageList.lastIndex)><a href="javascript:void(0)" onClick="goPage('${pageList.lastIndex}')">마지막</a></#if>
	</article>
</#macro>

<#-- Pagination -->
<#macro paging pageList currPage prefix>
	<article class="paginate">
	<#if (currPage > 1)><a href="javascript:void(0)" onClick="goPage('1')"><span>‹‹</span>처음&nbsp;</a></#if>
	<#if (currPage > 1)><a href="javascript:void(0)" onClick="goPage('${pageList.previousIndex}');"><span>‹</span>&nbsp;이전&nbsp;&nbsp;</a></#if>
	<#list pageList.indexs as index>
		<#if (currPage == index)><strong>&nbsp;${index}&nbsp;</strong><#elseif index?int==0>...<#else><a href="javascript:void(0)" onClick="goPage('${index}')">&nbsp;${index}&nbsp;</a></#if>
	</#list>
	<#if (pageList.nextIndex < pageList.lastIndex?number)><a href="javascript:void(0)" onClick="goPage('${pageList.nextIndex}')">&nbsp;&nbsp;다음<span>›</span>&nbsp;</a></#if>
	<#if (currPage < pageList.lastIndex)><a href="javascript:void(0)" onClick="goPage('${pageList.lastIndex}')">&nbsp;끝<span>››</span></a></#if>
	</article>
</#macro>

<#-- Top User Menu -->
<#macro menu menuList prefix>
	
	<#assign sub = 'N'?string>
	<#assign num = 0?int>
	
	<#list menuList as menu>
		<#if (menu.depth == 1)>
			<#assign num = num+1>
			<#if (menu.depth == 1 && sub == 'Y')>
				</ul>
				<#assign sub = 'N'?string>
			</#if>
			<li id="top-menu${num}" class="none">
			<#--
				<#if num == 1>
					<a href="#" id="top-menu-head1">
						<img src="${prefix}/images/menu_pglog_off.gif" title="TV편성표" onMouseOver="this.src='${prefix}/images/menu_pglog_on.gif'" onMouseOut="this.src='${prefix}/images/menu_pglog_off.gif'" />
					</a>
				<#else>
					<a href="${prefix}${menu.url}" id="top-menu-head${num}">
						<img src="${prefix}/images/menu_${menu.menuEnNm}_off.gif" title="${menu.menuNm}" onMouseOver="this.src='${prefix}/images/menu_${menu.menuEnNm}_on.gif'" onMouseOut="this.src='${prefix}/images/menu_${menu.menuEnNm}_off.gif'" />
					</a>
				</#if>
			-->
				<a href="${prefix}${menu.url}" id="top-menu-head${num}">
					<img src="${prefix}/images/menu_${menu.menuEnNm}_off.gif" title="${menu.menuNm}" onMouseOver="this.src='${prefix}/images/menu_${menu.menuEnNm}_on.gif'" onMouseOut="this.src='${prefix}/images/menu_${menu.menuEnNm}_off.gif'" />
				</a>
				<ul class="sMenu" id="submenu${num}">
		<#else>
			<#if (menu.url?index_of("?") > -1)>
					<#assign i=menu.menuId>
					<#if i ="3">
					<li><a href="${prefix}${menu.url}&menuId=${menu.menuId}">${menu.menuNm}</a></li>
					<#else>
					<li style="background:url(../images/menu_bar_gray.gif) no-repeat 5px 3px;"><a href="${prefix}${menu.url}&menuId=${menu.menuId}">${menu.menuNm}</a></li>
					
					</#if>
			<#else>
					<#assign j=menu.menuId>
					<#if j ="6" >
					<li><a href="${prefix}${menu.url}?menuId=${menu.menuId}">${menu.menuNm}</a></li>
					<#elseif j="9">
					<li><a href="${prefix}${menu.url}?menuId=${menu.menuId}">${menu.menuNm}</a></li>
					<#elseif j="13">
					<li><a href="${prefix}${menu.url}?menuId=${menu.menuId}">${menu.menuNm}</a></li>
					<#elseif j="17">
					<li><a href="${prefix}${menu.url}?menuId=${menu.menuId}">${menu.menuNm}</a></li>
					<#elseif j="21">
					<li><a href="${prefix}${menu.url}?menuId=${menu.menuId}">${menu.menuNm}</a></li>
					<#else>
					<li style="background:url(../images/menu_bar_gray.gif) no-repeat 5px 3px;"><a href="${prefix}${menu.url}?menuId=${menu.menuId}">${menu.menuNm}</a></li>
					</#if>
			</#if>
			<#assign sub = 'Y'?string>
		</#if>
		<#if !menu_has_next>
			<#if (menu.depth == 2 && sub == 'Y')>
				</ul>
			</#if>
			</li>
		</#if>
	</#list>
</#macro>



<#-- User Button Permit -->
<#-- 메뉴ID, 사번, 버튼 Properties ID값을 넣어주면 권한에 따라 버튼을 표시한다. -->
<#macro button menuId userId btId url css>
	<#if menuId?exists && (menuId?length > 0)>
		<#assign perm = tpl.getAccessRule(userId, menuId)>
		
		<#if (tpl.getIndexOf('common', btId) > -1)>
			<#assign btn = btId>
		<#else>
			<#assign btn = menuId+'.'+btId>
		</#if>
		<#if (perm == 'RW')>
			<#if (tpl.getIndexOf('ssc', url) > -1)>
				<a href="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>
			<#else>
				<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>
			</#if>
		<#elseif (perm == 'RD')>
			<#if (tpl.getIndexOf( 'download',btId) > -1)>
				<#if (tpl.getIndexOf(url,'ssc') > -1)>
					<a href="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/>1</a>
				<#else>
					<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>
				</#if>
			</#if>
		<#elseif (perm == 'R')>
			<#if (tpl.getIndexOf(btId, 'find') > -1) || (tpl.getIndexOf(btId, 'view') > -1) || (tpl.getIndexOf(btId, 'search') > -1)>
				<#if (tpl.getIndexOf(url,'ssc') > -1)>
					<a href="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>
				<#else>
					<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>
				</#if>
			</#if>
		<#else>
			&nbsp;
		</#if>
	<#else>
		<#if (tpl.getIndexOf('ssc', url) > -1)>
			<a href="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btId/></a>
		<#else>
			<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btId/></a>
		</#if>
	</#if>
</#macro>

<#macro buttonSingle menuId userId btId url css>
	<#if menuId?exists && (menuId?length > 0)>
		<#assign perm = tpl.getAccessRule(userId, menuId)>
		
		<#if (tpl.getIndexOf('common', btId) > -1)>
			<#assign btn = btId>
		<#else>
			<#assign btn = menuId+'.'+btId>
		</#if>
		<#if (perm == 'RW')>
			<#if (tpl.getIndexOf('ssc', url) > -1)>
				'<a href="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>'
			<#else>
				'<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>'
			</#if>
		<#elseif (perm == 'RD')>
			<#if (tpl.getIndexOf('download',btId) > -1)> 
				<#if (tpl.getIndexOf('ssc', url) > -1)>
					'<a href="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>'
				<#else>
					'<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>'
				</#if>
			<#else>
			''
			</#if>
		<#elseif (perm == 'R')>
			<#if (tpl.getIndexOf(btId, 'find') > -1) || (tpl.getIndexOf(btId, 'view') > -1) || (tpl.getIndexOf(btId, 'search') > -1)>
				<#if (tpl.getIndexOf('ssc', url) > -1)>
					'<a href="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>'
				<#else>
					'<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btn/></a>'
				</#if>
			<#else>
			''
			</#if>
		<#else>
			' '
		</#if>
	<#else>
		<#if (tpl.getIndexOf('ssc', url) > -1)>
			'<a href="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btId/></a>'
		<#else>
			'<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btId/></a>'
		</#if>
	</#if>
	
	
</#macro>

<#macro imgbutton menuId userId btId url css src>
	<#if menuId?exists && (menuId?length > 0)>
		<#assign perm = tpl.getAccessRule(userId, menuId)>
		
		<#if (tpl.getIndexOf('common', btId) > -1)>
			<#assign btn = btId>
		<#else>
			<#assign btn = menuId+'.'+btId>
		</#if>
		<#if (perm == 'RW')>
			<#if (tpl.getIndexOf('ssc', url) > -1)>   
				<a href="${url}" <#if css != ''>class="${css}"</#if>><img src="<@spring.url '${src}'/>" title="<@spring.message btn/>"></a>
			<#else>
				<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><img src="<@spring.url '${src}'/>" title="<@spring.message btn/>"></a>
			</#if>
		<#elseif (perm == 'RD')>
			<#if (tpl.getIndexOf(btId, 'download') > -1)>
				<#if (tpl.getIndexOf('ssc', url) > -1)>
					<a href="${url}" <#if css != ''>class="${css}"</#if>><img src="<@spring.url '${src}'/>" title="<@spring.message btn/>"></a>
				<#else>
				
					<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><img src="<@spring.url '${src}'/>" title="<@spring.message btn/>"></a>
				</#if>
			</#if>
		<#elseif (perm == 'R')>
			<#if (tpl.getIndexOf(btId, 'find') > -1) || (tpl.getIndexOf(btId, 'view') > -1) || (tpl.getIndexOf(btId, 'search') > -1)>
				<#if (tpl.getIndexOf('ssc', url) > -1)>
					<a href="${url}" <#if css != ''>class="${css}"</#if>><img src="<@spring.url '${src}'/>" title="<@spring.message btn/>"></a>
				<#else>
					<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><img src="<@spring.url '${src}'/>" title="<@spring.message btn/>"></a>
				</#if>
			</#if>
		<#else>
			&nbsp;
		</#if>
	<#else>
		<#if (tpl.getIndexOf('ssc', url) > -1)>
			<a href="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btId/></a>
		<#else>
			<a href="javascript:void(0)" onClick="${url}" <#if css != ''>class="${css}"</#if>><@spring.message btId/></a>
		</#if>
	</#if>
</#macro>


<#macro flash chartID swfURL xmlData width height>
	<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="${width}" height="${height}" id="${chartID}" >
		<param name="allowScriptAccess" value="always" />
      	<param name="movie" value="${swfURL}" />
      	<param name="FlashVars" value="chartWidth=${width}&chartHeight=${height}&debugMode=0&dataXML=${xmlData}">
      	<param name="quality" value="high" />
      	<embed src="${swfURL}" flashVars="chartWidth=${width}&chartHeight=${height}&debugMode=0&dataXML==${xmlData}" quality="high" width="${width}" height="${height}" name="${chartID}" allowScriptAccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
   </object>
</#macro>


