package it.michelelacorte.androidshortcuts;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;

import it.michelelacorte.androidshortcuts.util.StyleOption;
import it.michelelacorte.androidshortcuts.util.Utils;

/**
 * Created by Michele on 24/11/2016.
 */

public class Shortcuts implements Serializable{
    private final String TAG = "Shorctus";
    private static final long serialVersionUID = -29238982928391L;

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
    public void init(View layout, int optionLayoutStyle, final Activity activity, final Drawable packageImage){
        ImageView mShortcutsImage = (ImageView) layout.findViewById(R.id.shortcut_image);
        TextView mShortcutsText = (TextView) layout.findViewById(R.id.shortcut_text);
        RelativeLayout mShortcutsParent = (RelativeLayout) layout.findViewById(R.id.shortcut_parent);
        ImageView mShortcutsOptions = (ImageView) layout.findViewById(R.id.shortcut_options);

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

        if(shortcutsImage != 0) {
            if(packageImage != null) {
                int color = Utils.getDominantColor(Utils.convertDrawableToBitmap(packageImage));
                if (color != 0) {
                    Bitmap shortcutsImageBitmap = BitmapFactory.decodeResource(activity.getResources(), shortcutsImage);
                    Bitmap coloredBitmap = Utils.setColorOnBitmap(shortcutsImageBitmap, color);
                    mShortcutsImage.setImageBitmap(coloredBitmap);
                } else {
                    mShortcutsImage.setImageResource(shortcutsImage);
                }
            }else{
                mShortcutsImage.setImageResource(shortcutsImage);
            }
        }
        if(shortcutsImageBitmap != null) {
            if(packageImage != null) {
                int color = Utils.getDominantColor(Utils.convertDrawableToBitmap(packageImage));
                if (color != 0) {
                    Bitmap coloredBitmap = Utils.setColorOnBitmap(shortcutsImageBitmap, color);
                    mShortcutsImage.setImageBitmap(coloredBitmap);
                } else {
                    mShortcutsImage.setImageBitmap(shortcutsImageBitmap);
                }
            }else{
                mShortcutsImage.setImageBitmap(shortcutsImageBitmap);
            }
        }
        mShortcutsText.setText(shortcutsText);

        if(onShortcutsOptionClickListener != null) {
            mShortcutsOptions.setOnClickListener(onShortcutsOptionClickListener);
        } else if(targetClass != null && targetPackage != null) {
            mShortcutsOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(shortcutsImageBitmap != null) {
                            Utils.createShortcutsOnLauncher(activity, shortcutsImageBitmap, shortcutsText, targetClass, targetPackage, packageImage);
                        }else{
                            Drawable drawable = ContextCompat.getDrawable(activity.getApplicationContext(), shortcutsImage);
                            Bitmap toBitmap = Utils.convertDrawableToBitmap(drawable);
                            Utils.createShortcutsOnLauncher(activity, toBitmap, shortcutsText, targetClass, targetPackage, packageImage);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        switch (optionLayoutStyle){
            case StyleOption.LINE_LAYOUT:
                    mShortcutsOptions.setBackgroundResource(R.drawable.shortcuts_options);
                break;
            case StyleOption.CIRCLE_LAYOUT:
                    mShortcutsOptions.setBackgroundResource(R.drawable.shortcuts_options_2);
                break;
            case StyleOption.CIRCLE_LAYOUT_ALTERNATIVE:
                mShortcutsOptions.setBackgroundResource(R.drawable.shortcuts_options_3);
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
