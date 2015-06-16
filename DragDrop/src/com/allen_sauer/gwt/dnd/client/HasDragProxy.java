package com.allen_sauer.gwt.dnd.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * Convenience interface for creating drag proxies from widgets.
 * 
 * @see PickupDragController#setBehaviorDragProxy(boolean)
 */
public interface HasDragProxy {

	/**
	 * Method to return drag proxy widget.
	 * 
	 * @return the drag proxy widget
	 */
	Widget getDragProxy();

}
