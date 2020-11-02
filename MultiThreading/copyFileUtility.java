package com.example.io.utils;

import java.io.*;

public class copyFileUtility {

    public static void copy(InputStream source, OutputStream destination) throws IOException {
        int i;
        while((i=source.read())!=-1){
            destination.write(i);

        }
    }


    public static void copyFile(String sourceFile, String destinationFile) throws IOException {

       System.out.println("Current thead->"+Thread.currentThread().getName());

        FileInputStream fin=new FileInputStream(sourceFile);
        FileOutputStream fout= new FileOutputStream(destinationFile);

        copy(fin,fout);

        fin.close();
        fout.close();

    }


}
