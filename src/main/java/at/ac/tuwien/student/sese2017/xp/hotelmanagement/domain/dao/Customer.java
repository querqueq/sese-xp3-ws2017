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
 * Created by Laszlo on 03.11.2017 at 13:07.
 */
@SuppressWarnings("unused")
@Entity(name = "Customer")
@NamedQueries(
        @NamedQuery(name = "customer.byName", query = "SELECT c FROM Customer c WHERE uName like :uName")
)
//@Data
public class Customer implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Integer id;

    @Version
    @Getter
    private Date version;

//////////////////////////////////////////////////////

    @Getter @Setter
    @Column(name="uName", nullable = false)
    private String name;

    @Getter @Setter
    @Column(name="uBirth", nullable = false)
    private Date birthDay;

    @Getter @Setter
    @Column(name = "uGender", nullable = false)
    private Gender gender;

    @Getter @Setter
    @Column(name = "uBillAddr", nullable = false)
    private String billAddress;

    @Getter @Setter
    @Column(name = "uCompName")
    private String companyAddress;

    @Transient
    private transient String note = null;

    // column in getter
    private Blob noteBlob;

    @Getter @Setter
    @Column
    private Float rabat;

    @Getter @Setter
    @Column(name = "uPhoneNr", nullable = false)
    private String phoneNr;

    @Getter @Setter
    @Column(name = "uMail", nullable = false)
    private String eMail;

    @Getter @Setter
    @Column(name = "uWeb")
    private String webAddr;

    @Getter @Setter
    @Column(name = "uFAX")
    private String fax;

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
