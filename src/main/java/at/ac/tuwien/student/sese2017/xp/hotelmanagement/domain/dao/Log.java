package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dao;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Laszlo on 04.11.2017 at 20:07.
 */
@Entity(name ="Log")
public class Log implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Integer id;

    @Version
    @Getter
    private Date version;

///////////////////////////////

    @Getter @Setter
    @Column(name = "causingUser")
    private User name;


    @Transient
    private transient String note = null;

    // column in getter
    private Blob noteBlob;

    @Column(name = "uNote")
    public String getNote() {
        if (note == null) {
            note = Utils.fromBlob(noteBlob);
        }
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        try {
            noteBlob = new SerialBlob(note.getBytes());
        } catch (SQLException e) {
            e.printStackTrace();
//            TODO : handle exception
        }
    }

}
