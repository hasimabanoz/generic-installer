package com.generic.installer.listener;

import com.generic.installer.common.InstallerConstants;
import com.generic.installer.common.InstallerProperties;
import com.generic.installer.common.Log4JHelper;
import com.generic.installer.process.WindowsServiceProcess;
import com.izforge.izpack.event.SimpleInstallerListener;
import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.util.AbstractUIProgressHandler;
import com.izforge.izpack.util.OsVersion;

public class InstallerListener extends SimpleInstallerListener implements InstallerConstants {

	@Override
	public void afterPacks(AutomatedInstallData idata, AbstractUIProgressHandler handler) throws Exception {
		super.afterPacks(idata, handler);

		InstallerProperties.access();
	}

	@Override
	public void beforePacks(AutomatedInstallData idata, Integer npacks, AbstractUIProgressHandler handler) throws Exception {
		super.beforePacks(idata, npacks, handler);

		Log4JHelper.loadLog4jProperties();

		if (OsVersion.IS_WINDOWS) {
			// Eger windows ise ve onceki kuruluma ait servis var ise uninstall edelim
			new WindowsServiceProcess().run(null, new String[] { STOP_SERVICE, UNINSTALL_SERVICE });
		}
	}
}
