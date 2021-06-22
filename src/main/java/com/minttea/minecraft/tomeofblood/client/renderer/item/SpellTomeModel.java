package com.minttea.minecraft.tomeofblood.client.renderer.item;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.spell.ISpellTier;
import com.hollingsworth.arsnouveau.client.renderer.item.TransformAnimatedModel;
import com.minttea.minecraft.tomeofblood.TomeOfBloodMod;

import com.minttea.minecraft.tomeofblood.common.items.BloodTome;

import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SpellTomeModel extends TransformAnimatedModel<BloodTome> {


    ResourceLocation T1 =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier1.geo.json");
    ResourceLocation T2 =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier2.geo.json");
    ResourceLocation T3 =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier3.geo.json");
    ResourceLocation T3_CLOSED =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier3closed.geo.json");
    ResourceLocation T1_CLOSED =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier1closed.geo.json");
    ResourceLocation T2_CLOSED =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier2closed.geo.json");

    public boolean isOpen;



    @Override
    public ResourceLocation getModelLocation(BloodTome book, @Nullable ItemCameraTransforms.TransformType transformType) {

        if(transformType == ItemCameraTransforms.TransformType.GUI){
            if(book.tier == ISpellTier.Tier.ONE)
                return T1_CLOSED;
            if(book.tier == ISpellTier.Tier.TWO)
                return T2_CLOSED;
            return T3_CLOSED;
        }

        if(book.tier == ISpellTier.Tier.ONE)
            return T1;
        if(book.tier == ISpellTier.Tier.TWO)
            return T2;
        return T3;
    }

    @Override
    public ResourceLocation getTextureLocation(BloodTome spellTome) {

            return new ResourceLocation(TomeOfBloodMod.MODID, "textures/items/blood_magic_tome.png");
        }

    @Override
    public ResourceLocation getAnimationFileLocation(BloodTome spellTome) {
        return new ResourceLocation(ArsNouveau.MODID , "animations/spellbook_animations.json");
    }
}
