package com.taobao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taobao.common.utils.TaobaoResult;
import com.taobao.portal.pojo.CartItem;

public interface CartService {
	TaobaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);
	List<CartItem> getCartItemList(HttpServletRequest request,	HttpServletResponse response);
	TaobaoResult deleteCartItem(long itemId, HttpServletRequest request,	HttpServletResponse response);
}
