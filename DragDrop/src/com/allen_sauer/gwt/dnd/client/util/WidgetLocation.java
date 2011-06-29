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
package com.allen_sauer.gwt.dnd.client.util;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/*
 * * Class representing the location of one widget relative to another.
 */
public class WidgetLocation extends AbstractLocation {

  private int left;

  // private Widget reference;
  private int referenceAdjustLeft;

  private int referenceAdjustTop;

  private int top;

  // private Widget widget;
  private int widgetLeft;

  private int widgetTop;

  /**
   * Determine location of <code>widget</code> relative to <code>reference</code>, such that
   * <code>referencePanel.add(widget, location.getLeft(), location.getTop())</code> leaves the
   * widget in the exact same location on the screen. Note that <code>reference</code> need not be
   * the parent node, or even an ancestor of <code>widget</code>. Therefore coordinates returned may
   * be negative or may exceed the dimensions of <code>reference</code>.
   * 
   * @param widget the widget whose coordinates we seek
   * @param reference the widget relative to which we seek our coordinates
   */
  public WidgetLocation(Widget widget, Widget reference) {
    internalSetWidget(widget);
    internalSetReference(reference);
    recalculate();
  }

  /**
   * Constrain the widget location to the provided minimum and maximum values.
   * 
   * @param minLeft the minimum left value
   * @param minTop the minimum top value
   * @param maxLeft the maximum left value
   * @param maxTop the maximum top value
   */
  public void constrain(int minLeft, int minTop, int maxLeft, int maxTop) {
    left = Math.max(minLeft, Math.min(left, maxLeft));
    top = Math.max(minTop, Math.min(top, maxTop));
  }

  @Override
  public int getLeft() {
    return left;
  }

  // public Widget getReference() {
  // return reference;
  // }

  @Override
  public int getTop() {
    return top;
  }

  // public Widget getWidget() {
  // return widget;
  // }

  // public void setReference(Widget reference) {
  // internalSetReference(reference);
  // recalculate();
  // }
  //
  // public void setWidget(Widget widget) {
  // internalSetWidget(widget);
  // recalculate();
  // }

  @Override
  public String toString() {
    return "(" + left + ", " + top + ")";
  }

  private void internalSetReference(Widget reference) {
    // this.reference = reference;
    if (reference == null || reference == RootPanel.get()) {
      referenceAdjustLeft = 0;
      referenceAdjustTop = 0;
    } else {
      referenceAdjustLeft = reference.getAbsoluteLeft()
          + DOMUtil.getBorderLeft(reference.getElement());
      referenceAdjustTop = reference.getAbsoluteTop()
          + DOMUtil.getBorderTop(reference.getElement());
    }
  }

  private void internalSetWidget(Widget widget) {
    // this.widget = widget;
    if (widget == null || widget == RootPanel.get()) {
      widgetLeft = 0;
      widgetTop = 0;
    } else {
      widgetLeft = widget.getAbsoluteLeft() - widget.getElement().getScrollLeft();
      widgetTop = widget.getAbsoluteTop() - widget.getElement().getScrollTop();
    }
  }

  private void recalculate() {
    left = widgetLeft - referenceAdjustLeft;
    top = widgetTop - referenceAdjustTop;
  }
}
