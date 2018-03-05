package com.aladdin.order.service;

import com.aladdin.order.entity.Order;
import com.aladdin.order.entity.OrderAddress;
import com.aladdin.order.entity.OrderGoods;
import com.aladdin.order.entity.OrderLog;
import com.aladdin.order.entity.OrderPay;

public interface OrderService {
	
	public int saveOrder(Order order);
	
	public int saveOrderGoods(OrderGoods orderGoods);
	
	public int saveOrderPay(OrderPay orderPay);
	
	public OrderAddress findDefaultAddress(String memberId);

	public int saveOrderLog(OrderLog orderLog);

}
