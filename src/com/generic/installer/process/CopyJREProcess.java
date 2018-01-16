package com.generic.installer.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.generic.installer.common.InstallerConstants;
import com.generic.installer.common.InstallerProperties;
import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.util.AbstractUIProcessHandler;

public class CopyJREProcess extends AbstractProcess implements InstallerConstants {

	@Override
	public void run(AbstractUIProcessHandler handler, String[] args) {
		super.setHandler(handler);
		startProcessLog();
		try {
			infoLog("JRE Configuration Process is started");
			File srcPath = new File("jre");
			if (srcPath.exists()) {
				infoLog("Bundled JRE found!");
				// Set bundled_jre_detected variable to true to check at a later time (ReplaceBatchFiles)
				InstallerProperties.bundled_jre_detected.setValue(Boolean.TRUE.toString());
				File dstPath = new File(AutomatedInstallData.getInstance().getInstallPath() + File.separator + "jre");
				infoLog("Copying bundled JRE into install path...");
				copyDirectory(srcPath, dstPath);
				infoLog("Copy operation is finished...");
			} else
				infoLog("Bundled JRE not found! Using current JRE on the system");
		} catch (Exception e) {
			errorLog();
		}
		finishProcessLog();
	}

	public void copyDirectory(File srcPath, File dstPath) throws IOException {
		if (srcPath.isDirectory()) {
			if (!dstPath.exists()) {
				dstPath.mkdir();
			}

			String files[] = srcPath.list();
			for (int i = 0; i < files.length; i++) {
				copyDirectory(new File(srcPath, files[i]), new File(dstPath, files[i]));
			}
		} else {
			if (!srcPath.exists()) {
				System.out.println("File or directory does not exist.");
				System.exit(0);
			} else {
				InputStream in = new FileInputStream(srcPath);
				OutputStream out = new FileOutputStream(dstPath);

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}
		System.out.println("Directory copied.");
	}
}
