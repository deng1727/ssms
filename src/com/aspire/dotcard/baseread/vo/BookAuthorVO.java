package com.aspire.dotcard.baseread.vo;

public class BookAuthorVO {

	/**
	 * ����ID
	 */
	private String authorId;
	
	/**
	 * ��������
	 */
	private String authorName;
	
	/**
	 * ���
	 */
	private String description;
    
    /**
     * ������������ĸ
     */
    private String nameLetter;
    
    /**
     * �Ƿ�ԭ������
     */
    private String isOriginal;
    
    /**
     * �Ƿ����ר��
     */
    private String isPublish;
    
    /**
     * ͼƬ·��
     */
    private String authorPic;
    
    /**
     * �Ƽ��ֶα�ʶ
     */
    private String recommendManual;
    
    private String showName;
    
    private String showDesc;

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
		this.setShowName(authorName);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		this.setShowDesc(description);
	}
	
	/**
	 * ��ֵ
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data){
		if(data.length!=7){
			return false;
		}
		if(null==data[0]||"".equals(data[0].trim())
				||null==data[1]||"".equals(data[1].trim())){
			return false;
		}

		this.authorId = data[0].trim();
		this.authorName = data[1].trim();
		this.description = data[2];
        this.nameLetter = data[3];
        this.isPublish = data[4];
        this.isOriginal = data[5];
        this.authorPic = data[6];

		return true;
		
	}

    public String getAuthorPic()
    {
        return authorPic;
    }

    public void setAuthorPic(String authorPic)
    {
        this.authorPic = authorPic;
    }

    public String getIsOriginal()
    {
        return isOriginal;
    }

    public void setIsOriginal(String isOriginal)
    {
        this.isOriginal = isOriginal;
    }

    public String getIsPublish()
    {
        return isPublish;
    }

    public void setIsPublish(String isPublish)
    {
        this.isPublish = isPublish;
    }

    public String getNameLetter()
    {
        return nameLetter;
    }

    public void setNameLetter(String nameLetter)
    {
        this.nameLetter = nameLetter;
    }

	public String getRecommendManual()
	{
		return recommendManual;
	}

	public void setRecommendManual(String recommendManual)
	{
		this.recommendManual = recommendManual;
	}

	public String getShowName()
	{
		return showName;
	}

	public void setShowName(String showName)
	{
		if(null != showName && showName.length() > 10)
		{
			this.showName = showName.substring(0, 10) + "...";
		}
		else
		{
			this.showName = showName;
		}
	}

	public String getShowDesc()
	{
		return showDesc;
	}

	public void setShowDesc(String showDesc)
	{
		if(null != showDesc && showDesc.length() > 10)
		{
			this.showDesc = showDesc.substring(0, 10) + "...";
		}
		else
		{
			this.showDesc = showDesc;
		}
	}
}
