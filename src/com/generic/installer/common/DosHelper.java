package com.generic.installer.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class DosHelper implements InstallerConstants {

	private static Logger logger = Logger.getLogger(DosHelper.class);

	public static void exec(String commandLine) {
		try {
			logger.debug("Executing : " + commandLine);
			Process p = Runtime.getRuntime().exec(commandLine);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null) {
				logger.info(s);
			}
			while ((s = stdError.readLine()) != null) {
				logger.error(s);
			}

			p.exitValue();
		} catch (Exception e) {
			logger.error(e, e);
			logger.fatal("Error on executing command.", e);
		}
	}

}