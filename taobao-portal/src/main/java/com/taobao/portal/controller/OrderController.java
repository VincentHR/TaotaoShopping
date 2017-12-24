package com.taobao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.common.utils.ExceptionUtil;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbUser;
import com.taobao.portal.pojo.CartItem;
import com.taobao.portal.pojo.Order;
import com.taobao.portal.service.CartService;
import com.taobao.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model){
		//取出购物车商品列表
		List<CartItem> list = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", list);
		return "order-cart";
	}
	
	@RequestMapping("/create")
	public String createOrder(Order order, Model model, HttpServletRequest request){
		try {
			//从request中取出用户信息
			TbUser user = (TbUser) request.getAttribute("user");
			//补全order中的用户信息
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			//调用服务创建订单
			String orderId = orderService.createOrder(order);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "创建订单出错，请稍后重试！");
			return "error/exception";
		}
	}
}
