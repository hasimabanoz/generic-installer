package com.generic.launcher;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.generic.Application;
import com.generic.util.PropertiesUtils;

public class GenericServiceLauncher {
	static Logger log = Logger.getLogger(GenericServiceLauncher.class);

	public static Application application = new Application();
	private static GenericServiceLauncher instance = null;
	private final Properties appProps = new Properties();


	private boolean serviceStopped = true;

	public static GenericServiceLauncher getInstance() {
		if (instance == null) {
			instance = new GenericServiceLauncher();
		}
		return instance;
	}

	/**
	 * Generic Servisin calismaya basladig yer
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Add shutdown hook for gracefully shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				getInstance().stopService();
			}
		});
		startService(args);
	}

	/**
	 * Property dosyasindan ayarlarin sisteme yukelendigi metod
	 * 
	 * @param propertiesFileName
	 *            property dosya adi
	 * @return
	 */
	private boolean initialize(String propertiesFileName) {
		try {
			log.info("Initializing....");

			if (PropertiesUtils.loadProperties(propertiesFileName, appProps)) {
				//cfg.echoTimeout = Integer.parseInt(appProps.getProperty("cfg.echo.timeout", "5"));
			} else {
				return false;
			}

		} catch (final Exception e) {
			log.fatal(e, e);
			return false;
		}
		return true;
	}

	/**
	 * Servis baslamadan once konfigurasyonlarin ayarlandigi metod
	 * 
	 * @param args
	 */
	public static void startService(final String args[]) {

		// Set application properties file , if it's not specified application
		// try to find and use "RenameIt.properties" as default
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext(StockConstants.appContextPath);

		String appPropertiesFileName = "RenameIt.properties";

		if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
			// Properties file specified, we use this parameter
			appPropertiesFileName = args[0];
		}

		// Configure log4j
		if (args.length > 1 && StringUtils.isNotBlank(args[1])) {
			DOMConfigurator.configure(args[1]);
		}

		final StringBuilder logString = new StringBuilder();
		logString.append("\n\n\n\n");
		logString.append("\n\t\t******************************************************************************************");
		logString.append("\n\t\t***** " + application.getFullDescription() + " Starting....");
		logString.append("\n\t\t******************************************************************************************");
		logString.append("\n\n");
		log.info(logString.toString());

		getInstance().start(appPropertiesFileName);

	}

	private void start(final String appPropertiesFileName) {
		serviceStopped = false;

		if (getInstance().initialize(appPropertiesFileName)) {
			try {

				powerOn();
				while (!serviceStopped) {
					synchronized (this) {
						printMemoryUsage();
						// 10DK bekle. notify gelince devam ediyor.
						this.wait(10 * 60 * 1000);
					}
				}
				powerOff();

			} catch (final Exception e) {
				log.fatal(e, e);
			}
		} else {
			log.fatal("***** " + application.getFullDescription() + " couldn't initialized....");
		}

	}

	
	/**
	 * 
	 * @throws Exception
	 */
	public void powerOn() throws Exception {
		serviceStopped = false;
		// start service threads

	}

	public void powerOff() throws Exception {

		log.fatal("GenericService is shutting down...");
		printMemoryUsage();
		serviceStopped = true;
		//Stop service threads
		// Waiting 3 sec for sending fatal email
		Thread.sleep(3000);
	}

	public static void stopService(String[] args) {
		getInstance().stopService();
	}

	/**
	 * Stop this service instance
	 */
	public void stopService() {
		serviceStopped = true;
		synchronized (this) {
			notify();
		}
	}

	/**
	 * Print memory usage to log file.
	 */
	private void printMemoryUsage() {
		if (log.isDebugEnabled()) {
			final long max = Runtime.getRuntime().maxMemory() / 1024;
			final long total = Runtime.getRuntime().totalMemory() / 1024;
			final long free = Runtime.getRuntime().freeMemory() / 1024;
			final long used = total - free;

			// Build log string
			final StringBuilder logString = new StringBuilder("\n\tMax Memory : ").append(String.valueOf(max)).append(" kb.");
			logString.append("\n\tTotal Memory : ").append(String.valueOf(total)).append(" kb.");
			logString.append("\n\tUsed Memory : ").append(String.valueOf(used)).append(" kb.");
			logString.append("\n\tFree Memory : ").append(String.valueOf(free)).append(" kb.");

			log.debug(logString.toString());
		}
	}

}
