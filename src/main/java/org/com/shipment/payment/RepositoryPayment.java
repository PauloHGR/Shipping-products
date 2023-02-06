package org.com.shipment.payment;

import org.com.shipment.model.Payment;

public interface RepositoryPayment {
    void save(Payment payment);
}
