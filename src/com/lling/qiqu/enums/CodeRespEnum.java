package com.lling.qiqu.enums;

/**
 * @ClassName: CodeRespEnum
 * @Description: http返回code与desc对应值
 * @author lling
 * @date 2015-5-30
 */
public enum CodeRespEnum {
	
	CODE_SUCCESS("200", "success"),
	CODE_SERVER_EXCEPTION("500", "server exception"),
	CODE_PARAM_INVALUD("501", "request parameters is invalud"),
	CODE_SOURCE_EXISTS("502", "such source exists"),
	CODE_NO_SOURCE_EXISTS("503", "no such source exists"),
	CODE_OTHER_ERROR("504", "other error happened");
	
	private String code;
	private String desc;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	private CodeRespEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDescByCode(String code){
		for(CodeRespEnum codeRespEnum : CodeRespEnum.values()){
			if(codeRespEnum.getCode().equals(code)){
				return codeRespEnum.getDesc();
			}
		}
		return "";
	}
	
	

}
