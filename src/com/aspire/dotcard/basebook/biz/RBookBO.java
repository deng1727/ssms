package com.aspire.dotcard.basebook.biz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aspire.common.config.ArrayValue;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.basebook.BaseBookConstant;
import com.aspire.dotcard.basebook.config.BaseBookConfig;
import com.aspire.dotcard.basebook.dao.BookAuthorDao;
import com.aspire.dotcard.basebook.dao.BookCategoryDao;
import com.aspire.dotcard.basebook.dao.BookRecommendDao;
import com.aspire.dotcard.basebook.dao.BookReferenceDao;
import com.aspire.dotcard.basebook.dao.RBookDao;
import com.aspire.dotcard.basebook.dao.TypeDao;
import com.aspire.dotcard.basebook.vo.BookAuthorVO;
import com.aspire.dotcard.basebook.vo.RBookVO;
import com.aspire.dotcard.basebook.vo.RCategoryVO;
import com.aspire.dotcard.basebook.vo.RTypeVO;
import com.aspire.dotcard.basebook.vo.RecommendVO;
import com.aspire.dotcard.basebook.vo.ReferenceVO;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;


/**
 * ����ͼ�鴦��ҵ����
 * @author x_zhailiqing
 *
 */
public class RBookBO {
	protected static JLogger logger = LoggerFactory.getLogger(RBookBO.class);
	
	private static RBookBO instance = new RBookBO();
	
	private TaskRunner taskRunner;
	
	private int baseBookSynTaskNum;
	
	private int baseBookMaxReceivedNum;
	
	private RBookBO(){
		baseBookSynTaskNum = Integer.parseInt(BaseBookConfig.get("BaseBookSynTaskNum"));
		baseBookMaxReceivedNum = Integer.parseInt(BaseBookConfig.get("BaseBookMaxReceivedNum"));
	}
	
	public synchronized static RBookBO getInstance(){
		return instance;
	}
	
	/**
	 * ����ͼ��ר�����봦��
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookArea(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)throws Exception{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		//��ѯ���еĻ���
		Map allAreaCate = BookCategoryDao.getInstance().queryAllAreaCate();
		String recommendId = null;
		String firstId = null;
		String specialId = null;
		Map newArea = new HashMap();
		for(Iterator it = allAreaCate.entrySet().iterator();it.hasNext();){
			Map.Entry en = (Map.Entry)it.next();
			RCategoryVO tmp = (RCategoryVO) en.getValue();
			if(BaseBookConstant.CATE_TYPE_RECOMMEND.equals(tmp.getCatalogType())&&("".equals(tmp.getParentId())||tmp.getParentId()== null)){
				recommendId = Integer.toString(tmp.getId());
				continue;
			}
			else if(BaseBookConstant.CATE_TYPE_FIRST.equals(tmp.getCatalogType())&&("".equals(tmp.getParentId())||tmp.getParentId()== null)){
				firstId = Integer.toString(tmp.getId());
				continue;
			}
			else if(BaseBookConstant.CATE_TYPE_AREA.equals(tmp.getCatalogType())&&("".equals(tmp.getParentId())||tmp.getParentId()== null)){
				specialId = Integer.toString(tmp.getId());
				continue;
			}			
		}
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//��ȡҪ����Ķ���

		    	String[] dataArray = line.split("["+sep+"]");
		    	RCategoryVO cate = new RCategoryVO();
		    	if(!cate.setValue(dataArray, null)){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
				if(BaseBookConstant.CATE_TYPE_RECOMMEND.equals(cate.getCatalogType())){
					cate.setParentId(recommendId);
				}
				else if(BaseBookConstant.CATE_TYPE_FIRST.equals(cate.getCatalogType())){
					cate.setParentId(firstId);
				}
				else if(BaseBookConstant.CATE_TYPE_AREA.equals(cate.getCatalogType())){
					cate.setParentId(specialId);
				}
//				if(null==newArea.get(cate.getCategoryId())){
//					newArea.put(cate.getCategoryId(), cate);
//				}else{
//					//�ظ���ר����Ϣ
//					logger.error("�ļ�"+lfs[i]+"��"+lineNum+"��������ǰ�����Ϣ�ظ�");
//		    		rs[4] ++;
//		    		err.add(line);
//		    		continue;					
//				}
		    	try {
					//����
					if(cate.getChangeType()==1){
						
						if(null==allAreaCate.get(cate.getCategoryId())&&null==newArea.get(cate.getCategoryId())){
							addBookArea(cate);
							rs[1] ++;
						}else{
							//�޸�
							updateBookArea(cate);
							rs[2] ++;							
						}
					}else if(cate.getChangeType()==2){
						if(null==allAreaCate.get(cate.getCategoryId())&&null==newArea.get(cate.getCategoryId())){
							addBookArea(cate);
							rs[1] ++;
						}else{
							//�޸�
							updateBookArea(cate);
							rs[2] ++;							
						}
					}else if(cate.getChangeType()==3){
						//����
						delBookArea(cate);
						rs[3] ++;
					}
					newArea.put(cate.getCategoryId(), cate);
				} catch (Exception e) {
					logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}
			}
			rs[0] +=lineNum;
		}
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_catalog"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}	
	}	
	
	/**
	 * ����ͼ��ר��
	 * @param cate
	 */
	private void addBookArea(RCategoryVO cate)throws Exception{
		logger.debug("insert book Area:"+cate.getCategoryId());
        BookCategoryDao.getInstance().add(cate);		

	}
	
