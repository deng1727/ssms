package com.aspire.ponaadmin.web.system ;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * /**
  * <p>Title: </p>
  * <p>Description: </p>
  * <p>Copyright: Copyright (c) 2004</p>
  * <p>Company: www.aspire-tech.com</p>
  * Author: chenyang
  * Date: 2004-8-25
  * Time: 17:23:55
  * @CheckItem@ OPT-yanfeng-20041102 place version and patch files into war
  */
public class ProductInfo
{
    private static JLogger logger = LoggerFactory.getLogger(ProductInfo.class) ;

    /**
     * ��ʼ��������SystemConfigFilter���أ�
     * ����ֵ����web.xml�ļ���SystemConfigFilter�ĳ�ʼ��������
     */
    private static String versionFile = "/version.txt";
    private static String patchPath = "/patch/";
    private static boolean isInternal = false;
    private static String patchFilter = "txt";

    private static Properties versionInfo = null;
    private static Properties[] patchesInfo = null;
    private static javax.servlet.ServletContext servContext;

    public ProductInfo() {
    }

    /**
     * Misc.OAMʹ�õ� �ӿڣ�ֻ��ʾ��չ��patchFilterһ�µ��ļ�
     * @param internal Boolean
     * @return VersionVO
     */
    public static VersionVO queryVersionInfo(Boolean internal) {
        VersionVO version = new VersionVO();
        if (versionInfo == null) {
            load(internal.booleanValue());
        }
        /**
         * �汾�����磺MISC_PORTAL1.6.2
         */
        version.setVersionID(required(getField(versionInfo, "VersionID")));
        /**
         * �汾����ʱ��  ���磺2003/10/08 00:30
         */
        version.setUpTime(required(getField(versionInfo, "UpdateTime")));
        /**
         * ��Ȩ��Ϣ  ���磺��Ȩ����?2000-2004׿�����뼼�������ڣ����޹�˾
         */
        version.setOwnerInfo(required(getField(versionInfo, "OwnerInfo")));
        /**
         * ������Ϣ�б���ϸ��Ϣ��  queryPatchsArray(boolean),
         */
        version.setPatchvos(queryPatchsArray(internal.booleanValue()));
        return version;
    }

    public static PatchVO[] queryPatchsArray() {
        return queryPatchsArray(isInternal);
    }

    /**
     * ��ȡpatch��Ϣ
     * @param internal boolean
     * @return PatchVO[]
     */
    public static PatchVO[] queryPatchsArray(boolean internal) {
        /**
         *  isInternal���������ԣ���SystemConfigFilter֮��Ķ�����ʴ˷���ʱ��
         * �п��ܻ�ı������ԣ����ָı� ���������ļ�web.xml�еĳ�ʼֵ����
         * ì�ܡ���˳�����ʽ����     setIsInternal��boolean��
         * ����������queryPatchsArray(boolean )����ĸı�
         * ����Ϊ����ʱ�Եģ�����������ָ�����ʼ��ʱ��״̬��
         */
        if (internal != isInternal) { // ���������Ҫ����ϵͳ��ʼ����ֵ��һ��, �򱣴�ԭʼֵ���¼�����Ϣ��
            load(internal);
        }

        if (patchesInfo == null) {
            return null; //�ݴ������load��Ȼ�ò����κ���Ϣ���򱨴�
        }

        Vector patchVOs = new Vector();
        for (int i = 0; i < patchesInfo.length; i++) {
            if (patchesInfo[i] == null) {
                continue;
            }
            PatchVO patchVO = new PatchVO();
            /*
             * ����ID���磺MISC_PORTAL_PATCH1.6.2.001
             */
            patchVO.setPatchID(required(getField(patchesInfo[i], "PatchID")));
            /**
             * �����������磺USER_PATCH
             * */
            patchVO.setPatchName(required(getField(patchesInfo[i], "PatchName")));
            /**
             * �����������磺�������������û������ֹ���
             */
            patchVO.setPatchDesc(required(getField(patchesInfo[i],
                    "Description")));
            /**
             * �����޸ĵ�ȱ������������ȱ��ID��ȱ����ϸ��Ϣ��ȱ�ݼ����
             * ���磺�������޸��ˣ��û�����������û����ɹ������⣻
             * ȱ��ID��4560�������⵼������û��Ĺ��ܲ����ã�ȱ�ݼ��𣺸ߣ�
             */
            patchVO.setPatchAmended(optional(getField(patchesInfo[i], "Amended")));
            /**
             *  ������������������������Ӧ������ID��������ϸ��Ϣ��
             * ���磺���������������û�(ά����Ա������ͳ�ƹ��ܣ����Զ��û�
             * ����ͻ���¼���Ч�ʽ���ͳ�ƣ���Ӧ������ID��R5002
             */
            patchVO.setPatchAdded(optional(getField(patchesInfo[i], "Added")));
            /**
             * �򲹶���ʱ��  ���磺2004-04-19
             */
            patchVO.setUpTime(required(getField(patchesInfo[i], "UpdateTime")));
            patchVOs.add(patchVO);
        }

        if (internal != isInternal) { // �ָ�Ϊԭ���������Լ���
            load(isInternal);
        }
        PatchVO[] patchVOArray = new PatchVO[patchVOs.size()];
//        for (int i = 0; i < patchVOArray.length; i++) {
//             patchVOArray[i] = (PatchVO) patchVOs.elementAt(i);
//        }
        //add start by caohy 2005-12-12
        List list = new ArrayList();
        Object[] objArray = null;
        TreeMap map = null;
        if (patchVOs != null) {
            map = new TreeMap();
            for (int i = 0; i < patchVOs.size(); i++) {
                map.put(((PatchVO) patchVOs.elementAt(i)).getPatchID(),
                        (PatchVO) patchVOs.elementAt(i));
            }
        }
        Collection collection = map.values();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        Collections.reverse(list);
        objArray = list.toArray();
        for (int i = 0; i < objArray.length; i++) {
            patchVOArray[i] = (PatchVO) objArray[i];
        }
        //add end by caohy 2005-12-12

        return patchVOArray;
    }

