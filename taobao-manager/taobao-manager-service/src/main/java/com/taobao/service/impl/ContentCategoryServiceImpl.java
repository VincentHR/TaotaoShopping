package com.taobao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.TabableView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.mapper.TbContentCategoryMapper;
import com.taobao.pojo.TbContentCategory;
import com.taobao.pojo.TbContentCategoryExample;
import com.taobao.pojo.TbContentCategoryExample.Criteria;
import com.taobao.service.ContentCategoryService;

/**
 * 内容分类管理
 * 
 * @author Unknown
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		// 根据 parentId 查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> euTreeNodes = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode euTreeNode = new EUTreeNode();
			euTreeNode.setId(tbContentCategory.getId());
			euTreeNode.setText(tbContentCategory.getName());
			euTreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");

			euTreeNodes.add(euTreeNode);
		}
		return euTreeNodes;
	}

	@Override
	public TaobaoResult insertContentCategory(long parentId, String name) {
		// 创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		// 状态 1-正常， 2-删除
		contentCategory.setStatus(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.insert(contentCategory);
		// 查看父节点的 isParentId 是否为 true， 如果不是 true，则改为 true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		// 返回结果
		return TaobaoResult.ok(contentCategory);
	}

	@Override
	public TaobaoResult deleteContentCategory(Long parentId, Long id) {
		// 根据 id 删除节点
		contentCategoryMapper.deleteByPrimaryKey(id);
/*		// 判断 parentId 对应的节点下是否有子节点，如果没有子节点， 需要把 isParentId 改为 false
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> parentList = contentCategoryMapper.selectByExample(example);
		if(parentList.size() == 0){
			parentCat.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}*/
		return TaobaoResult.ok();
	}

	@Override
	public TaobaoResult updateContentCategory(Long id, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return TaobaoResult.ok(contentCategory);
	}
}
