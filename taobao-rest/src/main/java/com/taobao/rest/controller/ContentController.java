package com.taobao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.utils.ExceptionUtil;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbContent;
import com.taobao.rest.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public TaobaoResult getContentList(@PathVariable Long contentCategoryId){
		try {
			List<TbContent> list = contentService.getContentList(contentCategoryId);
			return TaobaoResult.ok(list);			
		} catch (Exception e) {
			e.printStackTrace();
			return TaobaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
