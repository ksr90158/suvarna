package com.dts.ebuy.model;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class MailMain {
public static void main(String args[])
{
	try {
		SampleMail.Send("xxx@gmai.com", "password", "xxxxx@gmail.com", "xxxxx@memphis.edu", "Matter", "Matter");
	} catch (AddressException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		System.out.println("Unable to send");
		e.printStackTrace();
	}
}
}