package com.app.cosmetics.helper;

import com.app.cosmetics.core.item.Item;

import java.util.List;

public class CreateItemHelper {
    public static Item create() {
        Item item = new Item();
        item.setName("Kem dưỡng da Pond");
        item.setDescription("Đây là kem dưỡng da số 1");
        item.setPrice(80_000);
        item.setPrePrice(60_000);
        item.setDiscountPrice(75_000);
        item.setTypes(List.of("30ml", "50ml"));

        return item;
    }
}
