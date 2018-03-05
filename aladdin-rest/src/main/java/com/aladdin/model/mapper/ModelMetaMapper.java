package com.aladdin.model.mapper;

import java.util.List;
import com.aladdin.model.entity.ModelMeta;

import tk.mybatis.mapper.common.Mapper;

public interface ModelMetaMapper extends Mapper<ModelMeta> {

	public List<ModelMeta> selectMds(String id);
}
