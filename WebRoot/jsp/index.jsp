<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html>
<html>
  <head>
    <title>奇趣营-首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="common/common.jsp" />
    <script src="resource/js/index.js"></script>
    
    <style type="text/css">
        body{
            padding-top: 70px;
            background-color: #f5f5f5;
        }
    </style>
  </head>
  <body id="body">
    <jsp:include page="common/top.jsp" />

    <div class="container">

      <div class="row">
        <div class="col-md-8">
          <div id="jingxuan_content">
            <%-- <div class="content_wrap">
              <div>
                <img class="portrait_image" src="http://7xixxm.com2.z0.glb.clouddn.com/95f28ea5-7e41-4461-ab7b-46488119bc51.jpg" />
                <div class="div_name_date">
                <span class="nickname" id="nickname">名字太难取</span><br>
                <span class="create_date" id="create_date">2015-7-16 20:01</span>
                </div>
              </div>
              <div class="content">
                <div class="content_div">最近缺钱，不想露面。 超过1块钱的活动不要叫我。 不要问我为什么，没钱，倔强！最近缺钱，不想露面。 超过1块钱的活动不要叫我。 不要问我为什么，没钱，倔强！</div>
                <!-- <div class="content_div">
                  <img id="content_img" src="http://7xixxm.com2.z0.glb.clouddn.com/c98f89e8-4106-4d35-a135-476a6a5676f1.jpg" />
                </div> -->
              </div><!--/.content -->
              <div class="row font_option">
                <div class="col-xs-3 col-sm-3 col-md-3" >
                  <img src="<%=basePath %>/resource/images/ic_ding_normal.png"/>103
                </div>
                <div class="col-xs-3 col-sm-3 col-md-3">
                  <img src="<%=basePath %>/resource/images/ic_cai_normal.png"/>103
                </div>
                <div class="col-xs-3 col-sm-3 col-md-3">
                  <img src="<%=basePath %>/resource/images/ic_comment_normal.png"/>103
                </div>
                <div class="col-xs-3 col-sm-3 col-md-3">
                  <img src="<%=basePath %>/resource/images/ic_forward_normal.png"/>
                </div>
              </div><!--/.content -->
            </div><!--/.content_wrap --> --%>
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
          <jsp:include page="common/left.jsp" />
        </div>
      </div> <!-- /.row -->
	  <!-- footer布局 -->
      <jsp:include page="common/footer.jsp" />
		
    </div><!-- /.container -->
	<img id="totop" onclick="toTop()" src="resource/images/totop.gif" />
  </body>
</html>
