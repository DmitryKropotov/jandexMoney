package com.app.service;

import com.app.model.Payment;
import com.app.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public void uploadPayments(List<Payment> payments) {
        paymentRepository.uploadPayments(payments);
    }

    @Override
    public int sumSpentMoney(int senderId) {
        return paymentRepository.sumSpentMoney(senderId);
    }
}
