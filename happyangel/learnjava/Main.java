package happyangel.learnjava;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static AtomicBoolean quit = new AtomicBoolean(false);

    public static void main(String[] args) {
        System.out.println("calEstimateAskeeImgCount is " + calEstimateAskeeImgCount(387));

        int a = 1;

    }

    private static String calEstimateAskeeImgCount(long imgCount)
    {
        if (imgCount > 0 && imgCount < 10) {
            return "N+";
        } else if (imgCount >= 10) {
            int nCount = 0;
            long tmpImgCount = imgCount;
            long firstEffectiveNum = 0;
            while (tmpImgCount != 0) {
                nCount++;
                firstEffectiveNum = tmpImgCount;
                tmpImgCount = tmpImgCount / 10;
            }

            String result = String.valueOf(firstEffectiveNum);
            while(nCount > 1) {
                result += "0";
                nCount--;
            }
            result += "+";
            return result;
        } else {
            return "";
        }
    }
}
