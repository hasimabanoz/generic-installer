package com.generic.installer.common;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.izforge.izpack.installer.AutomatedInstallData;

public class Log4JHelper implements InstallerConstants {

	private static Logger logger = Logger.getLogger(Log4JHelper.class);

	public static void loadLog4jProperties() {
		String logFilePath = AutomatedInstallData.getInstance().getInstallPath() + File.separator + "install.log";

		// load log4j properties
		Properties log4jProp = new Properties();
		log4jProp.put("log4j.rootCategory", "INFO,log");
		log4jProp.put("log4j.category.com.generic", "DEBUG, log, stdout");

		log4jProp.put("log4j.appender.log", "org.apache.log4j.DailyRollingFileAppender");
		log4jProp.put("log4j.appender.logger.Threshold", "DEBUG");

		log4jProp.put("log4j.appender.logger.layout", "org.apache.log4j.PatternLayout");
		log4jProp.put("log4j.appender.logger.layout.ConversionPattern", "%d{HH:mm:ss,SSS}\t%-5.5p\t%X{User}\t%c.%M(%L)\t%m\n");

		log4jProp.put("log4j.appender.logger.File", logFilePath);
		log4jProp.put("log4j.appender.logger.Append", "true");
		log4jProp.put("log4j.appender.logger.DatePattern", "'.'yyyy-MM-dd");
		// ##########################################################################################
		log4jProp.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		log4jProp.put("log4j.appender.stdout.Threshold", "DEBUG");
		log4jProp.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		log4jProp.put("log4j.appender.stdout.layout.ConversionPattern", "%d{HH:mm:ss,SSS} %c{1}.%M(%L) %-5p: %m%n");

		org.apache.log4j.PropertyConfigurator.configure(log4jProp);

		logger.info("initialize");
	}

}
