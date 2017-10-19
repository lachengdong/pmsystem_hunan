package com.sinog2c.mvc.controller;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.config.SpringContextHolder;
import com.sinog2c.model.BlueOALogAnnotation;
import com.sinog2c.model.RequireLog;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemLogService;



public class LogManage {
	private static LogManage instance = null;
	private SystemLogService logService;
	public final String ADD = "新增";
	public final String UPDATE = "更新";
	public final String DELETE = "删除";

	protected LogManage() {

	}

	public static LogManage getInstance() {
		if (instance == null) {
			synchronized (LogManage.class) {
				if (instance == null) {
					instance = new LogManage();
					instance.logService = SpringContextHolder
							.getBean("systemLogService");
				}
			}
		}
		return instance;
	}

	// public

	public <T> void AddLog(T obj, String operate, HttpServletRequest request) {
		String Logtype = "%s%s操作";
		String opcontent = "%s%s";
		String remark = operate + "成功 !";
		short status = 1;

		Class<?> cls = obj.getClass();

		if (cls.isAnnotationPresent(BlueOALogAnnotation.class)) {
			BlueOALogAnnotation mda = null;
			mda = cls.getAnnotation(BlueOALogAnnotation.class);
			String description = mda.Description();
			Logtype = String.format(Logtype, description, operate);
			opcontent = String.format(opcontent, description, operate);
		} else {
			String description = cls.getSimpleName();
			Logtype = String.format(Logtype, description, operate);
			opcontent = String.format(opcontent, description, operate);
		}
		opcontent = this.setOpcontent(obj, cls, opcontent);
		Class<?> cls2 = cls.getSuperclass();
		if (!cls2.equals(java.lang.Object.class)) {
			opcontent = this.setOpcontent(obj, cls2, opcontent);
		}
		SystemUser user = ControllerBase.getLoginUser(request);

		SystemLog log = new SystemLog();// 日志
		log.setLogtype(Logtype);
		log.setOpaction(operate);
		log.setOpcontent(opcontent);
		log.setRemark(remark);
		log.setStatus(status);
		logService.add(log, user);

	}

	private <T> String setOpcontent(T obj, Class<?> cls, String Opcontent) {
		Field[] fields;
		fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(RequireLog.class)) {
				try {
					fields[i].setAccessible(true);
					Object value = fields[i].get(obj);
					if (value != null) {
						Opcontent = Opcontent + "," + fields[i].getName() + "="
								+ fields[i].get(obj).toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return Opcontent;
	}

}
