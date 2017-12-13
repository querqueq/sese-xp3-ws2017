package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.lang.Nullable;
import lombok.Data;

@Data
@Entity
public class UserEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @Column //FIXME make unique and not nullable
  private String username;
  
  @Column
  private String password;
  
  @Enumerated
  @ElementCollection
  private List<Role> roles;
}
