package com.taobao.portal.service;

import com.taobao.portal.pojo.ItemInfo;

public interface ItemService {
	ItemInfo getItemById(long itemId);
	String getItemDescById(long itemId);
	String getItemParam(long itemId);
}
