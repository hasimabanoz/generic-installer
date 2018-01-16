package com.generic.installer.ant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

import com.generic.installer.common.InstallerConstants;

public class CreateJsmoothFiles implements Runnable, InstallerConstants {

	private String installerName = "";
	private String dir = "";

	public static void main(String[] args) {
		String installerName = args[0];
		String dir = args[1];

		new Thread(new CreateJsmoothFiles(installerName, dir)).run();
	}

	public CreateJsmoothFiles(String installerName, String dir) {
		this.installerName = installerName;
		this.dir = dir;
	}

	@Override
	public void run() {
		try {
			createFileInstallerJsmooth();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createFileInstallerJsmooth() throws IOException {
		String jsmoothPath = dir + SEPARATOR + "jsmooth";

		File defaultFile = new File(jsmoothPath + SEPARATOR + INSTALLER_JSMOOTH + DEFAULT_APPENDIX);
		File file = new File(jsmoothPath + SEPARATOR + INSTALLER_JSMOOTH);

		if (!file.exists()) {
			file.createNewFile();
		}

		// Read File
		BufferedReader br = new BufferedReader(new FileReader(defaultFile));
		LinkedList<String> lines = new LinkedList<String>();
		String str;
		while ((str = br.readLine()) != null) {
			str = str.replace("#installer.name#", installerName);

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

}
