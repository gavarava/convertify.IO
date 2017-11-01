package com.convertify.templates;

import java.io.*;

import com.convertify.templates.exceptions.HTMLTemplateInvalidException;
import com.google.common.io.CharStreams;

public class HTMLTemplate implements Template {

	private static String DEFAULT_ENCODING = "UTF-8";

	private File file;

	public HTMLTemplate(File file) throws HTMLTemplateInvalidException {
		this.file = file;
		if (isNotValid()) {
			throw new HTMLTemplateInvalidException();
		}
	}

	public String name() {
		return this.file.getName();
	}

	private boolean isNotValid() {
		return !fileIsValid();
	}

	private boolean fileIsValid() {
		return file != null && file.exists() && !file.isDirectory();
	}

	@Override public String getName() {
		return file.getName();
	}

	@Override public String getPath() {
		return file.getAbsolutePath();
	}

	@Override public String toString() {
		try {
			FileInputStream fis = new FileInputStream(file);
			return CharStreams.toString(new InputStreamReader(fis, DEFAULT_ENCODING));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
