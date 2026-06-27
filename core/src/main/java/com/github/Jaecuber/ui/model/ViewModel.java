package com.github.Jaecuber.ui.model;

import com.badlogic.gdx.utils.Array;
import com.github.Jaecuber.swingShootKill.Launcher;

public abstract class ViewModel {
    protected final Launcher launcher;
    private final Array<PropertyListener> listeners;

    public ViewModel(Launcher launcher){
        this.launcher = launcher;
        this.listeners = new Array<>();
    }

    public <T> void onPropertyChange(String propertyName, Class<T> propType, OnPropertyChange<T> consumer){
        listeners.add(new PropertyListener(propertyName, newValue -> {
            consumer.onChange(propType.cast(newValue));
        }));
    }

    public void clearPropertyChanges(){
        listeners.clear();
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (oldValue != null && oldValue.equals(newValue)) {
            return;
        }
        if (oldValue == null && newValue == null) {
            return;
        }

        for (PropertyListener listener : listeners) {
            if (listener.propertyName.equals(propertyName)) {
                listener.callback.onEvent(newValue);
            }
        }
    }

    @FunctionalInterface
    public interface OnPropertyChange<T>{
        void onChange(T value);
    }

    private static class PropertyListener {
        final String propertyName;
        final EventCallback callback;

        PropertyListener(String propertyName, EventCallback callback) {
            this.propertyName = propertyName;
            this.callback = callback;
        }
    }

    private interface EventCallback {
        void onEvent(Object newValue);
    }
}
