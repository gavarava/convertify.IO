package com.convertify.template.exceptions;

public class HTMLTemplateInvalidException extends TemplateException {

	public HTMLTemplateInvalidException() {
		super("Format or Encoding of the HTML Template is Invalid");
	}
}
