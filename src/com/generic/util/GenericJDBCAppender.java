package com.generic.util;

public class GenericJDBCAppender extends org.apache.log4j.jdbc.JDBCAppender{

	@Override
	public void setPassword(String password) {
		String decryptedPassword = "";
		if (password.startsWith("*") && password.endsWith("*")) {
			try {
				decryptedPassword = Protector.decrypt(password.replace("*", ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			decryptedPassword = password;
		}
		super.setPassword(decryptedPassword);
	}
}
