/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package robot;
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
import java.util.Enumeration;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.vecmath.Point3d;

/**
 *
 * @author Dawid
 */
public class robot extends JFrame {
    private SimpleUniverse univ = null;
    private BranchGroup scene = null;

    private TransformGroup animacja;
    private RotateBehavior awtBehavior;
    public float dlugoscCylindra=2f;
    private Transform3D trans= new Transform3D();
    private Transform3D p_cylindra3= new Transform3D();
    private float xloc=0f;
    private TransformGroup animowane;Appearance  wygladCylindra3 = new Appearance();
    private Cylinder cylinder3 = new Cylinder(0.05f, dlugoscCylindra, wygladCylindra3);
    
      
    public BranchGroup createSceneGraph() {
	// Create the root of the branch graph
	BranchGroup objRoot = new BranchGroup();

	// Create the transform group node and initialize it to the
	// identity.  Enable the TRANSFORM_WRITE capability so that
	// our behavior code can modify it at runtime.  Add it to the
	// root of the subgraph.
	animacja = new TransformGroup();
        animowane=new TransformGroup();
	animacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        animowane.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objRoot.addChild(animacja);
        animacja.addChild(animowane);
        
	// Create a simple shape leaf node, add it to the scene graph.
	
         Appearance  wygladCylindra = new Appearance();
        wygladCylindra.setColoringAttributes(new ColoringAttributes(0.2f,0.6f,0.6f,ColoringAttributes.NICEST));
        Cylinder cylinder = new Cylinder(0.4f, 0.4f, wygladCylindra);
        
         Transform3D  p_cylindra   = new Transform3D();
      
        p_cylindra.set(new Vector3f(0.0f,-0.5f,-1.0f));
      
         TransformGroup transformacja_c = new TransformGroup(p_cylindra);
         transformacja_c.addChild(cylinder);      
      animacja.addChild(transformacja_c);
       Appearance  wygladCylindra2 = new Appearance();
      wygladCylindra2.setColoringAttributes(new ColoringAttributes(0.0f,0.9f,0.6f,ColoringAttributes.NICEST));
      Cylinder cylinder2 = new Cylinder(0.05f, 2.0f, wygladCylindra2);

      Transform3D  p_cylindra2   = new Transform3D();
      p_cylindra2.set(new Vector3f(0.0f,0.5f,-1.0f));
      
      TransformGroup transformacja_c2 = new TransformGroup(p_cylindra2);
      transformacja_c2.addChild(cylinder2);
      animacja.addChild(transformacja_c2);
      

      //PUDEŁKO
      Appearance  wygladboxa = new Appearance();
      wygladboxa.setColoringAttributes(new ColoringAttributes(0.9f,0.2f,0.6f,ColoringAttributes.NICEST));
      Box box = new Box(0.15f, 0.06f, 0.15f, wygladboxa);
      
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
      wygladCylindra3.setColoringAttributes(new ColoringAttributes(0.9f,0.4f,0.9f,ColoringAttributes.NICEST));
      Cylinder cylinder3 = new Cylinder(0.05f, dlugoscCylindra, wygladCylindra3);

      Transform3D  p_cylindra3   = new Transform3D();
      p_cylindra3.set(new Vector3f(polozenieboxa[0]+(dlugoscCylindra/2),polozenieboxa[1],polozenieboxa[2]));
      
      Transform3D  tmp_rot      = new Transform3D();
      tmp_rot.rotZ(Math.PI/2);

      p_cylindra3.mul(tmp_rot);
      
      TransformGroup transformacja_c3 = new TransformGroup(p_cylindra3);
      transformacja_c3.addChild(cylinder3);
      animowane.addChild(transformacja_c3);
     
      //CHWYTAK
      Appearance  wygladdlon = new Appearance();
      wygladdlon.setColoringAttributes(new ColoringAttributes(0.0f,1.0f,0.0f,ColoringAttributes.NICEST));
      Box dlon = new Box(0.04f, 0.07f, 0.15f, wygladdlon);
      
      Transform3D  p_dlon   = new Transform3D();
      p_dlon.set(new Vector3f(polozenieboxa[0]+(dlugoscCylindra),polozenieboxa[1],polozenieboxa[2]));
      
      TransformGroup transformacja_dlon = new TransformGroup(p_dlon);
      transformacja_dlon.addChild(dlon);
      animowane.addChild(transformacja_dlon);
      
      ////////////////////////////////////////
      
      Appearance  wygladpalce = new Appearance();
      wygladpalce.setColoringAttributes(new ColoringAttributes(1.0f,0.0f,0.0f,ColoringAttributes.NICEST));
      Box palce = new Box(0.1f, 0.08f, 0.04f, wygladpalce);
      
      Transform3D  p_palce   = new Transform3D();
      p_palce.set(new Vector3f(polozenieboxa[0]+dlugoscCylindra+0.14f,polozenieboxa[1],polozenieboxa[2]-0.1f));
      
      TransformGroup transformacja_palce = new TransformGroup(p_palce);
      transformacja_palce.addChild(palce);
      animowane.addChild(transformacja_palce);
      
      ////////////////////////////////////////
      
      Appearance  wygladkciuk = new Appearance();
      wygladkciuk.setColoringAttributes(new ColoringAttributes(0.0f,0.0f,1.0f,ColoringAttributes.NICEST));
      Box kciuk = new Box(0.1f, 0.08f, 0.04f, wygladkciuk);
      
      Transform3D  p_kciuk   = new Transform3D();
      p_kciuk.set(new Vector3f(polozenieboxa[0]+dlugoscCylindra+0.14f,polozenieboxa[1],polozenieboxa[2]+0.1f));
      
      TransformGroup transformacja_kciuk = new TransformGroup(p_kciuk);
      transformacja_kciuk.addChild(kciuk);
      animowane.addChild(transformacja_kciuk);
	// create the RotateBehavior	
        awtBehavior = new RotateBehavior(animacja);
	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
						   100.0);
	awtBehavior.setSchedulingBounds(bounds);
	objRoot.addChild(awtBehavior);

