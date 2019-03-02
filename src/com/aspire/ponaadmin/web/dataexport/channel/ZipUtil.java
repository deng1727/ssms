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
 * ѹ������ ����zip�ļ�
 */
public class ZipUtil {

	/**
	 * @param zipFile  Ŀ�� ����zip�ļ���
	 * @param srcPathName Ҫѹ�����ļ����ڵ�·��
	 * @param includes �������ļ��б� eg:zip.setIncludes("*.java");
	 */
	public static void compress(File zipFile, String srcPathName,
			String includes) {
		File srcdir = new File(srcPathName);
		if (!srcdir.exists())
			throw new RuntimeException(srcPathName + "�����ڣ�");

		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		fileSet.setIncludes(includes);// ������Щ�ļ����ļ���
		// eg:zip.setIncludes("*.java");
		// fileSet.setExcludes(...); �ų���Щ�ļ����ļ���
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
