package com.haili.site.contants;

/**
 * 解析sql和param的类型
 * @author 王晓东
 */
public interface ConstantRender {

	/**
	 * 解析sql和param的类型：beetl
	 */
	public static final String sql_renderType_beetl = "beetl";

	/**
	 * 解析sql和param的类型：velocity
	 */
	public static final String sql_renderType_velocity = "velocity";

	/**
	 * 解析sql和param的类型：freeMarker
	 */
	public static final String sql_renderType_freeMarker = "freeMarker";
	
}
