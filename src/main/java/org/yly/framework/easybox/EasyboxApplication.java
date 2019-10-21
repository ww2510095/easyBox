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

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@EasyBoxScan(createTab = true)
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

    @RequestMapping("api/a")
    public Object a(HttpServletRequest mHttpServletRequest){
       System.out.println(Thread.currentThread().getName());

       return "1";
    }
    static int a =12345;
    @RequestMapping("/b")
    public String b() throws Exception{
        EasyBoxUser mMembe =new EasyBoxUser();
        mMembe.setUserName(System.currentTimeMillis()+"");
        mMembe.setId(System.currentTimeMillis()+"");
        a=a+1;
        mMembe.setPwd(a+"");
         mSysUserService.add(mMembe);
         return "添加完成";
    }

}
