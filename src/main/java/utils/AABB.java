package utils;

import org.joml.Vector3f;

public class AABB {

    public float minX, minY, minZ;
    public float maxX, maxY, maxZ;

    public AABB(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public AABB(Vector3f position, float width, float height, float depth) {
        this.minX = position.x();
        this.minY = position.y();
        this.minZ = position.z();
        this.maxX = position.x() + width;
        this.maxY = position.y() + height;
        this.maxZ = position.z() + depth;
    }

    public boolean intersects(AABB other) {
        return (this.minX < other.maxX && this.maxX > other.minX) &&
                (this.minY < other.maxY && this.maxY > other.minY) &&
                (this.minZ < other.maxZ && this.maxZ > other.minZ);
    }

    public AABB expand(Vector3f vec) {
        return new AABB(
                minX + Math.min(0, vec.x),
                minY + Math.min(0, vec.y),
                minZ + Math.min(0, vec.z),
                maxX + Math.max(0, vec.x),
                maxY + Math.max(0, vec.y),
                maxZ + Math.max(0, vec.z)
        );
    }

    public AABB offset(Vector3f vec) {
        return new AABB(
                minX + vec.x, minY + vec.y, minZ + vec.z,
                maxX + vec.x, maxY + vec.y, maxZ + vec.z
        );
    }

    public Vector3f getCenter() {
        return new Vector3f(
                (minX + maxX) / 2f,
                (minY + maxY) / 2f,
                (minZ + maxZ) / 2f
        );
    }
}
