package com.aladdin.project.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aladdin.model.entity.Brand;
import com.aladdin.model.entity.Model;
import com.aladdin.model.mapper.ModelMapper;
import com.aladdin.model.service.BrandService;
import com.aladdin.packages.entity.Packages;
import com.aladdin.packages.mapper.PackagesMapper;
import com.aladdin.project.ProjectReportContants;
import com.aladdin.project.entity.Area;
import com.aladdin.project.entity.Project;
import com.aladdin.project.entity.ProjectReport;
import com.aladdin.project.mapper.AreaMapper;
import com.aladdin.project.mapper.ProjectMapper;
import com.aladdin.project.mapper.ProjectReportMapper;
import com.aladdin.utils.FileUtil;
import com.aladdin.utils.StringUtils;
import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONObject;

@Service
public class ProjectReportServiceImpl implements ProjectReportService {
	
	@Autowired
	private ProjectReportMapper projectReportMapper;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private AreaMapper areaMapper;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BrandService brandService;

	@Override
	public List<ProjectReport> selectByCondition(ProjectReport report) {
		if(report==null){
			report=new ProjectReport();
		}
		report.setStatus(ProjectReportContants.status_ok);
		return projectReportMapper.select(report);
	}

	@Override
	public ProjectReport selectByKey(String id) {
		ProjectReport report=new ProjectReport();
		report.setId(id);
		report.setStatus(ProjectReportContants.status_ok);
		return projectReportMapper.selectOne(report);
	}
	
	

