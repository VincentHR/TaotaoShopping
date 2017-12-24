package com.taobao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.common.utils.CookieUtils;
import com.taobao.pojo.TbUser;
import com.taobao.portal.service.UserService;
import com.taobao.portal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//在Handler执行之前处理
		//判断用户是否登录
		//1、从cookie中取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//2、根据token换取用户信息，调用SSO系统的接口。
		TbUser user = userServiceImpl.getUserByToken(token);
		//取不到用户信息，需要跳转到登录页面，把用户请求的url作为参数传递给登录页面
		if(null == user){
			response.sendRedirect(userServiceImpl.SSO_DOMAIN_BASE_URL + userServiceImpl.SSO_PAGE_LOGIN + "?redirect="
					+ userServiceImpl.PROTAL_BASE_URL + request.getRequestURI());
			//返回false
			return false;
		}
		//取到用户信息，放行。
		//将用户信息放入request
		request.setAttribute("user", user);
		//返回值决定Handler是否执行。true :执行； false: 不执行
		return true;		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//在handler执行之后，返回ModelAndView之前处理
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//返回ModelAndView之后，即相应用户之后
		
	}

}
