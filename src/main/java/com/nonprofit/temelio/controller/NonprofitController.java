package com.nonprofit.temelio.controller;

import com.nonprofit.temelio.model.Nonprofit;
import com.nonprofit.temelio.service.NonprofitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nonprofits")
public class NonprofitController {

  private final NonprofitService nonprofitService;

  public NonprofitController(NonprofitService nonprofitService) {
    this.nonprofitService = nonprofitService;
  }

  //---------------Mandatory API that were asked in the task--------------------------------------//
  // As asked in the task "Allows someone to create nonprofits and their metadata (name, address, and email)"
  @PostMapping
  public ResponseEntity<?> createNonprofit(@RequestBody @Valid Nonprofit nonprofit) {
    nonprofitService.createNonprofit(nonprofit);
    return new ResponseEntity<>(Map.of("message", "Nonprofit created successfully."), HttpStatus.CREATED);
  }



  //------------- Supporting API that were not asked in the task----------------------------------//
  // I have added these supporting API as I think they are useful for the task and its workflow

  // Get all nonprofits
  @GetMapping
  public ResponseEntity<List<Nonprofit>> getAllNonprofits() {
    return ResponseEntity.ok(nonprofitService.getAllNonprofits());
  }

  // Get a specific nonprofit by email
  @GetMapping("/{email}")
  public ResponseEntity<?> getNonprofit(@PathVariable String email) {
    Nonprofit nonprofit = nonprofitService.getNonprofitByEmail(email);
    if (nonprofit == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of("error", "Nonprofit not found."));
    }
    return ResponseEntity.ok(nonprofit);
  }

  // Update a nonprofit (next important thing to implement )


  // Delete a nonprofit (next important thing to implement )
}
