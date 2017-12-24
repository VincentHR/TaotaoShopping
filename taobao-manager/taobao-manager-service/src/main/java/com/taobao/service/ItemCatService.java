package com.taobao.service;

import java.util.List;

import com.taobao.common.pojo.EUTreeNode;

public interface ItemCatService {

	
	List<EUTreeNode> getItemCatList(Long parentId);
}
