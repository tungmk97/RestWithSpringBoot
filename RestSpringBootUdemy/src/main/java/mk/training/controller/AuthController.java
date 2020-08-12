package mk.training.controller;

import io.swagger.annotations.ApiOperation;
import mk.training.data.model.User;
import mk.training.repository.UserRepository;
import mk.training.security.AccountCredentialsVO;
import mk.training.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  UserRepository userRepository;

  @ApiOperation(value = "/Authorize by user")
  @PostMapping(value = "/signin")
  public ResponseEntity signin(@RequestBody AccountCredentialsVO userCredentialsVO) {
    try {
      String username = userCredentialsVO.getUsername();
      String password = userCredentialsVO.getPassword();
      User user = userRepository.findByUsername(username);
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      String token = "";
      if (user != null) {
        token = jwtTokenProvider.createToken(username, user.getRoles());
      } else {
        throw new UsernameNotFoundException("Username " + username + " not found");
      }

      Map<Object, Object> model = new HashMap<>();
      model.put("username", username);
      model.put("token", token);
      return ResponseEntity.ok(model);

    } catch (AuthenticationException e) {
      e.printStackTrace();
      throw new BadCredentialsException("Invalid username/password");
    }
  }
}
