package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
  private CustomerRepository customerRepository;

  @Autowired
  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Long create(CustomerEntity entity) {
    if(entity.getBirthday().isAfter(LocalDate.now())) {
      throw new ValidationException("Cannot have been born in the future!");
    }
    return customerRepository.save(entity).getId();
  }

}
