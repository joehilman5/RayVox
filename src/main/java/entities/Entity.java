package entities;

import engine.ObjectLoader;
import org.joml.Vector3f;

public class Entity {

    private Model model;
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    private ObjectLoader loader;

    public Entity(Model model) {
        this.model = model;
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.scale = 1;
        this.loader = new ObjectLoader();
    }

    public Entity(Model model, int x, int y, int z) {
        this.model = model;
        this.position = new Vector3f(x, y, z);
        this.rotation = new Vector3f(0, 0, 0);
        this.scale = 1;
    }

    public Entity(Model model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.loader = new ObjectLoader();
    }

    public void incPosition(float x, float y, float z) {
        this.position.x += x;
        this.position.y += y;
        this.position.z += z;
    }


    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void incRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public ObjectLoader getLoader() {
        return loader;
    }
}
