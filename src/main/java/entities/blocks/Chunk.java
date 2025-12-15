package entities.blocks;

import engine.ObjectLoader;

public class Chunk {

    public static final int CHUNK_SIZE_X = 16;
    public static final int CHUNK_SIZE_Y = 256;
    public static final int CHUNK_SIZE_Z = 16;

    private ObjectLoader loader = new ObjectLoader();

    private int x;
    private int z;

    private final Block[][][] blocks = new Block[CHUNK_SIZE_X][CHUNK_SIZE_Y][CHUNK_SIZE_Z];

    private boolean dirty = true;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public void setBlock(Block block) {
        blocks[block.getX()][block.getY()][block.getZ()] = block;
        dirty = true;
    }

    public Block getBlock(int x, int y, int z) {
        if (x < 0 || x >= CHUNK_SIZE_X ||
                y < 0 || y >= CHUNK_SIZE_Y ||
                z < 0 || z >= CHUNK_SIZE_Z) {
            return null;
        }
        return blocks[x][y][z];
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean value) {
        dirty = value;
    }

    public void checkNeighbors(Block block) {
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        if(block == null) return;

        block.setFace(0, getBlock(x, y, z + 1) == null);
        block.setFace(1, getBlock(x, y, z - 1) == null);
        block.setFace(2, getBlock(x - 1, y, z) == null);
        block.setFace(3, getBlock(x + 1, y, z) == null);
        block.setFace(4, getBlock(x, y + 1, z) == null);
        block.setFace(5, getBlock(x, y - 1, z) == null);

        block.updateBlockModel();

    }

}
