package org.com.shipment;

import org.com.shipment.model.Shipment;

public interface Notifier {
    void send(Shipment shipment);
}
