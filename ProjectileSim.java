import javax.swing.*;
import java.awt.*;
import java.util.EventObject;

class ProjectileSim {

  JFrame f = new JFrame("Projectile Motion");
  ImageIcon pImg = new ImageIcon("PM.png");

  JButton bStart = Main.Button("START");
  JButton bReset = Main.Button("RESET");
  JButton bMenu = Main.Button("← BACK TO MENU");

  JSlider velocity = new JSlider(SwingConstants.HORIZONTAL, 5, 100, 50);
  JSlider angle = new JSlider(SwingConstants.HORIZONTAL, 1, 90, 45);
  JSlider height = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 0);

  JCanvas c = new JCanvas();
  JCanvas c2 = Main.Canvas();
  JCanvas c3 = Main.Canvas();
  JCanvas c4 = Main.Canvas();
  JCanvas c5 = Main.Canvas();
  JCanvas c6 = new JCanvas();
  JCanvas c9 = new JCanvas();
  JCanvas c7 = Main.Canvas();
  JCanvas c8 = new JCanvas();

  Color lB = new Color(135, 206, 235);
  Color lG = new Color(209, 255, 203);

  public ProjectileSim() {
    this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.f.setSize(500, 400);
    JBox.setSize(this.bMenu, 200, 15);
    JBox.setSize(this.velocity, 100, 20);
    JBox.setSize(this.angle, 100, 20);
    JBox.setSize(this.height, 100, 20);
    JBox.setSize(c6, 10, 10);
    JBox.setSize(c9, 5, 5);
    JBox.setSize(c8, 40, 20);

  }

  public void start() {
    JEventQueue events = new JEventQueue();
    JBox layout2 = JBox.vbox(bMenu, c,
        JBox.hbox(JBox.vbox(JBox.hbox(c7, height, c8), c6), JBox.vbox(bStart, bReset, c9),
            JBox.vbox(JBox.hbox(JBox.hglue(), c2, velocity, c3), JBox.hbox(JBox.hglue(), c4, angle, c5))));

    layout2.setBackground(Color.white);

    c2.drawString("Velocity", 0, 13);
    c4.drawString("Angle", 6, 13);
    c7.drawString("Height", 3, 13);

    c3.drawString(Integer.toString(velocity.getValue()) + " m/s", 3, 13);
    c5.drawString(Integer.toString(angle.getValue()) + "°", 3, 13);
    c8.drawString(Integer.toString(height.getValue()) + "m", 3, 13);

    c.setBackground(lB);
    c.setColor(Color.white);
    c.fillOval(15, 293, 7, 7);

    c.setColor(Color.green.darker());
    c.fillRect(0, 300, 500, 400);

    events.listenTo(bReset, "r");
    events.listenTo(bStart, "s");
    events.listenTo(height, "h");
    events.listenTo(angle, "a");
    events.listenTo(velocity, "v");
    events.listenTo(bMenu, "m");

    int hValue = 0;
    int vValue = 0;
    double aValue = 0;

    this.f.add(layout2);
    this.f.setVisible(true);

    boolean keepGoing = true;

    while (keepGoing == true) {
      EventObject event = events.waitEvent();
      String whatHappened = JEventQueue.getName(event);
      if (whatHappened.equals("m")) {
        this.f.dispose();
        return;
      }
      if (whatHappened.equals("h")) {
        c8.clear();
        hValue = height.getValue();
        c8.drawString(Integer.toString(hValue) + "m", 3, 13);

        c.setColor(lB);
        c.fillRect(8, 240, 22, 60);
        c.setColor(Color.lightGray);
        c.fillRect(8, 300 - hValue, 22, hValue);
        c.setColor(Color.white);
        c.fillOval(15, (300 - hValue) - 7, 7, 7);

      }
      if (whatHappened.equals("a")) {
        c5.clear();
        c5.drawString(Integer.toString(angle.getValue()) + "°", 3, 13);
      }
      if (whatHappened.equals("v")) {
        c3.clear();
        c3.drawString(Integer.toString(velocity.getValue()) + " m/s", 3, 13);
      }
      if (whatHappened.equals("r")) {
        c.setBackground(lB);

        c.setColor(Color.green.darker());
        c.fillRect(0, 300, 500, 400);

        c.setColor(Color.lightGray);
        c.fillRect(8, 300 - hValue, 22, hValue);
        c.setColor(Color.white);
        c.fillOval(15, (300 - hValue) - 7, 7, 7);
      }
      if (whatHappened.equals("s")) {
        c.setBackground(lB);

        c.setColor(Color.green.darker());
        c.fillRect(0, 300, 500, 400);

        c.setColor(Color.lightGray);
        c.fillRect(8, 300 - hValue, 22, hValue);
        c.setColor(Color.white);
        c.fillOval(15, (300 - hValue) - 7, 7, 7);
        vValue = velocity.getValue();
        aValue = Math.toRadians(angle.getValue());
        hValue = height.getValue();
        double time = 0;

        double Vx = vValue * Math.cos(aValue);
        double Vy = vValue * Math.sin(aValue);

        // double sValue =
        double t1 = (-(Vy) + Math.sqrt((Vy * Vy) - 4 * (-4.9) * (hValue))) / (2 * -4.9);
        double t2 = (-(Vy) - Math.sqrt((Vy * Vy) - 4 * (-4.9) * (hValue))) / (2 * -4.9);

        if (t1 > t2) {
          time = t1;
        } else {
          time = t2;
        }

        double vDistance = ((-(Vy * Vy)) / (2 * -9.8)) + hValue;
        double hDistance = time * Vx;
        System.out.println(hDistance);

        double xPos = 15;
        double yPos = hValue;
        double xPos2 = 0;

        while (yPos >= 0) {
          xPos2 = xPos;
          c.setColor(Color.white);
          c.fillOval((int) xPos, (int) ((300 - yPos) - 7), 7, 7);
          c.sleep(100);
          c.setColor(lB);
          c.fillOval((int) xPos, (int) ((300 - yPos) - 7), 7, 7);

          Vy = Vy - 9.81;

          xPos = xPos + Vx;
          yPos = yPos + Vy;

          c.setColor(Color.lightGray);
          c.fillRect(8, 300 - hValue, 22, hValue);

        }

        c.setColor(Color.white);
        c.fillOval((int)hDistance, 293, 7, 7);
      }
    }
  }

}