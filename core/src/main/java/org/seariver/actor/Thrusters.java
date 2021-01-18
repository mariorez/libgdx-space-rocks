package org.seariver.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import org.seariver.BaseActor;

public class Thrusters extends BaseActor {

    public Thrusters(float x, float y, Stage stage) {
        super(x, y, stage);
        loadTexture("fire.png");
    }
}