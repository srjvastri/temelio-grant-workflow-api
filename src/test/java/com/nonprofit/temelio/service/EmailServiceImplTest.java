package com.nonprofit.temelio.service;

import com.nonprofit.temelio.dto.SendEmailRequest;
import com.nonprofit.temelio.model.EmailLog;
import com.nonprofit.temelio.model.Nonprofit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailServiceImplTest {

  private EmailServiceImpl emailService;

  @Mock
  private NonprofitService nonprofitService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    emailService = new EmailServiceImpl(nonprofitService);
  }

  @Test
  public void testSendEmails_AllValid() {
    // Arrange
    String email1 = "contact@nonprofit1.org";
    String email2 = "info@nonprofit2.org";
    Nonprofit nonprofit1 = Nonprofit.builder()
        .name("Nonprofit One")
        .address("Address One")
        .email(email1)
        .build();
    Nonprofit nonprofit2 = Nonprofit.builder()
        .name("Nonprofit Two")
        .address("Address Two")
        .email(email2)
        .build();

    when(nonprofitService.getNonprofitByEmail(email1)).thenReturn(nonprofit1);
    when(nonprofitService.getNonprofitByEmail(email2)).thenReturn(nonprofit2);

    String template = "Sending money to nonprofit {name} at address {address} on {date}.";
    SendEmailRequest request = SendEmailRequest.builder()
        .emails(List.of(email1, email2))
        .template(template)
        .build();

    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    // Act
    List<Map<String, Object>> responses = emailService.sendEmails(request);

    // Assert
    assertEquals(2, responses.size());

    Map<String, Object> response1 = responses.get(0);
    assertEquals(email1, response1.get("nonprofit_email"));
    String expectedMessage1 = template.replace("{name}", nonprofit1.getName())
        .replace("{address}", nonprofit1.getAddress())
        .replace("{date}", currentDate);
    assertEquals(expectedMessage1, response1.get("message"));
    assertEquals(currentDate, response1.get("sent_at"));

    Map<String, Object> response2 = responses.get(1);
    assertEquals(email2, response2.get("nonprofit_email"));
    String expectedMessage2 = template.replace("{name}", nonprofit2.getName())
        .replace("{address}", nonprofit2.getAddress())
        .replace("{date}", currentDate);
    assertEquals(expectedMessage2, response2.get("message"));
    assertEquals(currentDate, response2.get("sent_at"));
  }

  @Test
  public void testSendEmails_SomeInvalid() {
    // Arrange
    String validEmail = "contact@nonprofit.org";
    String invalidEmail = "invalid@nonprofit.org";
    Nonprofit nonprofit = Nonprofit.builder()
        .name("Valid Nonprofit")
        .address("Valid Address")
        .email(validEmail)
        .build();

    when(nonprofitService.getNonprofitByEmail(validEmail)).thenReturn(nonprofit);
    when(nonprofitService.getNonprofitByEmail(invalidEmail)).thenReturn(null);

    String template = "Sending money to nonprofit {name} at address {address} on {date}.";
    SendEmailRequest request = SendEmailRequest.builder()
        .emails(List.of(validEmail, invalidEmail))
        .template(template)
        .build();

    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    // Act
    List<Map<String, Object>> responses = emailService.sendEmails(request);

    // Assert
    assertEquals(2, responses.size());

    // Check valid email response
    Map<String, Object> validResponse = responses.get(0);
    assertEquals(validEmail, validResponse.get("nonprofit_email"));
    String expectedMessage = template.replace("{name}", nonprofit.getName())
        .replace("{address}", nonprofit.getAddress())
        .replace("{date}", currentDate);
    assertEquals(expectedMessage, validResponse.get("message"));

    // Check invalid email response
    Map<String, Object> invalidResponse = responses.get(1);
    assertEquals(invalidEmail, invalidResponse.get("nonprofit_email"));
    assertEquals("Nonprofit not found.", invalidResponse.get("error"));
  }

  @Test
  public void testGetAllEmails() {
    // Arrange: send an email so that a log entry is added.
    String email = "contact@nonprofit.org";
    Nonprofit nonprofit = Nonprofit.builder()
        .name("Nonprofit Name")
        .address("Nonprofit Address")
        .email(email)
        .build();
    when(nonprofitService.getNonprofitByEmail(email)).thenReturn(nonprofit);

    String template = "Sending money to nonprofit {name} at address {address} on {date}.";
    SendEmailRequest request = SendEmailRequest.builder()
        .emails(List.of(email))
        .template(template)
        .build();

    emailService.sendEmails(request);

    // Act
    List<EmailLog> logs = emailService.getAllEmails();

    // Assert
    assertEquals(1, logs.size());
    EmailLog log = logs.get(0);
    assertEquals(email, log.getNonprofitEmail());
    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String expectedMessage = template.replace("{name}", nonprofit.getName())
        .replace("{address}", nonprofit.getAddress())
        .replace("{date}", currentDate);
    assertEquals(expectedMessage, log.getMessage());
    assertEquals(currentDate, log.getSentAt());
  }
}
