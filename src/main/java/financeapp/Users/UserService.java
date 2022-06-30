package financeapp.Users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    public boolean saveUser(CustomUser customUser){
        CustomUser customUserFromDB = userRepo.findCustomUserByEmail(customUser.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (customUserFromDB != null)
            return false;

        String notHashedPassword = customUser.getPassword();
        customUser.setPassword(passwordEncoder.encode(notHashedPassword));
        userRepo.save(customUser);
        return true;

    }

    public CustomUser findUserByEmail(String email) throws UsernameNotFoundException {
        CustomUser customUser = userRepo.findCustomUserByEmail(email);
        if (customUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + email);
        }
        return customUser;
    }


    public Boolean checkEmailAvailability(String email) {
        CustomUser customUser = userRepo.findCustomUserByEmail(email);
        if (customUser == null)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser myUser = userRepo.findCustomUserByEmail(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        return User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles("USER")
                .build();
    }
}
