
import javax.swing.*;
import java.util.EventObject;
import java.awt.*;

class Main {
  public static JCanvas Canvas() {
    JCanvas newC = new JCanvas();
    JBox.setSize(newC, 50, 20);
    return newC;
  }

  public static JButton Button(String label) {
    JButton newB = new JButton(label);
    JBox.setSize(newB, 90, 15);
    return newB;
  }

  public static void main(String[] args) {

    JFrame frame = new JFrame("Lab Simulators");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 400);


    JFrame pMotionFrame = new JFrame("Projectile Motion");
    pMotionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    pMotionFrame.setSize(500, 400);

    JFrame titrationFrame = new JFrame("Titration");
    titrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    titrationFrame.setSize(500, 400);

    JEventQueue events = new JEventQueue();

    ImageIcon pImg = new ImageIcon("PM.png");
    ImageIcon tImg = new ImageIcon("T.png");
    Color lG = new Color(209, 255, 203);

    JButton prjMotion = new JButton("Projectile Motion   ", pImg);
    JBox.setSize(prjMotion, 330, 120);
    JButton titration = new JButton("Titration Simulator", tImg);
    JBox.setSize(titration, 330, 120);

    JBox layout = JBox.hbox(JBox.vbox(JBox.vglue(), prjMotion, titration, JBox.vglue()), JBox.hglue());

    layout.setBackground(lG);

    events.listenTo(titration, "t");
    events.listenTo(prjMotion, "p");

    frame.add(layout);
    frame.setVisible(true);

    while (true) {
      frame.setVisible(true);
      EventObject event = events.waitEvent();
      String whatHappened = events.getName(event);

      if (whatHappened.equals("p")) {
        frame.setVisible(false);
        ProjectileSim p = new ProjectileSim();
        p.start();
      } else if (whatHappened.equals("t")) {
        frame.setVisible(false);
        TitrationSim t = new TitrationSim();
        t.start();
      }

    }

  }
}
