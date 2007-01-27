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
package com.allen_sauer.gwt.dragdrop.client.temp;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Temporary class which extends {@link com.google.gwt.user.client.ui.FlowPanel}
 * to allow for inserts at a specified index. To be removed once GWT implements
 * this functionality natively.
 * 
 * TODO remove after enhancement for issue 616
 * http://code.google.com/p/google-web-toolkit/issues/detail?id=616
 */
public class IndexedFlowPanel extends FlowPanel {

  /**
   * Inserts a widget before the specified index.
   * 
   * @param w the widget to be inserted
   * @param beforeIndex the index before which it will be inserted
   * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is out of
   *           range
   */
  public void insert(Widget w, int beforeIndex) {
      w.removeFromParent();
      super.insert(w, null, beforeIndex);
      DOM.insertChild(getElement(), w.getElement(), beforeIndex);
  }

}
