package com.sinog2c.ws.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.xml.namespace.QName;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srvSeal.PdfAutoSeal;

import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.dao.api.courtjoint.OpenToOutsideMapper;
import com.sinog2c.dao.api.courtjoint.TbdaraPrisonerMapper;
import com.sinog2c.dao.api.courtjoint.TbxfBackReplenishMapper;
import com.sinog2c.dao.api.courtjoint.TbxfOpenCourtMapper;
import com.sinog2c.dao.api.courtjoint.TbxfPutonrecordMapper;
import com.sinog2c.dao.api.courtjoint.TbxfRulingMapper;
import com.sinog2c.dao.api.officeAssistant.TbuserNoticeMapper;
import com.sinog2c.dao.api.officeAssistant.TbuserUserNoticeMapper;
import com.sinog2c.dao.api.system.UserOrgWrapperMapper;
import com.sinog2c.model.courtjoint.TbdaraPrisoner;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.officeAssistant.TbuserUserNotice;
import com.sinog2c.model.system.UserOrgWrapper;
import com.sinog2c.service.api.officeAssistant.UserNoticeService;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.FtpUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.ws.service.OpenToOutsideService;
import com.sinog2c.ws.util.MyZipUtil;
import com.sinog2c.ws.util.XmlUtils;

/**
 * Webservice 对外接口的业务实现类
 */
@Service
public class OpenToOutsideServiceImpl implements OpenToOutsideService {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory
			.newInstance();
	
	/*private	Client client = clientFactory.createClient("http://10.43.11.49:8080/ebas/services/jobEntryWebService?wsdl");
	
	private Endpoint endpoint = client.getEndpoint();*/

	private static final FTPClient ftpClient = new FTPClient();

	@Autowired
	OpenToOutsideMapper openToOutsideMapper;

	@Autowired
	TbxfPutonrecordMapper tbxfPutonrecordMapper;

	@Autowired
	TbxfBackReplenishMapper tbxfBackReplenishMapper;

	@Autowired
	TbxfOpenCourtMapper tbxfOpenCourtMapper;

	@Autowired
	TbdaraPrisonerMapper tbdaraPrisonerMapper;

	@Autowired
	TbxfRulingMapper tbxfRulingMapper;

	@Autowired
	TbxfSentenceAlterationMapper tbxfsentenceMapper;

	@Autowired
	TbuserNoticeMapper tbuserNoticeMapper;

	@Autowired
	UserNoticeService userNoticeService;

	@Autowired
	UserOrgWrapperMapper userorgwrappermapper;

	@Autowired
	TbuserUserNoticeMapper tbuserUserNoticeMapper;

	@Override
	public String getCaseInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeclareInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrisonOpinion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJabatanPenjaraOpinion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcuratorateOpinion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCrimeTruth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalBaseInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalConsumption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOldAndSick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalSocialRelations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalResume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalExecutedExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAssetPerformInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAwardInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPunishmentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSentenceInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPenaltyChangeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> getPiciInfo(Map map) {
		List<Map> list = openToOutsideMapper.getPiciInfo(map);
		return MapUtil.turnKeyToLowerCaseOfList(list);
	}

	@Override
	public List<Map<String, Object>> getCourtCaseHandleList(
			Map<String, Object> map) {
		List list = openToOutsideMapper.getCourtCaseHandleList(map);
		return list;
	}

	@Override
	public int countCourtCaseHandleList(Map<String, Object> map) {
		return openToOutsideMapper.countCourtCaseHandleList(map);
	}

	/*
	 * @Override public List<Map<String, Object>> getJcyWenShuList(Map<String,
	 * Object> map) { return openToOutsideMapper.getJcyWenShuList(map); }
	 * 
	 * @Override public int countJcyWenShuList(Map<String, Object> map) { return
	 * openToOutsideMapper.countJcyWenShuList(map); }
	 */

