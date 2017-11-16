package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@Transactional
public class CustomerSearch {
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @SuppressWarnings("unchecked")
  public List<CustomerEntity> search(String text) {
    if(StringUtils.isEmpty(text)) {
      return Collections.emptyList();
    }
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
    
    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
      .buildQueryBuilder()
      .forEntity(CustomerEntity.class)
      .get();
    
    Query query = queryBuilder
        .keyword()
        .fuzzy()
        .onFields("name", "billingAddress")
        .matching(text)
        .createQuery();
    
    return fullTextEntityManager.createFullTextQuery(query, CustomerEntity.class).getResultList();
  }
}
