package com.aspire.mm.common.client.mportal.vo;

import java.util.List;

/**
 * Department����.
 * @author mm autoCoder1.0
 *
 */
public class DepartmentVO  {
    /**
     * ����֯.
     */	
    public static String ROOT_PARENT_ID = "root";	
	
     /**
     * ��֯ID.
     */
    private String departmentId;
        /**
     * ��֯����.
     */
    private String departmentName;
        /**
     * ��֯����.
     */
    private String departmentDesc;
        /**
     * ������׳�ID.
     */
    private String parentId;
        /**
     * �ʼ�.
     */
    private String email;
        /**
     * ��ַ.
     */
    private String address;
        /**
     * ��֯������.
     */
    private String createUser;
    		    /**
     * ����ʱ��.
     */
    private String createDate;
    		    /**
     * ������ʱ��.
     */
    private String lastUpdateDate;
    
    /**
     * ������ʱ��.
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
