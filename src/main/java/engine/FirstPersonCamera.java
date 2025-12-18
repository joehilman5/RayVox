package engine;

import entities.Player;
import main.Launcher;
import org.joml.Vector3f;

public class FirstPersonCamera extends Camera {

    private WindowManager window;
    private Player player;

    private float yaw;
    private float pitch;

    private static final float MOUSE_SENSITIVITY = 0.03f;
    private static final float MAX_PITCH = 89.0f;

    public  FirstPersonCamera(Player player) {
        this.window = Launcher.getWindow();
        this.player = player;
        //this.setPosition(player.getPosition().x, player.getPosition().y + 2, player.getPosition().z);
        //this.setRotation(player.getRotation().x, player.getRotation().y + 180, player.getRotation().z);

        this.yaw = player.getRotation().y + 180f;
        this.pitch = player.getRotation().x;

        updatePosition();
        updateRotation();

    }

    public void update() {

        float mouseDX = (float) window.getDeltaX();
        float mouseDY = (float) window.getDeltaY();

        yaw += mouseDX * MOUSE_SENSITIVITY;
        pitch -= mouseDY *  MOUSE_SENSITIVITY;

        if(pitch > MAX_PITCH) pitch = MAX_PITCH;
        if(pitch < -MAX_PITCH) pitch = -MAX_PITCH;

        player.getRotation().y = -yaw;
        //player.getRotation().x = pitch;

        updatePosition();
        updateRotation();

    }

    private void updatePosition() {
        Vector3f position = player.getPosition();
        this.setPosition(position.x, position.y + 2.0f, position.z);
    }

    private void updateRotation() {
        this.setRotation(-pitch, yaw, 0);
    }


}
