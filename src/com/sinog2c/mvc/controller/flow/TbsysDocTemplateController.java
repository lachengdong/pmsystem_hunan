package com.sinog2c.mvc.controller.flow;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.Tbsys_doctemplate;
import com.sinog2c.model.system.Tbsys_doctemplateWithBLOBs;
import com.sinog2c.mvc.controller.LogManage;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.TbsysDocTemplateService;
import com.sinog2c.util.common.RequestUtil;


@Controller
@RequestMapping("/doccftpl")
public class TbsysDocTemplateController {

	@Resource
	TbsysDocTemplateService tbsysDocTemplateService;

	/**
	 * 方法描述：获取公文模板数据列表
	 */
	@RequestMapping(value = "/getdocTemplateList")
	@ResponseBody
	public JSONMessage<Tbsys_doctemplate> getdocTemplateList(
			HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		String departid = user.getDepartid();
		Map<String, Object> map = RequestUtil
				.parseParamMapForPagination(request);
		map.put("departid", departid);
		// 查询出所有数据集合
		JSONMessage<Tbsys_doctemplate> message = this.tbsysDocTemplateService
				.getDocTemplateBydept(map);
		return message;
	}

	@RequestMapping(value = "/saveDocTemplateWiththum")
	@ResponseBody
	public String saveDocTemplateWiththum(
			DefaultMultipartHttpServletRequest request)
			throws UnsupportedEncodingException {

		SystemUser user = ControllerBase.getLoginUser(request);
		CommonsMultipartFile file = (CommonsMultipartFile) request
				.getFile("Filethum");
		CommonsMultipartFile file2 = (CommonsMultipartFile) request
				.getFile("FileBlod");
		Tbsys_doctemplateWithBLOBs doctemp = JSON.parseObject(
				request.getParameter("data"), Tbsys_doctemplateWithBLOBs.class);
		doctemp.setThumbnail(file.getBytes());
		doctemp.setThumfiletype(file.getContentType());
		doctemp.setContent(file2.getBytes());
		doctemp.setFiletype(file2.getContentType());
		doctemp.setOpid(user.getUserid());
		doctemp.setDepartid(user.getDepartid());

		if (doctemp.getActiontype().equalsIgnoreCase("new")) {
			doctemp.setDoctempid(java.util.UUID.randomUUID().toString()
					.replace("-", ""));
			this.tbsysDocTemplateService.insertSelective(doctemp);
			LogManage.getInstance().AddLog(doctemp,
					LogManage.getInstance().ADD, request);
		} else {
			this.tbsysDocTemplateService.updateByPrimaryKeySelective(doctemp);
			LogManage.getInstance().AddLog(doctemp,
					LogManage.getInstance().UPDATE, request);
		}
		return "OK";
	}