	/**
	 * ����ͼ��ר��
	 * @param cate
	 */
	private void updateBookArea(RCategoryVO cate)throws Exception{
		logger.debug("update book Area:"+cate.getCategoryId());
	    BookCategoryDao.getInstance().update(cate);				
	}
	
	/**
	 * ɾ��ͼ��ר��
	 * @param cate
	 */
	private void delBookArea(RCategoryVO cate)throws Exception{
		logger.debug("delete book Area:"+cate.getCategoryId());
		//cate = BookCategoryDao.getInstance().getCategoryByType(cate.getCategoryId(), cate.getCatalogType());
		BookCategoryDao.getInstance().delete(cate);				
	}	
	
	/**
	 * ����ͼ�����е��봦��  �ܰ���
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookTotalRank(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber)throws Exception{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		//��ɾ��֮ǰ�İ�����
		List sale = new ArrayList();
		List click = new ArrayList();
		List flower = new ArrayList();
		List search = new ArrayList();
		Map allBook = RBookDao.getInstance().queryAllBooks();
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//��ȡҪ����Ķ���

		    	String[] dataArray = line.split("["+sep+"]");
		    	
		    	ReferenceVO ref = new ReferenceVO();
		    	if(null==dataArray[0]||"".equals(dataArray[0].trim())
		    			||null==dataArray[1]||"".equals(dataArray[1].trim())
		    			||null==dataArray[2]||"".equals(dataArray[2].trim())
		    			||null==dataArray[3]||"".equals(dataArray[3].trim())
		    			||null==dataArray[4]||"".equals(dataArray[4].trim())){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[5] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
	    		ref.setCategoryId(dataArray[0]+"T");
	    		ref.setBookId(dataArray[2].trim());
	    		ref.setSortNumber(Integer.parseInt(dataArray[3].trim()));
	    		ref.setRankValue(Integer.parseInt(dataArray[4].trim()));
	    		
	    		if(null==allBook.get(ref.getBookId())){
	    			logger.error("ͼ�鲻���ڣ�"+ref.getBookId());
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("��ͼ�鲻����");
	    			err.add(line);
	    			rs[5] ++;
	    			continue;
	    		}	    		
		    	if(BaseBookConstant.RANK_SALE.equals(dataArray[0])){
		    		sale.add(ref);
		    	}else if(BaseBookConstant.RANK_CLICK.equals(dataArray[0])){
		    		click.add(ref);
		    	}else if(BaseBookConstant.RANK_FLOWER.equals(dataArray[0])){
		    		flower.add(ref);
		    	}else if(BaseBookConstant.RANK_SEARCH.equals(dataArray[0])){
		    		search.add(ref);
		    	}
			}
			rs[0] +=lineNum;
		}
    	try{
    		RCategoryVO cate = new RCategoryVO();
    		//��������
    		if(sale.size()>0){
    			cate.setCategoryId("3T");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("���³���������");
    			updateRank(sale,cate);
    			rs[1] = sale.size();
    		}
    		//���
    		if(click.size()>0){
    			cate.setCategoryId("2T");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("���µ��������");
    			updateRank(click,cate);
    			rs[2] = click.size();
    		}
    		//�ʻ�
    		if(flower.size()>0){
    			cate.setCategoryId("7T");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK); 
    			logger.debug("�����ʻ�������");
    			updateRank(flower,cate);
    			rs[3] = flower.size();
    		}
    		//����
    		if(search.size()>0){
    			cate.setCategoryId("5T");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);  
    			logger.debug("��������������");
    			updateRank(search,cate);
    			rs[4] = search.size();
    		}
    		
    	}catch(Exception e){
			logger.error("�ܰ����ݴ���ʧ��");
			logger.error(e);
			rs[6] = err.size();		    		
    	}		
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_ranktotal"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}	
	}	
	
	/**
	 * ����ͼ�����е��봦��  �°���
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookMonthRank(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber)throws Exception{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		//��ɾ��֮ǰ�İ�����
		List sale = new ArrayList();
		List click = new ArrayList();
		List flower = new ArrayList();
		List search = new ArrayList();
		Map allBook = RBookDao.getInstance().queryAllBooks();
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//��ȡҪ����Ķ���

		    	String[] dataArray = line.split("["+sep+"]");
		    	
		    	ReferenceVO ref = new ReferenceVO();
		    	if(null==dataArray[0]||"".equals(dataArray[0].trim())
		    			||null==dataArray[1]||"".equals(dataArray[1].trim())
		    			||null==dataArray[2]||"".equals(dataArray[2].trim())
		    			||null==dataArray[3]||"".equals(dataArray[3].trim())
		    			||null==dataArray[4]||"".equals(dataArray[4].trim())){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[5] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
	    		ref.setCategoryId(dataArray[0]+"M");
	    		ref.setBookId(dataArray[2].trim());
	    		ref.setSortNumber(Integer.parseInt(dataArray[3].trim()));
	    		ref.setRankValue(Integer.parseInt(dataArray[4].trim()));
	    		
	    		if(null==allBook.get(ref.getBookId())){
	    			logger.error("ͼ�鲻���ڣ�"+ref.getBookId());
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("��ͼ�鲻����");
	    			err.add(line);
	    			rs[5] ++;
	    			continue;
	    		}	    		
		    	if(BaseBookConstant.RANK_SALE.equals(dataArray[0])){
		    		sale.add(ref);
		    	}else if(BaseBookConstant.RANK_CLICK.equals(dataArray[0])){
		    		click.add(ref);
		    	}else if(BaseBookConstant.RANK_FLOWER.equals(dataArray[0])){
		    		flower.add(ref);
		    	}else if(BaseBookConstant.RANK_SEARCH.equals(dataArray[0])){
		    		search.add(ref);
		    	}
			}
			rs[0] +=lineNum;
		}
    	try{
    		RCategoryVO cate = new RCategoryVO();
    		//��������
    		if(sale.size()>0){
    			cate.setCategoryId("3M");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("���³���������");
    			updateRank(sale,cate);
    			rs[1] = sale.size();
    		}
    		//���
    		if(click.size()>0){
    			cate.setCategoryId("2M");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK); 
    			logger.debug("���µ��������");
    			updateRank(click,cate);
    			rs[2] = click.size();
    		}
    		//�ʻ�
    		if(flower.size()>0){
    			cate.setCategoryId("7M");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);  
    			logger.debug("�����ʻ�������");
    			updateRank(flower,cate);
    			rs[3] = flower.size();
    		}
    		//����
    		if(search.size()>0){
    			cate.setCategoryId("5M");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);   
    			logger.debug("��������������");
    			updateRank(search,cate);
    			rs[4] = search.size();
    		}
    		
    	}catch(Exception e){
			logger.error("�°����ݴ���ʧ��");
			logger.error(e);
			rs[6] = err.size();		    		
    	}	
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_rankmonth"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}    	
	}		
	
	/**
	 * ����ͼ�����е��봦��  �ܰ���
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookWeekRank(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber)throws Exception{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		//��ɾ��֮ǰ�İ�����
		List sale = new ArrayList();
		List click = new ArrayList();
		List flower = new ArrayList();
		List search = new ArrayList();
		Map allBook = RBookDao.getInstance().queryAllBooks();
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//��ȡҪ����Ķ���

		    	String[] dataArray = line.split("["+sep+"]");
		    	
		    	ReferenceVO ref = new ReferenceVO();
		    	if(null==dataArray[0]||"".equals(dataArray[0].trim())
		    			||null==dataArray[1]||"".equals(dataArray[1].trim())
		    			||null==dataArray[2]||"".equals(dataArray[2].trim())
		    			||null==dataArray[3]||"".equals(dataArray[3].trim())
		    			||null==dataArray[4]||"".equals(dataArray[4].trim())){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[5] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	
	    		ref.setCategoryId(dataArray[0]+"E");
	    		ref.setBookId(dataArray[2].trim());
	    		ref.setSortNumber(Integer.parseInt(dataArray[3].trim()));
	    		ref.setRankValue(Integer.parseInt(dataArray[4].trim()));
	    		if(null==allBook.get(ref.getBookId())){
	    			logger.error("ͼ�鲻���ڣ�"+ref.getBookId());
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("��ͼ�鲻����");
	    			err.add(line);
	    			rs[5] ++;
	    			continue;
	    		}
		    	if(BaseBookConstant.RANK_SALE.equals(dataArray[0])){
		    		sale.add(ref);
		    	}else if(BaseBookConstant.RANK_CLICK.equals(dataArray[0])){
		    		click.add(ref);
		    	}else if(BaseBookConstant.RANK_FLOWER.equals(dataArray[0])){
		    		flower.add(ref);
		    	}else if(BaseBookConstant.RANK_SEARCH.equals(dataArray[0])){
		    		search.add(ref);
		    	}

			}
			rs[0] +=lineNum;
		}
    	try{
    		RCategoryVO cate = new RCategoryVO();
    		//��������
    		if(sale.size()>0){
    			cate.setCategoryId("3E");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("���³���������");
    			updateRank(sale,cate);
    			rs[1] = sale.size();
    		}
    		//���
    		if(click.size()>0){
    			cate.setCategoryId("2E");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);   
    			logger.debug("���µ��������");
    			updateRank(click,cate);
    			rs[2] = click.size();
    		}
    		//�ʻ�
    		if(flower.size()>0){
    			cate.setCategoryId("7E");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("�����ʻ�������");
    			updateRank(flower,cate);
    			rs[3] = flower.size();
    		}
    		//����
    		if(search.size()>0){
    			cate.setCategoryId("5E");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);    
    			logger.debug("��������������");
    			updateRank(search,cate);
    			rs[4] = search.size();
    		}
    		
    	}catch(Exception e){
			logger.error("�ܰ����ݴ���ʧ��");
			logger.error(e);
			rs[6] = err.size();		    		
    	}	
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_rankweek"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}    	
	}		
	
	/**
	 * ����������Ϣ
	 * @param rank
	 * @param rankType
	 * @throws Exception
	 */
	private void updateRank(List rank , RCategoryVO c)throws Exception{
		RCategoryVO cate = BookCategoryDao.getInstance().getCategoryByType(c.getCategoryId(), c.getCatalogType());
		if(null==cate){
			throw new BOException("���л��ܲ����ڣ�"+c.getCategoryId());
		}
		BookReferenceDao.getInstance().updateRank(rank, cate);
	}
	
