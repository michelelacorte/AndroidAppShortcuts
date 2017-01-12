package it.michelelacorte.androidshortcuts;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;

import it.michelelacorte.androidshortcuts.util.Utils;

/**
 * Created by Michele on 24/11/2016.
 */

public class Shortcuts implements Serializable{
    private final String TAG = "Shorctus";
    private static final long serialVersionUID = -29238982928391L;

    private ImageView mShortcutsImage;
    private TextView mShortcutsText;
    private RelativeLayout mShortcutsParent;
    private ImageView mShortcutsOptions;

    private String shortcutsText;
    private int shortcutsImage;
    private Bitmap shortcutsImageBitmap;
    private String targetClass;
    private String targetPackage;



    private View.OnClickListener onShortcutsClickListener;
    private View.OnClickListener onShortcutsOptionClickListener;

    private final int MAX_CHAR_SHORTCUTS = 16;

    /**
     * Public constructor for create custom shortcuts
     * @param shortcutsImage int
     * @param shortcutsText String (Max lenght 16 char)
     * @param onShortcutsClickListener View.OnClickListener
     */
    public Shortcuts(int shortcutsImage, String shortcutsText, View.OnClickListener onShortcutsClickListener){
        this.shortcutsImage = shortcutsImage;
        if(shortcutsText.toCharArray().length > MAX_CHAR_SHORTCUTS){
            this.shortcutsText = "NULL";
            Log.e(TAG, "Impossible to have string > 16 chars, setted to NULL string!");
        }else{
            this.shortcutsText = shortcutsText;
        }
        if(onShortcutsClickListener != null) {
            this.onShortcutsClickListener = onShortcutsClickListener;
        }else{
            Log.e(TAG, "OnClickListener must be different from NULL");
        }
    }


    /**
     * Public constructor for create custom shortcuts
     * @param shortcutsImage int
     * @param shortcutsText String (Max lenght 16 char)
     */
    public Shortcuts(int shortcutsImage, String shortcutsText){
        this.shortcutsImage = shortcutsImage;
        if(shortcutsText.toCharArray().length > MAX_CHAR_SHORTCUTS){
            this.shortcutsText = "NULL";
            Log.e(TAG, "Impossible to have string > 16 chars, setted to NULL string!");
        }else {
            this.shortcutsText = shortcutsText;
        }
    }


    /**
     * Public constructor for create custom shortcuts
     * @param shortcutsImage Bitmap
     * @param shortcutsText String
     */
    public Shortcuts(Bitmap shortcutsImage, String shortcutsText){
        this.shortcutsImageBitmap = shortcutsImage;
        this.shortcutsImage = 0;
        if(shortcutsText.toCharArray().length > MAX_CHAR_SHORTCUTS){
            this.shortcutsText = "NULL";
            Log.e(TAG, "Impossible to have string > 16 chars, setted to NULL string!");
        }else {
            this.shortcutsText = shortcutsText;
        }
    }

    /**
     * Public constructor for create custom shortcuts, only for remote use.
     * @param shortcutsImage Bitmap
     * @param shortcutsText String
     * @param targetClass String
     * @param targetPackage String
     */
    public Shortcuts(int shortcutsImage, String shortcutsText, String targetClass, String targetPackage){
        this.shortcutsImage = shortcutsImage;
        if(shortcutsText.toCharArray().length > MAX_CHAR_SHORTCUTS){
            this.shortcutsText = "NULL";
            Log.e(TAG, "Impossible to have string > 16 chars, setted to NULL string!");
        }else{
            this.shortcutsText = shortcutsText;
        }
        this.targetClass= targetClass;
        this.targetPackage = targetPackage;
    }


    /**
     * Public constructor for create custom shortcuts, only for remote use.
     * @param shortcutsImage Bitmap
     * @param shortcutsText String
     * @param targetClass String
     * @param targetPackage String
     */
    public Shortcuts(Bitmap shortcutsImage, String shortcutsText, String targetClass, String targetPackage){
        this.shortcutsImageBitmap = shortcutsImage;
        this.shortcutsImage = 0;
        if(shortcutsText.toCharArray().length > MAX_CHAR_SHORTCUTS){
            this.shortcutsText = "NULL";
            Log.e(TAG, "Impossible to have string > 16 chars, setted to NULL string!");
        }else{
            this.shortcutsText = shortcutsText;
        }
        this.targetClass = targetClass;
        this.targetPackage = targetPackage;
    }


