package com.telusko.SpringEcom.model.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
       String orederId,
       String customerName,
       String email,
       String status,
       LocalDate orderData,
       List<OrderItemResponse> items
) {
}
