package com.nonprofit.temelio.dataStore;

import com.nonprofit.temelio.model.Nonprofit;
import java.util.List;

public interface NonProfitDataStore {

  // Save a nonprofit
  void saveNonprofit(Nonprofit nonprofit);

  // Get a nonprofit by its email
  Nonprofit getNonprofit(String email);

  // Get all nonprofits
  List<Nonprofit> getAllNonprofits();

  // Update a nonprofit
  void updateNonprofit(String email, Nonprofit nonprofit);

  // Delete a nonprofit
  void deleteNonprofit(String email);
}
