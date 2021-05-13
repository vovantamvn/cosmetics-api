package com.app.cosmetics.application;

import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.application.data.CategoryData;
import com.app.cosmetics.core.category.Category;
import com.app.cosmetics.core.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryData findById(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(category);
    }

    public List<CategoryData> findAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryData create(String name) {
        Category category = new Category(name);
        Category result = categoryRepository.save(category);
        return toResponse(result);
    }

    public CategoryData update(Long id, String name) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        category.update(name);

        Category result = categoryRepository.save(category);

        return toResponse(result);
    }

    private CategoryData toResponse(Category category) {
        return modelMapper.map(category, CategoryData.class);
    }

    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException();
        }
        categoryRepository.deleteById(id);
    }
}
