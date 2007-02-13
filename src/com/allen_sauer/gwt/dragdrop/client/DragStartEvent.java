package com.allen_sauer.gwt.dragdrop.client;

import java.util.EventObject;

/**
 * Event containing information about the start of a drag.
 */
public class DragStartEvent extends EventObject {

  public DragStartEvent(Object source) {
    super(source);
  }
}
