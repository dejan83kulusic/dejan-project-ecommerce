package com.dejan.ecommerce.service;

import com.dejan.ecommerce.dao.CustomerRepository;
import com.dejan.ecommerce.dto.Purchase;
import com.dejan.ecommerce.dto.PurchaseResponse;
import com.dejan.ecommerce.entity.Customer;
import com.dejan.ecommerce.entity.Order;
import com.dejan.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;


@Service
public class CheckoutServiceImpl implements CheckoutService{
    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {


        Order order = purchase.getOrder();


        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);


        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));


        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer = purchase.getCustomer();

        String theEmail=customer.getEmail();
        Customer customerFromDb=customerRepository.findByEmail(theEmail);

        if(customerFromDb != null){
            customer=customerFromDb;
        }

        customer.add(order);


        customerRepository.save(customer);


        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {


        return UUID.randomUUID().toString();
    }
}
