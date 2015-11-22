<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">奇趣营</a>
    </div>
    <div class="collapse navbar-collapse">
      <ul class="nav navbar-nav">
        <li <c:if test="${type == null }">class="active"</c:if>><a href="<%=basePath%>jsp/index.jsp">精选</a></li>
        <li <c:if test="${type == 'qushi' }">class="active"</c:if>><a href="<%=basePath%>qushi/index">趣事</a></li>
        <li><a href="#contact">趣图</a></li>
        <li><a href="#contact">美图</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>
