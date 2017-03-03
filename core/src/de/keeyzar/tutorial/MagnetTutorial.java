package de.keeyzar.tutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.keeyzar.tutorial.entities.Coin;

/**
 * BASIC SETUP!!!
 */
public class MagnetTutorial extends ApplicationAdapter {
    private final float timeStep = 1/60f;
    private float xSpeed, ySpeed, speed = 20; //player move speed

    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer renderer;

    private Body player;

	@Override
	public void create () {
        camera = new OrthographicCamera(20, 20);
        world = new World(new Vector2(0, -10), true);
        renderer = new Box2DDebugRenderer();
        new Ground(world);
        createPlayer();
	}

    //create our playerBody
    private void createPlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(0, 3));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);
        player = world.createBody(bodyDef);
        player.createFixture(shape, 1f);
        shape.dispose();
    }

    @Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        checkInput();
        world.step(timeStep, 6, 2);
        updateCamera();
        renderer.render(world, camera.combined);
	}

    private void checkInput() {
	    //player movement
        xSpeed = 0;
        ySpeed = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            ySpeed = speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            ySpeed = -speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            xSpeed = -speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            xSpeed = speed;
        }
        player.setLinearVelocity(xSpeed, ySpeed);

        //spawning (see the "JustPressed" so we only spawn one object per press)
        //that's so we can observe our magnetic effect multiple times, without restarting
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            new Coin(new Vector2(8, 12), world);
        }
    }


    /**
     * fix camera to the center of the player
     */
	private void updateCamera(){
        camera.position.set(player.getPosition().x, player.getPosition().y + 5, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        renderer.dispose();
    }

    /**
     * Our ground, so we don't fall in a endless loop
     */
    class Ground {
        public Ground(World world){
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(new Vector2(0, 0.5f));
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(100, 1);
            body.createFixture(shape, 0);
        }
    }
}
