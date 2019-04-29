import { GetClassroomTypeListMain, AddClassroomTypeData, DeleteClassroomTypeData, SeeClassroomTypeData, EditClassroomTypeData } from '../services/ClassRoomType';
import { parse } from 'qs';
import { message } from 'antd';
import { routerRedux } from 'dva/router';

export default {
  namespace: 'ClassroomTypeDate',
  state: {
    result: [],
    resultName: [],
  },
  reducers: {
    getClassroomTypeListSuccess(state, action) {
      return {
        ...state,
        result: action.payload.result,
      };
    },
    seeClassroomTypeDataSuccess(state, action) {
      return {
        ...state,
        resultName: action.payload.resultName,
      };
    },
  },
  effects: {
      // 获取教室类型列表
    *GetClassroomTypeListMain({ payload }, { call, put }) {
      const data = yield call(GetClassroomTypeListMain);
      let result = [];
      if (data.code === 0) {
        result = data.result;
      }
      yield put({
        type: 'getClassroomTypeListSuccess',
        payload: {
          result,
        },
      });
    },

    // 添加教师类型
    *AddClassroomTypeData({ payload }, { call, put }) {
      const params = {
        name: payload.name,
      };
      const data = yield call(AddClassroomTypeData, parse(params));
      if (data.result === true) {
        message.success('添加成功');
      } else {
        message.error('添加失败');
      }
    },


    // 删除教师类型
    *DeleteClassroomTypeData({ payload }, { call, put }) {
      const params = {
        id: payload.id,
      };
      const data = yield call(DeleteClassroomTypeData, parse(params));
      if (data.result === true) {
        message.success('删除成功');
      } else {
        message.error('删除失败');
      }
    },


    // 获取要修改的教师类型
    *SeeClassroomTypeData({ payload }, { call, put }) {
      const params = {
        id: payload.id,
      };
      const data = yield call(SeeClassroomTypeData, parse(params));
      let result = [];
      if (data.code === 0) {
        result = data.result;
      }
      yield put({
        type: 'seeClassroomTypeDataSuccess',
        payload: {
          resultName: result,
        },
      });
    },


    // 修改教师类型
    *EditClassroomTypeData({ payload }, { call, put }) {
      const params = {
        id: payload.id,
        name: payload.name,
      };
      const data = yield call(EditClassroomTypeData, parse(params));
      if (data.result === true) {
        message.success('修改成功');
      } else {
        message.error('修改失败');
      }
    },


  },
  subscriptions: {
    setup({ dispatch, history }) {
      history.listen((location) => {
        if (location.pathname === '/classroomtype') {
          dispatch({
            type: 'GetClassroomTypeListMain',
          });
        } else if (location.pathname === '/place') {
          dispatch({
            type: 'GetClassroomTypeListMain',
          });
        }
      });
    },
  },
};
