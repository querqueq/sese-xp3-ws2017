package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReceiptRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataInjector;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.NotFoundException;

@Transactional
public class ReceiptServiceTest extends HotelManagementApplicationTests {

  @Autowired
  ReceiptService receiptService;

  @Autowired
  private ReceiptRepository receiptRepository;

  /**
   * Find all receipt on empty db
   */
  @Test
  public void getAllReceipts_emptyDB() throws Exception {
    // Clear all receipts to enable room removal
    receiptRepository.deleteAll();
    // Define expected result
    ReceiptEntity[] expectedResult = new ReceiptEntity[] {};
    // Execute service function
    List<ReceiptEntity> allReceiptsByCriteria = receiptService.search("Non-existant");

    // Check result
    assertNotNull("Null returned by service", allReceiptsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
    assertThat("Not the right elements returned", allReceiptsByCriteria,
        containsInAnyOrder(expectedResult));
  }

  /**
   * Tests if a NotFoundException is thrown if a non existing receipt is canceled.
   */
  @Test(expected = NotFoundException.class)
  public void testCancelNonExistingReceipt() {
    receiptService.cancelReceipt(Long.MAX_VALUE);
  }

  /**
   * Tests if an existing receipt can be canceled and is not found by findById after deletion.
   */
  @Test
  public void testCancelExistingReceipt() {
    long receiptId = TestDataInjector.RECEIPT_1.getReceiptId();
    assertTrue(receiptRepository.findById(receiptId).isPresent());
    receiptService.cancelReceipt(receiptId);
    assertFalse(receiptRepository.findById(receiptId).isPresent());
  }

  /**
   * Tests if null receiptId throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCancelNullReceipt() {
    receiptService.cancelReceipt(null);
  }

  /**
   * Tests if an already canceled receipt throws a NotFoundException.
   */
  @Test(expected = NotFoundException.class)
  public void testCancelAlreadyCanceledReceipt() {
    long receiptId = TestDataInjector.RECEIPT_1.getReceiptId();
    assertTrue(receiptRepository.findById(receiptId).isPresent());
    receiptService.cancelReceipt(receiptId);
    receiptService.cancelReceipt(receiptId);
  }

  /**
   * Tests if an existing receipt can be fetched using its receiptId.
   */
  @Test
  public void testGetReceipt() {
    assertThat(receiptService.getReceipt(TestDataInjector.RECEIPT_1.getReceiptId()).getReceiptId(),
        is(TestDataInjector.RECEIPT_1.getReceiptId()));
  }

  /**
   * Tests if getting a non existing receipt throws a NotFoundException.
   */
  @Test(expected = NotFoundException.class)
  public void testGetNonExistingReceipt() {
    receiptService.getReceipt(Long.MAX_VALUE);
  }

  /**
   * Tests if null receiptId throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetNullReceipt() {
    receiptService.getReceipt(null);
  }

  /**
   * Tests if all receipts for a customer are returned.
   */
  @Test
  public void testGetReceiptsForCustomer() {
    assertEquals(1,
        receiptService.getReceiptsForCustomer(TestDataInjector.CUSTOMER_1.getId()).size());
  }

  /**
   * Tests if null customerId throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetReceiptsNull() {
    receiptService.getReceiptsForCustomer(null);
  }
}
