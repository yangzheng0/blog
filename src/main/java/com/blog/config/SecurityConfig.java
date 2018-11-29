package com.blog.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 * 安全配置类
 * <p>Title: Authority</p>  
 * <p>Description: </p>  
 * @author Adam 
 * @date 2018年11月29日
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String KEY = "JC";
	

	

}
