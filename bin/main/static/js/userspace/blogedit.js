$(function(){
	
	//初始化 md 编辑器
	$("#md").markdown({
		language:"zh",
		fullscreen:{
			enable:true
		},
		resize:"vertical",
		localStorage:"md",
		imgurl:"http://localhost:8082",
		base64url:"http://localhost:8082"
	});
	
	//初始化标签控件
	$(".form-control-tag").tagEditor({
		initialTags:[],
		maxTags:5,
		delimiter:",",
		forceLowercase:false,
		animateDelete:0,
		placeholder:"请输入标签"
	});
	
	$(".form-control-chosen").chosen();
	
    // 初始化标签
    $('.form-control-tag').tagsInput({
    	'defaultText':'输入标签'
 
    });
	
	//图片上传
	$("#uploadImage").click(function(){
		$.ajax({
			url:"http://localhost:8082/upload",
			type:"POST",
			cache:false,
			data:new FormData($("#uploadformid")[0]),
			processData:false,
			contentType:false,
			success:function(data){
				var mdcontent=$("#md").val();
				$("#md").val(mdcontent + "\n![]("+data +") \n");
			}
		}).done(function(res){
			$("#file").val('');
		}).fail(function(res){});
	});
	
	//发布博客
	$("#submitBlog").click(function(){
		console.log($('#md').val());
		//获取CSRF TOKEN
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url:"/u/"+$(this).attr("userName")+"/blogs/edit",
			type:"POST",
			contentType:"application/json; charset=utf-8",
			data:JSON.stringify({
				"id":Number($('#id').val()),
				"title":$('#title').val(),
				"summary":$("#summary").val(),
				"content":$('#md').val(),
				"catalog":{"id":$('#catalogSelect').val()},
				"tags":$('.form-control-tag').val()
			}),
			beforeSend:function(request){
				request.setRequestHeader(csrfHeader,csrfToken);
			},
			success:function(data){
				if(data.success){
					//成功后,重定向
					window.location = data.body;
				}else{
					toastr.error("error!"+data.message);
				}
			},
			error:function(){
				toastr.error("error!");
			}
		});
	});
});