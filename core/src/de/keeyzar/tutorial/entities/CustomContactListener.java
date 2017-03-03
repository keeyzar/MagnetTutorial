package de.keeyzar.tutorial.entities;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * @author = Keeyzar on 03.03.2017.
 */
public class CustomContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        final Object userDataA = contact.getFixtureA().getUserData();
        final Object userDataB = contact.getFixtureB().getUserData();

        //if one userData is null, we do not need to check anything.
        if(userDataA != null && userDataB != null){
            //now only add the magnet to the coin
            if(userDataA instanceof MagnetBody && userDataB instanceof Coin){
                ((MagnetBody) userDataA).addCoin((Coin) userDataB);
            } else if (userDataB instanceof MagnetBody && userDataA instanceof Coin){
                ((MagnetBody) userDataB).addCoin((Coin) userDataA);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        //not needed for this tutorial
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //not needed for this tutorial
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //not needed for this tutorial
    }
}
