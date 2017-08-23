package mesh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Created by toann on 8/23/2017.
 */

public class MeshLesson1 extends ApplicationAdapter {

    //region Fields

    /**
     * Position attribute - (x, y)
     */
    public static final int POSITION_COMPONENTS = 2;

    /**
     * Color attribute - (r, g, b, a)
     */
    public static final int COLOR_COMPONENTS = 1;

    /**
     * Total number of components for all attributes
     */
    public static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;

    /**
     * The "size" (total number of floats) for a single triangle
     */
    public static final int PRIMITIVE_SIZE = 3 * NUM_COMPONENTS;

    /**
     * The maximum number of triangles our mesh will hold
     */
    public static final int MAX_TRIANGLES = 1;

    /**
     * The maximum number of vertices our mesh will hold
     */
    public static final int MAX_VERTICES = MAX_TRIANGLES * 3;

    /**
     * The array which holds all the data, interleaved like so:
     *      x, y, r, g, b, a
     *      x, y, r, g, b, a
     *      x, y, r, g, b, a
     *      x, y, r, g, b, a
     *      ... etc ...
     */
    protected float[] vertices = new float[MAX_VERTICES * NUM_COMPONENTS];

    /**
     * The current index that we are pushing triangles into the array
     */
    private int idx = 0;

    private Mesh mesh;
    private OrthographicCamera camera;
    private ShaderProgram shader;

    //endregion

    @Override
    public void create() {
        mesh = new Mesh(true, MAX_VERTICES, 0,
            new VertexAttribute(Usage.Position, POSITION_COMPONENTS, ShaderProgram.POSITION_ATTRIBUTE),
            new VertexAttribute(Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE));

        camera = new OrthographicCamera();
        shader = createMeshShader();
    }

    private ShaderProgram createMeshShader() {
        ShaderProgram.pedantic = false;
        ShaderProgram shader = new ShaderProgram(Gdx.files.internal("triangle.vert"), Gdx.files.internal("triangle.frag"));

        if (!shader.isCompiled())
            Gdx.app.error("Mesh Lesson", "" + shader.getLog());

        return shader;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //push a few triangles to the batch
        drawTriangle(10, 10, 40, 40, Color.RED);
        drawTriangle(50, 50, 70, 40, Color.BLUE);

        //this will render the above triangles to GL, using Mesh
        flush();
    }

    private void drawTriangle(float x, float y, float width, float height, Color color) {
        //we don't want to hit any index out of bounds exception...
        //so we need to flush the batch if we can't store any more verts
        if (idx == vertices.length) flush();

        float c = color.toFloatBits();

        //now we push the vertex data into our array
        //we are assuming (0, 0) is lower left, and Y is up

        // bottom left vertex
        vertices[idx++] = x;                    // position (x, y)
        vertices[idx++] = y;
        vertices[idx++] = c;                    // color (r, g, b, a)

        // top left vertex
        vertices[idx++] = x;                    // position (x, y)
        vertices[idx++] = y + height;
        vertices[idx++] = c;                    // color (r, g, b, a)

        // bottom right vertex
        vertices[idx++] = x + width;            // position (x, y)
        vertices[idx++] = y;
        vertices[idx++] = c;                    // color (r, g, b, a)
    }

    private void flush() {
        //if we've already flushed
        if (idx == 0) return;

        //sends our vertex data to the mesh
        mesh.setVertices(vertices);

        // no need for depth...
        Gdx.gl.glDepthMask(false);

        // enable blending, for alpha
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //number of vertices we need to render
        int vertexCount = idx / NUM_COMPONENTS;

        //update the camera with our Y-up coordinates
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //start the shader before setting any uniforms
        shader.begin();

        //update the projection matrix so our triangles are rendered in 2D
        shader.setUniformMatrix("u_projTrans", camera.combined);

        //render the mesh
        mesh.render(shader, GL20.GL_TRIANGLES, 0, vertexCount);

        shader.end();

        //re-enable depth to reset states to their default
        Gdx.gl.glDepthMask(true);

        //reset index to zero
        idx = 0;
    }

    public static void main(String[] args) {
        new LwjglApplication(new MeshLesson1(), "Mesh", 800, 600);
    }
}
