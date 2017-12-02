package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReceiptRepository;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
   * @param receiptRepository the receipt repository
   * @param customerRepository the customer repository
   */
  @Autowired
  public ReceiptService(ReceiptRepository receiptRepository, CustomerRepository customerRepository) {
    this.receiptRepository = receiptRepository;
    this.customerRepository = customerRepository;
  }

  /**
   * Full text search for receipts.
   * @param searchText multiple keywords seperated by whitespaces
   * @return List of matching receipts
   */
  public List<ReceiptEntity> search(String searchText) {
    return receiptRepository.search(searchText);
  }
  
  /**
   * Get all receipts of a single customer.
   * @param customerId the customer's id
   * @return List of all receipts of customer with {@linkplain customerId}
   */
  public List<ReceiptEntity> getReceiptsForCustomer(Long customerId) {
    return customerRepository.findById(customerId).map(c -> c.getReceipts())
        .orElse(Collections.emptyList());
  }
}
