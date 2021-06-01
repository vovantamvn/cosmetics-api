package com.app.cosmetics.application;

import com.app.cosmetics.api.OrderApi;
import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.application.data.OrderData;
import com.app.cosmetics.application.data.OrderItemData;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.core.order.Order;
import com.app.cosmetics.core.order.OrderRepository;
import com.app.cosmetics.core.order.PaymentMethod;
import com.app.cosmetics.core.orderitem.OrderItem;
import com.app.cosmetics.core.orderitem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderData create(OrderApi.OrderRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = new Order();
        order.setFirstName(request.getFirstName());
        order.setLastName(request.getLastName());
        order.setAddress(request.getAddress());
        order.setPhone(request.getPhone());
        order.setEmail(request.getEmail());
        order.setNote(request.getNote());
        order.setPaymentMethod(PaymentMethod.valueOf(
                request.getPaymentMethod()
        ));

        orderRepository.save(order); // save data

        int total = 0;
        int totalPre = 0;

        for (OrderApi.OrderItemRequest data : request.getItems()) {
            Item item = itemRepository.findById(data.getItemId())
                    .orElseThrow(NotFoundException::new);

            int currentCount = item.getCount() - data.getCount();

            item.setCount(currentCount);

            itemRepository.save(item);

            OrderItem orderItem = new OrderItem(
                    item,
                    data.getCount(),
                    item.getPrice(),
                    item.getPrePrice(),
                    order
            );

            orderItemRepository.save(orderItem);

            orderItems.add(orderItem);

            total += item.getPrice() * data.getCount();
            totalPre += item.getPrePrice() * data.getCount();
        }

        order.setOrderItems(orderItems);
        order.setTotal(total);
        order.setTotalPre(totalPre);

        orderRepository.save(order);

        return toResponse(order);
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
        List<OrderItemData> orderItemData = new ArrayList<>();

        for (OrderItem orderItem : order.getOrderItems()) {
            Item item = orderItem.getItem();

            OrderItemData data = OrderItemData.builder()
                    .id(item.getId()) // id of item
                    .name(item.getName()) // name of item
                    .price(orderItem.getPrice()) // price of orderItem
                    .prePrice(orderItem.getPrePrice()) // prePrice of orderItem
                    .count(orderItem.getCount()) // count of orderItem
                    .build();

            orderItemData.add(data);
        }

        return OrderData.builder()
                .id(order.getId())
                .firstName(order.getFirstName())
                .lastName(order.getLastName())
                .address(order.getAddress())
                .phone(order.getPhone())
                .email(order.getEmail())
                .total(order.getTotal())
                .totalPre(order.getTotalPre())
                .paymentMethod(order.getPaymentMethod())
                .note(order.getNote())
                .items(orderItemData)
                .build();
    }
}
