/**
 * Copyright 2008 Fred Sauer.
 */
package com.allen_sauer.gwt.dnd.demo.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class MultiRowTabHistoryTokens {
  private List<String> list = new ArrayList<String>();
  private HashMap<String, Integer> map = new HashMap<String, Integer>();

  public void add(String historyToken) {
    map.put(historyToken, list.size());
    list.add(historyToken);
  }

  public String getHistoryToken(int i) {
    return list.get(i);
  }

  public Integer getIndex(String historyToken) {
    return map.get(historyToken);
  }
}
