package com.aladdin.house.service;

import com.aladdin.house.entity.Attachment;

public interface HouseTypeAttachmentsService {
	/**
	 * @Title: add 
	 * @Description: TODO(添加附件) 
	 */
	public int add(Attachment attachment);
	/**
	 * @Title: delete 
	 * @Description: TODO(删除附件) 
	 */
	public int delete(Attachment attachment);
	
	/**
	 * @Title: delete 
	 * @Description: TODO(通过id删除附件) 
	 */
	public int delete(String  id);
}
