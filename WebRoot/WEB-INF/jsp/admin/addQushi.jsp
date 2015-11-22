<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
<link rel="stylesheet" rev="stylesheet"
	href="resource/admin/css/style.css" type="text/css" media="all" />
<script language="JavaScript" type="text/javascript">
	function link() {
		var content = document.getElementById("content").value;
		if (content == "") {
			alert("请填趣事内容！");
			document.getElementById("content").focus();
		} else {
			document.getElementById("fom").submit();
		}
	}
</script>
<style type="text/css">
<!--
.atten {
	font-size: 12px;
	font-weight: normal;
	color: #F00;
}
-->
</style>
</head>

<body class="ContentBody">
	<form action="<%=basePath%>qushiadmin/addqushi" method="post" name="fom" id="fom">
		<div class="MainDiv">
			<table width="99%" border="0" cellpadding="0" cellspacing="0"
				class="CContent">
				<tr>
					<th class="tablestyle_title">添加趣事</th>
				</tr>
				<tr>
					<td class="CPanel">

						<table border="0" cellpadding="0" cellspacing="0"
							style="width: 100%">
							<TR>
								<TD width="100%">
									<fieldset style="height: 100%;">
										<legend>添加趣事</legend>
										<table border="0" cellpadding="2" cellspacing="1"
											style="width: 100%">
											<tr>
												<td nowrap align="right" width="13%">趣事内容:</td>
												<td><textarea id="content" name="content" rows="10"
														cols="100"></textarea></td>
											</tr>
											<tr>
												<td nowrap align="right" width="13%">是否精选:</td>
												<td>
													<label for="jingxuan">是</label><input type="radio" id="jingxuan" name="isJingXuan" value="1" checked/>
    												&nbsp;&nbsp;&nbsp;&nbsp;
    												<label for="notJingxuan">否</label><input type="radio" id="notJingxuan" name="isJingXuan" value="0" />
												</td>
											</tr>
											<tr>
												<td nowrap align="right" width="13%">支持数:</td>
												<td>
													<input type="text" style="width:100px;" name="dingNum" id="dingNum" value="0"
													 onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
												</td>
											</tr>
											<tr>
												<td align="right">选择用户:</td>
												<td><select id="userId" name="userId">
														<c:forEach items="${users}" var="user">
															<option value="${user.id }">${user.userNike }</option>
														</c:forEach>
												</select></td>
											</tr>
										</table>
										<br />
									</fieldset>
								</TD>
							</TR>

						</TABLE>


					</td>
				</tr>
				<TR>
					<TD colspan="2" align="center" height="50px"><input
						type="button" name="Submit" value="添加" class="button"
						onclick="link();" /></TD>
				</TR>
			</TABLE>

			</td>
			</tr>
			</table>
		</div>
	</form>
</body>
</html>

