import request from '../utils/request.js';
import { stringify } from 'qs';
import config from '../utils/config.js';

const postHeader = new Headers({
  'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
});

// 获取基本信息
export async function GetBasicsInfo() {
  const query = {
    cmd: 'area',
    cmd_op: 'school_list',
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 获取logo信息
export async function GetLogoInfo() {
  const query = {
    cmd: 'area',
    cmd_op: 'school_picurl',
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 上传logo
export async function UploadLogo(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'school_todopic',
    img_name: params.img_name,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 添加基本信息
export async function TodoBasicsInfo(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'school_todo',
    cn_name: params.cn_name,
    en_name: params.en_name,
    location: params.location,
    mail: params.mail,
    phone: params.phone,
    postcode: params.postcode,
    region: params.region,
    website: params.website,
    fax: params.fax,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 获取校区列表
export async function GetBasicsSchoolList(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'zone_list',
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 添加校区
export async function AddBasicsSchool(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'zone_add',
    fax: params.fax,
    location: params.location,
    name: params.name,
    telephone: params.telephone,
    postcode: params.postcode,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 删除校区
export async function DeleteBasicsSchool(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'zone_delete',
    id: params.id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}

// 查出编辑校区
export async function SeeEditBasicsSchool(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'zone_getbyid',
    id: params.id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}

// 编辑校区
export async function EditBasicsSchool(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'zone_update',
    id: params.id,
    fax: params.fax,
    location: params.location,
    name: params.name,
    postcode: params.postcode,
    telephone: params.telephone,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 获取学段列表
export async function StudySectionList() {
  const query = {
    cmd: 'area',
    cmd_op: 'column_list',
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 添加学段
export async function AddStudySectionList(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'column_add',
    number: params.number,
    remark: params.remark,
    name: params.name,
    schoolSystem: params.schoolSystem,
    status: params.status,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 删除学段
export async function DeleteStudySectionList(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'column_delete',
    id: params.id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 查看修改学段
export async function SeeStudySectionList(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'column_getbyid',
    id: params.id,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}


// 修改学段
export async function EditStudySectionList(params) {
  const query = {
    cmd: 'area',
    cmd_op: 'column_update',
    id: params.id,
    number: params.number,
    name: params.name,
    remark: params.remark,
    schoolSystem: params.schoolSystem,
    status: params.status,
  };
  return request(`${config.placeService}?${stringify(query)}`, {
    method: 'get',
  });
}