    /**
     * Public constructor for create custom shortcuts
     * @param shortcutsImage Bitmap
     * @param shortcutsText String
     * @param onShortcutsClickListener View.OnClickListener
     * @param onShortcutsOptionClickListener View.OnClickListener
     */
    public Shortcuts(int shortcutsImage, String shortcutsText, View.OnClickListener onShortcutsClickListener, View.OnClickListener onShortcutsOptionClickListener){
        this.shortcutsImage = shortcutsImage;
        if(shortcutsText.toCharArray().length > MAX_CHAR_SHORTCUTS){
            this.shortcutsText = "NULL";
            Log.e(TAG, "Impossible to have string > 16 chars, setted to NULL string!");
        }else{
            this.shortcutsText = shortcutsText;
        }
        if(onShortcutsClickListener != null) {
            this.onShortcutsClickListener = onShortcutsClickListener;
        }else{
            Log.e(TAG, "OnClickListener must be different from NULL");
        }
        if(onShortcutsOptionClickListener != null) {
            this.onShortcutsOptionClickListener = onShortcutsOptionClickListener;
        }else{
            Log.e(TAG, "OnClickListener must be different from NULL");
        }
    }


    /**
     * Public method to initializate shortcuts, do not use this!
     * @param layout View
     */
    public void init(View layout, int optionLayoutStyle, final Activity activity){
        mShortcutsImage = (ImageView) layout.findViewById(R.id.shortcut_image);
        mShortcutsText = (TextView) layout.findViewById(R.id.shortcut_text);
        mShortcutsParent = (RelativeLayout) layout.findViewById(R.id.shortcut_parent);
        mShortcutsOptions = (ImageView) layout.findViewById(R.id.shortcut_options);

        if(onShortcutsClickListener != null)
            mShortcutsParent.setOnClickListener(onShortcutsClickListener);

        if(targetPackage != null && targetClass != null) {
            mShortcutsParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(targetPackage, targetClass));
                    activity.startActivity(intent);
                }
            });
        }

        if(shortcutsImage != 0)
        mShortcutsImage.setImageResource(shortcutsImage);
        if(shortcutsImageBitmap != null)
        mShortcutsImage.setImageBitmap(shortcutsImageBitmap);

        mShortcutsText.setText(shortcutsText);

        if(onShortcutsOptionClickListener != null) {
            mShortcutsOptions.setOnClickListener(onShortcutsOptionClickListener);
        } else {
            mShortcutsOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Utils.createShortcutsOnLauncher(activity, shortcutsImageBitmap, shortcutsText, targetClass, targetPackage);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        switch (optionLayoutStyle){
            case 0:
                    mShortcutsOptions.setBackgroundResource(R.drawable.shortcuts_options);
                break;
            case 1:
                    mShortcutsOptions.setBackgroundResource(R.drawable.shortcuts_options_2);
                break;
            default:
                    mShortcutsOptions.setBackgroundResource(R.drawable.shortcuts_options);
                Log.d(TAG, "Option invalid, restore default!");
                break;
        }
        Log.d(TAG, "Init completed!");
    }

    /**
     * Get target class string
     * @return String
     */
    public String getTargetClass() {
        return targetClass;
    }

    /**
     * Get target package string
     * @return String
     */
    public String getTargetPackage() {
        return targetPackage;
    }

    /**
     * Get listener of shortcuts
     * @return View.OnClickListener
     */
    public View.OnClickListener getOnShortcutsClickListener() {
        return onShortcutsClickListener;
    }

    /**
     * Get shortcuts text
     * @return String
     */
    public String getShortcutsText() {
        return shortcutsText;
    }

    /**
     * Get shortcuts image
     * @return Int
     */
    public int getShortcutsImage() {
        return shortcutsImage;
    }

    /**
     * Get shortcuts image
     * @return Bitmap
     */
    public Bitmap getShortcutsImageBitmap() {
        return shortcutsImageBitmap;
    }

    /**
     * Get listener of option menù (right menù of shortcuts)
     * @return View.OnClickListener
     */
    public View.OnClickListener getOnShortcutsOptionClickListener() {
        return onShortcutsOptionClickListener;
    }

}
