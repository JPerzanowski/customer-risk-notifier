package com.jakub.recruitment.customerrisknotifier.entity;

import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "customer_details")
public class CustomerDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "info_as_of_date")
    private String infoAsOfDate;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_start_date")
    private String customerStartDate;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "customer_income")
    private Float customerIncome;

    @Column(name = "customer_risk_class")
    private String customerRiskClass;

    @Column(name = "customer_business_type")
    private String customerBusinessType;

    public CustomerDetails(String infoAsOfDate, Integer customerId, String customerName, String customerStartDate,
                           String customerType, Float customerIncome, String customerRiskClass, String customerBusinessType) {
        this.infoAsOfDate = infoAsOfDate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerStartDate = customerStartDate;
        this.customerType = customerType;
        this.customerIncome = customerIncome;
        this.customerRiskClass = customerRiskClass;
        this.customerBusinessType = customerBusinessType;
    }
}
