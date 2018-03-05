package com.aladdin.project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo3;
import com.aladdin.base.ErrorEnum;
import com.aladdin.model.entity.Brand;
import com.aladdin.model.entity.Model;
import com.aladdin.model.entity.ModelMeta;
import com.aladdin.model.service.BrandService;
import com.aladdin.model.service.ModelService;
import com.aladdin.oss.OssService;
import com.aladdin.packages.entity.Packages;
import com.aladdin.packages.service.PackagesService;
import com.aladdin.project.entity.ProjectReport;
import com.aladdin.project.service.ProjectReportService;
import com.aladdin.properties.OssProperties;
import com.aladdin.utils.FileUtil;
import com.aladdin.utils.NumberToCN;
import com.aladdin.utils.StringUtils;
import com.aliyun.oss.model.PutObjectResult;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

@Controller
@RequestMapping("/api/vi/project_report")
public class ProjectReportController extends BaseController {

	public static DecimalFormat decimalFormat = new java.text.DecimalFormat("###.##");

	@Autowired
	private ProjectReportService projectReportService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private OssService ossService;

	@Autowired
	private OssProperties ossProperties;

	@Autowired
	private PackagesService packagesService;

	/**
	 * 根据项目id或套餐id查看所有报表信息
	 * 
	 * @param projectId
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "")
	public ResponseEntity<?> list(@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "packageId", required = false) String packageId,
			@RequestParam(value = "name", defaultValue = "reports_summary") String reportName, HttpServletRequest req,
			HttpServletResponse res) {

		ErrorEnum errorEnum = ErrorEnum.SERVER_ERROR;

		BaseVo3 baseVo3 = new BaseVo3();

		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isNotEmpty(projectId) || StringUtils.isNotEmpty(packageId)) {
			ProjectReport report = new ProjectReport();
			if (StringUtils.isNotEmpty(projectId)) {
				report.setProjectId(projectId);
			}
			if (StringUtils.isNotEmpty(packageId)) {
				report.setPackageId(packageId);
			}
			report.setName(reportName);

			List<ProjectReport> list = projectReportService.selectByCondition(report);
			String jsonCon = "";
			String reportId = "";

			if (list != null && list.size() > 0) {
				ProjectReport rep = list.get(0);
				if (rep != null && rep.getJsonfile() != null) {
					if (reportName.equals("reports_summary")) {
						projectReportService.updateSummaryReport(rep);
						ProjectReport sumrep = projectReportService.selectByKey(rep.getId());
						if (sumrep != null && sumrep.getJsonfile() != null) {
							jsonCon = sumrep.getJsonfile();
						}
					} else {
						jsonCon = rep.getJsonfile();
					}

					reportId = rep.getId();
				}

			}
			map.put("jsonfile", jsonCon);
			map.put("reportId", reportId);
			map.put("reportName", reportName);

		} else {
			errorEnum = ErrorEnum.PARAM_ERROR;
		}
		if (map != null) {
			baseVo3.setData(map);
			errorEnum = ErrorEnum.SUCCESS;
		}
		return buildResponseEntity(errorEnum, baseVo3);

	}

	/**
	 * 添加报表信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResponseEntity<?> save(
			// 套餐外主材增项报价表.
			@RequestParam("reports_main_material_additions") MultipartFile reports_main_material_additions,
			// 套餐外标准施工增项报价表.
			@RequestParam("reports_std_build_additions") MultipartFile reports_std_build_additions,
			// 套餐外施工增项明细报价表.
			@RequestParam("reports_customer_build_additions") MultipartFile reports_customer_build_additions,
			// 成本表.
			@RequestParam("reports_cost") MultipartFile reports_cost,
			// 套餐主材明细表.
			@RequestParam("reports_main_material_details") MultipartFile reports_main_material_details,

			@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "packageId", required = false) String packageId, HttpServletRequest req,
			HttpServletRequest rsp) {

		ErrorEnum errorEnum = ErrorEnum.SERVER_ERROR;
		BaseVo3 baseVo3 = new BaseVo3();

		Map<String, String> pMap = new HashMap<String, String>();

		pMap.put("reports_main_material_additions", getFileCon(reports_main_material_additions));
		pMap.put("reports_std_build_additions", getFileCon(reports_std_build_additions));
		pMap.put("reports_customer_build_additions", getFileCon(reports_customer_build_additions));
		pMap.put("reports_cost", getFileCon(reports_cost));
		pMap.put("reports_main_material_details", getFileCon(reports_main_material_details));

		List<ProjectReport> list = projectReportService.create(pMap, projectId, packageId, getUserId(req));

		if (list != null) {
			// baseVo2.setData(list);
			Map<String, Object> resMap = new HashMap<String, Object>();
			if (StringUtils.isNotEmpty(projectId)) {
				resMap.put("projectId", projectId);
			}
			if (StringUtils.isNotEmpty(packageId)) {
				// resMap.put("packageId", packageId);
				resMap.put("url", "http://127.0.0.1/baobiao.html?packageId=" + packageId);
			}
			baseVo3.setData(resMap);

			errorEnum = ErrorEnum.SUCCESS;
		}
		return buildResponseEntity(errorEnum, baseVo3);
	}

	private String getFileCon(MultipartFile file) {

		String res = "";
		try {
			InputStream inputStream = file.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			StringBuffer sb = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}

			// System.out.println(sb.toString());
			res = sb.toString();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}


	/**
	 * 导出excel
	 * 
	 * @param projectId
	 * @param req
	 * @param res
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/excel")
	public ResponseEntity<?> export(@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "packageId", required = false) String packageId, HttpServletRequest req,
			HttpServletResponse res) {

		ErrorEnum errorEnum = ErrorEnum.SERVER_ERROR;
		BaseVo3 baseVo3 = new BaseVo3();

		try {

			if (StringUtils.isNotEmpty(projectId) || StringUtils.isNotEmpty(packageId)) {

				ProjectReport reportPam = new ProjectReport();
				if (StringUtils.isNotEmpty(packageId)) {
					reportPam.setPackageId(packageId);
				}
				if (StringUtils.isNotEmpty(projectId)) {
					reportPam.setProjectId(projectId);
				}
				List<ProjectReport> reports = projectReportService.selectByCondition(reportPam);
				if (reports != null && reports.size() > 0) {

					String realPath1 = this.getClass().getClassLoader().getResource("").getPath()
							+ "reports_template.xlsx";
					System.out.println(realPath1);
//					File source = new File(realPath);// 源文件
					
//					InputStream tempinputstream=this.getClass().getResourceAsStream("/reports_template.xlsx");
//					
//					File source=new File("/tep/reports_template.xlsx");
//					FileUtil.inputstreamtofile(tempinputstream, source);
					
					String realPath=ossProperties.getTemplatePath()+"/reports_template.xlsx";
					
					//String realPath ="/usr/reports_template.xlsx";
					File source = new File(realPath);// 源文件
					

					String dir = ossProperties.getTemplatePath();// 目标文件夹
					String destinationPath = dir.concat(File.separator).concat(StringUtils.getUUID()).concat(".xlsx");// 目标文件
					File destination = new File(destinationPath);

					//System.out.println("copy----destination文件名：" + destinationPath);

					FileUtil.copy(source, destination);

					Workbook workbook = new XSSFWorkbook(destination);
					for (ProjectReport report : reports) {
						exportFile(report, workbook);
					}

					String fileUuid = StringUtils.getUUID().concat(".xlsx");

					String filePath = dir.concat(File.separator).concat(fileUuid);

					System.out.println("目标文件名：" + filePath);
					File file = new File(filePath);

					FileOutputStream outputStream = new FileOutputStream(file);
					workbook.write(outputStream);
					outputStream.flush();
					outputStream.close();
					workbook.close();

					// 上传到oss服务器
					String ossurl = "";

					InputStream is = new BufferedInputStream(new FileInputStream(file));
					PutObjectResult result = ossService.upload(fileUuid, is);
					if (result.getETag() != null) {
						ossurl = ossProperties.getUrl() + "/" + fileUuid;
					}
					is.close();

					Map<String, Object> ossMap = new HashMap<String, Object>();
					ossMap.put("excelUrl", ossurl);

					if (destination.exists()) {
						destination.delete();
					}
					if (file.exists()) {
						file.delete();
					}

					if (ossMap != null) {
						baseVo3.setData(ossMap);
					}
					errorEnum = errorEnum.SUCCESS;
				} else {
					errorEnum = ErrorEnum.NOT_EXIST;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buildResponseEntity(errorEnum, baseVo3);
	}

	public JSONObject getReportJson(ProjectReport report) {
		if (report != null) {
			String jsonfile = report.getJsonfile();
			if (StringUtils.isNotEmpty(jsonfile)) {
				try {
					return new JSONObject(jsonfile);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void exportFile(ProjectReport report, Workbook workbook) throws Exception {

		switch (report.getName()) {
		case "reports_summary":
			projectReportService.updateSummaryReport(report);
			Packages packages = new Packages();
			if (StringUtils.isNotEmpty(report.getPackageId())) {
				Packages packagesres = packagesService.selectByPrimaryKey(report.getPackageId());
				if (packagesres != null) {
					packages = packagesres;
				}
			}
			export_summary(workbook, getReportJson(report), packages);
			break;
		case "reports_main_material_additions":
			export_main_material_additions(workbook, getReportJson(report));
			break;
		case "reports_std_build_additions":
			export_std_build_additions(workbook, getReportJson(report));
			break;
		case "reports_customer_build_additions":
			export_customer_build_additions(workbook, getReportJson(report));
			break;
		case "reports_cost":

			export_cost(workbook, getReportJson(report));

			break;
		case "reports_main_material_details":
			export_main_material_details(workbook, getReportJson(report));
			break;
		case "reports_material_list":
			export_material_list(workbook, getReportJson(report));
			break;
		}

	}

	private void export_summary(Workbook wb, JSONObject jobj, Packages pkg) throws Exception {
		Sheet sheet = wb.getSheetAt(0);
		int r = 2;
		double sum = 0.0;
		double psum = 0.0;
		if (jobj.has("customer")) {
			JSONObject customer = jobj.getJSONObject("customer");
			Row customerRow = sheet.getRow(r);
			Cell cellCustomerName = customerRow.getCell(1);
			cellCustomerName.setCellValue(customer.getString("name"));
			Cell cellCustomerMobile = customerRow.getCell(3);
			cellCustomerMobile.setCellValue(customer.getString("mobile"));
			Cell cellCustomerAddress = customerRow.getCell(5);
			cellCustomerAddress.setCellValue(customer.getString("address"));
			r++;
		}
		if (jobj.has("decoration")) {
			JSONObject decoration = jobj.getJSONObject("decoration");
			Row decorationRow = sheet.getRow(r);
			decorationRow.getCell(1).setCellValue(decoration.getString("name"));
			decorationRow.getCell(3).setCellValue(decoration.getString("mobile"));

			JSONObject designer = jobj.getJSONObject("designer");
			decorationRow.getCell(5).setCellValue(designer.getString("name"));
			decorationRow.getCell(7).setCellValue(designer.getString("mobile"));
			r += 3;
		}
		if (jobj.has("package")) {
			String pkgname = "";
			String pkgunit = "平方米";
			String pkgprice = "";
			double pkgamount = 0;
			if (pkg != null) {
				pkgname = pkg.getName();
				pkgprice = pkg.getUnitprice();
			}

			JSONObject packageJsonObject = jobj.getJSONObject("package");
			if (StringUtils.isNotEmpty(pkgprice) && StringUtils.isNotEmpty(packageJsonObject.getString("area"))
					&& StringUtils.isNumeric(packageJsonObject.getString("area"))) {
				pkgamount = Double.valueOf(pkgprice.replace("¥", "").replace("/㎡", ""))
						* Double.valueOf(packageJsonObject.getString("area"));
				psum = psum + pkgamount;
			}
			Row packageRow = sheet.getRow(r);
			packageRow.getCell(0).setCellValue("1");
			packageRow.getCell(1).setCellValue(pkgname);
			packageRow.getCell(2).setCellValue(pkgunit);
			packageRow.getCell(3).setCellValue(packageJsonObject.getString("area"));
			packageRow.getCell(4).setCellValue(pkgprice);
			packageRow.getCell(5).setCellValue(pkgamount);
			packageRow.getCell(6).setCellValue(packageJsonObject.getString("remark"));
			r += 3;
		}

		Row mmaRow = sheet.getRow(r);
		if (jobj.getJSONObject("main_material_additions") != null
				&& jobj.getJSONObject("main_material_additions").getString("amount") != null
				&& StringUtils.isNumeric(jobj.getJSONObject("main_material_additions").getString("amount"))) {
			mmaRow.getCell(3).setCellValue(decimalFormat
					.format(Double.valueOf(jobj.getJSONObject("main_material_additions").getString("amount"))));
			sum = sum + Double.valueOf(jobj.getJSONObject("main_material_additions").getString("amount"));
		} else {
			mmaRow.getCell(3).setCellValue("");
		}

		r++;
		Row sbaRow = sheet.getRow(r);
		if (jobj.getJSONObject("std_build_additions") != null
				&& jobj.getJSONObject("std_build_additions").getString("amount") != null
				&& StringUtils.isNumeric(jobj.getJSONObject("std_build_additions").getString("amount"))) {
			sbaRow.getCell(3).setCellValue(decimalFormat
					.format(Double.valueOf(jobj.getJSONObject("std_build_additions").getString("amount"))));
			sum = sum + Double.valueOf(jobj.getJSONObject("std_build_additions").getString("amount"));
		} else {
			sbaRow.getCell(3).setCellValue("");
		}

		r++;
		Row cbaRow = sheet.getRow(r);
		cbaRow.getCell(3).setCellValue(jobj.getJSONObject("customer_build_additions").getString("amount"));
		if (jobj.getJSONObject("customer_build_additions") != null
				&& jobj.getJSONObject("customer_build_additions").getString("amount") != null
				&& StringUtils.isNumeric(jobj.getJSONObject("customer_build_additions").getString("amount"))) {
			cbaRow.getCell(3).setCellValue(decimalFormat
					.format(Double.valueOf(jobj.getJSONObject("customer_build_additions").getString("amount"))));
			sum = sum + Double.valueOf(jobj.getJSONObject("customer_build_additions").getString("amount"));
		} else {
			cbaRow.getCell(3).setCellValue("");
		}

		r++;
		Row addSumRow = sheet.getRow(r);
		addSumRow.getCell(3).setCellValue(decimalFormat.format(sum));
		addSumRow.getCell(6).setCellValue("大写：" + NumberToCN.parse(sum));
		r += 2;

		double sum2 = 0.0;
		Row designRow = sheet.getRow(r);
		if (jobj.getJSONObject("design") != null && jobj.getJSONObject("design").getString("amount") != null
				&& StringUtils.isNumeric(jobj.getJSONObject("design").getString("amount"))) {
			designRow.getCell(2).setCellValue(
					decimalFormat.format(Double.valueOf(jobj.getJSONObject("design").getString("amount"))));
			sum2 = sum2 + Double.valueOf(jobj.getJSONObject("design").getString("amount"));
		} else {
			designRow.getCell(2).setCellValue("");
		}

		r++;
		Row managementRow = sheet.getRow(r);

		if (jobj.getJSONObject("management") != null && jobj.getJSONObject("management").getString("amount") != null
				&& StringUtils.isNumeric(jobj.getJSONObject("management").getString("amount"))) {
			managementRow.getCell(2).setCellValue(
					decimalFormat.format(Double.valueOf(jobj.getJSONObject("management").getString("amount"))));
			sum2 = sum2 + Double.valueOf(jobj.getJSONObject("management").getString("amount"));
		} else {
			managementRow.getCell(2).setCellValue("");
		}

		r++;
		Row transportRow = sheet.getRow(r);

		if (jobj.getJSONObject("transport") != null && jobj.getJSONObject("transport").getString("amount") != null
				&& StringUtils.isNumeric(jobj.getJSONObject("transport").getString("amount"))) {
			transportRow.getCell(2).setCellValue(
					decimalFormat.format(Double.valueOf(jobj.getJSONObject("transport").getString("amount"))));
			sum2 = sum2 + Double.valueOf(jobj.getJSONObject("transport").getString("amount"));
		} else {
			transportRow.getCell(2).setCellValue("");
		}

		r++;
		Row totalSumRow = sheet.getRow(r);
		totalSumRow.getCell(2).setCellValue(decimalFormat.format(sum2 + sum + psum));
		totalSumRow.getCell(7).setCellValue("" + NumberToCN.parse(sum2 + sum + psum));

	}

	@SuppressWarnings("deprecation")
	private void export_main_material_additions(Workbook wb, JSONObject jobj) throws Exception {
		Sheet sheet = wb.getSheetAt(1);

		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		Font font = wb.createFont();
		font.setFontName("Microsoft YaHei");
		font.setFontHeightInPoints((short) 9);

		style.setFont(font);

		int zindex = 0;
		int r = 2;
		double SUM = 0.0;
		CellStyle headRowStyle = null;
		CellStyle headCellStyle = null;
		short headRowHight = 20;
		Row titleRow = null;
		if (jobj.has("children")) {
			JSONArray zones = jobj.getJSONArray("children");
			for (int zi = 0; zi < zones.size(); zi++) {
				JSONObject obj = zones.getJSONObject(zi);

				String zname = obj.getString("zone");
				if (zindex == 0) {
					Row headrow = sheet.getRow(r);
					headrow.getCell(0).setCellValue(zname);
					headCellStyle = headrow.getCell(0).getCellStyle();
					headRowStyle = headrow.getRowStyle();
					headRowHight = headrow.getHeight();
					r++;
					System.out.println();
					titleRow = sheet.getRow(r);
					r++;
					zindex++;
				} else {
					Row headrow = sheet.createRow(r);
					headrow.setRowStyle(headRowStyle);
					headrow.setHeight(headRowHight);
					for (int i = 0; i < 10; i++) {
						Cell cell = headrow.createCell(i);
						cell.setCellStyle(style);
					}
					sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 9));
					Cell headCell = headrow.getCell(0);
					headCell.setCellStyle(headCellStyle);
					headCell.setCellValue(zname);
					r++;
					if (titleRow != null) {
						Row trow = sheet.createRow(r);
						for (int i = 0; i < 10; i++) {
							Cell tcell = trow.createCell(i);
							tcell.setCellStyle(style);
							if (titleRow.getCell(i) != null) {
								tcell.setCellValue(titleRow.getCell(i).getStringCellValue());
								tcell.setCellStyle(titleRow.getCell(i).getCellStyle());
							}
						}
						r++;
					}
				}
				if (obj.has("children")) {
					JSONArray items = obj.getJSONArray("children");
					int numofchildren = items.size();
					double sum = 0.0;
					for (int i = 0; i < numofchildren; i++) {
						JSONObject item = items.getJSONObject(i);
						String category = "";
						String brand = "";
						String name = "";
						String modelnumber = "";
						String specification = "";
						String unit = "";
						String price = "0";
						String quantity = "0";
						double amount = 0;
						String remark = "";
						if (item.has("category")) {
							category = item.getString("category");
						}
						if (item.has("brand")) {
							brand = item.getString("brand");
						}
						if (item.has("product_mode")) {
							modelnumber = item.getString("product_mode");
						}
						if (item.has("specification")) {
							specification = item.getString("specification");
						}
						if (item.has("unit")) {
							unit = item.getString("unit");
						}
						if (item.has("quantity")) {
							quantity = item.getString("quantity");
						}
						if (item.has("price")) {
							price = item.getString("price");
						}
						if (item.has("amount")) {
							amount = Double.valueOf(item.getString("amount"));
						}
						if (item.has("remark")) {
							remark = item.getString("remark");
						}
						// System.out.println(r + "-----" +
						// sheet.getLastRowNum());
						// if (r < sheet.getLastRowNum()) {
						// sheet.shiftRows(r, sheet.getLastRowNum(), 1, true,
						// false);
						// }

						Row row = sheet.createRow(r);
						Cell cell;

						int c = 0;
						cell = row.createCell(c);
						cell.setCellValue(category);
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(brand);
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(name);
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(modelnumber);
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(specification);
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(unit);
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(quantity);
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(price);
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(amount);
						cell.setCellStyle(style);
						SUM = SUM + amount;
						sum = sum + amount;
						c++;
						cell = row.createCell(c);
						cell.setCellValue(remark);
						cell.setCellStyle(style);

						r++;

					}
					// if ((r - 1) - (r - numofchildren) >= 2) {
					// sheet.addMergedRegion(new CellRangeAddress(r
					// - numofchildren, r - 1, 0, 0));
					// }
					// Row sumRow = sheet.createRow(r);
					// for (int i = 0; i < 10; i++) {
					// Cell cell = sumRow.createCell(i);
					// cell.setCellStyle(style);
					// }
					// sheet.addMergedRegion(new CellRangeAddress(r, r, 1, 9));
					// Cell tcell = sumRow.getCell(0);
					// tcell.setCellValue("小计");
					// Cell scell = sumRow.getCell(1);
					// scell.setCellValue(decimalFormat.format(sum));
					// r++;
				} else {
					for (int i = 0; i < 10; i++) {
						Row row = sheet.createRow(r);
						Cell cell;
						for (int c = 0; c <= 9; c++) {
							cell = row.createCell(c);
							cell.setCellValue("");
							cell.setCellStyle(style);
						}
						r++;
					}
				}
			}
		}

		if (r > 2) {
			Row row = sheet.createRow(r);
			row.setRowStyle(headRowStyle);
			row.setHeight(headRowHight);
			for (int i = 0; i < 10; i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);
			}
			Cell cell1 = row.getCell(0);
			cell1.setCellValue("主材增项合计：");
			Cell cell2 = row.getCell(1);
			cell2.setCellValue(decimalFormat.format(SUM));
			Cell cell3 = row.getCell(3);
			cell3.setCellValue("大写：");
			Cell cell4 = row.getCell(4);
			cell4.setCellValue(NumberToCN.parse(SUM));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 1, 2));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 4, 9));
			r++;

			CellStyle style2 = wb.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_LEFT);
			style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style2.setBorderBottom(CellStyle.BORDER_THIN);
			style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style2.setBorderTop(CellStyle.BORDER_THIN);
			style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
			style2.setBorderLeft(CellStyle.BORDER_THIN);
			style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			style2.setBorderRight(CellStyle.BORDER_THIN);
			style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
			Row row2 = sheet.createRow(r);
			for (int i = 0; i < 10; i++) {
				Cell cell = row2.createCell(i);
				cell.setCellStyle(style2);
			}

			row2.getCell(0).setCellValue("业主签字：");
			row2.getCell(2).setCellValue("设计师签字：");
			row2.getCell(4).setCellValue("客户经理签字：");
			row2.getCell(6).setCellValue("阿拉丁电商盖章：");
			row2.getCell(8).setCellValue("    年    月   日");
			sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 4, 5));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 6, 7));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 8, 9));

			r++;
			// Row row3 = sheet.createRow(r);
			// for (int i = 0; i < 10; i++) {
			// Cell cell = row3.createCell(i);
			// cell.setCellStyle(style);
			// }
			// row3.getCell(0).setCellValue(" 年 月 日");
			// row3.getCell(3).setCellValue(" 年 月 日");
			// row3.getCell(6).setCellValue(" 年 月 日");
			// row3.getCell(9).setCellValue(" 年 月 日");
			// sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
			// sheet.addMergedRegion(new CellRangeAddress(r, r, 2, 3));
			// sheet.addMergedRegion(new CellRangeAddress(r, r, 4, 5));
		}
	}

	@SuppressWarnings("deprecation")
	private void export_std_build_additions(Workbook wb, JSONObject jobj) throws Exception {
		Sheet sheet = wb.getSheetAt(2);

		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		Font font = wb.createFont();
		font.setFontName("Microsoft YaHei");
		font.setFontHeightInPoints((short) 9);

		style.setFont(font);

		int index = 0;
		int r = 3;
		int c = 0;
		double SUM = 0.0;
		if (jobj.has("children")) {
			JSONArray items = jobj.getJSONArray("children");
			int numofchildren = items.size();
			for (int i = 0; i < numofchildren; i++) {
				JSONObject item = items.getJSONObject(i);
				sheet.shiftRows(r, sheet.getLastRowNum(), 1, true, false);
				Row row = sheet.createRow(r);
				Cell cell = row.createCell(0);
				cell.setCellValue(String.valueOf(index + 1));
				cell.setCellStyle(style);

				c = 1;
				cell = row.createCell(c);
				cell.setCellValue(item.getString("name"));
				cell.setCellStyle(style);

				c++;
				cell = row.createCell(c);
				cell.setCellValue(item.getString("unit"));
				cell.setCellStyle(style);

				c++;
				cell = row.createCell(c);
				cell.setCellValue(item.getString("price"));
				cell.setCellStyle(style);

				c++;
				cell = row.createCell(c);
				cell.setCellValue(item.getString("area"));
				cell.setCellStyle(style);

				c++;
				cell = row.createCell(c);
				cell.setCellValue(item.getString("amount"));
				cell.setCellStyle(style);

				c++;
				cell = row.createCell(c);
				cell.setCellValue(item.getString("remark"));
				cell.setCellStyle(style);

				if (StringUtils.isNumeric(item.getString("amount"))) {
					SUM = SUM + Double.valueOf(item.getString("amount"));
				}

				r++;
				index++;

			}

		}

		if (r <= 3) {
			for (int i = 0; i < 20; i++) {
				Row row = sheet.createRow(r);
				Cell cell;
				for (c = 0; c <= 6; c++) {
					cell = row.createCell(c);
					cell.setCellValue("");
					cell.setCellStyle(style);
				}
				r++;
			}
		}

		Row sumRow = sheet.createRow(r);
		for (int i = 0; i < 7; i++) {
			Cell cell = sumRow.createCell(i);
			cell.setCellStyle(style);
			switch (i) {
			case 0:
				cell.setCellValue("总计：");
				break;
			case 2:
				cell.setCellValue(SUM + "");
				break;
			case 4:
				cell.setCellValue("大写：");
				break;
			case 5:
				cell.setCellValue(NumberToCN.parse(SUM));
				break;
			}
		}

		sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(r, r, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(r, r, 5, 6));
		r++;
		Row row = sheet.createRow(r);
		for (int i = 0; i < 7; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
		}

		row.getCell(0).setCellValue("业主签字：");
		row.getCell(2).setCellValue("设计师签字：");
		row.getCell(4).setCellValue("客户经理签字：");
		row.getCell(6).setCellValue("阿拉丁电商盖章：");
		r++;
		Row row2 = sheet.createRow(r);
		for (int i = 0; i < 7; i++) {
			Cell cell = row2.createCell(i);
			cell.setCellStyle(style);
		}
		row2.getCell(0).setCellValue("    年    月   日");
		row2.getCell(2).setCellValue("    年    月   日");
		row2.getCell(4).setCellValue("    年    月   日");
		row2.getCell(6).setCellValue("    年    月   日");
		sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(r, r, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(r, r, 4, 5));
	}

	@SuppressWarnings("deprecation")
	private void export_customer_build_additions(Workbook wb, JSONObject jobj) throws Exception {
		Sheet sheet = wb.getSheetAt(3);

		CellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		Font font = wb.createFont();
		font.setFontName("Microsoft YaHei");
		font.setFontHeightInPoints((short) 9);

		style.setFont(font);

		int zindex = 0;
		int r = 3;
		int c = 0;
		double SUM = 0.0;
		if (jobj.has("children")) {
			JSONArray zones = jobj.getJSONArray("children");
			for (int m = 0; m < zones.size(); m++) {
				c = 0;
				JSONObject obj = zones.getJSONObject(m);
				String zname = obj.getString("zone");

				if (obj.has("children") && obj.getJSONArray("children").size() > 0) {
					sheet.shiftRows(r, sheet.getLastRowNum(), 1, true, false);
					Row section = sheet.createRow(r);
					for (int i = 0; i <= 6; i++) {
						Cell cell = section.createCell(i);
						cell.setCellStyle(style);
					}
					Cell cell = section.createCell(0);
					cell.setCellValue("(" + (zindex + 1) + ")" + zname);
					cell.setCellStyle(style);
					sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 6));
					r++;

					sheet.shiftRows(r, sheet.getLastRowNum(), 1, true, false);
					Row header = sheet.createRow(r);
					cell = header.createCell(0);
					cell.setCellValue("序号");
					cell.setCellStyle(style);
					cell = header.createCell(1);
					cell.setCellValue("施工项目");
					cell.setCellStyle(style);
					cell = header.createCell(2);
					cell.setCellValue("单位");
					cell.setCellStyle(style);
					cell = header.createCell(3);
					cell.setCellValue("工程量");
					cell.setCellStyle(style);
					cell = header.createCell(4);
					cell.setCellValue("单价");
					cell.setCellStyle(style);
					cell = header.createCell(5);
					cell.setCellValue("单项合计");
					cell.setCellStyle(style);
					cell = header.createCell(6);
					cell.setCellValue("施   工   说   明");
					cell.setCellStyle(style);

					r++;

					double sum = 0.0;
					JSONArray items = obj.getJSONArray("children");

					int numofchildren = items.size();

					for (int i = 0; i < numofchildren; i++) {

						JSONObject item = items.getJSONObject(i);

						sheet.shiftRows(r, sheet.getLastRowNum(), 1, true, false);
						Row row = sheet.createRow(r);
						cell = row.createCell(0);
						cell.setCellValue(String.valueOf(i + 1));
						cell.setCellStyle(style);

						c = 1;
						cell = row.createCell(c);
						cell.setCellValue(item.getString("name"));
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(item.getString("unit"));
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						if (StringUtils.isNumeric(item.getString("quantity"))) {
							cell.setCellValue(decimalFormat.format(Double.valueOf(item.getString("quantity"))));
						} else {
							cell.setCellValue("");
						}

						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						if (StringUtils.isNumeric(item.getString("price"))) {
							cell.setCellValue(decimalFormat.format(Double.valueOf(item.getString("price"))));
						} else {
							cell.setCellValue("");
						}
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						if (StringUtils.isNumeric(item.getString("amount"))) {
							cell.setCellValue(decimalFormat.format(Double.valueOf(item.getString("amount"))));
						} else {
							cell.setCellValue("");
						}
						cell.setCellStyle(style);

						c++;
						cell = row.createCell(c);
						cell.setCellValue(item.getString("remark"));
						cell.setCellStyle(style);

						r++;
						if (StringUtils.isNumeric(item.getString("amount"))) {
							sum = sum + Double.valueOf(item.getString("amount"));
							SUM = SUM + Double.valueOf(item.getString("amount"));
						}

					}

					if (numofchildren > 1) {
						Row row = sheet.createRow(r);
						for (int i = 0; i < 7; i++) {
							Cell sCell = row.createCell(i);
							sCell.setCellStyle(style);
							switch (i) {
							case 1:
								sCell.setCellValue("小计：");
								break;

							case 2:
								sCell.setCellValue("" + decimalFormat.format(sum));
								break;
							}
						}
						sheet.addMergedRegion(new CellRangeAddress(r, r, 2, 6));
						r++;
					}
					zindex++;
				}

			}

			sheet.createRow(r);
			r++;
			if (r <= 3) {
				r = r + 1;
				for (int i = 0; i < 20; i++) {
					Row row = sheet.createRow(r - 1);
					Cell cell;
					for (c = 0; c <= 6; c++) {
						cell = row.createCell(c);
						cell.setCellValue("");
						cell.setCellStyle(style);
					}
					r++;
				}
			}
			Row sumRow = sheet.createRow(r);
			for (int i = 0; i < 7; i++) {
				Cell cell = sumRow.createCell(i);
				cell.setCellStyle(style);
				switch (i) {
				case 0:
					cell.setCellValue("总计：");
					break;
				case 2:
					cell.setCellValue(decimalFormat.format(SUM));
					break;
				case 4:
					cell.setCellValue("大写：");
					break;
				case 5:
					cell.setCellValue(NumberToCN.parse(SUM));
					break;

				}
			}

			sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 5, 6));
			r++;
			Row row = sheet.createRow(r);
			for (int i = 0; i < 7; i++) {
				row.createCell(i);
			}

			row.getCell(0).setCellValue("业主签字：");
			row.getCell(2).setCellValue("设计师签字：");
			row.getCell(4).setCellValue("客户经理签字：");
			row.getCell(6).setCellValue("装饰公司盖章：");
			r++;
			Row row2 = sheet.createRow(r);
			for (int i = 0; i < 7; i++) {
				row2.createCell(i);
			}
			row2.getCell(0).setCellValue("    年    月   日");
			row2.getCell(2).setCellValue("    年    月   日");
			row2.getCell(4).setCellValue("    年    月   日");
			row2.getCell(6).setCellValue("    年    月   日");
			sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 4, 5));

		}
	}

	@Autowired
	private BrandService brandService;

	@SuppressWarnings({ "unused", "deprecation" })
	private void export_cost(Workbook wb, JSONObject jobj) throws Exception {
		Sheet sheet = wb.getSheetAt(4);

		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		Font font = wb.createFont();
		font.setFontName("Microsoft YaHei");
		font.setFontHeightInPoints((short) 9);
		font.setFontHeightInPoints((short) 9);
		style.setFont(font);

		double area = jobj.getDouble("house_area");
		int index = 0, zindex = 0;
		int r = 2;
		int c = 0;
		double SUM = 0.0;
		// 部品小计
		double SUM_P = 0.0;
		// 运费
		double SUM_T = 0.0;
		// 安装费
		double SUM_I = 0.0;
		// 部品合计
		double SUM_PT = 0.0;
		if (jobj.has("children")) {
			JSONArray zones = jobj.getJSONArray("children");
			for (int x = 0; x < zones.size(); x++) {
				// get zone
				c = 0;
				JSONObject obj = zones.getJSONObject(x);
				String zname = obj.getString("zone");

				// 判断区域内是否有施工项目，如果没有就不显示当前区域
				boolean f1 = false;
				if (obj.has("children")) {
					for (Object object : obj.getJSONArray("children")) {
						JSONObject jobj1 = new JSONObject(object.toString());
						if (jobj1.has("children")) {
							f1 = true;
							break;
						}
					}
				}

				if (f1) {

					// sheet.shiftRows(r, sheet.getLastRowNum(), 1, true,
					// false);
					Row section = sheet.createRow(r);

					for (int l = 0; l <= 17; l++) {
						Cell cell2 = section.createCell(l);
						cell2.setCellStyle(style);
					}
					Cell cell = section.createCell(0);
					cell.setCellValue("(" + (zindex + 1) + ")" + zname);
					sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 17));
					cell.setCellStyle(style);

					index++;
					r++;
					// sheet.shiftRows(r, sheet.getLastRowNum(), 1, true,
					// false);
					Row header = sheet.createRow(r);
					cell = header.createCell(c);
					cell.setCellValue("序号");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("工程");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("施工项目");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("施工子项");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("工程量");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("单位");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("人工费");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("材料费");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("单项合计");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("备注");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("部品品牌");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("部品型号");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("损耗率（%）");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("部品单价");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("部品小计");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("运费");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("安装费用");
					cell.setCellStyle(style);

					c++;
					cell = header.createCell(c);
					cell.setCellValue("部品合计");
					cell.setCellStyle(style);

					index++;
					r++;

					JSONArray engines = obj.getJSONArray("children");

					int numofchildren = engines.size();

					for (int i = 0; i < numofchildren; i++) {
						// get engineer

						int engstartr = r;

						index = 0;

						c = 0;

						JSONObject engine = engines.getJSONObject(i);

						String engname = engine.getString("name");

						if (engine.has("children")) {
							JSONArray unknownodes = engine.getJSONArray("children");
							int unknownodessize = unknownodes.size();

							for (int j = 0; j < unknownodessize; j++) {

								// who designed the spec that make me cry so
								// many
								// times?
								// I make sure it will be redefined soon (right
								// here
								// waiting
								// for CR's coming)

								JSONObject unknownode = unknownodes.getJSONObject(j);

								String product_id = unknownode.getString("product_id");
								String product_amount = unknownode.getString("product_amount");
								String transport_expenses = unknownode.getString("transport_expenses");
								String install_expenses = unknownode.getString("install_expenses");
								String wastage_rate = unknownode.getString("wastage_rate");
								// Model model = Model.find(product_id);

								Model model = modelService.selectByKey(product_id);

								String category = "";
								String brand = "";
								String name = "";
								String modelnumber = "";
								String specification;
								specification = "";
								String unit = "";
								double price = 0.0;
								int quantity = 0;
								String remark = "";
								if (model != null) {
									if (StringUtils.isNotEmpty(model.getCategoryId())) {// 工程分类
										category = model.getCategoryId();
									}
									if (StringUtils.isNotEmpty(model.getBrand())) {
										Brand brand2 = brandService.findBrandById(model.getBrand());
										if (brand2 != null && brand2.getBrand_name() != null) {
											brand = brand2.getBrand_name();
										}
									}
									name = model.getName();
									modelnumber = model.getModelnumber();

									ModelMeta metapam = new ModelMeta();
									metapam.setObjectId(model.getId());
									metapam.setKey("specification");// 规格

									ModelMeta metares = modelService.selectModelMetaByCondition(metapam);
									if (metares != null && metares.getValue() != null) {
										specification = metares.getValue();
									}

									if (model.getUnit() != null) {
										unit = model.getUnit();
									}
									if (model.getUnitprice() != null) {
										price = model.getUnitprice();
									}
								}

								int rbengin = r;
								if (unknownode.has("children")) {
									double sum_pt = 0.0;
									if (StringUtils.isNumeric(product_amount)) {
										SUM_P = SUM_P + Double.valueOf(product_amount);
										SUM_PT = SUM_PT + Double.valueOf(product_amount);
										sum_pt = sum_pt + Double.valueOf(product_amount);
									}
									if (StringUtils.isNumeric(transport_expenses)) {
										SUM_T = SUM_T + Double.valueOf(transport_expenses);
										SUM_PT = SUM_PT + Double.valueOf(transport_expenses);
										sum_pt = sum_pt + Double.valueOf(transport_expenses);
									}
									if (StringUtils.isNumeric(install_expenses)) {
										SUM_I = SUM_I + Double.valueOf(install_expenses);
										SUM_PT = SUM_PT + Double.valueOf(install_expenses);
										sum_pt = sum_pt + Double.valueOf(install_expenses);
									}

									JSONArray subengines = unknownode.getJSONArray("children");
									int subenginessize = subengines.size();

									for (int k = 0; k < subenginessize; k++) {

										JSONObject subeng = subengines.getJSONObject(k);

										String subengname = subeng.getString("name");

										if (subeng.has("children")) {
											JSONArray items = subeng.getJSONArray("children");
											int itemssize = items.size();

											double sum = 0.0;

											for (int m = 0; m < itemssize; m++) {

												JSONObject item = items.getJSONObject(m);
												//
												// sheet.shiftRows(r,
												// sheet.getLastRowNum(),
												// 1, true, false);
												Row row = sheet.createRow(r);

												c = 0;
												cell = row.createCell(c);
												cell.setCellValue(String.valueOf(index + 1));
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												cell.setCellValue(engname);
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												cell.setCellValue(subengname);
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												cell.setCellValue(item.getString("name"));
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												if (StringUtils.isNumeric(item.getString("workload"))) {
													cell.setCellValue(decimalFormat
															.format(Double.valueOf(item.getString("workload"))));
												} else {
													cell.setCellValue("0");
												}

												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												cell.setCellValue(item.getString("unit"));
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												if (StringUtils.isNumeric(item.getString("unitprice_man"))) {
													cell.setCellValue(decimalFormat
															.format(Double.valueOf(item.getString("unitprice_man"))));
												} else {
													cell.setCellValue("0");
												}
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												if (StringUtils.isNumeric(item.getString("unitprice_material"))) {
													cell.setCellValue(decimalFormat.format(
															Double.valueOf(item.getString("unitprice_material"))));
												} else {
													cell.setCellValue("0");
												}
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												if (StringUtils.isNumeric(item.getString("amount"))) {
													cell.setCellValue(decimalFormat
															.format(Double.valueOf(item.getString("amount"))));
												} else {
													cell.setCellValue("0");
												}

												cell.setCellStyle(style);
												if (StringUtils.isNumeric(item.getString("amount"))) {
													sum = sum + Double.valueOf(item.getString("amount"));
													SUM = SUM + Double.valueOf(item.getString("amount"));
												}
												c++;
												cell = row.createCell(c);
												cell.setCellValue(item.getString("remark"));
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												cell.setCellValue(brand);
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												cell.setCellValue(modelnumber);
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												cell.setCellValue(wastage_rate);
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												cell.setCellValue(decimalFormat.format(price));
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												if (StringUtils.isNumeric(product_amount)) {
													cell.setCellValue(
															decimalFormat.format(Double.valueOf(product_amount)));
												} else {
													cell.setCellValue("");
												}
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												if (StringUtils.isNumeric(transport_expenses)) {
													cell.setCellValue(
															decimalFormat.format(Double.valueOf(transport_expenses)));
												} else {
													cell.setCellValue("");
												}
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												if (StringUtils.isNumeric(product_amount)) {
													cell.setCellValue(
															decimalFormat.format(Double.valueOf(install_expenses)));
												} else {
													cell.setCellValue("");
												}
												cell.setCellStyle(style);

												c++;
												cell = row.createCell(c);
												cell.setCellValue(decimalFormat.format(sum_pt));
												cell.setCellStyle(style);

												r++;
												index++;
											}
											if (itemssize > 1) {
												Row row = sheet.createRow(r);
												for (int l = 0; l <= 17; l++) {
													Cell cell2 = row.createCell(l);
													cell2.setCellStyle(style);
												}
												row.getCell(0).setCellValue("" + String.valueOf(index + 1));
												row.getCell(3).setCellValue("小计");
												row.getCell(8).setCellValue(decimalFormat.format(sum));

												r++;
												index++;
												sheet.addMergedRegion(
														new CellRangeAddress(r - itemssize - 1, r - 1, 2, 2));
											}
										} else {

										}
									}
								}
								if (r - 1 - rbengin > 1) {
									for (int columnIndex = 10; columnIndex <= 17; columnIndex++) {
										sheet.addMergedRegion(
												new CellRangeAddress(rbengin, r - 1, columnIndex, columnIndex));
									}
								}

							}
						}
						if (r - 1 - engstartr > 1) {
							sheet.addMergedRegion(new CellRangeAddress(engstartr, r - 1, 1, 1));
						}
					}
					zindex++;
				}
			}

			Row sumRow = sheet.createRow(r);

			for (int l = 0; l <= 17; l++) {
				Cell cell2 = sumRow.createCell(l);
				cell2.setCellStyle(style);
			}

			sumRow.getCell(0).setCellValue("施工成本总计：");
			sumRow.getCell(8).setCellValue(decimalFormat.format(SUM));
			sumRow.getCell(14).setCellValue(decimalFormat.format(SUM_P));
			sumRow.getCell(15).setCellValue(decimalFormat.format(SUM_T));
			sumRow.getCell(16).setCellValue(decimalFormat.format(SUM_I));
			sumRow.getCell(17).setCellValue(decimalFormat.format(SUM_PT));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 7));
			r++;
			Row avgRow = sheet.createRow(r);

			for (int l = 0; l <= 17; l++) {
				Cell cell2 = avgRow.createCell(l);
				cell2.setCellStyle(style);
			}
			if (area < 1) {
				area = 1;
			}
			avgRow.getCell(0).setCellValue("平方成本价：");
			avgRow.getCell(8).setCellValue(decimalFormat.format(SUM / area));
			avgRow.getCell(17).setCellValue(decimalFormat.format(SUM_PT / area));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 7));
		}
	}

	@SuppressWarnings("deprecation")
	private void export_material_list(Workbook wb, JSONObject jobj) throws Exception {
		Sheet sheet = wb.getSheetAt(6);

		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		Font font = wb.createFont();
		font.setFontName("Microsoft YaHei");
		font.setFontHeightInPoints((short) 9);

		style.setFont(font);

		int index = 0, zindex = 0;
		int r = 2;
		int c = 0;
		JSONArray zones = jobj.getJSONArray("children");
		while (zindex < zones.size()) {
			c = 0;
			JSONObject obj = zones.getJSONObject(zindex);
			String zname = obj.getString("zone");

			if (obj.has("children")) {
				JSONArray items = obj.getJSONArray("children");

				int numofchildren = items.size();

				for (int i = 0; i < numofchildren; i++) {

					JSONObject item = items.getJSONObject(i);

					// sheet.shiftRows(r, sheet.getLastRowNum(), 1, true,
					// false);
					Row row = sheet.createRow(r);
					Cell cell = row.createCell(0);
					cell.setCellValue(String.valueOf(index + 1));
					cell.setCellStyle(style);

					if (i == 0) {
						cell = row.createCell(1);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(zname);
						cell.setCellStyle(style);
					}

					c = 2;
					cell = row.createCell(c);
					cell.setCellValue(item.getString("name"));
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(item.getString("position"));
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(item.getString("spec"));
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(item.getString("model"));
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(item.getString("unit"));
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(item.getString("brands"));
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(item.getString("purchase_places"));
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(item.getString("purchase_type"));
					cell.setCellStyle(style);

					r++;
					index++;
				}
				zindex++;
				if (numofchildren > 1) {
					sheet.addMergedRegion(new CellRangeAddress(r - numofchildren, r - 1, 1, 1));
				}
			}
		}

		if (r <= 2) {
			r = r + 2;
			for (int i = 0; i < 30; i++) {
				Row row = sheet.createRow(r);
				Cell cell;
				for (c = 0; c <= 9; c++) {
					cell = row.createCell(c);
					cell.setCellValue("");
					cell.setCellStyle(style);
				}
				r++;
			}
		}

		if (r > 2) {
			r++;
			Row row2 = sheet.createRow(r);
			for (int i = 0; i < 10; i++) {
				Cell cell = row2.createCell(i);
				cell.setCellStyle(style);
			}

			row2.getCell(0).setCellValue("业主签字：");
			row2.getCell(3).setCellValue("设计师签字：");
			row2.getCell(6).setCellValue("客户经理签字：");
			row2.getCell(9).setCellValue("阿拉丁电商盖章：");
			r++;
			Row row3 = sheet.createRow(r);
			for (int i = 0; i < 10; i++) {
				Cell cell = row3.createCell(i);
				cell.setCellStyle(style);
			}
			row3.getCell(0).setCellValue("    年    月   日");
			row3.getCell(3).setCellValue("    年    月   日");
			row3.getCell(6).setCellValue("    年    月   日");
			row3.getCell(9).setCellValue("    年    月   日");
			sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(r, r, 4, 5));
		}
	}

	@SuppressWarnings("deprecation")
	private void export_main_material_details(Workbook wb, JSONObject jobj) throws Exception {
		Sheet sheet = wb.getSheetAt(5);

		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		Font font = wb.createFont();
		font.setFontName("Microsoft YaHei");
		font.setFontHeightInPoints((short) 9);

		style.setFont(font);

		int r = 2;
		int c = 0;
		JSONArray zones = jobj.getJSONArray("children");
		for (int zi = 0; zi < zones.size(); zi++) {
			c = 0;
			JSONObject obj = zones.getJSONObject(zi);
			String zname = obj.getString("zone");

			if (obj.has("children")) {
				JSONArray items = obj.getJSONArray("children");

				int numofchildren = items.size();

				for (int i = 0; i < numofchildren; i++) {

					JSONObject item = items.getJSONObject(i);

					String id = item.getString("id");
					// Model model = Model.find(id);
					Model model = modelService.selectByKey(id);
					String category = "";
					String brand = "";
					String name = "";
					String modelnumber = "";
					String specification = "";
					String unit = "";
					int quantity = 0;
					String remark = "";
					if (model != null) {
						if (model.getCategoryId() != null) {
							category = model.getCategoryId();// 工程分类
						}
						if (StringUtils.isNotEmpty(model.getBrand())) {
							Brand brand2 = brandService.findBrandById(model.getBrand());
							if (brand2 != null && brand2.getBrand_name() != null) {
								brand = brand2.getBrand_name();
							}
						}

						name = model.getName();
						modelnumber = model.getModelnumber();

						ModelMeta metapam = new ModelMeta();
						metapam.setObjectId(model.getId());
						metapam.setKey("specification");

						ModelMeta metares = modelService.selectModelMetaByCondition(metapam);
						if (metares != null && metares.getValue() != null) {
							specification = metares.getValue();
						}
						if (model.getUnit() != null) {
							unit = model.getUnit();
						}

						if (item.has("quantity") && StringUtils.isNumeric(item.getString("quantity"))) {
							quantity = (int) Math.ceil(item.getDouble("quantity"));
						}
						remark = item.getString("remark");
					}
					Row row = sheet.createRow(r);
					Cell cell;

					cell = row.createCell(0);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(zname);
					cell.setCellStyle(style);
					c = 1;
					cell = row.createCell(c);
					cell.setCellValue(category);
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(brand);
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(name);
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(modelnumber);
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(specification);
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(unit);
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue(quantity);
					cell.setCellStyle(style);

					c++;
					cell = row.createCell(c);
					cell.setCellValue((remark != null && !"null".equals(remark)) ? remark : "");
					cell.setCellStyle(style);

					r++;
				}
				if (numofchildren > 1) {
					sheet.addMergedRegion(new CellRangeAddress(r - numofchildren, r - 1, 0, 0));
				}
			}
		}
	}

}
