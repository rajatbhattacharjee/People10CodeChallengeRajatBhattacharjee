package com.rajatbhattacharjee.springboot;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import org.springframework.stereotype.Component;
import com.rajatbhattacharjee.springboot.Customer;


@Component
public class CustomerService {
	
	static List<Customer> customers = new ArrayList<Customer>();
	static {
		customers.add(new Customer(1,"jhamilton0@usda.gov","Joshua","Hamilton","135.75.95.238",-27.634171,-52.273891,"2015-01-21 03:20:11", null));
		customers.add(new Customer(2,"anichols1@addtoany.com","Anthony","Nichols","147.3.15.197",56.476330,53.797821,"2015-01-16 00:10:27", null));
		customers.add(new Customer(3,"lmurphy2@sina.com.cn","Lawrence","Murphy","70.210.188.75",16.583330,121.500000,"2015-01-20 20:19:37", null));
		customers.add(new Customer(4,"sburke3@lycos.com","Scott","Burke","104.203.83.37",37.692478,120.971481,"2015-01-01 13:43:44", null));
		customers.add(new Customer(5,"hjackson4@nsw.gov.au","Howard","Jackson","59.197.227.237",37.578499,26.480690,"2015-01-27 13:43:37", null));
	}

    public boolean isCustomerExist(int customerId) {
		for (Customer cust: customers) {
			if (customerId == cust.getId())
				return true;
		}
		return false;
	}
	
	public Customer retrieveCustomerDetails(int customerId) {
		for (Customer cust: customers) {
			if (customerId == cust.getId()) {
				return cust;
			}
		}
		return null;
	}

	public void saveCustomer(Customer customerObj) {
		customers.add(customerObj);
	}

    public void deleteCustomerById (int customerId) {
		for (Customer cust: customers) {
			if (customerId == cust.getId()) {
				customers.remove(cust);
			}
		}
	}
}