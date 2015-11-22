<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html>
<html>
  <head>
    <title>奇趣营-趣事</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="/jsp/common/common.jsp" />
    <script src="<%=basePath%>resource/js/qushi.js"></script>
    
    <style type="text/css">
        body{
            padding-top: 70px;
            background-color: #f5f5f5;
        }
    </style>
  </head>
  <body id="body">
    <jsp:include page="/jsp/common/top.jsp" />

    <div class="container">

      <div class="row">
        <div class="col-md-8">
          <div id="jingxuan_content">
          	<c:forEach items="${page.list }" var="joke">
          	  <div class="content_wrap">
	              <div>
	                <img class="portrait_image" src="${joke.portraitUrl == null?'http://7xixxm.com2.z0.glb.clouddn.com/cc44325c-701b-4e97-8b93-c9187c75218e.jpg':joke.portraitUrl}" />
	                <div class="div_name_date">
		                <span class="nickname" id="nickname">${joke.userNike }</span><br>
		                <span class="create_date" id="create_date">
							<fmt:formatDate value="${joke.createDate}" pattern="yyyy-MM-dd HH:mm" />
						</span>
	                </div>
	              </div>
	              <div class="content">
	                <div class="content_div">${joke.content }</div>
	              </div><!--/.content -->
	              <div class="row font_option">
	                <div class="col-xs-3 col-sm-3 col-md-3" >
	                  <img src="<%=basePath %>/resource/images/ic_ding_normal.png"/>${joke.supportsNum }
	                </div>
	                <div class="col-xs-3 col-sm-3 col-md-3">
	                  <img src="<%=basePath %>/resource/images/ic_cai_normal.png"/>${joke.opposesNum }
	                </div>
	                <div class="col-xs-3 col-sm-3 col-md-3">
	                  <img src="<%=basePath %>/resource/images/ic_comment_normal.png"/>${joke.commentNum }
	                </div>
	                <div class="col-xs-3 col-sm-3 col-md-3">
	                  <img src="<%=basePath %>/resource/images/ic_forward_normal.png"/>
	                </div>
	              </div><!--/.content -->
	            </div><!--/.content_wrap -->
          	</c:forEach>
          </div><!--/.jingxuan_content -->
          <div class="load_more">
            <a id="btn_loadmore" href="javascript:loadMore()">加载更多</a>
            <input id="pageNum" type="hidden" value="2"/>
          </div>
          <div id="load_error" style="display:none;" class="alert alert-warning">
			   <a href="#" class="close" data-dismiss="alert">
			      &times;
			   </a>
			   加载失败 <a href="javascript:history.go(0)">点击刷新</a>
		  </div>
        </div>
        <!-- 右侧介绍及下载栏 -->
        <div class="col-md-4">
          <jsp:include page="/jsp/common/left.jsp" />
        </div>
      </div> <!-- /.row -->
	  <!-- footer布局 -->
      <jsp:include page="/jsp/common/footer.jsp" />
		
    </div><!-- /.container -->
	<img id="totop" onclick="toTop()" src="resource/images/totop.gif" />
  </body>
</html>
