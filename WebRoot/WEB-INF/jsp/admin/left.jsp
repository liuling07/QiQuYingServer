<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>后台管理系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(resource/admin/images/left.gif);
}
-->
</style>
<link href="resource/admin/css/css.css" rel="stylesheet" type="text/css" />
</head>
<SCRIPT language=JavaScript>
	function tupian(idt) {
		var nametu = "xiaotu" + idt;
		var tp = document.getElementById(nametu);
		tp.src = "images/ico05.gif";//图片ico04为白色的正方形

		for ( var i = 1; i < 30; i++) {

			var nametu2 = "xiaotu" + i;
			if (i != idt * 1) {
				var tp2 = document.getElementById('xiaotu' + i);
				if (tp2 != undefined) {
					tp2.src = "resource/admin/images/ico06.gif";
				}//图片ico06为蓝色的正方形
			}
		}
	}

	function list(idstr) {
		var name1 = "subtree" + idstr;
		var name2 = "img" + idstr;
		var objectobj = document.all(name1);
		var imgobj = document.all(name2);

		//alert(imgobj);

		if (objectobj.style.display == "none") {
			for (i = 1; i < 10; i++) {
				var name3 = "img" + i;
				var name = "subtree" + i;
				var o = document.all(name);
				if (o != undefined) {
					o.style.display = "none";
					var image = document.all(name3);
					image.src = "resource/admin/images/ico04.gif";
				}
			}
			objectobj.style.display = "";
			imgobj.src = "resource/admin/images/ico03.gif";
		} else {
			objectobj.style.display = "none";
			imgobj.src = "resource/admin/images/ico04.gif";
		}
	}
</SCRIPT>

<body>
	<table width="198" border="0" cellpadding="0" cellspacing="0"
		class="left-table01">
		<tr>
			<TD>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="207" height="55"
							background="resource/admin/images/nav01.gif">
							<table width="90%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="25%" rowspan="2"><img
										src="resource/admin/images/ico02.gif" width="35" height="35" /></td>
									<td width="75%" height="22" class="left-font01">您好，<span
										class="left-font02">${sessionScope.curr_user.userName }</span></td>
								</tr>
								<tr>
									<td height="22" class="left-font01">[&nbsp;<a
										href="<%=basePath%>admin/loginout" target="_top"
										class="left-font01">退出</a>&nbsp;]
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table> 
				
				<!--  趣事管理开始    -->
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0"
					class="left-table03">
					<tr>
						<td height="29">
							<table width="85%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="8%"><img name="img7" id="img7"
										src="resource/admin/images/ico04.gif" width="8" height="11" /></td>
									<td width="92%"><a href="javascript:" target="mainFrame"
										class="left-font03" onClick="list('7');">趣事管理</a></td>
								</tr>
							</table>
						</td>
					</tr>
				</TABLE>
				<table id="subtree7" style="DISPLAY: none" width="80%" border="0"
					align="center" cellpadding="0" cellspacing="0" class="left-table02">
					<tr>
						<td width="9%" height="20"><img id="xiaotu17"
							src="resource/admin/images/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a href="<%=basePath%>qushiadmin/list" target="mainFrame"
							class="left-font03" onClick="tupian('17');">趣事列表</a></td>
					</tr>
					<tr>
						<td width="9%" height="20"><img id="xiaotu18"
							src="resource/admin/images/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a href="<%=basePath%>qushiadmin/add/index"
							target="mainFrame" class="left-font03" onClick="tupian('18');">添加趣事</a></td>
					</tr>
				</table> 
				<!--  趣事管理结束    --> 
				
				<!--  趣图管理开始    -->
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0"
					class="left-table03">
					<tr>
						<td height="29">
							<table width="85%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="8%"><img name="img1" id="img1"
										src="resource/admin/images/ico04.gif" width="8" height="11" /></td>
									<td width="92%"><a href="javascript:" target="mainFrame"
										class="left-font03" onClick="list('1');">趣图管理</a></td>
								</tr>
							</table>
						</td>
					</tr>
				</TABLE>
				<table id="subtree1" style="DISPLAY: none" width="80%" border="0"
					align="center" cellpadding="0" cellspacing="0" class="left-table02">
					<tr>
						<td width="9%" height="20"><img id="xiaotu1"
							src="resource/admin/images/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a href="listxiangmuxinxi.htm"
							target="mainFrame" class="left-font03" onClick="tupian('1');">趣图列表</a></td>
					</tr>
					<tr>
						<td width="9%" height="20"><img id="xiaotu4"
							src="resource/admin/images/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a href="<%=basePath%>qutuadmin/add/index" target="mainFrame"
							class="left-font03" onClick="tupian('4');">添加趣图</a></td>
					</tr>
				</table> 
				<!-- 趣图管理结束    --> 
				
				<!--  美图管理开始    -->
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="left-table03">
					<tr>
						<td height="29"><table width="85%" border="0" align="center"
								cellpadding="0" cellspacing="0">
								<tr>
									<td width="8%" height="12"><img name="img2" id="img2"
										src="resource/admin/images/ico04.gif" width="8" height="11" /></td>
									<td width="92%"><a href="javascript:" target="mainFrame"
										class="left-font03" onClick="list('2');">美图管理</a></td>
								</tr>
							</table></td>
					</tr>
				</table>

				<table id="subtree2" style="DISPLAY: none" width="80%" border="0"
					align="center" cellpadding="0" cellspacing="0" class="left-table02">

					<tr>
						<td width="9%" height="20"><img id="xiaotu7"
							src="resource/admin/images/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a href="listkehuxinxi.html"
							target="mainFrame" class="left-font03" onClick="tupian('7');">美图列表</a></td>
					</tr>
					<tr>
						<td width="9%" height="20"><img id="xiaotu2"
							src="resource/admin/images/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a href="<%=basePath%>meituadmin/add/index"
							target="mainFrame" class="left-font03" onClick="tupian('2');">添加美图</a></td>
					</tr>
				</table> 
				<!--  美图管理结束    -->
				
				<!--  用户管理开始    -->
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0"
					class="left-table03">
					<tr>
						<td height="29">
							<table width="85%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="8%"><img name="img8" id="img8"
										src="resource/admin/images/ico04.gif" width="8" height="11" /></td>
									<td width="92%"><a href="javascript:" target="mainFrame"
										class="left-font03" onClick="list('8');">用户管理</a></td>
								</tr>
							</table>
						</td>
					</tr>
				</TABLE>
				
				<table id="subtree8" style="DISPLAY: none" width="80%" border="0"
					align="center" cellpadding="0" cellspacing="0" class="left-table02">
					<tr>
						<td width="9%" height="20"><img id="xiaotu20"
							src="resource/admin/images/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a href="vip!getAllVips.do"
							target="mainFrame" class="left-font03" onClick="tupian('20');">用户列表</a></td>
					</tr>
					<tr>
						<td width="9%" height="21"><img id="xiaotu21"
							src="resource/admin/images/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a href="listrenwu.htm" target="mainFrame"
							class="left-font03" onClick="tupian('21');">任务信息查看</a></td>
					</tr>
				</table> 
				<!--  用户管理结束    --> 

			</TD>
		</tr>
	</table>
</body>
</html>
