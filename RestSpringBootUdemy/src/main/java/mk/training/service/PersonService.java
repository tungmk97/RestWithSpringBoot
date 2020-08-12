package mk.training.service;

import mk.training.converter.DozerMapper;
import mk.training.data.vo.PersonVO;
import mk.training.exception.ResourceNotFoundException;
import mk.training.data.model.Person;
import mk.training.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {
  @Autowired
  PersonRepository personRepository;

  public PersonVO create(PersonVO person) {
    Person entity = DozerMapper.parseObject(person, Person.class);
    PersonVO personVO = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);

    return personVO;
  }

  public Page<PersonVO> findAll(Pageable pageable) {
    Page<Person> persons = personRepository.findAll(pageable);

//    List<PersonVO> personVOs = DozerMapper.parseListObjects(persons, PersonVO.class);
    return persons.map(this::converToPersonVO);
  }

  private PersonVO converToPersonVO(Person person) {
    return DozerMapper.parseObject(person, PersonVO.class);
  }

  public PersonVO findById(Long id) {
    Person entity =  personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Record for this ID"));
    return DozerMapper.parseObject(entity, PersonVO.class);
  }

  @Transactional
  public PersonVO disablePerson(Long id) {
    personRepository.disableUser(id);
    Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
    return DozerMapper.parseObject(person, PersonVO.class);
  }

  public PersonVO update(PersonVO person) {
    Person entity = personRepository.findById(person.getKey())
            .orElseThrow(() -> new ResourceNotFoundException("Not Record for this ID"));
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getLastName());
    entity.setGender(person.getGender());

    PersonVO personVO = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);

    return personVO;
  }

  public void delete(Long id) {
    Person person = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not record for this ID"));
    personRepository.delete(person);
  }
}
