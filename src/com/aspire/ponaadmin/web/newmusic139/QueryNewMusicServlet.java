package com.aspire.ponaadmin.web.newmusic139;
/**
 * 
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.aspire.ponaadmin.web.newmusic139.bo.New139BaseSynBO;
/**
 * 
 * @author tungke
 *139音乐新音乐查询接口  http+json
 *
 */
public class QueryNewMusicServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	public void init() throws ServletException {
		System.out.print("QueryNewMusicServlet started!");
	}

	public void process(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml;charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
			String musicname = request.getParameter("musicname");
			String singer = request.getParameter("singer");
			String temp = "";
			New139BaseSynBO bo = new New139BaseSynBO();
			temp = bo.queryNewMusic(musicname, singer, null);
			response.getWriter().write(temp);
			response.getWriter().flush();
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}

}
