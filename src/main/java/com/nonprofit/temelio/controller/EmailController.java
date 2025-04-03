package com.nonprofit.temelio.controller;

import com.nonprofit.temelio.dto.SendEmailRequest;
import com.nonprofit.temelio.model.EmailLog;
import com.nonprofit.temelio.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EmailController {

  private final EmailService emailService;

  public EmailController(EmailService emailService) {
    this.emailService = emailService;
  }

  //---------------Mandatory API that were asked in the task----------------------------------------//

  // Bulk send templated emails
  // As asked in the task- "Allows someone to customize an email in the API request to bulk send to a list of nonprofits
  // with a templated message (i.e. "Sending money to nonprofit { name } at address { address }")
  // and have the templated fields populated with the correct data"
  @PostMapping("/send-emails")
  public ResponseEntity<?> sendEmails(@RequestBody SendEmailRequest request) {
    return ResponseEntity.ok(emailService.sendEmails(request));
  }



  // Retrieve all email logs
  // As asked in the task- "Allows someone to retrieve all the emails that have been sent to nonprofits"
  @GetMapping("/emails")
  public ResponseEntity<List<EmailLog>> getAllEmails() {
    return ResponseEntity.ok(emailService.getAllEmails());
  }

  //----------------------------------------------------------------------------------------------//





  //------------- Supporting API that were not asked in the task----------------------------------//
  // I have added these supporting API as I think they are useful for the task and its workflow


  // Retrieve email logs for a specific nonprofit (next important thing to implement)
  // @GetMapping("/emails/{email}")

  // Resend an email for a specific nonprofit using a provided template (next important thing to implement)
  // @PostMapping("/resend-email/{email}")
}
