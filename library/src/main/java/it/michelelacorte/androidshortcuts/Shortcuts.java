package it.michelelacorte.androidshortcuts;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Michele on 24/11/2016.
 */

public class Shortcuts {
    private final String TAG = "Shorctus";

    private ImageView mShortcutsImage;
    private TextView mShortcutsText;
    private RelativeLayout mShortcutsParent;

    private String shortcutsText;
    private int shortcutsImage;
    private View.OnClickListener onShortcutsClickListener;

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
        this.onShortcutsClickListener = onShortcutsClickListener;
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
     * Public method to initializate shortcuts, do not use this!
     * @param layout View
     */
    public void init(View layout){
        mShortcutsImage = (ImageView) layout.findViewById(R.id.shortcut_image);
        mShortcutsText = (TextView) layout.findViewById(R.id.shortcut_text);
        mShortcutsParent = (RelativeLayout) layout.findViewById(R.id.shortcut_prent);

        if(onShortcutsClickListener != null)
        mShortcutsParent.setOnClickListener(onShortcutsClickListener);

        mShortcutsImage.setImageResource(shortcutsImage);
        mShortcutsText.setText(shortcutsText);
    }


}
