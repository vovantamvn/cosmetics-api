package com.app.cosmetics.core.discount;

import com.app.cosmetics.exception.ExpiredException;
import com.app.cosmetics.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;

    public DiscountResponse create(DiscountRequest request) {
        if (LocalDateTime.now().isAfter(request.getExpired())) {
            throw new ExpiredException();
        }

        Discount discount = new Discount();
        discount.setCode(request.getCode());
        discount.setDescription(request.getDescription());
        discount.setExpired(request.getExpired());
        discount.setCount(request.getCount());
        discount.setType(request.getType());
        discount.setActive(true);

        Discount result = discountRepository.save(discount);

        return toResponse(result);
    }

    public DiscountResponse inactive(long id) {
        Discount discount = discountRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        discount.setActive(false);

        Discount result = discountRepository.save(discount);

        return toResponse(result);
    }

    private DiscountResponse toResponse(Discount discount) {
        return modelMapper.map(discount, DiscountResponse.class);
    }
}
