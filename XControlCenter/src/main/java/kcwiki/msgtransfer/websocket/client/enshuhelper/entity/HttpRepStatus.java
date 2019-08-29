package kcwiki.msgtransfer.websocket.client.enshuhelper.entity;

public enum HttpRepStatus
{
  UNKNOWNS(0),  ERROR(-1),  FAILURE(1),  SUCCESS(2),  OK(200),  Created(201),  Accepted(202),  Not_Modified(304),  Bad_Request(400),  Unauthorized(401),  Payment_Required(402),  Forbidden(403),  Not_Acceptable(406),  Conflict(409),  Unavailable_For_Legal_Reasons(451),  Internal_Server_Error(500),  Service_Unavailable(503),  Network_Authentication_Required(511);
  
  private final int code;
  
  private HttpRepStatus(int code)
  {
    this.code = code;
  }
  
}
