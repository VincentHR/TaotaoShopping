package com.taobao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 商品添加模板管理Controller
 * @author Unknown
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbItemParam;
import com.taobao.service.ItemParamService;

@Controller
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping("/item/param/list")
	@ResponseBody
	public EUDataGridResult getItemParamList(Integer page, Integer rows) {
		EUDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}

	@RequestMapping("/item/param/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaobaoResult getItemParamByCid(@PathVariable Long itemCatId) {
		TaobaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}

	@RequestMapping("/item/param/save/{cid}")
	@ResponseBody
	public TaobaoResult insertItemParam(@PathVariable Long cid, String paramData) {
		// 创建pojo对象
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		TaobaoResult result = itemParamService.insertItemParam(itemParam);
		return result;
	}

}
