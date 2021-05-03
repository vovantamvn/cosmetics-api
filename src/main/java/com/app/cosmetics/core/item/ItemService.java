package com.app.cosmetics.core.item;

import com.app.cosmetics.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public ItemResponse create(ItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setImage(request.getImage());
        item.setPrice(request.getPrice());

        Item result = itemRepository.save(item);

        return toResponse(result);
    }

    public ItemResponse findById(long id) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(item);
    }

    /**
     * Update name, description, image, price
     * @param id
     * @param request
     * @return
     */
    public ItemResponse update(long id, ItemRequest request) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setImage(request.getImage());
        item.setPrice(request.getPrice());

        Item result = itemRepository.save(item);

        return toResponse(result);
    }

    public List<ItemResponse> findAll() {
        return itemRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ItemResponse toResponse(Item item) {
        return modelMapper.map(item, ItemResponse.class);
    }
}
