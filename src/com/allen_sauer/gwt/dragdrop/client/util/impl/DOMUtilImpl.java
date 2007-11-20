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

import com.allen_sauer.gwt.dragdrop.client.util.DOMUtil;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.util.DOMUtil} default
 * cross-browser implementation.
 */
public abstract class DOMUtilImpl {
  /**
   * @see DOMUtil#cancelAllDocumentSelections()
   */
  public native void cancelAllDocumentSelections()
  /*-{
    $doc.selection.empty();
  }-*/;

  /**
   * @see DOMUtil#getBorderLeft(Element)
   */
  public abstract int getBorderLeft(Element elem);

  /**
   * @see DOMUtil#getBorderTop(Element)
   */
  public abstract int getBorderTop(Element elem);

  /**
   * @see DOMUtil#getClientHeight(Element)
   */
  public abstract int getClientHeight(Element elem);

  /**
   * @see DOMUtil#getClientWidth(Element)
   */
  public abstract int getClientWidth(Element elem);

  /**
   * @see DOMUtil#getHorizontalBorders(Widget)
   */
  public final int getHorizontalBorders(Widget widget) {
    return widget.getOffsetWidth() - getClientWidth(widget.getElement());
  }

  /**
   * @see DOMUtil#getNodeName(Element)
   */
  public final native String getNodeName(Element elem)
  /*-{
    return elem.nodeName;
  }-*/;

  /**
   * @see DOMUtil#getVerticalBorders(Widget)
   */
  public final int getVerticalBorders(Widget widget) {
    return widget.getOffsetHeight() - getClientHeight(widget.getElement());
  }

  /**
   * @see DOMUtil#setStatus(String)
   */
  public final native void setStatus(String text)
  /*-{
     $wnd.status = text;
  }-*/;
}
