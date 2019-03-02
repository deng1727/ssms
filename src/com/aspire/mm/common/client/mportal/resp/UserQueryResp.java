package com.aspire.mm.common.client.mportal.resp;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.aspire.mm.common.client.httpsend.Resp;
import com.aspire.mm.common.client.mportal.vo.DepartmentVO;
import com.aspire.mm.common.client.mportal.vo.StaffVO;



public class UserQueryResp implements Resp {
	
	/**
     * 日志引用
     */
    private static Logger logger = Logger.getLogger(UserQueryResp.class);
    
    private String hRet;
    
    /**
     * 用户信息
     */
    private StaffVO Staff;

	public Resp praseResp(Document doc) {
		if(logger.isDebugEnabled()){
            logger.debug("UserQueryResp   start");
        }
		
		Element rootE = doc.getRootElement();
		this.setHRet(rootE.element("hRet").getText());
		StaffVO staff = new StaffVO();
		
		if(logger.isDebugEnabled()){
            logger.debug("hRet = " + this.getHRet());
        }
		
		//当查询成功时，设置用户信息
		if("0".equals(this.getHRet())){
			Element data = rootE.element("data").element("staff");		
			staff.setStaffId(data.element("staffId").getText()); 
			staff.setLoginName(data.element("loginName").getText());
			staff.setRealName(data.element("realName").getText());
			staff.setSex(data.element("sex").getText()); 
			staff.setTelephone(data.element("telephone").getText());
			staff.setMobile(data.element("mobile").getText()); 
			staff.setEmail(data.element("email").getText());
			staff.setDepartmentId(data.element("departmentId").getText());
			
			DepartmentVO department  = null;			
			Element dataD = data.element("department");
			if (dataD != null) {
				department = new DepartmentVO();
				department.setDepartmentId(dataD.element("departmentId").getText());				
				department.setDepartmentName(dataD.element("departmentName").getText());
				department.setEmail(dataD.element("email").getText());				
				department.setAddress(dataD.element("address").getText());
				staff.setDepartment(department);
			}
			
			if(logger.isDebugEnabled()){
	            logger.debug(staff.toString());
	        }
		}
		
		this.setStaff(staff);
		
		if(logger.isDebugEnabled()){
            logger.debug("UserQueryResp   end");
        }
		
		return this;
	}
	
	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("UserQueryResp[");
        sb.append("hRet:").append(hRet).append(",");
        sb.append("Staff:").append(Staff.toString());
        
        return sb.toString();
    }

	public StaffVO getStaff() {
		return Staff;
	}

	public void setStaff(StaffVO Staff) {
		this.Staff = Staff;
	}

	public String getHRet() {
		return hRet;
	}

	public void setHRet(String ret) {
		hRet = ret;
	}

}
