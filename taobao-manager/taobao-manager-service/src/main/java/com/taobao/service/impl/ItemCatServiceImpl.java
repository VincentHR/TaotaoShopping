package com.taobao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.mapper.TbItemCatMapper;
import com.taobao.pojo.TbItemCat;
import com.taobao.pojo.TbItemCatExample;
import com.taobao.pojo.TbItemCatExample.Criteria;
import com.taobao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EUTreeNode> getItemCatList(Long parentId) {
		
		//根据 parentId 查询分类列表
		TbItemCatExample example = new TbItemCatExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//分类列表转换为 TreeNode 的列表
		List<EUTreeNode> resultList = new ArrayList<>();
		for(TbItemCat tbItemCat : list){
			EUTreeNode node = new EUTreeNode(tbItemCat.getId(), tbItemCat.getName(), tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

}
