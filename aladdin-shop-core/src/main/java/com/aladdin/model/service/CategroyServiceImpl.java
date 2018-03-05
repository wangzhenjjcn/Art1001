package com.aladdin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aladdin.base.Pager;
import com.aladdin.model.entity.Category;
import com.aladdin.model.entity.Goods;
import com.aladdin.model.mapper.CategoryMapper;
import com.aladdin.model.mapper.GoodsMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("categoryService")
public class CategroyServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public List<Category> findByParentId(String parentId) {
		Category category = new Category();
		category.setGc_parent_id(parentId);
		category.setGc_show(1);
		return categoryMapper.select(category);
	}

	@Override
	public Goods findGoodsById(String id) {
		return goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public Category findCategoryById(String id) {
		return categoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public Pager<Goods> findAll(Pager<Goods> pager, String name, String cid, String bid) {
		
/*		Goods goods = new Goods();
		goods.setGoods_show(1);
		goods.setIs_del(0);*/
		
		
		Example example = new Example(Goods.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("goods_show", "1");
		criteria.andEqualTo("is_del","0");
		criteria.andEqualTo("goods_state","30");//审核通过

		if (!StringUtils.isEmpty(name)) {
			criteria.andLike("goods_name", "%"+name+"%");
		}
		
		if (!StringUtils.isEmpty(cid)) {
			criteria.andEqualTo("gc_id", cid);
		}
		
		if (!StringUtils.isEmpty(bid)) {
			criteria.andEqualTo("brand_id", bid);
		}
		

		PageHelper.startPage(pager.getPageIndex(), pager.getPageSize(), true);
		
		List<Goods> list = goodsMapper.selectByExample(example);
		
		PageInfo<Goods> pageInfo = new PageInfo<>(list);
	
	
		
		pager.setTotal(Integer.valueOf(pageInfo.getTotal()+""));
		pager.setMaxPages(pageInfo.getPages());
		

		
		pager.setData(pageInfo.getList());
		return pager;
	}
	
}
