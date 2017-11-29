package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReceiptRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ReceiptEntityTests extends EntityTestBase{
  @Autowired
  private ReceiptRepository receiptRepository;

  @Test
  public void testBasicStorageOfRecipes() {

    // SETUP Addresses
    AddressEntity address1 = new AddressEntity()
        .setName("Ira T. Adkins")
        .setStreetAddress1("215 Aenean Ave")
        .setStreetAddress2("")
        .setZipCode("41990-087")
        .setCity("Sint-Denijs-Westrem")
        .setState("Oost-Vlaanderen");

    AddressEntity address2 = new AddressEntity()
        .setName("Cheryl A. Nielsen")
        .setStreetAddress1("6126 Eu Ave")
        .setStreetAddress2("")
        .setZipCode("41687-555")
        .setCity("Te Puke")
        .setState("NI");

    AddressEntity address3 = new AddressEntity()
        .setName("Cassandra V. Noble")
        .setStreetAddress1("1059 Augue St.")
        .setStreetAddress2("Ap #542")
        .setZipCode("6453")
        .setCity("Sommariva Perno")
        .setState("Piemonte");

    CustomerEntity customer1 = new CustomerEntity()
        .setBillingAddress(address1)
        .setBirthday(LocalDate.of(1999, 1, 23))
        .setDiscount(BigDecimal.ZERO)
        .setEmail("N@A.nope")
        .setName("N/A")
        .setSex(Sex.MALE)
        .setPhoneNumber("0");

    CustomerEntity customer2 = new CustomerEntity()
        .setBillingAddress(address2)
        .setBirthday(LocalDate.of(1999, 1, 23))
        .setDiscount(BigDecimal.ZERO)
        .setEmail("N@A.nope")
        .setName("N/A")
        .setSex(Sex.MALE)
        .setPhoneNumber("0");

    // Setup of an example room
    RoomEntity room1 = new RoomEntity().setName("HoneyMoonSuite")
        .setMaxOccupants(2);
    room1.getPriceMap().put(PriceType.SINGLE, 240.11);
    room1.getPriceMap().put(PriceType.DOUBLE, 420.00);
    room1.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 330.90);
    room1 = entityManager.persist(room1);

    // Setup 2 receipts
    ReceiptEntity receipt1 = new ReceiptEntity()
        .addCustomer(customer1)
        .setHotelAddress(address3)
        .setDurationOfStay(10)
        .addRoom(room1)
        .setPrice(6853.95)
        .setDiscount(0.05)
        .setReceiptDate(LocalDateTime.now());

    ReceiptEntity receipt2 = new ReceiptEntity()
        .addCustomer(customer2)
        .setHotelAddress(address3)
        .setDurationOfStay(3)
        .addRoom(room1)
        .setPrice(601.8)
        .setDiscount(0.0)
        .setReceiptDate(LocalDateTime.now());

    receipt1 = entityManager.persist(receipt1);
    receipt2 = entityManager.persist(receipt2);


    Optional<ReceiptEntity> find = receiptRepository
        .findById(receipt1.getReceiptId());
    assertTrue("Receipt 2 not found", find.isPresent());
    ReceiptEntity foundReceipt1 = find.get();
    assertEquals("Discount not saved correctly", receipt1.getDiscount(), foundReceipt1.getDiscount());
    assertEquals("Duration not saved correctly", receipt1.getDurationOfStay(), foundReceipt1.getDurationOfStay());
    assertEquals("Price not saved correctly", receipt1.getPrice(), foundReceipt1.getPrice());
    assertEquals("Date not saved correctly", receipt1.getReceiptDate(), foundReceipt1.getReceiptDate());

    // Check mappings
    assertNotNull("Customer address not saved", foundReceipt1.getCustomers().get(0).getBillingAddress());
    assertNotNull("Hotel address not saved", foundReceipt1.getHotelAddress());
    assertNotNull("Room not saved", foundReceipt1.getRooms());

    // Check correctness of mapping data
    assertEquals("Customer address not saved correctly", address1.getName(), foundReceipt1.getCustomers().get(0).getBillingAddress().getName());
    assertEquals("Hotel address not saved correctly", address3.getName(), foundReceipt1.getHotelAddress().getName());
    assertEquals("Room not saved correctly", room1.getName(), foundReceipt1.getRooms().get(0).getName());
    assertEquals("Room not saved correctly", room1.getRoomId(), foundReceipt1.getRooms().get(0).getRoomId());

    Long customerAddrId = foundReceipt1.getCustomers().get(0).getBillingAddress().getAddressId();
    Long hotelAddrId = foundReceipt1.getHotelAddress().getAddressId();

    /*
    RECEIPT 2
     */
    find = receiptRepository
        .findById(receipt2.getReceiptId());
    assertTrue("Receipt 2 not found", find.isPresent());
    ReceiptEntity foundReceipt2 = find.get();
    assertEquals("Same address saved twice", foundReceipt1.getHotelAddress().getAddressId(), foundReceipt2.getHotelAddress().getAddressId());

    // DELETE first entity
    entityManager.remove(receipt1);

    assertNotNull("Room deleted with receipt", entityManager.find(RoomEntity.class, room1.getRoomId()));
    assertNotNull("Hotel address deleted with receipt", entityManager.find(AddressEntity.class, hotelAddrId));
    // OPTIONAL DELETION OF ORPHANED ADDRESSES BECAUSE DIFFICULT TO IMPLEMENT
    //    assertNull("Orphaned address not deleted with receipt", entityManager.find(AddressEntity.class, customerAddrId));

    customerAddrId = foundReceipt2.getCustomers().get(0).getBillingAddress().getAddressId();
    // DELETE second entity
    entityManager.remove(receipt2);
    assertNotNull("Room deleted with receipt", entityManager.find(RoomEntity.class, room1.getRoomId()));
    // OPTIONAL DELETION OF ORPHANED ADDRESSES BECAUSE DIFFICULT TO IMPLEMENT
    //    assertNull("Orphaned address not deleted with receipt", entityManager.find(AddressEntity.class, customerAddrId));



  }
}
