package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ExampleRepository;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class DataTests {
  private final static String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consetetur sadipscing "
      + "elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, "
      + "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita "
      + "kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum "
      + "dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut "
      + "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et "
      + "justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est "
      + "Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
      + "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed "
      + "diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita "
      + "kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \n"
      + "\n"
      + "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie "
      + "consequat, vel illum dolore eu feugiat nulla facilisis at";


  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ExampleRepository exampleRepository;

  @Test
  public void testExampleEntityRepo() {
    ExampleEntity exampleEntity = new ExampleEntity()
        .setDateField(new Date())
        .setFirstField("Hello")
        .setSecondField("World")
        .setLongField(LOREM_IPSUM);
    entityManager.persist(exampleEntity);
    List<ExampleEntity> find = exampleRepository
        .findBySecondFieldOrderByDateFieldDesc(exampleEntity.getSecondField());
    assertThat(find).extracting(ExampleEntity::getSecondField)
        .containsOnly(exampleEntity.getSecondField());

  }

}