package world;

import engine.ObjectLoader;
import engine.RenderManager;
import entities.blocks.AirBlock;
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
        Model blockModel2 = Block.getBlockModel(blockTexture);
//        for(int x = X_ORIGIN; x < WIDTH; x++) {
//            for(int z = Z_ORIGIN; z < LENGTH; z++) {
//                Block block = new Block(blockModel, x, 0, z);
//                world.setBlock(x, 0, z, block);
//            }
//        }
        Block block = new Block(blockModel);
        Block block2 = new Block(blockModel2);
        world.setBlock(0, 0, 0, block);
        world.setBlock(1, 0, 0, block2);
        Block airBlock = new AirBlock();
        world.setBlock(2, 0, 0, airBlock);

    }

    public void renderWorld() {
//        for(int x = X_ORIGIN; x < WIDTH; x++) {
//            for(int z = Z_ORIGIN; z < LENGTH; z++) {
//                Block block = world.getBlock(x, 0, z);
//                renderer.processEntity(block);
//            }
//        }
        Block block = world.getBlock(0, 0, 0);
        Block block2 = world.getBlock(1, 0, 0);
        Block airBlock = world.getBlock(2, 0, 0);
        renderer.processEntity(block);
        renderer.processEntity(block2);
        renderer.processEntity(airBlock);
    }

}
