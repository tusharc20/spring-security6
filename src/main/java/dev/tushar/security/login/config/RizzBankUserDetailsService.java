package dev.tushar.security.login.config;

import dev.tushar.security.login.entity.Customer;
import dev.tushar.security.login.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RizzBankUserDetailsService implements UserDetailsService {

    @Autowired
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName,  password;
        List<GrantedAuthority> authorities = null;
        List<Customer> customers = customerRepository.findByEmail(username);
        if(customers.isEmpty()){
            throw new UsernameNotFoundException("no user found this email");
        }
        else {
            userName = customers.get(0).getEmail();
            password = "{noop}"+customers.get(0).getPassword();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+customers.get(0).getRole()));
        }
        return new User(userName, password, authorities);
    }
}
