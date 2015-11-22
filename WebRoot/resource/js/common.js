$(function(){
	gotoTop();	
});
function gotoTop(min_height){
    //为窗口的scroll事件绑定处理函数
    $(window).scroll(function(){
        //获取窗口的滚动条的垂直位置
        var s = $(window).scrollTop();
        //当窗口的滚动条的垂直位置大于页面的最小高度时，让返回顶部元素渐现，否则渐隐
        if( s > 1000){
            $("#totop").fadeIn(100);
        }else{
            $("#totop").fadeOut(200);
        };
    });
};
function toTop() {
	$('html,body').animate({scrollTop:0},300);
}