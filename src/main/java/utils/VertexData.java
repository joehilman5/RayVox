package utils;

public class VertexData {

    public static float[] vertices = {
            // Front face
            -0.5f,  0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f,  0.5f, 0.5f,

            // Back face
            0.5f,  0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,

            // Left face
            -0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,

            // Right face
            0.5f,  0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f,  0.5f, -0.5f,

            // Top face
            -0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            0.5f,  0.5f, -0.5f,

            // Bottom face
            -0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f,  0.5f
    };

    public static float[] textureCoords = {
            0,0, 0,1, 1,1, 1,0,   // front
            0,0, 0,1, 1,1, 1,0,   // back
            0,0, 0,1, 1,1, 1,0,   // left
            0,0, 0,1, 1,1, 1,0,   // right
            0,0, 0,1, 1,1, 1,0,   // top
            0,0, 0,1, 1,1, 1,0    // bottom
    };


    public static float[] normals = {
            0,0,1, 0,0,1, 0,0,1, 0,0,1,  // front
            0,0,-1,0,0,-1,0,0,-1,0,0,-1, // back
            -1,0,0,-1,0,0,-1,0,0,-1,0,0, // left
            1,0,0, 1,0,0, 1,0,0, 1,0,0,  // right
            0,1,0, 0,1,0, 0,1,0, 0,1,0,  // top
            0,-1,0,0,-1,0,0,-1,0,0,-1,0 // bottom
    };


    public static int[] indices = {
            0,1,2, 2,3,0,
            4,5,6, 6,7,4,
            8,9,10,10,11,8,
            12,13,14,14,15,12,
            16,17,18,18,19,16,
            20,21,22,22,23,20
    };

    public static int[] frontFace = {
            0, 1, 2,  2, 3, 0
    };

    public static int[] backFace = {
        4, 5, 6,  6, 7, 4
    };

    public static int[] leftFace = {
            8, 9, 10,  10, 11, 8
    };

    public static int[] rightFace = {
            12, 13, 14,  14, 15, 12
    };

    public static int[] topFace = {
            16, 17, 18,  18, 19, 16
    };

    public static int[] bottomFace = {
            20, 21, 22,  22, 23, 20
    };

}
