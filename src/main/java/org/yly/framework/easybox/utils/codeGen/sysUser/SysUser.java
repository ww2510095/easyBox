package org.yly.framework.easybox.utils.codeGen.sysUser;

import lombok.Data;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.utils.codeGen.EasyBoxCodeGenTab;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxColumn;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;

@Data
@EasyBoxCodeGenTab(SysUserService.tabName)
public class SysUser extends EasyBoxBaseBean{

	@EasyBoxParamsTitle("编号")
	@EasyBoxColumn("ID")
	@EasyBoxCheckJavaBean(notNull = true,maxLength = 63,minLength= 2)
	private String id;
	
	@EasyBoxParamsTitle("归属公司")
	@EasyBoxColumn("COMPANY_ID")
	@EasyBoxCheckJavaBean(maxLength = 63,minLength= 2)
	private String companyId;
	
	@EasyBoxParamsTitle("归属部门")
	@EasyBoxColumn("OFFICE_ID")
	@EasyBoxCheckJavaBean(maxLength = 63,minLength= 2)
	private String officeId;
	
	@EasyBoxParamsTitle("登录名")
	@EasyBoxColumn("LOGIN_NAME")
	@EasyBoxCheckJavaBean(maxLength = 99,minLength= 2)
	private String loginName;
	
	@EasyBoxParamsTitle("密码")
	@EasyBoxColumn("PASSWORD")
	@EasyBoxCheckJavaBean(maxLength = 99,minLength= 2)
	private String password;
	
	@EasyBoxParamsTitle("工号")
	@EasyBoxColumn("NO")
	@EasyBoxCheckJavaBean(maxLength = 99,minLength= 2)
	private String no;
	
	@EasyBoxParamsTitle("姓名")
	@EasyBoxColumn("NAME")
	@EasyBoxCheckJavaBean(maxLength = 199,minLength= 2)
	private String name;
	
	@EasyBoxParamsTitle("邮箱")
	@EasyBoxColumn("EMAIL")
	@EasyBoxCheckJavaBean(maxLength = 254,minLength= 2)
	private String email;
	
	@EasyBoxParamsTitle("电话")
	@EasyBoxColumn("PHONE")
	@EasyBoxCheckJavaBean(maxLength = 14,minLength= 2)
	private String phone;
	
	@EasyBoxParamsTitle("手机")
	@EasyBoxColumn("MOBILE")
	@EasyBoxCheckJavaBean(maxLength = 14,minLength= 2)
	private String mobile;
	
	@EasyBoxParamsTitle("用户类型,0:后管+客户端，1：客户端，2：后管")
	@EasyBoxColumn("USER_TYPE")
	@EasyBoxCheckJavaBean(maxLength = 1,minLength= 1)
	private String userType;
	
	@EasyBoxParamsTitle("最后登陆IP")
	@EasyBoxColumn("LOGIN_IP")
	@EasyBoxCheckJavaBean(maxLength = 14,minLength= 2)
	private String loginIp;
	
	@EasyBoxParamsTitle("最后登陆时间")
	@EasyBoxColumn("LOGIN_DATE")
	@EasyBoxCheckJavaBean(maxLength = 10,minLength= 2)
	private String loginDate;
	
	@EasyBoxParamsTitle("创建者")
	@EasyBoxColumn("CREATE_BY")
	@EasyBoxCheckJavaBean(maxLength = 63,minLength= 2)
	private String createBy;
	
	@EasyBoxParamsTitle("创建时间")
	@EasyBoxColumn("CREATE_DATE")
	@EasyBoxCheckJavaBean(maxLength = 10,minLength= 2)
	private String createDate;
	
	@EasyBoxParamsTitle("更新者")
	@EasyBoxColumn("UPDATE_BY")
	@EasyBoxCheckJavaBean(maxLength = 63,minLength= 2)
	private String updateBy;
	
	@EasyBoxParamsTitle("更新时间")
	@EasyBoxColumn("UPDATE_DATE")
	@EasyBoxCheckJavaBean(maxLength = 10,minLength= 2)
	private String updateDate;
	
	@EasyBoxParamsTitle("备注信息")
	@EasyBoxColumn("REMARKS")
	@EasyBoxCheckJavaBean(maxLength = 254,minLength= 2)
	private String remarks;
	
	@EasyBoxParamsTitle("删除标记")
	@EasyBoxColumn("DEL_FLAG")
	@EasyBoxCheckJavaBean(maxLength = 1,minLength= 1)
	private String delFlag;
	
	@EasyBoxParamsTitle("设备类型")
	@EasyBoxColumn("DEVICE_MODEL")
	@EasyBoxCheckJavaBean(maxLength = 99,minLength= 2)
	private String deviceModel;
	
	@EasyBoxParamsTitle("设备系统")
	@EasyBoxColumn("DEVICE_SYSTEM")
	@EasyBoxCheckJavaBean(maxLength = 99,minLength= 2)
	private String deviceSystem;
	
	@EasyBoxParamsTitle("性别：M男F女")
	@EasyBoxColumn("USER_SEX")
	@EasyBoxCheckJavaBean(maxLength = 1,minLength= 1)
	private String userSex;
	
	@EasyBoxParamsTitle("密码重置标志")
	@EasyBoxColumn("RSET_PSW_MARK")
	@EasyBoxCheckJavaBean(number = true,maxLength = 4,minLength= 2)
	private String rsetPswMark;
	
	@EasyBoxParamsTitle("首次密码标志")
	@EasyBoxColumn("USE_FIRST_MARK")
	@EasyBoxCheckJavaBean(number = true,maxLength = 4,minLength= 2)
	private String useFirstMark;
	
	@EasyBoxParamsTitle("用户状态")
	@EasyBoxColumn("USER_STAUS")
	@EasyBoxCheckJavaBean(number = true,maxLength = 4,minLength= 2)
	private String userStaus;
	
	@EasyBoxParamsTitle("销户日期")
	@EasyBoxColumn("USER_END_DATE")
	@EasyBoxCheckJavaBean(maxLength = 19,minLength= 2)
	private String userEndDate;
	
	@EasyBoxParamsTitle("密码错误次数")
	@EasyBoxColumn("ERR_LGN_CNT")
	@EasyBoxCheckJavaBean(number = true,maxLength = 4,minLength= 2)
	private String errLgnCnt;
	
	@EasyBoxParamsTitle("用户类型")
	@EasyBoxColumn("USER_TYP")
	@EasyBoxCheckJavaBean(maxLength = 1,minLength= 1)
	private String userTyp;
	
	@EasyBoxParamsTitle("用户头像")
	@EasyBoxColumn("HEAD_IMG")
	@EasyBoxCheckJavaBean(maxLength = 254,minLength= 2)
	private String headImg;
	
	@EasyBoxParamsTitle("LOGINTOKEN")
	@EasyBoxColumn("LOGINTOKEN")
	@EasyBoxCheckJavaBean(maxLength = 31,minLength= 2)
	private String logintoken;
	
	@EasyBoxParamsTitle("FINGERPRINT")
	@EasyBoxColumn("FINGERPRINT")
	@EasyBoxCheckJavaBean(maxLength = 99,minLength= 2)
	private String fingerprint;
	
	@EasyBoxParamsTitle("授权密码错误次数")
	@EasyBoxColumn("AUTH_ERROR")
	@EasyBoxCheckJavaBean(number = true,maxLength = 2,minLength= 2)
	private String authError;
	
	
    
  
}