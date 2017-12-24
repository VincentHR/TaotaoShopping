package com.taobao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.mapper.TbItemParamMapper;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemExample;
import com.taobao.pojo.TbItemParam;
import com.taobao.pojo.TbItemParamExample;
import com.taobao.pojo.TbItemParamExample.Criteria;
import com.taobao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	/**
	 * 商品规格参数列表查询
	 */
	@Override
	public EUDataGridResult getItemParamList(int page, int rows){
		//查询商品列表
		TbItemParamExample example = new TbItemParamExample(); 
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public TaobaoResult getItemParamByCid(long cid) {
		
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size()>0){
			return TaobaoResult.ok(list.get(0));
		}
		return TaobaoResult.ok();
	}
	
	@Override
	public TaobaoResult insertItemParam(TbItemParam itemParam){
		//补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入到规格参数模板表
		itemParamMapper.insert(itemParam);
		return TaobaoResult.ok();
	}

}
