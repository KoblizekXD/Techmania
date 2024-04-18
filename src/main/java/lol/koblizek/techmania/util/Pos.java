package lol.koblizek.techmania.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import org.joml.Vector3i;

public class Pos extends BlockPos {
    public Pos(int i, int j, int k) {
        super(i, j, k);
    }

    public Pos(Vec3i pos) {
        super(pos);
    }

    public Pos(Vector3i pos) {
        super(pos.x, pos.y, pos.z);
    }

    public BlockPos oneLeft(Direction direction) {
        switch (direction) {
            case NORTH -> {
                return add(1, 0, 0);
            }
            case SOUTH -> {
                return add(-1, 0, 0);
            }
            case WEST -> {
                return add(0, 0, 1);
            }
            case EAST -> {
                return add(0, 0, -1);
            }
            default -> {
                return this;
            }
        }
    }

    public BlockPos left(Direction direction, int i) {
        switch (direction) {
            case NORTH -> {
                return add(i, 0, 0);
            }
            case SOUTH -> {
                return add(-1 * i, 0, 0);
            }
            case WEST -> {
                return add(0, 0, i);
            }
            case EAST -> {
                return add(0, 0, -1 * i);
            }
            default -> {
                return this;
            }
        }
    }

    public Pos oneRight(Direction direction) {
        switch (direction) {
            case NORTH -> {
                return new Pos(add(1, 0, 0));
            }
            case SOUTH -> {
                return new Pos(add(-1, 0, 0));
            }
            case WEST -> {
                return new Pos(add(0, 0, -1));
            }
            case EAST -> {
                return new Pos(add(0, 0, 1));
            }
            default -> {
                return this;
            }
        }
    }

    public Pos right(Direction direction, int i) {
        switch (direction) {
            case NORTH -> {
                return new Pos(add(i, 0, 0));
            }
            case SOUTH -> {
                return new Pos(add(-1 * i, 0, 0));
            }
            case WEST -> {
                return new Pos(add(0, 0, -1 * i));
            }
            case EAST -> {
                return new Pos(add(0, 0, i));
            }
            default -> {
                return this;
            }
        }
    }

    public Pos oneForward(Direction direction) {
        switch (direction) {
            case NORTH -> {
                return new Pos(add(0, 0, -1));
            }
            case SOUTH -> {
                return new Pos(add(0, 0, 1));
            }
            case WEST -> {
                return new Pos(add(-1, 0, 0));
            }
            case EAST -> {
                return new Pos(add(1, 0, 0));
            }
            default -> {
                return this;
            }
        }
    }

    public Pos forward(Direction direction, int i) {
        switch (direction) {
            case NORTH -> {
                return new Pos(add(0, 0, -1 * i));
            }
            case SOUTH -> {
                return new Pos(add(0, 0, i));
            }
            case WEST -> {
                return new Pos(add(-1 * i, 0, 0));
            }
            case EAST -> {
                return new Pos(add(i, 0, 0));
            }
            default -> {
                return this;
            }
        }
    }

    public Pos oneUp() {
        return new Pos(add(0, 1, 0));
    }

    public Pos up(int i) {
        return new Pos(add(0, i, 0));
    }
}
