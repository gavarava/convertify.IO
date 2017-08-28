package com.html.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.common.io.CharStreams;

public class HTMLTemplate implements Template {

	private static String DEFAULT_ENCODING = "UTF-8";

	private File htmlTemplate;

	public HTMLTemplate(File htmlTemplate) {
		this.htmlTemplate = htmlTemplate;
	}

	public File getHTMLTemplate() {
		return this.htmlTemplate;
	}

	public String getName() { return this.htmlTemplate.getName(); }

	public boolean isValid() {
		return (this.htmlTemplate != null && this.htmlTemplate.exists() && !this.htmlTemplate
				.isDirectory());
	}

	/**
	 * Returns the complete template File as a String
	 * @return
	 */
	@Override
	public String toString() {
		if (isValid()) {
			try {
				FileInputStream fis = new FileInputStream(getHTMLTemplate());
				return CharStreams.toString(new InputStreamReader(fis,
						DEFAULT_ENCODING));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
