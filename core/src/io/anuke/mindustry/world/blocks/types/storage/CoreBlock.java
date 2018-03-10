package io.anuke.mindustry.world.blocks.types.storage;

import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Tile;

import static io.anuke.mindustry.Vars.debug;
import static io.anuke.mindustry.Vars.state;

public class CoreBlock extends StorageBlock {
    protected int capacity = 1000;

    public CoreBlock(String name) {
        super(name);

        health = 800;
        solid = true;
        destructible = true;
        size = 3;
        hasInventory = false;
    }

    @Override
    public int handleDamage(Tile tile, int amount){
        return debug ? 0 : amount;
    }

    @Override
    public void handleItem(Item item, Tile tile, Tile source){
        if(Net.server() || !Net.active()) state.inventory.addItem(item, 1);
    }

    @Override
    public boolean acceptItem(Item item, Tile tile, Tile source){
        return item.material && state.inventory.getAmount(item) < capacity;
    }

    @Override
    public Item removeItem(Tile tile){
        for(int i = 0; i < state.inventory.getItems().length; i ++){
            if(state.inventory.getItems()[i] > 0){
                if(Net.server() || !Net.active()) state.inventory.getItems()[i] --;
                return Item.getByID(i);
            }
        }
        return null;
    }

    @Override
    public boolean hasItem(Tile tile){
        TileEntity entity = tile.entity;
        for(int i = 0; i < state.inventory.getItems().length; i ++){
            if(state.inventory.getItems()[i] > 0){
                return true;
            }
        }
        return false;
    }
}
