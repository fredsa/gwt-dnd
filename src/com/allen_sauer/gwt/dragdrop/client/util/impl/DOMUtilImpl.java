/*
 * Copyright 2007 Fred Sauer
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
package com.allen_sauer.gwt.dragdrop.client.util.impl;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.util.DOMUtil} default
 * cross-browser implementation.
 */
public abstract class DOMUtilImpl {
  /**
   * Cancel all currently selected region(s) on the current page.
   */
  public native void cancelAllDocumentSelections()
  /*-{
    $doc.selection.empty();
  }-*/;

  /**
   * Determine an element's left border width.
   * 
   * @param elem the element who's left border width is to be retrieved
   * @return the element's left border width in pixels
   */
  public abstract int getBorderLeft(Element elem);

  /**
   * Determine an element's top border width.
   * 
   * @param elem the element who's top border width is to be retrieved
   * @return the element's top border width in pixels
   */
  public abstract int getBorderTop(Element elem);

  /**
   * Determine an element's client height, which is the offset
   * width minus top and bottom border widths.
   * 
   * @param elem the element who's client height is to be retrieved
   * @return the element's client height in pixels
   */
  public abstract int getClientHeight(Element elem);

  /**
   * Determine an element's client width, which is the offset
   * width minus left and right border widths.
   * 
   * @param elem the element who's client width is to be retrieved
   * @return the element's client width in pixels
   */
  public abstract int getClientWidth(Element elem);

  /**
   * Determine an widget's horizontal border width, which is the sum
   * of the left and right border widths.
   * 
   * @param widget the widget's who's horizontal border width is to be determined
   * @return the widget's horizontal border width
   */
  public final int getHorizontalBorders(Widget widget) {
    return widget.getOffsetWidth() - getClientWidth(widget.getElement());
  }

  /**
   * Determine an element's node name via the <code>nodeName</code> property.
   * 
   * @param element the element who's node name is to be determined
   * @return the element's node name
   */
  public final native String getNodeName(Element element)
  /*-{
    return element.nodeName;
  }-*/;

  /**
   * Determine an widget's vertical border width, which is the sum
   * of the top and bottom border widths.
   * 
   * @param widget the widget's who's vertical border width is to be determined
   * @return the widget's vertical border width
   */
  public final int getVerticalBorders(Widget widget) {
    return widget.getOffsetHeight() - getClientHeight(widget.getElement());
  }

  /**
   * Determine if <code>parent</code> is an ancestor of <code>child</code>.
   * 
   * @param parent the element to consider as the ancestor of <code>child</code>
   * @param child the element to consider as the descendent of <code>parent</code>
   * @return <code>true</code> if relationship holds
   */
  public native boolean isOrContains(Element parent, Element child)
  /*-{
    return parent.contains(child);
  }-*/;

  /**
   * Set the browser's status bar text, if supported and enabled in the client browser.
   * 
   * @param text the message to use as the window status
   */
  public final native void setStatus(String text)
  /*-{
     $wnd.status = text;
  }-*/;
}
