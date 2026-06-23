package com.github.Jaecuber.swingShootKill.helpers;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.Jaecuber.swingShootKill.Launcher;

public class Helpers {

    private static Launcher launcher;

    public static void setLauncher(Launcher launcher1){
        launcher = launcher1;
    }


    public static Vector3 mousePosToWorld(Vector2 mousePos){
        OrthographicCamera camera = launcher.getCamera();
        return camera.unproject(new Vector3(mousePos, 0));
    }


}
