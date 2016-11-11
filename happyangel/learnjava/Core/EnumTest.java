package happyangel.learnjava.Core;

import java.util.Scanner;

/**
 * Created by xionglei on 16-11-11.
 *
 * test the ability of java enum
 *
 * from Cay Horstmann's <Core Java>
 */
public class EnumTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a size: (SMALL, MEDIUM, LARGE, EXTRA_LARGE) ");
        String input = in.next().toUpperCase();
        Size size = Enum.valueOf(Size.class, input);
        System.out.println("size=" + size);
        System.out.println("abbreviation=" + size.getAbbreviation());
        if (size == Size.EXTRA_LARGE) {
            System.out.println("Good job -- you paid atten to the _.");
        }
    }

    // you can if you like, add constructors, methods, and fields to an enumerated type
    enum Size {
        SMALL("S"),
        MEDIUM("M"),
        LARGE("L"),
        EXTRA_LARGE("XL");

        private String abbreviation;

        public String getAbbreviation() {
            return abbreviation;
        }

        private Size(String abbreviation) {
            this.abbreviation = abbreviation;
        }

    }
}
