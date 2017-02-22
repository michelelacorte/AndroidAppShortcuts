Now let's start to create Shortcuts!

This is example provided by example app

```groovy
                    ShortcutsBuilder builder = new ShortcutsBuilder.Builder(MainActivity.this, activityParent)
                            .normalShortcuts(gridView, (int) motionEvent.getX(), (int) motionEvent.getY(), 96)
                            .setOptionLayoutStyle(StyleOption.LINE_LAYOUT)
                            .setPackageImage(packageImage)
                            .setShortcutsArray(new Shortcuts(R.drawable.ic_add_black_24dp, "Shortcuts", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(getApplicationContext(), "Hello Shortcuts!!", Toast.LENGTH_LONG).show();
                                            if(shortcutsCreation != null)
                                                shortcutsCreation.clearAllLayout();
                                        }
                                    }),
                                    new Shortcuts(R.drawable.ic_done_black_24dp, "Nougat!", "it.michelelacorte.exampleandroidshortcuts.MainActivity", "it.michelelacorte.exampleandroidshortcuts"),
                                    new Shortcuts(R.drawable.ic_code_black_24dp, "App Shortcuts!", "it.michelelacorte.exampleandroidshortcuts.MainActivity", "it.michelelacorte.exampleandroidshortcuts"))
                            .build();

                     ShortcutsCreation shortcutsCreation = new ShortcutsCreation(builder);

                    shortcutsCreation.init();
```

Now let's start to explain it:

```groovy
                    
                    ShortcutsBuilder builder = new ShortcutsBuilder.Builder(this, activityParent) //first params Activity, second root layout of activity
                            .normalShortcuts(gridView, (int) motionEvent.getX(), (int) motionEvent.getY(), 96) //adapter view or gridview, X and Y position of click, and row height.
                            .setOptionLayoutStyle(StyleOption.LINE_LAYOUT) //layout style for right menù
                            .setPackageImage(packageImage) //optional package image
                            .setShortcutsArray() //set shortcuts array
                            //.setShortcutsList() // or list
                            .build(); //last call build.

                    ShortcutsCreation shortcutsCreation = new ShortcutsCreation(builder); //create class for layout shortcuts

                    shortcutsCreation.init(); //layout the shortcuts!
```

