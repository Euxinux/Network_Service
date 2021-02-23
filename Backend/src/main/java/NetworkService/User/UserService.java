package NetworkService.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserService {
    ValidationParameters validationParameters = new ValidationParameters();

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users")
    public ResponseEntity getUsers() throws JsonProcessingException {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(users));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users/{username}")
    public ResponseEntity getUser(@PathVariable("username") String username) throws JsonProcessingException {
        Optional<User> userByUsername = userRepository.findByUsername(username);
        return  ResponseEntity.ok(objectMapper.writeValueAsString(userByUsername));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody User user) {
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB.isPresent())
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody User user) throws JsonProcessingException {
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB.isPresent() && correctPassword(userFromDB,user))
            return  ResponseEntity.ok(objectMapper.writeValueAsString(userFromDB));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO) throws JsonProcessingException {
        Optional<User> userFromDB = userRepository.findByUsername(userDTO.getUsername());
        validationParameters.validation(userDTO);
        if(userFromDB.isEmpty() && validationParameters.isCorrect())
        {
            User savedUser = new User(userDTO.getUsername(),userDTO.getPassword());
            userRepository.save(savedUser);
            return ResponseEntity.ok(objectMapper.writeValueAsString(validationParameters.getErrorCode()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/users/password")
    public ResponseEntity changePassword(@RequestHeader("username") String username, @RequestBody String[] passwords) {
        Optional<User> userFromDB = userRepository.findByUsername(username);
        User user = new User(userFromDB.get().getId(),userFromDB.get().getUsername(),passwords[1]);
        UserDTO userDTO = new UserDTO(username,passwords[1],passwords[2]);

        if (userFromDB.isPresent() && userFromDB.get().getPassword().equals(passwords[0]))
            if (validationParameters.validation(userDTO).isCorrect){
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    private boolean correctPassword(Optional<User> userFromDB, User user) {
        if (user.getPassword().equals(userFromDB.get().getPassword()))
            return  true;
        return false;
    }



}
