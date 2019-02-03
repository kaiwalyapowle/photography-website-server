package com.saeebhurke.SaeeBhurke.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.saeebhurke.SaeeBhurke.models.EmailMessage;

public interface EmailService {

	void sendmail(EmailMessage emailMessage) throws AddressException, MessagingException, IOException;

}
