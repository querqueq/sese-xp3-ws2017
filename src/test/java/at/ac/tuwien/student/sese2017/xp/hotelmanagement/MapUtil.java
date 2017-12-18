package at.ac.tuwien.student.sese2017.xp.hotelmanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to create a map in one line.
 * @author Michael
 *
 */
public class MapUtil {
  /**
   * Creates a map using the supplied keys and values;
   * @param keys A list of the keys.
   * @param values A list of the values with matching positions to their keys.
   * @return A map depicting a zip between the keys and values list.
   */
  public static <K, V> Map<K, V> getMap(List<K> keys, List<V> values) {
    Map<K, V> map = new HashMap<>();
    if(keys == null || values == null || keys.size() != values.size()) {
      throw new IllegalArgumentException(
          "Keys and values must both be null and have the same size!");
    }
    for(int i = 0; i < keys.size(); i++) {
      map.put(keys.get(i), values.get(i));
    }
    return map;
  }
}
