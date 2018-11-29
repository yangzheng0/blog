package com.blog.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "姓名不能为空")
	@Size(min=2,max=20)
	@Column(nullable=false,length=20)
	private String name;
	
	@NotEmpty(message="邮箱不能为空")
	@Size(max=50)
	@Email(message="邮箱格式不对")
	@Column(nullable=false,length=50,unique=true)
	private String email;
	
	@NotEmpty(message="账号不能为空")
	@Size(min=3,max=20)
	@Column(nullable=false,length=20,unique=true)
	private String username;
	
	@NotEmpty(message="账号不能为空")
	@Size(max=100)
	@Column(length=100)
	private String password;
	
	@Column(length=200)
	private String avatar; //头像图片地址
	
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
		List<SimpleGrantedAuthority> simpleAuthoritys = new ArrayList<>();
		for(GrantedAuthority authority : simpleAuthoritys){
			
		}
		return simpleAuthoritys;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public User(Long id, String name, String email, String username, String password, String avatar) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.avatar = avatar;
	}

	protected User() {
		// JPA 的规范要求无参构造函数；设为 protected 防止直接使用
	}
	
	

}
