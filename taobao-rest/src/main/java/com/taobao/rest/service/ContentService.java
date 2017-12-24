package com.taobao.rest.service;

import java.util.List;

import com.taobao.pojo.TbContent;

public interface ContentService {
	List<TbContent> getContentList(Long contentCid);
}
