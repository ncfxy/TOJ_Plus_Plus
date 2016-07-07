package cn.tjuscs.oj.whc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.logging.Level;

/**
 * 用于处理Runtime.getRuntime().exec产生的错误流及输出流
 */
public class StreamGobbler extends Thread {
	InputStream _inputStream;
	String _type;
	OutputStream _outputStream;
	StringBuilder _results;

	StreamGobbler(InputStream is, String type, StringBuilder results) {
		this(is, type, results, null);
	}

	StreamGobbler(InputStream is, String type, StringBuilder results,
			OutputStream redirect) {
		this._inputStream = is;
		this._type = type;
		this._outputStream = redirect;
		this._results = results;
	}

	
	/**
	 * override from thread
	 */
	public void run() {
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferReader = null;
		PrintWriter printWriter = null;
		try {
			if (_outputStream != null) {
				printWriter = new PrintWriter(_outputStream);
			}

			inputStreamReader = new InputStreamReader(_inputStream, Charset.forName("GBK"));
			bufferReader = new BufferedReader(inputStreamReader);
			readFromInputStream(bufferReader, printWriter);
		} catch (IOException e) {
			ExecuteWindowsCommand.getLogger().log(Level.SEVERE, "error", e);
		} finally {
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				bufferReader.close();
				inputStreamReader.close();
			} catch (IOException e) {
				ExecuteWindowsCommand.getLogger().log(Level.SEVERE, "error", e);
			}
		}
	}

	private void readFromInputStream(BufferedReader bufferReader,
			PrintWriter printWriter) throws IOException {
		String line = null;
		while ((line = bufferReader.readLine()) != null) {
			if (printWriter != null) {
				printWriter.println(line);
			}
			ExecuteWindowsCommand.getLogger().log(Level.INFO, _type + ">" + line);
			_results.append(line + "\n");
		}

		if (printWriter != null) {
			printWriter.flush();
		}
	}
}