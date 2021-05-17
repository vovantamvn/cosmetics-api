package com.app.cosmetics.application;

import com.app.cosmetics.api.OrderApi;
import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.application.data.OrderData;
import com.app.cosmetics.core.order.Order;
import com.app.cosmetics.core.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderData create(OrderApi.OrderRequest request) {
        return null;
    }

    public OrderData findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(order);
    }

    public List<OrderData> findAll() {
        return orderRepository
                .findAll()
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    private OrderData toResponse(Order order) {
        return null;
    }
}
