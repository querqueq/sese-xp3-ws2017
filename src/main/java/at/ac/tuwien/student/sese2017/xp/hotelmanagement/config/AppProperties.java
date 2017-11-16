package at.ac.tuwien.student.sese2017.xp.hotelmanagement.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * Properties for HotelManagement application
 */
@ConfigurationProperties(prefix="appconfig")
@Service
@Data
public class AppProperties {
  private boolean injecttestdata;
}
