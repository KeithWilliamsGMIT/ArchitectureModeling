// Keith Williams
// 16/10/2015
// ErrorLog - Write any errors to a file

package files;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorLog {
	// Variables ********************************
	static String filePath = "error-log.txt";
	
	// Methods **********************************
	// Append an error message to the error log
	public static void witeError (String errorMessage) throws IOException {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		// Concatenate all the information to create error message
		String message = dateFormat.format(date) + ": " + errorMessage +
				" (Class:" + stackTraceElements[stackTraceElements.length - 2].getClassName() +
				" Method:" + stackTraceElements[stackTraceElements.length - 2].getMethodName() +
				" Line:" + stackTraceElements[stackTraceElements.length - 2].getLineNumber() + ")";
		
		PrintWriter out = new PrintWriter(new FileWriter("myfile.txt", true));
	    out.println(message);
		out.close();
	}
}
