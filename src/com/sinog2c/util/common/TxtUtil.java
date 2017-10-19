package com.sinog2c.util.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtUtil {
	
	private static final String  tabStr = "\t";
	
	public static void main(String[] args) {
		String txtFilePath = "d:/新建文本文档.txt";
		String charset="UTF-8";
//		List<Map<String,String>> result = readTxtFile(txtFilePath,charset);
//		for(Map<String,String> map : result){
//			System.out.println(map.get("uuid") + "---------" + map.get("dir") + "---------" +map.get("passwd"));
//		}
		
//		StringBuilder sb = new StringBuilder();
//		sb.append("uuid").append("\t").append("文件夹名称，内含压缩文件的的分包...").append("\t").append("压缩文件的解压密码...");
//		String content = sb.toString();
//		writeAppend(txtFilePath, content, null);
		
    }
	
	
	/**
	 * @Description: 读文件内容	内容格式为： str	str(uuid+'\t'字符串+'\t'+字符串)
	 * @param	txtFilePath 文件的路径
	 * @return List<Map<String,String>>
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午04:33:07
	 * @Version 3.1
	 */
    public static List<Map<String,String>> readTxtFile(String txtFilePath,String charset){
    	
    	List<Map<String,String>> result = new ArrayList<Map<String,String>>();
    	BufferedReader bufferedReader = null;
        try {
            File file=new File(txtFilePath);
            InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file),charset);//考虑到编码格式
            bufferedReader= new BufferedReader(streamReader);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
            	if(! StringNumberUtil.isEmpty(lineTxt)){
            		String[] lineStr = lineTxt.trim().split(tabStr);
            		if(3 == lineStr.length){
            			Map<String,String> map = new HashMap<String,String>();
            			map.put("uuid", lineStr[0]);
            			map.put("dir", lineStr[1]);
            			map.put("passwd", lineStr[2]);
            			result.add(map);
            		}
//            		System.out.println(lineTxt);
            	}
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("读取txt文件内容失败！");
        }finally{
        	try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return result;
    }
    
    
public static List<String> readTxtFileByLine(String txtFilePath,String charset){
    	
    	List<String> result = new ArrayList<String>();
    	BufferedReader bufferedReader = null;
        try {
            File file=new File(txtFilePath);
            InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file),charset);//考虑到编码格式
            bufferedReader= new BufferedReader(streamReader);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
            	if(StringNumberUtil.notEmpty(lineTxt)){
            		result.add(lineTxt);
            	}
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("读取txt文件内容失败！");
        }finally{
        	try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return result;
    }
    
    
    /**
     * @Description: 往txt文件中写数据，若文件存在，则追加内容，不存在则创建txt文件并写入内容，写完后追加换行，以方便下次写内容
     * @param	path 文件绝对路径 如 c:\test.txt
     * 			content 写入内容
     * 			charset 字符集，若为空,则默认采用字符集UTF-8
     * @return void  
     * @throws
     * @author 杨祖榕
     * @date 2016-6-4  下午05:49:41
     * @Version 3.1
     */
	public static void writeAppend(String filepath, String content, String charset){
		//
		charset = StringNumberUtil.isEmpty(charset) ? "UTF-8" : charset.trim();
		PrintWriter printWriter = null;
		try{
			FileOutputStream fos = new FileOutputStream(filepath,true);
			OutputStreamWriter outWriter = new OutputStreamWriter(fos, charset);
			printWriter = new PrintWriter(outWriter);
			printWriter.println(content);
			printWriter.flush();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("操作txt文件失败！");
		}finally {
			if (null != printWriter){
				printWriter.close();
			}
		}
	}

}
