package com.nonprofit.temelio.service;

import com.nonprofit.temelio.model.Nonprofit;
import java.util.List;

public interface NonprofitService {
  void createNonprofit(Nonprofit nonprofit);
  List<Nonprofit> getAllNonprofits();
  Nonprofit getNonprofitByEmail(String email);
}
