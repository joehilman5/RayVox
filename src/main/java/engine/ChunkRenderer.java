package engine;

import entities.Model;
import entities.Texture;
import entities.blocks.Block;
import entities.blocks.Chunk;
import utils.VertexData;

import java.util.ArrayList;
import java.util.List;

public class ChunkRenderer extends RenderManager {

    private ObjectLoader loader;

    public ChunkRenderer(ObjectLoader loader) {
        this.loader = loader;
    }

    public Model buildChunkModel(Chunk chunk) throws  Exception {

        List<Float> verticesList = new ArrayList<>();
        List<Float> normalsList = new ArrayList<>();
        List<Float> texCoordsList = new ArrayList<>();
        List<Integer> indicesList = new ArrayList<>();

        int indexOffset = 0;

        for (int x = 0; x < chunk.getWidth(); x++) {
            for (int y = 0; y < chunk.getHeight(); y++) {
                for (int z = 0; z < chunk.getDepth(); z++) {
                    Block block = chunk.getBlock(x, y, z);
                    if (block == null || block.isAir()) continue;

                    // Check all 6 faces
                    for (int face = 0; face < 6; face++) {
                        int[] offset = block.getFaceOffset(face);
                        int nx = x + offset[0];
                        int ny = y + offset[1];
                        int nz = z + offset[2];

                        Block neighbor = chunk.getBlock(nx, ny, nz);
                        if (neighbor == null || neighbor.isAir()) {
                            // Face is exposed → add its data
                            addFaceData(verticesList, normalsList, texCoordsList, indicesList,
                                    face, x, y, z, indexOffset);
                            indexOffset += 4; // each face has 4 vertices
                        }
                    }
                }
            }
        }

        // Convert lists to arrays
        float[] vertices = new float[verticesList.size()];
        for (int i = 0; i < verticesList.size(); i++) vertices[i] = verticesList.get(i);

        float[] normals = new float[normalsList.size()];
        for (int i = 0; i < normalsList.size(); i++) normals[i] = normalsList.get(i);

        float[] texCoords = new float[texCoordsList.size()];
        for (int i = 0; i < texCoordsList.size(); i++) texCoords[i] = texCoordsList.get(i);

        int[] indices = new int[indicesList.size()];
        for (int i = 0; i < indicesList.size(); i++) indices[i] = indicesList.get(i);

        // Load into a VAO
        Model model = loader.loadToVao(vertices, texCoords, normals, indices);
        model.setTexture(new Texture(loader.loadTexture("textures/dirt.png")));
        return model;
    }

    private void addFaceData(List<Float> verticesList, List<Float> normalsList,
                             List<Float> texCoordsList, List<Integer> indicesList,
                             int face, int x, int y, int z, int indexOffset) {

        float[] faceVertices = new float[0];
        float[] faceNormals = new float[0];
        float[] faceTexCoords = new float[0];
        int[] faceIndices = new int[0];

        switch (face) {
            case Block.FACE_FRONT:
                faceVertices = VertexData.frontVertices;
                faceNormals = VertexData.frontNormals;
                faceTexCoords = VertexData.frontTextureCoords;
                faceIndices = VertexData.frontIndices;
                break;
            case Block.FACE_BACK:
                faceVertices = VertexData.frontVertices; // you can make back separately
                faceNormals = new float[]{0, 0, -1};
                faceTexCoords = VertexData.frontTextureCoords;
                faceIndices = VertexData.frontIndices;
                break;
            case Block.FACE_LEFT:
                faceVertices = VertexData.rightVertices; // adjust for left
                faceNormals = new float[]{-1, 0, 0};
                faceTexCoords = VertexData.rightTextureCoords;
                faceIndices = VertexData.rightIndices;
                break;
            case Block.FACE_RIGHT:
                faceVertices = VertexData.rightVertices;
                faceNormals = VertexData.rightNormals;
                faceTexCoords = VertexData.rightTextureCoords;
                faceIndices = VertexData.rightIndices;
                break;
            case Block.FACE_TOP:
                faceVertices = new float[]{
                        0, 1, 0,
                        0, 1, 1,
                        1, 1, 1,
                        1, 1, 0
                };
                faceNormals = new float[]{0, 1, 0};
                faceTexCoords = VertexData.frontTextureCoords;
                faceIndices = VertexData.frontIndices;
                break;
            case Block.FACE_BOTTOM:
                faceVertices = new float[]{
                        0, 0, 1,
                        0, 0, 0,
                        1, 0, 0,
                        1, 0, 1
                };
                faceNormals = new float[]{0, -1, 0};
                faceTexCoords = VertexData.frontTextureCoords;
                faceIndices = VertexData.frontIndices;
                break;
        }

        // Add vertices offset by block position
        for (int i = 0; i < faceVertices.length; i += 3) {
            verticesList.add(faceVertices[i] + x);
            verticesList.add(faceVertices[i + 1] + y);
            verticesList.add(faceVertices[i + 2] + z);
        }

        // Add normals (same for all 4 vertices)
        for (int i = 0; i < 4; i++) {
            normalsList.add(faceNormals[0]);
            normalsList.add(faceNormals[1]);
            normalsList.add(faceNormals[2]);
        }

        // Add texture coordinates
        for (float f : faceTexCoords) texCoordsList.add(f);

        // Add indices with offset
        for (int i : faceIndices) indicesList.add(i + indexOffset);
    }

}
