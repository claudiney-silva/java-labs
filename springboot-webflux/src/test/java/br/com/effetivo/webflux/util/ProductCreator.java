package br.com.effetivo.webflux.util;

import br.com.effetivo.webflux.domain.Product;

public class ProductCreator {

    public static Product createProductToBeSaved() {
        return Product.builder()
                .name("Pen Drive")
                .build();
    }

    public static Product createValidProduct() {
        return createProductToBeSaved().withId(1);
    }

    public static Product createdValidUpdatedProduct() {
        return createValidProduct().withName("Notebook");
    }
}
