package com.velen.guesswho.characters;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.velen.guesswho.assetLoader.AssetLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class that uses an JSON file to read characters from, and can create a {@link CharacterGroup}
 * with these characters with {@link #getCharactersInJSON(String)}.
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
            groupName = jsonObject.getString("groupName");

            /* Create group's characters list*/
            JSONArray allCharactersArray = jsonObject.getJSONArray("characters");
            for(int i = 0; i < allCharactersArray.length(); i++) {
                JSONObject characterJSON = (JSONObject) allCharactersArray.get(i);
                characterGroup.addCharacter(createCharacter(characterJSON, groupName));
            }

            /* Add leader icon to group*/
            String leaderIconFile = jsonObject.getString("leader");
            characterGroup.setGroupLeader(AssetLoader.loadDrawableFromAssets(context, "characterGroups/" + groupName + "/leader/" + leaderIconFile));

            /* Add group's background*/
            String backgroundFile = jsonObject.getString("background");
            characterGroup.setGroupBackGround(AssetLoader.loadDrawableFromAssets(context, "characterGroups/" + groupName + "/background/" + backgroundFile));
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
            String attributeName = iterator.next();
            /* Add miscellaneous features*/
            if(attributeName.startsWith("miscellaneous")) {
                JSONArray miscArray = characterJSON.getJSONArray("miscellaneous");
                for(int i = 0; i < miscArray.length(); i++) {
                    miscellaneous.add(miscArray.getString(i));
                }
                break;
            }
            /* Add simple features*/
            String attributeValue = characterJSON.getString(attributeName);
            /* Capture name to help build character's image*/
            if(attributeName.startsWith("name")) {
                characterName = attributeValue;
            }
            builder.addSimpleFeature(attributeName, attributeValue);
        }

        builder.addMiscFeaturesList(miscellaneous);

        String imagePath = "characterGroups/" + groupName + "/characters/" + characterName.toLowerCase().replaceAll(" ", "") + CHARACTER_IMAGE_TYPE;
        Drawable image = AssetLoader.loadDrawableFromAssets(context, imagePath);
        Drawable flippedImage = AssetLoader.loadDrawableFromAssets(context, "characterGroups/flipped.png");
        Character character = builder.setDrawable(image).setFlippedDrawable(flippedImage).buildCharacter();
        return character;
    }

}
