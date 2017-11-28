package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.util.Collection;
import java.util.stream.Collectors;
import org.hibernate.search.bridge.StringBridge;

public class RoomCollectionBridge implements StringBridge {
  @Override
  public String objectToString(Object object) {
    @SuppressWarnings("unchecked")
    Collection<RoomEntity> rooms = (Collection<RoomEntity>) object;
    String returnString = String.join(" ", rooms.stream().map(RoomEntity::getName).collect(Collectors.toList()));
    return returnString;
  }

}
