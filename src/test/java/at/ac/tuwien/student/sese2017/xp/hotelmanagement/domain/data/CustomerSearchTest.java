package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerSearch;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Transactional
public class CustomerSearchTest extends HotelManagementApplicationTests {
  
  @Autowired
  private CustomerSearch customerSearch;

  @Test
  public void test() {
    System.out.println("Müller");
    printList(customerSearch.search("Müller"));
    System.out.println("Abbey");
    printList(customerSearch.search("Abbey"));
    System.out.println("Bäcker");
    printList(customerSearch.search("Bäcker"));
    System.out.println("Xyz");
    printList(customerSearch.search("Xyz"));
    System.out.println("Wien");
    printList(customerSearch.search("Wien"));
    System.out.println("Abbay");
    printList(customerSearch.search("Abbay"));
//    assertThat(customerSearch.search("Müller"), contains(TestDataInjector.CUSTOMER_1));
  }
  
  private void printList(List<?> lst) {
    System.out.println(lst.stream().map(Object::toString).collect(Collectors.joining("\n")));
  }
}
