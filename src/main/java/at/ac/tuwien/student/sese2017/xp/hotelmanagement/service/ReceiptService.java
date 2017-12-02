package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReceiptRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;


/**
 * Service class for all room related functions.
 *
 * @author Michael
 */
@Service
public class ReceiptService {

  private final ReceiptRepository receiptRepository;
  private final CustomerRepository customerRepository;

  /**
   * Constructs a {@linkplain ReceiptService}.
   * 
   * @param receiptRepository the receipt repository
   * @param customerRepository the customer repository
   */
  @Autowired
  public ReceiptService(ReceiptRepository receiptRepository,
      CustomerRepository customerRepository) {
    this.receiptRepository = receiptRepository;
    this.customerRepository = customerRepository;
  }

  /**
   * Full text search for receipts.
   * 
   * @param searchText multiple keywords seperated by whitespaces
   * @return List of matching receipts
   */
  public List<ReceiptEntity> search(String searchText) {
    return receiptRepository.search(searchText);
  }

  /**
   * Get all receipts of a single customer.
   * 
   * @param customerId the customer's id
   * @return List of all receipts of customer with {@linkplain customerId}
   */
  public List<ReceiptEntity> getReceiptsForCustomer(Long customerId) {
    return customerRepository.findById(customerId).map(c -> c.getReceipts())
        .orElse(Collections.emptyList());
  }

  /**
   * Cancels a existing non-canceled receipt.
   * 
   * @param receiptId
   */
  public void cancelReceipt(Long receiptId) {
    try {
      runWithExceptionHandling(() -> { receiptRepository.deleteById(receiptId); return null; });
    } catch (EmptyResultDataAccessException e) {
      throw new NotFoundException(receiptId, ReceiptEntity.class);
    }
  }

  /**
   * Returns a single {@linkplain ReceiptEntity} for given {@linkplain receiptId}.
   * 
   * @param receiptId
   * @return
   */
  public ReceiptEntity getReceipt(Long receiptId) {
    return runWithExceptionHandling(() -> receiptRepository.findById(receiptId)
        .orElseThrow(() -> new NotFoundException(receiptId, ReceiptEntity.class)));
  }

  private <T> T runWithExceptionHandling(Supplier<T> f) {
    try {
      return f.get();
    } catch (InvalidDataAccessApiUsageException e) {
      if (e.getRootCause().getClass().equals(IllegalArgumentException.class)) {
        throw (IllegalArgumentException) e.getRootCause();
      }
      throw e;
    }
  }
}
