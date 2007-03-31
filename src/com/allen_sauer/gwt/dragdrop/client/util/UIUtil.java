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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Library utility methods.
 */
public class UIUtil {

  /**
   * Gets an element's CSS based 'border-left-width' in pixels or <code>0</code> (zero)
   * when the element is hidden.
   * 
   * TODO deferred binding for browser specific behavior
   * 
   * @param elem the element to be measured
   * @return the width of the left CSS border
   */
  public static native int getBorderLeft(Element elem) /*-{
   try{
   // Compare to 'null' since 'undefined' not always JavaScript keyword
   if (elem.clientLeft != null) {
   return elem.clientLeft;
   } else if ($doc.defaultView != null) {
   var computedStyle = $doc.defaultView.getComputedStyle(elem, null);
   if (computedStyle != null) {
   var borderLeftWidth = computedStyle.getPropertyValue("border-left-width");
   return borderLeftWidth.indexOf("px") == -1 ? 0 : parseInt(borderLeftWidth.substr(0, borderLeftWidth.length - 2));
   } else {
   // Handle Safari when elem is hidden
   return 0;
   }
   } else {
   throw new Error("Unable to determine border-left-width");
   }
   } catch(e) {
   throw new Error("JavaScript Exception caught by UIUtil.getBorderLeft():\n" + e);
   }
   }-*/;

  /**
   * Gets an element's CSS based 'border-top-widget' in pixels or <code>0</code> (zero)
   * when the element is hidden.
   * 
   * TODO deferred binding for browser specific behavior
   * 
   * @param elem the element to be measured
   * @return the width of the top CSS border
   */
  public static native int getBorderTop(Element elem) /*-{
   try{
   // Compare to 'null' since 'undefined' not always JavaScript keyword
   if (elem.clientTop != null) {
   return elem.clientTop;
   } else if ($doc.defaultView != null) {
   var computedStyle = $doc.defaultView.getComputedStyle(elem, null);
   if (computedStyle != null) {
   var borderTopWidth = computedStyle.getPropertyValue("border-top-width");
   return borderTopWidth.indexOf("px") == -1 ? 0 : parseInt(borderTopWidth.substr(0, borderTopWidth.length - 2));
   } else {
   // Handle Safari when elem is hidden
   return 0;
   }
   } else {
   throw new Error("Unable to determine border-top-width");
   }
   } catch(e) {
   throw new Error("JavaScript Exception caught by UIUtil.getBorderTop():\n" + e);
   }
   }-*/;

  /**
   * Gets an element's client height in pixels or <code>0</code> (zero)
   * when the element is hidden. This is equal to offset height minus
   * the top and bottom CSS borders.
   * 
   * TODO deferred binding for browser specific behavior
   * 
   * @param elem the element to be measured
   * @return the element's client height
   */
  public static native int getClientHeight(Element elem) /*-{
   try{
   // Compare to null since undefined not always JavaScript keyword
   if (elem.clientHeight != null) {
   return elem.clientHeight;
   } else {
   // Safari when element is hidden
   return 0;
   }
   } catch(e) {
   throw new Error("JavaScript Exception caught by UIUtil.getClientHeight():\n" + e);
   }
   }-*/;

  /**
   * Gets an element's client widget in pixels or <code>0</code> (zero)
   * when the element is hidden. This is equal to offset widget minus 
   * the left and right CSS borders.
   * 
   * TODO deferred binding for browser specific behavior
   * 
   * @param elem the element to be measured
   * @return the element's client width
   */
  public static native int getClientWidth(Element elem) /*-{
   try{
   // Compare to null since undefined not always JavaScript keyword
   if (elem.clientWidth != null) {
   return elem.clientWidth;
   } else {
   // Safari when element is hidden
   return 0;
   }
   } catch(e) {
   throw new Error("JavaScript Exception caught by UIUtil.getClientWidth():\n" + e);
   }
   }-*/;

  /**
   * Gets the sum of an element's left and right CSS borders in pixels
   * 
   * @param widget the widget to be measured
   * @return the total border width
   */
  public static int getHorizontalBorders(Widget widget) {
    return widget.getOffsetWidth() - getClientWidth(widget.getElement());
  }

  public static native String getNodeName(Element element) /*-{
   return element.nodeName;
   }-*/;

  /**
   * Gets the sum of an element's top and bottom CSS borders in pixels
   * 
   * @param widget the widget to be measured
   * @return the total border height
   */
  public static int getVerticalBorders(Widget widget) {
    return widget.getOffsetHeight() - getClientHeight(widget.getElement());
  }

  /**
   * Resets an element to 'position: static' and removes the 'left' and 'top'
   * CSS style attributes to undo what AbsolutePanel does when a widget is attached
   * to it.
   * 
   * TODO remove after fix for <a href="http://code.google.com/p/google-web-toolkit/issues/detail?id=626">GWT issue 626</a> has been resolved
   * 
   * @param element the element to be modified
   */
  public static void resetStylePositionStatic(Element element) {
    DOM.setStyleAttribute(element, "left", "");
    DOM.setStyleAttribute(element, "top", "");
    DOM.setStyleAttribute(element, "position", "static");
  }
}
