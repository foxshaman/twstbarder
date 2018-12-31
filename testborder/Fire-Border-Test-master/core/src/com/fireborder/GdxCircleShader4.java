package com.fireborder;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.fireborder.plazma.PartEffect;
import com.fireborder.plazma.PartPlazma;

/**
 * Created by F0RIS on 09.02.2018.
 */
public class GdxCircleShader4 extends ApplicationAdapter {
    private static final Pixmap.Format TEXTURE_FORMAT = Pixmap.Format.RGBA8888;
    SpriteBatch batch;
    Texture virusTexture;
    private Texture badlogicTexture;
    private String vertexShader;
    private String fragmentShader;
    private ShaderProgram shaderProgram;
    private ShaderProgram shaderProgram1;
    private Texture circleImg;
    private Texture glowMask;
    private Texture coinTexture;
    private PartEffect pr;
    private PartEffect pr1;

    @Override
    public void create() {
        batch = new SpriteBatch();
        pr = new PartEffect(new PartPlazma(Gdx.files.classpath("fire.prt"), Gdx.files.classpath(""), 40), new PartPlazma(Gdx.files.classpath("smake1.prt"), Gdx.files.classpath(""), 70));
        pr.starter();
        pr.start();
        pr1 = new PartEffect(new PartPlazma(Gdx.files.classpath("fire.prt"), Gdx.files.classpath(""), 40), new PartPlazma(Gdx.files.classpath("smake1.prt"), Gdx.files.classpath(""), 70));
        pr1.starter();
        pr1.start();
        
        virusTexture = new Texture(Gdx.files.internal("_virus.png"), TEXTURE_FORMAT, false);
        virusTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        badlogicTexture = new Texture(Gdx.files.internal("badlogic.jpg"), TEXTURE_FORMAT, false);
        badlogicTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        coinTexture = new Texture(Gdx.files.internal("_coin_cell.png"), TEXTURE_FORMAT, false);
        coinTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);


        glowMask = new Texture(Gdx.files.internal("_glow_mask.png"), Pixmap.Format.LuminanceAlpha, false);
        glowMask.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);

        ShaderProgram.pedantic = false;

        vertexShader = Gdx.files.internal("shader/vertex.glsl").readString();
        fragmentShader = Gdx.files.internal("shader/circle_fragment_shader4.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader, fragmentShader);

        if (!shaderProgram.isCompiled()) {
            throw new GdxRuntimeException("Couldn't compile shader: " + shaderProgram.getLog());
        }

        circleImg = new Texture(Gdx.files.internal("_empty_cell.png"), TEXTURE_FORMAT, false);
        circleImg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        ///////////////////////////////////
        shaderProgram1 = batch.getShader();
        batch.setShader(shaderProgram);
        shaderProgram.begin();
    }


    float degree = 0;

    float coinSize = 50;
    Vector2 coinPos = new Vector2(400, 450);
    Color coinColor = new Color(Color.CORAL);

    float time = 0;
    
    float i = 0;
    boolean ch = true;

    @Override
    public void render() {
    	batch.setShader(shaderProgram);
        float color = 0.1f;
        Gdx.gl.glClearColor(color, color, color, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.enableBlending();
        batch.begin();

        //red virus or booster
        Color redClr = new Color(Color.RED);
        batch.setColor(redClr);
        batch.draw(virusTexture, 450, 300, 150, 150);


        Color blue = new Color(Color.BLUE);
        blue.a = ShaderParams.HAS_BORDER; //ask for a border
        batch.setColor(blue);
        batch.draw(badlogicTexture, 0, 0, 400, 400);


        batch.setColor(Color.WHITE);
        batch.draw(coinTexture, coinPos.x, coinPos.y, coinSize, coinSize);

        time += .05;

        coinColor.a = Math.min((float) Math.abs(Math.sin(time)), 0.85f);
        batch.setColor(coinColor);
        batch.draw(glowMask, coinPos.x - coinSize / 2, coinPos.y - coinSize / 2, coinSize * 2, coinSize * 2);
////////////////////////////////////////////////////
        batch.setShader(shaderProgram1);
        i = i + 1;
        pr.draw(batch, (float) Math.abs(Math.sin(time / 10)) * 400 + 70, 400, (float) 0.5, 40);
        pr.draw(batch, 700, 400, (float) 0.5, (float) Math.abs(Math.sin(time / 10)) * 100 + 40);
        
        batch.end();
        
    }

    @Override
    public void dispose() {
        batch.dispose();
        virusTexture.dispose();
        ///////////////////////////
        pr.dispose();
    }
}
