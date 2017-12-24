package com.taobao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.utils.HttpClientUtil;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.portal.pojo.SearchResult;
import com.taobao.portal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String queryString, int page) {
		//调用 Taotao-Search 的服务
		//查询参数
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page + "");
		try {
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			//把字符串转换成 java 对象
			TaobaoResult taobaoResult = TaobaoResult.formatToPojo(json, SearchResult.class);
			if(taobaoResult.getStatus() == 200){
				SearchResult result = (SearchResult)taobaoResult.getData();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
