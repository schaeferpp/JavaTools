package de.schaefer.tools.log.loggeroutput;

import javax.swing.JOptionPane;


public class JOptionPaneErrorLoggerOutput implements LoggerOutput {

    @Override
    public void log(String title, String message) {
	JOptionPane.showMessageDialog(null, message, title,
		JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void logStacktrace(String title, String message, String stacktrace) {
	JOptionPane.showMessageDialog(
		null,
		message + "\n"
			+ stacktrace.substring(0, stacktrace.indexOf("\n")),
		title, JOptionPane.ERROR_MESSAGE);
    }

}
