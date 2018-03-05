package com.aladdin.model.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladdin.base.BaseController;
import com.aladdin.base.ErrorEnum;
import com.aladdin.base.Pager;
import com.aladdin.model.entity.Brand;
import com.aladdin.model.entity.Goods;
import com.aladdin.model.entity.Model;
import com.aladdin.model.entity.Sku;
import com.aladdin.model.service.BrandService;
import com.aladdin.model.service.CategoryService;
import com.aladdin.model.service.ModelService;
import com.aladdin.model.service.ProductService;

@Controller
@RequestMapping("/api/v1/goods")
public class GoodsController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;

	@RequestMapping(value = "/list")
	public ResponseEntity<?> list(HttpServletRequest request,
			@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "cid", defaultValue = "") String cid,
			@RequestParam(value = "bid", defaultValue = "") String bid) {
		Pager<Goods> pager = new Pager<Goods>(pageNo, pageSize);

		pager = categoryService.findAll(pager, name, cid, bid);

		// List<Model> goodsModelList=modelService.selectGoodsModel();

		List<Goods> list = pager.getData();
		List<Goods> newList = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
			for (int n=0;n<list.size();n++) {
				Goods good = list.get(n);
				if(good!=null&&good.getBrand_id()!=null){
					Brand b = brandService.findBrandById(good.getBrand_id());
					good.setBrandName(b==null?"":b.getBrand_name());
				}
				List<Sku> skus = productService.findSkusByGood(good);
				if (skus != null) {
					for(int i=0; i<skus.size();i++){
						Sku sku = skus.get(i);
						List<Model> models = modelService.findBySku(sku);
						if(sku.getSpecGoodsSpec()==null||"".equals(sku.getSpecGoodsSpec().trim())||(models!=null&&!models.isEmpty())){
//							sku.setIs_exist(ModelContants.exist_yes);
							skus.remove(sku);
						}
					}
				}
				if(skus!=null&&!skus.isEmpty()){
					good.setSkus(skus);
					newList.add(good);
				}
			}
		}
		pager.setData(newList);
		return buildResponseEntity(ErrorEnum.SUCCESS, pager);
	}

}
