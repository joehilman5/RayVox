package entities;

import engine.EngineManager;
import engine.WindowManager;
import main.Launcher;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class Player extends Entity {

    private WindowManager window;

    private static final float RUN_SPEED = 5.0f;
    private static final float TURN_SPEED = 100f;
    private static final float GRAVITY  = -50;
    private static final float JUMP_POWER = 20;
    private static final float MODEL_YAW_OFFSET = 0;

    private static final float HEIGHT = 1;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
        this.window = Launcher.getWindow();
    }

    public void update() {
        checkInputs();

        float delta = EngineManager.getDelta();

        super.incRotation(0, currentTurnSpeed * delta, 0);

        float distance = currentSpeed * delta;
        float yaw = super.getRotation().y + MODEL_YAW_OFFSET;
        float yawRad = (float) Math.toRadians(yaw);

        float dx = (float) (distance * Math.sin(yawRad));
        float dz = (float) (distance * Math.cos(yawRad));

        super.incPosition(dx, 0, dz);

        upwardsSpeed += GRAVITY * delta;
        super.incPosition(0, upwardsSpeed * delta, 0);
        if(super.getPosition().y < HEIGHT){
            upwardsSpeed = 0;
            super.getPosition().y = HEIGHT;
            isInAir = false;
        }
    }

    private void jump() {
        if(!isInAir) {
            isInAir = true;
            this.upwardsSpeed = JUMP_POWER;
        }
    }

    private void checkInputs() {
        if(window.isKeyPressed(GLFW.GLFW_KEY_W)) {
            this.currentSpeed = RUN_SPEED;
        }else if(window.isKeyPressed(GLFW.GLFW_KEY_S)) {
            this.currentSpeed = -RUN_SPEED;
        }else {
            this.currentSpeed = 0;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_D)) {
            this.currentTurnSpeed = -TURN_SPEED;
        }else if(window.isKeyPressed(GLFW.GLFW_KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        }else {
            this.currentTurnSpeed = 0;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            jump();
        }
    }

    public float getCurrentTurnSpeed() {
        return currentTurnSpeed;
    }

    public float getUpwardsSpeed() {
        return upwardsSpeed;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }
}
