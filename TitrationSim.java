import javax.swing.*;
import java.awt.*;
import java.util.EventObject;

class TitrationSim {

  JFrame f = new JFrame("Titration Simulator");
  ImageIcon pImg = new ImageIcon("PM.png");

  JButton bMenu = Main.Button("← BACK TO MENU");
  JButton bDrop = new JButton("Drop");
  JButton bReset = new JButton("Reset");
  JCanvas c = new JCanvas();
  JCanvas c2 = new JCanvas();
  JCanvas c3 = new JCanvas();
  JCanvas c4 = new JCanvas();
  JCanvas c5 = new JCanvas();

  Color lB = new Color(135, 206, 235);
  Color lG = new Color(209, 255, 203);

  JTextField baseM = new JTextField();
  JTextField acidM = new JTextField();

  String[] acidStrings = { "HCl", "HNO\u2083", "HBr", "HI", "HClO\u2084" };
  String[] baseStrings = { "LiOH", "NaOH", "KOH", "RbOH", "CsOH" };

  JComboBox acidList = new JComboBox(acidStrings);
  JComboBox baseList = new JComboBox(baseStrings);

  JSlider buretVolumeSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 90, 50);

  public TitrationSim() {
    this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.f.setSize(500, 400);
    JBox.setSize(this.bMenu, 200, 15);
    JBox.setSize(this.bReset, 75, 25);
    JBox.setSize(this.c2, 70, 15);
    JBox.setSize(this.c3, 20, 20);
    JBox.setSize(this.c4, 20, 20);
    JBox.setSize(this.buretVolumeSlider, 70, 20);
    JBox.setSize(this.acidList, 70, 20);
    JBox.setSize(this.baseList, 70, 20);
    JBox.setSize(this.acidM, 50, 20);
    JBox.setSize(this.baseM, 50, 20);
    JBox.setSize(this.c5, 70, 15);

  }

  public void start() {
    JEventQueue events = new JEventQueue();
    JBox layout3 = JBox.vbox(bMenu, JBox.hbox(
        JBox.vbox(JBox.vglue(), JBox.hbox(baseM, c3), baseList, JBox.vglue(), JBox.hbox(acidM, c4), acidList,
            JBox.vglue()),
        c,
        JBox.vbox(JBox.vglue(), c5, buretVolumeSlider, JBox.vglue(), c2, bDrop, JBox.vglue(), JBox.vglue(), bReset)));
    layout3.setBackground(Color.lightGray);
    c.setBackground(Color.lightGray);
    c.drawLine(300, 10, 300, 215);
    c.drawLine(292, 10, 292, 215);
    c.drawLine(292, 215, 295, 225);
    c.drawLine(300, 215, 297, 225);
    c.drawLine(295, 225, 295, 228);
    c.drawLine(297, 225, 297, 228);

    c.setColor(lB);
    c.fillRect(293, 205 - (buretVolumeSlider.getValue() * 2), 6, 205 - (205 - (buretVolumeSlider.getValue() * 2)));
    c.setColor(Color.orange);
    c.fillRect(276, 205, 40, 7);
    c.setColor(Color.red);
    c.fillOval(287, 200, 17, 14);

    c.setColor(Color.black);
    for (int i = 185; i > 10; i = i - 20) {
      c.drawLine(292, i, 300, i);
    }

    c.drawString("10mL", 303, 190);
    c.drawString("30mL", 303, 150);
    c.drawString("50mL", 303, 110);
    c.drawString("70mL", 303, 70);
    c.drawString("90mL", 303, 30);

    c.drawLine(280, 240, 310, 240);
    c.drawLine(280, 240, 285, 245);
    c.drawLine(310, 240, 305, 245);
    c.drawLine(285, 245, 285, 300);
    c.drawLine(305, 245, 305, 300);
    c.drawLine(285, 300, 245, 345);
    c.drawLine(305, 300, 345, 345);
    c.drawLine(245, 345, 345, 345);

    c.setColor(lB);
    for (int i = 0; i < 50; i++) {
      c.drawLine(325 - i, 325, 343 - i, 344);
    }
    for (int i = 0; i < 50; i++) {
      c.drawLine(265 + i, 325, 247 + i, 344);
    }

    c3.drawString("M", 3, 15);
    c4.drawString("M", 3, 15);
    c5.drawString(buretVolumeSlider.getValue() + " mLs", 16, 10);

    c.setColor(Color.black);
    c.drawString("" + acidList.getSelectedItem(), 225, 325);
    c.drawString("" + baseList.getSelectedItem(), 240, 150);

    f.add(layout3);
    f.setVisible(true);
    events.listenTo(bMenu, "m");
    events.listenTo(bDrop, "d");
    events.listenTo(buretVolumeSlider, "v");
    events.listenTo(acidList, "al");
    events.listenTo(baseList, "bl");
    events.listenTo(bReset, "r");

    int buretCount = 0;
    int dNum = 0;
    int counter = 0;
    int x1 = 324;
    int x2 = 266;
    int y = 324;
    int buretVolume = 50;
    double baseVolume = 0;
    double acidVolume = 45;
    double baseConcentration = 0;
    double acidConcentration = 0;

    while (true) {
      EventObject event = events.waitEvent();
      String whatHappened = JEventQueue.getName(event);
      if (whatHappened.equals("v")) {
        c5.clear();
        c5.setColor(Color.black);
        c5.drawString(buretVolumeSlider.getValue() + " mLs", 16, 10);
        buretCount = 0;
        c.setColor(Color.lightGray);
        c.fillRect(293, 205 - (90 * 2), 6, 205 - (205 - (90 * 2)));
        c.setColor(lB);
        c.fillRect(293, 205 - (buretVolumeSlider.getValue() * 2), 6, 205 - (205 - (buretVolumeSlider.getValue() * 2)));
        c.setColor(Color.black);
        for (int i = 185; i > 10; i = i - 20) {
          c.drawLine(292, i, 300, i);
        }
        c.setColor(Color.orange);
        c.fillRect(276, 205, 40, 7);
        c.setColor(Color.red);
        c.fillOval(287, 200, 17, 14);

        buretVolume = buretVolumeSlider.getValue();
      }
      if (whatHappened.equals("m")) {
        this.f.dispose();
        return;
      }
      if (whatHappened.equals("al")) {
        c.setColor(Color.lightGray);
        c.fillRect(215, 310, 45, 15);
        c.setColor(Color.black);
        c.drawString("" + acidList.getSelectedItem(), 225, 325);
      }
      if (whatHappened.equals("bl")) {
        c.setColor(Color.lightGray);
        c.fillRect(230, 140, 45, 20);
        c.setColor(Color.black);
        c.drawString("" + baseList.getSelectedItem(), 240, 150);
      }
      if (whatHappened.equals("r")) {
        buretCount = 0;
        dNum = 0;
        counter = 0;
        x1 = 324;
        x2 = 266;
        y = 324;
        baseVolume = 0;
        acidVolume = 45;
        baseConcentration = 0;
        acidConcentration = 0;
        buretVolume = buretVolumeSlider.getValue();

        layout3.setBackground(Color.lightGray);
        c.setBackground(Color.lightGray);
        c.setColor(Color.black);
        c.drawLine(300, 10, 300, 215);
        c.drawLine(292, 10, 292, 215);
        c.drawLine(292, 215, 295, 225);
        c.drawLine(300, 215, 297, 225);
        c.drawLine(295, 225, 295, 228);
        c.drawLine(297, 225, 297, 228);

        c.setColor(lB);
        c.fillRect(293, 205 - (buretVolumeSlider.getValue() * 2), 6, 205 - (205 - (buretVolumeSlider.getValue() * 2)));
        c.setColor(Color.orange);
        c.fillRect(276, 205, 40, 7);
        c.setColor(Color.red);
        c.fillOval(287, 200, 17, 14);

        c.setColor(Color.black);
        for (int i = 185; i > 10; i = i - 20) {
          c.drawLine(292, i, 300, i);
        }

        c.drawString("10mL", 303, 190);
        c.drawString("30mL", 303, 150);
        c.drawString("50mL", 303, 110);
        c.drawString("70mL", 303, 70);
        c.drawString("90mL", 303, 30);

        c.drawLine(280, 240, 310, 240);
        c.drawLine(280, 240, 285, 245);
        c.drawLine(310, 240, 305, 245);
        c.drawLine(285, 245, 285, 300);
        c.drawLine(305, 245, 305, 300);
        c.drawLine(285, 300, 245, 345);
        c.drawLine(305, 300, 345, 345);
        c.drawLine(245, 345, 345, 345);

        c.setColor(lB);
        for (int i = 0; i < 50; i++) {
          c.drawLine(325 - i, 325, 343 - i, 344);
        }
        for (int i = 0; i < 50; i++) {
          c.drawLine(265 + i, 325, 247 + i, 344);
        }

        c3.drawString("M", 3, 15);
        c4.drawString("M", 3, 15);
        c5.drawString(buretVolumeSlider.getValue() + " mLs", 16, 10);

      }

      if (whatHappened.equals("d")) {
        if (buretVolume == 0) {

        } else if (baseM.getText().equals("") || acidM.getText().equals("")) {
          c.setColor(Color.black);
          c.drawString("Please fill in the concentrations", 0, 170);
          c.drawString("of the base and the acid!", 0, 185);
          c.sleep(2200);
          c.setColor(Color.lightGray);
          c.fillRect(0, 160, 250, 40);
        } else {
          dNum++;

          if (dNum == 1) {
            baseConcentration = Double.parseDouble(baseM.getText());
            acidConcentration = Double.parseDouble(acidM.getText());
            baseVolume = (acidVolume / 1000.0) * acidConcentration;
            baseVolume = (baseVolume / baseConcentration) * 1000.0;

          }
          c2.clear();

          if (dNum == 1) {
            c2.drawString(dNum + " mL", 18, 10);
          } else {
            c2.drawString(dNum + " mLs", 16, 10);
          }

          if (buretVolume > 2) {
            c.setColor(Color.lightGray);
            if (buretCount == 0) {
              c.drawLine(293, 205 - (buretVolumeSlider.getValue() * 2), 299, 205 - (buretVolumeSlider.getValue() * 2));
              c.drawLine(293, 205 - (buretVolumeSlider.getValue() * 2) + 1, 299,
                  205 - (buretVolumeSlider.getValue() * 2) + 1);
            } else {
              c.drawLine(293, 205 - (buretVolumeSlider.getValue() * 2) + buretCount, 299,
                  205 - (buretVolumeSlider.getValue() * 2) + buretCount);
              c.drawLine(293, 205 - (buretVolumeSlider.getValue() * 2) + buretCount + 1, 299,
                  205 - (buretVolumeSlider.getValue() * 2) + buretCount + 1);
            }

            buretCount = buretCount + 2;
          }

          buretVolume--;
          c5.clear();
          c5.setColor(Color.black);
          c5.drawString(buretVolume + " mLs", 16, 10);
          buretVolumeSlider.setValue(buretVolume);
          c.setColor(Color.black);
          for (int i = 185; i > 10; i = i - 20) {
            c.drawLine(292, i, 300, i);
          }

          for (int i = 0; i < 100 - counter; i++) {
            c.setColor(lB);
            c.fillOval(293, 213 + i, 5, 7);
            c.sleep(10);
            c.setColor(Color.lightGray);
            c.fillOval(293, 213 + i, 5, 7);

            c.setColor(Color.black);
            c.drawLine(292, 215, 295, 225);
            c.drawLine(300, 215, 297, 225);
            c.drawLine(295, 225, 295, 228);
            c.drawLine(297, 225, 297, 228);
            c.drawLine(280, 240, 310, 240);

          }

          if (dNum % 8 == 0) {
            c.setColor(lB);
            c.drawLine(x1, y, x2, y);
            x1--;
            x2++;
            y--;
            counter++;
          }
          if (dNum >= baseVolume) {
            c.setColor(Color.pink);
            for (int i = 0; i < 50; i++) {
              c.drawLine(325 - i, 325, 343 - i, 344);
            }
            for (int i = 0; i < 50; i++) {
              c.drawLine(265 + i, 325, 247 + i, 344);
            }
            x1 = 324;
            x2 = 266;
            y = 324;
            for (int i = 0; i < counter; i++) {
              c.drawLine(x1 - i, y - i, x2 + i, y - i);
            }

          }
        }

      }

    }
  }

}