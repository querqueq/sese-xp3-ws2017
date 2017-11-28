package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReceiptRepository;
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

  @Autowired
  public ReceiptService(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository;
  }

  public List<ReceiptEntity> search(String searchText) {
    return receiptRepository.search(searchText);
  }
}
