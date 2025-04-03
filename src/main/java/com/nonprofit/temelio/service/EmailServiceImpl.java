package com.nonprofit.temelio.service;

import com.nonprofit.temelio.dto.SendEmailRequest;
import com.nonprofit.temelio.model.EmailLog;
import com.nonprofit.temelio.model.Nonprofit;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

  private final NonprofitService nonprofitService;
  private List<EmailLog> emailLogs = new ArrayList<>();

  public EmailServiceImpl(NonprofitService nonprofitService) {
    this.nonprofitService = nonprofitService;
  }

  @Override
  public List<Map<String, Object>> sendEmails(SendEmailRequest request) {
    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    List<Map<String, Object>> responses = new ArrayList<>();

    for (String email : request.getEmails()) {
      Nonprofit nonprofit = nonprofitService.getNonprofitByEmail(email);
      if (nonprofit != null) {
        String personalizedMessage = request.getTemplate()
            .replace("{name}", nonprofit.getName())
            .replace("{address}", nonprofit.getAddress())
            .replace("{date}", currentDate);
        EmailLog log = new EmailLog(email, personalizedMessage, currentDate);
        emailLogs.add(log);
        responses.add(Map.of(
            "nonprofit_email", email,
            "message", personalizedMessage,
            "sent_at", currentDate));
      } else {
        responses.add(Map.of(
            "nonprofit_email", email,
            "error", "Nonprofit not found."));
      }
    }
    return responses;
  }

  @Override
  public List<EmailLog> getAllEmails() {
    return emailLogs;
  }
}
