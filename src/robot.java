/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package robot;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.geometry.Box;
import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Enumeration;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.GraphicsConfiguration;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Dawid i trochę Gosia :D
 */
public class robot extends Applet implements ActionListener, KeyListener {
    
    private SimpleUniverse univ = null;
    private BranchGroup scene = null;    
    private Timer timer;    
    private TransformGroup animacja;    
    private RotateBehavior awtBehavior;    
    public float dlugoscCylindra=1.5f;    
    public float polozenieCylindra=0f;  
    public float polozenieCylindrap=0f;
    private Transform3D trans= new Transform3D();
    private Transform3D trans3= new Transform3D();  
    private Transform3D trans1= new Transform3D();     
    private Transform3D p_cylindra3= new Transform3D();    
    private float yloc=0f;   
    private float ylocp=0f; 
    private TransformGroup animowane;    
    private TransformGroup animowane2;    
    private TransformGroup animowane1;   
    Appearance  wygladCylindra3 = new Appearance();   
    private Cylinder cylinder3 = new Cylinder(0.05f, dlugoscCylindra, wygladCylindra3);    
    public float angle=0f;   
    public float anglep=0f; 
    public boolean prawo;   
    private Button go = new Button("Go");    
    private Button reset = new Button("Reset");   
    private Button nagraj = new Button("Nagraj");    
    private Button odtworz = new Button("Odtwórz");
   
    TransformGroup transformacja_c3 = new TransformGroup(p_cylindra3);   
    private Transform3D trans2 = new Transform3D();   
    private boolean os, nagrywanie;   
    public float speed=1f;   
    private float polBox=2f;   
    public boolean polaczone=false;    
    public Group box1;
    
    public Queue<Character> kolejka = new LinkedList();


    
    
    public BranchGroup createSceneGraph() {
	
	BranchGroup objRoot = new BranchGroup();
        
        objRoot.setCapability(Group.ALLOW_CHILDREN_EXTEND) ;
        Color3f kolor_swiatla_tla      = new Color3f(0.5f, 0.4f, 0.91f);
        Color3f kolor_swiatla_kier     = new Color3f(1.0f, 0.5f, 0.0f);
        Color3f kolor_swiatla_pnkt     = new Color3f(0.0f, 1.0f, 0.0f);
        Color3f kolor_swiatla_sto      = new Color3f(0.0f, 0.0f, 1.0f);
        
        BoundingSphere obszar_ogr =  new BoundingSphere(new Point3d(0.0d,0.0d,0.0d), 10.0d);

        Vector3f kierunek_swiatla_kier = new Vector3f(4.0f, -5.0f, -2.0f);
        Vector3f kierunek_swiatla_sto  = new Vector3f(-4.0f, -5.0f, -2.0f);

	// Create the transform group node and initialize it to the
	// identity.  Enable the TRANSFORM_WRITE capability so that
	// our behavior code can modify it at runtime.  Add it to the
	// root of the subgraph.
	animacja = new TransformGroup();
        animowane=new TransformGroup();
        animowane1=new TransformGroup();
        animowane2=new TransformGroup();
	animacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        animowane.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        animowane1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        animowane2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objRoot.addChild(animacja);
        animacja.addChild(animowane);
        animowane.addChild(animowane1);
        objRoot.addChild(animowane2);
        
        
	//swiatla
        AmbientLight swiatlo_tla = new AmbientLight(kolor_swiatla_tla);
        DirectionalLight swiatlo_kier = new DirectionalLight(kolor_swiatla_kier, kierunek_swiatla_kier);
        PointLight swiatlo_pnkt = new PointLight(kolor_swiatla_pnkt, new Point3f(-0.5f,0.5f,-0.5f), new Point3f(0.1f,0.1f,0.1f));
        SpotLight swiatlo_sto = new SpotLight(kolor_swiatla_sto, new Point3f(0.5f, 0.5f, -0.5f), new Point3f(0.01f,0.01f,0.01f),
                                   new Vector3f(-1.0f, -1.0f, -1.0f), (float)(Math.PI), 100);

        swiatlo_tla.setInfluencingBounds(obszar_ogr);
        swiatlo_kier.setInfluencingBounds(obszar_ogr);
        swiatlo_pnkt.setInfluencingBounds(obszar_ogr);
        swiatlo_sto.setInfluencingBounds(obszar_ogr);
        objRoot.addChild(swiatlo_tla);
        objRoot.addChild(swiatlo_kier);
        objRoot.addChild(swiatlo_pnkt);
        objRoot.addChild(swiatlo_sto);
        
        Background background = new Background(new Color3f(0.0f,0.0f,0.5f));     
        background.setApplicationBounds(obszar_ogr);
        objRoot.addChild(background);
        
        //ziemia
        Appearance wyglad_ziemia = new Appearance();
        
        TextureLoader loader = new TextureLoader("obrazki/trawka.gif",null);
        ImageComponent2D image = loader.getImage();

        Texture2D trawka = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                                        image.getWidth(), image.getHeight());

