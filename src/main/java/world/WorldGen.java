package world;

import engine.ObjectLoader;
import engine.RenderManager;
import entities.Block;
import entities.Model;
import entities.Texture;

import java.util.ArrayList;
import java.util.List;

public class WorldGen {

    private static final int X_ORIGIN = -100;
    private static final int Z_ORIGIN = -100;

    private static final int WIDTH = 200;
    private static final int LENGTH = 200;

    private static RenderManager renderer;
    private static ObjectLoader loader;
    private static List<Block> blocks;

    public WorldGen(RenderManager renderer) {
        this.renderer = renderer;
        loader = new ObjectLoader();
        blocks = new ArrayList<Block>();
    }

    public void initWorld() throws Exception {
        Model model = loader.loadObjModel("/models/block_generic.obj");
        model.setTexture(new Texture(loader.loadTexture("/textures/dirt.png")));
        for(int x = X_ORIGIN; x < X_ORIGIN + WIDTH; x++) {
            for(int z = Z_ORIGIN; z < Z_ORIGIN + LENGTH; z++) {
                Block block = new Block(model, x, 0, z);
                blocks.add(block);
            }
        }
    }

    public void renderWorld() {
        for(Block block : blocks) {
            renderer.processEntity(block);
        }
    }

}
