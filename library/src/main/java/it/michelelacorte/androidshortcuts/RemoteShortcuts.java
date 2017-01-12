package it.michelelacorte.androidshortcuts;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by Michele on 10/01/2017.
 */

/**
 * Remote Shortcuts class provide method to serialize and deserialize shortcuts for save/get shortcuts from different apps
 */
public class RemoteShortcuts {
    private static final String TAG = "RemoteShorctus";

    /**
     * Save shortcuts on file
     * @param activity Activity
     * @param packageName String
     * @param listOfShortcuts ArrayList<Shortcuts>
     */
    public static void saveRemoteShortcuts(Activity activity, String packageName, ArrayList<Shortcuts> listOfShortcuts){
        String fileName = packageName + "/shortcut.shc";
        ObjectOutput out = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            checkPermission(activity);
        }

        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/Shortcuts/"+fileName);
            file.getParentFile().mkdirs();
            file.createNewFile();
            Log.e("TAG", "" +packageName+fileName);
            out = new ObjectOutputStream(new FileOutputStream(file, false));
            for(Shortcuts shortcuts : listOfShortcuts){
                if(shortcuts.getShortcutsText() != null) {
                    out.writeUTF(shortcuts.getShortcutsText());
                }if(shortcuts.getShortcutsImage() != 0) {
                    Bitmap image = BitmapFactory.decodeResource(activity.getResources(), shortcuts.getShortcutsImage());
                    final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    final byte[] imageByteArray = stream.toByteArray();
                    out.writeInt(imageByteArray.length);
                    out.write(imageByteArray);
                }if(shortcuts.getTargetPackage() != null && shortcuts.getTargetClass() != null){
                    out.writeUTF(shortcuts.getTargetPackage());
                    out.writeUTF(shortcuts.getTargetClass());
                }else{
                    out.writeUTF(activity.getPackageName());
                    out.writeUTF(activity.getPackageName()+"."+activity.getLocalClassName());
                }
            }
            out.close();
            Log.d(TAG, "Shortcuts saved into: " + Environment.getExternalStorageDirectory() + "/Shortcuts/"+fileName);
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());

        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

    }

    /**
     * Get shortcuts from file
     * @param activity Activity
     * @param packageName String
     * @return ArrayList<Shotrcuts>
     */
    public static ArrayList<Shortcuts> getRemoteShortcuts(Activity activity, String packageName){
        String fileName = packageName + "/shortcut.shc";
        ObjectInputStream input;
        ArrayList<Shortcuts> listOfShortcuts = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            checkPermission(activity);
        }

        try {
            input = new ObjectInputStream(new FileInputStream(Environment.getExternalStorageDirectory() + "/Shortcuts/"+fileName));
            try{
                while(true) {
                    String shortcutsText = input.readUTF();
                    final int length = input.readInt();
                    final byte[] imageByteArray = new byte[length];
                    input.readFully(imageByteArray);
                    Bitmap shortcutsImage = BitmapFactory.decodeByteArray(imageByteArray, 0, length);
                    String targetPackage = input.readUTF();
                    String targetClass = input.readUTF();
                    listOfShortcuts.add(new Shortcuts(shortcutsImage, shortcutsText, targetClass, targetPackage));
                }
            }catch (EOFException e){}
            input.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Shortcuts getted from: " + Environment.getExternalStorageDirectory() + "/Shortcuts/"+fileName);
        return listOfShortcuts;
    }

    /**
     * Check if user had permission
     * @param activity Activity
     */
    private static void checkPermission(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            requestPermission(activity);
        }
    }

    /**
     * Make request permission
     * @param activity Activity
     */
    private static void requestPermission(Activity activity) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.d(TAG, "Write External Storage permission allows us to do store shortcuts data. Please allow this permission in App Settings.");
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
            Log.d(TAG, "Write External Storage permission allows us to do store shortcuts data.");
        }
    }
}
