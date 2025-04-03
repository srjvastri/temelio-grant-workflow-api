package com.nonprofit.temelio.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailLog {
  @NotBlank(message = "Nonprofit email is required")
  @Email(message = "Nonprofit email should be valid")
  private String nonprofitEmail;

  @NotBlank(message = "Message is required")
  private String message;

  @NotBlank(message = "Sent date is required")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Sent date must be in the format yyyy-MM-dd")
  private String sentAt;
}
