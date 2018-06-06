package com.velen.guesswho.characters;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

import com.velen.guesswho.assetLoader.AssetLoader;

import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;

/**
 * A class that uses an XML file to read characters from, and can create a {@link CharacterGroup}
 * with these characters with {@link #getCharactersInXml(int)}.
 */
public class CharacterGroupBuilder {

    private final String CHARACTER_IMAGE_TYPE = ".png";
    private final int MAX_NUMBER_OF_ATTRIBUTES_FOR_EACH_CHAR = 9;
    private Context context;
    private CharacterGroup characterGroup;

    /**
     * The constructor for this class.
     * @param context The application's context.
     */
    public CharacterGroupBuilder(Context context) {
        this.context = context;
        this.characterGroup = new CharacterGroup();
    }

    /**
     * @param xmlToParse The R path where the xml is located.
     * @return A CharacterGroup containing all the characters found in the xml.
     */
    public CharacterGroup getCharactersInXml(int xmlToParse) {
        characterGroup = new CharacterGroup();
        parseXml(xmlToParse);
        return characterGroup;
    }

    private void parseXml(int xmlToParse) {
        XmlResourceParser parser = context.getResources().getXml(xmlToParse);
        int eventType = -1;
        Character myChar;
        String groupName = "";
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                String c = parser.getName();
                if(c.equals("groupName")) {
                    groupName = parser.getAttributeValue(0);
                    characterGroup.setGroupName(groupName);
                }
                if (c.equals("character")) {
                    int numberOfAttributes = parser.getAttributeCount();
                    String[] allAttributeNames = new String[MAX_NUMBER_OF_ATTRIBUTES_FOR_EACH_CHAR];
                    String[] allAttributeValues = new String[MAX_NUMBER_OF_ATTRIBUTES_FOR_EACH_CHAR];
                    for(int currentAttributeIndex = 0; currentAttributeIndex <= parser.getAttributeCount(); currentAttributeIndex++) {
                        allAttributeNames[currentAttributeIndex] = currentAttributeIndex<numberOfAttributes ? parser.getAttributeName(currentAttributeIndex) : null;
                        allAttributeValues[currentAttributeIndex] = currentAttributeIndex<numberOfAttributes ? parser.getAttributeValue(null, parser.getAttributeName(currentAttributeIndex)) : null;
                    }
                    myChar = createCharacter(allAttributeNames, allAttributeValues, groupName);
                    characterGroup.addCharacter(myChar);
                }
                if(c.equals("leader")) {
                    String file = parser.getAttributeValue(0);
                    characterGroup.setGroupLeader(AssetLoader.loadDrawableFromAssets(context, "characterGroups/" + groupName + "/leader/" + file));
                }
                if(c.equals("background")) {
                    String file = parser.getAttributeValue(0);
                    characterGroup.setGroupBackGround(AssetLoader.loadDrawableFromAssets(context, "characterGroups/" + groupName + "/background/" + file));
                }
            }
            try {
                eventType = parser.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Character createCharacter(String[] allAttributeNames, String[] allAttributeValues, String groupName) {
        CharacterBuilder builder = new CharacterBuilder();
        String characterName = "";
        for(int i = 0; i<MAX_NUMBER_OF_ATTRIBUTES_FOR_EACH_CHAR; i++) {
            if(allAttributeNames[i] != null) {
                if(allAttributeNames[i].startsWith("misc")) {
                    allAttributeNames[i] = "miscellaneous";
                }
                if(allAttributeNames[i].startsWith("name")) {
                    characterName = allAttributeValues[i];
                }
            }
            builder.addFeature(allAttributeNames[i], allAttributeValues[i]);
        }
        String imagePath = "characterGroups/" + groupName + "/characters/" + characterName.toLowerCase().replaceAll(" ", "") + CHARACTER_IMAGE_TYPE;
        Drawable image = AssetLoader.loadDrawableFromAssets(context, imagePath);
        Drawable flippedImage = AssetLoader.loadDrawableFromAssets(context, "characterGroups/flipped.png");
        return builder.setDrawable(image).setFlippedDrawable(flippedImage).buildCharacter();
    }

}
