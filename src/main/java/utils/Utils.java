package utils;

import engine.Camera;
import entities.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static FloatBuffer storeInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer storeInIntBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static String loadResource(String fileName) throws Exception {
        String result;
        try(InputStream in = Utils.class.getResourceAsStream(fileName);
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

    public static List<String> readAllLines(String fileName) {
        List<String> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(Class.class.forName(Utils.class.getName()).getResourceAsStream(fileName)))) {
            String line;
            while((line = br.readLine()) != null) {
                list.add(line);
            }
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Matrix4f createTransform(Entity entity) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(entity.getPosition())
                .rotateX((float) Math.toRadians(entity.getRotation().x))
                .rotateY((float) Math.toRadians(entity.getRotation().y))
                .rotateZ((float) Math.toRadians(entity.getRotation().z))
                .scale(entity.getScale());
        return matrix;
    }

    public static Matrix4f createView(Camera camera) {
        Vector3f pos = camera.getPosition();
        Vector3f rot = camera.getRotation();
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1, 0, 0));
        matrix.rotate((float) Math.toRadians(rot.y), new Vector3f(0, 1, 0));
        matrix.rotate((float) Math.toRadians(rot.z), new Vector3f(0, 0, 1));
        matrix.translate(-pos.x, -pos.y, -pos.z);
        return matrix;
    }
}
