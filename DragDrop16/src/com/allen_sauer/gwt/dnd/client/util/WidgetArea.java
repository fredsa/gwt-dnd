/*
 * Copyright 2009 Fred Sauer
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
package com.allen_sauer.gwt.dnd.client.util;

import com.google.gwt.user.client.ui.Widget;

/**
 * Class to represent a rectangular region of a widget relative to another
 * widget. Also keeps track of the size of the widget borders and its inner
 * width and height.
 */
public class WidgetArea extends AbstractArea {

  /**
   * Determine the area of a widget relative to a panel. The area returned is
   * such that the following are true:
   * <ul>
   * <li><code>parent.add(widget, area.getLeft(), area.getTop())</code>
   * leaves the object in the exact same location on the screen</li>
   * <li><code>area.getRight() = area.getLeft() + widget.getOffsetWidget()</code></li>
   * <li><code>area.getBottom() = area.getTop() + widget.getOffsetHeight()</code></li>
   * </ul>
   * 
   * Note that boundaryPanel need not be the parent node, or even an ancestor of
   * widget. Therefore coordinates returned may be negative or may exceed the
   * dimensions of boundaryPanel.
   * 
   * @param widget the widget whose area we seek
   * @param reference the widget relative to which we seek our area. If
   *            <code>null</code>, then <code>RootPanel().get()</code> is
   *            assumed
   */
  public WidgetArea(Widget widget, Widget reference) {
    setLeft(widget.getAbsoluteLeft());
    setTop(widget.getAbsoluteTop());

    if (reference != null) {
      setLeft(getLeft() - reference.getAbsoluteLeft()
          - DOMUtil.getBorderLeft(reference.getElement()));
      setTop(getTop() - reference.getAbsoluteTop() - DOMUtil.getBorderTop(reference.getElement()));
    }
    setRight(getLeft() + widget.getOffsetWidth());
    setBottom(getTop() + widget.getOffsetHeight());
  }
}
