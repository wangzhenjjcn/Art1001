package com.aladdin.model.mapper;

import com.aladdin.model.entity.Model;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.aladdin.base.Pager;
import tk.mybatis.mapper.common.Mapper;


public interface ModelMapper extends Mapper<Model> {
	/**条件查询**/
	public List<Model> find(@Param("params") Map<String, Object> paramsMap, @Param("pager") Pager<Model> pager);
	/**查询总条数**/
	public Integer count(@Param("params") Map<String, Object> paramsMap);
	//通过模型主键查询模型及关联表信息
	public Model findSingleModelById(String id);

}