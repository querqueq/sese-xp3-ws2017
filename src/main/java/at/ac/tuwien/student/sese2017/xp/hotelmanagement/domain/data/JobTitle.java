package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.util.Arrays;
import java.util.List;

public enum JobTitle {
  MANAGER("Verwaltung", Role.STAFF, Role.MANAGER),
  DEPUTEE("Stellvertretende Verwaltung", Role.STAFF, Role.MANAGER),
  RECEPTIONIST("Rezeption", Role.STAFF, Role.SECRETARY),
  CLEANER("Reinigung", Role.STAFF),
  MAINTENANCE("Wartung", Role.STAFF);
  
  private final List<Role> roles;
  private final String display;

  private JobTitle(String display, Role... roles) {
    this.display = display;
    this.roles = Arrays.asList(roles);
  }

  public List<Role> getRoles() {
    return roles;
  }

  public String getDisplay() {
    return display;
  }
}
