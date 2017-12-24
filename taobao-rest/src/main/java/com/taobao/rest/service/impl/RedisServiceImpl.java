package com.taobao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.utils.ExceptionUtil;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.rest.dao.JedisClient;
import com.taobao.rest.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public TaobaoResult syncContent(long contentCid) {
		try {
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid+"");			
		} catch (Exception e) {
			e.printStackTrace();
			return TaobaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaobaoResult.ok();
	}
}
