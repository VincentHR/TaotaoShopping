package com.taobao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.utils.JsonUtils;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.mapper.TbItemDescMapper;
import com.taobao.mapper.TbItemMapper;
import com.taobao.mapper.TbItemParamItemMapper;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.pojo.TbItemParamItem;
import com.taobao.pojo.TbItemParamItemExample;
import com.taobao.pojo.TbItemParamItemExample.Criteria;
import com.taobao.rest.dao.JedisClient;
import com.taobao.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Autowired
	private JedisClient jedisClient;

	@Override
	public TaobaoResult getItemBaseInfo(long itemId) {
		try {
			// 添加缓存逻辑
			// 从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return TaobaoResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 根据商品id查询商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		try {
			// 把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
			// 设置key的有效期（Hash不能设置过期时间，String可以）
			jedisClient.expire(REDIS_ITEM_EXPIRE + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用TaotaoResult包装一下
		return TaobaoResult.ok(item);
	}

	@Override
	public TaobaoResult getItemDesc(long itemId) {
		//添加缓存逻辑
		try {
			// 从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return TaobaoResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//创建查询条件
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		try {
			// 把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			// 设置key的有效期（Hash不能设置过期时间，String可以）
			jedisClient.expire(REDIS_ITEM_EXPIRE + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaobaoResult.ok(itemDesc);
	}

	@Override
	public TaobaoResult getItemParam(long itemId) {
		//添加缓存逻辑
				try {
					// 从缓存中取商品信息，商品id对应的信息
					String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
					// 判断是否有值
					if (!StringUtils.isBlank(json)) {
						// 把json转换成java对象
						TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
						return TaobaoResult.ok(itemParamItem);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		//创建查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> paramItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(paramItems != null && paramItems.size()>0){
			TbItemParamItem paramItem = paramItems.get(0);
			try {
				// 把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				// 设置key的有效期（Hash不能设置过期时间，String可以）
				jedisClient.expire(REDIS_ITEM_EXPIRE + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaobaoResult.ok(paramItem);
		}
		return TaobaoResult.build(400, "无此商品规格");
	}

}
