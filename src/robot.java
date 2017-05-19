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
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class robot extends JFrame{

    robot(){
        
        super("Robot cylindryczny");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

    
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(800,600));

        add(canvas3D);
        pack();
        setVisible(true);

        BranchGroup scena = utworzScene();
	scena.compile();

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.3f,6.0f));

        simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

        simpleU.addBranchGraph(scena);
        
    }
    

   BranchGroup utworzScene(){

      BranchGroup wezel_scena = new BranchGroup();      
      TransformGroup animacja = new TransformGroup();
      animacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      wezel_scena.addChild(animacja);

      Alpha alpha_animacja = new Alpha(-1,5000);

      RotationInterpolator obracacz = new RotationInterpolator(alpha_animacja, animacja);
      
      BoundingSphere bounds = new BoundingSphere();
      obracacz.setSchedulingBounds(bounds);
      animacja.addChild(obracacz);

     // BoundingSphere bounds = new BoundingSphere();
      obracacz.setSchedulingBounds(bounds);

      //ŚWIATŁA

      AmbientLight lightA = new AmbientLight();
      lightA.setInfluencingBounds(bounds);
      wezel_scena.addChild(lightA);

      Color3f light1Color = new Color3f(1.0f, 0.0f, 0.2f);
      Vector3f light1Direction = new Vector3f(0.0f, -0.5f, -1.0f);
      DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
      light1.setInfluencingBounds(bounds);
      wezel_scena.addChild(light1); 


      //CYLINDER1

      Appearance  wygladCylindra = new Appearance();
      wygladCylindra.setColoringAttributes(new ColoringAttributes(0.2f,0.6f,0.6f,ColoringAttributes.NICEST));
      Cylinder cylinder = new Cylinder(0.4f, 0.4f, wygladCylindra);

      Transform3D  p_cylindra   = new Transform3D();
      p_cylindra.set(new Vector3f(0.0f,-0.5f,-1.0f));
      
      TransformGroup transformacja_c = new TransformGroup(p_cylindra);
      transformacja_c.addChild(cylinder);
      animacja.addChild(transformacja_c);
      
      
      //CYLINDER2
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
      polozenieboxa[0] = 0f;
      polozenieboxa[1] = 1.5f;
      polozenieboxa[2] = -1f;
      
      Transform3D  p_boxa   = new Transform3D();
      p_boxa.set(new Vector3f(polozenieboxa[0],polozenieboxa[1],polozenieboxa[2]));
      
      TransformGroup transformacja_b = new TransformGroup(p_boxa);
      transformacja_b.addChild(box);
      animacja.addChild(transformacja_b);
     
      
      //CYLINDER3
      Appearance  wygladCylindra3 = new Appearance();
      wygladCylindra3.setColoringAttributes(new ColoringAttributes(0.9f,0.4f,0.9f,ColoringAttributes.NICEST));
      Cylinder cylinder3 = new Cylinder(0.05f, 1.0f, wygladCylindra3);

      Transform3D  p_cylindra3   = new Transform3D();
      p_cylindra3.set(new Vector3f(polozenieboxa[0]+0.5f,polozenieboxa[1],polozenieboxa[2]));
      
      Transform3D  tmp_rot      = new Transform3D();
      tmp_rot.rotZ(Math.PI/2);

      p_cylindra3.mul(tmp_rot);
      
      TransformGroup transformacja_c3 = new TransformGroup(p_cylindra3);
      transformacja_c3.addChild(cylinder3);
      animacja.addChild(transformacja_c3);
      
      //CHWYTAK
      Appearance  wygladdlon = new Appearance();
      wygladdlon.setColoringAttributes(new ColoringAttributes(0.0f,1.0f,0.0f,ColoringAttributes.NICEST));
      Box dlon = new Box(0.04f, 0.15f, 0.07f, wygladdlon);
      
      Transform3D  p_dlon   = new Transform3D();
      p_dlon.set(new Vector3f(polozenieboxa[0]+1.0f,polozenieboxa[1],polozenieboxa[2]));
      
      TransformGroup transformacja_dlon = new TransformGroup(p_dlon);
      transformacja_dlon.addChild(dlon);
      animacja.addChild(transformacja_dlon);
      
      ////////////////////////////////////////
      
      Appearance  wygladpalce = new Appearance();
      wygladpalce.setColoringAttributes(new ColoringAttributes(1.0f,0.0f,0.0f,ColoringAttributes.NICEST));
      Box palce = new Box(0.1f, 0.04f, 0.07f, wygladpalce);
      
      Transform3D  p_palce   = new Transform3D();
      p_palce.set(new Vector3f(1.05f,1.65f,-1.0f));
      
      TransformGroup transformacja_palce = new TransformGroup(p_palce);
      transformacja_palce.addChild(palce);
      animacja.addChild(transformacja_palce);
      
      ////////////////////////////////////////
      
      Appearance  wygladkciuk = new Appearance();
      wygladkciuk.setColoringAttributes(new ColoringAttributes(0.0f,0.0f,1.0f,ColoringAttributes.NICEST));
      Box kciuk = new Box(0.1f, 0.04f, 0.07f, wygladkciuk);
      
      Transform3D  p_kciuk   = new Transform3D();
      p_kciuk.set(new Vector3f(1.08f,1.39f,-1.0f));
      
      TransformGroup transformacja_kciuk = new TransformGroup(p_kciuk);
      transformacja_kciuk.addChild(kciuk);
      animacja.addChild(transformacja_kciuk);
      
      
      
      
      
      return wezel_scena ;

      
      
    }

   public static void main(String args[]){
//      new Pierwszy3D();
   //   System.getProperty("java.library.path");
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               robot robot= new robot();
               robot.setVisible(true);
               JOptionPane.showMessageDialog(robot,"hej","info",JOptionPane.PLAIN_MESSAGE);

                
            }
            
        });
       
   }



}


