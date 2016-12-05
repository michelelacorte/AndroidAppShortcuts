package it.michelelacorte.androidshortcuts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import it.michelelacorte.androidshortcuts.util.ResizeAnimation;


/**
 * Created by Michele on 24/11/2016.
 */

public class ShortcutsCreation {
    private static final int MAX_NUMBER_OF_SHORTCUTS = 10;

    private static RelativeLayout[] layout = new RelativeLayout[MAX_NUMBER_OF_SHORTCUTS];
    private static RelativeLayout triangle;
    private Context context;
    private AdapterView gridView;
    private Activity activity;

    private int positionInGrid = 0;
    private ViewGroup masterLayout;

    private int maxXScreen;
    private int maxYScreen;
    private float displayDensity;

    private int toolbarHeight;
    private final int DIM_WIDTH = 840;
    private final int DIM_HEIGHT = 200;

    /**
     * Public constructor for adapt shortcuts to layout
     * @param activity Activity
     * @param masterLayout ViewGroup
     * @param gridView AdapterView
     */
    public ShortcutsCreation(Activity activity, ViewGroup masterLayout, AdapterView gridView){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.gridView = gridView;
        this.masterLayout = masterLayout;
    }


    /**
     * Public method to create shortcuts
     * @param currentXPosition int
     * @param currentYPosition int
     * @param shortcuts Shortcuts...
     */
    public void createShortcuts(int currentXPosition, int currentYPosition, Shortcuts... shortcuts){
        getScreenDimension();
        positionInGrid = getPositionInGrid(currentXPosition, currentYPosition, gridView);

        if(layout != null || triangle != null)
        clearAllLayout();
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DIM_WIDTH, DIM_HEIGHT);
        RelativeLayout.LayoutParams paramsTriangle = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //int mIconHeight = ((GridView) gridView).getColumnWidth();
        int mIconHeight = Math.round(displayDensity * 96);
        int mIconWidth = maxXScreen / ((GridView) gridView).getNumColumns();
        int dim = (positionInGrid) * mIconWidth;
        int layoutHeightTotal = DIM_HEIGHT*shortcuts.length+20;

        triangle = (RelativeLayout) inflater.inflate(R.layout.shortcuts_triangle, null, false);

        for(int i = 0; i < shortcuts.length; i++){
            layout[i] = (RelativeLayout) inflater.inflate(R.layout.shortcuts, null, false);
            shortcuts[i].init(layout[i]);
            if((dim+DIM_WIDTH) > maxXScreen){
                //Destra
                ResizeAnimation scaleLayout = new ResizeAnimation(layout[i], 0, DIM_HEIGHT, DIM_WIDTH, DIM_HEIGHT);
                ResizeAnimation scaleTriangle = new ResizeAnimation(triangle, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                layout[i].setX(dim-DIM_WIDTH+(mIconWidth)-mIconWidth/4);
                triangle.setX((float) (dim+mIconWidth-mIconWidth/1.5));
                triangle.setRotation(180);
                scaleLayout.setDuration(300);
                scaleTriangle.setDuration(300);
                layout[i].startAnimation(scaleLayout);
                triangle.startAnimation(scaleTriangle);
            }else{
                //Sinistra
                ResizeAnimation scaleLayout = new ResizeAnimation(layout[i], 0, DIM_HEIGHT, DIM_WIDTH, DIM_HEIGHT);
                ResizeAnimation scaleTriangle = new ResizeAnimation(triangle, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                layout[i].setX(dim+mIconWidth/4);
                triangle.setX((float) (dim+mIconWidth/2));
                triangle.setRotation(180);
                scaleLayout.setDuration(300);
                scaleTriangle.setDuration(300);
                layout[i].startAnimation(scaleLayout);
                triangle.startAnimation(scaleTriangle);

            }

            if((toolbarHeight = getToolbarHeight(context)) > 0) {
                int maxYScreenWithToolbar = maxYScreen-toolbarHeight*2;
                positionInGrid = ((GridView) gridView).pointToPosition((int) currentXPosition, (int) currentYPosition);
                positionInGrid /= 4;
                mIconHeight = Math.round(displayDensity * 96)*positionInGrid+1;
                if (mIconHeight + layoutHeightTotal > maxYScreenWithToolbar) {
                    //Alto
                    if(i >= 1){
                        layout[i].setY(+layoutHeightTotal+mIconHeight/5-220+toolbarHeight*2);
                    }else {
                        layout[i].setY(+layoutHeightTotal+mIconHeight/5+toolbarHeight*2);
                    }
                    triangle.setY(+layoutHeightTotal+mIconHeight/5+toolbarHeight*2+160);
                    triangle.setRotation(0);
                } else {
                    //Basso
                    positionInGrid = ((GridView) gridView).pointToPosition((int) currentXPosition, (int) currentYPosition);
                    positionInGrid /= 4;
                    mIconHeight = Math.round(displayDensity * 96)*positionInGrid+1;
                    if(i >= 1) {
                        layout[i].setY(+mIconHeight*3/4+layoutHeightTotal+toolbarHeight/2-220);
                    }else{
                        layout[i].setY(+mIconHeight*3/4+layoutHeightTotal+toolbarHeight/2);
                    }
                    triangle.setY((float) (+mIconHeight*3/4+layoutHeightTotal*i-toolbarHeight+80));
                }
            }else{
                //TODO: Layout without Toolbar
            }
            masterLayout.addView(layout[i], params);
        }
        masterLayout.addView(triangle, paramsTriangle);
    }

    /**
     * Clear all shortcuts layout
     */
    public void clearAllLayout() {
        if(layout != null) {
            for (int i = 0; i < layout.length; i++) {
                if (layout[i] != null) {
                    if (((ViewGroup) layout[i].getParent()) != null)
                        ((ViewGroup) layout[i].getParent()).removeView(layout[i]);
                }
            }
        }
        if(triangle != null) {
            if (((ViewGroup) triangle.getParent()) != null)
                ((ViewGroup) triangle.getParent()).removeView(triangle);
        }
    }

    /**
     * Get toolbar height
     * @param context Context
     * @return int
     */
    private int getToolbarHeight(Context context){
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,context.getResources().getDisplayMetrics());
        }else{
            return 0;
        }
    }

    /**
     * Get screen dimension
     */
    private void getScreenDimension(){
        Display mdisp = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = activity.getApplicationContext().getResources().getDisplayMetrics();
        displayDensity = displayMetrics.density;
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxXScreen = mdispSize.x;
        maxYScreen = mdispSize.y;
    }

    /**
     * Get position in grid
     * @param currentXPosition int
     * @param currentYPosition int
     * @param gridView AdapterView
     * @return int
     */
    private int getPositionInGrid(int currentXPosition, int currentYPosition, AdapterView gridView){
        int positionInGrid = 0;
        if(gridView != null) {
            positionInGrid = ((GridView) gridView).pointToPosition(currentXPosition, currentYPosition);
            positionInGrid -= ((GridView) gridView).getNumColumns();
            positionInGrid %= ((GridView) gridView).getNumColumns();
            if(positionInGrid < 0){
                positionInGrid = ((GridView) gridView).pointToPosition(currentXPosition, currentYPosition);
            }
        }
        return positionInGrid;
    }
}
