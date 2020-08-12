package mk.training.data.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 80)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 80)
  private String lastName;

  @Column(nullable = false, length = 100)
  private String address;

  @Column(nullable = false, length = 6)
  private String gender;

  @Column(length = 6, columnDefinition = "tinyint default 1")
  private Boolean enable;

  public Person() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return id == person.id &&
            Objects.equals(firstName, person.firstName) &&
            Objects.equals(lastName, person.lastName) &&
            Objects.equals(address, person.address) &&
            Objects.equals(gender, person.gender);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender);
  }
}