	/**
	 * ����ͼ����µ��봦��
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookMonth(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)throws Exception{
		String line = null;
		BufferedReader br = null;
		//taskRunner = new TaskRunner(baseBookSynTaskNum,baseBookMaxReceivedNum);
		List err = new ArrayList();
		
		//��ȡ���µĸ�����ID
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//��ȡҪ����Ķ���

		    	String[] dataArray = line.split("["+sep+"]");
		    	RCategoryVO cate = new RCategoryVO();
		    	if(!cate.setValue(dataArray, BaseBookConstant.CATE_TYPE_MONTH)){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[2] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	
		    	try {
		    		addBookMonth(cate);
		    		rs[1]++;
				} catch (Exception e) {
					logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݴ���ʧ��");
					logger.error(e);
					err.add(line);
					rs[3]++;
                    error.append(lineNum).append(", ");
				}		    	
			}
			rs[0] +=lineNum;
		}	
//		taskRunner.waitToFinished();//�ȴ��������ݿ���ϡ�
//		taskRunner.end();//����������	
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_book2"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}		
	}	
	
	/**
	 * ����ͼ�������Ϣ
	 * @param ref
	 */
	private void addBookMonth(RCategoryVO cate)throws Exception{
		logger.debug("insert book Area:"+cate.getCategoryId());
		if(!BookCategoryDao.getInstance().isMonthExist(cate)){
		    BookCategoryDao.getInstance().addBookMonth(cate);	
		}else{
			BookCategoryDao.getInstance().updateBookMonth(cate);		
		}			
	}	
	
