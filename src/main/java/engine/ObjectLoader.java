package engine;

import entities.Model;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import utils.Consts;
import utils.Utils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectLoader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public Model loadToVao(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
        int vaoId = createVao();
        storeIndices(indices);
        storeData(0, 3, vertices);
        storeData(1, 2,  textureCoords);
        storeData(2, 3, normals);
        unbindVao();
        return new Model(vaoId, indices.length);
    }

    private int createVao() {
        int vaoId = GL30.glGenVertexArrays();
        vaos.add(vaoId);
        GL30.glBindVertexArray(vaoId);
        return vaoId;
    }

    private void storeIndices(int[] indices) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
        IntBuffer buffer = Utils.storeInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private void storeData(int attrib, int count, float[] data) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = Utils.storeInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attrib, count, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public int loadTexture(String fileName) throws Exception {
        int width, height;
        ByteBuffer buffer;
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            buffer = STBImage.stbi_load(Consts.PATH + fileName, w, h, c, 4);
            if(buffer == null) {
                throw new Exception("Image File: " + fileName + " not loaded " + STBImage.stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        int id = GL11.glGenTextures();
        textures.add(id);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        STBImage.stbi_image_free(buffer);
        return id;
    }

    private void unbindVao() {
        GL30.glBindVertexArray(0);
    }

    public void cleanUp() {
        for(int vaoId : vaos) {
            GL30.glDeleteVertexArrays(vaoId);
        }
        for(int vboId : vbos) {
            GL15.glDeleteBuffers(vboId);
        }
    }

    public Model loadObjModel(String fileName) {
        List<String> lines = Utils.readAllLines(fileName);

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Vector3i> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    vertices.add(new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    ));
                    break;
                case "vt":
                    textures.add(new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2])
                    ));
                    break;
                case "vn":
                    normals.add(new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    ));
                    break;
                case "f":
                    faces.add(processFace(tokens[1]));
                    faces.add(processFace(tokens[2]));
                    faces.add(processFace(tokens[3]));
                    break;
            }
        }

        List<Integer> indices = new ArrayList<>();
        float[] verticesArr = new float[vertices.size() * 3];
        float[] texCoordsArr = new float[vertices.size() * 2];
        float[] normalArr = new float[vertices.size() * 3];

        for (int i = 0; i < vertices.size(); i++) {
            Vector3f v = vertices.get(i);
            verticesArr[i * 3] = v.x;
            verticesArr[i * 3 + 1] = v.y;
            verticesArr[i * 3 + 2] = v.z;
        }

        for (Vector3i face : faces) {
            processVertex(face.x, face.y, face.z, textures, normals, indices, texCoordsArr, normalArr);
        }

        int[] indicesArr = indices.stream().mapToInt(Integer::intValue).toArray();

        return loadToVao(verticesArr, texCoordsArr, normalArr, indicesArr);
    }

    // helper methods
    private static Vector3i processFace(String token) {
        String[] parts = token.split("/");
        int pos = Integer.parseInt(parts[0]) - 1;
        int tex = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) - 1 : -1;
        int norm = parts.length > 2 ? Integer.parseInt(parts[2]) - 1 : -1;
        return new Vector3i(pos, tex, norm);
    }

    private static void processVertex(int pos, int texCoord, int normal,
                                      List<Vector2f> texCoordList, List<Vector3f> normalList,
                                      List<Integer> indicesList, float[] texCoordArr, float[] normalArr) {
        indicesList.add(pos);

        if (texCoord >= 0) {
            Vector2f tex = texCoordList.get(texCoord);
            texCoordArr[pos * 2] = tex.x;
            texCoordArr[pos * 2 + 1] = 1 - tex.y;
        }

        if (normal >= 0) {
            Vector3f n = normalList.get(normal);
            normalArr[pos * 3] = n.x;
            normalArr[pos * 3 + 1] = n.y;
            normalArr[pos * 3 + 2] = n.z;
        }
    }


}
