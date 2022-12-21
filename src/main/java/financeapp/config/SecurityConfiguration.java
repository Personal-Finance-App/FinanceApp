package financeapp.config;


import financeapp.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService);
//    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // TODO: Настроить в будущем права доступа
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/accounts").fullyAuthenticated()
                .antMatchers("/tinkof/*").fullyAuthenticated()
                .antMatchers("/sberbank/*").fullyAuthenticated()
                .antMatchers("/sberbank/**").fullyAuthenticated()
                .antMatchers("/reports").fullyAuthenticated()
                .antMatchers("/report/**").fullyAuthenticated()
                .antMatchers("/goals/**").fullyAuthenticated()
                .antMatchers("/goals").fullyAuthenticated()
                .antMatchers("/monthTransaction/**").fullyAuthenticated()
                .antMatchers("/category-history/**").fullyAuthenticated()
                .antMatchers("/new-plan/**").fullyAuthenticated()
                .antMatchers("/edit-plan/**").fullyAuthenticated()
                .and().formLogin();
        return http.build();
    }


}