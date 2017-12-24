package com.taobao.service;

import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbItemParam;

public interface ItemParamService {
	TaobaoResult getItemParamByCid(long cid);
	TaobaoResult insertItemParam(TbItemParam itemParam);
	EUDataGridResult getItemParamList(int page, int rows);
}
