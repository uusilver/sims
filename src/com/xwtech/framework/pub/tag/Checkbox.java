package com.xwtech.framework.pub.tag;

public class Checkbox
{
  public String name;

  public String label;

  public String value;

  public String addvalue;
  public boolean checked;

  public Checkbox(String name, String label, String value, String addvalue,boolean checked)
  {
    this.name = name;
    this.label = label;
    this.value = value;
    this.addvalue = addvalue;
    this.checked = checked;
  }
}
