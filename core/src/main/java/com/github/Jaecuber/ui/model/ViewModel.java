package com.github.Jaecuber.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.github.Jaecuber.swingShootKill.Launcher;

public abstract class ViewModel {
    protected final Launcher launcher;
    protected final PropertyChangeSupport propertyChangeSupport;

    public ViewModel(Launcher launcher){
        this.launcher = launcher;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public <T> void onPropertyChange(String propertyName, Class<T> propType, OnPropertyChange<T> consumer){
        this.propertyChangeSupport.addPropertyChangeListener(propertyName, evt -> {
            consumer.onChange(propType.cast(evt.getNewValue()));
        });
    }

    public void clearPropertyChanges(){
        for(PropertyChangeListener listener : this.propertyChangeSupport.getPropertyChangeListeners()){
            this.propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    @FunctionalInterface
    public interface OnPropertyChange<T> {
        void onChange(T value);
    }
}
