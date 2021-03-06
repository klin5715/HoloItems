package com.klin.holoItems.abstractClasses;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public abstract class PowerUp extends Item implements Interactable {
    private static final int durability = 0;
    public static final boolean stackable = false;

    private final int interval;
    private final int increments;

    public PowerUp(String name, Set<Enchantment> accepted, Material material, int quantity, String lore, boolean shiny, int cost, int interval, int increments){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost);
        this.interval = interval;
        this.increments = increments;
    }

    public void ability(PlayerInteractEvent event, Action action){
        Player player = event.getPlayer();
        if(action!=Action.LEFT_CLICK_AIR && action!=Action.LEFT_CLICK_BLOCK || !player.isInsideVehicle())
            return;
        ItemStack item = event.getItem();
        item.setAmount(item.getAmount()-1);
        new Task(HoloItems.getInstance(), 0, interval){
            int increment = 0;
            public void run(){
                if(increment>=increments || player.isInsideVehicle() || endCondition()) {
                    cancel();
                    return;
                }
                effect(player, player.getVehicle());
                increment++;
            }
        };
    }

    protected abstract void effect(Player player, Entity cart);

    protected abstract boolean endCondition();
}
