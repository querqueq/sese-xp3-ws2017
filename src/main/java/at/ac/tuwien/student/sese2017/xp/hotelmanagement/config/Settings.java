package at.ac.tuwien.student.sese2017.xp.hotelmanagement.config;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.InjectableDataDirectory;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataV1;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Default settings class to initialize and configure spring beans.
 *
 * <p>
 * This has nothing to do with providing parametrized settings like
 * values of the properties file.
 * This class uses values provided elsewhere (or hardcoded) to initialize
 * Services and other Beans.
 * </p>
 *
 * @author lkerck
 */
@Configuration
@Slf4j
public class Settings {
  /**
    * Only create the selected test data version as bean.
    * @param appProperties properties for testdataVersion.
    * @return selected test data version.
    */
  @Bean
  @Autowired
  public InjectableDataDirectory testDataDirectory(AppProperties appProperties) {
    // Don't build testdata directory if not required
    if (!appProperties.isInjectTestdata()) {
      return null;
    }

    // Check for specified version
    switch (appProperties.getTestdataVersion()) {
      case 1:
        return new TestDataV1();
      case 2:
        return new TestDataV2();
      default:
        log.info("No valid testdata version specified");
    }
    // Otherwise use latest
    return new TestDataV2();
  }
}
