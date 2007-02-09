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
package com.allen_sauer.gwt.dragdrop.client.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * Library utility methods.
 */
public class UIUtil {

  public static native int getBorderLeft(Element elem) /*-{
   // Compare to null since undefined not always JavaScript keyword
   if (elem.clientLeft != null) {
   return elem.clientLeft;
   } else if ($doc.defaultView != null) {
   var borderLeftWidth = $doc.defaultView.getComputedStyle(elem, null).getPropertyValue("border-left-width");
   return borderLeftWidth.indexOf("px") == -1 ? 0 : parseInt(borderLeftWidth.substr(0, borderLeftWidth.length - 2));
   } else {
   throw "Unable to determine border-left-width";
   }
   }-*/;

  public static native int getBorderTop(Element elem) /*-{
   // Compare to null since undefined not always JavaScript keyword
   if (elem.clientTop != null) {
   return elem.clientTop;
   } else if ($doc.defaultView != null) {
   var borderTopWidth = $doc.defaultView.getComputedStyle(elem, null).getPropertyValue("border-top-width");
   return borderTopWidth.indexOf("px") == -1 ? 0 : parseInt(borderTopWidth.substr(0, borderTopWidth.length - 2));
   } else {
   throw "Unable to determine border-top-width";
   }
   }-*/;

  // TODO remove after fix for issue 626
  // http://code.google.com/p/google-web-toolkit/issues/detail?id=626
  public static void resetStylePositionStatic(Element element) {
    DOM.setStyleAttribute(element, "left", "");
    DOM.setStyleAttribute(element, "top", "");
    DOM.setStyleAttribute(element, "position", "static");
  }

}
