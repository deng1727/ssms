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
 * 基地图书处理业务类
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
	 * 基地图书专区导入处理
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
		//查询所有的货架
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
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//获取要处理的对象

		    	String[] dataArray = line.split("["+sep+"]");
		    	RCategoryVO cate = new RCategoryVO();
		    	if(!cate.setValue(dataArray, null)){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
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
//					//重复的专区信息
//					logger.error("文件"+lfs[i]+"第"+lineNum+"行数据与前面的信息重复");
//		    		rs[4] ++;
//		    		err.add(line);
//		    		continue;					
//				}
		    	try {
					//新增
					if(cate.getChangeType()==1){
						
						if(null==allAreaCate.get(cate.getCategoryId())&&null==newArea.get(cate.getCategoryId())){
							addBookArea(cate);
							rs[1] ++;
						}else{
							//修改
							updateBookArea(cate);
							rs[2] ++;							
						}
					}else if(cate.getChangeType()==2){
						if(null==allAreaCate.get(cate.getCategoryId())&&null==newArea.get(cate.getCategoryId())){
							addBookArea(cate);
							rs[1] ++;
						}else{
							//修改
							updateBookArea(cate);
							rs[2] ++;							
						}
					}else if(cate.getChangeType()==3){
						//下线
						delBookArea(cate);
						rs[3] ++;
					}
					newArea.put(cate.getCategoryId(), cate);
				} catch (Exception e) {
					logger.error("文件"+lfs[i]+"第"+lineNum+"行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}
			}
			rs[0] +=lineNum;
		}
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_catalog"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}	
	}	
	
	/**
	 * 新增图书专区
	 * @param cate
	 */
	private void addBookArea(RCategoryVO cate)throws Exception{
		logger.debug("insert book Area:"+cate.getCategoryId());
        BookCategoryDao.getInstance().add(cate);		

	}
	
	/**
	 * 更新图书专区
	 * @param cate
	 */
	private void updateBookArea(RCategoryVO cate)throws Exception{
		logger.debug("update book Area:"+cate.getCategoryId());
	    BookCategoryDao.getInstance().update(cate);				
	}
	
	/**
	 * 删除图书专区
	 * @param cate
	 */
	private void delBookArea(RCategoryVO cate)throws Exception{
		logger.debug("delete book Area:"+cate.getCategoryId());
		//cate = BookCategoryDao.getInstance().getCategoryByType(cate.getCategoryId(), cate.getCatalogType());
		BookCategoryDao.getInstance().delete(cate);				
	}	
	
	/**
	 * 基地图书排行导入处理  总榜处理
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
		//先删除之前的榜单内容
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
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//获取要处理的对象

		    	String[] dataArray = line.split("["+sep+"]");
		    	
		    	ReferenceVO ref = new ReferenceVO();
		    	if(null==dataArray[0]||"".equals(dataArray[0].trim())
		    			||null==dataArray[1]||"".equals(dataArray[1].trim())
		    			||null==dataArray[2]||"".equals(dataArray[2].trim())
		    			||null==dataArray[3]||"".equals(dataArray[3].trim())
		    			||null==dataArray[4]||"".equals(dataArray[4].trim())){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
		    		rs[5] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
	    		ref.setCategoryId(dataArray[0]+"T");
	    		ref.setBookId(dataArray[2].trim());
	    		ref.setSortNumber(Integer.parseInt(dataArray[3].trim()));
	    		ref.setRankValue(Integer.parseInt(dataArray[4].trim()));
	    		
	    		if(null==allBook.get(ref.getBookId())){
	    			logger.error("图书不存在！"+ref.getBookId());
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行图书不存在");
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
    		//畅销排行
    		if(sale.size()>0){
    			cate.setCategoryId("3T");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("更新畅销总排行");
    			updateRank(sale,cate);
    			rs[1] = sale.size();
    		}
    		//点击
    		if(click.size()>0){
    			cate.setCategoryId("2T");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("更新点击总排行");
    			updateRank(click,cate);
    			rs[2] = click.size();
    		}
    		//鲜花
    		if(flower.size()>0){
    			cate.setCategoryId("7T");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK); 
    			logger.debug("更新鲜花总排行");
    			updateRank(flower,cate);
    			rs[3] = flower.size();
    		}
    		//搜索
    		if(search.size()>0){
    			cate.setCategoryId("5T");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);  
    			logger.debug("更新搜索总排行");
    			updateRank(search,cate);
    			rs[4] = search.size();
    		}
    		
    	}catch(Exception e){
			logger.error("总榜数据处理失败");
			logger.error(e);
			rs[6] = err.size();		    		
    	}		
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_ranktotal"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}	
	}	
	
	/**
	 * 基地图书排行导入处理  月榜处理
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
		//先删除之前的榜单内容
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
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//获取要处理的对象

		    	String[] dataArray = line.split("["+sep+"]");
		    	
		    	ReferenceVO ref = new ReferenceVO();
		    	if(null==dataArray[0]||"".equals(dataArray[0].trim())
		    			||null==dataArray[1]||"".equals(dataArray[1].trim())
		    			||null==dataArray[2]||"".equals(dataArray[2].trim())
		    			||null==dataArray[3]||"".equals(dataArray[3].trim())
		    			||null==dataArray[4]||"".equals(dataArray[4].trim())){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
		    		rs[5] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
	    		ref.setCategoryId(dataArray[0]+"M");
	    		ref.setBookId(dataArray[2].trim());
	    		ref.setSortNumber(Integer.parseInt(dataArray[3].trim()));
	    		ref.setRankValue(Integer.parseInt(dataArray[4].trim()));
	    		
	    		if(null==allBook.get(ref.getBookId())){
	    			logger.error("图书不存在！"+ref.getBookId());
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行图书不存在");
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
    		//畅销排行
    		if(sale.size()>0){
    			cate.setCategoryId("3M");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("更新畅销月排行");
    			updateRank(sale,cate);
    			rs[1] = sale.size();
    		}
    		//点击
    		if(click.size()>0){
    			cate.setCategoryId("2M");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK); 
    			logger.debug("更新点击月排行");
    			updateRank(click,cate);
    			rs[2] = click.size();
    		}
    		//鲜花
    		if(flower.size()>0){
    			cate.setCategoryId("7M");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);  
    			logger.debug("更新鲜花月排行");
    			updateRank(flower,cate);
    			rs[3] = flower.size();
    		}
    		//搜索
    		if(search.size()>0){
    			cate.setCategoryId("5M");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);   
    			logger.debug("更新搜索月排行");
    			updateRank(search,cate);
    			rs[4] = search.size();
    		}
    		
    	}catch(Exception e){
			logger.error("月榜数据处理失败");
			logger.error(e);
			rs[6] = err.size();		    		
    	}	
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_rankmonth"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}    	
	}		
	
	/**
	 * 基地图书排行导入处理  周榜处理
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
		//先删除之前的榜单内容
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
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//获取要处理的对象

		    	String[] dataArray = line.split("["+sep+"]");
		    	
		    	ReferenceVO ref = new ReferenceVO();
		    	if(null==dataArray[0]||"".equals(dataArray[0].trim())
		    			||null==dataArray[1]||"".equals(dataArray[1].trim())
		    			||null==dataArray[2]||"".equals(dataArray[2].trim())
		    			||null==dataArray[3]||"".equals(dataArray[3].trim())
		    			||null==dataArray[4]||"".equals(dataArray[4].trim())){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
		    		rs[5] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	
	    		ref.setCategoryId(dataArray[0]+"E");
	    		ref.setBookId(dataArray[2].trim());
	    		ref.setSortNumber(Integer.parseInt(dataArray[3].trim()));
	    		ref.setRankValue(Integer.parseInt(dataArray[4].trim()));
	    		if(null==allBook.get(ref.getBookId())){
	    			logger.error("图书不存在！"+ref.getBookId());
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行图书不存在");
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
    		//畅销排行
    		if(sale.size()>0){
    			cate.setCategoryId("3E");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("更新畅销周排行");
    			updateRank(sale,cate);
    			rs[1] = sale.size();
    		}
    		//点击
    		if(click.size()>0){
    			cate.setCategoryId("2E");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);   
    			logger.debug("更新点击周排行");
    			updateRank(click,cate);
    			rs[2] = click.size();
    		}
    		//鲜花
    		if(flower.size()>0){
    			cate.setCategoryId("7E");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);
    			logger.debug("更新鲜花周排行");
    			updateRank(flower,cate);
    			rs[3] = flower.size();
    		}
    		//搜索
    		if(search.size()>0){
    			cate.setCategoryId("5E");
    			cate.setCatalogType(BaseBookConstant.CATE_TYPE_RANK);    
    			logger.debug("更新搜索周排行");
    			updateRank(search,cate);
    			rs[4] = search.size();
    		}
    		
    	}catch(Exception e){
			logger.error("周榜数据处理失败");
			logger.error(e);
			rs[6] = err.size();		    		
    	}	
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_rankweek"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}    	
	}		
	
	/**
	 * 更新排行信息
	 * @param rank
	 * @param rankType
	 * @throws Exception
	 */
	private void updateRank(List rank , RCategoryVO c)throws Exception{
		RCategoryVO cate = BookCategoryDao.getInstance().getCategoryByType(c.getCategoryId(), c.getCatalogType());
		if(null==cate){
			throw new BOException("排行货架不存在！"+c.getCategoryId());
		}
		BookReferenceDao.getInstance().updateRank(rank, cate);
	}
	
	/**
	 * 基地图书包月导入处理
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
		
		//获取包月的父货架ID
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//获取要处理的对象

		    	String[] dataArray = line.split("["+sep+"]");
		    	RCategoryVO cate = new RCategoryVO();
		    	if(!cate.setValue(dataArray, BaseBookConstant.CATE_TYPE_MONTH)){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
		    		rs[2] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	
		    	try {
		    		addBookMonth(cate);
		    		rs[1]++;
				} catch (Exception e) {
					logger.error("文件"+lfs[i]+"第"+lineNum+"行数据处理失败");
					logger.error(e);
					err.add(line);
					rs[3]++;
                    error.append(lineNum).append(", ");
				}		    	
			}
			rs[0] +=lineNum;
		}	
//		taskRunner.waitToFinished();//等待更新数据库完毕。
//		taskRunner.end();//结束运行器	
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_book2"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}		
	}	
	
	/**
	 * 新增图书包月信息
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
	 * 基地图书商品导入处理 用于专区内容导入
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
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//获取要处理的对象

		    	String[] dataArray = line.split("["+sep+"]");
		    	ReferenceVO ref = new ReferenceVO();
		    	if(!ref.setValue(dataArray)){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	if(null==allBook.get(ref.getBookId())){
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行 图书不存在");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行图书不存在");
		    		rs[4]++;
		    		err.add(line);
		    		continue;
		    	}
		    	if(null==allArea.get(ref.getCategoryId())){
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行 专区不存在");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行专区不存在");
		    		rs[4]++;
		    		err.add(line);
		    		continue;		    		
		    	}
		    	try {
					//新增
					if(ref.getChangeType()==1){
						addBookReference(ref);
						rs[1] ++;
					}else if(ref.getChangeType()==2){
						//修改
						updateBookReference(ref);
						rs[2] ++;
					}else if(ref.getChangeType()==3){
						//下线
						delBookReference(ref);
						rs[3] ++;
					}
				} catch (Exception e) {
					logger.error("文件"+lfs[i]+"第"+lineNum+"行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}
			}
			rs[0] +=lineNum;
		}	
		taskRunner.waitToFinished();//等待更新数据库完毕。
		taskRunner.end();//结束运行器	
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_catalogcontent"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}		
	}	
	
	/**
	 * 新增专区内容信息
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
	 * 基地图书信息导入处理
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
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//获取要处理的对象

		    	String[] dataArray = line.split("["+sep+"]");
		    	RBookVO book = new RBookVO();
		    	if(!book.setValue(dataArray)){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	
		    	try {
		    		//判断图书类型 、作者是否存在
					//新增
					if(book.getChangeType()==1){
						if(null==all.get(book.getBookId())){
						    addBook(book);
						    rs[1] ++;
						}else{
							//修改
							updateBook(book);
							rs[2] ++;							
						}
					}else if(book.getChangeType()==2){
						if(null==all.get(book.getBookId())){
						    addBook(book);
						    rs[1] ++;
						}else{
							//修改
							updateBook(book);
							rs[2] ++;							
						}
					}else if(book.getChangeType()==3){
						//下线
						delBook(book);
						rs[3] ++;
					}
				} catch (Exception e) {
					logger.error("文件"+lfs[i]+"第"+lineNum+"行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}		    	
			}
			rs[0] +=lineNum;
		}	
		taskRunner.waitToFinished();//等待更新数据库完毕。
		taskRunner.end();//结束运行器	
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_book"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}		
	}	
	
	/**
	 * 新增图书信息
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
			throw new BOException("图书"+book.getBookName()+"作者不存在");
		}
		//设置作者名称
		book.setAuthorName(author.getAuthorName());
		//设置大类id
		book.setTypeId(type.getParentId());
		Object[] args = new Object[]{book};
		Class[] argsClass = new Class[]{RBookVO.class};
		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(), "add", args, argsClass);
		this.taskRunner.addTask(task);	
	}
	
	/**
	 * 更新
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
			throw new BOException("图书"+book.getBookName()+"作者不存在");
		}
		//设置作者名称
		book.setAuthorName(author.getAuthorName());
		//设置大类id
		book.setTypeId(type.getParentId());	
		Object[] args = new Object[]{book};
		Class[] argsClass = new Class[]{RBookVO.class};

		ReflectedTask task = new ReflectedTask(RBookDao.getInstance(), "update", args, argsClass);
		this.taskRunner.addTask(task);	
		
	}
	
	/**
	 * 删除、标志位删除 设置delflag=1
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
	 * 基地图书推荐导入处理
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
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//获取要处理的对象

		    	String[] dataArray = line.split("["+sep+"]");
		    	RecommendVO recommend = new RecommendVO();
		    	if(!recommend.setValue(dataArray)){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	if(null==allBook.get(recommend.getBookId())){
		    		logger.error("推荐图书不存在"+recommend.getBookId());
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行推荐图书不存在");
		    		rs[4]++;
		    		err.add(line);
		    		continue;
		    	}
		    	try {
					//新增
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
						//下线
						delRecommend(recommend);
						rs[3] ++;
					}
				} catch (Exception e) {
					logger.error("文件"+lfs[i]+"第"+lineNum+"行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}		    	
			}
			rs[0] +=lineNum;
		}	
		//allRecommend = null;
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_bookrecommend"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}		
	}	
	
	/**
	 * 新增推荐图书
	 * @param recommend
	 */
	private void addRecommend(RecommendVO recommend)throws Exception{
		logger.debug("insert recommend:"+recommend.getBookId());
	    BookRecommendDao.getInstance().add(recommend);			
	}
	
	/**
	 * 更新推荐信息
	 * @param recommend
	 */
	private void updateRecommend(RecommendVO recommend)throws Exception{
		logger.debug("insert recommend:"+recommend.getBookId());
		BookRecommendDao.getInstance().update(recommend);
	}
	
	/**
	 * 删除推荐信息
	 * @param recommend
	 */
	private void delRecommend(RecommendVO recommend)throws Exception{
		logger.debug("insert recommend:"+recommend.getBookId());
		BookRecommendDao.getInstance().delete(recommend);		
	}	
	
	/**
	 * 基地图书作者导入处理
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
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				
				//获取要处理的对象

		    	String[] dataArray = line.split("["+sep+"]");
		    	BookAuthorVO author = new BookAuthorVO();
		    	if(!author.setValue(dataArray)){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;			    		
		    	}
		    	try {
					//新增
					if(author.getChangeType()==1){
						if(null==all.get(author.getAuthorId())&&null==newAuthor.get(author.getAuthorId())){
						    addAuthor(author);
						    rs[1] ++;
						}else{
							//修改
							updateAuthor(author);
							rs[2] ++;							
						}
					}else if(author.getChangeType()==2){
						if(null==all.get(author.getAuthorId())&&null==newAuthor.get(author.getAuthorId())){
						    addAuthor(author);
						    rs[1] ++;
						}else{
							//修改
							updateAuthor(author);
							rs[2] ++;							
						}
					}else if(author.getChangeType()==3){
						//下线
						deleteAuthor(author);
						rs[3] ++;
					}
					newAuthor.put(author.getAuthorId(), author);
				} catch (Exception e) {
					logger.error("文件"+lfs[i]+"第"+lineNum+"行数据处理失败");
					logger.error(e);
					rs[5]++;
					err.add(line);
                    error.append(lineNum).append(", ");
				}		    	
			}
			rs[0] +=lineNum;
		}	
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_author"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}
	}
	
	/**
	 * 新增作者
	 * @param author
	 */
	private void addAuthor(BookAuthorVO author)throws Exception{
		logger.debug("insert new book author:"+author.getAuthorName());
		BookAuthorDao.getInstance().add(author);		
	}
	
	/**
	 * 更新作者信息
	 * @param author
	 */
	private void updateAuthor(BookAuthorVO author)throws Exception{
		logger.debug("insert new book author:"+author.getAuthorName());

		BookAuthorDao.getInstance().update(author);	
	
	}
	
	/**
	 * 删除作者
	 * @param author
	 */
	private void deleteAuthor(BookAuthorVO author)throws Exception{
		logger.debug("insert new book author:"+author.getAuthorName());
		BookAuthorDao.getInstance().delete(author);			
	}
	
	/**
	 * 处理基地图书数据分类导入
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
		//取出所有的大类
		Map allP = TypeDao.getInstance().getAllParentType();
		for(int i=0;i<lfs.length;i++){
			br = new BufferedReader(new InputStreamReader(new FileInputStream(lfs[i]),encoding));
			int lineNum = 0;
			while((line=br.readLine())!=null){
				lineNum++;
				if(lineNum==1){
					line=PublicUtil.delStringWithBOM(line);
				}
				logger.debug("开始处理文件"+lfs[i]+"第" + lineNum + "行数据。");
				
				if("".equals(line.trim())){
					continue;
				}
				

		    	String[] dataArray = line.split("["+sep+"]");
		    	RTypeVO type = new RTypeVO();
		    	if(!type.setValue(dataArray)){
		    		//数据格式错误
		    		logger.error("文件"+lfs[i]+"第"+lineNum+"行数据格式错误");
                    errorRowNumber.append("<br>").append("  第").append(lineNum).append("行数据格式错误");
		    		rs[4] ++;
		    		err.add(line);
		    		continue;				    		
		    	}
//		    	if(null==allP.get(getTypeMap().get(type.getTypeName()))){
//		    		logger.error("文件"+lfs[i]+"第"+lineNum+"未找到大分类");
//		    		rs[4] ++;
//		    		err.add(line);
//		    		continue;
//		    	}
		    	type.setParentId((String) allP.get(getTypeMap().get(type.getTypeName())));
		    	try {
					//新增
					if(type.getChangeType()==1){
						addType(type);
						rs[1] ++;
					}else if(type.getChangeType()==2){
						//修改
						updateType(type);
						rs[2] ++;
					}else if(type.getChangeType()==3){
						//下线
						deleteType(type);
						rs[3] ++;
					}
				} catch (Exception e) {
					logger.error("文件"+lfs[i]+"第"+lineNum+"行数据处理失败");
					logger.error(e);
					err.add(line);
					rs[5]++;
                    error.append(lineNum).append(", ");
				}
			}
			rs[0] +=lineNum;
		}
	    //生成导入错误文件
		if(err.size()>0){
			String errFile = "err_category"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt";
			makeErrFile(errFile,err);
		}
	}
	
	/**
	 * 生成导入错误的文件
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
			logger.error("导入错误文件失败",e);
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
	 * 新增图书类型
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
	 * 更新图书类型
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
	 * 删除图书类型
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
//		m.put("都市", "都市言情");
//		m.put("言情", "都市言情");
//		m.put("穿越", "穿越幻想");
//		m.put("玄幻", "玄幻奇幻");
//		m.put("奇幻", "玄幻奇幻");
//		m.put("武侠", "武侠仙侠");
//		m.put("仙侠", "武侠仙侠");
//		m.put("游戏", "游戏竞技");
//		m.put("竞技", "游戏竞技");
//		m.put("青春", "浪漫青春");
//		m.put("历史", "历史军事");
//		m.put("军事", "历史军事");
//		m.put("悬疑", "灵异悬疑");
//		m.put("古典", "名著传记");
//		m.put("传记", "名著传记");
//		m.put("译林", "名著传记");
//		m.put("纪实", "名著传记");
//		m.put("科幻", "科幻小说");
//		m.put("影视", "影视剧本");
//		m.put("时尚", "时尚生活");
//		m.put("生活", "时尚生活");
//		m.put("家庭", "时尚生活");
//		m.put("旅游", "时尚生活");
//		m.put("官场", "官场职场");
//		m.put("职场", "官场职场");
//		m.put("商战", "官场职场");
//		m.put("经管", "经管励志");
//		m.put("励志", "经管励志");
//		m.put("理财", "经管励志");
//		m.put("教育", "教育社科");
//		m.put("社科", "教育社科");
//		m.put("科普", "教育社科");
//		m.put("短篇", "短篇小品");
//		m.put("幽默", "短篇小品");
//		m.put("美文", "短篇小品");
//		m.put("童话", "短篇小品");
		return m;
	}	
	
	/**
	 * 更新货架下商品总数
	 */
	public void updateCateTotal(){
		logger.debug("update book category total books begin!");
		try {
			BookCategoryDao.getInstance().updateCateTotal();
		} catch (Exception e) {
			logger.error("更新货架下商品总数出错！",e);
		}
		logger.debug("update book category total books end!");
	}
	
	/**
	 * 更新图书分类下商品总数
	 */
	public void updateBookTypeTotal(){
		logger.debug("update book type total books begin!");
		try {
			///update t_rb_type tp set tp.totalbooks = (select count(*) from t_rb_book b where b.typeid=tp.typeid and b.delflag=0) where tp.parentid='0'
		} catch (Exception e) {
			logger.error("更新分类下商品总数出错！",e);
		}
		logger.debug("update book type total books end!");		
	}
}
