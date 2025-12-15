package world;

import entities.blocks.Block;

import java.util.HashMap;
import java.util.Map;

public class World {

    private final Map<String, Block> blocks = new HashMap<>();

    public void setBlock(int x, int y, int z, Block block) {
        if(block == null) return;

        String key = key(x, y, z);
        blocks.put(key, block);

        block.setDirty(true);

        //updateBlockFaces(block);

        //updateNeighborFaces(x, y, z);
    }

    public Block getBlock(int x, int y, int z) {
        return blocks.get(key(x, y, z));
    }

    private String key(int x, int y, int z) {
        return x +"_" + y + "_" + z;
    }

}
