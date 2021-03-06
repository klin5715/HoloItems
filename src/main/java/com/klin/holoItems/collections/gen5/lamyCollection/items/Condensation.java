package com.klin.holoItems.collections.gen5.lamyCollection.items;

import com.klin.holoItems.interfaces.Placeable;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen5.lamyCollection.LamyCollection;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Condensation extends Item implements Placeable {
    public static final String name = "condensation";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.SNOW_BLOCK;
    private static final int quantity = 64;
    private static final String lore =
            "The lava putter outer";
    private static final int durability = 0;
    public static final boolean stackable = true;
    private static final boolean shiny = true;
    public static final int cost = 0;

    public Condensation(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape("&%&","%*%","&%&");
        recipe.setIngredient('*', Material.WET_SPONGE);
        recipe.setIngredient('%', Material.SNOW_BLOCK);
        recipe.setIngredient('&', Material.SNOWBALL);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(BlockPlaceEvent event){
        event.setCancelled(false);
        Utility.vacuum(event.getBlockPlaced(), Material.LAVA, 1, 65);
    }
}
