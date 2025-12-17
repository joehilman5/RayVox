package main;

import engine.*;
import entities.Entity;
import entities.Model;
import entities.Player;
import entities.Texture;
import entities.blocks.AirBlock;
import entities.blocks.Block;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import world.World;
import world.WorldGen;

import java.util.ArrayList;
import java.util.List;

public class RayVox implements IRayVox {

    private static final float CAMERA_MOVE_SPEED = 0.005f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private FirstPersonCamera camera;
    private Vector3f cameraInc;
    private Light light;

    private Model bunnyModel;
    private Entity bunny;

    private Texture blockTexture;
    private Model blockModel;
    private Block block;
    private Model blockModel2;
    private Block block2;
    private World world;

    private Model playerModel;
    private Player player;

    public RayVox() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        cameraInc = new Vector3f(0, 0, 0);
        world = new World(renderer);
        //camera = new Camera();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        world.init();

        light = new Light(new Vector3f(0, 10, 0), new Vector3f(1, 1, 1));
        bunnyModel = loader.loadObjModel("/models/bunny.obj");
        bunnyModel.setTexture(new Texture(loader.loadTexture("/textures/dirt.png")));
        playerModel = loader.loadObjModel("/models/player.obj");
        playerModel.setTexture(new Texture(loader.loadTexture("/textures/dirt.png")));
        bunnyModel.getTexture().setShineDamper(10);
        bunnyModel.getTexture().setReflectivity(1f);
        bunny = new Entity(bunnyModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1f);
        player = new Player(playerModel, new Vector3f(0, 3, 0), new Vector3f(0, 0, 0), 1f, world);
        camera = new FirstPersonCamera(player);

        blockTexture = new Texture(loader.loadTexture("/textures/dirt.png"));
        blockModel = Block.getBlockModel(blockTexture);
        blockModel2 = Block.getBlockModel(blockTexture);
        block = new Block(blockModel, 0, 0, 0);
        block2 = new Block(blockModel2, 1, 0, 0);

    }

    @Override
    public void input() {
//        cameraInc.set(0, 0, 0);
//        if(window.isKeyPressed(GLFW.GLFW_KEY_W)) {
//            cameraInc.z = -1;
//        }
//        if(window.isKeyPressed(GLFW.GLFW_KEY_S)) {
//            cameraInc.z = 1;
//        }
//        if(window.isKeyPressed(GLFW.GLFW_KEY_A)) {
//            cameraInc.x = -1;
//        }
//        if(window.isKeyPressed(GLFW.GLFW_KEY_D)) {
//            cameraInc.x = 1;
//        }
//        if(window.isKeyPressed(GLFW.GLFW_KEY_Z)) {
//            cameraInc.y = -1;
//        }
//        if(window.isKeyPressed(GLFW.GLFW_KEY_X)) {
//            cameraInc.y = 1;
//        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_T)) {
            System.out.println("X: " + camera.getX() + " Y: " + camera.getY() + " Z: " + camera.getZ());
            System.out.println("RotX " + camera.getRotX() + " RotY " + camera.getRotY() + " RotZ " + camera.getRotZ());
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_P)) {
            //System.out.println(worldGen.getWorld().getNearbyBlocks(player.getX(), player.getY(), player.getZ()));
        }

    }

    @Override
    public void update() {
        //camera.movePosition(cameraInc.x * CAMERA_MOVE_SPEED, cameraInc.y * CAMERA_MOVE_SPEED, cameraInc.z * CAMERA_MOVE_SPEED);
        player.update();
        camera.update();

        bunny.incRotation(0, 0.5f, 0);
    }

    @Override
    public void render() {
        renderer.clear();

        if(window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        world.renderWorld();
        renderer.processEntity(player);

        window.setClearColor(0, 1, 1, 1);
        renderer.render(camera, light);

    }

    @Override
    public void cleanUp() {
        renderer.cleanUp();
        loader.cleanUp();
    }
}
