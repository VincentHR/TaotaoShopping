package com.taobao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.utils.TaobaoResult;
import com.taobao.mapper.TbOrderItemMapper;
import com.taobao.mapper.TbOrderMapper;
import com.taobao.mapper.TbOrderShippingMapper;
import com.taobao.order.dao.JedisClient;
import com.taobao.order.service.OrderService;
import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.TbOrderShipping;

/**
 * 创建订单 Service
 * @author 过云雨
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_INIT_KEY}")
	private String ORDER_INIT_KEY;
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;
	
	@Override
	public TaobaoResult cerateOrder(TbOrder order, List<TbOrderItem> orderItems, TbOrderShipping orderShipping) {
		//向订单表插入记录
		//获得订单号
		String string = jedisClient.get(ORDER_GEN_KEY);
		if (StringUtils.isBlank(string)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_KEY);
		}
		long orderId = jedisClient.incr(ORDER_GEN_KEY);
		//补全pojo属性
		order.setOrderId(orderId + "");
		//'状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭'
		order.setStatus(1);
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		//0：未评价； 1：已评价
		order.setBuyerRate(0);
		//向订单表插入数据
		tbOrderMapper.insert(order);
		//插入订单明细
		for (TbOrderItem orderItem : orderItems) {
			//补全订单明细
			//取订单明细id
			long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
			orderItem.setId(orderDetailId + "");
			orderItem.setOrderId(orderId + "");
			//向订单明细插入记录
			tbOrderItemMapper.insert(orderItem);
		}
		//插入物流表
		//补全物流表的属性
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		tbOrderShippingMapper.insert(orderShipping);
		
		return TaobaoResult.ok(orderId);
	}

}
