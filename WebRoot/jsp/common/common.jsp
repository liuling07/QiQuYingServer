<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<link rel="stylesheet" type="text/css" href="resource/css/style.css"/>
<script src="resource/js/jquery.js"></script>
<script src="resource/js/common.js"></script>
<link rel="shortcut icon" href="http://s0.pstatp.com/resource/neihan_web/static/image/favicon.ico">

<!-- Bootstrap -->
<link rel="stylesheet" href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/js/bootstrap.min.js"></script>

<script type="text/javascript">

<!--
function StringBuilder() {  
   this.data=Array("");  
}  
StringBuilder.prototype.append=function() {  
   this.data.push(arguments[0]);  
   return this;  
}  
StringBuilder.prototype.toString=function() {  
   return this.data.join("");  
}

//时间格式化
function getNowFormatDate(data) { 
    var day = new Date(data);
    var Year = 0; 
    var Month = 0; 
    var Day = 0; 
    var CurrentDate = ""; 
    //初始化时间 
    //Year= day.getYear();//有火狐下2008年显示108的bug 
    Year= day.getFullYear();//ie火狐下都可以 
    Month= day.getMonth()+1; 
    Day = day.getDate(); 
    //Hour = day.getHours(); 
    // Minute = day.getMinutes(); 
    // Second = day.getSeconds(); 
    CurrentDate += Year + "-"; 
    if (Month >= 10 ) 
    { 
    CurrentDate += Month + "-"; 
    } 
    else 
    { 
    CurrentDate += "0" + Month + "-"; 
    } 
    if (Day >= 10 ) 
    { 
    CurrentDate += Day ; 
    } 
    else 
    { 
    CurrentDate += "0" + Day ; 
    } 
    return CurrentDate; 
}
//-->
</script>
