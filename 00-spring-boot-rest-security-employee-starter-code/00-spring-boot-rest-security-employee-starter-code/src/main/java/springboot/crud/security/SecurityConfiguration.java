package springboot.crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Bean
    public UserDetailsManager UserDetailsManager(){
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(User.withDefaultPasswordEncoder().username("Dipraj").password("Kumar@123").roles("Employee").build());

        userDetailsManager.createUser(User.withDefaultPasswordEncoder().username("Ram").password("Ram@123").roles("Employee","Manager").build());

        userDetailsManager.createUser(User.withDefaultPasswordEncoder().username("Shyam").password("Shyam@123").roles("Employee","Manager","Admin").build());

        return userDetailsManager;
    }

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configure -> configure.requestMatchers(HttpMethod.GET,"/api/employees").hasRole("Employee"));
        http.authorizeHttpRequests(configure -> configure.requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("Employee"));
        http.authorizeHttpRequests(configure -> configure.requestMatchers(HttpMethod.POST,"/api/employees/").hasRole("Manager"));
        http.authorizeHttpRequests(configure -> configure.requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("Manager"));
        http.authorizeHttpRequests(configure -> configure.requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("Admin"));

        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf->csrf.disable());
        return http.build();
    }




}
