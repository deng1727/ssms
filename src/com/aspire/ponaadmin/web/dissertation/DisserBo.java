package com.aspire.ponaadmin.web.dissertation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.struts.upload.FormFile;
import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.FileUtils;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;
import com.aspire.ponaadmin.web.util.StringTool;

public class DisserBo {
	/**
	 * 日志引用
	 */
	private static JLogger logger = LoggerFactory.getLogger(DisserBo.class);

	private DisserDao dao;

	private static DisserBo bo;

	private DisserConfig config;

	private DisserBo() {
		dao = new DisserDao();
	}

	private static DisserBo getInstan() {
		if (bo == null)
			return new DisserBo();
		return bo;
	}

	public synchronized static DisserBo getInstance() {
		return getInstan();
	}

	/**
	 * 新增专题
	 * 
	 * @param form
	 *            actionForm 封装了页面数据的ActionForm
	 * @throws BOException
	 */
	public void add(DissAddForm form) throws BOException {
		DisserVo vo = new DisserVo();
		// 1.将form中的值copy到vo中
		this.copyData(form, vo);
		vo.setStatus(new Integer(1));
		// 2.获取用户选择的图片并保证到本地、执行数据库操作
		String[] relations = form.getRelation();
		process(form, vo, relations, true);

	}

	/**
	 * 修改专题信息
	 * 
	 * @param form
	 *            actionForm 封装了页面数据的ActionForm
	 * @throws BOException
	 */
	public void update(DissAddForm form) throws BOException {
		DisserVo vo = new DisserVo();
		// 1.将form中的值copy到vo中
		this.copyData(form, vo);
		// 2.获取用户选择的图片并保证到本地、执行数据库操作
		String[] relations = form.getRelation();
		process(form, vo, relations, false);
	}

	/**
	 * @param form
	 *            ActionForm 封装了页面数据的ActionForm对象
	 * @param vo
	 *            VO对象
	 * @param relations
	 * @throws BOException
	 */
	private void process(DissAddForm form, DisserVo vo, String[] relations,
			boolean addOrUpdate) throws BOException {
		if (relations.length > 0) {
			DissAddForm.Data data = null;
			String relation = null;
			String fileName = null;
			String filePath = null;
			String ip = null;
			int port = 0;
			String user = null;
			String pwd = null;
			String ftpDir = null;
			FTPUtil ftp = null;
			for (int i = 0; i < relations.length; i++) {
				relation = relations[i];
				relations[i] = null;// 清空数组的内容
				if (relation.equals("M")) {
					data = form.getM();
				} else if (relation.equals("W")) {
					data = form.getW();
				} else if (relation.equals("P1")) {
					data = form.getP1();
				} else if (relation.equals("P2")) {
					data = form.getP2();
				} else if (relation.equals("P3")) {
					data = form.getP3();
				}
				vo.setCategoryId(data.getCategoryId());
				if (data.getCategoryId().equals("")) {
					vo.setCategoryName("");
				} else {
					vo.setCategoryName(data.getCategoryName());
				}
				vo.setDissURL(data.getDissUrl());
				vo.setRelation(relation);

				FormFile f = data.getFile().getFile();
				if(addOrUpdate){//如果是新增
//					 获取序列ID
					int pk = 0;
					try {
						pk = dao.getPK();
					} catch (DAOException e2) {
						logger.error("获取专题序列出错");
						throw new BOException("获取专题序列出错");
					}
					vo.setDissId(pk + "");
				}
				 if (f.getFileSize() > 0) {// 插入数据时，logo图片是可选的，没有就有默认值；
					// 更新数据时，可能不修改logo图片
					config = DisserConfig.getInstance();
					ip = config.getIp();
					port = config.getPort();
					user = config.getUser();
					pwd = config.getPwd();
					ftpDir = config.getFtpDir();
					ftp = new FTPUtil(ip, port, user, pwd);
					fileName = data.getFile().getFileName();
					int d = fileName.indexOf(".");
					String suffix = fileName.substring(d);
					fileName = vo.getDissId() + suffix;// 以ID号做为新的文件名
					filePath = config.getLocalDir() + "/" + fileName;// 本地临时文件全路径
					try {
						saveTempFile(filePath, f.getInputStream());// 保存用户提交的logo图片
					} catch (Exception e1) {
						logger.error("获取用户上传的文件对象出错");
						throw new BOException("获取用户上传的文件对象出错");
					}
					// 上传本地图片到ftp资源服务器上
					try {
						ftp.putFilesNoMkNewDateDir(ftpDir, filePath, fileName);
					} catch (Exception e) {
						logger.error("上传图片到ftp资源服务器上出错");
						throw new BOException("上传图片到ftp资源服务器上出错");
					}
					filePath = config.getPicUrl();// 页面访问图片的资源服务器前部分
					vo.setLogoURL(filePath + fileName);
				}

				try {

					if (addOrUpdate)// 新增
					{
						checkLogoUrl(vo);
						dao.add(vo);

					} else {

						if (f.getFileSize() > 0)
						{
							dao.update(vo);
						}
						else//删除logo图片
						{
							checkLogoUrl(vo);
							dao.updateNoLogoUrl(vo);
							
						}
					}
				} catch (DAOException e) {
					logger.error("执行专题操作中数据库操作出错");
					throw new BOException("执行专题操作中数据库操作出错");
				}
				vo.setLogoURL("");//清空logourl
			}
			vo=null;//清理垃圾
			ftp=null;
			data=null;
			
		}
	}

