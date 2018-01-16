package com.generic.launcher;

import java.util.Properties;

import org.apache.log4j.Logger;

public class ServiceFacade {
	private static Logger logger = Logger.getLogger(ServiceFacade.class);
	private static ServiceFacade instance = new ServiceFacade();

	public void startServices(Properties appProps, boolean startQuartz) throws Exception {
		try {
			logger.debug("\n\t Starting services..\n");

		} catch (Exception e) {
			logger.fatal(e, e);
			shutdownServices();
			throw new Exception(e);
		}
	}

	public void shutdownServices() {

	}

	public static ServiceFacade getInstance() {
		return instance;
	}

	public void writeMemoryLog() {
		if (logger.isDebugEnabled()) {
			long max = Runtime.getRuntime().maxMemory() / 1024L;
			long total = Runtime.getRuntime().totalMemory() / 1024L;
			long free = Runtime.getRuntime().freeMemory() / 1024L;
			long used = total - free;

			StringBuilder logString = new StringBuilder("\n\tMax Memory : ").append(String.valueOf(max)).append(" kb.");
			logString.append("\n\tTotal Memory : ").append(String.valueOf(total)).append(" kb.");
			logString.append("\n\tUsed Memory : ").append(String.valueOf(used)).append(" kb.");
			logString.append("\n\tFree Memory : ").append(String.valueOf(free)).append(" kb.");

			logger.debug(logString.toString());
		}
	}

	
}