        trawka.setImage(0, image);
        trawka.setBoundaryModeS(Texture.WRAP);
        trawka.setBoundaryModeT(Texture.WRAP);
        
        wyglad_ziemia.setTexture(trawka);
        
        Point3f[]  coords = new Point3f[4];
        for(int i = 0; i< 4; i++)
            coords[i] = new Point3f();

        Point2f[]  tex_coords = new Point2f[4];
        for(int i = 0; i< 4; i++)
            tex_coords[i] = new Point2f();

        coords[0].y = -0.7f;
        coords[1].y = -0.7f;
        coords[2].y = -0.7f;
        coords[3].y = -0.7f;

        coords[0].x = 12.0f;
        coords[1].x = 12.0f;
        coords[2].x = -12.0f;
        coords[3].x = -12.0f;

        coords[0].z = 11.0f;
        coords[1].z = -13.0f;
        coords[2].z = -13.0f;
        coords[3].z = 11.0f;

        tex_coords[0].x = 0.0f;
        tex_coords[0].y = 0.0f;

        tex_coords[1].x = 20.0f;
        tex_coords[1].y = 0.0f;

        tex_coords[2].x = 0.0f;
        tex_coords[2].y = 20.0f;

        tex_coords[3].x = 20.0f;
        tex_coords[3].y = 20.0f;
        
        QuadArray qa_ziemia = new QuadArray(4, GeometryArray.COORDINATES|
                GeometryArray.TEXTURE_COORDINATE_2);
        qa_ziemia.setCoordinates(0,coords);

        qa_ziemia.setTextureCoordinates(0, tex_coords);


        Shape3D ziemia = new Shape3D(qa_ziemia);
        ziemia.setAppearance(wyglad_ziemia);

        objRoot.addChild(ziemia);
        
	//cylinder1      
        Material material_walca = new Material(new Color3f(0.5f, 0.3f,0.2f), new Color3f(0.1f,0.1f,0.1f),
                                                new Color3f(0.8f, 0.3f, 0.5f), new Color3f(0.2f, 0.2f, 0.2f), 20.0f);
        
        ColoringAttributes cattr = new ColoringAttributes();
        cattr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        
        Appearance  wygladCylindra = new Appearance();
        
        wygladCylindra.setMaterial(material_walca);
        wygladCylindra.setColoringAttributes(cattr);
        Cylinder cylinder = new Cylinder(0.4f, 0.4f, wygladCylindra);
        
        
        Transform3D  p_cylindra   = new Transform3D();
        p_cylindra.set(new Vector3f(0.0f,-0.5f,-1.0f));
      
        TransformGroup transformacja_c = new TransformGroup(p_cylindra);     
        animacja.addChild(transformacja_c);

        transformacja_c.addChild(cylinder);
        
        //podstawka
        
        Appearance  wygladpodstawki = new Appearance();
         Material material_podstawki = new Material(new Color3f(0.2f, 0.2f,0.2f), new Color3f(0.2f,0.2f,0.2f),
                                                new Color3f(0.5f, 0.3f, 0.2f), new Color3f(0.2f, 0.2f, 0.2f), 80.0f);
        
        wygladpodstawki.setMaterial(material_podstawki);
        wygladpodstawki.setColoringAttributes(cattr);
        Box podstawka = new Box(0.32f, 0.315f, 0.32f, wygladpodstawki);

        Transform3D  p_podstawka   = new Transform3D();
        p_podstawka.set(new Vector3f(1.65f,-0.38f,0.15f));

        TransformGroup transformacja_podstawka = new TransformGroup(p_podstawka);
        transformacja_podstawka.addChild(podstawka);
        animacja.addChild(transformacja_podstawka);
        
        
        //cylinder2
        Appearance  wygladCylindra2 = new Appearance();
        
