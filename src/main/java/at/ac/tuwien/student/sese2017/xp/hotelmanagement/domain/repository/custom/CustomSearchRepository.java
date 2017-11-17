package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.custom;

import java.util.List;

/**
 * Repository interface for Lucene via hibernate search in repository's.
 * @param <T> Entity type returned
 */
public interface CustomSearchRepository<T> {
  /**
   * Does a full text search over all CustomerEntities for search Strings longer
   * than 2 characters.
   * @param text The text to do the full text search with.
   * @return The list of matching CustomerEntity objects.
   */
  List<T> search(String text);
}
