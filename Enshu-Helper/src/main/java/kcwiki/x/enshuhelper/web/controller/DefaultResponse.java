/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping(value = {"/"}, produces = "application/json;charset=UTF-8")
public class DefaultResponse extends BaseController {
    
}
