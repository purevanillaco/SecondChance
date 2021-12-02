package co.purevanilla.mcplugins.secondchance.events;

import co.purevanilla.mcplugins.secondchance.Main;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

    public class Death implements Listener {

        @EventHandler
        public void onDamage(EntityDamageEvent e){

            if(e.getEntity() instanceof Player){
                if((((Player) e.getEntity()).getHealth() - e.getFinalDamage()) <= 0){

                    try {
                        if(!Main.registry.hadSecondChance((Player) e.getEntity())){
                            e.setCancelled(true);
                            List<PotionEffect> effects = new ArrayList<>();
                            effects.add(new PotionEffect(PotionEffectType.SATURATION,30*20,10));
                            effects.add(new PotionEffect(PotionEffectType.HEAL,30*20,10));
                            ((Player) e.getEntity()).getPlayer().addPotionEffects(effects);
                            ((Player) e.getEntity()).getPlayer().playEffect(EntityEffect.TOTEM_RESURRECT);
                            ((Player) e.getEntity()).getPlayer().playSound(e.getEntity().getLocation(), Sound.ITEM_TOTEM_USE,1,1);
                            ((Player) e.getEntity()).getPlayer().sendMessage(ChatColor.RED + "You will always remember this as the day you almost caught Captain "+((Player) e.getEntity()).getPlayer().getName()+"!");
                            if(((Player) e.getEntity()).getPlayer().hasPlayedBefore()){
                                ((Player) e.getEntity()).getPlayer().sendMessage(ChatColor.GRAY + "Jokes Aside: This is the fist time you almost die this week, since we want you to keep playing here, we are giving you a second chance, there is no /back command here for the default rank, so we wanted to be nice and provide you with a free second chance per week. Please, take care!");
                            } else {
                                ((Player) e.getEntity()).getPlayer().sendMessage(ChatColor.GRAY + "Jokes Aside: This is the fist time you almost die, since we want you to keep playing here, we are giving you a second (and last!) chance, there is no /back (on death) command here for the default rank, so we wanted to be nice and provide you with a free second chance per week. Please, take care!");
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
