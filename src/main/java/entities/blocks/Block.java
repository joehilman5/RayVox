package entities.blocks;

import entities.Entity;
import entities.Model;
import entities.Texture;
import utils.VertexData;

import java.util.ArrayList;
import java.util.List;

public class Block extends Entity {

    private boolean[] faces = {true, true, true, true, true, true};

    public Block(Model model, int x, int y, int z) {
        super(model, x, y, z);
    }

    public static Model getBlockModel(Texture texture) {
        float[] vertices = VertexData.vertices;
        float[] normals = VertexData.normals;
        float[] textureCoords = VertexData.textureCoords;
        int[] indices = VertexData.indices;
        Model model = loader.loadToVao(vertices, textureCoords, normals, indices);
        model.setTexture(texture);
        return model;
    }

    public void updateBlockModel() {
        float[] vertices = VertexData.vertices;
        float[] normals = VertexData.normals;
        float[] textureCoords = VertexData.textureCoords;
        List<Integer> indicesList = new ArrayList<>();
        if(getFace(0)) {
            for(int i = 0; i < VertexData.frontFace.length; i++) {
                indicesList.add(VertexData.frontFace[i]);
            }
        }
        if(getFace(1)) {
            for(int i = 0; i < VertexData.backFace.length; i++) {
                indicesList.add(VertexData.backFace[i]);
            }
        }
        if(getFace(2)) {
            for(int i = 0; i < VertexData.leftFace.length; i++) {
                indicesList.add(VertexData.leftFace[i]);
            }
        }
        if(getFace(3)) {
            for(int i = 0; i < VertexData.rightFace.length; i++) {
                indicesList.add(VertexData.rightFace[i]);
            }
        }
        if(getFace(4)) {
            for(int i = 0; i < VertexData.topFace.length; i++) {
                indicesList.add(VertexData.topFace[i]);
            }
        }
        if(getFace(5)) {
            for(int i = 0; i < VertexData.bottomFace.length; i++) {
                indicesList.add(VertexData.bottomFace[i]);
            }
        }
        int[] indices = new int[indicesList.size()];
        for(int i = 0; i < indicesList.size(); i++) {
            indices[i] = indicesList.get(i);
        }

        loader.updateModel(this.getModel(), vertices, textureCoords, normals, indices);
    }

    public void setFace(int index, boolean value) {
        faces[index] = value;
    }

    public boolean getFace(int index) {
        return faces[index];
    }

}
