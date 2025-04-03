package com.nonprofit.temelio.service;

import com.nonprofit.temelio.dataStore.NonProfitDataStore;
import com.nonprofit.temelio.model.Nonprofit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NonprofitServiceImpl implements NonprofitService {

  private final NonProfitDataStore nonprofitDataStore;

  @Autowired
  public NonprofitServiceImpl(NonProfitDataStore nonprofitDataStore) {
    this.nonprofitDataStore = nonprofitDataStore;
  }

  @Override
  public void createNonprofit(Nonprofit nonprofit) {
    // Check if this nonprofit already exists using email as a unique identifier.
    Nonprofit existing = nonprofitDataStore.getNonprofit(nonprofit.getEmail());
    if (existing != null) {
      throw new IllegalArgumentException("Nonprofit with email "
          + nonprofit.getEmail() + " already exists.");
    }
    // If not, save the nonprofit.
    nonprofitDataStore.saveNonprofit(nonprofit);
  }

  @Override
  public List<Nonprofit> getAllNonprofits() {
    return nonprofitDataStore.getAllNonprofits();
  }

  @Override
  public Nonprofit getNonprofitByEmail(String email) {
    return nonprofitDataStore.getNonprofit(email);
  }
}
