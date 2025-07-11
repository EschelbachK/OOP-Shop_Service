package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ShopService shopService;
    private TestRepo productRepo;
    private TestOrderRepo orderRepo;

    // Vor jedem Test: neue Instanzen der Fakes und ShopService erzeugen
    @BeforeEach
    void setUp() {
        productRepo = new TestRepo();
        orderRepo = new TestOrderRepo();
        shopService = new ShopService(productRepo, orderRepo);
    }

    // Testet erfolgreiches Anlegen einer Bestellung mit gültigen Produkt-IDs
    @Test
    void placeOrder_successful() {
        // Produkte im Fake-Repo anlegen
        productRepo.addProduct(new Product(1, "Apfel", 0.99));
        productRepo.addProduct(new Product(2, "Banane", 1.29));

        // Bestellung mit gültigen Produkt-IDs aufgeben
        shopService.placeOrder(List.of(1, 2));

        // Prüfen, ob Bestellung im Fake Order-Repo gespeichert wurde
        List<Order> orders = orderRepo.getAllOrders();
        assertEquals(1, orders.size(), "Es sollte genau eine Bestellung geben");

        Order order = orders.get(0);
        assertEquals(1, order.orderId(), "Bestell-ID sollte 1 sein");
        assertEquals("Kai Eschelbach", order.customerName(), "Kundenname stimmt");
        assertEquals(2, order.products().size(), "Bestellung enthält zwei Produkte");
    }

    // Verhalten bei ungültiger Produkt-ID (Produkt nicht vorhanden)
    @Test
    void placeOrder_withInvalidProductId() {
        // Nur ein Produkt im Repo hinzufügen
        productRepo.addProduct(new Product(1, "Apfel", 0.99));

        // Bestellung mit einer gültigen und einer ungültigen Produkt-ID
        shopService.placeOrder(List.of(1, 99));

        // Es sollte keine Bestellung erstellt werden, da Produkt 99 nicht existiert
        List<Order> orders = orderRepo.getAllOrders();
        assertTrue(orders.isEmpty(), "Keine Bestellung sollte angelegt werden");
    }

    // Prüft, ob getAllOrders alle gespeicherten Bestellungen zurückgibt
    @Test
    void getAllOrders() {
        // Leere Bestellungsliste am Anfang
        assertTrue(shopService.getAllOrders().isEmpty(), "Anfangs keine Bestellungen");

        // Bestellung manuell im Fake-Order-Repo anlegen
        Order order = new Order(1, "Kai Eschelbach", "2025-07-09", new ArrayList<>());
        orderRepo.addOrder(order);

        List<Order> orders = shopService.getAllOrders();
        assertEquals(1, orders.size(), "Eine Bestellung vorhanden");
        assertEquals(order, orders.get(0), "Bestellung stimmt überein");
    }

    // Prüft, ob printAllOrders-Ausgabe (hier nur Logik, keine Konsolenausgabeprüfung)
    @Test
    void printAllOrders_withAndWithoutOrders() {
        // Keine Bestellungen vorhanden (nur Logik testen, keine Ausgabe)
        shopService.printAllOrders();

        // Bestellung hinzufügen
        Order order = new Order(1, "Kai Eschelbach", "2025-07-09", List.of(new Product(1, "Apfel", 0.99)));
        orderRepo.addOrder(order);

        // Ausgabe mit Bestellung (ebenfalls nur Logik)
        shopService.printAllOrders();
    }

    // --- Test-Implementierungen für Test ---

    private static class TestRepo extends ProductRepo {
        // Keine Erweiterungen nötig, da ProductRepo schon alles implementiert
    }

    private static class TestOrderRepo implements OrderRepoInterface {
        private final List<Order> orders = new ArrayList<>();

        @Override
        public void addOrder(Order order) {
            orders.add(order);
        }

        @Override
        public boolean removeOrder(int orderId) {
            return orders.removeIf(o -> o.orderId() == orderId);
        }

        @Override
        public Optional<Order> getOrder(int orderId) {
            return orders.stream().filter(o -> o.orderId() == orderId).findFirst();
        }

        @Override
        public List<Order> getAllOrders() {
            return new ArrayList<>(orders);
        }
    }
}
