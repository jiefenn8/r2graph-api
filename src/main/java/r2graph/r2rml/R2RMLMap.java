package r2graph.r2rml;

import r2graph.configmap.ConfigMap;
import r2graph.configmap.EntityMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link ConfigMap} interface.
 */
public class R2RMLMap implements ConfigMap {

    private Map<String, EntityMap> entityMaps = new HashMap<>();

    public R2RMLMap() {
    }

    /**
     * @param id        the id name of the entity map
     * @param entityMap the {@code EntityMap} to appended to map
     * @return the given {@code EntityMap} when successfully appended
     * @see ConfigMap#addEntityMap(String, EntityMap)
     */
    @Override
    public EntityMap addEntityMap(String id, EntityMap entityMap) {
        return entityMaps.put(id, entityMap);
    }

    /**
     * @return the map containing all {@code EntityMap}
     * @link{ ConfigMap#listEntityMaps()}
     * @see ConfigMap#listEntityMaps()
     */
    @Override
    public Map<String, EntityMap> listEntityMaps() {
        return entityMaps;
    }

}