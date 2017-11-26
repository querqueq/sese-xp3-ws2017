package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.RoomRepository;
import java.util.Date;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomEntityTests {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private RoomRepository roomRepository;

  @Test
  public void testRoomEntityAndRepo() {
    // Create test entity
    RoomEntity roomEntity = new RoomEntity()
        .setName("Room 5")
        .setMaxOccupants(3);
    roomEntity.getPriceMap().put(PriceType.SINGLE, 400.21);
    roomEntity.getPriceMap().put(PriceType.DOUBLE, 600.38);
    roomEntity.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 532.89);
    roomEntity.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 610.88);
    // Persist
    roomEntity = entityManager.persist(roomEntity);

    assertNotNull("No room id automatically created", roomEntity.getRoomId());

    // Find that entity
    Optional<RoomEntity> byId = roomRepository.findById(roomEntity.getRoomId());
    assertTrue("Saved entity not found", byId.isPresent());

    RoomEntity saved = byId.get();
    assertEquals(roomEntity.getName(), saved.getName());
    assertEquals(roomEntity.getMaxOccupants(), saved.getMaxOccupants());
    assertEquals(roomEntity.getRoomId(), saved.getRoomId());
    assertNotNull("Price value null", saved.getPriceMap().get(PriceType.SINGLE));
    assertNotNull("Price value null", saved.getPriceMap().get(PriceType.DOUBLE));
    assertNull("Price value null", saved.getPriceMap().get(PriceType.TRIPLE));
    assertNotNull("Price value null", saved.getPriceMap().get(PriceType.SINGLE_WITH_CHILD));
    assertNotNull("Price value null", saved.getPriceMap().get(PriceType.SINGLE_WITH_TWO_CHILDREN));
    assertNull("Price value null", saved.getPriceMap().get(PriceType.DOUBLE_WITH_CHILD));

    assertEquals("Price value doesn't match", 400.21, saved.getPriceMap().get(PriceType.SINGLE), 0.001);
    assertEquals("Price value doesn't match", 600.38, saved.getPriceMap().get(PriceType.DOUBLE), 0.001);
    assertEquals("Price value doesn't match", 532.89, saved.getPriceMap().get(PriceType.SINGLE_WITH_CHILD), 0.001);
    assertEquals("Price value doesn't match", 610.88, saved.getPriceMap().get(PriceType.SINGLE_WITH_TWO_CHILDREN), 0.001);



    /*
    ROOMS CAN'T BE DELETED AT THE MOMENT!!


      // Setup 2 receipts
      ReceiptEntity receipt = new ReceiptEntity()
          .setDurationOfStay(10)
          .setRoom(roomEntity)
          .setPrice(6853.95)
          .setDiscount(0.05)
          .setReceiptDate(new Date());

      receipt = entityManager.persist(receipt);
      entityManager.remove(roomEntity);
      assertNotNull("Receipt removed with room",entityManager.find(ReceiptEntity.class, receipt.getReceiptId()));
     */


  }
}