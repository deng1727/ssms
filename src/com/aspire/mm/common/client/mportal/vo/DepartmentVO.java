package com.aspire.mm.common.client.mportal.vo;

import java.util.List;

/**
 * Department对象.
 * @author mm autoCoder1.0
 *
 */
public class DepartmentVO  {
    /**
     * 根组织.
     */	
    public static String ROOT_PARENT_ID = "root";	
	
     /**
     * 组织ID.
     */
    private String departmentId;
        /**
     * 组织名称.
     */
    private String departmentName;
        /**
     * 组织描述.
     */
    private String departmentDesc;
        /**
     * 父级组纷呈ID.
     */
    private String parentId;
        /**
     * 邮件.
     */
    private String email;
        /**
     * 地址.
     */
    private String address;
        /**
     * 组织创建者.
     */
    private String createUser;
    		    /**
     * 创建时间.
     */
    private String createDate;
    		    /**
     * 最后更新时间.
     */
    private String lastUpdateDate;
    
    /**
     * 最后更新时间.
     */
    private List childDepartments;   
    


        public String getDepartmentId() {
        return this.departmentId;
    }	
  
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
        public String getDepartmentName() {
        return this.departmentName;
    }	
  
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
        public String getDepartmentDesc() {
        return this.departmentDesc;
    }	
  
    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }
        public String getParentId() {
        return this.parentId;
    }	
  
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
        public String getEmail() {
        return this.email;
    }	
  
    public void setEmail(String email) {
        this.email = email;
    }
        public String getAddress() {
        return this.address;
    }	
  
    public void setAddress(String address) {
        this.address = address;
    }
        public String getCreateUser() {
        return this.createUser;
    }	
  
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    		    public String getCreateDate() {
        return this.createDate;
    }	
  
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    		    public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }	
  
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

	public List getChildDepartments() {
		return childDepartments;
	}

	public void setChildDepartments(List childDepartments) {
		this.childDepartments = childDepartments;
	}

}