    public static void setVersionFile(String filename) {
        versionFile = filename;
    }

    public static String getVersionFile() {
        if (versionFile == null) {
            return "/version.txt";
        } else {
            return versionFile;
        }
    }

    public static void setPatchPath(String path) {
        patchPath = path;
    }

    public static String getPatchPath() {
        if (patchPath == null) {
            return "/patch/";
        } else {
            return patchPath;
        }
    }


    public static void load() {
        load(isInternal);
    }

    public static void load(boolean internal) {
        versionInfo = new Properties();
        try {
            versionInfo.load(servContext.getResourceAsStream(getVersionFile()));
        } catch (FileNotFoundException e) {
            logger.error("û���ҵ��ļ���" + getVersionFile()+e.toString());
        } catch (IOException e) {
            logger.error("��ȡ�ļ�����" + getVersionFile()+e.toString());
        }
        patchesInfo = getPatchInfo(internal);
        /*get patch info in another method
                 File patchDir = new File(getPatchPath());
                 File[] patchFiles = new File[0];
                 if (patchDir.isDirectory()) {
            if (internal) { // ���ڲ���ʾ����Patch��Ϣ
                patchFiles = patchDir.listFiles();
            } else { //���ⲿֻ��ʾ��չ����txt��Patch��Ϣ
         patchFiles = patchDir.listFiles(new PatchFileFilter("." + patchFilter));
            }

                 }
                 patchesInfo = new Properties[patchFiles.length];
                 try {
            for (int i = 0; i < patchFiles.length; i++) {
                patchesInfo[i] = new Properties();
                patchesInfo[i].load(new FileInputStream(patchFiles[i]));
            }
                 } catch (FileNotFoundException e) {
            logger.error("û���ҵ�·����" + getPatchPath());
                 } catch (IOException e) {
            logger.error("��ȡ�ļ�·����" + getPatchPath());
                 }*/
    }

    private static Properties[] getPatchInfo(boolean internal) {
        //System.out.println("now into getPatchInfo:"+getPatchPath());
        Set path = servContext.getResourcePaths(getPatchPath());
        //System.out.println("path:"+path);
        Properties[] patchesInfos = null;
        if (path != null) {
            Object[] patchObjs = path.toArray();
            patchesInfos = new Properties[patchObjs.length];
            for (int i = 0; i < patchObjs.length; i++) {
                String pFile = (String) patchObjs[i];
                if (!internal) { //for external usage
                    if (!pFile.endsWith(patchFilter)) { //external can not view the txt file type
                        continue;
                    }
                }
                InputStream is = servContext.getResourceAsStream(pFile);
                //System.out.println("is :"+is);
                try {
                    patchesInfos[i] = new Properties();
                    patchesInfos[i].load(is);
                    is.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return patchesInfos;
    }

    public static void setIsInternal(String internal) {
        isInternal = "true".equals(internal) ? true : false;
    }

    public static void setPatchFilter(String filter) {
        if (filter == null) {
            return;
        }
        patchFilter = filter;
    }

    /**
     * �����С���Ҫ�ԡ��в���ȷ���˴����¡����롱����չ��
     * ͬʱ����ֵ(null����Ҫת��Ϊ���ִ�(""�������⡰��ָ�롱����Ĵ���
     * @param value String
     * @return String
     */
    private static String required(String value) {
        return value == null ? "" : value;
    }

    /**
     * �����С���Ҫ�ԡ��в���ȷ���˴����¡���ѡ������չ��
     * ͬʱ����ֵ(null����Ҫת��Ϊ���ִ�(""�������⡰��ָ�롱����Ĵ���
     * @param value String
     * @return String
     */
    private static String optional(String value) {
        return value == null ? "" : value;
    }

    /*
     * ����ID���磺MISC_PORTAL_PATCH1.6.2.001
     */

    public static String getVersionID() {
        return getField(versionInfo, "VersionID");
    }

    /**
     * �汾����ʱ��  ���磺2003/10/08 00:30
     * @return String
     */
    public static String getUpdateTime() {
        return getField(versionInfo, "UpdateTime");
    }

    /**
     * ��Ȩ��Ϣ  ���磺��Ȩ����?2000-2004׿�����뼼��(���ڣ����޹�˾
     * @return String
     */
    public static String getOwnerInfo() {
        return getField(versionInfo, "OwnerInfo");
    }

    /**
     * ��Ȩ��������Ϣ
     * @return String
     */
    public static String getWarnInfo() {

        return getField(versionInfo, "WarnInfo");
    }

    /**
     * �������ı�������
     * @param prop Properties
     * @param key String
     * @return String
     */
    public static String getField(Properties prop, String key) {
        if (prop == null) {
            return "";
        }
        String field = prop.getProperty(key);
        if (field == null) {
            logger.error("�汾/Patch��Ϣ����.��������ڣ�" + key);
            field="[none]";
        }
        return field;
    }

    public static void setServContext(javax.servlet.ServletContext pServContext) {
        servContext = pServContext;
    }


}

/**
 * ���Դ���
 * TrySth.tryProductInfo();
 */
