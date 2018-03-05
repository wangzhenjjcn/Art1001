package com.aladdin.component.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.base.Pager;
import com.aladdin.component.entity.Component;
import com.aladdin.component.mapper.ComponentMapper;
import com.aladdin.component.service.ComponentService;
import com.aladdin.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ComponentServiceImpl implements ComponentService {
	@Autowired
	private ComponentMapper componentMapper;

	@Override
	public List<Component> find(Map<String, Object> params) {

		Example example = new Example(Component.class);
		Criteria cri = example.createCriteria();

		Object parent =  params.get("parent");
		Object type = params.get("type");
		Object keyword = params.get("keyword");
		Object name = params.get("name");

		if (!StringUtils.isEmpty(parent)) {
			cri.andEqualTo("parent", parent);
		}
		if (!StringUtils.isEmpty(type)) {
			cri.andEqualTo("type", type);
		}
		if (!StringUtils.isEmpty(name)) {
			cri.andLike("name", "%" + name + "%");
		}
		if (!StringUtils.isEmpty(type)) {
			cri.andLike("name", "%" + keyword + "%");
		}
		cri.andEqualTo("status", 1);

		List<Component> list = componentMapper.selectByExample(example);
		return list;
	}

	@Override
	public Pager<Component> findByPager(Map<String, Object> params) {

		Example example = new Example(Component.class);

		Criteria cri = example.createCriteria();

		String createdBy = (String) params.get("createdBy");
		String parent = (String) params.get("parent");
		String type = String.valueOf(params.get("type"));

		Integer pageNo = 1;
		Integer pageSize = 10;
		if (params.get("pageNo") != null) {
			pageNo = Integer.parseInt(String.valueOf(params.get("pageNo")));
		}
		if (params.get("pageSize") != null) {
			pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
		}

		if (StringUtils.isNotEmpty(createdBy)) {
			cri.andEqualTo("createdBy", createdBy);
		}
		if (StringUtils.isNotEmpty(parent)) {
			cri.andEqualTo("parent", parent);
		}
		if (StringUtils.isNotEmpty(type)) {
			cri.andEqualTo("type", type);
		}
		cri.andEqualTo("status", 1);
		List<Component> list = componentMapper.selectByExampleAndRowBounds(example,
				new RowBounds((pageNo - 1) * pageSize, pageSize));

		int count = componentMapper.selectCountByExample(example);

		return new Pager<Component>(pageNo, pageSize, count, list);
	}

	@Override
	public Component find(String id) {

		Component c = componentMapper.selectByPrimaryKey(id);
		if (c != null && c.getStatus() == 1) {
			return c;
		}
		return null;
	}

	@Override
	public int save(Component com) {
		return componentMapper.insertSelective(com);
	}

	@Override
	public int update(Component com) {
		return componentMapper.updateByPrimaryKeySelective(com);
	}

	@Override
	public int remove(Component com) {
		com.setStatus(0);
		return componentMapper.updateByPrimaryKeySelective(com);
	}

	@Override
	public List<Component> findPath(Component component) {

		List<Component> components = new ArrayList<Component>();
		boolean flg = true;
		String pid = component.getParent();
		while (flg) {
			Component pComponent = find(pid);
			if (pComponent != null) {
				components.add(pComponent);
				pid = pComponent.getParent();
			} else {
				flg = false;
			}
		}
		return components;
	}

	@Override
	public List<Component> findChildren(Component component) {

		Example ex = new Example(Component.class);

		Criteria cr = ex.createCriteria();

		cr.andEqualTo("parent", component.getId());
		cr.andEqualTo("status", "1");

		List<Component> components = componentMapper.selectByExample(ex);

		return components;
	}

}
