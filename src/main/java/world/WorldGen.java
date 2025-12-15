package world;

import engine.ObjectLoader;
import engine.RenderManager;
import entities.blocks.Block;
import entities.Model;
import entities.Texture;

import java.util.Random;

public class WorldGen {

    private static final int WIDTH = 50;
    private static final int LENGTH = 50;
    private static final int MAX_HEIGHT = 20;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final World world;

    public WorldGen(RenderManager renderer) {
        this.renderer = renderer;
        this.loader = new ObjectLoader();
        this.world = new World();
    }

    public void initWorld() throws Exception {
        // Load block texture and model
        Texture blockTexture = new Texture(loader.loadTexture("textures/dirt.png"));
        Model blockModel = Block.getBlockModel(blockTexture);

        // Generate mountain using a simple heightmap
        Random rand = new Random();
        int centerX = WIDTH / 2;
        int centerZ = LENGTH / 2;
        double maxDistance = Math.sqrt(centerX*centerX + centerZ*centerZ);

        for (int x = 0; x < WIDTH; x++) {
            for (int z = 0; z < LENGTH; z++) {
                // Simple radial mountain: height decreases with distance from center
                double dx = x - centerX;
                double dz = z - centerZ;
                double distance = Math.sqrt(dx*dx + dz*dz);
                int height = (int)((1.0 - distance / maxDistance) * MAX_HEIGHT);
                height = Math.max(height, 1); // minimum height 1

                for (int y = 0; y < height; y++) {
                    Block block = new Block(blockModel);
                    world.setBlock(x, y, z, block);
                }
            }
        }
    }

    public void renderWorld() {
        // Render all blocks in the world
        for (Block block : world.getAllBlocks()) {
            renderer.processEntity(block);
        }
    }

    public World getWorld() {
        return world;
    }
}