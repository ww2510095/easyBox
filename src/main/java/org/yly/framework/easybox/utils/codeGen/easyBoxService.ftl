package ${packages};

import org.springframework.stereotype.Service;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;

@Service
public class ${className}Service extends EasyBoxBaseService<${className}>{
	public static final String tabName ="${tabName}";
	 @Override
    public String getTableName() {
        return tabName;
    }
    
  
}