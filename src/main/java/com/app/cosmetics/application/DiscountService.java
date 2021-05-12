package com.app.cosmetics.application;

import com.app.cosmetics.api.DiscountApi;
import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.core.discount.Discount;
import com.app.cosmetics.core.discount.DiscountRepository;
import com.app.cosmetics.application.data.DiscountData;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;

    public DiscountData findById(Long id) {
        Discount discount = discountRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        return toResponse(discount);
    }

    public List<DiscountData> findAll() {
        return discountRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DiscountData create(DiscountApi.DiscountRequest request) {
        Discount discount = new Discount(
                request.getCode(),
                request.getPercent(),
                request.getDescription(),
                request.getExpired(),
                request.getCount()
        );

        Discount result = discountRepository.save(discount);

        return toResponse(result);
    }

    public DiscountData inactive(Long id) {
        Discount discount = discountRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        discount.update(discount.getCount(), false);

        Discount result = discountRepository.save(discount);

        return toResponse(result);
    }

    private DiscountData toResponse(Discount discount) {
        return modelMapper.map(discount, DiscountData.class);
    }
}
