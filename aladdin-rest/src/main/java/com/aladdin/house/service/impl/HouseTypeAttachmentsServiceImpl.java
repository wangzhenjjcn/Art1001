package com.aladdin.house.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aladdin.house.entity.Attachment;
import com.aladdin.house.mapper.HouseTypeAttachmentsMapper;
import com.aladdin.house.service.HouseTypeAttachmentsService;
import com.aladdin.utils.BooleanUtils;
@Service
public class HouseTypeAttachmentsServiceImpl implements HouseTypeAttachmentsService {
	
	private HouseTypeAttachmentsMapper houseTypeAttachmentsMapper;
	@Override
	public int add(Attachment attachment) {
		if(attachment==null) 
			return 0;
		if(BooleanUtils.isEmpty(BooleanUtils.OR, //验证必填项
				attachment.getName(),
				attachment.getCreatedBy(),
				attachment.getType(),
				attachment.getUri(),
				attachment.getExt(),
				attachment.getSize()))
			return 0;
		Date date = new Date();
		attachment.setCreatedDate(date);
		attachment.setCreatedDate(date);
		attachment.setStatus(0);
		attachment.setId(UUID.randomUUID().toString().replace("-", ""));
		return houseTypeAttachmentsMapper.insertSelective(attachment);
	}
	@Override
	public int delete(Attachment attachment) {
		if(attachment==null) return 0;
		if(BooleanUtils.isEmpty(attachment.getId())){return 0;}
		return houseTypeAttachmentsMapper.delete(attachment);
	}
	@Override
	public int delete(String id) {
		Attachment attachments = new Attachment();
		attachments.setId(id);
		return delete(attachments);
	}
}
