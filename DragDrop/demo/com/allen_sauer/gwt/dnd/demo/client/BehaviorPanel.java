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
package com.allen_sauer.gwt.dnd.demo.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.dnd.client.util.StringUtil;

/**
 * Allows the user to select the desired drag behavior for this demonstration.
 */
class BehaviorPanel extends VerticalPanel {

  /**
   * CSS style name applied to heading HTML.
   */
  private static final String CSS_DEMO_BEHAVIOR_HEADING = "demo-behavior-heading";

  /**
   * CSS style name applied to entire behavior panel.
   */
  private static final String CSS_DEMO_BEHAVIOR_SELECTION = "demo-behavior-panel";

  BehaviorPanel(String headingHTML, String tooltip) {
    addStyleName(CSS_DEMO_BEHAVIOR_SELECTION);
    HTML heading = new HTML(headingHTML);
    heading.addStyleName(CSS_DEMO_BEHAVIOR_HEADING);
    heading.setTitle(tooltip);
    add(heading);
  }

  protected RadioButton newButton(String description, String tooltip) {
    RadioButton radioButton = new RadioButton(StringUtil.getShortTypeName(this), description, true);
    radioButton.setTitle(tooltip);
    return radioButton;
  }
}
