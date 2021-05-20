package com.app.cosmetics.application;

import com.app.cosmetics.api.ItemApi;
import com.app.cosmetics.application.data.BranchData;
import com.app.cosmetics.application.data.CategoryData;
import com.app.cosmetics.core.branch.Branch;
import com.app.cosmetics.core.branch.BranchRepository;
import com.app.cosmetics.core.category.Category;
import com.app.cosmetics.core.category.CategoryRepository;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.application.data.ItemData;
import com.app.cosmetics.api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ItemData create(ItemApi.ItemRequest request) {
        Branch branch = branchRepository
                .findById(request.getBranchId())
                .orElseThrow(NotFoundException::new);

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(NotFoundException::new);

        Item item = new Item(
                request.getName(),
                request.getDescription(),
                request.getImage(),
                request.getCount(),
                request.getPrice(),
                new ArrayList<>(),
                branch,
                category
        );

        Item result = itemRepository.save(item);

        return toResponse(result);
    }

    public ItemData findById(long id) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(item);
    }

    public ItemData update(long id, ItemApi.ItemRequest request) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        Branch branch = branchRepository
                .findById(request.getBranchId())
                .orElseThrow(NotFoundException::new);

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(NotFoundException::new);

        item.update(
                request.getName(),
                request.getDescription(),
                request.getImage(),
                request.getCount(),
                request.getPrice(),
                branch,
                category
        );

        Item result = itemRepository.save(item);

        return toResponse(result);
    }

    public List<ItemData> findAll() {
        return itemRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ItemData toResponse(Item item) {
        CategoryData categoryData = (item.getCategory() == null)
                ? null
                : modelMapper.map(item.getCategory(), CategoryData.class);

        BranchData branchData = (item.getBranch() == null)
                ? null
                : modelMapper.map(item.getBranch(), BranchData.class);

        return ItemData.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .image(item.getImage())
                .count(item.getCount())
                .price(item.getPrice())
                .category(categoryData)
                .branch(branchData)
                .build();
    }

    public void delete(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }
}
