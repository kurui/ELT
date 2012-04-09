package com.chinarewards.gwt.elt.client.department.model;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTree;

public interface MyTreeResources extends CellTree.Resources {
	

    @Source("tree_closed.gif")
    ImageResource cellTreeClosedItem();
    

    @Source("tree_open.gif")
    ImageResource cellTreeOpenItem();
    
    @Source("CellTree.css")
    CellTree.Style cellTreeStyle();
    

}


