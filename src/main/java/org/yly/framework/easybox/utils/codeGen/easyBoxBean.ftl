package ${packages};

import lombok.Data;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.utils.codeGen.EasyBoxCodeGenTab;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;

@Data
@EasyBoxCodeGenTab(${className}Service.tabName)
public class ${className} extends EasyBoxBaseBean{

	${beanData}
    
  
}