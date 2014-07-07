package de.schaefer.tools.log;

import java.util.LinkedList;

import de.schaefer.tools.log.loggeroutput.ConsoleLoggerOutput;
import de.schaefer.tools.log.loggeroutput.LoggerOutput;

public enum Logger {
	DEBUG("Debug"), INFO("Information"), WARN("Warnung"), ERROR("Error"), FATAL(
			"Schwerwiegend");

	private LinkedList<LoggerOutput> outputStreams = new LinkedList<LoggerOutput>();

	private static Logger loglevel = DEBUG;

	private String outputTitle;

	private Logger(String title) {
		this.outputTitle = title;
	}

	public static void setLogLevel(Logger logger) {
		loglevel = logger;
	}

	private void push(String message, String title) {
		if (outputStreams.size() == 0) {
			new ConsoleLoggerOutput().log(title, message);
		} else
			for (LoggerOutput o : outputStreams) {
				o.log(title, message);
			}
	}

	private void push(String message, String title, String stacktrace) {
		if (outputStreams.size() == 0) {
			LoggerOutput o = new ConsoleLoggerOutput();
			if (stacktrace != null && !stacktrace.equals("")) {
				o.logStacktrace(title, message, stacktrace);
			} else
				o.log(title, message);
		} else
			for (LoggerOutput o : outputStreams) {
				if (stacktrace != null && !stacktrace.equals("")) {
					o.logStacktrace(title, message, stacktrace);
				} else
					o.log(title, message);
			}
	}

	public void logException(Throwable t) {
		if (loglevel.ordinal() <= this.ordinal()) {
			logException(t, t.getLocalizedMessage());
		}
	}

	public void logException(Throwable t, String message) {
		if (loglevel.ordinal() <= this.ordinal()) {
			push(message + ": " + t.getMessage() + " (" + t.getClass().getName()
					+ ")", outputTitle,
					StackTraceFormatter.JAVA_STYLE.format(t.getStackTrace()));
		}
	}

	public void logMessage(String message) {
		if (loglevel.ordinal() <= this.ordinal()) {
			push(message, outputTitle);
		}
	}

	public void logMessage(String message, boolean strace) {
		if (loglevel.ordinal() <= this.ordinal()) {
			push(StackTraceFormatter.JAVA_STYLE.format(Thread.currentThread()
					.getStackTrace(), message), outputTitle);
		}
	}

	public void addLoggerOutput(LoggerOutput output) {
		outputStreams.add(output);
	}
}