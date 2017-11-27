package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.impl;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.custom.CustomSearchRepository;
import java.lang.annotation.ElementType;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.cfg.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Custom function implementations for CustomerRepository.
 *
 * @author Michael
 */
public class CustomerRepositoryImpl implements CustomSearchRepository<CustomerEntity> {
  /**
   * Entity manager.
   */
  private final EntityManager entityManager;

  /**
   * Constructor with dependency injection.
   * @param entityManager current entity manager.
   */
  @Autowired
  public CustomerRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * Provides full text search for CustomerEntity over its name and billingAddress fields.
   *
   * <p>Does a full text search over all CustomerEntities for search Strings longer
   * than 2 characters.</p>
   * @param text The text to do the full text search with.
   * @return The list of matching CustomerEntity objects.
   * @author Michael
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<CustomerEntity> search(String text) {
    if (StringUtils.isEmpty(text) || text.length() < 3) {
      return Collections.emptyList();
    }

    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
        .buildQueryBuilder()
        .forEntity(CustomerEntity.class)
        .overridesForField("name", "customanalyzer_query")
        .overridesForField("billingAddress.name", "customanalyzer_query")
        .overridesForField("billingAddress.streetAddress1", "customanalyzer_query")
        .overridesForField("billingAddress.streetAddress2", "customanalyzer_query")
        .overridesForField("billingAddress.city", "customanalyzer_query")
        .overridesForField("billingAddress.state", "customanalyzer_query")
        .overridesForField("billingAddress.zipCode", "customanalyzer_query")
        .get();

    Query query = queryBuilder
        .keyword()
        .fuzzy()
        .withEditDistanceUpTo(1)
        .onFields("name",
            "billingAddress.name",
            "billingAddress.streetAddress1",
            "billingAddress.streetAddress2",
            "billingAddress.city",
            "billingAddress.state",
            "billingAddress.zipCode")
        .matching(text)
        .createQuery();
     return fullTextEntityManager.createFullTextQuery(query, CustomerEntity.class).getResultList();
  }
}
