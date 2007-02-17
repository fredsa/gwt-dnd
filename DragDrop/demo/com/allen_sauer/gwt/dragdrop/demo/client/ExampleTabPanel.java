package com.allen_sauer.gwt.dragdrop.demo.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

/**
 * TabPanel which uses a VeriticalPanel to provide a description for each
 * example.
 */
public class ExampleTabPanel extends TabPanel {

  private static final String STYLE_DEMO_EXAMPLE_DESCRIPTION = "demo-example-description";

  public static HTML describe(String controllerClassName, String description) {
    HTML html = new HTML("<code>" + controllerClassName + "</code><br>\n" + "<i>" + description + "</i>");
    html.addStyleName(STYLE_DEMO_EXAMPLE_DESCRIPTION);
    return html;
  }

  private int counter;

  public void add(Example example) {
    String controllerClassName = example.getDropControllerClass().toString();
    controllerClassName = controllerClassName.substring(controllerClassName.lastIndexOf('.') + 1);
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(describe(controllerClassName, example.getDescription()));
    verticalPanel.add(example);
    add(verticalPanel, "Example " + ++counter, true);
  }
}
