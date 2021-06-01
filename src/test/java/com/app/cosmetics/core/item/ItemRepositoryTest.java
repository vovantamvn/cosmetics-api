package com.app.cosmetics.core.item;

import com.app.cosmetics.core.branch.Branch;
import com.app.cosmetics.core.branch.BranchRepository;
import com.app.cosmetics.core.category.Category;
import com.app.cosmetics.core.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BranchRepository branchRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void it_should_delete_item_and_do_not_delete_category() {
        // Arrange
        Category category = new Category("Category");

        Item item = new Item();
        item.setName("Kem dưỡng da Pond");
        item.setDescription("Đây là kem dưỡng da số 1");
        item.setCount(100);
        item.setPrice(50_000);
        item.setPrice(80_000);
        item.setTypes(List.of("30ml", "50ml"));
        item.setExpiry(LocalDate.of(2023, 06, 06));
        item.setCategory(category);

        // Act
        categoryRepository.save(category);
        itemRepository.save(item);

        final Long categoryId = category.getId();
        final Long itemId = item.getId();

        itemRepository.deleteById(itemId);

        // Assert
        assertFalse(itemRepository.existsById(itemId));
        assertTrue(categoryRepository.existsById(categoryId));
    }

    @Test
    void it_should_delete_item_and_do_not_delete_branch() {
        // Arrange
        Branch branch = new Branch("Branch");

        Item item = new Item();
        item.setName("Kem dưỡng da Pond");
        item.setDescription("Đây là kem dưỡng da số 1");
        item.setCount(100);
        item.setPrice(50_000);
        item.setPrice(80_000);
        item.setTypes(List.of("30ml", "50ml"));
        item.setExpiry(LocalDate.of(2023, 06, 06));
        item.setBranch(branch);

        // Act
        branchRepository.save(branch);
        itemRepository.save(item);

        final Long branchId = branch.getId();
        final Long itemId = item.getId();

        itemRepository.deleteById(itemId);

        // Assert
        assertFalse(itemRepository.existsById(itemId));
        assertTrue(branchRepository.existsById(branchId));
    }
}