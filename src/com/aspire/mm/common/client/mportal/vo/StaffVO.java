package com.aspire.mm.common.client.mportal.vo;


/**
 * Staff����.
 * @author mm autoCoder1.0
 *
 */
public class StaffVO {
    public static String STAFF_STATUS_INACTIVE = "0";// ������
	public static String STAFF_STATUS_NORMAL = "1";  // ����
    public static String STAFF_STATUS_LOCK = "2";    // ����
	
	/**
     * Ա��ID.
     */
    private String staffId;
        /**
     * ��¼�ʺ�.
     */
    private String loginName;
        /**
     * ��֯ID.
     */
    private String departmentId;
        /**
     * ��ʵ����.
     */
    private String realName;
        /**
     * ���루�������ܣ�.
     */
    private String password;
        /**
     * ״̬.
     */
    private String status;
        /**
     * �Ա�MALE-���ԣ�FEMALE-Ů�ԣ�UNKNOW-δ֪.
     */
    private String sex;
        /**
     * �绰.
     */
    private String telephone;
        /**
     * �ֻ�.
     */
    private String mobile;
        /**
     * ����.
     */
    private String email;
        /**
     * ��֯������.
     */
    private String createUser;
    		    /**
     * ����ʱ��.
     */
    private String createDate;
    		    /**
     * ����ʱ��.
     */
    private String expireDate;
    		    /**
     * ������ʱ��.
     */
    private String lastUpdateDate;

    /**
     * ������֯
     * @return
     */
    private DepartmentVO department;
    
	public String getStaffId() {
        return this.staffId;
    }	
  
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
        public String getLoginName() {
        return this.loginName;
    }	
  
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
        public String getDepartmentId() {
        return this.departmentId;
    }	
  
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
        public String getRealName() {
        return this.realName;
    }	
  
    public void setRealName(String realName) {
        this.realName = realName;
    }
        public String getPassword() {
        return this.password;
    }	
  
    public void setPassword(String password) {
        this.password = password;
    }
        public String getStatus() {
        return this.status;
    }	
  
    public void setStatus(String status) {
        this.status = status;
    }
        public String getSex() {
        return this.sex;
    }	
  
    public void setSex(String sex) {
        this.sex = sex;
    }
        public String getTelephone() {
        return this.telephone;
    }	
  
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
        public String getMobile() {
        return this.mobile;
    }	
  
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
        public String getEmail() {
        return this.email;
    }	
  
    public void setEmail(String email) {
        this.email = email;
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
    		    public String getExpireDate() {
        return this.expireDate;
    }	
  
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
    		    public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }	
  
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

	public DepartmentVO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentVO department) {
		this.department = department;
	}
}
