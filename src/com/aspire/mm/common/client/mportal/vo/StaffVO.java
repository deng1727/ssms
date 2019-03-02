package com.aspire.mm.common.client.mportal.vo;


/**
 * Staff对象.
 * @author mm autoCoder1.0
 *
 */
public class StaffVO {
    public static String STAFF_STATUS_INACTIVE = "0";// 待激活
	public static String STAFF_STATUS_NORMAL = "1";  // 正常
    public static String STAFF_STATUS_LOCK = "2";    // 锁定
	
	/**
     * 员工ID.
     */
    private String staffId;
        /**
     * 登录帐号.
     */
    private String loginName;
        /**
     * 组织ID.
     */
    private String departmentId;
        /**
     * 真实姓名.
     */
    private String realName;
        /**
     * 密码（经过加密）.
     */
    private String password;
        /**
     * 状态.
     */
    private String status;
        /**
     * 性别：MALE-男性；FEMALE-女性；UNKNOW-未知.
     */
    private String sex;
        /**
     * 电话.
     */
    private String telephone;
        /**
     * 手机.
     */
    private String mobile;
        /**
     * 邮箱.
     */
    private String email;
        /**
     * 组织创建者.
     */
    private String createUser;
    		    /**
     * 创建时间.
     */
    private String createDate;
    		    /**
     * 过期时间.
     */
    private String expireDate;
    		    /**
     * 最后更新时间.
     */
    private String lastUpdateDate;

    /**
     * 所属组织
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
