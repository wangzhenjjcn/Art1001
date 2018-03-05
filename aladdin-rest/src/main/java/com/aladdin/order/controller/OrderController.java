package com.aladdin.order.controller;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aladdin.base.BaseController;
import com.aladdin.base.ErrorEnum;
import com.aladdin.member.entity.Member;
import com.aladdin.model.entity.Goods;
import com.aladdin.model.entity.Model;
import com.aladdin.model.entity.Sku;
import com.aladdin.model.service.CategoryService;
import com.aladdin.model.service.ModelService;
import com.aladdin.model.service.ProductService;
import com.aladdin.order.entity.Order;
import com.aladdin.order.entity.OrderAddress;
import com.aladdin.order.entity.OrderGoods;
import com.aladdin.order.entity.OrderLog;
import com.aladdin.order.entity.OrderPay;
import com.aladdin.order.service.OrderService;
import com.aladdin.project.ProjectReportContants;
import com.aladdin.project.entity.ProjectReport;
import com.aladdin.project.service.ProjectReportService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.wordnik.swagger.annotations.Api;

@Api(value = "/api/order")
@RestController
@RequestMapping("/api/order")
public class OrderController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProjectReportService projectReportService;
	
	@RequestMapping("order")
	public ResponseEntity<?> order(
			@RequestParam(required=true) String packageId,
			HttpServletRequest req){
		
		if(packageId==null||"".equals(packageId)){
			return buildResponseEntity(ErrorEnum.NOT_EXIST);
		}
		String[] ids = findModelIds(packageId);
		if(ids==null||ids.length==0){
			return buildResponseEntity(ErrorEnum.NOT_EXIST);
		}
		return submit(ids,req);
	}
	
	public ResponseEntity<?> submit(String[] modelIds, HttpServletRequest request) {

		Member user = getLoginUser(request);
		
		List<Sku> skuList = new ArrayList<>();
		for(String modelId:modelIds){
			Model model = modelService.findSingleModelById(modelId);
			if (model == null || model.getModelCode() == null)
				continue;
			Sku sku = productService.findSkuById(model.getModelCode());
			if (sku == null)
				continue;
			Goods good = categoryService.findGoodsById(sku.getGoodsId());
			if (good == null)
				continue;
			
			sku.setGood(good);
			skuList.add(sku);
		}
		
		if(skuList.isEmpty()){
			return buildResponseEntity(ErrorEnum.NOT_EXIST);
		}

		Order order = new Order();
		order.setOrderId(UUID.randomUUID().toString().replace("-", ""));
		order.setOrderSn(DateTime.now().toString("yyyyMMddHHmmssSSS"));
		order.setStoreId("aladdin");
		order.setStoreName("一千零一艺-阿拉丁");
		order.setBuyerId(user.getId());
		order.setBuyerName(user.getName());
		order.setBuyerEmail(user.getEmail());
		order.setBuyerId(user.getId());
		order.setBuyerName(user.getName());
		order.setCreateTime(System.currentTimeMillis());
		// 设置支付方式默认值
		order.setPaymentId("1");
		order.setPaymentName("在线支付");
		order.setPaymentCode("1");
		order.setPaymentDirect("1");
		order.setPaymentState(0);
		
		BigDecimal totalPrice = new BigDecimal("0");
		for(Sku s : skuList){
			totalPrice = totalPrice.add(s.getSpecGoodsPrice());
		}
		// 处理订单价格
		order.setGoodsAmount(totalPrice);//商品总价格
		order.setDiscount(new BigDecimal("0.0"));//优惠总金额
		order.setOrderTotalPrice(totalPrice);//订单价格
		order.setOrderAmount(totalPrice);//订单应付金额
		// 运费
		order.setShippingFee(new BigDecimal("0.0"));

		order.setInvoice("不开发票");
		// 无换货
		order.setBarterState(70);
		order.setBarterNum(0);
		order.setOrderState(10);
		// 优惠券，无优惠券初始化数据库数据
		order.setCouponPrice(new BigDecimal("0.0"));
		// 促销金额
		order.setPromoPrice(new BigDecimal("0.0"));

		OrderAddress oa = orderService.findDefaultAddress(user.getId());
		if (oa == null) {
			return new ResponseEntity<String>("寄送地址不存在无法下单", HttpStatus.OK);
		}

		order.setAddressId(oa.getAddressId());
		order.setDaddressId("1234567890");
		order.setPredepositAmount(new BigDecimal("0"));
		OrderPay op = new OrderPay();
		op.setPayId(UUID.randomUUID().toString().replace("-", ""));
		op.setBuyerId(user.getId());
		op.setPaySn(DateTime.now().toString("yyyyMMddHHmmssSSS"));
		op.setApiPayState("0");
		int i = orderService.saveOrderPay(op);
		if (i > 0) {
			order.setPayId(op.getPayId());
			order.setPaySn(op.getPaySn());
		} else {
			return new ResponseEntity<String>("保存订单支付信息失败", HttpStatus.OK);
		}

		int o = orderService.saveOrder(order);

		if (o == 0) {
			return new ResponseEntity<String>("保存订单信息失败", HttpStatus.OK);
		}
		for(Sku s:skuList){
			OrderGoods og = new OrderGoods();
			og.setRecId(UUID.randomUUID().toString().replace("-", ""));
			og.setOrderId(order.getOrderId());
			og.setGoodsName(s.getGood().getGoods_name());
			og.setGoodsId(s.getGood().getGoods_id());
			og.setSpecId(s.getGoodsSpecId());
			og.setSpecInfo(s.getSkuName());
			og.setGoodsPrice(s.getSpecGoodsPrice());
			og.setGoodsNum(1);
			og.setGoodsImage(s.getGood().getGoods_image());
			og.setStoreId("aladdin"/*s.getGood().getStoreId()*/);
			og.setEvaluationStatus(0);
			og.setGoodsPayPrice(s.getSpecGoodsPrice());// 实际成交价格,无折扣为sku价格
			og.setBuyerId(user.getId());
			og.setGcId(s.getGood().getGc_id());
			// 填充默认值，数据库无默认值的
			og.setGoodsPreAmount(new BigDecimal("0.0"));
			og.setCommisRate(0F);
			og.setGoodsBarterNum(0);
			int iog = orderService.saveOrderGoods(og);
			if (iog == 0) {
				return new ResponseEntity<String>("生成订单商品失败", HttpStatus.OK);
			}
			//暂不考虑库存
		}

		OrderLog orderLog = new OrderLog();
		orderLog.setLogId(UUID.randomUUID().toString().replace("-", ""));
		orderLog.setOrderId(order.getOrderId());
		orderLog.setOrderState("10");// 未付款
		orderLog.setChangeState("20");
		orderLog.setStateInfo("提交订单");
		orderLog.setCreateTime(System.currentTimeMillis());
		orderLog.setOperator(user.getName());

		int ilog = orderService.saveOrderLog(orderLog);
		if (ilog == 0) {
			return new ResponseEntity<String>("生成日志表失败", HttpStatus.OK);
		}
		return buildResponseEntity(ErrorEnum.SUCCESS);
	}
	
	
	private String[] findModelIds(String packageId) {
		
		ProjectReport pr = new ProjectReport();
		pr.setPackageId(packageId);
		pr.setName(ProjectReportContants.REPORTS_MAIN_MATERIAL_DETAILS);
		
		List<ProjectReport> reports = projectReportService.selectByCondition(pr);
		if (reports == null | reports.isEmpty()) {
			return new String[] {};
		}
		ProjectReport p = reports.get(0); 
		if(p.getJsonfile()!=null&&!p.getJsonfile().trim().equals("")){
			return parseJson(p.getJsonfile());
		}
		return new String[] {};
	}
	/**
  {
	"id":"reports_main_material_details",
	"name":"主材明细表",
	"children":[
		{
			"children":[
					{"id":"3eb0d781042547e4bfe09399e466882c","unit":"㎡","remark":null,"quantity":26.11},
					{"id":"fd03eb575bc24362b4916dbdcddf8ef3","unit":"","remark":null,"quantity":50.75}
					],
					"zone":"客餐厅"
			}
		]
	}	
	 */
	@SuppressWarnings("unchecked")
	public static String[] parseJson(String jsonfile) {
		
		Set<String> idlist = new HashSet<>();
		Gson gson = new Gson();
		//转换全局json对象
		Map<String,Object> jsonObj = gson.fromJson(jsonfile, new TypeToken<Map<String,Object>>() { 
		}.getType());
		//获取外层children集合
		List<Map<String,Object>> outerChildren = (List<Map<String, Object>>) jsonObj.get("children");
		if(outerChildren!=null){
			//遍历外层childre集合,获取内层children
			for(Map<String,Object> map : outerChildren){
				List<Map<String,String>> innerChildren =  (List<Map<String, String>>) map.get("children");
				if(innerChildren!=null){
					for(Map<String,String> m :innerChildren){
						idlist.add(m.get("id"));
					}
				}
			}
		}
		return idlist.toArray(new String[]{});
	}
/*	public static void main(String[] args) {
		String json = 
		"{ 'id':'reports_main_material_details','name':'主材明细表','children':[{'children':[{'id':'3eb0d781042547e4bfe09399e466882c','unit':'㎡','remark':null,'quantity':26.11}],'zone':'客餐厅'},{'children':[{'id':'3eb0d781042547e4bfe09399e466882c','unit':'㎡','remark':null,'quantity':26.11}],'zone':'客餐厅'}]}";
		parseJson(json);
	}*/
}
