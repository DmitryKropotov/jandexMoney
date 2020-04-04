package com.app.controller;

import com.app.model.Payment;
import com.app.model.User;

import java.util.List;

public interface PaymentController {

    void uploadPayments(int id, int senderId, int receiverId, int number);

    int sumSpentMoney(int senderId);
}
