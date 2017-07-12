/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/*
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com), Nathan Sweet (admin@esotericsoftware.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.cookbook.samples;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cookbook.platforms.PlatformResolver;
import com.cookbook.utils.CameraHelper;
import com.cookbook.utils.DebugHelper;

public abstract class GdxSample extends InputAdapter implements ApplicationListener {
	protected static PlatformResolver m_platformResolver = null;
	private CameraHelper cameraHelper;
	private DebugHelper debugHelper;

	public static PlatformResolver getPlatformResolver() {
		return m_platformResolver;
	}

	public static void setPlatformResolver(PlatformResolver platformResolver) {
		m_platformResolver = platformResolver;
	}

	public void create () {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		debugHelper = new DebugHelper();
	}

	public void resume () {
	}

	public void render () {
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		OrthographicCamera camera = getCamera();
		if (camera == null) return;

		cameraHelper.update(Gdx.graphics.getDeltaTime());
		debugHelper.render(camera);
		cameraHelper.applyTo(camera);
	}

	public void resize (int width, int height) {
	}

	public void pause () {
	}

	public void dispose () {
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.SPACE:
				debugHelper.toggleEnabled();
				break;
		}

		return false;
	}

	protected OrthographicCamera getCamera() {
		return null;
	}
}
