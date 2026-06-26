package com.github.Jaecuber.swingShootKill.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.utils.ObjectMap;

public class UpgradeTags implements Component{
    public static final ComponentMapper<UpgradeTags> MAPPER = ComponentMapper.getFor(UpgradeTags.class);
    private ObjectMap<String, Integer> upgradeTags;
    private boolean dirty;

    public UpgradeTags(){
        upgradeTags = new ObjectMap<>();
        this.dirty = false;
    }

    public ObjectMap<String, Integer> getTags(){
        return this.upgradeTags;
    }

    public void addTag(String tag, Integer num){
        if(!upgradeTags.containsKey(tag)){
            upgradeTags.put(tag, num);
        }else{
            int newNum = upgradeTags.get(tag) + 1;
            upgradeTags.remove(tag);
            upgradeTags.put(tag, newNum);
        }
        this.dirty = true;
    }

    public boolean isDirty(){
        return this.dirty;
    }

    public void setDirty(boolean dirty){
        this.dirty = dirty;
    }
}
