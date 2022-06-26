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
        CustomUser customUserFromDB = userRepo.findUserByUsername(customUser.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (customUserFromDB != null)
            return false;

        String notHashedPassword = customUser.getPassword();
        customUser.setPassword(passwordEncoder.encode(notHashedPassword));
        userRepo.save(customUser);
        return true;

    }

    public CustomUser findUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = userRepo.findUserByUsername(username);
        if (customUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        return customUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser myUser = userRepo.findUserByUsername(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        UserDetails user = User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
               // .roles(myUser.getRole().getName())
                .build();
        return user;
    }
}
