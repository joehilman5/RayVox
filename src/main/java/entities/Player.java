package entities;

import engine.EngineManager;
import engine.WindowManager;
import entities.blocks.Block;
import main.Launcher;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import utils.AABB;
import world.World;

import java.util.List;

public class Player extends Entity {

    private WindowManager window;
    private World world;

    private static final float WIDTH = 0.6f;
    private static final float HEIGHT = 1.8f;
    private static final float DEPTH = 0.6f;

    private static final float RUN_SPEED = 5.0f;
    private static final float GRAVITY = -50f;
    private static final float JUMP_POWER = 12f;

    private float currentSpeed = 0;
    private float upwardsSpeed = 0;
    private float strafeSpeed = 0;

    private boolean isInAir = false;

    public Player(Model model, Vector3f position, Vector3f rotation, float scale, World world) {
        super(model, position, rotation, scale);
        this.window = Launcher.getWindow();
        this.world = world;
    }

    public void update() {
        checkInputs();

        float delta = EngineManager.getDelta();
        // Rotate player

        // Calculate movement in XZ plane
        float yawRad = (float) Math.toRadians(super.getRotation().y);

        float forwardX = (float) Math.sin(yawRad);
        float forwardZ = (float) Math.cos(yawRad);

        float rightX = (float) Math.sin(yawRad + Math.PI / 2);
        float rightZ = (float) Math.cos(yawRad + Math.PI / 2);

        float dx = (forwardX * currentSpeed + rightX * strafeSpeed) * delta;
        float dz = (forwardZ * currentSpeed + rightZ * strafeSpeed) * delta;

        // Apply gravity only if in air
//        if (isInAir) {
//            upwardsSpeed += GRAVITY * delta;
//        } else {
//            upwardsSpeed = 0;
//        }

        upwardsSpeed += GRAVITY * delta;

        float dy = upwardsSpeed * delta;

        // Move with collision
        move(dx, dy, dz);
    }

    private void jump() {
        if (!isInAir) {
            isInAir = true;
            upwardsSpeed = JUMP_POWER;
        }
    }

    private void checkInputs() {
        currentSpeed = 0;
        strafeSpeed = 0;

        if (window.isKeyPressed(GLFW.GLFW_KEY_W)) currentSpeed -= RUN_SPEED;
        if (window.isKeyPressed(GLFW.GLFW_KEY_S)) currentSpeed += RUN_SPEED;
        if (window.isKeyPressed(GLFW.GLFW_KEY_D)) strafeSpeed += RUN_SPEED;
        if (window.isKeyPressed(GLFW.GLFW_KEY_A)) strafeSpeed -= RUN_SPEED;
        if (window.isKeyPressed(GLFW.GLFW_KEY_SPACE)) jump();
    }

    private void move(float dx, float dy, float dz) {
        Vector3f pos = super.getPosition();

        // ---- Y AXIS ----
        float newY = pos.y + dy;
        Block collidedBlock = getCollidingBlock(pos.x, newY, pos.z);

        if(collidedBlock == null) {
            pos.y = newY;
            isInAir = true;
        }else {
            if(dy < 0) {
                //Land on block
                pos.y = collidedBlock.getBoundingBox().maxY;
                isInAir = false;
            }else if(dy > 0) {
                //Hit Ceiling
                pos.y = collidedBlock.getBoundingBox().minY - HEIGHT;
            }
            upwardsSpeed = 0;
        }

        // ---- X AXIS ----
        if (dx != 0) {
            collidedBlock = getCollidingBlock(pos.x + dx, pos.y, pos.z);
            if (collidedBlock == null) pos.x += dx;
        }

        // ---- Z AXIS ----
        if (dz != 0) {
            collidedBlock = getCollidingBlock(pos.x, pos.y, pos.z + dz);
            if (collidedBlock == null) pos.z += dz;
        }

        if(pos.y < 0) {
            setPosition(0, 0, 0);
        }

    }

    private Block getCollidingBlock(float x, float y, float z) {
        AABB playerBox = getPlayerAABB(x, y, z);

        int bx = (int) Math.floor(x);
        int by = (int) Math.floor(y);
        int bz = (int) Math.floor(z);

        List<Block> nearby = world.getNearbyBlocks(bx, by, bz);
        for (Block block : nearby) {
            if (block == null || !block.isSolid()) continue;
            if (playerBox.intersects(block.getBoundingBox())) return block;
        }
        return null;
    }

    private AABB getPlayerAABB(float x, float y, float z) {
        float halfW = WIDTH / 2f;
        float halfD = DEPTH / 2f;

        // Bottom of the player is y, top is y + HEIGHT
        return new AABB(
                x - halfW,
                y,
                z - halfD,
                x + halfW,
                y + HEIGHT,
                z + halfD
        );
    }

    private boolean isOnGround(Vector3f pos) {
        // Slightly below the player to check for ground
        return getCollidingBlock(pos.x, pos.y - 0.01f, pos.z) != null;
    }

    public float getUpwardsSpeed() { return upwardsSpeed; }
    public float getCurrentSpeed() { return currentSpeed; }
}