import request from '../utils/request.js';
import { stringify } from 'qs';
import config from '../utils/config.js';

const postHeader = new Headers({
  'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
});

// 获取教室类型列表
export async function GetClassroomTypeListMain() {
  const query = {
    cmd: 'area',
    cmd_op: 'atype_list',
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 添加教室类型
export async function AddClassroomTypeData(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'atype_add',
    name: params.name,
    type: '教室',
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


//  获取要修改的教室类型
export async function SeeClassroomTypeData(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'atype_getbyid',
    id: params.id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}

//  修改教室类型
export async function EditClassroomTypeData(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'atype_update',
    id: params.id,
    name: params.name,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


//  删除教室类型
export async function DeleteClassroomTypeData(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'atype_delete',
    id: params.id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}
