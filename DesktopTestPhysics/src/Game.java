import Physics.PhysicCar;
import Physics.PhysicsWorld;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;

public class Game implements ApplicationListener {
	public PerspectiveCamera cam;
	public Model model;
	public ModelInstance instance;
	public ModelInstance whell1;
	public ModelInstance whell3;
	public ModelInstance whell2;
	public ModelInstance chaiss;
	public ModelBatch modelBatch;
	PhysicCar car1;
	public ModelInstance instance1;
	
	Environment  environment;
	public void create() {
		Bullet.init();
		PhysicsWorld.instance("bw").addBox(new Vector3(0,-2,0), "a",new Vector3(100,2,100),0);
		PhysicsWorld.instance("bw").addBox(new Vector3(0,30,0), "b",new Vector3(5,5,5),10);
		modelBatch=new ModelBatch();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createAmbient(Color.CYAN)),
				Usage.Position | Usage.Normal);
		
		instance = new ModelInstance(model);
		
		model= modelBuilder.createBox(100f, 2f, 100f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				Usage.Position | Usage.Normal);
		instance1=new ModelInstance(model);
		
		
		model = modelBuilder.createBox(1, 0.5f, 1f,
				new Material(ColorAttribute.createAmbient(Color.CYAN)),
				Usage.Position | Usage.Normal);
		
		chaiss = new ModelInstance(model);
		
		model = modelBuilder.createCylinder(0.5f,0.5f,1,30,
				new Material(ColorAttribute.createAmbient(Color.CYAN)),
				Usage.Position | Usage.Normal);
		
		whell1 = new ModelInstance(model);
		
		
		model = modelBuilder.createCylinder(0.5f,0.5f,1,30,
				new Material(ColorAttribute.createAmbient(Color.CYAN)),
				Usage.Position | Usage.Normal);
		
		whell2 = new ModelInstance(model);
		
		model = modelBuilder.createCylinder(0.5f,0.5f,1,30,
				new Material(ColorAttribute.createAmbient(Color.CYAN)),
				Usage.Position | Usage.Normal);
		
		whell3 = new ModelInstance(model);
		
		Vector3[] whell=new Vector3[3];
		whell[0]=new Vector3(0,0.3f,2);
		whell[1]=new Vector3(-1,0.3f,-2);
		whell[2]=new Vector3(1,0.3f,-2);
		car1=new PhysicCar();
		car1.createCar(PhysicsWorld.instance("bw").getBoxCollisionShape(new Vector3(1,0.5f,1)), 800, whell,"car1","bw");
		
		environment = new Environment();
	        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}
int a=0;
	public void render() {
		
		a++;
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        PhysicsWorld.instance("bw").update();
        
        
        Matrix4 m=new Matrix4();
        m.set(PhysicsWorld.instance("bw").getMatrixName("a"));
        instance1.transform= m;
        
        Matrix4 m1=new Matrix4();
        m1.set(PhysicsWorld.instance("bw").getMatrixName("b"));
        instance.transform= m1;
       
        Matrix4 m3=new Matrix4();
        m3.set(car1.getMatrixChassisCar());
        chaiss.transform= m3;
        
        Matrix4 m4=new Matrix4();
        m4.set(car1.getWhellMatrix()[0]);
        whell1.transform= m4;
        
        Matrix4 m5=new Matrix4();
        m5.set(car1.getWhellMatrix()[1]);
        whell2.transform= m5;
        
        Matrix4 m6=new Matrix4();
        m6.set(car1.getWhellMatrix()[2]);
        whell3.transform= m6;
        
        cam.position.set(car1.getCarPosition().x, 10f,car1.getCarPosition().z );
		cam.lookAt(car1.getCarPosition().x, 0,car1.getCarPosition().z);
	
		cam.update();
       // whell3.transform= m6.rotate(new Vector3(0,1,0),90);
        
        if (a==30){
        	
        	a=0;
        }
        
        
        instance.calculateTransforms();
        modelBatch.begin(cam);
     
        modelBatch.render(instance,environment);
        modelBatch.render(instance1,environment);
        modelBatch.render(whell1,environment);
        modelBatch.render(whell2,environment);
        modelBatch.render(whell3,environment);
        modelBatch.render(chaiss,environment);
        modelBatch.end();

	}

	public void resize(int width, int height) {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
	}
}