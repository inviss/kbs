		<#include "/WEB-INF/views/includes/commonTags.ftl">
		<script Language="JavaScript">
		<!--
			function submit(){  
				var form = document.leftForm;
				form.submit();
			}
			function clear(){  
				var form = document.leftForm;
				form.startDt.value='';
				form.endDt.value='';
				form.hrNm.value='';
				form.jkNm.value='';
			}
		//-->
		</script>
	    <div class="aside">
			<h3 class="blind">고정 검색폼</h3>
	        <form method="post" name="leftForm" action="<@spring.url '/media/contentList.ssc'/>">
	        	<@spring.bind "search" />
				<fieldset>
					<div class="bbsview">
	            		<dl>
							<dt>경기일 검색</dt>
							<dd>
								<input type="text" id="startDt" name="startDt" style="width:64px;" value="<#if search.startDt?exists>${search.startDt?string("yyyy.MM.dd")}</#if>" readonly/>&nbsp;<img src="<@spring.url '/img/calendar.gif'/>" align="absmiddle" onclick="callCalPop('document.leftForm.startDt', 'yyyy.mm.dd');" style="cursor:pointer"/>~
								<input type="text" id="endDt" name="endDt" style="width:64px;" value="<#if search.endDt?exists>${search.endDt?string("yyyy.MM.dd")}</#if>" readonly/>&nbsp;<img src="<@spring.url '/img/calendar.gif'/>" align="absmiddle" onclick="callCalPop('document.leftForm.endDt', 'yyyy.mm.dd');" style="cursor:pointer"/>
							</dd>
						</dl>
						<dl>
							<dt>마명 검색</dt>
							<dd>
								<input type="text" name="hrNm" style="width:180px;" value="<#if search.hrNm?exists>${search.hrNm?replace("%", "")}</#if>"/>
							</dd>
						</dl>
			            <dl>
				            <dt>기수명 검색</dt>
				            <dd>
				            	<input type="text" name="jkNm" style="width:180px;" value="<#if search.jkNm?exists>${search.jkNm?replace("%", "")}</#if>"/>
				            </dd>
			            </dl>
					</div>
			        <div class="btn_st">
			            <div class="bulbtn">
			                <a href="javascript:void(0)" onClick="submit()"><span>검색</span></a>
			                <a href="javascript:void(0)" onClick="clear()"><span>초기화</span></a>
			            </div>
			        </div>
	        	</fieldset>
	        </form>	
	    </div>