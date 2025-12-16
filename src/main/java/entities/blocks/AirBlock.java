package entities.blocks;

import entities.Model;

public class AirBlock extends Block {


    public AirBlock() {
        super(null);
    }

    @Override
    public boolean isAir() {
        return true;
    }
}
