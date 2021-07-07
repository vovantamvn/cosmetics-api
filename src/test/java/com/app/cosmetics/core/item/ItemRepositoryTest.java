package com.app.cosmetics.core.item;

import com.app.cosmetics.core.branch.Branch;
import com.app.cosmetics.core.branch.BranchRepository;
import com.app.cosmetics.core.category.Category;
import com.app.cosmetics.core.category.CategoryRepository;
import com.app.cosmetics.helper.CreateItemHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void it_should_order_by_sold_asc() {
        // Arrange
        Item itemOne = CreateItemHelper.create();
        itemOne.setSold(15);
        Item itemTwo = CreateItemHelper.create();
        itemTwo.setSold(35);

        // Act
        itemRepository.save(itemOne);
        itemRepository.save(itemTwo);

        // Assert
        List<Item> hotItems = itemRepository.findHotItems();
        assertEquals(2, hotItems.size());
        assertEquals(35, hotItems.get(0).getSold());
        assertEquals(15, hotItems.get(1).getSold());
    }

    @Test
    void it_should_order_by_created_decs() {
        // Arrange
        Item itemOne = CreateItemHelper.create();
        Item itemTwo = CreateItemHelper.create();

        // Act
        itemRepository.save(itemOne);
        itemRepository.flush();
        itemRepository.save(itemTwo);

        // Assert
        List<Item> newItems = itemRepository.findNewItems();
        assertEquals(2, newItems.size());
        assertEquals(itemOne.getId(), newItems.get(1).getId());
        assertEquals(itemTwo.getId(), newItems.get(0).getId());
    }

    @Test
    void it_should_delete_item_and_do_not_delete_category() {
        // Arrange
        Category category = new Category("Category");

        Item item = new Item();
        item.setName("Kem dưỡng da Pond");
        item.setDescription("Đây là kem dưỡng da số 1");
        item.setPrice(50_000);
        item.setPrice(80_000);
        item.setTypes(List.of("30ml", "50ml"));
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
        item.setPrice(50_000);
        item.setPrice(80_000);
        item.setTypes(List.of("30ml", "50ml"));
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