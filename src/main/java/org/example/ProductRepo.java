package org.example;

import java.util.ArrayList;
import java.util.List;

public class ProductRepo {

    // Einen Speicher für die Produkte erstellen
    private final List<Product> products;

    // Konstruktor: Erstellt eine neue, leere Liste für Produkte
    public ProductRepo() {
        this.products = new ArrayList<>();
    }
    // Fügt ein Produkt zur Produktliste hinzu
    public void addProduct(Product product) {
        products.add(product);
    }
    // Gibt eine Kopie der gesamten Produktliste zurück
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    // Suche Produkt mit ID-Nummer
    public Product getProductById(int id) {
        for (Product p : products) {
            if (p.id() == id) {
                return p;
            }
        }
        return null;
    }
    // Entferne ein Produkt aus der Liste anhand seiner ID
    public boolean removeProductById(int id) {
        return products.removeIf(p -> p.id() == id);
    }

    // Produkt aktualisieren, über ID
    public boolean updateProduct(Product updatedProduct) {
        // In Listen verwendet man nicht ".length", sondern ".size"
        // Hier muss man auch alle Produkte durchlaufen, da die ID innerhalb
        // eines Produkts liegt und die ID kein Schlüssel-Index ist!
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).id() == updatedProduct.id()) {
                // Produkt ersetzen
                products.set(i, updatedProduct);
                // Aktualisierung erfolgreich
                return true;
            }
        }
        // Aktualisierung nicht erfolgreich, da kein Produkt mit der ID gefunden
        return false;
    }
    // Produkt existiert?
    public boolean existsById(int id) {
        for (Product p : products) {
            if (p.id() == id) {
                // Produkt gefunden!
                return true;
            }
        }
        // Kein Treffer, false
        return false;
    }
}