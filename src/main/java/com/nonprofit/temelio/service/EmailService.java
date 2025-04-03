package com.nonprofit.temelio.service;

import com.nonprofit.temelio.dto.SendEmailRequest;
import com.nonprofit.temelio.model.EmailLog;
import java.util.List;
import java.util.Map;

public interface EmailService {
  List<Map<String, Object>> sendEmails(SendEmailRequest request);
  List<EmailLog> getAllEmails();
}
