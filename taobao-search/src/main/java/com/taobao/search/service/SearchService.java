package com.taobao.search.service;

import com.taobao.search.pojo.SearchResult;

public interface SearchService {
	public SearchResult search(String queryString, int page, int rows)throws Exception;
}
