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

    public void logException(Throwable t) {
	logException(t, t.getLocalizedMessage());
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
	    new ConsoleLoggerOutput().log(title, message);
	} else
	    for (LoggerOutput o : outputStreams) {
		if (message != null && !message.equals("")
			&& (stacktrace == null || stacktrace.equals(""))) {
		    o.log(title, message);
		}
		if (stacktrace != null && !stacktrace.equals("")) {
		    o.logStacktrace(title, message, stacktrace);
		}
	    }
    }

    public void logException(Throwable t, String message) {
	if (loglevel.ordinal() >= this.ordinal()) {
	    push(message, outputTitle, makeStacktraceReadable(t));
	}
    }

    public void logMessage(String message) {
	if (this.ordinal() >= loglevel.ordinal()) {
	    push(message, outputTitle);
	}
    }

    private String makeStacktraceReadable(Throwable t) {
	StringBuilder res = new StringBuilder();

	StackTraceElement[] elems = t.getStackTrace();
	if (elems.length > 0)
	    res.append(String.format("Line: %7d\t in %s.%s()%n",
		    elems[0].getLineNumber(), elems[0].getClassName(),
		    elems[0].getMethodName()));

	for (int i = 1; i < elems.length; i++) {
	    res.append(String.format("\tLine: %7d\t in %s.%s()%n",
		    elems[i].getLineNumber(), elems[i].getClassName(),
		    elems[i].getMethodName()));
	}

	return res.toString();
    }

    public void addLoggerOutput(LoggerOutput output) {
	outputStreams.add(output);
    }
}