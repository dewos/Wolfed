
package it.wolfed.model;

import com.mxgraph.model.mxGeometry;
import it.wolfed.util.Constants;
import it.wolfed.util.IterableNodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Place.
 */
public class PlaceVertex extends Vertex
{
    /**
     * Number of tokens on the place.
     * 
     * Pnml mapped from {@link Constants#PNML_INITIALMARKING}
     */
    private int tokens = 0;
    
    /**
     * PlaceVertex Constructor.
     * 
     * @param parent
     * @param id
     * @param value 
     */
    public PlaceVertex(Object parent, String id, Object value)
    {
        super(
            parent,
            id,
            value,
            0, 0, 40, 40,
            Constants.STYLE_PLACE
        );
    }
    
    /**
     * Generate a new {@link PlaceVertex} from a pnml valid dom node.
     * 
     * <place id="p2"> 
     * 	<name> 
     * 		<text>p2</text> 
     * 		<graphics> 
     * 			<offset x="200" y="110"/> 
     * 		</graphics> 
     * 	</name> 
     * 	<graphics> 
     * 		<position x="200" y="70"/>
     * 		<dimension x="40" y="40"/> 
     * 	</graphics> 
     * 	<initialMarking> 
     * 		<text>2</text>
     * 	</initialMarking> 
     * </place>
     * 
     * 
     * @param parent
     * @param dom
     * @return  PlaceVertex
     * @see <a href="http://www.pnml.org/">http://www.pnml.org/</a>
     */  
    public static PlaceVertex factory(Object parent, Node dom)
    {
        String id, value = "";
        int tokens = 0;
        Node graphicsGeometry = null;
        NamedNodeMap placeAttributes = dom.getAttributes();
        id = placeAttributes.getNamedItem(Constants.PNML_ID).getNodeValue();

        for (final org.w3c.dom.Node childNode : new IterableNodeList(dom.getChildNodes()))
        {
            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                switch (childNode.getNodeName())
                {
                    // @note pnml "name" will be mapped to "value" property
                    case Constants.PNML_NAME:
                        value = childNode.getTextContent().trim();
                        break;
                        
                    // @note pnml "initialmarking" will be mapped to "tokens" property
                    case Constants.PNML_INITIALMARKING:
                        tokens = Integer.parseInt(childNode.getTextContent().trim());
                        break;
                    case Constants.PNML_GRAPHICS:
                        graphicsGeometry = childNode;
                        break;
                }
            }
        }

        PlaceVertex place = new PlaceVertex(parent, id, value);
        place.setTokens(tokens);
        place.setGraphics(graphicsGeometry);
        return place;
    };
    
    /**
     * Sets place tokens number.
     * 
     * @param tokens 
     */
    public void setTokens(int tokens)
    {
        this.tokens = tokens;
    }
    
    /**
     * Get place token number.
     * 
     * @return the tokens
     */
    public int getTokens()
    {
        return tokens;
    }
}
