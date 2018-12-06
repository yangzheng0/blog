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
	
	// 提交评论
	$(".blog-content-container").on("click","#submitComment", function () { 
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/comments', 
			 type: 'POST', 
			 data:{"blogId":blogId, "commentContent":$('#commentContent').val()},
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },
			 success: function(data){
				 console.log("data="+data);
				 if (data.success) {
					 // 清空评论框
					 $('#commentContent').val('');
					 // 获取评论列表
					 getComment(blogId);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	
	//获取评论列表
	function getComment(blogId){
		//获取CSRF Token
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url:"/comments",
			type:'GET',
			data:{"blogId":blogId},
			beforeSend:function(request){
				request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
			},
			success:function(data){
				$("#mainContainer").html(data);
			},
			error:function(){
				toastr.error("error!");
			}
		});
	};
	
	//删除评论
	$(".blog-content-container").on("click",".blog-delete-comment",function(){
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url:'/comments/'+$(this).attr("commentId")+"?blogId="+blogId,
			type:'DELETE',
			beforeSend:function(request){
				request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
			},
			success: function(data){
				 if (data.success) {
					 // 获取评论列表
					 getComment(blogId);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		});
	});
	
	// 提交点赞
	$(".blog-content-container").on("click","#submitVote", function () { 
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/votes', 
			 type: 'POST', 
			 data:{"blogId":blogId},
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },
			 success: function(data){
				 if (data.success) {
					 toastr.info(data.message);
						// 成功后，重定向
					 window.location = blogUrl;
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	// 提交点赞
	$(".blog-content-container").on("click","#cancelVote", function () { 
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/votes/'+$(this).attr('voteId')+'?blogId='+blogId, 
			 type: 'DELETE', 
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },
			 success: function(data){
				 if (data.success) {
					 toastr.info(data.message);
					// 成功后，重定向
					 window.location = blogUrl;
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	
	//初始化 博客评论
	getComment(blogId);
})