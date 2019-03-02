package com.aspire.ponaadmin.web.rightmanager.tag ;

import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.rightmanager.CompositeRightVO;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
import com.aspire.ponaadmin.common.rightmanager.RightVO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;

/**
 * <p>Ȩ��tag�ĸ�����</p>
 * <p>Ȩ��tag�ĸ�����</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightTagHelper
{

	/**
	 * Automatically generated method: RightTagHelper
	 */
	private RightTagHelper () {

	}

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(RightTagHelper.class);

    /**
     * �ж��Ƿ������Ȩ��
     * @param right RightVO
     * @param user UserSessionVO
     * @return boolean
     */
    public static boolean isInclude(RightVO right, UserSessionVO user)
    {
        if(right == null)
        {
            return false;
        }
        try
        {
            if(right instanceof CompositeRightVO)
            {
                CompositeRightVO compositeRight = (CompositeRightVO) right ;
                List rightList = compositeRight.getRightList();
                for(int i = 0; i < rightList.size(); i++)
                {
                    RightVO rightItem = (RightVO) rightList.get(i);
                    if(isInclude(rightItem, user))
                    {
                        return true;
                    }
                }

            }
            else
            {
                return RightManagerConstant.RIGHTCHECK_PASSED ==
                    RightManagerBO.getInstance().checkUserRight(user,
                                                                right.
                                                                getRightID()) ;
            }
        }
        catch (BOException e)
        {
            LOG.error(e) ;
        }
        return false;
    }
}
