package com.github.Jaecuber.swingShootKill.data;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class StatusBag {

    private Array<StatusEntry> statusEffects = new Array<>();
    private final ObjectMap<String, StatusEntry> statusMap = new ObjectMap<>();

    public StatusBag() {}

    public void initializeMap() {
        statusMap.clear();
        for (StatusEntry status : statusEffects) {
            statusMap.put(status.getName(), status);
        }
    }

   
    public StatusEntry getStatusConfig(String id) {
        return statusMap.get(id);
    }

    public Array<StatusEntry> getStatusEffects() {
        return statusEffects;
    }

    public void setStatusConfig(Array<StatusEntry> statusEffects) {
        this.statusEffects = statusEffects;
        initializeMap(); 
    }
}
