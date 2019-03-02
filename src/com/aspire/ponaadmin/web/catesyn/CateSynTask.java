package com.aspire.ponaadmin.web.catesyn;

import java.util.TimerTask;

public class CateSynTask extends TimerTask {

	public void run() {
		CateSynBo bo = new CateSynBo();
		try {
			bo.doSynCate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