	/**
	 * ����ͼ����Ʒ���봦�� ����ר�����ݵ���
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookReference(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)throws Exception{
		String line = null;
		BufferedReader br = null;
		taskRunner = new TaskRunner(baseBookSynTaskNum,baseBookMaxReceivedNum);
		Map allBook = RBookDao.getInstance().queryAllBooks();
		Map allArea = BookCategoryDao.getInstance().queryAllAreaCate();
		List err = new ArrayList();
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//��ȡҪ����Ķ���

		    	String[] dataArray = line.split("["+sep+"]");
		    	ReferenceVO ref = new ReferenceVO();
		    	if(!ref.setValue(dataArray)){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	if(null==allBook.get(ref.getBookId())){
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�� ͼ�鲻����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("��ͼ�鲻����");
		    		rs[4]++;
		    		err.add(line);
		    		continue;
		    	}
		    	if(null==allArea.get(ref.getCategoryId())){
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�� ר��������");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("��ר��������");
		    		rs[4]++;
		    		err.add(line);
		    		continue;		    		
		    	}
		    	try {
					//����
					if(ref.getChangeType()==1){
						addBookReference(ref);
						rs[1] ++;
					}else if(ref.getChangeType()==2){
						//�޸�
						updateBookReference(ref);
						rs[2] ++;
					}else if(ref.getChangeType()==3){
						//����
						delBookReference(ref);
						rs[3] ++;
					}
				} catch (Exception e) {
					logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}
			}
			rs[0] +=lineNum;
		}	
		taskRunner.waitToFinished();//�ȴ��������ݿ���ϡ�
		taskRunner.end();//����������	
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_catalogcontent"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}		
	}	
	
	/**
	 * ����ר��������Ϣ
	 * @param ref
	 */
	private void addBookReference(ReferenceVO ref)throws Exception{
		logger.debug("insert book reference:"+ref.getBookId());
		Object[] args = new Object[]{ref};
		Class[] argsClass = new Class[]{ReferenceVO.class};		
		if(!BookReferenceDao.getInstance().isExist(ref)){
			ReflectedTask task = new ReflectedTask(BookReferenceDao.getInstance(), "add", args, argsClass);
			this.taskRunner.addTask(task);
		}
	}
	
