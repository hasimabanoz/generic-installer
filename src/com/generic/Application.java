package com.generic;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.lang.StringUtils;

public class Application {
	private String name = "";
	private String major = "";
	private String minor = "";
	private String patch = "";
	private String buildDate = "";
	private String buildNumber = "";

	public Application() {
		// Manifest-Version: 1.0
		// Ant-Version: Apache Ant 1.9.6
		// Created-By: 1.8.0_111-b14 (Oracle Corporation)
		// RP-Application-Name: Rate Publisher
		// RP-Major: 1
		// RP-Minor: 1
		// RP-Patch: 0
		// RP-Build-Number: 0006
		// RP-Build-Date: 25/12/2016 20:45:47

		// !!!! build.properties icindeki <application.initial> degeri ile ayni olmalidir.
		String APP_PREFIX = "SA";// Sample Application
		Enumeration resEnum = null;
		try {
			resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
			while (resEnum.hasMoreElements()) {
				try {
					URL url = (URL) resEnum.nextElement();
					InputStream is = url.openStream();
					if (is != null) {
						Manifest manifest = new Manifest(is);
						Attributes mainAttribs = manifest.getMainAttributes();
						if (StringUtils.isNotBlank(mainAttribs.getValue(APP_PREFIX + "-Application-Name"))) {
							name = mainAttribs.getValue(APP_PREFIX + "-Application-Name");
						}
						if (StringUtils.isNotBlank(mainAttribs.getValue(APP_PREFIX + "-Major"))) {
							major = mainAttribs.getValue(APP_PREFIX + "-Major");
						}
						if (StringUtils.isNotBlank(mainAttribs.getValue(APP_PREFIX + "-Minor"))) {
							minor = mainAttribs.getValue(APP_PREFIX + "-Minor");
						}
						if (StringUtils.isNotBlank(mainAttribs.getValue(APP_PREFIX + "-Patch"))) {
							patch = mainAttribs.getValue(APP_PREFIX + "-Patch");
						}
						if (StringUtils.isNotBlank(mainAttribs.getValue(APP_PREFIX + "-Build-Number"))) {
							buildNumber = mainAttribs.getValue(APP_PREFIX + "-Build-Number");
						}

						if (StringUtils.isNotBlank(mainAttribs.getValue(APP_PREFIX + "-Build-Date"))) {
							buildDate = mainAttribs.getValue(APP_PREFIX + "-Build-Date");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String getNameAndVersion() {
		return name + " v" + major;
	}

	public String getNameAndVersionWithBuildNumber() {
		StringBuilder sbVersion = new StringBuilder(name);
		sbVersion.append(" - Version : ").append(major).append(".").append(minor).append(".").append(patch).append(".").append(buildNumber);
		return sbVersion.toString();
	}

	public String getFullDescription() {
		StringBuilder sbVersion = new StringBuilder(name);
		sbVersion.append(" - Version : ").append(major).append(".").append(minor).append(".").append(patch).append(".").append(buildNumber);
		sbVersion.append(" - Build Date : ").append(buildDate);
		return sbVersion.toString();
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return major;
	}

	public String getBuildDate() {
		return buildDate;
	}

}
