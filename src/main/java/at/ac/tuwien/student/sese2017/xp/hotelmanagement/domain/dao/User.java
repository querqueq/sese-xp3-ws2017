package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Laszlo on 04.11.2017 at 19:38.
 */
@Entity(name = "loginData")
@NamedQueries(
        @NamedQuery(name = "user.byName", query = "SELECT u FROM Customer u WHERE uName like :uName")
)
public class User implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Integer id;

    @Version
    @Getter
    private Date version;

///////////////////////////////

    @Getter @Setter
    @Column(name = "uName")
    private String name;

    @Getter @Setter
    @Column(name = "uPass")
    private String passHash;

    @Getter @Setter
    @Column(name = "uPriv")
    private PrivLevel uPriv;

}
