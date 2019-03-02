package com.aspire.dotcard.basebook.vo;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.StringTool;

/**
 * 图书信息VO
 * @author x_zhailiqing
 *
 */
public class RBookVO {
	
	protected static JLogger logger = LoggerFactory.getLogger(RBookVO.class);
	
	/**
	 * 图书id
	 */
	private String bookId;
	
	/**
	 * 图书名称
	 */
	private String bookName;
	
	/**
	 * 图书关键字
	 */
	private String keyWord;
	
	/**
	 * 长推荐字
	 */
	private String longRecommend;
	
	/**
	 * 短推荐字
	 */
	private String ShortRecommend;
	
	/**
	 * 简介
	 */
	private String description;
	
	/**
	 * 作者ID
	 */
	private String authorId;
	
	/**
	 * 作者名称
	 */
	private String authorName;
	
	/**
	 * 分类ID 35个基础类 对应表中的
	 */
	private String typeId;
	
	private String subTypeId;
	
	public String getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(String subTypeId) {
		this.subTypeId = subTypeId;
	}

	/**
	 * 入库时间
	 */
	private String inTime;
	
	/**
	 * 图书WAP页面入口地址
	 */
	private String bookUrl;
	
	/**
	 * 费用类型0免费；1按本计费；2、按章计费；3、按字计费
	 */
	private int chargeType;
	
	/**
	 * 费率，单位：分当chargeType = 0时，fee必须为0，入库时需要用厘为单位
	 */
	private int fee;
	
	/**
	 * 是否完本
	 */
	private String isFinish;	
	
	/**
	 * 1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现
	 */
	private int changeType;
	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
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

	public String getBookUrl() {
		return bookUrl;
	}

	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
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
	 * 赋值
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data){
		if(data.length!=14){
			return false;
		}

		int changeType;
		int charge ;
		int f ;		
		int isFinish ;
		try {
			changeType = Integer.parseInt(data[13]);
			charge = Integer.parseInt(data[10].trim());
			f = Integer.parseInt(data[11].trim())*10;
			isFinish = Integer.parseInt(data[12].trim());
		} catch (NumberFormatException e) {
			return false;
		}
		if(changeType!=1&&changeType!=2&&changeType!=3){
			logger.error("changeType字段错误，必须是1或2或3");
			return false;
		}
		if(charge!=0&&charge!=1&&charge!=2&&charge!=3){
			logger.error("图书字段chargeType "+data[0]+"计费类型不正确,必须是0或1或2或3");
			return false;
		}
		if(isFinish!=0&&isFinish!=1){
			logger.error("图书字段isFinish "+data[0]+"完本状态不正确");
			return false;
		}
		if(charge==0&&f!=0){
			return false;
		}
		if(!checkFieldLength(data[0],16,true)){
			logger.error("bookId验证错误，该字段是必填字段，且不超过16个字符");
			return false;			
		}
		this.bookId = data[0].trim();
		if(!checkFieldLength(data[1],100,true)){
			logger.error("bookName验证错误，该字段是必填字段，且不超过100个字符");
			return false;			
		}
		this.bookName = data[1].trim();
	
		if(!checkFieldLength(data[2],1000,false)){
			logger.error("keyWord验证错误，该字段不能超过1000个字符");
			return false;		
		}		
		this.keyWord = data[2];

		if(!checkFieldLength(data[3],100,true)){
			logger.error("longRecommend验证错误，该字段是必填字段，且不超过100个字符");
			return false;			
		}		
		this.longRecommend = data[3].trim();
	
		if(!checkFieldLength(data[4],100,false)){
			logger.error("ShortRecommend验证错误，该字段不超过100个字符");
			return false;		
		}		
		this.ShortRecommend = data[4];
	
		if(!checkFieldLength(data[5],2048,false)){
			logger.error("desc验证错误，该字段不超过2048个字符");
			return false;		
		}		
		this.description = data[5];
	
		if(!checkFieldLength(data[6],25,true)){
			logger.error("authorId验证错误，该字段是必填字段，且不超过25个字符");
			return false;			
		}		
		this.authorId = data[6].trim();
	
		if(!checkFieldLength(data[7],20,true)){
			logger.error("Categoryid验证错误，该字段是必填字段，且不超过20个字符");
			return false;			
		}		
		this.typeId = data[7].trim();
		this.subTypeId = data[7].trim();

		if(!checkFieldLength(data[8],14,true)){
			logger.error("inTime验证错误，该字段是必填字段，且不超过14个字符");
			return false;		
		}		
		this.inTime = data[8].trim();
	
		if(!checkFieldLength(data[9],255,true)){
			logger.error("bookUrl验证错误，该字段是必填字段，且不超过255个字符");
			return false;		
		}		
		this.bookUrl = data[9].trim();
		
		this.chargeType = charge;
		
		this.fee = f;
		if(StringTool.lengthOfHZ(""+fee)>10){
			logger.error("fee验证错误，该字段是必填字段，且不超过10个字符");
			return false;
		}		
		this.isFinish = data[12].trim();
		
		this.changeType = changeType;
		
		return true;
		
	}	
	
	/**
	 * 判断该字符是否超出maxLength的长度。
	 * @param field 要验证的字段内容
	 * @param maxLength 允许的最大长度
	 * @param must 是否是必填字段，如果为true，需要验证该字段是否为空（""）
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
}
