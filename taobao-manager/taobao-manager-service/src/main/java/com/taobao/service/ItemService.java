package com.taobao.service;

import java.util.List;

import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.pojo.EUTreeNode;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItemById(long itemId);
	EUDataGridResult getItemList(int page, int rows);
	TaobaoResult addItem(TbItem item, String desc, String itemParam) throws Exception;
	
}
