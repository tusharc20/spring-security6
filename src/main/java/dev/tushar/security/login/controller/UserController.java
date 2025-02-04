package dev.tushar.security.login.controller;

import dev.tushar.security.login.entity.Customer;
import dev.tushar.security.login.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;

    private  final PasswordEncoder passwordEncoder;


    @GetMapping("/register")
    public ResponseEntity<String> getUser(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("return user ");
    }

    @PostMapping("/save")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer){

        try{
            String hassPwd = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(hassPwd);
            Customer savedCustomer = customerRepository.save(customer);

            if(savedCustomer.getId() >0){
                return ResponseEntity.status(HttpStatus.CREATED).body("Given user details are saved "+ savedCustomer.getRole());
            }
            else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user creation failed");
            }
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an exception occured :"+ ex.getMessage());
        }
    }
}
