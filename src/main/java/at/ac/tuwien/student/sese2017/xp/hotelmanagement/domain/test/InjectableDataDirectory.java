package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test;

/**
 * Interface for TestDataDirectory for easy tests.
 *
 * <p>
 *     Initialize all domain objects as public final objects.
 *     The Injector finds them automatically and persists them to DB.
 * </p>
 */
public interface InjectableDataDirectory {
  int getTestdataVersion();
}
