/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package projektrobot;
import com.sun.j3d.utils.applet.MainFrame;
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

/**
 *
 * @author Dawid
 */
public class robot extends Applet implements ActionListener, KeyListener {
    
    private SimpleUniverse univ = null;
    private BranchGroup scene = null;    
    private Timer timer;    
    private TransformGroup animacja;    
    private RotateBehavior awtBehavior;    
    public float dlugoscCylindra=1.5f;    
    public float polozenieCylindra=0f;    
    private Transform3D trans= new Transform3D();    
    private Transform3D trans1= new Transform3D();     
    private Transform3D p_cylindra3= new Transform3D();    
    private float yloc=0f;    
    private TransformGroup animowane;    
    private TransformGroup animowane1;     
    Appearance  wygladCylindra3 = new Appearance();    
    private Cylinder cylinder3 = new Cylinder(0.05f, dlugoscCylindra, wygladCylindra3);
    public float angle=0f;    
    public boolean prawo;
    private Button go = new Button("Go");
    TransformGroup transformacja_c3 = new TransformGroup(p_cylindra3);   
    private Transform3D trans2 = new Transform3D();    
    private boolean os;
    public float speed=1f;
    
    
    public BranchGroup createSceneGraph() {
	
	BranchGroup objRoot = new BranchGroup();
        
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
	animacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        animowane.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        animowane1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objRoot.addChild(animacja);
        animacja.addChild(animowane);
        animowane.addChild(animowane1);
        
        
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


          // float dlugoscCylindra=1f;//>=0.2f
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
          // create the RotateBehavior	
        
	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
						   100.0);
        awtBehavior = new RotateBehavior(animowane);
	awtBehavior.setSchedulingBounds(bounds);
	objRoot.addChild(awtBehavior);

	return objRoot;
    }
  
    public void keyPressed(KeyEvent e) {

   if (e.getKeyChar()=='w') {if(yloc<=0.15f)yloc = yloc + speed*.01f;os=false;}

   if (e.getKeyChar()=='s') {if(yloc>=-1.5f)yloc = yloc - speed*.01f;os=false;}
   if (e.getKeyChar()=='a') {if(polozenieCylindra>=-0.37f)polozenieCylindra = polozenieCylindra - speed*.01f;
   os=true;
   }

   if (e.getKeyChar()=='d') {if(polozenieCylindra<=0.37f)polozenieCylindra = polozenieCylindra + speed*.01f;
   os=true;}
   if (e.getKeyChar()=='q') {angle = angle + speed*.01f;
   os=false;}

   if (e.getKeyChar()=='e') {angle = angle - speed*.01f;os=false;}
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

         timer.start();

      }

   }

   else {

       if(!os){
     trans.rotY(angle);
       trans.setTranslation(new Vector3f((float) sin(angle),yloc,(float) cos(angle)-1f)); 
     animowane.setTransform(trans);  
       }
       if(os){
        
          
          trans1.setTranslation(new Vector3f(polozenieCylindra+(float) sin(angle),yloc,(float) cos(angle)-1f)); 
     animowane1.setTransform(trans1);   
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

   add("North",p);

   go.addActionListener(this);

   go.addKeyListener(this);

     Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.3f,6.0f));
// Create a simple scene and attach it to the virtual universe

   BranchGroup scene = createSceneGraph();

   SimpleUniverse u = new SimpleUniverse(c);

   u.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

   u.addBranchGraph(scene);
      
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
    private javax.swing.JPanel drawingPanel;
    private javax.swing.JPanel guiPanel;
    private javax.swing.JButton rotateButton;
    private javax.swing.JButton rotateButton1;
    private javax.swing.JButton rotateButton3;
    private javax.swing.JButton rotateButton4;
    private javax.swing.JButton rotateButton5;
    private javax.swing.JButton rotateButton6;
}
