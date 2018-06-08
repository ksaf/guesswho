package com.velen.guesswho.characters;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

import com.velen.guesswho.assetLoader.AssetLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


    /**
     * @param jsonFile The string of path where the JSON file is located.
     * @return A CharacterGroup containing all the characters found in the JSON file.
     */
    public CharacterGroup getCharactersInJSON(String jsonFile) {
        String jsonString = loadJSONFromAsset(jsonFile);
        return parseJSON(jsonString);
    }

    private String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private CharacterGroup parseJSON(String jsonString) {
        characterGroup = new CharacterGroup();
        String groupName = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            groupName = getGroupName(jsonObject);
            JSONArray allCharactersArray = jsonObject.getJSONArray("characters");
            for(int i = 0; i < allCharactersArray.length(); i++) {
                JSONObject characterJSON = (JSONObject) allCharactersArray.get(i);
                characterGroup.addCharacter(createCharacter(characterJSON, groupName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return characterGroup;
    }

    private Character createCharacter(JSONObject characterJSON, String groupName) throws JSONException {
        CharacterBuilder builder = new CharacterBuilder();
        String characterName = "";

        Iterator<String> iterator = characterJSON.keys();
        List<String> miscellaneous = new ArrayList<>();
        while (iterator.hasNext()) {
            if(iterator.next().equals("miscellaneous")) {
                JSONArray miscArray = characterJSON.getJSONArray("miscellaneous");
                for(int i = 0; i < miscArray.length(); i++) {
                    miscellaneous.add(miscArray.getString(i));
                }
                break;
            }
            String attributeName = iterator.next();
            String attributeValue = characterJSON.getString(attributeName);
            builder.addFeature(attributeName, attributeValue);
        }

        String imagePath = "characterGroups/" + groupName + "/characters/" + characterName.toLowerCase().replaceAll(" ", "") + CHARACTER_IMAGE_TYPE;
        Drawable image = AssetLoader.loadDrawableFromAssets(context, imagePath);
        Drawable flippedImage = AssetLoader.loadDrawableFromAssets(context, "characterGroups/flipped.png");
        return builder.setDrawable(image).setFlippedDrawable(flippedImage).buildCharacter();
    }

    private String getGroupName(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("groupName");
    }

    private String getLeaderImagePath(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("leader");
    }

}
