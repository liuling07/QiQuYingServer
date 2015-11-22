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
<title>奇趣营后台管理系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="resource/admin/css/css.css" rel="stylesheet" type="text/css" />
<link href="resource/admin/css/styles.css" type="text/css" rel="stylesheet">
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="147" background="resource/admin/images/top02.gif"><img src="resource/admin/images/top03.gif" width="776" height="147" /></td>
  </tr>
</table>
<table width="562" border="0" align="center" cellpadding="0" cellspacing="0" class="right-table03">
  <tr>
    <td width="221"><table width="95%" border="0" cellpadding="0" cellspacing="0" class="login-text01">
      
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="login-text01">
          <tr>
            <td align="center"><img src="resource/admin/images/ico13.gif" width="107" height="97" /></td>
          </tr>
          <tr>
            <td height="40" align="center">&nbsp;</td>
          </tr>
          
        </table></td>
        <td><img src="resource/admin/images/line01.gif" width="5" height="292" /></td>
      </tr>
    </table></td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="31%" height="35" class="login-text02">用户名称：<br /></td>
        <td width="69%"><input id="username" name="username" type="text" size="30" /></td>
      </tr>
      <tr>
        <td height="35" class="login-text02">密　码：<br /></td>
        <td><input id="password" name="password" type="password" size="33" /></td>
      </tr>
      <tr>
        <td height="35" class="login-text02">验证图片：<br /></td>
        <td><img id="safecode" src="<%=basePath%>code" onclick="change()"/> <a href="javascript:change();" class="A-top">看不清?换一张</a></td>
      </tr>
      <tr>
        <td height="35" class="login-text02">验证码：</td>
        <td><input id="yanzhengma" name="yanzhengma" type="text" size="30" /></td>
      </tr>
      <tr>
        <td height="35">&nbsp;</td>
        <td><input name="Submit2" type="button" class="right-button01" value="确认登陆" onclick="check()" />
          <input name="Submit232" type="button" class="right-button02" value="重 置" onclick="reset()" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>

<script type="text/javascript">
	//验证码换一张
	function change(){
		var img =document.getElementById("safecode"); 
		img.src=img.src+"?a="+Math.random();
	}
	
	//验证验证码
	var req1;
	function checkCode(){
		var yanzhengma = document.getElementById("yanzhengma"); //验证码
		var url1 = "<%=basePath%>checkcode?code=" + escape(yanzhengma.value)+"&a="+Math.random();
		if(yanzhengma.value == ''){
			alert("请填写验证码！");
		}else{
			if(window.XMLHttpRequest) {
				req1 = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				req1 = new ActiveXObject("Microsoft.XMLHTTP");
			}
			req1.open("GET", url1);
			req1.onreadystatechange = callbackRand;
			req1.send(null);
		}
	}
	function callbackRand(){
		if(req1.readyState == 4) {
			if(req1.status == 200) {
				var m = req1.responseText;
				var msg = new String(m.toString());
				var flagRand = msg.indexOf("200");
				if(flagRand != -1){
					login();
				}else{
					alert("验证码错误！");
					document.getElementById("yanzhengma").focus(); //验证码
					document.getElementById("yanzhengma").value="";
				}
			}
		}
	}
	//验证
	function check(){
		var userName = document.getElementById("username");
		var password = document.getElementById("password");
		var yanzhengma = document.getElementById("yanzhengma");
		if(userName.value == ""){
			alert("请填写用户名！");
			document.getElementById("username").focus();
		}else if(password.value == ""){
			alert("请填写密码！");
			document.getElementById("password").focus();
		}else if(yanzhengma.value==""){
			alert("请填写验证码！");
			document.getElementById("yanzhengma").focus();
		}else{
			checkCode();
		}
	}
	//登录
	function login(){
		var userName = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		//创建核心对象
		var request = null;
		try{
			request = new XMLHttpRequest();
		}catch(e){
			request = new ActiveXObject("Microsoft.XMLHttp");//ie6
		}
		//打开并发送
		url = "<%=basePath%>admin/2login?userName="+encodeURI(encodeURI(userName))+"&password="+encodeURI(encodeURI(password))+"&a="+Math.random();
		request.open("post", url);
		request.send(null);
		request.onreadystatechange=  function(){
			if (request.readyState==4){
				if(request.status==200) {
					var txt = request.responseText;
		            var msg = new String(txt.toString());
		            var flagRand = msg.indexOf("200");
					if(flagRand != -1){
						location = "<%=basePath%>admin/";
					} else {
						change();
		            	alert("用户名或密码错误！");
					}
				} else {
					alert("请求服务器失败！");
				}
         	}
		}
	}
	//重置
	function reset(){
		document.getElementById("username").value="";
		document.getElementById("password").value="";
		document.getElementById("yanzhengma").value="";
	}
</script>

</html>