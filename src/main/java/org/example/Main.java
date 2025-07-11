package org.example;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ProductRepo produktListe = new ProductRepo();

        // Entweder OrderListRepo oder OrderMapRepo verwenden
        // OrderRepoInterface bestellListe = new OrderListRepo();
        OrderRepoInterface bestellListe = new OrderMapRepo();

        // ShopService bekommt das Interface
        ShopService shopService = new ShopService(produktListe, bestellListe);

        // Produkte hinzufügen
        produktListe.addProduct(new Product(1, "Apfel", 0.99));
        produktListe.addProduct(new Product(2, "Brot", 2.49));
        produktListe.addProduct(new Product(3, "Milch", 1.29));
        produktListe.addProduct(new Product(4, "Butter", 1.99));

        // Alle Produkte ausgeben (getAllProducts)
        System.out.println("Alle Produkte:");
        for (Product p : produktListe.getAllProducts()) {
            System.out.println(p.id() + ": " + p.name() + " (" + p.price() + " EUR)");
        }

        // Produkt mit ID 3 holen (getProductById)
        System.out.println("\nProdukt mit ID 3:");
        Product produkt3 = produktListe.getProductById(3);
        System.out.println(produkt3.name() + " kostet " + produkt3.price() + " EUR");

        // Prüfen, ob Produkt mit ID 5 existiert (existsById)
        System.out.println("\nExistiert Produkt mit ID 5? " + produktListe.existsById(5));

        // Produkt mit ID 3 aktualisieren (updateProduct)
        boolean updateErfolg = produktListe.updateProduct(new Product(3, "Milch", 1.59));
        System.out.println("Produkt 3 aktualisiert? " + updateErfolg);

        // Produkt mit ID 2 entfernen (removeProductById)
        boolean entfernt = produktListe.removeProductById(2);
        System.out.println("Produkt 2 entfernt? " + entfernt);

        // Bestellung mit gültigen Produkt-IDs aufgeben (placeOrder)
        shopService.placeOrder(List.of(1, 3));

        // Bestellung mit ungültiger Produkt-ID aufgeben (placeOrder)
        // Produkt 5 existiert nicht
        shopService.placeOrder(List.of(1, 5));

        // Alle Bestellungen ausgeben mit neuer Methode
        shopService.printAllOrders();

        // Bestellung mit ID 1 holen (getOrder)
        Optional<Order> bestellung1 = bestellListe.getOrder(1);
        System.out.println("\nBestellung mit ID 1 vorhanden? " + bestellung1.isPresent());

        // Bestellung mit ID 1 entfernen (removeOrder)
        boolean entferntBestellung = bestellListe.removeOrder(1);
        System.out.println("Bestellung 1 entfernt? " + entferntBestellung);

        // Alle Bestellungen nach Entfernung ausgeben
        System.out.println("\nBestellungen nach Entfernung der Bestellung 1:");
        shopService.printAllOrders();
    }
}
