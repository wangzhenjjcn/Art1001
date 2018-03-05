package com.aladdin.order.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.order.entity.Order;
import com.aladdin.order.entity.OrderAddress;
import com.aladdin.order.entity.OrderGoods;
import com.aladdin.order.entity.OrderLog;
import com.aladdin.order.entity.OrderPay;
import com.aladdin.order.mapper.OrderAddressMapper;
import com.aladdin.order.mapper.OrderGoodsMapper;
import com.aladdin.order.mapper.OrderLogMapper;
import com.aladdin.order.mapper.OrderMapper;
import com.aladdin.order.mapper.OrderPayMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderGoodsMapper orderGoodsMapper;

	@Autowired
	private OrderPayMapper orderPayMapper;

	@Autowired
	private OrderAddressMapper orderAddressMapper;

	@Autowired
	private OrderLogMapper orderLogMapper;
	
	@Override
	public int saveOrder(Order order) {
		
		if(order.getOrderId()==null){
			order.setOrderId(UUID.randomUUID().toString().replace("-", ""));
		}
		if(order.getOrderSn()==null){
			order.setOrderSn(DateTime.now().toString("yyyyMMddHHmmssSSS"));
		}
		if(order.getPaymentId()==null){
			order.setPayId("1");
		}
		if(order.getPaymentName()==null){
			order.setPaymentName("在线支付");
		}
		if(order.getPaymentCode()==null){
			order.setPaymentCode("1");
		}
		if(order.getPaymentDirect()==null){
			order.setPaymentDirect("1");
		}
		if(order.getPaymentState()==null){
			order.setPaymentState(0);
		}
		if(order.getInvoice()==null){
			order.setInvoice("不开发票");
		}
		if(order.getOrderState()==null){
			order.setOrderState(10);
		}
		return orderMapper.insertSelective(order);
	}

	@Override
	public int saveOrderGoods(OrderGoods orderGoods) {
		if(orderGoods.getRecId()==null){
			orderGoods.setRecId(UUID.randomUUID().toString().replace("-", ""));
		}
		return orderGoodsMapper.insertSelective(orderGoods);
	}

	@Override
	public int saveOrderPay(OrderPay orderPay) {
		if(orderPay.getPayId()==null||"".equals(orderPay.getPayId().trim())){
			orderPay.setPayId(UUID.randomUUID().toString().replace("-", ""));
		}
		if(orderPay.getPaySn()==null){
			orderPay.setPaySn(DateTime.now().toString("yyyyMMddHHmmssSSS"));
		}
		if(orderPay.getApiPayState()==null){
			orderPay.setApiPayState("0");
		}
		return orderPayMapper.insertSelective(orderPay);
	}

	@Override
	public OrderAddress findDefaultAddress(String memberId) {
		
		Example ex = new Example(OrderAddress.class);
		Criteria cri =  ex.createCriteria();
		
		cri.andEqualTo("memberId", memberId);
		cri.andEqualTo("isDefault", 1);
		List<OrderAddress> list = orderAddressMapper.selectByExample(ex);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int saveOrderLog(OrderLog orderLog) {
		if(orderLog.getLogId()==null){
			orderLog.setLogId(UUID.randomUUID().toString().replace("-", ""));
		}
		if(orderLog.getCreateTime()==0){
			orderLog.setCreateTime(System.currentTimeMillis());
		}
		return orderLogMapper.insertSelective(orderLog);
	}

}
