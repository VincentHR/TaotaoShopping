package com.taobao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taobao.common.utils.CookieUtils;
import com.taobao.common.utils.JsonUtils;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.mapper.TbUserMapper;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.TbUserExample;
import com.taobao.pojo.TbUserExample.Criteria;
import com.taobao.sso.dao.JedisClient;
import com.taobao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;

	@Override
	public TaobaoResult checkData(String content, Integer type) {
		// 创建查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 对数据进行校验：1、2、3分别代表username、phone、email
		// 用户名校验
		if (1 == type) {
			criteria.andUsernameEqualTo(content);
		} else if (2 == type) {
			criteria.andPhoneEqualTo(content);
			// e-mail校验
		} else {
			criteria.andEmailEqualTo(content);
		}
		// 根据条件执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return TaobaoResult.ok(true);
		}
		return TaobaoResult.ok(false);
	}

	@Override
	public TaobaoResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		// md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaobaoResult.ok();
	}

	/**
	 * 用户登录
	 */
	@Override
	public TaobaoResult userLogin(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		// 如果没有此用户名
		if (list == null || list.size() == 0) {
			return TaobaoResult.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		// 比对密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaobaoResult.build(400, "用户名或密码错误");
		}
		// 生成一个token
		String token = UUID.randomUUID().toString();
		// 保存用户信息之前，把用户对象中的密码清掉,相对来说比较安全
		user.setPassword(null);
		// 把用户信息写入redis
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		// 设置session的过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

		// 添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		// 返回token
		return TaobaoResult.ok(token);
	}

	/**
	 * 用户退出
	 */
	@Override
	public TaobaoResult userLogout(String token) {
		// 通过设置redis中相应的记录的过期时间为0，即可退出此用户
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, 0);
		return TaobaoResult.ok();
	}

	/**
	 * 根据token查询用户信息
	 */
	@Override
	public TaobaoResult getUserByToken(String token) {
		// 根据token从redis中查询用户信息
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		// 判断是否为空
		if (StringUtils.isBlank(json)) {
			return TaobaoResult.build(400, "此session已经过期，请重新登录！");
		}
		// 更新过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		// 返回用户信息
		return TaobaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

}
