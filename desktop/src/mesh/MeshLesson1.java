package mesh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Created by toann on 8/23/2017.
 */

public class MeshLesson1 extends ApplicationAdapter {

    //Position attribute - (x, y)
    public static final int POSITION_COMPONENTS = 2;

    //Color attribute - (r, g, b, a)
    public static final int COLOR_COMPONENTS = 4;

    //Total number of components for all attributes
    public static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;

    //The "size" (total number of floats) for a single triangle
    public static final int PRIMITIVE_SIZE = 3 * NUM_COMPONENTS;

    //The maximum number of triangles our mesh will hold
    public static final int MAX_TRIANGLES = 1;

    //The maximum number of vertices our mesh will hold
    public static final int MAX_VERTICES = MAX_TRIANGLES * 3;

    //The array which holds all the data, interleaved like so:
    //    x, y, r, g, b, a
    //    x, y, r, g, b, a,
    //    x, y, r, g, b, a,
    //    ... etc ...
    protected float[] vertices = new float[MAX_VERTICES * NUM_COMPONENTS];

    //The current index that we are pushing triangles into the array
    protected int idx = 0;

    private Mesh mesh;

    @Override
    public void create() {
        mesh = new Mesh(true, MAX_VERTICES, 0,
            new VertexAttribute(Usage.Position, POSITION_COMPONENTS, ShaderProgram.POSITION_ATTRIBUTE),
            new VertexAttribute(Usage.ColorPacked, COLOR_COMPONENTS, ShaderProgram.COLOR_ATTRIBUTE));
    }
}
