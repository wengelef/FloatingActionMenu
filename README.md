# FloatingActionMenu

Vertically expandable array of Floating Action Buttons

## Usage

```xml
<com.wengelef.floatingactionmenu.FloatingActionMenu
        android:id="@+id/fab_menu"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:mainMarginBottom="80dp"
        app:mainMarginEnd="80dp"
        app:verticalPadding="10dp"
        app:fabSize="normal"/>
```

```java
FloatingActionMenu mFloatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);

mFloatingActionMenu.addFab(new FloatingActionMenu.MenuAction(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d("FloatingActionMenu", "play clicked!");
    }
}, R.drawable.ic_play_arrow_white_24dp));

mFloatingActionMenu.addFab(new FloatingActionMenu.MenuAction(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d("FloatingActionMenu", "pause clicked!");
    }
}, R.drawable.ic_pause_white_24dp));
```

## Download

##### Gradle:

```java
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

```java
dependencies {
    compile 'com.github.wengelef:FloatingActionMenu:0.2.0'
}
```

##### Maven:

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```
<dependency>
    <groupId>com.github.wengelef</groupId>
    <artifactId>FloatingActionMenu</artifactId>
    <version>0.2.0</version>
</dependency>
```

## License

Copyright 2016 Florian Wengelewski

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.