package at.ac.tuwien.student.sese2017.xp.hotelmanagement;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Wrapper for integration test initialisation classes
 */
public final class HotelManagementApplicationTests {

    /**
     * Latest version
     */
    public static abstract class Default extends V1{

    }

    /**
     * Run with Testdata V1
     */
    @RunWith(SpringRunner.class)
    @SpringBootTest(properties = {"appconfig.testdataVersion=1"})
    public static abstract class V1 {

    }

    /**
     * Run with Testdata V2
     */
    @RunWith(SpringRunner.class)
    @SpringBootTest(properties = {"appconfig.testdataVersion=2"})
    public static abstract class V2 {

    }

}
