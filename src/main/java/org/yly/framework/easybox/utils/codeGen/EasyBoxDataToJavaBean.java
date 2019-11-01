package org.yly.framework.easybox.utils.codeGen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class EasyBoxDataToJavaBean {
	private String path = System.getProperty("user.dir")+"\\src/";
	private String url;
	private String userName;
	private String pwd;
	public EasyBoxDataToJavaBean(String url,String userName,String pwd) {
		this.url=url;
		this.userName=userName;
		this.pwd=pwd;
	}
	private String packageName="";
	public void setPackageName(String packageName) {
		packageName=packageName.replace(".", "/");
		packageName=packageName.replace("\\", "/");
		packageName=packageName.replace("//", "/");
		if(packageName.indexOf("/")==0){
			packageName=packageName.substring(1,packageName.length());
		}
		if(packageName.indexOf("main/java/")!=0){
			packageName="main/java/"+packageName;
		}
		this.packageName = packageName;
	}
	/**
	 * 可以将表转化为javaBean
	 * */
	public void toJavaBean_oracle(String... tabNames){
		toJavaBean_oracle(null,null, tabNames);
	}
	public void toJavaBean_oracle(String ftlPath,String... tabNames){
		toJavaBean_oracle(null, tabNames);
	}
	public void toJavaBean_oracle(String ftlPath,String outPath,String... tabNames){
		if(EasyBoxStringUtil.isBlank(ftlPath)){
			toDefaultFtl();
			ftlPath=path+"/main/resources/easyFtl/";
		}
		if(EasyBoxStringUtil.isBlank(outPath)){
			outPath=path;
		}
		if(tabNames==null||tabNames.length==0){
			throw new EasyBoxCheckException("表名不可为空");
		}
		for (String string : tabNames) {
			outFile(ftlPath,outPath,string);
			outFile2(ftlPath,outPath,string);
			outFile3(ftlPath,outPath,string);
		}
	}
	private void toDefaultFtl() {
          EasyBoxDefaultFtl mEasyBoxDefaultFtl =new EasyBoxDefaultFtl();
          out("easyBoxService.ftl", mEasyBoxDefaultFtl.getS());
          out("easyBoxBean.ftl", mEasyBoxDefaultFtl.getB());
          out("easyBoxController.ftl", mEasyBoxDefaultFtl.getC());

	}
	private void out(String name,String data) {
		File mfile = new File(path+"/main/resources/easyFtl/");
        if(!mfile.isDirectory()){
        	mfile.mkdirs();
        }
        try {
            File f1 = new File(mfile+"/"+name);
            if (!f1.exists()){
                f1.getParentFile().mkdirs();
            }else {
            	return;
            }
            // 创建基于文件的输出流
            FileOutputStream fos = new FileOutputStream(f1);
            // 把数据写入到输出流
            fos.write(data.getBytes());
            // 关闭输出流
            fos.close();
            System.out.println("模板"+name+"创建完成");
        } catch (IOException e) {

            e.printStackTrace();

        }
	}
	private void outFile(String flPath,String outPath,String tabName){
	
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(flPath));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            String s1=_to(tabName);
            String s2 =s1.substring(0,1);
            s2=s2.toUpperCase()+s1.substring(1,s1.length());
            dataMap.put("packages",packageName.replace("/", ".").substring(10)+"."+_to(tabName));
            dataMap.put("className", s2);
            StringBuilder sb = new StringBuilder();
            List<TabKey> listTabKey = getData(tabName);
            for (TabKey tabKey : listTabKey) {//
            	sb.append("@EasyBoxParamsTitle(\""+tabKey.getComments()+"\")");
            	String number="";
            	if(tabKey.getDataType().equals("NUMBER")){
            		number="number = true,";
            	}
            	sb.append("\n\t");
            	sb.append("@EasyBoxColumn(\""+tabKey.getColumnName()+"\")");
            	sb.append("\n\t");
            	int a =tabKey.getDataLength();
            	if(tabKey.getDataLength()>1){
            		a=a-1;
            	}
            	sb.append("@EasyBoxCheckJavaBean("+number+"maxLength = "+a+",minLength= "+(a>1?2:1)+")");
            	sb.append("\n\t");
            	sb.append("private String ");
            	sb.append(_to(tabKey.getColumnName()));
            	sb.append(";\n\t");
            	sb.append("\n\t");
            }
            dataMap.put("beanData", sb.toString());
            // step4 加载模版文件
            Template template = configuration.getTemplate("easyBoxBean.ftl");
            File mfile = new File(outPath + "\\" +packageName+"\\"+_to(tabName));
            if(!mfile.isDirectory()){
            	mfile.mkdirs();
            }
           
            // step5 生成数据
            File docFile = new File(outPath + "\\" +packageName+"\\"+ _to(tabName)+"\\"+s2+".java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^Bean创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		
	}
	private void outFile2(String flPath,String outPath,String tabName){
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(flPath));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            String s1=_to(tabName);
            String s2 =s1.substring(0,1);
            s2=s2.toUpperCase()+s1.substring(1,s1.length());
            dataMap.put("packages",packageName.replace("/", ".").substring(10)+"."+ _to(tabName));
            dataMap.put("className", s2);
            dataMap.put("tabName", tabName.toUpperCase());
            // step4 加载模版文件
            Template template = configuration.getTemplate("easyBoxService.ftl");
            File mfile = new File(outPath + "\\" +packageName+"\\"+_to(tabName));
            if(!mfile.isDirectory()){
            	mfile.mkdirs();
            }
           
            // step5 生成数据
            File docFile = new File(outPath + "\\" + packageName+"\\"+_to(tabName)+"\\"+s2+"Service.java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^Service创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    
		
	}
	private void outFile3(String flPath,String outPath,String tabName){
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(flPath));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            String s1=_to(tabName);
            String s2 =s1.substring(0,1);
            s2=s2.toUpperCase()+s1.substring(1,s1.length());
            dataMap.put("packages", packageName.replace("/", ".").substring(10)+"."+_to(tabName));
            dataMap.put("className", s2);
            dataMap.put("requestMapping", _to(tabName));
            
            // step4 加载模版文件
            Template template = configuration.getTemplate("easyBoxController.ftl");
            File mfile = new File(outPath + "\\" +packageName+"\\"+_to(tabName));
            if(!mfile.isDirectory()){
            	mfile.mkdirs();
            }
           
            // step5 生成数据
            File docFile = new File(outPath + "\\" + packageName+"\\"+_to(tabName)+"\\"+s2+"Controller.java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^easyBoxController创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    
		
	}
	
	public static void main(String[] args) {
		   String url = "jdbc:oracle:thin:@218.70.107.85:1521:ORCL"; //链接字符串
		   String[] s ={"sys_user"};
		   EasyBoxDataToJavaBean mEasyBoxDataToJavaBean = new EasyBoxDataToJavaBean( url,"ares","ares");
		   mEasyBoxDataToJavaBean.setPackageName("org.yly.framework.easybox.utils.codeGen");
		   mEasyBoxDataToJavaBean.toJavaBean_oracle(s);
	}
	
	
	 public   List<TabKey> getData(String tabName) {
	        String driver = "oracle.jdbc.OracleDriver";    //驱动标识符
	        Connection con = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;

	        try {
	            Class.forName(driver);
	            con = DriverManager.getConnection(url,userName, pwd);
	            String sql = "select  user_col_comments.COLUMN_NAME, COMMENTS,DATA_TYPE,"
	            		+ "NVL(DATA_PRECISION,DATA_LENGTH)DATA_LENGTH from user_col_comments "
	            		+ "left join user_tab_columns on user_tab_columns.TABLE_NAME "
	            		+ "=user_col_comments.TABLE_NAME  AND user_tab_columns.COLUMN_NAME =user_col_comments.COLUMN_NAME  "
	            		+ "where user_tab_columns.TABLE_NAME='"+tabName.toUpperCase()+"' ORDER BY COLUMN_ID ";
	            pstm =con.prepareStatement(sql);
	            rs = pstm.executeQuery();
	            List<TabKey> listTabKey = new ArrayList<>();
	            while(rs.next()) {
	            	TabKey mTabKey = new TabKey();
	            	mTabKey.setColumnName(rs.getString("COLUMN_NAME"));
	            	mTabKey.setComments(rs.getString("COMMENTS")==null?rs.getString("COLUMN_NAME"):rs.getString("COMMENTS"));
	            	mTabKey.setDataLength(rs.getInt("DATA_LENGTH"));
	            	mTabKey.setDataType(rs.getString("DATA_TYPE"));
	            	listTabKey.add(mTabKey);
	            }
	            return listTabKey;

	        } catch(Exception e) {
	        	e.printStackTrace();
	        	 throw new EasyBoxCheckException(e.getMessage());
	        }
	}
	 /**
	  * 将数据库命名法转为驼峰命名法
	  * */
	private String _to(String s){
		s=s.toLowerCase();
		String[] strs =s.split("_");
		String skey ="";
		for (String string : strs) {
			String s2 =string.substring(0,1);
            s2=s2.toUpperCase()+string.substring(1,string.length());
            skey=skey+s2;
		}
		String s2 =skey.substring(0,1);
        s2=s2.toLowerCase()+skey.substring(1,skey.length());
		return s2;
	}
}
