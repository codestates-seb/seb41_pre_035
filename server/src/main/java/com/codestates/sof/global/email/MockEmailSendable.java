package com.codestates.sof.global.email;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockEmailSendable implements EmailSendable {

	@Override
	public void send(String[] to, String subject,  Map<String, Object> variables, String templateName) {
		for (String t : to) {
			log.info("# Send a email to : " + t);
		}
	}
}