package common.log;

import org.apache.log4j.Logger;

/**
 * 
 * 日志编写 参考:http://www.tutorialspoint.com/spring/logging_with_log4j.htm
 * 
 * @author skygreen2001@gmail.com
 * 
 */
public class LogMe {
	static Logger log = Logger.getLogger(LogMe.class.getName());

	public static void info(String message) {
		log.info(message);
	}

	public static void debug(String message) {
		log.debug(message);
	}

	public static void error(String message) {
		log.error(message);
	}

	public static void fatal(String message) {
		log.fatal(message);
	}
}
