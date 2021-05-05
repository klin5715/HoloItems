package com.klin.holoItems.collections.gen2.aquaCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen2.aquaCollection.AquaCollection;
import com.klin.holoItems.interfaces.Holdable;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Set;

public class OnionRing extends Item implements Holdable {
    public static final String name = "onionRing";
    public static final Set<Enchantment> accepted = null;

    private static final Material material = Material.MUSIC_DISC_PIGSTEP;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
            "Guarantees your arrows landing while" +"/n"+
            "this is held in your offhand";
    private static final int durability = 0;
    private static final boolean stackable = false;
    private static final boolean shiny = true;

    public static final int cost = 1440;
    public static final char key = '0';

    public OnionRing(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+AquaCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" * ","*%*"," * ");
        recipe.setIngredient('*', Material.BEETROOT);
        recipe.setIngredient('%', Material.SPECTRAL_ARROW);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void ability(Event generic) {
        ProjectileLaunchEvent event = (ProjectileLaunchEvent) generic;
        Projectile proj = event.getEntity();
        if(!(proj instanceof AbstractArrow) || proj instanceof Trident)
            return;

        AbstractArrow arrow = (AbstractArrow) proj;
        Player player = (Player) arrow.getShooter();
        Vector velocity = arrow.getVelocity().multiply(-1);
        arrow.setVelocity(velocity);
        Utility.arrowDamage(player.getInventory().getItemInMainHand(), arrow, player);

        Bukkit.getServer().getPluginManager().callEvent(new ProjectileHitEvent(arrow, player));
        Location loc = player.getLocation().add(0, 1, 0);
        arrow.teleport(arrow.getLocation().add(loc.getDirection()));
        arrow.setVelocity(velocity);
        new BukkitRunnable() {
            public void run() {
                if(arrow.isValid())
                    arrow.remove();
            }
        }.runTaskLater(HoloItems.getInstance(), 1);
    }
}