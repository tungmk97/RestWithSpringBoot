package mk.training.controller;

import mk.training.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

  @GetMapping("/sum/{numberOne}/{numberTwo}")
  public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) {
    if(!isNumber(numberOne) || !isNumber(numberTwo)) {
      throw new ResourceNotFoundException("pls set a number values");
    }
    Double sum = convertToDouble(numberOne) + convertToDouble(numberTwo);
    
    return sum;
  }

  private Double convertToDouble(String numberOne) {
    double doubleNumber = Double.parseDouble(numberOne);
    return doubleNumber;
  }

  private boolean isNumber(String numberOne) {
    if (numberOne == null) {
      return false;
    }
    return true;
  }
}
