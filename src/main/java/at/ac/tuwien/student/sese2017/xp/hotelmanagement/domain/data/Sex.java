package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

public enum Sex {
  MALE("Männlich"),
  FEMALE("Weiblich"),
  MISCELLANEOUS("??!!??!!?!")
  ;
  
  private final String display;

  private Sex(String display) {
    this.display = display;
  }

  public String getDisplay() {
    return display;
  }
}
