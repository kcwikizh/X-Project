package kcwiki.msgtransfer.websocket.client.enshuhelper.entity;

public enum ExchangeMessageTypes
{
  PayloadError(-1),  BaseProtocol(0),  EnshuHelperRegister(1),  EnshuHelperUnregister(2),  EnshuHelperInform(3);
  
  private int code;
  
  private ExchangeMessageTypes(int code)
  {
    this.code = code;
  }

}
