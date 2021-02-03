package org.seariver.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.seariver.BaseActor;

public class Spaceship extends BaseActor {

    private ThrusterEffect thrusterEffect;
    private Shield shield;
    public int shieldPower;

    public Spaceship(float x, float y, Stage stage) {
        super(x, y, stage);

        loadTexture("spaceship.png");
        setBoundaryPolygon(8);

        setAcceleration(200);
        setMaxSpeed(100);
        setDeceleration(10);

        thrusterEffect = new ThrusterEffect();
        thrusterEffect.setPosition(0, 32);
        thrusterEffect.setRotation(90);
        thrusterEffect.setScale(0.25f);
        addActor(thrusterEffect);

        shield = new Shield(0, 0, stage);
        addActor(shield);
        shield.centerAtPosition(getWidth() / 2, getHeight() / 2);
        shieldPower = 100;
    }

    public void shoot() {
        if (getStage() == null)
            return;

        Laser laser = new Laser(0, 0, this.getStage());
        laser.centerAtActor(this);
        laser.setRotation(this.getRotation());
        laser.setMotionAngle(this.getRotation());
    }

    public void warp() {
        if (getStage() == null)
            return;

        Warp warp1 = new Warp(0, 0, this.getStage());
        warp1.centerAtActor(this);
        setPosition(MathUtils.random(800), MathUtils.random(600));
        Warp warp2 = new Warp(0, 0, this.getStage());
        warp2.centerAtActor(this);
    }

    public void act(float deltaTime) {
        super.act(deltaTime);

        float degreesPerSecond = 120; // degrees per second
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            rotateBy(degreesPerSecond * deltaTime);
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            rotateBy(-degreesPerSecond * deltaTime);

        if (Gdx.input.isKeyPressed(Keys.UP)) {
            accelerateAtAngle(getRotation());
            thrusterEffect.start();
        } else {
            thrusterEffect.stop();
        }

        applyPhysics(deltaTime);

        wrapAroundWorld();

        shield.setOpacity(shieldPower / 100f);
    }

}