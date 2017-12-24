package com.taobao.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.utils.HttpClientUtil;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbUser;
import com.taobao.portal.service.UserService;

/**
 * 用户管理Service
 * 
 * @author 过云雨
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_DOMAIN_BASE_URL}")
	public String SSO_DOMAIN_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;
	@Value("${SSO_PAGE_LOGIN}")
	public String SSO_PAGE_LOGIN;
	
	@Value("${PROTAL_BASE_URL}")
	public String PROTAL_BASE_URL;
	
	@Override
	public TbUser getUserByToken(String token) {
		try {
			// 调用sso系统的服务，根据token获取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
			// 把json转换成TaobaoResult
			TaobaoResult result = TaobaoResult.formatToPojo(json, TbUser.class);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