	@RequestMapping(value = "/getdocTemplateContent")
	public void getdocTemplateContent(HttpServletRequest request,
			@RequestParam("doctempid") String doctempid,
			HttpServletResponse response) {
		Tbsys_doctemplateWithBLOBs doc = this.tbsysDocTemplateService
				.selectByPrimaryKey(doctempid);
		if (doc != null && doc.getContent() != null) {
			OutputStream os;
			try {
				os = response.getOutputStream();
				response.setContentType(doc.getFiletype());
				os.write(doc.getContent());
				os.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/doSaveDocTemplate")
	@ResponseBody
	public String doSaveDocTemplate(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		Tbsys_doctemplateWithBLOBs doctemp = JSON.parseObject(
				request.getParameter("data"), Tbsys_doctemplateWithBLOBs.class);
		if (doctemp.getActiontype().equalsIgnoreCase("new")) {
			doctemp.setOpid(user.getUserid());
			doctemp.setDepartid(user.getDepartid());
			doctemp.setDoctempid(java.util.UUID.randomUUID().toString()
					.replace("-", ""));
			doctemp.setFiletype("image/jpeg");
			this.tbsysDocTemplateService.insertSelective(doctemp);
			LogManage.getInstance().AddLog(doctemp,
					LogManage.getInstance().ADD, request);
		} else {
			this.tbsysDocTemplateService.updateByPrimaryKeySelective(doctemp);
			LogManage.getInstance().AddLog(doctemp,
					LogManage.getInstance().UPDATE, request);
		}
		return "OK";
	}

	/**
	 * 删除公文模板信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/removeDocTemplate.action", method = RequestMethod.POST)
	@ResponseBody
	public int removeDocTemplate(HttpServletRequest request,
			@RequestParam(value = "doctempid", required = true) String doctempid) {
		int rows = this.tbsysDocTemplateService.deleteByPrimaryKey(doctempid);
		try {
			Tbsys_doctemplateWithBLOBs data = new Tbsys_doctemplateWithBLOBs();
			data.setDoctempid(doctempid);
			LogManage.getInstance().AddLog(data,
					LogManage.getInstance().DELETE, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

	@RequestMapping(value = "/toDocTempEditPage.action", method = RequestMethod.GET)
	public ModelAndView getView(
			HttpServletRequest request,
			@RequestParam(value = "doctempid", required = false) String doctempid,
			@RequestParam("actiontype") String actiontype) {
		ModelAndView mav = new ModelAndView("cdoc/TbsysDocTemplateEdit");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		// if (actiontype.equalsIgnoreCase("edit")) {
		// Tbsys_doctemplateWithBLOBs doc = this.tbsysDocTemplateService
		// .selectByPrimaryKey(doctempid);
		// String data = JSON.toJSONString(doc);
		// mav.addObject("data", data);
		// }
		mav.addObject("doctempid", doctempid);
		mav.addObject("path", path);
		mav.addObject("basePath", basePath);
		mav.addObject("actiontype", actiontype);
		return mav;
	}

	@RequestMapping(value = "/uploadDocTempFile")
	@ResponseBody
	public String uploadDocTempFile(DefaultMultipartHttpServletRequest request)
			throws UnsupportedEncodingException {
		SystemUser user =ControllerBase.getLoginUser(request);
		String fileName = request.getParameter("fileName");
		if (fileName.equalsIgnoreCase("filethum")) {
			CommonsMultipartFile file = (CommonsMultipartFile) request
					.getFile("Filethum");
			RequestUtil.setSessionAttribute(request, user.getUserid()
					+ "_docfilethum", file.getBytes());
			RequestUtil.setSessionAttribute(request, user.getUserid()
					+ "_docfilethumType", file.getContentType());
		} else {
			CommonsMultipartFile file = (CommonsMultipartFile) request
					.getFile("FileBlod");
			RequestUtil.setSessionAttribute(request, user.getUserid()
					+ "_docfilecontent", file.getBytes());
			RequestUtil.setSessionAttribute(request, user.getUserid()
					+ "_docfilecontentType", file.getContentType());
		}
		return "OK";
	}

	@RequestMapping(value = "/doSaveDocTemplateInfo")
	@ResponseBody
	public String doSaveDocTemplateInfo(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		Tbsys_doctemplateWithBLOBs doctemp = JSON.parseObject(
				request.getParameter("data"), Tbsys_doctemplateWithBLOBs.class);

		doctemp.setOpid(user.getUserid());
		doctemp.setDepartid(user.getDepartid());
		doctemp.setThumbnail((byte[]) RequestUtil.getSessionAttribute(request,
				String.format("%s_docfilethum", user.getUserid())));
		doctemp.setThumfiletype(RequestUtil.getSessionAttribute(request,
				String.format("%s_docfilethumType", user.getUserid()))
				.toString());
		doctemp.setContent((byte[]) RequestUtil.getSessionAttribute(request,
				String.format("%s_docfilecontent", user.getUserid())));
		doctemp.setFiletype(RequestUtil.getSessionAttribute(request,
				String.format("%s_docfilecontentType", user.getUserid()))
				.toString());
		RequestUtil.setSessionAttribute(request, user.getUserid()
				+ "_docfilethum", null);
		RequestUtil.setSessionAttribute(request, user.getUserid()
				+ "_docfilethumType", null);
		RequestUtil.setSessionAttribute(request, user.getUserid()
				+ "_docfilecontent", null);
		RequestUtil.setSessionAttribute(request, user.getUserid()
				+ "_docfilecontentType", null);
		doctemp.setOpid(user.getUserid());
		doctemp.setDepartid(user.getDepartid());
		if (doctemp.getActiontype().equalsIgnoreCase("new")) {
			doctemp.setDoctempid(java.util.UUID.randomUUID().toString()
					.replace("-", ""));
			this.tbsysDocTemplateService.insertSelective(doctemp);
			LogManage.getInstance().AddLog(doctemp,
					LogManage.getInstance().ADD, request);
		} else {
			this.tbsysDocTemplateService.updateByPrimaryKeySelective(doctemp);
			LogManage.getInstance().AddLog(doctemp,
					LogManage.getInstance().UPDATE, request);
		}
		return "OK";
	}

	@RequestMapping(value = "getTempThum")
	public void getTempThum(HttpServletRequest request,
			@RequestParam("doctempid") String doctempid,
			HttpServletResponse response) throws Exception {
		SystemUser user = ControllerBase.getLoginUser(request);

		Object obj = RequestUtil.getSessionAttribute(request, user.getUserid()
				+ "_doctempList");
		Tbsys_doctemplateWithBLOBs doc = null;
		if (obj != null) {
			@SuppressWarnings("unchecked")
			List<Tbsys_doctemplateWithBLOBs> list = (List<Tbsys_doctemplateWithBLOBs>) obj;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getDoctempid().equalsIgnoreCase(doctempid)) {
					doc = list.get(i);
					break;
				}
			}
		}
		if (doc == null) {
			doc = this.tbsysDocTemplateService.selectByPrimaryKey(doctempid);
		}

		if (doc != null && doc.getThumbnail() != null) {
			OutputStream os = response.getOutputStream();
			response.setContentType(doc.getThumfiletype());
			os.write(doc.getThumbnail());
			os.flush();
		}
	}

	

}
