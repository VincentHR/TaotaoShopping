package com.taobao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbUser;

public interface UserService {
	TaobaoResult checkData(String content, Integer type);
	TaobaoResult createUser(TbUser user);
	TaobaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);
	TaobaoResult getUserByToken(String token);
	TaobaoResult userLogout(String token);
}
