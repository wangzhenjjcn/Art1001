package com.aladdin.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.model.entity.Goods;
import com.aladdin.model.entity.Sku;
import com.aladdin.model.mapper.SkuMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private SkuMapper skuMapper;

	@Override
	public Sku findSkuById(String skuId) {
		return skuMapper.selectByPrimaryKey(skuId);
	}

	@Override
	public List<Sku> findSkusByGood(Goods good) {

		Example ex = new Example(Sku.class);
		Criteria cri = ex.createCriteria();

		cri.andEqualTo("goodsId", good.getGoods_id());

		List<Sku> skus = skuMapper.selectByExample(ex);

		return skus;
	}

	@Override
	public Sku findSkuBySpecifications(String specificationIds, Goods goods) {
		if (goods != null) {
			/** 通过商品id获得商品下所有对应的规格 */
			List<Sku> skus = findSkusByGood(goods);
			if (StringUtils.isNotEmpty(specificationIds)) {
				/** 将传入的规格值id,以逗号分隔转成String数组 */
				String[] strArr01 = specificationIds.split(",");
				/** 排序 */
				Arrays.sort(strArr01);

				for (Sku sku : skus) {
					/** 得到以逗号分隔的规格值id */
					String str = getThisGoodsAllSpecValueId(sku.getSpecGoodsSpec());

					/** 和传入的进行比较,如果匹配则返回,否则继续匹配 */
					String[] strArr02 = str.split(",");
					/** 排序 */
					Arrays.sort(strArr02);

					/** 比较两个数组是否相等 */
					/** 匹配 */
					if (Arrays.equals(strArr01, strArr02)) {
						return sku;
					}
				}
			} else {
				/** 如果只有一个规格那么直接返回 */
				if (skus.size() == 1) {
					return skus.get(0);
				}
			}

		} else {
			return null;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findSpecificationsByGood(Goods good) {

		if (good != null && good.getSpecName() != null && good.getGoodsSpec() != null) {
			return analySpecForShow(good.getSpecName(), good.getGoodsSpec());
		}
		return null;
	}

	public List<Map<String, Object>> analySpecForShow(String specname, String specval) {

		return analySpecForShow(specname, specval, null);

	}

	public List<Map<String, Object>> analySpecForShow(String specname, String specval,
			Map<String, String> selectSpecs) {

		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();

		if (StringUtils.isNotEmpty(specname) && StringUtils.isNotEmpty(specval)) {
			Map<String, String> specNameMap = new Gson().fromJson(specname, new TypeToken<HashMap<String, String>>() {
			}.getType());
			Map<String, String> specValueMap = new Gson().fromJson(specval, new TypeToken<HashMap<String, String>>() {
			}.getType());

			Set<String> set = specNameMap.keySet();
			for (String k : set) {
				Map<String, Object> pMap = new HashMap<String, Object>();
				if (StringUtils.isNotEmpty(k)) {
					pMap.put("goodsSpecId", k);
					if (specNameMap.get(k) != null) {
						pMap.put("goodsSpecName", specNameMap.get(k));
					}
					if (selectSpecs != null) {
						if (StringUtils.isNotEmpty(selectSpecs.get(k))) {
							pMap.put("defSpecVal", selectSpecs.get(k));
						}
					}
					String specValueStr = specValueMap.get(k);
					if (StringUtils.isNotEmpty(specValueStr)) {
						List<Map<String, String>> valList = new ArrayList<Map<String, String>>();
						Map<String, String> valMap = new Gson().fromJson(specValueStr,
								new TypeToken<HashMap<String, String>>() {
								}.getType());
						if (valMap != null) {
							for (String valkey : valMap.keySet()) {
								if (StringUtils.isNotEmpty(valkey)) {
									Map<String, String> valMap2 = new HashMap<String, String>();
									valMap2.put("specValueId", valkey);
									if (valMap.get(valkey) != null) {
										valMap2.put("specValueName", valMap.get(valkey));
									}
									if (valMap2 != null) {
										valList.add(valMap2);
									}
								}
							}
						}
						if (valList != null) {
							pMap.put("detailInfo", valList);
						}

					}
				}
				if (pMap != null) {
					resList.add(pMap);
				}

			}

		}
		return resList;
	}

	private String getThisGoodsAllSpecValueId(String specGoodsSpec) {
		if (specGoodsSpec == null || specGoodsSpec.trim().equals("")) {
			return null;
		}
		Map<String, String> map = new Gson().fromJson(specGoodsSpec, new TypeToken<HashMap<String, String>>() {
		}.getType());
		Set<String> set = map.keySet();
		String str = "";
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			str += it.next() + ",";
		}
		str = str.substring(0, str.length() - 1);
		return str;
	}
}
