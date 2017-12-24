package com.taobao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.utils.HttpClientUtil;
import com.taobao.common.utils.JsonUtils;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.portal.pojo.Order;
import com.taobao.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	
	@Override
	public String createOrder(Order order) {
		//调用taobao-order的服务提交订单
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		//把json转换成TaobaoResult
		TaobaoResult taobaoResult = TaobaoResult.format(json);
		if (taobaoResult.getStatus() == 200) {
			Object orderId = taobaoResult.getData();
			return orderId.toString();
		}
		return "";
	}

}
