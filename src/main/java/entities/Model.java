package entities;

public class Model {

    private int vaoId;
    private int vertexCount;
    private Texture texture;

    public Model(int vaoId, int vertexCount) {
        this.vaoId = vaoId;
        this.vertexCount = vertexCount;
    }

    public Model(int vaoId, int vertexCount, Texture texture) {
        this.vaoId = vaoId;
        this.vertexCount = vertexCount;
        this.texture = texture;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }
}
