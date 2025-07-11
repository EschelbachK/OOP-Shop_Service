package org.example;

import java.util.List;

public record Order(int orderId, String customerName, String orderDate, List<Product> products) {

}
