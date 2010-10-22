/*
 * Copyright 2009 Fred Sauer
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express
 * or implied. See the License for the specific language governing permissions
 * and limitations under
 * the License.
 */
package com.allen_sauer.gwt.dnd.client.util;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/*
 * * Class to represent a rectangular region of a widget relative to another
 * widget. Also keeps
 * track of the size of the widget borders and its inner width and height.
 */
public class WidgetArea extends AbstractArea {

  public WidgetArea(Widget widget, Widget reference) {
    setLeft(widget.getAbsoluteLeft());
    setTop(widget.getAbsoluteTop());

    if (reference != null) {
      setLeft(
          getLeft() - reference.getAbsoluteLeft() - DOMUtil.getBorderLeft(reference.getElement()));
      setTop(getTop() - reference.getAbsoluteTop() - DOMUtil.getBorderTop(reference.getElement()));
    }
    setRight(getLeft() + widget.getOffsetWidth());
    setBottom(getTop() + widget.getOffsetHeight());

    Element elem = widget.getElement().getOffsetParent();
    Element p;

    while (elem != null && (p = elem.getOffsetParent()) != null) {
      if (!"visible".equals(DOMUtil.getEffectiveStyle(elem, "overflow"))) {
        int left = elem.getAbsoluteLeft();

        if (getLeft() < left) {
          setLeft(left);
        }

        int top = elem.getAbsoluteTop();
        if (getTop() < top) {
          setTop(top);
        }

        int bottom = top + elem.getOffsetHeight();
        if (getBottom() > bottom) {
          setBottom(Math.max(getTop(), bottom));
        }

        int right = left + elem.getOffsetWidth();
        if (getRight() > right) {
          setRight(Math.max(getLeft(), right));
        }
      }

      elem = p;
    }
  }

}
