package org.yly.framework.easybox.utils.codeGen.sysUser;

import org.springframework.stereotype.Service;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;

@Service
public class SysUserService extends EasyBoxBaseService<SysUser>{
	public static final String tabName ="SYS_USER";
	 @Override
    public String getTableName() {
        return tabName;
    }
    
  
}