	@Override
	public String sendCaseData(Map<Object, Object> map) {
		boolean flag = false;
		Properties jyconfig = new GetProperty().bornProp(
				"courtJointPrisonconfig", null);
		String jxjsxml = (jyconfig.getProperty("jxjsxml") == null ? ""
				: jyconfig.getProperty("jxjsxml"));
		String jxjszip = (jyconfig.getProperty("jxjszip") == null ? ""
				: jyconfig.getProperty("jxjszip"));
		String strPath = jyconfig.getProperty("crimtxt") == null ? ""
				: jyconfig.getProperty("crimtxt");
		String courtip = jyconfig.getProperty("courtip");
		String courtport = jyconfig.getProperty("courtport");
		String courtftpIP = jyconfig.getProperty("courtftpIP");
		String courtftpPort = jyconfig.getProperty("courtftpPort");
		String courtftpUser = jyconfig.getProperty("courtftpUser");
		String courtftpPwd = jyconfig.getProperty("courtftpPwd");
		String[] crimidArray = (String[]) map.get("crimidArray");
		String picibianhao = String.valueOf(map.get("picibianhao"));
		String curyear = String.valueOf(map.get("curyear"));
		String courtid = String.valueOf(map.get("courtid"));
		Map<String, String> ajxxMap = new HashMap<String, String>();
		Map<String, String> bqMap = new HashMap<String, String>();
		Map<String, String> fzssMap = new HashMap<String, String>();
		Map<String, String> yjMap = new HashMap<String, String>();
		Map<String, String> xfbgMap = new HashMap<String, String>();
		Map<String, String> ypxxMap = new HashMap<String, String>();
		Map<String, String> zfjbxxMap = new HashMap<String, String>();
		Map<String, String> zfxfqkMap = new HashMap<String, String>();
		Map<String, String> lrbcMap = new HashMap<String, String>();
		Map<String, String> qkxxMap = new HashMap<String, String>();
		Map<String, String> shgxMap = new HashMap<String, String>();
		Map<String, String> jlxxMap = new HashMap<String, String>();
		Map<String, String> fxbxMap = new HashMap<String, String>();
		Map<String, String> ccxpxlxqkMap = new HashMap<String, String>();
		StringBuilder xfbgsb = new StringBuilder("");// 该字符串用于拼接xml
		StringBuilder xgyjxxsb = new StringBuilder("");// 该字符串用于拼接xml
		StringBuilder ajxxsb = new StringBuilder("");
		StringBuilder zfzlsb = new StringBuilder("");// 该字符串用于拼接xml
		StringBuilder fxbxsb = new StringBuilder("");// 该字符串用于拼接xml
		StringBuilder zfxxsb = new StringBuilder("");// 该字符串用于拼接xml
		List list = new ArrayList<Map>();// 批量上传需要的
		List<Map<String, String>> webservicelist = new ArrayList<Map<String, String>>();// 批量调webservice接口需要
		boolean a = false;
		// 拼装一个批次信息
		if (crimidArray != null && crimidArray.length > 0) {
			for (int i = 0; i < crimidArray.length; i++) {
				StringBuilder sb = new StringBuilder("");// 该字符串用于拼接xml
				Map<Object, Object> dataMap = new HashMap<Object, Object>();
				dataMap.put("crimid", crimidArray[i]);
				dataMap.put("curyear", curyear);
				dataMap.put("picibianhao", picibianhao);
				List ajxxlist = openToOutsideMapper
						.getAJXXCaseDateInfo(dataMap);
				List bqlist = openToOutsideMapper.getBQXXCaseDateInfo(dataMap);
				List fzsslist = openToOutsideMapper
						.getFZSSCaseDateInfo(dataMap);
				List yjlist = openToOutsideMapper.getyjDataInfo(dataMap);
				List zfjbxxlist = openToOutsideMapper
						.getZFJBXXCaseDateInfo(dataMap);
				List zfxfqklist = openToOutsideMapper
						.getZFXFQKCaseDateInfo(dataMap);
				List lrbclist = openToOutsideMapper
						.getLRBCCaseDateInfo(dataMap);
				List qkxxlist = openToOutsideMapper
						.getQKXXCaseDateInfo(dataMap);
				List shgxlist = openToOutsideMapper
						.getSHGXCaseDateInfo(dataMap);
				List jlxxlist = openToOutsideMapper
						.getJLXXCaseDateInfo(dataMap);
				List fxbxlist = openToOutsideMapper
						.getFXBXCaseDateInfo(dataMap);
				List ccxpxlxqklist = openToOutsideMapper
						.getCCQKCaseDateInfo(dataMap);
				List ypxxlist = openToOutsideMapper
						.getYPXXCaseDateInfo(dataMap);
				List xfbglist = openToOutsideMapper
						.getXFBGCaseDateInfo(dataMap);
				// 案件信息
				if (ajxxlist != null && ajxxlist.size() > 0) {
					ajxxMap = (Map<String, String>) ajxxlist.get(0);
				}
				makeXml(sb, "AJXX", ajxxMap);

				if (bqlist != null && bqlist.size() > 0) {
					bqMap = (Map<String, String>) bqlist.get(0);
					if (courtid != null && !courtid.equals("")
							&& !courtid.equals("null")) {
						if (bqMap.containsKey("TIQINGFAYUANID")) {
							bqMap.put("TIQINGFAYUANID", courtid.substring(1));
						}
					}
				}
				makeXml(sb, "BQXX", bqMap);

				if (fzsslist != null && bqlist.size() > 0) {
					fzssMap = (Map<String, String>) fzsslist.get(0);
				}
				makeXml(sb, "FZSS", fzssMap);

				if (yjlist != null && yjlist.size() > 0) {
					yjMap = (Map<String, String>) yjlist.get(0);
				}
				makeXml(xgyjxxsb, "XGYJ", yjMap);

				if (ypxxlist != null && ypxxlist.size() > 0) {
					ypxxMap = (Map<String, String>) ypxxlist.get(0);
				}
				makeXml(sb, "YPXX", ypxxMap);

				if (xfbglist != null && xfbglist.size() > 0) {
					xfbgMap = (Map<String, String>) xfbglist.get(0);
				}
				makeXml(xfbgsb, "XFBG", xfbgMap);

				ajxxsb.append(sb);
				ajxxsb.append(xfbgsb);
				ajxxsb.append(xgyjxxsb);
				XmlUtils.AddTQXXHead(ajxxsb, "AJXX");

				if (zfjbxxlist != null && zfjbxxlist.size() > 0) {
					zfjbxxMap = (Map<String, String>) zfjbxxlist.get(0);
				}
				makeXml(zfzlsb, "ZFJBXX", zfjbxxMap);

				if (zfxfqklist != null && zfxfqklist.size() > 0) {
					zfxfqkMap = (Map<String, String>) zfxfqklist.get(0);
				}
				makeXml(zfzlsb, "ZFXFQK", zfxfqkMap);

				if (lrbclist != null && lrbclist.size() > 0) {
					lrbcMap = (Map<String, String>) lrbclist.get(0);
				}
				makeXml(zfzlsb, "LRBC", lrbcMap);
				if (qkxxlist != null && qkxxlist.size() > 0) {
					qkxxMap = (Map<String, String>) qkxxlist.get(0);
				}
				makeXml(zfzlsb, "QKXX", qkxxMap);
				if (shgxlist != null && shgxlist.size() > 0) {
					shgxMap = (Map<String, String>) shgxlist.get(0);
				}
				makeXml(zfzlsb, "SHGX", shgxMap);
				if (jlxxlist != null && jlxxlist.size() > 0) {
					jlxxMap = (Map<String, String>) jlxxlist.get(0);
				}
				makeXml(zfzlsb, "JLXX", jlxxMap);
				XmlUtils.AddTQXXHead(zfzlsb, "ZFZL");

				if (fxbxlist != null && fxbxlist.size() > 0) {
					fxbxMap = (Map<String, String>) fxbxlist.get(0);
				}
				makeXml(fxbxsb, "FXBX", fxbxMap);
				if (ccxpxlxqklist != null && ccxpxlxqklist.size() > 0) {
					ccxpxlxqkMap = (Map<String, String>) ccxpxlxqklist.get(0);
				}

				makeXml(fxbxsb, "CCXPXLXQK", ccxpxlxqkMap);
				// Map<String, String> qlqkMap = (Map<String, String>)
				// qlqklist.get(0);
				// makeXml(sb,"FXBX",qlqkMap);
				// Map<String, String> cfqkMap = (Map<String, String>)
				// cfqklist.get(0);
				// makeXml(sb,"BQXX",ajxxMap);
				XmlUtils.AddTQXXHead(fxbxsb, "FXBX");

				zfxxsb.append(zfzlsb);
				zfxxsb.append(fxbxsb);
				XmlUtils.AddTQXXHead(zfxxsb, "ZFXX");
				// 生成XML/zip的文件夹路径 jyconfig里面配置的文件路径/批次编号/罪犯ID
				String filepath = jxjsxml + File.separator + picibianhao
						+ File.separator + crimidArray[i] + File.separator;
				String zipfilepath = jxjszip + File.separator + picibianhao
						+ File.separator + crimidArray[i];
				SimpleDateFormat df = new SimpleDateFormat("YYMMDDhhmmss");
				String xmlDate = df.format(new Date());
				String dataSign = UUID.randomUUID().toString().replace("-", "");
				// 命名规则[发送方单位编号]_[接收方单位编号] _[案件类型] _
				// [批次编号]_[阶段编号]_[数据标识]_[YY][MM][DD][HH][MM][SS].xml
				// zip文件名字 和 XML文件名字
				String fileName = (bqMap.get("THISSHORTNAME") + "_"
						+ bqMap.get("TIQINGFAYUANID") + "_"
						+ String.valueOf(ajxxMap.get("COMMUTETYPE")) + "_"
						+ picibianhao + "_" + "1601A" + "_" + dataSign + "_" + xmlDate)
						.replace("-", "");
				// 处理文书
				// 处理文书
				List otherwritlist = openToOutsideMapper
						.getWritDataInfo(dataMap);
				List writlist = openToOutsideMapper
						.getOtherWritDataInfo(dataMap);
				writlist.addAll(otherwritlist);
				StringBuilder writsb = new StringBuilder();
				for (int ii = 0; ii < writlist.size(); ii++) {
					Map writmap = (Map) writlist.get(ii);
					String clob = (String) writmap.get("DOCCONENT");
					Clob clob1 = (Clob) writmap.get("DOCCONENT1");
					String int1 = (String) writmap.get("INT1");
					String writname = (String) writmap.get("NAME");
					String writpath = getWritPath(
							(String) writmap.get("CODEID"),
							String.valueOf(ajxxMap.get("COMMUTETYPE")));
					String writfilepath = filepath + File.separator + writpath;
					FileUtil.createDirIfNotExist(writfilepath);
					String docconent = null;
					if ("1".equals(int1)) {
						String clobpath = clob;
						if (clobpath != null && clobpath.length() > 10
								&& XmlUtils.fileIsExists(strPath + clobpath)) {
							try {
								// 电子档案已经提前转化为PDF，复制过来
								// docconent =
								// FileUtil.readFromFile(strPath+clobpath,GkzxCommon.encoding);
								copyFile(
										strPath
												+ clobpath.replace(".txt",
														".pdf"), writfilepath
												+ File.separator + writname
												+ ".pdf");
								docconent = "1";
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							logger.info(strPath + clobpath + ":文件不存在");
							continue;
						}
						// 归档
					} else {
						docconent = StringNumberUtil.clob2String(clob1);
						String writflag = null;
						try {
							writflag = PdfAutoSeal.aipAutoPdf("STRDATA:"
									+ docconent, writfilepath + File.separator
									+ writname + ".pdf");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						logger.info("文书转pdf :" + writflag + "--文书名字:"
								+ crimidArray[i] + "--的--" + writname);
					}
					try {
						if (docconent == null) {
							continue;
						}
						SimpleDateFormat sfm = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date date = new Date();
						File file = new File(filepath + File.separator
								+ writname + ".pdf");
						Long filesize = 0L;
						if (file.exists()) {
							filesize = file.length();
						}
						writsb.append("<R><WSBH>" + writmap.get("ARCHIVEID")
								+ "</WSBH><WSFLMC>" + writmap.get("TYPENAME")
								+ "</WSFLMC><WSMC>" + writname
								+ "</WSMC><WSLX>1</WSLX><CFLJ>" + writpath
								+ File.separator + writname + ".pdf"
								+ "</CFLJ><XSSX>" + ii + 1 + "</XSSX><CJSJ>"
								+ sfm.format(date) + "</CJSJ><FSSJ>"
								+ sfm.format(date) + "</FSSJ>"
								+ "<BZ></BZ><WJDX>" + filesize + "</WJDX></R>");
					} catch (Exception e1) {
						logger.info("文书转pdf异常 :--文书名字:" + crimidArray[i]
								+ "--的--" + writname);
						e1.printStackTrace();
					}

				}
				XmlUtils.AddTQXXHead(writsb, "WSXX");
				XmlUtils.AddTQXXHead(writsb, "WSJZCL");
				// 生成XML
				StringBuilder lastSB = new StringBuilder();
				lastSB.append(ajxxsb);
				lastSB.append(zfxxsb);
				lastSB.append(writsb);
				flag = XmlUtils.Dom2File(lastSB, "JXJS", filepath, fileName
						+ ".xml");
				if (flag) {
					logger.info("生成新的XML文件，路径---\n" + filepath
							+ "\n生成新的文件，文件名---\n" + fileName);
				}
				// 打包文件
				FileUtil.createDirIfNotExist(zipfilepath);
				MyZipUtil.zip(filepath, zipfilepath + File.separator + fileName
						+ ".zip");
				logger.info("生成新的ZIP文件路径---\n" + zipfilepath
						+ "\n生成新的文件，文件名---\n" + fileName + ".zip");
				// a = FtpUtil.uploadFile(FtpUtil.getFTPClient(courtftpIP,
				// Integer.valueOf(courtftpPort),
				// courtftpUser,courtftpPwd),"jxjs/jyfs/",
				// zipfilepath+File.separator+fileName+".zip");
				Map<String, String> resultmap = new HashMap<String, String>();// 批量上传需要的
				Map<String, String> webservicemap = new HashMap<String, String>();// 批量调webservice接口需要
				// 使用批量上传
				resultmap.put("originfilename", zipfilepath + File.separator
						+ fileName + ".zip");
				resultmap.put("pathname", "/jxjs/jyfs/");
				resultmap.put("filename", fileName + ".zip");
				list.add(resultmap);
				webservicemap.put("fileName", fileName);
				webservicemap.put("crimidArray", crimidArray[i]);
				webservicemap
						.put("TIQINGFAYUANID", bqMap.get("TIQINGFAYUANID"));
				webservicemap.put("THISSHORTNAME", bqMap.get("THISSHORTNAME"));
				webservicemap.put("dataSign", dataSign);
				webservicelist.add(webservicemap);
			}
		}
		String url = "http://" + courtip + ":" + courtport+ "/ebas/services/jobEntryWebService?wsdl";
		//String url = "http://127.0.0.1:8081/pmsystem/ws/gkzx?wsdl";
		Map ftpMap = batchUploadFileToFTP(courtftpIP,
				Integer.valueOf(courtftpPort), courtftpUser, courtftpPwd, list);
		if (ftpMap.get("success").toString().equals("true")) {
			List<String> webserviceList = batchInvoke(url, "sendData",
					webservicelist);
			TbdaraPrisoner tbdaraPrisoner = new TbdaraPrisoner();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(new Date());
			for (int i = 0; i < webserviceList.size(); i++) {
				// 发送完毕后将罪犯记录为已发送
				tbdaraPrisoner.setBatchid(picibianhao);
				tbdaraPrisoner.setCrimid(webserviceList.get(i));
				tbdaraPrisoner.setOpid(map.get("name").toString());
				tbdaraPrisoner.setOptime(date);
				tbdaraPrisonerMapper.insert(tbdaraPrisoner);
			}
		}
		return "1";
	}

	/**
	 * 根据文书分类生成文书路径
	 * 
	 * @param string
	 * @return
	 */
	private String getWritPath(String codeid, String string) {
		String writPath = "18.其他材料";
		if (codeid.equals("002001")) {
			writPath = "01.减刑、假释建议书";
		}
		if (codeid.equals("004001") || codeid.equals("004002")) {
			writPath = "02.01.裁判文书";
		}
		if (codeid.equals("004004")) {
			writPath = "02.02.历次刑罚执行变更裁定书";
		}
		if (codeid.equals("005001")) {
			writPath = "02.03.执行通知书";
		}
		if (codeid.equals("012001")) {
			writPath = "02.04.入监登记表";
		}
		if (codeid.equals("008001")) {
			writPath = "05.罪犯确有悔改表现、立功表现或重大立功表现的具体事实的书面证明材料";
		}
		if (codeid.equals("011001")) {
			writPath = "06.01.罪犯评审鉴定表";
		}

		if (codeid.equals("007001")) {
			writPath = "06.02.奖惩审批表";
		}
		if (codeid.equals("006001")) {
			writPath = "06.03.罪犯考核奖惩统计台账";
		}

		if (codeid.equals("003001")) {
			if (string.equals("0")) {
				writPath = "07.01.减刑审核表";
			}
			if (string.equals("1")) {
				writPath = "07.01.假释审核表";
			}
		}
		if (codeid.equals("014001")) {
			writPath = "08.病犯、残犯当次提请减刑、假释时的病、残证明文件";
		}

		if (codeid.equals("013001")) {
			writPath = "10.假释案件所需社区矫正机构的调查评估报告";
		}
		if (codeid.equals("010001") || codeid.equals("015001")
				|| codeid.equals("015002") || codeid.equals("015003")
				|| codeid.equals("117001")) {
			writPath = "11.根据案件审理需要移送的其他材料";
		}

		if (codeid.equals("009001")) {
			writPath = "04.集体评议表";
		}
		if (codeid.equals("016001")) {
			if (string.equals("0")) {
				writPath = "19.01.提请减刑会议记录";
			}
			if (string.equals("1")) {
				writPath = "19.02.提请假释会议记录";
			}
		}

		return writPath;
	}

	@Override
	public String getCaseData(String filepath, String fileName, String stage) {
		Properties jyconfig = new GetProperty().bornProp(
				"courtJointPrisonconfig", null);
		String courtjxjsxmlpath = jyconfig.getProperty("courtjxjsxmlpath");
		String uuid = UUID.randomUUID().toString();
		String xmlpat = courtjxjsxmlpath + uuid;
		FileUtil.createDirIfNotExist(xmlpat);
		MyZipUtil.unZip(filepath + File.separator + fileName, xmlpat);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 根据阶段编号取不同的XML
		try {
			String xmlFileName = fileName.replace(".zip", ".xml");
			String xmlpath = xmlpat + File.separator + xmlFileName;
			File file = new File(xmlpath);
			if (stage.equals("1601B")) {
				// 法院-退回补充材料 1601B
				Document bdoc = XmlUtils.loadXml(xmlpath);
				Map bmap = xmlToMap(bdoc, "B", "");
				if (bmap != null && bmap.size() > 0) {
					tbxfBackReplenishMapper.insertMap(bmap);
					saveMessage(bmap, "B");
				}
			}
			if (stage.equals("1601C")) {// 删除改节点
				/*
				 * //if()法院-发送送达回证 1601C Document cdoc = XmlUtils.loadXml(
				 * "C:\\Users\\phn\\Desktop\\新建文件夹\\法院发送\\2400_1234_2c99893500000000015ced8e41510052_1601B_7203E0AFE4A387D60E84E8EB024014FA.xml"
				 * ); Map cmap = xmlToMap(cdoc, "C");
				 * if(cmap!=null&&cmap.size()>0){ datamap.putAll(cmap);
				 * //Integer cconsequence
				 * =openToOutsideMapper.getCaseDataC(cmap); }
				 */
			}
			if (stage.equals("1601D")) {
				// if()法院-发送立案通知 1601D
				Document ddoc = XmlUtils.loadXml(xmlpath);
				Map dmap = xmlToMap(ddoc, "D", "");
				if (dmap != null && dmap.size() > 0) {
					tbxfPutonrecordMapper.insertMap(dmap);
					saveMessage(dmap, "D");
				}

			}
			if (stage.equals("1601E")) {
				// 法院-退回补充材料 1601B
				Document edoc = XmlUtils.loadXml(xmlpath);
				Map emap = xmlToMap(edoc, "E", uuid);
				//
				if (emap != null && emap.size() > 0) {
					tbxfOpenCourtMapper.insertMap(emap);
					saveMessage(emap, "E");
				}

			}
			if (stage.equals("1601F")) {
				// 法院-退回补充材料 1601B
				Document bdoc = XmlUtils.loadXml(xmlpath);
				Map<String, String> fmap = xmlToMap(bdoc, "F", uuid);
				// Integer bconsequence =
				// openToOutsideMapper.getCaseDataB(fmap);
				if (fmap != null && fmap.size() > 0) {
					tbxfRulingMapper.insertMap(fmap);
					/*此处将信息存入刑期变动表
					 * TbxfSentenceAlteration tbxfsentence = new
					 * TbxfSentenceAlteration();
					 * tbxfsentence.setCrimid(String.valueOf(fmap.get("ZFBH")));
					 * tbxfsentence
					 * .setCaseno(Integer.valueOf(fmap.get("AJBH")));
					 * tbxfsentence
					 * .setCourtyear(Short.valueOf(fmap.get("COURTYEAR")));
					 * tbxfsentence
					 * .setCourtshort(String.valueOf(fmap.get("COURTSHORT")));
					 * tbxfsentence.setBatchid(Integer.valueOf(fmap.get("PC")));
					 * tbxfsentence
					 * .setCourtsanction(fmap.get("CDRQ").equals("")?
					 * format.parse
					 * ("1900-00-00"):DateUtils.string2Date(fmap.get(
					 * "CDRQ"),"yyyy-MM-dd"));
					 * tbxfsentence.setCourtchangefrom(fmap
					 * .get("XQQR").equals(""
					 * )?format.parse("1900-00-00"):DateUtils
					 * .string2Date(fmap.get("XQQR"),"yyyy-MM-dd"));
					 * tbxfsentence
					 * .setCourtchangeto(fmap.get("XQZR").equals("")?
					 * format.parse
					 * ("1900-00-00"):DateUtils.string2Date(fmap.get(
					 * "XQZR"),"yyyy-MM-dd"));
					 * tbxfsentence.setCourtchangeyear(fmap.get("BCJXN"));
					 * tbxfsentence.setCourtchangemonth(fmap.get("BCJXY"));
					 * tbxfsentence.setCourtchangeday(fmap.get("BCJXR"));
					 * tbxfsentence
					 * .setNowpunishmentyear(fmap.get("BGHSYZYXQN"));
					 * tbxfsentence
					 * .setNowpunishmentmonth(fmap.get("BGHSYZYXQY"));
					 * tbxfsentence.setNowpunishmentday(fmap.get("BGHSYZYXQR"));
					 * if(fmap.get("SFBDZZQLZS").equals("0")){
					 * tbxfsentence.setLosepoweryear("97"); }
					 * tbxfsentence.setLosepoweryear(fmap.get("BDZZQLN"));
					 * tbxfsentence.setLosepowermonth(fmap.get("BDZZQLY"));
					 * tbxfsentence.setLosepowereday(fmap.get("BDZZQLD"));
					 * tbxfsentenceMapper.insert(tbxfsentence);
					 */
					saveMessage(fmap, "F");
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Integer consequence = openToOutsideMapper.insertCaseData(datamap);
		return null;
	}

	/**
	 * 
	 * @param methodName
	 * @return
	 */
	private QName getQName(Endpoint endpoint ,String methodName) {
		// 如果调不到方法，说明服务端没有指定命名空间，命名空间不一样，需要用到这个QName—————————— sayHello是你要调的方法
		QName opName = new QName(endpoint.getService().getName()
				.getNamespaceURI(), methodName);
		BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
		if (bindingInfo.getOperation(opName) == null) {
			for (BindingOperationInfo operationInfo : bindingInfo
					.getOperations()) {
				if (methodName.equals(operationInfo.getName().getLocalPart())) {
					opName = operationInfo.getName();
					break;
				}
			}
		}
		return opName;
	}

	/**
	 * @Description: 封装个人信息的批次信息
	 * @param sb
	 *            接收字符串
	 * @param stageId
	 *            阶段编号
	 * @param baseMap
	 *            数据库中查询的数据
	 * @return void
	 * @throws
	 * @author zhaoyang
	 * @date 2016年11月1日
	 */
	public void makeXml(StringBuilder sb, String stageId,
			Map<String, String> baseMap) {
		Map<String, String> map = new HashMap<String, String>();
		// getXmlString()
		// 根据阶段编号封装批次的xml文件
		switch (stageId) {
		case "AJXX":
			getXmlString(
					sb,
					baseMap,
					"COMMUTETYPE,CHARGE,CHARGEREMARKE,JXJSTYPE,CRIMID,CASENUM,PICIBIANHAO",
					"XFBGLX,ZZM,ZMZS,JXJSLX,ZFBH,AJBH,PC", "AJJBXX");
			break;
		case "BQXX":
			getXmlString(
					sb,
					baseMap,
					"THISSHORTNAME,TIQINGFAYUANID,SBRESON,DETAINPRISON,JAILREPORT,AREANAME,NOWSENTENCETYPE,LOSEPOWERYEA,LOSEPOWERYEAR,LOSEPOWERMONTH,LOSEPOWEREDAY,COURTCHANGEYEAR,COURTCHANGEMONTH,COURTCHANGEDAY,COMMUTEYEAR,COMMUTEMONTH,COMMUTEDAY,NOWSENTENCETYPE,LENIENT_RANGE,STRICT_RANGE,COURTCHANGETO,YETREDUCEYEAR,YETREDUCEMONTH,YETREDUCEDAY,NOWPUNISHMENTYEAR,NOWPUNISHMENTMONTH,NOWPUNISHMENTDAY",
					"BQJG,JSFY,BQLY,FXJG,BQRQ,JQ,BGQZXXZ,BGQBDZZQLZS,BGQBDZZQLNXN,BGQBDZZQLNXY,BGQBDZZQLNXR,BGQZYXXQN,BGQZYXXQY,BGQZYXXQR,BCSQJXN,BCSQJXY,BCSQJXR,BGHZXXZ,CYQK,CKQK,XMRQ,YJXXQN,YJXXQY,YJXXQR,SYZYXN,SYZYXY,SYZYXR",
					"BQXX");
			break;
		case "FZSS":
			getXmlString(
					sb,
					baseMap,
					"COMMUTATION,ISJOINTCRIME,ISGANGCRIME,GANGSTATUS,JOINTNUM,JOINTCRIMES,CRIMEFACE,FLEETYPE_CODE,CASEINVOLVE,SISHI",
					"JXCD,SFGTFZ,SFTHFZ,THZDW,THRS,FZDW,FZSS,LCLX,AJSJ,SS",
					"FZSS");
			break;
		case "ZFJBXX":
			getXmlString(
					sb,
					baseMap,
					"CRIMINAID,NAME,CREDENTIALSNUM,NATION,GENDER,BIRTHDAY,CRIMEDATE,ARRESTDATE,ISMINOR,MARRIAGE,VOCATION,POLITICAL,CATION,COUNTRYAREA_CODE,DOMICILE,HOMEADDRESS,ORIGIN,ISRECIDIVISM,COMBINEDPUNISHMENT,ISLIMIT,POSTCRIME,UNDERMINE,AANULL,UNDERWORLD,ALLINCOME,REMUNERATION,ALLCONSUME,SPECIALPLOT,INPRISONDATE,EXECUTIONDATE,XLEVEL,CLEVEL,CUSTODYTYPE_CODE,CHARGECLASS_CODE,STATUS,AANULL,PPROVINCE,IDENTITY,AANULL,AANULL,SANCLASSSTATUS,UNDERMINE,AANULL,AANULL,RECIDIVIST",
					"ZFBH,XM,ZJHM,MZ,XB,CSRQ,FZSJ,FZSNL,SFWCNFZ,HYZK,ZY,ZZMM,WHCD,GJ,HJSZD,JTZZ,JG,SFLF,SFSZBF,SFXZJX,SFZWFZ,SFPHJR,SFLG,SFSH,JYCKZE,JYLDBC,JYXF,TSQJ,RJRQ,JFZXRQ,XZJB,ZWJB,FYLB,FGDJ,SFZJWZX,LXFS,YDW,SF,SJGJMM,SFZSJJ,SFSWRY,SFJRZP,XFZXQJYGYFZ,SFSXFH,SFGF",
					"ZFJBXX");
			break;
		case "ZFXFQK":
			getXmlString(sb, baseMap,
					"AANULL,REMUNERATION,WITHCASH ,SHOPPING,OVERPLUS",
					"NY,JYNSR,JYWSR,ZC,JE", "ZFXFQK");
			break;
		case "LRBC":
			String[] lrbcxx = { "LRBCQK", "R" };
			getXmlString(sb, baseMap,
					"OIDTYPE,AANULL,AANULL,AANULL,CONFIRMTIME,REVOCATIONTIME",
					"LBCLX,BCCD,LBCQK,YYJDYJ,JDRQ,CXRQ", lrbcxx);
			break;
		case "QKXX":
			String[] qkxx = { "QKXX", "R" };
			getXmlString(
					sb,
					baseMap,
					"SENTENCE,STARTTIME,TYPE,CRIMENAME,AANULL,RAWYEAR,RAWMONTH,RAWDAY,AANULL,EXECUTINGORGAN",
					"KSRQ,JSRQ,QKLB,ZM,PJJG,YPXQN,YPXQY,YPXQR,PJZH,ZXJG", qkxx);
			break;
		case "SHGX":
			String[] shgx = { "SHGX", "R" };
			getXmlString(
					sb,
					baseMap,
					"NAME,GENDER,RELATIONSHIP,BIRTHDAY,POLITICAL_CODE,HOMEADDRESS,VOCATION,RDUTY,ORGNAME,ISPRIMARYCONTACT,AANULL,PHONE",
					"XM,XB,CW,CSRQ,ZZMM,JTZZ,ZY,ZW,SZDW,SFZLXR,NL,LXFS", shgx);
			break;
		case "JLXX":
			String[] jlxx = { "JLXX", "R" };
			getXmlString(sb, baseMap,
					"BEGINDATE,ENDDATE,ORGNAME,VOCATION,DUTY",
					"KSRQ,JSRQ,DW,ZY,ZW", jlxx);
			break;
		case "FXBX":
			getXmlString(
					sb,
					baseMap,
					"LAWINTEGRAL,AANULL,REPENTANCE,AANULL,HONOUR,AANULL,REWARDINFO,PUNISHINFO",
					"FLJF,DNJF,HGBX,LGBX,ZDLGBX,LGBXBZ,JLQK,CFQK", "FXBXZS");
			break;
		case "CCXPXLXQK":
			getXmlString(
					sb,
					baseMap,
					"FINE,SUMFINE,THISFINE,CIVIL,SUMCIVIL,THISCIVIL,EXPROPRIATION,THISEXPROPRIATION,SUMEXPROPRIATION,QUANTUM,UNDONEQUANTUM,RECOVEREDAMOUNT,DONERECOVEREDAMOUNT,UNDONERECOVEREDAMOUNT,NONEXECUTIONAMOUNT,CONFISCATEAMOUNT,DONECONFISCATEAMOUNT,UNDONECONFISCATEAMOUNT",
					"FJJE,YJNFJ,BCJNJE,MSPCJE,YPCJE,BCPCJE,CCX,BCZXCCX,YZXCCX,PJZLTPJE,WPCJE,PJZJJE,YZJJE,WZJJE,WZXFJJE,PJMSCCJE,YLXMSCCJE,WLXMSCCJE",
					"CCXPXLXQK");
			break;
		case "YPXX":
			String[] ypxx = { "YPXX", "R" };
			getXmlString(
					sb,
					baseMap,
					"HANDLECOURT,AANULL,MAINCHARGE,MAINCHARGEREMARKE,AANULL,AANULL,JUDGEDATE,AANULL,CASENUM,AANULL,MAINTORTURE,ORIGINALY,ORIGINALM,ORIGINALD,LOSEPOWERLIFE,LOSEPOWERYEAR,LOSEPOWERMONTH,LOSEPOWERDAY,CONFISCATEALLAMOUNT,EXPROPRIATION,THISEXPROPRIATION,THISCIVIL,INPERSION,EFFECTDATE,PRISONSTARTTIME,PRISONENDTIME,AANULL,EXECUTETIME",
					"JBFY,SKSLX,PJZZM,PJZZMMS,PJQTZM,PJQTZMMS,CPRQ,JAFS,AH,SPCX,PJZX,YPZYXQN,YPZYXQY,YPZYXQR,SFBDZZQLZS,BDZZQLN,BDZZQLY,BDZZQLR,MSGRQBCC,MSGRCC,FJ,MSPC,ZFYKSRQ,YSFYSXRQ,XQQR,XQZR,SFJFZX,JFZXRQ",
					ypxx);
			break;
		case "XFBG":
			getXmlString(
					sb,
					baseMap,
					"APPLYOFFICE,ALLNULL,JAILNAME,COURTTITLE,COMMUTATIONCASENUM,COMMUTATIONDATE,ALLNULL,COMMUTATIONYEAR,COMMUTATIONMONTH,COMMUTATIONDAY,EXECUTEDYEAR,EXECUTEDMONTH,EXECUTEDDAY,ALLNULL,ALLNULL,ANNULPENALTYMONEY,COMMITMENTREASON,REDUCEREASON",
					"BQJG,BQLY,FXDD,JBFY,JXAH,JXRQ,SQCS,YJXN,YJXY,YJXR,YFXN,YFXY,YFXR,JAFS,JARQ,JMFJSE,SJZXSY,JXSY",
					"JBXX");
			getXmlString(
					sb,
					baseMap,
					"NOWPUNISHMENTMAIN,NOWPUNISHMENTYEAR,NOWPUNISHMENTMONTH,NOWPUNISHMENTDAY,LOSEPOWERLIFE,LOSEPOWERYEAR,LOSEPOWERMONTH,LOSEPOWERDAY",
					"BGQZXXZ,BGQXQN,BGQXQY,BGQXQR,BGQBDZZQLZS,BDZZQLN,BDZZQLY,BDZZQLR",
					"BGQXX");
			getXmlString(
					sb,
					baseMap,
					"COMMUTATIONTYPE,FREEPUNISHMENTYEAR,FREEPUNISHMENTMONT,FREEPUNISHMENTDAY,COURTCHANGEYEAR,COURTCHANGEMONTH,COURTCHANGEDAY,NOWLOSEPOWERLIFE,NOWLOSEPOWERYEAR,NOWLOSEPOWERMONTH,NOWLOSEPOWERDAYCOURTCHANGEFROM,COURTCHANGETO,ORDEALSTARTDATE,ORDEALENDDATE",
					"BCJXLX,ZYXXQN,ZYXXQY,ZYXXQR,BCJXN,BCJXY,BCJXR,BCBDZZQLZS,BCBDZZQLN,BCBDZZQLY,BCBDZZQLR,BGHSFRQ",
					"DCJXXX");
			XmlUtils.AddTQXXHead(sb, "R");
			XmlUtils.AddTQXXHead(sb, "LCSQXFBGQK");
			break;
		case "XGYJ":
			getXmlString(sb, map, "JAILINFO", "JYYJBZ", "JYYJ");
			getXmlString(sb, map, "ADMINISTRATION", "JYJYJBZ", "JYJYJ");
			getXmlString(sb, map, "PROSECUTOR", "JCYYJBZ", "JCYYJ");
			XmlUtils.AddTQXXHead(sb, "XGYJXX");
			break;
		}
	}

	/**
	 * @Description: 将数据库查询的到的map信息拼装成指定格式的字符串
	 * @param sb
	 *            保存封装字符串
	 * @param map
	 *            数据库查询的记录
	 * @param sqlNames
	 *            数据库对应的字段名
	 * @param fieldNames
	 *            Xml对应的标签名
	 * @param elements
	 *            封装标签数据的上级标签名
	 * @return void
	 * @throws
	 * @author zhaoyang
	 * @date 2016年10月28日
	 */
	private void getXmlString(StringBuilder sb, Map<String, String> map,
			String sqlNames, String fieldNames, String... elements) {
		Map<String, String> result = XmlUtils.Sql2List(map, sqlNames,
				fieldNames);
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		XmlUtils.map2List(result, list);
		XmlUtils.createXml(sb, list, elements);
	}

	@Override
	public Map findsome() {
		return openToOutsideMapper.findsome();
	}

	public String clobToString(Clob clob) {
		if (clob == null) {
			return null;
		}
		try {
			Reader inStreamDoc = clob.getCharacterStream();

			char[] tempDoc = new char[(int) clob.length()];
			inStreamDoc.read(tempDoc);
			inStreamDoc.close();
			return new String(tempDoc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException es) {
			es.printStackTrace();
		}

		return null;
	}

	private Map xmlToMap(Document doc, String stage, String uuid)
			throws Exception {
		Element root = doc.getRootElement();// 获取根节点
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (stage.equals("F")) {
			Element element = root.element("AJXX");// 获取名称为queryRequest的子节点
			List<Element> elements = element.elements("AJJBXX");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
			for (Object obj : elements) {
				Element element2 = (Element) obj;
				Map map5 = XmlUtils.Dom2Map(element2);
				List<Element> element3 = element2.elements();
				for (int i = 0; i < element3.size(); i++) {
					map.put(element3.get(i).getName(), element3.get(i)
							.getText());
				}
			}
			if (map.get("AH") != null && !map.get("AH").equals("")) {
				String ah = (String) map.get("AH");
				String courtyear = ah.substring(1, 5);
				String courtshort = ah.substring(6);
				map.put("COURTYEAR", courtyear);
				map.put("COURTSHORT", courtshort);
			}
			List<Element> elementsjaxx = element.elements("JAXX");

			for (Element obj : elementsjaxx) {
				List<Element> elements100 = obj.elements("BLJGXX");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
				System.out.println(elements100.size());
				for (Object obj100 : elements100) {
					Element element2 = (Element) obj100;
					Map map5 = XmlUtils.Dom2Map(element2);
					List<Element> element3 = element2.elements();
					for (int i = 0; i < element3.size(); i++) {
						map.put(element3.get(i).getName(), element3.get(i)
								.getText());

					}
				}
			}
			for (Element obj : elementsjaxx) {
				List<Element> elements100 = obj.elements("XFBGXX");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
				System.out.println(elements100.size());
				for (Object obj100 : elements100) {
					Element element2 = (Element) obj100;
					Map map5 = XmlUtils.Dom2Map(element2);
					List<Element> element3 = element2.elements();
					for (int i = 0; i < element3.size(); i++) {
						map.put(element3.get(i).getName(), element3.get(i)
								.getText());
					}
				}
				String LOSEPOWER = String.valueOf(map.get("BDZZQLN"))
						+ map.get("BDZZQLY") + map.get("BDZZQLR");
				map.put("LOSEPOWER", LOSEPOWER);
			}

			Element elementws = root.element("WSJZCL");
			List<Element> elementsws = elementws.elements("WSXX");
			Map wsmap = new HashMap<>();
			for (Element obj : elementsws) {
				List<Element> elements100 = obj.elements("R");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
				System.out.println(elements100.size());
				for (Object obj100 : elements100) {
					Element element2 = (Element) obj100;
					Map map5 = XmlUtils.Dom2Map(element2);
					List<Element> element3 = element2.elements();
					for (int i = 0; i < element3.size(); i++) {
						wsmap.put(element3.get(i).getName(), element3.get(i)
								.getText());
						if ("CFLJ".equals(element3.get(i).getName())) {
							wsmap.put("CFLJ", uuid + "/"
									+ element3.get(i).getText());
						}
					}
					wsmap.put("ZFBH", map.get("ZFBH"));
					wsmap.put("PC", map.get("PC"));
					wsmap.put("AJBH", map.get("AJBH"));
					openToOutsideMapper.insertWritData(wsmap);
				}
			}
		} else if (stage.equals("B")) {
			Element element = root.element("AJXX");// 获取名称为queryRequest的子节点
			List<Element> elements = element.elements("AJJBXX");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
			for (Object obj : elements) {
				Element element2 = (Element) obj;
				Map map5 = XmlUtils.Dom2Map(element2);
				List<Element> element3 = element2.elements();
				for (int i = 0; i < element3.size(); i++) {
					map.put(element3.get(i).getName(), element3.get(i)
							.getText());
				}
			}
			if (map.get("AH") != null && !map.get("AH").equals("")) {
				String ah = (String) map.get("AH");
				String courtyear = ah.substring(1, 5);
				String courtshort = ah.substring(6);
				map.put("COURTYEAR", courtyear);
				map.put("COURTSHORT", courtshort);
			}
			List<Element> elementlaxx = element.elements("LAXX");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
			for (Object obj : elementlaxx) {
				Element element2 = (Element) obj;
				Map map5 = XmlUtils.Dom2Map(element2);
				List<Element> element3 = element2.elements();
				for (int i = 0; i < element3.size(); i++) {
					map.put(element3.get(i).getName(), element3.get(i)
							.getText());
				}
			}
		} else if (stage.equals("C")) {

		} else if (stage.equals("D")) {
			Element element = root.element("AJXX");// 获取名称为queryRequest的子节点
			List<Element> elements = element.elements("AJJBXX");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
			for (Object obj : elements) {
				Element element2 = (Element) obj;
				Map map5 = XmlUtils.Dom2Map(element2);
				List<Element> element3 = element2.elements();
				for (int i = 0; i < element3.size(); i++) {
					map.put(element3.get(i).getName(), element3.get(i)
							.getText());
				}
			}
			List<Element> elementlaxx = element.elements("LAXX");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
			for (Object obj : elementlaxx) {
				Element element2 = (Element) obj;
				Map map5 = XmlUtils.Dom2Map(element2);
				List<Element> element3 = element2.elements();
				for (int i = 0; i < element3.size(); i++) {
					map.put(element3.get(i).getName(), element3.get(i)
							.getText());
				}
				if (map.get("AH") != null && !map.get("AH").equals("")) {
					String ah = (String) map.get("AH");
					String courtyear = ah.substring(1, 5);
					String courtshort = ah.substring(6);
					map.put("COURTYEAR", courtyear);
					map.put("COURTSHORT", courtshort);
				}
			}
		} else if (stage.equals("E")) {
			Element element = root.element("AJXX");// 获取名称为queryRequest的子节点
			List<Element> elements = element.elements("AJJBXX");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
			for (Object obj : elements) {
				Element element2 = (Element) obj;
				Map map5 = XmlUtils.Dom2Map(element2);
				List<Element> element3 = element2.elements();
				for (int i = 0; i < element3.size(); i++) {
					map.put(element3.get(i).getName(), element3.get(i)
							.getText());
				}
				if (map.get("AH") != null && !map.get("AH").equals("")) {
					String ah = (String) map.get("AH");
					String courtyear = ah.substring(1, 5);
					String courtshort = ah.substring(6);
					map.put("COURTYEAR", courtyear);
					map.put("COURTSHORT", courtshort);
				}
			}
			List<Element> elementsktqk = element.elements("KTQK");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
			for (Object obj100 : elementsktqk) {
				Element element2 = (Element) obj100;
				Map map5 = XmlUtils.Dom2Map(element2);
				List<Element> element3 = element2.elements();
				for (int i = 0; i < element3.size(); i++) {
					map.put(element3.get(i).getName(), element3.get(i)
							.getText());
				}
			}

			Element elementws = root.element("WSJZCL");
			List<Element> elementsws = elementws.elements("WSXX");
			Map wsmap = new HashMap<>();
			for (Element obj : elementsws) {
				List<Element> elements100 = obj.elements("R");// 获取这个子节点里面的所有子元素，也可以element.elements("userList")指定获取子元素
				System.out.println(elements100.size());
				for (Object obj100 : elements100) {
					Element element2 = (Element) obj100;
					Map map5 = XmlUtils.Dom2Map(element2);
					List<Element> element3 = element2.elements();
					for (int i = 0; i < element3.size(); i++) {
						wsmap.put(element3.get(i).getName(), element3.get(i)
								.getText());
						if ("CFLJ".equals(element3.get(i).getName())) {
							wsmap.put("CFLJ", uuid + "/"
									+ element3.get(i).getText());
						}
					}
				}
				wsmap.put("ZFBH", map.get("ZFBH"));
				wsmap.put("PC", map.get("PC"));
				wsmap.put("AJBH", map.get("AJBH"));
				wsmap.put("UUID", uuid);
				openToOutsideMapper.insertWritData(wsmap);
			}
		}
		return map;
	}

	@Override
	public void sendReceiptToCourt(Map<Object, Object> datamap) {
		/*
		 * Properties jyconfig = new
		 * GetProperty().bornProp("courtJointPrisonconfig", null); String
		 * jxjsxml = (jyconfig.getProperty("jxjsxml")==
		 * null?"":jyconfig.getProperty("jxjsxml")); String jxjszip =
		 * (jyconfig.getProperty("jxjszip")==
		 * null?"":jyconfig.getProperty("jxjszip")); String strPath =
		 * jyconfig.getProperty("crimtxt") == null ? "":
		 * jyconfig.getProperty("crimtxt"); //String scorpId =
		 * jyconfig.getProperty("scorpId"); SAXReader saxReader = new
		 * SAXReader(); String[] crimidArray = (String[])
		 * datamap.get("crimidArray"); String picibianhao = (String)
		 * datamap.get("picibianhao"); for (int i = 0; i < crimidArray.length;
		 * i++) {
		 * 
		 * String dataSign = UUID.randomUUID().toString().replace("-", "");
		 * 
		 * Map map = new HashMap<String, String>(); map.put("crimid",
		 * crimidArray[i]); map.put("picibianhao", picibianhao);
		 * map.put("curyear", datamap.get("curyear")); Map recemap =
		 * openToOutsideMapper.getReceiptDatatest(map); List bqlist =
		 * openToOutsideMapper.getBQXXCaseDateInfo(map); Map<String, String>
		 * bqMap = new HashMap<String, String>();
		 * if(bqlist!=null&&bqlist.size()>0){ bqMap = (Map<String, String>)
		 * bqlist.get(0); } List ajxxlist =
		 * openToOutsideMapper.getAJXXCaseDateInfo(map); Map<String, String>
		 * ajxxMap = new HashMap<String, String>();
		 * if(ajxxlist!=null&&ajxxlist.size()>0){ ajxxMap = (Map<String,
		 * String>) ajxxlist.get(0); } StringBuilder sb = new StringBuilder("");
		 * SimpleDateFormat dfjs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 * SimpleDateFormat dfjsrq = new SimpleDateFormat("yyyy-MM-dd"); String
		 * jsrqq = dfjsrq.format(new Date()); String jsrq = dfjs.format(new
		 * Date()); String filePath =
		 * jxjsxml+File.separator+picibianhao+File.separator
		 * +crimidArray[i]+File.separator+"给法院的送达回证"+File.separator; File file =
		 * new File(filePath); String [] fileNames = file.list();
		 * sb.append("<AJXX><AJJBXX><ZFBH>"
		 * +crimidArray[i]+"</ZFBH><AJBH>"+recemap
		 * .get("CASENUM")+"</AJBH><JSRQ>"
		 * +jsrqq+"</JSRQ><JSR>"+recemap.get("NAME"
		 * )+"</JSR><PC>"+picibianhao+"</PC></AJJBXX></AJXX>" +
		 * "<WSJZCL><WSXX><R><WSBH>"
		 * +"103810741873"+"</WSBH><WSFLMC>"+"减刑假释送达回证"+
		 * "</WSFLMC><WSMC>"+fileNames
		 * [0].substring(0,fileNames[0].indexOf("."))+"</WSMC>" +
		 * "<WSLX>1</WSLX><CFLJ>"
		 * +"13.送达回证/"+fileNames+"</CFLJ><XSSX>"+"1"+"</XSSX><CJSJ>"
		 * +jsrq+"</CJSJ>" + "<FSSJ>"+jsrq+"</FSSJ><BZ>"+""+
		 * "</BZ><WJDX>28347</WJDX></R></WSXX></WSJZCL>"); SimpleDateFormat df =
		 * new SimpleDateFormat("YYMMDDhhmmss"); String xmlDate = df.format(new
		 * Date()); //String fileName =
		 * 4309+"_"+"2400"+"_"+String.valueOf(ajxxMap
		 * .get("COMMUTETYPE"))+"_"+23655
		 * +"_"+"1601G"+"_"+UUID.randomUUID().toString()+"_"+xmlDate; String
		 * fileName =
		 * (bqMap.get("THISSHORTNAME")+"_"+bqMap.get("TIQINGFAYUANID")
		 * +"_"+String
		 * .valueOf(ajxxMap.get("COMMUTETYPE"))+"_"+picibianhao+"_"+"1601G"
		 * +"_"+dataSign+"_"+xmlDate).replace("-", ""); boolean
		 * flag=XmlUtils.Dom2File(sb,"JXJS",filePath ,fileName+".xml");
		 * System.out.println(flag); if(flag){
		 * logger.info("生成新的XML文件，路径---\n"+filePath
		 * +"\n生成新的文件，文件名---\n"+fileName); } String zipfilepath =
		 * jxjszip+File.separator+23655+File.separator+crimidArray[i]; try {
		 * FileUtil.copyDirectiory("C:\\新建文件夹 (2)\\", filePath); } catch
		 * (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } //打包文件
		 * FileUtil.createDirIfNotExist(zipfilepath); MyZipUtil.zip(filePath,
		 * zipfilepath+File.separator+fileName+".zip");
		 * logger.info("生成新的ZIP文件路径---\n"
		 * +zipfilepath+"\n生成新的文件，文件名---\n"+fileName+".zip"); String
		 * webservicepath = (jyconfig.getProperty("webservicepath")==
		 * null?"":jyconfig.getProperty("webservicepath")); String courtftpIP =
		 * jyconfig.getProperty("courtftpIP"); String courtftpPort =
		 * jyconfig.getProperty("courtftpPort"); String courtftpUser =
		 * jyconfig.getProperty("courtftpUser"); String courtftpPwd =
		 * jyconfig.getProperty("courtftpPwd"); String courtjxjszippath =
		 * jyconfig.getProperty("courtjxjszippath");
		 * FileUtil.createDirIfNotExist(courtjxjszippath);
		 * JaxWsDynamicClientFactory clientFactory =
		 * JaxWsDynamicClientFactory.newInstance(); //webservice的wsdl地址 //Client
		 * client = clientFactory.createClient(
		 * "http://192.168.1.40:8080/ebcp/services/jobEntryWebService?wsdl");
		 * Client client = clientFactory.createClient(
		 * "http://10.43.11.49:8080/ebas/services/jobEntryWebService?wsdl");
		 * Endpoint endpoint = client.getEndpoint();
		 * //如果调不到方法，说明服务端没有指定命名空间，命名空间不一样，需要用到这个QName—————————— sayHello是你要调的方法
		 * //QName opName =
		 * getQName(endpoint.getService().getName().getNamespaceURI(),
		 * "sayHello"); QName opName = getQName("sendData");
		 * //如果命名空间不一样，需要用到这个QName—————————— Object[] result1 = null; boolean a
		 * = FtpUtil.uploadFile(FtpUtil.getFTPClient(courtftpIP,Integer.valueOf(
		 * courtftpPort), courtftpUser,courtftpPwd),"jxjs/jyfs",
		 * zipfilepath+File.separator+fileName+".zip");
		 * 
		 * try { result1 =
		 * client.invoke(opName,"pmsystem","jxjs/jyfs/"+fileName+
		 * ".zip",crimidArray
		 * [i],bqMap.get("TIQINGFAYUANID"),bqMap.get("THISSHORTNAME"
		 * ),"1601G",dataSign); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } }
		 */
	}

	@Override
	public void sendReceiptToCourttest(Map<Object, Object> datamap) {
		Properties jyconfig = new GetProperty().bornProp(
				"courtJointPrisonconfig", null);
		String jxjsxml = (jyconfig.getProperty("jxjsxml") == null ? ""
				: jyconfig.getProperty("jxjsxml"));
		String jxjszip = (jyconfig.getProperty("jxjszip") == null ? ""
				: jyconfig.getProperty("jxjszip"));
		// String strPath = jyconfig.getProperty("crimtxt") == null ? "":
		// jyconfig.getProperty("crimtxt");
		// String scorpId = jyconfig.getProperty("scorpId");
		SAXReader saxReader = new SAXReader();
		String[] crimidArray = (String[]) datamap.get("crimidArray");
		String picibianhao = (String) datamap.get("picibianhao");
		for (int i = 0; i < crimidArray.length; i++) {

			String dataSign = UUID.randomUUID().toString().replace("-", "");

			Map map = new HashMap<String, String>();
			map.put("crimid", crimidArray[i]);
			map.put("picibianhao", picibianhao);
			map.put("curyear", datamap.get("curyear"));
			Map recemap = openToOutsideMapper.getReceiptDatatest(map);
			List bqlist = openToOutsideMapper.getBQXXCaseDateInfo(map);
			Map<String, String> bqMap = new HashMap<String, String>();
			if (bqlist != null && bqlist.size() > 0) {
				bqMap = (Map<String, String>) bqlist.get(0);
			}
			List ajxxlist = openToOutsideMapper.getAJXXCaseDateInfo(map);
			Map<String, String> ajxxMap = new HashMap<String, String>();
			if (ajxxlist != null && ajxxlist.size() > 0) {
				ajxxMap = (Map<String, String>) ajxxlist.get(0);
			}
			StringBuilder sb = new StringBuilder("");
			SimpleDateFormat dfjs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat dfjsrq = new SimpleDateFormat("yyyy-MM-dd");
			String jsrqq = dfjsrq.format(new Date());
			String jsrq = dfjs.format(new Date());
			String filePath = jxjsxml + File.separator + picibianhao
					+ File.separator + crimidArray[i] + File.separator
					+ "给法院的送达回证" + File.separator;
			File file = new File(filePath + File.separator + "13.送达回证");
			String[] fileNames = file.list();
			sb.append("<AJXX><AJJBXX><ZFBH>" + crimidArray[i] + "</ZFBH><AJBH>"
					+ recemap.get("CASENUM") + "</AJBH><JSRQ>" + jsrqq
					+ "</JSRQ><JSR>" + recemap.get("NAME") + "</JSR><PC>"
					+ picibianhao + "</PC></AJJBXX></AJXX>"
					+ "<WSJZCL><WSXX><R><WSBH>" + "103810741873"
					+ "</WSBH><WSFLMC>" + "减刑假释送达回证" + "</WSFLMC><WSMC>"
					+ fileNames[0].substring(0, fileNames[0].indexOf("."))
					+ "</WSMC>" + "<WSLX>1</WSLX><CFLJ>" + "13.送达回证/"
					+ fileNames[0] + "</CFLJ><XSSX>" + "1" + "</XSSX><CJSJ>"
					+ jsrq + "</CJSJ>" + "<FSSJ>" + jsrq + "</FSSJ><BZ>" + ""
					+ "</BZ><WJDX>28347</WJDX></R></WSXX></WSJZCL>");
			SimpleDateFormat df = new SimpleDateFormat("YYMMDDhhmmss");
			String xmlDate = df.format(new Date());
			// String fileName =
			// 4309+"_"+"2400"+"_"+String.valueOf(ajxxMap.get("COMMUTETYPE"))+"_"+23655+"_"+"1601G"+"_"+UUID.randomUUID().toString()+"_"+xmlDate;
			String fileName = (bqMap.get("THISSHORTNAME") + "_"
					+ bqMap.get("TIQINGFAYUANID") + "_"
					+ String.valueOf(ajxxMap.get("COMMUTETYPE")) + "_"
					+ picibianhao + "_" + "1601G" + "_" + dataSign + "_" + xmlDate)
					.replace("-", "");
			boolean flag = XmlUtils.Dom2File(sb, "JXJS", filePath, fileName
					+ ".xml");
			if (flag) {
				logger.info("生成新的XML文件，路径---\n" + filePath
						+ "\n生成新的文件，文件名---\n" + fileName);
			}
			String zipfilepath = jxjszip + File.separator + picibianhao
					+ File.separator + crimidArray[i];

			// 打包文件
			FileUtil.createDirIfNotExist(zipfilepath);
			MyZipUtil.zip(filePath, zipfilepath + File.separator + fileName
					+ ".zip");
			logger.info("生成新的ZIP文件路径---\n" + zipfilepath + "\n生成新的文件，文件名---\n"
					+ fileName + ".zip");
			String webservicepath = (jyconfig.getProperty("webservicepath") == null ? ""
					: jyconfig.getProperty("webservicepath"));
			String courtftpIP = jyconfig.getProperty("courtftpIP");
			String courtftpPort = jyconfig.getProperty("courtftpPort");
			String courtftpUser = jyconfig.getProperty("courtftpUser");
			String courtftpPwd = jyconfig.getProperty("courtftpPwd");
			String courtjxjszippath = jyconfig.getProperty("courtjxjszippath");
			FileUtil.createDirIfNotExist(courtjxjszippath);
			// JaxWsDynamicClientFactory clientFactory =
			// JaxWsDynamicClientFactory.newInstance();
			// webservice的wsdl地址
			// Client client =
			// clientFactory.createClient("http://192.168.1.40:8080/ebcp/services/jobEntryWebService?wsdl");
			Client client = clientFactory
					.createClient(webservicepath);
			Endpoint endpoint = client.getEndpoint();
			// 如果调不到方法，说明服务端没有指定命名空间，命名空间不一样，需要用到这个QName——————————
			// sayHello是你要调的方法
			// QName opName =
			// getQName(endpoint.getService().getName().getNamespaceURI(),
			// "sayHello");
			QName opName = getQName(endpoint,"sendData");
			// 如果命名空间不一样，需要用到这个QName——————————
			Object[] result1 = null;
			boolean a = FtpUtil.uploadFile(FtpUtil.getFTPClient(courtftpIP,
					Integer.valueOf(courtftpPort), courtftpUser, courtftpPwd),
					"jxjs/jyfs", zipfilepath + File.separator + fileName
							+ ".zip");

			try {
				result1 = client.invoke(opName, "pmsystem", "jxjs/jyfs/"
						+ fileName + ".zip", crimidArray[i],
						bqMap.get("TIQINGFAYUANID"),
						bqMap.get("THISSHORTNAME"), "1601G", dataSign);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 发送完毕后销毁
			client.destroy();
			Map tbmap = new HashMap<String, String>();
			tbmap.put("crimid", crimidArray[i]);
			tbmap.put("batchid", picibianhao);
			tbdaraPrisonerMapper.change(tbmap);
		}

	}

	private void saveMessage(Map map, String state) {
		String content = "";
		String title = "";
		if (state.equals("B")) {
			content = "罪犯编号：" + map.get("ZFBH") + "案件号：" + map.get("AH")
					+ "被退回！";
			title = "案件退回提醒";
		}
		if (state.equals("D")) {
			content = "罪犯编号：" + map.get("ZFBH") + "案件号：" + map.get("AH")
					+ "立案信息已到！";
			title = "法院立案提醒";
		}
		if (state.equals("E")) {
			content = "罪犯编号：" + map.get("ZFBH") + "案件号：" + map.get("AH")
					+ "开庭通知已到！";
			title = "法院开庭提醒";
		}
		if (state.equals("F")) {
			content = "罪犯编号：" + map.get("ZFBH") + "案件号：" + map.get("AH")
					+ "裁定信息已到！";
			title = "法院裁定信息提醒";
		}
		Properties jyconfig = new GetProperty().bornProp(
				"courtJointPrisonconfig", null);
		String persionid = jyconfig.getProperty("persionid");
		String noticePk = userNoticeService.getNoticePk();
		TbuserNotice tbuserNotice = new TbuserNotice();
		TbuserNotice notice = new TbuserNotice();
		notice.setNoticeid(Integer.valueOf(noticePk));
		notice.setContent(content);
		notice.setTitle(title);
		notice.setMessagetype(3);
		notice.setOpid("sysauto");
		notice.setUsername(persionid);
		notice.setStarttime(new Date());
		notice.setEndtime(new Date());
		notice.setOptime(new Date());
		notice.setState(0);
		notice.setText1(String.valueOf(map.get("ZFBH")));
		notice.setText2(String.valueOf(map.get("AJBH")));
		notice.setText3(String.valueOf(map.get("PC")));
		userNoticeService.insertDataToUserNotice(notice);
		List<UserOrgWrapper> UserOrgWrapperList = userorgwrappermapper
				.getIdsByDepartid(persionid);
		for (UserOrgWrapper userOrgWrapper : UserOrgWrapperList) {
			TbuserUserNotice usernotice = new TbuserUserNotice();
			usernotice.setNoticeid(Integer.valueOf(noticePk));// 通知id
			usernotice.setOpid("sysauto");
			usernotice.setOptime(new Date());
			usernotice.setUserid(userOrgWrapper.getUserid());// 用户id
			tbuserUserNoticeMapper.insert(usernotice);
		}
	}

	@Override
	public List<Map> getPrisonInfo(Map map) {
		List<Map> list = openToOutsideMapper.getPrisonInfo(map);
		return MapUtil.turnKeyToLowerCaseOfList(list);
	}

	@Override
	public List<Map> getCourt(Map map) {
		List<Map> list = openToOutsideMapper.getCourt(map);
		return MapUtil.turnKeyToLowerCaseOfList(list);
	}

	@Override
	public List<Map> getCourtP(Map map) {
		List<Map> list = openToOutsideMapper.getCourtP(map);
		return MapUtil.turnKeyToLowerCaseOfList(list);
	}

	public static void copyFile(String path1, String path2) throws IOException {
		File file = new File(path2);
		file.createNewFile();
		FileInputStream fis = new FileInputStream(path1);
		FileOutputStream fos = new FileOutputStream(path2);
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		int by = 0;
		byte[] buf = new byte[1024];
		while ((by = bis.read(buf)) != -1) {
			bos.write(buf, 0, by);
		}
		fis.close();
		fos.close();
		bis.close();
		bos.close();
	}

	// 删除文件下下面的文件
	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public String deleteData(String picibianhao, String crimid) {
		Map map = new HashMap<String, String>();
		map.put("batchid", picibianhao);
		map.put("crimid", crimid);
		tbdaraPrisonerMapper.deleteByBatchidAndCrimid(map);
		Properties jyconfig = new GetProperty().bornProp(
				"courtJointPrisonconfig", null);
		String jxjsxml = (jyconfig.getProperty("jxjsxml") == null ? ""
				: jyconfig.getProperty("jxjsxml"));
		String filepath = jxjsxml + File.separator + picibianhao
				+ File.separator + crimid + File.separator;
		delAllFile(filepath);
		return "1";
	}

	/**
	 * 批量上传文件
	 * 
	 * @param hostname
	 *            ftp服务ip
	 * @param port
	 *            端口号
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param fileList
	 *            需要上传的文件集合 originfilename 本地的文件路径（例如：D:/demo/1.zip）不能为空
	 *            pathname 上传到ftp的路径（例如：demo/aaa），为空时上传到根目录 filename
	 *            上传后的文件名，不能为空
	 * @return
	 */
	public static Map<String, Object> batchUploadFileToFTP(String hostname,
			int port, String username, String password,
			List<Map<String, Object>> fileList) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", "false");
		resultMap.put("message", "上传失败！");
		if (fileList != null && fileList.size() > 0) {
			ftpClient.setControlEncoding("UTF-8");
			try {
				// 连接FTP服务器
				ftpClient.connect(hostname, port);
				// 登录FTP服务器
				ftpClient.login(username, password);
				// 是否成功登录FTP服务器
				int replyCode = ftpClient.getReplyCode();
				if (FTPReply.isPositiveCompletion(replyCode)) {
					for (int i = 0; i < fileList.size(); i++) {
						InputStream inputStream = new FileInputStream(new File(
								fileList.get(i).get("originfilename")
										.toString()));
						ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
						ftpClient.makeDirectory(fileList.get(i).get("pathname")
								.toString());
						ftpClient.changeWorkingDirectory(fileList.get(i)
								.get("pathname").toString());
						ftpClient.storeFile(fileList.get(i).get("filename")
								.toString(), inputStream);
						inputStream.close();
					}
					resultMap.put("success", "true");
					resultMap.put("message", "上传成功！");
				}
				ftpClient.logout();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * @param url
	 *            webservice地址
	 * @param methodName
	 *            调用webservice的方法名
	 * @param list
	 *            调用需要的参数
	 * @return
	 */
	public List<String> batchInvoke(String url, String methodName,
			List<Map<String, String>> list) {
		// 调用webservice需要内存
		System.gc();
		Map map = new HashMap<String, String>();
		List resultlist = new ArrayList<String>();
		Object[] result = null;
		Client client = clientFactory.createClient(url);
		try {
			// 如果调不到方法，说明服务端没有指定命名空间，命名空间不一样，需要用到这个QName——————————
			// sayHello是你要调的方法
			// QName opName =
			// getQName(endpoint.getService().getName().getNamespaceURI(),
			// "sayHello");
			Endpoint endpoint = client.getEndpoint();
			QName opName = getQName(endpoint,"sendData");
			// 如果命名空间不一样，需要用到这个QName——————————
			for (int i = 0; i < list.size(); i++) {
				map = list.get(i);
				result = client.invoke(opName, "pmsystem",
						"jxjs/jyfs/" + map.get("fileName") + ".zip",
						map.get("crimidArray"), map.get("TIQINGFAYUANID"),
						map.get("THISSHORTNAME"), "1601A", map.get("dataSign"));
				logger.info(result[0]);
				if (result[0]!=null) {
					// 将成功的编号记录下来
					logger.info(map.get("crimidArray"));
					resultlist.add(map.get("crimidArray"));
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			client.destroy();
			System.gc();
		}
		return resultlist;
	}

	public static void main(String[] args) throws Exception {
		String filepath = "D:\\criminalarchives\\CriminalArchives";
		File file = new File(filepath);
		String fileList[];
		fileList = file.list();
		for (int i = 0; i < fileList.length; i++) {
			File file1 = new File(filepath + File.separator + fileList[i]);
			String fileList1[];
			fileList1 = file1.list();
			if (fileList1 == null) {
				continue;
			}
			for (int j = 0; j < fileList1.length; j++) {
				// System.out.println(filepath+File.separator+fileList[i]+File.separator+fileList1[i]);
				File file2 = new File(filepath + File.separator + fileList[i]
						+ File.separator + fileList1[j]);
				String fileList2[];
				fileList2 = file2.list();
				if (fileList2 == null) {
					continue;
				}
				for (int n = 0; n < fileList2.length; n++) {
					String finalpath = filepath + File.separator + fileList[i]
							+ File.separator + fileList1[j] + File.separator
							+ fileList2[n];
					copyFile(finalpath, "C:\\Users\\Administrator\\Desktop\\复制"
							+ File.separator + fileList[i] + File.separator
							+ fileList1[j] + File.separator + fileList2[n]);
					System.out.println(System.currentTimeMillis());
				}
			}
		}
	}
}
