import request from '../utils/request.js';
import config from '../utils/config.js';
import { stringify } from 'qs';

const postHeader = new Headers({
  'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
});

// 登陆
export async function login(params) {
  const query = {
    cmd: 'user',
    cmd_op: 'userCheck',
    loginName: params.username,
    userPwd: params.password,
  };

  // return request('/uapi/service/cloud/httpCommandService', {
  //   method: 'post',
  //   headers: postHeader,
  //   body: stringify(query),
  // });
}

// 根据token获取用户信息
export async function getUserInfo(params) {
  const query = {
    cmd: 'user',
    cmd_op: 'userInfoByToken',
    userToken: params.token,
  };

  return request(`${config.userService}?${stringify(query)}`, {
    method: 'get',
  });
}
