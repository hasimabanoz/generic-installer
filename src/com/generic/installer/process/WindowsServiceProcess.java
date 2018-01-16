package com.generic.installer.process;

import java.io.File;

import com.generic.installer.common.DosHelper;
import com.generic.installer.common.InstallerConstants;
import com.generic.installer.common.InstallerProperties;
import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.util.AbstractUIProcessHandler;
import com.izforge.izpack.util.OsVersion;

/**
 * @author erkan This class install, start/stop and unistall DTFIntegrator service
 */
public class WindowsServiceProcess extends AbstractProcess implements InstallerConstants {

	@Override
	public void run(AbstractUIProcessHandler handler, String[] args) {
		super.setHandler(handler);
		startProcessLog();
		if (OsVersion.IS_WINDOWS) {
			String binPath = AutomatedInstallData.getInstance().getInstallPath() + File.separator + "bin";

			for (String arg : args) {
				if (INSTALL_SERVICE.equals(arg) && InstallerProperties.install_service.getValue().equalsIgnoreCase("true")) {
					DosHelper.exec(binPath + File.separator + "installService.bat");
				}
				if (UNINSTALL_SERVICE.equals(arg)) {
					File f = new File(binPath + File.separator + "uninstallService.bat");
					if (f.exists()) {
						DosHelper.exec(binPath + File.separator + "uninstallService.bat");
					}
				}
			}

		} else if (OsVersion.IS_LINUX || OsVersion.IS_UNIX || OsVersion.IS_SUNOS) {

		}
		finishProcessLog();
	}
}
