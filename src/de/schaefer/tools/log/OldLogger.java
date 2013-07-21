package de.schaefer.tools.log;

import java.util.LinkedList;

import de.schaefer.tools.language.LanguagePropertyReader;
import de.schaefer.tools.log.loggeroutput.LoggerOutput;

@Deprecated
public class OldLogger {
    public static final int LOG_LEVEL_DEBUG = 1000;
    public static final int LOG_LEVEL_INFO = 1010;
    public static final int LOG_LEVEL_WARN = 1020;
    public static final int LOG_LEVEL_ERROR = 1030;
    public static final int LOG_LEVEL_FATAL = 1040;

    private static OldLogger logger;

    private LinkedList<LoggerOutput> debugStreams = new LinkedList<LoggerOutput>();
    private LinkedList<LoggerOutput> infoStreams = new LinkedList<LoggerOutput>();
    private LinkedList<LoggerOutput> warnStreams = new LinkedList<LoggerOutput>();
    private LinkedList<LoggerOutput> errorStreams = new LinkedList<LoggerOutput>();
    private LinkedList<LoggerOutput> fatalStreams = new LinkedList<LoggerOutput>();

    private int currentLogLevel = LOG_LEVEL_WARN;
    private boolean logOnLowerStreams = false;

    private OldLogger() {

    }

    public static synchronized OldLogger getLogger() {
	if (logger == null) {
	    logger = new OldLogger();
	}
	return logger;
    }

    public void logMessage(String message, int loglevel) {
	String title = null;
	if (loglevel >= currentLogLevel) {
	    switch (loglevel) {
	    case LOG_LEVEL_FATAL:

		title = "Fatal error!";
		push(message, title, fatalStreams);
		if (!logOnLowerStreams)
		    break;
	    case LOG_LEVEL_ERROR:
		title = LanguagePropertyReader.get("logger_errortitle");
		if (title == null) {
		    title = "Error!";
		}
		push(message, title, errorStreams);
		if (!logOnLowerStreams)
		    break;
	    case LOG_LEVEL_WARN:
		title = LanguagePropertyReader.get("logger_warntitle");
		if (title == null) {
		    title = "Warning!";
		}
		push(message, title, warnStreams);
		if (!logOnLowerStreams)
		    break;
	    case LOG_LEVEL_INFO:
		title = LanguagePropertyReader.get("logger_infotitle");
		if (title == null) {
		    title = "Information";
		}
		push(message, title, infoStreams);
		if (!logOnLowerStreams)
		    break;
	    case LOG_LEVEL_DEBUG:
		title = LanguagePropertyReader.get("logger_debugtitle");
		if (title == null) {
		    title = "Debug";
		}
		push(message, title, debugStreams);
		break;
	    default:
		title = LanguagePropertyReader.get("logger_undefined");
		if (title == null) {
		    title = "??";
		}
		push(message, title, warnStreams);
	    }
	}
    }

    public void logException(Throwable t, String message, int loglevel) {
	String title = null;
	if (loglevel >= currentLogLevel) {
	    switch (loglevel) {
	    case LOG_LEVEL_FATAL:
		title = "Fatal error!";
		push(message, title, makeStacktraceReadable(t), fatalStreams);
		if (!logOnLowerStreams)
		    break;
	    case LOG_LEVEL_ERROR:
		title = LanguagePropertyReader.get("logger_errortitle");
		if (title == null) {
		    title = "Error!";
		}
		push(message, title, makeStacktraceReadable(t), errorStreams);
		if (!logOnLowerStreams)
		    break;
	    case LOG_LEVEL_WARN:
		title = LanguagePropertyReader.get("logger_warntitle");
		if (title == null) {
		    title = "Warning!";
		}
		push(message, title, makeStacktraceReadable(t), warnStreams);
		if (!logOnLowerStreams)
		    break;
	    case LOG_LEVEL_INFO:
		title = LanguagePropertyReader.get("logger_infotitle");
		if (title == null) {
		    title = "Information";
		}
		push(message, title, makeStacktraceReadable(t), infoStreams);
		if (!logOnLowerStreams)
		    break;
	    case LOG_LEVEL_DEBUG:
		title = LanguagePropertyReader.get("logger_debugtitle");
		if (title == null) {
		    title = "Debug";
		}
		push(message, title, makeStacktraceReadable(t), debugStreams);
		break;
	    default:
		title = LanguagePropertyReader.get("logger_undefined");
		if (title == null) {
		    title = "??";
		}
		push(message, title, makeStacktraceReadable(t), infoStreams);
	    }
	}
    }

    public void logException(Throwable t, int loglevel) {
	logException(t, t.getLocalizedMessage(), loglevel);
    }

    private void push(String message, String title,
	    LinkedList<LoggerOutput> streams) {
	for (LoggerOutput o : streams) {
	    o.log(title, message);
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

    private void push(String message, String title, String stacktrace,
	    LinkedList<LoggerOutput> streams) {
	for (LoggerOutput o : streams) {
	    if (message != null && !message.equals("")
		    && (stacktrace == null || stacktrace.equals(""))) {
		o.log(title, message);
	    }
	    if (stacktrace != null && !stacktrace.equals("")) {
		o.logStacktrace(title, message, stacktrace);
	    }
	}
    }

    public void addLoggerOutput(int loglevel, LoggerOutput output) {
	switch (loglevel) {
	case LOG_LEVEL_DEBUG:
	    debugStreams.add(output);
	    break;
	case LOG_LEVEL_INFO:
	    infoStreams.add(output);
	    break;
	case LOG_LEVEL_WARN:
	    warnStreams.add(output);
	    break;
	case LOG_LEVEL_ERROR:
	    errorStreams.add(output);
	    break;
	case LOG_LEVEL_FATAL:
	    fatalStreams.add(output);
	    break;
	}
    }

    public void setLogLevel(int loglevel) {
	this.currentLogLevel = loglevel;
    }
}
