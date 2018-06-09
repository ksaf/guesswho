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

import static com.velen.guesswho.gameStrings.GameStringLiterals.*;

/**
 * A class that uses an JSON file to read characters from, and can create a {@link CharacterGroup}
 * with these characters with {@link #getCharactersInJSON(String)}.
 */
public class CharacterGroupBuilder {

    private final String CHARACTER_IMAGE_TYPE = PNG_EXTENSION;
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
            groupName = jsonObject.getString(GROUP_NAME);

            /* Create group's characters list*/
            JSONArray allCharactersArray = jsonObject.getJSONArray(CHARACTERS);
            for(int i = 0; i < allCharactersArray.length(); i++) {
                JSONObject characterJSON = (JSONObject) allCharactersArray.get(i);
                characterGroup.addCharacter(createCharacter(characterJSON, groupName));
            }

            /* Add leader icon to group*/
            String leaderIconFile = jsonObject.getString(LEADER);
            characterGroup.setGroupLeader(AssetLoader.loadDrawableFromAssets(context, CHARACTER_GROUPS_PATH + groupName + LEADER_PATH + leaderIconFile));

            /* Add group's background*/
            String backgroundFile = jsonObject.getString(BACKGROUND);
            characterGroup.setGroupBackGround(AssetLoader.loadDrawableFromAssets(context, CHARACTER_GROUPS_PATH + groupName + BACKGROUND_PATH + backgroundFile));
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
            if(attributeName.startsWith(MISCELLANEOUS)) {
                JSONArray miscArray = characterJSON.getJSONArray(MISCELLANEOUS);
                for(int i = 0; i < miscArray.length(); i++) {
                    miscellaneous.add(miscArray.getString(i));
                }
                break;
            }
            /* Add simple features*/
            String attributeValue = characterJSON.getString(attributeName);
            /* Capture name to help build character's image*/
            if(attributeName.startsWith(NAME)) {
                characterName = attributeValue;
            }
            builder.addSimpleFeature(attributeName, attributeValue);
        }

        builder.addMiscFeaturesList(miscellaneous);

        String imagePath = CHARACTER_GROUPS_PATH + groupName + CHARACTERS_PATH + characterName.toLowerCase().replaceAll(" ", "") + CHARACTER_IMAGE_TYPE;
        Drawable image = AssetLoader.loadDrawableFromAssets(context, imagePath);
        Drawable flippedImage = AssetLoader.loadDrawableFromAssets(context, CHARACTER_GROUPS_PATH + FLIPPED_FILE + PNG_EXTENSION);
        Character character = builder.setDrawable(image).setFlippedDrawable(flippedImage).buildCharacter();
        return character;
    }

}
