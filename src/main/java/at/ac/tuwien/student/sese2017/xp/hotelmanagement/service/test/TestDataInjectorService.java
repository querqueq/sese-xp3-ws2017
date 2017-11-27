package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.test;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.config.AppProperties;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataDirectory;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
public class TestDataInjectorService {

  /**
   * Test data directory with all entity objects included.
   */
  @Getter
  private final TestDataDirectory tD;

  /**
   * Transaction manager to initialize and commit a transaction.
   */
  private final PlatformTransactionManager txManager;
  /**
   * Entity manager.
   */
  private final EntityManager em;
  /**
   * App properties for the injectTestData property
   * is true if injectTestData flag is set true.
   * If no value is set at all it defaults to false.
   */
  private final AppProperties appProperties;


  /**
   * Initialize the testData injector.
   *
   * @param txManager Current transaction manager
   * @param em Current EM
   * @param appProperties injectTestData flag. Is set via command line --injecttestdata or
   *                       via properties file injecttestdata=true. Defaults to false if not set.
   */
  @Autowired
  public TestDataInjectorService(
      // Autowired, Specify correct transaction manager (multiple beans of this type can occur)
      @Qualifier("transactionManager") PlatformTransactionManager txManager,
      // Autowired above
      EntityManager em,
      // Injected by spring from properties
      AppProperties appProperties,
      // Test data entities
      TestDataDirectory testDataDirectory) {
    this.txManager = txManager;
    this.em = em;
    this.appProperties = appProperties;
    this.tD = testDataDirectory;
  }

  /**
   * Inject the test data into the database.
   *
   * @author lkerck
   */
  @PostConstruct
  @Transactional
  public void inject() {
    // If injectData property is set to false or not set at all return
    if (!appProperties.isInjectTestdata()) {
      return;
    }

    /* Check if "@SpringBootTest" annotation is on classpath to identify
     * test runs and warn if testdata is used in normal operation
     */
    try {
      Class.forName("org.springframework.boot.test.context.SpringBootTest");
    } catch (ClassNotFoundException e) {
      log.warn("\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nTEST DATA INJECTED IN DATABASE. "
          + "USE WITH CARE\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
    }

    // Initialize a new transacton
    TransactionTemplate tmpl = new TransactionTemplate(txManager);
    tmpl.execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(@Nullable TransactionStatus status) {
        // Now we're in a thread with a valid transaction, injecting the test datasets now.
        log.info("Injecting test data");
        em.persist(tD.ROOM_1);
        em.persist(tD.ROOM_2);
        em.persist(tD.ROOM_3);
        em.persist(tD.ROOM_4);
        em.persist(tD.ROOM_5);
        em.persist(tD.ROOM_6);
        em.persist(tD.CUSTOMER_1);
        em.persist(tD.CUSTOMER_2);
        em.persist(tD.CUSTOMER_3);
        log.info("Finished injecting test data");
      }
    });

  }

}
