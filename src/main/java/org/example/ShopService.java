package org.example;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    // Zugriff auf Produkt-Repository
    private final ProductRepo productRepo;
    // Zugriff auf Bestell-Repository über Interface
    private final OrderRepoInterface orderRepo;

    // Zähler für eindeutige Bestell-IDs
    private int nextOrderId = 1;

    // Konstruktor mit Produkt- und Bestell-Repository
    public ShopService(ProductRepo productRepo, OrderRepoInterface orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    // Neue Bestellung mit Liste von Produkt-IDs aufgeben
    public void placeOrder(List<Integer> productIds) {
        // Neue Liste anlegen, um die echten Produkt-Objekte zu speichern
        List<Product> productsInOrder = new ArrayList<>();
        // Für jede Produkt-ID in der übergebenen Liste
        for (Integer id : productIds) {
            // Produkt anhand der ID aus dem Produkt-Repository holen
            Product p = productRepo.getProductById(id);
            // Gefundenes Produkt zur Liste hinzufügen (kann auch null sein)
            productsInOrder.add(p);
        }

        // Prüfen, ob ein Produkt nicht existiert (null)
        if (productsInOrder.contains(null)) {
            System.out.println("Mindestens ein Produkt existiert nicht.");
            return;
        }

        // Neue Bestellung mit ID, Kunde, Datum und Produkten erstellen
        Order order = new Order(
                nextOrderId++,
                "Kai Eschelbach",
                "2025-07-09",
                productsInOrder
        );

        // Bestellung speichern
        orderRepo.addOrder(order);
        System.out.println("Bestellung erfolgreich erstellt.");
    }

    // Alle Bestellungen aus dem Repository holen
    public List<Order> getAllOrders () {
        return orderRepo.getAllOrders();
    }

    // Alle Bestellungen ausgeben
    public void printAllOrders() {
        List<Order> orders = getAllOrders();

        // Wenn keine Bestellungen da sind, Nachricht ausgeben
        if (orders.isEmpty()) {
            System.out.println("Aktuell stehen keine Bestellungen zur Verfügung.");
            return;
        }

        System.out.println("\nAlle Bestellungen:");
        for (Order order : orders) {
            System.out.println("Bestell-ID: " + order.orderId() + ", Kunde: " + order.customerName());
            for (Product p : order.products()) {
                System.out.println(" - " + p.name() + " (" + p.price() + " EUR)");
            }
        }
    }
}
