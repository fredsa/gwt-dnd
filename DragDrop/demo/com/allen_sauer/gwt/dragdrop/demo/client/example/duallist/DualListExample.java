/*
 * Copyright 2007 Fred Sauer
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
package com.allen_sauer.gwt.dragdrop.demo.client.example.duallist;

import com.allen_sauer.gwt.dragdrop.demo.client.example.Example;

/**
 * {@link com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController} example.
 */
public final class DualListExample extends Example {

  private static final String STYLENAME_DEMO_DUAL_LIST = "demo-dual-list";

  public DualListExample() {
    addStyleName(STYLENAME_DEMO_DUAL_LIST);
    DualListBox dualListBox = new DualListBox(10, "10em");
    dualListBox.addLeft("Apples");
    dualListBox.addLeft("Bananas");
    dualListBox.addLeft("Cucumbers");
    dualListBox.addLeft("Dates");
    dualListBox.addLeft("Enchiladas");
    setWidget(dualListBox);
  }

  public Class getControllerClass() {
    return ListBoxDragController.class;
  }

  public String getDescription() {
    return "Allow drag and drop between two lists.";
  }
}
