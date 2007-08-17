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

public class DOMUtilImplIE6 extends DOMUtilImpl {
  public native int getBorderLeft(Element elem)
  /*-{
    try {
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
      throw new Error("JavaScript Exception caught by DOMUtil.getBorderLeft():\n" + e);
    }
  }-*/;

  public native int getBorderTop(Element elem)
  /*-{
    try {
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
      throw new Error("JavaScript Exception caught by DOMUtil.getBorderTop():\n" + e);
    }
  }-*/;

  public native int getClientHeight(Element elem)
  /*-{
    try{
      // Compare to null since undefined not always JavaScript keyword
      if (elem.clientHeight != null) {
        return elem.clientHeight;
      } else {
        // Safari when element is hidden
        return 0;
      }
    } catch(e) {
      throw new Error("JavaScript Exception caught by DOMUtil.getClientHeight():\n" + e);
    }
  }-*/;

  public native int getClientWidth(Element elem)
  /*-{
    try{
      // Compare to null since undefined not always JavaScript keyword
      if (elem.clientWidth != null) {
        return elem.clientWidth;
      } else {
        // Safari when element is hidden
        return 0;
      }
    } catch(e) {
      throw new Error("JavaScript Exception caught by DOMUtil.getClientWidth():\n" + e);
    }
  }-*/;

  public int getHorizontalBorders(Widget widget) {
    return widget.getOffsetWidth() - getClientWidth(widget.getElement());
  }

  public native String getNodeName(Element element)
  /*-{
    return element.nodeName;
  }-*/;

  public int getVerticalBorders(Widget widget) {
    return widget.getOffsetHeight() - getClientHeight(widget.getElement());
  }

  public native void preventSelection(Element elem)
  /*-{
    elem.onselectstart = function() { return false; }
  }-*/;

  public native void unselect()
  /*-{
    // $doc.execCommand('Unselect', false, null);
    $doc.selection.empty();
    // $wnd.getSelection().removeAllRanges();
  }-*/;
}
