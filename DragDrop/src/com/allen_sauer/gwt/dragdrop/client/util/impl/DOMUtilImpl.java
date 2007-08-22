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

public abstract class DOMUtilImpl {
  public abstract int getBorderLeft(Element elem);

  public abstract int getBorderTop(Element elem);

  public abstract int getClientHeight(Element elem);

  public abstract int getClientWidth(Element elem);

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

  public native void setStatus(String text)
  /*-{
     $wnd.status = text;
  }-*/;

  public native void unselect()
  /*-{
    $doc.selection.empty();
  }-*/;
}
