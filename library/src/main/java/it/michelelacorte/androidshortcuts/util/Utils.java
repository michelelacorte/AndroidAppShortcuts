package it.michelelacorte.androidshortcuts.util;

import android.app.Activity;
import android.content.Context;
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
}
