package com.xwtech.framework.pub.tag;

/**
 * @author yug
 *
 *静态树Tag的参数
 */
public class TreeParam
{

  public TreeParam()
  {
  }

  //菜单的唯一ID
  private String id = "";

  //菜单的父ID
  private String pid = "";

  //显示的菜单的名称
  private String name = "";

  //菜单的连接URL
  private String url = "";

  //鼠标放到菜单上显示的名称
  private String title = "";

  //打开连接时的target
  private String target = "";

  //菜单显示的图标
  private String icon = "";

  //打开菜单时显示的图标
  private String iconOpen = "";

  //菜单是否可以被点击
  private String open = "";

  /**
   * @return 返回 icon。
   */
  public String getIcon()
  {
    return icon;
  }

  /**
   * @param icon 要设置的 icon。
   */
  public void setIcon(String icon)
  {
    this.icon = icon;
  }

  /**
   * @return 返回 iconOpen。
   */
  public String getIconOpen()
  {
    return iconOpen;
  }

  /**
   * @param iconOpen 要设置的 iconOpen。
   */
  public void setIconOpen(String iconOpen)
  {
    this.iconOpen = iconOpen;
  }

  /**
   * @return 返回 id。
   */
  public String getId()
  {
    return id;
  }

  /**
   * @param id 要设置的 id。
   */
  public void setId(String id)
  {
    this.id = id;
  }

  /**
   * @return 返回 name。
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name 要设置的 name。
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return 返回 open。
   */
  public String getOpen()
  {
    return open;
  }

  /**
   * @param open 要设置的 open。
   */
  public void setOpen(String open)
  {
    this.open = open;
  }

  /**
   * @return 返回 pid。
   */
  public String getPid()
  {
    return pid;
  }

  /**
   * @param pid 要设置的 pid。
   */
  public void setPid(String pid)
  {
    this.pid = pid;
  }

  /**
   * @return 返回 target。
   */
  public String getTarget()
  {
    return target;
  }

  /**
   * @param target 要设置的 target。
   */
  public void setTarget(String target)
  {
    this.target = target;
  }

  /**
   * @return 返回 title。
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * @param title 要设置的 title。
   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * @return 返回 url。
   */
  public String getUrl()
  {
    return url;
  }

  /**
   * @param url 要设置的 url。
   */
  public void setUrl(String url)
  {
    this.url = url;
  }
}
