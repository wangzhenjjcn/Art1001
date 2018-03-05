package com.aladdin.project.service;

import java.util.List;
import java.util.Map;

import com.aladdin.project.entity.ProjectReport;

public interface ProjectReportService {
	
	/**
	 * 添加报表信息
	 * @param filemap
	 * @param projectId
	 * @param packageId
	 * @param UserId
	 * @return
	 */
	List<ProjectReport> create(Map<String, String> filemap,String projectId,String packageId,String UserId);
	
	
	/**
	 * 条件查询列表
	 * @param report
	 * @return
	 */
	List<ProjectReport> selectByCondition(ProjectReport report);
		
	
	/**
	 * 根据id查询可用报表
	 * @param id
	 * @return
	 */
	ProjectReport selectByKey(String id);
	
	/**
	 * 根据报表id修改文件
	 * @param id
	 * @param jsonfile
	 * @return
	 */
	boolean updateJsonfileById(String id,String jsonfile);	
	
	/**
	 * 创建汇总表
	 * @param report
	 * @return
	 */
	public Integer createSummaryReport(ProjectReport report);
	public Integer updateSummaryReport(ProjectReport report);
	
	
	//-------------------------------------------------------------
	/**
	 * 编辑信息
	 * @param id
	 * @return
	 */
	public int updateJson(ProjectReport report,String path, String pname, String pvalue);
	
	/**
	 * 删除信息
	 * @param path
	 * @return
	 */
	public int removeJson(ProjectReport report,String path);
	
	/**
	 * 逻辑删除
	 * @param report
	 * @return
	 */
	public int remove(String id,String userId);
	
	
	public boolean deleteByCondition(ProjectReport report);
	
	
	
}
