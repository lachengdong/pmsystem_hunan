package com.sinog2c.mvc.controller.dbmsnew.old;


public class DbmsThreadImportController {
//	/**
//	 * 添加数据
//	 * 
//	 * @param rows
//	 *            列集合
//	 * @param prices
//	 *            值集合
//	 * @param docPath
//	 *            文件路径
//	 * @param docName
//	 *            文件名称
//	 * @param tableName
//	 *            表名
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public boolean ajaxExportInformation(String[] rows, String[] prices,String docPath, String docName, String tableName,DbmsNewDataExportSchemeManagerImpl dbmsNewDateExportSchemeManager) {
//		if (null != rows && null != prices && rows.length > 0&& prices.length > 0 && rows.length == prices.length) {
//			// 视图
//			Document doc = Dom4jXMLUtil.inits(docPath, docName);
//			Element root = doc.getRootElement();
//			Element table = root.addElement(tableName);
//			// 往视图中插入数据
//			for (int i = 0; i < rows.length; i++) {
//				Element row = table.addElement(rows[i]);
//				row.addText(prices[i]);
//			}
//			try {
//				// 导出主表数据
//				Dom4jXMLUtil.overRideXml(docPath + "/" + docName + ".xml", doc);
//				// 得到所有表的对应的 表信息 和数据
//				List[] list = getSonTableInfo(docName, rows, prices, docPath,dbmsNewDateExportSchemeManager);// 一条表数据
//				// 和
//				// 一条表信息
//				// 和一个表名
//				List<List<String>> sonRows = list[0];// 列名集合
//				List<List<Object[]>> sonValues = list[1];// 值集合
//				List<String> sonTableNames = list[2];// 表名集合
//				for (int i = 0; i < sonValues.size(); i++) {
//					List<Object[]> sonObjs = sonValues.get(i);
//					List<String> sonRow = sonRows.get(i);
//					String sonTableName = sonTableNames.get(i);// 表名
//					// 文件名后缀
//					String sonDocName = docName.substring(docName.lastIndexOf("@"), docName.length());
//					sonDocName = sonTableName + sonDocName;// 子表文件名
//					Document sonDoc = Dom4jXMLUtil.inits(docPath, sonDocName);// 创建子表文件
//					for (int j = 0; j < sonObjs.size(); j++) {
//						Object[] sonObj = sonObjs.get(j);
//						Element sonRoot = sonDoc.getRootElement();
//						Element sonTableNameElement = sonRoot.addElement(sonTableName);// 添加子表节点
//						for (int a = 0; a < sonObj.length; a++) {
//							String value = "";
//							if (null != sonObj[a] && !"".equals(sonObj[a])) {
//								value = sonObj[a].toString();// 值
//							}
//							String row = sonRow.get(a);// 列
//							Element sonRowElement = sonTableNameElement.addElement(row);
//							sonRowElement.addText(value);
//						}
//					}
//					Dom4jXMLUtil.overRideXml(docPath + "/" + sonDocName + ".xml",sonDoc);// 创建子表文件
//				}
//				return true;
//			} catch (Exception e) {
//				return false;
//			}
//		} else {
//			return false;
//		}
//	}
//	
//	
//	/**
//	 * 得到所有子表的数据集合
//	 * 
//	 * @param docName
//	 *            主表导出后文件名称
//	 * @param rows
//	 *            主表的列数组
//	 * @param prices
//	 *            主表的值数组
//	 * @return 集合数组 [0]=表的列集合 [1]=表的数据集合 [2]=表的名称
//	 */
//	@SuppressWarnings( { "null", "unchecked" })
//	public List[] getSonTableInfo(String docName, String[] rows,String[] prices, String docPath,DbmsNewDataExportSchemeManagerImpl dbmsNewDateExportSchemeManager) {
//		List[] list = new List[3];
//		List<List<String>> rowList = new ArrayList<List<String>>();
//		List<List<Object[]>> valueList = new ArrayList<List<Object[]>>();
//		List<String> tableName = new ArrayList<String>();
//		// 子表
//		String sonName = docName.substring(docName.lastIndexOf("@") + 1,docName.lastIndexOf("information"));// 得到方案ID
//		DbmsDatasChemeNew cheme = dbmsNewDateExportSchemeManager.getDatasChemeNewInfo(sonName);// 根据方案ID得到详细信息
//		DbmsDatabaseNew database = dbmsNewDateExportSchemeManager.getDataBaseNewInfo(cheme.getFromdatabaseid());// 得到数据库管理表的相应信息
//		Connection conn = dbmsNewDateExportSchemeManager.getConnection(database);// 根据数据管理表得到相应的conn对象
//		List<DbmsDatasTableNew> dbmsDatasTableList = dbmsNewDateExportSchemeManager.getTableList(sonName);// 得到操作表集合
//		Statement st = null;
//		ResultSet rs = null;
//		for (int a = 0; a < dbmsDatasTableList.size(); a++) {
//			DbmsDatasTableNew dbmsDatasTable = dbmsDatasTableList.get(a);
//			if (null != dbmsDatasTable) {
//				String joinedFields = dbmsDatasTable.getDdtjoinedfields();// 得到关系字段
//				String join = dbmsDatasTable.getDdtisscreen();
//				if (null != joinedFields && !"".equals(joinedFields)
//						&& "1".equals(join)) {// 判断是否筛选并且关联字段的值不为空
//					String whereCondition = "";// 条件
//					//判断是否有多个条件
//					String[] whens = joinedFields.split("and");// 截取单个条件
//					for (int i = 0; i < whens.length; i++) {
//						//截取前面的列名如 a.DATABASE=
//						try {
//							String viewJoin = whens[i].substring(whens[i].lastIndexOf("[") + 3, whens[i].lastIndexOf("]") - 2);// 得到视图的值名
//							//是否有视图变量存在
//							for (int j = 0; j < rows.length; j++) {
//								if (rows[j].equals(viewJoin)) {
//									// 拼接where后面的条件
//									if ("".equals(whereCondition)) {
//										whereCondition = whens[j].replace("[@_"+ viewJoin + "_@]", prices[j]);// 将特殊字符替换成值
//									} else {
//										whereCondition += " and "+ whens[j].replace("[@_"+ viewJoin + "_@]",prices[j]);// 将特殊字符替换成值
//									}
//									break;
//								}
//							}
//						} catch (StringIndexOutOfBoundsException e) {
//							if ("".equals(whereCondition)) {// 条件为空就直接添加
//								whereCondition = whens[i];// 将特殊字符替换成值
//							} else {
//								whereCondition += " and " + whens[i];// 将特殊字符替换成值
//							}
//							continue;
//						}
//					}
//					String sonTableName = dbmsDatasTable.getTablename();// 得到子表的表名
//					tableName.add(sonTableName);
//					String SQL = "select * from " + sonTableName + " a where "+ whereCondition;
//					// 表信息
//					List<String> listStr = getTableInfo(conn, SQL);
//					rowList.add(listStr);
//					try {
//						st = conn.createStatement();
//						rs = st.executeQuery(SQL);
//						// 表数据
//						List<Object[]> obj = replaceResultSet(rs, listStr);
//						valueList.add(obj);
//					} catch (SQLException e) {
//						e.printStackTrace();
//					} finally {
//						try {
//							rs.close();
//							st.close();
//						} catch (SQLException e) {
//							e.printStackTrace();
//						}
//						rs = null;
//						st = null;
//					}
//					// }
//				} else {
//					String suffix = docName.substring(docName.lastIndexOf("@"),docName.length());// 文件名统一后缀
//					String joindocName = dbmsDatasTable.getTablename() + suffix;
//					File f = new File(docPath + "/" + joindocName + ".xml");
//					if (!f.exists()) {
//						tableName.add(dbmsDatasTable.getTablename());
//						String SQL = "select * from "+ dbmsDatasTable.getTablename();
//						List<String> listStr = getTableInfo(conn, SQL);
//						rowList.add(listStr);
//						try {
//							st = conn.createStatement();
//							rs = st.executeQuery(SQL);
//							// 表数据
//							List<Object[]> obj = replaceResultSet(rs, listStr);
//							valueList.add(obj);
//						} catch (SQLException e) {
//							e.printStackTrace();
//						} finally {
//							try {
//								if(rs != null ){
//									rs.close();
//								}
//								if (st != null){
//									st.close();
//								}
//							} catch (SQLException e) {
//								e.printStackTrace();
//							}
//							rs = null;
//							st = null;
//						}
//					}
//				}
//			}
//		}
//		list[0] = rowList;
//		list[1] = valueList;
//		list[2] = tableName;
//		return list;
//	}
//	
//	
//	/** 得到表信息 */
//	private List<String> getTableInfo(Connection conn, String SQL) {
//		List<String> str = new ArrayList<String>();
//		Statement st = null;
//		ResultSet rs = null;
//		try {
//			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			rs = st.executeQuery(SQL);
//			ResultSetMetaData rsmd = rs.getMetaData();
//			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//				String name = rsmd.getColumnName(i);
//
//				str.add(name);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//				st.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return str;
//	}
//	
//	/** 将ResultSet 转换成 List */
//	private List<Object[]> replaceResultSet(ResultSet rs, List<String> cname) {
//		List<Object[]> list = new ArrayList<Object[]>();
//		try {
//			while (rs.next()) {
//				Object[] obj = new Object[cname.size()];
//				for (int i = 0; i < cname.size(); i++) {
//					try {
//						Clob clob = rs.getClob(cname.get(i));
//						obj[i] = clob.getSubString(1l, (int) clob.length());
//					} catch (Exception e) {
//						obj[i] = rs.getObject(cname.get(i));
//					}
//				}
//				list.add(obj);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
}
