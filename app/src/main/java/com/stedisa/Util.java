package com.stedisa;

import android.content.Context;

public class Util {
    public static int getImageIdByName(Context c, String name) {
        return c.getResources().getIdentifier(name, "drawable", c.getPackageName());
    }
}
