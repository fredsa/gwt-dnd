/*
 * Copyright 2008 Fred Sauer
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
package com.allen_sauer.gwt.dnd.demo.client.example.palette;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.HasDragHandle;

/**
 * Widget wrapper class used by {@link PalettePanel}.
 */
public class PaletteWidget extends AbsolutePanel implements HasDragHandle {

  private FocusPanel shim = new FocusPanel();

  private final Widget widget;

  /**
   * Default constructor to wrap the provided widget.
   * 
   * @param widget the widget to be wrapped
   */
  public PaletteWidget(Widget widget) {
    this.widget = widget;
    add(widget);

    // Add some CSS styling
    addStyleName("demo-PaletteWidget");
    widget.addStyleName("demo-PaletteWidget-widget");
    shim.addStyleName("demo-PaletteWidget-shim");
  }

  public PaletteWidget cloneWidget() {
    Widget clone;

    // Clone our internal widget
    if (widget instanceof Label) {
      Label label = (Label) widget;
      clone = new Label(label.getText());
    } else if (widget instanceof RadioButton) {
      RadioButton radioButton = (RadioButton) widget;
      clone = new RadioButton(radioButton.getName(), radioButton.getHTML(), true);
    } else if (widget instanceof CheckBox) {
      CheckBox checkBox = (CheckBox) widget;
      clone = new CheckBox(checkBox.getHTML(), true);
    } else {
      throw new IllegalStateException("Unhandled Widget class " + GWT.getTypeName(widget));
    }

    // Copy a few obvious common widget properties
    clone.setStyleName(widget.getStyleName());
    clone.setTitle(widget.getTitle());

    // Wrap the cloned widget in a new PaletteWidget instance
    return new PaletteWidget(clone);
  }

  public Widget getDragHandle() {
    return shim;
  }

  /**
   * Let shim size match our size.
   * 
   * @param width the desired pixel width
   * @param height the desired pixel height
   */
  public void setPixelSize(int width, int height) {
    super.setPixelSize(width, height);
    shim.setPixelSize(width, height);
  }

  /**
   * Let shim size match our size.
   * 
   * @param width the desired CSS width
   * @param height the desired CSS height
   */
  public void setSize(String width, String height) {
    super.setSize(width, height);
    shim.setSize(width, height);
  }

  /**
   * Adjust the shim size and attach once our widget dimensions are known.
   */
  protected void onLoad() {
    super.onLoad();
    shim.setPixelSize(getOffsetWidth(), getOffsetHeight());
    add(shim, 0, 0);
  }

  /**
   * Remove the shim to allow the widget to size itself when reattached.
   */
  protected void onUnload() {
    super.onUnload();
    shim.removeFromParent();
  }
}
