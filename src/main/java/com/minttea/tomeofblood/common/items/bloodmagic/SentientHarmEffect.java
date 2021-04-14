package com.minttea.tomeofblood.common.items.bloodmagic;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.common.potions.ManaRegenEffect;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.minttea.tomeofblood.common.items.NewGlyphLib;
import com.minttea.tomeofblood.util.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.item.BloodMagicItems;
import wayoftime.bloodmagic.common.item.soul.ItemSentientSword;
import wayoftime.bloodmagic.potion.BloodMagicPotions;
import wayoftime.bloodmagic.will.PlayerDemonWillHandler;

import javax.annotation.Nullable;
import java.util.List;

public class SentientHarmEffect extends AbstractEffect {


    public SentientHarmEffect() {
        super(NewGlyphLib.EffectSentientHarmID, "Sentient Harm");
    }

    @Override
    public void onResolve(RayTraceResult rayTraceResult, World world, LivingEntity shooter, List<AbstractAugment> augments, SpellContext spellContext)
    {
        if(rayTraceResult instanceof EntityRayTraceResult)
        {
            PlayerEntity player = (PlayerEntity) spellContext.getCaster();
            EnumDemonWillType type = PlayerDemonWillHandler.getLargestWillType(player);
            int souls = (int) PlayerDemonWillHandler.getTotalDemonWill(type, player);


            int bracket = getBracket(type, souls);
            float damage = 4.0f + (2.0f*getExtraDamage(spellContext, type, souls)) + (2.0f * getAmplificationBonus(augments));
            Entity entity = ((EntityRayTraceResult) rayTraceResult).getEntity();
            int time = getBuffCount(augments, AugmentExtendTime.class);
            applyPotion((LivingEntity) entity, BloodMagicPotions.SOUL_SNARE,augments,300,0);
            switch (type) {
                case CORROSIVE:
                    if (entity instanceof LivingEntity) {
                        applyPotion((LivingEntity) entity, Effects.WITHER, augments, (ItemSentientSword.poisonTime[bracket]*time) , ItemSentientSword.poisonLevel[bracket]);
                    }
                    break;
                case DEFAULT:
                    break;
                case DESTRUCTIVE:
                    break;
                case VENGEFUL:
                    if(!entity.isAlive())
                    {
                        player.addPotionEffect(new EffectInstance(ModPotions.MANA_REGEN_EFFECT,(ItemSentientSword.poisonTime[bracket]*time) , ItemSentientSword.poisonLevel[bracket],false,false));
                    }
                    break;
                case STEADFAST:
                    if(!entity.isAlive())
                    {
                        float absorption = player.getAbsorptionAmount();
                        player.addPotionEffect(new EffectInstance(Effects.ABSORPTION,(ItemSentientSword.absorptionTime[bracket]*time),127,false,false));
                        player.setAbsorptionAmount((float) Math.min(absorption + ((LivingEntity)entity).getMaxHealth() * 0.05f, ItemSentientSword.maxAbsorptionHearts ));
                    }
                    break;
            }
        }

    }

    public float getExtraDamage(SpellContext spellContext, EnumDemonWillType type, int bracket)
    {


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
    public String getBookDescription() {
        return "An advanced spell, that utilizes your collected demonic will to improve your damage output.";
    }
}
