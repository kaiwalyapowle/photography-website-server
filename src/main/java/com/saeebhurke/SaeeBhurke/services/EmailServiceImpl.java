package com.saeebhurke.SaeeBhurke.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.saeebhurke.SaeeBhurke.models.EmailMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Value("${spring.mail.username}")
	private String from;

	@Value("${mail.to}")
	private String to;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendmail(EmailMessage emailMessage) throws AddressException, MessagingException, IOException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(emailMessage.getSubject());
		helper.setText("<html><body>" + emailMessage.getBody() + "</html></body>", true);

		// attachments feature not yet introduced
		/*
		 * add the file attachment
		 * 
		 * File mf1 = new File("D:\\Saee Bhurke website\\1800_tiger.jpg"); File mf2 =
		 * new File("D:\\Saee Bhurke website\\street2.jpg"); File[] attachments = { mf1,
		 * mf2 };
		 * 
		 * for (File file : attachments) { FileSystemResource fr = new
		 * FileSystemResource((File) file); helper.addAttachment(file.getName(), fr); }
		 */
		mailSender.send(mimeMessage);

	}

}
