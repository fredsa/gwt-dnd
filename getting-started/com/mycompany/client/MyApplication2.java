/*
 * Copyright 2008 Fred Sauer
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
package com.mycompany.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;

/**
 * Illustrative example.
 */
public class MyApplication2 implements EntryPoint {

  public class IconDropController extends SimpleDropController {

    public IconDropController(Panel panel) {
      super(panel);
    }

    @Override
    public void onDrop(DragContext context) {
      super.onDrop(context);
      Widget draggable = context.draggable;
      int top = context.desiredDraggableY - context.boundaryPanel.getAbsoluteTop();
      int left = context.desiredDraggableX - context.boundaryPanel.getAbsoluteLeft();
      draggable.getElement().getStyle().setProperty("top", top + "px");
      draggable.getElement().getStyle().setProperty("left", left + "px");
    }
  }

  public void onModuleLoad() {
    ScrollPanel scrP = new ScrollPanel();
    scrP.setSize("400px", "500px");
    RootPanel.get().add(scrP);
    VerticalPanel vp = new VerticalPanel();
    scrP.add(vp);
    AbsolutePanel fp1 = new AbsolutePanel();
    fp1.setSize("350px", "500px");
    fp1.getElement().getStyle().setProperty("border", "solid 2px black");
    AbsolutePanel fp2 = new AbsolutePanel();
    fp2.setSize("350px", "500px");
    fp2.getElement().getStyle().setProperty("border", "solid 2px black");
    AbsolutePanel fp3 = new AbsolutePanel();
    fp3.setSize("350px", "500px");
    fp3.getElement().getStyle().setProperty("border", "solid 2px black");
    AbsolutePanel fp4 = new AbsolutePanel();
    fp4.setSize("350px", "500px");
    fp4.getElement().getStyle().setProperty("border", "solid 2px black");
    vp.add(fp1);
    vp.add(fp2);
    vp.add(fp3);
    vp.add(fp4);
    Image l1 = new Image();
    l1.setSize("40px", "40px");
    l1.getElement().getStyle().setProperty("position", "absolute");
    l1.getElement().getStyle().setProperty("background", "red");
    Image l2 = new Image();
    l2.setSize("40px", "40px");
    l2.getElement().getStyle().setProperty("position", "absolute");
    l2.getElement().getStyle().setProperty("background", "lime");
    fp1.add(l2);
    fp4.add(l1);

    // setup dragging
    PickupDragController dragController = new PickupDragController(fp1, false);
    dragController.setBehaviorDragProxy(true);
    dragController.setBehaviorMultipleSelection(false);
    dragController.setBehaviorConstrainedToBoundaryPanel(true);
    dragController.makeDraggable(l2);
    IconDropController dropController = new IconDropController(fp1);
    dragController.registerDropController(dropController);

    PickupDragController dragController2 = new PickupDragController(fp4, false);
    dragController2.setBehaviorDragProxy(true);
    dragController2.setBehaviorMultipleSelection(false);
    dragController2.setBehaviorConstrainedToBoundaryPanel(true);
    dragController2.makeDraggable(l1);
    IconDropController dropController2 = new IconDropController(fp4);
    dragController2.registerDropController(dropController2);
  }
}
