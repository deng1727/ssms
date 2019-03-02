package com.aspire.ponaadmin.web.dissertation;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 * @author yushiming
 *          2011-2-24
 */
public class DissAddForm extends ActionForm {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2408578400351363082L;
	private String dissId;
	private String dissName;
	private String descr;
	/**
	 * 关联门户，M\W\P1\P2\P3
	 */
	private String status;
	private String[] relation=new String[5];
	//专辑url地址
	private String urlM;
	private String urlW;
	private String p1DissUrl;
	private String p2DissUrl;
	private String p3DissUrl;
	//要上传的文件列表
	private List files;
	//要关联的货架ID
	private String categoryIdM;
	private String categoryIdW;
	private String p1CategoryId;
	private String p2CategoryId;
	private String p3CategoryId;
	//要关联的货架名称
	private String categoryNameM;
	private String categoryNameW;
	private String p1CategoryName;
	private String p2CategoryName;
	private String p3CategoryName;
	//有效时间
	private String startDate;
	private String endDate;
	//标签
	private String tag;
	//类型(Game/Soft/Theme)可多选
	private String[] types=new String[3] ;
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public DissAddForm(){
		files=new ArrayList();
		files.add(new UploadFile());
	}
	public UploadFile getUploadFile(int index){    
        int size = files.size();    
        if(index > size - 1){    
            for(int i = 0; i < index - size + 1; i++){    
                files.add(new UploadFile());    
            }    
        }    
        return (UploadFile)files.get(index);    
    }
	public void setM(String picUrl,String urlM,String categoryIdM,String categoryNameM ){
		this.urlM=urlM;
		this.categoryNameM=categoryNameM;
		this.categoryIdM=categoryIdM;
		UploadFile uf=null;
		uf=(UploadFile)this.getFiles().get(0);
		uf.setPicUrl(picUrl);
	}
	public void setW(String picUrl,String urlW,String categoryIdW,String categoryNameW ){
		this.urlW=urlW;
		this.categoryNameW=categoryNameW;
		this.categoryIdW=categoryIdW;
		UploadFile uf=null;
		uf=(UploadFile)this.getFiles().get(1);
		uf.setPicUrl(picUrl);
	}
	public void setP1(String picUrl,String p1DissUrl,String p1CategoryId,String p1CategoryName ){
		this.p1DissUrl=p1DissUrl;
		this.p1CategoryName=p1CategoryName;
		this.p1CategoryId=p1CategoryId;
		UploadFile uf=null;
		uf=(UploadFile)this.getFiles().get(2);
		uf.setPicUrl(picUrl);
	}
	public void setP2(String picUrl,String p2DissUrl,String p2CategoryId,String p2CategoryName ){
		this.p2DissUrl=p2DissUrl;
		this.p2CategoryName=p2CategoryName;
		this.p2CategoryId=p2CategoryId;
		UploadFile uf=null;
		uf=(UploadFile)this.getFiles().get(3);
		uf.setPicUrl(picUrl);
	}
	public void setP3(String picUrl,String p3DissUrl,String p3CategoryId,String p3CategoryName ){
		this.p3DissUrl=p3DissUrl;
		this.p3CategoryName=p3CategoryName;
		this.p3CategoryId=p3CategoryId;
		UploadFile uf=null;
		uf=(UploadFile)this.getFiles().get(4);
		uf.setPicUrl(picUrl);
	}
	public DissAddForm.Data getM(){
		return new Data(this.getUploadFile(0),this.getUrlM(),this.getCategoryIdM(),this.getCategoryNameM());
	}
	public DissAddForm.Data getW(){
		return new Data(this.getUploadFile(1),this.getUrlW(),this.getCategoryIdW(),this.getCategoryNameW());
	}
	public DissAddForm.Data getP1(){
		return new Data(this.getUploadFile(2),this.getP1DissUrl(),this.getP1CategoryId(),this.getP1CategoryName());
	}
	public DissAddForm.Data getP2(){
		return new Data(this.getUploadFile(3),this.getP2DissUrl(),this.getP2CategoryId(),this.getP2CategoryName());
	}
	public DissAddForm.Data getP3(){
		return new Data(this.getUploadFile(4),this.getP3DissUrl(),this.getP3CategoryId(),this.getP3CategoryName());
	}
	
