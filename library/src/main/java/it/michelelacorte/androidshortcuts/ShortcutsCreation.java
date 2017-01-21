package it.michelelacorte.androidshortcuts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import java.util.List;

import it.michelelacorte.androidshortcuts.util.GridSize;
import it.michelelacorte.androidshortcuts.util.Utils;

/**
 * Created by Michele on 24/11/2016.
 */

public class ShortcutsCreation {
    private final String TAG = "ShorctusCreation";
    private static final int MAX_NUMBER_OF_SHORTCUTS = 5;

    private static RelativeLayout[] layout = new RelativeLayout[MAX_NUMBER_OF_SHORTCUTS];
    private static RelativeLayout triangle;
    private Context context;
    private AdapterView gridView;
    private Activity activity;
    public static Drawable packageImage;

    private int positionInGrid = 0;
    private ViewGroup masterLayout;

    private int maxXScreen;
    private int maxYScreen;
    private float displayDensity;

    private int toolbarHeight;
    private int DIM_WIDTH = 840;
    private int DIM_HEIGHT = 200;

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

    public ShortcutsCreation(Activity activity, ViewGroup masterLayout, GridView gridView){
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
    public void createShortcuts(int currentXPosition, int currentYPosition, int rowHeight, int optionLayoutStyle, Shortcuts... shortcuts){
        if(shortcuts.length > MAX_NUMBER_OF_SHORTCUTS){
            Log.e(TAG, "Invalid Shortcuts number, max value is " + String.valueOf(MAX_NUMBER_OF_SHORTCUTS) + "!");
            return;
        }
        if(rowHeight < 0){
            Log.e(TAG, "Invalid Row Height, it must be greater than 0");
            return;
        }
        if(shortcuts.length == 0){
            Log.e(TAG, "Shortcuts must be at least one!");
            return;
        }

        //Get grid size
        GridSize gridSize = Utils.getGridSize(gridView);
        //Create shortcuts based on grid size
        createShortcutsBasedOnGridSize(currentXPosition, currentYPosition, rowHeight, gridSize, optionLayoutStyle, shortcuts);

        Log.d(TAG, "Shortcuts created!");
    }

    public void createShortcuts(int currentXPosition, int currentYPosition, int rowHeight, int optionLayoutStyle, List<Shortcuts> shortcuts){
        if(shortcuts.size() > MAX_NUMBER_OF_SHORTCUTS){
            Log.e(TAG, "Invalid Shortcuts number, max value is " + String.valueOf(MAX_NUMBER_OF_SHORTCUTS) + "!");
            return;
        }
        if(rowHeight < 0){
            Log.e(TAG, "Invalid Row Height, it must be greater than 0");
            return;
        }
        if(shortcuts.size() == 0){
            Log.e(TAG, "Shortcuts must be at least one!");
            return;
        }

        //Get grid size
        GridSize gridSize = Utils.getGridSize(gridView);
        //Create shortcuts based on grid size
        createShortcutsBasedOnGridSize(currentXPosition, currentYPosition, rowHeight, gridSize, optionLayoutStyle, shortcuts);

        Log.d(TAG, "Shortcuts created!");
    }

    /**
     * Create shortcuts based on grid size with List
     * @param currentXPosition int
     * @param currentYPosition int
     * @param rowHeight int
     * @param gridSize GridSize
     * @param shortcuts List<Shortcuts>
     */
    private void createShortcutsBasedOnGridSize(int currentXPosition, int currentYPosition, int rowHeight, GridSize gridSize, int optionLayoutStyle, List<Shortcuts> shortcuts){
        getScreenDimension();
        positionInGrid = getPositionInGrid(currentXPosition, currentYPosition, gridView);

        if(layout != null || triangle != null)
            clearAllLayout();
        switch (maxXScreen){
            case 720:
                DIM_WIDTH = 360;
                DIM_HEIGHT = 90;
                break;
            case 1080:
                DIM_WIDTH = 640;
                DIM_HEIGHT = 150;
                break;
            case 1440:
                DIM_WIDTH = 840;
                DIM_HEIGHT = 200;
                break;
            default:
                Log.e(TAG, "Resolution of screen not supported!");
                break;
        }

        if (isClickOnItem(currentXPosition, currentYPosition, gridSize)) {
            LayoutInflater inflater = LayoutInflater.from(context);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DIM_WIDTH, DIM_HEIGHT);
            RelativeLayout.LayoutParams paramsTriangle = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //int mIconHeight = ((GridView) gridView).getColumnWidth();
            int mIconHeight;
            int mIconWidth = maxXScreen / ((GridView) gridView).getNumColumns();
            int dim = (positionInGrid) * mIconWidth;
            int layoutHeightTotal = DIM_HEIGHT * shortcuts.size() + 20;

            triangle = (RelativeLayout) inflater.inflate(R.layout.shortcuts_triangle, null, false);

            //Scale animation right to left
            ScaleAnimation animationRightToLeft = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f);
            animationRightToLeft.setDuration(200);

            //Scale animation left to right
            ScaleAnimation animationLeftToRight = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animationLeftToRight.setDuration(200);

            for (int i = 0; i < shortcuts.size(); i++) {
                layout[i] = (RelativeLayout) inflater.inflate(R.layout.shortcuts, null, false);
                shortcuts.get(i).init(layout[i], optionLayoutStyle, activity, packageImage);
                if ((dim + DIM_WIDTH) >= maxXScreen) {
                    //Destra
                    layout[i].setX(dim - DIM_WIDTH + (mIconWidth) - mIconWidth / 4);
                    triangle.setX((float) (dim + mIconWidth - mIconWidth / 1.5));
                    triangle.setRotation(180);

                    //Start Animation
                    layout[i].startAnimation(animationRightToLeft);
                    triangle.startAnimation(animationRightToLeft);
                } else {
                    //Sinistra

                    layout[i].setX(dim + mIconWidth / 4);
                    triangle.setX((float) (dim + mIconWidth / 2));
                    triangle.setRotation(180);

                    //Start Animation
                    layout[i].startAnimation(animationLeftToRight);
                    triangle.startAnimation(animationLeftToRight);
                }

                if ((toolbarHeight = Utils.getToolbarHeight(activity)) > 0) {
                    int maxYScreenWithToolbar = maxYScreen - toolbarHeight * 2;
                    positionInGrid = ((GridView) gridView).pointToPosition((int) currentXPosition, (int) currentYPosition);
                    positionInGrid /= gridSize.getColumnCount();
                    mIconHeight = Math.round(displayDensity * rowHeight) * positionInGrid + 1;
                    if (mIconHeight + layoutHeightTotal > maxYScreenWithToolbar) {
                        //Alto
                        switch (maxXScreen){
                            case 720:
                                switch (shortcuts.size()){
                                    case 2:
                                        if (i >= 1) {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 - 75);
                                        } else {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 + 20);
                                        }
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 + 94);
                                        break;
                                    case 3:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 95);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 23);
                                        break;
                                    case 4:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 275);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 200);
                                        break;
                                    case 5:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 475);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 400);
                                        break;
                                    default:
                                        Log.e(TAG, "Invalid Shortcuts number, max value is " + String.valueOf(MAX_NUMBER_OF_SHORTCUTS) + "!");
                                        break;
                                }
                                triangle.setRotation(0);
                                break;
                            case 1080:
                                switch (shortcuts.size()){
                                    case 2:
                                        if (i >= 1) {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 160 + toolbarHeight * 2);
                                        } else {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2);
                                        }
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 + 120);
                                        break;
                                    case 3:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 180 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + 140 * i);
                                        break;
                                    case 4:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 160 * 3 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 - 177 * 2 + toolbarHeight * i);
                                        break;
                                    case 5:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 160 * 5 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 - 337 * 2 + toolbarHeight * i);
                                        break;
                                    default:
                                        Log.e(TAG, "Invalid Shortcuts number, max value is " + String.valueOf(MAX_NUMBER_OF_SHORTCUTS) + "!");
                                        break;
                                }
                                triangle.setRotation(0);
                                break;
                            case 1440:
                                switch (shortcuts.size()){
                                    case 2:
                                        if (i >= 1) {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 220 + toolbarHeight * 2);
                                        } else {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2);
                                        }
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 + 160);
                                        break;
                                    case 3:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 220 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + 195 * i);
                                        break;
                                    case 4:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 220 * 3 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 - 250 * 2 + toolbarHeight * i);
                                        break;
                                    case 5:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 220 * 5 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 - 470 * 2 + toolbarHeight * i);
                                        break;
                                    default:
                                        Log.e(TAG, "Invalid Shortcuts number, max value is " + String.valueOf(MAX_NUMBER_OF_SHORTCUTS) + "!");
                                        break;
                                }
                                triangle.setRotation(0);
                                break;
                            default:
                                Log.e(TAG, "Resolution of screen not supported!");
                                break;
                        }
                    } else {
                        //Basso
                        positionInGrid = ((GridView) gridView).pointToPosition((int) currentXPosition, (int) currentYPosition);
                        positionInGrid /= gridSize.getColumnCount();
                        mIconHeight = Math.round(displayDensity * rowHeight) * positionInGrid + 1;
                        switch (maxXScreen){
                            case 720:
                                if (i >= 1) {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2 - 100 * i);
                                } else {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2);
                                }
                                switch (shortcuts.size()){
                                    case 1:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 36));
                                        break;
                                    case 2:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 46));
                                        break;
                                    case 3:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 56));
                                        break;
                                    case 4:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 68));
                                        break;
                                    case 5:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 80));
                                        break;
                                    default:
                                        Log.e(TAG, "Invalid Shortcuts number, max value is " + String.valueOf(MAX_NUMBER_OF_SHORTCUTS) + "!");
                                        break;
                                }
                                break;
                            case 1080:
                                if (i >= 1) {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2 - 160 * i);
                                } else {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2);
                                }
                                triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 50 + 10*i));
                                break;
                            case 1440:
                                if (i >= 1) {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2 - 220 * i);
                                } else {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2);
                                }
                                triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 80));
                                break;
                            default:
                                Log.e(TAG, "Resolution of screen not supported!");
                                break;
                        }
                    }
                }
                masterLayout.addView(layout[i], params);
            }
            masterLayout.addView(triangle, paramsTriangle);
        }
    }

    /**
     * Create shortcuts based on grid size with varargs
     * @param currentXPosition int
     * @param currentYPosition int
     * @param rowHeight int
     * @param gridSize GridSize
     * @param shortcuts Shortcuts...
     */
    private void createShortcutsBasedOnGridSize(int currentXPosition, int currentYPosition, int rowHeight, GridSize gridSize, int optionLayoutStyle, final Shortcuts... shortcuts){
        getScreenDimension();
        positionInGrid = getPositionInGrid(currentXPosition, currentYPosition, gridView);

        if(layout != null || triangle != null)
            clearAllLayout();

        switch (maxXScreen){
            case 720:
                DIM_WIDTH = 360;
                DIM_HEIGHT = 90;
                break;
            case 1080:
                DIM_WIDTH = 640;
                DIM_HEIGHT = 150;
                break;
            case 1440:
                DIM_WIDTH = 840;
                DIM_HEIGHT = 200;
                break;
            default:
                Log.e(TAG, "Resolution of screen not supported!");
                break;
        }

        if (isClickOnItem(currentXPosition, currentYPosition, gridSize)) {
            LayoutInflater inflater = LayoutInflater.from(context);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DIM_WIDTH, DIM_HEIGHT);
            RelativeLayout.LayoutParams paramsTriangle = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //int mIconHeight = ((GridView) gridView).getColumnWidth();
            int mIconHeight;
            int mIconWidth = maxXScreen / ((GridView) gridView).getNumColumns();
            int dim = (positionInGrid) * mIconWidth;
            int layoutHeightTotal = DIM_HEIGHT * shortcuts.length + 20;

            triangle = (RelativeLayout) inflater.inflate(R.layout.shortcuts_triangle, null, false);

            //Scale animation right to left
            ScaleAnimation animationRightToLeft = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f);
            animationRightToLeft.setDuration(200);

            //Scale animation left to right
            ScaleAnimation animationLeftToRight = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animationLeftToRight.setDuration(200);

            for (int i = 0; i < shortcuts.length; i++) {
                layout[i] = (RelativeLayout) inflater.inflate(R.layout.shortcuts, null, false);
                shortcuts[i].init(layout[i], optionLayoutStyle, activity, packageImage);
                if ((dim + DIM_WIDTH) >= maxXScreen) {
                    //Destra
                    layout[i].setX(dim - DIM_WIDTH + (mIconWidth) - mIconWidth / 4);
                    triangle.setX((float) (dim + mIconWidth - mIconWidth / 1.5));
                    triangle.setRotation(180);

                    //Start Animation
                    layout[i].startAnimation(animationRightToLeft);
                    triangle.startAnimation(animationRightToLeft);
                } else {
                    //Sinistra
                    layout[i].setX(dim + mIconWidth / 4);
                    triangle.setX((float) (dim + mIconWidth / 2));
                    triangle.setRotation(180);

                    //Start Animation
                    layout[i].startAnimation(animationLeftToRight);
                    triangle.startAnimation(animationLeftToRight);
                }

                if ((toolbarHeight = Utils.getToolbarHeight(activity)) > 0) {
                    int maxYScreenWithToolbar = maxYScreen - toolbarHeight * 2;
                    positionInGrid = ((GridView) gridView).pointToPosition((int) currentXPosition, (int) currentYPosition);
                    positionInGrid /= gridSize.getColumnCount();
                    mIconHeight = Math.round(displayDensity * rowHeight) * positionInGrid + 1;
                    if (mIconHeight + layoutHeightTotal > maxYScreenWithToolbar) {
                        //Alto
                        switch (maxXScreen){
                            case 720:
                                switch (shortcuts.length){
                                    case 2:
                                        if (i >= 1) {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 - 75);
                                        } else {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 + 20);
                                        }
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 + 94);
                                        break;
                                    case 3:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 95);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 23);
                                        break;
                                    case 4:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 275);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 200);
                                        break;
                                    case 5:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 475);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * i - 400);
                                        break;
                                    default:
                                        Log.e(TAG, "Invalid numbers of shortcuts! Max is 5");
                                        break;
                                }
                                triangle.setRotation(0);
                                break;
                            case 1080:
                                switch (shortcuts.length){
                                    case 2:
                                        if (i >= 1) {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 160 + toolbarHeight * 2);
                                        } else {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2);
                                        }
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 + 120);
                                        break;
                                    case 3:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 180 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + 140 * i);
                                        break;
                                    case 4:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 160 * 3 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 - 177 * 2 + toolbarHeight * i);
                                        break;
                                    case 5:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 160 * 5 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 - 337 * 2 + toolbarHeight * i);
                                        break;
                                    default:
                                        Log.e(TAG, "Invalid numbers of shortcuts! Max is 5");
                                        break;
                                }
                                triangle.setRotation(0);
                                break;
                            case 1440:
                                switch (shortcuts.length){
                                    case 2:
                                        if (i >= 1) {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 220 + toolbarHeight * 2);
                                        } else {
                                            layout[i].setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2);
                                        }
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + toolbarHeight * 2 + 160);
                                        break;
                                    case 3:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 220 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 + 195 * i);
                                        break;
                                    case 4:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 220 * 3 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 - 250 * 2 + toolbarHeight * i);
                                        break;
                                    case 5:
                                        layout[i].setY(+layoutHeightTotal + mIconHeight / 5 - 220 * 5 + toolbarHeight * i);
                                        triangle.setY(+layoutHeightTotal + mIconHeight / 5 - 470 * 2 + toolbarHeight * i);
                                        break;
                                    default:
                                        Log.e(TAG, "Invalid numbers of shortcuts! Max is 5");
                                        break;
                                }
                                triangle.setRotation(0);
                                break;
                            default:
                                Log.e(TAG, "Resolution of screen not supported!");
                                break;
                        }
                    } else {
                        //Basso
                        positionInGrid = ((GridView) gridView).pointToPosition((int) currentXPosition, (int) currentYPosition);
                        positionInGrid /= gridSize.getColumnCount();
                        mIconHeight = Math.round(displayDensity * rowHeight) * positionInGrid + 1;
                        switch (maxXScreen){
                            case 720:
                                if (i >= 1) {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2 - 100 * i);
                                } else {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2);
                                }
                                switch (shortcuts.length){
                                    case 1:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 36));
                                        break;
                                    case 2:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 46));
                                        break;
                                    case 3:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 56));
                                        break;
                                    case 4:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 68));
                                        break;
                                    case 5:
                                        triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 80));
                                        break;
                                    default:
                                        Log.e(TAG, "Invalid numbers of shortcuts! Max is 5");
                                        break;
                                }
                                break;
                            case 1080:
                                if (i >= 1) {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2 - 160 * i);
                                } else {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2);
                                }
                                triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 50 + 10*i));
                                break;
                            case 1440:
                                if (i >= 1) {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2 - 220 * i);
                                } else {
                                    layout[i].setY(+mIconHeight * 3 / 4 + layoutHeightTotal + toolbarHeight / 2);
                                }
                                triangle.setY((float) (+mIconHeight * 3 / 4 + layoutHeightTotal - toolbarHeight * i + 80));
                                break;
                            default:
                                Log.e(TAG, "Resolution of screen not supported!");
                                break;
                        }
                    }
                }
                masterLayout.addView(layout[i], params);
            }
            masterLayout.addView(triangle, paramsTriangle);
        }
    }

    /**
     * Check if click is on Item
     * @param currentXPosition int
     * @param currentYPosition int
     * @param gridSize GridSize
     * @return boolean
     */
    private boolean isClickOnItem(int currentXPosition, int currentYPosition, GridSize gridSize){
        int positionPointed = ((GridView) gridView).pointToPosition(currentXPosition, currentYPosition);
        return positionPointed < gridSize.getColumnCount()*gridSize.getRowCount();
    }

    /**
     * Clear all shortcuts layout
     */
    public void clearAllLayout() {
        if(layout != null) {
            for (int i = 0; i < layout.length; i++) {
                if (layout[i] != null && ((ViewGroup) layout[i].getParent()) != null) {
                        ((ViewGroup) layout[i].getParent()).removeView(layout[i]);
                }
            }
            Log.d(TAG, "Layout clear!");
        }
        if(triangle != null && ((ViewGroup) triangle.getParent()) != null) {
            ((ViewGroup) triangle.getParent()).removeView(triangle);
            Log.d(TAG, "Layout clear!");
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
        Log.d(TAG, "Dimension acquired X:" + String.valueOf(maxXScreen) + " Y: " + String.valueOf(maxYScreen));
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
            Log.d(TAG, "Position In Grid: " + String.valueOf(positionInGrid));
            if(positionInGrid < 0){
                positionInGrid = ((GridView) gridView).pointToPosition(currentXPosition, currentYPosition);
                Log.w(TAG, "Position In Grid lower than 0, trying again, positionInGrid: " + String.valueOf(positionInGrid));
            }
        }
        return positionInGrid;
    }

    /**
     * Set package image for create shortcuts on launcher
     * @param packageImage Drawable
     */
    public static void setPackageImage(Drawable packageImage) {
        ShortcutsCreation.packageImage = packageImage;
    }
}
