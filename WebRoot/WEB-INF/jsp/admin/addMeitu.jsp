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
<script type="text/javascript" src="resource/js/jquery.js"></script>
<script type="text/javascript" src="resource/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	function ajaxFileUpload() {
	
		var filepath = $("#fileToUpload").val(); 
		
		if(filepath == "") {
			alert("请选择要上传的美图！"); 
        	return false;
		}
		
        var extStart = filepath.lastIndexOf("."); 
        var ext = filepath.substring(extStart,filepath.length).toUpperCase(); 
        if(ext!=".JPG" && ext!=".PNG" && ext!=".JPEG"){ 
        	alert("文件限于JPG/JPEG/PNG格式"); 
        	return false; 
        } 
	
		$("#loading").ajaxStart(function() {
			$(this).show();
		}).ajaxComplete(function() {
			//$(this).hide();
		});

		$.ajaxFileUpload({
			url : 'upload/meitu',
			secureuri : false,
			fileElementId : 'fileToUpload',
			dataType : 'json',
			success : function(data, status) {
				if(data.status == "200") {
					//alert("上传成功，请添加美图");
					$("#loading").attr("src", data.result);
					$("#imgUrl").val(data.result);
				} else {
					alert('上传失败，请重试');
					$("#loading").hide();
				}
			},
			error : function(data, status, e) {
				alert('上传出错');
				$("#loading").hide();
			}
		})
		return false;
	}
	
	function addQuTu() {
		var imgUrl = $("#imgUrl").val();
		if(imgUrl == "") {
			alert("请先上传要添加的美图！");
			return;
		}
		$("#addForm").submit();
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
	<div class="MainDiv">
		<table width="99%" border="0" cellpadding="0" cellspacing="0"
			class="CContent">
			<tr>
				<th class="tablestyle_title">添加美图</th>
			</tr>
			<tr>
				<td class="CPanel">
					<table border="0" cellpadding="0" cellspacing="0"
						style="width: 100%">
						<TR>
							<TD width="100%">
								<fieldset style="height: 100%;">
									<legend>添加美图</legend>
									<table border="0" cellpadding="2" cellspacing="1"
										style="width: 100%">
										<tr>
											<td nowrap align="right" width="13%">美图内容:</td>
											<td>
												<form name="form" action="" method="POST" enctype="multipart/form-data">
													<input id="fileToUpload" type="file" size="45" name="fileToUpload" class="input">
													<button class="button" onclick="return ajaxFileUpload();">上传</button>
												</form>
											</td>
										</tr>
										<tr>
											<td nowrap align="right" width="13%">上传预览:</td>
											<td>
												<img id="loading" src="resource/images/loading.gif" style="display: none;">
											</td>
										</tr>
										<form action="meituadmin/addmeitu" method="post" name="addForm" id="addForm">
											<tr>
												<td nowrap align="right" width="13%">美图标题:</td>
												<td><textarea id="title" name="title" rows="2"
														cols="50"></textarea></td>
											</tr>
											<tr>
												<td nowrap align="right" width="13%">是否精选:</td>
												<td>
													<label for="jingxuan">是</label><input type="radio" id="jingxuan" name="isJingXuan" value="1" />
    												&nbsp;&nbsp;&nbsp;&nbsp;
    												<label for="notJingxuan">否</label><input type="radio" id="notJingxuan" name="isJingXuan" value="0" checked />
												</td>
											</tr>
											<tr>
												<td nowrap align="right" width="13%">美图链接(可直接复制外链，免上传):</td>
												<td>
													<input type="text" style="width:500px;" name="imgUrl" id="imgUrl"/>
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
											<TR>
												<TD colspan="2" align="center" height="50px"><input
													type="button" value="添加" class="button"
													onclick="addQuTu()" /></TD>
											</TR>
										</form>
									</table>
									<br />
								</fieldset>
							</TD>
						</TR>
					</TABLE>
				</td>
			</tr>
		</TABLE>
		</td>
		</tr>
		</table>
	</div>
</body>
</html>

