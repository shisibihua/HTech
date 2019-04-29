package com.honghe.organization.agency.apidoc;


/**
 * Created by wei on 2016/2/16.
 */
public class Agency {
    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencySearchGetUserInfo 机构查询
     * @apiGroup agency
     * @apiDescription 查找机构信息 查询出的信息带用户信息
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId (不是必须) 机构id
     * @apiParam {String} agencyName  (不是必须) 机构名称
     * @apiParam {String} userId (不是必须) 用户id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 字段有:
     * userEmail
     * userRealName
     * userAvatar
     * agencyName
     * agencyId
     * userBirthday
     * userType
     * userAddress
     * pId
     * userGender
     * agencyLevel
     * userId
     * userMobile
     * userName
     * userPath
     * userRegdate
     */
    public void agencySearchGetUserInfo() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencySearch 机构查询
     * @apiGroup agency
     * @apiDescription 查找机构信息 查询出的信息不带用户信息
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId (不是必须) 机构id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 字段有:
     * agencyId
     * agencyName
     * pId
     * agencyLevel
     */
    public void agencySearch(){

    }


    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencySonSearch 所有下级子机构查询
     * @apiGroup agency
     * @apiDescription  根据机构id查询所有下级子机构包括下级的所有子机构
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId  机构id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 字段有:
     * id
     * name
     * agencyList
     * level
     */

    public void agencySonSearch(){

    }


    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agenecySearchByPageGetUserInfo 分页查询机构
     * @apiGroup agency
     * @apiDescription 分页查询机构信息    查询出的信息带用户信息
     * @apiVersion 1.0.0
     * @apiParam {String} page 页数
     * @apiParam {String} pageSize 每页记录数
     * @apiParam {String} agencyName (不是必须)机构名称
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 字段有:
     * userEmail
     * userRealName
     * userAvatar
     * agencyName
     * agencyId
     * userBirthday
     * userType
     * userAddress
     * pId,
     * userGender
     * agencyLevel
     * userId
     * userMobile
     * userName
     * userPath
     * userRegdate
     */
    public void agenecySearchByPageGetUserInfo() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencySearchByPage 分页查询机构
     * @apiGroup agency
     * @apiDescription 分页查询机构信息    查询出的信息不带用户信息
     * @apiVersion 1.0.0
     * @apiParam {String} page 页数
     * @apiParam {String} pageSize 每页记录数
     * @apiParam {String} agencyName (不是必须)机构名称
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 字段有:
     * agencyId
     * agencyName
     * pId
     * agencyLevel
     */

    public void agencySearchByPage() {

    }


    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyDelete 删除机构
     * @apiGroup agency
     * @apiDescription 删除机构，该机构下的子机构和用户也一起删除
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId 机构id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 返回true或false
     */
    public void agencyDelete() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyUserRelationDelete 用户与机构关系删除
     * @apiGroup agency
     * @apiDescription 删除用户与机构之间的关联
     * @apiVersion 1.0.0
     * @apiParam {String} userId 用户id
     * @apiParam {String} agencyId (非必须) 机构id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 返回true或false
     */
    public void agencyUserRelationDelete() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyAdd 添加机构
     * @apiGroup agency
     * @apiDescription 添加机构
     * @apiVersion 1.0.0
     * @apiParam {String} pId 机构父id
     * @apiParam {String} agencyName  机构名称
     * @apiParam {String} agencyLevel (不是必须) 机构等级
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 返回id为请求成功,空字符串为保存失败
     */
    public void agencyAdd() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyUserRelationAdd 用户与机构关系添加
     * @apiGroup agency
     * @apiDescription 添加用户与机构之间的关联
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId 机构id
     * @apiParam {String} userId 用户id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 返回id为请求成功,空字符串为保存失败
     */
    public void agencyUserRelationAdd() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyUpdate 更新机构
     * @apiGroup agency
     * @apiDescription 修改机构信息
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId 机构id
     * @apiParam {String} agencyName (不是必须) 机构名称
     * @apiParam {String} pId  (不是必须)机构父id
     * @apiParam {String} agencyLevel  (不是必须)机构等级
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 返回true或false
     */
    public void agencyUpdate() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyIdGetManager  获取管理员信息
     * @apiGroup agency
     * @apiDescription 根据机构id获取管理员信息
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId 机构id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 字段有:
     * id
     * type_name
     */

    public void agencyIdGetManager() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyGetSon  获取一级子机构
     * @apiGroup agency
     * @apiDescription 根据机构id获取他的一级子机构
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId 机构id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 字段有:
     * agencyId
     * agencyName
     * pId
     * agencyLevel
     */

    public void agencyGetSon() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyGetMany  根据机构多个id查询相应的多个机构信息
     * @apiGroup agency
     * @apiDescription 根据机构多个id查询相应的多个机构信息
     * @apiVersion 1.0.0
     * @apiParam {String} agencyIds 多个机构id，中间用,隔开
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 字段有:
     * agencyId
     * agencyName
     * pId
     * agencyLevel
     */

    public void agencyGetMany() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyParent  根据机构id获取他的上一级机构
     * @apiGroup agency
     * @apiDescription 根据机构id获取他的上一级机构
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId 机构id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 字段有:
     * agencyId
     * agencyName
     * pId
     * agencyLevel
     */

    public void agencyParent() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=agency&cmd_op=agencyFirstAdmin  获取第一级管理员
     * @apiGroup agency
     * @apiDescription 获取第一级管理员
     * @apiVersion 1.0.0
     * * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} type 请求方法名称
     * @apiSuccess {String} result 字段有:
     * id
     * type_name
     * userId
     */

    public void agencyFirstAdmin() {

    }
}
