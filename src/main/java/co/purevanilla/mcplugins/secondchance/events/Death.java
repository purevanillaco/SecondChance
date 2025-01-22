package co.purevanilla.mcplugins.secondchance.events;

import co.purevanilla.mcplugins.secondchance.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Death implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof Player player){
            try {
                if(!Main.registry.hadSecondChance(player)){
                    e.setCancelled(true);
                }
            } catch (Exception ex) {
                Main.registry.getPlugin().getLogger().log(Level.WARNING,ex.getMessage());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player player){
            if((player.getHealth() - e.getFinalDamage()) <= 0){
                try {
                    if(!Main.registry.hadSecondChance(player)){
                        e.setCancelled(true);
                        List<PotionEffect> effects = new ArrayList<>();
                        effects.add(new PotionEffect(PotionEffectType.SATURATION,30*20,10));
                        effects.add(new PotionEffect(PotionEffectType.REGENERATION,30*20,10));
                        player.addPotionEffects(effects);
                        player.playEffect(EntityEffect.TOTEM_RESURRECT);
                        player.playSound(e.getEntity().getLocation(), Sound.ITEM_TOTEM_USE,1,1);
                        player.sendMessage(Component.text("You will always remember this as the day you almost caught Captain ")
                                .color(NamedTextColor.RED)
                                .append(Component.text(player.getName()).color(NamedTextColor.RED))
                                .append(Component.text("!").color(NamedTextColor.RED)));

                        if (player.hasPlayedBefore()) {
                            player.sendMessage(Component.text("Jokes Aside: This is the first time you almost die this week, ")
                                    .color(NamedTextColor.GRAY)
                                    .append(Component.text("since we want you to keep playing here, we are giving you a second chance. Please, take care!")
                                            .color(NamedTextColor.GRAY)));
                        } else {
                            player.sendMessage(Component.text("Jokes Aside: This is the first time you almost die, ")
                                    .color(NamedTextColor.GRAY)
                                    .append(Component.text("since we want you to keep playing here, we are giving you a second (and last!) chance, so we wanted to be nice and provide you with a free second chance per week. Please, take care!")
                                            .color(NamedTextColor.GRAY)));
                        }
                        Main.registry.markPlayer((Player) e.getEntity());
                    }
                } catch (Exception ex) {
                    Main.registry.getPlugin().getLogger().log(Level.WARNING,ex.getMessage());
                }
            }
        }
    }
}
