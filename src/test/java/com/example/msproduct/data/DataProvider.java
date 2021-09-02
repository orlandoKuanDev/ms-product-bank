package com.example.msproduct.data;

import com.example.msproduct.model.entities.Product;
import com.example.msproduct.model.entities.Rules;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    public static Product ProductRequest() {
        List<String> customerTypeTarget = new ArrayList<>();
        customerTypeTarget.add("PERSONAL");
        Rules rules = new Rules();
        rules.setCustomerTypeTarget(customerTypeTarget);
        rules.setMaximumLimitMonthlyMovementsQuantity(1);
        rules.setMaximumLimitMonthlyMovements(false);
        rules.setMaximumLimitCreditPerson(1);
        rules.setCommissionMaintenance(false);
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
}
