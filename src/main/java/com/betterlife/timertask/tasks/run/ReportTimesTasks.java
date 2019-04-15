package com.betterlife.timertask.tasks.run;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.betterlife.timertask.common.log.LogMe;
import com.betterlife.timertask.tasks.Tasks;

/**
 * 定点报时
 * @author skygreen2001@gmail.com
 *
 */
@EnableScheduling
public class ReportTimesTasks extends Tasks {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"HH:mm:ss");

	@Scheduled(fixedRate = 1000)
	public void reportCurrentTime() {
		LogMe.debug("现在报时:" + dateFormat.format(new Date()));
	}
}
