package engine;

import entities.Player;
import main.Launcher;
import org.joml.Vector3f;

public class FirstPersonCamera extends Camera {

    private WindowManager window;
    private Player player;

    public  FirstPersonCamera(Player player) {
        this.window = Launcher.getWindow();
        this.player = player;
        this.setPosition(player.getPosition().x, player.getPosition().y + 2, player.getPosition().z);
        this.setRotation(player.getRotation().x, player.getRotation().y + 180, player.getRotation().z);
    }

    public void update() {

        this.setPosition(player.getPosition().x, player.getPosition().y + 2, player.getPosition().z);
        this.setRotation(player.getRotation().x, -player.getRotation().y + 180, player.getRotation().z);

    }


}
