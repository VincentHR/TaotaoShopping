package com.taobao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbContentCategory;
import com.taobao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCateList(@RequestParam(value = "id", defaultValue = "0")long parentId){
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaobaoResult createContentCategory(long parentId, String name){
		TaobaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/delete")
	public TaobaoResult deleteContentCategory(Long parentId, Long id){
		TaobaoResult result = contentCategoryService.deleteContentCategory(parentId, id);
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaobaoResult updateContentCategory(Long id, String name){
		TaobaoResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}
	
	
}
