package com.github.Jaecuber.swingShootKill.data;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class StatusBag {

    private Array<StatusEntry> statusTypes = new Array<>();
    private final ObjectMap<String, StatusEntry> statusMap = new ObjectMap<>();

    public StatusBag() {}

    public void initializeMap() {
        statusMap.clear();
        for (StatusEntry status : statusTypes) {
            statusMap.put(status.getName(), status);
        }
    }

   
    public StatusEntry getBulletConfig(String id) {
        return statusMap.get(id);
    }

    public Array<StatusEntry> getBulletTypes() {
        return statusTypes;
    }

    public void setBulletTypes(Array<StatusEntry> statusTypes) {
        this.statusTypes = statusTypes;
        initializeMap(); 
    }
}