	private void saveTempFile(String fileName, InputStream is)
			throws BOException {
		try {
			FileUtils.writeToFile(fileName, is, true);
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			logger.error("新增专题时,获取用户上传的logo图片失败");
			throw new BOException("新增专题时,获取用户上传的logo图片失败");
		}
	}

	private void copyData(DissAddForm form, DisserVo vo) {
		vo.setDissId(form.getDissId());
		vo.setDissName(form.getDissName());
		vo.setDescr(form.getDescr());
		vo.setStartDate(form.getStartDate());
		vo.setEndDate(form.getEndDate());
		vo.setKeywords(StringTool.packageTags(form.getTag()));
		vo.setDissType(StringTool.packageStr(form.getTypes(), ':'));

	}

	public int del(String dissId) throws BOException {
		try {
			return dao.del(dissId);
		} catch (DAOException e) {
			// TODO 自动生成 catch 块
			logger.error("删除专题记录失败" + e);
			throw new BOException("删除专题记录失败");
		}
	}

	public void queryAll(PageResult page) throws BOException {
		// 新增逻辑，根据当前时间改变专题的状态
		try {
			dao.updateStatus();
		} catch (DAOException e1) {
			logger.error("更新所有专题状态失败" + e1);
			throw new BOException("更新所有专题状态失败");
		}
		try {
			dao.queryAll(page);
		} catch (DAOException e) {
			logger.error("获取全部专辑信息异常" + e);
			throw new BOException("获取全部专辑信息异常！");
		}

	}

	public void queryByCondition(DissQueryForm form, PageResult page)
			throws BOException {
		String sql = "com.aspire.ponaadmin.web.dissertation.DisserBo.queryByCondition";
		try {
			sql = DB.getInstance().getSQLByCode(sql);
		} catch (DAOException e) {
			// TODO 自动生成 catch 块
			throw new BOException("获取专题sql语句出错");
		}
		StringBuffer sb = new StringBuffer(sql);
		String name = form.getDissName();
		int status = form.getStatus().intValue();
		String tag = form.getTag();
		String type = form.getType();
		if (name != null && !name.equals("")) {
			sb.append(" and dissname like '%").append(name).append("%' ");
		}
		if (status != 2) {
			sb.append(" and status=").append(status);
		}
		if (tag != null && !tag.equals("")) {
			sb.append(" and keywords like '%").append(tag).append("%' ");
		}
		if (!type.equals("all") && !type.equals("")) {
			sb.append(" and disstype like '%").append(type).append("%' ");
		}
		sql = sb.toString();
		logger.debug("执行专题查询的sql语句为：" + sql);
		try {
			dao.queryBySql(sql, page);
		} catch (DAOException e) {
			// TODO 自动生成 catch 块
			throw new BOException("执行专题条件查询出错：" + sql);
		}
	}

	/**
	 * 获取专题信息封装数据好的actionForm,展示给页面
	 * 
	 * @param dissId
	 * @return
	 * @throws BOException
	 * @throws DAOException
	 */
	public void getDiss(String dissId, DissAddForm form) throws BOException {
		List list = null;
		try {
			list = dao.getDiss(dissId);
		} catch (DAOException e) {
			// TODO 自动生成 catch 块
			logger.error("获取id=" + dissId + "的专题记录出错");
			throw new BOException("获取专题记录失败");
		}
		DisserVo vo = (DisserVo) list.get(0);
		// 公共信息
		form.setStatus(vo.showStatus());// 状态
		form.setDissName(vo.getDissName());// 专题名称
		form.setDescr(vo.getDescr());// 专题介绍
		form.setStartDate(vo.getStartDate());// 开始时间
		form.setEndDate(vo.getEndDate());// 截止时间
		form.setTag(StringTool.unPack(vo.getKeywords()));// 标签
		form.setTypes(vo.getDissType().split(":"));// 分类
		// 每个门户信息
		String picUrl = null;
		String dissUrl = null;
		String categoryName = null;
		String categoryId = null;
		String relation = vo.getRelation();
		// 一次只编辑一个专题相关的门户等信息
		picUrl = vo.getLogoURL();
		dissUrl = vo.getDissURL();
		categoryName = vo.getCategoryName();
		categoryId = vo.getCategoryId();
		form.setMsg(vo.getShowRelation());
		form.setM(picUrl, dissUrl, categoryId, categoryName);
		form.setRelation(new String[] { "M" });
		form.setDissId(dissId);

	}

	public void checkLogoUrl(DisserVo vo) {
		String logo = vo.getRelation();
		if (vo.getLogoURL() == null || vo.getLogoURL().trim().equals("")) {
			if (logo.equals("M")) {
				vo.setLogoURL(MOLOGOURL);
			} else if (logo.equals("W")) {
				vo.setLogoURL(WWWLOGOURL);
			}else if (logo.equals("P1")) {
				vo.setLogoURL(WAP1LOGOURL);
			}else if (logo.equals("P2")) {
				vo.setLogoURL(WAP2LOGOURL);
			}else{
				vo.setLogoURL(WAP3LOGOURL);
			}
		}
	}

	public static final String MOLOGOURL = "http://ota.mmarket.com:38080/rs/res/dissertation/MO.png";

	public static final String WWWLOGOURL = "http://ota.mmarket.com:38080/rs/res/dissertation/WWW.png";

	public static final String WAP1LOGOURL = "http://ota.mmarket.com:38080/rs/res/dissertation/WAP1.0.png";

	public static final String WAP2LOGOURL = "http://ota.mmarket.com:38080/rs/res/dissertation/WAP2.0.png";

	public static final String WAP3LOGOURL = "http://ota.mmarket.com:38080/rs/res/dissertation/WAPTouch.png";

}
