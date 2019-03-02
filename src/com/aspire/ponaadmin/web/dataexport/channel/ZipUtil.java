package com.aspire.ponaadmin.web.dataexport.channel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.mail.Mail;

/**
 * 压缩工具 生成zip文件
 */
public class ZipUtil {

	/**
	 * @param zipFile  目标 生成zip文件名
	 * @param srcPathName 要压缩的文件所在的路径
	 * @param includes 包含的文件列表 eg:zip.setIncludes("*.java");
	 */
	public static void compress(File zipFile, String srcPathName,
			String includes) {
		File srcdir = new File(srcPathName);
		if (!srcdir.exists())
			throw new RuntimeException(srcPathName + "不存在！");

		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		fileSet.setIncludes(includes);// 包括哪些文件或文件夹
		// eg:zip.setIncludes("*.java");
		// fileSet.setExcludes(...); 排除哪些文件或文件夹
		zip.addFileset(fileSet);
		zip.execute();
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String[] a = new String[]{"11","22","33"};
		String[] b = new String[]{"44","55","66"};
		List l = new ArrayList();
		
		l.add(a);
		l.add(b);
		DataExportTools.writeToCSVFile("D:/temp/20100710/tt.csv",l,"UTF-8");
		File f = new File("D:/temp/20100710/20100710.zip");
		ZipUtil.compress(f, "D:/temp/20100710", "**/*.csv");
		
		Mail.sendMail("aa", "aaa", new String[]{"zaife@vip.sina.com"},new File[]{f});
	}

}
