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

  public static HTML describe(Class controllerClass, String description) {
    String controllerClassName = controllerClass.toString();
    controllerClassName = controllerClassName.substring(controllerClassName.lastIndexOf('.') + 1);
    HTML html = new HTML("<code>" + controllerClassName + "</code><br>\n" + "<i>" + description + "</i>");
    html.addStyleName(STYLE_DEMO_EXAMPLE_DESCRIPTION);
    return html;
  }

  private int counter;

  public void add(Example example) {
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(describe(example.getDropControllerClass(), example.getDescription()));
    verticalPanel.add(example);
    add(verticalPanel, "Example " + ++counter, true);
  }
}