	return objRoot;
    }
    private Canvas3D createUniverse() {
	GraphicsConfiguration config =
	    SimpleUniverse.getPreferredConfiguration();

	Canvas3D c = new Canvas3D(config);

	univ = new SimpleUniverse(c);
         Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.3f,6.0f));

        univ.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
	// This will move the ViewPlatform back a bit so the
	// objects in the scene can be viewed.
      //  univ.getViewingPlatform().setNominalViewingTransform();

	// Ensure at least 5 msec per frame (i.e., < 200Hz)
	//univ.getViewer().getView().setMinimumFrameCycleTime(5);

	return c;
    }
    public robot() {
        // Initialize the GUI components
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        initComponents();

        // Create Canvas3D and SimpleUniverse; add canvas to drawing panel
        Canvas3D c = createUniverse();
        drawingPanel.add(c, java.awt.BorderLayout.CENTER);

        // Create the content branch and add it to the universe
        scene = createSceneGraph();
        univ.addBranchGraph(scene);
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
            angle += Math.toRadians(10.0);
            trans.rotY(angle);
            transformGroup.setTransform(trans);
            wakeupOn(criterion);
        }

        // when the mouse is clicked, postId for the behavior
        void rotate() {
            postId(ROTATE);
        }
    }
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        guiPanel = new javax.swing.JPanel();
        rotateButton = new javax.swing.JButton();
        rotateButton1 = new javax.swing.JButton();
        rotateButton3 = new javax.swing.JButton();
        rotateButton4 = new javax.swing.JButton();
        drawingPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Swing Interaction Test");

        guiPanel.setLayout(new java.awt.GridBagLayout());

        rotateButton.setText("dol");
        rotateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateButtonActionPerformed(evt);
            }
        });
        rotateButton1.setText("gora");
        rotateButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotate1ButtonActionPerformed(evt);
            }
        });
        rotateButton3.setText("lewo");
        rotateButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotate3ButtonActionPerformed(evt);
            }
        });
        rotateButton4.setText("prawo");
        rotateButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotate4ButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        guiPanel.add(rotateButton, gridBagConstraints);
        guiPanel.add(rotateButton1, gridBagConstraints);
        guiPanel.add(rotateButton3, gridBagConstraints);
        guiPanel.add(rotateButton4, gridBagConstraints);

        getContentPane().add(guiPanel, java.awt.BorderLayout.NORTH);

        drawingPanel.setPreferredSize(new java.awt.Dimension(700, 700));
        drawingPanel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(drawingPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>        
    private void rotateButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        xloc=xloc-0.01f;
       trans.setTranslation(new Vector3f(0,xloc,0.0f));
     animowane.setTransform(trans);
    }
    private void rotate1ButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        xloc=xloc+0.01f;
       trans.setTranslation(new Vector3f(0,xloc,0.0f));
     animowane.setTransform(trans);
    }  
    private void rotate3ButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        dlugoscCylindra=dlugoscCylindra-0.01f;
      // cylinder3=new Cylinder(0.05f, dlugoscCylindra, wygladCylindra3);
      // trans.
        //animowane.setTransform(trans);
    }
    private void rotate4ButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        dlugoscCylindra=dlugoscCylindra+0.01f;
      // cylinder3=new Cylinder(0.05f, dlugoscCylindra, wygladCylindra3);
       
     animowane.setTransform(trans);
    }     


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new robot().setVisible(true);
            }
        });
    }     
     private javax.swing.JPanel drawingPanel;
    private javax.swing.JPanel guiPanel;
    private javax.swing.JButton rotateButton;
    private javax.swing.JButton rotateButton1;
     private javax.swing.JButton rotateButton3;
    private javax.swing.JButton rotateButton4;
}
