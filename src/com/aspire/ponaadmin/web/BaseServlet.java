package com.aspire.ponaadmin.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;





import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.actionlog.ActionLogBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceUtil;
import com.aspire.ponaadmin.web.datafield.UploadFileKeyResUtil;
import com.aspire.ponaadmin.web.datafield.bo.KeyBaseBO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.newmusicsys.action.FileForm;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.system.SystemConfig;

public class BaseServlet extends HttpServlet {
	public final static String HTML_CONTENT_CHARSET = "text/html;charset=UTF-8";
	public final static String REQ_CHARSET_UTF_8 = "UTF-8";

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(BaseServlet.class);

	public void init(ServletConfig config) {

	}

	/***************************************************************************
	 * 从post请求里获取json对象
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 * @throws Exception
	 */
	public JSONObject getJsonFromRequest(
			javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
			throws IOException, JSONException {

		StringBuffer info = new StringBuffer();
		BufferedInputStream buf = null;
		try {
			buf = new BufferedInputStream(request.getInputStream());
			byte[] buffer = new byte[1024];
			int iRead = -1;
			while ((iRead = buf.read(buffer)) != -1) {
				info.append(new String(buffer, 0, iRead, "UTF-8"));
			}
		} finally {
			if (buf != null)
				buf.close();
		}

		String postStr = info.toString();
	    JSONObject json = JSONObject.fromObject(postStr);
		LOG.debug("jsonRequest:" + json.toString());

		return json;
	}

	/***************************************************************************
	 * json对象输出
	 * 
	 * @param jsonStr
	 * @param response
	 */
	public void flushJsonStr(String jsonStr,
			javax.servlet.http.HttpServletResponse response) {

		LOG.debug("jsonResponse:" + jsonStr);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			byte[] contentBytes = jsonStr.getBytes("utf-8");
			response.setContentLength(contentBytes.length);
			out.write(contentBytes);
			out.flush();
		} catch (Exception e) {
			LOG.error("输出后台流数据失败", e);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 输出结果
	 * 
	 * @param resp
	 * @param msg
	 * @throws IOException
	 */
	public void printOut(HttpServletResponse resp, String msg)
			throws IOException {

		resp.setContentType(HTML_CONTENT_CHARSET);
		resp.setCharacterEncoding(REQ_CHARSET_UTF_8);
		PrintWriter out;
		out = resp.getWriter();
		out.println(msg);
	}

	/***************************************************************************
	 * 输出响应xml
	 * 
	 * @param respXML
	 * @param response
	 */
	public void flushXML(String respXML,
			javax.servlet.http.HttpServletResponse response) {

		LOG.debug("respXML:" + respXML);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			byte[] contentBytes = respXML.getBytes("utf-8");
			response.setContentLength(contentBytes.length);
			out.write(contentBytes);
			out.flush();
		} catch (Exception e) {
			LOG.error("输出后台流数据失败", e);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 从请求中获取参数，如果为null就返回空字符串""
	 * 
	 * @param request
	 *            http请求
	 * @param key
	 *            参数的关键字
	 * @return 参数值
	 */
	protected String getParameter(HttpServletRequest request, String key) {
		String value = request.getParameter(key);
		if (value == null) {
			value = "";
		}
		// return Validator.filter(value.trim()) ;
		return value.trim();
	}

	/**
	 * 
	 * @desc
	 * @author dongke Aug 9, 2011
	 * @param keyBaseList
	 * @param delResourceList
	 * @param fileForm
	 * @param cid
	 * @param request
	 * @param resServerPath
	 * @throws BOException
	 */
	protected void saveKeyResource(List keyBaseList, List delResourceList,
			FileForm fileForm, String cid, HttpServletRequest request,
			String resServerPath, String servicePath) throws BOException {
		for (int i = 0; i < keyBaseList.size(); i++) {
			ResourceVO vo = (ResourceVO) keyBaseList.get(i);
			if (vo.getKeyType().equals("2")) {// 图片（文件）域
				String fileUrlValue = UploadFileKeyResUtil.getInstance()
						.getFileUrl(fileForm, resServerPath, cid,
								vo.getKeyname(), servicePath);
				vo.setValue(fileUrlValue);
				vo.setTid(cid);
			} else if (vo.getKeyType().equals("1")
					|| vo.getKeyType().equals("3")) {
				// 普通文本域 或大文本域
				vo.setValue(this.getParameter(request, vo.getKeyname()).trim());
				vo.setTid(cid);
			} else {
				LOG.debug("keyType is not reg:vo.getKeyType() = "
						+ vo.getKeyType());
			}
			String delValue = this.getParameter(request,
					"clear_" + vo.getKeyname()).trim();
			if (delValue != null && delValue.equals("1")) {
				delResourceList.add(vo);
			}
		}
	}

	/**
	 * 具体实现方式。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param key
	 *            String
	 * @param addInfo
	 *            String
	 */
	protected void saveMessages(HttpServletRequest request, String key,
			String addInfo) {
		String[] addInfos = { addInfo };
		this.saveMessages(request, key, addInfos);
	}

	protected void saveMessages(HttpServletRequest request, String key,
			String[] addInfos) {
		if ((key == null) || key.equals("")) { // no key ,just return
			return;
		}
		String msg = "";

		msg = ResourceUtil.getResource(key);
		if ((msg != null) && !msg.equals("")) {
			msg = MessageFormat.format(msg, addInfos);
			this.saveMessagesValue(request, msg);
		} else {
			LOG.error("Cannot get the key's value.The key is " + key);
		}
	}

	/**
	 * add a message value to the message vector
	 * 
	 * @param request
	 *            http请求
	 * @param msgValue
	 *            信息内容
	 */
	protected void saveMessagesValue(HttpServletRequest request, String msgValue)

	{
		if ((msgValue == null) || msgValue.equals("")) { // no key ,just return
			return;
		}
		Vector msgs = (Vector) request.getAttribute(Constants.REQ_KEY_MESSAGE);
		if (msgs == null) {
			msgs = new Vector();
		}
		if (!msgs.contains(msgValue)) { // donot add repeated
			msgs.add(msgValue);
		}
		request.setAttribute(Constants.REQ_KEY_MESSAGE, msgs);
	}
	
    /**
     * 提供子类保存需要显示到前台的信息。
     * @param request HttpServletRequest
     * @param key String
     */
    protected void saveMessages (HttpServletRequest request, String key)
    {
        if ((key == null) || key.equals(""))
        { //no key ,just return
            return ;
        }
        saveMessages(request, key, "") ;
    }
    
    /**
     * 写操作日志的封装方法，
     * 请注意！！！
     * 只能是登录后的action调用才能调用这个方法，不然会记录日志失败！！！
     * @param request HttpServletRequest
     * @param actionType String
     * @param actionTarget String
     * @param actionResult boolean
     * @param desc String
     */
    protected void actionLog (HttpServletRequest request, String actionType,
                              String actionTarget, boolean actionResult,
                              String desc)
    {
        //写操作日志
        try
        {
            UserVO logUser = null ;
            String IP = null ;
            UserSessionVO userSessionVO = UserManagerBO.getInstance().
                getUserSessionVO(request.getSession()) ;

            if (userSessionVO != null)
            {
                logUser = userSessionVO.getUser() ;
                //IP = userSessionVO.getAccessInfo().getIP() ;
                IP = request.getRemoteAddr() ;
            }
            else
            {
                logUser = new UserVO() ;
                IP = request.getRemoteAddr() ;
                logUser.setUserID("unknow") ;
                logUser.setName("unknow") ;

            }

            ActionLogBO.getInstance().log(logUser.getUserID(), logUser.getName(),
                                          logUser.getUserRolesInfo() == null ?
                                          "" : logUser.getUserRolesInfo(),
                                          actionType, actionResult,
                                          actionTarget, IP, desc) ;
        }
        catch (BOException e)
        {
            LOG.error(e) ;
        }
    }
    
    /**
     * 
     *@desc 保存扩展字段
     *@author dongke
     *Aug 8, 2011
     * @throws BOException 
     */
    protected void saveCategoryKeyResource(List keyBaseList,FileForm fileForm,String cid,HttpServletRequest request) throws BOException{

    	//资源服务器本模块路径
    	String resServerPath = SystemConfig.FTPDIR_CATEGORYPICURL;  	
    	List delResourcelist = new ArrayList();
    	this.saveKeyResource(keyBaseList,delResourcelist,fileForm,cid,request,resServerPath,"category"); 
    	
    	CategoryBO.getInstance().saveKeyResource(keyBaseList);	
    	KeyBaseBO.getInstance().delKeyResourceListValue(delResourcelist);
    }
}
