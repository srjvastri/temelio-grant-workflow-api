package com.nonprofit.temelio.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nonprofit {

  @NotBlank(message = "Name is required")
  @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
  private String name;

  @NotBlank(message = "Address is required")
  private String address;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;
}
