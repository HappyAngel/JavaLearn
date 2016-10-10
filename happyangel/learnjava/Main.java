package happyangel.learnjava;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static AtomicBoolean quit = new AtomicBoolean(false);

    public static void main(String[] args) {
        Test tt1 = Test.A;
        Test tt2 = Test.A;


        System.out.println(tt1 == Test.B);



    }

    enum Test {
        A,
        B,
    }
}
