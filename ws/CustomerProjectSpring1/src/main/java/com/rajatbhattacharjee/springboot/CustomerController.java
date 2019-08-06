package com.rajatbhattacharjee.springboot;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rajatbhattacharjee.springboot.Customer;
import com.rajatbhattacharjee.springboot.CustomErrorType;
import com.rajatbhattacharjee.springboot.CustomerService;

@RestController
class CustomerController {
	@Autowired
	private CustomerService customerService;
	public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@GetMapping("/customer/{customerId}")
	public Customer searchCustomerDetails(@PathVariable int customerId) {
		return customerService.retrieveCustomerDetails(customerId);
	}

	// Create Customer
    @RequestMapping(value = "/customer/", method = RequestMethod.POST)
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Customer : {}", customer);
 
        if (customerService.isCustomerExist(customer.getId())) {
        	String name = customer.getFirst_name() + customer.getLast_name();
            logger.error("Unable to create. A Customer with name {} already exist", name);
            return new ResponseEntity<Object>(new CustomErrorType("Unable to create. A Customer with name " + 
            		name + " already exist."),HttpStatus.CONFLICT);
        }
        customerService.saveCustomer(customer);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/customer/{id}").buildAndExpand(customer.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	// Update Customer
	@RequestMapping(value = "/customer/{customerId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@PathVariable("customerId") int customerId, @RequestBody Customer customer) {

        logger.info("Updating Customer with id {}", customerId);
        Customer cust = customerService.retrieveCustomerDetails(customerId);

        if (cust == null) {
            logger.error("Unable to update. Customer with id {} not found", customerId);
            return new ResponseEntity<Object>(new CustomErrorType("Unable to update. Customer with id " + customerId + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        cust.setEmail(customer.getEmail());
        cust.setFirst_name(customer.getFirst_name());
        cust.setLast_name(customer.getLast_name());
        cust.setIp(customer.getIp());
        cust.setLatitude(customer.getLatitude());
        cust.setLongitude(customer.getLongitude());
        cust.setCreated_at(customer.getCreated_at());
        cust.setUpdated_at(customer.getUpdated_at());

		return new ResponseEntity<Customer>(cust, HttpStatus.OK);
    }

    // Delete a customer
    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") int customerId) {
        logger.info("Fetching & Deleting Customer with id {}", customerId);
 
        Customer cust = customerService.retrieveCustomerDetails(customerId);
        
        if (cust == null) {
            logger.error("Unable to delete. Customer with id {} not found", customerId);
            return new ResponseEntity<Object>(new CustomErrorType("Unable to delete. Customer with id " + customerId + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }
}