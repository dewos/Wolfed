
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
    /**
     * Set the geometric aspect of the Vertex
     *  <graphics> 
     *      <position x="200" y="70"/>
     * 	    <dimension x="40" y="40"/> 
     * 	</graphics> 
     * @param graphics 
     */
    public void setGraphics(Node graphics){
        if(graphics == null) {
            setGeometry(new mxGeometry(0, 0, 40, 40));
        }
        else{

        for (final Node childNode : new IterableNodeList(graphics.getChildNodes()))
        {
            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                switch (childNode.getNodeName())
                {
                    case Constants.PNML_GRAPHICS_POSITION:
                        NamedNodeMap positionAttributes = childNode.getAttributes();
                        getGeometry().setX(Double.valueOf(positionAttributes.getNamedItem(Constants.PNML_GRAPHICS_DIMENSION_X).getNodeValue()));
                        getGeometry().setY(Double.valueOf(positionAttributes.getNamedItem(Constants.PNML_GRAPHICS_DIMENSION_Y).getNodeValue()));
                        
                        break;
                    case Constants.PNML_GRAPHICS_DIMENSION:
                        NamedNodeMap dimensionAttributes = childNode.getAttributes();
                        
                        getGeometry().setWidth(Double.valueOf(dimensionAttributes.getNamedItem(Constants.PNML_GRAPHICS_DIMENSION_X).getNodeValue()));
                        getGeometry().setHeight(Double.valueOf(dimensionAttributes.getNamedItem(Constants.PNML_GRAPHICS_DIMENSION_Y).getNodeValue()));
                        break;
                }
                
        }
            }
        }
    }
}
