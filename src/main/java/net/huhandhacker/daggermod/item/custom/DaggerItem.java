package net.huhandhacker.daggermod.item.custom;

import net.huhandhacker.daggermod.DaggerMod;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public boolean releaseUsing(final ItemStack itemStack, final Level level, final LivingEntity entity, final int remainingTime) {
        if (entity instanceof Player player) {
            if (player.getFoodData().getFoodLevel() > 1) {
                    int timeHeld = this.getUseDuration(itemStack, entity) - remainingTime;
                if (timeHeld < 10) {
                    return false;
                }
                float yRot = player.getYRot();
                float xRot = player.getXRot();
                float xd = -Mth.sin(yRot * (float) (Math.PI / 180.0)) * Mth.cos(xRot * (float) (Math.PI / 180.0));
                float yd = -Mth.sin(xRot * (float) (Math.PI / 180.0));
                float zd = Mth.cos(yRot * (float) (Math.PI / 180.0)) * Mth.cos(xRot * (float) (Math.PI / 180.0));
                float dist = Mth.sqrt(xd * xd + yd * yd + zd * zd);
                xd *= 0.75 / dist;
                yd *= 0.75 / dist;
                zd *= 0.75 / dist;
                LOGGER.info(String.valueOf("x:" + player.getLookAngle().x));
                LOGGER.info(String.valueOf("y:" + player.getLookAngle().y));
                LOGGER.info(String.valueOf("z:" + player.getLookAngle().z));
                player.causeFoodExhaustion(10F);
                player.push(xd, yd, zd);

                if (player.onGround()) {
                    float heightDifference = 0.001F;
                    player.move(MoverType.SELF, new Vec3(0.0, 0.001F, 0.0));
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
