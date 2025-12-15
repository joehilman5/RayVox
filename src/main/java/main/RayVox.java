package main;

import engine.*;
import entities.Entity;
import entities.Model;
import entities.Texture;
import entities.blocks.Block;
import entities.blocks.Chunk;
import entities.blocks.DirtBlock;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import world.WorldGen;

import java.util.ArrayList;
import java.util.List;

public class RayVox implements IRayVox {

    private static final float CAMERA_MOVE_SPEED = 0.05f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;
    private final ChunkRenderer chunkRenderer;

    private Camera camera;
    private Vector3f cameraInc;
    private Light light;

    private Model bunnyModel;
    private Entity bunny;

    private Texture blockTexture;
    private Model blockModel;
    private Block block;
    private Chunk chunk;
    private Model blockModel2;
    private Block block2;
    private WorldGen worldGen;

    public RayVox() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
        chunkRenderer = new ChunkRenderer();
        worldGen = new WorldGen(renderer);
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        chunkRenderer.init();
        worldGen.initWorld();

        light = new Light(new Vector3f(0, 10, 2), new Vector3f(1, 1, 1));
        bunnyModel = loader.loadObjModel("/models/bunny.obj");
        bunnyModel.setTexture(new Texture(loader.loadTexture("/textures/dirt.png")));
        bunnyModel.getTexture().setShineDamper(10);
        bunnyModel.getTexture().setReflectivity(1f);
        bunny = new Entity(bunnyModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1f);

        blockTexture = new Texture(loader.loadTexture("/textures/dirt.png"));
        blockModel = Block.getBlockModel(blockTexture);
        blockModel2 = Block.getBlockModel(blockTexture);
        block = new Block(blockModel, 0, 0, 0);
        block2 = new Block(blockModel2, 1, 0, 0);
        chunk = new Chunk(0, 0);
        chunk.setBlock(block);
        chunk.setBlock(block2);

    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);
        if(window.isKeyPressed(GLFW.GLFW_KEY_W)) {
            cameraInc.z = -1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_A)) {
            cameraInc.x = -1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_Z)) {
            cameraInc.y = -1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_T)) {
            block.setFace(3, !block.getFace(3));
            block.updateBlockModel();
        }

    }

    @Override
    public void update() {
        camera.movePosition(cameraInc.x * CAMERA_MOVE_SPEED, cameraInc.y * CAMERA_MOVE_SPEED, cameraInc.z * CAMERA_MOVE_SPEED);

        bunny.incRotation(0, 0.5f, 0);
    }

    @Override
    public void render() {
        renderer.clear();

        if(window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        //renderer.processEntity(block);
        //chunkRenderer.processChunk(chunk);
        worldGen.renderWorld();

        window.setClearColor(0, 1, 1, 1);
        renderer.render(camera, light);
        chunkRenderer.render(camera, light);

    }

    @Override
    public void cleanUp() {
        renderer.cleanUp();
        loader.cleanUp();
    }
}
