/**
 * 
 * MIT LICENSE
 * 
 * Copyright 2022 Philip Gilde & Oskar Stanschus
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * @author Philip Gilde & Oskar Stanschus
 * 
 */
package de.pogs.rl.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.pogs.rl.RocketLauncher;
import de.pogs.rl.game.GameScreen;
/**
 * Schildleiste
 */
public class PlayerArmor extends HUDComponent {

    private float width;
    private float height;

    public float progress = 0f;
    private float currProg = 0f;
    private float progress_response = 0.1f;
    private BitmapFont font;

    private Color color1 = new Color(0x349eebff);
    private Color color2 = new Color(0x2164c2ff);

    public PlayerArmor() {
        super();
        progress = 0.15f;
    }

    @Override
    public void render(SpriteBatch batch) {
        font.setColor(Color.WHITE);
        font.draw(batch,
                Math.round(GameScreen.getPlayer().getArmor()) + " | "
                        + Math.round(GameScreen.getPlayer().getMaxArmor()),
                position.x + width * 0.02f,
                position.y + height - ((height - (font.getCapHeight())) / 2));

    }

    @Override
    public void update(float delta) {
        this.progress = GameScreen.getPlayer().getArmor() / GameScreen.getPlayer().getMaxArmor();
        position.set(HUDUtils.getBottomCenter().x - width / 2,
                HUDUtils.getBottomCenter().y + height * 1.3f);
        updateAngle();
    }

    @Override
    public void resize(float width, float height) {
        this.width = (float) (width * 0.32);
        this.height = (float) (this.width * 0.07);
        font = RocketLauncher.getAssetHelper().getFont("superstar",
                (int) Math.ceil(this.height * 0.7));
    }

    public void updateAngle() {
        currProg += (progress - currProg) * progress_response;
        if (currProg > 1)
            currProg = 1f;
        if (currProg < 0)
            currProg = 0;
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    @Override
    public void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(new Color(0x26262691));
        shapeRenderer.rect(position.x, position.y, width, height);
        shapeRenderer.rect(position.x + width * 0.01f, position.y + width * 0.01f,
                (width - width * 0.02f) * currProg, height - width * 0.02f, color2, color1, color1,
                color2);
    }

}
