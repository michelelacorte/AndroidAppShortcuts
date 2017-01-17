package it.michelelacorte.androidshortcuts.util;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.AdapterView;
import android.widget.GridView;

import it.michelelacorte.androidshortcuts.R;

/**
 * Created by Michele on 09/12/2016.
 */

public class Utils {
    private static final String TAG = "Utils";

    /**
     * Get grid size
     * @param gridView AdapterView
     * @return GridSize
     */
    public static GridSize getGridSize(AdapterView gridView){
        int nColumn = ((GridView) gridView).getNumColumns();
        double nRow = Math.ceil((double)gridView.getCount()/(double)((GridView) gridView).getNumColumns());
        Log.d(TAG, "Number of Row: " + (int)nRow + "\nNumber of Column: " + nColumn);
        return new GridSize(nColumn, (int)nRow);
    }

    /**
     * Get toolbar height
     * @param activity Activity
     * @return int
     */
    public static int getToolbarHeight(Activity activity){
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            Log.d(TAG, "Toolbar found, height: " + String.valueOf(TypedValue.complexToDimensionPixelSize(tv.data,activity.getApplicationContext().getResources().getDisplayMetrics())));
            return TypedValue.complexToDimensionPixelSize(tv.data,activity.getApplicationContext().getResources().getDisplayMetrics());
        }else{
            Log.d(TAG, "Toolbar not found, height: 0");
            return 0;
        }
    }

    /**
     * Create shortcuts on launcher based on params
     * @param activity Activity
     * @param shortcutsImage Bitmap
     * @param shortcutsText String
     * @param className String
     * @param packageName String
     * @throws ClassNotFoundException
     */
    public static void createShortcutsOnLauncher(Activity activity, Bitmap shortcutsImage, String shortcutsText, String className, String packageName) throws ClassNotFoundException {

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.INSTALL_SHORTCUT);
        if (result != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.INSTALL_SHORTCUT)) {
                Log.d(TAG, "Install Shortcuts permission allows us to create shortcuts on launcher. Please allow this permission in App Settings.");
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.INSTALL_SHORTCUT}, 111);
                Log.d(TAG, "Install Shortcuts permission allows us to create shortcuts on launcher.");
            }
        }

        Intent shortcutIntent = new Intent(activity.getApplicationContext(), activity.getClass());
        shortcutIntent.setComponent(new ComponentName(
                packageName, className.replaceAll(packageName, "")));
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutsText);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, shortcutsImage);
        addIntent.putExtra("duplicate", false);
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        activity.getApplicationContext().sendBroadcast(addIntent);
    }

    /**
     * Convert drawable to bitmap
     * @param drawable Drawable
     * @return Bitmap
     */
    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
