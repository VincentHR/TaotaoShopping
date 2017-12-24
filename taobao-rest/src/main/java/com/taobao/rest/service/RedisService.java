package com.taobao.rest.service;

import com.taobao.common.utils.TaobaoResult;

public interface RedisService {
	TaobaoResult syncContent(long contentCid);
}
