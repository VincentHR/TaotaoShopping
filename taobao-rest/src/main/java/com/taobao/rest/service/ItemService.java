package com.taobao.rest.service;

import com.taobao.common.utils.TaobaoResult;

public interface ItemService {
	TaobaoResult getItemBaseInfo(long itemId);
	TaobaoResult getItemDesc(long itemId);
	TaobaoResult getItemParam(long itemId);
}