        Material material_walca2 = new Material(new Color3f(0.5f, 0.3f,0.2f), new Color3f(0.2f,0.2f,0.2f),
                                                new Color3f(0.8f, 0.3f, 0.2f), new Color3f(0.2f, 0.2f, 0.2f), 80.0f);
        
        wygladCylindra2.setMaterial(material_walca2);
        wygladCylindra2.setColoringAttributes(cattr);
        
        Cylinder cylinder2 = new Cylinder(0.05f, 2.0f, wygladCylindra2);

        Transform3D  p_cylindra2   = new Transform3D();
        p_cylindra2.set(new Vector3f(0.0f,0.5f,-1.0f));

        TransformGroup transformacja_c2 = new TransformGroup(p_cylindra2);
        transformacja_c2.addChild(cylinder2);
        animacja.addChild(transformacja_c2);


        //PUDEŁKO
        Appearance  wygladboxa = new Appearance();
        wygladboxa.setMaterial(material_walca);
        wygladboxa.setColoringAttributes(cattr);
        Box box = new Box(0.4f, 0.06f, 0.15f, wygladboxa);

       // Vector3f polozenieboxa = new Vector3f(0.0f,1.5f,-1.0f);
        float[] polozenieboxa = new float[3];
        polozenieboxa[0] = 0f;//stale
        polozenieboxa[1] = 1.3f;//<=1.5f, >=0.25f
        polozenieboxa[2] = -1f;//stale

        Transform3D  p_boxa   = new Transform3D();
        p_boxa.set(new Vector3f(polozenieboxa[0],polozenieboxa[1],polozenieboxa[2]));

        TransformGroup transformacja_b = new TransformGroup(p_boxa);
        transformacja_b.addChild(box);
        animowane.addChild(transformacja_b);
   

        //CYLINDER3
        Appearance  wygladCylindra3 = new Appearance();
        wygladCylindra3.setMaterial(material_walca2);
        wygladCylindra3.setColoringAttributes(cattr);
        Cylinder cylinder3 = new Cylinder(0.05f, dlugoscCylindra, wygladCylindra3);

        Transform3D  p_cylindra3   = new Transform3D();
        p_cylindra3.set(new Vector3f(polozenieboxa[0]+(dlugoscCylindra/2),polozenieboxa[1],polozenieboxa[2]));

        Transform3D  tmp_rot      = new Transform3D();
        tmp_rot.rotZ(Math.PI/2);

        p_cylindra3.mul(tmp_rot);

        TransformGroup transformacja_c3 = new TransformGroup(p_cylindra3);
        transformacja_c3.addChild(cylinder3);
        animowane1.addChild(transformacja_c3);
     
        //CHWYTAK
        Appearance  wygladdlon = new Appearance();
        wygladdlon.setColoringAttributes(new ColoringAttributes(0.0f,1.0f,0.0f,ColoringAttributes.NICEST));
        Box dlon = new Box(0.04f, 0.07f, 0.15f, wygladdlon);

        Transform3D  p_dlon   = new Transform3D();
        p_dlon.set(new Vector3f(polozenieboxa[0]+(dlugoscCylindra),polozenieboxa[1],polozenieboxa[2]));

        TransformGroup transformacja_dlon = new TransformGroup(p_dlon);
        transformacja_dlon.addChild(dlon);
        animowane1.addChild(transformacja_dlon);

        ////////////////////////////////////////
      
        Appearance  wygladpalce = new Appearance();
        wygladpalce.setColoringAttributes(new ColoringAttributes(1.0f,0.0f,0.0f,ColoringAttributes.NICEST));
        Box palce = new Box(0.1f, 0.08f, 0.04f, wygladpalce);

        Transform3D  p_palce   = new Transform3D();
        p_palce.set(new Vector3f(polozenieboxa[0]+dlugoscCylindra+0.14f,polozenieboxa[1],polozenieboxa[2]-0.1f));

        TransformGroup transformacja_palce = new TransformGroup(p_palce);
        transformacja_palce.addChild(palce);
        animowane1.addChild(transformacja_palce);

        ////////////////////////////////////////

        Appearance  wygladkciuk = new Appearance();
        wygladkciuk.setColoringAttributes(new ColoringAttributes(0.0f,0.0f,1.0f,ColoringAttributes.NICEST));
        Box kciuk = new Box(0.1f, 0.08f, 0.04f, wygladkciuk);

        Transform3D  p_kciuk   = new Transform3D();
        p_kciuk.set(new Vector3f(polozenieboxa[0]+dlugoscCylindra+0.14f,polozenieboxa[1],polozenieboxa[2]+0.1f));

