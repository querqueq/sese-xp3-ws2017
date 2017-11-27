package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReceiptRepository;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for all room related functions.
 *
 * @author akraschitzer
 * @author lkerck
 */
@Slf4j
@Service
public class ReceiptService {

  private final ReceiptRepository receiptRepository;

  @Autowired
  public ReceiptService(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository;
  }

  /**
   * Get all rooms.
   */
  public List<ReceiptEntity> getAllReceipts() {
    return null;
  }

  /**
   * Search for receipts by given criteria.
   *
   * <p>Any value given will be used. If null is passed, that criteria will be ignored</p>
   *
   * @param startDate earliest date
   * @param endDate latest date
   * @param roomName part of the room name the receipt is for
   * @param minPrice minimum price
   * @param maxPrice maximum price
   * @param minDiscount minimum discount
   * @param maxDiscount maximum discount
   * @param minDaysofStay minimum days stayed
   * @param maxDaysOfStay maximum days stayed
   * @param customerAddress part of the address the receipt was made out to
   * @return Filtered result list
   * @throws IllegalArgumentException if Integer values are negative or contradict themselves or if
   *     either price type or maxPrice is given without the other.
   */
  public List<ReceiptEntity> getAllReceiptsByCriteria(Date startDate, Date endDate,
                                                String roomName,
                                                Double minPrice, Double maxPrice,
                                                Double minDiscount, Double maxDiscount,
                                                Integer minDaysofStay, Integer maxDaysOfStay,
                                                String customerAddress) throws
      IllegalArgumentException {
    return null;
  }
}