	public ProjectReport findByProidAndName(String projectId,String packageId,String name){
		
		if((StringUtils.isEmpty(projectId) && StringUtils.isEmpty(packageId))||StringUtils.isEmpty(name)){
			return null;
		}
		
		ProjectReport projectReport=new ProjectReport();
		if(StringUtils.isNotEmpty(projectId)){
			projectReport.setProjectId(projectId);
		}
		if(StringUtils.isNotEmpty(packageId)){
			projectReport.setPackageId(packageId);
		}
		
		projectReport.setName(name);
		projectReport.setStatus(ProjectReportContants.status_ok);
		return projectReportMapper.selectOne(projectReport);
	}

	
	@Override
	public List<ProjectReport> create(
			Map<String, String> filemap, String projectId, String packageId,
			String UserId) {
		
		if(StringUtils.isEmpty(projectId) && StringUtils.isEmpty(packageId)){
			return null;
		}
		
		List<ProjectReport> reports = new ArrayList<ProjectReport>();
		
		try {
			//boolean f = true;
			for(String key:filemap.keySet()){
				ProjectReport report=new ProjectReport();
				if(StringUtils.isNotEmpty(projectId)){
					report.setProjectId(projectId);
				}
				if(StringUtils.isNotEmpty(packageId)){
					report.setPackageId(packageId);
				}
				report.setName(key);
				report.setStatus(ProjectReportContants.status_ok);
				
				ProjectReport res=projectReportMapper.selectOne(report);
				if(res!=null){
					//删除已有的项目报表
					res.setModifiedBy(UserId);
					res.setModifiedDate(new Date());
					deleteByCondition(res);
				}
				
				ProjectReport savereport = new ProjectReport();
				if(StringUtils.isNotEmpty(projectId)){
					savereport.setProjectId(projectId);
				}
				if(StringUtils.isNotEmpty(packageId)){
					savereport.setPackageId(packageId);
				}
				
				String jsonfile=filemap.get(key);
				
				if(jsonfile!=null){ 
					if(key.equals("reports_cost")){
						
					//org.json.JSONObject costJson=new JSONObject(filemap.get(key));
						
						org.json.JSONObject costJson=new org.json.JSONObject(filemap.get(key));
						
						
						if(costJson.has("children")){
							org.json.JSONArray array=costJson.getJSONArray("children");
							for(int i=0;i<array.length();i++){
							//for(Object obj:costJson.getJSONArray("children")){
								
								
								//JSONObject child2=new JSONObject(obj.toString());
								org.json.JSONObject child2=array.getJSONObject(i);
								if(child2.has("children")){
									org.json.JSONArray array2=child2.getJSONArray("children");
									for(int j=0;j<array2.length();j++){
									//for(Object obj2:child2.getJSONArray("children")){
										//JSONObject child3=new JSONObject(obj2.toString());
										org.json.JSONObject child3=array2.getJSONObject(j);
										if(child3.has("children")){
											org.json.JSONArray array3=child3.getJSONArray("children");
											for(int k=0;k<array3.length();k++){
											//for(Object obj3:child3.getJSONArray("children")){
											//	JSONObject child4=new JSONObject(obj3.toString());
												org.json.JSONObject child4=array3.getJSONObject(k);
												if(child4.has("product_id")){
													//取模型id
													String brandName="";
													String modelNumber="";
													
													String productId=child4.getString("product_id");
													if(StringUtils.isNotEmpty(productId)){
														Model model=modelMapper.selectByPrimaryKey(productId);
														if(model!=null){
															//品牌
															String brandId=model.getBrand();
															Brand brand=brandService.findBrandById(brandId);
															if(brand != null && brand.getBrand_name()!=null){
																brandName=brand.getBrand_name();
															}
															//型号
															if(model.getModelnumber()!=null){
																modelNumber=model.getModelnumber();
															}
															
														}
													}
													child4.put("brandName", brandName);
													child4.put("modelNumber", modelNumber);
												}
												
											}											
										}																			
									}
								}
							}
						}
						jsonfile=costJson.toString();
					}				
					savereport.setJsonfile(jsonfile);
				}
				
				savereport.setName(key);
				savereport.setCreatedBy(UserId);
				savereport.setCreatedDate(new Date());
				savereport.setStatus(ProjectReportContants.status_ok);

				savereport.setId(StringUtils.getUUID());
				if(projectReportMapper.insertSelective(savereport)==1){
					reports.add(savereport);
				}
			}
			
			//添加汇总报表信息
			ProjectReport sumrep=new ProjectReport();
			sumrep.setCreatedBy(UserId);
			sumrep.setCreatedDate(new Date());
			sumrep.setStatus(ProjectReportContants.status_ok);
			sumrep.setName("reports_summary");
			if(projectId!=null){
				sumrep.setProjectId(projectId);
			}
			if(packageId!=null){
				sumrep.setPackageId(packageId);
			}
			
			createSummaryReport(sumrep);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reports;
	}
	
	
	
	

//	@Override
//	public List<ProjectReport> create(
//			Map<String, String> filemap, String projectId, String packageId,
//			String UserId) {
//		
//		List<ProjectReport> reports = new ArrayList<ProjectReport>();
//		
//		try {
//			boolean f = true;
//			for(String key:filemap.keySet()){
//				ProjectReport report=new ProjectReport();
//				if(key.equals("reports_material_list")){
//					report.setProjectId(packageId);
//					f=false;
//				}else{
//					report.setProjectId(projectId);
//				}
//				report.setName(key);
//				report.setStatus(ProjectReportContants.status_ok);
//				ProjectReport res=projectReportMapper.selectOne(report);
//				if(res!=null){
//					res.setModifiedBy(UserId);
//					res.setModifiedDate(new Date());
//					deleteByCondition(res);
//				}
//				
//				ProjectReport savereport = new ProjectReport();
//				if ("reports_material_list".equals(key)) {
//					savereport.setProjectId(packageId);
//				} else {
//					savereport.setProjectId(projectId);
//				}
//				savereport.setJsonfile(filemap.get(key));
//				savereport.setName(key);
//				savereport.setCreatedBy(UserId);
//				savereport.setCreatedDate(new Date());
//				savereport.setPackageId(packageId);
//				savereport.setId(StringUtils.getUUID());
//				if(projectReportMapper.insertSelective(savereport)==1){
//					reports.add(savereport);
//				}
//			}
//			if(f){
//				ProjectReport summaryReport=new ProjectReport();
//				summaryReport.setProjectId(projectId);
//				summaryReport.setName("reports_summary");
//				summaryReport.setStatus(ProjectReportContants.status_ok);
//				ProjectReport resSummaryReport=projectReportMapper.selectOne(summaryReport);
//				if(resSummaryReport==null){
//					resSummaryReport = new ProjectReport();
//					resSummaryReport.setProjectId(projectId);
//					resSummaryReport.setName("reports_summary");
//					resSummaryReport.setPackageId(packageId);
//					resSummaryReport.setCreatedBy(UserId);
//					resSummaryReport.setCreatedDate(new Date());
//					resSummaryReport.setStatus(ProjectReportContants.status_ok);
//					createSummaryReport(resSummaryReport);
//				}
//			}
//			
//			// 创建施工物料清单报表
//			creatMaterialListReport(projectId, packageId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return reports;
//	}
	
	// 创建施工物料清单报表
	public Integer creatMaterialListReport(String projectId,
			String packageId) {
		int i = 0;
		try {
			ProjectReport projectsReport = findByProidAndName(projectId,packageId, "reports_material_list");
			if (projectsReport != null) {
				ProjectReport oreport = findByProidAndName(projectId,packageId, "reports_material_list");
				if (oreport != null) {
					deleteByCondition(oreport);
				}		
				if (StringUtils.isNotEmpty(projectsReport.getJsonfile())) {
					
					String path=projectsReport.getJsonfile();
					
					String nid=StringUtils.getUUID();
					String npath = ProjectReportContants.baseurl_reports_file+nid;
					FileUtil.copy(new File(path), new File(npath));
					
					ProjectReport report = new ProjectReport();
					report.setProjectId(projectId);
					report.setPackageId(packageId);
					report.setJsonfile(npath);
					report.setName("reports_material_list");
					report.setId(StringUtils.getUUID());
					projectReportMapper.insertSelective(report);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	
	//创建汇报表信息(保存空数据信息)
	@Override
	public Integer createSummaryReport(ProjectReport report) {
		int i = 0;
		try {
			
			
			JSONObject json = new JSONObject();//汇总表内容
			json.put("id", "reports_summary");
			json.put("name", "阿拉丁电商家庭居室装饰装修工程报价汇总表");
			
			// 客户信息
			JSONObject customerJson = new JSONObject();
			json.put("customer", customerJson);
			customerJson.put("name", "");
			customerJson.put("mobile", "");
			customerJson.put("address", "");
			
			// 装饰公司信息
			JSONObject decorationJson = new JSONObject();
			json.put("decoration", decorationJson);
			decorationJson.put("name", "");
			decorationJson.put("mobile", "");
			
			// 设计师信息
			JSONObject designerJson = new JSONObject();
			json.put("designer", designerJson);
			designerJson.put("name", "");
			designerJson.put("mobile", "");
			
			// 设计费用
			JSONObject designJson = new JSONObject();
			json.put("design", designJson);
			designJson.put("amount", "");
			designJson.put("remark", "");
			
			// 管理费用
			JSONObject managementJson = new JSONObject();
			json.put("management", managementJson);
			managementJson.put("amount", "");
			managementJson.put("remark", "");
			
			// 搬运费用
			JSONObject transportJson = new JSONObject();
			json.put("transport", transportJson);
			transportJson.put("amount", "");
			transportJson.put("remark", "");
			
			String projectId=report.getProjectId();
			String packageId=report.getPackageId();
			
			// 套餐信息
			JSONObject packageJson = new JSONObject();
			json.put("package", packageJson);

			double area = 0.0;
			double pkgamount = 0.0;

			ProjectReport reportCost = findByProidAndName(projectId, packageId,"reports_cost");
			if (reportCost != null) {
				JSONObject reportCostJson = getReportJson(reportCost.getJsonfile());
				if (reportCostJson != null) {
					area = reportCostJson.getDouble("house_area");
				}
				report.setPackageId(reportCost.getPackageId());
			}
			packageJson.put("area", area);
			packageJson.put("amount", pkgamount);
			packageJson.put("remark", "");

			// 套餐外主材增项报价
			JSONObject mmaJson = new JSONObject();
			json.put("main_material_additions", mmaJson);
			mmaJson.put("name", "套餐外主材增项报价");
			mmaJson.put("remark", "");
			double mmaamount = 0.0;
			mmaJson.put("amount", mmaamount);

			// 套餐外标准实施增项报价
			JSONObject sbaJson = new JSONObject();
			json.put("std_build_additions", sbaJson);
			sbaJson.put("name", "套餐外标准实施增项报价");
			sbaJson.put("remark", "");
			double sbaamount = 0.0;
			sbaJson.put("amount", sbaamount);
			
			// 套餐外施工增项明细报价表
			JSONObject cbaJson = new JSONObject();
			json.put("customer_build_additions", cbaJson);
			cbaJson.put("name", "套餐外施工增项明细报价表");
			cbaJson.put("remark", "");

			double cbaamount = 0.0;
			cbaJson.put("amount", cbaamount);
			
			String jsonfile=json.toString();

			ProjectReport oReport = findByProidAndName(projectId, packageId,"reports_summary");
			if(oReport!=null){
				//如果已经存在汇总报表,修改
				oReport.setModifiedBy(report.getCreatedBy());
				oReport.setModifiedDate(new Date());
				
				oReport.setJsonfile(jsonfile);
				i=projectReportMapper.updateByPrimaryKeySelective(oReport);
			}else{
				//如果不存在汇总报表,添加
				report.setJsonfile(jsonfile);
				report.setStatus(ProjectReportContants.status_ok);
				report.setId(StringUtils.getUUID());
				report.setName("reports_summary");
				
				i=projectReportMapper.insertSelective(report);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public JSONObject getReportJson(String fileurl) {
		try {
			
			if(StringUtils.isNotEmpty(fileurl)){
				JSONObject jsonObject=new JSONObject(fileurl);
				return jsonObject;
			}

//				File jsonFile = new File(fileurl);
//
//				if (jsonFile != null && jsonFile.exists()) {
//					StringBuilder jsonBuilder = new StringBuilder();
//					BufferedReader reader = new BufferedReader(
//							new InputStreamReader(
//									new FileInputStream(jsonFile), "UTF-8"));
//					String str = null;
//					while ((str = reader.readLine()) != null) {
//						jsonBuilder.append(str);
//					}
//					reader.close();
//					JSONObject json = new JSONObject(jsonBuilder.toString());
//					return json;
//				}

		} catch (Exception e) {
			e.printStackTrace();;
		}
		return null;
	}

	
	public boolean deleteByCondition(ProjectReport report){
		report.setStatus(ProjectReportContants.status_no);
		if(projectReportMapper.updateByPrimaryKeySelective(report)==1){
			return true;
		}
		return false;
	}

	@Override
	public int updateJson(ProjectReport report,String path, String pname, String pvaue) {
		int code = 0;
		try {
			String[] paths = path.split(";");
			JSONObject newJson = new JSONObject();
			String name=report.getName();
			String jsonfile=report.getJsonfile();
			JSONObject jsonObject = getReportJson(jsonfile);
			
			if ("reports_main_material_additions".equals(name)) {
				
				newJson.put("id", jsonObject.get("id"));
				newJson.put("name", jsonObject.get("name"));
				
				JSONArray newchildrenArray1 = new JSONArray();
				newJson.put("children", newchildrenArray1);
				for (Object children1 : jsonObject.getJSONArray("children")) {
					JSONObject childrenJsonObject1 = new JSONObject(
							children1.toString());
					JSONObject newChildrenJsonObject1 = new JSONObject();
					newchildrenArray1.add(newChildrenJsonObject1);
					newChildrenJsonObject1.put("zone",
							childrenJsonObject1.get("zone"));
					JSONArray newchildrenArray2 = new JSONArray();
					newChildrenJsonObject1.put("children", newchildrenArray2);
					for (Object children2 : childrenJsonObject1
							.getJSONArray("children")) {
						JSONObject childrenJsonObject2 = new JSONObject(
								children2.toString());
						JSONObject newChildrenJsonObject2 = new JSONObject();
						newchildrenArray2.add(newChildrenJsonObject2);
						if ("quantity".equals(pname)) {
							if (paths[0]
									.equals(childrenJsonObject1.get("zone"))
									&& paths[1].equals(childrenJsonObject2
											.get("id"))) {
								newChildrenJsonObject2.put("quantity", pvaue);
							} else {
								newChildrenJsonObject2.put("quantity",
										childrenJsonObject2
												.getString("quantity"));
							}
						} else {
							newChildrenJsonObject2.put("quantity",
									childrenJsonObject2.getString("quantity"));
						}
						newChildrenJsonObject2.put("id",
								childrenJsonObject2.getString("id"));
						newChildrenJsonObject2.put("remark",
								childrenJsonObject2.getString("remark"));
					}
				}
			} else if ("reports_summary".equals(name)) {
				
				newJson.put("id", jsonObject.get("id"));
				newJson.put("name", jsonObject.get("name"));

				// 客户信息
				JSONObject newcustomerJson = new JSONObject();
				JSONObject customerJson = jsonObject.getJSONObject("customer");
				newJson.put("customer", newcustomerJson);
				newcustomerJson.put("name", customerJson.get("name"));
				newcustomerJson.put("mobile", customerJson.get("mobile"));
				newcustomerJson.put("address", customerJson.get("address"));

				// 装饰公司信息
				JSONObject newdecorationJson = new JSONObject();
				JSONObject decorationJson = jsonObject
						.getJSONObject("decoration");
				newJson.put("decoration", newdecorationJson);
				newdecorationJson.put("name", decorationJson.get("name"));
				newdecorationJson.put("mobile", decorationJson.get("mobile"));
				// 设计师信息
				JSONObject newdesignerJson = new JSONObject();
				JSONObject designerJson = jsonObject.getJSONObject("designer");
				newJson.put("designer", newdesignerJson);
				newdesignerJson.put("name", designerJson.get("name"));
				newdesignerJson.put("mobile", designerJson.get("mobile"));
				// 设计费用
				JSONObject newdesignJson = new JSONObject();
				JSONObject designJson = jsonObject.getJSONObject("design");
				newJson.put("design", newdesignJson);
				if ("design".equals(paths[0]) && "amount".equals(pname)) {
					newdesignJson.put("amount", pvaue);
				} else {
					newdesignJson.put("amount", designJson.get("amount"));
				}
				if ("design".equals(paths[0]) && "remark".equals(pname)) {
					newdesignJson.put("remark", pvaue);
				} else {
					newdesignJson.put("remark", designJson.get("remark"));
				}
				// 管理费用
				JSONObject newmanagementJson = new JSONObject();
				JSONObject managementJson = jsonObject
						.getJSONObject("management");
				newJson.put("management", newmanagementJson);
				if ("management".equals(paths[0]) && "amount".equals(pname)) {
					newmanagementJson.put("amount", pvaue);
				} else {
					newmanagementJson.put("amount",
							managementJson.get("amount"));
				}
				if ("management".equals(paths[0]) && "remark".equals(pname)) {
					newmanagementJson.put("remark", pvaue);
				} else {
					newmanagementJson.put("remark",
							managementJson.get("remark"));
				}

				// 搬运费用
				JSONObject newtransportJson = new JSONObject();
				JSONObject transportJson = jsonObject
						.getJSONObject("transport");
				newJson.put("transport", newtransportJson);
				if ("transport".equals(paths[0]) && "amount".equals(pname)) {
					newtransportJson.put("amount", pvaue);
				} else {
					newtransportJson.put("amount", transportJson.get("amount"));
				}
				if ("transport".equals(paths[0]) && "remark".equals(pname)) {
					newtransportJson.put("remark", pvaue);
				} else {
					newtransportJson.put("remark", transportJson.get("remark"));
				}

				// 套餐信息
				JSONObject newpackagetJson = new JSONObject();
				JSONObject packageJson = jsonObject.getJSONObject("package");
				newJson.put("package", newpackagetJson);
				newpackagetJson.put("area", packageJson.get("area"));
				newpackagetJson.put("amount", packageJson.get("amount"));
				newpackagetJson.put("remark", packageJson.get("remark"));

				// 套餐外主材增项报价
				JSONObject newMMAJson = new JSONObject();
				JSONObject MMAJson = jsonObject
						.getJSONObject("main_material_additions");
				newJson.put("main_material_additions", newMMAJson);
				newMMAJson.put("name", MMAJson.get("name"));
				newMMAJson.put("remark", MMAJson.get("remark"));
				newMMAJson.put("amount", MMAJson.get("amount"));

				// 套餐外标准实施增项报价
				JSONObject newSBAJson = new JSONObject();
				JSONObject SBAJson = jsonObject
						.getJSONObject("std_build_additions");
				newJson.put("std_build_additions", newSBAJson);
				newSBAJson.put("name", SBAJson.get("name"));
				newSBAJson.put("remark", SBAJson.get("remark"));
				newSBAJson.put("amount", SBAJson.get("amount"));

				// 套餐外施工增项明细报价表
				JSONObject newCBAJson = new JSONObject();
				JSONObject CBAJson = jsonObject
						.getJSONObject("customer_build_additions");
				newJson.put("customer_build_additions", newCBAJson);
				newCBAJson.put("name", CBAJson.get("name"));
				newCBAJson.put("remark", CBAJson.get("remark"));
				newCBAJson.put("amount", CBAJson.get("amount"));

			} else if ("reports_std_build_additions".equals(name)) {
				
				newJson.put("id", jsonObject.get("id"));
				newJson.put("name", jsonObject.get("name"));
				JSONArray newchildrenArray = new JSONArray();
				newJson.put("children", newchildrenArray);
				for (Object children : jsonObject.getJSONArray("children")) {
					JSONObject childrenJsonObject = new JSONObject(
							children.toString());
					JSONObject newchildrenJsonObject = new JSONObject();
					newchildrenArray.add(newchildrenJsonObject);
					if ("zone".equals(pname)) {
						if (paths[0].equals(childrenJsonObject.get("name"))) {
							newchildrenJsonObject.put("zone", pvaue);
							newchildrenJsonObject.put(
									"amount",
									Double.valueOf(pvaue)
											* Double.valueOf(childrenJsonObject
													.getString("price")));
						} else {
							newchildrenJsonObject.put("zone",
									childrenJsonObject.getString("zone"));
						}
					} else {
						newchildrenJsonObject.put("zone",
								childrenJsonObject.getString("zone"));
					}
					newchildrenJsonObject.put("name",
							childrenJsonObject.getString("name"));
					newchildrenJsonObject.put("unit",
							childrenJsonObject.getString("unit"));
					newchildrenJsonObject.put("price",
							childrenJsonObject.getString("price"));
					newchildrenJsonObject.put("amount",
							newchildrenJsonObject.getDouble("price")
									* newchildrenJsonObject.getDouble("zone"));
					newchildrenJsonObject.put("remark",
							childrenJsonObject.getString("remark"));
				}

			} else if ("reports_customer_build_additions".equals(name)) {

				newJson.put("id", jsonObject.get("id"));
				newJson.put("name", jsonObject.get("name"));
				JSONArray newchildrenArray1 = new JSONArray();
				newJson.put("children", newchildrenArray1);
				for (Object children1 : jsonObject.getJSONArray("children")) {
					JSONObject childrenJsonObject1 = new JSONObject(
							children1.toString());
					JSONObject newchildrenJsonObject1 = new JSONObject();
					newchildrenArray1.add(newchildrenJsonObject1);
					newchildrenJsonObject1.put("zone",
							childrenJsonObject1.get("zone"));
					JSONArray newchildrenArray2 = new JSONArray();
					newchildrenJsonObject1.put("children", newchildrenArray2);
					Double totalAmount = 0.00;
					if (childrenJsonObject1.has("children")) {
						for (Object children2 : childrenJsonObject1
								.getJSONArray("children")) {
							JSONObject childrenJsonObject2 = new JSONObject(
									children2.toString());
							JSONObject newchildrenJsonObject2 = new JSONObject();
							newchildrenArray2.add(newchildrenJsonObject2);
							if (paths[0]
									.equals(childrenJsonObject1.get("zone"))
									&& paths[1].equals(childrenJsonObject2
											.get("name"))) {
								if ("unit".equals(pname)) {
									newchildrenJsonObject2.put("unit", pvaue);
								} else {
									newchildrenJsonObject2.put("unit",
											childrenJsonObject2
													.getString("unit"));
								}
								if ("quantity".equals(pname)) {
									newchildrenJsonObject2.put("quantity",
											pvaue);
								} else {
									newchildrenJsonObject2.put("quantity",
											childrenJsonObject2
													.getString("quantity"));
								}
								if ("price".equals(pname)) {
									newchildrenJsonObject2.put("price", pvaue);
								} else {
									newchildrenJsonObject2.put("price",
											childrenJsonObject2
													.getString("price"));
								}

								if ("remark".equals(pname)) {
									newchildrenJsonObject2.put("remark", pvaue);
								} else {
									newchildrenJsonObject2.put("remark",
											childrenJsonObject2
													.getString("remark"));
								}
								if ("name".equals(pname)) {
									newchildrenJsonObject2.put("name", pvaue);
								} else {
									newchildrenJsonObject2.put("name",
											childrenJsonObject2
													.getString("name"));
								}

							} else {
								newchildrenJsonObject2.put("unit",
										childrenJsonObject2.getString("unit"));
								newchildrenJsonObject2.put("quantity",
										childrenJsonObject2
												.getString("quantity"));
								newchildrenJsonObject2.put("price",
										childrenJsonObject2.getString("price"));

								newchildrenJsonObject2
										.put("remark", childrenJsonObject2
												.getString("remark"));
								newchildrenJsonObject2.put("name",
										childrenJsonObject2.getString("name"));
							}

							newchildrenJsonObject2.put(
									"amount",
									newchildrenJsonObject2.getDouble("price")
											* newchildrenJsonObject2
													.getDouble("quantity"));

							totalAmount = totalAmount
									+ newchildrenJsonObject2
											.getDouble("amount");
						}
					}
					if ("name".equals(pname)
							&& paths[0].equals(newchildrenJsonObject1
									.get("zone")) && "new".equals(paths[1])) {
						JSONObject newchildrenJsonObject2 = new JSONObject();
						newchildrenJsonObject2.put("name", pvaue);
						newchildrenJsonObject2.put("unit", "");
						newchildrenJsonObject2.put("quantity", "0");
						newchildrenJsonObject2.put("price", "0");
						newchildrenJsonObject2.put("amount", "0");
						newchildrenJsonObject2.put("remark", "");
						newchildrenArray2.add(newchildrenJsonObject2);
					}
					newchildrenJsonObject1.put("total", totalAmount);
				}
			} else if ("reports_material_list".equals(name)) {

				newJson.put("id", jsonObject.get("id"));
				newJson.put("name", jsonObject.get("name"));
				JSONArray newchildrenArray1 = new JSONArray();
				newJson.put("children", newchildrenArray1);
				for (Object children1 : jsonObject.getJSONArray("children")) {
					JSONObject childrenJsonObject1 = new JSONObject(
							children1.toString());
					JSONObject newchildrenJsonObject1 = new JSONObject();
					newchildrenArray1.add(newchildrenJsonObject1);
					newchildrenJsonObject1.put("zone",
							childrenJsonObject1.get("zone"));
					JSONArray newchildrenArray2 = new JSONArray();
					newchildrenJsonObject1.put("children", newchildrenArray2);
					if (childrenJsonObject1.has("children")) {

						for (Object children2 : childrenJsonObject1
								.getJSONArray("children")) {
							JSONObject childrenJsonObject2 = new JSONObject(
									children2.toString());
							JSONObject newchildrenJsonObject2 = new JSONObject();
							newchildrenArray2.add(newchildrenJsonObject2);
							if (paths[0]
									.equals(childrenJsonObject1.get("zone"))
									&& paths[1].equals(childrenJsonObject2
											.get("name"))) {
								if ("name".equals(pname)) {
									newchildrenJsonObject2.put("name", pvaue);
								} else {
									newchildrenJsonObject2.put("name",
											childrenJsonObject2
													.getString("name"));
								}
								if ("position".equals(pname)) {
									newchildrenJsonObject2.put("position",
											pvaue);
								} else {
									newchildrenJsonObject2.put("position",
											childrenJsonObject2
													.getString("position"));
								}
								if ("spec".equals(pname)) {
									newchildrenJsonObject2.put("spec", pvaue);
								} else {
									newchildrenJsonObject2.put("spec",
											childrenJsonObject2
													.getString("spec"));
								}
								if ("model".equals(pname)) {
									newchildrenJsonObject2.put("model", pvaue);
								} else {
									newchildrenJsonObject2.put("model",
											childrenJsonObject2
													.getString("model"));
								}
								if ("unit".equals(pname)) {
									newchildrenJsonObject2.put("unit", pvaue);
								} else {
									newchildrenJsonObject2.put("unit",
											childrenJsonObject2
													.getString("unit"));
								}
								if ("brands".equals(pname)) {
									newchildrenJsonObject2.put("brands", pvaue);
								} else {
									newchildrenJsonObject2.put("brands",
											childrenJsonObject2
													.getString("brands"));
								}
								if ("purchase_places".equals(pname)) {
									newchildrenJsonObject2.put(
											"purchase_places", pvaue);
								} else {
									newchildrenJsonObject2
											.put("purchase_places",
													childrenJsonObject2
															.getString("purchase_places"));
								}
								if ("purchase_type".equals(pname)) {
									newchildrenJsonObject2.put("purchase_type",
											pvaue);
								} else {
									newchildrenJsonObject2
											.put("purchase_type",
													childrenJsonObject2
															.getString("purchase_type"));
								}
							} else {
								newchildrenJsonObject2.put("name",
										childrenJsonObject2.getString("name"));
								newchildrenJsonObject2.put("position",
										childrenJsonObject2
												.getString("position"));
								newchildrenJsonObject2.put("spec",
										childrenJsonObject2.getString("spec"));
								newchildrenJsonObject2.put("model",
										childrenJsonObject2.getString("model"));
								newchildrenJsonObject2.put("unit",
										childrenJsonObject2.getString("unit"));
								newchildrenJsonObject2
										.put("brands", childrenJsonObject2
												.getString("brands"));
								newchildrenJsonObject2.put("purchase_places",
										childrenJsonObject2
												.getString("purchase_places"));
								newchildrenJsonObject2.put("purchase_type",
										childrenJsonObject2
												.getString("purchase_type"));
							}
						}
					}
					if ("name".equals(pname)
							&& paths[0].equals(childrenJsonObject1.get("zone"))
							&& "new".equals(paths[1])) {
						JSONObject newchildrenJsonObject2 = new JSONObject();
						newchildrenJsonObject2.put("name", pvaue);
						newchildrenJsonObject2.put("position", "");
						newchildrenJsonObject2.put("spec", "");
						newchildrenJsonObject2.put("model", "");
						newchildrenJsonObject2.put("unit", "");
						newchildrenJsonObject2.put("brands", "");
						newchildrenJsonObject2.put("purchase_places", "");
						newchildrenJsonObject2.put("purchase_type", "");
						newchildrenArray2.add(newchildrenJsonObject2);
					}
				}
			} else if ("reports_cost".equals(name)) {

				newJson.put("id", jsonObject.get("id"));
				newJson.put("name", jsonObject.get("name"));
				newJson.put("house_area", jsonObject.get("house_area"));
				if (jsonObject.has("children")) {
					JSONArray childrenArray1 = new JSONArray();
					newJson.put("children", childrenArray1);
					for (Object children1 : jsonObject.getJSONArray("children")) {
						JSONObject childrenobj1 = new JSONObject(
								children1.toString());
						JSONObject newchildrenobj1 = new JSONObject();
						childrenArray1.add(newchildrenobj1);
						newchildrenobj1.put("zone", childrenobj1.get("zone"));
						if (childrenobj1.has("children")) {
							JSONArray childrenArray2 = new JSONArray();
							newchildrenobj1.put("children", childrenArray2);
							for (Object children2 : childrenobj1
									.getJSONArray("children")) {
								JSONObject childrenobj2 = new JSONObject(
										children2.toString());
								JSONObject newchildrenobj2 = new JSONObject();
								childrenArray2.add(newchildrenobj2);
								newchildrenobj2.put("name",
										childrenobj2.get("name"));
								if (childrenobj2.has("children")) {
									JSONArray childrenArray3 = new JSONArray();
									newchildrenobj2.put("children",
											childrenArray3);
									for (Object children3 : childrenobj2
											.getJSONArray("children")) {
										JSONObject childrenobj3 = new JSONObject(
												children3.toString());
										JSONObject newchildrenobj3 = new JSONObject();
										childrenArray3.add(newchildrenobj3);
										newchildrenobj3.put("product_id",
												childrenobj3.get("product_id"));
										newchildrenobj3.put("product_amount",
												childrenobj3
														.get("product_amount"));
										newchildrenobj3
												.put("transport_expenses",
														childrenobj3
																.get("transport_expenses"));
										newchildrenobj3
												.put("install_expenses",
														childrenobj3
																.get("install_expenses"));
										newchildrenobj3.put("wastage_rate",
												childrenobj3
														.get("wastage_rate"));

										if (childrenobj3.has("children")) {
											JSONArray childrenArray4 = new JSONArray();
											newchildrenobj3.put("children",
													childrenArray4);
											for (Object children4 : childrenobj3
													.getJSONArray("children")) {
												JSONObject childrenobj4 = new JSONObject(
														children4.toString());
												JSONObject newchildrenobj4 = new JSONObject();
												childrenArray4
														.add(newchildrenobj4);
												if (childrenobj4
														.has("children")) {
													JSONArray childrenArray5 = new JSONArray();
													newchildrenobj4.put(
															"children",
															childrenArray5);
													newchildrenobj4
															.put("name",
																	childrenobj4
																			.get("name"));
													for (Object children5 : childrenobj4
															.getJSONArray("children")) {
														JSONObject childrenobj5 = new JSONObject(
																children5
																		.toString());
														JSONObject newchildrenobj5 = new JSONObject();
														childrenArray5
																.add(newchildrenobj5);

														newchildrenobj5
																.put("name",
																		childrenobj5
																				.get("name"));
														newchildrenobj5
																.put("workload",
																		childrenobj5
																				.get("workload"));
														newchildrenobj5
																.put("unit",
																		childrenobj5
																				.get("unit"));
														newchildrenobj5
																.put("unitprice_man",
																		childrenobj5
																				.get("unitprice_man"));
														newchildrenobj5
																.put("unitprice_material",
																		childrenobj5
																				.get("unitprice_material"));
														newchildrenobj5
																.put("amount",
																		childrenobj5
																				.get("amount"));
														if (paths.length == 5
																&& "remark"
																		.equals(pname)
																&& paths[0]
																		.equals(childrenobj1
																				.get("zone"))
																&& paths[1]
																		.equals(childrenobj2
																				.get("name"))
																&& paths[2]
																		.equals(childrenobj3
																				.get("product_id"))
																&& paths[3]
																		.equals(childrenobj4
																				.get("name"))
																&& paths[4]
																		.equals(childrenobj5
																				.get("name"))) {
															newchildrenobj5
																	.put("remark",
																			pvaue);
														} else {
															newchildrenobj5
																	.put("remark",
																			childrenobj5
																					.get("remark"));
														}
													}
												} else {
													newchildrenobj4
															.put("name",
																	childrenobj4
																			.get("name"));
													newchildrenobj4
															.put("workload",
																	childrenobj4
																			.get("workload"));
													newchildrenobj4
															.put("unit",
																	childrenobj4
																			.get("unit"));
													newchildrenobj4
															.put("unitprice_man",
																	childrenobj4
																			.get("unitprice_man"));
													newchildrenobj4
															.put("unitprice_material",
																	childrenobj4
																			.get("unitprice_material"));
													newchildrenobj4
															.put("amount",
																	childrenobj4
																			.get("amount"));
													if (paths.length == 4
															&& "remark"
																	.equals(pname)
															&& paths[0]
																	.equals(childrenobj1
																			.get("zone"))
															&& paths[1]
																	.equals(childrenobj2
																			.get("name"))
															&& paths[2]
																	.equals(childrenobj3
																			.get("product_id"))
															&& paths[3]
																	.equals(childrenobj4
																			.get("name"))) {
														newchildrenobj4
																.put("remark",
																		pvaue);
													} else {
														newchildrenobj4
																.put("remark",
																		childrenobj4
																				.get("remark"));
													}
												}
											}
										}
									}
								}

							}
						}
					}
				}
			}
			if (newJson != null) {
				String json_object_url=ProjectReportContants.baseurl_reports_file+StringUtils.getUUID();
				File jsonFile = new File(json_object_url);
				if (jsonFile != null && jsonFile.exists()) {
					BufferedWriter writer = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(
									jsonFile), "UTF-8"));
					writer.write(newJson.toString());
					writer.flush();
					writer.close();
				}
				code = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
	@Override
	public int removeJson(ProjectReport report,String path) {
		int code = 0;
		try {
			String[] paths = path.split(";");
			String name=report.getName();
			JSONObject newJson = null;
			if ("reports_customer_build_additions".equals(name)) {
				newJson = new JSONObject();
				JSONObject jsonObject = getReportJson(report.getJsonfile());
				newJson.put("id", jsonObject.get("id"));
				newJson.put("name", jsonObject.get("name"));
				JSONArray childrenArray1 = new JSONArray();
				newJson.put("children", childrenArray1);
				
				for (Object children1 : jsonObject.getJSONArray("children")) {
					JSONObject childrenobj1 = new JSONObject(
							children1.toString());
					JSONObject newchildrenobj1 = new JSONObject();
					childrenArray1.add(newchildrenobj1);
					newchildrenobj1.put("zone", childrenobj1.get("zone"));
					JSONArray childrenArray2 = new JSONArray();
					newchildrenobj1.put("children", childrenArray2);
					Double totalAmount = 0.00;
					for (Object children2 : childrenobj1
							.getJSONArray("children")) {
						JSONObject childrenobj2 = new JSONObject(
								children2.toString());
						if (!(paths[0].equals(childrenobj1.get("zone")) && paths[1]
								.equals(childrenobj2.get("name")))) {
							JSONObject newchildrenobj2 = new JSONObject();
							childrenArray2.add(newchildrenobj2);
							newchildrenobj2.put("unit",
									childrenobj2.getString("unit"));
							newchildrenobj2.put("quantity",
									childrenobj2.getString("quantity"));
							newchildrenobj2.put("price",
									childrenobj2.getString("price"));
							newchildrenobj2.put("amount",
									childrenobj2.getString("amount"));
							newchildrenobj2.put("remark",
									childrenobj2.getString("remark"));
							newchildrenobj2.put("name",
									childrenobj2.getString("name"));
							newchildrenobj2.put(
									"amount",
									newchildrenobj2.getDouble("quantity")
											* newchildrenobj2
													.getDouble("price"));
							totalAmount = totalAmount
									+ newchildrenobj2.getDouble("amount");
						}
					}
					newchildrenobj1.put("total", totalAmount);
				}
			} else if ("reports_material_list".equals(name)) {
				newJson = new JSONObject();
				JSONObject jsonObject = getReportJson(report.getJsonfile());
				newJson.put("id", jsonObject.get("id"));
				newJson.put("name", jsonObject.get("name"));
				JSONArray childrenArray1 = new JSONArray();
				newJson.put("children", childrenArray1);
				
				for (Object children1 : jsonObject.getJSONArray("children")) {
					JSONObject childrenobj1 = new JSONObject(
							children1.toString());
					JSONObject newchildrenobj1 = new JSONObject();
					childrenArray1.add(newchildrenobj1);
					newchildrenobj1.put("zone", childrenobj1.get("zone"));
					JSONArray childrenArray2 = new JSONArray();
					newchildrenobj1.put("children", childrenArray2);
					for (Object children2 : childrenobj1
							.getJSONArray("children")) {
						JSONObject childrenobj2 = new JSONObject(
								children2.toString());
						if (!(paths[0].equals(childrenobj1.get("zone")) && paths[1]
								.equals(childrenobj2.get("name")))) {
							JSONObject newchildrenobj2 = new JSONObject();
							childrenArray2.add(newchildrenobj2);
							newchildrenobj2.put("name",
									childrenobj2.getString("name"));
							newchildrenobj2.put("position",
									childrenobj2.getString("position"));
							newchildrenobj2.put("spec",
									childrenobj2.getString("spec"));
							newchildrenobj2.put("model",
									childrenobj2.getString("model"));
							newchildrenobj2.put("unit",
									childrenobj2.getString("unit"));
							newchildrenobj2.put("brands",
									childrenobj2.getString("brands"));
							newchildrenobj2.put("purchase_places",
									childrenobj2.getString("purchase_places"));
							newchildrenobj2.put("purchase_type",
									childrenobj2.getString("purchase_type"));
						}
					}
				}
			}
			if (newJson != null) {
				File jsonFile = new File(report.getJsonfile());
				if (jsonFile != null && jsonFile.exists()) {
					BufferedWriter writer = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(
									jsonFile), "UTF-8"));
					writer.write(newJson.toString());
					writer.flush();
					writer.close();
				}
				code = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
	@Override
	public int remove(String id,String userId){
		ProjectReport res=projectReportMapper.selectByPrimaryKey(id);
		if(res==null){
			throw new RuntimeException("id为"+id+"的报表不存在");
		}
		ProjectReport report=new ProjectReport();
		report.setModifiedDate(new Date());
		report.setModifiedBy(userId);
		report.setStatus(ProjectReportContants.status_no);
		return projectReportMapper.updateByPrimaryKey(report);
	}
	
	@Autowired
	private PackagesMapper packagesMapper;
	
	/**
	 * 修改汇总表信息
	 */
	@Override
	public Integer updateSummaryReport(ProjectReport report){
		int i = 0;
		try {
			String projectId = report.getProjectId();
			String packageid=report.getPackageId();
			
			JSONObject jsonObject = getReportJson(report.getJsonfile());
			JSONObject newjsonObject = new JSONObject();
			newjsonObject.put("id", "reports_summary");
			newjsonObject.put("name", "阿拉丁电商家庭居室装饰装修工程报价汇总表");
			
			// 客户信息
			JSONObject customerJson = new JSONObject();
			newjsonObject.put("customer", customerJson);
			
			customerJson.put("name", "");
			customerJson.put("mobile", "");
			customerJson.put("address", "");
			
			if(StringUtils.isNotEmpty(projectId)){
				Project project = projectMapper.selectByPrimaryKey(projectId);
				
				if (project != null) {
					customerJson.put("name", project.getCname()==null?"":project.getCname());
					customerJson.put("mobile", project.getPhone()==null?"":project.getPhone());
					String proadd=project.getAddress()==null?"":project.getAddress();
					if(StringUtils.isNotEmpty(project.getLocation())){
						Area area=areaMapper.selectByPrimaryKey(project.getLocation());
						if (area != null && area.getName() !=null) {
							customerJson.put("address",
									area.getName() + proadd);
						} else {
							customerJson.put("address", proadd);
						}						
					}

				}				
			}
			if(StringUtils.isNotEmpty(packageid)){
				Packages packages=packagesMapper.selectByPrimaryKey(packageid);
				if(packages!=null){
					customerJson.put("name", packages.getName()==null?"":packages.getName());
				}
			}
			
			// 装饰公司信息
			JSONObject decorationJson = new JSONObject();
			newjsonObject.put("decoration", decorationJson);
			decorationJson.put("name", "");
			decorationJson.put("mobile", "");
			// 设计师信息
			JSONObject designerJson = new JSONObject();
			newjsonObject.put("designer", designerJson);
			designerJson.put("name", "");
			designerJson.put("mobile", "");
			// 设计费用
			JSONObject newdesignJson = new JSONObject();
			JSONObject designJson = jsonObject.getJSONObject("design");
			newjsonObject.put("design", newdesignJson);
			newdesignJson.put("amount", designJson.get("amount"));
			newdesignJson.put("remark", designJson.get("remark"));
			// 管理费用
			JSONObject newmanagementJson = new JSONObject();
			JSONObject managementJson = jsonObject.getJSONObject("management");
			newjsonObject.put("management", newmanagementJson);
			newmanagementJson.put("amount", managementJson.get("amount"));
			newmanagementJson.put("remark", managementJson.get("remark"));

			// 搬运费用
			JSONObject newtransportJson = new JSONObject();
			JSONObject transportJson = jsonObject.getJSONObject("transport");
			newjsonObject.put("transport", newtransportJson);
			newjsonObject.put("transport", newtransportJson);
			newtransportJson.put("amount", transportJson.get("amount"));
			newtransportJson.put("remark", transportJson.get("remark"));
			// 套餐信息
			JSONObject packageJson = new JSONObject();
			newjsonObject.put("package", packageJson);

			double area = 0.0;
			double pkgamount = 0.0;			
			ProjectReport reportCost = findByProidAndName(projectId, packageid,"reports_cost");
			if (reportCost != null) {
				JSONObject reportCostJson = getReportJson(reportCost.getJsonfile());
				if (reportCostJson != null) {
					area = reportCostJson.getDouble("house_area");
				}
				//report.setPackageId(reportCost.getPackageId());
			}
			packageJson.put("area", area);
			packageJson.put("amount", pkgamount);
			packageJson.put("remark", "");

			// 套餐外主材增项报价
			JSONObject mmaJson = new JSONObject();
			newjsonObject.put("main_material_additions", mmaJson);
			mmaJson.put("name", "套餐外主材增项报价");
			mmaJson.put("remark", "");

			double mmaamount = 0.0;
			ProjectReport reportMMA = findByProidAndName(projectId, packageid,"reports_main_material_additions");
			if (reportMMA != null) {
				JSONObject reportMMAJson = getReportJson(reportMMA.getJsonfile());
				if (reportMMAJson != null) {
					if (reportMMAJson.has("children")) {
						for (Object children1 : reportMMAJson
								.getJSONArray("children")) {
							JSONObject childrenobj1 = new JSONObject(
									children1.toString());
							if (childrenobj1.has("children")) {
								for (Object children2 : childrenobj1
										.getJSONArray("children")) {
									JSONObject childrenobj2 = new JSONObject(
											children2.toString());
									if (childrenobj2.has("amount")
											&& StringUtils
													.isNumeric(childrenobj2
															.getString("amount"))) {
										mmaamount = mmaamount
												+ childrenobj2
														.getDouble("amount");
									}
								}
							}
						}
					}
				}
			}
			mmaJson.put("amount", mmaamount);

			// 套餐外标准实施增项报价
			JSONObject sbaJson = new JSONObject();
			newjsonObject.put("std_build_additions", sbaJson);
			sbaJson.put("name", "套餐外标准实施增项报价");
			sbaJson.put("remark", "");

			double sbaamount = 0.0;
			ProjectReport reportSBA = findByProidAndName(projectId,packageid, "reports_std_build_additions");
			if (reportSBA != null) {
				JSONObject reportSBAJson = getReportJson(reportSBA.getJsonfile());
				if (reportSBAJson != null) {
					if (reportSBAJson.has("children")) {
						for (Object children1 : reportSBAJson
								.getJSONArray("children")) {
							JSONObject childrenobj1 = new JSONObject(
									children1.toString());
							sbaamount = sbaamount
									+ childrenobj1.getDouble("amount");
						}
					}
				}
			}
			sbaJson.put("amount", sbaamount);
			// 套餐外施工增项明细报价表
			JSONObject cbaJson = new JSONObject();
			newjsonObject.put("customer_build_additions", cbaJson);
			cbaJson.put("name", "套餐外施工增项明细报价表");
			cbaJson.put("remark", "");

			double cbaamount = 0.0;
			ProjectReport reportCBA = findByProidAndName(projectId,packageid, "reports_customer_build_additions");
			if (reportCBA != null) {
				JSONObject reportCBAJson = getReportJson(reportCBA.getJsonfile());
				if (reportCBAJson != null) {
					if (reportCBAJson.has("children")) {
						for (Object children1 : reportCBAJson
								.getJSONArray("children")) {
							JSONObject childrenobj1 = new JSONObject(
									children1.toString());
							if (childrenobj1.has("children")) {
								for (Object children2 : childrenobj1
										.getJSONArray("children")) {
									JSONObject childrenobj2 = new JSONObject(
											children2.toString());
									if (childrenobj2.has("amount")
											&& StringUtils
													.isNumeric(childrenobj2
															.getString("amount"))) {
										cbaamount = cbaamount
												+ childrenobj2
														.getDouble("amount");
									}

								}
							}

						}
					}
				}
			}
			cbaJson.put("amount", cbaamount);
			
			String amturl=newjsonObject.toString();
			
//			String id=StringUtils.getUUID();
//			String amturl=ProjectReportContants.baseurl_reports_file+id;
//			
//			File file = new File(amturl);
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
//					new FileOutputStream(file), "UTF-8"));
//			writer.write(newjsonObject.toString());
//			writer.flush();
//			writer.close();

			ProjectReport oReport = findByProidAndName(projectId, packageid,"reports_summary");
			if (oReport != null) {
				
				oReport.setModifiedBy(report.getCreatedBy());
				oReport.setModifiedDate(new Date());
				
				report.setJsonfile(amturl);
				
				i=projectReportMapper.updateByPrimaryKeySelective(report);

			} else {		
				report.setJsonfile(amturl);
				report.setName("reports_summary");
				report.setStatus(ProjectReportContants.status_ok);
				report.setId(StringUtils.getUUID());
				
				i=projectReportMapper.insertSelective(report);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public boolean updateJsonfileById(String id, String jsonfile) {
		// TODO Auto-generated method stub
		ProjectReport report=new ProjectReport();
		report.setId(id);
		report.setJsonfile(jsonfile);
		report.setStatus(ProjectReportContants.status_ok);
		
		if(projectReportMapper.updateByPrimaryKeySelective(report)==1){
			return true;
		}
		return false;
	}

}
