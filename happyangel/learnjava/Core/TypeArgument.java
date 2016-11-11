package happyangel.learnjava.Core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by xionglei on 16-11-7.
 *
 * from Unbounded Wildcards in Java 8 Specification
 */
public class TypeArgument {
    // the use of an unbounded wildcard allows any kind of collection to be passed as an argument
    static void printCollection(Collection<?> c) {
        for (Object o : c) {
            System.out.println(o);
        }
    }

    // bounded wildcards
    static boolean addAll(Collection<? extends Long> c) {
        for (Long l : c) {
            System.out.println(l);
        }

        return true;
    }

    public static void main(String[] args) {
        Collection<String> cs = new ArrayList<>();
        cs.add("hello");
        cs.add("world");
        printCollection(cs);

        // addAll(cs); compile error of type argement
    }
}
