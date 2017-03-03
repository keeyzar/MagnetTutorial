package de.keeyzar.tutorial.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * @author = Keeyzar on 03.03.2017.
 */
public class MagnetBody {
    private Body player; //this is the body where we need to move the coins to.

    //that is the list of all coins, which we are attracting!
    private Array<Coin> coinsToHandle;

    //our calculation vector, so we do not create many new objects!
    private Vector2 calcVector = new Vector2();

    public MagnetBody(Body player, World world){
        this.player = player;
        coinsToHandle = new Array<Coin>();

        //create the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(player.getPosition().x, player.getPosition().y));
        CircleShape shape = new CircleShape();
        shape.setRadius(8);
        Body body = world.createBody(bodyDef);
        Fixture fixture = body.createFixture(shape, 0);
        fixture.setUserData(this); //so we can distinguish it from other objects
        fixture.setSensor(true); //so it wont collide physically with anything
        MassData massData = body.getMassData();
        massData.mass = 0.000001f; //should not add noticeably to weight of the player
        body.setMassData(massData);
        shape.dispose();

        //create the joint, so that the
        //magnet is always in the center of the player.
        RevoluteJointDef magnetPlayerJoint = new RevoluteJointDef();
        magnetPlayerJoint.type = JointDef.JointType.RevoluteJoint;
        magnetPlayerJoint.bodyA = player;
        magnetPlayerJoint.bodyB = body;
        magnetPlayerJoint.localAnchorA.set(0,0);
        magnetPlayerJoint.localAnchorB.set(0,0);
        world.createJoint(magnetPlayerJoint);
    }

    /**
     * in this method, we'll update all directions of the coins!
     */
    public void update(){
        Iterator<Coin> magneticActors = coinsToHandle.iterator();
        while(magneticActors.hasNext()){
            Coin coin = magneticActors.next();
            //if coin is null, it was collected, remove it!
            if(coin.getBody() == null){
                magneticActors.remove();
            }

            //calculate direction
            //and then set the velocity of the coin to this
            calcVector.set(player.getPosition());
            calcVector.sub(coin.getBody().getPosition()).nor();
            coin.getBody().setLinearVelocity(calcVector.scl(2));
        }
    }

    /**
     * here we add the coins, which needs to be managed
     */
    public void addCoin(Coin coinToAdd){
        //if the coin is already attracted, skip!
        if(!coinToAdd.isAttracted()){
            coinsToHandle.add(coinToAdd);
            coinToAdd.setAttracted(true);
        }
    }
}
