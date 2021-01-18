package org.seariver.screen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.seariver.BaseActor;
import org.seariver.BaseScreen;
import org.seariver.actor.Explosion;
import org.seariver.actor.Rock;
import org.seariver.actor.Spaceship;

public class LevelScreen extends BaseScreen {

    private Spaceship spaceship;
    private boolean gameOver;

    public void initialize() {
        BaseActor space = new BaseActor(0, 0, mainStage);
        space.loadTexture("space.png");
        space.setSize(800, 600);
        BaseActor.setWorldBounds(space);

        spaceship = new Spaceship(400, 300, mainStage);

        new Rock(600, 500, mainStage);
        new Rock(600, 300, mainStage);
        new Rock(600, 100, mainStage);
        new Rock(400, 100, mainStage);
        new Rock(200, 100, mainStage);
        new Rock(200, 300, mainStage);
        new Rock(200, 500, mainStage);
        new Rock(400, 500, mainStage);

        gameOver = false;
    }

    public void update(float dt) {

        for (BaseActor rockActor : BaseActor.getList(mainStage, "org.seariver.actor.Rock")) {
            if (rockActor.overlaps(spaceship)) {
                if (spaceship.shieldPower <= 0) {
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(spaceship);
                    spaceship.remove();
                    spaceship.setPosition(-1000, -1000);

                    BaseActor messageLose = new BaseActor(0, 0, uiStage);
                    messageLose.loadTexture("message-lose.png");
                    messageLose.centerAtPosition(400, 300);
                    messageLose.setOpacity(0);
                    messageLose.addAction(Actions.fadeIn(1));
                    gameOver = true;
                } else {
                    spaceship.shieldPower -= 34;
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(rockActor);
                    rockActor.remove();
                }

            }

            for (BaseActor laserActor : BaseActor.getList(mainStage, "org.seariver.actor.Laser")) {
                if (laserActor.overlaps(rockActor)) {
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(rockActor);
                    laserActor.remove();
                    rockActor.remove();
                }
            }
        }

        if (!gameOver && BaseActor.count(mainStage, "org.seariver.actor.Rock") == 0) {
            BaseActor messageWin = new BaseActor(0, 0, uiStage);
            messageWin.loadTexture("message-win.png");
            messageWin.centerAtPosition(400, 300);
            messageWin.setOpacity(0);
            messageWin.addAction(Actions.fadeIn(1));
            gameOver = true;
        }

    }

    // override default InputProcessor methods
    public boolean keyDown(int keycode) {
        if (keycode == Keys.SPACE)
            spaceship.shoot();

        if (keycode == Keys.X)
            spaceship.warp();

        return false;
    }
}
