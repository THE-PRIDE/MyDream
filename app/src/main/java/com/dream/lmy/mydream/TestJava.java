package com.dream.lmy.mydream;

public class TestJava {

    public static void main(String[] arg) {
        int a = getCompressScale(300, 400, 101, 200);
        System.out.println(a);

        int b = getCompressScale(300, 400, 0, 200);
        System.out.println(b);


        int c = getCompressScale(300, 400, 100, 0);
        System.out.println(c);


        int d = getCompressScale(0, 0, 100, 0);
        System.out.println(d);

        int e = getCompressScale(1080, 1920, 300, 400);
        System.out.println(e);

        int f = getCompressScale(9, 8, 7, 7);
        System.out.println(f);

    }

    public static int getCompressScale(int width, int height, int compWidth, int compHeight) {

        float widthScale = 0;
        float heightScale = 0;

        if (compWidth != 0) {
            widthScale = width / compWidth;
        }
        if (compHeight != 0) {
            heightScale = height / compHeight;
        }

        System.out.print("widthScale:" + widthScale + "----");
        System.out.print("heightScale:" + heightScale + "----");
        if (widthScale > 0 && heightScale == 0) {
            return (int) widthScale;
        }

        if (widthScale == 0 && heightScale > 0) {
            return (int) heightScale;
        }

        if (widthScale == 0 && heightScale == 0) {
            return 1;
        }
        if (compWidth > 0 && widthScale < heightScale && width > compWidth) {
            return (int) widthScale;
        } else if (compHeight > 0 && height > compHeight) {
            return (int) heightScale;
        } else {
            return 1;
        }
    }
}
