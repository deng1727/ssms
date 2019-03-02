package com.aspire.ponaadmin.web.ztree;

import java.util.List;

import com.aspire.common.db.DAOException;

import junit.framework.TestCase;

public class TestTreeNodeManager extends TestCase{
	public void testA(){
		try {
			List list = new TreeNodeManager().getchildrenNodes("701");
			System.out.println(list.size());
			System.out.println(((TreeNode)list.get(0)).getName());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
