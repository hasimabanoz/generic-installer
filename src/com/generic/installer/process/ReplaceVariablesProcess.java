package com.generic.installer.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.generic.installer.common.InstallerConstants;
import com.generic.installer.common.InstallerProperties;
import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.util.AbstractUIProcessHandler;
import com.izforge.izpack.util.OsVersion;

public class ReplaceVariablesProcess extends AbstractProcess implements InstallerConstants {

	@Override
	public void run(AbstractUIProcessHandler handler, String[] args) {
		super.setHandler(handler);
		startProcessLog();
		try {

			File rootFolder = new File(AutomatedInstallData.getInstance().getInstallPath());

			removePlatfomSpecificFiles(rootFolder);

			List<File> fileList = new ArrayList<File>();
			getFileList(rootFolder, fileList);
			for (File file : fileList) {
				// Read File
				BufferedReader br = new BufferedReader(new FileReader(file));
				LinkedList<String> lines = new LinkedList<String>();
				String str;

				String installPath = AutomatedInstallData.getInstance().getInstallPath();

				if (file.getName().endsWith(".properties") || file.getName().endsWith(".xml")) {
					installPath = installPath.replace("\\", "\\\\");
				}

				String installUrl = installPath.replace("\\\\", "/");

				boolean isBundledJreDetected = Boolean.valueOf(InstallerProperties.bundled_jre_detected.getValue());

				while ((str = br.readLine()) != null) {
					str = str.replace("#APP_PATH#", installPath);
					// URL style path is only for Derby connection URLs.
					str = str.replace("#APP_URL#", installUrl);
					// str = str.replace("#JAVA_PATH#", isBundledJreDetected ? JAVA_PATH_BUNDLED : JAVA_PATH_EXTERNAL);
					// str = str.replace("#JAVA_SERVICE_PATH#", isBundledJreDetected ? JAVA_SRV_PATH_BND : JAVA_SRV_PATH_EXT);
					str = str.replace("#TIMEZONE#", Calendar.getInstance().getTimeZone().getID());
					lines.add(str);
				}
				br.close();

				// Write file
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				ListIterator<String> iter = lines.listIterator();
				while (iter.hasNext()) {
					String line = iter.next();
					bw.write(line);
					bw.newLine();
				}
				bw.flush();
				bw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finishProcessLog();
	}

	private void removePlatfomSpecificFiles(File rootFolder) {
		// TODO Auto-generated method stub

	}

	private void getFileList(File rootFolder, List<File> fileList) {
		if (rootFolder.exists() && rootFolder.isDirectory()) {
			File[] files = rootFolder.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					getFileList(file, fileList);
				} else {

					// Kurulum yapilan platform icin gereksiz bir dosya ise silelim
					if (OsVersion.IS_WINDOWS) {
						if (file.getName().endsWith(".sh")) {
							file.delete();
							continue;
						}
					} else {
						// Windows degil ise exe ve bat dosyalarini temizleyelim
						if (file.getName().endsWith(".exe") || file.getName().endsWith(".bat")) {
							file.delete();
							continue;
						}
					}

					// Install path'e gore degistirilmesi gerebilecek dosyalari tesbit edelim
					if (file.getName().endsWith(".sh") || file.getName().endsWith(".bat") || file.getName().endsWith(".properties")
							|| file.getName().endsWith(".xml") || file.getName().endsWith(".mapping") || file.getName().endsWith(".js")
							|| file.getName().endsWith(".xsl")) {
						fileList.add(file);
					}

				}
			}
		}
	}

}
