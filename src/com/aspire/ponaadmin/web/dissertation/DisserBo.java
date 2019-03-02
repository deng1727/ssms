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
	 * ��־����
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
	 * ����ר��
	 * 
	 * @param form
	 *            actionForm ��װ��ҳ�����ݵ�ActionForm
	 * @throws BOException
	 */
	public void add(DissAddForm form) throws BOException {
		DisserVo vo = new DisserVo();
		// 1.��form�е�ֵcopy��vo��
		this.copyData(form, vo);
		vo.setStatus(new Integer(1));
		// 2.��ȡ�û�ѡ���ͼƬ����֤�����ء�ִ�����ݿ����
		String[] relations = form.getRelation();
		process(form, vo, relations, true);

	}

	/**
	 * �޸�ר����Ϣ
	 * 
	 * @param form
	 *            actionForm ��װ��ҳ�����ݵ�ActionForm
	 * @throws BOException
	 */
	public void update(DissAddForm form) throws BOException {
		DisserVo vo = new DisserVo();
		// 1.��form�е�ֵcopy��vo��
		this.copyData(form, vo);
		// 2.��ȡ�û�ѡ���ͼƬ����֤�����ء�ִ�����ݿ����
		String[] relations = form.getRelation();
		process(form, vo, relations, false);
	}

	/**
	 * @param form
	 *            ActionForm ��װ��ҳ�����ݵ�ActionForm����
	 * @param vo
	 *            VO����
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
				relations[i] = null;// ������������
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
				if(addOrUpdate){//���������
//					 ��ȡ����ID
					int pk = 0;
					try {
						pk = dao.getPK();
					} catch (DAOException e2) {
						logger.error("��ȡר�����г���");
						throw new BOException("��ȡר�����г���");
					}
					vo.setDissId(pk + "");
				}
				 if (f.getFileSize() > 0) {// ��������ʱ��logoͼƬ�ǿ�ѡ�ģ�û�о���Ĭ��ֵ��
					// ��������ʱ�����ܲ��޸�logoͼƬ
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
					fileName = vo.getDissId() + suffix;// ��ID����Ϊ�µ��ļ���
					filePath = config.getLocalDir() + "/" + fileName;// ������ʱ�ļ�ȫ·��
					try {
						saveTempFile(filePath, f.getInputStream());// �����û��ύ��logoͼƬ
					} catch (Exception e1) {
						logger.error("��ȡ�û��ϴ����ļ��������");
						throw new BOException("��ȡ�û��ϴ����ļ��������");
					}
					// �ϴ�����ͼƬ��ftp��Դ��������
					try {
						ftp.putFilesNoMkNewDateDir(ftpDir, filePath, fileName);
					} catch (Exception e) {
						logger.error("�ϴ�ͼƬ��ftp��Դ�������ϳ���");
						throw new BOException("�ϴ�ͼƬ��ftp��Դ�������ϳ���");
					}
					filePath = config.getPicUrl();// ҳ�����ͼƬ����Դ������ǰ����
					vo.setLogoURL(filePath + fileName);
				}

				try {

					if (addOrUpdate)// ����
					{
						checkLogoUrl(vo);
						dao.add(vo);

					} else {

						if (f.getFileSize() > 0)
						{
							dao.update(vo);
						}
						else//ɾ��logoͼƬ
						{
							checkLogoUrl(vo);
							dao.updateNoLogoUrl(vo);
							
						}
					}
				} catch (DAOException e) {
					logger.error("ִ��ר����������ݿ��������");
					throw new BOException("ִ��ר����������ݿ��������");
				}
				vo.setLogoURL("");//���logourl
			}
			vo=null;//��������
			ftp=null;
			data=null;
			
		}
	}

	private void saveTempFile(String fileName, InputStream is)
			throws BOException {
		try {
			FileUtils.writeToFile(fileName, is, true);
		} catch (IOException e) {
			// TODO �Զ����� catch ��
			logger.error("����ר��ʱ,��ȡ�û��ϴ���logoͼƬʧ��");
			throw new BOException("����ר��ʱ,��ȡ�û��ϴ���logoͼƬʧ��");
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
			// TODO �Զ����� catch ��
			logger.error("ɾ��ר���¼ʧ��" + e);
			throw new BOException("ɾ��ר���¼ʧ��");
		}
	}

	public void queryAll(PageResult page) throws BOException {
		// �����߼������ݵ�ǰʱ��ı�ר���״̬
		try {
			dao.updateStatus();
		} catch (DAOException e1) {
			logger.error("��������ר��״̬ʧ��" + e1);
			throw new BOException("��������ר��״̬ʧ��");
		}
		try {
			dao.queryAll(page);
		} catch (DAOException e) {
			logger.error("��ȡȫ��ר����Ϣ�쳣" + e);
			throw new BOException("��ȡȫ��ר����Ϣ�쳣��");
		}

	}

	public void queryByCondition(DissQueryForm form, PageResult page)
			throws BOException {
		String sql = "com.aspire.ponaadmin.web.dissertation.DisserBo.queryByCondition";
		try {
			sql = DB.getInstance().getSQLByCode(sql);
		} catch (DAOException e) {
			// TODO �Զ����� catch ��
			throw new BOException("��ȡר��sql������");
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
		logger.debug("ִ��ר���ѯ��sql���Ϊ��" + sql);
		try {
			dao.queryBySql(sql, page);
		} catch (DAOException e) {
			// TODO �Զ����� catch ��
			throw new BOException("ִ��ר��������ѯ����" + sql);
		}
	}

	/**
	 * ��ȡר����Ϣ��װ���ݺõ�actionForm,չʾ��ҳ��
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
			// TODO �Զ����� catch ��
			logger.error("��ȡid=" + dissId + "��ר���¼����");
			throw new BOException("��ȡר���¼ʧ��");
		}
		DisserVo vo = (DisserVo) list.get(0);
		// ������Ϣ
		form.setStatus(vo.showStatus());// ״̬
		form.setDissName(vo.getDissName());// ר������
		form.setDescr(vo.getDescr());// ר�����
		form.setStartDate(vo.getStartDate());// ��ʼʱ��
		form.setEndDate(vo.getEndDate());// ��ֹʱ��
		form.setTag(StringTool.unPack(vo.getKeywords()));// ��ǩ
		form.setTypes(vo.getDissType().split(":"));// ����
		// ÿ���Ż���Ϣ
		String picUrl = null;
		String dissUrl = null;
		String categoryName = null;
		String categoryId = null;
		String relation = vo.getRelation();
		// һ��ֻ�༭һ��ר����ص��Ż�����Ϣ
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
