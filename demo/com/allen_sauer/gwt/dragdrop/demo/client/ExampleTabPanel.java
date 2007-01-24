package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ExampleTabPanel extends TabPanel {

  private int counter;

  public void add(Panel panel, String controllerClassName, String description) {
    panel.addStyleName("example-panel");
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(describe(controllerClassName, description));
    verticalPanel.add(panel);
    add(verticalPanel, "Example " + ++counter, true);
  }

  public static HTML describe(String controllerClassName, String description) {
    HTML html = new HTML("<code>" + controllerClassName + "</code><br>\n"
        + "<i>" + description + "</i>");
    html.addStyleName("example-description");
    return html;
  }
}
