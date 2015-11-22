$(function(){
	$(".load_more").hide();
	showJingXuan();	
});

//显示精选内容
function showJingXuan(){
	try {
		$.ajaxSetup({
            error: function (x, e) {
            	//显示警告框
            	$('#load_error').show();
                return false;
            }
        });
		$.getJSON("api/list",{"newOrHotFlag":0,"offset":1,"count":30}, 
			function(data) {
		        if(data.code==200){	        	
		        	if(!data.data.empty){
		        		$("#jingxuan_content").html('');	        		
		        		var html = generateJingXuanHtml(data.data.list);
		        		$("#jingxuan_content").html(html);
		        		$(".load_more").show();
		        	}
		        }
	    });
	} catch (ex) {
		$('#load_error').show();
	}
}

//组装精选的html
function generateJingXuanHtml(list){
	var sb = new StringBuilder();
	var len = list.length;
	for(var i=0;i<len;i++){
		var portraitURL = list[i].portraitUrl;
		if(portraitURL == null) {
			portraitURL = 'http://7xixxm.com2.z0.glb.clouddn.com/cc44325c-701b-4e97-8b93-c9187c75218e.jpg';
		}
		if(list[i].type == '1') {
			sb.append('<div class="content_wrap">')
			
			.append('<div>')
			.append('<img class="portrait_image" src="').append(portraitURL).append('" />')
			.append('<div class="div_name_date">')
			.append('<span class="nickname" id="nickname">').append(list[i].userNike).append('</span>  <br>')
			.append('<span class="create_date" id="create_date">').append(getNowFormatDate(list[i].createDate)).append('</span>')
			.append('</div>')
			.append('</div>')

			.append('<div class="content">')
			.append('<div class="content_div">').append(list[i].content).append('</div>')
//			.append('<div class="content_div"><img id="content_img" src="').append(list[i].ImgUrl).append('" /></div>')
			.append('</div>')

			.append('<div class="row font_option">')
			.append('<div class="col-xs-3 col-sm-3 col-md-3" ><img src="resource/images/ic_ding_normal.png"/>')
			.append(list[i].supportsNum).append('</div>')
			.append('<div class="col-xs-3 col-sm-3 col-md-3"><img src="resource/images/ic_cai_normal.png"/>')
			.append(list[i].opposesNum).append('</div>')
			.append('<div class="col-xs-3 col-sm-3 col-md-3"><img src="resource/images/ic_comment_normal.png"/>')
			.append(list[i].commentNum).append('</div>')
			.append('<div class="col-xs-3 col-sm-3 col-md-3"><img src="resource/images/ic_forward_normal.png"/></div>')
			.append('</div>')
			
		    .append('</div>');
		} else {
			sb.append('<div class="content_wrap">')
			
			.append('<div style="display:block;">')
			.append('<img class="portrait_image" src="').append(portraitURL).append('" />')
			.append('<div class="div_name_date">')
			.append('<span class="nickname" id="nickname">').append(list[i].userNike).append('</span>  <br>')
			.append('<span class="create_date" id="create_date">').append(getNowFormatDate(list[i].createDate)).append('</span>')
			.append('</div>')
			.append('</div>')

			.append('<div class="content">')
			.append('<div class="content_div">').append(list[i].title).append('</div>')
			.append('<div class="content_div"><img id="content_img" src="').append(list[i].imgUrl).append('" /></div>')
			.append('</div>')

			.append('<div class="row font_option">')
			.append('<div class="col-xs-3 col-sm-3 col-md-3" ><img src="resource/images/ic_ding_normal.png"/>')
			.append(list[i].supportsNum).append('</div>')
			.append('<div class="col-xs-3 col-sm-3 col-md-3"><img src="resource/images/ic_cai_normal.png"/>')
			.append(list[i].opposesNum).append('</div>')
			.append('<div class="col-xs-3 col-sm-3 col-md-3"><img src="resource/images/ic_comment_normal.png"/>')
			.append(list[i].commentNum).append('</div>')
			.append('<div class="col-xs-3 col-sm-3 col-md-3"><img src="resource/images/ic_forward_normal.png"/></div>')
			.append('</div>')
			
		    .append('</div>');
		}
	}
	return sb.toString();
}

/**
 * 加载更多
 */
function loadMore() {
	$("#btn_loadmore").text('正在加载...');
	var pageNum = $('#pageNum').val();
	try {
		$.ajaxSetup({
            error: function (x, e) {
            	$('#btn_loadmore').text('加载更多');
                return false;
            }
        });
		$.getJSON("api/list",{"newOrHotFlag":0,"offset":pageNum,"count":30}, 
			function(data) {
		        if(data.code==200){	        	
		        	if(!data.data.empty){
		        		$("#pageNum").val(data.data.next);	        		
		        		var html = generateJingXuanHtml(data.data.list);
		        		$("#jingxuan_content").html($("#jingxuan_content").html() + html);
		        	}
		        }
		        $('#btn_loadmore').text('加载更多');
	    });
	} catch (ex) {
		$('#btn_loadmore').text('加载更多');
	}
}
