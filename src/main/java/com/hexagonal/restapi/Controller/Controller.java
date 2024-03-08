package com.hexagonal.restapi.Controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hexagonal.restapi.Database.CustomerRepository;
import com.hexagonal.restapi.Model.CustomerEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "/api", produces = {"application/json"})
@Tag(name = "Clientes")
@RestController
public class Controller {
    private final CustomerRepository customerRepository;

    public Controller(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Operation(summary = " Busca de Cliente por ID")
    @GetMapping("/customer/{customerId}")
    public CustomerEntity getCustomerInfo(@PathVariable String customerId) {
        Optional<CustomerEntity> customerInfo = customerRepository.findById(customerId);
        CustomerEntity result = new CustomerEntity(customerInfo.get().getId(), customerInfo.get().getName(),
                customerInfo.get().getPhone(), customerInfo.get().getCompanyName());

        return result;
    }
    @Operation(summary = " Criar Novo Cliente")
    @PostMapping("/customer")
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customerEntity) {

        CustomerEntity customerSaved = customerRepository.save(customerEntity);

        return new ResponseEntity<>(customerSaved, HttpStatus.CREATED);
    }

}
