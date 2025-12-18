package world;

import engine.RenderManager;
import entities.blocks.Block;
import org.joml.Vector3f;

import java.util.*;

public class World {

    private WorldGen worldGen;
    private RenderManager renderer;

    private final Map<String, Block> blocks = new HashMap<>();

    public World(RenderManager renderer) {
        this.renderer = renderer;
        worldGen = new WorldGen(renderer, this);
    }

    public void init() throws Exception {
        worldGen.initFlatworld();
        //worldGen.initWorld();
    }

    public void renderWorld() {
        worldGen.renderWorld();
    }

    public void setBlock(int x, int y, int z, Block block) {
        if(block == null) return;

        String key = key(x, y, z);
        blocks.put(key, block);
        block.setPosition(x, y, z);
        block.setDirty(true);

    }

    public Block getBlock(int x, int y, int z) {
        return blocks.get(key(x, y, z));
    }

    public List<Block> getNearbyBlocks(int x, int y, int z) {
        List<Block> batch = new ArrayList<>();

        // Include center block
        batch.add(getBlock(x, y, z));

        // Include surrounding blocks
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 2; dy++) { // +2 for tall player
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue; // center already added
                    Block b = getBlock(x + dx, y + dy, z + dz);
                    if (b != null) batch.add(b);
                }
            }
        }

        return batch;
    }

    private String key(int x, int y, int z) {
        return x +"_" + y + "_" + z;
    }

    public Collection<Block> getAllBlocks() {
        return blocks.values();
    }

}
