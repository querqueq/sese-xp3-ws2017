package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.impl;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.custom.CustomSearchRepository;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Custom function implementations for CustomerRepository.
 *
 * @author Michael
 */
public class ReceiptRepositoryImpl implements CustomSearchRepository<ReceiptEntity> {
  /**
   * Entity manager.
   */
  private final EntityManager entityManager;

  /**
   * Constructor with dependency injection.
   * @param entityManager current entity manager.
   */
  @Autowired
  public ReceiptRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * Provides full text search for ReceiptEntity over fields.
   *
   * <p>Does a full text search over all ReceiptEntities for search Strings longer
   * than 2 characters.</p>
   * @param text The text to do the full text search with.
   * @return The list of matching ReceiptEntity objects.
   * @author Michael
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<ReceiptEntity> search(String text) {
    if (StringUtils.isEmpty(text) || text.length() < 3) {
      return Collections.emptyList();
    }

    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
        .buildQueryBuilder()
        .forEntity(ReceiptEntity.class)
        .overridesForField("hotelAddress.name", "customanalyzer_query")
        .overridesForField("hotelAddress.streetAddress1", "customanalyzer_query")
        .overridesForField("rooms.name", "customanalyzer_query")
        .overridesForField("customers.name", "customanalyzer_query")
        .overridesForField("customers.billingAddress.name", "customanalyzer_query")
        .overridesForField("customers.billingAddress.streetAddress1", "customanalyzer_query")
        .get();

    Query query = queryBuilder
        .keyword()
        .fuzzy()
        .withEditDistanceUpTo(1)
        .onFields(
            "hotelAddress.name",
            "hotelAddress.streetAddress1")
        .andField("customers.name").boostedTo(5F)
        .andField("customers.billingAddress.name").boostedTo(4F)
        .andField("customers.billingAddress.streetAddress1").boostedTo(3F)
        .andField("rooms.name").boostedTo(3F)
        .andField("receiptDate").ignoreFieldBridge()
        .matching(text)
        .createQuery();
     return fullTextEntityManager.createFullTextQuery(query, CustomerEntity.class).getResultList();
  }
}
