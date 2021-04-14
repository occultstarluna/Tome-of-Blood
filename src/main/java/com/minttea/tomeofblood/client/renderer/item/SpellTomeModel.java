package com.minttea.tomeofblood.client.renderer.item;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.spell.ISpellTier;
import com.hollingsworth.arsnouveau.client.renderer.item.TransformAnimatedModel;
import com.minttea.tomeofblood.TomeOfBloodMod;
import com.minttea.tomeofblood.common.items.SpellTome;
import com.minttea.tomeofblood.common.items.astral.AstralTome;
import com.minttea.tomeofblood.common.items.bloodmagic.BloodTome;
import com.minttea.tomeofblood.common.items.botania.BotanicTome;
import com.minttea.tomeofblood.common.items.eidolon.WarlockTome;
import com.minttea.tomeofblood.common.items.nature.NaturalTome;
import com.minttea.tomeofblood.common.items.occultism.OccultTome;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SpellTomeModel extends TransformAnimatedModel<SpellTome> {


    ResourceLocation T1 =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier1.geo.json");
    ResourceLocation T2 =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier2.geo.json");
    ResourceLocation T3 =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier3.geo.json");
    ResourceLocation T3_CLOSED =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier3closed.geo.json");
    ResourceLocation T1_CLOSED =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier1closed.geo.json");
    ResourceLocation T2_CLOSED =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier2closed.geo.json");

    public boolean isOpen;



    @Override
    public ResourceLocation getModelLocation(SpellTome book, @Nullable ItemCameraTransforms.TransformType transformType) {

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
    public ResourceLocation getTextureLocation(SpellTome spellTome) {


        if(spellTome instanceof AstralTome)
        {
            return new ResourceLocation(TomeOfBloodMod.MODID, "textures/items/astral_tome.png");
        }
        else if(spellTome instanceof BloodTome)
        {
            return new ResourceLocation(TomeOfBloodMod.MODID, "textures/items/blood_magic_tome.png");
        }
        else if(spellTome instanceof BotanicTome)
        {
            return new ResourceLocation(TomeOfBloodMod.MODID, "textures/items/botania_tome.png");
        }
        else if(spellTome instanceof WarlockTome)
        {
            return new ResourceLocation(TomeOfBloodMod.MODID, "textures/items/eidolon_tome.png");
        }
        else if (spellTome instanceof NaturalTome)
        {
            return new ResourceLocation(TomeOfBloodMod.MODID, "textures/items/natural_tome.png");
        }
        else if (spellTome instanceof OccultTome)
        {
            return new ResourceLocation(TomeOfBloodMod.MODID, "textures/items/occult_tome.png");
        }
        return new ResourceLocation(ArsNouveau.MODID, "textures/items/spellbook_purple.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SpellTome spellTome) {
        return new ResourceLocation(ArsNouveau.MODID , "animations/spellbook_animations.json");
    }
}
