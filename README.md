# AndroidShortcuts
<h2 align="center">Shortcuts for Android on Pre Nougat 7.1!</h1>

##WHAT IS ANDROID SHORTCUTS?

The shorctus have a features of Android 7.1 Nougat, and available only for the launcher that implement, in this library, you can implement in your launcher shorctus starting from API 14!
I have also implemented [Force Touch](https://github.com/michelelacorte/ForceTouch) and YOU CAN USE ON CUSTOM LAUNCHER WITH SHORTCUTS!!

##DONATIONS

This project needs you! If you would like to support this project's further development, the creator of this project or the continuous maintenance of this project, feel free to donate. Your donation is highly appreciated (and I love food, coffee and beer). Thank you!

**PayPal**

* **[Donate $5]**: Thank's for creating this project, here's a coffee (or some beer) for you!

* **[Donate $10]**: Wow, I am stunned. Let me take you to the movies!ù

* **[Donate $15]**: I really appreciate your work, let's grab some lunch!

* **[Donate $25]**: That's some awesome stuff you did right there, dinner is on me!

* **[Donate $50]**: I really really want to support this project, great job!

* **[Donate $100]**: You are the man! This project saved me hours (if not days) of struggle and hard work, simply awesome!

* **[Donate $2799]**: Go buddy, buy Macbook Pro for yourself!

Of course, you can also choose what you want to donate, all donations are awesome!!

<img align="left" src="https://s15.postimg.org/km4eygofv/ic_launcher.png">
#v0.1.0

###Here we are!
###The touch force is ready and is going to get on the custom launcher !!

###As promised I've implemented Force Touch in my custom launcher ... and you see the picture

<h1 align="center"><img src="https://s17.postimg.org/vimarchhb/Force_Touch_Launcher_framed.png"/></h1>

###Yes, I'm working with shortcuts Android 7.1 Nougat! and will soon be available for custom launcher from API 14 !!
###See an example here (beta)

<h1 align="center"><img src="http://i.giphy.com/3oz8xM1ZWIeAjdXTHy.gif"/></h1>

###Stay Tuned!
###For other detail to use force touch follow [Force Touch](https://github.com/michelelacorte/ForceTouch)

##USAGE

###N.B

###This version is just a pre-relase to allow everyone to try it and report any bugs, so it support grid size 4x5 (Column x Row), in future versions there will be other dimensions!!


Add this to `build.gradle`

```groovy
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

Than add this dependencies

```groovy
compile 'com.github.michelelacorte:AndroidShortcuts:0.1.0'
```

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
                shortcutsCreation.createShortcuts((int)motionEvent.getX(), (int)motionEvent.getY(),
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

Coming soon with [Force Touch](https://github.com/michelelacorte/ForceTouch) implementation

##SYSTEM REQUIREMENT

Android API 14+

##STATUS

![project maintained](https://img.shields.io/badge/Project-Maintained-green.svg)

##CHANGELOG

**v0.1.0**
- Support API 14+ (API 25 Compatible)
- Added params `ShorcutsCreation` class for initialize `gridView` and `parentLayout`
- Added `Shortcuts` class for create your custom shortcuts!!

##CREDITS

Author: Michele Lacorte (micky1995g@gmail.com)

##CONTRIBUTING

If you want to contribute to the project fork it and open [Pull Request](https://github.com/michelelacorte/AndroidShortcuts/pulls), or contact me by e-mail.

Each proposal will be accepted!

##LICENSE

```
Copyright 2016 Michele Lacorte

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


[Donate $5]: 		https://www.paypal.me/MicheleLacorte/5
[Donate $10]:  		https://www.paypal.me/MicheleLacorte/10
[Donate $15]:  		https://www.paypal.me/MicheleLacorte/15
[Donate $25]:  		https://www.paypal.me/MicheleLacorte/25
[Donate $50]: 		https://www.paypal.me/MicheleLacorte/50
[Donate $100]: 		https://www.paypal.me/MicheleLacorte/100
[Donate $2799]: 	https://www.paypal.me/MicheleLacorte/2799
