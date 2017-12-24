package com.taobao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.utils.TaobaoResult;
import com.taobao.search.service.ItemService;

/**
 * 索引库维护
 * @author 过云雨
 *
 */
@Controller
@RequestMapping("/manager")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	/**
	 * 导入商品数据到索引库
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public TaobaoResult importAllItems(){
		TaobaoResult result = itemService.importAllItems();
		return result;
	}
	
}
