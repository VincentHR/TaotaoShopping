package com.taobao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.utils.JsonUtils;
import com.taobao.mapper.TbItemCatMapper;
import com.taobao.pojo.TbContentCategoryExample.Criteria;
import com.taobao.pojo.TbItemCat;
import com.taobao.pojo.TbItemCatExample;
import com.taobao.rest.dao.JedisClient;
import com.taobao.rest.pojo.CatNode;
import com.taobao.rest.pojo.CatResult;
import com.taobao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_ITEM_CAT_REDIS_KEY}")
	private String INDEX_ITEM_CAT_REDIS_KEY;

	@Override
	public CatResult getItemCatList() {

		CatResult catResult = new CatResult();
		// 查询分类列表
		catResult.setData(getCatList(0));

		return catResult;
	}

	/**
	 * 查询分类列表
	 * 
	 * @param parentId
	 * @return
	 */
	private List<?> getCatList(long parentId) {
		// 从缓存中取内容
		try {
			String result = jedisClient.hget(INDEX_ITEM_CAT_REDIS_KEY, parentId + "");
			if (!StringUtils.isBlank(result)) {
				// 把字符串转换成list
				List<TbItemCat> resultList = JsonUtils.jsonToList(result, TbItemCat.class);
				// 返回值list
				List list = new ArrayList<>();
				int count = 0;
				// 向list中添加节点
				for (TbItemCat tbItemCat : resultList) {
					// 判断是否为父节点
					if (tbItemCat.getIsParent()) {
						CatNode catNode = new CatNode();
						if (parentId == 0) {
							catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName()
									+ "</a>");
						} else {
							catNode.setName(tbItemCat.getName());
						}
						catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
						catNode.setItem(getCatList(tbItemCat.getId()));
						list.add(catNode);
						count++;
						// 第一级第一层只取14条记录
						if (parentId == 0 && count >= 14) {
							break;
						}
					} else {
						// 如果是叶子节点
						list.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
					}
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		com.taobao.pojo.TbItemCatExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 向缓存中添加内容
		try {
			// 把list转换成字符串
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_ITEM_CAT_REDIS_KEY, parentId + "", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回值list
		List resultList = new ArrayList<>();
		int count = 0;
		// 向list中添加节点
		for (TbItemCat tbItemCat : list) {
			// 判断是否为父节点
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName(
							"<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(catNode);
				count++;
				// 第一级第一层只取14条记录
				if (parentId == 0 && count >= 14) {
					break;
				}
			} else {
				// 如果是叶子节点
				resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}
		return resultList;
	}
}
