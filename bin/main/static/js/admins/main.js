$(function(){
	//菜单事件
	$(".blog-menu .list-group-item").click(function(){
		var url = $(this).attr("url");
		console.log(url);
		
		//先移除其他的点击样式，再添加当前的点击样式
		$(".blog-menu .list-group-item").removeClass("active");
		$(this).addClass("active");
		
		
		//加载其他模块到右侧工作区
		$.ajax({
			url:url,
			success:function(data){
				$("#rightContainer").html(data);
			},
			error:function(){
				alert("error")
			}
		});
	});
	
	//选中菜单第一栏
	$(".blog-menu .list-group-item:first").trigger("click");
})