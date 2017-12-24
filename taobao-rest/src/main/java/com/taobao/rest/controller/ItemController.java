package com.taobao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.utils.TaobaoResult;
import com.taobao.rest.service.ItemService;
/**
 * 商品信息Controller
 * @author 过云雨
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaobaoResult getItemBaseInfo(@PathVariable long itemId){
		TaobaoResult result = itemService.getItemBaseInfo(itemId);
		return result;
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaobaoResult getItemDesc(@PathVariable long itemId){
		TaobaoResult result = itemService.getItemDesc(itemId);
		return result;
	}
	
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaobaoResult getItemParamItem(@PathVariable long itemId){
		TaobaoResult result = itemService.getItemParam(itemId);
		return result;
	}
}







