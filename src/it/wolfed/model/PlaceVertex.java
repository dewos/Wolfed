
package it.wolfed.model;

import it.wolfed.util.Constants;
import it.wolfed.util.IterableNodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class PlaceVertex extends Vertex
{
    private int tokens = 0;
    
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
    
    /*
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
     */  
    public static PlaceVertex factory(Object parent, Node dom)
    {
        String id, name = "";
        int tokens = 0;
        
        NamedNodeMap placeAttributes = dom.getAttributes();
        id = placeAttributes.getNamedItem(Constants.ID).getNodeValue();

        for (final org.w3c.dom.Node childNode : new IterableNodeList(dom.getChildNodes()))
        {
            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                switch (childNode.getNodeName())
                {
                    case Constants.NAME:
                        name = childNode.getTextContent().trim();
                        break;

                    case Constants.INITIALMARKING:
                        tokens = Integer.parseInt(childNode.getTextContent().trim());
                        break;
                }
            }
        }

        PlaceVertex place = new PlaceVertex(parent, id, name);
        place.setTokens(tokens);
        
        return place;
    };

    public void setTokens(int tokens)
    {
        this.tokens = tokens;
    }
    
    /**
     * @return the tokens
     */
    public int getTokens()
    {
        return tokens;
    }
}