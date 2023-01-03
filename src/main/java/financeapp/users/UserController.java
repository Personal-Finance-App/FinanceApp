package financeapp.users;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Use this if u don't have opportunity to connect a real bank")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<UserWrapper> createPerson(@RequestBody DataForRegister data) {
        CustomUser customUser = new CustomUser(data.getEmail(), data.getPassword(), data.getFirstName()
                , data.getLastName(), data.getMiddleName());
        if (userService.saveUser(customUser))
            return ResponseEntity.ok().body(customUser.toWrapper());
        return null;
    }


    //Прежде чем отправить форму регистрации с помощью AJAX проверить доступность почты.
    @PostMapping("/checkEmail")
    @ResponseBody
    public ResponseEntity<?> checkEmail(@RequestBody EmailCheck data) {
        return ResponseEntity.ok().body(userService.checkEmailAvailability(data.getEmail()));
    }

}

@Data
class DataForRegister {
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
}

@Data
class EmailCheck {
    private String email;
}
