package main;

import engine.*;
import entities.Entity;
import entities.Model;
import entities.Texture;
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
    private final WorldGen worldGen;

    private Camera camera;
    private Vector3f cameraInc;

    private Model bunnyModel;
    private Entity bunny;
    private Light light;

    private Model model;
    private List<Entity> entities;

    public RayVox() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
        entities = new ArrayList<>();
        worldGen = new WorldGen(renderer);
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        light = new Light(new Vector3f(0, 2, 2), new Vector3f(1, 1, 1));
        bunnyModel = loader.loadObjModel("/models/bunny.obj");
        bunnyModel.setTexture(new Texture(loader.loadTexture("/textures/dirt.png")));
        bunnyModel.getTexture().setShineDamper(10);
        bunnyModel.getTexture().setReflectivity(1f);
        bunny = new Entity(bunnyModel, new Vector3f(0, 0, -10), new Vector3f(0, 0, 0), 1f);

        model = loader.loadObjModel("/models/block_generic.obj");
        model.setTexture(new Texture(loader.loadTexture("/textures/dirt.png")));
        worldGen.initWorld();

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

        renderer.processEntity(bunny);
        worldGen.renderWorld();

        window.setClearColor(0, 1, 1, 1);
        renderer.render(camera, light);

    }

    @Override
    public void cleanUp() {
        renderer.cleanUp();
        loader.cleanUp();
    }
}
