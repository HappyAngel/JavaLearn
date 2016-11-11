package happyangel.learnjava.Core;

/**
 * Created by xionglei on 16-11-7.
 *
 * test Java 8 Specification's example: type variable
 */
public class TypeVariable {
    <T extends C & I> void test(T t) {
        t.mI();
        t.mCPublic();
        t.mCProtected();
        t.mCPackage();
        //t.mCPrivate(); // compile-error
    }


}

class C {
    public void mCPublic() {}
    protected void mCProtected() {}
              void mCPackage() {}
    private void mCPrivate() {}
}

interface I {
    void mI();
}

class CT extends C implements I {
    public void mI() {};
}