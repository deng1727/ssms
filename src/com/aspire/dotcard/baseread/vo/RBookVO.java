package com.aspire.dotcard.baseread.vo;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.config.BaseReadConfig;
import com.aspire.ponaadmin.web.util.StringTool;

/**
 * ͼ����ϢVO
 * @author x_zhailiqing
 *
 */
public class RBookVO {
	
	protected static JLogger logger = LoggerFactory.getLogger(RBookVO.class);
	
	/**
	 * ͼ��id
	 */
	private String bookId;
	
	/**
	 * ͼ������
	 */
	private String bookName;
	
	/**
	 * ͼ��ؼ���
	 */
	private String keyWord;
	
	/**
	 * ���Ƽ���
	 */
	private String longRecommend;
	
	/**
	 * ���Ƽ���
	 */
	private String ShortRecommend;
	
	/**
	 * ���
	 */
	private String description;
	
	/**
	 * ����ID
	 */
	private String authorId;
	
	/**
	 * ����ID
	 */
	private String typeId;
	
	/**
	 * ���ʱ��
	 */
	private String inTime;
	
	/**
	 * ��������0��ѣ�1�����Ʒѣ�2�����¼Ʒѣ�3�����ּƷ�
	 */
	private int chargeType;
	
    /**
     * ���ʣ���λ���ֵ�chargeType = 0ʱ��fee����Ϊ0�����ʱ��Ҫ����Ϊ��λ
     */
    private int fee;
	
	/**
	 * �Ƿ��걾
	 */
	private String isFinish;	
    
    /**
     * ����
     */
    private int size;
    
    /**
     * �½���
     */
    private int chapterCount;
    
    /**
     * ͼ��ͼƬ
     */
    private String bookPic;
    /**
     * ����½���,add by 20140122
     */
    private int freeChapterCount=0;
    
	
	public int getFreeChapterCount() {
		return freeChapterCount;
	}

	public void setFreeChapterCount(int freeChapterCount) {
		this.freeChapterCount = freeChapterCount;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getLongRecommend() {
		return longRecommend;
	}

	public void setLongRecommend(String longRecommend) {
		this.longRecommend = longRecommend;
	}

	public String getShortRecommend() {
		return ShortRecommend;
	}

	public void setShortRecommend(String shortRecommend) {
		ShortRecommend = shortRecommend;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String categoryId) {
		this.typeId = categoryId;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public int getChargeType() {
		return chargeType;
	}

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	
	/**
	 * ��ֵ
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data){
	
		if(data.length==14 || data.length==15){
		    
    		String picUrl = BaseReadConfig.get("picUrl");
    		
    		// ������������
    		if(null==picUrl || "".equals(picUrl))
    		{
    			//picUrl = "http://wap.cmread.com/iread/img?bookId=";
    			picUrl = "http://rs.base.mmarket.com/read/logo/#bookid#.jpg";
    		}
    		
    		int charge ;
    		int f ;		
    		int isFinish ;
            int fileSize;
            int chapter;
            int freeChapter = 0;
    		try {
    			charge = Integer.parseInt(data[9].trim());
    			f = Integer.parseInt(data[10].trim())*10;
    			isFinish = Integer.parseInt(data[11].trim());
                fileSize = Integer.parseInt(data[12].trim());
                chapter = Integer.parseInt(data[13].trim());
                if(data.length>14){
                    
                    freeChapter = Integer.parseInt(data[14].trim());//����½���,add by 20140122
                    logger.debug("��������½���freeChapterCount=="+freeChapter);
                }
    		} catch (NumberFormatException e) {
    			return false;
    		}
    		
    		if(charge!=0&&charge!=1&&charge!=2&&charge!=3){
    			logger.error("ͼ���ֶ�chargeType "+data[0]+"�Ʒ����Ͳ���ȷ,������0��1��2��3");
    			return false;
    		}
    		if(isFinish!=0&&isFinish!=1){
    			logger.error("ͼ���ֶ�isFinish "+data[0]+"�걾״̬����ȷ");
    			return false;
    		}
    //		if(charge==0&&f!=0){
    //			return false;
    //		}
    		if(!checkFieldLength(data[0],20,true)){
    			logger.error("bookId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����20���ַ�");
    			return false;			
    		}
    		this.bookId = data[0].trim();
    		if(!checkFieldLength(data[1],100,true)){
    			logger.error("bookName��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����100���ַ�");
    			return false;			
    		}
    		this.bookName = data[1].trim();
    	
    		if(!checkFieldLength(data[2],1000,false)){
    			logger.error("keyWord��֤���󣬸��ֶβ��ܳ���1000���ַ�");
    			return false;		
    		}		
    		this.keyWord = data[2];
    
    		if(!checkFieldLength(data[3],100,false)){
    			logger.error("longRecommend��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����100���ַ�");
    			return false;			
    		}		
    		this.longRecommend = data[3].trim();
    	
    		if(!checkFieldLength(data[4],100,false)){
    			logger.error("ShortRecommend��֤���󣬸��ֶβ�����100���ַ�");
    			return false;		
    		}		
    		this.ShortRecommend = data[4];
    	
    		if(!checkFieldLength(data[5],2048,false)){
    			logger.error("desc��֤���󣬸��ֶβ�����2048���ַ�");
    			return false;		
    		}		
    		this.description = data[5];
    	
    		if(!checkFieldLength(data[6],25,true)){
    			logger.error("authorId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����25���ַ�");
    			return false;			
    		}		
    		this.authorId = data[6].trim();
    	
    		if(!checkFieldLength(data[7],20,true)){
    			logger.error("Categoryid��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����20���ַ�");
    			return false;			
    		}		
    		this.typeId = data[7].trim();
    
    		if(!checkFieldLength(data[8],14,true)){
    			logger.error("inTime��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����14���ַ�");
    			return false;		
    		}		
    		this.inTime = data[8].trim();
    	
    		this.chargeType = charge;
    		this.fee = f;
            
    		if(StringTool.lengthOfHZ(""+fee)>12){
    			logger.error("fee��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����12���ַ�");
    			return false;
    		}		
    		this.isFinish = data[11].trim();
            this.size = fileSize;
            this.chapterCount = chapter;
            this.freeChapterCount = freeChapter;
    		this.bookPic = picUrl.replace("#bookid#", this.bookId)  ;
    		
		}else
        {
            logger.error("===============�ļ�ÿ���ֶγ��Ȳ���(14����15)====================");
            
            return false;
        }
		return true;
		
	}	
	
	/**
	 * �жϸ��ַ��Ƿ񳬳�maxLength�ĳ��ȡ�
	 * @param field Ҫ��֤���ֶ�����
	 * @param maxLength �������󳤶�
	 * @param must �Ƿ��Ǳ����ֶΣ����Ϊtrue����Ҫ��֤���ֶ��Ƿ�Ϊ�գ�""��
	 * @return
	 */
	protected  boolean checkFieldLength(String field,int maxLength,boolean must)
	{
		if(field==null)
		{
			return false;
		}
		if(StringTool.lengthOfHZ(field)>maxLength)
		{
			return false;
		}
		if(must)
		{
		  if(field.equals(""))
		  {	
			return false;
		  }
		}
		return true;
		
	}

    public int getChapterCount()
    {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount)
    {
        this.chapterCount = chapterCount;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

	public String getBookPic()
	{
		return bookPic;
	}

	public void setBookPic(String bookPic)
	{
		this.bookPic = bookPic;
	}	
}
