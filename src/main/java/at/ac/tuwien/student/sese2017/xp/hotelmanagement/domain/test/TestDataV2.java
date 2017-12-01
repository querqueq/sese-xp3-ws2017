package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test;


/**
 * Version 2 of the test data.
 * <p>
 *   Object wrapper for test data instances
 * </p>
 */
public class TestDataV2 extends TestDataV1 {
  /*
   ALL fields with Entity objects get injected into the database by TestDataInjectorService

   The order in which the entities get injected can be specified with the @Order annotation
   */

  public TestDataV2() {
    // Edit existing data or create new
    this.ROOM_2.setName("Other Name");
  }

  @Override
  public int getTestdataVersion() {
    return 2;
  }
}
