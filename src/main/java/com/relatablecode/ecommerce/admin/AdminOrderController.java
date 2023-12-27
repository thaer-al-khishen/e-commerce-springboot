package com.relatablecode.ecommerce.admin;

import com.relatablecode.ecommerce.model.Order;
import com.relatablecode.ecommerce.requests.UpdateOrderStatusRequest;
import com.relatablecode.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //Alternatively, you can have admin authorization on the method level instead of controller level
//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, updateOrderStatusRequest.getNewStatus());
        return ResponseEntity.ok("Order status updated to " + updatedOrder.getStatus());
    }

}
