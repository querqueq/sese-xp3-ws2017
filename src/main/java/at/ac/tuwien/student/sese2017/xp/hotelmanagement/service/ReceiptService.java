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

  @Autowired
  public ReceiptService(ReceiptRepository receiptRepository, CustomerRepository customerRepository) {
    this.receiptRepository = receiptRepository;
    this.customerRepository = customerRepository;
  }

  public List<ReceiptEntity> search(String searchText) {
    return receiptRepository.search(searchText);
  }
  
  public List<ReceiptEntity> getReceiptsForCustomer(Long customerId) {
    return customerRepository.findById(customerId).map(c -> c.getReceipts())
        .orElse(Collections.emptyList());
  }
}
