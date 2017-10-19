package com.sinog2c.service.api.system;

import javax.servlet.http.HttpServletRequest;

public interface CourtVideoSessionService {

	Object getVideoSessionList(HttpServletRequest request);

	Object getVideoSessionDepartments(HttpServletRequest request);

	Object updateVideoSession(HttpServletRequest request);

	Object insertVideoSession(HttpServletRequest request);

	Object getVideoSessionById(HttpServletRequest request);

	Object deleteVideoSessionById(HttpServletRequest request);

	Object deleteBatchVideoSessionByIds(HttpServletRequest request);

	Object getVideoPlayDepartments(HttpServletRequest request);


}
