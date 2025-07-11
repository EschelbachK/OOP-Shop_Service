package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderListRepo implements OrderRepoInterface {

    // Liste zum Speichern der Bestellungen
    private final List<Order> orders = new ArrayList<>();

    @Override
    public void addOrder(Order order) {
        // Bestellung hinzufügen
        orders.add(order);
    }

    @Override
    public boolean removeOrder(int orderId) {
        // Bestellung anhand der ID entfernen
        return orders.removeIf(order -> order.orderId() == orderId);
    }

    @Override
    public Optional<Order> getOrder(int orderId) {
        // Bestellung anhand der ID suchen
        return orders.stream()
                .filter(order -> order.orderId() == orderId)
                .findFirst();
    }

    @Override
    public List<Order> getAllOrders() {
        // Alle Bestellungen zurückgeben
        return new ArrayList<>(orders);
    }
}
