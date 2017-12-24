package com.taobao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.utils.ExceptionUtil;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.search.pojo.SearchResult;
import com.taobao.search.service.SearchService;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/query", method=RequestMethod.GET)
	@ResponseBody
	public TaobaoResult search(@RequestParam("q") String queryString, 
			@RequestParam(defaultValue="1")Integer page,
			@RequestParam(defaultValue="60")Integer rows){
		//查询条件不能为空
		if(StringUtils.isBlank(queryString)){
			return TaobaoResult.build(400, "查询条件不能为空");
		}
		SearchResult searchResult = null;
		try {
			queryString = new String(queryString.getBytes("iso-8859-1"), "utf-8");
			searchResult = searchService.search(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return TaobaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		return TaobaoResult.ok(searchResult);
	}
}
