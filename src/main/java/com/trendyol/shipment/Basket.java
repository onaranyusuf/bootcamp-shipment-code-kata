package com.trendyol.shipment;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Basket {

    private List<Product> products;

    private static final int THRESHOLD_VALUE = 3;

    public ShipmentSize getShipmentSize() {

        Map<ShipmentSize, Long> SizeCounter = products.stream()
                .map(Product::getSize)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        ShipmentSize MostCommon = null;

        for (Map.Entry<ShipmentSize, Long> entry : SizeCounter.entrySet()) {
            if (entry.getValue() >= THRESHOLD_VALUE) {
                MostCommon = entry.getKey();
                break;
            }
        }

        if (MostCommon == ShipmentSize.SMALL) {
            return ShipmentSize.MEDIUM;
        }
        else if (MostCommon == ShipmentSize.MEDIUM) {
            return ShipmentSize.LARGE;
        }
        else if (MostCommon == ShipmentSize.LARGE) {
            return ShipmentSize.X_LARGE;
        }
        else {
            return RegularShipmentSize(products);
        }
    }

    private ShipmentSize RegularShipmentSize(List<Product> products) {
        return products.stream()
                .map(Product::getSize)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
