
package it.wolfed.model;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import it.wolfed.util.Constants;
import it.wolfed.util.IterableNodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Vertices are the node elements of the graph model. 
 */
abstract public class Vertex extends mxCell
{
    /**
     * Vertex Constructor.
     *
     * @param parent
     * @param id
     * @param value
     * @param x
     * @param y
     * @param width
     * @param height
     * @param style
     */
    public Vertex(Object parent, String id, Object value,
			double x, double y, double width, double height, String style)
    {
        setId(id);
        setValue(value);
        setGeometry(new mxGeometry(x, y, width, height));
        setStyle(style);
        setVertex(true);
        setConnectable(true);
    }
}
