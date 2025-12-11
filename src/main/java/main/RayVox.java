package main;

import engine.ObjectLoader;
import engine.RenderManager;
import engine.WindowManager;
import entities.Model;
import entities.Texture;
import org.lwjgl.opengl.GL11;

import javax.swing.*;

public class RayVox implements IRayVox {

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Model model;

    public RayVox() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
        };

        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        model = loader.loadToVao(vertices, textureCoords, indices);
        model.setTexture(new Texture(loader.loadTexture("/textures/dirt.png")));

    }

    @Override
    public void input() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        if(window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(1, 0, 0, 1);
        renderer.render(model);
    }

    @Override
    public void cleanUp() {
        renderer.cleanUp();
        loader.cleanUp();
    }
}
