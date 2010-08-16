/*
 * Copyright 2010 Fred Sauer
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
package com.allen_sauer.gwt.dnd.demo.client.example.insertpanel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * {@link VerticalPanel} which has a permanent spacer at the end to prevent CSS collapse of the
 * panel and its parent.
 */
public class VerticalPanelWithSpacer extends VerticalPanel {
  private static final String CSS_DEMO_INSERT_PANEL_EXAMPLE_SPACER = "demo-InsertPanelExample-spacer";

  public VerticalPanelWithSpacer() {
    Label spacerLabel = new Label("");
    spacerLabel.setStylePrimaryName(CSS_DEMO_INSERT_PANEL_EXAMPLE_SPACER);
    super.add(spacerLabel);
  }

  @Override
  public void add(Widget w) {
    super.insert(w, getWidgetCount() - 1);
  }

  @Override
  public void insert(Widget w, int beforeIndex) {
    if (beforeIndex == getWidgetCount()) {
      beforeIndex--;
    }
    super.insert(w, beforeIndex);
  }

}
