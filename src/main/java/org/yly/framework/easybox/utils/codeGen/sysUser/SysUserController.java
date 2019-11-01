package org.yly.framework.easybox.utils.codeGen.sysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yly.framework.easybox.mybatis.Controller.EasyBoxBaseController;

@RestController
@RequestMapping("sysUser")
public class SysUserController extends EasyBoxBaseController<SysUser> {

  	@Autowired
    private SysUserService cSysUserService;

    @Override
    protected SysUserService getService() {
        return cSysUserService;
    }
  
}