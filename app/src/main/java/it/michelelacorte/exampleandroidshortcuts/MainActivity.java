package it.michelelacorte.exampleandroidshortcuts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import it.michelelacorte.androidshortcuts.RemoteShortcuts;
import it.michelelacorte.androidshortcuts.Shortcuts;
import it.michelelacorte.androidshortcuts.ShortcutsCreation;
import it.michelelacorte.androidshortcuts.util.StyleOption;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private AdapterView gridView;
    public static Drawable img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Try to hide toolbar, library works!
        /*
        try {
            getSupportActionBar().hide();
        }catch(Exception e){}
        */


        //Example of remote shortcuts
        ArrayList<Shortcuts> listOfShortcuts = new ArrayList<>();


        listOfShortcuts.add(new Shortcuts(R.drawable.ic_done_black_24dp, "AndroidShortcuts"));
        listOfShortcuts.add(new Shortcuts(R.drawable.ic_add_black_24dp, "App Shortcuts"));
        listOfShortcuts.add(new Shortcuts(R.drawable.ic_code_black_24dp, "Test Shortcuts", "it.michelelacorte.exampleandroidshortcuts.MainActivity", "it.michelelacorte.exampleandroidshortcuts"));

        //Call this for save shortcuts and make accessible from library
        RemoteShortcuts.saveRemoteShortcuts(this, this.getPackageName(), listOfShortcuts);

        RelativeLayout activityParent = (RelativeLayout) findViewById(R.id.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        final ExampleArrayAdapter exampleArrayAdapter = new ExampleArrayAdapter(this, R.layout.app_grid_item);
        gridView.setAdapter(exampleArrayAdapter);

        //Create shortcuts
        final ShortcutsCreation shortcutsCreation = new ShortcutsCreation(MainActivity.this, activityParent, gridView);


        //Create gesture detector for onLongPress
        final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                shortcutsCreation.clearAllLayout();
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
                shortcutsCreation.clearAllLayout();
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                shortcutsCreation.clearAllLayout();
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                shortcutsCreation.clearAllLayout();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                shortcutsCreation.clearAllLayout();
                //Now create shortcuts!
                int positionPointed = ((GridView) gridView).pointToPosition((int) motionEvent.getX(),  (int) motionEvent.getY());
                try {
                    Drawable packageImage = ExampleArrayAdapter.pkgAppsList.get(positionPointed).activityInfo.loadIcon(getPackageManager());
                    shortcutsCreation.setPackageImage(packageImage);
                    shortcutsCreation.createShortcuts((int) motionEvent.getX(), (int) motionEvent.getY(), 96, StyleOption.LINE_LAYOUT,
                            new Shortcuts(R.drawable.ic_add_black_24dp, "Shortcuts", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getApplicationContext(), "Hello Shortcuts!!", Toast.LENGTH_LONG).show();
                                    shortcutsCreation.clearAllLayout();
                                }
                            }),
                            new Shortcuts(R.drawable.ic_done_black_24dp, "Nougat!", "it.michelelacorte.exampleandroidshortcuts.MainActivity", "it.michelelacorte.exampleandroidshortcuts"),
                            new Shortcuts(R.drawable.ic_code_black_24dp, "App Shortcuts!", "it.michelelacorte.exampleandroidshortcuts.MainActivity", "it.michelelacorte.exampleandroidshortcuts"));
                }catch(Exception e) {
                    Toast.makeText(MainActivity.this, "Position Incorrect!", Toast.LENGTH_SHORT)
                            .show();
                }
/*

                ArrayList<Shortcuts> listOfShortcuts = new ArrayList<>();
                int positionPointed = ((GridView) gridView).pointToPosition((int) motionEvent.getX(),  (int) motionEvent.getY());
                try {
                    Log.d(TAG, "NAME OF PACK CLICKED: " + ExampleArrayAdapter.pkgAppsList.get(positionPointed).activityInfo.packageName);

                    //Get clicked package name
                    String packageName = ExampleArrayAdapter.pkgAppsList.get(positionPointed).activityInfo.packageName;
                    //Get package icon
                    Drawable packageImage = ExampleArrayAdapter.pkgAppsList.get(positionPointed).activityInfo.loadIcon(getPackageManager());
                    //Get shortcuts for this package
                    listOfShortcuts = RemoteShortcuts.getRemoteShortcuts(MainActivity.this, packageName);

                    //If shortcuts are defined, show it!
                    if (listOfShortcuts != null && listOfShortcuts.size() > 0) {
                        shortcutsCreation.setPackageImage(packageImage);
                        shortcutsCreation.createShortcuts((int) motionEvent.getX(), (int) motionEvent.getY(), 96, StyleOption.LINE_LAYOUT, listOfShortcuts);
                    } else {
                        Toast.makeText(MainActivity.this, "App Shortcuts not found for this package!", Toast.LENGTH_SHORT)
                                .show();
                    }
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Position Incorrect!", Toast.LENGTH_SHORT)
                            .show();
                }
                */

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                shortcutsCreation.clearAllLayout();
                return false;
            }
        });

        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.about:
                aboutAlertDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void aboutAlertDialog()
    {
        AlertDialog builder =
                new AlertDialog.Builder(this, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.app_name))
                        .setCancelable(false)
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage(R.string.disclaimer_dialog_message)
                        .setPositiveButton(getResources().getString(R.string.disclaimer_dialog_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).create();
        builder.show();
        ((TextView)builder.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)builder.findViewById(android.R.id.message)).setGravity(Gravity.CENTER_VERTICAL);
        builder.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
}

    class ExampleArrayAdapter extends BaseAdapter {


        private Activity activity;
        private int layoutResourceId;
        public static ArrayList<ResolveInfo> pkgAppsList;

        public ExampleArrayAdapter(Activity activity, int layoutResourceId) {
            this.activity = activity;
            this.layoutResourceId = layoutResourceId;
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            pkgAppsList = new ArrayList<>(activity.getPackageManager().queryIntentActivities( mainIntent, 0));

            final PackageManager pm = activity.getPackageManager();
            Collections.sort(pkgAppsList, new Comparator<ResolveInfo>(){
                public int compare(ResolveInfo emp1, ResolveInfo emp2) {
                    return emp1.loadLabel(pm).toString().compareToIgnoreCase(emp2.loadLabel(pm).toString());
                }
            });
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 20;//create views as per count of IDU found in one group
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.appImage = (ImageView) row.findViewById(R.id.appIcon);
                holder.appText = (TextView) row.findViewById(R.id.appText);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            holder.appImage.setImageDrawable(pkgAppsList.get(position).loadIcon(activity.getPackageManager()));
            holder.appText.setText(pkgAppsList.get(position).loadLabel(activity.getPackageManager()).toString());
            //holder.appImage.setImageResource(R.mipmap.ic_launcher);
            //holder.appText.setText("App Text");
            return row;
        }

        static class ViewHolder {
            private ImageView appImage;
            private TextView appText;
        }
    }
