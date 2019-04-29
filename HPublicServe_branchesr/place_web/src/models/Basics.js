import { parse } from 'qs';
import { message } from 'antd';
import { GetLogoInfo, UploadLogo, GetBasicsInfo, TodoBasicsInfo, GetBasicsSchoolList, AddBasicsSchool, DeleteBasicsSchool, EditBasicsSchool, SeeEditBasicsSchool, StudySectionList, AddStudySectionList, DeleteStudySectionList, EditStudySectionList, SeeStudySectionList } from '../services/Basics';

export default {
  namespace: 'BasicsMain',
  state: {
    result: [],
    Schooldata: [],
    SeeSchooldata: [],
    StudySectiondata: [],
    SeeStudySectiondata: [],
    GetLogoInfoData: [],
  },

  reducers: {
    getBasicsInfoSuccess(state, action) {
      return {
        ...state,
        result: action.payload.result,
      };
    },

    addBasicsSchoolSuccess(state, action) {
      return {
        ...state,
        Schooldata: action.payload.Schooldata,
      };
    },
    saveId(state, action) {
      return {
        ...state,
        school_id: action.payload.school_id,
      };
    },
    seeEditBasicsSchoolSuccess(state, action) {
      return {
        ...state,
        SeeSchooldata: action.payload.SeeSchooldata,
      };
    },
    studySectionListSuccess(state, action) {
      return {
        ...state,
        StudySectiondata: action.payload.StudySectiondata,
      };
    },
    seeStudySectiondataSuccess(state, action) {
      return {
        ...state,
        SeeStudySectiondata: action.payload.SeeStudySectiondata,
      };
    },


    getLogoInfoSuccess(state, action) {
      return {
        ...state,
        GetLogoInfoData: action.payload.GetLogoInfoData,
      };
    },
  },

  effects: {

    // 获取学校信息
    *GetBasicsInfo({ payload }, { call, put }) {
      const data = yield call(GetBasicsInfo);
      let result = [];
      if (data.code === 0) {
        result = data.result;
      }
      yield put({
        type: 'getBasicsInfoSuccess',
        payload: {
          result,
        },
      });
    },


    // 获取logo信息

    *GetLogoInfo({ payload }, { call, put }) {
      const data = yield call(GetLogoInfo);
      if (data.code === 0 && data.result.length > 0) {
        yield put({
          type: 'getLogoInfoSuccess',
          payload: {
            GetLogoInfoData: data.result,
          },
        });
      }
    },


    // logo上传
    *UploadLogo({ payload }, { call, put }) {
      const params = {
        img_name: payload.img_name,
      };
      const data = yield call(UploadLogo, parse(params));
      if (data.result === true) {
        const data = yield call(GetLogoInfo);
        if (data.code === 0 && data.result.length > 0) {
          yield put({
            type: 'getLogoInfoSuccess',
            payload: {
              GetLogoInfoData: data.result,
            },
          });
          setTimeout(() => {
            message.success('上传成功');
          }, 500);
        }
      } else {
        message.error('上传失败');
      }
    },


    // 添加学校弹窗
    *TodoBasicsInfo({ payload }, { call, put }) {
      const params = {
        cn_name: payload.cn_name,
        en_name: payload.en_name,
        location: payload.location,
        mail: payload.mail,
        phone: payload.phone,
        postcode: payload.postcode,
        region: payload.region,
        website: payload.website,
        fax: payload.fax,
      };
      const data = yield call(TodoBasicsInfo, parse(params));
      if (data.result === true) {
        message.success('添加成功');
      } else {
        message.error('添加失败');
      }
    },


    // 获取校区信息
    *GetBasicsSchoolList({ payload }, { call, put, select }) {
      const data = yield call(GetBasicsSchoolList);
      let Schooldata = [];
      if (data.code === 0 && data.result.length > 0) {
        Schooldata = data.result;
      }
      yield put({
        type: 'addBasicsSchoolSuccess',
        payload: {
          Schooldata,
        },
      });
    },


    // 添加校区
    *AddBasicsSchool({ payload }, { call, put }) {
      const params = {
        fax: payload.fax,
        location: payload.location,
        name: payload.name,
        postcode: payload.postcode,
        telephone: payload.telephone,
      };
      const data = yield call(AddBasicsSchool, parse(params));
      if (data.result === true) {
        message.success('添加成功');
      } else {
        message.error('添加失败');
      }
    },


    // 删除校区
    *DeleteBasicsSchool({ payload }, { call, put }) {
      const params = {
        id: payload.id,
      };
      const data = yield call(DeleteBasicsSchool, parse(params));
      if (data.result === '1') {
        message.success('删除成功');
      } else if (data.result === '2') {
        message.error('删除失败');
      } else if (data.result === '3') {
        message.error('该校区下存在地点，请先删除子地点');
      } else {
        message.error('删除失败');
      }
    },


    // 查找编辑校区
    *SeeEditBasicsSchool({ payload }, { call, put }) {
      const params = {
        id: payload.id,
      };
      const data = yield call(SeeEditBasicsSchool, parse(params));
      if (data.code === 0 || data.result.length > 0) {
        yield put({
          type: 'seeEditBasicsSchoolSuccess',
          payload: {
            SeeSchooldata: data.result[0],
          },
        });
      }
    },


    // 编辑校区

    *EditBasicsSchool({ payload }, { call, put }) {
      const params = {
        id: payload.id,
        fax: payload.fax,
        location: payload.location,
        name: payload.name,
        postcode: payload.postcode,
        telephone: payload.telephone,
      };
      const data = yield call(EditBasicsSchool, parse(params));
      if (data.result === true) {
        message.success('修改成功');
      } else {
        message.error('修改失败');
      }
    },


    // 获取学段列表

    *StudySectionList({ payload }, { call, put }) {
      const data = yield call(StudySectionList);
      if (data.code === 0 || data.result.length > 0) {
        yield put({
          type: 'studySectionListSuccess',
          payload: {
            StudySectiondata: data.result,
          },
        });
      }
    },


    // 添加学段

    *AddStudySectionList({ payload }, { call, put }) {
      const params = {
        number: payload.number,
        remark: payload.remark,
        name: payload.name,
        schoolSystem: payload.schoolSystem,
        status: payload.status,
      };
      const data = yield call(AddStudySectionList, parse(params));
      if (data.result === true) {
        message.success('添加成功');
      } else {
        message.error('添加失败');
      }
    },


    // 删除学段
    *DeleteStudySectionList({ payload }, { call, put }) {
      const params = {
        id: payload.id,
      };
      const data = yield call(DeleteStudySectionList, parse(params));
      if (data.result === true) {
        message.success('删除成功');
      } else {
        message.error('删除失败');
      }
    },


    // 修改学段

    *EditStudySectionList({ payload }, { call, put }) {
      const params = {
        id: payload.id,
        number: payload.number,
        name: payload.name,
        remark: payload.remark,
        schoolSystem: payload.schoolSystem,
        status: payload.status,
      };
      const data = yield call(EditStudySectionList, parse(params));
      if (data.result === true) {
        message.success('修改成功');
      } else {
        message.error('修改失败');
      }
    },


    // 查找修改学段
    *SeeStudySectionList({ payload }, { call, put }) {
      const params = {
        id: payload.id,
      };
      const data = yield call(SeeStudySectionList, parse(params));
      if (data.code === 0 || data.result.length > 0) {
        yield put({
          type: 'seeStudySectiondataSuccess',
          payload: {
            SeeStudySectiondata: data.result[0],
          },
        });
      }
    },

  },

  subscriptions: {
    setup({ dispatch, history }) {
      history.listen((location) => {
        if (location.pathname === '/') {
          dispatch({
            type: 'GetBasicsInfo',
          });


          dispatch({
            type: 'GetBasicsSchoolList',
          });

          dispatch({
            type: 'StudySectionList',
          });


          dispatch({
            type: 'GetLogoInfo',
          });
        }
      });
    },
  },
};
