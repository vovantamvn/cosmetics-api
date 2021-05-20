package com.app.cosmetics.application;

import com.app.cosmetics.api.OrderApi;
import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.application.data.OrderData;
import com.app.cosmetics.application.data.OrderItemData;
import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.account.AccountRepository;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.core.order.Order;
import com.app.cosmetics.core.order.OrderRepository;
import com.app.cosmetics.core.order.OrderStatus;
import com.app.cosmetics.core.orderitem.OrderItem;
import com.app.cosmetics.core.orderitem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderItemRepository orderItemRepository;

    public OrderData create(OrderApi.OrderRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = new Order();
        orderRepository.save(order); // save data

        int totalPrice = 0;

        for (OrderApi.OrderItemRequest data : request.getItems()) {
            Item item = itemRepository.findById(data.getItemId())
                    .orElseThrow(NotFoundException::new);

            OrderItem orderItem = new OrderItem(
                    item,
                    data.getCount(),
                    item.getPrice(),
                    order
            );

            orderItemRepository.save(orderItem);

            orderItems.add(orderItem);

            totalPrice += item.getPrice() * data.getCount();
        }

        Account account = getAccountFromRequest(request);

        order.setAccount(account);
        order.setOrderItems(orderItems);
        order.setTotal(totalPrice);

        orderRepository.save(order);

        return toResponse(order);
    }

    private Account getAccountFromRequest(OrderApi.OrderRequest request) {
        Account account = new Account(
                request.getPhone(),
                passwordEncoder.encode("123456"),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getAddress(),
                "",
                request.getPhone(),
                new ArrayList<>()
        );

        return accountRepository.save(account);
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
                    .count(orderItem.getCount()) // count of orderItem
                    .build();

            orderItemData.add(data);
        }

        Account account = order.getAccount();

        return OrderData.builder()
                .id(order.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .address(account.getAddress())
                .phone(account.getPhone())
                .email(account.getEmail())
                .total(order.getTotal())
                .items(orderItemData)
                .build();
    }
}