        TransformGroup transformacja_kciuk = new TransformGroup(p_kciuk);
        transformacja_kciuk.addChild(kciuk);
        animowane1.addChild(transformacja_kciuk);

        Group box1 = createBox(0.3, new Vector3d(1.5, 2, 0.0));
	

	animowane2.addChild(box1);
	
	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
						   100.0);
        // create the RotateBehavior	
        awtBehavior = new RotateBehavior(animowane);
	awtBehavior.setSchedulingBounds(bounds);
	objRoot.addChild(awtBehavior);

	return objRoot;
    }
    
    public class Boxx extends Shape3D {

    public Boxx(double xsize, double ysize, double zsize) {
	super();
	double xmin = 0;
	double xmax =  1;
	double ymin = 0;
	double ymax =  1;
	double zmin = 0;
	double zmax =  1;

	QuadArray box = new QuadArray(24, QuadArray.COORDINATES);

	Point3d verts[] = new Point3d[24];

	// front face
	verts[0] = new Point3d(xmax, ymin, zmax);
	verts[1] = new Point3d(xmax, ymax, zmax);
	verts[2] = new Point3d(xmin, ymax, zmax);
	verts[3] = new Point3d(xmin, ymin, zmax);
	// back face
	verts[4] = new Point3d(xmin, ymin, zmin);
	verts[5] = new Point3d(xmin, ymax, zmin);
	verts[6] = new Point3d(xmax, ymax, zmin);
	verts[7] = new Point3d(xmax, ymin, zmin);
	// right face
	verts[8] = new Point3d(xmax, ymin, zmin);
	verts[9] = new Point3d(xmax, ymax, zmin);
	verts[10] = new Point3d(xmax, ymax, zmax);
	verts[11] = new Point3d(xmax, ymin, zmax);
	// left face
	verts[12] = new Point3d(xmin, ymin, zmax);
	verts[13] = new Point3d(xmin, ymax, zmax);
	verts[14] = new Point3d(xmin, ymax, zmin);
	verts[15] = new Point3d(xmin, ymin, zmin);
	// top face
	verts[16] = new Point3d(xmax, ymax, zmax);
	verts[17] = new Point3d(xmax, ymax, zmin);
	verts[18] = new Point3d(xmin, ymax, zmin);
	verts[19] = new Point3d(xmin, ymax, zmax);
	// bottom face
	verts[20] = new Point3d(xmin, ymin, zmax);
	verts[21] = new Point3d(xmin, ymin, zmin);
	verts[22] = new Point3d(xmax, ymin, zmin);
	verts[23] = new Point3d(xmax, ymin, zmax);

	box.setCoordinates(0, verts);
        setGeometry(box);
        
        Appearance app= new Appearance();
        
        app.setCapability(app.ALLOW_COLORING_ATTRIBUTES_WRITE);
        app.setCapability(app.ALLOW_MATERIAL_WRITE);
        ColoringAttributes cattr2 = new ColoringAttributes();
        cattr2.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        cattr2.setColor(0.6f, 0.3f, 0.2f);
     
        Material material_walca2 = new Material(new Color3f(0.5f, 0.3f,0.2f), new Color3f(0.8f,0.2f,0.2f),
                                                new Color3f(0.8f, 0.3f, 0.5f), new Color3f(0.2f, 0.2f, 0.2f), 20.0f);
        app.setMaterial(material_walca2);
        app.setColoringAttributes(cattr2);
        
        
	setAppearance(app);
    }
}
    private Group createBox(double scale, Vector3d pos) {
	// Create a transform group node to scale and position the object.
	Transform3D t = new Transform3D();
	t.set(scale, pos);
	TransformGroup objTrans = new TransformGroup(t);

	// Create a simple shape leaf node and add it to the scene graph
	Shape3D shape = new Boxx(1,1, 1.0);
	objTrans.addChild(shape);        
        
        Appearance  app = shape.getAppearance();
       
       shape.setAppearance(app);
       
	// Create a new Behavior object that will perform the collision
	// detection on the specified object, and add it into
	// the scene graph.
	CollisionDetector cd = new CollisionDetector(shape);
	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	cd.setSchedulingBounds(bounds);

	// Add the behavior to the scene graph
	objTrans.addChild(cd);

	return objTrans;
    }
  
    public void keyPressed(KeyEvent e) {
        
   if (e.getKeyChar()=='w') {if((yloc + speed*.01f)<=0.15f)yloc = yloc + speed*.01f;os=false;if(nagrywanie==true) kolejka.add('w');}
   if (e.getKeyChar()=='s') {if((yloc - speed*.01f)>=-1.5f)yloc = yloc - speed*.01f;os=false;if(nagrywanie==true) kolejka.add('s');}
   if (e.getKeyChar()=='a') {if((polozenieCylindra - speed*.01f)>=-0.37f)polozenieCylindra = polozenieCylindra - speed*.01f;os=true;if(nagrywanie==true) kolejka.add('a');}
   if (e.getKeyChar()=='d') {if((polozenieCylindra + speed*.01f)<=0.37f)polozenieCylindra = polozenieCylindra + speed*.01f;os=true;if(nagrywanie==true) kolejka.add('d');}
   if (e.getKeyChar()=='q') {angle = angle + speed*.01f;os=false;if(nagrywanie==true) kolejka.add('q');}
   if (e.getKeyChar()=='e') {angle = angle - speed*.01f;os=false;if(nagrywanie==true) kolejka.add('e');}
   if (e.getKeyChar()=='p') {speed=speed*2f;}
   if (e.getKeyChar()=='o') {speed=speed/2f;}
}

