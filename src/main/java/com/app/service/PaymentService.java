package com.app.service;

import com.app.model.Payment;
import com.app.model.User;

import java.util.List;

public interface PaymentService {

    void uploadPayments(List<Payment> paymentnts);

    int sumSpentMoney(int senderId);
}

