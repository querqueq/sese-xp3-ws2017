package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

public enum PriceType {
  SINGLE("Einzel"),
  DOUBLE("Doppel"),
  TRIPLE("Tripel"),
  SINGLE_WITH_CHILD("Einzel mit Kind"),
  SINGLE_WITH_TWO_CHILDREN("Einzel mit zwei Kindern"),
  DOUBLE_WITH_CHILD("Doppel mit Kind");

  private final String text;

  PriceType(final String text) {
    this.text = text;
  }

  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return text;
  }
}
