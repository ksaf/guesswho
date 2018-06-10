package com.velen.guesswho.characters;

import android.graphics.drawable.Drawable;

import com.velen.guesswho.features.GroupFeaturesPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A class holding a group of characters in a list*/
public class CharacterGroup {

    private List<Character> characters = new ArrayList<>();
    private GroupFeaturesPool groupFeaturesPool = new GroupFeaturesPool();
    private Drawable groupLeader;
    private Drawable groupBackGround;
    private String groupName;

    /**
     * @return A list of characters currently in the group.
     */
    public List<Character> getCharacters() {
        return characters;
    }
    /**
     * Adds a character to this group.
     * @param character Character to add.
     * @return True if the character was added, otherwise false.
     */
    public boolean addCharacter(Character character) {
        groupFeaturesPool.addToGroupPool(character.getFeatures());
        return characters.add(character);
    }

    /**
     * Removes a character to from this group.
     * @param character Character to remove.
     * @return True if the character was removed, otherwise false.
     */
    public boolean removeCharacter(Character character) {
        groupFeaturesPool.removeFromPool(character.getFeatures());
        return characters.remove(character);
    }

    /**
     * Shuffles the characters in this group.
     * @return shuffledList A shuffled list with all the characters in this group.
     */
    public List<Character> shuffleGroup() {
        List<Character> shuffledList = new ArrayList<>(characters);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }

    /**
     * @return The group size.
     */
    public int getGroupSize() {
        return characters.size();
    }

    /**
     * @return A Class containing all the available features of all characters in this group.
     */
    public GroupFeaturesPool getAllAvailableFeaturesForGroup() {
        return groupFeaturesPool;
    }

    /**
     * Returns a sub-CharacterGroup containing the characters that have a specific feature.
     * @param featureType The type of feature you want to check for (gender, eye color, ..)
     * @param featureChoice The choice for that feature (male, green, ..).
     * @return The new CharacterGroup.
     */
    public CharacterGroup getCharactersWithFeature(String featureType, String featureChoice) {
        CharacterGroup subGroup = new CharacterGroup();
        for(int i = 0; i<characters.size(); i++) {
            if(characters.get(i).hasFeature(featureType, featureChoice)) {
                subGroup.addCharacter(characters.get(i));
            }
        }
        return subGroup;
    }

    /**
     * Returns a sub-CharacterGroup containing the characters that do not have a specific feature.
     * @param featureType The type of feature you want to check for (gender, eye color, ..)
     * @param featureChoice The choice for that feature (male, green, ..).
     * @return The new CharacterGroup.
     */
    public CharacterGroup getCharactersWithoutFeature(String featureType, String featureChoice) {
        CharacterGroup subGroup = new CharacterGroup();
        for(int i = 0; i<characters.size(); i++) {
            if(!characters.get(i).hasFeature(featureType, featureChoice)) {
                subGroup.addCharacter(characters.get(i));
            }
        }
        return subGroup;
    }

    /**
     * @param character The character to check.
     * @return True if the character is part of this group, or false if not.
     */
    public boolean isInGroup(Character character) {
        return characters.contains(character);
    }

    /**
     * Splits the group in a number of partitions. Original group remains unchanged.
     * @param partitionToGet Which partition to return.
     * @param partitionsNumber How many partitions to split to.
     * @return A new CharacterGroup which is a fragment of the original.
     */
    public CharacterGroup getSplitGroup(int partitionToGet, int partitionsNumber) {
        List<List<Character>> partitions = new ArrayList<>();
        int partitionSize = characters.size() / partitionsNumber;
        int leftOver = characters.size() % partitionsNumber;
        int take = partitionSize;

        for(int i = 0, iT = characters.size(); i < iT; i += take) {
            if( leftOver > 0 )
            {
                leftOver--;

                take = partitionSize + 1;
            }
            else
            {
                take = partitionSize;
            }

            partitions.add( new ArrayList<>( characters.subList( i, Math.min( iT, i + take ) ) ) );
        }

        List<CharacterGroup> partitionsOfGroup = new ArrayList<>();
        for(int j = 0; j < partitions.size(); j++) {
            partitionsOfGroup.add(makeFromList(partitions.get(j)));
        }
        return partitionsOfGroup.get(partitionToGet - 1);
    }

    public static CharacterGroup makeFromList(List<Character> characters) {
        CharacterGroup newGroup = new CharacterGroup();
        for(Character c : characters) {
            newGroup.addCharacter(c);
        }
        return newGroup;
    }

    public void setGroupBackGround(Drawable groupBackGround) {
        this.groupBackGround = groupBackGround;
    }

    public Drawable getGroupBackGround() {
        return groupBackGround;
    }

    public void setGroupLeader(Drawable groupLeader) {
        this.groupLeader = groupLeader;
    }

    public Drawable getGroupLeader() {
        return groupLeader;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
