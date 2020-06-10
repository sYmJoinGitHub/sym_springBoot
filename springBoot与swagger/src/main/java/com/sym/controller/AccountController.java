package com.sym.controller;

import com.sym.dto.AccountDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author shenyanming
 * Created on 2020/6/9 14:17
 */
@RestController
@RequestMapping("/account")
@Api("账户接口")
public class AccountController {

    @ApiOperation("列表信息")
    @GetMapping("/list")
    public Collection<AccountDTO> list() {
        return AccountDTO.list();
    }

    @ApiOperation("根据主键id获取单个账户信息")
    @GetMapping("/get/{id}")
    public AccountDTO get(@ApiParam("主键id") @PathVariable("id") Integer id) {
        return AccountDTO.get(id);
    }

}
