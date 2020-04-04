package com.app.controller;

import com.app.model.Payment;
import com.app.model.User;
import com.app.service.PaymentService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log
public class PaymentControllerImpl implements PaymentController {

    @Autowired
    PaymentService paymentService;

    @Override
    @RequestMapping(value = "/loadPayments", method = RequestMethod.GET)
    public void uploadPayments(int id, int senderId, int receiverId, int number) {
        log.info("uploadPayments method");
        Payment payment = new Payment(id, senderId, receiverId, number);
        log.info(payment + "");
        List<Payment> list = new ArrayList();
        list.add(payment);
        paymentService.uploadPayments(list);
    }

    @Override
    @RequestMapping(value = "/sumSpentMoney", method = RequestMethod.GET)
    public int sumSpentMoney(int senderId) {
        System.out.println("sumSpentMoney method");
        return paymentService.sumSpentMoney(senderId);
    }
}
