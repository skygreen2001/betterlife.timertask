package com.betterlife.timertask.common.mail;

import java.io.StringWriter;
//import java.util.HashMap;
//import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.ui.velocity.VelocityEngineUtils;

import com.betterlife.timertask.common.Gc;
import com.betterlife.timertask.common.log.LogMe;
import com.betterlife.timertask.domain.MailObject;

public class MailSender {

	private JavaMailSender mailSender;

	private VelocityEngine velocityEngine;

	private static MailSender robot;

	private String defaultEncoding;

	private String fromEmail;

	private String mailModelFileName;

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * @return the mailModelFileName
	 */
	public String getMailModelFileName() {
		return mailModelFileName;
	}

	/**
	 * @param mailModelFileName
	 *            the mailModelFileName to set
	 */
	public void setMailModelFileName(String mailModelFileName) {
		this.mailModelFileName = mailModelFileName;
	}

	/**
	 * @return the defaultEncoding
	 */
	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	/**
	 * @param defaultEncoding
	 *            the defaultEncoding to set
	 */
	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public static MailSender robot() {
		if (robot == null) {
			robot = (MailSender) Gc.context.getBean(Gc.CTX_NAME_MAIL_SERVICE);
			LogMe.debug("启动邮箱服务！");
		}
		return robot;
	}

	public boolean out(final MailObject mailContent) {// final User user
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, defaultEncoding);
				message.setFrom(fromEmail);
				message.setSubject(mailContent.getSubject());
				message.setTo(mailContent.getMailto());
//				Map<String, Object> model = new HashMap<String, Object>();
//				model.put("message", mailContent);
//				String text = VelocityEngineUtils.mergeTemplateIntoString(
//				velocityEngine, mailModelFileName, defaultEncoding,
//				model);
//				message.setText(text, true);
				
				VelocityContext model = new VelocityContext();
				model.put("message", mailContent);
				StringWriter sw = new StringWriter();
				velocityEngine.mergeTemplate(getMailModelFileName(), getDefaultEncoding(), model, sw);
				message.setText(sw.toString(), true);
			}
		};
		this.mailSender.send(preparator);
		return true;
	}

}
