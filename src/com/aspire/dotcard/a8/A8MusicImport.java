package com.aspire.dotcard.a8;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GAudio;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;

public class A8MusicImport extends A8DataImport
{
	/**
	 * 用于缓存终端音乐各个自分类下，"其他"分类节点。
	 */
	private HashMap cateCache=new HashMap();
	/**
	 * 保存 A8音乐大类。目前只有地区，风格，语言，用于保存音乐分类的缓存的 key值
	 */
	private HashMap musicCate=new HashMap();
	private static final String qitaCateName="其他";
	public A8MusicImport() throws BOException
	{
		super();
	}



	/**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(A8MusicImport.class);

	void init()
	{
		this.ftpDir =A8ParameterConfig.A8MusicDir;
		this.localDir=getPathWithSlash(A8ParameterConfig.BackupDir)+A8ParameterConfig.A8MusicDir;
		this.title="A8音乐数据导入";
		String date = PublicUtil.getDateString(new Date(), "yyyyMMdd");
		this.regex="A8_music_"+date+"\\.txt";

	}

	int insertDB(Object object)
	{
            GAudio audio = ( GAudio ) object;
            // 将新解析出来的全曲ID对应的记录从待删除记录列表中移除
            boolean result = dbList.remove(audio.getId());
            // 返回成功，说明对应的全曲记录在数据库中存在，需要更新
            if (result)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("全曲ID " + audio.getContentID() + " 对应的a8音乐存在于系统中，需要更新！");
                }
                return this.updateDBAudio(audio);
            }
            // 返回失败，说明对应的全曲记录在数据库中不存在，需要新增
            else
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("全曲ID " + audio.getContentID() + " 对应的a8音乐不在系统中，需要新增！");
                }
                return this.InsertContent(audio, successCount);
            }
  
	}

	Object transformVOByLineText(String line, int lineNumber)
	{
		//得到系统当前时间
         String currentTime = PublicUtil.getCurDateTime();
		 GAudio audio;
		
		 if (logger.isDebugEnabled())
		 {
			 logger.debug("getAudioByline is starting.....");
		 }
		 String[] arrColorContent = line.split("\\|");
		 int size = arrColorContent.length;
		 if (!(size == 12||size==16))
		 {
			 logger.error("第"+lineNumber+"行格式错误，只有12或者16个属性,此条内容无法导入！"+line );
			 return null;
		 }
		 audio = new GAudio();
		 //判断歌曲ID是否只包括字母和数字
		 if (!arrColorContent[0].matches("[0-9A-Za-z]*"))
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " 含有除了数字和字母外的字符，此条内容无法导入！");
			 return null;
		 }
		 if (StringTool.lengthOfHZ(arrColorContent[0])>25)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " 歌曲id超过了25个字符，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[1])>300)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，歌曲名称大于300个字符，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[2])>50)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，歌手名称大于50个字符，此条内容无法导入！");
			 return null;
		 }
		 if(!arrColorContent[3].matches("[0-9]{1,15}"))
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，歌曲播放时长只能为数字，且不能超过15位，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[4])>250)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，aac播放HTTP URL名称大于250个字符，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[5])>250)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，mp3播放HTTP URL名称大于250个字符，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[6])>250)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，歌词HTTP URL名称大于250个字符，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[7])>40)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，歌曲风格流派分类名称大于40个字符，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[8])>40)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，歌曲语言分类大于40个字符，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[9])>40)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，歌曲歌手地区分类大于40个字符，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[10])>200)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，歌曲归属专辑名称大于200个字符，此条内容无法导入！");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[11])>255)
		 {
			 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + " ，歌曲归属专辑图片url大于255个字符，此条内容无法导入！");
			 return null;
		 }
		 //
		 if(size==16)
		 {
			 if(StringTool.lengthOfHZ(arrColorContent[12])>250)
			 {
				 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + "  K歌aac播放HTTP URL大于255个字符，此条内容无法导入！");
				 return null;
			 }
			 if(StringTool.lengthOfHZ(arrColorContent[13])>250)
			 {
				 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + "  K歌mp3播放HTTP URL大于255个字符，此条内容无法导入！");
				 return null;
			 }
			 if(StringTool.lengthOfHZ(arrColorContent[14])>250)
			 {
				 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + "  歌词HTTP URL大于255个字符，此条内容无法导入！");
				 return null;
			 }
			 if(StringTool.lengthOfHZ(arrColorContent[15])>10)
			 {
				 logger.error("第"+lineNumber+"行格式错误，此条内容无法导入！歌曲ID " + arrColorContent[0] + "   K歌歌曲播放时长大于10个字符，此条内容无法导入！");
				 return null;
			 }
		 }
		int j=size;
		 while(--j >= 0)
		 {
			 if("".equals(arrColorContent[j].trim()))
			 {
				 logger.error("第"+lineNumber+"行中有空字段，此条内容无法导入！" +arrColorContent );
				 return null;
			 }
		 }
		 // 设置VO的字段值，注意ID做了特别处理，前面加了字符串"a8"
		 audio.setId("a8" + arrColorContent[0].trim());
		 audio.setName(arrColorContent[1].trim());
		 audio.setSinger(arrColorContent[2].trim());
		 audio.setAverageMark(Integer.parseInt(arrColorContent[3]) * 1000);
		 audio.setAacAuditionUrl(arrColorContent[4].trim());
		 audio.setMp3AuditionUrl(arrColorContent[5].trim());
		 audio.setLrcURL(arrColorContent[6].trim());
		 audio.setCateName(arrColorContent[7].trim());
		 audio.setAudioLanguage(arrColorContent[8].trim());
		 audio.setSingerZone(arrColorContent[9].trim());
		 audio.setSpecial(arrColorContent[10].trim());
		 audio.setSpecialURL(arrColorContent[11].trim());
		 
         //k歌信息
		 if(size==16)//只有K歌才有一下属性。
		 {
			 audio.setKAacAuditionUrl(arrColorContent[12]);
			 audio.setKMp3AuditionUrl(arrColorContent[13]);
			 audio.setKLrcURL(arrColorContent[14]);
			 audio.setAudioLength(Integer.parseInt(arrColorContent[15])*1000);
			 audio.setIsKAudio("1");
		 }else
		 {
			 audio.setIsKAudio("0");
		 }
		 

		 //设置内容内码为全曲ID
		 audio.setContentID(arrColorContent[0].trim());
		 //下面是新提供的数据中没有的字段
		 audio.setCreateDate(currentTime);
		 audio.setMarketDate(currentTime);
		 audio.setLupdDate(currentTime);
	     return audio;
	}
    /**
     * 逐一的插入全曲记录
     */
    private int InsertContent(GAudio audioVO,int sortID)
    {
        try
        {
            Category node = new Category();
            node.setId("702");
            node.setPath("{100}.{702}");

            node.addNode(audioVO);
            node.saveNode();
            addIntoCategory(RepositoryConstants.ROOT_CHILDCATEGORY_STYLE_ID,audioVO.getCateName(),audioVO.getId(),sortID) ;
            addIntoCategory(RepositoryConstants.ROOT_CHILDCATEGORY_LANGUAGE_ID,audioVO.getAudioLanguage(),audioVO.getId(),sortID) ;
            addIntoCategory(RepositoryConstants.ROOT_CHILDCATEGORY_ZONE_ID,audioVO.getSingerZone(),audioVO.getId(),sortID) ;
        }
        catch (Exception ex)
        {
            logger.error("添加A8音乐数据出错，ID为：" + audioVO.getId(), ex);
            return A8ParameterConfig.failure;
        }
        return A8ParameterConfig.success_add;
    }

    
    
