package com.forestotzka.yurufu.sloves;

import com.forestotzka.yurufu.sloves.datagen.ModBlockTagProvider;
import com.forestotzka.yurufu.sloves.datagen.ModItemTagProvider;
import com.forestotzka.yurufu.sloves.datagen.ModLootTableProvider;
import com.forestotzka.yurufu.sloves.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SlovesDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        //pack.addProvider(ModBlockTagProvider::new);
        //pack.addProvider(ModItemTagProvider::new);
        //pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModRecipeProvider::new);
    }
}
