package com.nonprofit.temelio.dataStore;

import com.nonprofit.temelio.model.Nonprofit;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class Hashmap implements NonProfitDataStore {

  private Map<String, Nonprofit> nonprofits;

  public Hashmap() {
    nonprofits = new HashMap<>();
  }

  @Override
  public void saveNonprofit(Nonprofit nonprofit) {
    nonprofits.put(nonprofit.getEmail(), nonprofit);
  }

  @Override
  public Nonprofit getNonprofit(String email) {
    return nonprofits.get(email);
  }

  @Override
  public List<Nonprofit> getAllNonprofits() {
    return new ArrayList<>(nonprofits.values());
  }

  @Override
  public void updateNonprofit(String email, Nonprofit nonprofit) {
    nonprofits.put(email, nonprofit);
  }

  @Override
  public void deleteNonprofit(String email) {
    nonprofits.remove(email);
  }
}
