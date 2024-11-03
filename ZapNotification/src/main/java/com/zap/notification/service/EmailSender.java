package com.zap.notification.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.zap.notification.ZapNotificationApplication;
import com.zap.notification.pojo.MessagePojo;

@Component
public class EmailSender {

    // Send email method
    public void sendEmail(MessagePojo pojo, String recipientEmail, String attachmentPath, String senderEmailPlaceholder) 
    {
        try {
            // Use existing method from another class to create mailSender
            JavaMailSender mailSender = new ZapNotificationApplication().mailSender(); 

            // Create MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            // Set the recipient email and subject
            helper.setTo(recipientEmail);
            helper.setSubject(pojo.getMsgtitle());

            // Prepare the email content
            String emailContent = pojo.getMsgcontent(); // Assuming this is where the template is
            helper.setText(emailContent, true); // `true` for HTML content
            
            // Use the senderEmailPlaceholder as the "from" address
            if (isValidEmail(senderEmailPlaceholder)) {
                helper.setFrom(senderEmailPlaceholder); // Set dynamic "from" address
            } else {
            	
                helper.setFrom(new InternetAddress(pojo.getClientemail(), pojo.getClientemail())); // Fallback to the client's email if the placeholder is invalid
            }
            message.setFrom(new InternetAddress(pojo.getClientemail(), pojo.getClientemail()));

            // If an attachment path is provided, add the file as an attachment
            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                FileSystemResource file = new FileSystemResource(new File(attachmentPath));
                helper.addAttachment(file.getFilename(), file);
            }

            // Send the email
            mailSender.send(message);
            System.out.println("Email sent successfully with attachment (if provided).");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send email with attachment.");
        }
    }

    // Method to validate email addresses
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }
}
