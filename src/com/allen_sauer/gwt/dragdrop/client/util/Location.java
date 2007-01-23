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

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A position represented by a left (x) and top (y) coordinate.
 */
public class Location {

  private int left;
  private int top;

  public Location(int left, int top) {
    this.left = left;
    this.top = top;
  }

  public Location(Widget widget, AbsolutePanel boundryPanel) {
    this.left = widget.getAbsoluteLeft()
        - (boundryPanel == null ? 0 : boundryPanel.getAbsoluteLeft());
    this.top = widget.getAbsoluteTop()
        - (boundryPanel == null ? 0 : boundryPanel.getAbsoluteTop());
  }

  public int getLeft() {
    return this.left;
  }

  public int getTop() {
    return this.top;
  }

  public String toString() {
    return "(" + this.left + ", " + this.top + ")";
  }

}
