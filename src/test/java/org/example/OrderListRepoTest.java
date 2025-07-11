package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    private OrderListRepo orderRepo;

    // Vor jedem Test: Neues OrderListRepo anlegen
    @BeforeEach
    void setUp() {
        orderRepo = new OrderListRepo();
    }

    // Bestellung hinzufügen und prüfen, ob sie gespeichert wurde
    @Test
    void addOrder() {
        Order order = new Order(1, "Kunde", "2025-07-11", new ArrayList<>());
        orderRepo.addOrder(order);

        List<Order> allOrders = orderRepo.getAllOrders();
        assertEquals(1, allOrders.size(), "Eine Bestellung sollte gespeichert sein");
        assertEquals(order, allOrders.get(0), "Gespeicherte Bestellung stimmt");
    }

    // Bestellung entfernen und prüfen, ob sie weg ist
    @Test
    void removeOrder() {
        Order order = new Order(1, "Kunde", "2025-07-11", new ArrayList<>());
        orderRepo.addOrder(order);

        boolean removed = orderRepo.removeOrder(1);
        assertTrue(removed, "Bestellung sollte entfernt werden");

        boolean removedAgain = orderRepo.removeOrder(1);
        assertFalse(removedAgain, "Bereits entfernte Bestellung kann nicht nochmal entfernt werden");
    }

    // Bestellung anhand der ID suchen (vorhanden & nicht vorhanden)
    @Test
    void getOrder() {
        Order order = new Order(1, "Kunde", "2025-07-11", new ArrayList<>());
        orderRepo.addOrder(order);

        Optional<Order> found = orderRepo.getOrder(1);
        assertTrue(found.isPresent(), "Bestellung sollte gefunden werden");
        assertEquals(order, found.get(), "Gefundene Bestellung stimmt");

        Optional<Order> notFound = orderRepo.getOrder(99);
        assertFalse(notFound.isPresent(), "Nicht vorhandene Bestellung wird nicht gefunden");
    }

    // Alle Bestellungen abrufen und Anzahl prüfen
    @Test
    void getAllOrders() {
        // Anfangs sollte keine Bestellung vorhanden sein
        assertTrue(orderRepo.getAllOrders().isEmpty(), "Anfangs keine Bestellungen");

        Order order1 = new Order(1, "Kunde1", "2025-07-11", new ArrayList<>());
        Order order2 = new Order(2, "Kunde2", "2025-07-12", new ArrayList<>());

        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);

        List<Order> allOrders = orderRepo.getAllOrders();
        assertEquals(2, allOrders.size(), "Zwei Bestellungen sollten vorhanden sein");
        assertTrue(allOrders.contains(order1), "Bestellung 1 sollte dabei sein");
        assertTrue(allOrders.contains(order2), "Bestellung 2 sollte dabei sein");
    }
}
