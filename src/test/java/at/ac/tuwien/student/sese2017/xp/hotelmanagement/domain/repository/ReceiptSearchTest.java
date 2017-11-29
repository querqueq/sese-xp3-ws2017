package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.custom.CustomSearchRepository;

public class ReceiptSearchTest extends SearchTestBase {
  
  @Autowired
  private ReceiptRepository search;

  @Override
  protected CustomSearchRepository<?> getSearch() {
    return search;
  }

}
