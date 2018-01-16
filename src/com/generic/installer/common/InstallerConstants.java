package com.generic.installer.common;

import java.io.File;

public interface InstallerConstants {

	public static final String SEPARATOR = File.separator;
	public static final String DEFAULT_APPENDIX = ".default";
	public static final String CLASS_APPENDIX = ".class";
	public static final String EQUAL = "=";
	public static final String QUOTES = "\"";
	public static final String SLASH = "\\";
	public static final String TAB = "\t";

	public static final String START_SERVICE = "start";
	public static final String STOP_SERVICE = "stop";
	public static final String INSTALL_SERVICE = "install";
	public static final String UNINSTALL_SERVICE = "uninstall";

	public static final String LOG4J_XML = "log4j.xml";
	public static final String INSTALLER_JSMOOTH = "installer.jsmooth";
}
