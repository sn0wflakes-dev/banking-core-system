package aji.intern.core.service;

import aji.intern.core.rest.dto.email.Email;

public interface EmailService {
    void sendEmail(String subject, Email email);
}
