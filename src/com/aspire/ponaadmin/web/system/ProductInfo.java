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
     * 初始化参数由SystemConfigFilter加载，
     * 参数值来自web.xml文件中SystemConfigFilter的初始化参数。
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
     * Misc.OAM使用的 接口，只显示扩展名patchFilter一致的文件
     * @param internal Boolean
     * @return VersionVO
     */
    public static VersionVO queryVersionInfo(Boolean internal) {
        VersionVO version = new VersionVO();
        if (versionInfo == null) {
            load(internal.booleanValue());
        }
        /**
         * 版本号例如：MISC_PORTAL1.6.2
         */
        version.setVersionID(required(getField(versionInfo, "VersionID")));
        /**
         * 版本升级时间  例如：2003/10/08 00:30
         */
        version.setUpTime(required(getField(versionInfo, "UpdateTime")));
        /**
         * 版权信息  例如：版权所有?2000-2004卓望数码技术（深圳）有限公司
         */
        version.setOwnerInfo(required(getField(versionInfo, "OwnerInfo")));
        /**
         * 补丁信息列表，详细信息见  queryPatchsArray(boolean),
         */
        version.setPatchvos(queryPatchsArray(internal.booleanValue()));
        return version;
    }

    public static PatchVO[] queryPatchsArray() {
        return queryPatchsArray(isInternal);
    }

    /**
     * 获取patch信息
     * @param internal boolean
     * @return PatchVO[]
     */
    public static PatchVO[] queryPatchsArray(boolean internal) {
        /**
         *  isInternal代表隐秘性，当SystemConfigFilter之外的对象访问此方法时，
         * 有可能会改变隐秘性，这种改变 会与配置文件web.xml中的初始值发生
         * 矛盾。因此除非显式调用     setIsInternal（boolean）
         * 方法，否则queryPatchsArray(boolean )引起的改变
         * 被认为是临时性的，操作结束后恢复到初始化时的状态。
         */
        if (internal != isInternal) { // 如果隐秘性要求与系统初始化的值不一致, 则保存原始值重新加载信息。
            load(internal);
        }

        if (patchesInfo == null) {
            return null; //容错处理，如果load仍然得不到任何信息，则报错
        }

        Vector patchVOs = new Vector();
        for (int i = 0; i < patchesInfo.length; i++) {
            if (patchesInfo[i] == null) {
                continue;
            }
            PatchVO patchVO = new PatchVO();
            /*
             * 补丁ID例如：MISC_PORTAL_PATCH1.6.2.001
             */
            patchVO.setPatchID(required(getField(patchesInfo[i], "PatchID")));
            /**
             * 补丁名称例如：USER_PATCH
             * */
            patchVO.setPatchName(required(getField(patchesInfo[i], "PatchName")));
            /**
             * 补丁描述例如：本补丁完善了用户管理部分功能
             */
            patchVO.setPatchDesc(required(getField(patchesInfo[i],
                    "Description")));
            /**
             * 补丁修改的缺陷描述，包括缺陷ID、缺陷详细信息、缺陷级别等
             * 例如：本补丁修改了，用户管理中添加用户不成功的问题；
             * 缺陷ID：4560；该问题导致添加用户的功能不可用；缺陷级别：高；
             */
            patchVO.setPatchAmended(optional(getField(patchesInfo[i], "Amended")));
            /**
             *  补丁新增功能描述，包括对应的需求ID、需求详细信息等
             * 例如：本补丁新增加了用户(维护人员）考勤统计功能，可以对用户
             * 处理突发事件的效率进行统计；对应的需求ID：R5002
             */
            patchVO.setPatchAdded(optional(getField(patchesInfo[i], "Added")));
            /**
             * 打补丁的时间  例如：2004-04-19
             */
            patchVO.setUpTime(required(getField(patchesInfo[i], "UpdateTime")));
            patchVOs.add(patchVO);
        }

        if (internal != isInternal) { // 恢复为原来的隐秘性级别
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
            logger.error("没有找到文件：" + getVersionFile()+e.toString());
        } catch (IOException e) {
            logger.error("读取文件错误：" + getVersionFile()+e.toString());
        }
        patchesInfo = getPatchInfo(internal);
        /*get patch info in another method
                 File patchDir = new File(getPatchPath());
                 File[] patchFiles = new File[0];
                 if (patchDir.isDirectory()) {
            if (internal) { // 对内部显示所有Patch信息
                patchFiles = patchDir.listFiles();
            } else { //对外部只显示扩展名是txt的Patch信息
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
            logger.error("没有找到路径：" + getPatchPath());
                 } catch (IOException e) {
            logger.error("读取文件路径：" + getPatchPath());
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
     * 需求中“重要性”尚不明确，此处留下“必须”的扩展。
     * 同时，空值(null）需要转化为空字串(""），避免“空指针”错误的传播
     * @param value String
     * @return String
     */
    private static String required(String value) {
        return value == null ? "" : value;
    }

    /**
     * 需求中“重要性”尚不明确，此处留下“可选”的扩展。
     * 同时，空值(null）需要转化为空字串(""），避免“空指针”错误的传播
     * @param value String
     * @return String
     */
    private static String optional(String value) {
        return value == null ? "" : value;
    }

    /*
     * 补丁ID例如：MISC_PORTAL_PATCH1.6.2.001
     */

    public static String getVersionID() {
        return getField(versionInfo, "VersionID");
    }

    /**
     * 版本升级时间  例如：2003/10/08 00:30
     * @return String
     */
    public static String getUpdateTime() {
        return getField(versionInfo, "UpdateTime");
    }

    /**
     * 版权信息  例如：版权所有?2000-2004卓望数码技术(深圳）有限公司
     * @return String
     */
    public static String getOwnerInfo() {
        return getField(versionInfo, "OwnerInfo");
    }

    /**
     * 版权的声明信息
     * @return String
     */
    public static String getWarnInfo() {

        return getField(versionInfo, "WarnInfo");
    }

    /**
     * 处理中文编码问题
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
            logger.error("版本/Patch信息有误.具体错误在：" + key);
            field="[none]";
        }
        return field;
    }

    public static void setServContext(javax.servlet.ServletContext pServContext) {
        servContext = pServContext;
    }


}

/**
 * 测试代码
 * TrySth.tryProductInfo();
 */
