package entities.blocks;

import entities.Entity;
import entities.Model;
import entities.Texture;
import utils.AABB;
import utils.VertexData;

import java.util.ArrayList;
import java.util.List;

public class Block extends Entity {

    private boolean[] faces = {true, true, true, true, true, true};
    private AABB boundingBox;

    public enum Face {
        FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM
    }

    private boolean dirty = false;
    private boolean isAir = false;
    private boolean solid = true;

    public Block(Model model, int x, int y, int z) {
        super(model, x, y, z);
        this.boundingBox = new AABB(x, y, z, x+1, y+1, z+1);
    }

    public Block(Model model) {
        super(model);
        this.boundingBox = new AABB(getPosition(), 1, 1, 1);
    }

    public static Model getBlockModel(Texture texture) {
        float[] vertices = VertexData.vertices;
        float[] normals = VertexData.normals;
        float[] textureCoords = VertexData.uvs;
        int[] indices = VertexData.indices;
        Model model = loader.loadToVao(vertices, textureCoords, normals, indices);
        model.setTexture(texture);
        return model;
    }


    public void setFace(int index, boolean value) {
        faces[index] = value;
    }

    public boolean getFace(int index) {
        return faces[index];
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isAir() {
        return isAir;
    }

    public AABB getBoundingBox() {
        return boundingBox;
    }

    public boolean isSolid() {
        return solid;
    }
}
