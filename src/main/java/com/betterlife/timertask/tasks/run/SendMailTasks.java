package com.betterlife.timertask.tasks.run;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.betterlife.timertask.common.Gc;
import com.betterlife.timertask.common.log.LogMe;
import com.betterlife.timertask.common.mail.MailSender;
import com.betterlife.timertask.domain.MailObject;
import com.betterlife.timertask.tasks.Tasks;

/**
 * 定时发送邮件
 * @author skygreen2001@gmail.com
 * 
 */ 
@EnableScheduling
public class SendMailTasks extends Tasks {

		private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"HH:mm:ss");

		@Scheduled(cron = "*/8 * * * * SUN-SAT")
		public void sendEmail() 
		{
			if (!Gc.isRunEmail)return;
			LogMe.debug("发送邮件开始时间: " + dateFormat.format(new Date()));
			MailObject mail=new MailObject();
			mail.setMailto("skygreen2001@163.com");
			mail.setSubject("烤鸡翅膀，我最爱吃!");
			mail.setUserName("周月璞");
			MailSender.robot().out(mail);
			LogMe.debug("发送邮件结束时间: " + dateFormat.format(new Date()));
		}

}
