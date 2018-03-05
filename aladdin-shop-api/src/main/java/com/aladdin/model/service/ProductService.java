package com.aladdin.model.service;

import java.util.List;
import java.util.Map;

import com.aladdin.model.entity.Goods;
import com.aladdin.model.entity.Sku;

public interface ProductService {
	/**
	 * 根据skuid查询sku
	 */
	public Sku findSkuById(String skuId);
	/**
	 * 查询spu下的所有sku
	 */
	public List<Sku> findSkusByGood(Goods good);
	/**
	 * 查询spu下的所有规格信息
	 */
	@Deprecated
	public List<Map<String, Object>> findSpecificationsByGood(Goods good);
	/**
	 * 通过规格值ids串匹配商品关联的sku
	 */
	@Deprecated
	public Sku findSkuBySpecifications(String specificationIds,Goods good);
}
