package com.app.repository;

import com.app.model.Payment;

import java.util.List;

public interface PaymentRepository {

    void uploadPayments(List<Payment> paymentnts);

    int sumSpentMoney(int senderId);
}


