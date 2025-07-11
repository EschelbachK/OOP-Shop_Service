package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderMapRepo implements OrderRepoInterface {

    // Bestellungen mit ihrer ID als Schlüssel
    private final Map<Integer, Order> orders = new HashMap<>();

    // Neue Bestellung hinzufügen
    @Override
    public void addOrder(Order order) {
        orders.put(order.orderId(), order);
    }

    // Bestellung mit bestimmter ID entfernen
    @Override
    public boolean removeOrder(int orderId) {
        return orders.remove(orderId) != null;
    }

    // Bestellung mit bestimmter ID suchen (kann leer sein)
    @Override
    public Optional<Order> getOrder(int orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    // Alle Bestellungen als Liste zurückgeben
    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
}
