package com.fireborder.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fireborder.GdxCircleShader4;
import com.fireborder.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new MyGdxGame(), config);
		config.width = 1366;
		config.height = 720;
		new LwjglApplication(new GdxCircleShader4(), config);
	}
}
