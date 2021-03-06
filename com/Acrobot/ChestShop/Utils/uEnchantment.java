package com.Acrobot.ChestShop.Utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Acrobot
 */
public class uEnchantment {
    public static String getEnchantment(ItemStack item) {
        return encodeEnchantment(item.getEnchantments());
    }

    public static String encodeEnchantment(Map<Enchantment, Integer> map) {
        int integer = 0;
        for (Map.Entry<Enchantment, Integer> enchantment : map.entrySet()) {
            integer = integer * 1000 + (enchantment.getKey().getId()) * 10 + enchantment.getValue();
        }

        if (integer == 0) return null;

        return Integer.toString(integer, 32);
    }

    public static Map<Enchantment, Integer> decodeEnchantment(String base32) {
        Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();

        if (base32 == null) {
            return map;
        }

        StringBuilder integer = new StringBuilder(String.valueOf(Integer.parseInt(base32, 32)));

        while (integer.length() % 3 != 0) {
            integer.insert(0, '0');
        }

        String enchantment = integer.toString();

        for (int i = 0; i < enchantment.length() / 3; i++) {
            String item = enchantment.substring(i * 3, i * 3 + 3);

            Enchantment ench = Enchantment.getById(Integer.parseInt(item.substring(0, 2)));

            if (ench == null) continue;
            int level = Integer.parseInt(item.substring(2));
            if (ench.getMaxLevel() < level || level < ench.getStartLevel()) continue;
            map.put(ench, level);
        }

        return map;
    }
}
