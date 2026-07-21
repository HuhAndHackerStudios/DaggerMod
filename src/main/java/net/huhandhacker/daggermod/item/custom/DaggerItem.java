package net.huhandhacker.daggermod.item.custom;

import net.huhandhacker.daggermod.DaggerMod;
import net.huhandhacker.daggermod.stat.ModStats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;

public class DaggerItem extends Item {
    public DaggerItem(Properties properties) {
        super(properties);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(DaggerMod.MOD_ID);

    @Override
    public ItemUseAnimation getUseAnimation(final ItemStack itemStack) {
        return ItemUseAnimation.TRIDENT;
    }

    @Override
    public int getUseDuration(final ItemStack itemStack, final LivingEntity user) {
        return 62000;
    }



    @Override
    public boolean releaseUsing(final ItemStack itemStack, final Level level, final LivingEntity entityt, final int remainingTime) {
        if (entityt instanceof Player player) {
            int timeHeld = this.getUseDuration(itemStack, entityt) - remainingTime;
            if (timeHeld < 6200) {
                return false;
            }
            else {
                if (player.getFoodData().getFoodLevel() > 1) {

                    Vec3 look = player.getLookAngle();

                    AABB hitbox = player.getBoundingBox()
                            .expandTowards(player.getDeltaMovement())
                            .inflate(0.5);

                    List<LivingEntity> entities = level.getEntitiesOfClass(
                            LivingEntity.class,
                            hitbox,
                            entity -> entity != player && entity.isAlive()
                    );

                    if (!entities.isEmpty()) {
                        LivingEntity target = entities.getFirst();

                        Vec3 targetLook = target.getLookAngle();

                        Vec3 toPlayer = player.position()
                                .subtract(target.position())
                                .normalize();

                        double dot = targetLook.dot(toPlayer);

                        if (dot < 0.1) {

                            target.hurt(
                                    player.damageSources().playerAttack(player),
                                    38.0F
                            );
                            player.awardStat(ModStats.BACKSTABS);
                            player.getCooldowns().addCooldown(this.getDefaultInstance(), 20 * 20);

                        } else {

                            target.hurt(
                                    player.damageSources().playerAttack(player),
                                    7.25F
                            );

                        }
                    }

                }
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.nextDamageWillBreak()) {
            return InteractionResult.FAIL;
        }

        if (EnchantmentHelper.getTridentSpinAttackStrength(itemInHand, player) > 0.0F && !player.isInWaterOrRain()) {
            return InteractionResult.FAIL;
        }

        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }
}
