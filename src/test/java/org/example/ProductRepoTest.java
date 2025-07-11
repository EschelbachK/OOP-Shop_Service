package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    private ProductRepo productRepo;

    // Vor jedem Test: Neues Produkt-Repository anlegen
    @BeforeEach
    void setUp() {
        productRepo = new ProductRepo();
    }

    // Produkt hinzufügen und prüfen, ob es gespeichert wurde
    @Test
    void addProduct() {
        Product p = new Product(1, "Apfel", 0.99);
        productRepo.addProduct(p);

        List<Product> all = productRepo.getAllProducts();
        assertEquals(1, all.size(), "Produktliste sollte 1 Produkt enthalten");
        assertEquals(p, all.get(0), "Das Produkt sollte korrekt gespeichert sein");
    }

    // Alle Produkte abrufen und die Anzahl prüfen
    @Test
    void getAllProducts() {
        productRepo.addProduct(new Product(1, "Apfel", 0.99));
        productRepo.addProduct(new Product(2, "Banane", 1.29));

        List<Product> all = productRepo.getAllProducts();
        assertEquals(2, all.size(), "Produktliste sollte 2 Produkte enthalten");
    }

    // Produkt nach ID suchen (vorhanden & nicht vorhanden)
    @Test
    void getProductById() {
        Product p = new Product(1, "Apfel", 0.99);
        productRepo.addProduct(p);

        Product found = productRepo.getProductById(1);
        assertNotNull(found, "Produkt sollte gefunden werden");
        assertEquals(p, found, "Gefundenes Produkt stimmt überein");

        Product notFound = productRepo.getProductById(99);
        assertNull(notFound, "Nicht vorhandenes Produkt liefert null");
    }

    // Produkt entfernen und prüfen, ob es weg ist
    @Test
    void removeProductById() {
        productRepo.addProduct(new Product(1, "Apfel", 0.99));
        productRepo.addProduct(new Product(2, "Banane", 1.29));

        boolean removed = productRepo.removeProductById(1);
        assertTrue(removed, "Produkt mit ID 1 sollte entfernt werden");

        assertNull(productRepo.getProductById(1), "Produkt 1 sollte nicht mehr vorhanden sein");
        assertEquals(1, productRepo.getAllProducts().size(), "Es sollte noch ein Produkt übrig sein");

        // Entfernen eines nicht existierenden Produkts gibt false zurück
        boolean removedNonExistent = productRepo.removeProductById(99);
        assertFalse(removedNonExistent, "Nicht vorhandenes Produkt kann nicht entfernt werden");
    }

    // Produkt aktualisieren (vorhanden & nicht vorhanden)
    @Test
    void updateProduct() {
        productRepo.addProduct(new Product(1, "Apfel", 0.99));

        Product updated = new Product(1, "Apfel Bio", 1.29);
        boolean success = productRepo.updateProduct(updated);
        assertTrue(success, "Produkt sollte erfolgreich aktualisiert werden");

        Product p = productRepo.getProductById(1);
        assertEquals("Apfel Bio", p.name(), "Produktname sollte aktualisiert sein");
        assertEquals(1.29, p.price(), 0.0001, "Produktpreis sollte aktualisiert sein");

        Product nonExisting = new Product(99, "Nicht vorhanden", 0.0);
        boolean fail = productRepo.updateProduct(nonExisting);
        assertFalse(fail, "Update von nicht vorhandenem Produkt schlägt fehl");
    }

    // Existenz eines Produkts anhand der ID prüfen
    @Test
    void existsById() {
        productRepo.addProduct(new Product(1, "Apfel", 0.99));

        assertTrue(productRepo.existsById(1), "Produkt mit ID 1 sollte existieren");
        assertFalse(productRepo.existsById(99), "Produkt mit ID 99 sollte nicht existieren");
    }
}
