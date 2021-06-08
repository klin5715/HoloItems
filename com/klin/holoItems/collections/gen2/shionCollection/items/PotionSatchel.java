package com.klin.holoItems.collections.gen2.shionCollection.items;

import com.klin.holoItems.abstractClasses.Pack;
import com.klin.holoItems.HoloItems;
import com.klin.holoItems.collections.gen2.shionCollection.ShionCollection;
import com.klin.holoItems.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Set;

public class PotionSatchel extends Pack {
    public static final String name = "potionSatchel";
    private static final Set<Enchantment> accepted = null;

    private static final Material material = Material.LEAD;
    private static final String lore =
            "§6Ability" +"/n"+
            "Store up to 27 potions and throw them" +"/n"+
            "with extended range";
    private static final int durability = 0;
    private static final boolean shiny = true;

    private static  final int size = 27;
    public static final String title = "Potting. . .";
    public static final boolean display = false;

    public static final int cost = 290;
    public static final char key = '1';

    public PotionSatchel(){
        super(name, accepted, material, lore, durability, shiny, size, title, display, cost,
                ""+ShionCollection.key+key, key);
    }

    public void registerRecipes(){
        ShapedRecipe recipe =
                new ShapedRecipe(new NamespacedKey(HoloItems.getInstance(), name), item);
        recipe.shape(" % ","/&/","#*#");
        recipe.setIngredient('%', Material.LEAD);
        recipe.setIngredient('/', Material.STRING);
        recipe.setIngredient('&', Material.SHULKER_BOX);
        recipe.setIngredient('*', Material.SADDLE);
        recipe.setIngredient('#', Material.LEATHER);
        recipe.setGroup(name);
        Bukkit.getServer().addRecipe(recipe);
    }

    public int ability(Inventory inv, ItemStack item, Player player) {
        World world = player.getWorld();
        String potions = "";

        int size = 0;
        for(ItemStack content : inv.getContents()) {
            if(content==null || content.getType()==Material.AIR)
                continue;
            String type = content.getType().toString();
            if(!type.contains("POTION") || type.equals("POTION")) {
                world.dropItemNaturally(player.getLocation(), content);
                continue;
            }
            PotionData potData = ((PotionMeta) content.getItemMeta()).getBasePotionData();
            potions += type.substring(0,type.indexOf("_"))+"-"+potData.getType().toString();
            if(potData.isExtended())
                potions += "+";
            else if(potData.isUpgraded())
                potions += "*";
            else
                potions += "x";
            potions += " ";

            size++;
        }

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING, potions);
        item.setItemMeta(meta);
        player.sendMessage("Filled "+meta.getDisplayName()+"§f to: "+size);
        return size;
    }

    protected void repack(ItemStack item, Inventory inv){
        String stored = item.getItemMeta().
                getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if (stored == null)
            return;

        String[] potions = stored.split(" ");
        for(String potion : potions){
            if(potion.isEmpty())
                continue;
            String[] data = potion.split("-");
            ItemStack pot;
            if ("LINGERING".equals(data[0]))
                pot = new ItemStack(Material.LINGERING_POTION);
            else
                pot = new ItemStack(Material.SPLASH_POTION);
            PotionType type = PotionType.valueOf(data[1].substring(0, data[1].length()-1));
            PotionMeta potMeta = (PotionMeta) pot.getItemMeta();
            potMeta.setBasePotionData(new PotionData(type,
                    data[1].endsWith("+"), data[1].endsWith("*")));
            pot.setItemMeta(potMeta);
            inv.addItem(pot);
        }
    }

    public void effect(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        String potions = meta.getPersistentDataContainer().get(Utility.pack, PersistentDataType.STRING);
        if(potions==null || potions.isEmpty())
            return;

        String[] data = potions.substring(0, potions.indexOf(" ")).split("-");
        ItemStack pot;
        if ("LINGERING".equals(data[0]))
            pot = new ItemStack(Material.LINGERING_POTION);
        else
            pot = new ItemStack(Material.SPLASH_POTION);
        PotionType type = PotionType.valueOf(data[1].substring(0, data[1].length()-1));
        PotionMeta potMeta = (PotionMeta) pot.getItemMeta();
        potMeta.setBasePotionData(new PotionData(type,
                data[1].endsWith("+"), data[1].endsWith("*")));
        pot.setItemMeta(potMeta);

        HumanEntity player = event.getPlayer();
        ThrownPotion potion = (player).launchProjectile(ThrownPotion.class,
                player.getLocation().getDirection().multiply(2));
        potion.setItem(pot);

        meta.getPersistentDataContainer().set(Utility.pack, PersistentDataType.STRING,
                potions.substring(potions.indexOf(" ")+1));
        item.setItemMeta(meta);
        int length = potions.split(" ").length;
        if(length==10 || length==4 || length ==1)
            player.sendMessage("§7"+(length-1)+" remaining");
    }
}