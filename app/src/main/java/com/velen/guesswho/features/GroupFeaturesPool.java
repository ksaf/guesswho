package com.velen.guesswho.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.velen.guesswho.gameStrings.GameStringLiterals.MISCELLANEOUS;

/**
 * This class contains the logic for adding and removing a character's features to a groupPool of all the features of a character group.
 */
public class GroupFeaturesPool {

    private final Map<String, List<String>> groupPool = new HashMap<>();
    private final Map<String, List<String>> groupPoolWithDuplicates = new HashMap<>();

    /**
     * Adds a given character's features to the groupPool, and to a separate groupPool containing duplicates.
     * @param characterFeatures An instance of a character's {@link CharacterFeatures} to be added to the groupPool.
     */
    public void addToGroupPool(CharacterFeatures characterFeatures) {
        addToGroupPool(characterFeatures, groupPool, false);
        addToGroupPool(characterFeatures, groupPoolWithDuplicates, true);
    }

    /**
     * Removes a given character's features from the groupPool, and from a separate groupPool containing duplicates.
     * @param characterFeatures An instance of a character's {@link CharacterFeatures} to be removed from the groupPool.
     */
    public void removeFromPool(CharacterFeatures characterFeatures) {
//        removeFromPool(characterFeatures, groupPool);
//        removeFromPool(characterFeatures, groupPoolWithDuplicates);
        removeFromPool(characterFeatures, groupPoolWithDuplicates, groupPool);
    }

    /**
     * @return A list with all the feature types available in the groupPool.
     */
    public List<String> getAllAvailableFeatureTypes() {
        return new ArrayList<>(groupPool.keySet());
    }

    /**
     * @param type The specific type you want to get available features for.
     * @return A List containing all feature choices available for the requested feature type.
     */
    public List<String> getAllAvailableFeaturesFor(String type) {
        return groupPool.get(type);
    }

    public List<String> getAllAvailableFeaturesIfMoreThanOneFor(String type) {
        return groupPool.get(type).size() > 1 ? groupPool.get(type) : new ArrayList<String>();
    }

    /**
     * Like {@link #getAllAvailableFeaturesFor(String)}, but will also return more instances
     * of the same feature choice if available (ex. hair: x5 black, x3 blonde, x1 red).
     * @param type The specific type you want to get available features for.
     * @return A List containing all feature choices available for the requested feature type.
     */
    public List<String> getAllAvailableFeaturesIncludingDuplicatesFor(String type) {
        return groupPoolWithDuplicates.get(type);
    }

    private void addToGroupPool(CharacterFeatures characterFeatures, Map<String, List<String>> pool, boolean includeDuplicates) {
        String[] allTypes = characterFeatures.getContainedTypes().toArray(new String[characterFeatures.getContainedTypes().size()]);
        for(String type : allTypes) {
            if(pool.get(type) == null) {
                pool.put(type, new ArrayList<String>());
            }
            List<String> featuresInPoolForType = pool.get(type);
            String featureToAdd = characterFeatures.getFeatureForSimpleType(type);
            if(includeDuplicates && featureToAdd != null) {
                featuresInPoolForType.add(featureToAdd);
            } else {
                if(!featuresInPoolForType.contains(featureToAdd) && featureToAdd != null) {
                    featuresInPoolForType.add(featureToAdd);
                }
            }
        }
        if(pool.get(MISCELLANEOUS) == null) {
            pool.put(MISCELLANEOUS, new ArrayList<String>());
        }
        if(includeDuplicates) {
            pool.get(MISCELLANEOUS).addAll(characterFeatures.getMiscFeatures());
        } else {
            for(String miscFeature : characterFeatures.getMiscFeatures()) {
                if(!pool.get(MISCELLANEOUS).contains(miscFeature)) {
                    pool.get(MISCELLANEOUS).add(miscFeature);
                }
            }
        }
    }

    private void removeFromPool(CharacterFeatures characterFeatures, Map<String, List<String>> poolWithDuplicates, Map<String, List<String>> poolWithoutDuplicates) {
        String[] allTypes = characterFeatures.getContainedTypes().toArray(new String[characterFeatures.getContainedTypes().size()]);
        for(String type : allTypes) {
            if(poolWithDuplicates.get(type) == null) {
                poolWithDuplicates.put(type, new ArrayList<String>());
            }
            List<String> featuresInPoolForType = poolWithDuplicates.get(type);
            String featureToRemove = characterFeatures.getFeatureForSimpleType(type);
            if(featuresInPoolForType.contains(featureToRemove)) {
                featuresInPoolForType.remove(featureToRemove);
                if(!featuresInPoolForType.contains(featureToRemove)) {
                    poolWithoutDuplicates.get(type).remove(featureToRemove);
                }
            }
        }
        if(poolWithDuplicates.get(MISCELLANEOUS) == null) {
            poolWithDuplicates.put(MISCELLANEOUS, new ArrayList<String>());
        }
        if(poolWithoutDuplicates.get(MISCELLANEOUS) == null) {
            poolWithoutDuplicates.put(MISCELLANEOUS, new ArrayList<String>());
        }
        for(String miscFeature : characterFeatures.getMiscFeatures()) {
            if(poolWithDuplicates.get(MISCELLANEOUS).contains(miscFeature)) {
                poolWithDuplicates.get(MISCELLANEOUS).remove(miscFeature);
                if(!poolWithDuplicates.get(MISCELLANEOUS).contains(miscFeature)) {
                    poolWithoutDuplicates.get(MISCELLANEOUS).remove(miscFeature);
                }
            }
        }
    }

}
