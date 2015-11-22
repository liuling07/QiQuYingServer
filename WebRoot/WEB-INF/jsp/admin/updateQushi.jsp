<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
<link rel="stylesheet" rev="stylesheet" href="resource/admin/css/style.css" type="text/css" media="all" />
<script language="JavaScript" type="text/javascript">
function link(){
	var vippoint = document.getElementById("vippoint").value;
	var vipcarddays = document.getElementById("vipcarddays").value;
	if(vippoint == ""){
		alert("请填写会员积分！");
		document.getElementById("vippoint").focus();
	}else if(vipcarddays == ""){
		alert("请填写阅读卡时长！");
		document.getElementById("vipcarddays").focus();
	}else{
		document.getElementById("fom").submit();
	}
}



</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
-->
</style>
</head>

<body class="ContentBody">
  <form action="${pageContext.request.contextPath}/vip!updateVipAdmin.do" method="post" name="fom" id="fom" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" >会员信息修改页面</th>
  </tr>
  <tr>
    <td class="CPanel">
		
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>修改会员信息</legend>
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					 
					  <tr>
					    <td nowrap align="right" width="13%">会员帐号:</td>
					    <td width="41%">${requestScope.vip.vipaccount }
					    <input type="hidden" name="vipid" value="${requestScope.vip.vipid }"> </td>
					    <td align="right" width="19%">真实姓名:</td>
					    <td width="27%"> ${requestScope.vip.vipname }</td>
					    </tr>
					  <tr>
					    <td nowrap align="right">性别:</td>
					    <td> ${requestScope.vip.vipsex }</td>
					    <td align="right">身份证号码:</td>
					    <td> ${requestScope.vip.vipidcard }</td>
					  </tr>
					   <tr>
					    <td nowrap align="right">邮箱:</td>
					    <td> ${requestScope.vip.vipmail }</td>
					    <td align="right">出生日期:</td>
					    <td> <fmt:formatDate value="${requestScope.vip.vipbirthday }" pattern="yyyy年MM月dd日"/> </td>
					  </tr>
					  <tr>
					    <td nowrap align="right">注册日期:</td>
					    <td> <fmt:formatDate value="${requestScope.vip.vipregistdate }" pattern="yyyy年MM月dd日"/></td>
					    <td align="right">会员积分:</td>
					    <td><input name="vippoint" id="vippoint" class="text" value="${requestScope.vip.vippoint }" onkeypress="return check(event)" type="text" size="5"  onpaste="return false;"  ondragenter="return false" style="ime-mode:disabled"/></td>
					  </tr>
					  <tr>
					    <td nowrap align="right">阅读卡时长:</td>
					    <td><input name="vipcarddays" id="vipcarddays" onkeypress="return check(event)" class="text" value="${requestScope.vip.vipcarddays }" type="text" size="5"  onpaste="return false;"  ondragenter="return false" style="ime-mode:disabled"/></td>
                        <td align="right">状态:</td>
					    <td><select name="vipstate" id="vipstate">
                          <option value="可用">可用</option>
                          <option value="冻结">冻结</option>
                        </select></td>
					  </tr>
					  </table>
			 <br />
				</fieldset>			</TD>
		</TR>
		
		</TABLE>
	
	
	 </td>
  </tr>
		<TR>
			<TD colspan="2" align="center" height="50px">
			<input type="button" name="Submit" value="保存" class="button" onclick="link();"/>　
			
			<input type="button" name="Submit2" value="返回" class="button" onclick="window.history.go(-1);"/></TD>
		</TR>
		</TABLE>
	
	
	 </td>
  </tr>
  </table>
</div>
</form>
</body>
<script type="text/javascript">
	//只能是数字
	function check(event){
		var e = event || window.event;
		var code = e.charCode || e.keyCode;
		if(code >= 48 && code <= 57  || code == 8||code==46){
			return true;
		}
		return false;
	}
	onload = function(){
		document.getElementById("vipstate").value = "${requestScope.vip.vipstate }";
	}
</script>
</html>

