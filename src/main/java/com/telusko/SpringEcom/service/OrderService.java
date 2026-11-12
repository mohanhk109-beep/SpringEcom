//package com.telusko.SpringEcom.service;
//
//import com.telusko.SpringEcom.model.Order;
//import com.telusko.SpringEcom.model.OrderItem;
//import com.telusko.SpringEcom.model.Product;
//import com.telusko.SpringEcom.model.dto.OrderItemRequest;
//import com.telusko.SpringEcom.model.dto.OrderItemResponse;
//import com.telusko.SpringEcom.model.dto.OrderRequest;
//import com.telusko.SpringEcom.model.dto.OrderResponse;
//import com.telusko.SpringEcom.repo.OrderRepo;
//import com.telusko.SpringEcom.repo.ProductRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class OrderService {
//    @Autowired
//    private ProductRepo productRepo;
//    private OrderRepo orderRepo;
//    public OrderResponse placeOrder(OrderRequest request) {
//       Order order = new Order();
//       String orderId = UUID.randomUUID().toString().substring(0,8).toUpperCase();
//       order.setOrderId(orderId);
//       order.setCustomerName(request.customerName());
//       order.setEmail(request.email());
//       order.setStatus("PLACED");
//       order.setOrderDate(LocalDate.now());
//       List<OrderItem>orderItems= new ArrayList<>();
//       for(OrderItemRequest itemReq:request.items()){
////           Product product = productRepo.findById(itemReq.productId())
////                   .orElseThrow(() - >new RuntimeException("product not found"));
//           Product product = productRepo.findById(itemReq.productId())
//                   .orElseThrow(() -> new RuntimeException("product not found"));
//           product.setStockQuantity(product.getStockQuantity()-itemReq.quantity());
//           productRepo.save(product);
//           OrderItem orderItem = OrderItem.builder()
//                   .product(product)
//                   .quantity(itemReq.quantity())
//                   .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())))
//                   .order(order)
//                   .build();
//            orderItems.add(orderItem);
//
//       }
//       //orderItems.add(orderItem);
//       order.setOrderItems(orderItems);
//       Order savedOrder  = orderRepo.save(order);
//       List<OrderItemResponse>itemResponses = new ArrayList<>();
//       for(OrderItem item:order.getOrderItems()){
//           OrderItemResponse orderItemResponse = new OrderItemResponse(
//                   item.getProduct().getName(),
//                   item.getQuantity(),
//                   item.getTotalPrice());
//           itemResponses.add(orderItemResponse);
//
//       }
//       OrderResponse orderResponse = new OrderResponse(
//               savedOrder.getOrderId(),
//               savedOrder.getCustomerName(),
//               savedOrder.getEmail(),
//               savedOrder.getStatus(),
//               savedOrder.getOrderDate()
//         );
//
//
//        return orderResponse;
//    }
//
//    public List<OrderResponse> getAllOrderResponses() {
//        return null;
//    }
//}



package com.telusko.SpringEcom.service;

import com.telusko.SpringEcom.model.Order;
import com.telusko.SpringEcom.model.OrderItem;
import com.telusko.SpringEcom.model.Product;
import com.telusko.SpringEcom.model.dto.OrderItemRequest;
import com.telusko.SpringEcom.model.dto.OrderItemResponse;
import com.telusko.SpringEcom.model.dto.OrderRequest;
import com.telusko.SpringEcom.model.dto.OrderResponse;
import com.telusko.SpringEcom.repo.OrderRepo;
import com.telusko.SpringEcom.repo.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;


    public OrderResponse placeOrder(OrderRequest request) {

        // 1. Create Order object
        Order order = new Order();

        // 2. Generate unique Order ID
        String orderId = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        order.setOrderId(orderId);
        order.setCustomerName(request.customerName());
        order.setEmail(request.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());


        // 3. Create list of OrderItems
        List<OrderItem> orderItems = new ArrayList<>();


        // 4. Process every item in the request
        for (OrderItemRequest itemReq : request.items()) {

            // Find product
            Product product = productRepo.findById(itemReq.productId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found"));


            // Check stock
            if (product.getStockQuantity() < itemReq.quantity()) {
                throw new RuntimeException(
                        "Not enough stock for product: "
                                + product.getName()
                );
            }


            // Reduce stock
            product.setStockQuantity(
                    product.getStockQuantity()
                            - itemReq.quantity()
            );


            // Save updated product
            productRepo.save(product);


            // Calculate total price
            BigDecimal totalPrice =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            itemReq.quantity()
                                    )
                            );


            // Create OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.quantity())
                    .totalPrice(totalPrice)
                    .order(order)
                    .build();


            // Add OrderItem to list
            orderItems.add(orderItem);
        }


        // 5. Add OrderItems to Order
        order.setOrderItems(orderItems);


        // 6. Save Order
        Order savedOrder = orderRepo.save(order);


        // 7. Create response list
        List<OrderItemResponse> itemResponses =
                new ArrayList<>();


        // 8. Convert OrderItem → OrderItemResponse
        for (OrderItem item : savedOrder.getOrderItems()) {

            OrderItemResponse itemResponse =
                    new OrderItemResponse(
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getTotalPrice()
                    );

            itemResponses.add(itemResponse);
        }


        // 9. Create final OrderResponse
        OrderResponse orderResponse =
                new OrderResponse(
                        savedOrder.getOrderId(),
                        savedOrder.getCustomerName(),
                        savedOrder.getEmail(),
                        savedOrder.getStatus(),
                        savedOrder.getOrderDate(),
                        itemResponses
                );


        // 10. Return response
        return orderResponse;
    }


    public List<OrderResponse> getAllOrderResponses() {
      List<Order> orders = orderRepo.findAll();
      List<OrderResponse> orderResponses =  new ArrayList<>();
      for(Order order : orders){
          List<OrderItemResponse> itemResponses = new ArrayList<>();
          for(OrderItem item : order.getOrderItems()){
              OrderItemResponse orderItemResponse = new OrderItemResponse(
                      item.getProduct().getName(),
                      item.getQuantity(),
                      item.getTotalPrice()
              );
              itemResponses.add(orderItemResponse);
          }
          OrderResponse orderResponse  =new  OrderResponse(
                  order.getOrderId(),
                  order.getCustomerName(),
                  order.getEmail(),
                  order.getStatus(),
                  order.getOrderDate(),
                  itemResponses

          );
      }


        return orderResponses;
    }
}