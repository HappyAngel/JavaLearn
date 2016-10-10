package happyangel.learnjava.Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

/**
 * Created by xionglei on 16-10-10.
 *
 * from Cay Horstmann's <Core Java>
 */
public class InnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000, true);
        clock.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/*
    A clock that prints the time in regular intervals
 */
class TalkingClock {
    private int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    public void start() {
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }

    public class TimePrinter implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Date now = new Date();
            System.out.println("At the tone, the time is " + now);
            // note there, the inner class TimePrinter CAN ACCESS the PRIVATE variable beep of the outer class here
            // you can also use TalkingClock.this.beep instead of beep here
            if (beep) {
                System.out.println("Beeping...");
            }
        }
    }
}
