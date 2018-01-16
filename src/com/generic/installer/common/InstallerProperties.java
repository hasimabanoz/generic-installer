package com.generic.installer.common;

import java.io.File;

import com.izforge.izpack.installer.AutomatedInstallData;

public enum InstallerProperties {
	install_service("install.service", "true"),
	// Bundled JRE variable must be shared between processes. Default value: false= not detected
	bundled_jre_detected("bundled.jre.detected", Boolean.FALSE.toString());
	;

	private String key;

	private InstallerProperties(String key, String value) {
		setKey(key);
		setValue(value);
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		AutomatedInstallData data = AutomatedInstallData.getInstance();
		String value = data.getVariable(key);
		if (value != null && value.trim().length() > 0) {
			return value;
		} else {
			return "";
		}
	}

	public void setValue(String value) {
		AutomatedInstallData data = AutomatedInstallData.getInstance();
		if (value != null && value.trim().length() > 0) {
			data.setVariable(key, value);
		} else {
			data.setVariable(key, "");
		}
	}

	public static void access() {
		AutomatedInstallData data = AutomatedInstallData.getInstance();
		data.setVariable("UNINSTALL_PATH", data.getInstallPath() + File.separator + "Uninstaller");
	}

}
