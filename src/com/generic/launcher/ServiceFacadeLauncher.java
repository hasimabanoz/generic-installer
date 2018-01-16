package com.generic.launcher;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.generic.Application;
import com.generic.util.PropertiesUtils;

public class ServiceFacadeLauncher {
	private static Logger logger = Logger.getLogger(ServiceFacadeLauncher.class);

	private static ServiceFacadeLauncher instance = new ServiceFacadeLauncher();
	private static Application application = new Application();
	private static boolean serviceStopped = true;
	private Properties appProps = new Properties();

	// runImmediately parameters
	private boolean runImmediately = false;

	public boolean init(String appPropertiesFileName) {
		try {
			logger.debug(application.getFullDescription() + " initializing....");

			if (PropertiesUtils.loadProperties(appPropertiesFileName, appProps)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.fatal(e, e);
			try {
				stopService();
			} catch (Exception e1) {
				logger.error(e1, e1);
			}
			return false;
		}

	}

	public static void main(String[] args) {
		// Add shutdown hook for gracefully shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				getInstance().stopService();
			}
		});
		startService(args);
	}

	private void start(String appPropertiesFileName) {
		serviceStopped = false;
		if (getInstance().init(appPropertiesFileName)) {
			try {
				powerOn();
				while (!serviceStopped) {
					synchronized (this) {
						try {
							printMemoryUsage();
							// wait 10 minute
							this.wait(10 * 60 * 1000);

						} catch (InterruptedException e) {
							logger.fatal(e, e);
						}
					}
				}
			} catch (Exception e) {
				logger.fatal(e, e);
			}
		} else {
			logger.error("***** " + application.getFullDescription() + " couldn't initialized....");
		}
		try {
			getInstance().powerOff();
		} catch (Exception e) {
			logger.error(e, e);
		}

	}

	private void powerOn() throws Exception {
		if (runImmediately) {
			//ServiceFacade.getInstance().startServices(appProps, false);
			//ServiceFacade.getInstance().startDSSProcess(xmlFilePattern, xslFileName);
			stopService();
		} else {
			ServiceFacade.getInstance().startServices(appProps, true);
		}
	}

	private void powerOff() {
		ServiceFacade.getInstance().shutdownServices();
	}

	public static ServiceFacadeLauncher getInstance() {
		return instance;
	}

	/**
	 * Print memory usage to log file.
	 */
	private void printMemoryUsage() {
		if (logger.isDebugEnabled()) {
			long max = Runtime.getRuntime().maxMemory() / 1024;
			long total = Runtime.getRuntime().totalMemory() / 1024;
			long free = Runtime.getRuntime().freeMemory() / 1024;
			long used = total - free;

			// Build log string
			StringBuilder logString = new StringBuilder("\n\tMax Memory : ").append(String.valueOf(max)).append(" kb.");
			logString.append("\n\tTotal Memory : ").append(String.valueOf(total)).append(" kb.");
			logString.append("\n\tUsed Memory : ").append(String.valueOf(used)).append(" kb.");
			logString.append("\n\tFree Memory : ").append(String.valueOf(free)).append(" kb.");

			logger.debug(logString.toString());
		}
	}

	/**
	 * Static method called by prunsrv to start/stop the service. Pass the argument "start" to start the service, and pass "stop" to stop the service.
	 */
	public static void startService(String args[]) {
		try {
			StringBuilder logString = new StringBuilder();
			logString.append("\n\n\n\n\n");
			logString.append("\n\t******************************************************************************************");
			logString.append("\n\t***** ").append(application.getFullDescription()).append(" is starting ....");
			logString.append("\n\t******************************************************************************************\n\n\n\n");
			logger.debug(logString.toString());

			logger.debug("Parameters : ");
			for (int i = 0; i < args.length; i++) {
				logger.debug("Param " + i + ": " + args[i]);
			}

			String appPropertiesFileName = "YOKCKayit.properties";

			if (args.length > 0 && StringUtils.isNotBlank(args[0]))
				appPropertiesFileName = args[0];
			if (StringUtils.isEmpty(appPropertiesFileName))
				throw new Exception("Parameter is empty : " + args[0] + " YOKCKayit is not started.");

			getInstance().start(appPropertiesFileName);

		} catch (Exception e) {
			logger.error(e, e);
			logger.fatal(e);
		}

	}


	/**
	 * Static method called by prunsrv to start/stop the service. Pass the argument "start" to start the service, and pass "stop" to stop the service.
	 */
	public static void stopService(String args[]) {
		getInstance().stopService();
	}

	/**
	 * Stop this service instance
	 */
	public void stopService() {
		serviceStopped = true;
		synchronized (this) {
			this.notify();
		}
	}

	public boolean isRunImmediately() {
		return runImmediately;
	}

	public void setRunImmediately(boolean runImmediately) {
		this.runImmediately = runImmediately;
	}

}
