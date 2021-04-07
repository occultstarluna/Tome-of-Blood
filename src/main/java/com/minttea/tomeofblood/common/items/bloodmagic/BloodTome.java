package com.minttea.tomeofblood.common.items.bloodmagic;

import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.minttea.tomeofblood.common.items.SpellTome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BloodTome extends SpellTome {

    private static final Logger LOGGER = LogManager.getLogger();
    public BloodTome(Tier tier) {
        super(tier);
        //LOGGER.debug("Is the ISTER null?" + (this.getItemStackTileEntityRenderer()==null));
    }

    @Override
    public SpellResolver getSpellResolver(SpellContext context)
    {
        return new BloodSpellResolver(context);
    }


}