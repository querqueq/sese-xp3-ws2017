package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by Laszlo on 03.11.2017 at 13:36.
 */
@SuppressWarnings("WeakerAccess")
public class Utils {
    public static String fromBlob(Blob blob) {
        try {
            InputStream inputStream = blob.getBinaryStream();
            StringBuilder sb = new StringBuilder();
            int size;
            byte [] buffer = new byte[2_000];
            while ((size = inputStream.read(buffer)) >0) {
                sb.append(new String(buffer, 0, size));
            }
            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
