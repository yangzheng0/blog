$(function() {
	// 获取分类列表
	function getCatalogs(username) {
		// 获取 CSRF Token 
		
		$.ajax({ 
			 url: '/catalogs', 
			 type: 'GET', 
			 data:{"username":username},
			 success: function(data){
				$("#catalogMain").html(data);
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	}
	
	// 获取编辑分类的页面
	$(".blog-content-container").on("click",".blog-add-catalog", function () { 
		console.log("编辑");
		$.ajax({ 
			 url: '/catalogs/edit', 
			 type: 'GET', 
			 success: function(data){
				 console.log("data="+data);
				 $("#catalogFormContainer").html(data);
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	// 提交分类
	$("#submitEditCatalog").click(function() {
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/catalogs', 
			 type: 'POST', 
			 contentType: "application/json; charset=utf-8",
			 data:JSON.stringify({"username":username, "catalog":{"id":$('#catalogId').val(), "name":$('#catalogName').val()}}),
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },
			 success: function(data){
				 if (data.success) {
					 toastr.info(data.message);
					 // 成功后，刷新列表
					 getCatalogs(username);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	//获取编辑某个分类的页面
	$(".blog-content-container").on("click",".blog-edit-catalog",function(){
		$.ajax({
			url:"/catalogs/edit/"+$(this).attr("catalogId"),
			type:"GET",
			success:function(data){
				$("#catalogFormContainer").html(data);
			},
			error:function(){
				toastr.error("error!");
			}
		});
	});
	
	// 删除分类
	$(".blog-content-container").on("click",".blog-delete-catalog", function () { 
		//获取CSRF TOKEN
		console.log("删除");
		
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url: '/catalogs/'+$(this).attr('catalogid')+'?username='+username, 
			 type: 'DELETE', 
			 beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
            },
			 success: function(data){
				 if (data.success) {
					 toastr.info(data.message);
					 // 成功后，刷新列表
					 getCatalogs(username);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		});
	});
	
	getCatalogs(username);
})