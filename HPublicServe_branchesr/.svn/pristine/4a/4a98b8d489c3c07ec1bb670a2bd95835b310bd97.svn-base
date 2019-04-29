import request from '../utils/request.js';
import { stringify } from 'qs';
import config from '../utils/config.js';

// 获取左侧树
export async function getPlaceTree(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'area_tree',
    userId: params.userId,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}

// 获取地点列表
export async function getPlaceTableMain(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'area_arealistByPage',
    p_id: params.areaId,
    page: params.page,
    pageSize: params.pageSize,
    name: params.searchWord,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 获取地点类型
export async function getPlaceType(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'area_typelist',
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 添加地点
export async function AddPlaceData(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'area_areaadd',
    isselect: params.isselect,
    name: params.name,
    number: params.number,
    p_id: params.p_id,
    remark: params.remark,
    type_id: params.type_id,
    roomtype_id: params.roomtype_id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 删除地点
export async function DeletePlaceData(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'area_areadelete',
    id: params.id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 获取要修改地点信息
export async function SeeEditPlaceData(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'area_areainfo',
    id: params.id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 修改地点信息
export async function EditPlaceDataMain(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'area_areaupdateById',
    id: params.id,
    name: params.name,
    isselect: params.isselect,
    number: params.number,
    p_id: params.p_id,
    remark: params.remark,
    type_id: params.type_id,
    roomtype_id: params.roomtype_id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 地点信息搜索
export async function SeachPlaceDataMain(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'area_areaseachByPage',
    name: params.name,
    p_id: params.areaId,
    page: params.page,
    pageSize: params.pageSize,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}
