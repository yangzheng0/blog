package com.blog.entity;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
public class Blog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "标题不能为空")
	@Size(min=2,max=50)
	@Column(nullable = false,length=50)//映射为字段，值不能为空
	private String title;
	
	@NotEmpty(message = "摘要不能为空")
	@Size(min=2,max=300)
	@Column(nullable = false)//映射为字段，值不能为空
	private String summary;
	
	@Lob // 大对象，映射 MySQL 的 Long Text 类型
	@Basic(fetch = FetchType.LAZY)	//懒加载
	@NotEmpty(message = "内容不能为空")
	@Size(min=2)
	@Column(nullable = false)
	private String content;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Size(min = 2)
	@Column(nullable = false)
	private String htmlContent; // 将 md 转为 html
	

}
