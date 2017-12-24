package com.taobao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbContent;
import com.taobao.service.ContentService;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(int page, int rows, Long categoryId){
		EUDataGridResult result = contentService.getContentList(page, rows, categoryId);
		return result;
	}
	
	@RequestMapping(value = "/content/save", method = RequestMethod.POST)
	@ResponseBody
	public TaobaoResult insertContent(TbContent content){
		TaobaoResult result = contentService.insertContent(content);
		return result;
	}	
	
}
