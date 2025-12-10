package main;

public interface IRayVox {

    void init() throws Exception;

    void input();

    void update();

    void render();

    void cleanUp();

}
