package at.ac.tuwien.student.sese2017.xp.hotelmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Main entry point of the application.
 *
 * <p>This class initializes the spring context and
 * launches the application.</p>
 *
 * @author lkerck
 */
@SpringBootApplication
public class HotelManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(HotelManagementApplication.class, args);
  }
  
  @Bean
  MethodValidationPostProcessor methodValidationPostProcessor() {
    return new MethodValidationPostProcessor();
  }
}
