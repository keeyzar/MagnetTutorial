package de.keeyzar.tutorial.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Our coin, which we want to collect
 */
public class Coin {
    private Body body;
    private boolean attracted = false;

    public Coin(Vector2 pos, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(pos));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);
        body = world.createBody(bodyDef);
        final Fixture fixture = body.createFixture(shape, 1);
        fixture.setUserData(this); //IMPORTANT STEP!!
        fixture.setSensor(true); //should not physically collide.
        shape.dispose();
    }

    public boolean isAttracted() {
        return attracted;
    }

    public void setAttracted(boolean attracted) {
        this.attracted = attracted;
    }

    public Body getBody(){
        return body;
    }
}
