package happyangel.learnjava;


import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static AtomicBoolean quit = new AtomicBoolean(false);

    public static void main(String[] args) {
        A a = new A();

    }
}

    class A {
        int a = 10;

        public A() {
            access();
        }


        public void access() {
            System.out.println(a);
        }
    }

    class B extends A {
        int a = 11;
        public B(){

        }

        @Override
        public void access() {
            System.out.println(a);
        }
    }

