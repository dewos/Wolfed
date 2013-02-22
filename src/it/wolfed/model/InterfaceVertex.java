
package it.wolfed.model;

import it.wolfed.util.Constants;

public class InterfaceVertex extends Vertex
{
    public InterfaceVertex(Object parent, String id, Object value)
    {
        super(
            parent,
            id,
            value,
            0, 0, 40, 40,
            Constants.STYLE_INTERFACE
        );
    }
}