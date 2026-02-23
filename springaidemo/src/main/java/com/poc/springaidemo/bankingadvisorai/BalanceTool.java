package com.poc.springaidemo.bankingadvisorai;

import com.poc.springaidemo.bankingadvisorai.Customer;
import com.poc.springaidemo.bankingadvisorai.CustomerRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalanceTool {

    @Autowired
    CustomerRepository customerRepository;


    @Tool(name = "getBalance")
    public String getBalance(@ToolParam(description = "customerId") String customerId) {
        Customer customer = customerRepository.findByCustomerId(Integer.parseInt(customerId));
        if (customer != null) {
            return "The current balance for customer " + customerId + " is ₹" + customer.getBalance();
        } else {
            return "Customer with ID " + customerId + " not found.";
        }
    }
}
