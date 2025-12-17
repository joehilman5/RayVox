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
        //worldGen.initFlatworld();
        worldGen.initWorld();
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
        batch.add(getBlock(x+1, y, z));
        batch.add(getBlock(x-1, y, z));
        batch.add(getBlock(x, y+1, z));
        batch.add(getBlock(x, y-1, z));
        batch.add(getBlock(x, y, z+1));
        batch.add(getBlock(x, y, z-1));
        return batch;
    }

    private String key(int x, int y, int z) {
        return x +"_" + y + "_" + z;
    }

    public Collection<Block> getAllBlocks() {
        return blocks.values();
    }

}
