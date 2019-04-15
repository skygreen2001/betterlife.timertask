package com.betterlife.timertask.domain;

public class MailObject 
{
	/**
	 * 发送至邮箱
	 */
	private String mailto;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 发送至用户名
	 */
	private String userName;

	/**
	 * @return the mailto
	 */
	public String getMailto() {
		return mailto;
	}

	/**
	 * @param mailto the mailto to set
	 */
	public void setMailto(String mailto) {
		this.mailto = mailto;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	

}
