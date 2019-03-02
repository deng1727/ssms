package com.aspire.ponaadmin.web.music139;

public class Result {

	private String name;

	private String msg;

	private StringBuffer skipMusic = new StringBuffer();

	private int invalidteCount = 0;

	private boolean success = false;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Result(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getMsg() {
		if (this.success) {
			return msg + "<br>"
					+ this.skipMusic.toString();
		} else {
			return msg;
		}
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addInvalidateCount() {
		this.invalidteCount++;
	}

	public StringBuffer getSkipMusic() {
		return skipMusic;
	}

	public void setSkipMusic(StringBuffer skipMusic) {
		this.skipMusic = skipMusic;
	}
}
