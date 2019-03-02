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
	 * ���ڻ����ն����ָ����Է����£�"����"����ڵ㡣
	 */
	private HashMap cateCache=new HashMap();
	/**
	 * ���� A8���ִ��ࡣĿǰֻ�е�����������ԣ����ڱ������ַ���Ļ���� keyֵ
	 */
	private HashMap musicCate=new HashMap();
	private static final String qitaCateName="����";
	public A8MusicImport() throws BOException
	{
		super();
	}



	/**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(A8MusicImport.class);

	void init()
	{
		this.ftpDir =A8ParameterConfig.A8MusicDir;
		this.localDir=getPathWithSlash(A8ParameterConfig.BackupDir)+A8ParameterConfig.A8MusicDir;
		this.title="A8�������ݵ���";
		String date = PublicUtil.getDateString(new Date(), "yyyyMMdd");
		this.regex="A8_music_"+date+"\\.txt";

	}

	int insertDB(Object object)
	{
            GAudio audio = ( GAudio ) object;
            // ���½���������ȫ��ID��Ӧ�ļ�¼�Ӵ�ɾ����¼�б����Ƴ�
            boolean result = dbList.remove(audio.getId());
            // ���سɹ���˵����Ӧ��ȫ����¼�����ݿ��д��ڣ���Ҫ����
            if (result)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("ȫ��ID " + audio.getContentID() + " ��Ӧ��a8���ִ�����ϵͳ�У���Ҫ���£�");
                }
                return this.updateDBAudio(audio);
            }
            // ����ʧ�ܣ�˵����Ӧ��ȫ����¼�����ݿ��в����ڣ���Ҫ����
            else
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("ȫ��ID " + audio.getContentID() + " ��Ӧ��a8���ֲ���ϵͳ�У���Ҫ������");
                }
                return this.InsertContent(audio, successCount);
            }
  
	}

	Object transformVOByLineText(String line, int lineNumber)
	{
		//�õ�ϵͳ��ǰʱ��
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
			 logger.error("��"+lineNumber+"�и�ʽ����ֻ��12����16������,���������޷����룡"+line );
			 return null;
		 }
		 audio = new GAudio();
		 //�жϸ���ID�Ƿ�ֻ������ĸ������
		 if (!arrColorContent[0].matches("[0-9A-Za-z]*"))
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ���г������ֺ���ĸ����ַ������������޷����룡");
			 return null;
		 }
		 if (StringTool.lengthOfHZ(arrColorContent[0])>25)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ����id������25���ַ������������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[1])>300)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ���������ƴ���300���ַ������������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[2])>50)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ���������ƴ���50���ַ������������޷����룡");
			 return null;
		 }
		 if(!arrColorContent[3].matches("[0-9]{1,15}"))
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ����������ʱ��ֻ��Ϊ���֣��Ҳ��ܳ���15λ�����������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[4])>250)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ��aac����HTTP URL���ƴ���250���ַ������������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[5])>250)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ��mp3����HTTP URL���ƴ���250���ַ������������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[6])>250)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " �����HTTP URL���ƴ���250���ַ������������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[7])>40)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ������������ɷ������ƴ���40���ַ������������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[8])>40)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ���������Է������40���ַ������������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[9])>40)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ���������ֵ����������40���ַ������������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[10])>200)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ����������ר�����ƴ���200���ַ������������޷����룡");
			 return null;
		 }
		 if(StringTool.lengthOfHZ(arrColorContent[11])>255)
		 {
			 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + " ����������ר��ͼƬurl����255���ַ������������޷����룡");
			 return null;
		 }
		 //
		 if(size==16)
		 {
			 if(StringTool.lengthOfHZ(arrColorContent[12])>250)
			 {
				 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + "  K��aac����HTTP URL����255���ַ������������޷����룡");
				 return null;
			 }
			 if(StringTool.lengthOfHZ(arrColorContent[13])>250)
			 {
				 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + "  K��mp3����HTTP URL����255���ַ������������޷����룡");
				 return null;
			 }
			 if(StringTool.lengthOfHZ(arrColorContent[14])>250)
			 {
				 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + "  ���HTTP URL����255���ַ������������޷����룡");
				 return null;
			 }
			 if(StringTool.lengthOfHZ(arrColorContent[15])>10)
			 {
				 logger.error("��"+lineNumber+"�и�ʽ���󣬴��������޷����룡����ID " + arrColorContent[0] + "   K���������ʱ������10���ַ������������޷����룡");
				 return null;
			 }
		 }
		int j=size;
		 while(--j >= 0)
		 {
			 if("".equals(arrColorContent[j].trim()))
			 {
				 logger.error("��"+lineNumber+"�����п��ֶΣ����������޷����룡" +arrColorContent );
				 return null;
			 }
		 }
		 // ����VO���ֶ�ֵ��ע��ID�����ر���ǰ������ַ���"a8"
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
		 
         //k����Ϣ
		 if(size==16)//ֻ��K�����һ�����ԡ�
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
		 

		 //������������Ϊȫ��ID
		 audio.setContentID(arrColorContent[0].trim());
		 //���������ṩ��������û�е��ֶ�
		 audio.setCreateDate(currentTime);
		 audio.setMarketDate(currentTime);
		 audio.setLupdDate(currentTime);
	     return audio;
	}
    /**
     * ��һ�Ĳ���ȫ����¼
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
            logger.error("���A8�������ݳ���IDΪ��" + audioVO.getId(), ex);
            return A8ParameterConfig.failure;
        }
        return A8ParameterConfig.success_add;
    }

    
    
    /**
     * ����������Ϣ
     * 
     * @param vo ������Ϣ��vo
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
			logger.error("�����������ݳ���id��"+vo.getId(),e);
			return A8ParameterConfig.failure;
		}
    	return A8ParameterConfig.success_update;

    }
    /**
     * �������ȫ���ϼܵ���Ӧ�ķ�����
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
        	// �õ��ն�����Ƶ����Ƶ�������
            Category category = ( Category ) Repository.getInstance()
                                                       .getNode(pCategoryID,
                                                                RepositoryConstants.TYPE_CATEGORY);
            Searchor search = new Searchor();
            // �������������ͬ����������
            search.getParams().add(new SearchParam("name",
                                                   RepositoryConstants.OP_EQUAL,
                                                   cateName));
            List list = category.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
            //�������
             childCategory = null;
            if ( null == list || list.size() == 0)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("���ַ��಻���ڣ�����\"����\"�����У�");
                }
                	search = new Searchor();
                    // �������������ͬ����������
                    search.getParams().add(new SearchParam("name",
                                                           RepositoryConstants.OP_EQUAL,
                                                           qitaCateName));
                    list = category.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
                    if(list==null||list.size()==0)
                    {
                    	//Ŀǰ�÷����»�û��"����"����,����֮
                    	if(logger.isDebugEnabled())
                    	{
                    		logger.debug("����idΪ"+pCategoryID+"�ķ�����û��"+qitaCateName+"���࣬����֮");
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
     * ɾ��ȫ��A8�����ļ��в����ڶ��������ݿ���������ݣ�ɾ��ǰ��Ҫ�����¼ܲ���
     * 
     * @param deleteList
     * @return �ɹ�ɾ����ý�����
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
	        // ��ɾ���Ĳ���ڵ�
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
				//ɾ����Ʒ
				for (int j = 0; j < refList.size(); j++)
				{
					ReferenceNode ref = ( ReferenceNode ) refList.get(j);
					Category pCategory = ( Category ) Repository.getInstance()
					.getNode(ref.getParentID(),
							RepositoryConstants.TYPE_CATEGORY);
					pCategory.delNode(ref);
					pCategory.saveNode();
				}
				// ��Ҫɾ����������
				Category rootSaveNode = ( Category ) Repository.getInstance()
				.getNode(RepositoryConstants.ROOT_CONTENT_ID,
						RepositoryConstants.TYPE_CATEGORY);
				rootSaveNode.delNode(audioVO);
				rootSaveNode.saveNode();
			} catch (Exception e)
			{
				logger.error("ɾ���������ݳ���id��"+id,e);
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
        // ���½���������ȫ��ID��Ӧ�ļ�¼�Ӵ�ɾ����¼�б����Ƴ�
        boolean result = dbList.remove(audio.getId());
        // ���سɹ���˵����Ӧ��ȫ����¼�����ݿ��д��ڣ���Ҫ����
        if (result)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("ȫ��ID " + audio.getContentID() + " ��Ӧ��a8���ִ�����ϵͳ�У���Ҫ���£�");
            }
            return  this.updateDBAudio(audio);
            
        }
        // ����ʧ�ܣ�˵����Ӧ��ȫ����¼�����ݿ��в����ڣ���Ҫ����
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("ȫ��ID " + audio.getContentID() + " ��Ӧ��a8���ֲ���ϵͳ�У���Ҫ������");
            }
            return this.deleteDBAudio(audio);
        }
	}*/

}
