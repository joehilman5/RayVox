package utils;

public class VertexData {

    // ---------------- FRONT ----------------
    public static float[] frontVertices = {
            0, 1, 1,  // top-left
            0, 0, 1,  // bottom-left
            1, 0, 1,  // bottom-right
            1, 1, 1   // top-right
    };
    public static float[] frontNormals = {0, 0, 1};
    public static float[] frontTextureCoords = {
            0, 1,
            0, 0,
            1, 0,
            1, 1
    };
    public static int[] frontIndices = {0, 1, 2, 2, 3, 0};

    // ---------------- BACK ----------------
    public static float[] backVertices = {
            1, 1, 0,  // top-right
            1, 0, 0,  // bottom-right
            0, 0, 0,  // bottom-left
            0, 1, 0   // top-left
    };
    public static float[] backNormals = {0, 0, -1};
    public static float[] backTextureCoords = frontTextureCoords;
    public static int[] backIndices = {0, 1, 2, 2, 3, 0};

    // ---------------- LEFT ----------------
    public static float[] leftVertices = {
            0, 1, 0,  // top-left
            0, 0, 0,  // bottom-left
            0, 0, 1,  // bottom-right
            0, 1, 1   // top-right
    };
    public static float[] leftNormals = {-1, 0, 0};
    public static float[] leftTextureCoords = frontTextureCoords;
    public static int[] leftIndices = {0, 1, 2, 2, 3, 0};

    // ---------------- RIGHT ----------------
    public static float[] rightVertices = {
            1, 1, 1,  // top-left
            1, 0, 1,  // bottom-left
            1, 0, 0,  // bottom-right
            1, 1, 0   // top-right
    };
    public static float[] rightNormals = {1, 0, 0};
    public static float[] rightTextureCoords = frontTextureCoords;
    public static int[] rightIndices = {0, 1, 2, 2, 3, 0};

    // ---------------- TOP ----------------
    public static float[] topVertices = {
            0, 1, 0,  // top-left
            0, 1, 1,  // bottom-left
            1, 1, 1,  // bottom-right
            1, 1, 0   // top-right
    };
    public static float[] topNormals = {0, 1, 0};
    public static float[] topTextureCoords = frontTextureCoords;
    public static int[] topIndices = {0, 1, 2, 2, 3, 0};

    // ---------------- BOTTOM ----------------
    public static float[] bottomVertices = {
            0, 0, 1,  // top-left
            0, 0, 0,  // bottom-left
            1, 0, 0,  // bottom-right
            1, 0, 1   // top-right
    };
    public static float[] bottomNormals = {0, -1, 0};
    public static float[] bottomTextureCoords = frontTextureCoords;
    public static int[] bottomIndices = {0, 1, 2, 2, 3, 0};

}