    /**
     * 更新音乐信息
     * 
     * @param vo 音乐信息的vo
     * @return  
     */
    protected int updateDBAudio(GAudio vo)
    {
    	if (logger.isDebugEnabled())
    	{
    		logger.debug("updateDBAudio()");
    	}
    	try
		{
			GAudio audio = ( GAudio ) Repository.getInstance()
			.getNode(vo.getId(),
					GAudio.TYPE_AUDIO);
			audio.setName(vo.getName());
			audio.setSinger(vo.getSinger());
			audio.setAverageMark(vo.getAverageMark());
			audio.setAacAuditionUrl(vo.getAacAuditionUrl());
			audio.setMp3AuditionUrl(vo.getMp3AuditionUrl());
			audio.setLrcURL(vo.getLrcURL());
			audio.setCateName(vo.getCateName());
			audio.setAudioLanguage(vo.getAudioLanguage());
			audio.setSingerZone(vo.getSingerZone());
			audio.setSpecial(vo.getSpecial());
			audio.setSpecialURL(vo.getSpecialURL());
			
			
			audio.setIsKAudio(vo.getisKAudio());
			if(vo.getisKAudio().equals("1"))
			{
				audio.setKAacAuditionUrl(vo.getKAacAuditionUrl());
				audio.setKMp3AuditionUrl(vo.getKMp3AuditionUrl());
				audio.setKLrcURL(vo.getKLrcURL());
				audio.setAudioLength(vo.getAudioLength());
			}else
			{
				audio.setAudioLength(0);
			}
			audio.setLupdDate(vo.getLupdDate());
			
			audio.save();
		} catch (BOException e)
		{
			logger.error("更新音乐数据出错，id："+vo.getId(),e);
			return A8ParameterConfig.failure;
		}
    	return A8ParameterConfig.success_update;

    }
    /**
     * 将导入的全曲上架到对应的分类下
     * @param audio
     * @throws BOException
     */
    protected void addIntoCategory(String pCategoryID, String cateName, String contentID, int i) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addIntoCategory(" + contentID + ")");
        }
        HashMap cacheCate =(HashMap)musicCate.get(pCategoryID);
        if(cacheCate==null)
        {
        	musicCate.put(pCategoryID, new HashMap());
        	cacheCate=(HashMap)musicCate.get(pCategoryID);
        }
        
        Category childCategory=(Category)cacheCate.get(cateName);
        if(childCategory==null)
        {
        	// 得到终端音乐频道子频道－风格
            Category category = ( Category ) Repository.getInstance()
                                                       .getNode(pCategoryID,
                                                                RepositoryConstants.TYPE_CATEGORY);
            Searchor search = new Searchor();
            // 构造大类名称相同的搜索条件
            search.getParams().add(new SearchParam("name",
                                                   RepositoryConstants.OP_EQUAL,
                                                   cateName));
            List list = category.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
            //子类对象
             childCategory = null;
            if ( null == list || list.size() == 0)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("音乐分类不存在，放入\"其他\"分类中：");
                }
                	search = new Searchor();
                    // 构造大类名称相同的搜索条件
                    search.getParams().add(new SearchParam("name",
                                                           RepositoryConstants.OP_EQUAL,
                                                           qitaCateName));
                    list = category.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
                    if(list==null||list.size()==0)
                    {
                    	//目前该分类下还没有"其他"分类,创建之
                    	if(logger.isDebugEnabled())
                    	{
                    		logger.debug("分类id为"+pCategoryID+"的分类下没有"+qitaCateName+"分类，创建之");
                    	}
                    	childCategory = new Category();
                        childCategory.setName(qitaCateName);
                        childCategory.setDesc(qitaCateName);
                        childCategory.setRelation("O");
                        String childCategoryID = CategoryBO.getInstance()
                                                           .addCategory(pCategoryID,
                                                                        childCategory);
                        childCategory = ( Category ) Repository.getInstance()
                                                               .getNode(childCategoryID,
                                                                        RepositoryConstants.TYPE_CATEGORY);
                        
                    }else
                    {
                    	childCategory=(Category)list.get(0);
                    }
                    cateCache.put(qitaCateName, childCategory);
                }else
                {
                	childCategory = ( Category ) list.get(0);
                	cateCache.put(cateName, childCategory);
                }
                
            }
      

        
       
        ReferenceNode ref = new ReferenceNode();
        ref.setRefNodeID(contentID);
        ref.setSortID(i);
        ref.setCategoryID(childCategory.getCategoryID());
        ref.setGoodsID(PublicUtil.rPad( childCategory.getCategoryID()+ "|"
                                       + contentID + "|", 39, "0"));
        ref.setLoadDate(DateUtil.formatDate(new Date(),
                                            "yyyy-MM-dd HH:mm:ss"));
        childCategory.addNode(ref);
        childCategory.saveNode();
        if (logger.isDebugEnabled())
        {
            logger.debug("addIntoCategory success![" + contentID + "].");
        }
    }

	List getDBDate() throws DAOException
	{
		return A8ImportDAO.getInstance().getAllAudioID();
	}
	
    /**
     * 删除全量A8音乐文件中不存在而本地数据库的音乐数据，删除前需要进行下架操作
     * 
     * @param deleteList
     * @return 成功删除的媒体个数
     */
	void deleteFromDB(List list)
	{
		if (logger.isDebugEnabled())
        {
            logger.debug("deleteDBAudio()");
        }
		for(int i=0;i<list.size();i++)
		{
			String id=(String)list.get(i);
	        // 待删除的彩铃节点
	        try
			{
				GAudio audioVO = ( GAudio ) Repository.getInstance()
				.getNode(id,
						GAudio.TYPE_AUDIO);

				Searchor searchor = new Searchor();
				searchor.getParams()
				.add(new SearchParam("refNodeID",
						RepositoryConstants.OP_EQUAL,
						id));
				searchor.setIsRecursive(true);
				Category rootCagetory = ( Category ) Repository.getInstance()
				.getNode(RepositoryConstants.ROOT_CATEGORY_ID,
						RepositoryConstants.TYPE_CATEGORY);
				List refList = rootCagetory.searchNodes(RepositoryConstants.TYPE_REFERENCE,
						searchor,
						null);
				//删除商品
				for (int j = 0; j < refList.size(); j++)
				{
					ReferenceNode ref = ( ReferenceNode ) refList.get(j);
					Category pCategory = ( Category ) Repository.getInstance()
					.getNode(ref.getParentID(),
							RepositoryConstants.TYPE_CATEGORY);
					pCategory.delNode(ref);
					pCategory.saveNode();
				}
				// 还要删除彩铃数据
				Category rootSaveNode = ( Category ) Repository.getInstance()
				.getNode(RepositoryConstants.ROOT_CONTENT_ID,
						RepositoryConstants.TYPE_CATEGORY);
				rootSaveNode.delNode(audioVO);
				rootSaveNode.saveNode();
			} catch (Exception e)
			{
				logger.error("删除音乐数据出错，id："+id,e);
				failureCount++;
			}
			successDelete++;
		}
       
	}

	Object getVOKey(Object object)
	{
		return ((GAudio)object).getId();
	}

	/*int insertDB(Object vo)
	{
		// TODO Auto-generated method stub
		GAudio audio = ( GAudio )vo;
        // 将新解析出来的全曲ID对应的记录从待删除记录列表中移除
        boolean result = dbList.remove(audio.getId());
        // 返回成功，说明对应的全曲记录在数据库中存在，需要更新
        if (result)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("全曲ID " + audio.getContentID() + " 对应的a8音乐存在于系统中，需要更新！");
            }
            return  this.updateDBAudio(audio);
            
        }
        // 返回失败，说明对应的全曲记录在数据库中不存在，需要新增
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("全曲ID " + audio.getContentID() + " 对应的a8音乐不在系统中，需要新增！");
            }
            return this.deleteDBAudio(audio);
        }
	}*/

}
