package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.util.Arrays;
import java.util.List;

public enum JobTitle {
  MANAGER(Role.STAFF, Role.MANAGER),
  DEPUTEE(Role.STAFF, Role.MANAGER),
  RECEPTIONIST(Role.STAFF, Role.SECRETARY),
  CLEANER(Role.STAFF),
  MAINTENANCE(Role.STAFF);
  
  private final List<Role> roles;

  private JobTitle(Role... roles) {
    this.roles = Arrays.asList(roles);
  }

  public List<Role> getRoles() {
    return roles;
  }
}
