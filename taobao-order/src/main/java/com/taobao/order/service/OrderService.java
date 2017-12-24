package com.taobao.order.service;

import java.util.List;

import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.TbOrderShipping;

public interface OrderService {
	TaobaoResult cerateOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItems, TbOrderShipping tbOrderShipping);
}
