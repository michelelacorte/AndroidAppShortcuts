package it.michelelacorte.androidshortcuts;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Michele on 24/11/2016.
 */

public class Shortcuts implements Parcelable {
    private final String TAG = "Shorctus";

    private ImageView mShortcutsImage;
    private TextView mShortcutsText;
    private RelativeLayout mShortcutsParent;
    private ImageView mShortcutsOptions;

    private String shortcutsText;
    private int shortcutsImage;


    private View.OnClickListener onShortcutsClickListener;
    private View.OnClickListener onShortcutsOptionClickListener;
    private IRemoteShortcutClickListener onIRemoteShortcutsClickListener;

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

    public Shortcuts(int shortcutsImage, String shortcutsText, final IRemoteShortcutClickListener onIRemoteShortcutsClickListener){
        this.shortcutsImage = shortcutsImage;
        if(shortcutsText.toCharArray().length > MAX_CHAR_SHORTCUTS){
            this.shortcutsText = "NULL";
            Log.e(TAG, "Impossible to have string > 16 chars, setted to NULL string!");
        }else{
            this.shortcutsText = shortcutsText;
        }
        if(onIRemoteShortcutsClickListener != null) {
            this.onIRemoteShortcutsClickListener = onIRemoteShortcutsClickListener;
        }else{
            Log.e(TAG, "OnClickListener must be different from NULL");
        }
        this.onShortcutsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onIRemoteShortcutsClickListener.onShortcutsClickListener();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        this.onShortcutsOptionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onIRemoteShortcutsClickListener.onShortcutsOptionClickListener();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
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
    public void init(View layout, int optionLayoutStyle){
        mShortcutsImage = (ImageView) layout.findViewById(R.id.shortcut_image);
        mShortcutsText = (TextView) layout.findViewById(R.id.shortcut_text);
        mShortcutsParent = (RelativeLayout) layout.findViewById(R.id.shortcut_parent);
        mShortcutsOptions = (ImageView) layout.findViewById(R.id.shortcut_options);

        if(onShortcutsClickListener != null)
            mShortcutsParent.setOnClickListener(onShortcutsClickListener);

        mShortcutsImage.setImageResource(shortcutsImage);
        mShortcutsText.setText(shortcutsText);

        if(onShortcutsOptionClickListener != null)
            mShortcutsOptions.setOnClickListener(onShortcutsOptionClickListener);

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

    public IRemoteShortcutClickListener getOnIRemoteShortcutsClickListener() {
        return onIRemoteShortcutsClickListener;
    }

    public View.OnClickListener getOnShortcutsClickListener() {
        return onShortcutsClickListener;
    }

    public String getShortcutsText() {
        return shortcutsText;
    }

    public int getShortcutsImage() {
        return shortcutsImage;
    }


    private Shortcuts(Parcel in) {
        shortcutsText = in.readString();
        shortcutsImage = in.readInt();
        onShortcutsClickListener = (View.OnClickListener) in.readValue(View.OnClickListener.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(shortcutsText);
        out.writeInt(shortcutsImage);
        out.writeValue(onShortcutsClickListener);
    }

    public static final Parcelable.Creator<Shortcuts> CREATOR
            = new Parcelable.Creator<Shortcuts>() {
        public Shortcuts createFromParcel(Parcel in) {
            return new Shortcuts(in);
        }

        public Shortcuts[] newArray(int size) {
            return new Shortcuts[size];
        }
    };

}
