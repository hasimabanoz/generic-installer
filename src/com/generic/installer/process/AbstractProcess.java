package com.generic.installer.process;

import com.izforge.izpack.util.AbstractUIProcessHandler;

public abstract class AbstractProcess {

	private AbstractUIProcessHandler handler = null;
	private String processName = "";

	public abstract void run(AbstractUIProcessHandler handler, String[] args);

	public AbstractProcess() {
		this.processName = getClass().getSimpleName();
	}

	public void setHandler(AbstractUIProcessHandler handler) {
		this.handler = handler;
	}

	public void infoLog(String log) {
		if (handler != null)
			handler.logOutput(">>>> " + log, false);
	}

	public void errorLog() {
		if (handler != null)
			handler.logOutput(">>>> Error occured while executing process '" + processName + "'. Please see log file.", true);
	}

	public void startProcessLog() {
		if (handler != null)
			handler.logOutput("* start process '" + processName + "'", false);
	}

	public void finishProcessLog() {
		if (handler != null) {
			handler.logOutput("* finish process '" + processName + "'", false);
			handler.logOutput("-------------", false);
		}
	}

}
