package org.yly.framework.easybox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yly.framework.easybox.base.log.config.EasyBoxLogAutoConfiguration;
import org.yly.framework.easybox.base.user.EasyBoxUser;
import org.yly.framework.easybox.base.user.EasyBoxUserService;
import org.yly.framework.easybox.init.EasyBoxScan;

@SpringBootApplication
@EasyBoxScan(beanUrl = {"org.yly.framework.easybox"})
@RestController
public class EasyboxApplication {
    @Autowired
    private EasyBoxUserService mSysUserService;
    @Autowired
    private EasyBoxLogAutoConfiguration mEasyBoxLogAutoConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(EasyboxApplication.class, args);
        System.out.println("===========");
    }

    @RequestMapping("/a")
    public Object a(){
        System.out.println("==========="+ mEasyBoxLogAutoConfiguration.isSave());

        EasyBoxUser mMembe =new EasyBoxUser();
        mMembe.setPwd("1234");
        return mSysUserService.getAll(mMembe,1,10);
    }
    static int a =12345;
    @RequestMapping("/b")
    public String b() throws Exception{
        EasyBoxUser mMembe =new EasyBoxUser();
        mMembe.setUname(System.currentTimeMillis()+"");
        mMembe.setId(System.currentTimeMillis()+"");
        a=a+1;
        mMembe.setPwd(a+"");
         mSysUserService.add(mMembe);
         return "添加完成";
    }

}
