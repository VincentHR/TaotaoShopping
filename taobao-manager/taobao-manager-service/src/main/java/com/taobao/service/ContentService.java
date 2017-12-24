package com.taobao.service;

import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbContent;

public interface ContentService {
	EUDataGridResult getContentList(int page, int rows, Long categoryId);
	TaobaoResult insertContent(TbContent content);
}
