package engine;

import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f rotation;

    public Camera() {
        position = new Vector3f(0, 5, -10);
        rotation = new Vector3f(0, 180, 0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void movePosition(float x, float y, float z) {
        if(z != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * z;
            position.z += (float) Math.cos(Math.toRadians(rotation.y)) * z;
        }
        if(x != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * x;
            position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * x;
        }
        position.y += y;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void moveRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public int getX() {
        return (int)  position.x;
    }

    public int getY() {
        return (int)  position.y;
    }

    public int getZ() {
        return (int)  position.z;
    }

    public int getRotX() {
        return (int)  rotation.x;
    }
    public int getRotY() {
        return (int)  rotation.y;
    }
    public int getRotZ() {
        return (int)  rotation.z;
    }
}
