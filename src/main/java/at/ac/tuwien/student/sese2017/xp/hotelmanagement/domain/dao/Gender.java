package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dao;

/**
 * Created by Laszlo on 03.11.2017 at 13:20.
 */
public enum Gender {
    female ("Weiblich"),
    male ("Maenlich");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