	public class Data{
		public Data(UploadFile file,String dissUrl,String categoryId,String categoryName){
			this.file=file;
			this.categoryId=categoryId;
			this.categoryName=categoryName;
			this.dissUrl=dissUrl;
		}
		private UploadFile file;
		private String dissUrl;
		private String categoryId;
		private String categoryName;
		public String getCategoryId() {
			return categoryId;
		}
		private void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}
		public String getCategoryName() {
			return categoryName;
		}
		private void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		public String getDissUrl() {
			return dissUrl;
		}
		private void setDissUrl(String dissUrl) {
			this.dissUrl = dissUrl;
		}
		public UploadFile getFile() {
			return file;
		}
		private void setFile(UploadFile file) {
			this.file = file;
		}

	}

	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getDissName() {
		return dissName;
	}
	public void setDissName(String dissName) {
		this.dissName = dissName;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List getFiles() {
		return files;
	}
	public void setFiles(List files) {
		this.files = files;
	}
	public String getP1CategoryId() {
		return p1CategoryId;
	}
	public void setP1CategoryId(String categoryId) {
		p1CategoryId = categoryId;
	}
	public String getP1CategoryName() {
		return p1CategoryName;
	}
	public void setP1CategoryName(String categoryName) {
		p1CategoryName = categoryName;
	}
	public String getP1DissUrl() {
		return p1DissUrl;
	}
	public void setP1DissUrl(String dissUrl) {
		p1DissUrl = dissUrl;
	}
	public String getP2CategoryId() {
		return p2CategoryId;
	}
	public void setP2CategoryId(String categoryId) {
		p2CategoryId = categoryId;
	}
	public String getP2CategoryName() {
		return p2CategoryName;
	}
	public void setP2CategoryName(String categoryName) {
		p2CategoryName = categoryName;
	}
	public String getP2DissUrl() {
		return p2DissUrl;
	}
	public void setP2DissUrl(String dissUrl) {
		p2DissUrl = dissUrl;
	}
	public String getP3CategoryId() {
		return p3CategoryId;
	}
	public void setP3CategoryId(String categoryId) {
		p3CategoryId = categoryId;
	}
	public String getP3CategoryName() {
		return p3CategoryName;
	}
	public void setP3CategoryName(String categoryName) {
		p3CategoryName = categoryName;
	}
	public String getP3DissUrl() {
		return p3DissUrl;
	}
	public void setP3DissUrl(String dissUrl) {
		p3DissUrl = dissUrl;
	}
	public String[] getRelation() {
		return relation;
	}
	public void setRelation(String[] relation) {
		this.relation = relation;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public String getUrlM() {
		return urlM;
	}
	public void setUrlM(String urlM) {
		this.urlM = urlM;
	}
	public String getUrlW() {
		return urlW;
	}
	public void setUrlW(String urlW) {
		this.urlW = urlW;
	}
	public String getCategoryIdM() {
		return categoryIdM;
	}
	public void setCategoryIdM(String categoryIdM) {
		this.categoryIdM = categoryIdM;
	}
	public String getCategoryIdW() {
		return categoryIdW;
	}
	public void setCategoryIdW(String categoryIdW) {
		this.categoryIdW = categoryIdW;
	}
	public String getCategoryNameM() {
		return categoryNameM;
	}
	public void setCategoryNameM(String categoryNameM) {
		this.categoryNameM = categoryNameM;
	}
	public String getCategoryNameW() {
		return categoryNameW;
	}
	public void setCategoryNameW(String categoryNameW) {
		this.categoryNameW = categoryNameW;
	}
	public String getDissId() {
		return dissId;
	}
	public void setDissId(String dissId) {
		this.dissId = dissId;
	}


	
}
