package com.velen.guesswho.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains the logic for adding and removing a character's features to a pool of all the features of a character group.
 */
public class FeaturesPool {

    private final Map<String, List<String>> pool = new HashMap<>();
    private final Map<String, List<String>> poolWithDuplicates = new HashMap<>();

    /**
     * Adds a given character's features to the pool, and to a separate pool containing duplicates.
     * @param characterFeatures An instance of a character's {@link CharacterFeatures} to be added to the pool.
     */
    public void addToPool(CharacterFeatures characterFeatures) {
        addToPool(characterFeatures, pool, false);
        addToPool(characterFeatures, poolWithDuplicates, true);
    }

    /**
     * Removes a given character's features from the pool, and from a separate pool containing duplicates.
     * @param characterFeatures An instance of a character's {@link CharacterFeatures} to be removed from the pool.
     */
    public void removeFromPool(CharacterFeatures characterFeatures) {
//        removeFromPool(characterFeatures, pool);
//        removeFromPool(characterFeatures, poolWithDuplicates);
        removeFromPool(characterFeatures, poolWithDuplicates, pool);
    }

    /**
     * @return A list with all the feature types available in the pool.
     */
    public List<String> getAllAvailableFeatureTypes() {
        return new ArrayList<>(pool.keySet());
    }

    /**
     * @param type The specific type you want to get available features for.
     * @return A List containing all feature choices available for the requested feature type.
     */
    public List<String> getAllAvailableFeaturesFor(String type) {
        return pool.get(type);
    }

    public List<String> getAllAvailableFeaturesIfMoreThanOneFor(String type) {
        return pool.get(type).size() > 1 ? pool.get(type) : new ArrayList<String>();
    }

    /**
     * Like {@link #getAllAvailableFeaturesFor(String)}, but will also return more instances
     * of the same feature choice if available (ex. hair: x5 black, x3 blonde, x1 red).
     * @param type The specific type you want to get available features for.
     * @return A List containing all feature choices available for the requested feature type.
     */
    public List<String> getAllAvailableFeaturesIncludingDuplicatesFor(String type) {
        return poolWithDuplicates.get(type);
    }

    private void addToPool(CharacterFeatures characterFeatures, Map<String, List<String>> pool, boolean includeDuplicates) {
        String[] allTypes = characterFeatures.getContainedTypes().toArray(new String[characterFeatures.getContainedTypes().size()]);
        for(String type : allTypes) {
            if(pool.get(type) == null) {
                pool.put(type, new ArrayList<String>());
            }
            List<String> featuresInPoolForType = pool.get(type);
            String featureToAdd = characterFeatures.getNextFeatureFor(type);
            if(includeDuplicates && featureToAdd != null) {
                featuresInPoolForType.add(featureToAdd);
            } else {
                if(!featuresInPoolForType.contains(featureToAdd) && featureToAdd != null) {
                    featuresInPoolForType.add(featureToAdd);
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
            String featureToRemove = characterFeatures.getNextFeatureFor(type);
            if(featuresInPoolForType.contains(featureToRemove)) {
                featuresInPoolForType.remove(featureToRemove);
                if(!featuresInPoolForType.contains(featureToRemove)) {
                    poolWithoutDuplicates.get(type).remove(featureToRemove);
                }
            }
        }
    }

}
