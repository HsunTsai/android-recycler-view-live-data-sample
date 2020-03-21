package com.hsun.myapplication.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DeepCopy {
    @NonNull
    private static Object cloneObject(Object object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            return objectInputStream.readObject();
        } catch (Exception e) {
            Log.e(DeepCopy.class.getSimpleName(), "Model has not been serialized !");
            return new Object();
        }
    }

    @NonNull
    public static List<?> cloneArray(List<?> objects) {
        ArrayList<Object> copyList = new ArrayList<>();
        for (Object object : objects) {
            copyList.add(cloneObject(object));
        }
        return copyList;
    }
}
