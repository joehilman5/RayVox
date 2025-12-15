package engine;

import entities.Model;
import entities.Texture;
import entities.blocks.Block;
import entities.blocks.Chunk;
import main.Launcher;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChunkRenderer {

    private static WindowManager window;
    private ShaderManager shader;

    private List<Chunk> chunks = new ArrayList<>();

    public ChunkRenderer() {
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

    public void render(Camera camera, Light light) {
        shader.bind();
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());
        shader.setUniform("viewMatrix", Utils.createView(camera));
        shader.setUniform("lightColor", light.getColor());
        shader.setUniform("lightPosition", light.getPosition());
        shader.setUniform("textureSampler", 0);

        for(Chunk chunk : chunks) {

           for(int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
               for(int y = 0; y < Chunk.CHUNK_SIZE_Y; y++) {
                   for(int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                       Block block = chunk.getBlock(x,y,z);
                       if(block == null) continue;
                       prepareBlock(block);
                       shader.setUniform("transformationMatrix", Utils.createTransform(block));
                       GL11.glDrawElements(GL11.GL_TRIANGLES, block.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                       unbindBlock();
                   }
               }
           }
        }
        chunks.clear();
    }

    public void processChunk(Chunk chunk) {
        chunks.add(chunk);
    }

    private void prepareBlock(Block block) {
        Model model = block.getModel();
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

    private void unbindBlock() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

}
