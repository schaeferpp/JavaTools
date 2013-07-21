package de.schaefer.tools.log.loggeroutput;

public interface LoggerOutput {
    public void log(String title, String message);

    public void logStacktrace(String title, String message, String stacktrace);
}
