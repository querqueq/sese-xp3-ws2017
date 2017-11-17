package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerSearch;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataInjector;

@Transactional
public class CustomerSearchTest extends HotelManagementApplicationTests {

  @Autowired
  private CustomerSearch customerSearch;

  @Test
  public void testSearchPartialMatch() {
    assertThat(
        customerSearch.search("Bäcker").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_1.getName()),
            hasItem(TestDataInjector.CUSTOMER_2.getName())));
  }

  @Test
  public void testSearchSuccessful() {
    assertThat(
        customerSearch.search("Müller").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(1),
            hasItem(TestDataInjector.CUSTOMER_1.getName())));
    assertThat(
        customerSearch.search("Abbey").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_2.getName()),
            hasItem(TestDataInjector.CUSTOMER_3.getName())));
    assertThat(
        customerSearch.search("Wien").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_1.getName()),
            hasItem(TestDataInjector.CUSTOMER_3.getName())));
  }

  @Test
  public void testSearchSpellingError() {
    assertThat(
        customerSearch.search("Abbay").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_2.getName()),
            hasItem(TestDataInjector.CUSTOMER_3.getName())));
  }

  @Test
  public void testSearchUnfindable() {
    assertThat(customerSearch.search("Xyczzte").stream().map(c -> c.getName())
        .collect(Collectors.toList()), hasSize(0));
  }

  @Test
  public void testSearchOneCharacter() {
    assertThat(
        customerSearch.search("b").stream().map(c -> c.getName()).collect(Collectors.toList()),
        hasSize(0));
  }

  @Test
  public void testSearchTwoCharacter() {
    assertThat(
        customerSearch.search("bb").stream().map(c -> c.getName()).collect(Collectors.toList()),
        hasSize(0));
  }

  @Test
  public void testSearchThreeCharacter() {
    assertThat(
        customerSearch.search("bbe").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_2.getName()),
            hasItem(TestDataInjector.CUSTOMER_3.getName())));
  }

  @Test
  public void testSearchEmpty() {
    assertThat(
        customerSearch.search("").stream().map(c -> c.getName()).collect(Collectors.toList()),
        hasSize(0));
  }

  @Test
  public void testSearchNull() {
    assertThat(
        customerSearch.search(null).stream().map(c -> c.getName()).collect(Collectors.toList()),
        hasSize(0));
  }

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
  }

  private void printList(List<?> lst) {
    System.out.println(lst.stream().map(Object::toString).collect(Collectors.joining("\n")));
  }
}
