package com.aspire.dotcard.syncAndroid.ssms;

public class RankVO {
	private String contentid;
	private Long rank;

	RankVO(String contentid, Long rank) {
		this.contentid = contentid;
		this.rank = rank;
	}

	public String getContentid() {
		return contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

}
