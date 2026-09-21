package aji.intern.core.service.impl;

import aji.intern.core.rest.dto.email.Email;
import aji.intern.core.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LogManager.getLogger(EmailServiceImpl.class);

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmail(String subject, Email email) {
        try {
            MimeMessage mime = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mime, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

            Context context = new Context();
            context.setVariable("name", email.getCustomerName());
            context.setVariable("email", email.getEmail());
            context.setVariable("otpCode", email.getOtp());
            context.setVariable("actionContext", email.getOperation());
            context.setVariable("expiryMinutes", email.getExpiry());

            String html = templateEngine.process("otp-email-template", context);

            helper.setTo(email.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom("dev.example.bank@gmail.com");

            javaMailSender.send(mime);
        } catch (MessagingException e) {
            log.error("Failed to send email");
            throw new RuntimeException(e);
        }
    }
}
