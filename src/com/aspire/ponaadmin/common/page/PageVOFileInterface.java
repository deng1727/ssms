package com.aspire.ponaadmin.common.page;

import java.io.File;
/**
 * <p>File��ҳ����VO�Ĺ���͸�ֵ�ӿ�</p>
 * <p>Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author dongxk
 * @version 1.0.0.0
 */

public interface PageVOFileInterface
{

    /**
     * ����VO����
     *
     * @return VOObject
     */
    public Object createObject () ;

    /**
     * ��File��ȡ������ֵ����VO����
     * @param vo VOObject
     * @param f File
     */
    public void CopyValFromFile(Object vo, File f);
}
