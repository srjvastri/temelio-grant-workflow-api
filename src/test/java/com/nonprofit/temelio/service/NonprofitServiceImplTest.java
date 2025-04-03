package com.nonprofit.temelio.service;

import com.nonprofit.temelio.dataStore.Hashmap;
import com.nonprofit.temelio.dataStore.NonProfitDataStore;
import com.nonprofit.temelio.model.Nonprofit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NonprofitServiceImplTest {

  private NonProfitDataStore dataStore;
  private NonprofitService nonprofitService;

  @BeforeEach
  public void setUp() {
    // Use the in-memory datastore implementation.
    dataStore = new Hashmap();
    nonprofitService = new NonprofitServiceImpl(dataStore);
  }

  @Test
  public void testCreateNonprofit_Success() {
    Nonprofit nonprofit = Nonprofit.builder()
        .name("Helping Hands")
        .address("123 Charity Lane")
        .email("contact@helpinghands.org")
        .build();

    nonprofitService.createNonprofit(nonprofit);

    Nonprofit fetched = nonprofitService.getNonprofitByEmail("contact@helpinghands.org");
    assertNotNull(fetched, "Nonprofit should be saved and retrievable");
    assertEquals("Helping Hands", fetched.getName());
    assertEquals("123 Charity Lane", fetched.getAddress());
    assertEquals("contact@helpinghands.org", fetched.getEmail());
  }

  @Test
  public void testCreateNonprofit_Duplicate() {
    Nonprofit nonprofit = Nonprofit.builder()
        .name("Helping Hands")
        .address("123 Charity Lane")
        .email("contact@helpinghands.org")
        .build();
    nonprofitService.createNonprofit(nonprofit);

    // Attempt to create a nonprofit with the same email should throw an exception.
    Nonprofit duplicate = Nonprofit.builder()
        .name("Helping Hands Duplicate")
        .address("456 Different Ave")
        .email("contact@helpinghands.org")
        .build();

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      nonprofitService.createNonprofit(duplicate);
    });
    assertTrue(exception.getMessage().contains("already exists"),
        "Exception message should indicate that the nonprofit already exists");
  }

  @Test
  public void testGetAllNonprofits() {
    // Initially, the datastore should be empty.
    List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();
    assertNotNull(nonprofits, "List should not be null");
    assertTrue(nonprofits.isEmpty(), "List should be empty initially");

    // Create two nonprofits.
    Nonprofit nonprofit1 = Nonprofit.builder()
        .name("Helping Hands")
        .address("123 Charity Lane")
        .email("contact@helpinghands.org")
        .build();
    Nonprofit nonprofit2 = Nonprofit.builder()
        .name("Aid Alliance")
        .address("789 Kindness Blvd")
        .email("info@aidalliance.org")
        .build();

    nonprofitService.createNonprofit(nonprofit1);
    nonprofitService.createNonprofit(nonprofit2);

    nonprofits = nonprofitService.getAllNonprofits();
    assertEquals(2, nonprofits.size(), "List should contain 2 nonprofits after adding them");
  }

  @Test
  public void testGetNonprofitByEmail() {
    // Test for a nonprofit that doesn't exist.
    Nonprofit fetched = nonprofitService.getNonprofitByEmail("nonexistent@example.com");
    assertNull(fetched, "Nonprofit should be null when not found");

    // Create a nonprofit and then retrieve it.
    Nonprofit nonprofit = Nonprofit.builder()
        .name("Helping Hands")
        .address("123 Charity Lane")
        .email("contact@helpinghands.org")
        .build();
    nonprofitService.createNonprofit(nonprofit);

    fetched = nonprofitService.getNonprofitByEmail("contact@helpinghands.org");
    assertNotNull(fetched, "Nonprofit should be retrievable after creation");
    assertEquals("Helping Hands", fetched.getName());
  }
}
