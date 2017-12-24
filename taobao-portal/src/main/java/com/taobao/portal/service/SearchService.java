package com.taobao.portal.service;

import com.taobao.portal.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString, int page);
}
