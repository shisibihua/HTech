package com.honghe.user.apidoc;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/12/24
 */
public class User {


    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=userCheck 用户认证
     * @apiGroup user
     * @apiDescription 用户验证
     * @apiVersion 1.0.1
     * @apiParam {String} loginName 登录名(可以是邮箱或用户名或学籍号或手机号)
     * @apiParam {String} userPwd  密码
     * @apiParam {String} token  系统标识(不是必须)
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 结果json格式,字段:
     * userId(用户id)
     * userName(用户名)
     * userType(用户身份)
     * userRealName(用户真实姓名)
     * userPath(用户头像地址)
     * userAvatar(用户头像图片名称)
     * userGender(用户性别 1代表男 2代表女 0 未知)
     * userBirthday(用户出生日期)
     * userMobile(手机号)
     * userEmail(邮箱)
     * userAddress(家庭住址)
     * userNum(学籍号)
     * userRegdate(注册时间)
     * userStatus(用户状态  0-用户注册未激活 1-用户正常使用 2-用户被禁用 3-用户未激活被禁用)
     */

    public void userCheck() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=userSearch 用户信息查询
     * @apiGroup user
     * @apiDescription 参数可以传递userId或loginName或userMobile或userEmail
     * @apiVersion 1.0.5
     * @apiParam {String} userId 用户id,多个用,分割
     * @apiParam {String} userName 用户名
     * @apiParam {String} userMobile 手机号
     * @apiParam {String} userEmail 邮箱
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 在结果json格式
     * 1 参数为userId或loginName返回结果为
     * userId(用户id)
     * userName(用户名)
     * userType(用户身份)
     * userRealName(用户真实姓名)
     * userPath(用户头像地址)
     * userAvatar(用户头像图片名称)
     * userGender(用户性别 1代表男 2代表女 0 未知)
     * userBirthday(用户出生日期)
     * userMobile(手机号)
     * userEmail(邮箱)
     * userAddress(家庭住址)
     * userNum(学籍号)
     * userRegdate(注册时间)
     * userStatus(用户状态  0-用户注册未激活 1-用户正常使用 2-用户被禁用 3-用户未激活被禁用)
     * agencyId(机构id)
     * agencyName(结构名称)
     * 2 参数为userMobile或userEmail返回结果为
     * userId(用户id)
     */
    public void userSearch() {

    }


    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=userSearchByPage 用户信息分页查询
     * @apiGroup user
     * @apiDescription 分页获取用户信息列表(不建议使用)
     * @apiVersion 1.0.0
     * @apiParam {String} page  第几页
     * @apiParam {String} pageSize 每页显示记录数
     * @apiParam {String} loginName(不是必须) 用户名或手机号或邮箱或学籍号
     * @apiParam {String} token 系统标识(不是必须) 如果此参数存在则查询所有系统用户,否则只查询当前token代表的系统用户
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 在结果json格式
     * userId(用户id)
     * userName(用户名)
     * userType(用户身份)
     * userRealName(用户真实姓名)
     * userPath(用户头像地址)
     * userAvatar(用户头像图片名称)
     * userGender(用户性别 1代表男 2代表女 0 未知)
     * userBirthday(用户出生日期)
     * userMobile(手机号)
     * userEmail(邮箱)
     * userAddress(家庭住址)
     * userNum(学籍号)
     * userRegdate(注册时间)
     * userStatus(用户状态  0-用户注册未激活 1-用户正常使用 2-用户被禁用 3-用户未激活被禁用)
     * roleId(角色id)
     * roleName(角色名称)
     */
    public void userSearchByPage() {

    }


    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=userTokenSearchByPage 非本系统用户信息分页查询
     * @apiGroup user
     * @apiDescription 根据系统标识获取其它系统用户信息(主要用做非本系统用户导入功能)
     * @apiVersion 1.0.0
     * @apiParam {String} page  第几页
     * @apiParam {String} pageSize 每页显示记录数
     * @apiParam {String} token 系统标识
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 在结果json格式
     * userId(用户id)
     * userName(用户名)
     * userType(用户身份)
     * userRealName(用户真实姓名)
     * userPath(用户头像地址)
     * userAvatar(用户头像图片名称)
     * userGender(用户性别 1代表男 2代表女 0 未知)
     * userBirthday(用户出生日期)
     * userMobile(手机号)
     * userEmail(邮箱)
     * userAddress(家庭住址)
     * userNum(学籍号)
     * userRegdate(注册时间)
     * userStatus(用户状态  0-用户注册未激活 1-用户正常使用 2-用户被禁用 3-用户未激活被禁用)
     * token 系统标识(如果一个用户属于多个系统,token值用,分割)
     */
    public void userTokenSearchByPage() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=userTypeSearch 用户身份查询
     * @apiGroup user
     * @apiDescription 获取所有用户身份
     * @apiVersion 1.0.0
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 结果json格式,字段:
     * typeId(身份id)
     * typeName(身份名称)
     */
    public void userTypeSearch() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=userAdminSearchByAgency 根据机构id获取所属机构管理员信息
     * @apiGroup user
     * @apiDescription 根据机构id获取所属机构管理员信息
     * @apiVersion 1.0.0
     * @apiParam {String} agencyId 机构id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result 结果json格式,字段:
     * userId(用户id)
     * userRealName(用户真实姓名)
     * agencyId(机构id)
     */
    public void userAdminSearchByAgency() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=userDelete 删除用户
     * @apiGroup user
     * @apiDescription 删除用户
     * @apiVersion 1.0.0
     * @apiParam {String} userId  用户id
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result true 或 false
     */
    public void userDelete() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=userAdd 增加用户
     * @apiGroup user
     * @apiDescription 增加用户
     * @apiVersion 1.0.0
     * @apiParam {String} userName  用户名 可以为空
     * @apiParam {String} userPwd  密码
     * @apiParam {String} userType  用户身份
     * @apiParam {String} userRealName  真实姓名 可以为空
     * @apiParam {String} userPath  用户头像图片路径
     * @apiParam {String} userAvatar  用户头像图片名称
     * @apiParam {String} userGender  用户性别
     * @apiParam {String} userBirthday  出生日期
     * @apiParam {String} userMobile   手机号
     * @apiParam {String} userEmail  邮箱
     * @apiParam {String} userAddress  家庭住址
     * @apiParam {String} userNum  学籍号
     * @apiParam {String} token  系统标识
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result -1 表示已存在, 0 失败, 成功返回用户id
     */
    public void userAdd() {

    }


    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=userUpdate 修改用户信息
     * @apiGroup user
     * @apiDescription 修改用户信息包括修改用户密码(userOldPwd, userPwd)和修改用户基本信息
     * @apiVersion 1.0.0
     * @apiParam {String} userId  用户名id
     * @apiParam {String} userOldPwd  原有密码
     * @apiParam {String} userPwd  新密码
     * @apiParam {String} userType  用户身份
     * @apiParam {String} userRealName  真实姓名
     * @apiParam {String} userPath  用户头像图片路径
     * @apiParam {String} userAvatar  用户头像图片名称
     * @apiParam {String} userGender  用户性别
     * @apiParam {String} userBirthday  出生日期
     * @apiParam {String} userMobile   手机号
     * @apiParam {String} userEmail  邮箱
     * @apiParam {String} userAddress  家庭住址
     * @apiParam {String} userNum  学籍号
     * @apiParam {String} userStatus  用户状态 (0-用户注册未激活 1-用户正常使用 2-用户被禁用 3-用户未激活被禁用',)
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result true 或 false
     */
    public void userUpdate() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=hasUserEmail 用户邮箱是否存在
     * @apiGroup user
     * @apiDescription 用户邮箱是否存在
     * @apiVersion 1.0.0
     * @apiParam {String} userEmail 邮箱号
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result true 或 false
     */
    public void hasUserEmail() {

    }

    /**
     * @api {post} /service/cloud/httpCommandService?cmd=user&cmd_op=hasUserMobile 用户手机号是否存在
     * @apiGroup user
     * @apiDescription 用户手机号是否存在
     * @apiVersion 1.0.0
     * @apiParam {String} userMobile  手机号
     * @apiSuccess {String} code 0为正确,-1参数错误,-2没有此方法
     * @apiSuccess {String} name 请求方法名称
     * @apiSuccess {String} result true 或 false
     */
    public void hasUserMobile() {

    }

}
