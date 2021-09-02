package com.example.msproduct.model.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Rules {

    private List<String> customerTypeTarget;

    private boolean commissionMaintenance;

    private boolean maximumLimitMonthlyMovements;
    private Integer maximumLimitMonthlyMovementsQuantity;

    private Integer maximumLimitCreditPerson;
    private Integer maximumLimitCreditEnterprise;
}
