package com.example.msproduct.data;

import com.example.msproduct.model.entities.Product;
import com.example.msproduct.model.entities.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DataProvider {
    public static Product ProductRequest() {
        return Product.builder()
                .id("1")
                .productName("CUENTA CORRIENTE")
                .productType("PASIVO")
                .rules(Rules.builder()
                        .customerTypeTarget(customerTypeTarget("PERSONAL"))
                        .maximumLimitMonthlyMovementsQuantity(1)
                        .maximumLimitMonthlyMovements(false)
                        .commissionMaintenance(false)
                        .build())
                .build();
    }
    public static Product ProductCreate() {
        return Product.builder()
                .id("1")
                .productName("CUENTA CORRIENTE")
                .productType("PASIVO")
                .rules(Rules.builder()
                        .customerTypeTarget(customerTypeTarget("PERSONAL"))
                        .maximumLimitMonthlyMovementsQuantity(1)
                        .maximumLimitMonthlyMovements(false)
                        .commissionMaintenance(false)
                        .build())
                .build();
    }
    public static Product ProductUpdate() {
        return Product.builder()
                .id("1")
                .productName("CUENTA CORRIENTE")
                .productType("PASIVO")
                .rules(Rules.builder()
                        .customerTypeTarget(customerTypeTarget("ENTERPRISE"))
                        .maximumLimitMonthlyMovementsQuantity(1)
                        .maximumLimitMonthlyMovements(false)
                        .commissionMaintenance(false)
                        .build())
                .build();
    }
    public static Product ProductResponse() {
        return Product.builder()
                .id("2")
                .productName("AHORRO")
                .productType("PASIVO")
                .rules(Rules.builder()
                        .customerTypeTarget(customerTypeTarget("PERSONAL"))
                        .maximumLimitMonthlyMovementsQuantity(1)
                        .maximumLimitMonthlyMovements(true)
                        .commissionMaintenance(false)
                        .build())
                .build();
    }
    public static List<String> customerTypeTarget(String type) {
        List<String> customerTypeTarget = new ArrayList<>();
        customerTypeTarget.add(type);
        return customerTypeTarget;
    }
}
