package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

public enum Sex {
  MALE("M\u00e4nnlich"),  // Maenlich
  FEMALE("Weiblich"),
  MISCELLANEOUS("Nicht bin\u00e4r") // Nicht binaer
  ;
  
  private final String display;

  private Sex(String display) {
    this.display = display;
  }

  public String getDisplay() {
    return display;
  }
}
