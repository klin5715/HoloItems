package com.klin.holoItems.collections.gen0.suiseiCollection.items;

import com.klin.holoItems.HoloItems;
import com.klin.holoItems.Item;
import com.klin.holoItems.collections.gen0.suiseiCollection.SuiseiCollection;
import com.klin.holoItems.interfaces.Interactable;
import com.klin.holoItems.utility.Task;
import com.klin.holoItems.utility.Utility;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class Comet extends Item implements Interactable {
    public static final String name = "comet";
    public static final Set<Enchantment> accepted = new HashSet<Enchantment>(){{
        add(Enchantment.FIRE_ASPECT);
        add(Enchantment.DAMAGE_ALL);
        add(Enchantment.DAMAGE_ARTHROPODS);
        add(Enchantment.DAMAGE_UNDEAD);
        add(Enchantment.DIG_SPEED);
        add(Enchantment.DURABILITY);
        add(Enchantment.LOOT_BONUS_BLOCKS);
        add(Enchantment.LOOT_BONUS_MOBS);
        add(Enchantment.MENDING);
        add(Enchantment.PIERCING);
        add(Enchantment.SILK_TOUCH);
    }};

    private static final Material material = Material.GOLDEN_AXE;
    private static final int quantity = 1;
    private static final String lore =
            "§6Ability" +"/n"+
            "Right click to throw an axe";
    private static final int durability = 32;
    public static final boolean stackable = false;
    private static final boolean shiny = false;

    public static final int cost = 2100;
    public static final char key = '2';

    public Comet(){
        super(name, accepted, material, quantity, lore, durability, stackable, shiny, cost,
                ""+SuiseiCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe0 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"0"), item);
        recipe0.shape("** ","*% "," % ");
        recipe0.setIngredient('*', Material.NETHER_STAR);
        recipe0.setIngredient('%', Material.CHAIN);
        recipe0.setGroup(name);
        Bukkit.getServer().addRecipe(recipe0);

        ShapedRecipe recipe1 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"1"), item);
        recipe1.shape("** ","%* ","%  ");
        recipe1.setIngredient('*', Material.NETHER_STAR);
        recipe1.setIngredient('%', Material.CHAIN);
        recipe1.setGroup(name);
        Bukkit.getServer().addRecipe(recipe1);

        ShapedRecipe recipe2 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"2"), item);
        recipe2.shape("** ","*% "," % ");
        recipe2.setIngredient('*', Material.NETHER_STAR);
        recipe2.setIngredient('%', Material.CHAIN);
        recipe2.setGroup(name);
        Bukkit.getServer().addRecipe(recipe2);

        ShapedRecipe recipe3 =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name+"3"), item);
        recipe3.shape(" **"," %*"," % ");
        recipe3.setIngredient('*', Material.NETHER_STAR);
        recipe3.setIngredient('%', Material.CHAIN);
        recipe3.setGroup(name);
        Bukkit.getServer().addRecipe(recipe3);
    }

    public void ability(PlayerInteractEvent event, Action action){
        if(!(action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK) ||
                event.useInteractedBlock()==Event.Result.ALLOW)
            return;

        Player player = event.getPlayer();
        if(player.hasPotionEffect(PotionEffectType.WEAKNESS)){
            player.sendMessage("§7The axe weighs heavily on your arms");
            return;
        }
        double damage = 4+3*(Utility.checkPotionEffect(player, PotionEffectType.INCREASE_DAMAGE)-
                Utility.checkPotionEffect(player, PotionEffectType.WEAKNESS));

        Location location = player.getEyeLocation();
        World world = player.getWorld();
        ItemStack item = event.getItem();
        boolean hand = event.getHand()==EquipmentSlot.HAND;

        double distance = 100;
        Vector dir = player.getLocation().getDirection().multiply(3);
        Set<LivingEntity> targets = new HashSet<>();
        for(int i=0; i<1+item.getEnchantmentLevel(Enchantment.PIERCING); i++) {
            RayTraceResult result = world.rayTrace(location, dir, 100,
                    FluidCollisionMode.NEVER, true, 0.5,
                    entity -> (entity != player &&
                            entity instanceof LivingEntity && !(entity instanceof ArmorStand) &&
                            !targets.contains(entity)));
            if (result != null) {
                LivingEntity entity = (LivingEntity) result.getHitEntity();
                if (entity != null) {
                    distance = location.distance(result.getHitEntity().getLocation());
                    targets.add(entity);
                }
                else if (result.getHitBlock() != null) {
                    distance = location.distance(result.getHitBlock().getLocation());
                    break;
                }
            }
        }
        double iterations = distance/3;

        ArmorStand stand = world.spawn(location.clone().add(0, -1, 0), ArmorStand.class);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setCanPickupItems(false);
        stand.getPersistentDataContainer().set(Utility.key, PersistentDataType.INTEGER, 0);

        if(hand)
            stand.getEquipment().setItemInMainHand(item);
        else
            stand.getEquipment().setItemInOffHand(item);

        if (player.getGameMode()!=GameMode.CREATIVE)
            Utility.addDurability(item, -1, player);

        double height = player.getLocation().getY();
        new Task(HoloItems.getInstance(), 1, 1){
            double increment = 0;
            final boolean crit = player.getLocation().getY()<height;

            public void run(){
                if(increment>=0.3*iterations) {
                    if(!targets.isEmpty()) {
                        if (player.getGameMode()!=GameMode.CREATIVE)
                            Utility.addDurability(item, 0.5, player);
                        for (LivingEntity target : targets) {
                            if (target.isValid())
                                Utility.damage(item, damage, crit, player, target, false, true);
                        }
                    }
                    stand.remove();
                    cancel();
                    return;
                }

                double angle = increment*Math.PI;
                if(hand)
                    stand.setRightArmPose(stand.getRightArmPose().setX(angle));
                else
                    stand.setLeftArmPose(stand.getLeftArmPose().setX(angle));
                increment += 0.3;

                stand.teleport(stand.getLocation().clone().
                        add(dir.getX(), -0.3*Math.sin(angle)+dir.getY(), dir.getZ()));
            }
        };
    }
}