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
package com.allen_sauer.gwt.dnd.demo.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.StringUtil;
import com.allen_sauer.gwt.dnd.demo.client.example.Example;
import com.allen_sauer.gwt.dnd.demo.client.ui.MultiRowTabPanel;
import com.allen_sauer.gwt.dnd.demo.client.util.GWTUtil;

/**
 * {@link MultiRowTabPanel} which uses a {@link VerticalPanel} to provide a description
 * for each example.
 */
public final class ExampleTabPanel extends MultiRowTabPanel {

  private static final String CSS_DEMO_EXAMPLE_DESCRIPTION = "demo-example-description";

  private static final String CSS_DEMO_EXAMPLE_TAB_PANEL_TAB_BAR = "demo-ExampleTabPanel-tab-bar";

  /**
   * Describe an example in a consistent way by including a description and the
   * name of the {@link com.allen_sauer.gwt.dnd.client.drop.DropController}
   * used in the example.
   *
   * @param classes the primary DropController used in this example
   * @param description a brief description of the example
   * @return HTML widget describing the example
   */
  public static HTML describe(Class<?>[] classes, String description) {
    String sourceCodeLinks = "";
    for (int i = 0; i < classes.length; i++) {
      sourceCodeLinks += GWTUtil.getClassAnchorHTML(classes[i]);
      if (i < classes.length - 1) {
        sourceCodeLinks += ", ";
      }
    }
    HTML html = new HTML("<i>" + description + "</i><br>\n(Source code: " + sourceCodeLinks + ")");
    html.addStyleName(CSS_DEMO_EXAMPLE_DESCRIPTION);
    return html;
  }

  public ExampleTabPanel(int widgetsPerRow) {
    super(widgetsPerRow);
    addTabBarStyleName(CSS_DEMO_EXAMPLE_TAB_PANEL_TAB_BAR);
  }

  /**
   * Add another example to demonstrate.
   * @param example the example panel to add
   */
  public void add(Example example) {
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(describe(example.getInvolvedClasses(), example.getDescription()));
    verticalPanel.add(example);
    Label tabLabel = new Label("Demo " + (getTabCount() + 1));
    tabLabel.setWordWrap(false);
    String title = DOMUtil.adjustTitleForBrowser(StringUtil.getShortTypeName(example) + "\n"
        + example.getDescription());
    tabLabel.setTitle(title);
    add(verticalPanel, tabLabel);
  }
}
