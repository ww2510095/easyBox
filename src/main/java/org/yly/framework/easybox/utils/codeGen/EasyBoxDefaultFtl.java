package org.yly.framework.easybox.utils.codeGen;

import lombok.Data;

/**
 * 默认的ftl文件，将写入到resources里面
 * */
@Data
public class EasyBoxDefaultFtl {
	
	private String b="package ${packages};\r\n" + 
			"\r\n" + 
			"import lombok.Data;\r\n" + 
			"import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;\r\n" + 
			"import org.yly.framework.easybox.utils.codeGen.EasyBoxCodeGenTab;\r\n" + 
			"import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxColumn;\r\n" + 
			"import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;\r\n" + 
			"import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;\r\n" + 
			"\r\n" + 
			"@Data\r\n" + 
			"@EasyBoxCodeGenTab(${className}Service.tabName)\r\n" + 
			"public class ${className} extends EasyBoxBaseBean{\r\n" + 
			"\r\n" + 
			"	${beanData}\r\n" + 
			"    \r\n" + 
			"  \r\n" + 
			"}";
	private String s="package ${packages};\r\n" + 
			"\r\n" + 
			"import org.springframework.stereotype.Service;\r\n" + 
			"import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;\r\n" + 
			"\r\n" + 
			"@Service\r\n" + 
			"public class ${className}Service extends EasyBoxBaseService<${className}>{\r\n" + 
			"	public static final String tabName =\"${tabName}\";\r\n" + 
			"	 @Override\r\n" + 
			"    public String getTableName() {\r\n" + 
			"        return tabName;\r\n" + 
			"    }\r\n" + 
			"    \r\n" + 
			"  \r\n" + 
			"}";
	private String c="package ${packages};\r\n" + 
			"\r\n" + 
			"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
			"import org.springframework.web.bind.annotation.RequestMapping;\r\n" + 
			"import org.springframework.web.bind.annotation.RestController;\r\n" + 
			"import org.yly.framework.easybox.mybatis.Controller.EasyBoxBaseController;\r\n" + 
			"\r\n" + 
			"@RestController\r\n" + 
			"@RequestMapping(\"${requestMapping}\")\r\n" + 
			"public class ${className}Controller extends EasyBoxBaseController<${className}> {\r\n" + 
			"\r\n" + 
			"  	@Autowired\r\n" + 
			"    private ${className}Service c${className}Service;\r\n" + 
			"\r\n" + 
			"    @Override\r\n" + 
			"    protected ${className}Service getService() {\r\n" + 
			"        return c${className}Service;\r\n" + 
			"    }\r\n" + 
			"  \r\n" + 
			"}";

}
