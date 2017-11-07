package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Laszlo on 03.11.2017 at 13:47.
 */
@Entity(name = "Room")
//@Data
public class Room implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    private Integer id;

    @Version
    @Getter
    private Date version;

///////////////////////////////

    @Getter @Setter
    @Column(name = "rName")
    private String name;

    @Getter @Setter
    @Column(name = "rMaxPers")
    private Byte maxPersons;

    @Getter @Setter
    @Column(name = "rPriceEZ")
    private Float priceEZ;

    @Getter @Setter
    @Column(name = "rPriceDZ")
    private Float priceDZ;

    @Getter @Setter
    @Column(name = "rPrice3Z")
    private Float price3Z;

    @Getter @Setter
    @Column(name = "rPriceEZ1K")
    private Float priceEZ1K;

    @Getter @Setter
    @Column(name = "rPriceEZ2K")
    private Float priceEZ2K;

    @Getter @Setter
    @Column(name = "rPriceDZ1K")
    private Float priceDZ1K;

    @Lob
    @Column(name = "rNote")
    @Getter @Setter
    private String roomNotes;

}