public void keyReleased(KeyEvent e){

   // Invoked when a key has been released.

}

public void keyTyped(KeyEvent e){

   //Invoked when a key has been typed.

}

public void actionPerformed(ActionEvent e ) {

   // start timer when button is pressed

  if (e.getSource()==go){

      if (!timer.isRunning()) {
        angle=0;
        speed=1;
        polozenieCylindra=0;
        yloc=0;
        trans.rotY(angle);
        trans.setTranslation(new Vector3f((float) sin(angle),yloc,(float) cos(angle)-1f)); 
        animowane.setTransform(trans); 
     
        trans1.setTranslation(new Vector3f(polozenieCylindra,0, 0)); 
        animowane1.setTransform(trans1);
     
        timer.start();
      }
    }
   
    if (e.getSource()==reset){
       angle=0;
       speed=1;
       polozenieCylindra=0;
       yloc=0;
       trans.rotY(angle);
       polBox=2f;
       polaczone=false;
       trans.setTranslation(new Vector3f((float) sin(angle),yloc,(float) cos(angle)-1f)); 
       animowane.setTransform(trans); 
       
       trans1.setTranslation(new Vector3f(polozenieCylindra,0, 0)); 
       animowane1.setTransform(trans1);
       
    trans3.setTranslation(new Vector3f(0,0,0)); 
    animowane2.setTransform(trans3);
  
    }
    
    if (e.getSource()==odtworz){
        
        yloc=ylocp;
        polozenieCylindra=polozenieCylindrap;
        angle=anglep;
//        while (!kolejka.isEmpty()){
//            System.out.format(String.valueOf(kolejka.poll()));
//        }
        
        while (!kolejka.isEmpty()){
        if (kolejka.poll()=='w'){if((yloc + speed*.01f)<=0.15f)yloc = yloc + speed*.01f;os=false;        while (!kolejka.isEmpty()){
            System.out.format(String.valueOf(kolejka.poll()));
        }}
        else if (kolejka.poll()=='s'){if((yloc - speed*.01f)>=-1.5f)yloc = yloc - speed*.01f;os=false;}
            else if (kolejka.poll()=='a'){ if((polozenieCylindra - speed*.01f)>=-0.37f)polozenieCylindra = polozenieCylindra - speed*.01f;os=true;}
                else if (kolejka.poll()=='d') {if((polozenieCylindra + speed*.01f)<=0.37f)polozenieCylindra = polozenieCylindra + speed*.01f;os=true;}
                    else if (kolejka.poll()=='q') {angle = angle + speed*.01f;os=false;}
                        else if (kolejka.poll()=='e') {angle = angle - speed*.01f;os=false;}
        
        
        }
        
        nagrywanie=false;
    }
    
        if (e.getSource()==nagraj){
        nagrywanie=true;
        ylocp=yloc;
        polozenieCylindrap=polozenieCylindra;
        anglep=angle;
    }
    
    if((polBox>-0.05f)&&(!polaczone))
    {       polBox=polBox-0.01f;
    trans2.setTranslation(new Vector3f(0,polBox-2,0)); 
    animowane2.setTransform(trans2);
    
    }
     
    
    if(!os){
    trans.rotY(angle);
    trans.setTranslation(new Vector3f((float) sin(angle),yloc,(float) cos(angle)-1f)); 
    animowane.setTransform(trans); 
    if(polaczone)
    {
        trans3.rotY(angle);
    trans3.setTranslation(new Vector3f(0,yloc-0.8f,0)); 
   animowane2.setTransform(trans3);
    }
    
           }
    if(os){   
    trans1.setTranslation(new Vector3f(polozenieCylindra,0, 0)); 
    animowane1.setTransform(trans1);   

    }
    

}

