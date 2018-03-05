package com.aladdin.model.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aladdin.model.entity.ModelFunction;
public interface ModelFunctionMapper {
	
	int deleteByPrimaryKey(ModelFunction mf);

	int insert(ModelFunction mf);

	int insertSelective(ModelFunction mf);
	
	List<ModelFunction> selectByModelId(@Param("modelId") String modelId);
}
