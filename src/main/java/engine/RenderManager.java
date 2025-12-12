package engine;

import entities.Entity;
import entities.Model;
import entities.Texture;
import main.Launcher;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderManager {

    private static WindowManager window;
    private ShaderManager shader;

    private Map<Model, List<Entity>> entities = new HashMap<Model, List<Entity>>();


    public RenderManager() {
        window = Launcher.getWindow();
    }

    public void init() throws Exception {
        shader = new ShaderManager();
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shader.link();
        shader.createUniform("textureSampler");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
        shader.createUniform("lightPosition");
        shader.createUniform("lightColor");
        shader.createUniform("shineDamper");
        shader.createUniform("reflectivity");
    }

    /*
    public void render(Entity entity, Camera camera, Light light) {
        Model model = entity.getModel();
        Texture texture = model.getTexture();
        shader.bind();
        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Utils.createTransform(entity));
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());
        shader.setUniform("viewMatrix", Utils.createView(camera));
        shader.setUniform("lightPosition", light.getPosition());
        shader.setUniform("lightColor", light.getColor());
        shader.setUniform("shineDamper", texture.getShineDamper());
        shader.setUniform("reflectivity", texture.getReflectivity());
        GL30.glBindVertexArray(model.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        shader.unbind();
    }

     */

    public void render(Camera camera, Light light) {

        shader.bind();
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());
        shader.setUniform("viewMatrix", Utils.createView(camera));
        shader.setUniform("lightColor", light.getColor());
        shader.setUniform("lightPosition", light.getPosition());
        shader.setUniform("textureSampler", 0);

        for(Model model: entities.keySet()) {
            prepModel(model, camera, light);
            List<Entity> batch = entities.get(model);
            for(Entity entity: batch) {
                prepareEntity(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES,  model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindModel();
        }
        shader.unbind();
        entities.clear();
    }

    public void processEntity(Entity entity) {
        Model model = entity.getModel();
        List<Entity> batch = entities.get(model);
        if(batch != null) {
            batch.add(entity);
        }else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(model, newBatch);
        }
    }

    private void prepModel(Model model, Camera camera, Light light) {
        Texture texture = model.getTexture();

        shader.setUniform("shineDamper", texture.getShineDamper());
        shader.setUniform("reflectivity", texture.getReflectivity());
        GL30.glBindVertexArray(model.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
    }

    private void prepareEntity(Entity entity) {
        shader.setUniform("transformationMatrix", Utils.createTransform(entity));
    }

    private void unbindModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
