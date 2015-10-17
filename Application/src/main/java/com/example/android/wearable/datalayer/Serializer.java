/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.android.wearable.datalayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Iaroslav
 * WARNING: (DE)SERIALIZATION IS PERFORMED IN MEMORY FOR BETTER PERFORMANCE
 */
public class Serializer {



    public static void SerializeToFile(Object obj, File file) throws IOException {

        FileOutputStream fileOut;

        if (!file.exists()) {
            file.createNewFile();
        }

        fileOut = new FileOutputStream(file);
        byte [] data = SerializeToBytes(obj);

        fileOut.write(data);
        fileOut.close();

    }

    public static Object DeserializeFromFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {

        FileInputStream fileIn;

        fileIn = new FileInputStream(file);
        byte [] data = InputStreamToByte(fileIn);
        fileIn.close();

        return DeserializeFromBytes(data);
    }

    public static byte[] SerializeToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    public static Object DeserializeFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }

    public static byte [] InputStreamToByte(InputStream is) throws IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();

    }

}