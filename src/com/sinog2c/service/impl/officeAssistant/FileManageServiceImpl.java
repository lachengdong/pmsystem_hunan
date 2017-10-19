package com.sinog2c.service.impl.officeAssistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.officeAssistant.TbuserFileinfoMapper;
import com.sinog2c.model.officeAssistant.TbuserFileinfo;
import com.sinog2c.service.api.officeAssistant.FileManageService;
import com.sinog2c.service.impl.base.ServiceImplBase;

/**
 * 
 * @author wangxf
 * 
 */
@Service("fileManageService")
public class FileManageServiceImpl extends ServiceImplBase implements
		FileManageService {

	@Autowired
	private TbuserFileinfoMapper tbuserFileinfoMapper;

	/**
	 * 根据文件id查询文件详细信息
	 */
	@Override
	public TbuserFileinfo getfileInfo(String id) {
		return tbuserFileinfoMapper.getfileInfo(id);
	}

	/**
	 * 新增文件、文件夹（非空字段）
	 */
	@Override
	public int insertSelective(TbuserFileinfo record) {
		return tbuserFileinfoMapper.insertSelective(record);
	}

	/**
	 * 查询选中文件夹下的文件
	 */
	@Override
	public List<Map> selectData(int fileId, String type) {
		return tbuserFileinfoMapper.selectData(fileId, type);
	}
    /**
     * 方法描述：通过问价夹的编号，查询出文件夹中的所有的文件
     * @version 2014年10月24日09:00:06
     */
	public List<Map> selectFileData(Map map){
		return tbuserFileinfoMapper.selectFileData(map);
	}
	/**
	 * 方法描述：查询出文件夹中所有的数据
	 * @version 2014年10月24日09:14:34
	 */
	public int selectFileDataCount(Map map){
		return tbuserFileinfoMapper.selectFileDataCount(map);
	}
	/**
	 * 查询共享文件夹和当前用户的个人文件夹
	 */
	@Override
	public List<Map> selectTree(Map map) {
		return tbuserFileinfoMapper.selectTree(map);
	}

	/**
	 * 新增文件、文件夹
	 */
	@Override
	public int addfile(TbuserFileinfo fileInfo) {
		return tbuserFileinfoMapper.addfile(fileInfo);
	}

	/**
	 * 修改文件
	 */
	@Override
	public int updateFile(int fileid, String virtualname, String remark) {
		return tbuserFileinfoMapper.updateFile(fileid, virtualname, remark);
	}

	/**
	 * 删除文件
	 */
	@Override
	public int deleteFile(List idlist) {
		return tbuserFileinfoMapper.deleteFile(idlist);
	}
	/**
	 * 查询出所有的选择文件
	 */
	public List<Map> selectFileName(List idList){
		return tbuserFileinfoMapper.selectFileName(idList);
	}
	/**
	 * 方法 描述：删除所有的文件修改状态
	 * version:2014年10月23日17:35:02
	 */
	public int updateFileIeaf(String fileid){
		return tbuserFileinfoMapper.updateFileIeaf(fileid);
	}
	@SuppressWarnings("all")
	public List<Map> selectFolderByFolderId(HttpServletRequest request){
		List<Map> list = new ArrayList<Map>();
		try {
			String fileid = request.getParameter("id");
			Map map = new HashMap();
			map.put("fileid", fileid);
			list = tbuserFileinfoMapper.selectFolderByFolderId(map);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}
	public List<Map> judgeIsharedByFileid(HttpServletRequest request){
		List<Map> list = new ArrayList<Map>();
		try {
			String fileid = request.getParameter("fileid");
			Map map = new HashMap();
			map.put("fileid", fileid);
			list = tbuserFileinfoMapper.judgeIsharedByFileid(map);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

}
