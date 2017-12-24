package com.taobao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.utils.ExceptionUtil;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbUser;
import com.taobao.sso.service.UserService;

/**
 * 用户Controller
 * 
 * @author 过云雨
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {

		TaobaoResult result = null;
		// 参数有效性校验
		if (StringUtils.isBlank(param)) {
			result = TaobaoResult.build(400, "校验内容不能为空");
		}
		if (type == null) {
			result = TaobaoResult.build(400, "校验内容类型不能为空");
		}
		if (type != 1 && type != 2 && type != 3) {
			result = TaobaoResult.build(400, "校验内容类型错误");
		}
		// 校验出错
		if (null != result) {
			if (null != callback) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			} else {
				return result;
			}
		}
		try {
			// 调用服务
			result = userService.checkData(param, type);
		} catch (Exception e) {
			result = TaobaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		if (null != callback) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}
	
	//创建用户
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public TaobaoResult createUser(TbUser user){
		try {
			TaobaoResult result = userService.createUser(user);			
			return result;
		} catch (Exception e) {
			return TaobaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	//用户登录
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public TaobaoResult userLogin(String username, String password,
			HttpServletRequest request, HttpServletResponse response){
		try {
			TaobaoResult result = userService.userLogin(username, password, request, response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaobaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	/**
	 * 用户退出
	 * 根据token找出相应的储存在redis中的值
	 * 然后删除
	 * @return
	 */
	@RequestMapping("/logout/{token}")
	public String userLogout(@PathVariable String token){
		userService.userLogout(token);			
		return "login";
	}
	
	//返回用户信息
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback){
		TaobaoResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = TaobaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		//判断是否为jsonp调用
		if(StringUtils.isBlank(callback)){
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	} 
}













