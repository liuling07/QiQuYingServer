<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="resource/js/jquery.js"></script>
		<link rel="stylesheet" type="text/css" href="resource/css/style.css"/> 
		<title>奇趣营</title>
	</head>
	<script>
		
	</script>
	<body>
		<div class="tab">
			<label class="title">奇趣营</label>
		</div>

		<div class="content_wrap" style="margin-bottom:0px;">
			<div style="padding:12px;display:block;">
				<img class="portrait_image" src="${joke.portraitUrl}" />
				<span class="nickname" id="nickname">${joke.userNike }</span>  <br>
				<span class="create_date" id="create_date">
					<fmt:formatDate value="${joke.createDate}" pattern="yyyy-MM-dd HH:mm" />
				</span>
			</div>
			<div class="content">
				<c:if test="${joke.type == 1 }">   <!-- 趣事 -->
					<div style="margin:12px;margin-top:18px;">
						${joke.content }
					</div>
				</c:if>
				<c:if test="${joke.type != 1 }">   <!-- 图 -->
					<c:if test="${not empty joke.title}">
						<div style="margin:12px;margin-top:18px;">
							${joke.title }
						</div>
					</c:if>
					<div class="content_img_wrap" style="margin:12px;">
						<img id="content_img" src="${joke.imgUrl }" />
					</div>
				</c:if>
			</div>
		</div>
		<div style="margin-top:4px;padding-bottom:100px;">
			<a href="qushi/detail/last/${joke.id }" class="button main" style="margin:12px; float:left;">上一条</a>
			<a href="qushi/detail/next/${joke.id }" class="button main" style="margin:12px; float:right;">下一条</a>
		</div>
		<div class="content_wrap" style="margin-top:12px;">
			<c:forEach items="${comments }" var="comment">
				<div style="padding:12px;display:block;">
					<c:if test="${empty comment.portraitUrl}">
						<img class="portrait_image" src="resource/images/default_portrait.png" />
					</c:if>
					<c:if test="${not empty comment.portraitUrl}">
						<img class="portrait_image" src="${comment.portraitUrl }" />
					</c:if>
					<span class="nickname comment" id="nickname">${comment.userNike }</span>  <br>
					<span class="create_date" id="create_date">
						<fmt:formatDate value="${comment.createDate}" pattern="yyyy-MM-dd HH:mm" />
					</span>
				</div>
				<div class="content" style="margin-left:48px;">
					<div style="margin:12px;margin-top:18px;">
						${comment.content}
					</div>
				</div>
				<div style="margin:0;padding:0;width:100%;height:1px;background-color:#e0e0e0;overflow:hidden;margin-bottom:8px;margin-top:8px;"> </div>
			</c:forEach>
		</div>
		<div class="overlay">
			<table border="0" width="100%" height="100%">
  				<tr>
    				<td width="auto" align="center" valign="center">
    					<img src="http://7xjehg.com1.z0.glb.clouddn.com/logo.png" />
    				</td>
    				<td width="auto" align="left" valign="center">
						<div style="font-size:16px;width:100%;text-align:left;color:#212121">奇趣营<div>
						<div style="font-size:14px;;width:100%;text-align:left;color:#757575" >搞笑段子、图片，火辣美女图片集中营地<div>
    				</td>
    				<td width="35%" align="left" valign="center">
    					<a href="http://qiquying.com/QiQuYingServer/files/QiQuYing.apk" class="button main">下载APP</a>
    				</td>
  				</tr>
			</table>
		</div>
	</body>
</html>