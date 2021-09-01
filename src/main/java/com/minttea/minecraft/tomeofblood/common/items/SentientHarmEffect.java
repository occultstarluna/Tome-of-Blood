package com.minttea.minecraft.tomeofblood.common.items;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.augment.*;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.minttea.minecraft.tomeofblood.TomeOfBloodMod;
import com.minttea.minecraft.tomeofblood.setup.Registries.SpellRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.item.BloodMagicItems;
import wayoftime.bloodmagic.common.item.soul.ItemSentientSword;
import wayoftime.bloodmagic.potion.BloodMagicPotions;
import wayoftime.bloodmagic.will.PlayerDemonWillHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class SentientHarmEffect extends AbstractEffect {

    public static SentientHarmEffect INSTANCE = new SentientHarmEffect();

    public SentientHarmEffect() {
        super(NewGlyphLib.EffectSentientHarmID, "Sentient Harm");

    }
    @Override
    public void onResolve(RayTraceResult rayTraceResult, World world, LivingEntity shooter, SpellStats spellStats, SpellContext spellContext)
    {
        if(rayTraceResult instanceof EntityRayTraceResult)
        {
            PlayerEntity player = (PlayerEntity) spellContext.getCaster();
            EnumDemonWillType type = PlayerDemonWillHandler.getLargestWillType(player);
            int souls = (int) PlayerDemonWillHandler.getTotalDemonWill(type, player);




            int bracket = getBracket(type, souls);
            //PortUtil.sendMessage(player, new StringTextComponent("Bracket "+ bracket));
            Entity entity = ((EntityRayTraceResult) rayTraceResult).getEntity();
            int time = spellStats.getDurationInTicks();
            float damage = (float) (4.0f + (2.0f*getExtraDamage(spellContext, type, souls)) + (2.0f * spellStats.getAmpMultiplier()));
            //PortUtil.sendMessage(player, new StringTextComponent("Duration is " + time + "Damage is " + damage));
            ((LivingEntity)entity).addPotionEffect(new EffectInstance(BloodMagicPotions.SOUL_SNARE,300,0));

            switch (type) {
                case CORROSIVE:
                    if (entity instanceof LivingEntity) {
                        //applyPotion((LivingEntity) entity, Effects.WITHER, spellStats , (ItemSentientSword.poisonTime[bracket]*time) , ItemSentientSword.poisonLevel[bracket], true);
                        ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.WITHER, (time > 0) ? (ItemSentientSword.poisonTime[bracket]*time):(ItemSentientSword.poisonTime[bracket]), ItemSentientSword.poisonLevel[bracket]+1));
                        //PortUtil.sendMessage(player, new StringTextComponent("Will Type is " + type.getString()+", "+souls));
                    }
                    break;
                case DEFAULT:
                    //PortUtil.sendMessage(player, new StringTextComponent("Will Type is " + type.getString()+", "+souls));
                    break;
                case DESTRUCTIVE:
                    //PortUtil.sendMessage(player, new StringTextComponent("Will Type is " + type.getString()+", "+souls));
                    break;
                case VENGEFUL:
                    //sendMessage(player, new StringTextComponent("Will Type is " + type.getString()+", "+souls));
                    if(((LivingEntity) entity).getHealth() < damage)
                    {
                        player.addPotionEffect(new EffectInstance(ModPotions.MANA_REGEN_EFFECT,(time > 0) ? (ItemSentientSword.absorptionTime[bracket]*time):(ItemSentientSword.absorptionTime[bracket]) , ItemSentientSword.absorptionTime[bracket],false,false));
                    }
                    break;
                case STEADFAST:
                    //PortUtil.sendMessage(player, new StringTextComponent("Will Type is " + type.getString()+", "+souls));
                    if(((LivingEntity) entity).getHealth() < damage)
                    {
                        float absorption = player.getAbsorptionAmount();
                        player.addPotionEffect(new EffectInstance(Effects.ABSORPTION,(time > 0) ? (ItemSentientSword.absorptionTime[bracket]*time):(ItemSentientSword.absorptionTime[bracket]),127,false,false));
                        player.setAbsorptionAmount((float) Math.min(absorption + ((LivingEntity)entity).getMaxHealth() * 0.25f, ItemSentientSword.maxAbsorptionHearts ));
                    }
                    break;
            }

            dealDamage(world,shooter,damage,spellStats,entity,buildDamageSource(world, shooter).setMagicDamage());
        }

    }

    public float getExtraDamage(SpellContext spellContext, EnumDemonWillType type, int souls)
    {


        int bracket = getBracket(type, souls);
        if(bracket<0)
        {
            return 0;
        }
        switch (type)
        {
            case CORROSIVE:
            case DEFAULT: return (float) ItemSentientSword.defaultDamageAdded[bracket];
            case DESTRUCTIVE: return (float) ItemSentientSword.destructiveDamageAdded[bracket];
            case VENGEFUL: return (float)  ItemSentientSword.vengefulDamageAdded[bracket];
            case STEADFAST: return (float) ItemSentientSword.steadfastDamageAdded[bracket];
        }
        return 0;
    }

        public int getBracket(EnumDemonWillType type, int souls)
        {
            int bracket = -1;
            for(int i = 0; i< ItemSentientSword.soulBracket.length; i++)
            {
                if(souls >= ItemSentientSword.soulBracket[i])
                {
                    bracket = i;
                }
            }
            return bracket;
        }



    @Override
    public int getManaCost() {
        return 50;
    }

    @Nullable
    @Override
    public Item getCraftingReagent() {
        return BloodMagicItems.SENTIENT_SWORD.get();
    }

    @Override
    public Tier getTier()
    {
        return Tier.TWO;
    }

    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(
                AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE,
                AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE,
                AugmentFortune.INSTANCE
        );
    }

    @Override
    public String getBookDescription() {
        return "An advanced spell, that utilizes your collected demonic will to improve your damage output.";
    }
    public Set<SpellSchool> getSchools() {
        return setOf(SpellRegistry.BLOODMAGIC);
    }
}
