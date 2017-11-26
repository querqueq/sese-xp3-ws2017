package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.AddressRepository;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressEntityTests extends EntityTestBase {

  @Autowired
  AddressRepository addressRepository;

  @Test
  public void testExampleEntityRepo() {
    AddressEntity addressEntity = new AddressEntity()
        .setName("Ira T. Adkins")
        .setStreetAddress1("215 Aenean Ave")
        .setStreetAddress2("")
        .setZipCode("41990-087")
        .setCity("Sint-Denijs-Westrem")
        .setState("Oost-Vlaanderen");

    addressEntity = entityManager.persist(addressEntity);
    List<ExampleEntity> find = addressRepository
        .find(exampleEntity.getSecondField());
    assertThat(find).extracting(ExampleEntity::getSecondField)
        .containsOnly(exampleEntity.getSecondField());
    assertThat(find).extracting(ExampleEntity::getFirstField)
        .containsOnly(exampleEntity.getFirstField());
    assertThat(find).extracting(ExampleEntity::getLongField)
        .containsOnly(exampleEntity.getLongField());
    assertThat(find).extracting(ExampleEntity::getDateField)
        .containsOnly(exampleEntity.getDateField());
    assertThat(find).extracting(ExampleEntity::getId)
        .containsOnly(exampleEntity.getId());

  }

}
