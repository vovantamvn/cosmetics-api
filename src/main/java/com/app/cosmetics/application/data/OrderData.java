package com.app.cosmetics.application.data;

import com.app.cosmetics.core.order.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderData {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private int total;
    private PaymentMethod paymentMethod;
    private String note;
    private List<OrderItemData> items;
}
