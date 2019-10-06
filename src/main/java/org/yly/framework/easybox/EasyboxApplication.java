package org.yly.framework.easybox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yly.framework.easybox.init.EasyBoxScan;
import org.yly.framework.easybox.task.Member;
import org.yly.framework.easybox.task.Members;

@SpringBootApplication
@EasyBoxScan
@RestController
public class EasyboxApplication {
    @Autowired
    private Members mMembers;

    public static void main(String[] args) {
        SpringApplication.run(EasyboxApplication.class, args);
        System.out.println("===========");
    }

    @RequestMapping("/a")
    public Object a(){
        Member mMembe =new Member();
        mMembe.setPwd("1234");
        return mMembers.getAll(mMembe,1,10);
    }
    static int a =12345;
    @RequestMapping("/b")
    public String b() throws Exception{
        Member mMembe =new Member();
        mMembe.setUname(System.currentTimeMillis()+"");
        mMembe.setId(System.currentTimeMillis()+"");
        a=a+1;
        mMembe.setPwd(a+"");
         mMembers.add(mMembe);
         return "添加完成";
    }

}
