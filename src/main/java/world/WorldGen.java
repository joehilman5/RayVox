package world;

import engine.ObjectLoader;
import engine.RenderManager;
import entities.blocks.Block;
import entities.Model;
import entities.Texture;

import java.util.ArrayList;
import java.util.List;

public class WorldGen {

    private static final int X_ORIGIN = -50;
    private static final int Z_ORIGIN = -50;

    private static final int WIDTH = 50;
    private static final int LENGTH = 50;

    private static RenderManager renderer;
    private static ObjectLoader loader;
    private static List<Block> blocks;

    private World world;

    public WorldGen(RenderManager renderer) {
        this.renderer = renderer;
        loader = new ObjectLoader();
        blocks = new ArrayList<Block>();
        world = new World();
    }

    public void initWorld() throws Exception {
        Texture blockTexture = new Texture(loader.loadTexture("textures/dirt.png"));
        Model blockModel = Block.getBlockModel(blockTexture);
        for(int x = X_ORIGIN; x < WIDTH; x++) {
            for(int z = Z_ORIGIN; z < LENGTH; z++) {
                Block block = new Block(blockModel, x, 0, z);
                world.setBlock(x, 0, z, block);
            }
        }
    }

    public void renderWorld() {
        for(int x = X_ORIGIN; x < WIDTH; x++) {
            for(int z = Z_ORIGIN; z < LENGTH; z++) {
                Block block = world.getBlock(x, 0, z);
                renderer.processEntity(block);
            }
        }
    }

}
