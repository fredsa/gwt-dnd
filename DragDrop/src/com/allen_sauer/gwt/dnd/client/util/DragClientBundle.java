/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

// CHECKSTYLE_OFF
public interface DragClientBundle extends ClientBundle {

  interface DragCssResource extends CssResource {

    @ClassName("dragdrop-boundary")
    public String boundary();

    @ClassName("dragdrop-draggable")
    public String draggable();

    @ClassName("dragdrop-dragging")
    public String dragging();

    /**
     * CSS style name applied to drop targets.
     */
    @ClassName("dragdrop-dropTarget")
    public String dropTarget();

    /**
     * CSS style name which is applied to drop targets which are being actively engaged by the
     * current drag operation.
     */
    @ClassName("dragdrop-dropTarget-engage")
    public String dropTargetEngage();

    @ClassName("dragdrop-flow-panel-positioner")
    public String flowPanelPositioner();

    @ClassName("dragdrop-handle")
    public String handle();

    /**
     * CSS style name applied to movable panels.
     */
    @ClassName("dragdrop-movable-panel")
    public String movablePanel();

    @ClassName("dragdrop-positioner")
    public String positioner();

    /**
     * CSS style name applied to drag proxies.
     */
    @ClassName("dragdrop-proxy")
    public String proxy();

    @ClassName("dragdrop-selected")
    public String selected();
  }

  static final DragClientBundle INSTANCE = GWT.create(DragClientBundle.class);

  @Source("gwt-dnd.css")
  DragCssResource css();
}
