package com.app.repository;

import com.app.model.Payment;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;
import org.hibernate.*;

import javax.persistence.PersistenceException;
import java.util.List;

@Repository
@Log
public class PaymentRepositoryImpl implements PaymentRepository {

    private Session session;

    public PaymentRepositoryImpl(SessionFactory sessionFactory) {
        this.session = sessionFactory.openSession();
    }

    @Override
    public void uploadPayments(List<Payment> payments) {
        payments.forEach(payment -> {
            try {
                Transaction txn = session.beginTransaction();
                session.save(payment);
                txn.commit();
            } catch (PersistenceException e) {
                log.info(e + "");
            }
        });
    }

    @Override
    public int sumSpentMoney(int senderId) {
        String sql = "from Payment where senderId = " + senderId;
        Query query = session.createQuery(sql, Payment.class);
        return query.getResultList().size();
    }
}
