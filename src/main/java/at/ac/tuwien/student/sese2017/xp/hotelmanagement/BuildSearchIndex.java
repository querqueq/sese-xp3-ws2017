package at.ac.tuwien.student.sese2017.xp.hotelmanagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Creates Lucene indexes for data that was inserted outside the application through scripts.
 * @author Michael
 *
 */
@Slf4j
@Component
public class BuildSearchIndex implements ApplicationListener<ApplicationReadyEvent> {

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Create an initial Lucene index for the data already present in the
   * database.
   * This method is called when Spring's startup.
   */
  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    try {
      // gets an entity manager that creates indexes for the full text search
      FullTextEntityManager fullTextEntityManager =
          Search.getFullTextEntityManager(entityManager);
      fullTextEntityManager.createIndexer().startAndWait();
    } catch (InterruptedException e) {
      log.error(
          "An error occurred trying to build the serach index", e);
      Thread.currentThread().interrupt();
    }
  }
}
