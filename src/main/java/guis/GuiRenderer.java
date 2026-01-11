package guis;

import engine.ObjectLoader;
import engine.ShaderManager;
import engine.WindowManager;
import entities.Model;
import main.Launcher;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import utils.Utils;

import java.util.List;

public class GuiRenderer {

    private static WindowManager window;
    private ShaderManager shader;
    private final ObjectLoader loader;

    private Model quad;

    public GuiRenderer(ObjectLoader loader) {
        window = Launcher.getWindow();
        this.loader = loader;
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = loader.loadToVao(positions);
    }

    public void init() throws Exception {
        shader = new ShaderManager();
        shader.createVertexShader(Utils.loadResource("/shaders/guiVertex.vs"));
        shader.createFragmentShader(Utils.loadResource("/shaders/guiFragment.fs"));
        shader.link();
        shader.createUniform("textureSampler");
        //shader.createUniform("transformationMatrix");
    }

    public void render(List<GuiTexture> guis) {
        shader.bind();
        GL30.glBindVertexArray(quad.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        for(GuiTexture gui: guis) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
            shader.setUniform("textureSampler", 0);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.unbind();
    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
