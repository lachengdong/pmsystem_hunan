package com.sinog2c.util.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class FreeMarkerUtil {
	/**
	 * 交换数据时，freemarker处理xml模板
	 * */

	public static  String createXml(HttpServletRequest request) {
		//获取文件在服务器的路径
		String tomcatRoot = request.getSession().getServletContext().getRealPath("/");  
        String[] foo = tomcatRoot.split("\\\\");//截取反斜杠时，是4个'\',22个事不行的
        StringBuilder tomcatWebAppsBuilder = new StringBuilder();  
        for(int i =0;i<foo.length;i++){  
        	if(i==0){
        		tomcatWebAppsBuilder.append(foo[i]+"\\");
        	}else{
        		tomcatWebAppsBuilder.append(foo[i]+"/");  
        	}
        }  
        String tomcatWebApps = tomcatWebAppsBuilder.toString();
        
		GetProperty promodel = new GetProperty();
		Properties pro = promodel.bornProp(GkzxCommon.DATABASETYPE, null);
		
		//模板路径
		String templatePath = tomcatWebApps+pro.getProperty("templatePath");
		//生成文件路径，路径自己决定
		String generateFilePath = pro.getProperty("generateFilePath");
		String templateName = pro.getProperty("templateName");
		String name = pro.getProperty("name");
		String age = pro.getProperty("age");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		
		//map中的数据是要现实在模板上的数据，一下变量金座参考而已
		Map dataMap = new HashMap();
		dataMap.put("name",name);
		dataMap.put("age",age);
		dataMap.put("date",df.format(new Date()));
		Configuration configuration = new Configuration();
		try {
			configuration
					.setDirectoryForTemplateLoading(new File(templatePath));
		} catch (IOException e2) {
			e2.printStackTrace(); 
		}
		Template t = null;
		try {
			t = configuration.getTemplate(templateName, "utf-8"); // 文件名
		} catch (IOException e) {
			e.printStackTrace();
		}
		File outFile = new File(generateFilePath + "/outFilessa"
				+ Math.random() * 10000 + ".xml");
		String returnPath = outFile.getParentFile().getPath();
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "UTF-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			t.process(dataMap, out);

		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnPath;
	}
	/**
     * 压缩文件分割
     * file：文件。
     * size：长度。
     */
    public static void splitFile(File file,int size) throws Exception{
        if(size<=0){
            size = 1024;
        }
        if(!file.isFile()){
            throw new Exception("file not exists"+file.getAbsolutePath());
        }
        String filename = file.getAbsolutePath();
        File filetmp = new File(filename+"_"+0+".zip");
        if(filetmp.isFile()){
            throw new Exception("file exists"+filetmp.getAbsolutePath());
        }
        
        byte[] buf = new byte[1024*20*1024];//长度要给够
        FileInputStream fis = new FileInputStream(file);
        int readsize = 0;
        int pos = 0;
        int k = 0;
        int m = -1;
        File fileout = null;
        FileOutputStream fos = null;
        while((readsize = fis.read(buf, 0, buf.length))>0){
            
            if(k!=m)
            {
                if(fos!=null){
                    fos.close();
                    fos = null;
                }
                m = k;
                fileout = new File(filename+"_"+k+".zip");
                fos = new FileOutputStream(fileout);
            }
            fos.write(buf,0,readsize);
            fos.flush();
            pos += readsize;
            if(pos>size*(k+1)){
                k++;
            }
        }
        if(fos!=null){
            fos.close();
            fos = null;
        }
        fis.close();
    }


//合并文件
    public static void combination(File file) throws Exception{
        String filename = file.getAbsolutePath();
        File fileout = new File(filename);
        
        if(fileout.isFile()){
            throw new Exception("file exists"+fileout.getAbsolutePath());
        }
        FileOutputStream fos = new FileOutputStream(fileout);
        int k = 0;
        File filein = null;
        FileInputStream fis = null;
        byte[] buf = new byte[1024*20*1024];//读取的长度
        while(true){
            if(fis!=null){
                fis.close();
                fis = null;
            }
            filein = new File(filename+"_"+k+".zip");
            if(!filein.isFile()){
                break;
            }
            fis = new FileInputStream(filein);
            int readsize = 0;
            while((readsize = fis.read(buf, 0, buf.length))>0){
                fos.write(buf,0,readsize);
                fos.flush();
            }
            k++;
        }
        if(fis!=null){
            fis.close();
            fis = null;
        }
        fos.close();
    }

}
