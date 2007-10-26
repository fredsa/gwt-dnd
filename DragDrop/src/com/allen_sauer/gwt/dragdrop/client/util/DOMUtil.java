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
package com.allen_sauer.gwt.dragdrop.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dragdrop.client.util.impl.DOMUtilImpl;

/**
 * Provides DOM utility methods.
 */
public class DOMUtil {
  private static DOMUtilImpl impl;

  static {
    impl = (DOMUtilImpl) GWT.create(DOMUtilImpl.class);
  }

  /**
   * Cancel all currently selected region(s) on the current page.
   */
  public static void cancelAllDocumentSelections() {
    impl.cancelAllDocumentSelections();
  }

  /**
   * Set an element's location as fast as possible, avoiding some of the overhead in
   * {@link AbsolutePanel#setWidgetPosition(Widget, int, int)}.
   * 
   * @param elem the element's whose position is to be modified
   * @param left the left pixel offset
   * @param top the top pixel offset
   */
  public static native void fastSetElementPosition(Element elem, int left, int top)
  /*-{
    elem.style.left = left + "px";
    elem.style.top = top + "px";
  }-*/;

  /**
   * Gets an element's CSS based 'border-left-width' in pixels or <code>0</code>
   * (zero) when the element is hidden.
   * 
   * @param elem the element to be measured
   * @return the width of the left CSS border in pixels
   */
  public static int getBorderLeft(Element elem) {
    return impl.getBorderLeft(elem);
  }

  /**
   * Gets an element's CSS based 'border-top-widget' in pixels or <code>0</code>
   * (zero) when the element is hidden.
   * 
   * @param elem the element to be measured
   * @return the width of the top CSS border in pixels
   */
  public static int getBorderTop(Element elem) {
    return impl.getBorderTop(elem);
  }

  /**
   * Gets an element's client height in pixels or <code>0</code> (zero) when
   * the element is hidden. This is equal to offset height minus the top and
   * bottom CSS borders.
   * 
   * @param elem the element to be measured
   * @return the element's client height in pixels
   */
  public static int getClientHeight(Element elem) {
    return impl.getClientHeight(elem);
  }

  /**
   * Gets an element's client widget in pixels or <code>0</code> (zero) when
   * the element is hidden. This is equal to offset width minus the left and
   * right CSS borders.
   * 
   * @param elem the element to be measured
   * @return the element's client width in pixels
   */
  public static int getClientWidth(Element elem) {
    return impl.getClientWidth(elem);
  }

  /**
   * Gets the sum of an element's left and right CSS borders in pixels.
   * 
   * @param widget the widget to be measured
   * @return the total border width in pixels
   */
  public static int getHorizontalBorders(Widget widget) {
    return impl.getHorizontalBorders(widget);
  }

  /**
   * Determine an element's node name via the <code>nodeName</code> property.
   * 
   * @param elem the element whose node name is to be determined
   * @return the element's node name
   */
  public static String getNodeName(Element elem) {
    return impl.getNodeName(elem);
  }

  /**
   * Gets the sum of an element's top and bottom CSS borders in pixels.
   * 
   * @param widget the widget to be measured
   * @return the total border height in pixels
   */
  public static int getVerticalBorders(Widget widget) {
    return impl.getVerticalBorders(widget);
  }

  /**
   * Determine if <code>parent</code> is an ancestor of <code>child</code>.
   * 
   * TODO replace with DOM.isOrHasChild after GWT Issue 1218 is addressed
   * 
   * @param parent the element to consider as the ancestor of <code>child</code>
   * @param child the element to consider as the descendant of <code>parent</code>
   * @return <code>true</code> if relationship holds
   */
  public static boolean isOrContains(Element parent, Element child) {
    return impl.isOrContains(parent, child);
  }

  /**
   * Set the browser's status bar text, if supported and enabled in the client browser.
   * 
   * @param text the message to use as the window status
   */
  public static void setStatus(String text) {
    impl.setStatus(text);
  }
}
