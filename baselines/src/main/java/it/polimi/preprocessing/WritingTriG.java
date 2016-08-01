package it.polimi.preprocessing;

import java.io.FileWriter;
import java.io.IOException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WritingTriG {

	private final String EOF = System.getProperty("line.separator");
	private final FileWriter w;

	public void write(String s) throws IOException {
		w.write(s);
	}

	public void close() throws IOException {
		w.close();
	}

	public void eof() throws IOException {
		w.write(EOF);
	}

	public void flush() throws IOException {
		w.flush();
	}
}