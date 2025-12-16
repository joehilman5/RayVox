package entities.blocks;

import entities.Model;
import entities.Texture;

public class DirtBlock extends Block {


    public DirtBlock(int x, int y, int z, Model model, String textureFile) throws Exception {
        super(model, x, y, z);
        this.getModel().setTexture(new Texture(loader.loadTexture(textureFile)));
    }


}
