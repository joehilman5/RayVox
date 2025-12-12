package entities.blocks;

public class Chunk {

    private final int width;
    private final int height;
    private final int depth;

    private Block[][][] blocks;

    public int chunkX, chunkY, chunkZ;

    public Chunk(int chunkX, int chunkY, int chunkZ, int width, int height, int depth) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;

        this.width = width;
        this.height = height;
        this.depth = depth;

        blocks = new Block[width][height][depth];
        initBlocks();
    }

    private void initBlocks() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    blocks[x][y][z] = null;
                }
            }
        }
    }

    public Block getBlock(int x, int y, int z) {
        if (isValid(x, y, z)) {
            return blocks[x][y][z];
        }
        return null; // outside chunk is considered air
    }

    public void setBlock(int x, int y, int z, Block block) {
        if (isValid(x, y, z)) {
            blocks[x][y][z] = block;
        }
    }

    private boolean isValid(int x, int y, int z) {
        return x >= 0 && x < width && y >= 0 && y < height && z >= 0 && z < depth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

    public int getChunkZ() {
        return chunkZ;
    }

}
