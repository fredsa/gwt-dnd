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
package com.allen_sauer.gwt.dnd.client.drop;

import com.google.gwt.user.client.ui.Panel;

/**
 * A {@link DropController} which allows a draggable widget to be placed at valid positions
 * (locations) on the drop target, such as {@link com.google.gwt.user.client.ui.AbsolutePanel},
 * {@link com.google.gwt.user.client.ui.VerticalPanel} or
 * {@link com.google.gwt.user.client.ui.HorizontalPanel}. Which positions are valid is determined
 * by the implementing subclass.
 */
public abstract class AbstractPositioningDropController extends AbstractDropController {

  /**
   * Constructor.
   * @param dropTarget the drop target to use
   */
  public AbstractPositioningDropController(Panel dropTarget) {
    super(dropTarget);
  }
}
