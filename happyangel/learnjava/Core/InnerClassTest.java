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
        TalkingClock clock = new TalkingClock();
        clock.start(1000, true);

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/*
    A clock that prints the time in regular intervals
 */
class TalkingClock {

    public void start(int interval, boolean beep) {

        /*
            this is the form of anonymous inner class,
            the syntax is like:

            SuperType(Constructor parameters) {
                inner class methods

            }

            inner class can only implements interfaces or extend classes

            the reason to use inner class is it can access outer class's variables including private ones.
            Also it can be hidden from other classes.
         */
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date now = new Date();
                System.out.println("At the tone, the time is " + now);
                // note there, the inner class TimePrinter CAN ACCESS the PRIVATE variable beep of the outer class here
                // you can also use TalkingClock.this.beep instead of beep here
                if (beep) {
                    System.out.println("Beeping...");
                }
            }
        };
        Timer t = new Timer(interval, listener);
        t.start();
    }

    /*
        this is the form of normal inner class
     */

//    public class TimePrinter implements ActionListener {
//        public void actionPerformed(ActionEvent event) {
//            Date now = new Date();
//            System.out.println("At the tone, the time is " + now);
//            // note there, the inner class TimePrinter CAN ACCESS the PRIVATE variable beep of the outer class here
//            // you can also use TalkingClock.this.beep instead of beep here
//            if (beep) {
//                System.out.println("Beeping...");
//            }
//        }
//    }
}
