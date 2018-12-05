$(function(){
	$.catalog("#catalog",".post-content");
	
	//处理删除博客事件
	$(".blog-content-container").on('click',".blog-delete-blog",function(){
		//获取CSRF TOKEN
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url:$(this).attr("blogUrl"),
			type:'DELETE',
			beforeSend:function(request){
				request.setRequestHeader(csrfHeader,csrfToken);
			},
			success:function(data){
				console.log(data);
				if(data.success){
					//成功后,重定向
					window.location = data.body;
				}else{
					toastr.error("error!");
				}
			},
			error:function(){
				toastr.error(data.message);
			}
		});
	});
})