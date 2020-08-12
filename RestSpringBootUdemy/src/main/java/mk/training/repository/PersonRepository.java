package mk.training.repository;

import mk.training.data.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

  @Query("UPDATE Person p SET p.enable = false where p.id = :id")
  void disableUser(@Param("id") Long id);
}
