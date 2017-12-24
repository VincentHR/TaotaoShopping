package com.taobao.service;

import java.util.List;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.common.utils.TaobaoResult;

public interface ContentCategoryService {
	List<EUTreeNode> getCategoryList(long parentId);
	TaobaoResult insertContentCategory(long parentId, String name);
	TaobaoResult deleteContentCategory(Long parentId, Long id);
	TaobaoResult updateContentCategory(Long id, String name);
}
