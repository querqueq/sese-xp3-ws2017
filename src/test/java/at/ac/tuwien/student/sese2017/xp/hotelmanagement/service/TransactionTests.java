package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.AddressEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReceiptRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TransactionTests extends AbstractTransactionalJUnit4SpringContextTests {


  @Autowired
  ReceiptRepository receiptRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void testEnvarsSoftDelete() {
    PlatformTransactionManager txMgr = applicationContext.getBean(PlatformTransactionManager.class);

    TransactionStatus transaction1 = txMgr.getTransaction(new DefaultTransactionDefinition(PROPAGATION_REQUIRES_NEW));

    ReceiptEntity receipt = new ReceiptEntity()
        .addCustomer(new CustomerEntity()
            .setBillingAddress(new AddressEntity()
                .setName("Abbey Fields")
                .setStreetAddress1("Karlsplatz 1")
                .setZipCode("1040")
                .setCity("Wien")
                .setState("Austria"))
            .setBirthday(LocalDate.of(1982, 7, 7))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("hr.mueller@example.org")
            .setName("Abbey Fields")
            .setSex(Sex.MALE)
            .setPhoneNumber("01234567"))
        .setHotelAddress(new AddressEntity()
            .setName("Hotel zum schoenen Urblaub")
            .setStreetAddress1("Am Buchtaler Jockl 1")
            .setZipCode("3024")
            .setCity("Lungau nahe dem Pongau")
            .setState("Austria"))
        .setDurationOfStay(10)
        .addRoom(new RoomEntity().setName("presidentialSuite")
            .setMaxOccupants(4))
        .setPrice(Double.POSITIVE_INFINITY)
        .setDiscount(0.05)
        .setReceiptDate(LocalDateTime.now());;

    entityManager.persist(receipt);

    txMgr.commit(transaction1);

    Long receiptId = receipt.getReceiptId();

    int revisionCount = receiptRepository.findRevisions(receiptId).getContent().size();
    assertEquals(1, receiptRepository.findRevisions(receiptId).getContent().size());


    TransactionStatus transaction2 = txMgr.getTransaction(new DefaultTransactionDefinition(PROPAGATION_REQUIRES_NEW));
    receipt.setDurationOfStay(4);
    receiptRepository.save(receipt);
    txMgr.commit(transaction2);
    int revisionCount2 = receiptRepository.findRevisions(receiptId).getContent().size();

    TransactionStatus transaction3 = txMgr.getTransaction(new DefaultTransactionDefinition(PROPAGATION_REQUIRES_NEW));
    receiptRepository.deleteById(receiptId);
    txMgr.commit(transaction3);

    int revisionCount3 = receiptRepository.findRevisions(receiptId).getContent().size();
    assertTrue(!receiptRepository.findById(receiptId).isPresent());
    assertEquals(revisionCount + 1, revisionCount2);
  }



}
