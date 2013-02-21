
package it.wolfed.model;

import it.wolfed.util.Constants;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ArcEdge extends Edge
{
    private String sourceId;
    private String targetId;
    
    public ArcEdge(Object parent, String id, Object value)
    {
        super(
            parent,
            id,
            value,
            null,
            null
        );  
    }
    
   /*
    * <arc id="a31" source="t7" target="p7">
    *    <inscription>
    *       <text>1</text>
    *     </inscription>
    *     <graphics/>
    *     <toolspecific tool="WoPeD" version="1.0">
    *        <probability>1.0</probability>
    *        <displayProbabilityOn>false</displayProbabilityOn>
    *        <displayProbabilityPosition x="500.0" y="0.0"/>
    *     </toolspecific>
    *  </arc>
    */
    public static ArcEdge factory(Object parent, Node dom)
    {
        NamedNodeMap transitionAttributes = dom.getAttributes();
        String id = transitionAttributes.getNamedItem(Constants.PNML_ID)
                        .getNodeValue();
        
        String sourceId = transitionAttributes.getNamedItem(Constants.PNML_SOURCE)
                             .getNodeValue();

        String targetId = transitionAttributes.getNamedItem(Constants.PNML_TARGET)
                        .getNodeValue();
        
        ArcEdge arc = new ArcEdge(parent, id, null);
        
        arc.setTargetId(targetId);
        arc.setSourceId(sourceId);
        
        return arc;
    };
    
    
    public String getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }

    public String getTargetId()
    {
        return targetId;
    }

    public void setTargetId(String targetId)
    {
        this.targetId = targetId;
    }
}