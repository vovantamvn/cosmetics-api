package com.app.cosmetics.application;

import com.app.cosmetics.api.OrderApi;
import com.app.cosmetics.api.exception.MyException;
import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.application.data.OrderData;
import com.app.cosmetics.application.data.OrderItemData;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.core.lot.Lot;
import com.app.cosmetics.core.lot.LotRepository;
import com.app.cosmetics.core.order.Order;
import com.app.cosmetics.core.order.OrderRepository;
import com.app.cosmetics.core.order.PaymentMethod;
import com.app.cosmetics.core.orderitem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final LotRepository lotRepository;

    @Transactional
    public OrderData create(OrderApi.OrderRequest request) {

        Order order = new Order();

        int total = 0;
        int totalPre = 0;
        List<OrderItem> orderItems = getOrderItemListRequest(request, order);

        for (OrderItem orderItem : orderItems) {
            total += orderItem.getCount() * orderItem.getPrice();
            totalPre += orderItem.getCount() * orderItem.getPrePrice();
        }

        /**
         * If order success then save it to database
         */

        for (OrderItem orderItem : orderItems) {
            buyItem(orderItem.getItem().getId(), orderItem.getCount());
        }

        order.setFirstName(request.getFirstName());
        order.setLastName(request.getLastName());
        order.setAddress(request.getAddress());
        order.setPhone(request.getPhone());
        order.setEmail(request.getEmail());
        order.setNote(request.getNote());
        order.setPaymentMethod(PaymentMethod.valueOf(
                request.getPaymentMethod()
        ));
        order.setOrderItems(orderItems);
        order.setTotal(total);
        order.setTotalPre(totalPre);

        orderRepository.save(order);

        return toResponse(order);
    }

    private List<OrderItem> getOrderItemListRequest(OrderApi.OrderRequest request, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderApi.OrderItemRequest data : request.getItems()) {
            Item item = itemRepository.findById(data.getItemId())
                    .orElseThrow(NotFoundException::new);

            OrderItem orderItem = new OrderItem(
                    item,
                    data.getCount(),
                    item.getDiscountPrice(),
                    item.getPrePrice(),
                    order
            );

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    @Transactional
    public void buyItem(Long itemId, int count) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(NotFoundException::new);

        List<Lot> lots = item.getLots();

        int totalBuy = 0;

        for (Lot lot : lots) {
            if (totalBuy >= count) {
                break;
            }

            if (lot.getCount() == 0) {
                continue;
            }

            int need = count - totalBuy;

            if (lot.getCount() >= need) {
                totalBuy += need;

                int currentCount = lot.getCount() - need;

                lot.setCount(currentCount);
                lotRepository.save(lot);
            } else {
                totalBuy += lot.getCount();

                lot.setCount(0);
                lotRepository.save(lot);
            }
        }

        if (totalBuy < count) {
            throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "Not enough count for itemId " + item.getId());
        }
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
