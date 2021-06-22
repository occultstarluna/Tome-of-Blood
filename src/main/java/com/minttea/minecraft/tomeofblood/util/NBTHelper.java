package com.minttea.minecraft.tomeofblood.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;

import javax.swing.*;

public class NBTHelper {
    public static class Item
    {

        public static boolean verify(ItemStack stack, String tag)
        {
            return !stack.isEmpty() && stack.getOrCreateTag().contains(tag);
        }
        public static ListNBT getList(ItemStack stack, String tag, int type) {
            return verify(stack,tag) ? stack.getOrCreateTag().getList(tag, type) : new ListNBT();
        }

        public static void setList(ItemStack stack, String tag, ListNBT nbt) {
            stack.getOrCreateTag().put(tag, nbt);
        }
    }
}
