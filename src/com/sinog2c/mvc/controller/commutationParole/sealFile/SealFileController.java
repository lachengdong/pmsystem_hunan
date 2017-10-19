package com.sinog2c.mvc.controller.commutationParole.sealFile;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import DBstep.iMsgServer2000;

import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.PreviewPrintService;
import com.sinog2c.service.api.commutationParole.sealfile.SealFileService;
import com.sinog2c.util.common.MapUtil;

/**
 * 签章文件
 * @author liuyi
 *
 */

@Controller
@RequestMapping("/sealfile")
@SuppressWarnings("all")
public class SealFileController extends ControllerBase{
	private int mFileSize;
	  private byte[] mFileBody;
	  private String mFileName;
	  private String mFileType;
	  private String mFileDate;
	  private String mFileID;

	  private String mRecordID;
	  private String[] reconrID;
	  private String mTemplate;
	  private String mDateTime;
	  private String mOption;
	  private String mMarkName;
	  private String mPassword;
	  private String mMarkList;
	  private String mBookmark;
	  private String mDescript;
	  private String mHostName;
	  private String mMarkGuid;
	  private String mCommand;
	  private String mContent;
	  private String mHtmlName;
	  private String mDirectory;
	  private String mFilePath;

	  private String mUserName;
	  private int mColumns;
	  private int mCells;
	  private String mMyDefine1;
	  private String mLocalFile;
	  private String mRemoteFile;
	  private String mLabelName;
	  private String mImageName;
	  private String mTableContent;

	  private String Sql;

	  //打印控制
	  private String mOfficePrints;
	  private int mCopies;

	  //自定义信息传递
	  private String mInfo;

	  private DBstep.iMsgServer2000 MsgObj;
//	  private DBstep.iDBManager2000 DbaObj;

	@Autowired
	public SealFileService sealFileService;
	
	/**
	 * 跳向签章文件页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toSealFilePage")
	public ModelAndView toSealFilePage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("commutationParole/sealfile/sealFileManage");
		Calendar cal = Calendar.getInstance();
		mav.addObject("caseyear", cal.YEAR);
		SystemUser user = (SystemUser) WebUtils.getSessionAttribute(request, SESSION_USER_KEY);
		SystemOrganization org = user.getOrganization();
		mav.addObject("shortname", org.getShortname());//单位简称
		return mav;
	}
	
	/**
	 * 获取签章文件的列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSealFileList")
	@ResponseBody
	public Object getSealFileList(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		Object obj = sealFileService.getSealFileList(request,user);
		return obj;
	}
	
	/**
	 * 跳向签章文件页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toSinaguratePage")
	public ModelAndView toSinaguratePage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("commutationParole/sealfile/sinagurate");
		return mav;
	}
	
	/**
	 * 查看pdf文件页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPdfFilePage")
	public ModelAndView checkPdfFilePage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("commutationParole/sealfile/checkPdfFilePage");
		return mav;
	}
	
	/**
	 * 保存签章文件
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSealFile")
	public ModelAndView saveSealFile(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		iMsgServer2000 msgObj = new iMsgServer2000();
		msgObj.Load(request);
		byte[] aa = msgObj.MsgFileBody();
		int length = msgObj.MsgFileSize();
		
		System.out.print(length+"="+aa.length);
		return mav;
	}
//	/**
//	 * 方法描述：转到documentEdit页面
//	 * @version 2014年9月10日11:21:08
//	 * @return
//	 */
//	@RequestMapping("/documentEdit")
//	public ModelAndView documentEdit(){
//		ModelAndView maView = new ModelAndView("commutationParole/sealfile/documentEdit");
//		return maView;
//	}
	/**
	 * 方法描述：获取签章方案
	 * @version :2014年9月10日11:21:36
	 */
	@RequestMapping("/getSignScheme.json")
	@ResponseBody
	public Object getSignScheme(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		List<Map> listMaps = sealFileService.getSignScheme(request,user);
		listMaps = MapUtil.turnKeyToLowerCaseOfList(listMaps);
		JSONMessage message = JSONMessage.newMessage();
		message.setData(listMaps);
		return message;
	}
	/**
	 * 方法描述：签章更新成功以后 保存签章进程
	 * @version :2014年1月11日11:18:01
	 * @author mushuhong
	 */
	@RequestMapping(value = "/updateSignProgressToFlowBase")
	public String updateSignProgressToFlowBase(HttpServletRequest request){
		int count = sealFileService.updateSignProgressToFlowBase(request);
		return null;
	}
}
