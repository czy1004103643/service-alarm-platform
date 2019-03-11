package com.newegg.ec.tool.controller;

import com.newegg.ec.tool.entity.Group;
import com.newegg.ec.tool.entity.Result;
import com.newegg.ec.tool.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/7
 */
@Controller
@RequestMapping("/group/*")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @RequestMapping(value = "/getGroupList", method = RequestMethod.GET)
    @ResponseBody
    public Result getGroupList() {
        List<Group> groupList = groupService.getGroupList();
        return Result.successResult(groupList);
    }

    @RequestMapping(value = "/getGroupById", method = RequestMethod.GET)
    @ResponseBody
    public Result getGroupById(@PathParam("groupId") String groupId) {
        Group group = groupService.getGroupById(groupId);
        return Result.successResult(group);
    }

    @RequestMapping(value = "/saveGroup", method = RequestMethod.POST)
    @ResponseBody
    public Result saveGroup(@RequestBody Group group) {
        boolean status = groupService.saveGroup(group);
        return status ? Result.successResult() : Result.failResult();
    }

    @RequestMapping(value = "/deleteGroupById", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteGroupById(String groupId) {
        boolean status = groupService.deleteGroupById(groupId);
        return status ? Result.successResult() : Result.successResult();
    }

}
