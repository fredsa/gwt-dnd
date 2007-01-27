/*
 * Copyright 2006 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dragdrop.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.drop.BoundryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.AbstractDropController;
import com.allen_sauer.gwt.dragdrop.client.util.Area;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A helper class to track all instances of
 * {@link com.allen_sauer.gwt.dragdrop.demo.client.drop.AbstractDropController}.
 * 
 */
public class DropControllerCollection {

  private static final DropControllerCollection SINGLETON = new DropControllerCollection();

  public static DropControllerCollection singleton() {
    return SINGLETON;
  }

  private Collection dropTargetCollection = new ArrayList();

  private DropControllerCollection() {
  }

  public void add(AbstractDropController dropTargetPanel) {
    this.dropTargetCollection.add(dropTargetPanel);
  }

  public AbstractDropController getIntersectDropController(Widget widget,
      Panel boundryPanel) {
    Area widgetArea = new Area(widget, null);
    Area boundryArea = new Area(boundryPanel, null);
    AbstractDropController result = null;
    for (Iterator iterator = this.dropTargetCollection.iterator(); iterator.hasNext();) {
      AbstractDropController dropController = (AbstractDropController) iterator.next();
      Panel target = dropController.getDropTargetPanel();
      Area targetArea = new Area(target, null);
      if (dropController instanceof BoundryDropController
          || targetArea.intersects(boundryArea)
          && widgetArea.intersects(targetArea)) {
        if (result == null
            || DOM.isOrHasChild(result.getDropTargetPanel().getElement(),
                target.getElement())) {
          result = dropController;
        }
      }
    }
    return result;
  }

}
