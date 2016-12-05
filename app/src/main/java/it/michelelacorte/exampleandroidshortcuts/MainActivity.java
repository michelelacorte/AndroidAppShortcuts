package it.michelelacorte.exampleandroidshortcuts;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import it.michelelacorte.androidshortcuts.Shortcuts;
import it.michelelacorte.androidshortcuts.ShortcutsCreation;

public class MainActivity extends AppCompatActivity {

    private AdapterView gridView;
    private RelativeLayout activityParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityParent = (RelativeLayout) findViewById(R.id.activity_main);

        gridView= (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ExampleArrayAdapter(this, R.layout.app_grid_item));

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
                shortcutsCreation.createShortcuts((int)motionEvent.getX(), (int)motionEvent.getY(), 96,
                        new Shortcuts(R.mipmap.ic_launcher, "Shortcuts", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "Hello Shortcuts!!", Toast.LENGTH_LONG).show();
                            }
                        }),
                        new Shortcuts(R.mipmap.ic_launcher, "Nougat!"));
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
}


class ExampleArrayAdapter extends BaseAdapter {


    private Activity activity;
    private int layoutResourceId;

    public ExampleArrayAdapter(Activity activity, int layoutResourceId) {
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater =  (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.appImage = (ImageView) row.findViewById(R.id.appIcon);
            holder.appText = (TextView) row.findViewById(R.id.appText);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.appImage.setImageResource(R.mipmap.ic_launcher);
        holder.appText.setText("App Text");
        return row;
    }

    static class ViewHolder {
        ImageView appImage;
        TextView appText;
    }
}
