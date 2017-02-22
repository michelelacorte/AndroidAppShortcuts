#Here we are!

##Android Shortcuts Library is now complete for support with other app, now all developers can register own shortcuts and make accessible for the launcher that implement library!

##No more talk, and let's see how you implement!

###In App "client" we only do this:

```
        ArrayList<Shortcuts> listOfShortcuts = new ArrayList<>();
        listOfShortcuts.add(new Shortcuts(R.mipmap.ic_launcher, "Test File AA"));
        listOfShortcuts.add(new Shortcuts(R.mipmap.ic_launcher, "Test Service AA", "it.michelelacorte.aa.MainActivity", "it.michelelacorte.aa"));

         // Only 1 line!!!
        RemoteShortcuts.saveRemoteShortcuts(this, this.getPackageName(), listOfShortcuts);
```

###Than in "server" app, launcher for example, we do:

```
                ArrayList<Shortcuts> listOfShortcuts = new ArrayList<>();
                int positionPointed = ((GridView) gridView).pointToPosition((int) motionEvent.getX(),  (int) motionEvent.getY());

                //Get clicked package name
                String packageName = ExampleArrayAdapter.pkgAppsList.get(positionPointed).activityInfo.packageName;

                //Get shortcuts for this package, again, 1 line!
                listOfShortcuts = RemoteShortcuts.getRemoteShortcuts(MainActivity.this, packageName);

                ShortcutsBuilder builder = new ShortcutsBuilder.Builder(MainActivity.this, activityParent)
                         .normalShortcuts(gridView, (int) motionEvent.getX(), (int) motionEvent.getY(), 96)
                         .setOptionLayoutStyle(StyleOption.LINE_LAYOUT)
                         .setPackageImage(packageImage)
                         .setShortcutsList(listOfShortcuts)
                         .build();
                         
                shortcutsCreation = new ShortcutsCreation(builder);

                //If shortcuts are defined, show it!
                if(listOfShortcuts != null && listOfShortcuts.size() > 0) {
                    shortcutsCreation.init();
                }else{
                    Toast.makeText(MainActivity.this, "App Shortcuts not found for this package!", Toast.LENGTH_SHORT)
                            .show();
                }
```

