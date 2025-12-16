package utils;

public class VertexData {

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

    public static final float[] vertices = {
            // FRONT (+Z)
            0, 1, 1,
            0, 0, 1,
            1, 0, 1,
            1, 1, 1,

            // BACK (-Z)
            1, 1, 0,
            1, 0, 0,
            0, 0, 0,
            0, 1, 0,

            // LEFT (-X)
            0, 1, 0,
            0, 0, 0,
            0, 0, 1,
            0, 1, 1,

            // RIGHT (+X)
            1, 1, 1,
            1, 0, 1,
            1, 0, 0,
            1, 1, 0,

            // TOP (+Y)
            0, 1, 0,
            0, 1, 1,
            1, 1, 1,
            1, 1, 0,

            // BOTTOM (-Y)
            0, 0, 1,
            0, 0, 0,
            1, 0, 0,
            1, 0, 1
    };

    // UVs (same for every face)
    public static final float[] uvs = {
            0, 1,
            0, 0,
            1, 0,
            1, 1,

            0, 1,
            0, 0,
            1, 0,
            1, 1,

            0, 1,
            0, 0,
            1, 0,
            1, 1,

            0, 1,
            0, 0,
            1, 0,
            1, 1,

            0, 1,
            0, 0,
            1, 0,
            1, 1,

            0, 1,
            0, 0,
            1, 0,
            1, 1
    };

    // Per-vertex normals
    public static final float[] normals = {
            // FRONT
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,

            // BACK
            0, 0, -1,
            0, 0, -1,
            0, 0, -1,
            0, 0, -1,

            // LEFT
            -1, 0, 0,
            -1, 0, 0,
            -1, 0, 0,
            -1, 0, 0,

            // RIGHT
            1, 0, 0,
            1, 0, 0,
            1, 0, 0,
            1, 0, 0,

            // TOP
            0, 1, 0,
            0, 1, 0,
            0, 1, 0,
            0, 1, 0,

            // BOTTOM
            0, -1, 0,
            0, -1, 0,
            0, -1, 0,
            0, -1, 0
    };

    // 36 indices (6 faces × 2 triangles × 3 vertices)
    public static final int[] indices = {
            0,  1,  2,  2,  3,  0,   // front
            4,  5,  6,  6,  7,  4,   // back
            8,  9, 10, 10, 11,  8,   // left
            12, 13, 14, 14, 15, 12,   // right
            16, 17, 18, 18, 19, 16,   // top
            20, 21, 22, 22, 23, 20    // bottom
    };

}
