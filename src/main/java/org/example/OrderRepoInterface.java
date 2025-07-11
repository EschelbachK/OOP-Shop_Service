package org.example;

import java.util.List;
import java.util.Optional;

public interface OrderRepoInterface {

    // Bestellung zur Liste hinzufügen
    void addOrder(Order order);

    // Bestellung anhand der ID entfernen
    boolean removeOrder(int orderId);

    // Bestellung anhand der ID suchen (Optional, da evtl. nicht vorhanden)
    Optional<Order> getOrder(int orderId);

    // Alle Bestellungen als Liste zurückgeben
    List<Order> getAllOrders();
}
