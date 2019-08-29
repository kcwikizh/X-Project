package kcwiki.msgtransfer.websocket.client.enshuhelper.entity;

public class ExchangeMessageEntity
  extends BaseMessageEntity
{
  private ExchangeMessageTypes exchangeMessageTypes;
  
  public ExchangeMessageTypes getExchangeMessageTypes()
  {
    return exchangeMessageTypes;
  }
  
  public void setExchangeMessageTypes(ExchangeMessageTypes exchangeMessageTypes)
  {
    this.exchangeMessageTypes = exchangeMessageTypes;
  }
}
