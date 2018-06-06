package com.velen.guesswho.assetLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AssetLoaderTest {

    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void tearDown() {}

    @Test
    public void shouldLoadDrawableFromAssets() {
        Drawable imageLoaded = AssetLoader.loadDrawableFromAssets(context, "characterGroups/orestis.bmp");
        Assert.assertNotNull(imageLoaded);
    }

    @Test
    public void shouldLoadBitmapFromAssets() {
        Bitmap imageLoaded = AssetLoader.loadBitmapFromAssets(context, "characterGroups/orestis.bmp");
        Assert.assertNotNull(imageLoaded);
    }


}
