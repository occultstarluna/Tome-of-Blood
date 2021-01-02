package com.minttea.tomeofblood.client.renderer.item;

import com.hollingsworth.arsnouveau.client.renderer.item.SpellBookModel;
import com.minttea.tomeofblood.TomeOfBloodMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class TomeRenderer extends ItemStackTileEntityRenderer {
    public final SpellBookModel model = new SpellBookModel();
    public static final ResourceLocation tome = new ResourceLocation(TomeOfBloodMod.MODID + ":textures/entity/tome_of_blood_final.png");

    public TomeRenderer(){ }

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay)
    {
        ms.push();
        ms.translate(0.5,1,1);
        ms.scale(0.5f,0.5f,0.5f);
        ms.rotate(Vector3f.XP.rotation(180));
        IVertexBuilder buffer = buffers.getBuffer(model.getRenderType(tome));
        model.render(ms, buffer,light,overlay, 1,1,1,1,1);
        ms.pop();
    }

    public SpellBookModel getModel()
    {
        return model;
    }
}
