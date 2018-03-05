package com.aladdin.model;

public class ModelContants {

	public static final Integer comefrom_catalog = 2;// 创建来源 型录

	public static final Integer comefrom_product = 0;// 创建来源 商品

	public static final Integer comefrom_designer = 1;// 创建来源 设计师

	public static final Integer state_init = 0;// 未审核

	public static final Integer state_ok = 1;// 审核通过

	public static final Integer state_no = 2;// 审核不通过;

	public static final Integer accessibility_no = 0;// 不共享

	public static final Integer accessibility_ok = 3;// 共享

	public static final Integer status_ok = 1;// 表示可用

	public static final Integer status_no = 0;// 表示禁用

	/**
	 * 表示商品已建立过单品模型
	 */
	public static final Byte exist_yes = 1;

	/**
	 * 表示商品没建立过单品模型
	 */
	public static final Byte exist_no = 0;
	
	/**
	 * model meta常量
	 */
	public static final String stage = "stage";
	public static final String modelSize = "modelSize";
	public static final String modelMaterial = "modelMaterial";
	public static final String unitpriceMaterial = "unitpriceMaterial";
	public static final String unitpriceMan = "unitpriceMan";
	public static final String transportExpenses = "transportExpenses";
	public static final String installExpenses = "installExpenses";
	public static final String wastageRate = "wastageRate";
	public static final String exportType = "exportType";
	public static final String pCategory = "projectCategory";
//	public static final String materialtype = "materialtype";
	
}
