#IN PORGRESS...

For AIDL communication, owner of launcher have to do this:


```groovy
                String PACKAGE_OF_SHORTCUTS;
                try {
                    PACKAGE_OF_SHORTCUTS = serviceConnection.getService().getPackageName();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                int positionPointed = ((GridView) gridView).pointToPosition((int) motionEvent.getX(),  (int) motionEvent.getY());
                
                if(ExampleArrayAdapter.pkgAppsList.get(positionPointed).activityInfo.packageName.equalsIgnoreCase(PACKAGE_OF_SHORTCUTS)) {
                    try {
                        shortcutsCreation.createShortcuts((int) motionEvent.getX(), (int) motionEvent.getY(), 96, 1, serviceConnection.getService().getShortcuts());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "App Shortcuts not found for this package!", Toast.LENGTH_SHORT)
                            .show();
                }
```
