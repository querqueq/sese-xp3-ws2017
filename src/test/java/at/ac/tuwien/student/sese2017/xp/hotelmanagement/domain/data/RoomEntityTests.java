package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.RoomRepository;
import java.util.List;
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

  }
}