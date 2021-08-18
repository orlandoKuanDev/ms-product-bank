package com.example.msproduct.model.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rules {

    private List<String> customerTypeTarget;

    private boolean commissionMaintenance;

    private boolean maximumLimitMonthlyMovements;
    private Integer maximumLimitMonthlyMovementsQuantity;

    private Integer maximumLimitCreditPerson;
    private Integer maximumLimitCreditEnterprise;
}