	private void updateBookReference(ReferenceVO ref)throws Exception{
		logger.debug("update book referenc:"+ref.getBookId());
		addBookReference(ref);			
	}
	
	private void delBookReference(ReferenceVO ref)throws Exception{
		logger.debug("insert book:"+ref.getBookId());
		Object[] args = new Object[]{ref};
		Class[] argsClass = new Class[]{ReferenceVO.class};
		ReflectedTask task = new ReflectedTask(BookReferenceDao.getInstance(), "delete", args, argsClass);
		this.taskRunner.addTask(task);			
	}	
	
	/**
	 * ����ͼ����Ϣ���봦��
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBook(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)throws Exception{
		String line = null;
		BufferedReader br = null;
		Map all = RBookDao.getInstance().queryAllBooks();
		taskRunner = new TaskRunner(baseBookSynTaskNum,baseBookMaxReceivedNum);
		List err = new ArrayList();
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//��ȡҪ����Ķ���

		    	String[] dataArray = line.split("["+sep+"]");
		    	RBookVO book = new RBookVO();
		    	if(!book.setValue(dataArray)){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	
		    	try {
		    		//�ж�ͼ������ �������Ƿ����
					//����
					if(book.getChangeType()==1){
						if(null==all.get(book.getBookId())){
						    addBook(book);
						    rs[1] ++;
						}else{
							//�޸�
							updateBook(book);
							rs[2] ++;							
						}
					}else if(book.getChangeType()==2){
						if(null==all.get(book.getBookId())){
						    addBook(book);
						    rs[1] ++;
						}else{
							//�޸�
							updateBook(book);
							rs[2] ++;							
						}
					}else if(book.getChangeType()==3){
						//����
						delBook(book);
						rs[3] ++;
					}
				} catch (Exception e) {
					logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}		    	
			}
			rs[0] +=lineNum;
		}	
		taskRunner.waitToFinished();//�ȴ��������ݿ���ϡ�
		taskRunner.end();//����������	
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_book"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}		
	}	
	
	/**
	 * ����ͼ����Ϣ
	 * @param book
	 */
	private void addBook(RBookVO book)throws Exception{
		logger.debug("insert book:"+book.getBookName());
		RTypeVO type = new RTypeVO();
		type.setTypeId(book.getTypeId());
		BookAuthorVO author = new BookAuthorVO();
		author.setAuthorId(book.getAuthorId());
		type = TypeDao.getInstance().getRTypeVO(type);
		author = BookAuthorDao.getInstance().getAuthor(author);
		if(null==type||null==author){
			throw new BOException("ͼ��"+book.getBookName()+"���߲�����");
		}
		//������������
		book.setAuthorName(author.getAuthorName());
		//���ô���id
		book.setTypeId(type.getParentId());
		Object[] args = new Object[]{book};
		Class[] argsClass = new Class[]{RBookVO.class};
		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(), "add", args, argsClass);
		this.taskRunner.addTask(task);	
	}
	
	/**
	 * ����
	 * @param book
	 */
	private void updateBook(RBookVO book)throws Exception{
		logger.debug("update book:"+book.getBookName());
		RTypeVO type = new RTypeVO();
		type.setTypeId(book.getTypeId());
		BookAuthorVO author = new BookAuthorVO();
		author.setAuthorId(book.getAuthorId());
		type = TypeDao.getInstance().getRTypeVO(type);
		author = BookAuthorDao.getInstance().getAuthor(author);
		if(null==type||null==author){
			throw new BOException("ͼ��"+book.getBookName()+"���߲�����");
		}
		//������������
		book.setAuthorName(author.getAuthorName());
		//���ô���id
		book.setTypeId(type.getParentId());	
		Object[] args = new Object[]{book};
		Class[] argsClass = new Class[]{RBookVO.class};

		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(), "update", args, argsClass);
		this.taskRunner.addTask(task);	
		
	}
	
	/**
	 * ɾ������־λɾ�� ����delflag=1
	 * @param book
	 */
	private void delBook(RBookVO book){
		logger.debug("delete book:"+book.getBookName());
		Object[] args = new Object[]{book};
		Class[] argsClass = new Class[]{RBookVO.class};
		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(), "delete", args, argsClass);
		this.taskRunner.addTask(task);	
	}
	
	/**
	 * ����ͼ���Ƽ����봦��
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookRecommend(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)throws Exception{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		//taskRunner = new TaskRunner(baseBookSynTaskNum,baseBookMaxReceivedNum);
		//Map allRecommend = BookRecommendDao.getInstance().queryAllRecommend();
		Map allBook = RBookDao.getInstance().queryAllBooks();
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//��ȡҪ����Ķ���

		    	String[] dataArray = line.split("["+sep+"]");
		    	RecommendVO recommend = new RecommendVO();
		    	if(!recommend.setValue(dataArray)){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	if(null==allBook.get(recommend.getBookId())){
		    		logger.error("�Ƽ�ͼ�鲻����"+recommend.getBookId());
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("���Ƽ�ͼ�鲻����");
		    		rs[4]++;
		    		err.add(line);
		    		continue;
		    	}
		    	try {
					//����
					if(recommend.getChangeType()==1){
						if(!BookRecommendDao.getInstance().isExist(recommend)){
						    addRecommend(recommend);
						    rs[1] ++;
						}else{
							updateRecommend(recommend);
							rs[2] ++;
						}
					}else if(recommend.getChangeType()==2){
						if(!BookRecommendDao.getInstance().isExist(recommend)){
						    addRecommend(recommend);
						    rs[1] ++;
						}else{
							updateRecommend(recommend);
							rs[2] ++;
						}
					}else if(recommend.getChangeType()==3){
						//����
						delRecommend(recommend);
						rs[3] ++;
					}
				} catch (Exception e) {
					logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}		    	
			}
			rs[0] +=lineNum;
		}	
		//allRecommend = null;
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_bookrecommend"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}		
	}	
	
	/**
	 * �����Ƽ�ͼ��
	 * @param recommend
	 */
	private void addRecommend(RecommendVO recommend)throws Exception{
		logger.debug("insert recommend:"+recommend.getBookId());
	    BookRecommendDao.getInstance().add(recommend);			
	}
	
	/**
	 * �����Ƽ���Ϣ
	 * @param recommend
	 */
	private void updateRecommend(RecommendVO recommend)throws Exception{
		logger.debug("insert recommend:"+recommend.getBookId());
		BookRecommendDao.getInstance().update(recommend);
	}
	
	/**
	 * ɾ���Ƽ���Ϣ
	 * @param recommend
	 */
	private void delRecommend(RecommendVO recommend)throws Exception{
		logger.debug("insert recommend:"+recommend.getBookId());
		BookRecommendDao.getInstance().delete(recommend);		
	}	
	
	/**
	 * ����ͼ�����ߵ��봦��
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 */
	public void dealBaseBookAuthor(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error)throws Exception{
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		Map all = BookAuthorDao.getInstance().queryAllAuthor();
		Map newAuthor = new HashMap();
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//��ȡҪ����Ķ���

		    	String[] dataArray = line.split("["+sep+"]");
		    	BookAuthorVO author = new BookAuthorVO();
		    	if(!author.setValue(dataArray)){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	try {
					//����
					if(author.getChangeType()==1){
						if(null==all.get(author.getAuthorId())&&null==newAuthor.get(author.getAuthorId())){
						    addAuthor(author);
						    rs[1] ++;
						}else{
							//�޸�
							updateAuthor(author);
							rs[2] ++;							
						}
					}else if(author.getChangeType()==2){
						if(null==all.get(author.getAuthorId())&&null==newAuthor.get(author.getAuthorId())){
						    addAuthor(author);
						    rs[1] ++;
						}else{
							//�޸�
							updateAuthor(author);
							rs[2] ++;							
						}
					}else if(author.getChangeType()==3){
						//����
						deleteAuthor(author);
						rs[3] ++;
					}
					newAuthor.put(author.getAuthorId(), author);
				} catch (Exception e) {
					logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݴ���ʧ��");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}		    	
			}
			rs[0] +=lineNum;
		}	
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_author"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}
	}
	
	/**
	 * ��������
	 * @param author
	 */
	private void addAuthor(BookAuthorVO author)throws Exception{
		logger.debug("insert new book author:"+author.getAuthorName());
		BookAuthorDao.getInstance().add(author);		
	}
	
	/**
	 * ����������Ϣ
	 * @param author
	 */
	private void updateAuthor(BookAuthorVO author)throws Exception{
		logger.debug("insert new book author:"+author.getAuthorName());

		BookAuthorDao.getInstance().update(author);	
	
	}
	
	/**
	 * ɾ������
	 * @param author
	 */
	private void deleteAuthor(BookAuthorVO author)throws Exception{
		logger.debug("insert new book author:"+author.getAuthorName());
		BookAuthorDao.getInstance().delete(author);			
	}
	
	/**
	 * �������ͼ�����ݷ��ർ��
	 * @param encoding
	 * @param sep
	 * @param rs
	 * @param lfs
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void dealBaseBookType(String encoding, String sep, int[] rs,
			String[] lfs, StringBuffer errorRowNumber, StringBuffer error) throws Exception {
		String line = null;
		BufferedReader br = null;
		List err = new ArrayList();
		//taskRunner = new TaskRunner(baseBookSynTaskNum,baseBookMaxReceivedNum);
		//ȡ�����еĴ���
		Map allP = TypeDao.getInstance().getAllParentType();
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("��ʼ�����ļ�"+lfs[i]+"��" + lineNum + "�����ݡ�");
				
				if("".equals(line.trim())){
					continue;
				}
				

		    	String[] dataArray = line.split("["+sep+"]");
		    	RTypeVO type = new RTypeVO();
		    	if(!type.setValue(dataArray)){
		    		//���ݸ�ʽ����
		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݸ�ʽ����");
                    errorRowNumber.append("<br>").append("  ��").append(lineNum).append("�����ݸ�ʽ����");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;				    		
		    	}
//		    	if(null==allP.get(getTypeMap().get(type.getTypeName()))){
//		    		logger.error("�ļ�"+lfs[i]+"��"+lineNum+"δ�ҵ������");
//		    		rs[4] ++;
//		    		err.add(line);
//		    		continue;
//		    	}
		    	type.setParentId((String) allP.get(getTypeMap().get(type.getTypeName())));
		    	try {
					//����
					if(type.getChangeType()==1){
						addType(type);
						rs[1] ++;
					}else if(type.getChangeType()==2){
						//�޸�
						updateType(type);
						rs[2] ++;
					}else if(type.getChangeType()==3){
						//����
						deleteType(type);
						rs[3] ++;
					}
				} catch (Exception e) {
					logger.error("�ļ�"+lfs[i]+"��"+lineNum+"�����ݴ���ʧ��");
					logger.error(e);
					err.add(line);
					rs[5]++;
                    error.append(lineNum).append(", ");
				}
			}
			rs[0] +=lineNum;
		}
	    //���ɵ�������ļ�
		if(err.size()>0){
			String errFile = "err_category"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}
	}
	
	/**
	 * ���ɵ��������ļ�
	 * @param errFile
	 * @param err
	 */
	private void makeErrFile(String errFile,List err){
		BufferedWriter out = null;
		try{
			String path = BaseBookConfig.get("errorFileDir");
			IOUtil.checkAndCreateDir(path);
			File f = new File(path+File.separator+errFile);
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			if(null==err){
				return ;
			}
			int size = err.size();
			for(int i=0;i<size;i++){
				out.write((String)err.get(i));
				out.write("\r\n");
			}
			out.flush();
		}catch(Exception e){
			logger.error("��������ļ�ʧ��",e);
		}finally{
			if(null!=out){
				try {
					out.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}
	
	/**
	 * ����ͼ������
	 * @param type
	 */
	private void addType(RTypeVO type)throws Exception{
		logger.debug("insert new booktype:"+type.getTypeId()+","+type.getTypeName());
		if(!TypeDao.getInstance().isExist(type)){
		    TypeDao.getInstance().addType(type);
		}else{
			TypeDao.getInstance().update(type);	
		}
	}
	
	/**
	 * ����ͼ������
	 * @param type
	 */
	private void updateType(RTypeVO type)throws Exception{
		logger.debug("update booktype:"+type.getTypeId()+","+type.getTypeName());
		if(!TypeDao.getInstance().isExist(type)){
		    TypeDao.getInstance().addType(type);
		}else{
			TypeDao.getInstance().update(type);	
		}	
	}
	
	/**
	 * ɾ��ͼ������
	 * @param type
	 */
	private void deleteType(RTypeVO type)throws Exception{
		logger.debug("delete booktype:"+type.getTypeId()+","+type.getTypeName());
		TypeDao.getInstance().delete(type);			
	}
	
	private Map getTypeMap(){
		
		Map m = new HashMap();
		
		ArrayValue[] typeArray = BaseBookConfig.getArrayValue("BaseBookType");
		if(null!=typeArray){
			for(int i=0;i<typeArray.length;i++){
				String tmp = typeArray[i].getValue();
				String parent = tmp.split("[|]")[0];
				String[] child = tmp.split("[|]")[1].split("[;]");
				for(int j=0;j<child.length;j++){
					m.put(child[j], parent);
				}
			}
		}
//		
//		m.put("����", "��������");
//		m.put("����", "��������");
//		m.put("��Խ", "��Խ����");
//		m.put("����", "�������");
//		m.put("���", "�������");
//		m.put("����", "��������");
//		m.put("����", "��������");
//		m.put("��Ϸ", "��Ϸ����");
//		m.put("����", "��Ϸ����");
//		m.put("�ഺ", "�����ഺ");
//		m.put("��ʷ", "��ʷ����");
//		m.put("����", "��ʷ����");
//		m.put("����", "��������");
//		m.put("�ŵ�", "��������");
//		m.put("����", "��������");
//		m.put("����", "��������");
//		m.put("��ʵ", "��������");
//		m.put("�ƻ�", "�ƻ�С˵");
//		m.put("Ӱ��", "Ӱ�Ӿ籾");
//		m.put("ʱ��", "ʱ������");
//		m.put("����", "ʱ������");
//		m.put("��ͥ", "ʱ������");
//		m.put("����", "ʱ������");
//		m.put("�ٳ�", "�ٳ�ְ��");
//		m.put("ְ��", "�ٳ�ְ��");
//		m.put("��ս", "�ٳ�ְ��");
//		m.put("����", "������־");
//		m.put("��־", "������־");
//		m.put("���", "������־");
//		m.put("����", "�������");
//		m.put("���", "�������");
//		m.put("����", "�������");
//		m.put("��ƪ", "��ƪСƷ");
//		m.put("��Ĭ", "��ƪСƷ");
//		m.put("����", "��ƪСƷ");
//		m.put("ͯ��", "��ƪСƷ");
		return m;
	}	
	
	/**
	 * ���»�������Ʒ����
	 */
	public void updateCateTotal(){
		logger.debug("update book category total books begin!");
		try {
			BookCategoryDao.getInstance().updateCateTotal();
		} catch (Exception e) {
			logger.error("���»�������Ʒ��������",e);
		}
		logger.debug("update book category total books end!");
	}
	
	/**
	 * ����ͼ���������Ʒ����
	 */
	public void updateBookTypeTotal(){
		logger.debug("update book type total books begin!");
		try {
			///update t_rb_type tp set tp.totalbooks = (select count(*) from t_rb_book b where b.typeid=tp.typeid and b.delflag=0) where tp.parentid='0'
		} catch (Exception e) {
			logger.error("���·�������Ʒ��������",e);
		}
		logger.debug("update book type total books end!");		
	}
}
