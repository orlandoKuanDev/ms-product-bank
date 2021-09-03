package com.example.msproduct.data;

import com.example.msproduct.model.entities.Product;
import com.example.msproduct.model.entities.Rules;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    public static Product ProductRequest() {
        List<String> customerTypeTarget = new ArrayList<>();
        customerTypeTarget.add("PERSONAL");
        return Product.builder()
                .id("1")
                .productName("CUENTA CORRIENTE")
                .productType("PASIVO")
                .rules(Rules.builder()
                        .customerTypeTarget(customerTypeTarget)
                        .maximumLimitMonthlyMovementsQuantity(1)
                        .maximumLimitMonthlyMovements(false)
                        .maximumLimitCreditPerson(1)
                        .commissionMaintenance(false)
                        .build())
                .build();
    }
    public static Product ProductResponse() {
        List<String> customerTypeTarget = new ArrayList<>();
        customerTypeTarget.add("PERSONAL");
        return Product.builder()
                .id("1")
                .productName("AHORRO")
                .productType("PASIVO")
                .rules(Rules.builder()
                        .customerTypeTarget(customerTypeTarget)
                        .maximumLimitMonthlyMovementsQuantity(1)
                        .maximumLimitMonthlyMovements(true)
                        .commissionMaintenance(false)
                        .build())
                .build();
    }
}
