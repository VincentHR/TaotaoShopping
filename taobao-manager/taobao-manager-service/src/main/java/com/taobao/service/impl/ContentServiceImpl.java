package com.taobao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.utils.HttpClientUtil;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.mapper.TbContentMapper;
import com.taobao.pojo.TbContent;
import com.taobao.pojo.TbContentCategoryExample;
import com.taobao.pojo.TbContentCategoryExample.Criteria;
import com.taobao.pojo.TbContentExample;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemExample;
import com.taobao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	/**
	 * 内容列表查询
	 */
	@Override
	public EUDataGridResult getContentList(int page, int rows, Long categoryId){
		//查询商品列表
		TbContentExample example = new TbContentExample();
		com.taobao.pojo.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExample(example);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaobaoResult insertContent(TbContent content) {
		//填充表中信息
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaobaoResult.ok();
	}
}
