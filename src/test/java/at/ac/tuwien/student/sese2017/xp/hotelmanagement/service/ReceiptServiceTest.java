package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReceiptRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.RoomRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataInjector;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class ReceiptServiceTest extends HotelManagementApplicationTests {

  @Autowired
  ReceiptService receiptService;

  @Autowired
  private ReceiptRepository receiptRepository;

  @Autowired
  private RoomRepository roomRepository;

  /**
   * Find all receipt on empty db
   */
  @Test
  public void getAllReceipts_emptyDB() throws Exception {
    // Clear all receipts to enable room removal
    receiptRepository.deleteAll();
    // Define expected result
    ReceiptEntity[] expectedResult = new ReceiptEntity[]{};
    // Execute service function
    List<ReceiptEntity> allReceiptsByCriteria = receiptService.search("Non-existant");

    // Check result
    assertNotNull("Null returned by service", allReceiptsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
  }
}