public class CollisionDetector extends Behavior {
    private  final Color3f highlightColor =
	new Color3f(0.0f, 1.0f, 0.0f);
    private  final ColoringAttributes highlight =
	new ColoringAttributes(highlightColor,
			       ColoringAttributes.SHADE_GOURAUD);

    public boolean inCollision = false;
    private Shape3D shape;
    private ColoringAttributes shapeColoring;
    private Appearance shapeAppearance;
    private WakeupOnCollisionEntry wEnter;
    private WakeupOnCollisionExit wExit;
    public CollisionDetector(Shape3D s) {
	shape = s;
	shapeAppearance = shape.getAppearance();
	shapeColoring = shapeAppearance.getColoringAttributes();
	inCollision = false;
    }

    public void initialize() {
	wEnter = new WakeupOnCollisionEntry(shape);
	wExit = new WakeupOnCollisionExit(shape);
	wakeupOn(wEnter);
    }

    public void processStimulus(Enumeration criteria) {
	inCollision = !inCollision;
 polaczone=true;
	if (inCollision) {
	    shapeAppearance.setColoringAttributes(highlight);
           
	    wakeupOn(wExit);
            
	}
	else {
            
	    shapeAppearance.setColoringAttributes(shapeColoring);
	    wakeupOn(wEnter);
	}
    }
}



    public robot() {
        // Initialize the GUI components
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
      //  initComponents();
      setLayout(new BorderLayout());

   GraphicsConfiguration config =

      SimpleUniverse.getPreferredConfiguration();

   Canvas3D c = new Canvas3D(config);

   add("Center", c);

   c.addKeyListener(this);

   timer = new Timer(10,this);

   //timer.start();

   Panel p =new Panel();

   p.add(go);
   p.add(reset);
   p.add(nagraj);
   p.add(odtworz);
   
      

   add("North",p);

   go.addActionListener(this);
   go.addKeyListener(this);
   reset.addActionListener(this);
   reset.addKeyListener(this);
   nagraj.addActionListener(this);
   nagraj.addKeyListener(this);
   odtworz.addActionListener(this);
   odtworz.addKeyListener(this);
   
   
     Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.3f,6.0f));
// Create a simple scene and attach it to the virtual universe



   BranchGroup scene = createSceneGraph();

   SimpleUniverse u = new SimpleUniverse(c);

   u.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

   u.addBranchGraph(scene);
   
   OrbitBehavior orbit = new OrbitBehavior(c, OrbitBehavior.REVERSE_ROTATE); //obracanie obserwatora
        orbit.setSchedulingBounds(new BoundingSphere());
        u.getViewingPlatform().setViewPlatformBehavior(orbit);
      
    }

    
    class RotateBehavior extends Behavior {

        private TransformGroup transformGroup;
        private Transform3D trans = new Transform3D();
        private WakeupCriterion criterion;
        private float angle = 0.0f;

        private final int ROTATE = 1;

        // create a new RotateBehavior
        RotateBehavior(TransformGroup tg) {
            transformGroup = tg;
        }

        // initialize behavior to wakeup on a behavior post with id = ROTATE
        public void initialize() {
            criterion = new WakeupOnBehaviorPost(this, ROTATE);
            wakeupOn(criterion);
        }

        // processStimulus to rotate the cube
        public void processStimulus(Enumeration criteria) {
            if(prawo)
             angle += Math.toRadians(1.0);
            else
                 angle -= Math.toRadians(1.0);
            /*trans.rotY(angle);
            
             trans.setTranslation(new Vector3f((float) sin(angle),yloc,(float) cos(angle)-1f)); 
             
            transformGroup.setTransform(trans);*/
            wakeupOn(criterion);
        }
        // when the mouse is clicked, postId for the behavior
        void rotate() {
            postId(ROTATE);
        }
    }
  

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                robot PR=new robot();
              

   PR.addKeyListener(PR);
                MainFrame mf = new MainFrame(PR, 1200, 1200); 
            }
        });
    }     

}
