/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xcontrolled.exception;

import org.iharu.exception.BaseException;
import org.iharu.type.error.ErrorType;

/**
 *
 * @author iHaru
 */
public class XControlledModuleConnectFail extends BaseException {
    
    public XControlledModuleConnectFail(ErrorType errorType, String module, String msg, Throwable sourceException) {
        super(errorType, module, msg, sourceException);
    }
    
    public XControlledModuleConnectFail(ErrorType errorType, String module, String msg) {
        super(errorType, module, msg);
    }
    
    public XControlledModuleConnectFail(ErrorType errorType, String msg) {
        super(errorType, msg);
    }
    
    public XControlledModuleConnectFail(ErrorType errorType, Throwable sourceException) {
        super(errorType, sourceException);
    }
    
    public XControlledModuleConnectFail(ErrorType errorType) {
        super(errorType);
    }
    
}
