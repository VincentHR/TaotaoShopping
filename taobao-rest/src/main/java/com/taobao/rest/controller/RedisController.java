package com.taobao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.utils.TaobaoResult;
import com.taobao.rest.service.RedisService;

/**
 * 缓存同步
 * 
 * @author Unknown
 *
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {

	@Autowired
	private RedisService redisService;

	@RequestMapping("/content/{contentCid}")
	@ResponseBody
	public TaobaoResult contentCacheSync(@PathVariable Long contentCid) {
		TaobaoResult result = redisService.syncContent(contentCid);
		return result;
	}

}