Now let's start to create Shortcuts!

```groovy
    //Layout for shortcuts
    private AdapterView gridView;
    private RelativeLayout activityParent;
```

Than in MainActivity

```
        activityParent = (RelativeLayout) findViewById(R.id.activity_main);
        gridView=(GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new MyArrayAdapter(this, R.layout.app_grid_item));
        
        //Create Shortcuts
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
            
                //Make sure to clear layout before create new
                shortcutsCreation.clearAllLayout();
                
                //Now create shortcuts!!
                shortcutsCreation.createShortcuts((int)motionEvent.getX(), (int)motionEvent.getY(), 96, 0
                        new Shortcuts(R.mipmap.ic_launcher, "Shortcuts", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "Hello Shortcuts!!", Toast.LENGTH_LONG).show();
                            }
                        }),
                        new Shortcuts(R.mipmap.ic_launcher, "Hello!"));
            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                shortcutsCreation.clearAllLayout();
                return false;
            }
        });

        // Set custom touch listener
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
```
