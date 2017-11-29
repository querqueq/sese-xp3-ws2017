package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.AddressRepository;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressEntityTests extends EntityTestBase {

  @Autowired
  AddressRepository addressRepository;

  @Test
  public void testStorageOfAddressEntity() {
    AddressEntity addressEntity = new AddressEntity()
        .setName("Ira T. Adkins")
        .setStreetAddress1("215 Aenean Ave")
        .setStreetAddress2("")
        .setZipCode("41990-087")
        .setCity("Sint-Denijs-Westrem")
        .setState("Oost-Vlaanderen");

    addressEntity = entityManager.persist(addressEntity);
    List<AddressEntity> find = addressRepository
        .findAllByNameContainingIgnoreCase(addressEntity.getName());
    assertThat(find).extracting(AddressEntity::getName)
        .containsOnly(addressEntity.getName());
    assertThat(find).extracting(AddressEntity::getStreetAddress1)
        .containsOnly(addressEntity.getStreetAddress1());
    assertThat(find).extracting(AddressEntity::getStreetAddress2)
        .containsOnly(addressEntity.getStreetAddress2());
    assertThat(find).extracting(AddressEntity::getZipCode)
        .containsOnly(addressEntity.getZipCode());
    assertThat(find).extracting(AddressEntity::getCity)
        .containsOnly(addressEntity.getCity());
    assertThat(find).extracting(AddressEntity::getState)
        .containsOnly(addressEntity.getState());

  }

}
