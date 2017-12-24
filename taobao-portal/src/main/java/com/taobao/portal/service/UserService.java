package com.taobao.portal.service;

import com.taobao.pojo.TbUser;

public interface UserService {
	TbUser getUserByToken(String token);
}
