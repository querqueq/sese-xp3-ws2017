package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.test;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.config.AppProperties;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.InjectableDataDirectory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Injector service for test data.
 *
 * <p>
 *   Uses a TestDataDirectory bean and injects all fields in that directory into the database.
 *   To order the entries (e.g.: this has to be persisted before that) the
 *   {@link org.springframework.core.annotation.Order} annotation can be used
 *   (first element is Integer.MIN_VALUE last element is Integer.MAX_VALUE)
 * </p>
 *
 */
@Service
@Slf4j
public class TestDataInjectorService {

  /**
   * Test data directory with all entity objects included.
   */
  @Getter
  private final InjectableDataDirectory tD;

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
   * @param testDataDirectory bean implementing InjectableDataDirectory with entities set as
   *                        public fields.
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
      InjectableDataDirectory testDataDirectory) {
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
      log.warn("TEST DATA INJECTED IN DATABASE. USE WITH CARE");
      // PRINT RED WARNING TO SYSTEM OUT
      System.out.println("\u001B[31m");
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      System.out.println("!! TEST DATA INJECTED IN DATABASE. USE WITH CARE  !!");
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      System.out.println("\u001B[0m");
    }

    // Analyzes the InjectableDataDirectory for entity objects and orders them
    // according to their Order value
    List<Object> objects = prepareTestData();

    // Create a transaction and persist all objects to db
    persistAll(objects);
  }

  /**
   * Analyzes the InjectableDataDirectory for entity objects and orders them
   * according to their Order value
   * @return ordered list of entity objects
   */
  public List<Object> prepareTestData() {
    log.info("Preparing test data");

    // Prioritized data objects
    Map<Integer, List<Object>> data = new HashMap<>();

    // Get all declared fields
    Field[] fields = tD.getClass().getDeclaredFields();
    // Iterate over all fields of testdata directory
    for(Field f : fields){
      // Get type of field
      Class type = f.getType();

      // Ignore non fields without JPA entities
      if(!hasJPAAnnotation(type)) {
        log.debug("Ignoring non JPA field {}", f.getName());
        continue;
      }

      // Try to access the field and get it's value, otherwise
      // log warning and continue with the next
      Object object;
      try {
        object = f.get(tD);
      } catch (IllegalAccessException e) {
        log.warn("Couldn't access field {} of {}", f.getName(), tD.getClass().getName());
        continue;
      }

      // Add to data ready to be injected.
      addToHashList(getOrder(f), object, data);
    }

    // Flatten priority data into a ordered list
    return reduceHashList(data);
  }

  /**
   * Write all elements in that list to database
   * @param toPersist list with entity elements
   */
  private void persistAll(List<Object> toPersist) {
    // Initialize a new transacton
    TransactionTemplate tmpl = new TransactionTemplate(txManager);
    tmpl.execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(@Nullable TransactionStatus status) {
        // Now we're in a thread with a valid transaction, injecting the test datasets now.
        log.info("Injecting test data");
        for (Object o : toPersist) {
          // Persist entity
          em.persist(o);
        }
        log.info("Finished injecting test data");
      }
    });
  }

  /**
   * Add a new element to a priority value
   * @param key priority value
   * @param value element
   * @param priorityList priority map/list structure
   */
  private static void addToHashList(int key, Object value, Map<Integer, List<Object>> priorityList) {
    // If there is no current list for this order value, create one
    if(!priorityList.containsKey(key)) {
      priorityList.put(key, new ArrayList<>());
    }
    // Add value to list
    priorityList.get(key).add(value);
  }

  /**
   * Reduce the complex priority map/list structure to a simple ordered list
   * @param priorityList priority data
   * @return ordered list
   */
  private static List<Object> reduceHashList(Map<Integer, List<Object>> priorityList) {
    Integer[] keys = priorityList.keySet().toArray(new Integer[0]);
    Arrays.sort(keys);
    List<Object> returnList = new ArrayList<>();
    for (Integer key : keys) {
      returnList.addAll(priorityList.get(key));
    }
    return returnList;
  }

  /**
   * Check if class has an JPA annotation
   * @param clazz class to check
   * @return true if @Entity is found
   */
  public static boolean hasJPAAnnotation(Class clazz) {
    // If jpa annotation found return true
    return clazz.getAnnotation(Entity.class) != null;
  }

  /**
   * Check if field has an Order set
   * @param f field to check
   * @return order if order is set, otherwise an default oder of 0
   */
  public static int getOrder(Field f) {
    // Get order annotation
    Order annotation = f.getAnnotation(Order.class);

    // Return order if annotation is found otherwise return 0
    if(annotation != null) {
      return annotation.value();
    }
    return 0;
  }
}
