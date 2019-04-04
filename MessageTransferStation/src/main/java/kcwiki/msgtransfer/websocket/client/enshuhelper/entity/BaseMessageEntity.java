package kcwiki.msgtransfer.websocket.client.enshuhelper.entity;

public class BaseMessageEntity
{
  private int code;
  private HttpRepStatus status;
  private String payload;
  private String info;
  
  public int getCode()
  {
    return code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  public HttpRepStatus getStatus()
  {
    return status;
  }
  
  public void setStatus(HttpRepStatus status)
  {
    this.status = status;
  }
  
  public String getInfo()
  {
    return info;
  }
  
  public void setInfo(String info)
  {
    this.info = info;
  }
  
  public String getPayload()
  {
    return payload;
  }
  
  public void setPayload(String payload)
  {
    this.payload = payload;
  }
}
