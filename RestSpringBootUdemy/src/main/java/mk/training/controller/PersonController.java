package mk.training.controller;

import io.swagger.annotations.ApiOperation;
import mk.training.data.vo.PersonVO;
import mk.training.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  private PersonService personService;


  @ApiOperation(value = "Create new Person")
  @PostMapping()
  public PersonVO createNewPerson(@RequestBody PersonVO person) {
    PersonVO personVO = personService.create(person);
    personVO.add(linkTo(methodOn(PersonController.class).getPersonById(personVO.getKey())).withSelfRel());
    return personVO;
  }

  @ApiOperation(value = "Get all person")
  @GetMapping
  public ResponseEntity<PagedModel<PersonVO>> getAllPerson(
          @RequestParam(value = "page", defaultValue = "0") int page,
          @RequestParam(value = "limit", defaultValue = "12") int limit,
          @RequestParam(value = "direction", defaultValue = "asc") String direction,
          PagedResourcesAssembler assembler
  ) {
    Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "id"));
    Page<PersonVO> persons = personService.findAll(pageable);
    persons
            .stream()
            .forEach(p ->
                    p.add(linkTo(methodOn(PersonController.class).
                            getPersonById(p.getKey())).withSelfRel()));

    return new ResponseEntity<>(assembler.toModel(persons), HttpStatus.OK);
  }

  @ApiOperation(value = "Get person by id")
  @GetMapping("/{id}")
  public PersonVO getPersonById(@PathVariable("id") Long id) {
    PersonVO personVO = personService.findById(id);
    personVO.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());

    return personVO;
  }

  @ApiOperation(value = "disable person by id")
  @PatchMapping("/{id}")
  public PersonVO disablePerson(@PathVariable("id") Long id) {
    PersonVO personVO = personService.disablePerson(id);
    personVO.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());

    return personVO;
  }

  @ApiOperation(value = "Update person")
  @PutMapping
  public PersonVO updatePerson(@RequestBody PersonVO person) {
    PersonVO personVO = personService.update(person);
    personVO.add(linkTo(methodOn(PersonController.class).getPersonById(personVO.getKey())).withSelfRel());
    return personVO;
  }

  @ApiOperation(value = "Delete person by id")
  @DeleteMapping("/{id}")
  public void deletePersonById(@PathVariable("id") Long id) {
    personService.delete(id);
  }
}
