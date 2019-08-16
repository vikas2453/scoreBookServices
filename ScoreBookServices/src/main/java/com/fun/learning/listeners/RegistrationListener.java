package com.fun.learning.listeners;

import javax.xml.bind.annotation.XmlRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.fun.learning.event.UserRegistrationEvent;
import com.fun.learning.model.User;
import com.fun.learning.service.VerficationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RegistrationListener implements ApplicationListener<UserRegistrationEvent>{
	
	private final JavaMailSender mailSender = new JavaMailSenderImpl();
		
	private final VerficationService verficationService;

	@Override
	public void onApplicationEvent(UserRegistrationEvent event) {
		User user=event.getUser();
		String userName=user.getUsername();
		String verficationId=verficationService.createVerfication(userName);
		String email = user.getEmail();
		SimpleMailMessage message= new SimpleMailMessage();
		message.setSubject("AccountVerfication");
		//some Verifcation Id needs to be sent here but as of now we'r sending username. 
		message.setText("Account Activation Link: http://localhost:8200/verify/email?Id="+userName);
		message.setTo(email);
		mailSender.send(message);
	}

}
