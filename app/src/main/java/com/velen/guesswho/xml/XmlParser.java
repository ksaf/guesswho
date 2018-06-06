package com.velen.guesswho.xml;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    public final static String NEXT_CHARACTER_START = "nextCharacterStart";
    private Context context;

    public XmlParser(Context context) {
        this.context = context;
    }

    public List<String> parseXml(int xmlToParse, String tag, String... features) {
        XmlResourceParser parser = context.getResources().getXml(xmlToParse);
        int eventType = -1;
        List<String> results = new ArrayList<>();

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                String c = parser.getName();

                if (c.equals(tag)) {
                    for(String feature : features) {
                        results.add(parser.getAttributeValue(null, feature));
                    }
                }
                results.add(NEXT_CHARACTER_START);
            }
            try {
                eventType = parser.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

}
